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
package sos.scheduler.editor.app;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


class Dialog extends org.eclipse.swt.widgets.Dialog {
	
	
	Object result;
	
	//private FTPDialogListener listener = null;
	//FTPDialog ftpDialog = null;	
	private Object obj = null;
	
	private Text text = null; 

	public Dialog(Shell parent, int style) {
		super(parent, style);
	}
	
	
	public Dialog(Shell parent) {
		this(parent, 0);
	}
	
	/*public Object open(FTPDialog ftpDialog_) {
		//listener = listener_;
		ftpDialog = ftpDialog_;
		return open();
	}*/
	
	public Object open(Object obj_) {
		//listener = listener_;
		obj = obj_;
		return open();
	}
	
	public Object open() {
		Shell parent = getParent();
		final Shell newFolderShell =
			new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		
		newFolderShell.addTraverseListener(new TraverseListener() {
			public void keyTraversed(final TraverseEvent e) {				
				if(e.detail == SWT.TRAVERSE_ESCAPE) {
					close();
				}
			}
		});
		
		newFolderShell.setImage(ResourceManager.getImageFromResource("/sos/scheduler/editor/editor.png"));
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 10;
		gridLayout.horizontalSpacing = 10;
		gridLayout.marginHeight = 10;
		gridLayout.marginWidth = 10;
		gridLayout.marginTop = 10;
		gridLayout.numColumns = 2;
		newFolderShell.setLayout(gridLayout);
		newFolderShell.setText("Create New Folder");
		newFolderShell.setText(getText());
		newFolderShell.pack();

		/*if (obj instanceof FTPDialogListener)
			text = new Text(newFolderShell, SWT.PASSWORD | SWT.BORDER);
		else
		*/ 
			text = new Text(newFolderShell, SWT.BORDER);
		
		text.addKeyListener(new KeyAdapter() {
			public void keyPressed(final KeyEvent e) {
				if (e.keyCode == SWT.CR)
					doSomethings();
			}
		});
		text.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false, 2, 1));

		final Button butOK = new Button(newFolderShell, SWT.NONE);
		butOK.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				
				doSomethings();
				
			}
		});
		butOK.setText("OK");

		final Button butCancel = new Button(newFolderShell, SWT.NONE);
		butCancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				close();
			}
		});
		butCancel.setLayoutData(new GridData(GridData.END, GridData.CENTER, true, false));
		butCancel.setText("Cancel");
		newFolderShell.open();		
		
		//org.eclipse.swt.graphics.Rectangle rect = image.getBounds();
		newFolderShell.setSize(241, 107);

		
		org.eclipse.swt.widgets.Display display = parent.getDisplay();
		while (!newFolderShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		//image.dispose();		
		return result;
		
	}
	

	public static void main(String[] args) {
		final Shell shell =
			new Shell();
		shell.pack();
		

		Dialog dialog = new Dialog(shell);
		
		dialog.open();
	}
	
	private void close() {
		getParent().close();
	}
	
	public void doSomethings() {
		try {
		if(obj instanceof FTPDialog) {
			
			FTPDialog ftpDialog = (FTPDialog)obj;
			
			ftpDialog.getListener().getCurrProfile().mkDirs(text.getText());
			ftpDialog.refresh();
			
		/*} else if (obj instanceof FTPDialogListener) {
			
			FTPDialogListener listener = (FTPDialogListener)obj;
			listener.setPassword(text.getText());
			*/
		} else if(obj instanceof WebDavDialog) {
				
				WebDavDialog webdavDialog = (WebDavDialog)obj;
				String parentPath = webdavDialog.getTxtUrl().getText();
				if(!parentPath.endsWith("/"))
					parentPath = parentPath + "/";
				
				webdavDialog.getListener().mkDirs(parentPath + text.getText());
				webdavDialog.refresh();
				
		} else if (obj instanceof WebDavDialogListener) {
			
			WebDavDialogListener listener = (WebDavDialogListener)obj;
			listener.setPassword(text.getText());
			
		}
		} catch (Exception e) {
			try {
  			new ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName(), e);
  		} catch(Exception ee) {
  			//tu nichts
  		}
		}
		close();
	}
}
