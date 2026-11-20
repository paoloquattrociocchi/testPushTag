package it.giustizia.smp.service;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import it.giustizia.smp.dao.AttoDao;
import it.giustizia.smp.dao.CommonDao;
import it.giustizia.smp.dao.MisuraDao;
import it.giustizia.smp.dao.ProcedimentoDao;
import it.giustizia.smp.dao.SoggettoDao;
import it.giustizia.smp.dao.cameralini.ProcedimentoDaoCam;
import it.giustizia.smp.dto.KeyMessageDto;
import it.giustizia.smp.dto.pg_atto.DettaglioAttoDto;
import it.giustizia.smp.dto.pg_atto.ImpugnazioneCassellarioDTO;
import it.giustizia.smp.dto.pg_atto.RedazioneSchedaDto;
import it.giustizia.smp.dto.pg_cameralini.IntestatarioDto;
import it.giustizia.smp.dto.pg_notifiche.DestinatarioDto;
import it.giustizia.smp.dto.pg_procedimento.SogIntDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_procedimento.SogSpesePDettaglioProcedimentoDto;
import it.giustizia.smp.dto.pg_soggetto.DettaglioPersonaFisicaDto;
import it.giustizia.smp.dto.pg_stralcio.RicercaStralcioDto;
import it.giustizia.smp.form.AllegatoForm;
import it.giustizia.smp.form.AmministratoreForm;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.form.BeneStralcioForm;
import it.giustizia.smp.form.CertificatoPenaleForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.ComunicazioneForm;
import it.giustizia.smp.form.DifensoreForm;
import it.giustizia.smp.form.FirmaForm;
import it.giustizia.smp.form.ImpugnazioneForm;
import it.giustizia.smp.form.MagistratoForm;
import it.giustizia.smp.form.MisuraForm;
import it.giustizia.smp.form.NotificaAttoForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.form.SoggettoStralcioForm;
import it.giustizia.smp.form.SoggettoTitolareForm;
import it.giustizia.smp.form.UdienzaForm;
import it.giustizia.smp.model.TAmministratori;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnMagistrati;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.model.TAttoGenerico;
import it.giustizia.smp.model.TAttoId;
import it.giustizia.smp.model.TAvvocati;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TCertificatoPenale;
import it.giustizia.smp.model.TComunicazione;
import it.giustizia.smp.model.TContante;
import it.giustizia.smp.model.TDecreto;
import it.giustizia.smp.model.TDefIstGen;
import it.giustizia.smp.model.TDefIstGenId;
import it.giustizia.smp.model.TDenaro;
import it.giustizia.smp.model.TImpugnazione;
import it.giustizia.smp.model.TIstanza;
import it.giustizia.smp.model.TMisuraPers;
import it.giustizia.smp.model.TNotifDest;
import it.giustizia.smp.model.TNotifRicevute;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaFisicaId;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TPersonaGiurId;
import it.giustizia.smp.model.TProcedimento;
import it.giustizia.smp.model.TProvFg;
import it.giustizia.smp.model.TRelAtti;
import it.giustizia.smp.model.TRelAttoComunicaz;
import it.giustizia.smp.model.TRelAttoIstGen;
import it.giustizia.smp.model.TRelAttoMisPers;
import it.giustizia.smp.model.TRelAttoMisPersId;
import it.giustizia.smp.model.TRelAttoProc;
import it.giustizia.smp.model.TRelBeneAttoId;
import it.giustizia.smp.model.TRelMagAtto;
import it.giustizia.smp.model.TRelProcAttoAmm;
import it.giustizia.smp.model.TRelProcBene;
import it.giustizia.smp.model.TRelProcProc;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.model.TRelProcSogintAtto;
import it.giustizia.smp.model.TRelProcSogintAttoId;
import it.giustizia.smp.model.TRelProcSogintVer;
import it.giustizia.smp.model.TRelSoggImp;
import it.giustizia.smp.model.TRelSoggSpese;
import it.giustizia.smp.model.TSoggetto;
import it.giustizia.smp.model.TSoggettoId;
import it.giustizia.smp.model.TTpCatAtto;
import it.giustizia.smp.model.TTpSpeseProcessuali;
import it.giustizia.smp.model.TTpStatoAtto;
import it.giustizia.smp.model.TUdienza;
import it.giustizia.smp.model.TUdienzaId;
import it.giustizia.smp.service.cameralini.ProcedimentoServiceCam;
import it.giustizia.smp.service.webservices.IDocumentaleService;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.MappaStatiComunicazioneCasellario;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.client.documentale.model.general.stralcio.Documento;

/**
 * AttoServiceImpl
 * 
 * @author Almaviva S.p.A. classe del atto
 */

@Transactional(rollbackFor = Exception.class)
public class AttoServiceImpl implements AttoService {

	private AttoDao attoDao;
	@Autowired
	private Mapper mapperBean;
	@Autowired
	private MisuraService misuraService;
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private ProcedimentoDao procedimentoDao;
	@Autowired
	private ProcedimentoDaoCam procedimentoDaoCam;
	@Autowired
	private MisuraDao misuraDao;
	
	@Autowired
	private SoggettoDao soggettoDao;	

	@Autowired
	private IDocumentaleService documentaleService;
	@Autowired
	private ProcedimentoService procedimentoService;
	
	@Autowired
	private ProcedimentoServiceCam procedimentoServiceCam;

	@Autowired
	private CommonService commonService;
	@Autowired(required = true)
	private HttpServletRequest request;

	private Logger logger = Logger.getLogger(getClass());

	/**
	 * aggiornaIstanzaSoggettoDiffidato
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param attoForm
	 * @param codUtente
	 * @return long
	 */
	public Long aggiornaIstanzaSoggettoDiffidato(long idUffOwn, long idProcedimento, AttoForm attoForm, String codUtente) {

		TAtto atto = new TAtto();
		TIstanza istanza = new TIstanza();
		Utility.popolaDocumento(attoForm, atto);
		mapperBean.map(attoForm, atto);
		mapperBean.map(attoForm, istanza);
		long idDestinatario = Long.parseLong(attoForm.getDestinatario().getId());

		return attoDao.aggiornaIstanzaSoggettoDiffidato(idUffOwn, idDestinatario, idProcedimento, atto, codUtente, istanza);
	}

	public void aggiornaAccertamento(long idUfficio, long idProcedimento, AttoForm form, String codiceUtente, long idStatoProc) {

		TAtto atto = new TAtto();
		TIstanza istanza = new TIstanza();
		TAttoId idAtto = new TAttoId(form.getCodAtto(), (int) idUfficio);
		// copio dataRicezione in dataEmissione
		form.setDataEmissione(form.getDataInformativa());
		atto.setFileDocumento(form.getAllegato().getDocumento());
		mapperBean.map(form, atto);
		mapperBean.map(form, istanza);
		atto.setId(idAtto);
		attoDao.aggiornaAccertamento(idUfficio, atto, codiceUtente, idProcedimento, idStatoProc, istanza);
	}

	/**
	 * copiaMisBeneAtto
	 * 
	 * @param idProcedimento
	 * @param idAtto
	 * @param idBene
	 * @param idUffOwn
	 * @param idCodiceUtente
	 */
	public void copiaMisBeneAtto(long idProcedimento, long idAttoDecreto, long idBene, int idUffOwn, String idCodiceUtente, String flagRevoca,
			String nomeMetodo) {

		attoDao.copiaMisBeneAtto(idProcedimento, idAttoDecreto, idBene, idUffOwn, idCodiceUtente, flagRevoca, nomeMetodo);
	}

	/**
	 * definitivitaAttoRegistratoSalva
	 * 
	 * @param dataDefinitivita
	 * @param misurePatrimoniali
	 * @param misurePersonali
	 * @param idProcedimento
	 * @param idUfficioUtente
	 * @param idAtto
	 * @param tipoAtto
	 * @param codUtente
	 * @param tipo
	 * @return void
	 */
	public void definitivitaAttoRegistratoSalva(Date dataDefinitivita, List<BeneForm> misurePatrimoniali, List<SoggettoForm> misurePersonali,
			long idProcedimento, Integer idUfficioUtente, long idAtto, long tipoAtto, String codUtente, String tipo) {

		if (misurePatrimoniali != null && !misurePatrimoniali.isEmpty()) {
			for (BeneForm bene : misurePatrimoniali) {
				if (bene.getListaIdCheckboxString() != null) {
					for (String idSoggettiTitolareETitolo : bene.getListaIdCheckboxString()) {
						long idSoggetto = 0;
						long idInteresse = 0;
						long esito = 0;
						long tipoMis = 0;
						StringTokenizer st = new StringTokenizer(idSoggettiTitolareETitolo, ";");
						int numToken = st.countTokens();
						while (st.hasMoreTokens()) {
							idSoggetto = Long.valueOf(st.nextToken()).longValue();
							idInteresse = Long.valueOf(st.nextToken()).longValue();
							if(st.hasMoreTokens()) {
								esito = Long.valueOf(st.nextToken()).longValue();
							}
							if (numToken == 4)
								tipoMis = Long.valueOf(st.nextToken()).longValue();
						}
						attoDao.definitivitaMisPatrAttoRegistratoSalva(dataDefinitivita, bene.getIdBene(), idSoggetto, idInteresse, idProcedimento,
								idUfficioUtente, idAtto, tipoAtto, tipoMis, codUtente);
					}
				}
			}
		}
		if (misurePersonali != null && !misurePersonali.isEmpty()) {
			for (SoggettoForm sogg : misurePersonali) {
				if (sogg.getMisuraInfo() != null) {
					for (String misuraInfo : sogg.getMisuraInfo()) {
						long idMisura = 0;
						long tipoMisura = 0;
						StringTokenizer st = new StringTokenizer(misuraInfo, ";");
						while (st.hasMoreTokens()) {
							idMisura = Long.valueOf(st.nextToken()).longValue();
							tipoMisura = Long.valueOf(st.nextToken()).longValue();
						}
						attoDao.definitivitaMisPersAttoRegistratoSalva(dataDefinitivita, idProcedimento, idUfficioUtente, idAtto, idMisura,
								tipoMisura, tipoAtto, codUtente);
					}
				}
			}
		}

	}
	

	
	/**
	 * definitivitaCancella
	 * 
	 * @param  misurePatrimoniali
	 * @param  misurePersonali
	 * @param  idProcedimento
	 * @param  idUfficioUtente
	 * @param  idAtto
	 * @param  tipoAtto
	 * @param  codUtente
	 * @param  tipo
	 * @return void
	 */
	public void definitivitaCancella(List<BeneForm> misurePatrimoniali, List<SoggettoForm> misurePersonali,
			long idProcedimento, Integer idUfficioUtente, long idAtto, long tipoAtto, String codUtente, String tipo) {

		if (misurePatrimoniali != null && !misurePatrimoniali.isEmpty()) {
			for (BeneForm bene : misurePatrimoniali) {
				if (bene.getListaIdCheckboxString() != null) {
					for (String idSoggettiTitolareETitolo : bene.getListaIdCheckboxString()) {
						long idSoggetto = 0;
						long idInteresse = 0;
						long esito = 0;
						long tipoMis = 0;
						StringTokenizer st = new StringTokenizer(idSoggettiTitolareETitolo, ";");
						int numToken = st.countTokens();
						while (st.hasMoreTokens()) {
							idSoggetto = Long.valueOf(st.nextToken()).longValue();
							idInteresse = Long.valueOf(st.nextToken()).longValue();
							
							if(st.hasMoreTokens()) {
								esito = Long.valueOf(st.nextToken()).longValue();
							}
							
							if (numToken == 4)
								tipoMis = Long.valueOf(st.nextToken()).longValue();
						}						
			
						
						attoDao.definitivitaMisPatrCancella(bene.getIdBene(), idSoggetto, idInteresse, idProcedimento,
								idUfficioUtente, idAtto, tipoAtto, tipoMis, codUtente);
					}
				}
			}
		}
		if (misurePersonali != null && !misurePersonali.isEmpty()) {
			for (SoggettoForm sogg : misurePersonali) {
				if (sogg.getMisuraInfo() != null) {
					for (String misuraInfo : sogg.getMisuraInfo()) {
						long idMisura = 0;
						long tipoMisura = 0;
						StringTokenizer st = new StringTokenizer(misuraInfo, ";");
						while (st.hasMoreTokens()) {
							idMisura = Long.valueOf(st.nextToken()).longValue();
							tipoMisura = Long.valueOf(st.nextToken()).longValue();
						}
						attoDao.definitivitaMisPersCancella( idProcedimento, idUfficioUtente, idAtto, idMisura,
								tipoMisura, tipoAtto, codUtente);
					}
				}
			}
		}

	}	
	
	
	
	public void definitivitaFascGestCancella( List<BeneForm> misurePatrimoniali, 
			long idProcedimento, Integer idUfficioUtente, long idAtto, long tipoAtto, String codUtente, String tipo) {

		if (misurePatrimoniali != null && !misurePatrimoniali.isEmpty()) {
			for (BeneForm bene : misurePatrimoniali) {
				if (bene.getListaIdCheckboxString() != null) {
					for (String idSoggettiTitolareETitolo : bene.getListaIdCheckboxString()) {
						long idSoggetto = 0;						
						long idInteresse = 0;
						long esito = 0;
						long tipoMis = 0;
						StringTokenizer st = new StringTokenizer(idSoggettiTitolareETitolo, ";");
						int numToken = st.countTokens();
						while (st.hasMoreTokens()) {
							idSoggetto = Long.valueOf(st.nextToken()).longValue();
							idInteresse = Long.valueOf(st.nextToken()).longValue();
							if(st.hasMoreTokens()) {
								esito = Long.valueOf(st.nextToken()).longValue();
							}
							if (numToken == 4)
								tipoMis = Long.valueOf(st.nextToken()).longValue();
						}						
			
						//quella misura potrebbe non essere nel fascicolo di gestione ed andare in errore
						//quindi viene gestita l'eccezione
						try {
							attoDao.definitivitaMisPatrCancella(bene.getIdBene(), idSoggetto, idInteresse, idProcedimento,
									idUfficioUtente, idAtto, tipoAtto, tipoMis, codUtente);
						} catch(Exception e) {}						

					}
				}
			}
		}
		

	}	
	
	public void definitivitaFascGestAttoRegistratoSalva(Date dataDefinitivita, List<BeneForm> misurePatrimoniali, 
			long idProcedimento, Integer idUfficioUtente, long idAtto, long tipoAtto, String codUtente, String tipo) {

		if (misurePatrimoniali != null && !misurePatrimoniali.isEmpty()) {
			for (BeneForm bene : misurePatrimoniali) {
				if (bene.getListaIdCheckboxString() != null) {
					for (String idSoggettiTitolareETitolo : bene.getListaIdCheckboxString()) {
						long idSoggetto = 0;
						long idInteresse = 0;
						long esito = 0;
						long tipoMis = 0;
						StringTokenizer st = new StringTokenizer(idSoggettiTitolareETitolo, ";");
						int numToken = st.countTokens();
						while (st.hasMoreTokens()) {
							idSoggetto = Long.valueOf(st.nextToken()).longValue();
							idInteresse = Long.valueOf(st.nextToken()).longValue();
							if(st.hasMoreTokens()) {
								esito = Long.valueOf(st.nextToken()).longValue();
							}
							if (numToken == 4)
								tipoMis = Long.valueOf(st.nextToken()).longValue();
						}
						//quella misura potrebbe non essere nel fascicolo di gestione ed andare in errore
						//quindi viene gestita l'eccezione
						try {
							attoDao.definitivitaMisPatrAttoRegistratoSalva(dataDefinitivita, bene.getIdBene(), idSoggetto, idInteresse, idProcedimento,
									idUfficioUtente, idAtto, tipoAtto, tipoMis, codUtente);
						} catch(Exception e) {}
					}
				}
			}
		}
		

	}	

	/**
	 * definitivitaAttoRegistratoConfermaeInvia
	 * 
	 * @param idProcedimento
	 * @param idAtto
	 * @param idUfficioUtente
	 * @param idProcUdi
	 * @param idProcProp
	 * @param idProcGen
	 * @param codUtente
	 * @param aggiornaStatoProc
	 * @return void
	 */
	public void definitivitaAttoRegistratoConfermaeInvia(long idProcedimento, long idAtto, Integer idUfficioUtente, long idProcUdi, long idProcProp,
			long idProcGen, String codUtente, int aggiornaStatoProc) {

		attoDao.definitivitaAttoRegistratoConfermaInvia(idProcedimento, idAtto, idUfficioUtente.intValue(), idProcUdi, idProcProp, idProcGen,
				codUtente, aggiornaStatoProc);
		return;
	}
	
	public void definitivitaAttoRegistratoConfermaeInviaData(long idProcedimento, long idAtto, Integer idUfficioUtente, long idProcUdi, long idProcProp,
			long idProcGen, String codUtente, int aggiornaStatoProc, Date dataDef) {

		attoDao.definitivitaAttoRegistratoConfermaInviaData(idProcedimento, idAtto, idUfficioUtente.intValue(), idProcUdi, idProcProp, idProcGen,
				codUtente, aggiornaStatoProc, dataDef);
		return;
	}	
	
	public void definitivitaAttoRegistratoConfermaeInvia(long idProcedimento, long idAtto, Integer idUfficioUtente, long idProcUdi, long idProcProp,
			long idProcGen, String codUtente, int aggiornaStatoProc, Date dataDef) {

		attoDao.definitivitaAttoRegistratoConfermaInviaData(idProcedimento, idAtto, idUfficioUtente.intValue(), idProcUdi, idProcProp, idProcGen,
				codUtente, aggiornaStatoProc, dataDef);
		return;
	}	

	public void definitivitaRevocazioneConfiscaConfermaInvia(long idProcedimento, long idAtto, Integer idUffOwn, long idDest1, long idDest2,
			long idDest3, String codUtente, int aggiornaStatoProc) {

		attoDao.definitivitaRevocazioneConfiscaConfermaInvia(idProcedimento, idAtto, idUffOwn, idDest1, idDest2, idDest3, codUtente,
				aggiornaStatoProc);
		return;
	}

	public void definitivitaIIGradoConfermaInvia(long idProcedimento, long idAtto, Integer idUffOwn, long idDest1, long idDest2, long idDest3,
			String codUtente, int aggiornaStatoProc) {

		attoDao.definitivitaIIGradoConfermaInvia(idProcedimento, idAtto, idUffOwn, idDest1, idDest2, idDest3, codUtente, aggiornaStatoProc);
		return;
	}

	/**
	 * dettaglioAtto
	 * 
	 * @param idAtto
	 * @param idProcedimento
	 * @param codiceUtente
	 * @return AttoForm
	 */
	public AttoForm dettaglioAtto(int idUffOwn, long idAtto, long idProcedimento, String codiceUtente) {

		AttoForm attoForm = new AttoForm();
		DettaglioAttoDto risultato = attoDao.dettaglioAtto(idUffOwn, idAtto, idProcedimento, codiceUtente);

		if (risultato != null) {
			mapperBean.map(risultato, attoForm);

			// al momento inserisco n.d.
			// attoForm.setNumeroAtto(Constants.FormattazioneAnnoNumeroAtto.NUMERO_ATTO_NON_DISP);

			FirmaForm ff = new FirmaForm();
			if ("S".equalsIgnoreCase(attoForm.getAllegato().getDocumentoFirmato())) {
				ff.setFirmaImmediata(true);
			} else {
				ff.setFirmaImmediata(false);
			}
			attoForm.getAllegato().setFirmaRemota(ff);

			if (Utility.isTIstanzaRecord(attoForm.getTipoAtto().getId())) {

				Criterion c1 = Restrictions.eq("id.codiKIdIstanza", idAtto);
				Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", idUffOwn);
				Object obj = attoDao.getAttoByPrimaryKey(TIstanza.class, c1, c2);
				if (obj != null) {
					TIstanza record = (TIstanza) obj;
					// TODO sostituire con mapping nel xml
					if (record.getDescRichiesta() != null) {
						attoForm.setNote(record.getDescRichiesta());
					}
					if (record.getDescAltroMod() != null) {
						attoForm.setDescAltroMod(record.getDescAltroMod());
					}
					attoForm.setAltroOrgano(record.getDescAltroOrgano());

					if (record.getTAnUfficio() != null) {
						attoForm.setIndirizzoOrgano(new ComboForm(String.valueOf(record.getTAnUfficio().getCodiKIdUfficio()), null));

						if (record.getTAnUfficio().getTTpTipoUfficio() != null)
							attoForm.setOrgano(new ComboForm(String.valueOf(record.getTAnUfficio().getTTpTipoUfficio().getCodiKIdTipoUff()), null));

						if (record.getTAnUfficio().getTAnComuni() != null) {
							attoForm.setComuneOrgano(new ComboForm(String.valueOf(record.getTAnUfficio().getTAnComuni().getCodiKIdComune()), null));
							attoForm.setProvinciaOrgano(new ComboForm(record.getTAnUfficio().getTAnComuni().getTAnProvincia().getCodiKIdIstatProv(),
									null));
						}
					}
					
					if (record.getDescCognProp() != null) {
						attoForm.setCognProponente(record.getDescCognProp());
					}
					
					if (record.getDescNomeProp() != null) {
						attoForm.setNomeProponente(record.getDescNomeProp());
					}
					
					if (record.getDescTipoProp() != null) {
						attoForm.setTipoProponente(new ComboForm(record.getDescTipoProp(), null));
					}		
					
					if (record.getDataSpedizione() != null) {
						attoForm.setPeriodoRifDal(record.getDataSpedizione());
					}	
					
					if (record.getDataPervIst() != null) {
						attoForm.setPeriodoRifAl(record.getDataPervIst());
					}	
					
					
				}
			}

			if (Utility.isTAttoGenericoRecord(attoForm.getTipoAtto().getId())) {

				Criterion c1 = Restrictions.eq("id.codiKIdAttoGen", idAtto);
				Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", idUffOwn);
				Object obj = attoDao.getAttoByPrimaryKey(TAttoGenerico.class, c1, c2);
				if (obj != null) {
					TAttoGenerico record = (TAttoGenerico) obj;

					if (record.getTAnUfficio() != null) {
						attoForm.setIndirizzoOrgano(new ComboForm(String.valueOf(record.getTAnUfficio().getCodiKIdUfficio()), null));

						if (record.getTAnUfficio().getTTpTipoUfficio() != null)
							attoForm.setOrgano(new ComboForm(String.valueOf(record.getTAnUfficio().getTTpTipoUfficio().getCodiKIdTipoUff()), null));

						if (record.getTAnUfficio().getTAnComuni() != null) {
							attoForm.setComuneOrgano(new ComboForm(String.valueOf(record.getTAnUfficio().getTAnComuni().getCodiKIdComune()), null));
							attoForm.setProvinciaOrgano(new ComboForm(record.getTAnUfficio().getTAnComuni().getTAnProvincia().getCodiKIdIstatProv(),
									null));
						}
					}
				}
			}
			
			if (Utility.isTProvvFGRecord(attoForm.getTipoAtto().getId())) {

				Criterion c1 = Restrictions.eq("id.codiKIdProvFg", idAtto);
				Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", idUffOwn);
				Object obj = attoDao.getAttoByPrimaryKey(TProvFg.class, c1, c2);
				if (obj != null) {
					TProvFg record = (TProvFg) obj;
					
					attoForm.setDataUdienza(record.getDataUdienza());
					attoForm.setEsitoProvv(record.getEsito());
					attoForm.setDescEsitoProvv(record.getDescEsito());
					attoForm.setFlgImp(record.getFlgImp());
					attoForm.setDescEsitoImp(record.getDescEsitoImp());
					attoForm.setFlgCas(record.getFlgCas());
					attoForm.setDescEsitoCas(record.getDescEsitoCas());
					attoForm.setDescNote(record.getDescNote());
				}
			}			
			
		}

		return attoForm;
	}

	/**
	 * eliminaMisBeneAtto
	 * 
	 * @param idProcedimento
	 * @param idAtto
	 * @param idBene
	 * @param idUffOwn
	 * @param idPersona
	 * @param idTitoloInt
	 */
	public void eliminaMisBeneAtto(long idProcedimento, long idAtto, long idBene, int idUffOwn, long idPersona, long idTitoloInt) {

		attoDao.eliminaMisBeneAtto(idProcedimento, idAtto, idBene, idUffOwn, idPersona, idTitoloInt);
	}

	/**
	 * eliminaRelMagAtto
	 * 
	 * @param idUffOwn
	 * @param idAtto
	 * @param idMagistrato
	 * @param descRuolo
	 */
	public void eliminaRelMagAtto(Integer idUffOwn, long idAtto, long idMagistrato, String descRuolo) {

		attoDao.eliminaRelMagAtto(idUffOwn, idAtto, idMagistrato, descRuolo);
	}

	/**
	 * esecuzioneAttoRegistrato
	 * 
	 * @param dataEsecuzione
	 * @param beniSelezionati
	 * @param idProcedimento
	 * @param idUfficioUtente
	 * @param idAtto
	 * @param codUtente
	 */
	public void esecuzioneAttoRegistrato(Date dataEsecuzione, List<BeneForm> beniSelezionati, long idProcedimento, int idUfficioUtente, long idAtto,
			String codUtente) {

		for (BeneForm bene : beniSelezionati) {
			for (String idSoggettiTitolareETitolo : bene.getListaIdCheckboxString()) {
				String idSoggetto = null;
				String idInteresse = null;
				StringTokenizer st = new StringTokenizer(idSoggettiTitolareETitolo, ";");
				while (st.hasMoreTokens()) {
					idSoggetto = st.nextToken();
					idInteresse = st.nextToken();
				}
				attoDao.esecuzioneAttoRegistrato(dataEsecuzione, bene.getIdBene(), Long.parseLong(idSoggetto), Long.parseLong(idInteresse),
						idProcedimento, idUfficioUtente, idAtto, codUtente);
			}
		}
	}

	/**
	 * getAttiByProcedimento
	 * 
	 * @param idProcedimento
	 * @param tipoAtto
	 * @param listaCategoriaAtto
	 * @return List<AttoForm>
	 */
	public List<AttoForm> getAttiByProcedimento(long idProcedimento, long tipoAtto, String listaCategoriaAtto) {

		List<AttoForm> results = new ArrayList<AttoForm>();
		List daoResults = attoDao.getAttiByProcedimento(idProcedimento, tipoAtto, listaCategoriaAtto);
		Iterator iter = daoResults.iterator();
		while (iter.hasNext()) {
			AttoForm currentForm = new AttoForm();
			Map map = (Map) iter.next();
			TAtto currentFonte = (TAtto) map.get("atto");
			TTpCatAtto currentCategoriaFonte = (TTpCatAtto) map.get("cat_atto");
			TTpStatoAtto statoAtto = (TTpStatoAtto) map.get("stato_atto");
			ComboForm statoAttoForm = new ComboForm();
			ComboForm fonte = new ComboForm();
			mapperBean.map(currentFonte, currentForm);
			mapperBean.map(currentCategoriaFonte, fonte);
			mapperBean.map(statoAtto, statoAttoForm);
			currentForm.setStatoAtto(statoAttoForm);
			currentForm.setDataInformativa(currentForm.getDataEmissione());
			currentForm.setFonte(fonte);
			currentForm.setIdProcedimento(idProcedimento);
			currentForm.setIdUfficio(currentFonte.getTAnUfficio().getCodiKIdUfficio());
			results.add(currentForm);
		}
		return results;
	}
	
	
	/**
	 * getAttiOpposizioneDissequestro
	 * 
	 * @param idProcedimento
	 * @return List<AttoForm>
	 */
	public List<AttoForm> getAttiOpposizioneDissequestro(long idProcedimento) {
		List<AttoForm> results = new ArrayList<AttoForm>();
		List daoResults = attoDao.getAttiOpposizioneDissequestro(idProcedimento);
		Iterator iter = daoResults.iterator();
		while (iter.hasNext()) {
			AttoForm currentForm = new AttoForm();
			Map map = (Map) iter.next();
			TAtto currentFonte = (TAtto) map.get("atto");
			TTpCatAtto currentCategoriaFonte = (TTpCatAtto) map.get("cat_atto");
			TTpStatoAtto statoAtto = (TTpStatoAtto) map.get("stato_atto");
			ComboForm statoAttoForm = new ComboForm();
			ComboForm fonte = new ComboForm();
			mapperBean.map(currentFonte, currentForm);
			mapperBean.map(currentCategoriaFonte, fonte);
			mapperBean.map(statoAtto, statoAttoForm);
			currentForm.setStatoAtto(statoAttoForm);
			currentForm.setDataInformativa(currentForm.getDataEmissione());
			currentForm.setFonte(fonte);
			results.add(currentForm);
		}
		return results;
	}

	
	/**
	 * getAttiPerditaEfficaciaSequestro
	 * 
	 * @param idProcedimento
	 * @return List<AttoForm>
	 */
	public List<AttoForm> getAttiPerditaEfficaciaSequestro(long idProcedimento) {
		List<AttoForm> results = new ArrayList<AttoForm>();
		List daoResults = attoDao.getAttiPerditaEfficaciaSequestro(idProcedimento);
		Iterator iter = daoResults.iterator();
		while (iter.hasNext()) {
			AttoForm currentForm = new AttoForm();
			Map map = (Map) iter.next();
			TAtto currentFonte = (TAtto) map.get("atto");
			TTpCatAtto currentCategoriaFonte = (TTpCatAtto) map.get("cat_atto");
			TTpStatoAtto statoAtto = (TTpStatoAtto) map.get("stato_atto");
			ComboForm statoAttoForm = new ComboForm();
			ComboForm fonte = new ComboForm();
			mapperBean.map(currentFonte, currentForm);
			mapperBean.map(currentCategoriaFonte, fonte);
			mapperBean.map(statoAtto, statoAttoForm);
			currentForm.setStatoAtto(statoAttoForm);
			currentForm.setDataInformativa(currentForm.getDataEmissione());
			currentForm.setFonte(fonte);
			results.add(currentForm);
		}
		return results;
	}	
	
