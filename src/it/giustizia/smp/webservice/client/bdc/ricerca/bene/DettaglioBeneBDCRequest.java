/**
 * DettaglioBeneBDCRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.giustizia.smp.webservice.client.bdc.ricerca.bene;

public class DettaglioBeneBDCRequest  implements java.io.Serializable {
    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.InputDettaglioBeneBDC inputDettaglioBeneBDC;

    public DettaglioBeneBDCRequest() {
    }

    public DettaglioBeneBDCRequest(
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.InputDettaglioBeneBDC inputDettaglioBeneBDC) {
           this.inputDettaglioBeneBDC = inputDettaglioBeneBDC;
    }


    /**
     * Gets the inputDettaglioBeneBDC value for this DettaglioBeneBDCRequestType.
     * 
     * @return inputDettaglioBeneBDC
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.InputDettaglioBeneBDC getInputDettaglioBeneBDC() {
        return inputDettaglioBeneBDC;
    }


    /**
     * Sets the inputDettaglioBeneBDC value for this DettaglioBeneBDCRequestType.
     * 
     * @param inputDettaglioBeneBDC
     */
    public void setInputDettaglioBeneBDC(it.giustizia.smp.webservice.client.bdc.ricerca.bene.InputDettaglioBeneBDC inputDettaglioBeneBDC) {
        this.inputDettaglioBeneBDC = inputDettaglioBeneBDC;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DettaglioBeneBDCRequest)) return false;
        DettaglioBeneBDCRequest other = (DettaglioBeneBDCRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.inputDettaglioBeneBDC==null && other.getInputDettaglioBeneBDC()==null) || 
             (this.inputDettaglioBeneBDC!=null &&
              this.inputDettaglioBeneBDC.equals(other.getInputDettaglioBeneBDC())));
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
        if (getInputDettaglioBeneBDC() != null) {
            _hashCode += getInputDettaglioBeneBDC().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DettaglioBeneBDCRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "dettaglioBeneBDCRequestType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inputDettaglioBeneBDC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "inputDettaglioBeneBDC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "inputDettaglioBeneBDCType"));
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
