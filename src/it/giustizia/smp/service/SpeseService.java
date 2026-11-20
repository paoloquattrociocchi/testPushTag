package it.giustizia.smp.service;


import it.giustizia.smp.form.SpeseSoggettoForm;


/**
 * SpeseService
 * 
 * @author A
 *         Interfaccia Spese Service
 */
public interface SpeseService {
    
    public void salvaSpeseProcessuali(SpeseSoggettoForm speseSoggettoForm, long idAtto, long idProcedimento, String codUtente, long idUfficio, long idTipoUff, String idSpeseP);

	public void salvaSpeseProcessuali(SpeseSoggettoForm speseSoggettoForm, long idCurrentAtto, long idProcedimento,
			String idCodiceUtente, int idUffOwn, int idTipoUfficio);

   
}
