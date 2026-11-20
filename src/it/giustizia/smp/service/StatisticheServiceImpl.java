package it.giustizia.smp.service;

import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dao.StatisticheDao;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.StatisticheForm;
import it.giustizia.smp.model.TAnMagistrati;
import it.giustizia.smp.model.TStampa;
import it.giustizia.smp.model.TTpStampa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementazione del service per la funzione di gestione statistiche (monitoraggio)
 * 
 * @author m.prisco
 * 
 */
public class StatisticheServiceImpl implements StatisticheService {

	
	
	@Autowired
	private StatisticheDao statisticheDao;

	public StatisticheDao getStatisticheDao() {
		return statisticheDao;
	}

	public void setStatisticheDao(StatisticheDao statisticheDao) {
		this.statisticheDao = statisticheDao;
	}


	@Autowired
	private CommonDao commonDao;
	
	public List<TStampa> getListaStampeByTipoUfficio(int idTipoUff , StatisticheForm statisticheForm) {
		return statisticheDao.getListaStampeByTipoUfficio(idTipoUff, statisticheForm);
	}

	public TStampa getStampaByID(int idStampa) {
		return statisticheDao.getStampaByID(idStampa);
	}

	public List<ComboForm> getComboMagistrati(int idUfficioUtente) {
		List<Long> relazioniUfficio = statisticheDao.listaIdMagistratiByUfficio(idUfficioUtente);
		List<ComboForm> risultati = new ArrayList<ComboForm>();
		if (relazioniUfficio != null) {
			Iterator<Long> iterator = relazioniUfficio.iterator();
			while (iterator.hasNext()) {
				TAnMagistrati tmp = commonDao.getMagistratoById(iterator.next());
				if(tmp != null){
				  risultati.add(new ComboForm(String.valueOf(tmp.getCodiKIdMagistrato()), tmp.getDescCognomeMag() + " " + tmp.getDescNomeMag()));
				}else{
					  risultati.add(new ComboForm());
				}
					
			}
		}
		
		return sortedComboList(risultati);
	}

	public static List<ComboForm> sortedComboList(List<ComboForm> listInput ) {
		Comparator<ComboForm> comparator = new Comparator<ComboForm>() {
			public int compare(ComboForm o1, ComboForm o2) {
				return o1.getDescrizione().compareTo(o2.getDescrizione());
			}
		};
	    Collections.sort(listInput, comparator);
	    return listInput;
	  
	}
	
	
	public List<ComboForm> getComboProcedente(int idUfficioUtente) {
		List<ComboForm> risultati = new ArrayList<ComboForm>();
		List<Object[]> listaProcedenti = statisticheDao.listaIdProcedentiByUfficio(idUfficioUtente);
		for (Object[] tAnProcedente : listaProcedenti) {
			risultati.add(new ComboForm(String.valueOf(tAnProcedente[0].toString()), tAnProcedente[1].toString()));
		}
		return risultati;
	}

	public List<ComboForm> getValoriFiltro1(StatisticheForm statisticheForm) {
		List<ComboForm> risultati = new ArrayList<ComboForm>();
		List<TTpStampa> listaValoriFiltro = statisticheDao.getValoriFiltro1(statisticheForm);
		for (TTpStampa item : listaValoriFiltro) {
			risultati.add(new ComboForm(item.getCodiKIdCategoria()+"", item.getDescCategoria()));
		}
		return risultati;
	}

	public List<ComboForm> getValoriFiltro2(StatisticheForm statisticheForm) {
		List<ComboForm> risultati = new ArrayList<ComboForm>();
		List<TTpStampa> listaValoriFiltro = statisticheDao.getValoriFiltro2(statisticheForm);
		for (TTpStampa item : listaValoriFiltro) {
			risultati.add(new ComboForm(item.getCodiKIdCategoria()+"", item.getDescCategoria()));
		}
		return risultati;
	}

	public List<ComboForm> getValoriFiltro3(StatisticheForm statisticheForm) {
		List<ComboForm> risultati = new ArrayList<ComboForm>();
		List<TTpStampa> listaValoriFiltro = statisticheDao.getValoriFiltro3(statisticheForm);
		for (TTpStampa item : listaValoriFiltro) {
			risultati.add(new ComboForm(item.getCodiKIdCategoria()+"", item.getDescCategoria()));
		}
		return risultati;
	}
	
	

}
