package it.giustizia.smp.service.webservices;

import java.util.List;

import it.giustizia.smp.form.BeneForm;


public interface IBDCService {
    
    public List<BeneForm> ricercaBeniBDC(BeneForm formRicerca, Long idUfficioUtente, Long idTipoUfficioUtente, Long idProcedimento); 
    
    public BeneForm dettaglioBeneBDC(BeneForm formRicerca);
}
