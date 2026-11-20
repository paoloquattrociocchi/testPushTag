package it.giustizia.smp.service;

import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.form.RiunioneForm;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

public interface RiunioneService {

	public KeyMessageDto controlloRiunione(final long idProcedimentoRiunente, final long idProcedimentoRiunito, final String codiceUtente, final int idUfficio, final String nomeMetodo);
	
	public KeyMessageDto esecuzioneRiunione(String funzione,long idAtto, final long idProcedimentoRiunente, final long idProcedimentoRiunito, final String codiceUtente, final int idUfficio, final String nomeMetodo, final long magistrato, final Date dataRiunione, final RiunioneForm form);
	
	public boolean documentaleRiunione(long idProcedimentoOrigine, long idProcedimentoDestinazione, ArrayList<BigInteger> documenti);
}
