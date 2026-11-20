package it.giustizia.smp.dao;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import it.giustizia.smp.dto.pg_notifiche.DestinatarioDto;
import it.giustizia.smp.form.NotificaAttoForm;
import it.giustizia.smp.form.NotificheEComunicazioniForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.model.TAmministratori;
import it.giustizia.smp.model.TAnUfficio;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TDepositi;
import it.giustizia.smp.model.TDepositiAmm;
import it.giustizia.smp.model.TNotifAmm;
import it.giustizia.smp.model.TNotifDest;
import it.giustizia.smp.model.TNotifRicevute;
import it.giustizia.smp.model.TNotifiche;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TTpRuoliDest;
import it.giustizia.smp.model.TTpStatiTrasm;
import it.giustizia.smp.model.TTpTipoUfficio;
import it.giustizia.smp.model.VNotifiche;
import it.giustizia.smp.model.VRappTrasm;
import it.giustizia.smp.model.VRicevuta;
import it.giustizia.smp.utility.notifiche.ServiceResult;
import it.giustizia.smp.utility.notifiche.ServiceResult.Result;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.InfoEccezione;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.InfoTrasmissione;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.InviaComunicazione;
import it.giustizia.smp.webservice.client.glmp.servizitrasmissione.RisultatoComunicazione;

/**
 * Implementazione del dao delle notifiche
 * @author m.prisco
 *
 */

public class NotificheDaoImpl extends HibernateDaoSupport implements NotificheDao {
	@Autowired
	private ReloadableResourceBundleMessageSource configParameterSource;

	public String isAttoInviabile(final long idAtto,final long idProcedimento) {

		return (String) getHibernateTemplate().execute(new HibernateCallback<String>() {

			public String doInHibernate(final Session session) throws HibernateException, SQLException {
				//System.out.println(idAtto);
				Object retValue =  session.getNamedQuery("PG_NOTIFICHE.IS_ATTO_INVIABILE")
						.setParameter("CODI_K_ID_ATTO_PARAM", idAtto, Hibernate.LONG)
						.setParameter("CODI_K_ID_PROC_PARAM", idProcedimento, Hibernate.LONG)
						.uniqueResult();
				
				
				return (String)retValue;
				
			}
		});
	}
	
	public Boolean hasNotifiche(final Long idAtto,final  Integer idUfficio) {
		return (Boolean) getHibernateTemplate().execute(new HibernateCallback<Boolean>() {

			public Boolean doInHibernate(final Session session) throws HibernateException, SQLException {
				//System.out.println(idAtto);
				Object retValue =  session.getNamedQuery("PG_NOTIFICHE.HAS_NOTIFICHE")
						.setParameter("CODI_K_ID_ATTO_PARAM", idAtto, Hibernate.LONG)
						.setParameter("CODI_K_ID_UFFICIO_PARAM", idUfficio, Hibernate.INTEGER)
						.uniqueResult();
				
				if (retValue!=null && "S".equals((String)retValue)) {
					return true;
				} else {
					return false;
				}
			}
		});
	}
	
	
	public DestinatarioDto getDetinatarioDTObyIdUfficio(final int idUfficio) {
		return  getHibernateTemplate().execute(new HibernateCallback<DestinatarioDto>() {
			public    DestinatarioDto   doInHibernate(final Session session) throws HibernateException, SQLException {
				List<?> l =  session.getNamedQuery("PG_NOTIFICHE.CARICA_DESTINATARIO")
						.setParameter("CODI_K_ID_UFFICIO", idUfficio, Hibernate.INTEGER)
						.setResultTransformer(Transformers.aliasToBean(DestinatarioDto.class))
						.list();
				return ( DestinatarioDto)l.get(0);
			}
		});
		
	}
	
	/***
	 * Restituisce la lista dei destinatari possibili per l'atto e l'ufficio passato
	 * @param idAtto
	 * @param idUfficio
	 * @return
	 */
	public List<DestinatarioDto> caricaListaDestinatari(final long idProc, final long idAtto,final int idUfficio) {
		return  getHibernateTemplate().execute(new HibernateCallback< List<DestinatarioDto>>() {
			@SuppressWarnings("unchecked")
			public   List<DestinatarioDto>  doInHibernate(final Session session) throws HibernateException, SQLException {
				List<?> l =  session.getNamedQuery("PG_NOTIFICHE.CARICA_DESTINATARI_ATTO")
						.setParameter("CODI_K_ID_PROC_PARAM", idProc, Hibernate.LONG)
						.setParameter("CODI_K_ID_ATTO_PARAM", idAtto, Hibernate.LONG)
						.setParameter("CODI_K_ID_UFFICIO", idUfficio, Hibernate.INTEGER)
						.setResultTransformer(Transformers.aliasToBean(DestinatarioDto.class))
						.list();
				return ( List<DestinatarioDto>)l;
			}
		});
	}

	
	/***
	 * Restituisce la lista dei destinatari possibili per l'atto e l'ufficio passato
	 * @param idAtto
	 * @param idUfficio
	 * @return
	 */
	public void modificaCopiaPDOC(final Long numeCopiaPdoc, final long idAtto,final int idUfficio) {
		getHibernateTemplate().execute(new HibernateCallback<Void>() {
			
			public  Void doInHibernate(final Session session) throws HibernateException, SQLException {
				session.getNamedQuery("PG_NOTIFICHE.UPDATE_COPIA_PDOC")
						.setParameter("NUMCOPIAPDOC", numeCopiaPdoc, Hibernate.LONG)
						.setParameter("CODI_K_ID_ATTO_PARAM", idAtto, Hibernate.LONG)
						.setParameter("CODI_K_ID_UFFICIO", idUfficio, Hibernate.INTEGER)
						.list();
				return null;
			}
		});
	}


