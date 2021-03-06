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
package com.sos.VirtualFileSystem.SFTP;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.JSHelper.Options.SOSOptionInFileName;
import com.sos.VirtualFileSystem.Interfaces.ISOSAuthenticationOptions;
import com.sos.VirtualFileSystem.Interfaces.ISOSConnection;
import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFile;
import com.sos.VirtualFileSystem.Options.SOSConnection2OptionsAlternate;
import com.sos.VirtualFileSystem.Options.SOSConnection2OptionsSuperClass;
import com.sos.VirtualFileSystem.common.SOSVfsTransferBaseClass;
import com.sos.i18n.annotation.I18NResourceBundle;

/**
 * @ressources jsch-0.1.48.jar
 *
 * @author Robert Ehrlich
 *
 */
@I18NResourceBundle(baseName = "SOSVirtualFileSystem", defaultLocale = "en")
public class SOSVfsSFtpJCraft extends SOSVfsTransferBaseClass {

	@SuppressWarnings("unused")
	private final Logger	logger			= Logger.getLogger(SOSVfsSFtpJCraft.class);

	/** ssh connection object */
	private Channel			sshConnection	= null;

	/** ssh session object */
	private Session			sshSession		= null;

	/** SFTP Client **/
	private ChannelSftp		sftpClient		= null;

	private JSch			secureChannel	= null;

	/**
	 *
	 * \brief SOSVfsSFtpJCraft
	 *
	 * \details
	 *
	 */
	public SOSVfsSFtpJCraft() {
		super();

		secureChannel = new JSch();
	}

