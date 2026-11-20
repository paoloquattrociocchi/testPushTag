package it.giustizia.smp.service;

import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import org.springframework.transaction.annotation.Transactional;

import it.giustizia.smp.dao.SpeseProcessualiDao;

import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.SpeseSoggettoForm;

import it.giustizia.smp.model.TRelSoggSpese;
import it.giustizia.smp.model.TRelSoggSpeseId;
import it.giustizia.smp.utility.Constants;


/**
 * SpeseServiceImpl
 * 
 * @author 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Transactional(rollbackFor = Exception.class)
public class SpeseServiceImpl implements SpeseService {
	
	private Logger logger = Logger.getLogger(getClass());

	private SpeseProcessualiDao speseProcessualiDao;

	
	public void salvaSpeseProcessuali(SpeseSoggettoForm speseSoggettoForm, long idAtto, long idProcedimento, String idCodiceUtente, long idUfficio, long idTipoUff, String idSpesaP) {

		for (SoggettoForm soggetto :speseSoggettoForm.getSoggetti() ) {
			
				TRelSoggSpese soggspese = new TRelSoggSpese();
				TRelSoggSpeseId idSoggSpese = new TRelSoggSpeseId();
				idSoggSpese.setCodiKIdProc(idProcedimento);
				idSoggSpese.setCodiKIdAtto(idAtto);
				if (soggetto.getDescrRuolo().contentEquals(Constants.RUOLO_DIRETTO)){
					idSoggSpese.setCodiFkIdRuolo(Constants.ID_RUOLO_DIRETTO);	
				}
				if (soggetto.getDescrRuolo().contentEquals(Constants.RUOLO_TERZO)){
					idSoggSpese.setCodiFkIdRuolo(Constants.ID_RUOLO_TERZO);	
				}
				if (soggetto.getDescrRuolo().contentEquals(Constants.RUOLO_INTERVENTIENTE)){
					idSoggSpese.setCodiFkIdRuolo(Constants.ID_RUOLO_INTERVENIENTE);	
				}
				//idSoggSpese.setCodiFkIdRuolo(soggetto.getIdRuolo());
				idSoggSpese.setcodiFkIdVer(soggetto.getIdVersione().shortValue());
				idSoggSpese.setCodiKIdSpeseProcessuali(Integer.parseInt(idSpesaP));
				idSoggSpese.setCodiKIdSoggInt(new Long(soggetto.getIdSoggetto()));
				idSoggSpese.setCodiKIdUffOwner((int) idUfficio);
				soggspese.setId(idSoggSpese);
				soggspese.setDescUtente(String.valueOf(idCodiceUtente));
				speseProcessualiDao.salvaRelSpeseProc(soggspese);		
		}

		
	}

	@Override
	public void salvaSpeseProcessuali(SpeseSoggettoForm speseSoggettoForm, long idAtto, long idProcedimento,
			String idCodiceUtente, int idUfficio, int idTipoUfficio) {
		for (SoggettoForm soggetto :speseSoggettoForm.getSoggettiSpeseP()) {
			for (String idSoggettiTitolareETitolo : soggetto.getListaIdCheckboxString()) {
				String idSoggetto = null;
				StringTokenizer st = new StringTokenizer(idSoggettiTitolareETitolo, ";");
				while (st.hasMoreTokens()) {
					idSoggetto = st.nextToken();
					st.nextToken();
				}
				
				TRelSoggSpese soggspese = new TRelSoggSpese();
				TRelSoggSpeseId idSoggSpese = new TRelSoggSpeseId();
				idSoggSpese.setCodiKIdProc(idProcedimento);
				idSoggSpese.setCodiKIdAtto(idAtto);
				idSoggSpese.setCodiFkIdRuolo(soggetto.getIdRuolo());
				idSoggSpese.setcodiFkIdVer(soggetto.getIdVersione().shortValue());
				//idSoggSpese.setCodiKIdSpeseProcessuali(Integer.parseInt(idSpesaP));
				idSoggSpese.setCodiKIdSoggInt(new Long(idSoggetto));
				idSoggSpese.setCodiKIdUffOwner((int) idUfficio);
				soggspese.setId(idSoggSpese);
				soggspese.setDescUtente(String.valueOf(idCodiceUtente));
				
				speseProcessualiDao.salvaRelSpeseProc(soggspese);	
		
			}
		}
		
	}
	
}