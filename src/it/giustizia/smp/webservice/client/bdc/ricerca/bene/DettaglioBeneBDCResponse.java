/**
 * DettaglioBeneBDCResponseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.giustizia.smp.webservice.client.bdc.ricerca.bene;

public class DettaglioBeneBDCResponse  implements java.io.Serializable {
    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.OutputDettaglioBeneBDC outputDettaglioBeneBDC;

    public DettaglioBeneBDCResponse() {
    }

    public DettaglioBeneBDCResponse(
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.OutputDettaglioBeneBDC outputDettaglioBeneBDC) {
           this.outputDettaglioBeneBDC = outputDettaglioBeneBDC;
    }


    /**
     * Gets the outputDettaglioBeneBDC value for this DettaglioBeneBDCResponseType.
     * 
     * @return outputDettaglioBeneBDC
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.OutputDettaglioBeneBDC getOutputDettaglioBeneBDC() {
        return outputDettaglioBeneBDC;
    }


    /**
     * Sets the outputDettaglioBeneBDC value for this DettaglioBeneBDCResponseType.
     * 
     * @param outputDettaglioBeneBDC
     */
    public void setOutputDettaglioBeneBDC(it.giustizia.smp.webservice.client.bdc.ricerca.bene.OutputDettaglioBeneBDC outputDettaglioBeneBDC) {
        this.outputDettaglioBeneBDC = outputDettaglioBeneBDC;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DettaglioBeneBDCResponse)) return false;
        DettaglioBeneBDCResponse other = (DettaglioBeneBDCResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.outputDettaglioBeneBDC==null && other.getOutputDettaglioBeneBDC()==null) || 
             (this.outputDettaglioBeneBDC!=null &&
              this.outputDettaglioBeneBDC.equals(other.getOutputDettaglioBeneBDC())));
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
        if (getOutputDettaglioBeneBDC() != null) {
            _hashCode += getOutputDettaglioBeneBDC().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DettaglioBeneBDCResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "dettaglioBeneBDCResponseType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outputDettaglioBeneBDC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "outputDettaglioBeneBDC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "outputDettaglioBeneBDCType"));
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
