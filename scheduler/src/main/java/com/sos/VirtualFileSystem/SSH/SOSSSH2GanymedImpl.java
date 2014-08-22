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
package com.sos.VirtualFileSystem.SSH;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;

import org.apache.log4j.Logger;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.HTTPProxyData;
import ch.ethz.ssh2.SFTPException;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.SFTPv3FileAttributes;
import ch.ethz.ssh2.SFTPv3FileHandle;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.sos.JSHelper.Basics.JSJobUtilities;
import com.sos.VirtualFileSystem.DataElements.SOSFileList;
import com.sos.VirtualFileSystem.DataElements.SOSFolderName;
import com.sos.VirtualFileSystem.Interfaces.ISOSAuthenticationOptions;
import com.sos.VirtualFileSystem.Interfaces.ISOSConnection;
import com.sos.VirtualFileSystem.Interfaces.ISOSConnectionOptions;
import com.sos.VirtualFileSystem.Interfaces.ISOSSession;
import com.sos.VirtualFileSystem.Interfaces.ISOSShell;
import com.sos.VirtualFileSystem.Interfaces.ISOSShellOptions;
import com.sos.VirtualFileSystem.Interfaces.ISOSVFSHandler;
import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFileSystem;
import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFolder;
import com.sos.VirtualFileSystem.Options.SOSConnection2OptionsAlternate;
import com.sos.VirtualFileSystem.Options.SOSConnection2OptionsSuperClass;
import com.sos.VirtualFileSystem.common.SOSVfsBaseClass;
import com.sos.i18n.annotation.I18NResourceBundle;

/**
* \class SOSSSH2GanymedImpl
*
* \brief SOSSSH2GanymedImpl -
*
* \details
*
* \section SOSSSH2GanymedImpl.java_intro_sec Introduction
*
* \section SOSSSH2GanymedImpl.java_samples Some Samples
*
* \code
*   .... code goes here ...
* \endcode
*
* <p style="text-align:center">
* <br />---------------------------------------------------------------------------
* <br /> APL/Software GmbH - Berlin
* <br />##### generated by ClaviusXPress (http://www.sos-berlin.com) #########
* <br />---------------------------------------------------------------------------
* </p>
* \author KB
* @version $Id: SOSSSH2GanymedImpl.java 20723 2013-07-18 18:19:56Z kb $16.05.2010
* \see reference
*
* Created on 16.05.2010 19:17:53
 */

@I18NResourceBundle(baseName = "SOSVirtualFileSystem", defaultLocale = "en")
public class SOSSSH2GanymedImpl extends SOSVfsBaseClass implements JSJobUtilities, ISOSShell, ISOSVFSHandler, ISOSVirtualFileSystem, ISOSConnection, ISOSSession {

	private final String	conClassName	= "SOSSSH2GanymedImpl";

	private final Logger			logger			= Logger.getLogger(SOSSSH2GanymedImpl.class);

	private JSJobUtilities objJSJobUtilities = this;

	public SOSSSH2GanymedImpl() {
		//
	}
	private ISOSConnectionOptions		objCO					= null;
	private ISOSAuthenticationOptions	objAO					= null;
	private ISOSShellOptions			objSO					= null;

	boolean								isAuthenticated			= false;
	boolean								isConnected				= false;

	/** Line currently being displayed on the shell **/
	protected String					strCurrentLine			= "";

	/** ssh connection object */
	protected Connection				sshConnection			= null;

	/** ssh session object */
	protected Session					sshSession				= null;

	/** Inputstreams for stdout and stderr **/
	protected InputStream				ipsStdOut;
	protected InputStream				ipsStdErr;

	private boolean						flgIsRemoteOSWindows	= false;
	private SFTPv3Client				sftpClient				= null;

	private RemoteConsumer				stdoutConsumer			= null;
	private RemoteConsumer				stderrConsumer			= null;

	/** Output from stdout and stderr **/
	protected StringBuffer				strbStdoutOutput = new StringBuffer();
	protected StringBuffer				strbStderrOutput = new StringBuffer();

	/** timestamp of the last text from stdin or stderr **/
	protected long						lngLastTime				= 0;

	private OutputStream				stdin;
	private OutputStreamWriter			stdinWriter				= null;

