package it.giustizia.smp.webservice.client.bdc.ricerca.bene;

public class RicercaProxy implements it.giustizia.smp.webservice.client.bdc.ricerca.bene.Ricerca {
  private String _endpoint = null;
  private it.giustizia.smp.webservice.client.bdc.ricerca.bene.Ricerca ricerca = null;
  
  public RicercaProxy() {
    _initRicercaProxy();
  }
  
  public RicercaProxy(String endpoint) {
    _endpoint = endpoint;
    _initRicercaProxy();
  }
  
  private void _initRicercaProxy() {
    try {
      ricerca = (new it.giustizia.smp.webservice.client.bdc.ricerca.bene.RicercaServiceLocator()).getRicercaSoap11();
      if (ricerca != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)ricerca)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)ricerca)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (ricerca != null)
      ((javax.xml.rpc.Stub)ricerca)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.giustizia.smp.webservice.client.bdc.ricerca.bene.Ricerca getRicerca() {
    if (ricerca == null)
      _initRicercaProxy();
    return ricerca;
  }
  
  public it.giustizia.smp.webservice.client.bdc.ricerca.bene.RicercaBeneBDCResponse ricercaBeneBDC(it.giustizia.smp.webservice.client.bdc.ricerca.bene.RicercaBeneBDCRequest ricercaBeneBDCRequest) throws java.rmi.RemoteException{
    if (ricerca == null)
      _initRicercaProxy();
    return ricerca.ricercaBeneBDC(ricercaBeneBDCRequest);
  }
  
  public it.giustizia.smp.webservice.client.bdc.ricerca.bene.DettaglioBeneBDCResponse dettaglioBeneBDC(it.giustizia.smp.webservice.client.bdc.ricerca.bene.DettaglioBeneBDCRequest dettaglioBeneBDCRequest) throws java.rmi.RemoteException{
    if (ricerca == null)
      _initRicercaProxy();
    return ricerca.dettaglioBeneBDC(dettaglioBeneBDCRequest);
  }
  
  
}