	@Override
	public void StrictHostKeyChecking(final String pstrStrictHostKeyCheckingValue) {
		JSch.setConfig("StrictHostKeyChecking", pstrStrictHostKeyCheckingValue);
		//	'	sshConnection.StrictHostKeyChecking(pstrStrictHostKeyCheckingValue);
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
	 */
	@Override
	public ISOSConnection Connect() {
		@SuppressWarnings("unused")
		SOSConnection2OptionsAlternate pConnection2OptionsAlternate = null;
		this.Connect(pConnection2OptionsAlternate);
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
	 * @param pobjConnectionOptions
	 * @return
	 */
	@Override
	public ISOSConnection Connect(final SOSConnection2OptionsAlternate pConnection2OptionsAlternate) {
		connection2OptionsAlternate = pConnection2OptionsAlternate;

		if (connection2OptionsAlternate == null) {
			RaiseException(SOSVfs_E_190.params("connection2OptionsAlternate"));
		}

		this.StrictHostKeyChecking(connection2OptionsAlternate.StrictHostKeyChecking.Value());
		this.connect(connection2OptionsAlternate.host.Value(), connection2OptionsAlternate.port.value());
		return this;
	}

	/**
	 *
	 * \brief Authenticate
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param authenticationOptions
	 * @return
	 */
	@Override
	public ISOSConnection Authenticate(final ISOSAuthenticationOptions pAuthenticationOptions) {
		authenticationOptions = pAuthenticationOptions;

		try {
			this.doAuthenticate(authenticationOptions);
		}
		catch (Exception ex) {
			Exception exx = ex;

			this.disconnect();

			if (connection2OptionsAlternate != null) {
				SOSConnection2OptionsSuperClass optionsAlternatives = connection2OptionsAlternate.Alternatives();

				if (!optionsAlternatives.host.IsEmpty() && !optionsAlternatives.user.IsEmpty()) {
					logINFO(SOSVfs_I_170.params(connection2OptionsAlternate.Alternatives().host.Value()));
					try {

						host = optionsAlternatives.host.Value();
						port = optionsAlternatives.port.value();

						this.doAuthenticate(optionsAlternatives);
						exx = null;
					}
					catch (Exception e) {
						exx = e;
					}
				}
			}

			if (exx != null) {
				RaiseException(exx, SOSVfs_E_168.get());
			}
		}

		return this;
	}

	/**
	 *
	 * \brief login
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param strUserName
	 * @param strPassword
	 */
	@Override
	public void login(final String pUserName, final String pPassword) {

		try {

			userName = pUserName;

			logger.debug(SOSVfs_D_132.params(userName));

			this.createSession(userName, host, port);
			sshSession.setPassword(pPassword);
			sshSession.connect();

			this.createSftpClient();

			reply = "OK";
			logger.debug(SOSVfs_D_133.params(userName));
			this.LogReply();
		}
		catch (Exception e) {
			RaiseException(e, SOSVfs_E_134.params("authentication"));
		}

	} // private boolean login

	/**
	 *
	 * \brief disconnect
	 *
	 * \details
	 *
	 * \return
	 *
	 */
	@Override
	public void disconnect() {
		reply = "disconnect OK";

		if (sftpClient != null) {
			try {
				sftpClient.exit();
				if (sftpClient.isConnected()) {
					sftpClient.disconnect();
				}
				sftpClient = null;
			}
			catch (Exception ex) {
				reply = "disconnect: " + ex;
			}
		}

		if (sshConnection != null) {
			try {
				sshConnection.disconnect();
				sshConnection = null;
			}
			catch (Exception ex) {
				reply = "disconnect: " + ex;
			}
		}

		this.logINFO(reply);
	}

	@Override
	public boolean isConnected() {
		return sftpClient != null && sftpClient.isConnected();
	}

	/**
	 * Creates a new subdirectory on the FTP server in the current directory .
	 * @param pstrPathName The pathname of the directory to create.
	 * @exception JobSchedulerException
	 */
	@Override
	public void mkdir(final String path) {
		try {
			reply = "";
			String strPath = "";
			String[] pathArray = path.split("/");
			for (String strSubFolder : pathArray) {
				if (strSubFolder.trim().length() > 0) {
					strPath += strSubFolder + "/";
					logger.debug(HostID(SOSVfs_D_179.params("mkdir", strPath)));

					this.getClient().mkdir(strPath);
					this.getClient().chmod(484, strPath);
					reply = "mkdir OK";
					logger.debug(HostID(SOSVfs_D_181.params("mkdir", strPath, getReplyString())));
				}
			}
			// DoCD(strCurrentDir);
			logINFO(HostID(SOSVfs_D_181.params("mkdir", path, getReplyString())));
		}
		catch (Exception e) {
			reply = e.toString();
			RaiseException(e, SOSVfs_E_134.params("[mkdir]"));
		}
	}

	/**
	 * Removes a directory on the FTP server (if empty).
	 * @param path The pathname of the directory to remove.
	 * @exception JobSchedulerException
	 */
	@Override
	public void rmdir(final String path) {
		try {
			reply = "rmdir OK";
			String[] pathArray = path.split("/");
			for (int i = pathArray.length; i > 0; i--) {
				String strT = "";
				for (int j = 0; j < i; j++) {
					strT += pathArray[j] + "/";
				}
				logger.debug(HostID(SOSVfs_D_179.params("rmdir", strT)));
				this.getClient().rmdir(strT);

				reply = "rmdir OK";
				logger.debug(HostID(SOSVfs_D_181.params("rmdir", strT, getReplyString())));
			}
			logINFO(HostID(SOSVfs_D_181.params("rmdir", path, getReplyString())));
		}
		catch (Exception e) {
			reply = e.toString();
			RaiseException(e, SOSVfs_E_134.params("[rmdir]"));
		}
	}

	/**
	 * Check and return "true" if file is a directory
	 *
	 * @param filename
	 * @return true, if filename is a directory
	 */
	@Override
	public boolean isDirectory(final String filename) {

		/**
		 * Problem: the concept of links is not considered in the Concept of SOSVfs
		 * Therefore: wie return here "true" if the filename is a link
		 */
		boolean flgR = false;
		try {
			SftpATTRS attributes = this.getClient().stat(filename);
			if (attributes != null) {
				flgR = attributes.isDir();
				if (flgR == false) {
					flgR = attributes.isLink();
				}
			}
		}
		catch (Exception e) {
		}

		return flgR;
	}

	/**
	 *
	 * \brief listNames
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param path
	 * @return
	 * @throws IOException
	 */
	@Override
	public String[] listNames(String path) throws IOException {
		path = resolvePathname(path);
		try {
			if (path.length() == 0) {
				path = ".";
			}

			if (!this.fileExists(path)) {
				return null;
			}

			if (!isDirectory(path)) {
				reply = "ls OK";
				return new String[] { path };
			}

			@SuppressWarnings("unchecked")
			Vector<LsEntry> lsResult = this.getClient().ls(path);
			String[] result = new String[lsResult.size()];
			String sep = path.endsWith("/") ? "" : "/";

			for (int i = 0, j = 0; i < lsResult.size(); i++) {
				LsEntry entry = lsResult.get(i);
				String strFileName = path + sep + entry.getFilename();
				//				if (isDirectory(strFileName) == false) {
				result[j++] = strFileName;
				//				}
			}

			reply = "ls OK";
			return result;
		}
		catch (Exception e) {
			reply = e.toString();
			return null;
		}
	}

	/**
	 * return the size of remote-file on the remote machine on success, otherwise -1
	 * @param remoteFile the file on remote machine
	 * @return the size of remote-file on remote machine
	 */
	@Override
	public long size(String remoteFile) throws Exception {
		remoteFile = this.resolvePathname(remoteFile);
		long size = -1;

		SftpATTRS objAttr;
		try {
			objAttr = this.getClient().stat(remoteFile);
			if (objAttr != null) {
				size = objAttr.getSize();
			}
		}
		catch (SftpException e) {
			// e.printStackTrace();
		}

		return size;
	}

	/**
	 *
	 * \brief getFile
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param remoteFile
	 * @param localFile
	 * @param append
	 * @return
	 */
	@Override
	public long getFile(final String remoteFile, final String localFile, final boolean append) {
		String sourceLocation = this.resolvePathname(remoteFile);
		File transferFile = null;
		long remoteFileSize = -1;
		FileOutputStream fos = null;

		try {
			remoteFileSize = this.size(remoteFile);

			fos = new FileOutputStream(localFile, append);

			this.getClient().get(sourceLocation, fos);

			fos.flush();
			fos.close();
			fos = null;

			transferFile = new File(localFile);

			if (!append) {
				if (remoteFileSize > 0 && remoteFileSize != transferFile.length()) {
					throw new JobSchedulerException(SOSVfs_E_162.params(remoteFileSize, transferFile.length()));
				}
			}

			remoteFileSize = transferFile.length();
			reply = "get OK";
			logINFO(HostID(SOSVfs_I_182.params("getFile", sourceLocation, localFile, getReplyString())));
		}
		catch (Exception ex) {
			reply = ex.toString();
			RaiseException(ex, SOSVfs_E_184.params("getFile", sourceLocation, localFile));
		}
		finally {
			if (fos != null) {
				try {
					fos.close();
				}
				catch (Exception ex) {
				}
			}
		}

		return remoteFileSize;
	}

	/**
	 * Stores a file on the server using the given name.
	 *
	 * @param localFile The name of the local file.
	 * @param remoteFile The name of the remote file.
	 * @return file size
	 *
	 * @exception Exception
	 * @see #put( String, String )
	 */
	@Override
	// ISOSVfsFileTransfer
	public long putFile(final String localFile, final String remoteFile) {
		long size = 0;
		try {
			// mit Progress Monitor
			// SftpProgressMonitor monitor = new ProgressMonitor();
			// this.getClient().put(localFile, this.resolvePathname(remoteFile),monitor,ChannelSftp.OVERWRITE);

			this.getClient().put(localFile, this.resolvePathname(remoteFile), ChannelSftp.OVERWRITE);

			reply = "put OK";
			logINFO(HostID(SOSVfs_I_183.params("putFile", localFile, remoteFile, getReplyString())));
			return this.size(remoteFile);
		}
		catch (Exception e) {
			reply = e.toString();
			RaiseException(e, SOSVfs_E_185.params("putFile()", localFile, remoteFile));
		}

		return size;
	}

	/**
	 * Deletes a file on the FTP server.
	 * @param The path of the file to be deleted.
	 * @return True if successfully completed, false if not.
	 * @throws RunTime error occurs while either sending a
	 * command to the server or receiving a reply from the server.
	 */
	@Override
	public void delete(final String path) {
		try {
			if (this.isDirectory(path)) {
				throw new JobSchedulerException(SOSVfs_E_186.params(path));
			}

			this.getClient().rm(path);
		}
		catch (Exception ex) {
			reply = ex.toString();
			RaiseException(ex, SOSVfs_E_187.params("delete", path));
		}

		reply = "rm OK";
		logINFO(HostID(SOSVfs_D_181.params("delete", path, getReplyString())));
	}

	/**
	 *
	 * \brief rename
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param from
	 * @param to
	 */
	@Override
	public void rename(String from, String to) {
		from = this.resolvePathname(from);
		to = this.resolvePathname(to);
		try {
			this.getClient().rename(from, to);
		}
		catch (Exception e) {
			reply = e.toString();
			throw new JobSchedulerException(SOSVfs_E_188.params("rename", from, to), e);
		}

		reply = "mv OK";
		logger.info(HostID(SOSVfs_I_189.params(from, to, getReplyString())));
	}

	/**
	 *
	 * \brief ExecuteCommand
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param strCmd
	 */
	@Override
	public void ExecuteCommand(final String cmd) {

		final String strEndOfLine = System.getProperty("line.separator");

		ChannelExec channelExec = null;
		Integer exitCode = null;
		InputStream out = null;
		InputStream err = null;
		BufferedReader errReader = null;

		try {
			if (sshSession == null) {
				// sshSession = sshConnection.openSession();
				// TODO create Session ??
				throw new JobSchedulerException(SOSVfs_E_190.params("sshSession"));
			}

			channelExec = (ChannelExec) sshSession.openChannel("exec");
			channelExec.setCommand(cmd);
			channelExec.setInputStream(null);
			channelExec.setErrStream(null);

			out = channelExec.getInputStream();
			err = channelExec.getErrStream();

			channelExec.connect();

			logger.debug(SOSVfs_D_163.params("stdout", cmd));
			StringBuffer outContent = new StringBuffer();
			byte[] tmp = new byte[1024];

			while (true) {
				while (out.available() > 0) {
					int i = out.read(tmp, 0, 1024);
					if (i < 0) {
						break;
					}

					// output line
					outContent.append(new String(tmp, 0, i) + strEndOfLine);
				}
				if (channelExec.isClosed()) {
					exitCode = channelExec.getExitStatus();
					break;
				}
				try {
					Thread.sleep(1000);
				}
				catch (Exception ee) {
				}
			}
			logger.debug(outContent);

			logger.debug(SOSVfs_D_163.params("stderr", cmd));
			errReader = new BufferedReader(new InputStreamReader(err));
			StringBuffer errContent = new StringBuffer();
			while (true) {
				String line = errReader.readLine();
				if (line == null) {
					break;
				}
				errContent.append(line + strEndOfLine);
			}
			logger.debug(errContent);

			if (exitCode != null) {
				if (!exitCode.equals(new Integer(0))) {
					throw new JobSchedulerException(SOSVfs_E_164.params(exitCode));
				}
			}

			reply = "OK";
			logINFO(HostID(SOSVfs_I_192.params(getReplyString())));
		}
		catch (Exception ex) {
			reply = ex.toString();
			RaiseException(ex, SOSVfs_E_134.params("ExecuteCommand"));
		}
		finally {
			if (out != null) {
				try {
					out.close();
				}
				catch (Exception e) {
				}
			}
			if (errReader != null) {
				try {
					errReader.close();
				}
				catch (Exception e) {
				}
			}
			if (err != null) {
				try {
					err.close();
				}
				catch (Exception e) {
				}
			}
			if (channelExec != null) {
				try {
					channelExec.disconnect();
				}
				catch (Exception e) {
				}
			}
		}
	}

	/**
	 *
	 * \brief getInputStream
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param fileName
	 * @return
	 */
	@Override
	public InputStream getInputStream(final String fileName) {
		try {
			return this.getClient().get(fileName);
		}
		catch (Exception ex) {
			RaiseException(ex, SOSVfs_E_193.params("getInputStream()", fileName));
			return null;
		}
	}

	/**
	 *
	 * \brief getOutputStream
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param fileName
	 * @return
	 */
	@Override
	public OutputStream getOutputStream(final String fileName) {
		try {
			boolean flgModeAppend = false;
			boolean flgModeRestart = false;

			int intTransferMode = ChannelSftp.OVERWRITE;
			if (flgModeAppend) {
				intTransferMode = ChannelSftp.APPEND;
			}
			else if (flgModeRestart ){
				intTransferMode = ChannelSftp.RESUME;
			}

			return this.getClient().put(fileName, intTransferMode);
		}
		catch (Exception ex) {
			RaiseException(ex, SOSVfs_E_193.params("getOutputStream()", fileName));
			return null;
		}
	}

	@Override
	public boolean changeWorkingDirectory(String pathname) {
		try {
			pathname = this.resolvePathname(pathname);

			if (!this.fileExists(pathname)) {
				reply =String.format("Filepath '%1$s' does not exist.", pathname);
				return false;
			}
			if (!this.isDirectory(pathname)) {
				reply =String.format("Filepath '%1$s' is not a directory.", pathname);
				return false;
			}

			this.getClient().cd(pathname);
			reply = "cwd OK";
		}
		catch (Exception ex) {
			throw new JobSchedulerException( SOSVfs_E_193.params("cwd", pathname), ex);
		}
		finally {
			String strM = SOSVfs_D_194.params(pathname, getReplyString());
			logger.debug(strM);
		}
		return true;
	}

	@Override
	public ISOSVirtualFile getFileHandle(String fileName) {
		fileName = adjustFileSeparator(fileName);
		ISOSVirtualFile file = new SOSVfsSFtpFileJCraft(fileName);
		file.setHandler(this);

		logger.debug(SOSVfs_D_196.params(fileName));

		return file;
	}

	/**
	 *
	 * \brief getModificationTime
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param path
	 * @return
	 */
	@Override
	public String getModificationTime(final String path) {
		String dateTime = null;

		try {
			SftpATTRS objAttr = this.getClient().stat(path);
			if (objAttr != null) {
				int mt = objAttr.getMTime();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dateTime = df.format(new Date(mt));
			}
		}
		catch (SftpException e) {
			// e.printStackTrace();
		}
		return dateTime;
	}

	/**
	 *
	 * \brief fileExists
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param filename
	 * @return
	 */
	@Override
	protected boolean fileExists(final String filename) {

		try {
			SftpATTRS attributes = this.getClient().stat(filename);

			if (attributes != null) {
				return !attributes.isLink() || attributes.isDir();
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			return false;
		}
	}

	@Override
	protected String getCurrentPath() {
		String path = null;

		try {
			path = this.getClient().pwd();
			logger.debug(HostID(SOSVfs_D_195.params(path)));
			LogReply();
		}
		catch (Exception e) {
			RaiseException(e, SOSVfs_E_134.params("getCurrentPath"));
		}

		return path;
	}

	/**
	 *
	 * \brief doAuthenticate
	 *
	 * \details
	 *
	 * \return ISOSConnection
	 *
	 * @param authenticationOptions
	 * @return
	 * @throws Exception
	 */
	private ISOSConnection doAuthenticate(final ISOSAuthenticationOptions pAuthenticationOptions) throws Exception {

		authenticationOptions = pAuthenticationOptions;

		userName = authenticationOptions.getUser().Value();
		String password = authenticationOptions.getPassword().Value();
		logger.debug(SOSVfs_D_132.params(userName));

		this.createSession(userName, host, port);

		if (authenticationOptions.getAuth_method().isPublicKey()) {
			logger.debug(SOSVfs_D_165.params("userid", "publickey"));

			SOSOptionInFileName authenticationFile = authenticationOptions.getAuth_file();
			authenticationFile.CheckMandatory(true);
			if (authenticationFile.IsNotEmpty()) {
				secureChannel.addIdentity(authenticationFile.JSFile().getPath());
			}
		}
		else {
			if (authenticationOptions.getAuth_method().isPassword()) {
				logger.debug(SOSVfs_D_165.params("userid", "password"));
				sshSession.setPassword(password);
			}
			else {
				throw new JobSchedulerException(SOSVfs_E_166.params(authenticationOptions.getAuth_method().Value()));
			}
		}

		try {
			sshSession.connect();
			this.createSftpClient();
		}
		catch (Exception ex) {
			throw new JobSchedulerException(SOSVfs_E_167.params(authenticationOptions.getAuth_method().Value(), authenticationOptions.getAuth_file().Value()));
		}

		reply = "OK";
		logger.debug(SOSVfs_D_133.params(userName));
		this.LogReply();

		return this;
	}

	public ChannelSftp getClient() {
		if (sftpClient == null) {
			try {
				if (sshConnection == null) {
					RaiseException(SOSVfs_E_190.params("sshConnection Object"));
				}

				sftpClient = (ChannelSftp) sshConnection;
			}
			catch (Exception e) {
				RaiseException(e, SOSVfs_E_196.get());
			}
		}
		return sftpClient;
	}

	/**
	 *
	 * \brief connect
	 *
	 * \details
	 *
	 * \return void
	 *
	 * @param phost
	 * @param pport
	 */
	private void connect(final String phost, final int pport) {

		host = phost;
		port = pport;

		logger.debug(SOSVfs_D_0101.params(host, port));

		if (this.isConnected() == false) {
			this.LogReply();
		}
		else {
			logWARN(SOSVfs_D_0103.params(host, port));
		}
	}

	/**
	 *
	 * \brief createSession
	 *
	 * \details
	 *
	 * \return void
	 *
	 * @param puser
	 * @param phost
	 * @param pport
	 * @throws Exception
	 */
	private void createSession(final String puser, final String phost, final int pport) throws Exception {

		if (secureChannel == null) {
			throw new JobSchedulerException(SOSVfs_E_190.params("secureChannel"));
		}
		sshSession = secureChannel.getSession(puser, phost, pport);

		java.util.Properties config = new java.util.Properties();
		// TODO "StrictHostKeyChecking" �ber eine option steuern
		config.put("StrictHostKeyChecking", "no");
		sshSession.setConfig(config);
	}

	/**
	 *
	 * \brief createObjFTPClient
	 *
	 * \details
	 *
	 * \return void
	 *
	 * @throws Exception
	 */
	private void createSftpClient() throws Exception {

		if (sshSession == null) {
			throw new JobSchedulerException(SOSVfs_E_190.params("sshSession"));
		}

		sshConnection = sshSession.openChannel("sftp");
		sshConnection.connect();

		sftpClient = (ChannelSftp) sshConnection;
	}

	/**
	 *
	 * @author re
	 *
	 * Progress monitor (sftp operations GET & PUT)
	 *
	 */
	private static class ProgressMonitor implements SftpProgressMonitor {
		long	totalSize			= 0;
		long	transmittedSize		= 0;
		long	transmittedPercent	= -1;

		@Override
		public void init(final int operation, final String src, final String dest, final long size) {
			totalSize = size;
			transmittedSize = 0;
			transmittedPercent = -1;
		}

		@Override
		public boolean count(final long transmitted) {

			System.out.println("Completed " + transmittedSize + "(" + transmittedPercent + "%) out of " + totalSize + ".");

			transmittedSize += transmitted;

			long percent = transmittedSize * 100 / totalSize;

			if (transmittedPercent >= percent) {
				return true;
			}

			transmittedPercent = percent;

			return true;
		}

		@Override
		public void end() {

			System.out.println("END " + transmittedSize + "(" + transmittedPercent + "%) out of " + totalSize + ".");
		}
	}

	@Override
	public void close() {
		try {
//			sshSession.disconnect();  // see SOSFTP-122
//			sshSession = null;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public OutputStream getOutputStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getInputStream() {
		return null;
	}
}
