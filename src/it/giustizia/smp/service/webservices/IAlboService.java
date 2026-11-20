package it.giustizia.smp.service.webservices;

import java.util.List;

import it.giustizia.smp.form.AmministratoreAlboForm;


public interface IAlboService {
    
    public List<AmministratoreAlboForm> elenco(AmministratoreAlboForm amministratoreForm);
    
}
