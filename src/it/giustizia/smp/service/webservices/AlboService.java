package it.giustizia.smp.service.webservices;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import it.giustizia.smp.form.AmministratoreAlboForm;
import it.giustizia.smp.webservice.client.albo.Amministratore;
import it.giustizia.smp.webservice.client.albo.ElencoRequest;
import it.giustizia.smp.webservice.client.albo.ElencoResponse;
import it.giustizia.smp.webservice.client.albo.AlboWSService;


public class AlboService implements IAlboService {
    
    @Autowired
    private Mapper mapperBean; 
    
    @Autowired
    private AlboWSService alboServiceClient;
   
   
    public List<AmministratoreAlboForm> elenco(AmministratoreAlboForm amministratoreForm) {
    	List<AmministratoreAlboForm> listaReturn = new LinkedList<AmministratoreAlboForm>();
    	ElencoRequest request = new ElencoRequest();
    	mapperBean.map(amministratoreForm, request);
    	ElencoResponse response = alboServiceClient.elenco(request);
    	List<Amministratore> listaAmministratori = response.getAmministratore();
    	Iterator itr = listaAmministratori.iterator();
        while(itr.hasNext()) {
        	Amministratore element = (Amministratore)itr.next();
        	AmministratoreAlboForm currentForm = new AmministratoreAlboForm();
            mapperBean.map(element, currentForm);
            listaReturn.add(currentForm);
        }
    	
        return listaReturn;
    }


   
}
