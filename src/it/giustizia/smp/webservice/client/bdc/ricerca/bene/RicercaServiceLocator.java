/**
 * RicercaServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.giustizia.smp.webservice.client.bdc.ricerca.bene;

public class RicercaServiceLocator extends org.apache.axis.client.Service implements it.giustizia.smp.webservice.client.bdc.ricerca.bene.RicercaService {

    public RicercaServiceLocator() {
    }


    public RicercaServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public RicercaServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for RicercaSoap11
    //private java.lang.String RicercaSoap11_address = "http://localhost:8080/BDC/ricercaBDC";
    private java.lang.String RicercaSoap11_address;
    
    public java.lang.String getRicercaSoap11Address() {
        return RicercaSoap11_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RicercaSoap11WSDDServiceName = "RicercaSoap11";

    public java.lang.String getRicercaSoap11WSDDServiceName() {
        return RicercaSoap11WSDDServiceName;
    }

    public void setRicercaSoap11WSDDServiceName(java.lang.String name) {
        RicercaSoap11WSDDServiceName = name;
    }

    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.Ricerca getRicercaSoap11() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(RicercaSoap11_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRicercaSoap11(endpoint);
    }

    public it.giustizia.smp.webservice.client.bdc.ricerca.bene.Ricerca getRicercaSoap11(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.giustizia.smp.webservice.client.bdc.ricerca.bene.RicercaSoap11Stub _stub = new it.giustizia.smp.webservice.client.bdc.ricerca.bene.RicercaSoap11Stub(portAddress, this);
            _stub.setPortName(getRicercaSoap11WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRicercaSoap11EndpointAddress(java.lang.String address) {
        RicercaSoap11_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.giustizia.smp.webservice.client.bdc.ricerca.bene.Ricerca.class.isAssignableFrom(serviceEndpointInterface)) {
                it.giustizia.smp.webservice.client.bdc.ricerca.bene.RicercaSoap11Stub _stub = new it.giustizia.smp.webservice.client.bdc.ricerca.bene.RicercaSoap11Stub(new java.net.URL(RicercaSoap11_address), this);
                _stub.setPortName(getRicercaSoap11WSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("RicercaSoap11".equals(inputPortName)) {
            return getRicercaSoap11();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "RicercaService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://bdc.giustizia.it/ws", "RicercaSoap11"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("RicercaSoap11".equals(portName)) {
            setRicercaSoap11EndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
