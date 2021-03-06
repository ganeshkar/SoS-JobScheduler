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
package sos.scheduler.editor.doc.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.jdom.Element;

import sos.scheduler.editor.app.IUnsaved;
import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.doc.DocumentationDom;
import sos.scheduler.editor.doc.listeners.ProcessListener;

public class ProcessForm extends SOSJOEMessageCodes implements IUnsaved, IUpdateLanguage {

	private ProcessListener	listener	= null;

	private Group			group		= null;

	@SuppressWarnings("unused")
	private Label			label		= null;

	@SuppressWarnings("unused")
	private Label			label1		= null;

	@SuppressWarnings("unused")
	private Label			label2		= null;

	private Text			tFile		= null;

	private Text			tParameter	= null;

	private Text			tLog		= null;

	private Button			cUseProcess	= null;

	private Group			group1		= null;

	@SuppressWarnings("unused")
	private Label			label3		= null;

	private Text			tName		= null;

	@SuppressWarnings("unused")
	private Label			label4		= null;

	private Text			tValue		= null;

	private Button			bApply		= null;

	private Label			label5		= null;

	private Table			tVariables	= null;

	private Button			bRemove		= null;

	public ProcessForm(Composite parent, int style, DocumentationDom dom, Element job) {
		super(parent, style);
		initialize();
		setToolTipText();
		listener = new ProcessListener(dom, job);
		cUseProcess.setSelection(listener.isProcess());
		initValues();
	}

