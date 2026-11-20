package it.giustizia.smp.service.webservices;

import java.util.LinkedList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.service.BeneService;
import it.giustizia.smp.webservice.client.bdc.ricerca.bene.BeneBDC;
import it.giustizia.smp.webservice.client.bdc.ricerca.bene.InputDettaglioBeneBDC;
import it.giustizia.smp.webservice.client.bdc.ricerca.bene.InputRicercaBeneBDC;
import it.giustizia.smp.webservice.client.bdc.ricerca.bene.RicercaBeneBDCClient;


public class BDCService implements IBDCService {
    
    @Autowired
    private Mapper mapperBean; 
    
    @Autowired
    private RicercaBeneBDCClient ricercaBeneBDCClient;
    
    @Autowired
    private BeneService beneService;

    public List<BeneForm> ricercaBeniBDC(BeneForm formRicerca, Long idUfficioUtente, Long idTipoUfficioUtente, Long idProcedimento) {
        List<BeneForm> l = new LinkedList<BeneForm>();
        InputRicercaBeneBDC inputRicercaBeneBDC = new InputRicercaBeneBDC();
        inputRicercaBeneBDC.setIdUfficio(idUfficioUtente);
        inputRicercaBeneBDC.setIdTipoUfficio(idTipoUfficioUtente);
        mapperBean.map(formRicerca, inputRicercaBeneBDC);      
        BeneBDC[] data = ricercaBeneBDCClient.ricercaBeniBDC(inputRicercaBeneBDC);
        List<BeneForm> beniCurrentProcedimento = beneService.listaBeniPerProcedimento(0, idUfficioUtente.intValue(), idProcedimento, new BeneForm());
        // scorri tutti i beni tornati dal web service ma escludi tutti quelli che già fanno parte del procedimento corrente
        for(int i=0;i<data.length;i++) {
            BeneBDC current = data[i];
            BeneForm tmpBeneForm = new BeneForm();
            tmpBeneForm.setIdBene(current.getIdBene());
            if( !beniCurrentProcedimento.contains(tmpBeneForm) ) {
                BeneForm currentForm = new BeneForm();
                mapperBean.map(current, currentForm);
                currentForm.setBeneFromBDC(true);
                l.add( currentForm );
            }
        }
        return l;
    }

    public BeneForm dettaglioBeneBDC(BeneForm formRicerca) {
        InputDettaglioBeneBDC input = new InputDettaglioBeneBDC();     
        input.setIdBene(formRicerca.getIdBene());
        input.setVersioneBene(formRicerca.getVersioneBene());      
        BeneBDC beneFromBDC = ricercaBeneBDCClient.dettaglioBeneBDC(input);
        BeneForm beneForm = new BeneForm();
        mapperBean.map(beneFromBDC, beneForm);
        beneForm.setBeneFromBDC(true);
        return beneForm;
    }
}
