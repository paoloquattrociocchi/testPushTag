package it.giustizia.smp.service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.giustizia.smp.dao.AttoDao;
import it.giustizia.smp.dao.SoggettoDao;
import it.giustizia.smp.dto.pg_procedimento.MisurePersSoggDto;
import it.giustizia.smp.dto.pg_procedimento.SogIntDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_procedimento.SogSpesePDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_soggetto.BeniCollegatiTerzoDto;
import it.giustizia.smp.dto.pg_soggetto.DettaglioPersonaFisicaDto;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.SpeseSoggettoForm;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnProvincia;
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TDecreto;
import it.giustizia.smp.model.TIntestatario;
import it.giustizia.smp.model.TIstanza;
import it.giustizia.smp.model.TPersonaFisicaId;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TPersonaGiurId;
import it.giustizia.smp.model.TProvFg;
import it.giustizia.smp.model.TRelAttoMisPers;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TRelSoggSpese;
import it.giustizia.smp.model.TRelSoggSpeseId;
import it.giustizia.smp.model.TTpSettoreAtt;
import it.giustizia.smp.model.TTpTipoSocieta;
import it.giustizia.smp.model.TTpTipoStrada;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.xml.Soggetto;

/**
 * SoggettoServiceImpl
 * 
 * @author Almaviva S.p.A. classe del procedimento
 */
@Transactional(rollbackFor = Exception.class)
public class SoggettoServiceImpl implements SoggettoService {

	@Autowired
	private SoggettoDao soggettoDao;
	@Autowired
	private AttoDao attoDao;
	@Autowired
	private Mapper mapperBean;

	@Autowired(required = true)
	private HttpServletRequest request;


	private Logger logger = Logger.getLogger(getClass());

	
	
	public void setSoggettoDao(SoggettoDao soggettoDao) {
		this.soggettoDao = soggettoDao;
	}

	/**
	 * caricaValoriDettaglioSoggFisVerCopertinaAtto
	 * @param soggetto
	 * @return
	 */
	public SoggettoForm caricaValoriDettaglioSoggFisVerCopertinaAtto(SoggettoForm soggetto) {

		TPersonaFisicaId id = new TPersonaFisicaId();
		id.setCodiKIdUffOwner(soggetto.getIdUfficio());
		id.setCodiKIdPersonaFis(soggetto.getIdSoggetto());
		id.setCodiKIdVer((short) soggetto.getIdVersione().longValue());
		DettaglioPersonaFisicaDto personaFisicaDto = soggettoDao.getDettaglioPersonaFisicaVer(id);
		SoggettoForm soggettoReturn = new SoggettoForm(); 
		mapperBean.map(personaFisicaDto, soggettoReturn);
		return soggettoReturn;
	}
	
	/**
	 * caricaValoriDettaglioSoggGiuVerCopertinaAtto
	 * @param soggetto
	 * @return
	 */
	public SoggettoForm caricaValoriDettaglioSoggGiuVerCopertinaAtto(SoggettoForm soggetto) {

		TPersonaGiurId id = new TPersonaGiurId();
		id.setCodiKIdUffOwner(soggetto.getIdUfficio());
		id.setCodiKIdPersGiur(soggetto.getIdSoggetto());
		id.setCodiKIdVer((short) soggetto.getIdVersione().longValue());
		Map<String, Object> risultato = soggettoDao.getDettaglioPersonaGiuVer(id);
		TPersonaGiur personaG = (TPersonaGiur) risultato.get("p_giur");
		TTpTipoSocieta tiposoc = (TTpTipoSocieta) risultato.get("tiposoc");
		TAnStato stato = (TAnStato) risultato.get("stato");
		TAnComuni comuni = (TAnComuni) risultato.get("comuni");
		TAnProvincia provincia = (TAnProvincia) risultato.get("provincia");
		TTpTipoStrada strada = (TTpTipoStrada) risultato.get("strada");
		TTpSettoreAtt settAtt = (TTpSettoreAtt) risultato.get("settAtt");
		
		SoggettoForm soggettoReturn = new SoggettoForm(); 
		
		if (personaG != null) {
			mapperBean.map(personaG, soggettoReturn);
		}
		if (tiposoc != null) {
			mapperBean.map(tiposoc, soggettoReturn);
		}
		if (stato != null) {
			mapperBean.map(stato, soggettoReturn);
		}
		if (comuni != null) {
			mapperBean.map(comuni, soggettoReturn);
		}
		if (provincia != null) {
			mapperBean.map(provincia, soggettoReturn);
		}
		if (strada != null) {
			mapperBean.map(strada, soggettoReturn);
		}
		if (settAtt != null) {
			mapperBean.map(settAtt, soggettoReturn);
		}
		return soggettoReturn;
	}

