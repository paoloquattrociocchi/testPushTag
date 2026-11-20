/**
 * IndirizzoFormBeneBDCType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.giustizia.smp.webservice.client.bdc.ricerca.bene;

public class IndirizzoFormBeneBDC  implements java.io.Serializable {
    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC toponimo;

    private java.lang.String denominazione;

    private java.lang.String civico;

    private java.lang.String cap;

    public IndirizzoFormBeneBDC() {
    }

    public IndirizzoFormBeneBDC(
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC toponimo,
           java.lang.String denominazione,
           java.lang.String civico,
           java.lang.String cap) {
           this.toponimo = toponimo;
           this.denominazione = denominazione;
           this.civico = civico;
           this.cap = cap;
    }


    /**
     * Gets the toponimo value for this IndirizzoFormBeneBDCType.
     * 
     * @return toponimo
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC getToponimo() {
        return toponimo;
    }


    /**
     * Sets the toponimo value for this IndirizzoFormBeneBDCType.
     * 
     * @param toponimo
     */
    public void setToponimo(it.giustizia.smp.webservice.client.bdc.ricerca.bene.ComboFormBeneBDC toponimo) {
        this.toponimo = toponimo;
    }


    /**
     * Gets the denominazione value for this IndirizzoFormBeneBDCType.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this IndirizzoFormBeneBDCType.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the civico value for this IndirizzoFormBeneBDCType.
     * 
     * @return civico
     */
    public java.lang.String getCivico() {
        return civico;
    }


    /**
     * Sets the civico value for this IndirizzoFormBeneBDCType.
     * 
     * @param civico
     */
    public void setCivico(java.lang.String civico) {
        this.civico = civico;
    }


    /**
     * Gets the cap value for this IndirizzoFormBeneBDCType.
     * 
     * @return cap
     */
    public java.lang.String getCap() {
        return cap;
    }


    /**
     * Sets the cap value for this IndirizzoFormBeneBDCType.
     * 
     * @param cap
     */
    public void setCap(java.lang.String cap) {
        this.cap = cap;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IndirizzoFormBeneBDC)) return false;
        IndirizzoFormBeneBDC other = (IndirizzoFormBeneBDC) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.toponimo==null && other.getToponimo()==null) || 
             (this.toponimo!=null &&
              this.toponimo.equals(other.getToponimo()))) &&
            ((this.denominazione==null && other.getDenominazione()==null) || 
             (this.denominazione!=null &&
              this.denominazione.equals(other.getDenominazione()))) &&
            ((this.civico==null && other.getCivico()==null) || 
             (this.civico!=null &&
              this.civico.equals(other.getCivico()))) &&
            ((this.cap==null && other.getCap()==null) || 
             (this.cap!=null &&
              this.cap.equals(other.getCap())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getToponimo() != null) {
            _hashCode += getToponimo().hashCode();
        }
        if (getDenominazione() != null) {
            _hashCode += getDenominazione().hashCode();
        }
        if (getCivico() != null) {
            _hashCode += getCivico().hashCode();
        }
        if (getCap() != null) {
            _hashCode += getCap().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IndirizzoFormBeneBDC.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "indirizzoFormBeneBDCType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("toponimo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "toponimo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "comboFormBeneBDCType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("civico");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "civico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cap");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "cap"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
