package it.giustizia.smp.dao;

import it.giustizia.smp.model.TAnMenu;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.model.TUtente;

import java.util.ArrayList;

public interface LoginDao {

    public ArrayList<TAnMenu> getMenuUtente(TUtente utente, long idTipoUfficio);

    public TTpTipoUfficio getTTipoUfficio(String descTipoUfficio);

    public TAnUfficio getUfficioByTipoUfficioAndCitta(TTpTipoUfficio u, String cittaUfficio);

    public TUtente loginUtente(String userid, String password);
}
