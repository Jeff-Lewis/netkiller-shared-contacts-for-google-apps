//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.3-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.03.14 at 01:16:23 PM GMT+05:30 
//


package com.metacube.ipathshala.core.jaxb.set;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{}set-name"/>
 *         &lt;element ref="{}parent-set-name"/>
 *         &lt;element ref="{}set-order"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "setName",
    "parentSetName",
    "setOrder"
})
@XmlRootElement(name = "setElement")
public class SetElement
    implements Serializable
{

    private final static long serialVersionUID = 12343L;
    @XmlElement(name = "set-name", required = true)
    protected String setName;
    @XmlElement(name = "parent-set-name", required = true)
    protected String parentSetName;
    @XmlElement(name = "set-order", required = true)
    protected String setOrder;

    /**
     * Gets the value of the setName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSetName() {
        return setName;
    }

    /**
     * Sets the value of the setName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSetName(String value) {
        this.setName = value;
    }

    /**
     * Gets the value of the parentSetName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentSetName() {
        return parentSetName;
    }

    /**
     * Sets the value of the parentSetName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentSetName(String value) {
        this.parentSetName = value;
    }

    /**
     * Gets the value of the setOrder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSetOrder() {
        return setOrder;
    }

    /**
     * Sets the value of the setOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSetOrder(String value) {
        this.setOrder = value;
    }

}
