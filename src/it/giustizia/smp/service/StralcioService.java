package it.giustizia.smp.service;

import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.form.SoggettoStralcioForm;
import it.giustizia.smp.form.StralcioForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface StralcioService {

	public List<SoggettoStralcioForm> ricercaStralcio(long idProcedimento, long idUffOwner, StralcioForm stralcioForm, String flagValido);

	public void impostaStralcioMisPatr(long idProcedimento, long idUffOwner, boolean checked, long idSoggInt, long idBene, String idSoggTit, final int tipoBene, final int catBene, final int sottoCatBene, final String codiceUtente);
	
	public void impostaStralcioMisPers(long idProcedimento, long idUffOwner, boolean checked, long idSoggInt, final String codiceUtente);
	
	public void impostaStralcioSoggetto(long idProcedimento, long idUffOwner, boolean checked, long idSoggInt, final String idsSoggTit,final String idsMisure, final int tipoBene, final int catBene, final int sottoCatBene, final String codiceUtente);
	
	public KeyMessageDto controlloStralcio(long idProcedimento);
	
	public KeyMessageDto esecuzioneStralcio(String funzione,long idAtto,long idProcedimento, String codiceUtente, Date dataStralcio, long magistrato, StralcioForm stralcioForm);
	
	public boolean documentaleStralcio(long idProcedimentoOrigine, long idProcedimentoDestinazione, ArrayList<it.giustizia.smp.webservice.client.documentale.model.general.stralcio.Documento> documenti);
	
	public KeyMessageDto copiaSpostaAttoGenerico(final long idProcedimento, final long idProcedimentoDestinatario, final long idAtto, final String operazione, final String codiceUtente, final long idUffOwn, final String nomeMetodo);
}
