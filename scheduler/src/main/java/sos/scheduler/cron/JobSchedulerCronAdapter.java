/**
 * Copyright (C) 2014 BigLoupe http://bigloupe.github.io/SoS-JobScheduler/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
/********************************************************* begin of preamble
**
** Copyright (C) 2003-2012 Software- und Organisations-Service GmbH. 
** All rights reserved.
**
** This file may be used under the terms of either the 
**
**   GNU General Public License version 2.0 (GPL)
**
**   as published by the Free Software Foundation
**   http://www.gnu.org/licenses/gpl-2.0.txt and appearing in the file
**   LICENSE.GPL included in the packaging of this file. 
**
** or the
**  
**   Agreement for Purchase and Licensing
**
**   as offered by Software- und Organisations-Service GmbH
**   in the respective terms of supply that ship with this file.
**
** THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
** IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
** THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
** PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
** BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
** CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
** SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
** INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
** CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
** ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
** POSSIBILITY OF SUCH DAMAGE.
********************************************************** end of preamble*/
/*
 * JobSchedulerCronAdapter.java
 * Created on 07.09.2007
 *
 */
package sos.scheduler.cron;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sos.scheduler.misc.SchedulerJavaObject;
import sos.spooler.Job;
import sos.spooler.Variable_set;
import sos.util.SOSFile;
import sos.util.SOSFileOperations;
import sos.util.SOSLogger;
import sos.util.SOSSchedulerLogger;
import sos.xml.SOSXMLXPath;

@SuppressWarnings("deprecation")
public class JobSchedulerCronAdapter extends sos.spooler.Job_impl {

	private static final String	conXPathSPOOLER_ANSWER_ERROR		= "//spooler/answer/ERROR";
	public static final String	conAttributeJobNAME					= "name";
	public static final String	conJobSchedulerJobFileNameExtension	= ".job.xml";

	// configuration directory for Job Scheduler dynamic configuration files
	// needs an own subdirectory for converted cron jobs
	private File				schedulerCronConfigurationDir		= new File("config/live/cron");

	// Path to the crontab
	private String				crontab								= "";

	private SOSLogger			logger;

	private CronConverter		converter;

	private boolean				useDynamicConfiguration				= false;
	private String				monitoredLiveDir					= null;
	private String[]			monitoredLiveDirs					= null;
	private final Vector<File>	liveDirCrontabs						= new Vector<File>();

	private Iterator<File>		liveDirCrontabsIterator				= null;