	private void initialize() {
		cUseProcess = JOE_B_ProcessForm_UseProcess.Control(new Button(this, SWT.RADIO));
		cUseProcess.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if (cUseProcess.getSelection() != listener.isProcess()) {
					if (cUseProcess.getSelection())
						listener.setProcess();
					initValues();
				}
			}
		});

		createGroup();
		setSize(new Point(623, 421));
		setLayout(new GridLayout());
	}

	/**
	 * This method initializes group
	 */
	private void createGroup() {
		GridData gridData11 = new GridData(GridData.FILL, GridData.FILL, true, true);

		GridData gridData2 = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridData2.horizontalIndent = 7; // Generated

		GridData gridData1 = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridData1.horizontalIndent = 7; // Generated

		GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridData.horizontalIndent = 7; // Generated

		GridLayout gridLayout = new GridLayout(2, false);
		// gridLayout.numColumns = 2; // Generated

		group = JOE_G_ProcessForm_Process.Control(new Group(this, SWT.NONE));
		group.setLayoutData(gridData11); // Generated
		group.setLayout(gridLayout); // Generated

		label = JOE_L_ProcessForm_File.Control(new Label(group, SWT.NONE));

		tFile = JOE_T_ProcessForm_File.Control(new Text(group, SWT.BORDER));
		tFile.setLayoutData(gridData); // Generated
		tFile.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				listener.setFile(tFile.getText());
				if (cUseProcess.getSelection())
					Utils.setBackground(tFile, true);
			}
		});

		label1 = JOE_L_ProcessForm_Parameter.Control(new Label(group, SWT.NONE));

		tParameter = JOE_T_ProcessForm_Parameter.Control(new Text(group, SWT.BORDER));
		tParameter.setLayoutData(gridData1); // Generated
		tParameter.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				listener.setParam(tParameter.getText());
			}
		});

		label2 = JOE_L_ProcessForm_Log.Control(new Label(group, SWT.NONE));

		tLog = JOE_T_ProcessForm_Log.Control(new Text(group, SWT.BORDER));
		tLog.setLayoutData(gridData2); // Generated
		tLog.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				listener.setLog(tLog.getText());
			}
		});

		createGroup1();
	}

	/**
	 * This method initializes group1
	 */
	private void createGroup1() {
		GridData gridData9 = new GridData(GridData.FILL, GridData.CENTER, false, false);

		GridData gridData8 = new GridData(GridData.FILL, GridData.BEGINNING, false, false);

		GridData gridData7 = new GridData(GridData.FILL, GridData.FILL, true, true, 4, 1);

		GridData gridData6 = new GridData(GridData.FILL, GridData.CENTER, false, false, 5, 1);

		GridData gridData5 = new GridData(GridData.FILL, GridData.CENTER, true, false);

		GridData gridData4 = new GridData(GridData.FILL, GridData.CENTER, true, false);

		GridData gridData3 = new GridData(GridData.FILL, GridData.FILL, true, true, 2, 1);

		GridLayout gridLayout1 = new GridLayout(5, false);

		group1 = JOE_G_ProcessForm_EnvironmentVariables.Control(new Group(group, SWT.NONE));
		group1.setLayout(gridLayout1); // Generated
		group1.setLayoutData(gridData3); // Generated

		label3 = JOE_L_Name.Control(new Label(group1, SWT.NONE));

		tName = JOE_T_ProcessForm_Name.Control(new Text(group1, SWT.BORDER));
		tName.setLayoutData(gridData4); // Generated
		tName.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				bApply.setEnabled(tName.getText().length() > 0);
				getShell().setDefaultButton(bApply);
			}
		});

		label4 = JOE_L_ProcessForm_Value.Control(new Label(group1, SWT.NONE));

		tValue = JOE_T_ProcessForm_Value.Control(new Text(group1, SWT.BORDER));
		tValue.setLayoutData(gridData5); // Generated
		tValue.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				bApply.setEnabled(tName.getText().length() > 0);
				getShell().setDefaultButton(bApply);
			}
		});

		bApply = JOE_B_ProcessForm_Apply.Control(new Button(group1, SWT.NONE));
		bApply.setLayoutData(gridData9); // Generated
		bApply.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				applyParam();
			}
		});

		label5 = new Label(group1, SWT.SEPARATOR | SWT.HORIZONTAL);
		label5.setText("Label"); // Generated
		label5.setLayoutData(gridData6); // Generated

		tVariables = JOE_Tbl_ProcessForm_Variables.Control(new Table(group1, SWT.BORDER));
		tVariables.setHeaderVisible(true); // Generated
		tVariables.setLayoutData(gridData7); // Generated
		tVariables.setLinesVisible(true); // Generated
		tVariables.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if (tVariables.getSelectionCount() > 0) {
					TableItem item = tVariables.getItem(tVariables.getSelectionIndex());
					tName.setText(item.getText(0));
					tValue.setText(item.getText(1));
					bApply.setEnabled(false);
					tName.setFocus();
				}
				bRemove.setEnabled(tVariables.getSelectionCount() > 0);
			}
		});

		bRemove = JOE_B_ProcessForm_Remove.Control(new Button(group1, SWT.NONE));
		bRemove.setLayoutData(gridData8); // Generated
		bRemove.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if (tVariables.getSelectionCount() > 0) {
					listener.removeVariable(tVariables.getItem(tVariables.getSelectionIndex()).getText(0));
					listener.fillTable(tVariables);
					tName.setText("");
					tValue.setText("");
					bApply.setEnabled(false);
				}
				bRemove.setEnabled(false);
				boolean valid = (!tName.getText().equals(""));
				if (valid) {
					getShell().setDefaultButton(bApply);
				}
				bApply.setEnabled(valid);
			}
		});

		TableColumn tableColumn = JOE_TCl_ProcessForm_Name.Control(new TableColumn(tVariables, SWT.NONE));
		tableColumn.setWidth(150); // Generated

		TableColumn tableColumn1 = JOE_TCl_ProcessForm_Value.Control(new TableColumn(tVariables, SWT.NONE));
		tableColumn1.setWidth(60); // Generated
	}

	public void apply() {
		if (isUnsaved())
			applyParam();
	}

	public boolean isUnsaved() {
		return bApply.isEnabled();
	}

	public void setToolTipText() {
		// cUseProcess.setToolTipText(Messages.getTooltip("doc.process.useProcess"));
	}

	private void initValues() {
		boolean enabled = cUseProcess.getSelection();
		tFile.setEnabled(enabled);
		tParameter.setEnabled(enabled);
		tLog.setEnabled(enabled);
		tName.setEnabled(enabled);
		tValue.setEnabled(enabled);
		bApply.setEnabled(enabled);
		bRemove.setEnabled(false);

		if (enabled) {
			tFile.setText(listener.getFile());
			tParameter.setText(listener.getParam());
			tLog.setText(listener.getLog());
			tName.setText("");
			tValue.setText("");
			tFile.setFocus();
			listener.fillTable(tVariables);
		}
	}

	private void applyParam() {
		listener.applyParam(tName.getText(), tValue.getText());
		tName.setText("");
		tValue.setText("");
		listener.fillTable(tVariables);
		tVariables.deselectAll();
		bApply.setEnabled(false);
		bRemove.setEnabled(false);
		tName.setFocus();
	}

} // @jve:decl-index=0:visual-constraint="10,10"
