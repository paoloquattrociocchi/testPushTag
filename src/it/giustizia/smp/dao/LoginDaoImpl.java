package it.giustizia.smp.dao;

import it.giustizia.smp.model.TAnMenu;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TAnMenuProfilo;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.model.TUtente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class LoginDaoImpl extends HibernateDaoSupport implements LoginDao {

    public ArrayList<TAnMenu> getMenuUtente(TUtente utente, long idTipoUfficio) {

        ArrayList<TAnMenu> menu = new ArrayList<TAnMenu>();
        DetachedCriteria criteria = DetachedCriteria.forClass(TAnMenuProfilo.class);
        criteria.createAlias("TTpProfilo", "prof");
        if(utente.getTTpProfilo().getDescProfilo().equals("CANCELLIERE") && idTipoUfficio == 6)
        	criteria.add(Restrictions.eq("prof.descProfilo", "CANCELLIERE_CDA")).addOrder(Order.asc("id"));
        else if(utente.getTTpProfilo().getDescProfilo().equals("SEGRETARIO") && idTipoUfficio == 8)
        	criteria.add(Restrictions.eq("prof.descProfilo", "SEGRETARIO_PG")).addOrder(Order.asc("id"));
        else
        	criteria.add(Restrictions.eq("prof.descProfilo", utente.getTTpProfilo().getDescProfilo())).addOrder(Order.asc("id"));
        List<TAnMenuProfilo> menuProfilo = getHibernateTemplate().findByCriteria(criteria);
        if (!menuProfilo.isEmpty()) {
            Iterator<TAnMenuProfilo> itr = menuProfilo.iterator();
            while (itr.hasNext()) {
                menu.add((itr.next()).getTAnMenu());
            }
        }
        Collections.sort(menu, new Comparator<Object>() {

            public int compare(Object o1, Object o2) {

            	TAnMenu m1 = (TAnMenu) o1;
            	TAnMenu m2 = (TAnMenu) o2;
                return m1.getNumeOrdineVisual().compareTo(m2.getNumeOrdineVisual());
            }
        });
        return menu;
    }

    /**
     * getTTipoUfficio
     * 
     * @param descTipoUfficio
     * @return TTipoUfficio
     */
    public TTpTipoUfficio getTTipoUfficio(String descTipoUfficio) {

        DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoUfficio.class);
        criteria.add(Restrictions.eq("descTipoUff", descTipoUfficio).ignoreCase());
        TTpTipoUfficio lTipoUfficio = (TTpTipoUfficio) getHibernateTemplate().findByCriteria(criteria).get(0);
        return lTipoUfficio;
    }

    public TAnUfficio getUfficioByTipoUfficioAndCitta(TTpTipoUfficio tipoUfficio, String cittaUfficio) {

        DetachedCriteria criteria = DetachedCriteria.forClass(TAnUfficio.class);
        criteria.add(Restrictions.eq("descSedeUfficio", cittaUfficio));
        criteria.add(Restrictions.eq("TTpTipoUfficio", tipoUfficio));
        TAnUfficio ufficio = (TAnUfficio) getHibernateTemplate().findByCriteria(criteria).get(0);
        return ufficio;
    }

    public TUtente loginUtente(String userid, String password) {

        DetachedCriteria criteria = DetachedCriteria.forClass(TUtente.class);
        criteria.add(Restrictions.eq("userid", userid)).add(Restrictions.eq("password", password)).add(Restrictions.eq("attivo", "S"));
        List<TUtente> c = getHibernateTemplate().findByCriteria(criteria);
        TUtente ut = null;
        if (!c.isEmpty()) {
            ut = c.get(0);
        }
        return ut;
    }
}
