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
package sos.scheduler.editor.conf;

import org.eclipse.swt.widgets.TreeItem;

import sos.scheduler.editor.app.IDataChanged;

public interface ISchedulerUpdate extends IDataChanged {
    
	
	public void updateJobs();    

  	public void updateJob();  	
  	
  	public void updateJob(org.jdom.Element elem);

  	public void expandItem(String name);

    public void updateJob(String s);
    
    public void updateCommands();

    public void updateExitCodesCommand();

    public void updateOrder(String s);

    public void updateOrders();

    public void updateDays(int type);
    
    public void updateDays(int type, String name);    
    
    public void updateSpecificWeekdays();
    
    public void updateJobChains();
    
    public void updateJobChain(String newName, String oldName);
        
    public void updateSchedules();
 
    public void updateScripts();
    
    public void updateTreeItem(String s);
    
    public void updateTree(String which);
    
    public void updateFont();
    
    public void updateFont(TreeItem item);
    
    public void updateWebServices();
    
    public void updateRunTime();
    
    
        
    
}