	public List<MisurePersSoggDto> misurePersonaliSoggetto(long idSoggetto, int idUffOwn, long idProcedimento) {

		List<MisurePersSoggDto> lista = null; 
		List<TRelAttoMisPers> listaRelAttoMisPers = soggettoDao.misurePersonaliSoggetto(idSoggetto, idUffOwn, idProcedimento);
		if(listaRelAttoMisPers!=null && listaRelAttoMisPers.size()>0) {
			lista = new ArrayList<MisurePersSoggDto>();
			for(int i=0;i<listaRelAttoMisPers.size();i++) {
				TRelAttoMisPers relAttoMisPers = listaRelAttoMisPers.get(i);
				Long numeroAtto = null;
				Short annoAtto = null;		
				String richiesta = null; 
				
				String idTipoAtto = "" + relAttoMisPers.getTAtto().getTTpCatAtto().getTTpTipoAtto().getCodiKIdTipoAtto();
				Long idAtto = relAttoMisPers.getTAtto().getId().getCodiKIdAtto();

				if (Utility.isTDecretoRecord(idTipoAtto)) {
					 Criterion c1 = Restrictions.eq("id.codiKIdDecreto", idAtto);
					 Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", idUffOwn);
					 Object obj = attoDao.getAttoByPrimaryKey(TDecreto.class, c1, c2);
					 if(obj != null){
						TDecreto record = (TDecreto) obj;
						numeroAtto = record.getNumeAtto();
						annoAtto = record.getNumeAnnoAtto();
					 }
				} else if (Utility.isTIstanzaRecord(idTipoAtto)) {
		
					Criterion c1 = Restrictions.eq("id.codiKIdIstanza", idAtto);
					Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", idUffOwn);
					Object obj = attoDao.getAttoByPrimaryKey(TIstanza.class, c1, c2);
					if (obj != null) {
						TIstanza record = (TIstanza) obj;
						numeroAtto = record.getNumeAtto();
						annoAtto = record.getNumeAnnoAtto();
						richiesta = record.getDescRichiesta();
					}
				} else if (Utility.isTProvvFGRecord(idTipoAtto)) {
					Criterion c1 = Restrictions.eq("id.codiKIdProvFg", idAtto);
					Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", idUffOwn);
					Object obj = attoDao.getAttoByPrimaryKey(TProvFg.class, c1, c2);
					if (obj != null) {
						TProvFg record = (TProvFg) obj;
						numeroAtto = record.getNumeProvFg();
						annoAtto = record.getNumeAnnoProvFg();
					}
				}		
				
				MisurePersSoggDto misPersSoggDto = new MisurePersSoggDto();
				misPersSoggDto.setRelAttoMisPers(relAttoMisPers);
				misPersSoggDto.setAnnoAtto(annoAtto);
				misPersSoggDto.setNumeroAtto(numeroAtto);
				misPersSoggDto.setRichiesta(richiesta);

				lista.add(misPersSoggDto);
			} 
		}
		return lista;
	}