	/***
	 * Restituisce la lista di tutti i possibili tipi ufficio 
	 */
	@SuppressWarnings("unchecked")
	public List<TTpTipoUfficio> getTipoUfficiPerDesinatario() {
		List<TTpTipoUfficio> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoUfficio.class);
		lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	
	/***
	 * Restituisce la lista di tutti i possibili tipi ufficio 
	 */
	@SuppressWarnings("unchecked")
	public List<TTpRuoliDest> getListaTipoRuoliDesinatario() {
		List<TTpRuoliDest> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpRuoliDest.class);
		lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	/***
	 * Gestione dell'inserimento della notifica
	 */
	@Transactional
	public ServiceResult insertNotifica(	
								final NotificaAttoForm attoForm,
								final TAtto tAtto, 
								final ProcedimentoForm procedimento, 
								final int idUfficioMitt,
								final RisultatoComunicazione responseSenzaContenuto, 
								final RisultatoComunicazione responseConContenutoSenzaXML,
								final RisultatoComunicazione responseConContenutoConXML,
								final InviaComunicazione requestSenzaContenuto,
								final InviaComunicazione requestConContenutoSenzaXML,
								final InviaComunicazione requestConContenutoConXML,
								final String descUtente,
								final List<DestinatarioDto> listaDestinatariSelezionatiSenzaContenuto, 
								final List<DestinatarioDto> listaDestinatariSelezionatiConContenuto,
								final byte[] xmlDati,
								final BigInteger idPDOCXml) throws Exception {
		return getHibernateTemplate().execute(new HibernateCallback<ServiceResult>() {
			
			public  ServiceResult doInHibernate(final Session session) throws HibernateException, SQLException  {
				// Se è stata effettuata almeno una trasmissione
				ServiceResult result = new ServiceResult(Result.OK);
				if (responseSenzaContenuto!=null) {
					for (InfoEccezione eccezione : responseSenzaContenuto.getEccezioni()) {
						if (eccezione!=null) result.addWarning(eccezione.getDestinatario().getRuolo() + "-" + eccezione.getDestinatario().getCodice() + "; Eccezione:" + eccezione.getMessaggioEccezione() + "<br/>");
					}
				}
				if (responseConContenutoSenzaXML!=null) {
					for (InfoEccezione eccezione : responseConContenutoSenzaXML.getEccezioni()) {
						if (eccezione!=null) result.addWarning(eccezione.getDestinatario().getRuolo() + "-" + eccezione.getDestinatario().getCodice() + "; Eccezione:" + eccezione.getMessaggioEccezione() + "<br/>");
					} 
				}
				if (responseConContenutoConXML!=null) {
					for (InfoEccezione eccezione : responseConContenutoConXML.getEccezioni()) {
						if (eccezione!=null) result.addWarning(eccezione.getDestinatario().getRuolo() + "-" + eccezione.getDestinatario().getCodice() + "; Eccezione:" + eccezione.getMessaggioEccezione() + "<br/>");
					} 
				}

				// PRIMA CHIAMATA PER INSERIMENTO IN T_NOTIFICHE
				Object retValue =  session.getNamedQuery("PG_NOTIFICHE.INSERIMENTO_NOTIFICA")
				.setParameter("CODI_FK_ID_ATTO_PARAM", tAtto.getId().getCodiKIdAtto(), Hibernate.LONG)
				.setParameter("CODI_FK_ID_UFFICIO", idUfficioMitt, Hibernate.INTEGER)
				.setParameter("FLG_DATI_SENSIBILI_PARAM", (attoForm.getFlgDatiSensibili()?"S":"N"), Hibernate.STRING)
				.setParameter("DESC_OGGETTO_PARAM", attoForm.getOggetto(), Hibernate.STRING)
				.setParameter("DESC_EVENTO_PARAM", attoForm.getTesto(), Hibernate.STRING)
				.setParameter("DESC_UTENTE_PARAM", descUtente, Hibernate.STRING)
				.setParameter("DATA_INVIO_PARAM", new Date(), Hibernate.DATE)
				.uniqueResult();
				if (retValue == null || "".equals((String)retValue)) {
					// Se non è stato restituito l'id della notifica
					result.setKO("Errore durante l'inserimento in T_NOTIFICA");
					return result;
				}
				String idNotifica = (String)retValue;
				
				if (responseSenzaContenuto!=null && responseSenzaContenuto.getTrasmissioni().size()>0) {
					// INSERIMENTO SULLE FIGLIE PER SENZA CONTENUTO
					insertInTNotifRichieteEDest(attoForm, tAtto, procedimento, idUfficioMitt, idNotifica,result,session,requestSenzaContenuto,responseSenzaContenuto,descUtente,listaDestinatariSelezionatiSenzaContenuto,null,null);
				}
				if (responseConContenutoSenzaXML!=null && responseConContenutoSenzaXML.getTrasmissioni().size()>0) {
					// INSERIMENTO SULLE FIGLIE PER CON CONTENUTO
					insertInTNotifRichieteEDest(attoForm, tAtto, procedimento, idUfficioMitt, idNotifica,result,session,requestConContenutoSenzaXML,responseConContenutoSenzaXML,descUtente,listaDestinatariSelezionatiConContenuto,null,null);
				}
				if (responseConContenutoConXML!=null && responseConContenutoConXML.getTrasmissioni().size()>0) {
					// INSERIMENTO SULLE FIGLIE PER CON CONTENUTO
					insertInTNotifRichieteEDest(attoForm, tAtto, procedimento, idUfficioMitt, idNotifica,result,session,requestConContenutoConXML,responseConContenutoConXML,descUtente,listaDestinatariSelezionatiConContenuto,xmlDati,idPDOCXml);
				}
				
				
				return result;
			}
		});
		
	}
	/***
	 * Inserimento in tNotifiche e T_NOTIF_DEST
	 * @param idNotifica
	 * @param result
	 * @param session
	 * @param request
	 * @param response
	 */
	private void insertInTNotifRichieteEDest(  
			NotificaAttoForm attoForm,
			TAtto tAtto, 
			ProcedimentoForm procedimento, 
			int idUfficioMitt,
			String idNotifica,
			ServiceResult result,
			Session session,
			InviaComunicazione 		request,
			RisultatoComunicazione 	response,
			String 					descUtente,
			List<DestinatarioDto> 	listaDestinatariSelezionati,
			final byte[] xmlDati,
			final BigInteger idPDOCXml
			
			) throws SQLException {
		Blob blobRequest = serializeInBlob(marshaller(request));
		Blob blobResponse = serializeInBlob(marshaller(response));
		String allegaContenuto = "N";
		try {
			allegaContenuto = (request.getNotifica().isAllegaContenuti()?"S":"N");
		}catch(Exception e){}
		
		// CHIAMATA PER INSERIMENTO IN T_ATTO T_NOTIF_RICHIESTE
		Object retValue =  session.getNamedQuery("PG_NOTIFICHE.INSERT_IN_NOTIF_RICHIESTE")
		.setParameter("CODI_FK_ID_NOTIFICA_PARAM", Long.parseLong(idNotifica), Hibernate.LONG)
		.setParameter("FLG_ALLEGA_CONT_PARAM",allegaContenuto , Hibernate.STRING)
		.setParameter("XML_RICHIESTA_PARAM", blobRequest, Hibernate.BLOB)
		.setParameter("XML_RISPOSTA_PARAM", blobResponse, Hibernate.BLOB)
		.uniqueResult();
		if (retValue == null || "".equals((String)retValue)) {
			// Se non è stato restituito l'id della notifica
			result.setKO("Errore durante l'inserimento in T_NOTIF_RICHIESTE");
			return;
		}
		//int i = 0;
		for (InfoTrasmissione trasmissione : response.getTrasmissioni()) {

			// Estraggo il destinatario dalla risposta di trasmissione
			DestinatarioDto destinatarioDto = trovaDestinatarioDto(listaDestinatariSelezionati, trasmissione.getDestinatario().getCodice());
			if (destinatarioDto==null) {
				String errore = "Errore in NotificheDaoImpl.insertInTNotifRichieteEDest. Il destinatarioDto non "
								+ " è presente nel risultato della trasmissione. Se stai usando il Mock controlla il valore codice destinatario.";
				logger.error(errore);
				throw new SQLException(errore);
			}
			if ("N".equals(allegaContenuto)) {
				// Se non ho allegato il contenute (Comuncazioni fra uffici) faccio partire anche il flusso
				
				if ("UFFICIO_PENALE".equals(destinatarioDto.getDESC_RUOLO_DEST())) {
					//Se il destinatario è un ufficio giudiziario devo invocare la Creazione del flusso per Comunicare il PDOC all'ufficio di destinazione
					
					try {
						TAnUfficio uffDest = getUfficioByCodugne(destinatarioDto.getCODI_DEST());
						logger.info("Inizio -> Creazione flusso per ufficio di destinazione della notifiche");
						session.getNamedQuery("PG_FLUSSI_generateFlussoNotifiche")
						.setParameter("idProc", procedimento.getIdProcedimento() , Hibernate.LONG)
						.setParameter("idAtto", tAtto.getId().getCodiKIdAtto() , Hibernate.LONG)
						.setParameter("idUffMitt", idUfficioMitt , Hibernate.INTEGER)
						.setParameter("idUffDest", uffDest.getCodiKIdUfficio() , Hibernate.INTEGER)
						.setParameter("numePDOC", tAtto.getNumeDocumentoPdoc(), Hibernate.LONG)
						.setParameter("numeCopiaPDOC", tAtto.getNumeCopiaPdoc() , Hibernate.LONG)
						.setParameter("nomeDocumento", tAtto.getDescNomeDocumento() , Hibernate.STRING)
						.setParameter("tipoDocumento", tAtto.getDescTipoDocumento() , Hibernate.STRING)
						.setParameter("descUtente", descUtente , Hibernate.STRING).uniqueResult();
					
					} catch (Exception e) {
						String errore = "Errore durante la creazione del flusso per l'ufficio di destinazione:" +  destinatarioDto.getCODI_DEST() + " (Errore:" + e.getMessage() + ")";
						logger.error(errore,e);
						throw new SQLException(errore,e);
					}
					logger.info("Fine -> Creazione flusso per ufficio di destinazione della notifiche");
				}
			}
			try {
				
				//TODO TOOL AMMINISTRATORI GESTIRE INSERIMENTO IN T_NOTIF_AMM
				Object retValueDest =  session.getNamedQuery("PG_NOTIFICHE.INSERT_IN_NOTIF_DEST")
				.setParameter("CODI_FK_ID_NOTIF_RICHIESTA", Long.parseLong((String)retValue), Hibernate.LONG)
				.setParameter("CODI_FK_RUOLO_DEST", destinatarioDto.getCODI_K_RUOLO_DEST().intValue() , Hibernate.INTEGER)
				.setParameter("DESC_DESTINATARO", destinatarioDto.getDESC_DEST() , Hibernate.STRING)
				.setParameter("DESC_CODI_DEST", destinatarioDto.getCODI_DEST()  , Hibernate.STRING)
				.setParameter("DESC_CODI_TRASMISSIONE", trasmissione.getIdTrasmissione(), Hibernate.STRING)
				.setParameter("DESC_PEC_DEST",trasmissione.getMail(), Hibernate.STRING)
				.setParameter("DESC_ECCEZIONE", null, Hibernate.STRING)
				.setParameter("DESC_UTENTE", descUtente, Hibernate.STRING)
				.setParameter("DATA_ULT_AGG", new Date(), Hibernate.DATE)
				.uniqueResult();
				if (retValueDest == null || "".equals((String)retValueDest)) {
					// Se non è stato restituito l'id della notifica
					result.setKO("Errore durante l'inserimento in T_NOTIF_DEST");
					return;
				}
				
				if (idPDOCXml!=null) {
					TNotifAmm tNotifAmm = new TNotifAmm();
					tNotifAmm.setCodiKIdNotifAmm(Long.valueOf((String)retValueDest));
					tNotifAmm.setNumePdoc(idPDOCXml.longValue());
					tNotifAmm.setXmlToolAmm(Hibernate.createBlob(xmlDati));
					session.save(tNotifAmm);
				}
				//F.C. MEV 2024-024 solo se utilizzo il MOCK GL  setto il flag Trasmissione conclusa a S nella tabella
				//T_NOTIF_DEST
				String mockResponse = configParameterSource.getMessage("MOCK_RESPONSE_SERVIZIO_GL", null, null);
				if(mockResponse != null && mockResponse.equals("S")) {
					List<TNotifDest> attesaRicevuta = getNotifDestAttesaRicevuta();
					for (TNotifDest tNotifDest : attesaRicevuta) {
						if (tNotifDest.getCodiKIdNotifDest() == Long.valueOf(retValueDest.toString())) {
							tNotifDest.setFlgTrasmConclusa('S');
							tNotifDest.setDataUltAgg(new Date());
							session.saveOrUpdate(tNotifDest);
							break;
						}
					}
					
				}
				
			} catch (Exception e) {
				String errore = "Errore durante l'inserimento in PG_NOTIFICHE.INSERT_IN_NOTIF_DEST :" +  destinatarioDto.getCODI_DEST() + " (Errore:" + e.getMessage() + ")";
				logger.error(errore,e);
				throw new SQLException(errore,e);
			}
		}
	}
	
