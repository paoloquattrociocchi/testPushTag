/**
 * OutputDettaglioBeneBDCType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.giustizia.smp.webservice.client.bdc.ricerca.bene;

public class OutputDettaglioBeneBDC  implements java.io.Serializable {
    private it.giustizia.smp.webservice.client.bdc.ricerca.bene.BeneBDC bene;

    public OutputDettaglioBeneBDC() {
    }

    public OutputDettaglioBeneBDC(
           it.giustizia.smp.webservice.client.bdc.ricerca.bene.BeneBDC bene) {
           this.bene = bene;
    }


    /**
     * Gets the bene value for this OutputDettaglioBeneBDCType.
     * 
     * @return bene
     */
    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.BeneBDC getBene() {
        return bene;
    }


    /**
     * Sets the bene value for this OutputDettaglioBeneBDCType.
     * 
     * @param bene
     */
    public void setBene(it.giustizia.smp.webservice.client.bdc.ricerca.bene.BeneBDC bene) {
        this.bene = bene;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OutputDettaglioBeneBDC)) return false;
        OutputDettaglioBeneBDC other = (OutputDettaglioBeneBDC) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bene==null && other.getBene()==null) || 
             (this.bene!=null &&
              this.bene.equals(other.getBene())));
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
        if (getBene() != null) {
            _hashCode += getBene().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OutputDettaglioBeneBDC.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "outputDettaglioBeneBDCType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bene");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "bene"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "beneBDCType"));
        elemField.setMinOccurs(0);
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
