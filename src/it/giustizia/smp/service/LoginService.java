package it.giustizia.smp.service;

import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.model.TAnMenu;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TUtente;

import java.util.ArrayList;

public interface LoginService {

	public ArrayList<TAnMenu> getMenuUtente(TUtente utente, long idTipoUfficio);

	public TTpTipoUfficio getTTipoUfficio(String descTipoUfficio);

	public TAnUfficio getUfficioByTipoUfficioAndCitta(TTpTipoUfficio u, String cittaUfficio);

	public boolean isOfficeTypeTribunale(TTpTipoUfficio tipoUfficio);

	//public TUtente loginUtente(String userid, String password);

	/**
	 * @param profilo
	 *            è una stringa rappresentativa del profilo di un utente (formato: distretto|citta|ufficio|ruolo)
	 * @return L'oggetto TUtente corrispondente ai dati della stringa in input.
	 */
	public TUtente getUtenteByProfilo(String profiloString, ProfiloForm profilo);
}