	private Integer						intExitStatus				= null;
	private String						strExitSignal				= null;

	private Vector<String>				vecFilesToDelete		= new Vector<String>();

	private Vector<String> getFilesToDelete() {

		if (vecFilesToDelete == null) {
			vecFilesToDelete = new Vector<String>();
		}
		return vecFilesToDelete;
	}

	@Override
	public ISOSConnection Connect(final String pstrHostName, final int pintPortNumber) throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Connect";

		try {
			isConnected = false;
			this.setSshConnection(new Connection(pstrHostName, pintPortNumber));

		}
		catch (Exception e) {
			if (this.getSshConnection() != null)
				try {
					this.getSshConnection().close();
					this.setSshConnection(null);
				}
				catch (Exception ex) {
				}
			throw new Exception(e.getMessage());
		}

		return this;
	}

	/**
	 *
	 * \brief Connect
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public ISOSConnection Connect() throws Exception {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Connect";

		try {
			isConnected = false;
			this.setSshConnection(new Connection(objCO.getHost().Value(), objCO.getPort().value()));

			if (objCO.getProxy_host().IsNotEmpty()) {
				HTTPProxyData objProxy = null;
				if (objCO.getProxy_user().IsEmpty()) {
					objProxy = new HTTPProxyData(objCO.getProxy_host().Value(), objCO.getProxy_port().value());
				}
				else {
					objProxy = new HTTPProxyData(objCO.getProxy_host().Value(), objCO.getProxy_port().value(), objCO.getProxy_user().Value(),
							objCO.getProxy_password().Value());
				}
				this.getSshConnection().setProxyData(objProxy);
			}
			this.getSshConnection().connect();
			isConnected = true;

			logger.debug(SOSVfs_D_0102.params(objCO.getHost().Value(), objCO.getPort().value()));
		}
		catch (Exception e) {
			try {
				this.setSshConnection(null);
			}
			catch (Exception ex) {
			}
			throw e;
		}

		return this;
	}

	/**
	 * Check existence of a file or directory
	 *
	 * @param psftpClient
	 * @param filename
	 * @return true, if file exists
	 * @throws Exception
	 */
	protected boolean sshFileExists(final SFTPv3Client psftpClient, final String filename) {

		try {
			SFTPv3FileAttributes attributes = psftpClient.stat(filename);

			if (attributes != null) {
				return attributes.isRegularFile() || attributes.isDirectory();
			}
			else {
				return false;
			}

		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * Checks if file is a directory
	 *
	 * @param psftpClient
	 * @param filename
	 * @return true, if filename is a directory
	 */
	protected boolean isDirectory(final SFTPv3Client psftpClient, final String filename) {
		try {
			return psftpClient.stat(filename).isDirectory();
		}
		catch (Exception e) {
		}
		return false;
	}

	/**
	 * Returns the file size of a file
	 *
	 * @param sftpClient
	 * @param filename
	 * @return the size of the file
	 * @throws Exception
	 */
	protected long getFileSize(final SFTPv3Client psftpClient, final String filename) throws Exception {
		return psftpClient.stat(filename).size.longValue();
	}

	/**
	 * Check existence of a file or directory
	 *
	 * @param sftpClient
	 * @param filename
	 * @return integer representation of file permissions
	 * @throws Exception
	 */
	protected int sshFilePermissions(final SFTPv3Client psftpClient, final String filename) {

		try {
			SFTPv3FileAttributes attributes = psftpClient.stat(filename);

			if (attributes != null) {
				return attributes.permissions.intValue();
			}
			else {
				return 0;
			}

		}
		catch (Exception e) {
			return 0;
		}
	}

	/**
	 * normalize / to \ and remove trailing slashes from a path
	 *
	 * @param path
	 * @return normalized path
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private String normalizePath(final String path) throws Exception {

		String normalizedPath = path.replaceAll("\\\\", "/");
		while (normalizedPath.endsWith("\\") || normalizedPath.endsWith("/")) {
			normalizedPath = normalizedPath.substring(0, normalizedPath.length() - 1);
		}

		return normalizedPath;
	}

	/**
	 * @return Returns the sshConnection.
	 */
	protected Connection getSshConnection() {
		return sshConnection;
	}

	protected void setSshConnection(final Connection psshConnection) {
		if (psshConnection == null) {
			isConnected = false;
			if (sftpClient != null) {
				if (this.getFilesToDelete() != null) {
					for (String strFileNameToDelete : vecFilesToDelete) {
						try {
							this.deleteFile(strFileNameToDelete);
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
					vecFilesToDelete = null;
				}
				sftpClient.close();
				sftpClient = null;
				logger.debug(SOSVfs_D_232.params("sftpClient"));
			}

			if (stderrConsumer != null) {
				stderrConsumer.end();
				stderrConsumer = null;
				logger.debug(SOSVfs_D_232.params("stderrConsumer"));
			}

			if (stdoutConsumer != null) {
				stdoutConsumer.end();
				stdoutConsumer = null;
				logger.debug(SOSVfs_D_232.params("stdoutConsumer"));
			}

			if (sshSession != null) {
				sshSession.close();
				sshSession = null;
				logger.debug(SOSVfs_D_232.params("sshSession"));

			}

			if (sshConnection != null) {
				sshConnection.close();
				logger.debug(SOSVfs_D_232.params("sshConnection"));
			}
		}
		sshConnection = psshConnection;
	}

	/**
	 * @return Returns the sshSession.
	 */
	public Session getSshSession() {
		return sshSession;
	}

	/**
	 * @param sshSession The sshSession to set.
	 */
	public void setSshSession(final Session psshSession) {
		sshSession = psshSession;
	}

	/**
	 *
	 * \brief createCommandScript
	 *
	 * \details
	 *
	 * \return File
	 *
	 * @param isWindows
	 * @return
	 * @throws Exception
	 */
	@Override
	public String createScriptFile(final String pstrContent) throws Exception {
		try {
			String commandScript = pstrContent;
//			logger.info("pstrContent = " + pstrContent);
			if (flgIsRemoteOSWindows == false) {
				commandScript = commandScript.replaceAll("(?m)\r", "");
			}
			logger.info(SOSVfs_I_233.params("commandScript"));
			commandScript = objJSJobUtilities.replaceSchedulerVars(flgIsRemoteOSWindows, commandScript);
			String suffix = flgIsRemoteOSWindows ? ".cmd" : ".sh";
			File resultFile = File.createTempFile("sos", suffix);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(resultFile)));
			out.write(commandScript);
			out.flush();
			out.close();
			resultFile.deleteOnExit();
			putFile(resultFile);

			String strFileName2Return = resultFile.getName();
			if (flgIsRemoteOSWindows == false) {
				strFileName2Return = "./" + strFileName2Return;
			}
			this.getFilesToDelete().add(strFileName2Return);
			return strFileName2Return;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public ISOSConnection getConnection() {
		return this;
	}

	@Override
	public ISOSSession getSession() {
		return this;
	}

	@Override
	public ISOSConnection Connect(final ISOSConnectionOptions pobjConnectionOptions) throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Connect";
		objCO = pobjConnectionOptions;
		if (objCO != null) {
			this.Connect();
		}
		return this;
	}

	@Override
	public void CloseConnection() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::CloseConnection";

		this.setSshConnection(null);
	}

	/**
	 *
	 * \brief Authenticate
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pobjAO
	 * @return
	 * @throws Exception
	 */
	@Override
	public ISOSConnection Authenticate(final ISOSAuthenticationOptions pobjAO) throws Exception {

		final String conMethodName = conClassName + "::Authenticate";

		if (pobjAO == null) {
			throw new Exception (SOSVfs_E_234.params(conClassName));
		}
		objAO = pobjAO;

		String strUserID = objAO.getUser().Value();
		String strPW = objAO.getPassword().Value();

		if (objAO.getAuth_method().isPublicKey()) {
			objAO.getAuth_file().CheckMandatory(true);
			File authenticationFile = objAO.getAuth_file().JSFile();
			isAuthenticated = getSshConnection().authenticateWithPublicKey(strUserID, authenticationFile, strPW);
		}
		else
			if (objAO.getAuth_method().isPassword()) {
				isAuthenticated = getSshConnection().authenticateWithPassword(strUserID, strPW);
			}

		if (isAuthenticated == false) {
			throw new Exception(SOSVfs_E_235.params(conMethodName, objAO.toString()));
		}

		logger.info(SOSVfs_D_133.params(strUserID));
		return this;
	}

	@Override
	public ISOSVFSHandler getHandler() {
		return this;
	}

	/**
	 *
	 * \brief remoteIsWindowsShell
	 *
	 * \details
	 *
	 * \return boolean
	 *
	 * @return
	 */
	@Override
	public boolean remoteIsWindowsShell() {
		Session objSSHSession = null;
		flgIsRemoteOSWindows = false;

		try {
			// TODO the testcommand should be defined by an option
			String checkShellCommand = "echo %ComSpec%";
			logger.debug(SOSVfs_D_236.get());
			objSSHSession = this.getSshConnection().openSession();
			logger.debug(SOSVfs_D_0151.params(checkShellCommand));
			objSSHSession.execCommand(checkShellCommand);

			logger.debug(SOSVfs_D_163.params("stdout", checkShellCommand));
			ipsStdOut = new StreamGobbler(objSSHSession.getStdout());
			ipsStdErr = new StreamGobbler(objSSHSession.getStderr());
			BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(ipsStdOut));
			String stdOut = "";
			while (true) {
				String line = stdoutReader.readLine();
				if (line == null)
					break;
				logger.debug(line);
				stdOut += line;
			}
			logger.debug(SOSVfs_D_163.params("stderr", checkShellCommand));
			BufferedReader stderrReader = new BufferedReader(new InputStreamReader(ipsStdErr));
			while (true) {
				String line = stderrReader.readLine();
				if (line == null)
					break;
				logger.debug(line);
			}
			// TODO The expected result-string for testing the os should be defined by an option
			if (stdOut.indexOf("cmd.exe") > -1) {
				logger.debug(SOSVfs_D_237.get());
				flgIsRemoteOSWindows = true;
				return true;
			}
			else {
				logger.debug(SOSVfs_D_238.get());
			}
		}
		catch (Exception e) {
			logger.debug(SOSVfs_D_239.params(e));
		}
		finally {
			if (objSSHSession != null)
				try {
					objSSHSession.close();
				}
				catch (Exception e) {
					logger.debug(SOSVfs_D_240.params(e));
				}
		}
		return false;
	}

	/**
	 *
	 * \brief transferCommandScript
	 *
	 * \details
	 *
	 * \return File
	 *
	 * @param pfleCommandFile
	 * @param isWindows
	 * @return
	 * @throws Exception
	 */
	public void putFile(File pfleCommandFile) throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::putFile";
		String suffix = flgIsRemoteOSWindows ? ".cmd" : ".sh";
		String strFileName = pfleCommandFile.getName();

		try {
			boolean exists = true;
			// check if File already exists
			while (exists) {
				try {
					FtpClient().stat(strFileName);
				}
				catch (SFTPException e) {
					logger.debug(SOSVfs_E_241.params(e.getServerErrorCode()));
					exists = false;
				}
				if (exists) {
					logger.debug(SOSVfs_D_242.get());
					/**
					 * \todo
					 * TODO Tempfile-NamePrefix variable via options
					 */
					File resultFile = File.createTempFile("sos", suffix);
					resultFile.delete();
					pfleCommandFile.renameTo(resultFile);
					pfleCommandFile = resultFile;
				}
			}

			// set execute permissions for owner
			SFTPv3FileHandle fileHandle = this.getFileHandle(strFileName, new Integer(0700));

			FileInputStream fis = null;
			long offset = 0;
			try {
				fis = new FileInputStream(pfleCommandFile);
				// TODO BufferSize as an Option
				byte[] buffer = new byte[1024];
				while (true) {
					int len = fis.read(buffer, 0, buffer.length);
					if (len <= 0)
						break;
					sftpClient.write(fileHandle, offset, buffer, 0, len);
					offset += len;
				}
				fis.close();
				fis = null;
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			finally {
				if (fis != null)
					try {
						fis.close();
						fis = null;
					}
					catch (Exception ex) {
					}
			}

			logger.debug(SOSVfs_D_243.params(sftpClient.canonicalPath(strFileName)));
			sftpClient.closeFile(fileHandle);

			fileHandle = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public SFTPv3FileHandle getFileHandle(final String pstrFileName, final Integer pintPermissions) throws Exception {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::setFilePermissions";

		SFTPv3FileAttributes attr = new SFTPv3FileAttributes();
		attr.permissions = pintPermissions;
		SFTPv3FileHandle fileHandle = this.FtpClient().createFileTruncate(pstrFileName, attr);
		return fileHandle;
	} // private void setFilePermissions

	public void setFilePermissions(final String pstrFileName, final Integer pintPermissions) throws Exception {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::setFilePermissions";

		SFTPv3FileAttributes attr = new SFTPv3FileAttributes();
		attr.permissions = pintPermissions;
		@SuppressWarnings("unused")
		SFTPv3FileHandle fileHandle = this.FtpClient().createFileTruncate(pstrFileName, attr);
	} // private void setFilePermissions

//	@Override
//	public ISOSVirtualFile getFile(String pstrFileName) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

	/**
	 *
	 * \brief deleteCommandScript
	 *
	 * \details
	 *
	 * \return void
	 *
	 * @param pstrCommandFile
	 * @throws Exception
	 */
	public void deleteFile(final String pstrCommandFile) throws Exception {
		try {
			if (isNotEmpty(pstrCommandFile)) {
				this.FtpClient().rm(pstrCommandFile);
				logger.debug(SOSVfs_I_0113.params(pstrCommandFile));
			}
		}
		catch (Exception e) {
			logger.error(SOSVfs_E_244.params(e));
		}
	}

	private SFTPv3Client FtpClient() throws Exception {
		if (sftpClient == null) {
			sftpClient = new SFTPv3Client(this.getSshConnection());
		}
		return sftpClient;
	}

	@Override
	public void CloseSession() throws Exception {

	}

	/**
	 *
	 * \brief OpenSession
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pobjShellOptions
	 * @return
	 * @throws Exception
	 */
	@Override
	public ISOSSession OpenSession(final ISOSShellOptions pobjShellOptions) {

		objSO = pobjShellOptions;
		if (objSO == null) {
			throw new RuntimeException(SOSVfs_E_245.get());
		}

		try {
			this.setSshSession(this.getSshConnection().openSession());
			if (objSO.getSimulate_shell().value() == true) {
				long loginTimeout = objSO.getSimulate_shell_login_timeout().value();
				String strPromptTrigger = objSO.getSimulate_shell_prompt_trigger().Value();

				logger.debug(SOSVfs_D_246.params("PTY"));
				this.getSshSession().requestDumbPTY();
				logger.debug(SOSVfs_D_247.params("shell"));
				this.getSshSession().startShell();
				ipsStdOut = getSshSession().getStdout();
				ipsStdErr = getSshSession().getStderr();
				stdoutConsumer = new RemoteConsumer(getStdOutBuffer(), true, ipsStdOut);
				stderrConsumer = new RemoteConsumer(getStdErrBuffer(), false, ipsStdErr);
				stdoutConsumer.start();
				stderrConsumer.start();
				stdin = getSshSession().getStdin();
				stdinWriter = new OutputStreamWriter(stdin);
				logger.debug(SOSVfs_D_248.get());
				boolean loggedIn = false;
				while (loggedIn == false) {
					if (lngLastTime > 0) {
						loggedIn = Check4TimeOutOrPrompt(loginTimeout, strPromptTrigger);
					}
				}
			}
			else {
				if (objSO.getIgnore_hangup_signal().value() == false) {
					sshSession.requestPTY("vt100");
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		return this;
	}

	/**
	 *
	 * \brief Check4TimeOutOrPrompt
	 *
	 * \details
	 *
	 * \return boolean
	 *
	 * @param plngTimeOut
	 * @param pstrPromptTrigger
	 * @return
	 */
	private boolean Check4TimeOutOrPrompt(final long plngTimeOut, final String pstrPromptTrigger) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Check4TimeOutOrPrompt";

		long currentTimeMillis = System.currentTimeMillis();
		if (plngTimeOut > 0 && lngLastTime + plngTimeOut < currentTimeMillis) {// kommt nichts mehr
			return true;
		}
		if (pstrPromptTrigger.length() > 0 && strCurrentLine.indexOf(pstrPromptTrigger) != -1) {
			logger.debug(SOSVfs_D_249.params(pstrPromptTrigger));
			strCurrentLine = "";
			return true;
		}

		return false;
	} // private boolean Check4TimeOutOrPrompt

	/**
	 *
	 * \brief ExecuteCommand
	 *
	 * \details
	 * A single command is executed by this method.
	 * a command can be executed in a "simulated"-shell or as a single ssh-session.
	 *
	 * if it is executed as a single ssh-session the method must wait until the
	 * ssh-server was sent an EOF signal, because to get the exit-code and exit-status of
	 * the command is not poossible.
	 *
	 * @param pstrCmd
	 * @throws Exception
	 */
	@Override
	public void ExecuteCommand(final String pstrCmd) throws Exception {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::ExecuteCommand";

		intExitStatus = null;
		strExitSignal = null;
		String strCmd = pstrCmd;

		if (objSO.getSimulate_shell().value() == true) {
			long lngInactivityTimeout = objSO.getSimulate_shell_inactivity_timeout().value();
			String strPromptTrigger = objSO.getSimulate_shell_prompt_trigger().Value();

			stdinWriter.write(strCmd + "\n");
			stdinWriter.flush();
			boolean prompt = false;
			while (prompt == false) {
				prompt = Check4TimeOutOrPrompt(lngInactivityTimeout, strPromptTrigger);
			}
			strCurrentLine = "";
			logger.debug(SOSVfs_D_163.params("stdout", strCmd));
			logger.debug(getStdOutBuffer().toString());
			strbStdoutOutput = new StringBuffer();
		}
		else {
			// tempor�r eingebaut um zu pr�fen ob das so mit VMS geht. ur 21.6.2013		
			if (flgIsRemoteOSWindows == false && !strCmd.startsWith("@") && !strCmd.startsWith("run ")) {
				strCmd = "echo $$ && " + strCmd;
			}

			this.getSshSession().execCommand(strCmd);

			logger.info(SOSVfs_D_163.params("stdout", strCmd));

			ipsStdOut = new StreamGobbler(this.getSshSession().getStdout());
			ipsStdErr = new StreamGobbler(this.getSshSession().getStderr());
			BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(ipsStdOut));
			strbStdoutOutput = new StringBuffer();
			while (true) {
				String line = stdoutReader.readLine();
				if (line == null)
					break;
				logger.info(line);
				getStdOutBuffer().append(line + "\n");
			}

			logger.info(SOSVfs_D_163.params("stderr", strCmd));
			BufferedReader stderrReader = new BufferedReader(new InputStreamReader(ipsStdErr));
			strbStderrOutput = new StringBuffer();
			while (true) {
				String line = stderrReader.readLine();
				if (line == null)
					break;
				logger.info(line);
				strbStderrOutput.append(line + "\n");
			}
			/**
			 * give the command (e.g. session) some time to end.
			 * otherwise it is not possible to get valid exit-infos.
			 */
			// TODO waitForCondition as an Option
			@SuppressWarnings("unused")
			int res = getSshSession().waitForCondition(ChannelCondition.EOF, 30 * 1000);

		} // 		if (objSO.getSimulate_shell().value() == true)

		String strWhatSignal = "";
		try {
			strWhatSignal = "exit status";
			intExitStatus = this.getSshSession().getExitStatus();
			strWhatSignal = "exit signal";
			strExitSignal = this.getSshSession().getExitSignal();
		}
		catch (Exception e) {
			logger.info(SOSVfs_I_250.params(strWhatSignal));
		}
	}

	@Override
	public Integer getExitCode() {
		return intExitStatus;
	}

	@Override
	public String getExitSignal() {
		return strExitSignal;
	}

//	@Override
	public StringBuffer getStdErrBuffer() throws Exception {
		if (strbStderrOutput == null) {
			strbStderrOutput = new StringBuffer();
		}
		return strbStderrOutput;
	}

//	@Override
	public StringBuffer getStdOutBuffer() throws Exception {
		if (strbStdoutOutput == null) {
			strbStdoutOutput = new StringBuffer();
		}
		return strbStdoutOutput;
	}

	/**
	 * This thread consumes output from the remote server puts it into fields of
	 * the main class
	 */
	class RemoteConsumer extends Thread {
		private final StringBuffer	sbuf;
		private boolean			writeCurrentline	= false;
		private final InputStream		stream;
		boolean					end					= false;

		RemoteConsumer(StringBuffer buffer, final boolean writeCurr, final InputStream str) {
			if (buffer == null) {
				buffer = new StringBuffer();
			}
			sbuf = buffer;
			writeCurrentline = true;
			stream = str;
		}

		/**
		 *
		 * \brief addText
		 *
		 * \details
		 *
		 * \return void
		 *
		 * @param data
		 * @param len
		 */
		private void addText(final byte[] data, final int len) {
			lngLastTime = System.currentTimeMillis();
			String outstring = new String(data).substring(0, len);
			sbuf.append(outstring);
			if (writeCurrentline) {
				int newlineIndex = outstring.indexOf("\n");
				if (newlineIndex > -1) {
					String stringAfterNewline = outstring.substring(newlineIndex);
					strCurrentLine = stringAfterNewline;
				}
				else {
					strCurrentLine += outstring;
				}
			}
		}

		/**
		 *
		 * \brief run
		 *
		 * \details
		 *
		 * \return
		 *
		 */
		@Override
		public void run() {
			byte[] buff = new byte[64];

			try {
				while (!end) {
					buff = new byte[8];
					int len = stream.read(buff);
					if (len == -1)
						return;
					addText(buff, len);
				}
			}
			catch (Exception e) {
			}
		}

		/**
		 *
		 * \brief end
		 *
		 * \details
		 *
		 * \return void
		 *
		 */
		public synchronized void end() {
			end = true;
		}
	} // RemoteConsumer

//	@Override
//	public boolean FileExists(String filename) throws Exception {
//
//		return this.sshFileExists(this.FtpClient(), filename);
//	}
//
//	@Override
//	public Integer getFilePermissions(final String pstrFileName) throws Exception {
//		return this.sshFilePermissions(this.FtpClient(), pstrFileName);
//	}
//
//	@Override
//	public long getFileSize(final String pstrFileName) throws Exception {
//
//		return this.getFileSize(this.FtpClient(), pstrFileName);
//	}
//
//	@Override
//	public boolean isDirectory(final String pstrFileName) throws Exception {
//
//		return this.isDirectory(this.FtpClient(), pstrFileName);
//	}
//
//	@Override
//	public void putFile(String pstrFileName) throws Exception {
//		this.putFile(new File(pstrFileName));
//	}

	@Override
	public SOSFileList dir(final SOSFolderName pobjFolderName) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public SOSFileList dir() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public ISOSVirtualFolder mkdir(final SOSFolderName pobjFolderName) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean rmdir(final SOSFolderName pobjFolderName) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String myReplaceAll(final String source, final String what, final String replacement) {
		return source;
	}

	@Override
	public String replaceSchedulerVars(final boolean isWindows, final String pstrString2Modify) {
		logger.debug(SOSVfs_D_251.get());
		return pstrString2Modify;
	}

	@Override
	public void setJSParam(final String pstrKey, final String pstrValue) {

	}

//	@Override
//	public void setParameters(Variable_set pVariableSet) {
//
//	}

	@Override
	public void setJSJobUtilites (final JSJobUtilities pobjJSJobUtilities) {
		if (pobjJSJobUtilities == null) {
			objJSJobUtilities = this;
		}
		else {
			objJSJobUtilities = pobjJSJobUtilities;
		}
		logger.debug(SOSVfs_D_252.params(objJSJobUtilities.getClass().getName()));

	}

	@Override
	public void setJSParam(final String pstrKey, final StringBuffer pstrValue) {
		setJSParam(pstrKey, pstrValue.toString());

	}

	@Override
	public StringBuffer getStdErr() throws Exception {
		return getStdErrBuffer();
	}

	@Override
	public StringBuffer getStdOut() throws Exception {
		return getStdOutBuffer();
	}

//	@Override
//	public ISOSVirtualFile TransferMode(SOSOptionTransferMode pobjFileTransferMode) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public SOSFileList dir(final String pathname, final int flag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISOSConnection Connect(final SOSConnection2OptionsAlternate pobjConnectionOptions) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCurrentNodeName() {
		return "";
	}

	@Override
	public void setStateText(final String pstrStateText) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doPostLoginOperations() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCC(final int pintCC) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ISOSConnection Connect(final SOSConnection2OptionsSuperClass pobjConnectionOptions) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}