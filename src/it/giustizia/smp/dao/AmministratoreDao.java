package it.giustizia.smp.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.giustizia.smp.model.TRelProcAttoAmm;
import it.giustizia.smp.model.TRelProcBeneAttoAmm;

public interface AmministratoreDao {

	public void copiaAmministratori(int idUfficio, long idProcedimento, long idAtto, String codiceUtente, String nomeMetodo);
	public Object aggiornaRecords();
	public Object aggiornaRecords(ArrayList<TRelProcAttoAmm> listaRecordDaCancellare);
	public void copiaAmministratoriIIGrado(int idUfficio, long idProcedimento, long idAtto, String codiceUtente, String nomeMetodo);
	public Object updateBeneAmministrato(final ArrayList<Long> elencoFascicoli, final Long idBene , final Date dataFineIncarico );
	//public Object updateAmministratoriCopertina(final ArrayList<Long> elencoFascicoli, final Date dataFineIncarico );	
}