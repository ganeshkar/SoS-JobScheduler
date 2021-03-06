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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.jdom.Element;

import sos.scheduler.editor.app.IUnsaved;
import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.doc.DocumentationDom;
import sos.scheduler.editor.doc.IUpdateTree;
import sos.scheduler.editor.doc.listeners.ProfilesListener;

public class ProfilesForm extends SOSJOEMessageCodes implements IUnsaved, IUpdateLanguage {
    private ProfilesListener listener    = null;

    private DocumentationDom dom         = null;

    IUpdateTree              treeHandler = null;

    private Group            group       = null;

    @SuppressWarnings("unused")
	private Label            label3      = null;

    private Text             tName       = null;

    private Button           bNotes      = null;

    private Button           bApply      = null;

    private Label            label4      = null;

    private Button           bNew        = null;

    private Label            label5      = null;

    private Button           bRemove     = null;

    private Table            tProfiles   = null;


    public ProfilesForm(Composite parent, int style, DocumentationDom dom, Element parentElement,
            IUpdateTree treeHandler) {
        super(parent, style);
        this.dom = dom;
        this.treeHandler = treeHandler;
        listener = new ProfilesListener(dom, parentElement);
        initialize();
    }


    private void initialize() {
        createGroup();
        setSize(new Point(561, 401));
        setLayout(new FillLayout());

        setProfileStatus(false);
        bRemove.setEnabled(false);
        fillProfiles();
        setToolTipText();
    }


    private void createGroup() {

        GridData gridData11 = new GridData(GridData.FILL, GridData.FILL, true, true, 3, 3);
        
        GridData gridData6 = new GridData(GridData.FILL, GridData.CENTER, false, false);
        
        GridData gridData5 = new GridData(GridData.FILL, GridData.BEGINNING, false, false);
        
        GridData gridData4 = new GridData(GridData.FILL, GridData.CENTER, false, false);
        
        GridData gridData3 = new GridData(GridData.FILL, GridData.CENTER, false, false);
        
        GridData gridData1 = new GridData(GridData.FILL, GridData.CENTER, false, false, 4, 1);
        
        GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
        
        GridLayout gridLayout2 = new GridLayout(4, false);
        
        group = JOE_G_ProfilesForm_Profiles.Control(new Group(this, SWT.NONE));
        group.setLayout(gridLayout2); // Generated
        
        label3 = JOE_L_Name.Control(new Label(group, SWT.NONE));
        
        tName = JOE_T_ProfilesForm_Name.Control(new Text(group, SWT.BORDER));
        tName.setLayoutData(gridData); // Generated
        tName.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
            public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
                bApply.setEnabled(true);
                getShell().setDefaultButton(bApply);
            }
        });
        
        bNotes = JOE_B_ProfilesForm_ProfileNotes.Control(new Button(group, SWT.NONE));
        bNotes.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
//                String tip = Messages.getTooltip("doc.note.text.profile");
            	String tip = "";
//                DocumentationForm.openNoteDialog(dom, listener.getProfileElement(), "note", tip, true, !listener
//                        .isNewProfile(),"Profile Note");
                DocumentationForm.openNoteDialog(dom, listener.getProfileElement(), "note", tip, true, !listener
                        .isNewProfile(), JOE_B_ProfilesForm_ProfileNotes.label());
            }
        });
        
        bApply = JOE_B_ProfilesForm_ApplyProfile.Control(new Button(group, SWT.NONE));
        bApply.setLayoutData(gridData3);
        bApply.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                applyProfile();
            }
        });
        
        label4 = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
        label4.setText("Label"); // Generated
        label4.setLayoutData(gridData1); // Generated
        
        tProfiles = JOE_Tbl_ProfilesForm_Profiles.Control(new Table(group, SWT.BORDER));
        tProfiles.setHeaderVisible(true); // Generated
        tProfiles.setLayoutData(gridData11); // Generated
        tProfiles.setLinesVisible(true); // Generated
        tProfiles.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                if (tProfiles.getSelectionCount() > 0) {
                    listener.selectProfile(tProfiles.getSelectionIndex());
                    setProfileStatus(true);
                    bRemove.setEnabled(true);
                    bApply.setEnabled(false);
                }
            }
        });
        
        TableColumn tableColumn = JOE_TCl_ProfilesForm_Name.Control(new TableColumn(tProfiles, SWT.NONE));
        tableColumn.setWidth(450); // Generated
        
        bNew = JOE_B_ProfilesForm_NewProfile.Control(new Button(group, SWT.NONE));
        bNew.setLayoutData(gridData4); // Generated
        bNew.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                listener.setNewProfile();
                setProfileStatus(true);
                bApply.setEnabled(true);
                bRemove.setEnabled(false);
                getShell().setDefaultButton(bApply);
            }
        });
        
        label5 = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
        label5.setText("Label"); // Generated
        label5.setLayoutData(gridData6); // Generated
        
        bRemove = JOE_B_ProfilesForm_RemoveProfile.Control(new Button(group, SWT.NONE));
        bRemove.setLayoutData(gridData5); // Generated
        bRemove.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                if (tProfiles.getSelectionCount() > 0) {
                    if (listener.removeProfile(tProfiles.getSelectionIndex())) {
                        setProfileStatus(false);
                        bRemove.setEnabled(false);
                        bApply.setEnabled(false);
                        fillProfiles();
                    }

                }
            }
        });
    }


    public void apply() {
        if (isUnsaved())
            applyProfile();
    }


    public boolean isUnsaved() {
        listener.checkSettings();
        return bApply.isEnabled();
    }


    public void setToolTipText() {
//
    }


    private void applyProfile() {
        listener.applyProfile(tName.getText());
        fillProfiles();
        bRemove.setEnabled(tProfiles.getSelectionCount() > 0);
        bApply.setEnabled(false);
    }


    private void setProfileStatus(boolean enabled) {
        tName.setEnabled(enabled);
        bNotes.setEnabled(enabled);
        if (enabled) {
            tName.setText(listener.getName());
            tName.setFocus();
        }
        bApply.setEnabled(false);
    }


    private void fillProfiles() {
        listener.fillProfiles(tProfiles);
        if (treeHandler != null)
            treeHandler.fillProfiles();
    }

} // @jve:decl-index=0:visual-constraint="10,10"
