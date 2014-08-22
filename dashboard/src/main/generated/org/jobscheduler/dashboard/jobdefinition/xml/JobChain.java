//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.21 at 05:03:57 AM PDT 
//


package org.jobscheduler.dashboard.jobdefinition.xml;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;sequence>
 *             &lt;element name="job_chain_node.job_chain" type="{}job_chain_node.job_chain"/>
 *             &lt;choice maxOccurs="unbounded">
 *               &lt;element name="job_chain_node.job_chain" type="{}job_chain_node.job_chain"/>
 *               &lt;element name="job_chain_node.end" type="{}job_chain_node.end"/>
 *             &lt;/choice>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;element name="file_order_source" maxOccurs="unbounded" minOccurs="0">
 *               &lt;complexType>
 *                 &lt;complexContent>
 *                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                     &lt;attribute name="directory" use="required" type="{}String" />
 *                     &lt;attribute name="regex" type="{}String" />
 *                     &lt;attribute name="delay_after_error" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *                     &lt;attribute name="repeat">
 *                       &lt;simpleType>
 *                         &lt;union>
 *                           &lt;simpleType>
 *                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *                               &lt;enumeration value="no"/>
 *                             &lt;/restriction>
 *                           &lt;/simpleType>
 *                           &lt;simpleType>
 *                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *                             &lt;/restriction>
 *                           &lt;/simpleType>
 *                         &lt;/union>
 *                       &lt;/simpleType>
 *                     &lt;/attribute>
 *                     &lt;attribute name="max" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *                     &lt;attribute name="next_state" type="{}String" />
 *                   &lt;/restriction>
 *                 &lt;/complexContent>
 *               &lt;/complexType>
 *             &lt;/element>
 *             &lt;choice maxOccurs="unbounded">
 *               &lt;element name="job_chain_node">
 *                 &lt;complexType>
 *                   &lt;complexContent>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                       &lt;attribute name="state" use="required" type="{}String" />
 *                       &lt;attribute name="job" type="{}Path" />
 *                       &lt;attribute name="next_state" type="{}String" />
 *                       &lt;attribute name="error_state" type="{}String" />
 *                       &lt;attribute name="on_error">
 *                         &lt;simpleType>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *                             &lt;enumeration value="setback"/>
 *                             &lt;enumeration value="suspend"/>
 *                           &lt;/restriction>
 *                         &lt;/simpleType>
 *                       &lt;/attribute>
 *                       &lt;attribute name="suspend" type="{}Yes_no" />
 *                       &lt;attribute name="delay" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                     &lt;/restriction>
 *                   &lt;/complexContent>
 *                 &lt;/complexType>
 *               &lt;/element>
 *               &lt;element name="file_order_sink">
 *                 &lt;complexType>
 *                   &lt;complexContent>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                       &lt;attribute name="state" use="required" type="{}String" />
 *                       &lt;attribute name="remove" type="{}Yes_no" />
 *                       &lt;attribute name="move_to" type="{}File" />
 *                       &lt;attribute name="delay" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                     &lt;/restriction>
 *                   &lt;/complexContent>
 *                 &lt;/complexType>
 *               &lt;/element>
 *               &lt;element name="job_chain_node.end" type="{}job_chain_node.end"/>
 *             &lt;/choice>
 *           &lt;/sequence>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{}Name" />
 *       &lt;attribute name="replace" type="{}Yes_no" />
 *       &lt;attribute name="visible" type="{}Yes_no_never" />
 *       &lt;attribute name="orders_recoverable" type="{}Yes_no" />
 *       &lt;attribute name="distributed" type="{}Yes_no" />
 *       &lt;attribute name="title" type="{}String" />
 *       &lt;attribute name="max_orders" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "note",
    "jobChainNodeJobChain",
    "jobChainNodeJobChainOrJobChainNodeEnd",
    "fileOrderSource",
    "jobChainNodeOrFileOrderSinkOrJobChainNodeEnd"
})
@XmlRootElement(name = "job_chain")
public class JobChain {

