package it.giustizia.smp.dao;

import it.giustizia.smp.model.TAnMenu;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.model.TUtente;

import java.util.ArrayList;

public interface HomeDao {

    public ArrayList<TAnMenu> getMenuUtente(TUtente utente, long idTipoUfficio);

    public TTpTipoUfficio getTTipoUfficio(String descTipoUfficio);

}
