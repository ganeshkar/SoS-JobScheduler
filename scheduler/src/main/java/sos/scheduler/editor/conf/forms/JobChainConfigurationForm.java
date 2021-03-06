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
package sos.scheduler.editor.conf.forms;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import sos.scheduler.editor.app.IContainer;
import sos.scheduler.editor.app.IEditor;
import sos.scheduler.editor.app.IOUtils;
import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.MainWindow;
import sos.scheduler.editor.app.Messages;
import sos.scheduler.editor.app.Options;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.app.TreeData;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.listeners.JobChainConfigurationListener;
import sos.scheduler.editor.conf.DetailDom;
import  sos.scheduler.editor.conf.IDetailUpdate;

public class JobChainConfigurationForm extends SOSJOEMessageCodes implements IDetailUpdate, IEditor {  
    

    private JobChainConfigurationListener listener;

    private IContainer        container;

    private TreeItem          selection;

    private SashForm          sashForm  = null;

    private Group             gTree     = null;

    private Tree              tree      = null;

    private Composite         cMainForm = null;
    
    private static String     filename  = "";
    
    private static DetailDom         dom       = null;


    public JobChainConfigurationForm(IContainer container, Composite parent, int style) {
        super(parent, style);
        this.container = container;
        dom = new DetailDom();
        dom.setDataChangedListener(this);
        listener = new JobChainConfigurationListener(this, dom);

    }


    private void initialize() {
        FillLayout fillLayout = new FillLayout();
        fillLayout.spacing = 0;
        fillLayout.marginWidth = 5;
        fillLayout.marginHeight = 5;
        setLayout(fillLayout);
        createSashForm();
    }


    /**
     * This method initializes sashForm
     */
    private void createSashForm() {
        sashForm = new SashForm(this, SWT.NONE);
        createGTree();
        createCMainForm();
        cMainForm = new Composite(sashForm, SWT.NONE);
        cMainForm.setLayout(new FillLayout());
               
        Options.loadSash("main", sashForm);
        
    }


