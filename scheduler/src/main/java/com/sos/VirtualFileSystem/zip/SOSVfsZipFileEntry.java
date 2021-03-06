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
package com.sos.VirtualFileSystem.zip;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.zip.ZipEntry;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Basics.JSToolBox;
import com.sos.JSHelper.DataElements.JSDataElementDateTime;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.VirtualFileSystem.Interfaces.ISOSVfsFileTransfer;
import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFile;
import com.sos.VirtualFileSystem.common.SOSVfsCommonFile;
import com.sos.i18n.annotation.I18NResourceBundle;

/**
* \class SOSVfsZipFileEntry 
* 
* \brief SOSVfsZipFileEntry - 
* 
* \details
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
* @version $Id: SOSVfsZipFileEntry.java 18488 2012-11-27 21:02:06Z kb $14.09.2010
* \see reference
*
 */
@I18NResourceBundle(baseName = "SOSVirtualFileSystem", defaultLocale = "en")
public class SOSVfsZipFileEntry extends SOSVfsCommonFile {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6693187904489763940L;
	private final String		conClassName		= "SOSVfsZipFileEntry";
	private final Logger				logger				= Logger.getLogger(SOSVfsZipFileEntry.class);
	private ZipEntry			objZipEntry			= null;
	private String				strFileName			= "";

	public SOSVfsZipFileEntry(final String pstrFileName) {
		super();
		strFileName = pstrFileName;
	}

	/**
	 * \brief FileExists
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean FileExists() throws Exception {
		boolean flgResult = true;
		return flgResult;
	}

	/**
	 * \brief delete
	 * 
	 * \details
	 *
	 * \return 
	 *
	 */
	@Override
	public boolean delete() {
		notImplemented();
		// super.delete();
		// logger.debug(String.format("File %1$s deleted", strFileName));
		return true;
	}

	/**
	 * \brief deleteFile
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @throws Exception
	 */
	@Override
	public void deleteFile() {
		this.delete();
	}

	/**
	 * \brief getFileAppendStream
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @return
	 */
	@Override
	public OutputStream getFileAppendStream() {
		final String conMethodName = conClassName + "::getFileAppendStream";
		notImplemented();
		OutputStream objO = null;
		// try {
		// objO = new FileOutputStream(new File(strFileName), true);
		// }
		// catch (FileNotFoundException e) {
		// e.printStackTrace();
		// String strT = String.format("%1$s failed", conMethodName);
		// logger.error(strT, e);
		// throw new JobSchedulerException(strT, e);
		// }
		return objO;
	}

	/**
	 * \brief getFileInputStream
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @return
	 */
	@Override
	public InputStream getFileInputStream() {
		final String conMethodName = conClassName + "::getFileInputStream";
		InputStream objO = null;
		String strEntryName = "";
		try {
			if (objInputStream == null) {
				if (objVFSHandler == null) {
					throw new JobSchedulerException("objVFSHandler == null");
				}
				if (objZipEntry == null) {
					throw new JobSchedulerException("objZipEntry == null");
				}
				strEntryName = objZipEntry.getName();
				objInputStream = objVFSHandler.getInputStream(objZipEntry.getName());
				logger.debug(SOSVfs_D_207.params(strEntryName));
			}
		}
		catch (Exception e) {
			String strT = SOSVfs_E_134.params(conMethodName);
			logger.error(strT, e);
			throw new JobSchedulerException(strT, e);
		}
		return objInputStream;
	}

