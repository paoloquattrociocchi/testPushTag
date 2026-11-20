package it.giustizia.smp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.giustizia.smp.dao.HomeDao;
import it.giustizia.smp.model.TAnMenu;
import it.giustizia.smp.model.TUtente;

@Transactional(rollbackFor = Exception.class)
public class HomeServiceImpl implements HomeService {

	private HomeDao			homeDao;

	public ArrayList<TAnMenu> getMenuUtente(TUtente utente, long idTipoUfficio) {

		return homeDao.getMenuUtente(utente, idTipoUfficio);
	}
	
	public void setHomeDao(HomeDao homeDao) {

		this.homeDao = homeDao;
	}

}
