package it.giustizia.smp.service.webservices;

import java.util.List;

import it.giustizia.smp.webservice.client.reginde.Soggetto;

public interface IRegindeService {

	public Soggetto dettagliSoggettoPerCodice(String codiceFiscale);
	
	public List<Soggetto> ricercaSoggetto(String cognome);
}
