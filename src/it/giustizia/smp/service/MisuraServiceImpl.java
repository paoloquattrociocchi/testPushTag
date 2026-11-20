package it.giustizia.smp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.giustizia.smp.dao.MisuraDao;
import it.giustizia.smp.dto.pg_misura.DettaglioMisuraPersonaleDto;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.MisuraForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.model.TMisuraPers;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TRelAttoMisPers;
import it.giustizia.smp.model.TTpEsito;
import it.giustizia.smp.model.TTpTipoMisura;
import it.giustizia.smp.utility.Constants;

@Transactional(rollbackFor = Exception.class)
public class MisuraServiceImpl implements MisuraService {

	private static final int	CODICE_ESITO_1_PROPOSTA_ACCOGLIE	= 28;
	private static final int	CODICE_ESITO_2_PROPOSTA_ACCOGLIE	= 27;
	private final static int	CODICE_ESITO_FIN_APPLICA = 1;
	// private static final int CODICE_ESITO_2_IMPUGNATO_IN_CORTE_D_APPELLO = 0;
	@Autowired
	private Mapper				mapperBean;
	private MisuraDao			misuraDao;
	
	@Autowired
	private ProcedimentoService	procedimentoService;
	@Autowired(required = true)
	private HttpServletRequest	request;
	@Autowired
	private BeneService			beneService;

