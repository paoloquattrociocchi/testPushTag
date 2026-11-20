package it.giustizia.smp.webservice.client.allineamentoFascicoliPDoc;

import java.rmi.ConnectException;

import it.giustizia.smp.model.TAnUtenzePdoc;
import it.giustizia.smp.webservice.client.allineamentoFascicoliPDoc.model.general.recuperoFascicoloResponse.Risposta;

public interface IAllineamentoFascicoliPDocClient {

	Risposta recuperaFascicolo(String codiceFascicolo, TAnUtenzePdoc utenzaPdoc) throws ConnectException;

}