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
package com.sos.scheduler.history.db;

//com.sos.scheduler.history


import java.util.Date;
import javax.persistence.*;

import org.apache.log4j.Logger;
import com.sos.hibernate.classes.DbItem;

/**
* \class SchedulerOrderDBItem 
* 
* \brief SchedulerOrderDBItem - 
* 
* \details
*
* \section SchedulerOrderDBItem.java_intro_sec Introduction
*
* \section SchedulerOrderDBItem.java_samples Some Samples
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
* \version 13.05.2011
* \see reference
*
* Created on 28.10.2012 16:42:58
 */

@Entity
@Table(name = "SCHEDULER_ORDERS")
public class SchedulerOrderDBItem extends DbItem {

    private static Logger logger = Logger.getLogger(SchedulerOrderDBItem.class);

    private String        spoolerId;
    private String        jobChain;
    private String        orderId;
    private String        state;
    private String        stateText;
    private String        title;
    private Date          createdTime;
    private Date          modTime;
    private Long          ordering;
    private byte[]        payload;
    private byte[]        runTime;
    private String        initialState;
    private byte[]        orderXml;
    private Date          distributedNextTime;
    private String        occupyingClusterMemberId;

    public SchedulerOrderDBItem() {

    }

    @Column(name = "`SPOOLER_ID`", nullable = false)
    public String getSpoolerId() {
        return spoolerId;
    }

    @Column(name = "`SPOOLER_ID`", nullable = false)
    public void setSpoolerId(String spoolerId) {
        this.spoolerId = spoolerId;
    }

    @Column(name = "`ORDER_ID`", nullable = false)
    public String getOrderId() {
        return orderId;
    }

    @Column(name = "`ORDER_ID`", nullable = false)
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "`JOB_CHAIN`", nullable = false)
    public String getJobChain() {
        return jobChain;
    }

    @Column(name = "`JOB_CHAIN`", nullable = false)
    public void setJobChain(String jobChain) {
        this.jobChain = jobChain;
    }

    @Column(name = "`CREATED_TIME`", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreatedTime() {
        return createdTime;
    }

    @Column(name = "`CREATED_TIME`", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Column(name = "`MOD_TIME`", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getModTime() {
        return modTime;
    }

    @Column(name = "`MOD_TIME`", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public void setEndTime(Date modTime) {
        this.modTime = modTime;
    }

    @Column(name = "`TITLE`", nullable = true)
    public String getCause() {
        return title;
    }

    @Column(name = "`TITLE`", nullable = true)
    public void setCause(String title) {
        this.title = title;
    }

    @Column(name = "`STATE`", nullable = true)
    public String getState() {
        return state;
    }

    @Column(name = "`STATE`", nullable = true)
    public void setState(String state) {
        this.state = state;
    }

    @Lob
    @Column(name = "`PAYLOAD`", nullable = true)
    public byte[] getPayload() {
        return payload;
    }

    @Column(name = "`PAYLOAD`", nullable = true)
    public void setLog(byte[] log) {
        this.payload = payload;
    }

    @Column(name = "`STATE_TEXT`", nullable = true)
    public String getStateText() {
        return stateText;
    }

    @Column(name = "`STATE_TEXT`", nullable = true)
    public void setStateText(String stateText) {
        this.stateText = stateText;
    }

    @Column(name = "`ORDERING`", nullable = true)
    public Long getOrderingText() {
        return ordering;
    }

    @Column(name = "`ORDERING`", nullable = true)
    public void setOrderingText(Long ordering) {
        this.ordering = ordering;
    }

    @Lob
    @Column(name = "`RUN_TIME`", nullable = true)
    public byte[] getRunTime() {
        return runTime;
    }

    @Column(name = "`RUN_TIME`", nullable = true)
    public void setRunTime(byte[] runtime) {
        this.runTime = runtime;
    }

    @Column(name = "`INITIAL_STATE`", nullable = true)
    public String getInitialState() {
        return initialState;
    }

    @Column(name = "`INITIAL_STATE`", nullable = true)
    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    @Column(name = "`OCCUPYING_CLUSTER_MEMBER_ID`", nullable = true)
    public String getOccupyingClusterMemberId() {
        return occupyingClusterMemberId;
    }

    @Column(name = "`OCCUPYING_CLUSTER_MEMBER_ID`", nullable = true)
    public void setOccupyingClusterMemberId(String occupyingClusterMemberId) {
        this.occupyingClusterMemberId = occupyingClusterMemberId;
    }

    @Lob
    @Column(name = "`ORDER_XML`", nullable = true)
    public byte[] getOrderXml() {
        return orderXml;
    }

    @Column(name = "`ORDER_XML`", nullable = true)
    public void setOrderXml(byte[] orderXml) {
        this.orderXml = orderXml;
    }

    @Column(name = "`DISTRIBUTED_NEXT_TIME`", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDistributedNextTime() {
        return distributedNextTime;
    }

    @Column(name = "`DISTRIBUTED_NEXT_TIME`", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public void setDistributedNextTime(Date distributedNextTime) {
        this.distributedNextTime = distributedNextTime;
    }

    @Transient
    public String getSchedulerId() {
        return this.getSpoolerId();
    }


    
}