    protected List<Object> note;
    @XmlElement(name = "job_chain_node.job_chain")
    protected JobChainNodeJobChain jobChainNodeJobChain;
    @XmlElements({
        @XmlElement(name = "job_chain_node.job_chain", type = JobChainNodeJobChain.class),
        @XmlElement(name = "job_chain_node.end", type = JobChainNodeEnd.class)
    })
    protected List<Object> jobChainNodeJobChainOrJobChainNodeEnd;
    @XmlElement(name = "file_order_source")
    protected List<JobChain.FileOrderSource> fileOrderSource;
    @XmlElements({
        @XmlElement(name = "job_chain_node", type = JobChain.JobChainNode.class),
        @XmlElement(name = "file_order_sink", type = JobChain.FileOrderSink.class),
        @XmlElement(name = "job_chain_node.end", type = JobChainNodeEnd.class)
    })
    protected List<Object> jobChainNodeOrFileOrderSinkOrJobChainNodeEnd;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "replace")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String replace;
    @XmlAttribute(name = "visible")
    protected String visible;
    @XmlAttribute(name = "orders_recoverable")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String ordersRecoverable;
    @XmlAttribute(name = "distributed")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String distributed;
    @XmlAttribute(name = "title")
    protected String title;
    @XmlAttribute(name = "max_orders")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger maxOrders;

    /**
     * Gets the value of the note property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the note property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNote().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getNote() {
        if (note == null) {
            note = new ArrayList<Object>();
        }
        return this.note;
    }

    /**
     * Gets the value of the jobChainNodeJobChain property.
     * 
     * @return
     *     possible object is
     *     {@link JobChainNodeJobChain }
     *     
     */
    public JobChainNodeJobChain getJobChainNodeJobChain() {
        return jobChainNodeJobChain;
    }

    /**
     * Sets the value of the jobChainNodeJobChain property.
     * 
     * @param value
     *     allowed object is
     *     {@link JobChainNodeJobChain }
     *     
     */
    public void setJobChainNodeJobChain(JobChainNodeJobChain value) {
        this.jobChainNodeJobChain = value;
    }

    /**
     * Gets the value of the jobChainNodeJobChainOrJobChainNodeEnd property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the jobChainNodeJobChainOrJobChainNodeEnd property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJobChainNodeJobChainOrJobChainNodeEnd().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JobChainNodeJobChain }
     * {@link JobChainNodeEnd }
     * 
     * 
     */
    public List<Object> getJobChainNodeJobChainOrJobChainNodeEnd() {
        if (jobChainNodeJobChainOrJobChainNodeEnd == null) {
            jobChainNodeJobChainOrJobChainNodeEnd = new ArrayList<Object>();
        }
        return this.jobChainNodeJobChainOrJobChainNodeEnd;
    }

    /**
     * Gets the value of the fileOrderSource property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fileOrderSource property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFileOrderSource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JobChain.FileOrderSource }
     * 
     * 
     */
    public List<JobChain.FileOrderSource> getFileOrderSource() {
        if (fileOrderSource == null) {
            fileOrderSource = new ArrayList<JobChain.FileOrderSource>();
        }
        return this.fileOrderSource;
    }

    /**
     * Gets the value of the jobChainNodeOrFileOrderSinkOrJobChainNodeEnd property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the jobChainNodeOrFileOrderSinkOrJobChainNodeEnd property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JobChain.JobChainNode }
     * {@link JobChain.FileOrderSink }
     * {@link JobChainNodeEnd }
     * 
     * 
     */
    public List<Object> getJobChainNodeOrFileOrderSinkOrJobChainNodeEnd() {
        if (jobChainNodeOrFileOrderSinkOrJobChainNodeEnd == null) {
            jobChainNodeOrFileOrderSinkOrJobChainNodeEnd = new ArrayList<Object>();
        }
        return this.jobChainNodeOrFileOrderSinkOrJobChainNodeEnd;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the replace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReplace() {
        return replace;
    }

    /**
     * Sets the value of the replace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReplace(String value) {
        this.replace = value;
    }

    /**
     * Gets the value of the visible property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisible() {
        return visible;
    }

    /**
     * Sets the value of the visible property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisible(String value) {
        this.visible = value;
    }

    /**
     * Gets the value of the ordersRecoverable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrdersRecoverable() {
        return ordersRecoverable;
    }

    /**
     * Sets the value of the ordersRecoverable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrdersRecoverable(String value) {
        this.ordersRecoverable = value;
    }

    /**
     * Gets the value of the distributed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistributed() {
        return distributed;
    }

    /**
     * Sets the value of the distributed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistributed(String value) {
        this.distributed = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the maxOrders property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxOrders() {
        return maxOrders;
    }

    /**
     * Sets the value of the maxOrders property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxOrders(BigInteger value) {
        this.maxOrders = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="state" use="required" type="{}String" />
     *       &lt;attribute name="remove" type="{}Yes_no" />
     *       &lt;attribute name="move_to" type="{}File" />
     *       &lt;attribute name="delay" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class FileOrderSink {

        @XmlAttribute(name = "state", required = true)
        protected String state;
        @XmlAttribute(name = "remove")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String remove;
        @XmlAttribute(name = "move_to")
        protected String moveTo;
        @XmlAttribute(name = "delay")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger delay;

        /**
         * Gets the value of the state property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getState() {
            return state;
        }

        /**
         * Sets the value of the state property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setState(String value) {
            this.state = value;
        }

        /**
         * Gets the value of the remove property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRemove() {
            return remove;
        }

        /**
         * Sets the value of the remove property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRemove(String value) {
            this.remove = value;
        }

        /**
         * Gets the value of the moveTo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMoveTo() {
            return moveTo;
        }

        /**
         * Sets the value of the moveTo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMoveTo(String value) {
            this.moveTo = value;
        }

        /**
         * Gets the value of the delay property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getDelay() {
            return delay;
        }

        /**
         * Sets the value of the delay property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setDelay(BigInteger value) {
            this.delay = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="directory" use="required" type="{}String" />
     *       &lt;attribute name="regex" type="{}String" />
     *       &lt;attribute name="delay_after_error" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *       &lt;attribute name="repeat">
     *         &lt;simpleType>
     *           &lt;union>
     *             &lt;simpleType>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
     *                 &lt;enumeration value="no"/>
     *               &lt;/restriction>
     *             &lt;/simpleType>
     *             &lt;simpleType>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
     *               &lt;/restriction>
     *             &lt;/simpleType>
     *           &lt;/union>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="max" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *       &lt;attribute name="next_state" type="{}String" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class FileOrderSource {

        @XmlAttribute(name = "directory", required = true)
        protected String directory;
        @XmlAttribute(name = "regex")
        protected String regex;
        @XmlAttribute(name = "delay_after_error")
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger delayAfterError;
        @XmlAttribute(name = "repeat")
        protected String repeat;
        @XmlAttribute(name = "max")
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger max;
        @XmlAttribute(name = "next_state")
        protected String nextState;

        /**
         * Gets the value of the directory property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDirectory() {
            return directory;
        }

        /**
         * Sets the value of the directory property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDirectory(String value) {
            this.directory = value;
        }

        /**
         * Gets the value of the regex property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRegex() {
            return regex;
        }

        /**
         * Sets the value of the regex property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRegex(String value) {
            this.regex = value;
        }

        /**
         * Gets the value of the delayAfterError property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getDelayAfterError() {
            return delayAfterError;
        }

        /**
         * Sets the value of the delayAfterError property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setDelayAfterError(BigInteger value) {
            this.delayAfterError = value;
        }

        /**
         * Gets the value of the repeat property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRepeat() {
            return repeat;
        }

        /**
         * Sets the value of the repeat property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRepeat(String value) {
            this.repeat = value;
        }

        /**
         * Gets the value of the max property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getMax() {
            return max;
        }

        /**
         * Sets the value of the max property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setMax(BigInteger value) {
            this.max = value;
        }

        /**
         * Gets the value of the nextState property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNextState() {
            return nextState;
        }

        /**
         * Sets the value of the nextState property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNextState(String value) {
            this.nextState = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="state" use="required" type="{}String" />
     *       &lt;attribute name="job" type="{}Path" />
     *       &lt;attribute name="next_state" type="{}String" />
     *       &lt;attribute name="error_state" type="{}String" />
     *       &lt;attribute name="on_error">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
     *             &lt;enumeration value="setback"/>
     *             &lt;enumeration value="suspend"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="suspend" type="{}Yes_no" />
     *       &lt;attribute name="delay" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class JobChainNode {

        @XmlAttribute(name = "state", required = true)
        protected String state;
        @XmlAttribute(name = "job")
        protected String job;
        @XmlAttribute(name = "next_state")
        protected String nextState;
        @XmlAttribute(name = "error_state")
        protected String errorState;
        @XmlAttribute(name = "on_error")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String onError;
        @XmlAttribute(name = "suspend")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String suspend;
        @XmlAttribute(name = "delay")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger delay;

        /**
         * Gets the value of the state property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getState() {
            return state;
        }

        /**
         * Sets the value of the state property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setState(String value) {
            this.state = value;
        }

        /**
         * Gets the value of the job property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getJob() {
            return job;
        }

        /**
         * Sets the value of the job property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setJob(String value) {
            this.job = value;
        }

        /**
         * Gets the value of the nextState property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNextState() {
            return nextState;
        }

        /**
         * Sets the value of the nextState property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNextState(String value) {
            this.nextState = value;
        }

        /**
         * Gets the value of the errorState property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getErrorState() {
            return errorState;
        }

        /**
         * Sets the value of the errorState property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setErrorState(String value) {
            this.errorState = value;
        }

        /**
         * Gets the value of the onError property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOnError() {
            return onError;
        }

        /**
         * Sets the value of the onError property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOnError(String value) {
            this.onError = value;
        }

        /**
         * Gets the value of the suspend property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSuspend() {
            return suspend;
        }

        /**
         * Sets the value of the suspend property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSuspend(String value) {
            this.suspend = value;
        }

        /**
         * Gets the value of the delay property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getDelay() {
            return delay;
        }

        /**
         * Sets the value of the delay property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setDelay(BigInteger value) {
            this.delay = value;
        }

    }

}