    /**
     * This method initializes gTree
     */
    private void createGTree() {
        gTree = JOE_G_JobAssistent_JobChainConfiguration.Control(new Group(sashForm, SWT.NONE));
        gTree.setLayout(new FillLayout());
//        gTree.setText("Job Chain Configuration");
        tree = new Tree(gTree, SWT.BORDER);
        //tree.setMenu(new TreeMenu(tree, dom, this).getMenu());
        tree.addListener(SWT.MenuDetect, new Listener() {
            public void handleEvent(Event e) {
                e.doit = tree.getSelectionCount() > 0;
            }
        });
        tree.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                if (tree.getSelectionCount() > 0) {
                    
                	selection = tree.getSelection()[0];
                	//if (selection == null)  selection = tree.getItem(0);

                    e.doit = listener.treeSelection(tree, cMainForm);

                    if (!e.doit) {
                        tree.setSelection(new TreeItem[] { selection });
                    } else {
                        selection = tree.getSelection()[0];
                    }
                } else
                	selection = tree.getItem(0);
            }
        });
    }


    /**
     * This method initializes cMainForm
     */
    private void createCMainForm() {
        
    }


    public Shell getSShell() {
        return this.getShell();
    }


    public void updateLanguage() {
        if (cMainForm.getChildren().length > 0) {
            if (cMainForm.getChildren()[0] instanceof IUpdateLanguage) {
                ((IUpdateLanguage) cMainForm.getChildren()[0]).setToolTipText();
            }
        }
    }


    public void dataChanged() {
        container.setStatusInTitle();
    }
    
    public void updateState(String state){
    	TreeItem item = tree.getSelection()[0];        
//        item.setText("State: " + state);
    	item.setText(JOE_M_JobAssistent_State.params(state));
        dom.setChanged(true);
    }
    
    public void updateJobChainname(String name){    	
    	TreeItem item = tree.getItem(0);
//    	item.setText("Job Chain: " + name);
    	item.setText(JOE_JobAssistent_JobChain.params(name));
    	dom.setChanged(true);
    }

    public void updateNote() {
    	dom.setChanged(true);
    }
	
	public void updateParamNote(){
		dom.setChanged(true);
	}
	
	public void updateParam(){
		dom.setChanged(true);
	}
	
    public boolean applyChanges() {
        Control[] c = cMainForm.getChildren();
        return c.length == 0 || Utils.applyFormChanges(c[0]);
    }


    public void openBlank() {
        initialize();       
        listener.treeFillMain(tree, cMainForm);
    }


    public boolean open(Collection files) {
        boolean res = IOUtils.openFile(files, dom);
        if (res) {
            initialize();
            listener.setFilename(filename);
            listener.treeFillMain(tree, cMainForm);
        }

        return res;
    }

    public boolean open(String filename, Collection files) {
        boolean res = IOUtils.openFile(filename, files, dom);
        if (res) {
            initialize();
            listener.setFilename(filename);
            listener.treeFillMain(tree, cMainForm);
        }

        return res;
    }

    public static String getFile(Collection filenames) {

    	 try {    		 
             // open file dialog
             if (filename == null || filename.equals("")) {
                 FileDialog fdialog = JOE_FD_JobAssistent_OpenFile.Control(new FileDialog(MainWindow.getSShell(), SWT.OPEN));
                 fdialog.setFilterPath(Options.getLastDirectory());
//                 fdialog.setText("Open File");
                 filename = fdialog.open();
             }

             // check for opened file
             if (filenames != null) {
                 for (Iterator it = filenames.iterator(); it.hasNext();) {
                     if (((String) it.next()).equals(filename)) {
//                         MainWindow.message(Messages.getString("MainListener.fileOpened"), SWT.ICON_INFORMATION | SWT.OK);
                    	 MainWindow.message(JOE_M_JobAssistent_FileIsOpened.label(), SWT.ICON_INFORMATION | SWT.OK);
                         return "";
                     }
                 }
             }

             if (filename != null && !filename.equals("")) { //$NON-NLS-1$
                 File file = new File(filename);
//System.out.println("~~~~~~~~~~~~~~~~~filename: " + filename);
                 // check the file
                 if (!file.exists()) {
                	 //System.out.println("~~~~~~~~~~~~~~~~~not exist filename: " + filename);
//                     MainWindow.message(Messages.getString("MainListener.fileNotFound"), //$NON-NLS-1$
//                             SWT.ICON_WARNING | SWT.OK);
                     MainWindow.message(JOE_M_JobAssistent_FileNotFound.label(), //$NON-NLS-1$
                             SWT.ICON_WARNING | SWT.OK);
                 } else if (!file.canRead())
//                     MainWindow.message(Messages.getString("MainListener.fileReadProtected"), //$NON-NLS-1$
//                             SWT.ICON_WARNING | SWT.OK);
                 MainWindow.message(JOE_M_JobAssistent_FileReadProtected.label(), //$NON-NLS-1$
                         SWT.ICON_WARNING | SWT.OK);
             } else
                 return filename;

//             MainWindow.getSShell().setText("Job Details Editor [" + filename + "]");
             MainWindow.getSShell().setText(JOE_M_JobAssistent_JobDetailsEditor.params(filename));

             Options.setLastDirectory(new File(filename), dom);
             return filename;
         } catch (Exception e) {
        	 try {
// 				new sos.scheduler.editor.app.ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName() , e);
        		 new sos.scheduler.editor.app.ErrorLog(JOE_E_0002.params(sos.util.SOSClassUtil.getMethodName()) , e);
 			} catch(Exception ee) {
 				//tu nichts
 			}
             e.printStackTrace();
             MainWindow.message(e.getMessage(), SWT.ICON_ERROR | SWT.OK);
         }
          
         return filename;
    }

    public boolean save() {        
    	boolean res = IOUtils.saveFile(dom, false);
        if (res)
            container.setNewFilename(null);
        return res;
    }


    public boolean saveAs() {
    	String old = dom.getFilename();
        boolean res = IOUtils.saveFile(dom, true);
        if (res)
            container.setNewFilename(old);
        return res;
    }


    public boolean close() {
        return applyChanges() && IOUtils.continueAnyway(dom);
    }


    public boolean hasChanges() {
        Options.saveSash("main", sashForm.getWeights());
        return dom.isChanged();
    }


    public String getHelpKey() {
        if (tree.getSelectionCount() > 0) {
            TreeItem item = tree.getSelection()[0];
            TreeData data = (TreeData) item.getData();
            if (data != null && data.getHelpKey() != null)
                return data.getHelpKey();
        }
        return null;
    }


    public String getFilename() {    	
        return dom.getFilename();        
    }


	public Composite getCMainForm() {
		return cMainForm;
	}


	public DetailDom getDom() {
		return dom;
	}


}