	private DestinatarioDto  trovaDestinatarioDto(List<DestinatarioDto>listaDestinatariSelezionati, String codiDest) {
		for (DestinatarioDto destinatarioDto : listaDestinatariSelezionati) {
			if (destinatarioDto.getCODI_DEST().equals(codiDest)) {
				return destinatarioDto;
			}
		}
		return null;
	}


	/***
	 * Decodifica il codice di trasmissione ricevuto dal WS
	 */
	public String decodificaStatoTrasmissione(String stato) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpStatiTrasm.class);
		criteria.add(Restrictions.eq("codiKStatoTrasm", stato));
		@SuppressWarnings("unchecked")
		
		List<TTpStatiTrasm> lista = (List<TTpStatiTrasm>)getHibernateTemplate().findByCriteria(criteria);
		if (lista.size()==0) {
			return stato + " (Non Decodificato)";
		} else {
			return lista.get(0).getDescStato();
		}
	}
	
	/***
	 * Restituisce la lista delle notifiche in attesa di ricevuta
	 */
	public TTpStatiTrasm getTTpStatiTrasmById(String statoTrasmissione) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpStatiTrasm.class);
		criteria.add(Restrictions.eq("codiKStatoTrasm", statoTrasmissione));
		@SuppressWarnings("unchecked")
		
		List<TTpStatiTrasm> lista = (List<TTpStatiTrasm>)getHibernateTemplate().findByCriteria(criteria);
		if (lista.size()==0) {
			return null;
		} else {
			return lista.get(0);
		}
	}
	
	/***
	 * Restitusce la lista delle notifiche disponibili per l'atto passato
	 */
	public List<TNotifiche>  getListaNotifiche(Long idAtto, int idUfficio) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TNotifiche.class);
		criteria.add(Restrictions.eq("codiFkIdAtto", idAtto));
		criteria.add(Restrictions.eq("codiFkIdUffOwner", idUfficio));
		criteria.addOrder(Order.desc("dataUltAgg"));
		@SuppressWarnings("unchecked")
		List<TNotifiche> lista = (List<TNotifiche>)getHibernateTemplate().findByCriteria(criteria);
		Hibernate.initialize(lista);
		return lista;
	}
	
	/***
	 * Restitusce la lista delle notifiche disponibili per l'atto passato
	 */
	public List<VRicevuta>  getListaAttiRicevutoDaAttoNotificato(Long idAttoNotificato, int idUfficioNotificato) {
		DetachedCriteria criteria = DetachedCriteria.forClass(VRicevuta.class);
		criteria.add(Restrictions.eq("idAttoNotificato", idAttoNotificato));
		criteria.add(Restrictions.eq("idUffOwnerNotificato", idUfficioNotificato));
		@SuppressWarnings("unchecked")
		List<VRicevuta> lista = (List<VRicevuta>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	/***
	 * Restitusce la lista delle notifiche disponibili per l'atto passato
	 */
	public  List<VRappTrasm>  getRappTrasmDaAttoNotificato(Long idAttoNotificato, int idUfficioNotificato) {
		DetachedCriteria criteria = DetachedCriteria.forClass(VRappTrasm.class);
		criteria.add(Restrictions.eq("idAttoNotificato", idAttoNotificato));
		criteria.add(Restrictions.eq("idUffOwnerNotificato", idUfficioNotificato));
		@SuppressWarnings("unchecked")
		List<VRappTrasm> lista = (List<VRappTrasm>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	
	/*
	public TNotifRicevute caricaNotificatoDaRicevuta(long codAtto, int idUfficio) {
		/*List<TNotifiche> lista = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(TNotifiche.class);
		criteria.setFetchMode("TNotifRichiestes", FetchMode.JOIN);
		criteria.setFetchMode("TNotifRichiestes.TNotifDests", FetchMode.JOIN);
		criteria.createAlias("TNotifRichiestes", "richieste");
		criteria.createAlias("TNotifRichiestes.TNotifDests", "dest");
		criteria.createAlias("TNotifRichiestes.TNotifDests.TNotifRicevutes", "ricevute");
		criteria.add(Restrictions.eq("ricevute.codiFkIdAttoRicevuta", codAtto));
		criteria.add(Restrictions.eq("ricevute.codiFkIdUffOwnerRicevuta", idUfficio));
		*
		DetachedCriteria criteria = DetachedCriteria.forClass(TNotifRicevute.class);
		criteria.setFetchMode("TNotifDest", FetchMode.JOIN);
		criteria.setFetchMode("TNotifRichieste", FetchMode.JOIN);
		criteria.add(Restrictions.eq("codiFkIdAttoRicevuta", codAtto));
		criteria.add(Restrictions.eq("codiFkIdUffOwnerRicevuta", idUfficio));

		List<TNotifRicevute> lista = null;
		
		lista = getHibernateTemplate().findByCriteria(criteria);
		if (lista.size() > 0) {
			lista.get(0).getTNotifDest().getTNotifRichieste().getTNotifiche().getCodiFkIdAtto();
			return lista.get(0);
		}
		return null;
	}*/

	
	
	/***
	 * Restituisce la lista delle notifiche in attesa di ricevuta
	 */
	public List<TNotifDest> getNotifDestAttesaRicevuta() {
		return (List<TNotifDest>) getHibernateTemplate().execute(new HibernateCallback<List<TNotifDest>>() {

			public List<TNotifDest> doInHibernate(final Session session) throws HibernateException, SQLException {
				
				Criteria criteria =  session.createCriteria(TNotifDest.class);
				criteria.add(Restrictions.or(
						Restrictions.eq("flgTrasmConclusa", 'N'),
						Restrictions.isNull("flgTrasmConclusa")
						));
				@SuppressWarnings("unchecked")
				List<TNotifDest> lista = criteria.list();
				Hibernate.initialize(lista);
				for (TNotifDest tNotifDest : lista) {
					tNotifDest.getTNotifRichieste().getTNotifiche().getCodiFkIdUffOwner();
				}
				
				return lista;
			}
		});
		
	}
	
	/***
	 * Restituisce la lista delle notifiche in attesa di ricevuta
	 */
	public List<TNotifDest> getNotifDestAttesaRappTrasm() {
		return (List<TNotifDest>) getHibernateTemplate().execute(new HibernateCallback<List<TNotifDest>>() {
			public List<TNotifDest> doInHibernate(final Session session) throws HibernateException, SQLException {
				Criteria criteria =  session.createCriteria(TNotifDest.class);
				
				criteria.add(Restrictions.isNull("codiFkAttoRapporto"));
				criteria.add(Restrictions.eq("flgTrasmConclusa", 'S'));
				
				@SuppressWarnings("unchecked")
				List<TNotifDest> lista = criteria.list();
				Hibernate.initialize(lista);
				for (TNotifDest tNotifDest : lista) {
					tNotifDest.getTNotifRichieste().getTNotifiche().getCodiFkIdUffOwner();
				}
				return lista;
			}
		});
		
	}
	
	
	public boolean isStatoTrasmissioneGiaGestito(String statoTrasmissione, long codiKIdNotifDest) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TNotifRicevute.class);
		criteria.add(Restrictions.eq("TNotifDest.codiKIdNotifDest", codiKIdNotifDest));
		criteria.add(Restrictions.eq("codiFkStatoTrasm", statoTrasmissione));
				
		@SuppressWarnings("unchecked")
		List<TNotifRicevute> lista = (List<TNotifRicevute>)getHibernateTemplate().findByCriteria(criteria);
		if (lista.size()>0) return true;
		else return false;
	}
	
	
	
	public Integer getIdRuoloDestDaTipoUfficio(int idTipoUfficio) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TTpTipoUfficio.class);
		criteria.add(Restrictions.eq("codiKIdTipoUff", idTipoUfficio));
		@SuppressWarnings("unchecked")
		List<TTpTipoUfficio> lista = (List<TTpTipoUfficio>)getHibernateTemplate().findByCriteria(criteria);
		if (lista.size()>0) return lista.get(0).getCodiFKIdRuoloDest();
		else return -1;
	}
	
	/***
	 * Inserimento dell'atto di tipo ricevuta
	 */
	public long inserisciAttoRicevuta(	final int idUfficioOwn,
										final int codiKIdCatAttoRicevuta, 
										final String descUtente, 
										final long idProcedimento, 
										final long codiKIdNotifDest,
										final String codiKStatoTrasm, 
										final byte[] file,
										final String tipoDoc) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback<Long>() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {
				Blob blobFile = serializeInBlob(file);
				Object retValue =  session.getNamedQuery("PG_NOTIFICHE.INSERISCI_ATTO_RICEVUTA")
				.setParameter("IDUFFOWN_PARAM", idUfficioOwn, Hibernate.INTEGER)
				.setParameter("ID_CAT_ATTO_PARAM",codiKIdCatAttoRicevuta , Hibernate.INTEGER)
				.setParameter("DESCUTENTE_PARAM", descUtente, Hibernate.STRING)
				.setParameter("ID_PROCEDIMENTO_PARAM", idProcedimento, Hibernate.LONG)
				.setParameter("ID_NOTIF_DEST_PARAM", codiKIdNotifDest, Hibernate.LONG)
				.setParameter("CODI_K_STATO_TRASM_PARAM", codiKStatoTrasm, Hibernate.STRING)
				.setParameter("DOCUMENTO_PARAM", blobFile, Hibernate.BLOB)
				.setParameter("TIPO_DOC_PARAM", tipoDoc, Hibernate.STRING)
				
				.uniqueResult();
				
				if (retValue instanceof Long ) 
					return (Long)retValue;
				else return ((BigDecimal)retValue).longValue();
			}
		});
		
	}
	
	/***
	 * Inserimento dell'atto di tipo rapporto trasmissione
	 */
	public long inserisciAttoRappTrasm(	final int idUfficioOwn, 
										final int codiKIdCatAttoRicevuta, 
										final String descUtente, 
										final long idProcedimento, 
										final long codiKIdNotifDest,
										final String idPDocTrasmissione) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback<Long>() {

			public Long doInHibernate(final Session session) throws HibernateException, SQLException {
				Object retValue =  session.getNamedQuery("PG_NOTIFICHE.INSERISCI_ATTO_RAPP_TRASM")
				.setParameter("IDUFFOWN_PARAM", idUfficioOwn, Hibernate.INTEGER)
				.setParameter("ID_CAT_ATTO_PARAM",codiKIdCatAttoRicevuta , Hibernate.INTEGER)
				.setParameter("DESCUTENTE_PARAM", descUtente, Hibernate.STRING)
				.setParameter("ID_PROCEDIMENTO_PARAM", idProcedimento, Hibernate.LONG)
				.setParameter("ID_NOTIF_DEST_PARAM", codiKIdNotifDest, Hibernate.LONG)
				.setParameter("ID_PDOC_TRASMISSIONE", idPDocTrasmissione, Hibernate.STRING)
				.uniqueResult();
				
				if (retValue instanceof Long ) 
					return (Long)retValue;
				else return ((BigDecimal)retValue).longValue();
			}
		});
		
	}
	

	
	/**
	 * Crea l'oggetto di invocazione per ogni operation
	 */
	private Blob serializeInBlob(byte[] byteArray) {
		return Hibernate.createBlob(byteArray);
	}

	/**
	 * Esegue il marshaller per la response e la request del servizio
	 */
	private byte[] marshaller(Object request) {
		byte[] firmaBytes = new byte[1000];
		// ByteArrayInputStream bis = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			JAXBContext contextObj = JAXBContext.newInstance(request.getClass());
			Marshaller marshallerObj = contextObj.createMarshaller();
			marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshallerObj.marshal(request, bos);
			firmaBytes = bos.toByteArray();
			// bis = new ByteArrayInputStream(bos.toByteArray());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return firmaBytes;
	}
	
	public List<VNotifiche> getListaNotifichePerFiltro(int idUfficioMitt, int idTipoUff, NotificheEComunicazioniForm notificheEComunicazioniForm) {
		DetachedCriteria criteria = DetachedCriteria.forClass(VNotifiche.class);
		
		//P.Q. 10/07/2025 202507010116 
		criteria.add(Restrictions.eq("codiFkIdUffOwner", idUfficioMitt));
		//P.Q. 10/07/2025 202507010116 
		
		if (notificheEComunicazioniForm.getFiltroDataAl()!=null) {
			criteria.add(Restrictions.le("dataInvio", notificheEComunicazioniForm.getFiltroDataAl()));
		}
		if (notificheEComunicazioniForm.getFiltroDataDal()!=null) {
			criteria.add(Restrictions.ge("dataInvio", notificheEComunicazioniForm.getFiltroDataDal()));
		}
		if (notificheEComunicazioniForm.getAnnoProcedimento()!=null) {
			criteria.add(Restrictions.eq("numeAnnoProc", notificheEComunicazioniForm.getAnnoProcedimento()));
		}
		if (notificheEComunicazioniForm.getNumProcedimento()!=null) {
			criteria.add(Restrictions.eq("numeProc", notificheEComunicazioniForm.getNumProcedimento()));
		}
		
		if (notificheEComunicazioniForm.getTipoDestinatario()!=null && 
			notificheEComunicazioniForm.getTipoDestinatario().getId()!=null && 
			!notificheEComunicazioniForm.getTipoDestinatario().getId().equals("-1")) {
			criteria.add(Restrictions.eq(
					"codiFkRuoloDest", 
					Integer.parseInt(notificheEComunicazioniForm.getTipoDestinatario().getId())
				)
			);
		}
		
		
		criteria.addOrder(Order.asc("dataInvio"));
		//criteria.add(Restrictions.eq("idAttoNotificato", idAttoNotificato));
		//criteria.add(Restrictions.eq("idUffOwnerNotificato", idUfficioNotificato));
		@SuppressWarnings("unchecked")
		List<VNotifiche> lista = (List<VNotifiche>)getHibernateTemplate().findByCriteria(criteria,0,400);
		return lista;
	}

	
	/***
	 * Restituisce l'ufficio associato al codice giudiziario passato
	 */
	public TAnUfficio getUfficioByCodugne(String codugne) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAnUfficio.class)
				.add( Restrictions.eq( "descUffGiudiz", codugne ));
		@SuppressWarnings("unchecked")
		List<TAnUfficio> uffici = getHibernateTemplate().findByCriteria(criteria);
		if (uffici.size()>0) return uffici.get(0);
		return null;
	}

	/***
	 * Restituisce l'amministratore associato al codice fiscale passato
	 */
	public TAmministratori getAmminByCodiceFiscale(String codfisc) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TAmministratori.class)
				.add( Restrictions.eq( "descCodiceFiscale", codfisc ));
		@SuppressWarnings("unchecked")
		List<TAmministratori> ammin = getHibernateTemplate().findByCriteria(criteria);
		if (ammin.size()>0) return ammin.get(0);
		return null;
	}

	/***
	 * Inserisce il deposito nella tabella T_DEPOSITI se ancora non è stato inserito
	 */
	
	public void inserisciDepositoSeNonEsiste(
			final String idDepositoGl, 
			final String idMittenteString, 
			final String idDestinatariString, 
			final Integer idUfficioMittente,
			final Integer idUfficioDestinatario, 
			final Date dataRicezione,
			final String idDocumentoString) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TDepositi.class)
				.add( Restrictions.eq( "descIdDepositoGl", idDepositoGl ));
		@SuppressWarnings("unchecked")
		List<TDepositi> depositoCercato = getHibernateTemplate().findByCriteria(criteria);
		if (depositoCercato.size()==0) {
			getHibernateTemplate().execute(new HibernateCallback<Long>() {

				public Long doInHibernate(final Session session) throws HibernateException, SQLException {

					TDepositi tDeposito = new TDepositi();
					
					if (idUfficioDestinatario > 0) {
						TAnUfficio uffDest = new TAnUfficio();
						uffDest.setCodiKIdUfficio(idUfficioDestinatario);
						tDeposito.setTAnUfficioByCodiFkIdUffDes(uffDest);
					} else
						tDeposito.setTAnUfficioByCodiFkIdUffDes(null);
					if (idUfficioMittente != null) {
						TAnUfficio uffMitt = new TAnUfficio();
						uffMitt.setCodiKIdUfficio(idUfficioMittente);
						tDeposito.setTAnUfficioByCodiFkIdUffMit(uffMitt);
					} else
						tDeposito.setTAnUfficioByCodiFkIdUffMit(null);
					tDeposito.setDataRicezione(dataRicezione);
					tDeposito.setDescIdDepositoGl(idDepositoGl);
					tDeposito.setDescIdDestinatario(idDestinatariString);
					tDeposito.setDescIdMittente(idMittenteString);
					tDeposito.setDescUtente("JOB RicezioneDepositi");
					tDeposito.setFlgAccettato('N');
					tDeposito.setNumeCopiaPdoc(Long.parseLong(idDocumentoString));
					
					session.saveOrUpdate(tDeposito);
					session.flush();
					
					return tDeposito.getCodiKIdDeposito();
				}
				
			});
			System.out.println("Inserito deposito idDepositoGl:" + idDepositoGl);
		}
	}

	/***
	 * Inserisce il deposito nella tabella T_DEPOSITI_AMM se ancora non è stato inserito
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void inserisciDepositoAmm(
			final long idDeposito, 
			final TProcedimento tProcedimentoFg, 
			final TProcedimento tProcedimento,
			final TDepositi tDeposito,
			final Blob blobXml,
			final Blob blobPdf, 
			final String descTipoDoc, 
			final String descNomeDoc) {

		// inserisce solamente .... se gia' lo trova non fa nulla 
		DetachedCriteria criteria = DetachedCriteria.forClass(TDepositiAmm.class)
				.add( Restrictions.eq( "codiKIdDepositoAmm", idDeposito ));
		List<TDepositiAmm> depositoAmmCercato = getHibernateTemplate().findByCriteria(criteria);
		if (depositoAmmCercato.size()==0) {
			
			/*
			TDepositiAmm depositoAmm = new TDepositiAmm();
			depositoAmm.setCodiKIdDepositoAmm(idDeposito);
			depositoAmm.setTProcedimentoByCodiFkIdProcFg(tProcedimentoFg);
			depositoAmm.setTProcedimentoByCodiFkIdProc(tProcedimento);
			depositoAmm.setTDepositi(tDeposito);
			depositoAmm.setXmlToolAmm(blobXml);
			depositoAmm.setPdfToolAmm(blobPdf);
			depositoAmm.setDescTipoDocumento(descTipoDoc);
			depositoAmm.setDescNomeDocumento(descNomeDoc);
			depositoAmm.setFlgAcquisito('N');
			getHibernateTemplate().save(depositoAmm);
			getHibernateTemplate().flush();
			
			*/
			getHibernateTemplate().execute(new HibernateCallback() {
	
				public Object doInHibernate(final Session session) throws HibernateException, SQLException {
			
					Query q= session.getNamedQuery("PG_NOTIFICHE.INSERT_IN_DEPOSITI_AMM");
					q.setParameter("CODI_K_ID_DEPOSITO_AMM", idDeposito, Hibernate.LONG);
					q.setParameter("CODI_FK_ID_PROC_FG", tProcedimentoFg.getCodiKIdProc(), Hibernate.LONG);
					q.setParameter("CODI_FK_ID_PROC", tProcedimento.getCodiKIdProc(), Hibernate.LONG);
					q.setParameter("XML_TOOL_AMM", blobXml, Hibernate.BLOB);
					q.setParameter("PDF_TOOL_AMM", blobPdf, Hibernate.BLOB);
					q.setParameter("DESC_TIPO_DOCUMENTO", descTipoDoc, Hibernate.STRING);
					q.setParameter("DESC_NOME_DOCUMENTO", descNomeDoc, Hibernate.STRING);
					q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					Object retValue = q.uniqueResult();
					
					return retValue;
				}
			});
			
		}
	}

	public TDepositi getDepositoByGl(final String idDepositoGl) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TDepositi.class);
		criteria.add(Restrictions.eq("descIdDepositoGl", idDepositoGl));
		
		@SuppressWarnings("unchecked")
		List<TDepositi> result = getHibernateTemplate().findByCriteria(criteria);
		if (result.size() > 0)
		{
			return result.get(0);
		}
		else
			return null;
	}

	public TNotifAmm getTNotifAmmByid(long codiKIdNotifDest) {
		return getHibernateTemplate().get(TNotifAmm.class, codiKIdNotifDest);
	}
}
	
