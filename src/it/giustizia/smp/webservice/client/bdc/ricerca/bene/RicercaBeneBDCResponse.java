/**
 * RicercaBeneBDCResponseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.giustizia.smp.webservice.client.bdc.ricerca.bene;

public class RicercaBeneBDCResponse  implements java.io.Serializable {
    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.BeneBDC[] outputRicercaBeneBDC;

    public RicercaBeneBDCResponse() {
    }

    public RicercaBeneBDCResponse(
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.BeneBDC[] outputRicercaBeneBDC) {
           this.outputRicercaBeneBDC = outputRicercaBeneBDC;
    }


    /**
     * Gets the outputRicercaBeneBDC value for this RicercaBeneBDCResponseType.
     * 
     * @return outputRicercaBeneBDC
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.BeneBDC[] getOutputRicercaBeneBDC() {
        return outputRicercaBeneBDC;
    }


    /**
     * Sets the outputRicercaBeneBDC value for this RicercaBeneBDCResponseType.
     * 
     * @param outputRicercaBeneBDC
     */
    public void setOutputRicercaBeneBDC(it.giustizia.smp.webservice.client.bdc.ricerca.bene.BeneBDC[] outputRicercaBeneBDC) {
        this.outputRicercaBeneBDC = outputRicercaBeneBDC;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaBeneBDCResponse)) return false;
        RicercaBeneBDCResponse other = (RicercaBeneBDCResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.outputRicercaBeneBDC==null && other.getOutputRicercaBeneBDC()==null) || 
             (this.outputRicercaBeneBDC!=null &&
              java.util.Arrays.equals(this.outputRicercaBeneBDC, other.getOutputRicercaBeneBDC())));
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
        if (getOutputRicercaBeneBDC() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOutputRicercaBeneBDC());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOutputRicercaBeneBDC(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RicercaBeneBDCResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "ricercaBeneBDCResponseType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outputRicercaBeneBDC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "outputRicercaBeneBDC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "outputRicercaBeneBDCType"));
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