	public List<MisurePersSoggDto> cauzioniSoggetto(long idSoggetto, int idUffOwn, long idProcedimento) {
		
		List<MisurePersSoggDto> lista = null; 
		List<TRelAttoMisPers> listaRelAttoMisPers = soggettoDao.cauzioniSoggetto(idSoggetto, idUffOwn, idProcedimento);
		if(listaRelAttoMisPers!=null && listaRelAttoMisPers.size()>0) {
			lista = new ArrayList<MisurePersSoggDto>();
			for(int i=0;i<listaRelAttoMisPers.size();i++) {
				TRelAttoMisPers relAttoMisPers = listaRelAttoMisPers.get(i);
				Long numeroAtto = null;
				Short annoAtto = null;				
				String richiesta = null; 
				
				String idTipoAtto = "" + relAttoMisPers.getTAtto().getTTpCatAtto().getTTpTipoAtto().getCodiKIdTipoAtto();
				Long idAtto = relAttoMisPers.getTAtto().getId().getCodiKIdAtto();

				if (Utility.isTDecretoRecord(idTipoAtto)) {
					 Criterion c1 = Restrictions.eq("id.codiKIdDecreto", idAtto);
					 Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", idUffOwn);
					 Object obj = attoDao.getAttoByPrimaryKey(TDecreto.class, c1, c2);
					 if(obj != null){
						TDecreto record = (TDecreto) obj;
						numeroAtto = record.getNumeAtto();
						annoAtto = record.getNumeAnnoAtto();
					 }
				} else if (Utility.isTIstanzaRecord(idTipoAtto)) {
		
					Criterion c1 = Restrictions.eq("id.codiKIdIstanza", idAtto);
					Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", idUffOwn);
					Object obj = attoDao.getAttoByPrimaryKey(TIstanza.class, c1, c2);
					if (obj != null) {
						TIstanza record = (TIstanza) obj;
						numeroAtto = record.getNumeAtto();
						annoAtto = record.getNumeAnnoAtto();
						richiesta = record.getDescRichiesta();
					}
				} else if (Utility.isTProvvFGRecord(idTipoAtto)) {
					Criterion c1 = Restrictions.eq("id.codiKIdProvFg", idAtto);
					Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", idUffOwn);
					Object obj = attoDao.getAttoByPrimaryKey(TProvFg.class, c1, c2);
					if (obj != null) {
						TProvFg record = (TProvFg) obj;
						numeroAtto = record.getNumeProvFg();
						annoAtto = record.getNumeAnnoProvFg();
					}
				}		
				
				MisurePersSoggDto misPersSoggDto = new MisurePersSoggDto();
				misPersSoggDto.setRelAttoMisPers(relAttoMisPers);
				misPersSoggDto.setAnnoAtto(annoAtto);
				misPersSoggDto.setNumeroAtto(numeroAtto);
				misPersSoggDto.setRichiesta(richiesta);

				lista.add(misPersSoggDto);
			}
		}
		return lista;		
		
	}

	public List<MisurePersSoggDto> art67Soggetto(long idSoggetto, int idUffOwn, long idProcedimento) {
		
		List<MisurePersSoggDto> lista = null; 
		List<TRelAttoMisPers> listaRelAttoMisPers =  soggettoDao.art67Soggetto(idSoggetto, idUffOwn, idProcedimento);
		if(listaRelAttoMisPers!=null && listaRelAttoMisPers.size()>0) {
			lista = new ArrayList<MisurePersSoggDto>();
			for(int i=0;i<listaRelAttoMisPers.size();i++) {
				TRelAttoMisPers relAttoMisPers = listaRelAttoMisPers.get(i);
				Long numeroAtto = null;
				Short annoAtto = null;	
				String richiesta = null;
				
				String idTipoAtto = "" + relAttoMisPers.getTAtto().getTTpCatAtto().getTTpTipoAtto().getCodiKIdTipoAtto();
				Long idAtto = relAttoMisPers.getTAtto().getId().getCodiKIdAtto();

				if (Utility.isTDecretoRecord(idTipoAtto)) {
					 Criterion c1 = Restrictions.eq("id.codiKIdDecreto", idAtto);
					 Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", idUffOwn);
					 Object obj = attoDao.getAttoByPrimaryKey(TDecreto.class, c1, c2);
					 if(obj != null){
						TDecreto record = (TDecreto) obj;
						numeroAtto = record.getNumeAtto();
						annoAtto = record.getNumeAnnoAtto();
					 }
				} else if (Utility.isTIstanzaRecord(idTipoAtto)) {
		
					Criterion c1 = Restrictions.eq("id.codiKIdIstanza", idAtto);
					Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", idUffOwn);
					Object obj = attoDao.getAttoByPrimaryKey(TIstanza.class, c1, c2);
					if (obj != null) {
						TIstanza record = (TIstanza) obj;
						numeroAtto = record.getNumeAtto();
						annoAtto = record.getNumeAnnoAtto();
						richiesta = record.getDescRichiesta();
					}
				} else if (Utility.isTProvvFGRecord(idTipoAtto)) {
					Criterion c1 = Restrictions.eq("id.codiKIdProvFg", idAtto);
					Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", idUffOwn);
					Object obj = attoDao.getAttoByPrimaryKey(TProvFg.class, c1, c2);
					if (obj != null) {
						TProvFg record = (TProvFg) obj;
						numeroAtto = record.getNumeProvFg();
						annoAtto = record.getNumeAnnoProvFg();
					}
				}		
				
				MisurePersSoggDto misPersSoggDto = new MisurePersSoggDto();
				misPersSoggDto.setRelAttoMisPers(relAttoMisPers);
				misPersSoggDto.setAnnoAtto(annoAtto);
				misPersSoggDto.setNumeroAtto(numeroAtto);
				misPersSoggDto.setRichiesta(richiesta);

				lista.add(misPersSoggDto);
			}
		}
		return lista;		
				
	}		
	
