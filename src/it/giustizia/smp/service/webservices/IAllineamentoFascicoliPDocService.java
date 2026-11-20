package it.giustizia.smp.service.webservices;

import it.giustizia.smp.webservice.client.allineamentoFascicoliPDoc.model.general.recuperoFascicoloResponse.Risposta;

public interface IAllineamentoFascicoliPDocService {

	Risposta recuperaFascicolo(String codiceFascicolo, int idUffOwner);

}