	/**
	 * getAttiRevocaOpposizioneDissequestro
	 * 
	 * @param idProcedimento
	 * @return List<AttoForm>
	 */
	public List<AttoForm> getAttiRevocaOpposizioneDissequestro(long idProcedimento) {
		List<AttoForm> results = new ArrayList<AttoForm>();
		List daoResults = attoDao.getAttiRevocaOpposizioneDissequestro(idProcedimento);
		Iterator iter = daoResults.iterator();
		while (iter.hasNext()) {
			AttoForm currentForm = new AttoForm();
			Map map = (Map) iter.next();
			TAtto currentFonte = (TAtto) map.get("atto");
			TTpCatAtto currentCategoriaFonte = (TTpCatAtto) map.get("cat_atto");
			TTpStatoAtto statoAtto = (TTpStatoAtto) map.get("stato_atto");
			ComboForm statoAttoForm = new ComboForm();
			ComboForm fonte = new ComboForm();
			mapperBean.map(currentFonte, currentForm);
			mapperBean.map(currentCategoriaFonte, fonte);
			mapperBean.map(statoAtto, statoAttoForm);
			currentForm.setStatoAtto(statoAttoForm);
			currentForm.setDataInformativa(currentForm.getDataEmissione());
			currentForm.setFonte(fonte);
			results.add(currentForm);
		}
		return results;
	}

	/**
	 * getAttiRevocazioneConfisca
	 * 
	 * @param idProcedimento
	 * @return List<AttoForm>
	 */
	public List<AttoForm> getAttiRevocazioneConfisca(long idProcedimento) {
		List<AttoForm> results = new ArrayList<AttoForm>();
		List daoResults = attoDao.getAttiRevocazioneConfisca(idProcedimento);
		Iterator iter = daoResults.iterator();
		while (iter.hasNext()) {
			AttoForm currentForm = new AttoForm();
			Map map = (Map) iter.next();
			TAtto currentFonte = (TAtto) map.get("atto");
			TTpCatAtto currentCategoriaFonte = (TTpCatAtto) map.get("cat_atto");
			TTpStatoAtto statoAtto = (TTpStatoAtto) map.get("stato_atto");
			ComboForm statoAttoForm = new ComboForm();
			ComboForm fonte = new ComboForm();
			mapperBean.map(currentFonte, currentForm);
			mapperBean.map(currentCategoriaFonte, fonte);
			mapperBean.map(statoAtto, statoAttoForm);
			currentForm.setStatoAtto(statoAttoForm);
			currentForm.setDataInformativa(currentForm.getDataEmissione());
			currentForm.setFonte(fonte);
			results.add(currentForm);
		}
		return results;
	}

	/**
	 * getAttiParere
	 * 
	 * @param idProcedimento
	 * @return List<AttoForm>
	 */
	public List<AttoForm> getAttiParere(long idProcedimento) {
		List<AttoForm> results = new ArrayList<AttoForm>();
		List daoResults = attoDao.getAttiParere(idProcedimento);
		Iterator iter = daoResults.iterator();
		while (iter.hasNext()) {
			AttoForm currentForm = new AttoForm();
			Map map = (Map) iter.next();
			TAtto currentFonte = (TAtto) map.get("atto");
			TTpCatAtto currentCategoriaFonte = (TTpCatAtto) map.get("cat_atto");
			TTpStatoAtto statoAtto = (TTpStatoAtto) map.get("stato_atto");
			ComboForm statoAttoForm = new ComboForm();
			ComboForm fonte = new ComboForm();
			mapperBean.map(currentFonte, currentForm);
			mapperBean.map(currentCategoriaFonte, fonte);
			mapperBean.map(statoAtto, statoAttoForm);
			currentForm.setStatoAtto(statoAttoForm);
			currentForm.setDataInformativa(currentForm.getDataEmissione());
			currentForm.setFonte(fonte);
			results.add(currentForm);
		}
		return results;
	}

	/**
	 * getAttiOrdineCronologico
	 * 
	 * @param idProcedimento
	 */
	public List<AttoForm> getAttiOrdineCronologico(long idProcedimento, String codiceUtente, String nomeMetodo, Boolean filtraRicevute) {

		// Controllo su T_REL_PROC_PROC se per il procedimento corrente esistono
		// due record con lo stesso CODI_FK_UFF_OWNER2
		// PROCEDURA: restituisce una lista listaRelProcProc di T_REL_PROC_PROC
		List<TRelProcProc> listaRelProcProc = procedimentoService.getTRelProcProcByProc1(idProcedimento, null, codiceUtente, nomeMetodo);
		// controllo se esistono due record con lo stesso CODI_FK_UFF_OWNER2
		boolean flag = esisteProcConUffOwnerUguale(listaRelProcProc);
		if (filtraRicevute == null) {
			filtraRicevute = false;
		}
		List<AttoForm> results = new ArrayList<AttoForm>();
		List daoResults = attoDao.getAttiOrdineCronologico(idProcedimento);
		Iterator iter = daoResults.iterator();
		while (iter.hasNext()) {
			AttoForm currentForm = new AttoForm();
			Map map = (Map) iter.next();
			TAtto currentAtto = (TAtto) map.get("atto");
			if (!(filtraRicevute && currentAtto.getTTpTipoAtto().getCodiKIdTipoAtto() == Constants.TipoAtto.TIPO_ATTO_RICEVUTA)) {
				TTpCatAtto currentCategoria = (TTpCatAtto) map.get("cat_atto");
				TTpStatoAtto statoAtto = (TTpStatoAtto) map.get("stato_atto");
	
				String numeroProcedStralcio = "";
				// se caso sopra...
				if (flag) {
					// cerco nella listaRelProcProc se esiste un CODI_K_ID_PROC2 =
					// codiIdProcEmiss (dell'atto che sto ciclando)
					// ==>se esiste prendo il NUME_ANNO_PROC2 / NUME_PROCEDIMENTO2
					// (e lo accodo alla descrizione dell'atto)
					for (Iterator<TRelProcProc> iterator = listaRelProcProc.iterator(); iterator.hasNext();) {
						Map<String, Object> row = (Map<String, Object>) iterator.next();
						TRelProcProc tRelProcProc = (TRelProcProc) row.get("relProcProc");
	
						if (currentAtto.getCodiIdProcEmiss()!=null && (  tRelProcProc.getId().getCodiKIdProc2() == currentAtto.getCodiIdProcEmiss())) {
							String numProvProc = getNumeroAnnoNumeroAtto(String.valueOf(tRelProcProc.getNumeAnnoProc2()),
									String.valueOf(tRelProcProc.getNumeProcedimento2()));
							ComboForm tribunale = commonService.getDettaglioUfficio(tRelProcProc.getTAnUfficio().getCodiKIdUfficio());
	
							numeroProcedStralcio = " (Emesso nel proc. " + numProvProc + " - " + tribunale.getDescrizione() + ")";
							currentCategoria.setDescCatAtto(currentCategoria.getDescCatAtto());
							break;
						}
					}
				}
	
				ComboForm statoAttoForm = new ComboForm();
				ComboForm catAttoForm = new ComboForm();
				mapperBean.map(currentAtto, currentForm);
				mapperBean.map(currentCategoria, catAttoForm);
				mapperBean.map(statoAtto, statoAttoForm);
				currentForm.setStatoAtto(statoAttoForm);
				// currentForm.setDataInformativa(currentForm.getDataEmissione());
				currentForm.setCategoriaAtto(catAttoForm);
	
				// al momento inserisco n.d.
				currentForm.setNumeroAtto(Constants.FormattazioneAnnoNumeroAtto.NUMERO_ATTO_NON_DISP);
	
				// associo all'atto l'eventuale procedimento di origine
				currentForm.setNumeroProcedimentoOrigine(numeroProcedStralcio);
				//System.out.println("Current atto type" + currentForm.getTipoAtto().getId());
				if (Integer.parseInt(currentForm.getTipoAtto().getId()) == Constants.TipoAtto.TIPO_ATTO_RICEVUTA) {
					// Aggiunto le informazione dell'atto notificato
					
					NotificaAttoForm attoNotificato =  this.getAttoNoficatoDaRicevuta(currentAtto.getId().getCodiKIdUffOwner(), currentAtto.getId().getCodiKIdAtto(),idProcedimento, codiceUtente, currentAtto.getTTpCatAtto().getCodiKIdCatAtto());
					if (attoNotificato.getListaDestinatari().size() > 0) {
						String destinatario = "&nbsp;&nbsp; <img src=\"" + request.getContextPath() + "/images/destinatario.png\" title=\"" + attoNotificato.getListaDestinatari().get(0).getDESC_DEST().replaceAll("\"", "") + "\"/>";
						currentForm.setDescInfoScheda("<table><tr><td valign='middle'> / " + attoNotificato.getCategoriaAtto().getDescrizione()+ "</td><td valign='middle'>" + destinatario + "</td></tr></table>");
					}	
					results.add(currentForm);
				} else  if (currentForm.getCategoriaAtto().getId().equals(Constants.PROPOSTA) || 
						   currentForm.getCategoriaAtto().getId().equals(Constants.ISTANZA_MODIFICA_PERSONALE)||
						   currentForm.getCategoriaAtto().getId().equals(Constants.ISTANZA_MODIFICA_PATRIMONIALE)) {
					int idTipoFascicolo = 0;
					if (request.getSession().getAttribute(Constants.TIPO_FASCICOLO) != null) {
						Object idTipoFascOBJ = request.getSession().getAttribute(Constants.TIPO_FASCICOLO);
						if (idTipoFascOBJ instanceof String)
							idTipoFascicolo = Integer.valueOf((String) idTipoFascOBJ);
						else
							idTipoFascicolo = (Integer) idTipoFascOBJ;
					} else {
						ProcedimentoForm procedimentoForm = procedimentoService.getDettaglioProcedimento(idProcedimento);
						idTipoFascicolo = Integer.valueOf(procedimentoForm.getTipoFascicolo().getId());
					}
					if (idTipoFascicolo == Constants.TIPO_FASCICOLO_AGGRAVAMENTO) {
						ProcedimentoForm procedimento = procedimentoService.getDettaglioProcedimento(idProcedimento);
	
						// Solo per l'ufficio Procura, se lo stato del procedimento
						// è ancora iscritto o in lavorazione non devo vedere la
						// proposta
						boolean proposta = true;
						ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute("profilo");
						if (profilo.getIdTipoUfficio() == new Long(Constants.TIPO_UFFICIO_PROCURA)
								&& (Long.valueOf(procedimento.getStatoProcedimento().getId()) == Constants.STATOPROC_IN_LAVORAZIONE || procedimento
										.getStatoProcedimento().getId().equals(Constants.STATOPROC_ISCRITTO))) {
							proposta = false;
						}
						if (proposta) {
							results.add(currentForm);
						}
					} else {
						if (commonDao.existMisurePersonali(idProcedimento, currentForm.getCodAtto())
								|| commonDao.existMisurePatrimoniali(idProcedimento, currentForm.getCodAtto())) {
							results.add(currentForm);
						}
					}
			
				} else if (Utility.isTDecretoRecord(currentForm.getTipoAtto().getId())) {
	
					Criterion c1 = Restrictions.eq("id.codiKIdDecreto", currentAtto.getId().getCodiKIdAtto());
					Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", currentAtto.getId().getCodiKIdUffOwner());
					Object obj = attoDao.getAttoByPrimaryKey(TDecreto.class, c1, c2);
					if (obj != null) {
						TDecreto ist = (TDecreto) obj;
						String numAttoView = builNumeroAttoView(ist);
						if (ist.getNumeAnnoAtto() != null) {
							currentForm.setNumeAnnoAtto(ist.getNumeAnnoAtto().toString());
						}
						if (ist.getNumeAtto() != null) {
							currentForm.setNumeAtto(ist.getNumeAtto().toString());
						}
						currentForm.setNumeroAtto(numAttoView);
					}
	
					results.add(currentForm);
				} else if (Utility.isTImpugnazioneRecord(currentForm.getTipoAtto().getId())) {
	
					Criterion c1 = Restrictions.eq("id.codiKIdImpugnaz", currentAtto.getId().getCodiKIdAtto());
					Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", currentAtto.getId().getCodiKIdUffOwner());
					Object obj = attoDao.getAttoByPrimaryKey(TImpugnazione.class, c1, c2);
					if (obj != null) {
						TImpugnazione ist = (TImpugnazione) obj;
						String numAttoView = builNumeroAttoView(ist);
						if (ist.getNumeAnnoAtto() != null) {
							currentForm.setNumeAnnoAtto(ist.getNumeAnnoAtto().toString());
						}
						if (ist.getNumeAtto() != null) {
							currentForm.setNumeAtto(ist.getNumeAtto().toString());
						}
						currentForm.setNumeroAtto(numAttoView);
					}
	
					results.add(currentForm);
				} else if (Utility.isTProvvFGRecord(currentForm.getTipoAtto().getId())) {
					
					Criterion c1 = Restrictions.eq("id.codiKIdProvFg", currentAtto.getId().getCodiKIdAtto());
					Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", currentAtto.getId().getCodiKIdUffOwner());
					Object obj = attoDao.getAttoByPrimaryKey(TProvFg.class, c1, c2);
					if (obj != null) {
						TProvFg ist = (TProvFg) obj;
						String numAttoView = builNumeroAttoView(ist);
						if (ist.getNumeAnnoProvFg() != null) {
							currentForm.setNumeAnnoAtto(ist.getNumeAnnoProvFg().toString());
						}
						if (ist.getNumeProvFg() != null) {
							currentForm.setNumeAtto(ist.getNumeProvFg().toString());
						}
						currentForm.setNumeroAtto(numAttoView);
					}
	
					results.add(currentForm);
				} else if (currentForm.getCategoriaAtto().getId().equals(Constants.REDAZIONE_SCHEDA) || currentForm.getCategoriaAtto().getId().equals(Constants.FOGLIO_COMPLEMENTARE)  ) {
					// Aggiunto nome e cognome del soggetto nel caso di redazione scheda
					ComunicazioneForm comunicazioneForm = getComunicazioneByIdAtto(idProcedimento, currentAtto.getId().getCodiKIdAtto(), currentAtto.getId().getCodiKIdUffOwner());
					currentForm.setDescInfoScheda( comunicazioneForm.getDescSoggetto() + " / " + comunicazioneForm.getDescInvioComunicazione());
					results.add(currentForm);
				} else if (currentForm.getCategoriaAtto().getId().equals(Constants.CERTIFICATO_PENALE)) {
					// Aggiunto nome e cognome del soggetto nel caso di certificato penale
					CertificatoPenaleForm certificatoPenaleForm = getCertificatoPenaleByIdAtto(idProcedimento, currentAtto.getId().getCodiKIdAtto(),  currentAtto.getId().getCodiKIdUffOwner());
					currentForm.setDescInfoScheda(" (" + certificatoPenaleForm.getDescSoggetto() + ")");
					results.add(currentForm);
				}else if (currentForm.getCategoriaAtto().getId().equals(Constants.ATTO_VERSIONAMENTO_SOGGETTO)) {	
					// Aggiunto nome e cognome del soggetto versionato
		
					TRelProcSogintVer sogintVer = new TRelProcSogintVer();
					sogintVer = attoDao.getTRelProcSogintVerByAtto(currentAtto.getId().getCodiKIdAtto(), currentAtto.getId().getCodiKIdUffOwner());
	
					SoggettoForm soggettoReturn = new SoggettoForm();
					String lTipoSoggetto = sogintVer.getTSoggetto().getTTpTipoSoggetto().getCodiKIdTipoSogg()+"";
					
					if(Constants.SOGGETTO_FISICO.equalsIgnoreCase(lTipoSoggetto)){
						TPersonaFisicaId id = new TPersonaFisicaId();
						id.setCodiKIdUffOwner(sogintVer.getId().getCodiKIdUffOwner());
						id.setCodiKIdPersonaFis(sogintVer.getId().getCodiKIdSoggInt());
						id.setCodiKIdVer(sogintVer.getId().getCodiKIdVer());
						DettaglioPersonaFisicaDto personaFisicaDto = soggettoDao.getDettaglioPersonaFisicaVer(id);
		
						mapperBean.map(personaFisicaDto, soggettoReturn);
					
					}else if(Constants.SOGGETTO_GIURIDICO.equalsIgnoreCase(lTipoSoggetto)){ 
						TPersonaGiurId id = new TPersonaGiurId();
						id.setCodiKIdUffOwner(sogintVer.getId().getCodiKIdUffOwner());
						id.setCodiKIdPersGiur(sogintVer.getId().getCodiKIdSoggInt());
						id.setCodiKIdVer(sogintVer.getId().getCodiKIdVer());
						Map<String, Object> risultato = soggettoDao.getDettaglioPersonaGiuVer(id);
						TPersonaGiur personaG = (TPersonaGiur) risultato.get("p_giur");
					
						mapperBean.map(personaG, soggettoReturn);
						
					}
					currentForm.setDescVersionamento( " " + soggettoReturn.getDescrizioneSoggettoConVersione());
					results.add(currentForm);				
				} else {
					results.add(currentForm);
				}
			}
		}
		return results;
	}

	
	public List<AttoForm> getElencoAttiTribunale(long idProcedimento, String codiceUtente, String nomeMetodo, Boolean filtraRicevute) {

		// Controllo su T_REL_PROC_PROC se per il procedimento corrente esistono
		// due record con lo stesso CODI_FK_UFF_OWNER2
		// PROCEDURA: restituisce una lista listaRelProcProc di T_REL_PROC_PROC
		List<TRelProcProc> listaRelProcProc = procedimentoService.getTRelProcProcByProc1(idProcedimento, null, codiceUtente, nomeMetodo);
		// controllo se esistono due record con lo stesso CODI_FK_UFF_OWNER2
		boolean flag = esisteProcConUffOwnerUguale(listaRelProcProc);
		if (filtraRicevute == null) {
			filtraRicevute = false;
		}
		List<AttoForm> results = new ArrayList<AttoForm>();
		List daoResults = attoDao.getAttiOrdineCronologico(idProcedimento);
		Iterator iter = daoResults.iterator();
		while (iter.hasNext()) {
			AttoForm currentForm = new AttoForm();
			Map map = (Map) iter.next();
			TAtto currentAtto = (TAtto) map.get("atto");
			if (!(filtraRicevute && currentAtto.getTTpTipoAtto().getCodiKIdTipoAtto() == Constants.TipoAtto.TIPO_ATTO_RICEVUTA)) {
				TTpCatAtto currentCategoria = (TTpCatAtto) map.get("cat_atto");
				TTpStatoAtto statoAtto = (TTpStatoAtto) map.get("stato_atto");
	
				String numeroProcedStralcio = "";
				// se caso sopra...
				if (flag) {
					// cerco nella listaRelProcProc se esiste un CODI_K_ID_PROC2 =
					// codiIdProcEmiss (dell'atto che sto ciclando)
					// ==>se esiste prendo il NUME_ANNO_PROC2 / NUME_PROCEDIMENTO2
					// (e lo accodo alla descrizione dell'atto)
					for (Iterator<TRelProcProc> iterator = listaRelProcProc.iterator(); iterator.hasNext();) {
						Map<String, Object> row = (Map<String, Object>) iterator.next();
						TRelProcProc tRelProcProc = (TRelProcProc) row.get("relProcProc");
	
						if (tRelProcProc.getId().getCodiKIdProc2() == currentAtto.getCodiIdProcEmiss()) {
							String numProvProc = getNumeroAnnoNumeroAtto(String.valueOf(tRelProcProc.getNumeAnnoProc2()),
									String.valueOf(tRelProcProc.getNumeProcedimento2()));
							ComboForm tribunale = commonService.getDettaglioUfficio(tRelProcProc.getTAnUfficio().getCodiKIdUfficio());
	
							numeroProcedStralcio = " (Emesso nel proc. " + numProvProc + " - " + tribunale.getDescrizione() + ")";
							currentCategoria.setDescCatAtto(currentCategoria.getDescCatAtto());
							break;
						}
					}
				}
	
				ComboForm statoAttoForm = new ComboForm();
				ComboForm catAttoForm = new ComboForm();
				mapperBean.map(currentAtto, currentForm);
				mapperBean.map(currentCategoria, catAttoForm);
				mapperBean.map(statoAtto, statoAttoForm);
				currentForm.setStatoAtto(statoAttoForm);
				// currentForm.setDataInformativa(currentForm.getDataEmissione());
				currentForm.setCategoriaAtto(catAttoForm);
	
				// al momento inserisco n.d.
				currentForm.setNumeroAtto(Constants.FormattazioneAnnoNumeroAtto.NUMERO_ATTO_NON_DISP);
	
				// associo all'atto l'eventuale procedimento di origine
				currentForm.setNumeroProcedimentoOrigine(numeroProcedStralcio);
				
				if (Integer.parseInt(currentForm.getTipoAtto().getId()) == Constants.TipoAtto.TIPO_ATTO_RICEVUTA) {
					// Aggiunto le informazione dell'atto notificato
					
					NotificaAttoForm attoNotificato =  this.getAttoNoficatoDaRicevuta(currentAtto.getId().getCodiKIdUffOwner(), currentAtto.getId().getCodiKIdAtto(),idProcedimento, codiceUtente,currentAtto.getTTpCatAtto().getCodiKIdCatAtto());
					String destinatario = "&nbsp;&nbsp; <img src=\"" + request.getContextPath() + "/images/destinatario.png\" title=\"" + attoNotificato.getListaDestinatari().get(0).getDESC_DEST().replaceAll("\"", "") + "\"/>";
					currentForm.setDescInfoScheda("<table><tr><td valign='middle'> / " + attoNotificato.getCategoriaAtto().getDescrizione()+ "</td><td valign='middle'>" + destinatario + "</td></tr></table>");
					results.add(currentForm);
				} else  if (currentForm.getCategoriaAtto().getId().equals(Constants.PROPOSTA) || 
						   currentForm.getCategoriaAtto().getId().equals(Constants.ISTANZA_MODIFICA_PERSONALE)||
						   currentForm.getCategoriaAtto().getId().equals(Constants.ISTANZA_MODIFICA_PATRIMONIALE)) {
					int idTipoFascicolo = 0;
					if (request.getSession().getAttribute(Constants.TIPO_FASCICOLO) != null) {
						Object idTipoFascOBJ = request.getSession().getAttribute(Constants.TIPO_FASCICOLO);
						if (idTipoFascOBJ instanceof String)
							idTipoFascicolo = Integer.valueOf((String) idTipoFascOBJ);
						else
							idTipoFascicolo = (Integer) idTipoFascOBJ;
					} else {
						ProcedimentoForm procedimentoForm = procedimentoService.getDettaglioProcedimento(idProcedimento);
						idTipoFascicolo = Integer.valueOf(procedimentoForm.getTipoFascicolo().getId());
					}
					if (idTipoFascicolo == Constants.TIPO_FASCICOLO_AGGRAVAMENTO) {
						ProcedimentoForm procedimento = procedimentoService.getDettaglioProcedimento(idProcedimento);
	
						// Solo per l'ufficio Procura, se lo stato del procedimento
						// è ancora iscritto o in lavorazione non devo vedere la
						// proposta
						boolean proposta = true;
						ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute("profilo");
						if (profilo.getIdTipoUfficio() == new Long(Constants.TIPO_UFFICIO_PROCURA)
								&& (Long.valueOf(procedimento.getStatoProcedimento().getId()) == Constants.STATOPROC_IN_LAVORAZIONE || procedimento
										.getStatoProcedimento().getId().equals(Constants.STATOPROC_ISCRITTO))) {
							proposta = false;
						}
						if (proposta) {
							results.add(currentForm);
						}
					} else {
						if (commonDao.existMisurePersonali(idProcedimento, currentForm.getCodAtto())
								|| commonDao.existMisurePatrimoniali(idProcedimento, currentForm.getCodAtto())) {
							results.add(currentForm);
						}
					}
			
				} else if (Utility.isTDecretoRecord(currentForm.getTipoAtto().getId())) {
	
					Criterion c1 = Restrictions.eq("id.codiKIdDecreto", currentAtto.getId().getCodiKIdAtto());
					Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", currentAtto.getId().getCodiKIdUffOwner());
					Object obj = attoDao.getAttoByPrimaryKey(TDecreto.class, c1, c2);
					if (obj != null) {
						TDecreto ist = (TDecreto) obj;
						String numAttoView = builNumeroAttoView(ist);
						if (ist.getNumeAnnoAtto() != null) {
							currentForm.setNumeAnnoAtto(ist.getNumeAnnoAtto().toString());
						}
						if (ist.getNumeAtto() != null) {
							currentForm.setNumeAtto(ist.getNumeAtto().toString());
						}
						currentForm.setNumeroAtto(numAttoView);
					}
					if ( currentForm.getStatoAtto().getId() != null  && (currentForm.getStatoAtto().getId().equals("3") || currentForm.getStatoAtto().getId().equals("4"))){
	                 if (  currentForm.getCategoriaAtto().getId().equals("4") || currentForm.getCategoriaAtto().getId().equals("300")
	                		 || currentForm.getCategoriaAtto().getId().equals("301")  || currentForm.getCategoriaAtto().getId().equals("302")	
	                		  || currentForm.getCategoriaAtto().getId().equals("31")
	                		 ){
					results.add(currentForm);
	                   }
					}
				}  
			}
		}
		return results;
	}


