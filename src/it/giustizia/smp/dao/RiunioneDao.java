package it.giustizia.smp.dao;

import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.model.TAtto;

import java.util.Date;

/**
 * @author massimo.demattia
 *
 */

public interface RiunioneDao {

	public KeyMessageDto controlloRiunione(final long idProcedimentoRiunente, final long idProcedimentoRiunito, final String codiceUtente, final int idUfficio, final String nomeMetodo);
	
	public KeyMessageDto esecuzioneRiunione(final String funzione, final long idAtto, final long idProcedimentoRiunente, final long idProcedimentoRiunito, final String codiceUtente, final int idUfficio, final String nomeMetodo, final long magistrato, final Date dataRiunione, final TAtto atto);
}