	public void setBeneService(BeneService beneService) {

		this.beneService = beneService;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SoggettoForm caricaMisuraPersonale(SoggettoForm soggettoForm) {
		ProcedimentoForm procedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)));
		if (soggettoForm.getIdAtto() == null || soggettoForm.getIdAtto() == 0L) {
			long idAtto = 0L;
			AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
			if(atto != null){
				idAtto = atto.getCodAtto();
				soggettoForm.setIdAtto(idAtto);
			}
		}
		
		
		List<DettaglioMisuraPersonaleDto> risultato = misuraDao.caricaMisuraPersonale(soggettoForm.getIdProcedimento(), soggettoForm.getIdAtto(), soggettoForm.getIdUfficio(), soggettoForm.getIdSoggetto());
		soggettoForm.setMisura(new MisuraForm());
		if (risultato != null) {
			Iterator<DettaglioMisuraPersonaleDto> iterator = risultato.iterator();
			List<Long> idRestrizioni = new ArrayList();
			List<Long> idMisuraInserita = new ArrayList();
			List<Long> idVecchiePrescrizioni = new ArrayList();
			List<ComboForm> vecchiePrescrizioni = new ArrayList();
			while (iterator.hasNext()) {
				DettaglioMisuraPersonaleDto misuraPersonale = iterator.next();
				idMisuraInserita.add(misuraPersonale.getID_MISURA_INSERITA().longValue());
				if (misuraPersonale.getID_ATTO() != null) {
					soggettoForm.setIdAtto(misuraPersonale.getID_ATTO().longValue());
				}
				if (misuraPersonale.getULTERIORI_PRESCRIZIONI() != null) {
					if (misuraPersonale.getDATA_FINE_VALIDITA() == null) {
						soggettoForm.getMisura().setUlterioriPrescrizioni(misuraPersonale.getULTERIORI_PRESCRIZIONI());
					} else {
						soggettoForm.getMisura().setUlterioriPrescrizioniVecchie(misuraPersonale.getULTERIORI_PRESCRIZIONI());
					}
				}
				if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("P")) {
					soggettoForm.getMisura().setTipoMisura(new ComboForm());
					soggettoForm.getMisura().getTipoMisura().setId("" + misuraPersonale.getID_TIPO_MISURA());
					soggettoForm.getMisura().getTipoMisura().setDescrizione(misuraPersonale.getDESC_TIPO_MISURA());
					soggettoForm.setTipologiaPrevenzione(new ComboForm());
					soggettoForm.getTipologiaPrevenzione().setId("" + misuraPersonale.getID_TIPO_PREV());
					soggettoForm.getTipologiaPrevenzione().setDescrizione(misuraPersonale.getDESC_TIPO_PREV());
					soggettoForm.getMisura().setNormativaDiRiferimento(new ComboForm());
					soggettoForm.getMisura().getNormativaDiRiferimento().setId("" + misuraPersonale.getID_NORMATIVA());
					soggettoForm.getMisura().getNormativaDiRiferimento().setDescrizione(misuraPersonale.getDESC_NORMATIVA());
					if (misuraPersonale.getLOCALITA() != null) {
						soggettoForm.getMisura().setLocalita(misuraPersonale.getLOCALITA());
					}
					if (misuraPersonale.getANNI() != null) {
						soggettoForm.getMisura().setAnni(misuraPersonale.getANNI().shortValue());
					}
					if (misuraPersonale.getGIORNI() != null) {
						soggettoForm.getMisura().setGiorni(misuraPersonale.getGIORNI().shortValue());
					}
					if (misuraPersonale.getMESI() != null) {
						soggettoForm.getMisura().setMesi(misuraPersonale.getMESI().shortValue());
					}
					if (misuraPersonale.getDECISIONE_MISURA() != null) {
						ComboForm decisioneMisura = new ComboForm(misuraPersonale.getDECISIONE_MISURA() + "", misuraPersonale.getDESC_DECISIONE_MISURA() + "");
						soggettoForm.getMisura().setDecisioneMisuraPersonale(decisioneMisura);
					}
				}
				
				if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("AP")) {
					soggettoForm.getMisura().setTipoMisura(new ComboForm());
					soggettoForm.getMisura().getTipoMisura().setId("" + misuraPersonale.getID_TIPO_MISURA());
					soggettoForm.getMisura().getTipoMisura().setDescrizione(misuraPersonale.getDESC_TIPO_MISURA());
					soggettoForm.setTipologiaPrevenzione(new ComboForm());
					soggettoForm.getTipologiaPrevenzione().setId("" + misuraPersonale.getID_TIPO_PREV());
					soggettoForm.getTipologiaPrevenzione().setDescrizione(misuraPersonale.getDESC_TIPO_PREV());
					soggettoForm.getMisura().setNormativaDiRiferimento(new ComboForm());
					soggettoForm.getMisura().getNormativaDiRiferimento().setId("" + misuraPersonale.getID_NORMATIVA());
					soggettoForm.getMisura().getNormativaDiRiferimento().setDescrizione(misuraPersonale.getDESC_NORMATIVA());
					if (misuraPersonale.getLOCALITA() != null) {
						soggettoForm.getMisura().setLocalita(misuraPersonale.getLOCALITA());
					}
	
				}				
				
				if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("C")) {
					if(misuraPersonale.getDECISIONE_MISURA()!=null){
					    if(misuraPersonale.getDECISIONE_MISURA().intValue()==28 ||
					   //nuovi codici esito per cauzione e articolo 67.   		
					    		 misuraPersonale.getDECISIONE_MISURA().intValue()==208 || 
                        	    misuraPersonale.getDECISIONE_MISURA().intValue()==50 || 
					            misuraPersonale.getDECISIONE_MISURA().intValue()==51 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==52 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==53 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==223 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==226 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==227 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==228 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==229 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==230  ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==235 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==236 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==237 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==238 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==243 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==252 ){
					        soggettoForm.getMisura().setCauzione("S");
		                    soggettoForm.getMisura().setImporto(misuraPersonale.getIMPORTO_CAUZIONE());
		                    soggettoForm.getMisura().setNote(misuraPersonale.getNOTE());
					    }
					    else soggettoForm.getMisura().setCauzione("N");
					}
					else {
					    soggettoForm.getMisura().setCauzione("S");
                        soggettoForm.getMisura().setImporto(misuraPersonale.getIMPORTO_CAUZIONE());
                        soggettoForm.getMisura().setNote(misuraPersonale.getNOTE());
					}
				}
				
				if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("A")) {
                    if(misuraPersonale.getDECISIONE_MISURA()!=null){
                        if(misuraPersonale.getDECISIONE_MISURA().intValue()==28 ||
                       	//nuovi codici esito per cauzione e articolo 67.
                              		 misuraPersonale.getDECISIONE_MISURA().intValue()==210 ||
                                misuraPersonale.getDECISIONE_MISURA().intValue()==50 || 
                                misuraPersonale.getDECISIONE_MISURA().intValue()==51 ||
                                misuraPersonale.getDECISIONE_MISURA().intValue()==52 ||
                                misuraPersonale.getDECISIONE_MISURA().intValue()==53 ||
                                misuraPersonale.getDECISIONE_MISURA().intValue()==223 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==226 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==227 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==228 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==229 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==230){
				            soggettoForm.getMisura().setArt67("S");
				        }
				        else soggettoForm.getMisura().setArt67("N");
				    }
				    else soggettoForm.getMisura().setArt67("S");
				}

				if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("S")) {             					 
					  soggettoForm.getMisura().setArt9c1("S");				 
				    }
				
								
				
				if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("R")) {
					if (misuraPersonale.getDATA_FINE_VALIDITA() == null) {
						idRestrizioni.add(misuraPersonale.getID_TIPO_MISURA().longValue());
					} else {
						ComboForm vecchiePrescrizioniCombo = new ComboForm(misuraPersonale.getID_TIPO_MISURA() + "", misuraPersonale.getDESC_TIPO_MISURA());
						idVecchiePrescrizioni.add(Long.parseLong(vecchiePrescrizioniCombo.getId()));
						vecchiePrescrizioni.add(vecchiePrescrizioniCombo);
					}
				}
				if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("OP")) {
					if (misuraPersonale.getDECISIONE_MISURA() != null) {
						ComboForm decisioneMisura = new ComboForm(misuraPersonale.getDECISIONE_MISURA() + "", null);
						soggettoForm.getMisura().setDecisioneMisuraPersonale(decisioneMisura);
					}
				}
				if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("C")
						&& Long.parseLong(procedimento.getTipoProcedimento().getId()) == Constants.TIPOPROC_CAUZIONI_CAM) {
					if (misuraPersonale.getDECISIONE_MISURA() != null) {
						ComboForm decisioneMisura = new ComboForm(misuraPersonale.getDECISIONE_MISURA() + "", null);
						soggettoForm.getMisura().setDecisioneMisuraPersonale(decisioneMisura);
					}
				}
				
			}// fine del ciclo while
			if ((soggettoForm.getMisura() == null) || (soggettoForm.getMisura().getCauzione() == null)) {
				soggettoForm.getMisura().setCauzione("N");
			}
			if ((soggettoForm.getMisura() == null) || (soggettoForm.getMisura().getArt67() == null)) {
				soggettoForm.getMisura().setArt67("N");
			}
			soggettoForm.getMisura().setIdSelezionati(idRestrizioni);
			if (!idMisuraInserita.isEmpty()) {
				String idMisureCaricate = idMisuraInserita.get(0) + "";
				if (idMisuraInserita.size() > 1) {
					for (int i = 1; i < idMisuraInserita.size(); i++) {
						idMisureCaricate += ";" + idMisuraInserita.get(i);
					}
				}
				soggettoForm.getMisura().setIdMisureCaricate(idMisureCaricate);
			}
			if (!vecchiePrescrizioni.isEmpty()) {
				soggettoForm.getMisura().setVecchiePrescrizioni(vecchiePrescrizioni);
				soggettoForm.getMisura().setIdVecchiePrescrizioni(idVecchiePrescrizioni);
			}
		} else {
			soggettoForm.getMisura().setCauzione("N");
		}
		return soggettoForm;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SoggettoForm caricaMisuraPersonaleCas(SoggettoForm soggettoForm) {

		if (soggettoForm.getIdAtto() == null || soggettoForm.getIdAtto() == 0L) {
			long idAtto = 0L;
			AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
			if(atto != null){
				idAtto = atto.getCodAtto();
				soggettoForm.setIdAtto(idAtto);
			}
		}
		
		
		List<DettaglioMisuraPersonaleDto> risultato = misuraDao.caricaMisuraPersonale(soggettoForm.getIdProcedimento(), soggettoForm.getIdAtto(), soggettoForm.getIdUfficio(), soggettoForm.getIdSoggetto());
		soggettoForm.setMisura(new MisuraForm());
		if (risultato != null) {
			Iterator<DettaglioMisuraPersonaleDto> iterator = risultato.iterator();
			List<Long> idRestrizioni = new ArrayList();
			List<Long> idMisuraInserita = new ArrayList();
			List<Long> idVecchiePrescrizioni = new ArrayList();
			List<ComboForm> vecchiePrescrizioni = new ArrayList();
			while (iterator.hasNext()) {
				DettaglioMisuraPersonaleDto misuraPersonale = iterator.next();
				idMisuraInserita.add(misuraPersonale.getID_MISURA_INSERITA().longValue());
				if (misuraPersonale.getID_ATTO() != null) {
					soggettoForm.setIdAtto(misuraPersonale.getID_ATTO().longValue());
				}
				if (misuraPersonale.getULTERIORI_PRESCRIZIONI() != null) {
					if (misuraPersonale.getDATA_FINE_VALIDITA() == null) {
						soggettoForm.getMisura().setUlterioriPrescrizioni(misuraPersonale.getULTERIORI_PRESCRIZIONI());
					} else {
						soggettoForm.getMisura().setUlterioriPrescrizioniVecchie(misuraPersonale.getULTERIORI_PRESCRIZIONI());
					}
				}
				if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("P")) {
					soggettoForm.getMisura().setTipoMisura(new ComboForm());
					soggettoForm.getMisura().getTipoMisura().setId("" + misuraPersonale.getID_TIPO_MISURA());
					soggettoForm.getMisura().getTipoMisura().setDescrizione(misuraPersonale.getDESC_TIPO_MISURA());
					soggettoForm.setTipologiaPrevenzione(new ComboForm());
					soggettoForm.getTipologiaPrevenzione().setId("" + misuraPersonale.getID_TIPO_PREV());
					soggettoForm.getTipologiaPrevenzione().setDescrizione(misuraPersonale.getDESC_TIPO_PREV());
					soggettoForm.getMisura().setNormativaDiRiferimento(new ComboForm());
					soggettoForm.getMisura().getNormativaDiRiferimento().setId("" + misuraPersonale.getID_NORMATIVA());
					soggettoForm.getMisura().getNormativaDiRiferimento().setDescrizione(misuraPersonale.getDESC_NORMATIVA());
					if (misuraPersonale.getLOCALITA() != null) {
						soggettoForm.getMisura().setLocalita(misuraPersonale.getLOCALITA());
					}
					if (misuraPersonale.getANNI() != null) {
						soggettoForm.getMisura().setAnni(misuraPersonale.getANNI().shortValue());
					}
					if (misuraPersonale.getGIORNI() != null) {
						soggettoForm.getMisura().setGiorni(misuraPersonale.getGIORNI().shortValue());
					}
					if (misuraPersonale.getMESI() != null) {
						soggettoForm.getMisura().setMesi(misuraPersonale.getMESI().shortValue());
					}
					if (misuraPersonale.getDECISIONE_MISURA() != null) {
						/** MODIFICA RICHEISTA DA STEFANIA PER VISUALIZZAZIONE ESITO SU SCHEDA CASELLARIO E FOGLIO COMPLEMENTARE
						 *  La modifica prende la descrizione dell'esito della misura dando priorità in ordine a partire dal III grado II grado I grado.
						 */
						if (misuraPersonale.getDEC_MISURA_III_GRADO()!=null) {
							ComboForm decisioneMisura = new ComboForm(misuraPersonale.getDEC_MISURA_III_GRADO() + "", misuraPersonale.getDESC_DEC_MISURA_III_GRADO() + "");
							soggettoForm.getMisura().setDecisioneMisuraPersonale(decisioneMisura);
						} else if (misuraPersonale.getDECISIONE_MISURA_II_GRADO() !=null) {
							ComboForm decisioneMisura = new ComboForm(misuraPersonale.getDECISIONE_MISURA_II_GRADO() + "", misuraPersonale.getDESC_DECISIONE_MISURA_II_GRADO() + "");
							soggettoForm.getMisura().setDecisioneMisuraPersonale(decisioneMisura);
						} else if (misuraPersonale.getDECISIONE_MISURA_I_GRADO() !=null) { 
							ComboForm decisioneMisura = new ComboForm(misuraPersonale.getDECISIONE_MISURA_I_GRADO() + "", misuraPersonale.getDESC_DECISIONE_MISURA_I_GRADO() + "");
							soggettoForm.getMisura().setDecisioneMisuraPersonale(decisioneMisura);
						} else {
							ComboForm decisioneMisura = new ComboForm(misuraPersonale.getDECISIONE_MISURA() + "", misuraPersonale.getDESC_DECISIONE_MISURA() + "");
							soggettoForm.getMisura().setDecisioneMisuraPersonale(decisioneMisura);
						}
					}
				}
				
				if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("AP")) {
					soggettoForm.getMisura().setTipoMisura(new ComboForm());
					soggettoForm.getMisura().getTipoMisura().setId("" + misuraPersonale.getID_TIPO_MISURA());
					soggettoForm.getMisura().getTipoMisura().setDescrizione(misuraPersonale.getDESC_TIPO_MISURA());
					soggettoForm.setTipologiaPrevenzione(new ComboForm());
					soggettoForm.getTipologiaPrevenzione().setId("" + misuraPersonale.getID_TIPO_PREV());
					soggettoForm.getTipologiaPrevenzione().setDescrizione(misuraPersonale.getDESC_TIPO_PREV());
					soggettoForm.getMisura().setNormativaDiRiferimento(new ComboForm());
					soggettoForm.getMisura().getNormativaDiRiferimento().setId("" + misuraPersonale.getID_NORMATIVA());
					soggettoForm.getMisura().getNormativaDiRiferimento().setDescrizione(misuraPersonale.getDESC_NORMATIVA());
					if (misuraPersonale.getLOCALITA() != null) {
						soggettoForm.getMisura().setLocalita(misuraPersonale.getLOCALITA());
					}
	
				}				
				
				if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("C")) {
					if(misuraPersonale.getDECISIONE_MISURA()!=null){
					    if(misuraPersonale.getDECISIONE_MISURA().intValue()==28 ||
					   //nuovi codici esito per cauzione e articolo 67.   		
					    		 misuraPersonale.getDECISIONE_MISURA().intValue()==208 || 
                        	    misuraPersonale.getDECISIONE_MISURA().intValue()==50 || 
					            misuraPersonale.getDECISIONE_MISURA().intValue()==51 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==52 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==53 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==223 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==226 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==227 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==228 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==229 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==230  ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==235 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==236 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==237 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==238 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==243 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==252 ){
					        soggettoForm.getMisura().setCauzione("S");
		                    soggettoForm.getMisura().setImporto(misuraPersonale.getIMPORTO_CAUZIONE());
		                    soggettoForm.getMisura().setNote(misuraPersonale.getNOTE());
					    }
					    else soggettoForm.getMisura().setCauzione("N");
					}
					else {
					    soggettoForm.getMisura().setCauzione("S");
                        soggettoForm.getMisura().setImporto(misuraPersonale.getIMPORTO_CAUZIONE());
                        soggettoForm.getMisura().setNote(misuraPersonale.getNOTE());
					}
				}
				
				if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("A")) {
                    if(misuraPersonale.getDECISIONE_MISURA()!=null){
                        if(misuraPersonale.getDECISIONE_MISURA().intValue()==28 ||
                       	//nuovi codici esito per cauzione e articolo 67.
                              		 misuraPersonale.getDECISIONE_MISURA().intValue()==210 ||
                                misuraPersonale.getDECISIONE_MISURA().intValue()==50 || 
                                misuraPersonale.getDECISIONE_MISURA().intValue()==51 ||
                                misuraPersonale.getDECISIONE_MISURA().intValue()==52 ||
                                misuraPersonale.getDECISIONE_MISURA().intValue()==53 ||
                                misuraPersonale.getDECISIONE_MISURA().intValue()==223 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==226 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==227 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==228 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==229 ||
					            misuraPersonale.getDECISIONE_MISURA().intValue()==230){
				            soggettoForm.getMisura().setArt67("S");
				        }
				        else soggettoForm.getMisura().setArt67("N");
				    }
				    else soggettoForm.getMisura().setArt67("S");
				}

				if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("S")) {             					 
					  soggettoForm.getMisura().setArt9c1("S");				 
				    }
				
								
				
				if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("R")) {
					if (misuraPersonale.getDATA_FINE_VALIDITA() == null) {
						idRestrizioni.add(misuraPersonale.getID_TIPO_MISURA().longValue());
					} else {
						ComboForm vecchiePrescrizioniCombo = new ComboForm(misuraPersonale.getID_TIPO_MISURA() + "", misuraPersonale.getDESC_TIPO_MISURA());
						idVecchiePrescrizioni.add(Long.parseLong(vecchiePrescrizioniCombo.getId()));
						vecchiePrescrizioni.add(vecchiePrescrizioniCombo);
					}
				}
				if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("OP")) {
					if (misuraPersonale.getDECISIONE_MISURA() != null) {
						ComboForm decisioneMisura = new ComboForm(misuraPersonale.getDECISIONE_MISURA() + "", null);
						soggettoForm.getMisura().setDecisioneMisuraPersonale(decisioneMisura);
					}
				}
			}// fine del ciclo while
			if ((soggettoForm.getMisura() == null) || (soggettoForm.getMisura().getCauzione() == null)) {
				soggettoForm.getMisura().setCauzione("N");
			}
			if ((soggettoForm.getMisura() == null) || (soggettoForm.getMisura().getArt67() == null)) {
				soggettoForm.getMisura().setArt67("N");
			}
			soggettoForm.getMisura().setIdSelezionati(idRestrizioni);
			if (!idMisuraInserita.isEmpty()) {
				String idMisureCaricate = idMisuraInserita.get(0) + "";
				if (idMisuraInserita.size() > 1) {
					for (int i = 1; i < idMisuraInserita.size(); i++) {
						idMisureCaricate += ";" + idMisuraInserita.get(i);
					}
				}
				soggettoForm.getMisura().setIdMisureCaricate(idMisureCaricate);
			}
			if (!vecchiePrescrizioni.isEmpty()) {
				soggettoForm.getMisura().setVecchiePrescrizioni(vecchiePrescrizioni);
				soggettoForm.getMisura().setIdVecchiePrescrizioni(idVecchiePrescrizioni);
			}
		} else {
			soggettoForm.getMisura().setCauzione("N");
		}
		return soggettoForm;
	}
