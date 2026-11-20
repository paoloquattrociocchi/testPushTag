package it.giustizia.smp.dao;

import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.dto.pg_stralcio.RicercaStralcioDto;
import it.giustizia.smp.model.TAtto;

import java.util.Date;
import java.util.List;

/**
 * @author massimo.demattia
 *
 */

public interface StralcioDao {

	public List<RicercaStralcioDto> ricercaStralcio(final long idProcedimento, final long idUffOwn, final String idsMisure, final String idsSoggInt, final String idsSoggTit, final Integer idTipoBene, final Integer idCatBene, final Integer idSottoCatBene, final String flagValido);

	public Character impostaStralcioMisPatr(final long idProcedimento, final long idUffOwner, final boolean checkedSoggValido, final long idSoggInt, final long idBene, final String idSoggTit, final int tipoBene, final int catBene, final int sottoCatBene, final String codiceUtente);
	
	public Character impostaStralcioMisPers(final long idProcedimento, final long idUffOwner, final boolean checkedSoggValido, final long idSoggInt, final String codiceUtente);
	
	public Character impostaStralcioSoggetto(final long idProcedimento, final long idUffOwner, final boolean checkedSoggValido, final long idSoggInt, final String idsSoggTit, final String idsMisure, final int tipoBene, final int catBene, final int sottoCatBene, final String codiceUtente);
	
	public KeyMessageDto controlloStralcio(final long idProcedimento);
	
	public KeyMessageDto esecuzioneStralcio(final String funzione,final long idAtto,final long idProcedimento, final String descUtente, final Date dataStralcio, final long magistrato, final TAtto atto);
	
	public KeyMessageDto copiaSpostaAttoGenerico(final long idProcedimento, final long idProcedimentoDestinatario, final long idAtto, final String operazione, final String codiceUtente, final long idUffOwn, final String nomeMetodo);
}
