package it.giustizia.smp.service;

import java.util.ArrayList;

import it.giustizia.smp.model.TAnMenu;
import it.giustizia.smp.model.TUtente;

public interface HomeService {

	public ArrayList<TAnMenu> getMenuUtente(TUtente utente, long idTipoUfficio);

}