	/**
	 * getListaSoggCondannati
	 * @param idUfficio
	 * @param idProcedimento
	 * @param idAtto
	 * @param flagSpese
	 * @param flagCassa
	 * @return
	 */
	public List<SoggettoForm> getListaSoggCondannati(long idUfficio,long idProcedimento,long idAtto,String flagSpese,String flagCassa) {

	List<SogIntDettaglioProcedimentoDto> soggetti = soggettoDao.getListaSoggCondannati(idUfficio, idProcedimento,idAtto,flagSpese,flagCassa);
	
	List<SoggettoForm> risultati = new ArrayList<SoggettoForm>();
	Iterator<SogIntDettaglioProcedimentoDto> iter = soggetti.iterator();
	
	while (iter.hasNext()) {
		SogIntDettaglioProcedimentoDto currentDto = iter.next();
		SoggettoForm sForm = new SoggettoForm();
		mapperBean.map(currentDto, sForm);
		risultati.add(sForm);
	}
	return risultati;
}	
	
	public TRelProcSogint dettaglioRelSogInt(long idSoggetto, int idUffOwn, long idProcedimento) {
		return soggettoDao.dettaglioRelSogInt(idSoggetto, idUffOwn, idProcedimento);
	}
	
	public List<BeniCollegatiTerzoDto> beniCollegatiTerzo(long idUfficio, long idProcedimento, long idSoggTit) {
		
		return soggettoDao.beniCollegatiTerzo(idUfficio, idProcedimento, idSoggTit);
	}

	
	public Long getIdSoggettoAnnullato(long idSoggetto, int idUffOwn, long idProcedimento){
		 
		 Long id = soggettoDao.getIdSoggettoAnnullato(idSoggetto, idUffOwn, idProcedimento);
		 return id;

	}

	public SoggettoForm getSoggettoIntestatario(long idSoggetto, int idUffOwner){
		TIntestatario intestatario = soggettoDao.getIntestatario(idSoggetto, idUffOwner);
		if(intestatario != null)
		{
			SoggettoForm soggettoForm= new SoggettoForm();
			mapperBean.map(intestatario, soggettoForm);
			return soggettoForm;
		}
		else
			return null;
	}

