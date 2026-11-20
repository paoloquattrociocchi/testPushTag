/**
 * RicercaBeneBDCRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.giustizia.smp.webservice.client.bdc.ricerca.bene;

public class RicercaBeneBDCRequest  implements java.io.Serializable {
    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.InputRicercaBeneBDC inputRicercaBeneBDC;

    public RicercaBeneBDCRequest() {
    }

    public RicercaBeneBDCRequest(
            it.giustizia.smp.webservice.client.bdc.ricerca.bene.InputRicercaBeneBDC inputRicercaBeneBDC) {
           this.inputRicercaBeneBDC = inputRicercaBeneBDC;
    }


    /**
     * Gets the inputRicercaBeneBDC value for this RicercaBeneBDCRequestType.
     * 
     * @return inputRicercaBeneBDC
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.InputRicercaBeneBDC getInputRicercaBeneBDC() {
        return inputRicercaBeneBDC;
    }


    /**
     * Sets the inputRicercaBeneBDC value for this RicercaBeneBDCRequestType.
     * 
     * @param inputRicercaBeneBDC
     */
    public void setInputRicercaBeneBDC(it.giustizia.smp.webservice.client.bdc.ricerca.bene.InputRicercaBeneBDC inputRicercaBeneBDC) {
        this.inputRicercaBeneBDC = inputRicercaBeneBDC;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaBeneBDCRequest)) return false;
        RicercaBeneBDCRequest other = (RicercaBeneBDCRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.inputRicercaBeneBDC==null && other.getInputRicercaBeneBDC()==null) || 
             (this.inputRicercaBeneBDC!=null &&
              this.inputRicercaBeneBDC.equals(other.getInputRicercaBeneBDC())));
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
        if (getInputRicercaBeneBDC() != null) {
            _hashCode += getInputRicercaBeneBDC().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RicercaBeneBDCRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "ricercaBeneBDCRequestType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inputRicercaBeneBDC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "inputRicercaBeneBDC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "inputRicercaBeneBDCType"));
        elemField.setNillable(false);
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
