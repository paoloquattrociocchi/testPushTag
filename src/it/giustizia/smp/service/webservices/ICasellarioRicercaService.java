package it.giustizia.smp.service.webservices;

import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.model.TUtente;
import it.giustizia.smp.service.webservices.CasellarioRicercaService.RispostaRicerca;

public interface ICasellarioRicercaService {

	public RispostaRicerca ricerca(final String descUtente, final TUtente tUtente, final long idUffOwn, AttoForm attoForm,ProcedimentoForm procedimentoForm,String idNSC,String tipoCertificato,String authUsername,String authPassword);
	
		
	
}