	public String flagDecretoSequestro(SoggettoForm soggetto) {
		String ret = "N";
		List<TRelBeneAtto> daoResults = soggettoDao.flagDecretoSequestro(soggetto.getIdSoggetto(), soggetto.getIdUfficio(), soggetto.getIdProcedimento());
		if (daoResults != null && !daoResults.isEmpty()) {
			Iterator iter = daoResults.iterator();
			boolean lock = false;
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				TRelBeneAtto currentFonte = (TRelBeneAtto) map.get("atto");
				if (currentFonte.getDataAssunzione() != null) {
					lock = true;
				}
			}
			if (lock) {
				ret = "S";
			}
		}
		return ret;
	}

	public void salvaSpeseProcessuali(SpeseSoggettoForm speseSoggettoForm, long idAtto, long idProcedimento, String idCodiceUtente, long idUfficio, long idTipoUff, String idSpesaP) {

		for (SoggettoForm soggetto :speseSoggettoForm.getSoggetti() ) {
			
				TRelSoggSpese soggspese = new TRelSoggSpese();
				TRelSoggSpeseId idSoggSpese = new TRelSoggSpeseId();
				String tipoOper;
				// anche se selezioniamo applica a tutti, 
				// possiamo cancellare l'importo associato ai singoli soggetti se si è selezionato NON CONDANNATO(NO_ITEM_SELECTED)
				if (idSpesaP.equals(Constants.NO_ITEM_SELECTED)) {
					tipoOper =  "C";
				}else{
					tipoOper =  "T";
				}
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
				//idSoggSpese.setCodiKIdSpeseProcessuali(2);
				idSoggSpese.setCodiKIdSoggInt(soggetto.getIdSoggetto());
				idSoggSpese.setCodiKIdUffOwner(soggetto.getIdUfficio());
				soggspese.setId(idSoggSpese);
				soggspese.setDescUtente(idCodiceUtente);
				soggettoDao.salvaRelSpeseProc(soggspese, tipoOper);		
		}

		
	}

	@Override
	public void salvaSpeseProcessuali(SpeseSoggettoForm speseSoggettoForm, long idAtto, long idProcedimento,
			String idCodiceUtente, int idUfficio, int idTipoUfficio) {
		int ii=0;
		for (SoggettoForm soggetto :speseSoggettoForm.getSoggettiSpeseP()) {
						
			    List<SoggettoForm> soggetti = speseSoggettoForm.getSoggetti();
			    SoggettoForm sogg = soggetti.get(ii);
				TRelSoggSpese soggspese = new TRelSoggSpese();
				TRelSoggSpeseId idSoggSpese = new TRelSoggSpeseId();
				String tipoOper = ""; //campo passato al db per capir tipo di operazione.
				idSoggSpese.setCodiKIdProc(idProcedimento);
				idSoggSpese.setCodiKIdAtto(idAtto);
				if (sogg.getDescrRuolo().contentEquals(Constants.RUOLO_DIRETTO)){
					idSoggSpese.setCodiFkIdRuolo(Constants.ID_RUOLO_DIRETTO);	
				}
				if (sogg.getDescrRuolo().contentEquals(Constants.RUOLO_TERZO)){
					idSoggSpese.setCodiFkIdRuolo(Constants.ID_RUOLO_TERZO);	
				}
				if (sogg.getDescrRuolo().contentEquals(Constants.RUOLO_INTERVENTIENTE)){
					idSoggSpese.setCodiFkIdRuolo(Constants.ID_RUOLO_INTERVENIENTE);	
				}
				idSoggSpese.setcodiFkIdVer(sogg.getIdVersione().shortValue());
				idSoggSpese.setCodiKIdSpeseProcessuali(Integer.parseInt(soggetto.getSpeseProcessuali().getId()));
				if (soggetto.getSpeseProcessuali().getId().equals(Constants.NO_ITEM_SELECTED)) {
					tipoOper =  "C";
				}else{
					tipoOper =  "T";
				}
				idSoggSpese.setCodiKIdSoggInt(new Long(sogg.getIdSoggetto()));
				idSoggSpese.setCodiKIdUffOwner((int) idUfficio);
				soggspese.setId(idSoggSpese);
				soggspese.setDescUtente(String.valueOf(idCodiceUtente));
				
				soggettoDao.salvaRelSpeseProc(soggspese, tipoOper);	
				ii++;
			
		}
		
	}
	
	public void cancellaSpeseProcessuali(SpeseSoggettoForm speseSoggettoForm, long idAtto, long idProcedimento, String idCodiceUtente, int idUfficio,  String cancella) {

		for (SoggettoForm soggetto :speseSoggettoForm.getSoggetti() ) {
			
				TRelSoggSpese soggspese = new TRelSoggSpese();
				TRelSoggSpeseId idSoggSpese = new TRelSoggSpeseId();
				String tipoOper = "C"; //campo passato al db per capir tipo di operazione in questo caso cancella il record  
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
				idSoggSpese.setCodiKIdSpeseProcessuali(Integer.parseInt(cancella));
				//idSoggSpese.setCodiKIdSpeseProcessuali(2);
				idSoggSpese.setCodiKIdSoggInt(soggetto.getIdSoggetto());
				idSoggSpese.setCodiKIdUffOwner(soggetto.getIdUfficio());
				soggspese.setId(idSoggSpese);
				soggspese.setDescUtente(idCodiceUtente);
				soggettoDao.salvaRelSpeseProc(soggspese, tipoOper);		
		}

		
	}

}
