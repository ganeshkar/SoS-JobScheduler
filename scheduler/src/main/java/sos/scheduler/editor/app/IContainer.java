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

import org.eclipse.swt.custom.CTabItem;

import sos.scheduler.editor.actions.forms.ActionsForm;
import sos.scheduler.editor.conf.forms.JobChainConfigurationForm;
import sos.scheduler.editor.conf.forms.SchedulerForm;
import sos.scheduler.editor.doc.forms.DocumentationForm;

public interface IContainer {
	
    public SchedulerForm newScheduler();
    
    public SchedulerForm newScheduler(int type);

    public DocumentationForm newDocumentation();        

    public SchedulerForm openScheduler();
    
    public org.eclipse.swt.widgets.Composite openQuick();
    
    public org.eclipse.swt.widgets.Composite openQuick(String filename);
    
    public SchedulerForm openScheduler(String filename);
    
   // public SchedulerForm reOpenScheduler(String filename);

    public DocumentationForm openDocumentation();
    
    public DocumentationForm openDocumentation(String filename);
    
    public String openDocumentationName();

    public IEditor getCurrentEditor();

    public void setStatusInTitle();

    public void setNewFilename(String oldFilename);       

    public boolean closeAll();

    public void updateLanguages();
    
    public JobChainConfigurationForm newDetails();
    
    public JobChainConfigurationForm openDetails();
    
    //public SchedulerForm openDirectory();
    
    public SchedulerForm openDirectory(String filename);        
    
    public CTabItem getCurrentTab();
    
    public ActionsForm newActions();

	public void setTitleText(String strT);
}