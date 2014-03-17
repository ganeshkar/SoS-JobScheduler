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
package com.sos.scheduler.engine.kernel.order;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.sos.scheduler.engine.data.folder.JobChainPath;
import com.sos.scheduler.engine.data.order.OrderKey;
import com.sos.scheduler.engine.kernel.cppproxy.Order_subsystemC;
import com.sos.scheduler.engine.kernel.job.Job;
import com.sos.scheduler.engine.kernel.order.jobchain.JobChain;
import com.sos.scheduler.engine.kernel.scheduler.SchedulerException;
import com.sos.scheduler.engine.kernel.scheduler.Subsystem;

import javax.annotation.Nullable;

public class OrderSubsystem implements Subsystem {
    private final Order_subsystemC cppProxy;

    public OrderSubsystem(Order_subsystemC cppproxy) {
        this.cppProxy = cppproxy;
    }

    public final ImmutableList<JobChain> jobChains() {
        return ImmutableList.copyOf(cppProxy.java_file_baseds());
    }

    public final Iterable<JobChain> jobchainsOfJob(final Job job) {
        return Iterables.filter(jobChains(), new Predicate<JobChain>() {
            @Override public boolean apply(JobChain jobChain) {
                return jobChain.refersToJob(job);
            }
        });
    }

    public final JobChain jobChain(JobChainPath o) {
        JobChain result = cppProxy.java_file_based_or_null(o.asString());
        if (result == null)
            throw new SchedulerException("Unknown '"+o+"'");
        return result;
    }

    @Nullable public final Order orderOrNull(OrderKey orderKey) {
        return jobChain(orderKey.getJobChainPath()).orderOrNull(orderKey.getId());
    }

    public final Order order(OrderKey orderKey) {
        return jobChain(orderKey.getJobChainPath()).order(orderKey.getId());
    }

    public  final void tryRemoveOrder(OrderKey k) {
        Order o = orderOrNull(k);
        if (o != null)
            o.remove();
    }
}