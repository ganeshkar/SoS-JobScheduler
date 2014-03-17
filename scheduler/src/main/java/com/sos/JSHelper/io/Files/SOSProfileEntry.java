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
package com.sos.JSHelper.io.Files;

import org.apache.log4j.Logger;

/**
* \class SOSProfileEntry 
* 
* \brief SOSProfileEntry - 
* 
* \details
*
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
* \version $Id$
* \see reference
*
* Created on 26.08.2011 20:59:01
 */

/**
 * @author KB
 *
 */
public class SOSProfileEntry {

	@SuppressWarnings("unused")
	private final String		conClassName	= "SOSProfileEntry";
	private static final String	conSVNVersion	= "$Id$";
	private static final Logger	logger			= Logger.getLogger(SOSProfileEntry.class);
	private String				strName;
	private String				strValue;

	public SOSProfileEntry() {
		//
	}

	public SOSProfileEntry(String pstrEntryName) {
		this(pstrEntryName, "");
	}

	public SOSProfileEntry(String pstrEntryName, String pstrEntryValue) {
		strName = pstrEntryName;
		strValue = pstrEntryValue;
		// System.out.println ("SOSProfileEntry: " + pstrEntryName + " = " + pstrEntryValue);
	}

	public String toString() {
		return (strName + "=" + strValue + "\n");
	}

	public String toXML() {
		String strT = "";
		strT = strT.concat("<" + strName + ">\n");
		strT = strT.concat(strValue + "\n");
		return strT.concat("</" + strName + ">\n");
	}

	/**
	 * @return
	 */
	public String Name() {
		return strName;
	}

	/**
	 * @return
	 */
	public String Value() {
		return strValue;
	}

	/**
	 * @param string
	 */
	public void Name(String string) {
		strName = string;
	}

	/**
	 * @param string
	 */
	public void Value(String string) {
		strValue = string;
	}

}