//	public Long duplicaMisuraPersonalePerAggravamento(long idMisura, int idUfficioUtente) {
//
//		return misuraDao.duplicaMisuraPersonalePerAggravamento(idMisura, idUfficioUtente);
//	}
	
	public void duplicaMisuraPersonalePerAggravamento( Long idPadre,Long idAggravamento,Long idAttoPadreAggiornato,Long idAttoAggravamento,Long idSoggettoInteressato,Integer idUffOwn){ 
		 misuraDao.duplicaMisuraPersonalePerAggravamento(idPadre,idAggravamento,idAttoPadreAggiornato,idAttoAggravamento,idSoggettoInteressato,idUffOwn);
	}	

	public void inserisciMisuraPersonale(long vidProc, long vidUffOwn, long idSoggInt, String vcodUtente, SoggettoForm soggettoForm) {

		long vidAtto = 0L;
		String cauzione = "N";
		String idPrescrizioni;
		String articolo67 = "N";
		String articolo9c1 ="N";
		String idMisureCaricate = null;
//		try {
			TRelAttoMisPers relAttoMisPers = new TRelAttoMisPers();
			mapperBean.map(soggettoForm, relAttoMisPers);
			if (soggettoForm.getMisura() != null) {
				if (soggettoForm.getIdAtto() != null) {
					vidAtto = soggettoForm.getIdAtto();
				}
				if (soggettoForm.getMisura().getCauzione() != null) {
					cauzione = soggettoForm.getMisura().getCauzione();
				}
				if (soggettoForm.getMisura().getArt67() != null) {
					articolo67 = soggettoForm.getMisura().getArt67();
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
			} else {
				idPrescrizioni = null;
			}
			//commentato da sabino
			//relAttoMisPers.setCodiFkIdTipoAtto(9L);
			misuraDao.inserisciMisuraPersonale(idMisureCaricate, vidProc, vidAtto, vidUffOwn, relAttoMisPers, idSoggInt, cauzione, articolo67, vcodUtente, idPrescrizioni, null,articolo9c1);
			
			procedimentoService.aggiornaTipoProcPrevProc(vidProc, vidUffOwn, vcodUtente);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public void inserisciMisuraPersonaleDecisorioIGrado(long vidProc, long tipoProc, long vidUffOwn, long idSoggInt, String vcodUtente, SoggettoForm soggettoForm) {

		long vidAtto = 0L;
		String cauzione = "N";
		String idPrescrizioni;
		String articolo67 = "N";
		String idMisureCaricate = null;
	    String idDecisione  = soggettoForm.getMisura().getDecisioneMisuraPersonale().getId();

			TRelAttoMisPers relAttoMisPers = new TRelAttoMisPers();
			mapperBean.map(soggettoForm, relAttoMisPers);
			if (soggettoForm.getMisura() != null) {
				if (soggettoForm.getIdAtto() != null) {
					vidAtto = soggettoForm.getIdAtto();
				}
				if (soggettoForm.getMisura().getCauzione() != null) {
					cauzione = soggettoForm.getMisura().getCauzione();
				}
				
				
				if (soggettoForm.getMisura().getTipoMisura().getId()!= null && !soggettoForm.getMisura().getTipoMisura().getId().equals("") ){
					TTpTipoMisura tipoMisura =new TTpTipoMisura();
					tipoMisura.setCodiKIdTipoMisura(Integer.parseInt(soggettoForm.getMisura().getTipoMisura().getId()));
					relAttoMisPers.setTTpTipoMisura(tipoMisura);
					
				}
				
				//modifica cauzione
				if (tipoProc==Constants.TIPOPROC_CAUZIONI_CAM){
					if (idDecisione.equals("235") ||
						idDecisione.equals("236") ||
						idDecisione.equals("237") ||
						idDecisione.equals("238"))
					cauzione ="S";
				else cauzione ="N";
				}
				
				if (soggettoForm.getMisura().getArt67() != null) {
					articolo67 = soggettoForm.getMisura().getArt67();
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
			} else {
				idPrescrizioni = null;
			}
			
			//long idEsito = Long.parseLong(soggettoForm.getMisura().getDecisioneMisuraPersonale().getId());
			// misuraDao.inserisciMisuraPersonaleDecisorioIGrado(vidProc,idEsito, vidAtto, vidUffOwn, vcodUtente);
           //commentato da sabino
			// relAttoMisPers.setCodiFkIdTipoAtto(3L);
            BigDecimal importoCauzione = soggettoForm.getMisura().getImporto();
			//misuraDao.inserisciMisuraPersonale(idMisureCaricate, vidProc, vidAtto, vidUffOwn, relAttoMisPers, idSoggInt, cauzione, articolo67, vcodUtente, idPrescrizioni, importoCauzione);
            
            
            String azionePrescrAccoglie = null;
            String azionePrescrNoAccoglie = null;
            
            
            //pez
            if (tipoProc==15L || tipoProc==16L){
            TTpEsito ttpEsito =new TTpEsito(Integer.parseInt(idDecisione));            
            relAttoMisPers.setTTpEsitoByCodiFkIdEsito1(ttpEsito);
            }
            
            // *** GESTIONE PRESCRIZIONI PROPOSTA-DECRETO DECISORIO I GRADO ***
            //Recupero la lista delle Restrizioni della Proposta
            List<TRelAttoMisPers> listaPrescrPropostaDB = new ArrayList<TRelAttoMisPers>();
            listaPrescrPropostaDB = listaIdMisureSpecificheProposta(vidProc,vidUffOwn,idSoggInt,0L,"R");
            String idPrescrizioniProposta = listaPrescrizioni(listaPrescrPropostaDB);
            //Decisione = ACCOGLIE (28) per Fascicolo di Prevenzione
            //Decisione = ACCOGLIE... (50-51-52-53) per Aggravamento
            // (59-60-61-62) per II Grado
            if      (  idDecisione.equals("28") || idDecisione.equals("50") || idDecisione.equals("51") 
            		|| idDecisione.equals("52") || idDecisione.equals("53")
            	    || idDecisione.equals("59") || idDecisione.equals("60") 
            	    || idDecisione.equals("61") || idDecisione.equals("62")
            	    || idDecisione.equals("223") || idDecisione.equals("226")
            	    || idDecisione.equals("227") || idDecisione.equals("228")
            	    || idDecisione.equals("229") || idDecisione.equals("230")
            	    || idDecisione.equals("235") || idDecisione.equals("236")
            	    || idDecisione.equals("237") || idDecisione.equals("238")
            	    || idDecisione.equals("243") || idDecisione.equals("252")
            		){
                //Azioni da fare sulla base delle Prescrizioni presenti in pagina del Decisorio e quelle della Proposta sul DB
                azionePrescrAccoglie = azionePrescrizioni(idPrescrizioni, idPrescrizioniProposta);
            }
            else{//Decisione != ACCOGLIE
                //Recupero la lista delle Restrizioni del Decisorio presenti sul DB
                List<TRelAttoMisPers> listaPrescrDecisorioDB = new ArrayList<TRelAttoMisPers>();
                listaPrescrDecisorioDB = listaIdMisureSpecificheProposta(vidProc,vidUffOwn,idSoggInt,vidAtto,"R");
                String idPrescrizioniDecisorio = listaPrescrizioni(listaPrescrDecisorioDB);
                //Azioni da fare sulla base delle Prescrizioni presenti sul DB del Decisorio e quelle della Proposta sul DB
                azionePrescrNoAccoglie = azionePrescrizioni(idPrescrizioniDecisorio, idPrescrizioniProposta);
            }
            // *** GESTIONE PRESCRIZIONI PROPOSTA-DECRETO DECISORIO I GRADO ***
            String cauzioneProp = soggettoForm.getMisura().getCauzioneProp();
            String noteCauz = soggettoForm.getMisura().getNote();
            String articolo67Prop = soggettoForm.getMisura().getArt67Prop();
            long idDecisioneLong = Long.valueOf((soggettoForm.getMisura().getDecisioneMisuraPersonale().getId()));
            misuraDao.inserisciMisuraPersonaleDecIGrado(vidProc, vidAtto, vidUffOwn, relAttoMisPers, idSoggInt, cauzione, cauzioneProp, noteCauz, articolo67, articolo67Prop, vcodUtente, importoCauzione, azionePrescrAccoglie, azionePrescrNoAccoglie, idDecisioneLong, tipoProc);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

    private String listaPrescrizioni(List<TRelAttoMisPers> listaPrescrPropostaDB) {
        
        String idPrescrizioni = "";
        for(int i=0;i<listaPrescrPropostaDB.size();i++){
            TRelAttoMisPers tRelAttoMisPers = (TRelAttoMisPers)listaPrescrPropostaDB.get(i);
            if(i==0){
                idPrescrizioni = tRelAttoMisPers.getTTpTipoMisura().getCodiKIdTipoMisura() + "";
            }
            else{
                idPrescrizioni += ";" + tRelAttoMisPers.getTTpTipoMisura().getCodiKIdTipoMisura();
            }
        }
        return idPrescrizioni;
    }

    public List<TRelAttoMisPers> listaIdMisureSpecificheProposta(long vidProc, long vidUffOwn, long idSoggInt, long vidAtto, String carMisura) {

	    List queryResults = null;
	    List<TRelAttoMisPers> listaDB = new ArrayList<TRelAttoMisPers>();
        queryResults = misuraDao.listaIdMisSpecificheProposta(vidProc,vidUffOwn,idSoggInt,vidAtto,carMisura);
        if ((queryResults != null) && !queryResults.isEmpty()) {
            Iterator iterator = queryResults.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> row = (Map<String, Object>) iterator.next();
                TRelAttoMisPers atto = (TRelAttoMisPers) row.get("atto");
                listaDB.add(atto);
            }
        }
        return listaDB;
    }

    private String azionePrescrizioni(String idPrescrizioniDecisorio, String idPrescrizioniProposta) {

	    //Azioni possibili :
	    //1.Prescrizione presente nella proposta e non confermata nel decisorio
	    //2.Prescrizione presente nella proposta e confermata nel decisorio
	    //3.Prescrizione non presente nella proposta, ma inserita nel decisorio
	    //4.Prescrizione non presente nella proposta e non inserita nemmeno nel decisorio
	    //String azione contiene l'azione corrispondente ad ogni idPrescrizione nell'ordine 30,31,32,33
	    String azione = "";
	    String idPrescPossibili[] = {"30","31","32","33"};
	    
	    for (int i = 0; i < idPrescPossibili.length; i++) {
            String idPrescr = idPrescPossibili[i];
            if(i==0){
                azione = azioneSpecificaPrescrizione(idPrescrizioniDecisorio, idPrescrizioniProposta, idPrescr) + "";
            }
            else azione += ";" + azioneSpecificaPrescrizione(idPrescrizioniDecisorio, idPrescrizioniProposta, idPrescr); 
        }	    
        return azione;
    }
	
	private String azioneSpecificaPrescrizione(String idPrescrizioniDecisorio, String idPrescrizioniProposta, String idPrescr){
	    
	    String azione = "";
	    if(idPrescrizioniProposta.contains(idPrescr)){
	        if(idPrescrizioniDecisorio!=null){
                if(idPrescrizioniDecisorio.contains(idPrescr)){
                    azione = azione + "2";
                }
                else azione = azione + "1";
	        }
	        else azione = azione + "1";
        }
        else{
            if(idPrescrizioniDecisorio!=null){
                if(idPrescrizioniDecisorio.contains(idPrescr)){
                    azione = azione + "3";
                }
                else azione = azione + "4"; 
            }
            else azione = azione + "4";
        }
	    return azione;
	}

    public void inserisciMisuraSoggettoDiffidato(long idProc, int idUffOwn, long idSoggInt, String codUtente) {

		misuraDao.inserisciMisuraSoggettoDiffidato(idProc, idUffOwn, idSoggInt, 34, "OP", codUtente);
	}

	public List<TMisuraPers> listaMisurePersByIdSoggetto(long idSoggettoInteressato, long idProcedimento, int idUffOwn) {

		return misuraDao.listaMisurePersByIdSoggetto(idSoggettoInteressato, idProcedimento, idUffOwn);
	}

	public List<TMisuraPers> listaMisurePersByIdSoggetto(long idSoggettoInteressato, int idUffOwn) {

		return misuraDao.listaMisurePersByIdSoggetto(idSoggettoInteressato, idUffOwn);
	}

	public List<TRelAttoMisPers> listaRelAttoMisurePersByIdAtto(long idAtto, int idUffOwn,long idProcedimento) {

		return misuraDao.listaRelAttoMisurePersByIdAtto(idAtto, idUffOwn,idProcedimento);
	}		
	
	public List<TRelAttoMisPers> listaRelAttoMisPersByProcEUfficio(Long idPadre, Integer idUffOwn) {

		List<TRelAttoMisPers> risultati = misuraDao.listaRelAttoMisPersByProcEUfficio(idPadre, idUffOwn);
		return risultati;
	}

	public TRelAttoMisPers relAttoMisPersByProcMisPersEAtto(long idMisura, long idPadre, long idAttoPadreAggiornato, int idUffOwn) {

		TRelAttoMisPers relAttoMisPersByProcMisPersEAtto = misuraDao.relAttoMisPersByProcMisPersEAtto(idMisura, idPadre, idAttoPadreAggiornato, idUffOwn);

		return relAttoMisPersByProcMisPersEAtto;
	}

	public void saveRelazioneMisuraPersonalePerAggravamento(TRelAttoMisPers relazione, long idAggravamento, long idAttoAggravamento, long idMisura, int idUfficioUtente) {

		misuraDao.salvaRelazioneMisuraPersonalePerAggravamento(relazione, idAggravamento, idAttoAggravamento, idMisura, idUfficioUtente);
	}

	public void setMisuraDao(MisuraDao misuraDao) {

		this.misuraDao = misuraDao;
	}

	public boolean stataAccolta(TRelAttoMisPers tmp) {

		String tipoUtente = (String) request.getSession().getAttribute(Constants.TIPO_UTENTE);
		int idTipoUfficio = (Integer) request.getSession().getAttribute(Constants.ID_TIPO_UFFICIO);
		if ((tmp.getTTpEsitoByCodiFkIdEsito3() != null) && (tmp.getTTpEsitoByCodiFkIdEsito3().getCodiKIdEsito() >0)) {
			// se è andato in cassazione
			return false;
		}
		// se l'utente è della procura
		if (tipoUtente.equals(Constants.TIPO_UTENTE_SMP)) {
			if ((tmp.getTTpEsitoByCodiFkIdEsito2() != null) && (tmp.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito() >0) && (tmp.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito() == MisuraServiceImpl.CODICE_ESITO_2_PROPOSTA_ACCOGLIE)) {
				// se è stata accolta in secondo grado
				return true;
			}
			if ((tmp.getTTpEsitoByCodiFkIdEsito1() != null) && (tmp.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() >0) && (tmp.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() == MisuraServiceImpl.CODICE_ESITO_1_PROPOSTA_ACCOGLIE)) {
				// se è stata accolta in primo grado
				return true;
			}
		}
		// se l'utente è del tribunale
		if (tipoUtente.equals(Constants.TIPO_UTENTE_SMP_GIU) && idTipoUfficio!=Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
			if ((tmp.getTTpEsitoFinale() != null) && (tmp.getTTpEsitoFinale().getCodiKIdEsitoFin() >0) && (tmp.getTTpEsitoFinale().getCodiKIdEsitoFin() == MisuraServiceImpl.CODICE_ESITO_FIN_APPLICA)) {
				// se è stata accolta in primo grado
				return true;
			}
		}
		// se l'utente è della corte d'appello
		if (tipoUtente.equals(Constants.TIPO_UTENTE_SMP_GIU) && idTipoUfficio==Constants.TIPO_UFFICIO_CORTE_D_APPELLO) {
			if ((tmp.getTTpEsitoFinale() != null) && (tmp.getTTpEsitoFinale().getCodiKIdEsitoFin() >0) && (tmp.getTTpEsitoFinale().getCodiKIdEsitoFin() == MisuraServiceImpl.CODICE_ESITO_FIN_APPLICA)) {
				// se è stata accolta in secondo grado
				return true;
			}
		}
		return false;
	}

	public List<SoggettoForm> getSogIntSenzaDecisorio(final long idProcedimento, final long idAtto, final long idUffOwn, final String tipiMisura,String descAtto) {

		List<SoggettoForm> listaSoggetti = new ArrayList<SoggettoForm>();
		List<TPersonaFisica> personeFisiche = misuraDao.getSogIntSenzaDecisorio(idProcedimento, idAtto, idUffOwn, tipiMisura, descAtto);
		if (personeFisiche != null && personeFisiche.size() > 0) {
			Iterator iter = personeFisiche.iterator();
			while (iter.hasNext()) {
				Map<String, Object> row = (Map<String, Object>) iter.next();
				TPersonaFisica persona = (TPersonaFisica) row.get("personaFisica");
				SoggettoForm soggetto = new SoggettoForm();
				mapperBean.map(persona, soggetto);
				listaSoggetti.add(soggetto);
			}
		}
		return listaSoggetti;
	}

	public List<BeneForm> getBeniSenzaDecisorio(final long idProcedimento, final long idAtto, final long idUffOwn,String descAtto) {

		List<BeneForm> listaBeni = new ArrayList<BeneForm>();
		List<TBene> beni = misuraDao.getBeniSenzaDecisorio(idProcedimento, idAtto, idUffOwn,descAtto);
		if (beni != null && beni.size() > 0) {
			Iterator iter = beni.iterator();
			while (iter.hasNext()) {
				Map<String, Object> row = (Map<String, Object>) iter.next();
				TBene bene = (TBene) row.get("bene");
				BeneForm beneForm = new BeneForm();
				mapperBean.map(bene, beneForm);
				listaBeni.add(beneForm);
			}
		}
		return listaBeni;
	}

	public void eliminaMisuraPersonaleDelSoggetto(long idSoggetto) {

		ProcedimentoForm procedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)));
		Long idProcedimento = procedimento.getIdProcedimento();
		Integer idUfficioUtente = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		Long idAtto = beneService.getIdAttoByUfficioEProcedimento(idUfficioUtente, idProcedimento);
		String codUtente = (String) request.getSession().getAttribute(Constants.ID_CODICE_UTENTE);
		List<TMisuraPers> misurePersonali = misuraDao.listaMisurePersByIdSoggetto(idSoggetto, idProcedimento, idUfficioUtente);
		for (TMisuraPers misuraPersonale : misurePersonali) {
			Set<TRelAttoMisPers> relazioni = misuraPersonale.getTRelAttoMisPerses();
			if (relazioni != null && !relazioni.isEmpty()) {
				for (TRelAttoMisPers relazione : relazioni) {
					if (relazione.getId().getCodiKIdAtto() == idAtto && relazione.getId().getCodiKIdProc() == idProcedimento) {
						misuraDao.eliminaRelazioneAttoMisuraPersonale(relazione);
					}
				}
			}
			misuraDao.eliminaMisuraPersonale(misuraPersonale);
		}
		aggiornaTipoProcPrevProc(idProcedimento, idUfficioUtente, codUtente);
	}
	
	public Long aggiornaTipoProcPrevProc(long idProc, long idUffOwn, String codUtente) {

		return misuraDao.aggiornaTipoProcPrevProc(idProc, idUffOwn, codUtente);
	}
	
	
	public void convalidaNonConvalidaSequestro(long idProcedimento, long idAtto, long idBene, int idTipoInteresse, long idSoggetto, long idTipoMisura, int idUffOwn, String descUtente, String tipoOperazione, String nomeMetodo) {
		misuraDao.convalidaNonConvalidaSequestro(idProcedimento, idAtto, idBene, idTipoInteresse, idSoggetto, idTipoMisura, idUffOwn, descUtente, tipoOperazione, nomeMetodo);
	}
	
	public Date getDataDefinitivitaDecisorioIGradoRiesame(long idMisura, long idProc, long idAtto, int idUffOwn) {
		return misuraDao.getDataDefinitivitaDecisorioIGradoRiesame(idMisura, idProc, idAtto, idUffOwn);
	}
	
	//2GRADO
	
	public SoggettoForm caricaMisuraPersonaleIIGrado(SoggettoForm soggettoForm) 
	{
		
		List<DettaglioMisuraPersonaleDto> risultato = misuraDao.caricaMisuraPersonaleIIGrado(soggettoForm.getIdProcedimento(), soggettoForm.getIdAtto(), soggettoForm.getIdUfficio(), soggettoForm.getIdSoggetto());
		soggettoForm.setMisura(new MisuraForm());
		if (risultato != null) 
		{
				Iterator<DettaglioMisuraPersonaleDto> iterator = risultato.iterator();
				List<Long> idRestrizioni = new ArrayList<Long>();
				List<Long> idMisuraInserita = new ArrayList<Long>();
				List<Long> idVecchiePrescrizioni = new ArrayList<Long>();
				List<ComboForm> vecchiePrescrizioni = new ArrayList<ComboForm>();
				while (iterator.hasNext()) {
					DettaglioMisuraPersonaleDto misuraPersonale = iterator.next();
					idMisuraInserita.add(misuraPersonale.getID_MISURA_INSERITA().longValue());
					if (misuraPersonale.getID_ATTO() != null) {
						soggettoForm.setIdAtto(misuraPersonale.getID_ATTO().longValue());
					}
					if (misuraPersonale.getULTERIORI_PRESCRIZIONI() != null) {
						if (misuraPersonale.getDATA_FINE_VALIDITA() == null) {
							soggettoForm.getMisura().setUlterioriPrescrizioni(misuraPersonale.getULTERIORI_PRESCRIZIONI());
						} else {
							soggettoForm.getMisura().setUlterioriPrescrizioniVecchie(misuraPersonale.getULTERIORI_PRESCRIZIONI());
						}
					}
					if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("P")) {
						soggettoForm.setVisibilitaIIGrado(""+misuraPersonale.getBLOCCO());
						soggettoForm.getMisura().setTipoMisura(new ComboForm());
						soggettoForm.getMisura().getTipoMisura().setId("" + misuraPersonale.getID_TIPO_MISURA());
						soggettoForm.getMisura().getTipoMisura().setDescrizione(misuraPersonale.getDESC_TIPO_MISURA());
						soggettoForm.setTipologiaPrevenzione(new ComboForm());
						soggettoForm.getTipologiaPrevenzione().setId("" + misuraPersonale.getID_TIPO_PREV());
						soggettoForm.getTipologiaPrevenzione().setDescrizione(misuraPersonale.getDESC_TIPO_PREV());
						soggettoForm.getMisura().setNormativaDiRiferimento(new ComboForm());
						soggettoForm.getMisura().getNormativaDiRiferimento().setId("" + misuraPersonale.getID_NORMATIVA());
						soggettoForm.getMisura().getNormativaDiRiferimento().setDescrizione(misuraPersonale.getDESC_NORMATIVA());
						if (misuraPersonale.getLOCALITA() != null) {
							soggettoForm.getMisura().setLocalita(misuraPersonale.getLOCALITA());
						}
						if (misuraPersonale.getANNI() != null) {
							soggettoForm.getMisura().setAnni(misuraPersonale.getANNI().shortValue());
						}
						if (misuraPersonale.getGIORNI() != null) {
							soggettoForm.getMisura().setGiorni(misuraPersonale.getGIORNI().shortValue());
						}
						if (misuraPersonale.getMESI() != null) {
							soggettoForm.getMisura().setMesi(misuraPersonale.getMESI().shortValue());
						}
						if (misuraPersonale.getDECISIONE_MISURA() != null) {
							ComboForm decisioneMisura = new ComboForm(misuraPersonale.getDECISIONE_MISURA() + "", misuraPersonale.getDESC_DECISIONE_MISURA() + "");
							soggettoForm.getMisura().setDecisioneMisuraPersonaleIIGrado(decisioneMisura);
						}
						if (misuraPersonale.getDECISIONE_MISURA_I_GRADO() != null) {
							ComboForm decisioneMisura = new ComboForm(misuraPersonale.getDECISIONE_MISURA_I_GRADO() + "", misuraPersonale.getDESC_DECISIONE_MISURA_I_GRADO() + "");
							soggettoForm.getMisura().setDecisioneMisuraPersonale(decisioneMisura);
						}
						//do la cauzione e art 67 se c'e' l'impugnazione della misura personale
						if(soggettoForm.getVisibilitaIIGrado().equals("S")){
						soggettoForm.getMisura().setCauzione("N");
						soggettoForm.getMisura().setArt67("N");
						}
						
					}
					
					if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("C")) {
						if(misuraPersonale.getDECISIONE_MISURA()!=null){
						    if(misuraPersonale.getIMPORTO_CAUZIONE() != null  ){
						        soggettoForm.getMisura().setCauzione("S");
			                    soggettoForm.getMisura().setImporto(misuraPersonale.getIMPORTO_CAUZIONE());
			                    soggettoForm.getMisura().setNote(misuraPersonale.getNOTE());
						    }
						    else
						    { 
						    	soggettoForm.getMisura().setCauzione("N");
						    
						    }
						}
						else 
						{ 
					        soggettoForm.getMisura().setCauzione("S");
		                    soggettoForm.getMisura().setImporto(misuraPersonale.getIMPORTO_CAUZIONE());
		                    soggettoForm.getMisura().setNote(misuraPersonale.getNOTE());
								
						}
//						Segnalazione EMS-97
						ProcedimentoForm procedimento = ((ProcedimentoForm) (request.getSession().getAttribute(Constants.ID_PROCEDIMENTO)));
						if(procedimento.getTipoProcedimento().getId().toString().equals("" + Constants.TIPOPROC_CAUZIONI_CAM))
						{
							if (misuraPersonale.getDECISIONE_MISURA() != null) {
								ComboForm decisioneMisura = new ComboForm(misuraPersonale.getDECISIONE_MISURA() + "", misuraPersonale.getDESC_DECISIONE_MISURA() + "");
								soggettoForm.getMisura().setDecisioneMisuraPersonaleIIGrado(decisioneMisura);
							}
						}
					}
					if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("A")) 
					{
	                    if(misuraPersonale.getDECISIONE_MISURA()!=null)
	                    {
	                        if(misuraPersonale.getDECISIONE_MISURA().intValue()==214)
	                        {
					            soggettoForm.getMisura().setArt67("S");
					        }
					        else
					        {
					        	soggettoForm.getMisura().setArt67("N");
					        }
					    }
					    else
				    	{
					    	soggettoForm.getMisura().setArt67("S");
				    	}
					}
					
					if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("R")) 
					{
						if (misuraPersonale.getDATA_FINE_VALIDITA() == null)
						{
							idRestrizioni.add(misuraPersonale.getID_TIPO_MISURA().longValue());
						} else 
						{
							ComboForm vecchiePrescrizioniCombo = new ComboForm(misuraPersonale.getID_TIPO_MISURA() + "", misuraPersonale.getDESC_TIPO_MISURA());
							idVecchiePrescrizioni.add(Long.parseLong(vecchiePrescrizioniCombo.getId()));
							vecchiePrescrizioni.add(vecchiePrescrizioniCombo);
						}
					}
					if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("OP")) 
					{
						if (misuraPersonale.getDECISIONE_MISURA() != null) 
						{
							ComboForm decisioneMisura = new ComboForm(misuraPersonale.getDECISIONE_MISURA() + "", null);
							soggettoForm.getMisura().setDecisioneMisuraPersonale(decisioneMisura);
						}
					}
				}// fine del ciclo while
				
				soggettoForm.getMisura().setIdSelezionati(idRestrizioni);
				
				if (!idMisuraInserita.isEmpty())
				{
					String idMisureCaricate = idMisuraInserita.get(0) + "";
					if (idMisuraInserita.size() > 1)
					{
						for (int i = 1; i < idMisuraInserita.size(); i++) 
						{
							idMisureCaricate += ";" + idMisuraInserita.get(i);
						}
					}
					soggettoForm.getMisura().setIdMisureCaricate(idMisureCaricate);
				}
				if (!vecchiePrescrizioni.isEmpty()) 
				{
					soggettoForm.getMisura().setVecchiePrescrizioni(vecchiePrescrizioni);
					soggettoForm.getMisura().setIdVecchiePrescrizioni(idVecchiePrescrizioni);
				}
		} 
		return soggettoForm;
	}
	
	
	/**
	 * caricaMisuraPersonaleIIIGrado
	 * @param soggettoForm
	 * @return
	 */
	public SoggettoForm caricaMisuraPersonaleIIIGrado(SoggettoForm soggettoForm) 
	{
		
		List<DettaglioMisuraPersonaleDto> risultato = misuraDao.caricaMisuraPersonaleIIIGrado(soggettoForm.getIdProcedimento(), soggettoForm.getIdAtto(), soggettoForm.getIdUfficio(), soggettoForm.getIdSoggetto());
		soggettoForm.setMisura(new MisuraForm());
		if (risultato != null && !risultato.isEmpty()) 
		{
				Iterator<DettaglioMisuraPersonaleDto> iterator = risultato.iterator();
				List<Long> idRestrizioni = new ArrayList<Long>();
				List<Long> idMisuraInserita = new ArrayList<Long>();

				while (iterator.hasNext()) {
				   DettaglioMisuraPersonaleDto misuraPersonale = iterator.next();
				   if(misuraPersonale != null && misuraPersonale.getID_MISURA_INSERITA()!= null){
					   
					idMisuraInserita.add(misuraPersonale.getID_MISURA_INSERITA().longValue());
					
					if (misuraPersonale.getID_ATTO() != null) {
						soggettoForm.setIdAtto(misuraPersonale.getID_ATTO().longValue());
					}
					if (misuraPersonale.getULTERIORI_PRESCRIZIONI() != null) {
						if (misuraPersonale.getDATA_FINE_VALIDITA() == null) {
							soggettoForm.getMisura().setUlterioriPrescrizioni(misuraPersonale.getULTERIORI_PRESCRIZIONI());
						} else {
							soggettoForm.getMisura().setUlterioriPrescrizioniVecchie(misuraPersonale.getULTERIORI_PRESCRIZIONI());
						}
					}
					if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("P")) {
						soggettoForm.setVisibilitaIIGrado(""+misuraPersonale.getBLOCCO());
						soggettoForm.getMisura().setTipoMisura(new ComboForm());
						soggettoForm.getMisura().getTipoMisura().setId("" + misuraPersonale.getID_TIPO_MISURA());
						soggettoForm.getMisura().getTipoMisura().setDescrizione(misuraPersonale.getDESC_TIPO_MISURA());
						soggettoForm.setTipologiaPrevenzione(new ComboForm());
						soggettoForm.getTipologiaPrevenzione().setId("" + misuraPersonale.getID_TIPO_PREV());
						soggettoForm.getTipologiaPrevenzione().setDescrizione(misuraPersonale.getDESC_TIPO_PREV());
						soggettoForm.getMisura().setNormativaDiRiferimento(new ComboForm());
						soggettoForm.getMisura().getNormativaDiRiferimento().setId("" + misuraPersonale.getID_NORMATIVA());
						soggettoForm.getMisura().getNormativaDiRiferimento().setDescrizione(misuraPersonale.getDESC_NORMATIVA());
						if (misuraPersonale.getLOCALITA() != null) {
							soggettoForm.getMisura().setLocalita(misuraPersonale.getLOCALITA());
						}
						if (misuraPersonale.getANNI() != null) {
							soggettoForm.getMisura().setAnni(misuraPersonale.getANNI().shortValue());
						}
						if (misuraPersonale.getGIORNI() != null) {
							soggettoForm.getMisura().setGiorni(misuraPersonale.getGIORNI().shortValue());
						}
						if (misuraPersonale.getMESI() != null) {
							soggettoForm.getMisura().setMesi(misuraPersonale.getMESI().shortValue());
						}
						if (misuraPersonale.getDECISIONE_MISURA() != null) {
							ComboForm decisioneMisura = new ComboForm(misuraPersonale.getDECISIONE_MISURA() + "", misuraPersonale.getDESC_DECISIONE_MISURA() + "");
							soggettoForm.getMisura().setDecisioneMisuraPersonale(decisioneMisura);
						}
						if (misuraPersonale.getDECISIONE_MISURA_II_GRADO() != null) {
							ComboForm decisioneMisuraII = new ComboForm(misuraPersonale.getDECISIONE_MISURA_II_GRADO() + "", misuraPersonale.getDESC_DECISIONE_MISURA_II_GRADO() + "");
							soggettoForm.getMisura().setDecisioneMisuraPersonaleIIGrado(decisioneMisuraII);
						}
						
						if (misuraPersonale.getDEC_MISURA_III_GRADO() != null) {
							ComboForm decisioneMisuraIII = new ComboForm(misuraPersonale.getDEC_MISURA_III_GRADO() + "", misuraPersonale.getDESC_DEC_MISURA_III_GRADO() + "");
							soggettoForm.getMisura().setDecisioneMisuraPersonaleSentenzaCassazione(decisioneMisuraIII);
						}
						
						//do la cauzione e art 67 se c'e' l'impugnazione della misura personale
						if(soggettoForm.getVisibilitaIIGrado().equals("S")){
						soggettoForm.getMisura().setCauzione("N");
						soggettoForm.getMisura().setArt67("N");
						}
						
					}
					
					if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("C")) {
						if(misuraPersonale.getDECISIONE_MISURA_II_GRADO()!=null){
//							Segnalazione EMS-102
							soggettoForm.setTipologiaPrevenzione(new ComboForm());
							soggettoForm.getTipologiaPrevenzione().setId("" + misuraPersonale.getID_TIPO_PREV());
							soggettoForm.getTipologiaPrevenzione().setDescrizione(misuraPersonale.getDESC_TIPO_PREV());
						    if(misuraPersonale.getDECISIONE_MISURA_II_GRADO().intValue()==212 ){
						        soggettoForm.getMisura().setCauzione("S");
			                    soggettoForm.getMisura().setImporto(misuraPersonale.getIMPORTO_CAUZIONE());
			                    soggettoForm.getMisura().setNote(misuraPersonale.getNOTE());
						    }
						    else
						    { 
						    	soggettoForm.getMisura().setCauzione("N");
						    }
						}
						else 
						{ 
					        soggettoForm.getMisura().setCauzione("S");
		                    soggettoForm.getMisura().setImporto(misuraPersonale.getIMPORTO_CAUZIONE());
		                    soggettoForm.getMisura().setNote(misuraPersonale.getNOTE());
						}
						if (misuraPersonale.getDEC_MISURA_III_GRADO() != null) {
							ComboForm decisioneMisuraCarattere = new ComboForm(misuraPersonale.getDEC_MISURA_III_GRADO() + "", misuraPersonale.getDESC_DEC_MISURA_III_GRADO() + "");
							soggettoForm.getMisura().setDecisioneCauzioneSentenzaCassazione(decisioneMisuraCarattere);
						}
					}
					if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("A")) 
					{
	                    if(misuraPersonale.getDECISIONE_MISURA_II_GRADO()!=null)
	                    {
	                        if(misuraPersonale.getDECISIONE_MISURA_II_GRADO().intValue()==214)
	                        {
					            soggettoForm.getMisura().setArt67("S");
					        }
					        else
					        {
					        	soggettoForm.getMisura().setArt67("N");
					        }
					    }
					    else
				    	{
					    	soggettoForm.getMisura().setArt67("S");
				    	}
	        			if (misuraPersonale.getDEC_MISURA_III_GRADO() != null) {
							ComboForm decisioneMisuraArt67 = new ComboForm(misuraPersonale.getDEC_MISURA_III_GRADO() + "", misuraPersonale.getDESC_DEC_MISURA_III_GRADO() + "");
							soggettoForm.getMisura().setDecisioneArt67SentenzaCassazione(decisioneMisuraArt67);
						}
					}
					if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("R")) 
					{
						if (misuraPersonale.getDATA_FINE_VALIDITA() == null)
						{
							idRestrizioni.add(misuraPersonale.getID_TIPO_MISURA().longValue());
						} else 
						{
							ComboForm vecchiePrescrizioniCombo = new ComboForm(misuraPersonale.getID_TIPO_MISURA() + "", misuraPersonale.getDESC_TIPO_MISURA());
			
						}
					}
					if (!misuraPersonale.getCARATTERE_MISURA().isEmpty() && misuraPersonale.getCARATTERE_MISURA().equals("OP")) 
					{
						if (misuraPersonale.getDECISIONE_MISURA() != null) 
						{
							ComboForm decisioneMisura = new ComboForm(misuraPersonale.getDECISIONE_MISURA() + "", null);
							soggettoForm.getMisura().setDecisioneMisuraPersonale(decisioneMisura);
						}
					}
					
				 }
				}// fine del ciclo while
				
	
				if (idRestrizioni !=  null && !idRestrizioni.isEmpty())
				{				
				  soggettoForm.getMisura().setIdSelezionati(idRestrizioni);
				}
				
				if (idMisuraInserita !=  null && !idMisuraInserita.isEmpty())
				{
					String idMisureCaricate = idMisuraInserita.get(0) + "";
					if (idMisuraInserita.size() > 1)
					{
						for (int i = 1; i < idMisuraInserita.size(); i++) 
						{
							idMisureCaricate += ";" + idMisuraInserita.get(i);
						}
					}
					soggettoForm.getMisura().setIdMisureCaricate(idMisureCaricate);
				}
		} 
		return soggettoForm;
	}	
	 public List<TRelAttoMisPers> listaIdMisureSpecificheRicorso(long vidProc, long vidUffOwn, long idSoggInt, long vidAtto, String carMisura) {

		    List queryResults = null;
		    List<TRelAttoMisPers> listaDB = new ArrayList<TRelAttoMisPers>();

	        queryResults = misuraDao.listaIdMisSpecificheRicorso(vidProc,vidUffOwn,idSoggInt,vidAtto,carMisura);

	        if ((queryResults != null) && !queryResults.isEmpty()) {
	            Iterator iterator = queryResults.iterator();
	            while (iterator.hasNext()) {
	                Map<String, Object> row = (Map<String, Object>) iterator.next();
	                TRelAttoMisPers atto = (TRelAttoMisPers) row.get("atto");
	                listaDB.add(atto);
	            }
	        }
	        return listaDB;
	    }

	 public void inserisciMisuraPersonaleDecisorioIIGrado(long vidProc, long tipoProc, long vidUffOwn, long idSoggInt, String vcodUtente, SoggettoForm soggettoForm,boolean annullaNormativa) {

		long vidAtto = 0L;
		String cauzione = "N";
		String idPrescrizioni;
		String articolo67 = "N";
		String idMisureCaricate = null;
		String idDecisione = null;
	            
        if (soggettoForm.getMisura().getDecisioneMisuraPersonaleIIGrado()!=null && soggettoForm.getMisura().getDecisioneMisuraPersonaleIIGrado().getId()!= null)
        	idDecisione = soggettoForm.getMisura().getDecisioneMisuraPersonaleIIGrado().getId();
        
		TRelAttoMisPers relAttoMisPers = new TRelAttoMisPers();
		mapperBean.map(soggettoForm, relAttoMisPers);
		if (annullaNormativa) relAttoMisPers.setTAnNormative(null);
		if (soggettoForm.getMisura() != null) 
		{
			if (soggettoForm.getIdAtto() != null) {
				vidAtto = soggettoForm.getIdAtto();
			}
			if (soggettoForm.getMisura().getCauzione() != null) {
				cauzione = soggettoForm.getMisura().getCauzione();
			}
			
			if (soggettoForm.getMisura().getTipoMisura().getId()!= null && !soggettoForm.getMisura().getTipoMisura().getId().equals("") )
			{
				TTpTipoMisura tipoMisura =new TTpTipoMisura();
				tipoMisura.setCodiKIdTipoMisura(Integer.parseInt(soggettoForm.getMisura().getTipoMisura().getId()));
				relAttoMisPers.setTTpTipoMisura(tipoMisura);
				
			}
			//modifica cauzione
			if (tipoProc == Constants.TIPOPROC_CAUZIONI_CAM) 
			{
				if (idDecisione.equals("262") || idDecisione.equals("263") || idDecisione.equals("264") ||
						idDecisione.equals("235") || idDecisione.equals("236") || idDecisione.equals("237") || idDecisione.equals("238")) {
					cauzione = "S";
				} else {
					cauzione = "N";
				}
			}
			if (soggettoForm.getMisura().getArt67() != null) {
				articolo67 = soggettoForm.getMisura().getArt67();
			}
			if (soggettoForm.getMisura().getIdMisureCaricate() != null) {
				idMisureCaricate = soggettoForm.getMisura().getIdMisureCaricate();
			}
			if ((soggettoForm.getMisura().getIdSelezionati() != null) && !soggettoForm.getMisura().getIdSelezionati().isEmpty()) 
			{
				idPrescrizioni = soggettoForm.getMisura().getIdSelezionati().get(0) + "";
				if (soggettoForm.getMisura().getIdSelezionati().size() > 1) {
					for (int i = 1; i < soggettoForm.getMisura().getIdSelezionati().size(); i++) {
						idPrescrizioni += ";" + soggettoForm.getMisura().getIdSelezionati().get(i);
					}
				}
			} else {
				idPrescrizioni = null;
			}
		} 
		else {
			idPrescrizioni = null;
		}
		
        BigDecimal importoCauzione =null;
        if (soggettoForm.getMisura().getImporto()!= null) 
        	importoCauzione=soggettoForm.getMisura().getImporto() ;
		  
        if (soggettoForm.getMisura().getDecisioneMisuraPersonaleIIGrado()!=null && soggettoForm.getMisura().getDecisioneMisuraPersonaleIIGrado().getId()!= null)
        idDecisione = soggettoForm.getMisura().getDecisioneMisuraPersonaleIIGrado().getId();
	            
	           
       //DECISIONE DI 2 GRADO RIGUARDARE
        
        
        // *** GESTIONE PRESCRIZIONI PROPOSTA-DECRETO DECISORIO I GRADO ***
   //     String cauzioneProp = soggettoForm.getMisura().getCauzioneProp();
        String noteCauz = soggettoForm.getMisura().getNote();
  //      String articolo67Prop = soggettoForm.getMisura().getArt67Prop();
       
        long idDecisioneLong =0L;
        if (idDecisione!=null) idDecisioneLong=Long.valueOf(idDecisione);
        
        String visibilita=soggettoForm.getVisibilitaIIGrado();
        
        //se la visibilità è N non deve aggiornare la personale e le prescrizione,
        //ma verificare esclusivamente cauzione e art 67 e aggiornare l'esito 2 e l'esito finale
        //idPrescrizioni sono le prescrizioni selezionate
        //se il campo è null non bisogna fare nulla;
        //mentre se sono presenti separate da; bisogna cancellare le vecchie presenti e fare l'insert delle nuovi
        //cauzione se è null non bisogna far null
        //altrimenti regolarsi in base al valore del campo inserendo opportunamente anche l'importo e le note
        //articolo67 se è null non bisogna far null
        //altrimenti regolarsi in base al valore del campo
    
          misuraDao.inserisciMisuraPersonaleDecIIGrado(vidProc, vidAtto, vidUffOwn, relAttoMisPers, idSoggInt, cauzione, noteCauz, articolo67,  vcodUtente, importoCauzione, idDecisioneLong, tipoProc,idPrescrizioni,visibilita);

		}

	//FINE 2GRADO

	 
	 /**
	  * inserisciMisuraPersonaleDecisorioIIIGrado
	  * @param vidProc
	  * @param tipoProc
	  * @param vidUffOwn
	  * @param idSoggInt
	  * @param vcodUtente
	  * @param soggettoForm
	  */
	 public void inserisciMisuraPersonaleDecisorioIIIGrado(long vidProc, long tipoProc, long vidUffOwn, long idSoggInt, String vcodUtente, SoggettoForm soggettoForm) {

			long vidAtto = 0L;

			TRelAttoMisPers relAttoMisPers = new TRelAttoMisPers();
			mapperBean.map(soggettoForm, relAttoMisPers);
			
			if (soggettoForm.getMisura() != null) {
				if (soggettoForm.getIdAtto() != null) {
					vidAtto = soggettoForm.getIdAtto();
				}
			} 
					         	            
            String idDecisione = null;
          
            if (soggettoForm.getMisura().getDecisioneMisuraPersonaleSentenzaCassazione()!=null && soggettoForm.getMisura().getDecisioneMisuraPersonaleSentenzaCassazione().getId()!= null)
                idDecisione = soggettoForm.getMisura().getDecisioneMisuraPersonaleSentenzaCassazione().getId();
           
            long idDecisioneLong =0L;
            if (idDecisione!=null) 
            	idDecisioneLong=Long.valueOf(idDecisione);
                 
            misuraDao.inserisciMisuraPersonaleDecIIIGrado(vidProc, vidAtto, vidUffOwn, idSoggInt,  vcodUtente, idDecisioneLong, tipoProc,soggettoForm.getDataRicezioneSentenza(),Long.parseLong(soggettoForm.getNumeAnnoSentenza()),Long.parseLong(soggettoForm.getNumeSentenza()));

		}
	 
	 public void salvaSentenzaOppDivietiQuestore(long vidProc, long tipoProc, long vidUffOwn, long idSoggInt, String vcodUtente, SoggettoForm soggettoForm, long vidAtto) {
                
         misuraDao.inserisciMisuraPersonaleDecIIIGrado(vidProc, vidAtto, vidUffOwn, idSoggInt,  vcodUtente, Long.parseLong(soggettoForm.getDecisioneErroreMaterialeIIIGrado().getId()), tipoProc,soggettoForm.getDataRicezioneSentenza(),Long.parseLong(soggettoForm.getNumeAnnoSentenza()),Long.parseLong(soggettoForm.getNumeSentenza()));

		}
	 
	 public BigDecimal getImportoCauzione(long vidProc, long vidAtto, Integer vidUffOwn){
		BigDecimal importo = null;
		importo = misuraDao.getImportoCauzione(vidProc, vidAtto, vidUffOwn);
		return importo;
	 }

}
