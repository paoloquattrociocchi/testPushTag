/**
 * InputDettaglioBeneBDCType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.giustizia.smp.webservice.client.bdc.ricerca.bene;

public class InputDettaglioBeneBDC  implements java.io.Serializable {
    private long idBene;

    private long versioneBene;

    public InputDettaglioBeneBDC() {
    }

    public InputDettaglioBeneBDC(
           long idBene,
           long versioneBene) {
           this.idBene = idBene;
           this.versioneBene = versioneBene;
    }


    /**
     * Gets the idBene value for this InputDettaglioBeneBDCType.
     * 
     * @return idBene
     */
    public long getIdBene() {
        return idBene;
    }


    /**
     * Sets the idBene value for this InputDettaglioBeneBDCType.
     * 
     * @param idBene
     */
    public void setIdBene(long idBene) {
        this.idBene = idBene;
    }


    /**
     * Gets the versioneBene value for this InputDettaglioBeneBDCType.
     * 
     * @return versioneBene
     */
    public long getVersioneBene() {
        return versioneBene;
    }


    /**
     * Sets the versioneBene value for this InputDettaglioBeneBDCType.
     * 
     * @param versioneBene
     */
    public void setVersioneBene(long versioneBene) {
        this.versioneBene = versioneBene;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InputDettaglioBeneBDC)) return false;
        InputDettaglioBeneBDC other = (InputDettaglioBeneBDC) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.idBene == other.getIdBene() &&
            this.versioneBene == other.getVersioneBene();
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
        _hashCode += new Long(getIdBene()).hashCode();
        _hashCode += new Long(getVersioneBene()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InputDettaglioBeneBDC.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "inputDettaglioBeneBDCType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idBene");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "idBene"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("versioneBene");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "versioneBene"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