	public List<AttoForm> getElencoAttiCda(long idProcedimento, String codiceUtente, String nomeMetodo) {

		// Controllo su T_REL_PROC_PROC se per il procedimento corrente esistono
		// due record con lo stesso CODI_FK_UFF_OWNER2
		// PROCEDURA: restituisce una lista listaRelProcProc di T_REL_PROC_PROC
		List<TRelProcProc> listaRelProcProc = procedimentoService.getTRelProcProcByProc1(idProcedimento, null, codiceUtente, nomeMetodo);
		// controllo se esistono due record con lo stesso CODI_FK_UFF_OWNER2
		boolean flag = esisteProcConUffOwnerUguale(listaRelProcProc);

		List<AttoForm> results = new ArrayList<AttoForm>();
		List daoResults = attoDao.getAttiOrdineCronologico(idProcedimento);
		Iterator iter = daoResults.iterator();
		while (iter.hasNext()) {
			AttoForm currentForm = new AttoForm();
			Map map = (Map) iter.next();
			TAtto currentAtto = (TAtto) map.get("atto");

				TTpCatAtto currentCategoria = (TTpCatAtto) map.get("cat_atto");
				TTpStatoAtto statoAtto = (TTpStatoAtto) map.get("stato_atto");
	
				String numeroProcedStralcio = "";
				// se caso sopra...
				if (flag) {
					// cerco nella listaRelProcProc se esiste un CODI_K_ID_PROC2 =
					// codiIdProcEmiss (dell'atto che sto ciclando)
					// ==>se esiste prendo il NUME_ANNO_PROC2 / NUME_PROCEDIMENTO2
					// (e lo accodo alla descrizione dell'atto)
					for (Iterator<TRelProcProc> iterator = listaRelProcProc.iterator(); iterator.hasNext();) {
						Map<String, Object> row = (Map<String, Object>) iterator.next();
						TRelProcProc tRelProcProc = (TRelProcProc) row.get("relProcProc");
	
						if (tRelProcProc.getId().getCodiKIdProc2() == currentAtto.getCodiIdProcEmiss()) {
							String numProvProc = getNumeroAnnoNumeroAtto(String.valueOf(tRelProcProc.getNumeAnnoProc2()),
									String.valueOf(tRelProcProc.getNumeProcedimento2()));
							ComboForm tribunale = commonService.getDettaglioUfficio(tRelProcProc.getTAnUfficio().getCodiKIdUfficio());
	
							numeroProcedStralcio = " (Emesso nel proc. " + numProvProc + " - " + tribunale.getDescrizione() + ")";
							currentCategoria.setDescCatAtto(currentCategoria.getDescCatAtto());
							break;
						}
					}
				}
	
				ComboForm statoAttoForm = new ComboForm();
				ComboForm catAttoForm = new ComboForm();
				mapperBean.map(currentAtto, currentForm);
				mapperBean.map(currentCategoria, catAttoForm);
				mapperBean.map(statoAtto, statoAttoForm);
				currentForm.setStatoAtto(statoAttoForm);
				currentForm.setCategoriaAtto(catAttoForm);
	
				// al momento inserisco n.d.
				currentForm.setNumeroAtto(Constants.FormattazioneAnnoNumeroAtto.NUMERO_ATTO_NON_DISP);
	
				// associo all'atto l'eventuale procedimento di origine
				currentForm.setNumeroProcedimentoOrigine(numeroProcedStralcio);
				
				if (currentForm.getCategoriaAtto().getId().equalsIgnoreCase(Constants.VERBALE_UDIENZA) ||
						currentForm.getCategoriaAtto().getId().equalsIgnoreCase(Constants.VERBALE_RINVIO_UDIENZA)) {
					
				        	results.add(currentForm);
	                
	
				} else   if (Utility.isTDecretoRecord(currentForm.getTipoAtto().getId())) {
	
					Criterion c1 = Restrictions.eq("id.codiKIdDecreto", currentAtto.getId().getCodiKIdAtto());
					Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", currentAtto.getId().getCodiKIdUffOwner());
					Object obj = attoDao.getAttoByPrimaryKey(TDecreto.class, c1, c2);
					if (obj != null) {
						TDecreto ist = (TDecreto) obj;
						String numAttoView = builNumeroAttoView(ist);
						if (ist.getNumeAnnoAtto() != null) {
							currentForm.setNumeAnnoAtto(ist.getNumeAnnoAtto().toString());
						}
						if (ist.getNumeAtto() != null) {
							currentForm.setNumeAtto(ist.getNumeAtto().toString());
						}
						currentForm.setNumeroAtto(numAttoView);
					}
			
					
			

			
					  if ( currentForm.getStatoAtto().getId() != null  && (currentForm.getStatoAtto().getId().equals("3"))){
				        if (  currentForm.getCategoriaAtto().getId().equals(Constants.DECRETO_FISSAZIONE_UDIENZA_II_GRADO) || currentForm.getCategoriaAtto().getId().equals(Constants.DECRETO_ANTICIPAZIONE_UDIENZA_II_GRADO)
					       		 || currentForm.getCategoriaAtto().getId().equals(Constants.DECRETO_RINVIO_UDIENZA_II_GRADO) || currentForm.getCategoriaAtto().getId().equals(Constants.DECRETO_PROROGA_SEQUESTRO_CDA)
					       		 || currentForm.getCategoriaAtto().getId().equals(Constants.DECRETO_PROROGA_SOSPENSIONE_CDA) || currentForm.getCategoriaAtto().getId().equals(Constants.DECRETO_REVOCA_SEQUESTRO_II_GRADO)
					       		 || currentForm.getCategoriaAtto().getId().equals(Constants.DECRETO_SOSPENSIONE_TEMP_CDA) || currentForm.getCategoriaAtto().getId().equals(Constants.DECRETO_SEQUESTRO_ORDINARIO_II_GRADO)
					       		 || currentForm.getCategoriaAtto().getId().equals(Constants.DECRETO_SEQUESTRO_SOSPENSIONE_CDA) 
					       	)
				        {
				        		results.add(currentForm);
	                    }
					}
					
				}  
			
		}
		return results;
	}
	
	


	
	public List<AttoForm> getAttiOrdineCronologico(long idProcedimento, String codiceUtente, String nomeMetodo, String tipologiaAtto) {

		List<AttoForm> listaAtti = getAttiOrdineCronologico(idProcedimento, codiceUtente, nomeMetodo,false);
		List<AttoForm> listaAttiGenerici = new ArrayList<AttoForm>();
		for (Iterator iterator = listaAtti.iterator(); iterator.hasNext();) {
			AttoForm attoForm = (AttoForm) iterator.next();
			if (attoForm.getTipoAtto() != null && attoForm.getTipoAtto().getId().equalsIgnoreCase(tipologiaAtto)) {
				listaAttiGenerici.add(attoForm);
			}
		}
		return listaAttiGenerici;

	}

	
	
	private boolean esisteProcConUffOwnerUguale(List<TRelProcProc> listaRelProcProc) {

		int listSize = listaRelProcProc.size();
		HashMap<String, String> hm = new HashMap<String, String>();
		for (Iterator<TRelProcProc> iterator = listaRelProcProc.iterator(); iterator.hasNext();) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TRelProcProc tRelProcProc = (TRelProcProc) row.get("relProcProc");
			int uffOwner2 = tRelProcProc.getTAnUfficio().getCodiKIdUfficio();

			hm.put(String.valueOf(uffOwner2), String.valueOf(uffOwner2));
		}
		int hashSize = hm.size();