	/**
	 * \brief getFileOutputStream
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @return
	 */
	@Override
	public OutputStream getFileOutputStream() {
		final String conMethodName = conClassName + "::getFileOutputStream";
		// OutputStream objO = null;
		try {
			if (objOutputStream == null) {
				objOutputStream = objVFSHandler.getOutputStream(objZipEntry.getName());
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			String strT = SOSVfs_E_134.params(conMethodName);
			logger.error(strT, e);
			throw new JobSchedulerException(strT, e);
		}
		return objOutputStream;
	}

	/**
	 * \brief getFilePermissions
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public Integer getFilePermissions() throws Exception {
		return 0;
	}

	/**
	 * \brief getFileSize
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public long getFileSize() {
		return objZipEntry.getSize();
	}

	/**
	 * \brief getModificationTime
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @return
	 */
	@Override
	public String getModificationTime() {
		Date dteModificationTime = new Date(objZipEntry.getTime());
		String strMod = new JSDataElementDateTime(dteModificationTime).FormattedValue();
		return strMod;
	}

	/**
	 * \brief getName
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @return
	 */
	@Override
	public String getName() {
		return objZipEntry.getName();
	}

	/**
	 * \brief getParent
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @return
	 */
	@Override
	public String getParentVfs() {
		notImplemented();
		String strT = objZipEntry.getName();
		return strT;
	}

	/**
	 * \brief getParentFile
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @return
	 */
	@Override
	public ISOSVirtualFile getParentVfsFile() {
		notImplemented();
		// File strP = super.getParentFile();
		// ISOSVirtualFile objF = new SOSVfsFtpFile(strP.getAbsolutePath());
		// objF.setHandler(getHandler());
		// return objF;
		return null;
	}

	/**
	 * \brief isDirectory
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean isDirectory() {
		boolean flgResult = false;
		if (objZipEntry != null) {
			flgResult = objZipEntry.isDirectory();
		}
		else {
			flgResult = true;
		}
		return flgResult;
	}

	/**
	 * \brief isEmptyFile
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @return
	 */
	@Override
	public boolean isEmptyFile() {
		boolean flgResult = this.getFileSize() <= 0;
		return flgResult;
	}

	/**
	 * \brief notExists
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @return
	 */
	@Override
	public boolean notExists() {
		boolean flgResult = false;
		return flgResult;
	}

	/**
	 * \brief putFile
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @param fleFile
	 * @throws Exception
	 */
	@Override
	public void putFile(final File pfleFile) throws Exception {
		JSToolBox.notImplemented();
	}

	/**
	 * \brief putFile
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @param strFileName
	 * @throws Exception
	 */
	@Override
	public void putFile(final String pstrFileName) throws Exception {
		JSToolBox.notImplemented();
	}

	/**
	 * \brief rename
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @param pstrNewFileName
	 */
	@Override
	public void rename(final String pstrNewFileName) {
		notImplemented();
		// super.renameTo(new File(pstrNewFileName));
		// logger.info(String.format("File %1$s renamed to %2$s ", strFileName, pstrNewFileName));
	}

	/**
	 * \brief setFilePermissions
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @param pintNewPermission
	 * @throws Exception
	 */
	@Override
	public void setFilePermissions(final Integer pintNewPermission) throws Exception {
		JSToolBox.notImplemented();
	}

	/**
	 * \brief setHandler
	 * 
	 * \details
	 *
	 * \return 
	 *
	 * @param pobjVFSHandler
	 */
	@Override
	public void setHandler(final ISOSVfsFileTransfer pobjVFSHandler) {
		objVFSHandler = pobjVFSHandler;
	}

	@Override
	public void close() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::close";
		if (objOutputStream != null) {
			this.closeOutput();
		}
		else {
			if (objInputStream != null) {
				this.closeInput();
			}
		}
	}

	@Override
	public void closeInput() {
		final String conMethodName = conClassName + "::closeInput";
		try {
			if (objInputStream != null) {
				objInputStream.close();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new JobSchedulerException(SOSVfs_E_134.params(conMethodName), e);
		}
		finally {
			objInputStream = null;
		}
	}

	@Override
	public void closeOutput() {
		final String conMethodName = conClassName + "::closeOutput";
		try {
			if (objEntryOutputStream == null) {
				return;
			}
			objEntryOutputStream.flush();
			objEntryOutputStream.closeEntry();
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new JobSchedulerException(SOSVfs_E_134.params(conMethodName), e);
		}
		finally {
			objOutputStream = null;
		}
	}

	@Override
	public void flush() {
		final String conMethodName = conClassName + "::flush";
		try {
			this.getFileOutputStream().flush();
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new JobSchedulerException(SOSVfs_E_134.params(conMethodName), e);
		}
	}

	@Override
	public int read(final byte[] bteBuffer) {
		final String conMethodName = conClassName + "::read";
		int lngBytesRed = 0;
		try {
			lngBytesRed = this.getFileInputStream().read(bteBuffer);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new JobSchedulerException(SOSVfs_E_134.params(conMethodName), e);
		}
		return lngBytesRed;
	}

	@Override
	public int read(final byte[] bteBuffer, final int intOffset, final int intLength) {
		final String conMethodName = conClassName + "::read";
		int lngBytesRed = 0;
		try {
			lngBytesRed = this.getFileInputStream().read(bteBuffer, intOffset, intLength);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new JobSchedulerException(SOSVfs_E_134.params(conMethodName), e);
		}
		return lngBytesRed;
	}

	@Override
	public void write(final byte[] bteBuffer, final int intOffset, final int intLength) {
		final String conMethodName = conClassName + "::write";
		try {
			this.getFileOutputStream().write(bteBuffer, intOffset, intLength);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new JobSchedulerException(SOSVfs_E_134.params(conMethodName), e);
		}
	}

	@Override
	public void write(final byte[] bteBuffer) {
		final String conMethodName = conClassName + "::write";
		try {
			this.getFileOutputStream().write(bteBuffer);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new JobSchedulerException(SOSVfs_E_134.params(conMethodName), e);
		}
	}

	public void setZipEntry(final ZipEntry objZE) {
		objZipEntry = objZE;
	}

	public ZipEntry getZipEntry() {
		return objZipEntry;
	}

	@Override
	public String MakeZIPFile(final String pstrZipFileNameExtension) {
		notImplemented();
		return null;
	}

	@Override
	public void putFile(final ISOSVirtualFile pobjVirtualFile) throws Exception {
		notImplemented();
	}

	@Override
	public void setModeAppend(final boolean pflgModeAppend) {
		notImplemented();
	}

	@Override
	public long getModificationDateTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long setModificationDateTime(final long pdteDateTime) {
		// TODO Auto-generated method stub
		return 0;
	}
}
