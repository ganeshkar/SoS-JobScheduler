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
package sos.scheduler.process;


import sos.util.SOSSchedulerLogger;

/**
 * <p>ASKOrderMonitor implementiert ein Monitor-Script, das pro Auftrag vor bzw. nach dessen Verarbeitung gestartet wird.
 * Das Script wird f�r Standard Job-Klassen verwendet, an die Auftragsparameter aus der XML-Konfiguration �bergeben werden.</p>
 * 
 * @author andreas.pueschel@sos-berlin.com
 * @since 1.0 2006-10-05 
 */

public class ProcessJobMonitor extends ProcessBaseMonitor {


    /**
     * Initialisierung vor Verarbeitung eines Jobs
     * @see sos.spooler.Monitor_impl#spooler_task_before()
     */
    public boolean spooler_task_before() {
        
        try { // to map order configuration to this job
            this.setLogger(new SOSSchedulerLogger(spooler_log));            
            
            if (spooler_task.params().value("configuration_path") != null && spooler_task.params().value("configuration_path").length() > 0)
                this.setConfigurationPath(spooler_task.params().value("configuration_path"));

            if (spooler_task.params().value("configuration_file") != null && spooler_task.params().value("configuration_file").length() > 0)
                this.setConfigurationFilename(spooler_task.params().value("configuration_file"));

            this.prepareConfiguration();

            return true;
        } catch (Exception e) {
            spooler_log.warn("error occurred in spooler_process_before(): " + e.getMessage());
            return false;
        }
    }

    
    /**
     * Cleanup nach Verarbeitung eines Jobs
     * @see sos.spooler.Monitor_impl#spooler_task_after()
     */

    public boolean spooler_task_after(boolean rc) {
        
        try { // to map order configuration to this job
            this.setLogger(new SOSSchedulerLogger(spooler_log));            

            if (rc == false) spooler_task.order().setback();
            
            return rc;

        } catch (Exception e) {
            spooler_log.warn("error occurred in spooler_process_after(): " + e.getMessage());
            return false;
        } finally {
            try { this.cleanupConfiguration(); } catch (Exception e) {}
        }
    }

}