		// se l hm ha meno elementi di list vuol dire che qualcuno è
		// doppio/triplo...
		if (hashSize < listSize) {
			return true;
		} else {
			return false;
		}
	}

	public static String builNumeroAttoView(TDecreto ist) {
		String numAtto = "";
		String annoAtto = "";
		if (ist.getNumeAnnoAtto() != null) {
			annoAtto = String.valueOf(ist.getNumeAnnoAtto());
		}
		if (ist.getNumeAtto() != null) {
			numAtto = String.valueOf(ist.getNumeAtto());
		}

		String numAttoView = getNumeroAnnoNumeroAtto(numAtto, annoAtto);
		return numAttoView;
	}
	


	public static String builNumeroAttoView(TImpugnazione ist) {
		String numAtto = "";
		String annoAtto = "";
		if (ist.getNumeAnnoAtto() != null) {
			annoAtto = String.valueOf(ist.getNumeAnnoAtto());
		}
		if (ist.getNumeAtto() != null) {
			numAtto = String.valueOf(ist.getNumeAtto());
		}

		String numAttoView = getNumeroAnnoNumeroAtto(numAtto, annoAtto);
		return numAttoView;
	}

	public static String builNumeroAttoView(TProvFg ist) {
		String numAtto = "";
		String annoAtto = "";
		if (ist.getNumeAnnoProvFg() != null) {
			annoAtto = String.valueOf(ist.getNumeAnnoProvFg());
		}
		if (ist.getNumeProvFg() != null) {
			numAtto = String.valueOf(ist.getNumeProvFg());
		}

		String numAttoView = getNumeroAnnoNumeroAtto(numAtto, annoAtto);
		return numAttoView;
	}
	
	public static String builNumeroAttoView(TIstanza ist) {
		String numAtto = "";
		String annoAtto = "";
		if (ist.getNumeAnnoAtto() != null) {
			annoAtto = String.valueOf(ist.getNumeAnnoAtto());
		}
		if (ist.getNumeAtto() != null) {
			numAtto = String.valueOf(ist.getNumeAtto());
		}

		String numAttoView = getNumeroAnnoNumeroAtto(numAtto, annoAtto);
		return numAttoView;
	}	

	private static String getNumeroAnnoNumeroAtto(String numAtto, String annoAtto) {
		String numAttoView = annoAtto + Constants.FormattazioneAnnoNumeroAtto.DIVISORE_ANNO_NUMERO_ATTO + numAtto;
		return numAttoView;
	}

	public static String builNumeroAttoView(String numeroAnnoAtto, String numeroAtto) {
		String numAttoView = getNumeroAnnoNumeroAtto(numeroAtto, numeroAnnoAtto);
		return numAttoView;
	}

	/**
	 * getAttoById
	 * 
	 * @param idAtto
	 * @param idUfficio
	 * @return AttoForm
	 */
	public AttoForm getAttoById(long idAtto, long idUfficio) {

		TAttoId id = new TAttoId(idAtto, (int) idUfficio);
		TAtto atto = attoDao.getAttoById(id);

		AttoForm aForm = new AttoForm();
		aForm.getAllegato().setDocumento(atto.getFileDocumento());

		mapperBean.map(atto, aForm);

		if (Utility.isTDecretoRecord(aForm.getTipoAtto().getId())) {

			Criterion c1 = Restrictions.eq("id.codiKIdDecreto", idAtto);
			Criterion c2 = Restrictions.eq("id.codiKIdUffOwner", (int) idUfficio);
			Object obj = attoDao.getAttoByPrimaryKey(TDecreto.class, c1, c2);
			if (obj != null) {
				TDecreto decr = (TDecreto) obj;
				if (decr != null && decr.getTUdienza() != null) {
					aForm.setFkUdienzaDecreto(decr.getTUdienza().getId().getCodiKIdUdienza());
				}
			}
		}

		if (atto != null && atto.getNumeDocumentoPdoc() != null) {
			byte[] doc = documentaleService.recuperoDocumento(Utility.longToBigInteger(atto.getNumeDocumentoPdoc()),atto.getId().getCodiKIdUffOwner());
			if (doc != null) {
				Blob b = Hibernate.createBlob(doc);
				aForm.getAllegato().setDocumento(b);
			}
		}
		aForm.setDataInformativa(aForm.getDataEmissione());
		return aForm;
	}

	
	public AttoForm getTRelProcSogintAttoById(long idAtto, long idUfficio,long idProc) {

		TRelProcSogintAttoId id = new TRelProcSogintAttoId(idProc,idAtto, (int) idUfficio);
		TRelProcSogintAtto sogintAtto = attoDao.getTRelProcSogintAttoById(id);

		AttoForm aForm = new AttoForm();
		
		if(sogintAtto != null){
			aForm.setDataCessazioneMisura(sogintAtto.getDataCessazioneMisura());
			aForm.setDataRipresaMisura(sogintAtto.getDataRipresaMisura());
		}
		
		return aForm;
	}	
	
	/**
	 * getCatAttoById
	 * 
	 * @param idCatAtto
	 * @return TTpCatAtto
	 */
	public TTpCatAtto getCatAttoById(int idCatAtto) {

		TTpCatAtto catAtto = attoDao.getCatAttoById(idCatAtto);
		return catAtto;
	}

	/**
	 * getDettaglioMagistratoAtto
	 * 
	 * @param idUffOwn
	 * @param idAtto
	 * @param idMagistrato
	 * @param descRuolo
	 * @return MagistratoForm
	 */
	public MagistratoForm getDettaglioMagistratoAtto(Integer idUffOwn, long idAtto, long idMagistrato, String descRuolo) {

		Map<String, Object> queryResults = null;
		// try {
		queryResults = attoDao.dettaglioMagistratoPerAtto(idUffOwn, idAtto, idMagistrato, descRuolo);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		
		MagistratoForm form = new MagistratoForm();
		
		if (queryResults != null){
			TRelMagAtto relAtto = (TRelMagAtto) queryResults.get("rel");
			TAnMagistrati magistrato = (TAnMagistrati) queryResults.get("mag");
			mapperBean.map(relAtto, form);
			mapperBean.map(magistrato, form);
		}
		
		return form;
	}

	public Long getIdAttoAggiornatoPerPadreAggravamento(Long idPadre, Integer idUffOwn) {

		List<TRelAttoMisPers> relazioni = misuraService.listaRelAttoMisPersByProcEUfficio(idPadre, idUffOwn);
		Long risultato = null;
		for (TRelAttoMisPers tmp : relazioni) {
			if (misuraService.stataAccolta(tmp)) {
				risultato = tmp.getId().getCodiKIdAtto();
			}
		}
		return risultato;
	}

	public UdienzaForm getUdienzaById(long codiceUdienza, long codiceUfficio) {

		TUdienzaId id = new TUdienzaId((int) codiceUfficio, codiceUdienza);
		TUdienza udienza = attoDao.getUdienzaById(id);
		if (udienza != null) {
			UdienzaForm udienzaForm = new UdienzaForm();
			mapperBean.map(udienza, udienzaForm);
			// inizializzazione del campo minuti e ore
			if (udienza.getOraInizioUdienza() != null) {
				StringTokenizer st = new StringTokenizer(udienza.getOraInizioUdienza(), ":");
				while (st.hasMoreTokens()) {
					ComboForm comboFormOra = new ComboForm();
					comboFormOra.setId(st.nextToken());
					ComboForm comboFormMinuti = new ComboForm();
					comboFormMinuti.setId(st.nextToken());
					udienzaForm.setOraInizioUdienza(comboFormOra);
					udienzaForm.setMinutiInizioUdienza(comboFormMinuti);
				}
			}
			return udienzaForm;
		} else
			return null;
	}

	/**
	 * inserisciMagistratoPerAtto
	 * 
	 * @param idUffOwn
	 * @param idAtto
	 * @param idMagistrato
	 * @param descRuolo
	 * @param flgRelatore
	 * @param codiUtente
	 */
	public void inserisciMagistratoPerAtto(Integer idUffOwn, long idAtto, long idMagistrato, String tipoRuoloMag, String flgRelatore,
			String codiUtente) {

		// try {
		attoDao.inserisciMagistratoPerAtto(idUffOwn, idAtto, idMagistrato, tipoRuoloMag, flgRelatore, codiUtente);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public long inviaAtto(long idProcedimento, long idUfficio, String codiceUtente, long idStatoAtto, AttoForm attoForm, long idDest, long idUdienza) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);
		atto.getId().setCodiKIdUffOwner((int) idUfficio);
		atto.setDescUtente(codiceUtente);

		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return attoDao.inviaAtto(idProcedimento, atto, idStatoAtto, idDest, idUdienza);
	}

	/**
	 * inviaAttoDecretoSequestro
	 * 
	 * @param idUfficio
	 * @param idProcedimento
	 * @param attoForm
	 * @param codUtente
	 * @param tipoAtto
	 * @param categoriaAtto
	 * @param idStatoAtto
	 * @param idDestinatario
	 * @param idUltDestinatario
	 * @param codUdienzaAtto
	 * @param codUdienzaFiss
	 * @param codUdienzaInFaseDiRip
	 * @param codUdienzaInFaseDiRim
	 * @param statoUdienzaPrec
	 * @return Long
	 */
	public Long inviaAttoDecretoSequestro(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,
			long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario, long codUdienzaAtto, long codUdienzaFiss,
			long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim, long statoUdienzaPrec, long idMagistrato, String giudiceDelegato) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return attoDao.inviaAttoDecretoSequestro(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto, idStatoAtto, idDestinatario,
				idUltDestinatario, codUdienzaAtto, codUdienzaFiss, codUdienzaInFaseDiRip, codUdienzaInFaseDiRim, statoUdienzaPrec, idMagistrato,
				giudiceDelegato);
	}
	
	/**
	 * aggiornaAttoDecretoSequestro
	 * 
	 * @param idUfficio
	 * @param idProcedimento
	 * @param attoForm
	 * @param codUtente
	 * @param tipoAtto
	 * @param categoriaAtto
	 * @param idStatoAtto
	 * @param idDestinatario
	 * @param idUltDestinatario
	 * @return Long
	 */
	public Long aggiornaAttoDecretoSequestro(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,
			long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return attoDao.aggiornaAttoDecretoSequestro(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto, idStatoAtto, idDestinatario,
				idUltDestinatario);
	}
	

	public void attoDecretoApplicazioneProvvisoria(long vidProc, long vidUffOwn, long idSoggInt, String vcodUtente, SoggettoForm soggettoForm,AttoForm attoForm) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		long vidAtto = 0L;
		String cauzione = "N";
		String idPrescrizioni;
		String articolo67 = "N";
		String articolo9c1 ="N";
		String idMisureCaricate = null;
		TRelAttoMisPers relAttoMisPers = new TRelAttoMisPers();
		mapperBean.map(soggettoForm, relAttoMisPers);
		if (soggettoForm.getMisura() != null)
		{
			if (soggettoForm.getIdAtto() != null) {
				vidAtto = soggettoForm.getIdAtto();
			}
			
			if (soggettoForm.getMisura().getArt9c1() != null) {
				articolo9c1 = soggettoForm.getMisura().getArt9c1();
			}
			
			
			if (soggettoForm.getMisura().getIdMisureCaricate() != null) {
				idMisureCaricate = soggettoForm.getMisura().getIdMisureCaricate();
			}
			if ((soggettoForm.getMisura().getIdSelezionati() != null) && !soggettoForm.getMisura().getIdSelezionati().isEmpty()) {
				idPrescrizioni = soggettoForm.getMisura().getIdSelezionati().get(0) + "";
				if (soggettoForm.getMisura().getIdSelezionati().size() > 1) {
					for (int i = 1; i < soggettoForm.getMisura().getIdSelezionati().size(); i++) {
						idPrescrizioni += ";" + soggettoForm.getMisura().getIdSelezionati().get(i);
					}
				}
			} else {
				idPrescrizioni = null;
			}
		} else 
		{
				idPrescrizioni = null;
		}

			
		 attoDao.attoDecretoApplicazioneProvvisoria(atto,idMisureCaricate, vidProc, vidAtto, vidUffOwn, relAttoMisPers, idSoggInt, cauzione, articolo67, vcodUtente, idPrescrizioni, null,articolo9c1);
	}	
	
	public void attoDecretoRevocaApplicazioneProvvisoria(long vidProc, long vidUffOwn, long idSoggInt, String vcodUtente, SoggettoForm soggettoForm,AttoForm attoForm) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		long vidAtto = 0L;
		String cauzione = "N";
		String idPrescrizioni;
		String articolo67 = "N";
		String articolo9c1 ="N";
		String idMisureCaricate = null;
		TRelAttoMisPers relAttoMisPers = new TRelAttoMisPers();
		mapperBean.map(soggettoForm, relAttoMisPers);
		if (soggettoForm.getMisura() != null)
		{
			if (soggettoForm.getIdAtto() != null) {
				vidAtto = soggettoForm.getIdAtto();
			}
			
			if (soggettoForm.getMisura().getArt9c1() != null) {
				articolo9c1 = soggettoForm.getMisura().getArt9c1();
			}
			
			
			if (soggettoForm.getMisura().getIdMisureCaricate() != null) {
				idMisureCaricate = soggettoForm.getMisura().getIdMisureCaricate();
			}
			if ((soggettoForm.getMisura().getIdSelezionati() != null) && !soggettoForm.getMisura().getIdSelezionati().isEmpty()) {
				idPrescrizioni = soggettoForm.getMisura().getIdSelezionati().get(0) + "";
				if (soggettoForm.getMisura().getIdSelezionati().size() > 1) {
					for (int i = 1; i < soggettoForm.getMisura().getIdSelezionati().size(); i++) {
						idPrescrizioni += ";" + soggettoForm.getMisura().getIdSelezionati().get(i);
					}
				}
			} else {
				idPrescrizioni = null;
			}
		} else 
		{
				idPrescrizioni = null;
		}

			
		 attoDao.attoDecretoRevocaApplicazioneProvvisoria(atto,idMisureCaricate, vidProc, vidAtto, vidUffOwn, relAttoMisPers, idSoggInt, cauzione, articolo67, vcodUtente, idPrescrizioni, null,articolo9c1);
	}		
	

	/**
	 * @param idUfficio
	 * @param idProcedimento
	 * @param attoForm
	 * @param codUtente
	 * @param tipoAtto
	 * @param categoriaAtto
	 * @param idStatoAtto
	 * @param idDestinatario
	 * @param idUltDestinatario
	 * @param codUdienzaAtto
	 * @param codUdienzaFiss
	 * @param codUdienzaInFaseDiRip
	 * @param codUdienzaInFaseDiRim
	 * @param statoUdienzaPrec
	 * @return Long
	 */
	public Long inviaAttoDecretoConvalidaSequestro(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,
			long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario, long codUdienzaAtto, long codUdienzaFiss,
			long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim, long statoUdienzaPrec, String nomeMetodo) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return attoDao.inviaAttoDecretoConvalidaRevocaSequestro(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto, idStatoAtto,
				idDestinatario, idUltDestinatario, codUdienzaAtto, codUdienzaFiss, codUdienzaInFaseDiRip, codUdienzaInFaseDiRim, statoUdienzaPrec,
				Constants.CONVALIDA, nomeMetodo);
	}
	
	


	public Long inviaDecretoPerditaEfficaciaSequestro(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,
			long categoriaAtto, long idStatoAtto,long codUdienzaAtto, long codUdienzaFiss,
			long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim, long statoUdienzaPrec, String nomeMetodo) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return attoDao.inviaDecretoPerditaEfficaciaSequestro(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto, idStatoAtto,
				codUdienzaAtto, codUdienzaFiss, codUdienzaInFaseDiRip, codUdienzaInFaseDiRim, statoUdienzaPrec,
				Constants.CONVALIDA, nomeMetodo);
	}	

	public Long inviaAttoDecretoRevocaSequestro(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,
			long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario, long codUdienzaAtto, long codUdienzaFiss,
			long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim, long statoUdienzaPrec, String nomeMetodo) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return attoDao.inviaAttoDecretoConvalidaRevocaSequestro(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto, idStatoAtto,
				idDestinatario, idUltDestinatario, codUdienzaAtto, codUdienzaFiss, codUdienzaInFaseDiRip, codUdienzaInFaseDiRim, statoUdienzaPrec,
				Constants.REVOCA, nomeMetodo);
	}

	public Long inviaAttoDecretoOpposizioneDissequestro(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,
			long categoriaAtto, long idStatoAtto, long idDestinatario, String nomeMetodo) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return attoDao.inviaAttoDecretoOpposizioneDissequestro(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto, idStatoAtto,
				idDestinatario, nomeMetodo);
	}

	public Long inviaAttoDecretoRestBeni(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto, long categoriaAtto,
			long idStatoAtto, long idDestinatario, String nomeMetodo) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return attoDao.inviaAttoDecretoRestBeni(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto, idStatoAtto, idDestinatario,
				nomeMetodo);
	}

	public Long inviaAttoDecretoRevocaOpposizioneDissequestro(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente,
			long tipoAtto, long categoriaAtto, long idStatoAtto, long idDestinatario, String nomeMetodo) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return attoDao.inviaAttoDecretoRevocaOpposizioneDissequestro(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto,
				idStatoAtto, idDestinatario, nomeMetodo);
	}

	public Long inviaAttoRevocazioneConfisca(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,
			long categoriaAtto, long idStatoAtto, long idDestinatario, String nomeMetodo) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return attoDao.inviaAttoRevocazioneConfisca(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto, idStatoAtto, idDestinatario,
				nomeMetodo);
	}

	/**
	 * inviaDecretoIncompetenza
	 * 
	 * @param idProcedimento
	 * @param idAtto
	 * @param idMittente
	 * @param idDestinatario
	 * @param idCodiceUtente
	 * @param idUfficio
	 * @return
	 */
	public long inviaDecretoIncompetenza(long idProcedimento, long idAtto, long idMittente, long idDestinatario1, long idDestinatario2,
			long idDestinatario3, String idCodiceUtente, long idUfficio, long categoriaAtto, String nomeMetodo) {

		return attoDao.inviaDecretoIncompetenza(idProcedimento, idAtto, idMittente, idDestinatario1, idDestinatario2, idDestinatario3,
				idCodiceUtente, idUfficio, categoriaAtto, nomeMetodo);
	}

	public void inviaDecretoIncompetenzaProcura(long idProcedimento, long idAtto, long idMittente, long idDestinatarioProposta, long idDestinatario2,
			long idDestinatario3, String idCodiceUtente, long idUfficio, long categoriaAtto, String nomeMetodo) {

		attoDao.inviaDecretoIncompetenzaProcura(idProcedimento, idAtto, idMittente, idDestinatarioProposta, idDestinatario2, idDestinatario3,
				idCodiceUtente, idUfficio, categoriaAtto, nomeMetodo);
	}

	public long inviaDefinitivita(AttoForm attoForm, String codUtente, long idUfficio, ProcedimentoForm procedimentoForm, long idUffProcProp,
			Long idDestinatario) {

		attoForm.setIdProcedimento(procedimentoForm.getIdProcedimento());
		TRelBeneAttoId idRelBeneAtto = new TRelBeneAttoId();
		mapperBean.map(attoForm, idRelBeneAtto);
		idRelBeneAtto.setCodiKIdUffOwner((int) idUfficio);
		TRelAttoMisPersId idRelAttoMisPers = new TRelAttoMisPersId();
		mapperBean.map(attoForm, idRelAttoMisPers);
		TProcedimento procedimento = new TProcedimento();
		mapperBean.map(procedimentoForm, procedimento);
		idRelAttoMisPers.setCodiKIdUffOwner((int) idUfficio);
		long idTipoAtto = Long.parseLong(attoForm.getTipoAtto().getId());
		return attoDao.applicaDefinitivita(idRelBeneAtto, idRelAttoMisPers, procedimento, codUtente, idTipoAtto, attoForm.getDataDefinitivita(),
				idUffProcProp, idDestinatario);
	}

	public void inviaDefinitivitaDecisorioIGradoRiesame(AttoForm attoForm, String codUtente, long idUffOwn, ProcedimentoForm procedimentoForm,
			long idProcUdienza, long idProcGenerale) {

		long idProc = procedimentoForm.getIdProcedimento();
		long annoProc = procedimentoForm.getAnno();
		long numeProc = procedimentoForm.getNumero();
		long idAtto = attoForm.getCodAtto();
		long tipoAtto = Long.parseLong(attoForm.getTipoAtto().getId());
		Date dataDefinitivita = attoForm.getDataDefinitivita();

		attoDao.inviaDefinitivitaDecisorioIGradoRiesame(idProc, numeProc, annoProc, idAtto, tipoAtto, idUffOwn, idProcGenerale, idProcUdienza,
				codUtente, dataDefinitivita);
	}

	public void inviaUdienza(long idUfficio, String codiceUtente, long idProcedimento, long idTipoAtto, long idCategoriaAtto, AttoForm udienza,
			long idDest) {

		Map<String, Long> resultMap = null;
		long codAtto = 0;
		long idUdienza = 0;
		if (udienza.getCodAtto() > 0) {
			UdienzaForm uForm = (UdienzaForm) udienza;
			codAtto = udienza.getCodAtto();
			idUdienza = uForm.getCodUdienza();
			AttoForm attoUdienza = getAttoById(udienza.getCodAtto(), idUfficio);
			attoUdienza.setDataEmissione(udienza.getDataEmissione());
			attoUdienza.setDataDeposito(udienza.getDataDeposito());
			attoUdienza.setDataRegistrazione(udienza.getDataRegistrazione());
			modificaUdienza(idUfficio, uForm);
			inviaAtto(idProcedimento, idUfficio, codiceUtente, Constants.STATOATTO_REGISTRATO, attoUdienza, idDest, idUdienza);
		} else {
			resultMap = salvaUdienza(idUfficio, codiceUtente, idProcedimento, idTipoAtto, idCategoriaAtto, udienza);
			codAtto = resultMap.get("ID_ATTO");
		}
	}

	/**
	 * listaBeniNoMisPatr
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param idAtto
	 * @return List<BeneForm>
	 */
	public List<BeneForm> listaBeniNoMisPatr(long idUffOwn, long idProcedimento, long idAtto) {

		List queryResults = null;
		// try {
		queryResults = attoDao.listaBeniNoMisPatr(idUffOwn, idProcedimento, idAtto);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		Iterator itx = queryResults.iterator();
		while (itx.hasNext()) {
			Map<String, Object> rows = (Map<String, Object>) itx.next();
			TBene bene = (TBene) rows.get("bene");
			BeneForm beneForm = new BeneForm();
			mapperBean.map(bene, beneForm);
			risultati.add(beneForm);
		}
		return risultati;
	}

	/**
	 * listaBeniNoTitDir
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param idAtto
	 * @return List<BeneForm>
	 */
	public List<BeneForm> listaBeniNoTitDir(long idUffOwn, long idProcedimento, long idAtto) {

		List queryResults = null;
		// try {
		queryResults = attoDao.listaBeniNoTitDir(idUffOwn, idProcedimento, idAtto);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<BeneForm> risultati = new ArrayList<BeneForm>();
		Iterator itx = queryResults.iterator();
		while (itx.hasNext()) {
			Map<String, Object> rows = (Map<String, Object>) itx.next();
			TBene bene = (TBene) rows.get("bene");
			TRelProcBene rel = (TRelProcBene) rows.get("proc_bene");
			BeneForm beneForm = new BeneForm();
			mapperBean.map(bene, beneForm);
			mapperBean.map(rel, beneForm);
			risultati.add(beneForm);
		}
		return risultati;
	}

	/**
	 * listaMagistratiPerAtto
	 * 
	 * @param idUffOwn
	 * @param idAtto
	 * @return List<MagistratoForm>
	 */
	public List<MagistratoForm> listaMagistratiPerAtto(int idUffOwn, long idAtto, int ruoloMag) {

		List queryResults = null;
		// try {
		queryResults = attoDao.listaMagistratiPerAtto(idUffOwn, idAtto, ruoloMag);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<MagistratoForm> risultati = new ArrayList<MagistratoForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TRelMagAtto relAtto = (TRelMagAtto) row.get("rel");
			TAnMagistrati magistrato = (TAnMagistrati) row.get("mag");
			MagistratoForm form = new MagistratoForm();
			mapperBean.map(relAtto, form);
			mapperBean.map(magistrato, form);
			risultati.add(form);
		}
		return risultati;
	}
	/*
	 * modificaAtto
	 * 
	 * @param attoForm
	 * @param idStatoAtto
	 * @return long
	 */
	// Specifiche Intervento-006-SpeseProces-NumAtto: aggiunto parametro di passaggio
	public long modificaAtto(int idUffOwn, AttoForm attoForm, long idStatoAtto, boolean test) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);
		// if (attoForm.getDocumento() != null) {
		// atto.setFileDocumento(attoForm.getDocumento());
		// atto.setDescNomeDocumento(attoForm.getFileName());
		// atto.setDescTipoDocumento(attoForm.getContentType());
		// }
		Utility.popolaDocumento(attoForm, atto);
		// sabino
		// inserimento null nel passaggio dei parametri
		return attoDao.modificaAtto(idUffOwn, atto, idStatoAtto, null, test);
	}

	/**
	 * modificaMagistratoPerAtto
	 * 
	 * @param idUffOwn
	 * @param idAtto
	 * @param idMagistrato
	 * @param descRuolo
	 * @param descNuovoRuolo
	 * @param flgRelatore
	 */
	public void modificaMagistratoPerAtto(Integer idUffOwn, long idAtto, long idMagistrato, String descRuolo, String descNuovoRuolo,
			String flgRelatore) {

		// try {
		attoDao.modificaMagistratoPerAtto(idUffOwn, idAtto, idMagistrato, descRuolo, descNuovoRuolo, flgRelatore);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public void modificaUdienza(long idUfficio, UdienzaForm uForm) {

		TUdienza uPojo = new TUdienza();
		TUdienzaId id = new TUdienzaId();
		id.setCodiKIdUffOwner((int) idUfficio);
		id.setCodiKIdUdienza(uForm.getCodUdienza());
		uPojo.setId(id);
		mapperBean.map(uForm, uPojo);
		uPojo.setOraInizioUdienza(uForm.getOraInizioUdienza().getId() + ":" + uForm.getMinutiInizioUdienza().getId());
		attoDao.modificaUdienza(uPojo, uForm.getCodAtto(), uForm.getAllegato().getDocumento(), uForm.getAllegato().getFileName(), uForm.getAllegato()
				.getContentType(), uForm.getDataEmissione(), uForm.getDataDeposito());
	}

	/**
	 * salvaAtto
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param codiceUtente
	 * @param attoForm
	 * @param idStatoAtto
	 * @return long
	 */
	public long salvaAtto(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		return attoDao.salvaAtto(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto);
	}

	/**
	 * salvaAttoConMagistrato
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param codiceUtente
	 * @param attoForm
	 * @param idStatoAtto
	 * @return long
	 */
	public long salvaAttoDecretoConMagistrato(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto) {

		TAtto atto = new TAtto();
		TDecreto decreto = new TDecreto();
		mapperBean.map(attoForm, atto);
		// if (attoForm.getFileData() != null) {
		// byte b[] = attoForm.getFileData().getBytes();
		// Blob blobDocumento = Hibernate.createBlob(b);
		// atto.setFileDocumento(blobDocumento);
		// atto.setDescNomeDocumento(attoForm.getFileName());
		// atto.setDescTipoDocumento(attoForm.getContentType());
		// }
		Utility.popolaDocumento(attoForm, atto);
		return attoDao.salvaAttoDecretoConMagistrato(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, decreto);
	}

	/**
	 * salvaAtto
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param codiceUtente
	 * @param attoForm
	 * @param idStatoAtto
	 * @return long
	 */
	public long salvaAttoDecretoDecisorio(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto,
			long idAttoOrigineMisure) {

		TAtto atto = popolaAttoEDocumento(attoForm);
		return attoDao.salvaAttoDecretoDecisorio(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, idAttoOrigineMisure, null);
	}
	
	public long salvaAttoSentenzaRicorsoIIGrado(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto,
			long idAttoOrigineMisure) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);
		Utility.popolaDocumento(attoForm, atto);
		return attoDao.salvaAttoSentenzaRicorsoIIGrado(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, idAttoOrigineMisure, null);
	}

	public Map salvaUdienza(long idUfficio, String codiceUtente, long idProcedimento, long idTipoAtto, long idCategoriaAtto, AttoForm udienza) {

		UdienzaForm uForm = (UdienzaForm) udienza;
		TUdienza uPojo = new TUdienza();
		TUdienzaId id = new TUdienzaId();
		TProcedimento proc = new TProcedimento();
		proc.setCodiKIdProc(idProcedimento);
		id.setCodiKIdUffOwner((int) idUfficio);
		uPojo.setId(id);
		uPojo.setDescUtente(codiceUtente);
		uPojo.setTProcedimento(proc);
		mapperBean.map(uForm, uPojo);
		if (uForm.getOraInizioUdienza() != null && uForm.getMinutiInizioUdienza() != null)
			uPojo.setOraInizioUdienza(uForm.getOraInizioUdienza().getId() + ":" + uForm.getMinutiInizioUdienza().getId());
		Blob blobDocumento = null;
		if (uForm.getAllegato().getFileData() != null) {
			byte b[] = uForm.getAllegato().getFileData().getBytes();
			blobDocumento = Hibernate.createBlob(b);
		}
		// uPojo.setFileDocumento(blob);
		return attoDao.salvaUdienza(uPojo, idTipoAtto, idCategoriaAtto, blobDocumento, uForm.getAllegato().getFileName(), uForm.getAllegato()
				.getContentType());
	}

	public Map salvaUdienzaDecretoSeqConv(long idUfficio, String codiceUtente, long idProcedimento, long codiceAtto, long codUdienzaFiss,
			long codUdienzaVerbRis, UdienzaForm uForm) {

		TUdienza uPojo = new TUdienza();
		TUdienzaId id = new TUdienzaId();
		TProcedimento proc = new TProcedimento();
		proc.setCodiKIdProc(idProcedimento);
		id.setCodiKIdUffOwner((int) idUfficio);
		uPojo.setId(id);
		uPojo.setDescUtente(codiceUtente);
		uPojo.setTProcedimento(proc);
		mapperBean.map(uForm, uPojo);
		if (uForm.getOraInizioUdienza() != null && uForm.getMinutiInizioUdienza() != null)
			uPojo.setOraInizioUdienza(uForm.getOraInizioUdienza().getId() + ":" + uForm.getMinutiInizioUdienza().getId());
		return attoDao.salvaUdienzaDecretoSeqConv(uPojo, codiceAtto, codUdienzaFiss, codUdienzaVerbRis);
	}

	public void eliminaUdienzaSeqConv(long idUdienza, long codAtto, long idUfficio) {

		attoDao.eliminaUdienzaSeqConv(idUdienza, codAtto, idUfficio);
		return;
	}

	public Map inserisciDecretoAnticipazioneRinvioUdienza(long idUfficio, String codiceUtente, long idProcedimento, long idTipoAtto,
			long idCategoriaAtto, AttoForm udienza) {

		UdienzaForm uForm = (UdienzaForm) udienza;
		TUdienza uPojo = new TUdienza();
		TUdienzaId id = new TUdienzaId();
		TProcedimento proc = new TProcedimento();
		proc.setCodiKIdProc(idProcedimento);
		id.setCodiKIdUffOwner((int) idUfficio);
		uPojo.setId(id);
		uPojo.setDescUtente(codiceUtente);
		uPojo.setTProcedimento(proc);
		mapperBean.map(uForm, uPojo);
		// if(uForm.getOraInizioUdienza() != null &&
		// uForm.getMinutiInizioUdienza() != null)
		// uPojo.setOraInizioUdienza(uForm.getOraInizioUdienza().getId() + ":" +
		// uForm.getMinutiInizioUdienza().getId());
		Blob blobDocumento = null;
		// if(uForm.getFileData() != null) {
		// byte b[] = uForm.getFileData().getBytes();
		// blobDocumento = Hibernate.createBlob(b);
		// }
		// uPojo.setFileDocumento(blob);
		return attoDao.inserisciDecretoAnticipazioneRinvioUdienza(uPojo, idTipoAtto, idCategoriaAtto, blobDocumento, uForm.getAllegato()
				.getFileName(), uForm.getAllegato().getContentType(), uForm.getUdienzaPrecedente().getCodUdienza());
	}

	public void setAttoDao(AttoDao attoDao) {

		this.attoDao = attoDao;
	}

	public void setMisuraService(MisuraService misuraService) {

		this.misuraService = misuraService;
	}

	/**
	 * aggiornaIstanzaSoggettoDiffidato
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param attoForm
	 * @param codUtente
	 * @return long
	 */
	public Long aggiornaDecisorioIGradoSoggettoDiffidato(long idUffOwn, long idProcInUdienza, long idProcGenerale, long idProcedimento,
			AttoForm attoForm, String codUtente) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);
		// byte b[] = attoForm.getFileData().getBytes();
		// if (b.length > 0) {
		// Blob blob = Hibernate.createBlob(b);
		// atto.setFileDocumento(blob);
		// atto.setDescNomeDocumento(attoForm.getFileName());
		// atto.setDescTipoDocumento(attoForm.getContentType());
		// } else
		// atto.setFileDocumento(null);
		Utility.popolaDocumento(attoForm, atto);
		int idEsito = Integer.parseInt(attoForm.getEsitoOpposizione().getId());
		return attoDao.aggiornaDecisorioIGradoSoggettoDiffidato(idUffOwn, idProcInUdienza, idProcGenerale, idProcedimento, atto, codUtente, idEsito);
	}

	/**
	 * aggiornaDecisorioIGradoPrevenzione
	 * 
	 * @param idUffOwn
	 * @param idProcInUdienza
	 * @param idProcGenerale
	 * @param idProcProponente
	 * @param idProcedimento
	 * @param attoForm
	 * @param codUtente
	 * @return long
	 */
	public Long aggiornaDecisorioPrevenzione(long idUffOwn, long idProcInUdienza, long idProcGenerale, long idProcProponente, long idProcedimento,
			AttoForm attoForm, String codUtente, int tipoUff, long idMagistrato, String giudiceDelegato) {

		TAtto atto = popolaAttoEDocumento(attoForm);
		return attoDao.aggiornaDecisorioPrevenzione(idUffOwn, idProcInUdienza, idProcGenerale, idProcProponente, idProcedimento, atto, codUtente,
				tipoUff, idMagistrato, giudiceDelegato);
	}

	/**
	 * aggiornaSentenzaOpposizioneDissequestro
	 * 
	 * @param idUffOwn
	 * @param idProcInUdienza
	 * @param idTribunale
	 * @param idPg_Cda
	 * @param idProcedimento
	 * @param attoForm
	 * @param codUtente
	 * @return long
	 */
	public Long aggiornaSentenzaOpposizioneDissequestro(long idUffOwn, long idUff1, long idUff2, long idUff3, long idProcedimento, AttoForm attoForm,
			String codUtente) {

		TAtto atto = popolaAttoEDocumento(attoForm);
		return attoDao.aggiornaSentenzaOpposizioneDissequestro(idUffOwn, idUff1, idUff2, idUff3, idProcedimento, atto, codUtente);
	}

	/**
	 * aggiornaSentenzaRevocazioneConfisca
	 * 
	 * @param idUffOwn
	 * @param idProcInUdienza
	 * @param idTribunale
	 * @param idPg_Cda
	 * @param idProcedimento
	 * @param attoForm
	 * @param codUtente
	 * @return long
	 */
	public Long aggiornaSentenzaRevocazioneConfisca(long idUffOwn, long idUff1, long idUff2, long idUff3, long idProcedimento, AttoForm attoForm,
			String codUtente) {

		TAtto atto = popolaAttoEDocumento(attoForm);
		return attoDao.aggiornaSentenzaRevocazioneConfisca(idUffOwn, idUff1, idUff2, idUff3, idProcedimento, atto, codUtente);
	}

	public Long aggiornaDecretoAnticipazioneRinvioUdienza(UdienzaForm udienzaForm, long idUdienzaPrecedente, int idUffOwn, long idProcInUdienza,
			long idProcedimento, String codUtente) {

		TUdienza udienza = new TUdienza();
		mapperBean.map(udienzaForm, udienza);
		udienza.getId().setCodiKIdUffOwner(idUffOwn);
		udienza.setOraInizioUdienza(udienzaForm.getOraInizioUdienza().getId() + ":" + udienzaForm.getMinutiInizioUdienza().getId());

		AttoForm attoForm = (AttoForm) udienzaForm;
		TAtto atto = popolaAttoEDocumento(attoForm);
		return attoDao.aggiornaDecretoAnticipazioneRinvioUdienza(udienza, atto, idUdienzaPrecedente, idProcInUdienza, idProcedimento, codUtente);
	}

	public long inserisciAttoDecretoConvalidaSequestro(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto,
			long idAttoOrigine, String nomeMetodo) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TDecreto decreto = new TDecreto();
		mapperBean.map(attoForm, decreto);

		Utility.popolaDocumento(attoForm, atto);

		return attoDao.inserisciAttoDecretoConvalidaSequestro(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, idAttoOrigine, decreto,
				nomeMetodo);
	}
	
	/**
	 * inserisciDecretoPerditaEfficacia
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param codiceUtente
	 * @param attoForm
	 * @param idStatoAtto
	 * @param idAttoOrigine
	 * @param nomeMetodo
	 * @return
	 */
	public long inserisciDecretoPerditaEfficacia(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto,
			long idAttoOrigine, String nomeMetodo) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TDecreto decreto = new TDecreto();
		mapperBean.map(attoForm, decreto);

		Utility.popolaDocumento(attoForm, atto);

		return attoDao.inserisciDecretoPerditaEfficacia(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, idAttoOrigine, decreto,
				nomeMetodo);
	}	

	public long inserisciAttoDecretoOpposizioneDissequestro(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm,
			long idStatoAtto, long idAttoOrigine, String nomeMetodo) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TDecreto decreto = new TDecreto();
		mapperBean.map(attoForm, decreto);

		Utility.popolaDocumento(attoForm, atto);

		return attoDao.inserisciAttoDecretoOpposizioneDissequestro(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, idAttoOrigine, decreto,
				nomeMetodo);
	}

	public long inserisciAttoRevocaDecretoOpposizioneDissequestro(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm,
			long idStatoAtto, long idAttoOrigine, String nomeMetodo) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TDecreto decreto = new TDecreto();
		mapperBean.map(attoForm, decreto);

		Utility.popolaDocumento(attoForm, atto);

		return attoDao.inserisciAttoRevocaDecretoOpposizioneDissequestro(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, idAttoOrigine,
				decreto, nomeMetodo);
	}

	public long inserisciAttoRevocazioneConfisca(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto,
			long idAttoOrigine, String nomeMetodo) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TDecreto decreto = new TDecreto();
		mapperBean.map(attoForm, decreto);

		Utility.popolaDocumento(attoForm, atto);

		return attoDao
				.inserisciAttoRevocazioneConfisca(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, idAttoOrigine, decreto, nomeMetodo);
	}

	public long inserisciAttoSentenzaOpposizioneDissequestro(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm,
			long idStatoAtto, int idCatAtto, String nomeMetodo) {

		long idAtto;

		// ricerco nel procedimento l'atto di impugnazione di opposizione al
		// dissequestro
		List<Map> attiImpugnOpposizione = attoDao.getRelAttoProcByIdProcAndCat(idProcedimento, idCatAtto);
		if (attiImpugnOpposizione == null || attiImpugnOpposizione.size() == 0) {
			throw new RuntimeException("Non esiste alcun record cdi categoria Opposizione al dissequestro");
		}

		Iterator iter = attiImpugnOpposizione.iterator();
		Map map = (Map) iter.next();
		TRelAttoProc relAtto = (TRelAttoProc) map.get("relAttoProc");

		long idAttoOrigine = relAtto.getId().getCodiKIdAtto();
		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TDecreto decreto = new TDecreto();
		mapperBean.map(attoForm, decreto);

		Utility.popolaDocumento(attoForm, atto);

		idAtto = attoDao.inserisciAttoSentenzaOpposizioneDissequestro(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, idAttoOrigine,
				decreto, nomeMetodo);

		// ricerco i beni legati all'atto di origine per copiare le misure
		// patrimoniali
		// nel nuovo atto
		List<BeneSoggettoForm> beniAttoOrigine = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAttoOrigine,
				idUffOwn);

		for (int i = 0; i < beniAttoOrigine.size(); i++) {
			BeneSoggettoForm bene = beniAttoOrigine.get(i);
			long idBene = (Long) bene.getBene().getIdBene();

			copiaMisBeneAtto(idProcedimento, idAtto, idBene, idUffOwn, codiceUtente, Constants.TipiFlagDecretoRevoca.REVOCA, nomeMetodo);
		}

		return idAtto;
	}

	public long inserisciAttoSentenzaRevocazioneConfisca(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto,
			int idCatAtto, String nomeMetodo) {

		long idAtto;

		// ricerco nel procedimento l'atto di istanza di revocazione della
		// confisca
		List<Map> attiIstanzaRevocazione = attoDao.getRelAttoProcByIdProcAndCat(idProcedimento, idCatAtto);
		if (attiIstanzaRevocazione == null || attiIstanzaRevocazione.size() == 0) {
			throw new RuntimeException("Non esiste alcun record cdi categoria Opposizione al dissequestro");
		}

		Iterator iter = attiIstanzaRevocazione.iterator();
		Map map = (Map) iter.next();
		TRelAttoProc relAtto = (TRelAttoProc) map.get("relAttoProc");

		long idAttoOrigine = relAtto.getId().getCodiKIdAtto();

		// long idAttoOrigine =0;

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TDecreto decreto = new TDecreto();
		mapperBean.map(attoForm, decreto);

		Utility.popolaDocumento(attoForm, atto);

		idAtto = attoDao.inserisciAttoSentenzaRevocazioneConfisca(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, idAttoOrigine, decreto,
				nomeMetodo);

		// ricerco i beni legati all'atto di origine per copiare le misure
		// patrimoniali
		// nel nuovo atto
		List<BeneSoggettoForm> beniAttoOrigine = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAttoOrigine,
				idUffOwn);

		for (int i = 0; i < beniAttoOrigine.size(); i++) {
			BeneSoggettoForm bene = beniAttoOrigine.get(i);
			long idBene = (Long) bene.getBene().getIdBene();

			copiaMisBeneAtto(idProcedimento, idAtto, idBene, idUffOwn, codiceUtente, Constants.TipiFlagDecretoRevoca.REVOCA, nomeMetodo);
		}

		return idAtto;
	}

	public long inserisciAttoDecretoRevocaSequestro(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto,
			long idAttoOrigine, String nomeMetodo) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		// TDecreto decreto = new TDecreto();
		// mapperBean.map(attoForm, decreto);

		Utility.popolaDocumento(attoForm, atto);

		return attoDao.inserisciAttoDecretoRevocaSequestro(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, idAttoOrigine, nomeMetodo);
	}

	public void convalidaNonConvalidaSequestro(List<BeneForm> beniSelezionati, long idProcedimento, int idUffOwn, long idAtto, long idTipoMisura,
			String descUtente, String tipoOperazione, String nomeMetodo) {

		for (BeneForm bene : beniSelezionati) {
			for (String idSoggettiTitolareETitolo : bene.getListaIdCheckboxString()) {
				String idSoggetto = null;
				String idInteresse = null;
				StringTokenizer st = new StringTokenizer(idSoggettiTitolareETitolo, ";");
				while (st.hasMoreTokens()) {
					idSoggetto = st.nextToken();
					idInteresse = st.nextToken();
				}
				misuraService.convalidaNonConvalidaSequestro(idProcedimento, idAtto, bene.getIdBene(), Integer.parseInt(idInteresse),
						Long.parseLong(idSoggetto), idTipoMisura, idUffOwn, descUtente, tipoOperazione, nomeMetodo);
			}
		}
	}

	public boolean esisteDecretoConvalidaSequesto(long idAttoOrigine, long idUffOwn) {
		return attoDao.esisteDecretoConvalidaSequesto(idAttoOrigine, idUffOwn);
	}

	public Map<String, Long> inserisciAttoDecretoAsseRemNuovoRuolo(long idUfficio, String codUtente, Long idProcedimento, long idTipoAtto,
			long idCategoriaAtto, UdienzaForm udienzaForm) {

		TUdienza uPojo = new TUdienza();
		TUdienzaId id = new TUdienzaId();
		TProcedimento proc = new TProcedimento();
		proc.setCodiKIdProc(idProcedimento);
		id.setCodiKIdUffOwner((int) idUfficio);
		uPojo.setId(id);
		uPojo.setDescUtente(codUtente);
		uPojo.setTProcedimento(proc);
		mapperBean.map(udienzaForm, uPojo);
		return attoDao.inserisciAttoDecretoAsseRemNuovoRuolo(uPojo, idTipoAtto, idCategoriaAtto, udienzaForm.getUdienzaPrecedente().getCodUdienza());
	}

	public Map salvaUdienzaDecretoRiemissioneSuRuolo(long idUfficio, String codiceUtente, long idProcedimento, long codiceAtto, UdienzaForm uForm) {

		TUdienza uPojo = new TUdienza();
		TUdienzaId id = new TUdienzaId();
		TProcedimento proc = new TProcedimento();
		proc.setCodiKIdProc(idProcedimento);
		id.setCodiKIdUffOwner((int) idUfficio);
		uPojo.setId(id);
		uPojo.setDescUtente(codiceUtente);
		uPojo.setTProcedimento(proc);
		mapperBean.map(uForm, uPojo);
		if (uForm.getOraInizioUdienza() != null && uForm.getMinutiInizioUdienza() != null)
			uPojo.setOraInizioUdienza(uForm.getOraInizioUdienza().getId() + ":" + uForm.getMinutiInizioUdienza().getId());

		TAtto atto = new TAtto();
		mapperBean.map(uForm, atto);
		// Gestione documento allegato
		// byte b[] = null;
		// if(uForm.getFileData()!=null)
		// b = uForm.getFileData().getBytes();
		// if (b!=null && b.length > 0) {
		// Blob blob = Hibernate.createBlob(b);
		// atto.setFileDocumento(blob);
		// atto.setDescNomeDocumento(uForm.getFileName());
		// atto.setDescTipoDocumento(uForm.getContentType());
		// } else
		// atto.setFileDocumento(null);

		Utility.popolaDocumento(uForm, atto);
		return attoDao.salvaUdienzaDecretoRiemissioneSuRuolo(uPojo, codiceAtto, atto);
	}

	public long inviaAttoDecretoRemissioneSuRuolo(AttoForm attoForm, long idStatoAtto, int idUffOwn, Long idProcedimento, String codiceUtente,
			long idProcUdi, long codUdienzaPrec, long codUdienzaNew) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);
		// Gestione documento allegato fatta nel salvataggio precedente

		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return attoDao.inviaAttoDecretoRemissioneSuRuolo(atto, idUffOwn, idProcedimento, codiceUtente, idStatoAtto, codUdienzaPrec, codUdienzaNew,
				idProcUdi);
	}

	public long inviaAttoDecretoAssegnazioneNuovoRuolo(AttoForm attoForm, long idStatoAtto, int idUffOwn, Long idProcedimento, String codiceUtente,
			long idProcUdi, long codUdienza) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return attoDao.inviaAttoDecretoAssegnazioneNuovoRuolo(atto, idUffOwn, idProcedimento, codiceUtente, idStatoAtto, codUdienza, idProcUdi);
	}

	public long inserisciAttoGenerico(int idUfficio, String codUtente, Long idProcedimento, AttoForm attoForm, long idStatoAtto, String nomeMetodo) {
		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		return attoDao.inserisciAttoGenerico(idUfficio, idProcedimento, codUtente, atto.getTTpTipoAtto().getCodiKIdTipoAtto(), atto.getTTpCatAtto()
				.getCodiKIdCatAtto(), (int) idStatoAtto, nomeMetodo);
	}

	public Object getAttoByPrimaryKey(Class<?> clazz, Criterion c1, Criterion c2) {

		return attoDao.getAttoByPrimaryKey(clazz, c1, c2);

	}

	/**
	 * getAttoById
	 * 
	 * @param idAtto
	 * @param idUfficio
	 * @return TAtto
	 */
	public TAtto getTAttoById(long idAtto, long idUfficio) {

		TAttoId id = new TAttoId(idAtto, (int) idUfficio);
		TAtto atto = attoDao.getAttoById(id);

		return atto;
	}

	public boolean firmaRemotaDocumento(AttoForm attoForm, long idDocumentale, long idUfficio) {
		boolean result = false;

		if (idDocumentale > 0 && attoForm.getAllegato().getFirmaRemota().isFirmaImmediata()) {

			// TODO TRY DA VERIFICARE CON ROBERTA
			try {
				// controllo se è già firmato
				TAtto tAtto = getTAttoById(attoForm.getCodAtto(), idUfficio);
				if (tAtto.getFlgFirma() == 'S') {
					if (logger.isDebugEnabled()) {
						logger.debug("**********GESTIONE DOCUMENTALE ==> firmaRemotaDocumento: DOCUMENTO GIA' FIRMATO");
					}
					attoForm.getAllegato().setDocumentoFirmato("S");
					return result;
				}
				if (documentaleService.firmaRemota(Utility.longToBigInteger(idDocumentale), attoForm.getAllegato().getFirmaRemota().getUserName()
						.toString(), attoForm.getAllegato().getFirmaRemota().getPassword().toString(), attoForm.getAllegato().getFirmaRemota()
						.getDocumentalePin().toString(), attoForm.getIdUfficio()))
					result = attoDao.eseguitoFirmaRemota(idDocumentale);

				if (result)
					attoForm.getAllegato().setDocumentoFirmato("S");
			} catch (Exception e) {
				result = false;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("**********GESTIONE DOCUMENTALE ==> firmaRemotaDocumento: " + result);
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("**********GESTIONE DOCUMENTALE ==> firmaRemotaDocumento: DA NON FIRMARE idDocumentale " + idDocumentale
						+ " isFirmaImmediata: " + attoForm.getAllegato().getFirmaRemota().isFirmaImmediata());
			}
		}

		return result;
	}

	public void rimuoviDocumentoAllegatoDaAtto(long idUfficio, long codAtto, BigInteger numeDocumentoPdoc, long idProcedimento) {
		Integer idUfficioI = (int)(long) idUfficio;
		boolean esitoEliminaDocumentoPDoc = documentaleService.eliminaDocumento(numeDocumentoPdoc, idUfficioI);

		if (logger.isDebugEnabled()) {
			logger.debug("##########GESTIONE DOCUMENTALE  documentaleService.eliminaDocumento esitoEliminaDocumentoPDoc: "
					+ esitoEliminaDocumentoPDoc);
		}
		if (esitoEliminaDocumentoPDoc) {
			rimuoviDocumentoAllegatoDaTAtto(idUfficio, codAtto, idProcedimento);
		}
	}

	public AllegatoForm extractBlobFromDocumentazione(Long numeroAttoPDoc, int idUffOwn) {

		//P.Q. 05/07/2022 Ticket 20220627018 Inizio
		byte[] allegato;
		allegato = documentaleService.recuperoDocumento(Utility.longToBigInteger(numeroAttoPDoc), idUffOwn);
		//P.Q. 05/07/2022 Ticket 20220627018 Fine
		AllegatoForm allegatoForm = new AllegatoForm();
		//P.Q. 05/07/2022 Ticket 20220627018 Inizio
		if (allegato != null) {
			allegatoForm.setDocumento(Hibernate.createBlob(allegato));
		}
		//allegatoForm.setDocumento(Hibernate.createBlob(documentaleService.recuperoDocumento(Utility.longToBigInteger(numeroAttoPDoc))));
		//P.Q. 05/07/2022 Ticket 20220627018 Fine		
		return allegatoForm;
	}

	public void rimuoviDocumentoAllegatoDaTAtto(final long idUfficio, final long codAtto, final long idProcedimento) {

		attoDao.rimuoviDocumentoAllegatoDaAtto(idUfficio, codAtto, idProcedimento);

	}

	public KeyMessageDto cancellaAtto(int idUfficio, long codAtto, String descUtente, String nomeMetodo) {
		return attoDao.cancellaAtto(idUfficio, codAtto, descUtente, nomeMetodo);
	}

	public ArrayList<Documento> getDocumentiAttiByProcedimento(long idProcedimento) {

		ArrayList<Documento> documenti = new ArrayList<Documento>();

		List daoResults = attoDao.getAttiOrdineCronologico(idProcedimento);
		Iterator iter = daoResults.iterator();
		while (iter.hasNext()) {
			Map map = (Map) iter.next();
			TAtto currentAtto = (TAtto) map.get("atto");
			if (currentAtto.getNumeDocumentoPdoc() != null && currentAtto.getNumeDocumentoPdoc() > 0) {
				Documento doc = new Documento();
				doc.setIddocumento(new BigInteger(currentAtto.getNumeDocumentoPdoc().toString()));

				TRelAttoProc rel = attoDao.getRelAttoProcByAtto(idProcedimento, currentAtto.getId().getCodiKIdUffOwner(), currentAtto.getId()
						.getCodiKIdAtto());

				if (rel.getFlgValido() != 'N') {
					doc.setOperazione("C");
				} else {
					doc.setOperazione("S");
				}

				documenti.add(doc);
			}

		}
		return documenti;
	}

	public ArrayList<BigInteger> getIdsDocumentiAttiByProcedimento(long idProcedimento) {

		ArrayList<BigInteger> idsDocumenti = new ArrayList<BigInteger>();

		List daoResults = attoDao.getAttiOrdineCronologico(idProcedimento);
		Iterator iter = daoResults.iterator();
		while (iter.hasNext()) {
			Map map = (Map) iter.next();
			TAtto currentAtto = (TAtto) map.get("atto");
			if (currentAtto.getNumeDocumentoPdoc() != null && currentAtto.getNumeDocumentoPdoc() > 0) {
				idsDocumenti.add(new BigInteger(currentAtto.getNumeDocumentoPdoc().toString()));
			}
		}
		return idsDocumenti;
	}

	public List<TRelAtti> getRelAtti(long idAttoOrigine, int idUffOwnerOrigine, long idAttoFinale, int idUffOwnerFinale, String descUtente,
			String nomeMetodo) {

		return attoDao.getRelAtti(idAttoOrigine, idUffOwnerOrigine, idAttoFinale, idUffOwnerFinale, descUtente, nomeMetodo);
	}

	public String numeroAttoOrigine(long idProcedimento, long idAttoFinale, int idUffOwnerFinale, String descUtente, String nomeMetodo) {

		List<TRelAtti> listaAtti = attoDao.getRelAtti(0, 0, idAttoFinale, idUffOwnerFinale, descUtente, nomeMetodo);
		if ((listaAtti == null) || listaAtti.isEmpty()) {
			return null;
		}
		Iterator iterator = listaAtti.iterator();
		String numeroAttoOrigine = "";
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TRelAtti relAtti = (TRelAtti) row.get("relAtti");
			int idUffOwnOrigine = relAtti.getId().getCodiKIdUffOwnerOr();
			long idAttoOrigine = relAtti.getId().getCodiKIdAttoOr();

			DettaglioAttoDto dettaglioAtto = attoDao.dettaglioAtto(idUffOwnOrigine, idAttoOrigine, idProcedimento, descUtente);
			if (dettaglioAtto == null || (dettaglioAtto.getNUME_ANNO_ATTO() == null && dettaglioAtto.getNUME_ATTO() == null))
				return null;
			else
				numeroAttoOrigine = builNumeroAttoView(dettaglioAtto.getNUME_ANNO_ATTO(), dettaglioAtto.getNUME_ATTO());
			break;
		}
		return numeroAttoOrigine;

	}

	public long idAttoOrigine(long idProcedimento, long idAttoFinale, int idUffOwnerFinale, String descUtente, String nomeMetodo) {

		List<TRelAtti> listaAtti = attoDao.getRelAtti(0, 0, idAttoFinale, idUffOwnerFinale, descUtente, nomeMetodo);
		if ((listaAtti == null) || listaAtti.isEmpty()) {
			return 0;
		}
		Iterator iterator = listaAtti.iterator();
		long idAttoOrigine = 0;
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TRelAtti relAtti = (TRelAtti) row.get("relAtti");
			int idUffOwnOrigine = relAtti.getId().getCodiKIdUffOwnerOr();
			idAttoOrigine = relAtti.getId().getCodiKIdAttoOr();

			DettaglioAttoDto dettaglioAtto = attoDao.dettaglioAtto(idUffOwnOrigine, idAttoOrigine, idProcedimento, descUtente);
			if (dettaglioAtto == null || (dettaglioAtto.getNUME_ANNO_ATTO() == null && dettaglioAtto.getNUME_ATTO() == null))
				return 0;
			break;
		}
		return idAttoOrigine;

	}

	private TAtto popolaAttoEDocumento(AttoForm attoForm){
		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);
		Utility.popolaDocumento(attoForm, atto);
		return atto;
	}

	public KeyMessageDto esisteDecretoSequestroProcedimento(int idUffOwn, long idProcedimento, String codiceUtente, long idStatoAtto, String nomeMetodo) {
		return attoDao.isEsisteDecretoSequestroProcedimento(idProcedimento, idUffOwn, codiceUtente, nomeMetodo);
	}

	public long salvaDecretoRigettoTotaleSequestro(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto){
		return salvaAttoDecretoConMagistrato(idUffOwn, idProcedimento, codiceUtente, attoForm, idStatoAtto);
	}

	public KeyMessageDto insDecretoRigettoTotaleSequestro(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm,
			long idStatoAtto, String nomeMetodo) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		// Controllo che la procedura di Controllo_Quota dia esito positivo
		// altrimenti interrompo l'operazione e mostro l'errore all'utente a
		// video
		KeyMessageDto messaggioRitorno = attoDao.isEsisteDecretoSequestroProcedimento(idProcedimento, idUffOwn, codiceUtente, nomeMetodo);
		if (!messaggioRitorno.isError()) {
			KeyMessageDto messDecreto = attoDao.insDecretoRigettoTotaleSequestro(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto,
					nomeMetodo);
			return messDecreto;
		} else {
			return messaggioRitorno;
		}

	}

	public Long inviaAttoDecretoRigettoTotaleSequestro(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,
			long categoriaAtto, long idStatoAtto, long idDestinatario, long idUltDestinatario, long codUdienzaAtto, long codUdienzaFiss,
			long codUdienzaInFaseDiRip, long codUdienzaInFaseDiRim, long statoUdienzaPrec, String nomeMetodo) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return attoDao.inviaAttoDecretoRigettoTotaleSequestro(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto, idStatoAtto,
				idDestinatario, idUltDestinatario, codUdienzaAtto, codUdienzaFiss, codUdienzaInFaseDiRip, codUdienzaInFaseDiRim, statoUdienzaPrec,
				nomeMetodo);
	}

	public String getNumeroProcedimentoOrigine(long idProcedimento, long idAtto, int idUffOwn, String codiceUtente, String nomeMetodo) {

		// TODO
		// Controllo su T_REL_PROC_PROC se per il procedimento corrente esistono
		// due record con lo stesso CODI_FK_UFF_OWNER2

		// PROCEDURA: restituisce una lista listaRelProcProc di T_REL_PROC_PROC
		List<TRelProcProc> listaRelProcProc = procedimentoService.getTRelProcProcByProc1(idProcedimento, null, codiceUtente, nomeMetodo);
		// controllo se esistono due record con lo stesso CODI_FK_UFF_OWNER2
		boolean flag = esisteProcConUffOwnerUguale(listaRelProcProc);
		String numeroProcedStralcio = "";

		TAtto tAtto = getTAttoById(idAtto, idUffOwn);

		if (flag) {
			// cerco nella listaRelProcProc se esiste un CODI_K_ID_PROC2 =
			// codiIdProcEmiss (dell'atto che sto ciclando)
			// ==>se esiste prendo il NUME_ANNO_PROC2 / NUME_PROCEDIMENTO2 (e lo
			// accodo alla descrizione dell'atto)
			for (Iterator<TRelProcProc> iterator = listaRelProcProc.iterator(); iterator.hasNext();) {
				Map<String, Object> row = (Map<String, Object>) iterator.next();
				TRelProcProc tRelProcProc = (TRelProcProc) row.get("relProcProc");

				if (tAtto.getCodiIdProcEmiss()!=null && ( tRelProcProc.getId().getCodiKIdProc2() == tAtto.getCodiIdProcEmiss())) {
					String numProvProc = getNumeroAnnoNumeroAtto(String.valueOf(tRelProcProc.getNumeAnnoProc2()),
							String.valueOf(tRelProcProc.getNumeProcedimento2()));
					ComboForm tribunale = commonService.getDettaglioUfficio(tRelProcProc.getTAnUfficio().getCodiKIdUfficio());

					numeroProcedStralcio = " (Emesso nel procedimento " + numProvProc + " dell'ufficio " + tribunale.getDescrizione() + ")";
					break;
				}
			}
		}

		return numeroProcedStralcio;

	}

	public Long registraAttoGenerico(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long idStatoAtto, String nomeMetodo) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TAttoGenerico attoGenerico = new TAttoGenerico();
		mapperBean.map(attoForm, attoGenerico);

		Utility.popolaDocumento(attoForm, atto);
		return attoDao.registraAttoGenerico(idUfficio, idProcedimento, atto, codUtente, idStatoAtto, attoGenerico, nomeMetodo);

	}
	
	
	public Long registraOrdinanazaArt15Antimafia(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long idStatoAtto, String nomeMetodo) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TAttoGenerico attoGenerico = new TAttoGenerico();
		mapperBean.map(attoForm, attoGenerico);
	
		Utility.popolaDocumento(attoForm, atto);
		
		return attoDao.registraOrdinanazaArt15Antimafia(idUfficio, idProcedimento, atto, codUtente, idStatoAtto,attoForm.getDataCessazioneMisura(),attoGenerico, nomeMetodo);

	}
	
	public Long registraIntercettazioniTelefoniche(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long idStatoAtto, String nomeMetodo) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TAttoGenerico attoGenerico = new TAttoGenerico();
		mapperBean.map(attoForm, attoGenerico);
	
		Utility.popolaDocumento(attoForm, atto);
		
		long idMagistrato = 0;
		if(attoForm.getMagistrato() != null && attoForm.getMagistrato().getId() != null)
		   idMagistrato=Long.parseLong(attoForm.getMagistrato().getId());
		
		return attoDao.registraIntercettazioniTelefoniche(idUfficio, idProcedimento, atto, codUtente, idStatoAtto, idMagistrato, attoGenerico, nomeMetodo);

	}	

	public Long registraOrdinanazaArt14Antimafia(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long idStatoAtto, String nomeMetodo) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TAttoGenerico attoGenerico = new TAttoGenerico();
		mapperBean.map(attoForm, attoGenerico);
	
		Utility.popolaDocumento(attoForm, atto);
		
		long idMagistrato = 0;
		if(attoForm.getMagistrato() != null && attoForm.getMagistrato().getId() != null)
		   idMagistrato=Long.parseLong(attoForm.getMagistrato().getId());
		
		return attoDao.registraOrdinanazaArt14Antimafia(idUfficio, idProcedimento, atto, codUtente, idStatoAtto,idMagistrato,attoForm.getDataCessazioneMisura(),attoForm.getDataRipresaMisura(), attoGenerico, nomeMetodo);

	}	
	
	// MODIFICA 01/2014 - Gestione controllo collegio Presidenziale/Collegiale
	public KeyMessageDto controllaCollegioAtto(long idProcedimento, String idCatAtto, String descUtente, long idUffOwn, String printMethodName) {
		return attoDao.controllaCollegioAtto(idProcedimento, idCatAtto, descUtente, idUffOwn, printMethodName);
	}

	public KeyMessageDto controllaCollegioAttoInvio(long idProcedimento, long idAtto, String descUtente, long idUfficio, String printMethodName) {
		return attoDao.controllaCollegioAttoInvio(idProcedimento, idAtto, descUtente, idUfficio, printMethodName);
	}

	// MODIFICA 01/2014 - Gestione controllo collegio Presidenziale/Collegiale

	public MagistratoForm getMagistratoPerAtto(final Integer idUffOwn, final long idAtto, final int descrRuolo) {

		Long idMagistrato = attoDao.getMagistratoPerAtto(idUffOwn, idAtto, descrRuolo);

		if (idMagistrato != null) {
			return getDettaglioMagistratoAtto(idUffOwn, idAtto, idMagistrato, descrRuolo + "");
		}
		return null;
	}

	/**
	 * getRicercaSoggettoAtto
	 * 
	 * @param idProcedimento
	 * @param descRuolo
	 * @param flagValido
	 * @return List<SoggettoForm>
	 */

	public List<SoggettoForm> getRicercaSoggettoAtto(long idProcedimento, long idatto, int iduff, int descRuolo, Character flagValido) {

		List<SoggettoForm> lista = new ArrayList<SoggettoForm>();

		List<SogIntDettaglioProcedimentoDto> soggettiProcedimento = attoDao.getRicercaSoggettoAtto(idProcedimento, idatto, iduff, descRuolo,
				flagValido);
		Iterator<SogIntDettaglioProcedimentoDto> iter = soggettiProcedimento.iterator();
		while (iter.hasNext()) {
			SoggettoForm soggettoForm = new SoggettoForm();
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, soggettoForm);
			// Art67 e Cauzione sono significativi solo per le misure PERSONALI
			if (soggettoForm.getMisura().getDescrizioneMisura() == null || soggettoForm.getMisura().getDescrizioneMisura().contains("PATRIMONIALE")) {
				soggettoForm.getMisura().setArt67(null);
				soggettoForm.getMisura().setCauzione(null);
			}
			lista.add(soggettoForm);
		}
		return lista;

	}

	/**
	 * getRicercaSoggettoRicorso
	 * @param idProcedimento
	 * @param idatto
	 * @param iduff
	 * @param descRuolo
	 * @return
	 */
	public List<SoggettoForm> getRicercaSoggettoRicorso(long idProcedimento, long idatto, int iduff, int descRuolo) {

		List<SoggettoForm> lista = new ArrayList<SoggettoForm>();

		List<SogIntDettaglioProcedimentoDto> soggettiProcedimento = attoDao.getRicercaSoggettoRicorso(idProcedimento, idatto, iduff, descRuolo);
		Iterator<SogIntDettaglioProcedimentoDto> iter = soggettiProcedimento.iterator();
		
		while (iter.hasNext()) {
			SoggettoForm soggettoForm = new SoggettoForm();
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, soggettoForm);
			lista.add(soggettoForm);
		}
		return lista;

	}	
	
	
   /**
    * getRicercaAvvRicorso
    * @param idProcedimento
    * @param idatto
    * @param iduff
    * @return List<DifensoreForm>
    */
	public List<DifensoreForm> getRicercaAvvRicorso(long idProcedimento,long idatto, int iduff) {

		List<DifensoreForm> results = new ArrayList<DifensoreForm>();
		List<TAvvocati> daoResults = attoDao.getRicercaAvvRicorso(idProcedimento, idatto, iduff);
		Iterator iter = daoResults.iterator();
		while (iter.hasNext()) {
			DifensoreForm currentForm = new DifensoreForm();
			Map map = (Map) iter.next();
			TAvvocati currentFonte = (TAvvocati) map.get("avvocati");
			mapperBean.map(currentFonte, currentForm);
			results.add(currentForm);
		}
		return results;
	}	
	
	
	
        /**
         * getricercaIstanza
         * @param idUffOwn
         * @param idProcedimento
         * @param idTipoAtto
         * @param idCatAtto
         * @return List<AttoForm>
         */
		public List<AttoForm> getricercaIstanza(int idUffOwn,  Long idProcedimento,  Long idTipoAtto,  Long idCatAtto) {

			List<AttoForm> results = new ArrayList<AttoForm>();
			List<TIstanza> daoResults = attoDao.getricercaIstanza(idUffOwn,idProcedimento, idTipoAtto, idCatAtto);
			Iterator iter = daoResults.iterator();
			while (iter.hasNext()) {
				AttoForm currentForm = new AttoForm();
				Map map = (Map) iter.next();
				TIstanza currentIstanza = (TIstanza) map.get("istanza");
				mapperBean.map(currentIstanza, currentForm);
				results.add(currentForm);
			}
			return results;
		}	
	
	
	

	/**
	 * getRicercaMisureAtto
	 * 
	 * @param idProcedimento
	 * @param idUfficio
	 * @param attoForm
	 * @param flgValido
	 * @return
	 */
	public List<SoggettoStralcioForm> getRicercaMisureAtto(long idProcedimento, long idUfficio, AttoForm attoForm, String flgValido, int categoriaAtto) {

		String idsMisure = null;
		String idsSoggInt = null;
		String idsSoggTit = null;
		Integer idTipoBene = null;
		Integer idCatBene = null;
		Integer idSottoCatBene = null;
		List<RicercaStralcioDto> queryResults = null;
		MisuraForm misuraForm = null;
		RicercaStralcioDto dto = null;
		BeneStralcioForm beneStralcioForm = null;
		SoggettoStralcioForm soggettoStralcioForm = null;
		SoggettoTitolareForm soggettoTitolareForm = null;
		List<BeneStralcioForm> listaBeneStralcioForm = null;

		if (Utility.isComboFormSelected(attoForm.getTipoMisure())) {
			idsMisure = attoForm.getTipoMisure().getId();
		}
		if (Utility.isComboFormSelected(attoForm.getSoggettiDiretti())) {
			idsSoggInt = attoForm.getSoggettiDiretti().getId();
		}
		if (Utility.isComboFormSelected(attoForm.getSoggettiTitolari())) {
			idsSoggTit = attoForm.getSoggettiTitolari().getId();
		}
		if (Utility.isComboFormSelected(attoForm.getSoggettiDirettiFiltro())) {
			idsSoggInt = attoForm.getSoggettiDirettiFiltro().getId();
		}
		if (Utility.isComboFormSelected(attoForm.getSoggettiTitolariFiltro())) {
			idsSoggTit = attoForm.getSoggettiTitolariFiltro().getId();
		}
		if (Utility.isComboFormSelected(attoForm.getTipologiaBene())) {
			idTipoBene = Integer.valueOf(attoForm.getTipologiaBene().getId());
		}
		if (Utility.isComboFormSelected(attoForm.getCategoriaBene())) {
			idCatBene = Integer.valueOf(attoForm.getCategoriaBene().getId());
		}
		if (Utility.isComboFormSelected(attoForm.getSottoCategoriaBene())) {
			idSottoCatBene = Integer.valueOf(attoForm.getSottoCategoriaBene().getId());
		}

        if (categoriaAtto == 24 || categoriaAtto == 335){ //cassazione
			queryResults = attoDao.getRicercaMisureAttoCass(attoForm.getCodAtto(), idProcedimento, idUfficio, idsMisure, idsSoggInt, idsSoggTit, idTipoBene,
					idCatBene, idSottoCatBene, flgValido, categoriaAtto, Integer.parseInt(attoForm.getStatoAtto().getId()));       	
        }else{//cda
			queryResults = attoDao.getRicercaMisureAtto(attoForm.getCodAtto(), idProcedimento, idUfficio, idsMisure, idsSoggInt, idsSoggTit, idTipoBene,
					idCatBene, idSottoCatBene, flgValido, categoriaAtto, Integer.parseInt(attoForm.getStatoAtto().getId()));
        }

		List<SoggettoStralcioForm> risultati = new ArrayList<SoggettoStralcioForm>();
		if (queryResults == null || queryResults.isEmpty()) {
			return risultati;
		}

		for (int i = 0; i < queryResults.size(); i++) {

			dto = (RicercaStralcioDto) queryResults.get(i);
			soggettoStralcioForm = new SoggettoStralcioForm();
			misuraForm = new MisuraForm();
			BigDecimal prevSoggInt = null;
			BigDecimal prevIdBene = null;
			BigDecimal prevIdSoggInt = null;
			boolean isFirst = true;
			mapperBean.map(dto, soggettoStralcioForm);

			listaBeneStralcioForm = new ArrayList<BeneStralcioForm>();
			soggettoStralcioForm.setListaBeneStralcioForm(listaBeneStralcioForm);

			prevSoggInt = dto.getCODI_K_ID_SOGG_INT();

			// inizio gestione NO_MIS_ID
			if (Constants.TipoMisura.NO_MIS_ID.equalsIgnoreCase(dto.getTIPO_MISURA())) {
				isFirst = false;
				mapperBean.map(dto, misuraForm);
				soggettoStralcioForm.setFlgValido(String.valueOf(dto.getFLG_VALIDO()));
				soggettoStralcioForm.setMisura(misuraForm);
				// continue;
			} else {

				// ciclo sui beni
				for (; i < queryResults.size(); i++) {
					if (!isFirst) {
						dto = (RicercaStralcioDto) queryResults.get(i);
					}

					if (prevSoggInt.equals(dto.getCODI_K_ID_SOGG_INT())) {

						// inizio gestione MIS_PERS
						if (Constants.TipoMisura.PERSONALE_ID.equalsIgnoreCase(dto.getTIPO_MISURA())) {
							isFirst = false;
							misuraForm = new MisuraForm();
							mapperBean.map(dto, misuraForm);

							// soggettoStralcioForm.setMisura(misuraForm);
							beneStralcioForm = new BeneStralcioForm();
							// mapping bene
							misuraForm.setCarattereMisura(dto.getCARATTERE_MISURA());
							mapperBean.map(dto, beneStralcioForm);
							beneStralcioForm.setMisura(misuraForm);
							listaBeneStralcioForm.add(beneStralcioForm);
							continue;
						} else {
							isFirst = true;
						}
						// fine gestione MIS_PERS

						beneStralcioForm = new BeneStralcioForm();
						mapperBean.map(dto, beneStralcioForm);
						listaBeneStralcioForm.add(beneStralcioForm);

						List<SoggettoTitolareForm> listSoggettoTitolareForm = new ArrayList<SoggettoTitolareForm>();
						beneStralcioForm.setListaSoggettoTitolareForm(listSoggettoTitolareForm);
						prevIdBene = dto.getCODI_K_ID_BENE();
						prevIdSoggInt = dto.getCODI_K_ID_SOGG_INT();

						// ciclo sui soggetti titolare
						for (; i < queryResults.size(); i++) {
							if (!isFirst) {
								dto = (RicercaStralcioDto) queryResults.get(i);
							}
							isFirst = false;
							if (prevIdBene.equals(dto.getCODI_K_ID_BENE()) && prevIdSoggInt.equals(dto.getCODI_K_ID_SOGG_INT())) {
								soggettoTitolareForm = new SoggettoTitolareForm();
								mapperBean.map(dto, soggettoTitolareForm);
								listSoggettoTitolareForm.add(soggettoTitolareForm);
							} else {
								isFirst = true;
								i--;
								break;
							}
						}
					} else {
						isFirst = true;
						i--;
						break;
					}
				}
			}
			risultati.add(soggettoStralcioForm);
		}
		return risultati;
	}

	/**
	 * salvaAttoRicorso
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param codiceUtente
	 * @param attoForm
	 * @param idStatoAtto
	 * @param idAttoOrigineMisure
	 * @return long id atto originato
	 */
	public long salvaAttoRicorso(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto,
			long idAttoOrigineMisure) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		long idAtto = attoDao.salvaAttoRicorso(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, idAttoOrigineMisure);

		return idAtto;
	}

	public Long registraAttoRicorsoInAppello(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,
			long categoriaAtto, long idStatoAtto, long idDestinatario, String tipoUfficio) {

		TAtto atto = new TAtto();
		TImpugnazione impugnazioneTmp = new TImpugnazione();
		TRelSoggImp soggImpTmp = new TRelSoggImp();
		
		mapperBean.map(attoForm, atto);
		ImpugnazioneForm impugnazione = attoForm.getImpugnazione();
		
		mapperBean.map(attoForm.getImpugnazione(), impugnazioneTmp);
		
		mapperBean.map(attoForm.getImpugnazione(), soggImpTmp);

		String listaIdSoggetti = null;
		// per la procura e la procura generale non avvaloro impugnazione
		if (tipoUfficio.equalsIgnoreCase(Constants.TIPO_UFFICIO_TRIBUNALE)) {
			TSoggetto soggetto = new TSoggetto();
			TSoggettoId soggettoId = new TSoggettoId();
			soggetto.setId(soggettoId);
			
			listaIdSoggetti = "";
			
			if (impugnazione.getParte() != null) {
				if (impugnazione.getParte().getId() != null && impugnazione.getParte().getId().equals("D")) {
					
					
					Iterator itx = impugnazione.getIdsDirettiInteressati().iterator();
                	while (itx.hasNext()) {
						listaIdSoggetti += "" + itx.next();
						if (itx.hasNext()) {
							listaIdSoggetti += ",";
						}
					}
				

				}
				if (impugnazione.getParte().getId() != null && impugnazione.getParte().getId().equals("N")) {
					
					
					Iterator itx = impugnazione.getIdsDirettiInteressati().iterator();
                	while (itx.hasNext()) {
						listaIdSoggetti += "" + itx.next();
						if (itx.hasNext()) {
							listaIdSoggetti += ",";
						}
					}
				

				}				
				if (impugnazione.getParte().getId() != null && impugnazione.getParte().getId().equals("T")) {
					
					Iterator itx = impugnazione.getIdsSoggettiTitolari().iterator();
                	while (itx.hasNext()) {
						listaIdSoggetti += "" + itx.next();
						if (itx.hasNext()) {
							listaIdSoggetti += ",";
						}
					}					

				}
				if (impugnazione.getParte().getId() != null && impugnazione.getParte().getId().equals("A")) {
					
					
					
					
					List<TAvvocati> listaAvvocati = getAvvocatiDifensori(idProcedimento, idUfficio,
							Long.parseLong(impugnazione.getAvvocatiDifensori().getId()));
					soggImpTmp.setDescCognAvv(listaAvvocati.get(0).getDescCognomeAvv());
					soggImpTmp.setDescNomeAvv(listaAvvocati.get(0).getDescNomeAvv());
					
					Iterator itx = impugnazione.getIdsSoggettiDifensori().iterator();
                	while (itx.hasNext()) {
						listaIdSoggetti += "" + itx.next();
						if (itx.hasNext()) {
							listaIdSoggetti += ",";
						}
					}	
					

				}
				
				ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
				String tipoProc = procedimentoForm.getTipoProcedimento().getId();
				if (impugnazione.getParte().getId() != null && impugnazione.getParte().getId().equals("I") && attoForm.getCategoriaAtto()!=null && tipoProc.equals(""+Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM) ){
					List<SoggettoForm> soggettiIntervenienti = procedimentoService.soggettiDirettiInteressatiDelProcedimento(idProcedimento, Constants.RUOLO_INTERVENTIENTE, Constants.Flag.S);
					
					Iterator itx = soggettiIntervenienti.iterator();
	            	while (itx.hasNext()) {
	            		SoggettoForm sogg = (SoggettoForm)itx.next();
						listaIdSoggetti += "" + sogg.getIdSoggetto();
						if (itx.hasNext()) {
							listaIdSoggetti += ",";
						}
					}					
									
					
				}					
				
			
			}
		
		
			if (impugnazione.getParte() != null && impugnazione.getParte().getId() != null && impugnazione.getParte().getId().equals("E")) {
				List<TAvvocati> listaAvvocati = getAvvocatiDifensori(idProcedimento, idUfficio,
						Long.parseLong(impugnazione.getAvvocatiDifensori().getId()));
				soggImpTmp.setDescCognAvv(listaAvvocati.get(0).getDescCognomeAvv());
				soggImpTmp.setDescNomeAvv(listaAvvocati.get(0).getDescNomeAvv());
				
				Iterator itx = impugnazione.getIdsSoggettiDifensori().iterator();
	        	while (itx.hasNext()) {
					listaIdSoggetti += "" + itx.next();
					if (itx.hasNext()) {
						listaIdSoggetti += ",";
					}
				}					
	
			}				

		}
		
 
		Utility.popolaDocumento(attoForm, atto);
		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
 
		Long codiKIdAmministratore = null;
		List<AttoForm> idsAttoIstanza = getAttiByProcedimento(idProcedimento, 5l, Constants.ISTANZA_LIQUIDAZIONE_AMM);
		if(idsAttoIstanza!=null && !idsAttoIstanza.isEmpty()) {
			AttoForm af = idsAttoIstanza.get(0);					
			AmministratoreForm amministratore = procedimentoService.getAmministratoreByIstanza(idProcedimento, af.getCodAtto(), new Integer(""+idUfficio));
			if(amministratore!=null) codiKIdAmministratore = new Long(amministratore.getId());
		}
		
		BigDecimal idIntestatario = null;
		List<IntestatarioDto> lista = procedimentoDaoCam.getIntestatari(new Integer(""+idUfficio), idProcedimento);
		if(lista != null && !lista.isEmpty()){
			IntestatarioDto intest = (IntestatarioDto)lista.get(0);  
			idIntestatario = intest.getCODI_K_ID_INTESTATARIO();
	
		}
		
		return attoDao.registraAttoRicorsoInAppello(idUfficio, idProcedimento,listaIdSoggetti, soggImpTmp ,impugnazioneTmp, atto, codUtente, tipoAtto, categoriaAtto,
				idStatoAtto, idDestinatario, codiKIdAmministratore,idIntestatario);

	}
	
	public Long registraAttoRicorsoInCassazione(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,
			long categoriaAtto, long idStatoAtto, long idDestinatario, int tipoUfficio) {

		TAtto atto = new TAtto();
		TImpugnazione impugnazioneTmp = new TImpugnazione();
		TRelSoggImp soggImpTmp = new TRelSoggImp();

		mapperBean.map(attoForm, atto);
		ImpugnazioneForm impugnazione = attoForm.getImpugnazione();
		mapperBean.map(attoForm.getImpugnazione(), impugnazioneTmp);
		
		mapperBean.map(attoForm.getImpugnazione(), soggImpTmp);		

		// per la procura generale non avvaloro impugnazione
		String listaIdSoggetti = null;
		if (tipoUfficio == Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {

			
			listaIdSoggetti = "";
			
			if (impugnazione.getParte() != null) {
				if (impugnazione.getParte().getId() != null && impugnazione.getParte().getId().equals("D")) {
					
					Iterator itx = impugnazione.getIdsDirettiInteressati().iterator();
                	while (itx.hasNext()) {
						listaIdSoggetti += "" + itx.next();
						if (itx.hasNext()) {
							listaIdSoggetti += ",";
						}
					}
		

				}
				
				if (impugnazione.getParte().getId() != null && impugnazione.getParte().getId().equals("N")) {
					
					
					Iterator itx = impugnazione.getIdsDirettiInteressati().iterator();
                	while (itx.hasNext()) {
						listaIdSoggetti += "" + itx.next();
						if (itx.hasNext()) {
							listaIdSoggetti += ",";
						}
					}
				

				}					
				if (impugnazione.getParte().getId() != null && impugnazione.getParte().getId().equals("T")) {
					
					Iterator itx = impugnazione.getIdsSoggettiTitolari().iterator();
                	while (itx.hasNext()) {
						listaIdSoggetti += "" + itx.next();
						if (itx.hasNext()) {
							listaIdSoggetti += ",";
						}
					}					

				}
				if (impugnazione.getParte().getId() != null && impugnazione.getParte().getId().equals("A")) {
					List<TAvvocati> listaAvvocati = getAvvocatiDifensori(idProcedimento, idUfficio,
							Long.parseLong(impugnazione.getAvvocatiDifensori().getId()));
					soggImpTmp.setDescCognAvv(listaAvvocati.get(0).getDescCognomeAvv());
					soggImpTmp.setDescNomeAvv(listaAvvocati.get(0).getDescNomeAvv());
					
					Iterator itx = impugnazione.getIdsSoggettiDifensori().iterator();
                	while (itx.hasNext()) {
						listaIdSoggetti += "" + itx.next();
						if (itx.hasNext()) {
							listaIdSoggetti += ",";
						}
					}					
									

				}
				
				if (impugnazione.getParte().getId() != null && impugnazione.getParte().getId().equals("E")) {
					List<TAvvocati> listaAvvocati = getAvvocatiDifensori(idProcedimento, idUfficio,
							Long.parseLong(impugnazione.getAvvocatiDifensori().getId()));
					soggImpTmp.setDescCognAvv(listaAvvocati.get(0).getDescCognomeAvv());
					soggImpTmp.setDescNomeAvv(listaAvvocati.get(0).getDescNomeAvv());
					
					Iterator itx = impugnazione.getIdsSoggettiDifensori().iterator();
                	while (itx.hasNext()) {
						listaIdSoggetti += "" + itx.next();
						if (itx.hasNext()) {
							listaIdSoggetti += ",";
						}
					}					

				}	
				
				
			}
		}

		Utility.popolaDocumento(attoForm, atto);
		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());

		Long codiKIdAmministratore = null;
		List<AttoForm> idsAttoIstanza = null;

		if (tipoUfficio == Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
			idsAttoIstanza = getAttiByProcedimento(idProcedimento, 4l, Constants.RICORSO_IN_APPELLO);	
		} else {
			idsAttoIstanza = getAttiByProcedimento(idProcedimento, 5l, Constants.ISTANZA_LIQUIDAZIONE_AMM);
		}
		if(idsAttoIstanza!=null && !idsAttoIstanza.isEmpty()) {
			AttoForm af = idsAttoIstanza.get(0);
			AmministratoreForm amministratore = null;
			if (tipoUfficio == Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
				amministratore = procedimentoService.getAmministratoreByImpugnazione(idProcedimento, af.getCodAtto(), new Integer(""+idUfficio));
			} else {
				amministratore = procedimentoService.getAmministratoreByIstanza(idProcedimento, af.getCodAtto(), new Integer(""+idUfficio));
			}				
			if(amministratore!=null) codiKIdAmministratore = new Long(amministratore.getId());
		}
	
		return attoDao.registraAttoRicorsoInCassazione(idUfficio, idProcedimento,listaIdSoggetti, soggImpTmp,impugnazioneTmp, atto, codUtente, tipoAtto, categoriaAtto,
				idStatoAtto, idDestinatario, codiKIdAmministratore);

	}	

	public void impostaRicorsoMisPatr(long idProcedimento, long idAtto, long idUffOwner, boolean checked, long idSoggInt, long idBene,
			String idSoggTit, int tipoBene, int catBene, int sottoCatBene, String codiceUtente, int catAtto,String lTipoRicorso) {
		
		if("CASSAZIONE".equalsIgnoreCase(lTipoRicorso)){
			attoDao.impostaRicorsoCassMisPatr(idProcedimento, idAtto, idUffOwner, checked, idSoggInt, idBene, idSoggTit, tipoBene, catBene, sottoCatBene,
					codiceUtente, catAtto);
		}else if("APPELLO".equalsIgnoreCase(lTipoRicorso)){
			attoDao.impostaRicorsoMisPatr(idProcedimento, idAtto, idUffOwner, checked, idSoggInt, idBene, idSoggTit, tipoBene, catBene, sottoCatBene,
					codiceUtente, catAtto);
		}

	}

	public void impostaRicorsoSoggetto(long idProcedimento, long idAtto, long idUffOwner, boolean checked, long idSoggInt, final String idsSoggTit,
			final String idsMisure, final int tipoBene, final int catBene, final int sottoCatBene, String codiceUtente, int catAtto,String lTipoRicorso) {

		
		if("CASSAZIONE".equalsIgnoreCase(lTipoRicorso)){
			attoDao.impostaRicorsoCassSoggetto(idProcedimento, idAtto, idUffOwner, checked, idSoggInt, idsSoggTit, idsMisure, tipoBene, catBene,
					sottoCatBene, codiceUtente, catAtto);
		}else if("APPELLO".equalsIgnoreCase(lTipoRicorso)){
			attoDao.impostaRicorsoSoggetto(idProcedimento, idAtto, idUffOwner, checked, idSoggInt, idsSoggTit, idsMisure, tipoBene, catBene,
					sottoCatBene, codiceUtente, catAtto);
		}		

	}

	public List<TAvvocati> getAvvocatiDifensori(long idProc, long idUfficio, long idAvvocato) {
		ProcedimentoForm procedimentoForm = (ProcedimentoForm) request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
		String tipoProcS = procedimentoForm.getTipoProcedimento().getId();
		int tipoProc = new Integer(tipoProcS); 
		if(tipoProc == Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM || tipoProc == Constants.TIPOPROC_CORREZIONE_ERRORI_CAM) {
			return attoDao.getAvvocatiDifensoriIntestatari(idProc, idUfficio, idAvvocato);
		} else {		
			return attoDao.getAvvocatiDifensori(idProc, idUfficio, idAvvocato);
		}
	}

	public List<SoggettoForm> getSoggettiDifensori(long idUfficio, long idProcedimento, long idAvvocato) {

		List<SogIntDettaglioProcedimentoDto> soggetti = attoDao.getSoggettiDifensori(idUfficio, idProcedimento, idAvvocato);
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

	public List<SoggettoForm> getSoggettiDifensoriProcura(long idUfficio, long idProcedimento) {

		List<SogIntDettaglioProcedimentoDto> soggetti = attoDao.getSoggettiDifensoriProcura(idUfficio, idProcedimento);
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

	public void impostaRicorsoMisPers(long idProcedimento, long idAtto, long idUffOwner, boolean checked, long idMisuraPers, long idSoggInt,
			final String codiceUtente, int catAtto,String lTipoRicorso) {

		if("CASSAZIONE".equalsIgnoreCase(lTipoRicorso)){
			attoDao.impostaRicorsoCassMisPers(idProcedimento, idAtto, idUffOwner, checked, idMisuraPers, idSoggInt, codiceUtente, catAtto);
		}else if("APPELLO".equalsIgnoreCase(lTipoRicorso)){
			attoDao.impostaRicorsoMisPers(idProcedimento, idAtto, idUffOwner, checked, idMisuraPers, idSoggInt, codiceUtente, catAtto);
		}	
		

	}

	public ImpugnazioneForm getImpugnazioneByIdAttoUfficioOwner(long idAtto, int idUfficio) {

		TImpugnazione impugnazione = attoDao.getImpugnazioneByIdAttoUfficioOwner(idAtto, idUfficio);
		ImpugnazioneForm impugnazioneForm = new ImpugnazioneForm();
		mapperBean.map(impugnazione, impugnazioneForm);

		getTRelSoggImpByIdImpugnazioneIdOwner(idAtto, idUfficio, impugnazioneForm);
		
		/*if (impugnazioneForm.getParte().getId() != null && impugnazioneForm.getParte().getId().equals("D")) {
			if(impugnazione.getTSoggetto() != null && impugnazione.getTSoggetto().getId() != null){
			 ComboForm combo = new ComboForm("" + impugnazione.getTSoggetto().getId().getCodiKIdSoggInt(), "");
			 impugnazioneForm.setDirettiInteressati(combo);
			}
		}
		if (impugnazioneForm.getParte().getId() != null && impugnazioneForm.getParte().getId().equals("T")) {
			if(impugnazione.getTSoggetto() != null && impugnazione.getTSoggetto().getId() != null){
			 ComboForm combo = new ComboForm("" + impugnazione.getTSoggetto().getId().getCodiKIdSoggInt(), "");
			 impugnazioneForm.setSoggettiTitolari(combo);
			}
		}
		if (impugnazioneForm.getParte().getId() != null && impugnazioneForm.getParte().getId().equals("A")) {
			if(impugnazione.getTSoggetto() != null && impugnazione.getTSoggetto().getId() != null){
		
			 ComboForm combo = new ComboForm("" + impugnazione.getTSoggetto().getId().getCodiKIdSoggInt(), "");
			 impugnazioneForm.setSoggettiDifensori(combo);
			}
		}*/
		
		return impugnazioneForm;

	}

	/**
	 * ricercaAttiRicorso
	 * 
	 * @param idProcedimento
	 * @param idUffOwner
	 * @return List<AttoForm>
	 */
	public List<AttoForm> ricercaAttiRicorso(long idProcedimento, long idUffOwner) {

		List<AttoForm> results = new ArrayList<AttoForm>();

		List daoResults = attoDao.ricercaAttiRicorso(idProcedimento, idUffOwner);
		Iterator iter = daoResults.iterator();

		while (iter.hasNext()) {
			AttoForm currentForm = new AttoForm();
			ImpugnazioneForm currentImpForm = new ImpugnazioneForm();
			Map map = (Map) iter.next();
			TAtto currentAtto = (TAtto) map.get("atto");
			TTpCatAtto currentCategoria = (TTpCatAtto) map.get("cat_atto");
			TTpStatoAtto statoAtto = (TTpStatoAtto) map.get("stato_atto");
			TImpugnazione impugnazione = (TImpugnazione) map.get("impugnazione");

			ComboForm statoAttoForm = new ComboForm();
			ComboForm catAttoForm = new ComboForm();

			mapperBean.map(currentAtto, currentForm);
			mapperBean.map(currentCategoria, catAttoForm);
			mapperBean.map(statoAtto, statoAttoForm);
			mapperBean.map(impugnazione, currentImpForm);

			currentForm.setStatoAtto(statoAttoForm);
			currentForm.setCategoriaAtto(catAttoForm);
			currentForm.setImpugnazione(currentImpForm);

			// al momento inserisco n.d.
			currentForm.setNumeroAtto(Constants.FormattazioneAnnoNumeroAtto.NUMERO_ATTO_NON_DISP);

			if (impugnazione.getDataDepImp() != null) {
				currentForm.setDataDeposito(impugnazione.getDataDepImp());
			}

			if (impugnazione.getNumeAnnoAtto() != null) {
				currentForm.setNumeAnnoAtto(impugnazione.getNumeAnnoAtto().toString());
			}
			if (impugnazione.getNumeAtto() != null) {
				currentForm.setNumeAtto(impugnazione.getNumeAtto().toString());
			}

			results.add(currentForm);
		}
		return results;
	}

	public void inviaRicorsoInAppello(long iduffown, long idproc, long iddestinatario,Date datatrasmCda, String descutente) {

		attoDao.inviaRicorsoInAppello(iduffown, idproc, iddestinatario,datatrasmCda, descutente);
	}

	public boolean isRicorsoInLavorazione(long idProcedimento, long idUffOwner) {

		List daoResults = attoDao.ricercaRicorsoInLavorazione(idProcedimento, idUffOwner);

		if (daoResults.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

	public KeyMessageDto controllaMisureDefinitivitaIGrado(long idProc, long idAtto, long idUfficio, String descUtente, String funzione, String metodo) {

		return attoDao.controllaMisureDefinitivitaIGrado(idProc, idAtto, idUfficio, descUtente, funzione, metodo);
	}
	
	public KeyMessageDto controllaImpugnazioneCauArt67(long idProc, long idAtto, long idUfficio, String descUtente, String metodo) {

		return attoDao.controllaImpugnazioneCauArt67(idProc, idAtto, idUfficio, descUtente, metodo);
	}	
	
	public KeyMessageDto controllaDefinitivitaIIGradoSuMisure(long idProc, long idAtto, long idUfficio, String descUtente, String funzione, String metodo) {

		return attoDao.controllaDefinitivitaIIGradoSuMisure(idProc, idAtto, idUfficio, descUtente, funzione, metodo);
	}	

	public KeyMessageDto controllaMisureDefinitivitaIIGrado(long idProc, long idAtto, long idUfficio, String descUtente, String funzione,
			String metodo) {

		return attoDao.controllaMisureDefinitivitaIIGrado(idProc, idAtto, idUfficio, descUtente, funzione, metodo);
	}

	public long inserisciRichiestaParere(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto,
			String nomeMetodo) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TAttoGenerico attoGenerico = new TAttoGenerico();
		mapperBean.map(attoForm, attoGenerico);

		Utility.popolaDocumento(attoForm, atto);

		return attoDao.inserisciParere(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, 0, attoGenerico, nomeMetodo);
	}

	public long inserisciParere(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, long idAttoOrigine,
			String nomeMetodo) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TAttoGenerico attoGenerico = new TAttoGenerico();
		mapperBean.map(attoForm, attoGenerico);

		Utility.popolaDocumento(attoForm, atto);

		return attoDao.inserisciParere(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, idAttoOrigine, attoGenerico, nomeMetodo);
	}

	public Long aggiornaInviaParere(boolean isRichiesta, long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,
			long categoriaAtto, long idStatoAtto, long idDestinatario, String nomeMetodo) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		return attoDao.aggiornaInviaParere(isRichiesta, idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto, idStatoAtto,
				idDestinatario, nomeMetodo);

	}

	public KeyMessageDto controllaRegImpugnazione(long idProc, long idAtto, long idUfficio, String descUtente, String metodo) {

		return attoDao.controllaRegImpugnazione(idProc, idAtto, idUfficio, descUtente, metodo);
	}

	public KeyMessageDto controllaInsertDefinitivita(long idProc, long idMisPers, long idBene, long idInteresse, long idSoggetto, Integer idUfficio,
			String descUtente, String funzione, String metodo) {

		return attoDao.controllaInsertDefinitivita(idProc, idMisPers, idBene, idInteresse, idSoggetto, idUfficio, descUtente, funzione, metodo);
	}

	public KeyMessageDto controllaUpdEsitoMisPers(long idProc, long idAtto, long idSoggetto,long idEsito, String cauzione, String art67, Integer idUfficio,
			String descUtente, String funzione, String metodo) {

		return attoDao.controllaUpdEsitoMisPers(idProc, idAtto,idSoggetto, idEsito, cauzione, art67, idUfficio, descUtente, funzione, metodo);
	}
	


	public long inserisciDecretoIncompetenza(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto,
			String nomeMetodo) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		return attoDao.inserisciDecretoIncompetenza(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, nomeMetodo);
	}

	public long inserisciDecretoOrdinanzaRestituzioneBeni(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm,
			long idStatoAtto, String nomeMetodo) {

		TAtto atto = popolaAttoEDocumento(attoForm);

		return attoDao.inserisciDecretoOrdinanzaRestituzioneBeni(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, nomeMetodo);
	}

	public void revocaDefinitivitaAttoRegistratoSalva(List<BeneForm> misurePatrimoniali, List<SoggettoForm> misurePersonali, long idProcedimento,
			Integer idUfficioUtente, long idAtto, String codUtente, String tipo, String tipoFunzione, String nomeMetodo) {

		if (misurePatrimoniali != null && !misurePatrimoniali.isEmpty()) {
			for (BeneForm bene : misurePatrimoniali) {
				if (bene.getListaIdCheckboxString() != null) {
					for (String idSoggettiTitolareETitolo : bene.getListaIdCheckboxString()) {
						long idSoggetto = 0;
						long idInteresse = 0;
						long idEsito = 0;
						long esito = 0;
						long tipoMis = 0;
						StringTokenizer st = new StringTokenizer(idSoggettiTitolareETitolo, ";");
						int numToken = st.countTokens();
						while (st.hasMoreTokens()) {
							idSoggetto = Long.valueOf(st.nextToken()).longValue();
							idInteresse = Long.valueOf(st.nextToken()).longValue();
							esito = Long.valueOf(st.nextToken()).longValue();
							if (numToken == 4)
								tipoMis = Long.valueOf(st.nextToken()).longValue();
						}
						attoDao.revocaDefinitivitaMisPatrAttoRegistratoSalva(bene.getIdBene(), idSoggetto, idInteresse, idProcedimento,
								idUfficioUtente, idAtto, codUtente, tipoFunzione, nomeMetodo);
					}
				}
			}
		}
		if (misurePersonali != null && !misurePersonali.isEmpty()) {
			for (SoggettoForm sogg : misurePersonali) {
				if (sogg.getMisuraInfo() != null) {
					for (String misuraInfo : sogg.getMisuraInfo()) {
						long idMisura = 0;
						long tipoMisura = 0;
						StringTokenizer st = new StringTokenizer(misuraInfo, ";");
						while (st.hasMoreTokens()) {
							idMisura = Long.valueOf(st.nextToken()).longValue();
							tipoMisura = Long.valueOf(st.nextToken()).longValue();
						}
						attoDao.revocaDefinitivitaMisPersAttoRegistratoSalva(idProcedimento, idUfficioUtente, idAtto, idMisura, codUtente,
								tipoFunzione, nomeMetodo);
					}
				}
			}
		}

	}
	
	public void revocaDefinitivitaFascGestAttoRegistratoSalva(List<BeneForm> misurePatrimoniali, long idProcedimento,
			Integer idUfficioUtente, long idAtto, String codUtente, String tipo, String tipoFunzione, String nomeMetodo) {

		if (misurePatrimoniali != null && !misurePatrimoniali.isEmpty()) {
			for (BeneForm bene : misurePatrimoniali) {
				if (bene.getListaIdCheckboxString() != null) {
					for (String idSoggettiTitolareETitolo : bene.getListaIdCheckboxString()) {
						long idSoggetto = 0;
						long idInteresse = 0;
						long idEsito = 0;
						long esito = 0;
						long tipoMis = 0;
						StringTokenizer st = new StringTokenizer(idSoggettiTitolareETitolo, ";");
						int numToken = st.countTokens();
						while (st.hasMoreTokens()) {
							idSoggetto = Long.valueOf(st.nextToken()).longValue();
							idInteresse = Long.valueOf(st.nextToken()).longValue();
							esito = Long.valueOf(st.nextToken()).longValue();
							if (numToken == 4)
								tipoMis = Long.valueOf(st.nextToken()).longValue();
						}
						//quella misura potrebbe non essere nel fascicolo di gestione ed andare in errore
						//quindi viene gestita l'eccezione
						try {
							attoDao.revocaDefinitivitaMisPatrAttoRegistratoSalva(bene.getIdBene(), idSoggetto, idInteresse, idProcedimento,
									idUfficioUtente, idAtto, codUtente, tipoFunzione, nomeMetodo);
						} catch(Exception e){}
					}
				}
			}
		}
		

	}	
	
	public boolean getSogNoDecErroreMat(long idProcedimento, long idAtto, long idUffOwn, String descAtto) {
		List<TRelAttoIstGen>  lista = attoDao.getSogNoDecErroreMat(idProcedimento, idAtto, idUffOwn,descAtto);
		
		boolean ritorno = true;
		if(lista !=null && lista.size() >0)
			ritorno =false;
		
		return ritorno;
	}	
	
	
	
	public void revocaDefinitivitaInvio(long idProcedimento, Integer idUfficioUtente, long idAtto, String codUtente, String tipoFunzione,
			String nomeMetodo) {

		attoDao.revocaDefinitivitaInvio(idProcedimento, idUfficioUtente, idAtto, codUtente, tipoFunzione, nomeMetodo);
	}

	public KeyMessageDto controllaRegistrazioneDecisorio(long idProc, long idAtto, long idUfficio, String descUtente, String funzione, String metodo) {

		return attoDao.controllaRegistrazioneDecisorio(idProc, idAtto, idUfficio, descUtente, funzione, metodo);
	}

	/*
	 * Estrae la lista delle misure sulle quali è possibile inviare una scheda al casellario per il procedimento passato (non-Javadoc)
	 * il tipo assume RS per Redazione e FC per foglio complementare
	 * @see it.giustizia.smp.service.AttoService#getListaMisurePerRedazioneScheda(long, int)
	 */
	public List<RedazioneSchedaDto> getListaMisurePerRedazioneScheda(long idProcedimento, int idUffOwn, String tipo) {
		List<RedazioneSchedaDto> l = attoDao.getListaMisurePerRedazioneScheda(idProcedimento, idUffOwn, tipo);
		if ((l != null) && (l.size() > 0)) {
			// Verifico se successivamente ad un eventuale cancellazione è presente una scheda per la stessa misura
			int currentIndex = 0;
			for (RedazioneSchedaDto redazioneSchedaDto : l) {
				// 'Assente','Presente','Inviata','Annullata'
				redazioneSchedaDto.setShowOption(false); // imposto di default a false
				if (redazioneSchedaDto.getSTATO().equalsIgnoreCase("assente")) {
					redazioneSchedaDto.setShowOption(true); // se non vi sono schede imposto a true
				} else if (redazioneSchedaDto.getSTATO().equalsIgnoreCase("cancellata")
						|| redazioneSchedaDto.getSTATO().equalsIgnoreCase("annullata")) {
					// se è cancellata verifico se la successiva la non esiste
					if (l.size() <= currentIndex + 1)
						redazioneSchedaDto.setShowOption(true);
					if (l.size() > currentIndex + 1) {
						if (l.get(currentIndex + 1).getSTATO().equalsIgnoreCase("Presente") &&
						// se è cancellata e la successiva è presente ed è per una misura diversa
								redazioneSchedaDto.getCODI_K_ID_MISURA().longValue() != l.get(currentIndex + 1).getCODI_K_ID_MISURA().longValue()) {
							redazioneSchedaDto.setShowOption(true);
						}
					}
				}
				currentIndex++;
			}
		}
		return l;

	}

	public List<SoggettoForm> listaTerziNoTitDir(long idUffOwn, long idProcedimento, long idAtto) {

		List<SoggettoForm> lista = new ArrayList<SoggettoForm>();
		TProcedimento id = new TProcedimento();
		id.setCodiKIdProc(idProcedimento);
		List<SogIntDettaglioProcedimentoDto> soggettiProcedimento = attoDao.listaTerziNoTitDir(idUffOwn, idProcedimento, idAtto);
		Iterator<SogIntDettaglioProcedimentoDto> iter = soggettiProcedimento.iterator();
		while (iter.hasNext()) {
			SoggettoForm soggettoForm = new SoggettoForm();
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, soggettoForm);
			// Art67 e Cauzione sono significativi solo per le misure PERSONALI
			if (soggettoForm.getMisura().getDescrizioneMisura() == null || soggettoForm.getMisura().getDescrizioneMisura().contains("PATRIMONIALE")) {
				soggettoForm.getMisura().setArt67(null);
				soggettoForm.getMisura().setCauzione(null);
			}
			lista.add(soggettoForm);
		}
		return lista;
	}

	/***
	 * Esegue l'inserimento dell'atto relativo alla redazione scheda
	 * 
	 * @param idUffOwn
	 * @param idTipoProc
	 * @param dataRedazioneScheda
	 * @param descUtente
	 * @param descSedeOrgano
	 * @param descOrgano
	 * @param idAttoMisura
	 * @param idMisuraPersonale
	 * @param idProcedimento
	 * @return
	 */
	public Long inserisciRedazioneScheda(final long idUffOwn, final long idTipoProc, final Date dataRedazioneScheda, final String descUtente,
			final String descSedeOrgano, final String descOrgano, final long idAttoMisura, 
			final Long idMisuraPersonale, final long idProcedimento) {
		return attoDao.inserisciRedazioneScheda(idUffOwn, idTipoProc, dataRedazioneScheda, descUtente, descSedeOrgano, descOrgano, idAttoMisura,
				idMisuraPersonale, idProcedimento);

	}
	
	
	
	/***
	 * Esegue l'inserimento dell'atto relativo alla redazione scheda
	 * 
	 * @param idUffOwn
	 * @param idTipoProc
	 * @param dataRedazioneScheda
	 * @param descUtente
	 * @param descSedeOrgano
	 * @param descOrgano
	 * @param idAttoMisura
	 * @param idMisuraPersonale
	 * @param idProcedimento
	 * @return
	 */
	public Long inserisciRedazioneSchedaFC(final long idUffOwn, final long idTipoProc, final Date dataRedazioneScheda, final String descUtente,
			final String descSedeOrgano, final String descOrgano, final long idAttoMisura, final Long idMisuraPersonale, 
			final long idProcedimento, boolean fcem, final Long idSogInt) {
		return attoDao.inserisciRedazioneSchedaFC(idUffOwn, idTipoProc, dataRedazioneScheda, descUtente, descSedeOrgano, descOrgano, idAttoMisura,
				idMisuraPersonale, idProcedimento,fcem,idSogInt);

	}


	public String cancellaCasellario(final long idUffOwn, final long idComunicazione, final String descUtente) {
		return attoDao.cancellaCasellario(idUffOwn, idComunicazione, descUtente);

	}

	/***
	 * Legge le informazione di una comunicazione al casellario a partiere dall'idAtto
	 * 
	 * @param idComuniczione
	 * @return
	 */
	public ComunicazioneForm getComunicazioneByIdAtto(final long idProcedimento, final long idAtto, final long idUffOwn) {

		List<TRelAttoComunicaz> l = attoDao.getTRelAttoComunicaz(idAtto);
		for (TRelAttoComunicaz tRelAttoComunicaz : l) {
			TComunicazione tComunicazione = tRelAttoComunicaz.getTComunicazione();
			ComunicazioneForm comunicazioneForm = new ComunicazioneForm();
			comunicazioneForm.setIdAtto(idAtto);
			comunicazioneForm.setIdComunicazione(tComunicazione.getCodiKIdComunicaz());
			comunicazioneForm.setIdAttoEsecutivo(tComunicazione.getCodiFkIdAttoEs());
			comunicazioneForm.setDataInvio(tComunicazione.getDataInvio());
			if (tComunicazione.getFileModuloElettorale()==null) {
				comunicazioneForm.setHasModuloElettorale(false);
			}else {
				comunicazioneForm.setHasModuloElettorale(true);
			}
			comunicazioneForm.setIdMisuraPersonale(tComunicazione.getCodiFkIdMisuraPers());
			
			comunicazioneForm.setDataRedazioneScheda(tRelAttoComunicaz.getTAtto().getDataEmissione());
			comunicazioneForm.setFlagStatoInvioComunicazione((tComunicazione.getFlgStatoInvio() == null ? null : tComunicazione.getFlgStatoInvio()
					.toString()));
			String descSoggetto = "";
			if (tComunicazione.getCodiFkIdMisuraPers()!=null) {
				TMisuraPers misura = misuraDao.getTMisuraPerById(tComunicazione.getCodiFkIdMisuraPers(), (int) idUffOwn);
				comunicazioneForm.setDataDefinitivita(misuraDao.getDataDefinitivitaDecisorioIGradoRiesame(misura.getId().getCodiKIdMisura(),
						idProcedimento, comunicazioneForm.getIdAttoEsecutivo(), (int) idUffOwn));
				TSoggetto soggetto = misura.getTSoggetto();
				comunicazioneForm.setIdSoggetto(soggetto.getId().getCodiKIdSoggInt());
				TPersonaFisica pf = null;
				short lastVer = 0;
				for (TPersonaFisica tPersonaFisica : soggetto.getTPersonaFisicas()) {
					if (lastVer< tPersonaFisica.getId().getCodiKIdVer()) {
						lastVer = tPersonaFisica.getId().getCodiKIdVer();
						pf = tPersonaFisica;
					}
				}
				//descSoggetto ="(" + soggetto.getTPersonaFisicas().iterator().next().getDescCognome() +  " "  +  soggetto.getTPersonaFisicas().iterator().next().getDescNome() + ")";
				if (pf!=null) {
					descSoggetto ="(" + pf.getDescCognome() +  " "  +  pf.getDescNome() + ")";
				}
			} else {
				List<IntestatarioDto> listaIntestatari = procedimentoServiceCam.getIntestatari((int)idUffOwn, idProcedimento);
			    descSoggetto = "";
				if (listaIntestatari.size()>0) {
					descSoggetto ="(" + listaIntestatari.get(0).getDESC_COGNOME() +  " "  +  listaIntestatari.get(0).getDESC_NOME()+ ")"; 
					comunicazioneForm.setIdSoggetto(listaIntestatari.get(0).getCODI_FK_ID_SOGG_INT().longValue());
				}
		
				
			}
			comunicazioneForm.setDescSoggetto(descSoggetto);
			if (comunicazioneForm.getFlagStatoInvioComunicazione()!=null) {
				comunicazioneForm.setDescInvioComunicazione(MappaStatiComunicazioneCasellario.getDescrizioneStatoScheda(comunicazioneForm.getFlagStatoInvioComunicazione()));
			} else {
				comunicazioneForm.setDescInvioComunicazione("");
			}
			return comunicazioneForm;
		}
		return null;
	}

	/**
	 * metodo service per il versionamento del soggetto
	 * 
	 * @param idUffOwn
	 * @param idProcedimento
	 * @param descUtente
	 * @param attoForm
	 * @param idMagistrato
	 * @param soggettoForm
	 * @return id atto
	 */
	public Long inserisciAttoVerSoggetto(long idUffOwn, long idProcedimento, String descUtente, AttoForm attoForm, long idMagistrato,
			SoggettoForm soggettoForm) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TPersonaFisica personaFisica = new TPersonaFisica();
		mapperBean.map(soggettoForm, personaFisica);

		TPersonaGiur personaGiuridica = new TPersonaGiur();
		mapperBean.map(soggettoForm, personaGiuridica);

		Utility.popolaDocumento(attoForm, atto);

		return attoDao.inserisciAttoVerSoggetto(idUffOwn, idProcedimento, descUtente, atto, idMagistrato, personaFisica, personaGiuridica,
				soggettoForm.getTipoSoggetto());
	}

	public TComunicazione getTComunicazioneById(long idComuniczione) {
		return attoDao.getTComunicazioneById(idComuniczione);
	}

	/**
	 * getTRelProcSogintVerByAtto
	 * 
	 * @param idAtto
	 * @param idUffOwn
	 * @return SoggettoForm
	 */
	public SoggettoForm getTRelProcSogintVerByAtto(long idAtto, int idUffOwn) {

		TRelProcSogintVer sogintVer = new TRelProcSogintVer();
		sogintVer = attoDao.getTRelProcSogintVerByAtto(idAtto, idUffOwn);
	
		SoggettoForm sf = new SoggettoForm();
		mapperBean.map(sogintVer, sf);

		return sf;
	}
	
	 public KeyMessageDto controllaEsitoFinale(int idUffOwn, long idProc, long idAtto, int catAtto){
	    	
	    	return attoDao.controllaEsitoFinale(idUffOwn, idProc, idAtto, catAtto);
	}

	/***
	 * Restituisce la lista delle impugnazioni presenti nel grado precedente per un decisorio/misura 
	 * @param grado, assume il valore 1 per prelevare le impugnazioni di primo grado e 2 per le impugnazioni di 2 grado
	 * @param idUffOwn, ufficio corrente
	 * @param idProc
	 * @param idAttoDecisorioAttuale
	 * @param idMisuraPersAttuale
	 * @return
	 */
	public List<ImpugnazioneCassellarioDTO> getListaImpugnazioniGradoPrecedente(int idUffOwn, long idProc, long idAttoDecisorioAttuale, long idMisuraPersAttuale) {
		List<ImpugnazioneCassellarioDTO> list = attoDao.listaImpugnazioniGradoPrec(idProc, idUffOwn, idMisuraPersAttuale, idAttoDecisorioAttuale);
		return list;
	}

	public CertificatoPenaleForm getCertificatoPenaleByIdAtto(long idProcedimento, long idAtto, long idUffOwn) {
		TCertificatoPenale tCertificatoPenale= attoDao.getTCertificatoPenale(idProcedimento,idAtto,idUffOwn);
		CertificatoPenaleForm certificatoPenaleForm = new CertificatoPenaleForm();
		certificatoPenaleForm.setIdAtto(idAtto);
		certificatoPenaleForm.setIdCertificatoPenale(tCertificatoPenale.getCodiKIdCertificato());
		certificatoPenaleForm.setIdSoggetto(tCertificatoPenale.getTRelProcSogint().getId().getCodiKIdSoggInt());
		TRelProcSogint tRelProcSogint = procedimentoDao.getTrelProcSogintByIdsoggIdProcIdUff(idProcedimento, tCertificatoPenale.getTRelProcSogint().getId().getCodiKIdSoggInt(), (int)idUffOwn, 0l);
		short idVersioneSoggetto = tRelProcSogint.getTRelProcSogintVer().getId().getCodiKIdVer();
		certificatoPenaleForm.setIdVersioneSoggetto(idVersioneSoggetto);
		TPersonaFisica persona = tRelProcSogint.getTRelProcSogintVer().getTSoggetto().getTPersonaFisicas().iterator().next();
		certificatoPenaleForm.setDescSoggetto(persona.getDescCognome() + " " + persona.getDescNome());
		return certificatoPenaleForm;
	}

	public TCertificatoPenale getTCertificatoPenaleByIdAtto(Long idProcedimento, long idAtto, int idUffOwn) {
		return  attoDao.getTCertificatoPenale(idProcedimento,idAtto,idUffOwn);
	}	
	
	/**
	 * getAttiProrogaSequestro
	 * 
	 * @param idProcedimento
	 * @return List<AttoForm>
	 */
	public List<AttoForm> getAttiProrogaSequestro(long idProcedimento, long idUffOwn) {
		List<AttoForm> results = new ArrayList<AttoForm>();
		List daoResults = attoDao.getAttiProrogaSequestro(idProcedimento, idUffOwn);
		Iterator iter = daoResults.iterator();
		while (iter.hasNext()) {
			AttoForm currentForm = new AttoForm();
			Map map = (Map) iter.next();
			TAtto currentFonte = (TAtto) map.get("atto");
			TTpCatAtto currentCategoriaFonte = (TTpCatAtto) map.get("cat_atto");
			TTpStatoAtto statoAtto = (TTpStatoAtto) map.get("stato_atto");
			ComboForm statoAttoForm = new ComboForm();
			ComboForm fonte = new ComboForm();
			mapperBean.map(currentFonte, currentForm);
			mapperBean.map(currentCategoriaFonte, fonte);
			mapperBean.map(statoAtto, statoAttoForm);
			currentForm.setStatoAtto(statoAttoForm);
			currentForm.setDataInformativa(currentForm.getDataEmissione());
			currentForm.setFonte(fonte);
			results.add(currentForm);
		}
		return results;
	}	

	/**
	 * getAttiProrogaSequestro
	 * 
	 * @param idProcedimento
	 * @return List<AttoForm>
	 */
	public List<AttoForm> getAttiProrogaSequestroCdA(long idProcedimento, long idUffOwn) {
		List<AttoForm> results = new ArrayList<AttoForm>();
		List daoResults = attoDao.getAttiProrogaSequestroCdA(idProcedimento, idUffOwn);
		Iterator iter = daoResults.iterator();
		while (iter.hasNext()) {
			AttoForm currentForm = new AttoForm();
			Map map = (Map) iter.next();
			TAtto currentFonte = (TAtto) map.get("atto");
			TTpCatAtto currentCategoriaFonte = (TTpCatAtto) map.get("cat_atto");
			TTpStatoAtto statoAtto = (TTpStatoAtto) map.get("stato_atto");
			ComboForm statoAttoForm = new ComboForm();
			ComboForm fonte = new ComboForm();
			mapperBean.map(currentFonte, currentForm);
			mapperBean.map(currentCategoriaFonte, fonte);
			mapperBean.map(statoAtto, statoAttoForm);
			currentForm.setStatoAtto(statoAttoForm);
			currentForm.setDataInformativa(currentForm.getDataEmissione());
			currentForm.setFonte(fonte);
			results.add(currentForm);
		}
		return results;
	}	

	
	public long inserisciAttoDecretoProrogaSequestro(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm,
			long idStatoAtto, long idAttoOrigine, String nomeMetodo) {

		long idAtto;
		
		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TDecreto decreto = new TDecreto();
		mapperBean.map(attoForm, decreto);

		Utility.popolaDocumento(attoForm, atto);

		idAtto =  attoDao.inserisciAttoDecretoProrogaSequestro(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, idAttoOrigine, decreto,
				nomeMetodo);
		
		
		List<BeneSoggettoForm> beniAttoOrigine = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAttoOrigine,
				idUffOwn);

		for (int i = 0; i < beniAttoOrigine.size(); i++) {
			BeneSoggettoForm bene = beniAttoOrigine.get(i);
			long idBene = (Long) bene.getBene().getIdBene();

			copiaMisBeneAtto(idProcedimento, idAtto, idBene, idUffOwn, codiceUtente, Constants.TipiFlagDecretoRevoca.DECRETO, nomeMetodo);
		}
		
		return idAtto;		
	}	
	
	public Long inviaAttoDecretoProrogaSequestro(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, String nomeMetodo) {
		
		TAtto atto = popolaAttoEDocumento(attoForm);

		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return attoDao.inviaAttoDecretoProrogaSequestro(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto, idStatoAtto, nomeMetodo);
	}

	public void aggiornaDatiAtto(long codiceAtto, int idUffOwner, String note){
		attoDao.aggiornaDatiAtto(codiceAtto, idUffOwner, Utility.limitaStringa(note, Constants.TATTO_CAMPO_NOTE_LEN));
	}

	public void aggiornaDatiAtto(AttoForm attoForm){
		attoDao.aggiornaDatiAtto(popolaAttoEDocumento(attoForm));
	}
	
	public KeyMessageDto controllaAmministratoriSenzaBeni(int idUffOwn, long idProc, long idAtto){
    	
    	return attoDao.controllaAmministratoriSenzaBeni(idUffOwn, idProc, idAtto);
	}

	public boolean controllaAmministratoriSenzaBeniDecisorio(int idUffOwn, long idProc, long idAtto){
    	
    	List l = attoDao.controllaAmministratoriSenzaBeniDecisorio(idUffOwn, idProc, idAtto);
    	if(l!=null && l.size()>0) return true;
    	else return false;
	}

	// P.Q. MEV 2020/48 Inizio
	public boolean controllaAmministratoriSenzaBeniDecisorioSecondGrado(int idUffOwn, long idProc, long idAtto, long idFascicoloGestione){
    	
    	List l = attoDao.controllaAmministratoriSenzaBeniDecisorioSecondoGrado(idUffOwn, idProc, idAtto, idFascicoloGestione);
    	if(l!=null && l.size()>0) return true;
    	else return false;
	}
	// P.Q. MEV 2020/48 Fine	
	
	public boolean controllaBeniAmministratoriSenzaAmministratore(int idUffOwn, long idProc, long idAtto){
    	
    	List l = attoDao.controllaBeniAmministratoriSenzaAmministratore(idUffOwn, idProc, idAtto);
    	if(l!=null && l.size()>0) return true;
    	else return false;
	}	
	
	public KeyMessageDto controllaInserimentoDecretoSospensioneTemporanea(long idProcedimento, int idUffOwn) {
		return attoDao.controllaInserimentoDecretoSospensioneTemporanea(idProcedimento, idUffOwn);
	}
	
	public Long inserisciAttoDecretoSospensioneTemporanea(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm, long idStatoAtto, String nomeMetodo) {
		
		long idAtto;
		
		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TDecreto decreto = new TDecreto();
		mapperBean.map(attoForm, decreto);

		Utility.popolaDocumento(attoForm, atto);

		idAtto = attoDao.inserisciAttoDecretoSospensioneTemporanea(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, decreto,
				nomeMetodo);
		
		return idAtto;			
	}
	
	public Long inviaAttoDecretoSospensioneTemporanea(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, String nomeMetodo) {
		
		TAtto atto = popolaAttoEDocumento(attoForm);

		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return attoDao.inviaAttoDecretoSospensioneTemporanea(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto, idStatoAtto, nomeMetodo);
	}	
	
	public long inserisciAttoDecretoProrogaSospensioneTemporanea(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm,
			long idStatoAtto, long idAttoOrigine, String nomeMetodo) {

		long idAtto;
		
		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TDecreto decreto = new TDecreto();
		mapperBean.map(attoForm, decreto);

		Utility.popolaDocumento(attoForm, atto);

		idAtto =  attoDao.inserisciAttoDecretoProrogaSospensioneTemporanea(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, idAttoOrigine, decreto,
				nomeMetodo);
		
		
		List<BeneSoggettoForm> beniAttoOrigine = procedimentoService.listaBeniConSoggettiAssociatiAdAttoEProcedimento(idProcedimento, idAttoOrigine,
				idUffOwn);

		for (int i = 0; i < beniAttoOrigine.size(); i++) {
			BeneSoggettoForm bene = beniAttoOrigine.get(i);
			long idBene = (Long) bene.getBene().getIdBene();

			copiaMisBeneAtto(idProcedimento, idAtto, idBene, idUffOwn, codiceUtente, Constants.TipiFlagDecretoRevoca.DECRETO, nomeMetodo);
		}
		
		return idAtto;		
	}	
	
	public Long inviaAttoDecretoProrogaSospensioneTemporanea(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, String nomeMetodo) {
		
		TAtto atto = popolaAttoEDocumento(attoForm);

		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return attoDao.inviaAttoDecretoProrogaSospensioneTemporanea(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto, idStatoAtto, nomeMetodo);
	}

	public List<AttoForm> getAttiSospensioneTemporanea(long idProcedimento, long idUffOwn) {
		List<AttoForm> results = new ArrayList<AttoForm>();
		List daoResults = attoDao.getAttiSospensioneTemporanea(idProcedimento, idUffOwn);
		Iterator iter = daoResults.iterator();
		while (iter.hasNext()) {
			AttoForm currentForm = new AttoForm();
			Map map = (Map) iter.next();
			TAtto currentFonte = (TAtto) map.get("atto");
			TTpCatAtto currentCategoriaFonte = (TTpCatAtto) map.get("cat_atto");
			TTpStatoAtto statoAtto = (TTpStatoAtto) map.get("stato_atto");
			ComboForm statoAttoForm = new ComboForm();
			ComboForm fonte = new ComboForm();
			mapperBean.map(currentFonte, currentForm);
			mapperBean.map(currentCategoriaFonte, fonte);
			mapperBean.map(statoAtto, statoAttoForm);
			currentForm.setStatoAtto(statoAttoForm);
			currentForm.setDataInformativa(currentForm.getDataEmissione());
			currentForm.setFonte(fonte);
			results.add(currentForm);
		}
		return results;
	}	
	
	public ImpugnazioneCassellarioDTO getSentenzaCassazione(int idUffOwn, long idProc, long idAttoDecisorioAttuale, long idMisuraPersAttuale) {
		ImpugnazioneCassellarioDTO sentenzaCasellario = attoDao.getSentenzaCassazione(idUffOwn, idProc, idAttoDecisorioAttuale, idMisuraPersAttuale);
		return sentenzaCasellario;
	}	
	
	public long inserisciAttoDecretoSequestroSospensioneTemporanea(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm,
			long idStatoAtto, long idAttoOrigine, String nomeMetodo) {

		long idAtto;
		
		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TDecreto decreto = new TDecreto();
		mapperBean.map(attoForm, decreto);

		Utility.popolaDocumento(attoForm, atto);

		idAtto =  attoDao.inserisciAttoDecretoSequestroSospensioneTemporanea(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, idAttoOrigine, decreto,
				nomeMetodo);
		
		return idAtto;		
	}	
	
	public Long inviaAttoDecretoSequestroSospensioneTemporanea(long idUfficio, long idProcedimento, AttoForm attoForm, String codUtente, long tipoAtto,long categoriaAtto, long idStatoAtto, String nomeMetodo) {
		
		TAtto atto = popolaAttoEDocumento(attoForm);

		attoForm.setDataRegistrazione(Calendar.getInstance().getTime());
		return attoDao.inviaAttoDecretoSequestroSospensioneTemporanea(idUfficio, idProcedimento, atto, codUtente, tipoAtto, categoriaAtto, idStatoAtto, nomeMetodo);
	}
	
	public List<AttoForm> getAttiDecretoApplicazioneProvvisoria(long idProcedimento, long idUffOwn) {
		List<AttoForm> results = new ArrayList<AttoForm>();
		List daoResults = attoDao.getAttiDecretoApplicazioneProvvisoria(idProcedimento, idUffOwn);
		Iterator iter = daoResults.iterator();
		while (iter.hasNext()) {
			AttoForm currentForm = new AttoForm();
			Map map = (Map) iter.next();
			TAtto currentFonte = (TAtto) map.get("atto");
			TTpCatAtto currentCategoriaFonte = (TTpCatAtto) map.get("cat_atto");
			TTpStatoAtto statoAtto = (TTpStatoAtto) map.get("stato_atto");
			ComboForm statoAttoForm = new ComboForm();
			ComboForm fonte = new ComboForm();
			mapperBean.map(currentFonte, currentForm);
			mapperBean.map(currentCategoriaFonte, fonte);
			mapperBean.map(statoAtto, statoAttoForm);
			currentForm.setStatoAtto(statoAttoForm);
			currentForm.setDataInformativa(currentForm.getDataEmissione());
			currentForm.setFonte(fonte);
			results.add(currentForm);
		}
		return results;
	}	
	
	public long inserisciAttoDecretoRevocaApplicazioneProvvisoria(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm,
			long idStatoAtto, long idAttoOrigine, String nomeMetodo) {

		long idAtto;
		
		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TDecreto decreto = new TDecreto();
		mapperBean.map(attoForm, decreto);

		Utility.popolaDocumento(attoForm, atto);

		idAtto =  attoDao.inserisciAttoDecretoRevocaApplicazioneProvvisoria(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, idAttoOrigine, decreto,
				nomeMetodo);
		
		return idAtto;		
	}

	public long salvaIstanzaFascicoloGestione(int idUffOwn, long idProcedimento, String codiceUtente,
			long statoAtto, long tipoAtto, long categoriaAtto, AttoForm attoForm, String nomeMetodo) {
		long idAtto;
		
		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TIstanza istanza = new TIstanza();
		mapperBean.map(attoForm, istanza);

		Utility.popolaDocumento(attoForm, atto);

		idAtto =  attoDao.salvaIstanzaFascicoloGestione(idUffOwn, idProcedimento, null, codiceUtente, statoAtto, tipoAtto, categoriaAtto, atto, istanza, nomeMetodo);
		
		return idAtto;	
	}		

	public long salvaIstanzaRinunciaAmministratore(int idUffOwn, long idProcedimento, Long idAmministratore, String codiceUtente, long statoAtto, long tipoAtto, long categoriaAtto, AttoForm attoForm, String nomeMetodo) {
		long idAtto;
		
		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);
	
		TIstanza istanza = new TIstanza();
		mapperBean.map(attoForm, istanza);
	
		Utility.popolaDocumento(attoForm, atto);
	
		idAtto =  attoDao.salvaIstanzaFascicoloGestione(idUffOwn, idProcedimento, idAmministratore, codiceUtente, statoAtto, tipoAtto, categoriaAtto, atto, istanza, nomeMetodo);
		
		return idAtto;	
	}	


	
	public List<SoggettoStralcioForm> getRicercaMisureIstanza(long idProcedimento, long idUfficio, AttoForm attoForm, String flgValido, int categoriaAtto) {

		
		List<RicercaStralcioDto> queryResults = null;
		MisuraForm misuraForm = null;
		RicercaStralcioDto dto = null;
		BeneStralcioForm beneStralcioForm = null;
		SoggettoStralcioForm soggettoStralcioForm = null;
		SoggettoTitolareForm soggettoTitolareForm = null;
		List<BeneStralcioForm> listaBeneStralcioForm = null;

	
     
	   queryResults = attoDao.getRicercaMisureIstanza(attoForm.getCodAtto(), idProcedimento, idUfficio,flgValido, categoriaAtto, Integer.parseInt(attoForm.getStatoAtto().getId()));
       

		List<SoggettoStralcioForm> risultati = new ArrayList<SoggettoStralcioForm>();
		if (queryResults == null || queryResults.isEmpty()) {
			return risultati;
		}

		for (int i = 0; i < queryResults.size(); i++) {

			dto = (RicercaStralcioDto) queryResults.get(i);
			soggettoStralcioForm = new SoggettoStralcioForm();
			misuraForm = new MisuraForm();
			BigDecimal prevSoggInt = null;
			BigDecimal prevIdBene = null;
			BigDecimal prevIdSoggInt = null;
			boolean isFirst = true;
			mapperBean.map(dto, soggettoStralcioForm);

			listaBeneStralcioForm = new ArrayList<BeneStralcioForm>();
			soggettoStralcioForm.setListaBeneStralcioForm(listaBeneStralcioForm);

			prevSoggInt = dto.getCODI_K_ID_SOGG_INT();

			// inizio gestione NO_MIS_ID
			if (Constants.TipoMisura.NO_MIS_ID.equalsIgnoreCase(dto.getTIPO_MISURA())) {
				isFirst = false;
				mapperBean.map(dto, misuraForm);
				soggettoStralcioForm.setFlgValido(String.valueOf(dto.getFLG_VALIDO()));
				soggettoStralcioForm.setMisura(misuraForm);
				// continue;
			} else {

				// ciclo sui beni
				for (; i < queryResults.size(); i++) {
					if (!isFirst) {
						dto = (RicercaStralcioDto) queryResults.get(i);
					}

					if (prevSoggInt.equals(dto.getCODI_K_ID_SOGG_INT())) {

						// inizio gestione MIS_PERS
						if (Constants.TipoMisura.PERSONALE_ID.equalsIgnoreCase(dto.getTIPO_MISURA())) {
							isFirst = false;
							misuraForm = new MisuraForm();
							mapperBean.map(dto, misuraForm);

							// soggettoStralcioForm.setMisura(misuraForm);
							beneStralcioForm = new BeneStralcioForm();
							// mapping bene
							misuraForm.setCarattereMisura(dto.getCARATTERE_MISURA());
							mapperBean.map(dto, beneStralcioForm);
							beneStralcioForm.setMisura(misuraForm);
							listaBeneStralcioForm.add(beneStralcioForm);
							continue;
						} else {
							isFirst = true;
						}
						// fine gestione MIS_PERS

						beneStralcioForm = new BeneStralcioForm();
						mapperBean.map(dto, beneStralcioForm);
						listaBeneStralcioForm.add(beneStralcioForm);

						List<SoggettoTitolareForm> listSoggettoTitolareForm = new ArrayList<SoggettoTitolareForm>();
						beneStralcioForm.setListaSoggettoTitolareForm(listSoggettoTitolareForm);
						prevIdBene = dto.getCODI_K_ID_BENE();
						prevIdSoggInt = dto.getCODI_K_ID_SOGG_INT();

						// ciclo sui soggetti titolare
						for (; i < queryResults.size(); i++) {
							if (!isFirst) {
								dto = (RicercaStralcioDto) queryResults.get(i);
							}
							isFirst = false;
							if (prevIdBene.equals(dto.getCODI_K_ID_BENE()) && prevIdSoggInt.equals(dto.getCODI_K_ID_SOGG_INT())) {
								soggettoTitolareForm = new SoggettoTitolareForm();
								mapperBean.map(dto, soggettoTitolareForm);
								listSoggettoTitolareForm.add(soggettoTitolareForm);
							} else {
								isFirst = true;
								i--;
								break;
							}
						}
					} else {
						isFirst = true;
						i--;
						break;
					}
				}
			}
			
			
			TProcedimento procedimento = procedimentoService.getProcedimentoById(idProcedimento);
			if (15 == procedimento.getTTpTipoProc().getCodiKIdTipoProc()) {
				soggettoStralcioForm.setListaBeneStralcioForm(filtraListaBeneStralcioFormOnlyCauzione(listaBeneStralcioForm));
			}

			risultati.add(soggettoStralcioForm);
		}
		return risultati;
	}

	private List<BeneStralcioForm> filtraListaBeneStralcioFormOnlyCauzione(List<BeneStralcioForm> listaBeneStralcioForm) {
		List<BeneStralcioForm> res = new ArrayList<BeneStralcioForm>();
		for (BeneStralcioForm beneStralcioForm : listaBeneStralcioForm) {
			if ("25".equals(beneStralcioForm.getMisura().getTipoMisura().getId())) {
				res.add(beneStralcioForm);
			}
		}
		return res;
	}

	
	public List<SoggettoForm> getRicercaSoggettoIstanza(long idProcedimento, long idatto, int iduff, int descRuolo) {

		List<SoggettoForm> lista = new ArrayList<SoggettoForm>();

		List<SogIntDettaglioProcedimentoDto> soggettiProcedimento = attoDao.getRicercaSoggettoIstanza(idProcedimento, idatto, iduff, descRuolo);
		Iterator<SogIntDettaglioProcedimentoDto> iter = soggettiProcedimento.iterator();
		while (iter.hasNext()) {
			SoggettoForm soggettoForm = new SoggettoForm();
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, soggettoForm);
			// Art67 e Cauzione sono significativi solo per le misure PERSONALI
			if (soggettoForm.getMisura().getDescrizioneMisura() == null || soggettoForm.getMisura().getDescrizioneMisura().contains("PATRIMONIALE")) {
				soggettoForm.getMisura().setArt67(null);
				soggettoForm.getMisura().setCauzione(null);
			}
			lista.add(soggettoForm);
		}
		return lista;

	}	
	
	public List<DifensoreForm> getRicercaAvvIstanza(long idProcedimento,long idatto, int iduff) {

		List<DifensoreForm> results = new ArrayList<DifensoreForm>();
		List<TAvvocati> daoResults = attoDao.getRicercaAvvIstanza(idProcedimento, idatto, iduff);
		Iterator iter = daoResults.iterator();
		while (iter.hasNext()) {
			DifensoreForm currentForm = new DifensoreForm();
			Map map = (Map) iter.next();
			TAvvocati currentFonte = (TAvvocati) map.get("avvocati");
			mapperBean.map(currentFonte, currentForm);
			results.add(currentForm);
		}
		return results;
	}		
	public void impostaIstanzaMisPatr(long idProcedimento, long idAtto, long idUffOwner, boolean checked, long idSoggInt, long idBene,
			String idSoggTit,String codiceUtente, int catAtto,String lTipoRicorso) {
		
		
			attoDao.impostaIstanzaMisPatr(idProcedimento, idAtto, idUffOwner, checked, idSoggInt, idBene,
					idSoggTit,codiceUtente, catAtto);
		

	}

	public void impostaIstanzaSoggetto(long idProcedimento, long idAtto, long idUffOwner, boolean checked, long idSoggInt, String codiceUtente, int catAtto,String lTipoRicorso) {

		
		
			attoDao.impostaIstanzaSoggetto(idProcedimento, idAtto, idUffOwner, checked, idSoggInt,codiceUtente, catAtto);
				

	}

	public void impostaIstanzaMisPers(long idProcedimento, long idAtto, long idUffOwner, boolean checked, long idMisuraPers, long idSoggInt,
			final String codiceUtente, int catAtto,String lTipoRicorso) {

		
			attoDao.impostaIstanzaMisPers(idProcedimento, idAtto, idUffOwner, checked, idMisuraPers, idSoggInt, codiceUtente, catAtto);
			

	}
	

	/**
	 * getTRelSoggImpByIdImpugnazioneIdOwner
	 * @param idImpugnazione
	 * @param IdUffOwner
	 * @param impForm
	 * @return ImpugnazioneForm
	 */
	public ImpugnazioneForm getTRelSoggImpByIdImpugnazioneIdOwner(long idImpugnazione,int IdUffOwner,ImpugnazioneForm impForm) {

		
		TRelSoggImp soggIm = attoDao.getTRelSoggImpByIdImpugnazioneIdOwner(idImpugnazione, IdUffOwner);
		ComboForm comboParte = new ComboForm();
		ComboForm comboDifensore = new ComboForm();
		
		if(soggIm != null){
		
			if(soggIm.getFlgTipoParte() != null)
				comboParte.setId(soggIm.getFlgTipoParte().toString());
			
			if(soggIm.getTAvvocati() != null && soggIm.getTAvvocati().getCodiKIdAvvocato() != 0)
				comboDifensore.setId(String.valueOf(soggIm.getTAvvocati().getCodiKIdAvvocato()));
			
			impForm.setDescTipoProp(soggIm.getDescTipoProp());
			impForm.setDescCognAvv(soggIm.getDescCognAvv());
			impForm.setDescNomeAvv(soggIm.getDescNomeAvv());
			impForm.setAvvocatiDifensori(comboDifensore);
			impForm.setParte(comboParte);

			if(soggIm.getTSoggetto()!=null && soggIm.getTSoggetto().getId()!=null && soggIm.getFlgTipoParte() != null && soggIm.getFlgTipoParte().equals('N')) {
				long idSoggInv = soggIm.getTSoggetto().getId().getCodiKIdSoggInt();
				impForm.setIdSoggInv(idSoggInv);
			}

		}
        
		return impForm;
	}	
	public List<SoggettoForm> getSoggettiDifensoriRicorso(long idProc, long idImpugnazione, long idUfficio,  long idAvvocato,  String cognomeAvv, String nomeAvv, String descUtente) {

		List<SogIntDettaglioProcedimentoDto> soggetti = attoDao.getSoggettiDifensoriRicorso(idProc, idImpugnazione, idUfficio, idAvvocato, cognomeAvv, nomeAvv, descUtente);
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
	
	public List<SoggettoForm> getSoggettiDifensoriIstanza( long idImpugnazione, long idUfficio,  long idAvvocato,  String cognomeAvv, String nomeAvv, String descUtente) {

		List<SogIntDettaglioProcedimentoDto> soggetti = attoDao.getSoggettiDifensoriIstanza(idImpugnazione, idUfficio, idAvvocato, cognomeAvv, nomeAvv, descUtente);
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
	
	public long inserisciProvvedimentoFascicoloGestione(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm,
			long idStatoAtto, long idAttoOrigine, String nomeMetodo) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TDecreto decreto = new TDecreto();
		mapperBean.map(attoForm, decreto);

		Utility.popolaDocumento(attoForm, atto);

		return attoDao.inserisciProvvedimentoFascicoloGestione(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, idAttoOrigine, decreto,
				nomeMetodo);
	}	
	

	public NotificaAttoForm getAttoNoficatoDaRicevuta(int idUffOwn, long idAtto, long idProcedimento, String codiceUtente,Integer idCatAtto) {

		NotificaAttoForm notificaAttoForm = new NotificaAttoForm();
		TNotifRicevute notificaRicevuta =  attoDao.caricaNotificatoDaRicevuta(idAtto,idUffOwn);
		AttoForm attoNotificato = null;
		if (notificaRicevuta != null){
			// Lettura da Ricevuta se esiste
			attoNotificato = dettaglioAtto(
					notificaRicevuta.getTNotifDest().getTNotifRichieste().getTNotifiche().getCodiFkIdUffOwner(),
					notificaRicevuta.getTNotifDest().getTNotifRichieste().getTNotifiche().getCodiFkIdAtto(),
					idProcedimento,
					codiceUtente);
				//	NotificaAttoForm notificaAttoForm = new NotificaAttoForm();
				mapperBean.map(attoNotificato, notificaAttoForm);
				DestinatarioDto destinatarioDTO = new DestinatarioDto();
				String pec = "";
				if (notificaRicevuta.getTNotifDest().getDescPecDest()!=null) {
					pec = " (" + notificaRicevuta.getTNotifDest().getDescPecDest() + ")";
				}
				destinatarioDTO.setDESC_DEST(notificaRicevuta.getTNotifDest().getDescDestinataro() + pec);
				notificaAttoForm.getListaDestinatari().add(destinatarioDTO);
				
				if (attoNotificato.getAllegato().getFileName() != null) {
					attoNotificato.getAllegato().setFileName(attoNotificato.getAllegato().getFileName());
					attoNotificato.getAllegato().setContentType(attoNotificato.getAllegato().getContentType());
				}
			
		} else {
			if(idCatAtto!=null && idCatAtto.intValue()==Constants.CAT_ATTO_RAPP_TRASMISSIONE) {
				// carico il dettaglio dell'atto dall'rapporto di trasmissione

				TNotifDest notifDest =  attoDao.caricaNotificatoDaRapporto(idAtto,idUffOwn);
				if (notifDest!=null) {
					attoNotificato = dettaglioAtto(
							idUffOwn,
							notifDest.getTNotifRichieste().getTNotifiche().getCodiFkIdAtto(),
							idProcedimento,
							codiceUtente);
					
					mapperBean.map(attoNotificato, notificaAttoForm);
					DestinatarioDto destinatarioDTO = new DestinatarioDto();
					String pec = "";
					if (notifDest.getDescPecDest()!=null) {
						pec = " (" + notifDest.getDescPecDest() + ")";
					}
					destinatarioDTO.setDESC_DEST(notifDest.getDescDestinataro() + pec);
					notificaAttoForm.getListaDestinatari().add(destinatarioDTO);
					
					if (attoNotificato.getAllegato().getFileName() != null) {
						attoNotificato.getAllegato().setFileName(attoNotificato.getAllegato().getFileName());
						attoNotificato.getAllegato().setContentType(attoNotificato.getAllegato().getContentType());
					}
				}

			}
		}
		return notificaAttoForm;
	}
 
	public long salvaProvvedimentoFascicoloGestione(int idUffOwn, long idProcedimento, long idAtto, String codiceUtente, AttoForm attoForm, long idStatoAtto, String nomeMetodo) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TProvFg provv = new TProvFg();
		mapperBean.map(attoForm, provv);

		Utility.popolaDocumento(attoForm, atto);
		
		return attoDao.salvaProvvedimentoFascicoloGestione(idUffOwn, idProcedimento, idAtto, codiceUtente, atto, idStatoAtto, provv, nomeMetodo);
	}
	

	public long inserisciOrdinanzaAntimafia(int idUfficio, String codUtente, Long idProcedimento, AttoForm attoForm, long idStatoAtto, String nomeMetodo,long idSoggetto) {

        TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		return attoDao.inserisciOrdinanzaAntimafia(idUfficio, idProcedimento, codUtente, atto.getTTpTipoAtto().getCodiKIdTipoAtto(), atto.getTTpCatAtto()
				.getCodiKIdCatAtto(), (int) idStatoAtto, nomeMetodo,idSoggetto);
	}
	
	/**
	 * getSogAntimafia
	 * @param idUffOwn
	 * @param idProc
	 * @return SoggettoForm
	 */
	public SoggettoForm getSogAntimafia(int idUffOwn, Long idProc, Long idAtto) {
		
		List<SogIntDettaglioProcedimentoDto> soggetti = attoDao.getSogAntimafia(idUffOwn, idProc,idAtto);
		SoggettoForm sForm = new SoggettoForm();
		
		Iterator<SogIntDettaglioProcedimentoDto> iter = soggetti.iterator();
		while (iter.hasNext()) {
			SogIntDettaglioProcedimentoDto currentDto = iter.next();
			
			mapperBean.map(currentDto, sForm);
		}
		return sForm;
	}	

	public long inserisciProvvedimentoRevocaAmministratori(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm,
			long idStatoAtto, String nomeMetodo, Long idAttoOrigine) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TDecreto decreto = new TDecreto();
		mapperBean.map(attoForm, decreto);

		Utility.popolaDocumento(attoForm, atto);

		return attoDao.inserisciProvvedimentoRevocaAmministratori(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, decreto, nomeMetodo, idAttoOrigine);
	}	

	public long salvaProvvedimentoRevocaAmministratori(int idUffOwn, long idProcedimento, long idAtto, String codiceUtente, AttoForm attoForm, long idStatoAtto, List<Long> amministratoriRevocati, String nomeMetodo) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TDecreto decreto = new TDecreto();
		mapperBean.map(attoForm, decreto);

		Utility.popolaDocumento(attoForm, atto);
		
		String idsAmm = null;
		if(amministratoriRevocati!=null) {
			idsAmm = "";
			for(int i=0;i<amministratoriRevocati.size();i++) {
				Long idAmministratore = amministratoriRevocati.get(i);
				idsAmm += idAmministratore+";";
			}
		}

		attoDao.revocaAmministratore(idUffOwn, idProcedimento, idAtto, idsAmm, attoForm.getDataDeposito(), codiceUtente, nomeMetodo);

		return attoDao.salvaProvvedimentoRevocaAmministratori(idUffOwn, idProcedimento, idAtto, codiceUtente, atto, idStatoAtto, decreto, nomeMetodo);
	}
	
	public long inserisciProvvedimentoNominaAmministratori(int idUffOwn, long idProcedimento, String codiceUtente, AttoForm attoForm,
			long idStatoAtto, String nomeMetodo) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TDecreto decreto = new TDecreto();
		mapperBean.map(attoForm, decreto);

		Utility.popolaDocumento(attoForm, atto);

		return attoDao.inserisciProvvedimentoNominaAmministratori(idUffOwn, idProcedimento, codiceUtente, atto, idStatoAtto, decreto, nomeMetodo);
	}	

	public long salvaProvvedimentoNominaAmministratori(int idUffOwn, long idProcedimento, long idAtto, String codiceUtente, AttoForm attoForm, long idStatoAtto, String nomeMetodo) {

		TAtto atto = new TAtto();
		mapperBean.map(attoForm, atto);

		TDecreto decreto = new TDecreto();
		mapperBean.map(attoForm, decreto);

		Utility.popolaDocumento(attoForm, atto);
		
		return attoDao.salvaProvvedimentoNominaAmministratori(idUffOwn, idProcedimento, idAtto, codiceUtente, atto, idStatoAtto, decreto, nomeMetodo);
	}
	
	public List<AmministratoreForm> getAmministratoriRevocabili(int idUffOwn, Long idProcedimento) {

		List queryResults = null;
		queryResults = attoDao.getAmministratoriRevocabili(idUffOwn, idProcedimento);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<AmministratoreForm> risultati = new ArrayList<AmministratoreForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TRelProcAttoAmm relProcAttoAmm = (TRelProcAttoAmm) row.get("rel");
			TAmministratori amministratore = (TAmministratori) row.get("amm");
			TAnComuni comuni = (TAnComuni) row.get("comuni");
			AmministratoreForm form = new AmministratoreForm();

			form.setId(amministratore.getCodiKIdAmmin());
			form.setIdAtto(relProcAttoAmm.getTAtto().getId().getCodiKIdAtto());
			form.setCognome(amministratore.getDescCognome());
			form.setNome(amministratore.getDescNome());
			if (amministratore.getDescPartitaIva()!=null){
				form.setCodiceFiscale(amministratore.getDescPartitaIva());	
			}
			if (amministratore.getDescCodiceFiscale()!=null){
				form.setCodiceFiscale(amministratore.getDescCodiceFiscale());	
			}
			form.setDescrIscrizioneAlbo(amministratore.getDescStatoIscrizioneAlbo());
			form.setDataIscrizioneAlbo(amministratore.getDataIscrizioneAlbo());
			if (amministratore.getDescComEsteroDl()!=null){
				form.setComuneDomicilioLegale(amministratore.getDescComEsteroDl());	
			}else{
				if(comuni!=null) {
					form.setComuneDomicilioLegale(comuni.getDescComune() + " ("+comuni.getTAnProvincia().getDescSiglaProvincia()+" )");
				}
			}
			form.setDataInizioIncarico(relProcAttoAmm.getDataInizioIncarico());
			form.setDataFineIncarico(relProcAttoAmm.getDataFineIncarico());
			risultati.add(form);
		}
		return risultati;
	}
	
	public List<AmministratoreForm> getAmministratoriRevocati(int idUffOwn, Long idProcedimento, Long idAtto) {

		List queryResults = null;
		queryResults = attoDao.getAmministratoriRevocati(idUffOwn, idProcedimento, idAtto);
		if ((queryResults == null) || queryResults.isEmpty()) {
			return null;
		}
		List<AmministratoreForm> risultati = new ArrayList<AmministratoreForm>();
		Iterator iterator = queryResults.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			TRelProcAttoAmm relProcAttoAmm = (TRelProcAttoAmm) row.get("rel");
			TAmministratori amministratore = (TAmministratori) row.get("amm");
			TAnComuni comuni = (TAnComuni) row.get("comuni");
			AmministratoreForm form = new AmministratoreForm();

			form.setId(amministratore.getCodiKIdAmmin());
			form.setIdAtto(relProcAttoAmm.getTAtto().getId().getCodiKIdAtto());
			form.setCognome(amministratore.getDescCognome());
			form.setNome(amministratore.getDescNome());
			if (amministratore.getDescPartitaIva()!=null){
				form.setCodiceFiscale(amministratore.getDescPartitaIva());	
			}
			if (amministratore.getDescCodiceFiscale()!=null){
				form.setCodiceFiscale(amministratore.getDescCodiceFiscale());	
			}
			form.setDescrIscrizioneAlbo(amministratore.getDescStatoIscrizioneAlbo());
			form.setDataIscrizioneAlbo(amministratore.getDataIscrizioneAlbo());
			if (amministratore.getDescComEsteroDl()!=null){
				form.setComuneDomicilioLegale(amministratore.getDescComEsteroDl());	
			}else{
				if(comuni!=null) {
					form.setComuneDomicilioLegale(comuni.getDescComune() + " ("+comuni.getTAnProvincia().getDescSiglaProvincia()+" )");
				}
			}
			form.setDataInizioIncarico(relProcAttoAmm.getDataInizioIncarico());
			form.setDataFineIncarico(relProcAttoAmm.getDataFineIncarico());
			risultati.add(form);
		}
		return risultati;
	}

	public Date getMaxDataEmissioneProposta(int idUffOwn, long idProcedimento) {
		return attoDao.getMaxDataEmissioneProposta(idUffOwn, idProcedimento);
	}	
	

	/**
	 * aggiornaDecisorioIIIGrado
	 * @param idUffOwn
	 * @param idProc
	 * @param attoForm
	 * @param codUtente
	 * @param tipoUff
	 * @return
	 */
	public Long aggiornaDecisorioIIIGrado(long idUffOwn, long idProc,AttoForm attoForm, String codUtente, int tipoUff) {

		TAtto atto = popolaAttoEDocumento(attoForm);
		return attoDao.aggiornaDecisorioIIIGrado(idUffOwn, idProc, atto, codUtente,tipoUff);
	}
	
	
	/**
	 * Escude le misure per le quali non deve essere inviato il foglio complementare.
	 * L'esclusione avviene utilizzando un funzione che in base all'idAtto e della misura
	 * e ad un serie di altre considerazioni isolate su un funzione del db determina il codice
	 * della misura da invare a NSC .
	 * @param listaMisure
	 */
	public void escludiMisureNonInviabile(List<RedazioneSchedaDto> listaMisure, long idProcedimento, int idUffOwn) {
		ArrayList<RedazioneSchedaDto> clone = new ArrayList<RedazioneSchedaDto>(listaMisure);
		for (RedazioneSchedaDto redazioneSchedaDto : clone) {
			
			// Se la funzione che determina l'esito della misura restituisce null
			// escludo la misura dalla lista di quelle inviabili
			String esitoNSC = attoDao.getCodiceEsitoNSCPerFoglioComplementare(	
					idProcedimento,
					idUffOwn,
					redazioneSchedaDto.getCODI_K_ID_ATTO().longValue() ,
					redazioneSchedaDto.getCODI_K_ID_MISURA().longValue()
			);
			
			if (esitoNSC == null) {
				//TODO MARCO FC decommentare logica di esclusione per esito nsc
				listaMisure.remove(redazioneSchedaDto);
			}
			
		}
		
		
	}
	
	public TDefIstGen caricaDefinitivitaIstGen(TDefIstGenId id) {
		
		return attoDao.caricaDefinitivitaIstGen(id);
	}
	
	
	public void salvaDecErroreMateriale( long idAtto,  long idProcedimento,  String idCodiceUtente,  int idUfficio,  String descDecisorio, SoggettoForm soggettoForm)
	{
	    IntestatarioDto intestario = new IntestatarioDto();	    
	    TRelAttoIstGen rec = new TRelAttoIstGen();    
	    int idDecisorio=0;
	   
	    if("DECISORIO".equalsIgnoreCase(descDecisorio)){
	    	 rec.setDescAltro1(soggettoForm.getDescAltro1());
	    	 idDecisorio = Integer.parseInt(soggettoForm.getDecisioneErroreMaterialeIGrado().getId());
	    	 
	    	 
	    }else if("SENTENZA_CASSAZIONE".equalsIgnoreCase(descDecisorio)){
	    	rec.setDescAltro3(soggettoForm.getDescAltro3());
	    	idDecisorio = Integer.parseInt(soggettoForm.getDecisioneErroreMaterialeIIIGrado().getId());
	    }
	    	          
		mapperBean.map(soggettoForm, intestario);
		
		
		Date dataRicezione = null;
		Long numeAnnoSentenza =null;
		Long numeSentenza = null;
		
		if(soggettoForm != null && soggettoForm.getDataRicezioneSentenza() != null){
		 dataRicezione = soggettoForm.getDataRicezioneSentenza();
		}
		if(soggettoForm != null && soggettoForm.getNumeAnnoSentenza() != null){
		 numeAnnoSentenza = Long.parseLong(soggettoForm.getNumeAnnoSentenza());
		}
		 if(soggettoForm != null && soggettoForm.getNumeSentenza() != null){ 
		 numeSentenza =Long.parseLong(soggettoForm.getNumeSentenza());
	    }
	
			

		attoDao.salvaDecErroreMateriale(idAtto, idProcedimento, idCodiceUtente, idUfficio, descDecisorio, idDecisorio, intestario, rec,dataRicezione,numeAnnoSentenza,numeSentenza);
	}

	public boolean existsIntervenientiNonAssociati(Long idProcedimento, int idUfficio) {
		List existsIntervenientiNonAssociati = attoDao.existsIntervenientiNonAssociati(idProcedimento, idUfficio);
		if (CollectionUtils.isEmpty(existsIntervenientiNonAssociati)) {
			return false;
		} else {
			return existsIntervenientiNonAssociati.get(0) != null;
		}
	}	
	
	public boolean isAttoOrigine(long idAtto, int idUffOwn) {
		return attoDao.isAttoOrigine(idAtto, idUffOwn);
	}

	public long ordinanzaVenditaBene(long idUffOwn, String codUtente, long idprocedimento, BeneForm beneForm, AttoForm attoForm) {

		TAtto atto = popolaAttoEDocumento(attoForm);
		
		TBene bene = new TBene();
		mapperBean.map(beneForm, bene);
		
		TDenaro denaro = new TDenaro();
		mapperBean.map(beneForm, denaro);
		
		TContante contante = new TContante();
		mapperBean.map(beneForm, contante);
	
		
		long statoBene = 0;
		if(beneForm.getStatoConservazione()!=null){
			statoBene = NumberUtils.toLong(beneForm.getStatoConservazione().getId(),0);
		}
		
		List<Map> insAtto = attoDao.ordinanzaVenditaBene(idUffOwn, codUtente, idprocedimento, statoBene, bene, denaro, beneForm.getNumBollettino(), atto);
		
		if (insAtto != null && insAtto.size() > 0){
			return  (Long) insAtto.get(0).get("IDATTO");
		}
		return 0;
	}
	
	public long inserisciAttoRelazioneAmm(int idUffOwn, String idCodiceUtente, long idProcFG, long idTipoAtto, long idCatAtto, 
			   long idStatoAtto, Date dataRicezione, long idAmm, String cognomeAmm, String nomeAmm, Date dataInizio, Date dataFine, long idPDOC, 
			   Blob blob, String tipoAllegato, String nomeAllegato, String numeroRiferimento) {
						return attoDao.inserisciAttoRelazioneAmm(idUffOwn, idCodiceUtente, idProcFG, idTipoAtto, idCatAtto, idStatoAtto, dataRicezione, idAmm, cognomeAmm, nomeAmm, dataInizio, dataFine, idPDOC, blob, tipoAllegato, nomeAllegato, numeroRiferimento);
					}	

	public TAttoGenerico getTAttoGenerico(long idAtto, long idUfficio){
		return attoDao.getTAttoGenerico(idAtto, idUfficio);
	}
	
	
	public long contaInvioMsgCassazione( long idProc,  long idAtto,  long idUffOwn){
		
		return attoDao.contaInvioMsgCassazione(idProc, idAtto, idUffOwn);
	}
	
	public int getUffOwnerFromIdDocumento(BigInteger idDocumento){
		return attoDao.getUffOwnerFromIdDocumento(idDocumento);
	}
	
	public int getUffOwnerTestDocumentale(){
		return attoDao.getUffOwnerTestDocumentale();
	}
	
	public boolean getRelAttoProcByIdProcAndCat( long idProcedimento,  int idCatAtto){
		List<Map> list = attoDao.getRelAttoProcByIdProcAndCat(idProcedimento, idCatAtto);
		if (list == null || list.size() == 0) 
			return false;
		else
			return true;
	}
	
	public KeyMessageDto controllaPresenzaRicorso(long idProc,long idUfficio, String funzione, String descUtente){
    	
    	return attoDao.controllaPresenzaRicorso(idProc, idUfficio, funzione, descUtente);
    }
	
	public List<TRelAttoMisPers> getTRelAttoMisPers(long idProcedimento,long idAttoImpugnazione,int idUffOwn){
		return attoDao.getTRelAttoMisPers(idProcedimento, idAttoImpugnazione, idUffOwn);
	}
	
	public TDecreto getTDecreto(long idAttoDecreto, int idUffOwnerDecreto){
		return attoDao.getTDecreto( idAttoDecreto,  idUffOwnerDecreto);
	}
	
	public KeyMessageDto impostaGiudizioDefinitivoI(long idProc,long idUfficio, String funzione, String descUtente){
    	
    	return attoDao.impostaGiudizioDefinitivoI(idProc, idUfficio, funzione, descUtente);
    }

	
	public void cancelIdTransazioneDocumentale(long idAtto, int uffOwner) {
		
		  attoDao.cancelIdTransazioneDocumentale(idAtto, uffOwner);
		
	}

	/**
	 * getRicercaSoggettoAttoSpeseProcessuali 
	 * 
	 * @param idProcedimento
	 * @param descRuolo
	 * @param flagValido
	 * @return List<SoggettoForm>
	 */
	public List<SoggettoForm> getRicercaSoggettoAttoSpeseProc(long idProcedimento, long idatto, int iduff, int descRuolo, Character flagValido, List<ComboForm> listaSpeseProcessuali) {

		List<SoggettoForm> lista = new ArrayList<SoggettoForm>();

		List<SogSpesePDettaglioProcedimentoDto> soggettiProcedimento = attoDao.getRicercaSoggettoAttoSpeseProc(idProcedimento, idatto, iduff, descRuolo,
				flagValido);
		Iterator<SogSpesePDettaglioProcedimentoDto> iter = soggettiProcedimento.iterator();
		while (iter.hasNext()) {
			SoggettoForm soggettoForm = new SoggettoForm();
			SogSpesePDettaglioProcedimentoDto currentDto = iter.next();
			mapperBean.map(currentDto, soggettoForm);
			
			// Art67 e Cauzione sono significativi solo per le misure PERSONALI
			if (soggettoForm.getMisura().getDescrizioneMisura() == null || soggettoForm.getMisura().getDescrizioneMisura().contains("PATRIMONIALE")) {
				soggettoForm.getMisura().setArt67(null);
				soggettoForm.getMisura().setCauzione(null);
			}
			soggettoForm.setListaSpeseProcessuali(listaSpeseProcessuali);
			if (soggettoForm.getSpeseProcessuali().getId() == null || soggettoForm.getSpeseProcessuali().getId().equals(Constants.NO_ITEM_SELECTED) ){
				soggettoForm.getSpeseProcessuali().setDescrizione("NON CONDANNATO");
			
		    }else{
				
		    	int idSpesaP = Integer.parseInt(soggettoForm.getSpeseProcessuali().getId());
		    	TTpSpeseProcessuali ricercaSpesa = commonService.ricercaSpeseProcessuali(idSpesaP);
		    	DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
				decimalFormatSymbols.setDecimalSeparator(',');
				decimalFormatSymbols.setGroupingSeparator('.');
				soggettoForm.getSpeseProcessuali().setDescrizione(String.valueOf(new DecimalFormat("#,##0.00", decimalFormatSymbols).format(ricercaSpesa.getImportoSpese()))+" ");
				
			}
		
			
			lista.add(soggettoForm);
		}
		return lista;

	}
	
}