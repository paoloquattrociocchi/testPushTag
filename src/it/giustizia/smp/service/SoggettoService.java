package it.giustizia.smp.service;


import java.util.List;

import it.giustizia.smp.dto.pg_procedimento.MisurePersSoggDto;
import it.giustizia.smp.dto.pg_soggetto.BeniCollegatiTerzoDto;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.SpeseSoggettoForm;
import it.giustizia.smp.model.TRelProcSogSog;
import it.giustizia.smp.model.TRelProcSogint;


/**
 * SoggettoService
 * 
 * @author Almaviva S.p.A.
 *         Interfaccia Soggetto Service 
 */
public interface SoggettoService {

	/**
	 * caricaValoriDettaglioSoggFisVerCopertinaAtto
	 * @param soggetto
	 * @return
	 */
	public SoggettoForm caricaValoriDettaglioSoggFisVerCopertinaAtto(SoggettoForm soggetto);    

	/**
	 * caricaValoriDettaglioSoggGiuVerCopertinaAtto
	 * @param soggetto
	 * @return
	 */
	public SoggettoForm caricaValoriDettaglioSoggGiuVerCopertinaAtto(SoggettoForm soggetto);   
	
	public List<MisurePersSoggDto> misurePersonaliSoggetto(long idSoggetto, int idUffOwn, long idProcedimento);

	public List<MisurePersSoggDto> cauzioniSoggetto(long idSoggetto, int idUffOwn, long idProcedimento);

	public List<MisurePersSoggDto> art67Soggetto(long idSoggetto, int idUffOwn, long idProcedimento);
	
	/**
	 * getListaSoggCondannati
	 * @param idUfficio
	 * @param idProcedimento
	 * @param idAtto
	 * @param flagSpese
	 * @param flagCassa
	 * @return
	 */
	public List<SoggettoForm> getListaSoggCondannati(long idUfficio,long idProcedimento,long idAtto,String flagSpese,String flagCassa);
	
	public TRelProcSogint dettaglioRelSogInt(long idSoggetto, int idUffOwn, long idProcedimento);
	
	public List<BeniCollegatiTerzoDto> beniCollegatiTerzo(long idUfficio, long idProcedimento, long idSoggTit);	
	
	public Long getIdSoggettoAnnullato(long idSoggetto, int idUffOwn, long idProcedimento);

	public SoggettoForm getSoggettoIntestatario(long idSoggetto, int idUffOwner);
	
	public String flagDecretoSequestro(SoggettoForm soggetto);
	
	//mev speseprocessulai	
	
	public void salvaSpeseProcessuali(SpeseSoggettoForm speseSoggettoForm, long idAtto, long idProcedimento, String codUtente, long idUfficio, long idTipoUff, String idSpeseP);

	public void salvaSpeseProcessuali(SpeseSoggettoForm speseSoggettoForm, long idCurrentAtto, long idProcedimento,
				String idCodiceUtente, int idUffOwn, int idTipoUfficio);
	
	public void cancellaSpeseProcessuali(SpeseSoggettoForm speseSoggettoForm, long idCurrentAtto, long idProcedimento,
			String idCodiceUtente, int idUffOwn, String cancella);
	//fine Mev SpeseProcessuali
}
