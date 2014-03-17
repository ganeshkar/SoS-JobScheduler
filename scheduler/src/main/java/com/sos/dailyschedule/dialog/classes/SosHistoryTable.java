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
package com.sos.dailyschedule.dialog.classes;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Table;

import com.sos.dashboard.globals.DashBoardConstants;
import com.sos.dialog.components.SOSTableColumn;
import com.sos.localization.Messages;

/**
* \class SosHistoryTable 
* 
* \brief SosHistoryTable - 
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
* \author Uwe Risse
* \version 31.01.2012
* \see reference
*
* Created on 31.01.2012 09:59:28
 */
public class SosHistoryTable extends Table {
	@SuppressWarnings("unused")
	private final String	conClassName	= "SosHistoryTable";
	private SOSTableColumn	c1;
	private SOSTableColumn	c2;
	private SOSTableColumn	c3;
	private SOSTableColumn	c4;

	public SosHistoryTable(Composite composite, int style) {
		super(composite, style);
		createTable(composite);
	}

	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void createTable(final Composite composite) {
		this.setSortDirection(SWT.UP);
		this.setLinesVisible(true);
		this.setHeaderVisible(true);
		Messages messages = new Messages(DashBoardConstants.conPropertiesFileName);
		c1 = new SOSTableColumn(this, SWT.NONE, messages.getLabel(DashBoardConstants.conSOSDashB_START), 90, SOSTableColumn.ColumnType.DATE);
		c2 = new SOSTableColumn(this, SWT.NONE, messages.getLabel(DashBoardConstants.conSOSDashB_END), 90, SOSTableColumn.ColumnType.DATE);
		c3 = new SOSTableColumn(this, SWT.NONE, messages.getLabel(DashBoardConstants.conSOSDashB_DURATION), 90);
		c4 = new SOSTableColumn(this, SWT.NONE, messages.getLabel(DashBoardConstants.conSOSDashB_EXIT), 90);
		composite.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = composite.getClientArea();
				Point size = computeSize(SWT.DEFAULT, SWT.DEFAULT);
				ScrollBar vBar = getVerticalBar();
				int width = area.width - computeTrim(0, 0, 0, 0).width + vBar.getSize().x;
				if (size.y > area.height + getHeaderHeight()) {
					Point vBarSize = vBar.getSize();
					if (vBar.isVisible()) {
						width -= vBarSize.x;
					}
				}
				Point oldSize = getSize();
				if (oldSize.x > area.width) {
					c3.setWidth(width - c1.getWidth() - c2.getWidth());
					setSize(area.width, area.height);
				}
				else {
					setSize(area.width, area.height);
					c3.setWidth(width - c1.getWidth() - c2.getWidth());
				}
			}
		});
	}
}