	@Override
	public boolean spooler_init() throws Exception {
		File liveFolder = new File(spooler.configuration_directory());
		schedulerCronConfigurationDir = new File(liveFolder, "cron");
		logger = new SOSSchedulerLogger(spooler_log);
		monitoredLiveDir = spooler.variables().value("cron_adapter_dynamic_configuration_dir");
		if (monitoredLiveDir != null && monitoredLiveDir.length() > 0) {
			spooler_log.info("parameter cron_adapter_dynamic_configuration_dir: " + monitoredLiveDir);
			monitoredLiveDirs = monitoredLiveDir.split(";");
		}
		else
			monitoredLiveDir = null;
		if (spooler_task == null) {
			spooler.variables().set_var("cron_adapter_use_dynamic_configuration", "1");
			if (monitoredLiveDir == null) {
				spooler_log.info("Using dynamic configuration files. Deleting files in " + schedulerCronConfigurationDir.getAbsolutePath());
				SOSFileOperations.removeFile(schedulerCronConfigurationDir, logger);
			}
			else {
				for (String currentDir : monitoredLiveDirs) {
					File fDir = new File(currentDir);
					if (!fDir.exists()) {
						spooler_log.info(fDir.getAbsolutePath() + " doesn't exist.");
						continue;
					}
					spooler_log.debug5("Looking for " + crontab + " files in " + currentDir);
					Vector<File> crontabsInCurrentDir = SOSFile.getFilelist(currentDir, "^" + crontab + "$", 0, true);
					Iterator<File> iter = crontabsInCurrentDir.iterator();
					while (iter.hasNext()) {
						File crontabFile = iter.next();
						spooler_log.info("Deleting configuration files in " + crontabFile.getParent());
						SOSFileOperations.removeFile(crontabFile.getParentFile(), ".*\\.xml$", SOSFileOperations.GRACIOUS, logger);
					}
				}
			}
			return true;
		}
		boolean systemCrontab = false;
		boolean oldRunTime = false;
		String systemUser = System.getProperty("user.name");
		String sChangeUser = "";
		String timeout = "";
		try {
			Variable_set params = spooler_task.params();
			//schedulerCronConfigurationDir = params.value("scheduler_cron_configuration_dir");
			//logger.info("parameter scheduler_cron_configuration_dir: "+schedulerCronConfigurationDir);
			crontab = params.value("crontab");
			if (monitoredLiveDir != null && crontab.length() == 0) {
				crontab = "crontab";
			}
			if (crontab.length() == 0)
				throw new Exception("missing parameter crontab");
			logger.info("parameter crontab: " + crontab);
			String dc = spooler.variables().var("cron_adapter_use_dynamic_configuration");
			if (dc != null && dc.equals("1")) {
				useDynamicConfiguration = true;
				if (monitoredLiveDir == null)
					logger.info("Using dynamic configuration files in directory " + schedulerCronConfigurationDir.getAbsolutePath());
			}
			if (crontab.equalsIgnoreCase("/etc/crontab"))
				systemCrontab = true;
			String sOldRunTime = params.value("old_run_time");
			if (sOldRunTime.equalsIgnoreCase("1") || sOldRunTime.equalsIgnoreCase("true") || sOldRunTime.equalsIgnoreCase("yes")) {
				oldRunTime = true;
				logger.info("parameter old_run_time: true");
			}
			String sSystemCrontab = params.value("systab");
			if (sSystemCrontab != null && sSystemCrontab.length() > 0) {
				if (sSystemCrontab.equalsIgnoreCase("1") || sSystemCrontab.equalsIgnoreCase("true") || sSystemCrontab.equalsIgnoreCase("yes")) {
					systemCrontab = true;
				}
				else {
					systemCrontab = false;
				}
				logger.info("parameter systab: " + sSystemCrontab);
			}
			sChangeUser = params.value("changeuser");
			if (sChangeUser != null && sChangeUser.length() > 0) {
				logger.info("parameter changeuser: " + sChangeUser);
			}
			else
				sChangeUser = "";

			if (systemCrontab && sChangeUser.equalsIgnoreCase("su") && !systemUser.equalsIgnoreCase("root")) {
				logger.warn("You are running the Job Scheduler as "
						+ systemUser
						+ " and you are trying to use a system crontab with 'su'. This will not work. Either run the Job Scheduler as root, or use 'sudo' as change_user_command parameter");
			}
			timeout = params.value("timeout");
			if (timeout != null && timeout.length() > 0) {
				logger.info("parameter timeout: " + timeout);
			}
		}
		catch (Exception e) {
			logger.error("Error reading job parameters: " + e);
			return false;
		}

		try {
			converter = new CronConverter(logger);
			converter.setOldRunTime(oldRunTime);
			converter.setSystemCronTab(systemCrontab);
			converter.setChangeUserCommand(sChangeUser);
			if (timeout != null && timeout.length() > 0) {
				converter.setTimeout(timeout);
			}
			if (monitoredLiveDir != null) {
				for (String currentDir : monitoredLiveDirs) {
					File fDir = new File(currentDir);
					if (!fDir.exists()) {
						spooler_log.info(fDir.getAbsolutePath() + " doesn't exist.");
						continue;
					}
					spooler_log.debug5("Looking for crontab files in " + currentDir);
					Vector<File> crontabsInCurrentDir = SOSFile.getFilelist(currentDir, "^crontab$", 0, true);
					Iterator<File> iter = crontabsInCurrentDir.iterator();
					while (iter.hasNext()) {
						File crontabFile = iter.next();
						spooler_log.debug7(" found: " + crontabFile.getAbsolutePath());
						liveDirCrontabs.add(crontabFile);
					}
				}
				liveDirCrontabsIterator = liveDirCrontabs.iterator();
				if (!liveDirCrontabsIterator.hasNext()) {
					spooler_log.info("No crontabs found.");
					return false;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			logger.error("Error initializing cron converter: " + e);
			return false;
		}
		return true;
	}

	@Override
	public boolean spooler_process() throws Exception {
		try {
			File crontabFile = new File(crontab);
			if (liveDirCrontabsIterator != null) {
				crontabFile = liveDirCrontabsIterator.next();
				schedulerCronConfigurationDir = crontabFile.getParentFile();
			}
			//  This activates the file-watcher for the cron-tab file
			spooler_job.start_when_directory_changed(crontabFile.getParentFile(), "^" + crontabFile.getName() + "$");

			if (!crontabFile.canRead()) {
				logger.info("Failed to read crontab " + crontabFile.getAbsolutePath());
			}
			// check if converted jobs exits from previous runs
			HashMap<String, Element> previousMapping = (HashMap<String, Element>) SchedulerJavaObject.getObject(spooler.variables(), spooler_job.name() + "_"
					+ crontabFile.getAbsolutePath() + "_cron2job_mapping");
			if (previousMapping == null) {
				previousMapping = new HashMap<String, Element>();
			}

			HashMap<String, String> previousCommentsMapping = (HashMap<String, String>) SchedulerJavaObject.getObject(spooler.variables(), spooler_job.name()
					+ "_" + crontabFile.getAbsolutePath() + "_cron2comments_mapping");
			if (previousCommentsMapping == null)
				previousCommentsMapping = new HashMap<String, String>();
			debugHashMap(previousCommentsMapping, "previousCommentsMapping");

			HashMap<String, String> currentCommentsMapping = new HashMap<String, String>();
			HashMap<String, Element> changedJobs = new HashMap<String, Element>();
			HashSet<?> previousEnvVariables = (HashSet<?>) SchedulerJavaObject.getObject(spooler.variables(),
					spooler_job.name() + "_" + crontabFile.getAbsolutePath() + "_env_variables");
			HashSet<String> currentEnvVariables = new HashSet<String>();
			boolean environmentChanged = false;
			if (previousEnvVariables == null)
				previousEnvVariables = new HashSet<Object>();

			logger.debug1("Comparing new jobs with jobs of previous run.");
			changedJobs.putAll(previousMapping);
			converter.getReservedJobNames().clear();
			converter.getSkipLines().clear();
			Set<String> previousCronLines = previousMapping.keySet();
			BufferedReader in = new BufferedReader(new StringReader(""));
			if (crontabFile.canRead()) {
				in = new BufferedReader(new FileReader(crontabFile));
			}

			String currentLine = "";
			String currentCommentJobName = "";
			String currentCommentJobTitle = "";
			String currentCommentJobTimeout = "";

			while ((currentLine = in.readLine()) != null) {
				Matcher envMatcher = converter.cronRegExEnvironmentPattern.matcher(currentLine);

				if (envMatcher.matches()) {
					currentEnvVariables.add(currentLine);
					if (!previousEnvVariables.contains(currentLine)) {
						environmentChanged = true;
					}
					continue;
				}
				Matcher commentMatcher = converter.cronRegExCommentPattern.matcher(currentLine);
				if (commentMatcher.matches()) {
					Matcher jobNameMatcher = converter.cronRegExJobNamePattern.matcher(commentMatcher.group(1));
					Matcher jobTitleMatcher = converter.cronRegExJobTitlePattern.matcher(commentMatcher.group(1));
					Matcher jobTimeoutMatcher = converter.cronRegExJobTimeoutPattern.matcher(commentMatcher.group(1));
					if (jobNameMatcher.matches()) {
						currentCommentJobName = jobNameMatcher.group(1);
						//logger.debug9("commentname: "+currentCommentJobName);
					}
					if (jobTitleMatcher.matches()) {
						currentCommentJobTitle = jobTitleMatcher.group(1);
						//logger.debug9("commenttitle: "+currentCommentJobTitle);
					}
					if (jobTimeoutMatcher.matches()) {
						currentCommentJobTimeout = jobTimeoutMatcher.group(1);
						//logger.debug9("commenttimeout: "+currentCommentJobTimeout);
					}
					continue;
				}
				if (previousCronLines.contains(currentLine)) {
					// found unchanged Job
					Element jobElement = previousMapping.get(currentLine);
					String jobName = jobElement.getAttribute(conAttributeJobNAME);
					String previousCommentForThisJob = previousCommentsMapping.get(currentLine).toString();
					String commentForThisJob = currentCommentJobName + "-" + currentCommentJobTitle + "-" + currentCommentJobTimeout;
					if (!previousCommentForThisJob.equals(commentForThisJob)) {
						logger.debug6("Job-Manipulating comments for current line have changed.");
					}
					else {
						logger.debug6("current line was already submitted in last run: " + currentLine);
						converter.getSkipLines().add(currentLine);
						converter.getReservedJobNames().add(jobName);
						changedJobs.remove(currentLine);
					}
				}
				Matcher cronMatcher = converter.currentCronPattern.matcher(currentLine);
				if (cronMatcher.matches()) {
					currentCommentsMapping.put(currentLine, currentCommentJobName + "-" + currentCommentJobTitle + "-" + currentCommentJobTimeout);
					currentCommentJobName = "";
					currentCommentJobTitle = "";
					currentCommentJobTimeout = "";
				}
			}

			if (environmentChanged) {
				if (previousMapping.size() > 0) {
					logger.info("Environment has changed, all jobs need to be submitted again.");
				}
				converter.getSkipLines().clear();
				converter.getReservedJobNames().clear();
			}
			// (previous) names of the changed jobs. if a name has remains, the job doesn't need to be deleted
			HashSet<String> changedJobNames = new HashSet<String>();
			Collection<Element> changedJobElements = changedJobs.values();
			Iterator<Element> iter = changedJobElements.iterator();
			while (iter.hasNext()) {
				Element changedJob = iter.next();
				changedJobNames.add(changedJob.getAttribute(conAttributeJobNAME));
			}

			HashMap<String, Element> updatedMapping = new HashMap<String, Element>();
			if (crontabFile.canRead()) {
				converter.cronFile2SchedulerXML(crontabFile, updatedMapping);
			}

			logger.debug1("updating changed jobs");
			Iterator<Element> updatedJobsIterator = updatedMapping.values().iterator();
			while (updatedJobsIterator.hasNext()) {
				Element updatedJob = updatedJobsIterator.next();
				String updatedJobName = updatedJob.getAttribute(conAttributeJobNAME);
				// this job will be updated an doesn't need to be deleted afterwards
				changedJobNames.remove(updatedJobName);
				updateJob(updatedJob);
			}

			if (!changedJobNames.isEmpty()) {
				logger.debug1("removing renamed/deleted jobs");
				Iterator<String> removedJobsIter = changedJobNames.iterator();
				while (removedJobsIter.hasNext()) {
					String jobName = removedJobsIter.next().toString();
					removeJob(jobName);
				}
			}

			//Build new HashMap of current configuration
			Iterator<String> changedJobsIter = changedJobs.keySet().iterator();
			while (changedJobsIter.hasNext()) {
				previousMapping.remove(changedJobsIter.next());
			}

			previousMapping.putAll(updatedMapping);
			logger.debug3("Storing mapping to Scheduler variable");
			SchedulerJavaObject.putObject(previousMapping, spooler.variables(), spooler_job.name() + "_" + crontabFile.getAbsolutePath() + "_cron2job_mapping");
			logger.debug3("Storing comments mapping to Scheduler variable");
			debugHashMap(currentCommentsMapping, "currentCommentsMapping");
			SchedulerJavaObject.putObject(currentCommentsMapping, spooler.variables(), spooler_job.name() + "_" + crontabFile.getAbsolutePath()
					+ "_cron2comments_mapping");
			logger.debug3("Storing environment variables to Scheduler variable");
			SchedulerJavaObject.putObject(currentEnvVariables, spooler.variables(), spooler_job.name() + "_" + crontabFile.getAbsolutePath() + "_env_variables");
		}

		catch (Exception e) {
			e.printStackTrace(System.err);
			logger.error("Error updating Job Scheduler configuration from crontab: " + e);
		}

		if (liveDirCrontabsIterator != null) {
			return liveDirCrontabsIterator.hasNext();
		}

		return false;
	}

	private void debugHashMap(final HashMap<String, String> map, final String name) throws Exception {
		logger.debug9(name + ":");
		Set<String> keys = map.keySet();
		if (keys != null) {
			Iterator<String> iter = keys.iterator();
			while (iter.hasNext()) {
				String key = iter.next().toString();
				String value = map.get(key).toString();
				logger.debug9("[" + key + "]: " + value);
			}
		}
	}

	private void removeJob(final String jobName) {
		try {
			if (useDynamicConfiguration) {
				removeJobFile(jobName);
			}
			else {
				removeJobXMLCommand(jobName);
			}
			logger.info(String.format("job '%1$s' removed from JS."));
		}
		catch (Exception e) {
			try {
				logger.warn("Failed to remove job \"" + jobName + "\": " + e);
			}
			catch (Exception ex) {
			}
		}
	}

	private void removeJobXMLCommand(final String jobName) throws Exception {
		Job currentJob = spooler.job(jobName);
		if (currentJob == null) {
			throw new Exception("Could not find job: " + jobName);
		}
		currentJob.remove();
	}

	private void removeJobFile(final String jobName) throws Exception {
		String fileName = normalizeJobName(jobName);
		File jobFile = new File(schedulerCronConfigurationDir, fileName + conJobSchedulerJobFileNameExtension);
		if (!jobFile.delete()) {
			throw new Exception("Failed to delete file " + jobFile.getAbsolutePath());
		}
	}

	private void updateJob(final Element updatedJob) throws Exception {
		Document jobDocument;

		try {
			jobDocument = converter.getDocBuilder().newDocument();
			jobDocument.appendChild(jobDocument.importNode(updatedJob, true));
		}
		catch (Exception e) {
			throw new Exception("Error creating DOM Document for job: " + e, e);
		}
		if (useDynamicConfiguration) {
			updateJobFile(jobDocument);
		}
		else {
			updateJobXMLCommand(jobDocument);
		}

		Element objJob = jobDocument.getDocumentElement();
		String jobName = objJob.getAttribute(conAttributeJobNAME);

		logger.info(String.format("job '%1$s' updated in JS.", jobName));

	}

	private void updateJobXMLCommand(final Document updatedJobDoc) throws Exception {
		String jobName = "";
		try {
			Element updatedJob = updatedJobDoc.getDocumentElement();
			jobName = updatedJob.getAttribute(conAttributeJobNAME);
			StringWriter out = new StringWriter();
			OutputFormat format = new OutputFormat(updatedJobDoc);
			format.setEncoding("UTF-8");
			format.setIndenting(true);
			format.setIndent(2);
			XMLSerializer serializer = new XMLSerializer(out, format);
			serializer.setNamespaces(true);

			serializer.serialize(updatedJobDoc);
			logger.info("submitting job...  " + jobName);
			logger.debug9(out.toString());
			String answer = spooler.execute_xml(out.toString());
			logger.debug3("answer from JobScheduler: " + answer);

			SOSXMLXPath xpath = new SOSXMLXPath(new StringBuffer(answer));
			String errorMsg = "";
			if (xpath.selectSingleNodeValue(conXPathSPOOLER_ANSWER_ERROR) != null) {
				String errorCode = xpath.selectSingleNodeValue(conXPathSPOOLER_ANSWER_ERROR + "/@code");
				String errorText = xpath.selectSingleNodeValue(conXPathSPOOLER_ANSWER_ERROR + "/@text");
				errorMsg = errorCode + ":" + errorText;
			}

			if (!errorMsg.equals("")) {
				throw new Exception("JobScheduler answer ERROR:" + errorMsg + "\n" + answer);
			}
		}

		catch (Exception e) {
			e.printStackTrace(System.err);
			throw new Exception("Error occured updating job \"" + jobName + "\": " + e, e);
		}
	}

	private void updateJobFile(final Document updatedJobDoc) throws Exception {
		String jobName = "";
		File jobFile = null;
		try {
			Element updatedJob = updatedJobDoc.getDocumentElement();
			jobName = updatedJob.getAttribute(conAttributeJobNAME);
			String fileName = normalizeJobName(jobName);
			updatedJob.removeAttribute(conAttributeJobNAME);
			jobFile = new File(schedulerCronConfigurationDir, fileName + conJobSchedulerJobFileNameExtension);
			OutputStream fout = new FileOutputStream(jobFile, false);
			OutputStreamWriter out = new OutputStreamWriter(fout, "UTF-8");
			OutputFormat format = new OutputFormat(updatedJobDoc);
			format.setEncoding("UTF-8");
			format.setIndenting(true);
			format.setIndent(2);
			XMLSerializer serializer = new XMLSerializer(out, format);
			logger.debug3("Writing file " + jobFile.getAbsolutePath());
			serializer.serialize(updatedJobDoc);
			out.close();
		}

		catch (Exception e) {
			e.printStackTrace(System.err);
			if (jobFile != null) {
				throw new Exception("Error occured updating job file \"" + jobFile.getAbsolutePath() + "\": " + e, e);
			}
			else
				throw new Exception("Error occured updating file for job \"" + jobName + "\": " + e, e);
		}
	}

	private static String normalizeJobName(final String jobName) {
		String fileName = jobName.replaceAll("\\W", "_");
		return fileName;
	}

}
