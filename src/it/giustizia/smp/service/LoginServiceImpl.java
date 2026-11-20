package it.giustizia.smp.service;

import it.giustizia.smp.controller.LoginController;
import it.giustizia.smp.dao.LoginDao;
import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.model.TAnMenu;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TTpProfilo;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.model.TUtente;
import it.giustizia.smp.utility.Utility;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements LoginService {

	private Logger				logger	= Logger.getLogger(LoginController.class);
	private LoginDao			loginDao;
	@Autowired(required = true)
	private HttpServletRequest	request;

	public ArrayList<TAnMenu> getMenuUtente(TUtente utente, long idTipoUfficio) {

		return loginDao.getMenuUtente(utente, idTipoUfficio);
	}

	/**
	 * getTTipoUfficio
	 * 
	 * @param descTipoUfficio
	 * @return TTipoUfficio
	 */
	public TTpTipoUfficio getTTipoUfficio(String descTipoUfficio) {

		TTpTipoUfficio tTipoUfficio = new TTpTipoUfficio();
//		try {
			tTipoUfficio = loginDao.getTTipoUfficio(descTipoUfficio);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return tTipoUfficio;
	}

	public TAnUfficio getUfficioByTipoUfficioAndCitta(TTpTipoUfficio tipoUfficio, String cittaUfficio) {

		TAnUfficio u = null;
//		try {
			u = loginDao.getUfficioByTipoUfficioAndCitta(tipoUfficio, cittaUfficio);
			if (u != null) {
				logger.debug("Ufficio dell'utente caricato correttamente");
			}
//		} catch (Exception e) {
//			logger.error("Errore durante il caricamento dell'ufficio\n " + e);
//		}
		return u;
	}

	public boolean isOfficeTypeTribunale(TTpTipoUfficio tipoUfficio) {

		return (tipoUfficio != null) && ((tipoUfficio.getCodiKIdTipoUff() == 6) || (tipoUfficio.getCodiKIdTipoUff() == 16) || (tipoUfficio.getCodiKIdTipoUff() == 19));
	}

	public TUtente getUtenteByProfilo(String profiloString, ProfiloForm profilo) {

		ProfiloForm profiloDiSessione = Utility.parseProfiloFromString(profiloString, "|");
		TUtente utente = new TUtente();
		TTpProfilo profiloObj = new TTpProfilo();
		profiloObj.setDescProfilo(profiloDiSessione.getRuolo().toUpperCase());
		utente.setTTpProfilo(profiloObj);
		String sessionId = request.getSession().getId();
		utente.setUserid(sessionId);
		utente.setNome(profiloDiSessione.getCommonName());
		utente.setPassword("d033e22ae348aeb5660fc2140aec35850c4da997");
		utente.setAttivo('S');
		utente.setCodiceFiscale("AAABBB56A56S654G");
//		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
//		roles.add(new GrantedAuthorityImpl("ROLE_USER"));
		TTpTipoUfficio tipoUfficio = getTTipoUfficio(profiloDiSessione.getDescrizioneUfficio());
		profiloDiSessione.setIdTipoUfficio(tipoUfficio.getCodiKIdTipoUff());
		profilo.setIdTipoUfficio(tipoUfficio.getCodiKIdTipoUff());
//		request.getSession().setAttribute("profilo", profiloDiSessione);
		request.getSession().setAttribute("profilo", profilo);
		return utente;
	}

	/*public TUtente loginUtente(String userid, String password) {

		TUtente utente = null;
		try {
			String pw = Utility.calcolaHash(password);
			utente = loginDao.loginUtente(userid, pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return utente;
	}*/

	public void setLoginDao(LoginDao loginDao) {

		this.loginDao = loginDao;
	}
}
