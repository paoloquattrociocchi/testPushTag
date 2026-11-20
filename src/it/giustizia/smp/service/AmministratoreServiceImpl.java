package it.giustizia.smp.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.giustizia.smp.dao.AmministratoreDao;
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
import it.giustizia.smp.model.TAnUfficio;
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
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelProcAttoAmm;
import it.giustizia.smp.model.TRelProcBeneAttoAmm;
import it.giustizia.smp.model.TRelProcProc;
import it.giustizia.smp.model.TRelProcProcId;
import it.giustizia.smp.utility.Constants;

@Transactional(rollbackFor = Exception.class)
public class AmministratoreServiceImpl implements AmministratoreService {
	
	private AmministratoreDao amministratoreDao;
	
	public AmministratoreDao getAmministratoreDao() {
		return amministratoreDao;
	}

	public void setAmministratoreDao(AmministratoreDao amministratoreDao) {
		this.amministratoreDao = amministratoreDao;
	}

	@Autowired
	private BeneService beneService;

	@Autowired
	private AttoService attoService;	
	
	@Autowired
	private CommonService commonService;	
	
	@Autowired
	private ProcedimentoService procedimentoService;
	
	@Override
	public void copiaAmministratori(int idUfficio, long idProcedimento, long idAtto, String codiceUtente, String nomeMetodo) {
		
		amministratoreDao.copiaAmministratori(idUfficio, idProcedimento, idAtto, codiceUtente, nomeMetodo);
				
	}
	
	@Override
	public void copiaAmministratoriIIGrado(int idUfficio, long idProcedimento, long idAtto, String codiceUtente, String nomeMetodo) {
		
		amministratoreDao.copiaAmministratoriIIGrado(idUfficio, idProcedimento, idAtto, codiceUtente, nomeMetodo);
				
	}	
	
	@Override
	public void sistemaDate(int idUfficio, long idProcedimento, long idAttoDecisorio, Date dataDeposito, int grado) {
		
		// Devo confrontare gli amministratori presenti sul DECISORIO con quelli presenti negli altri decreti.
		// listaBeniAmministratoriDecisorio contiene gli amministratori legati dal decreto decisorio di I grado
		// listaBeniAmministratoriAltriDecreti contiene tutti gli amministratori che non sono legati al DECISORIO
		List<TRelProcBeneAttoAmm> listaBeniAmministratoriAltriDecreti = new ArrayList<TRelProcBeneAttoAmm>();
		
		List<TRelProcBeneAttoAmm> listaBeniAmministratoriDecisorio = beneService.getAmministratoriBeni(idUfficio, idProcedimento, idAttoDecisorio, null, false, false);
		if (grado == Constants.PRIMO_GRADO) {
			// prendo tutti i beni amministrati di qualsiasi decreto prima del decreto decisorio
			listaBeniAmministratoriAltriDecreti = beneService.getAmministratoriBeni(idUfficio, idProcedimento, idAttoDecisorio, null, false, true);	
		} else {
			// prendo tutti i beni amministrati di tutti i decreti di sequestro di II
			List<AttoForm> listaAttiSequestri = attoService.getAttiByProcedimento(idProcedimento, 18l, "322");
			for (AttoForm attoForm : listaAttiSequestri) {
				listaBeniAmministratoriAltriDecreti.addAll(beneService.getAmministratoriBeni(idUfficio, idProcedimento, attoForm.getCodAtto(), null, false, false));				
			}
		}
				
		List<TRelProcAttoAmm> listaBeniAmministratoriCopertina = beneService.getAmministratoriCopertina(idUfficio, idProcedimento, null, null, false);
		
		// Devo prendere anche il fascicolo di gestione
		Long idProcedimentoGestione = null;
		HashMap fascGestione = commonService.getFascicoloGestione(idProcedimento, idUfficio, null);
		int idUfficioFascGest = 0;
		if(fascGestione!=null) {
			Long idProcGest = (Long) fascGestione.get("idProcGest");
			Long idUffRic = (Long) fascGestione.get("idUffRic");
			if(idProcGest!=null && idUffRic!=null && idProcGest>0 && idUffRic>0) {
				idProcedimentoGestione = idProcGest;
				idUfficioFascGest = idUffRic.intValue();
			}
		}	
		
		if(idProcedimentoGestione != null) {
			if(listaBeniAmministratoriAltriDecreti != null) {
				//listaBeniAmministratoriAltriDecreti.addAll(beneService.getAmministratoriBeni(idUfficio, idProcedimentoGestione, idAttoDecisorio, null, false, true));				
				listaBeniAmministratoriAltriDecreti.addAll(beneService.getAmministratoriBeni(idUfficioFascGest, idProcedimentoGestione, null, null, false, null));				
			} else {
				//listaBeniAmministratoriAltriDecreti = beneService.getAmministratoriBeni(idUfficio, idProcedimentoGestione, idAttoDecisorio, null, false, true);
				listaBeniAmministratoriAltriDecreti = beneService.getAmministratoriBeni(idUfficioFascGest, idProcedimentoGestione, null, null, false, null);				
			}
			if(listaBeniAmministratoriCopertina != null) {
				listaBeniAmministratoriCopertina.addAll(beneService.getAmministratoriCopertina(idUfficioFascGest, idProcedimentoGestione, null, null, false));
			} else {
				listaBeniAmministratoriCopertina = beneService.getAmministratoriCopertina(idUfficioFascGest, idProcedimentoGestione, null, null, false);				
			}			
		}		
		
		// Per adesso li appoggio in un nuovo arraylist;
		List<TRelProcBeneAttoAmm> listaRecordDaAggiornare = new ArrayList<TRelProcBeneAttoAmm>();
		
		// Devo impostare la data di FINE INCARICO a tutti i record che sono in listaBeniAmministratoriDecisorio ma non sono su listaBeniAmministratoriAltriDecreti (CASO 1 = cancellati sul decisorio)
		if (listaBeniAmministratoriAltriDecreti != null) {
			for (TRelProcBeneAttoAmm recordAltriDecreti : listaBeniAmministratoriAltriDecreti) {
				boolean trovato = false;
				if (listaBeniAmministratoriDecisorio != null) {
					for (TRelProcBeneAttoAmm recordDecisorio : listaBeniAmministratoriDecisorio) {

						// controllo solo su amministratore e bene
						if (recordAltriDecreti.getTAmministratori().getCodiKIdAmmin() == recordDecisorio.getTAmministratori().getCodiKIdAmmin() && 
							recordAltriDecreti.getTBene().getId().getCodiKIdBene() == recordDecisorio.getTBene().getId().getCodiKIdBene()) {
							trovato = true;
							
							// Pre imposto come nessuna operazione di aggiornamento da eseguire.
							recordAltriDecreti.setTipoAggiornamento(Constants.NESSUNA_OPERAZIONE);
							recordDecisorio.setTipoAggiornamento(Constants.NESSUNA_OPERAZIONE);
							
							// se data inizio == null inserisco nella nuova tabella caso 2 e metto come data inizio quella del decreto.
							//  118, 119, 123 devono essere esclusi in partenza
							
							// Cerco nella RelBeneAtto la misura legata al bene.
							// Se trovo almeno un 118, 119 o 123 il record e' ok , altrimenti devo chiudere le data di fine incarico							
							Set<TRelBeneAtto> setRelBeneAtto = recordDecisorio.getTAtto().getTRelBeneAttos();
							
							boolean esito = false;
														
							if (grado == Constants.PRIMO_GRADO) {
							    for (Iterator<TRelBeneAtto> it = setRelBeneAtto.iterator(); it.hasNext(); ) {
							    	TRelBeneAtto current = it.next();
							        if (current.getId().getCodiKIdAtto() == recordDecisorio.getTAtto().getId().getCodiKIdAtto() &&
							        	current.getId().getCodiKIdBene() == recordDecisorio.getTBene().getId().getCodiKIdBene() &&
							        	(current.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() == 118 || 
							        	 current.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() == 119 || 
							        	 current.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() == 123)) {
							            esito = true;
							        	break;						        	
							        }
							    }
							} else { // secondo grado
							    for (Iterator<TRelBeneAtto> it = setRelBeneAtto.iterator(); it.hasNext(); ) {
							    	TRelBeneAtto current = it.next();
							        if (current.getId().getCodiKIdAtto() == recordDecisorio.getTAtto().getId().getCodiKIdAtto() &&
							        	current.getId().getCodiKIdBene() == recordDecisorio.getTBene().getId().getCodiKIdBene() &&
							        	( ((current.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() == 118 || 
							        	   current.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() == 119 || 
							        	   current.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() == 123) && current.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito() == 128)) || current.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito() == 130)  {
							            esito = true;
							        	break;						        	
							        }
							    }								
							}
							
						    if(esito == false ) {
						    	
						    	// se non esiste almeno una misura con la confisca , devo chiudere le coppie amministratore/bene (sia sul decisorio che sul decreto)
					    		recordDecisorio.setDataFineIncarico(dataDeposito);
					    		recordDecisorio.setTipoAggiornamento(Constants.AGGIORNA_DATA_FINE_INCARICO_DECRETI_NON_CONFISCA);
								recordAltriDecreti.setDataFineIncarico(dataDeposito);
								recordAltriDecreti.setTipoAggiornamento(Constants.AGGIORNA_DATA_FINE_INCARICO_DECRETI_PRECEDENTI);						    								    	
						    }
							
						    // se data inizio incarico = null oppure 
						    // se data inizio incarico <> null e diverso da data inizio incarico del decreto 
						    // -> set della data inizio incarico con quella del decreto (caso 2)
//							if(recordDecisorio.getDataInizioIncarico() == null || (recordDecisorio.getDataInizioIncarico() != null && recordDecisorio.getDataInizioIncarico() != recordAltriDecreti.getDataInizioIncarico()) ) {  
							recordDecisorio.setDataInizioIncarico(recordAltriDecreti.getDataInizioIncarico());
							recordDecisorio.setTipoAggiornamento(Constants.AGGIORNA_DATA_INIZIO_INCARIO_DECRETO_ATTUALE);
							listaRecordDaAggiornare.add(recordDecisorio);
//							}
							
							break;							
						}
					}
					
					// Se non lo trovo devo impostare la data di fine incarico (controllo prima se era gia' impostata...)
					if(trovato == false) {							
						recordAltriDecreti.setDataFineIncarico(dataDeposito);
						recordAltriDecreti.setTipoAggiornamento(Constants.AGGIORNA_DATA_FINE_INCARICO_DECRETI_PRECEDENTI);
						listaRecordDaAggiornare.add(recordAltriDecreti);								
					}		
					
				} else { // nel decisorio di I non ci sono beni amministrati. Dopo la copia sono stati cancellati												
						recordAltriDecreti.setDataFineIncarico(dataDeposito);
						recordAltriDecreti.setTipoAggiornamento(Constants.AGGIORNA_DATA_FINE_INCARICO_DECRETI_PRECEDENTI);
						listaRecordDaAggiornare.add(recordAltriDecreti);							
				}
			}
		}	
		
		// Copio i rimanenti (le coppie amministratori/beni creati nel decisorio)
		if (listaBeniAmministratoriDecisorio != null) {
			for (TRelProcBeneAttoAmm recordDecisorio : listaBeniAmministratoriDecisorio) {
				if (recordDecisorio.getTipoAggiornamento() == Constants.AGGIORNA_DATA_INIZIO_INCARIO_NUOVO_AMMINISTRATORE) {
					
					// controllo se c'e' almeno una confisca , se non la trovo devo chiudere subito l'amministrazione del bene					
					Set<TRelBeneAtto> setRelBeneAtto = recordDecisorio.getTAtto().getTRelBeneAttos();
					
					boolean esito = false;
					
					if (grado == Constants.PRIMO_GRADO) {
					    for (Iterator<TRelBeneAtto> it = setRelBeneAtto.iterator(); it.hasNext(); ) {
					    	TRelBeneAtto current = it.next();
					        if (current.getId().getCodiKIdAtto() == recordDecisorio.getTAtto().getId().getCodiKIdAtto() &&
					        	current.getId().getCodiKIdBene() == recordDecisorio.getTBene().getId().getCodiKIdBene() &&
					        	(current.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() == 118 || 
					        	 current.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() == 119 || 
					        	 current.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() == 123)) {
					            esito = true;
					        	break;						        	
					        }
					    }						
					} else { // secondo grado
					    for (Iterator<TRelBeneAtto> it = setRelBeneAtto.iterator(); it.hasNext(); ) {
					    	TRelBeneAtto current = it.next();
					        if (current.getId().getCodiKIdAtto() == recordDecisorio.getTAtto().getId().getCodiKIdAtto() &&
					        	current.getId().getCodiKIdBene() == recordDecisorio.getTBene().getId().getCodiKIdBene() &&
					        	( ((current.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() == 118 || 
					        	   current.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() == 119 || 
					        	   current.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() == 123) && current.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito() == 128)) || current.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito() == 130)  {
					            esito = true;
					        	break;						        	
					        }
					    }						
					}

				    if(esito == false ) {
				    	recordDecisorio.setDataFineIncarico(dataDeposito);				    	
				    }
				    		
				    if (recordDecisorio.getDataInizioIncarico() == null) {
				    	recordDecisorio.setDataInizioIncarico(dataDeposito);
				    }
				    
					listaRecordDaAggiornare.add(recordDecisorio);			
				}
			}
		}
		
		// Devo controllare se chiudere eventuali record su T_REL_PROC_ATTO_AMM
		// Un record su T_REL_PROC_ATTO_AMM deve essere chiuso se tutti i beni amministrati da un amministratore hanno la data di fine incarico
		// Il controllo va fatto sia su listaBeniAmministratoriAltriDecreti che su listaBeniAmministratoriDecisorio 
		
		if (listaBeniAmministratoriCopertina != null) {		
			for(TRelProcAttoAmm recordRelProcAttoAmm : listaBeniAmministratoriCopertina) {
				boolean daChiudere = true;
				if (listaBeniAmministratoriAltriDecreti != null) {
					for(TRelProcBeneAttoAmm recordRelProcBeneAttoAmm : listaBeniAmministratoriAltriDecreti) {
						if(recordRelProcAttoAmm.getId().getCodiKIdAmmin() == recordRelProcBeneAttoAmm.getId().getCodiKIdAmmin() ) {
							if(recordRelProcBeneAttoAmm.getDataFineIncarico() == null) {
								daChiudere = false;
								break;
							}
						}
					}					
				}
				
				if(daChiudere == true) {
					if (listaBeniAmministratoriDecisorio != null) {
						for(TRelProcBeneAttoAmm recordRelProcBeneAttoAmm : listaBeniAmministratoriDecisorio) {
							if(recordRelProcAttoAmm.getId().getCodiKIdAmmin() == recordRelProcBeneAttoAmm.getId().getCodiKIdAmmin() ) {
								if(recordRelProcBeneAttoAmm.getDataFineIncarico() == null) {
									daChiudere = false;
									break;
								}
							}
						}					
					}					
				}						
				
				if(daChiudere == true) {
					recordRelProcAttoAmm.setDataFineIncarico(dataDeposito);
				}				
				
			}
		}
	
		ArrayList<TRelProcAttoAmm> listaRecordDaCancellare = new ArrayList<TRelProcAttoAmm>();
		
		if (listaBeniAmministratoriCopertina != null) {
			for (TRelProcAttoAmm recordRelProcAttoAmm : listaBeniAmministratoriCopertina) {		
				boolean daCancellare = true;
				if(recordRelProcAttoAmm.getTAtto().getId().getCodiKIdAtto() == idAttoDecisorio) {
					if (listaBeniAmministratoriDecisorio != null) { 
						for(TRelProcBeneAttoAmm recordRelProcBeneAttoAmm : listaBeniAmministratoriDecisorio) {
							if(recordRelProcAttoAmm.getTAmministratori().getCodiKIdAmmin() == recordRelProcBeneAttoAmm.getTAmministratori().getCodiKIdAmmin() ) {
									daCancellare = false;
									break;
								}
							}
					}
									
					if(daCancellare == true) {		
						listaRecordDaCancellare.add(recordRelProcAttoAmm);
					}	
				}
			}				
		}
		
		amministratoreDao.aggiornaRecords(listaRecordDaCancellare);
	}

	@Override
	public void sistemaDateIIGrado(int idUfficio, long idProcedimento, long idAttoDecisorio, Date dataDeposito, int grado) {
		
		/*chiusura dell'amministrazione (su tutti i record su cui è aperta) AL DECII
		 * 1) seq (amm) + dec conf + ricorso -> DECII
		 * 	- confisca -> l'amm resta aperta
		 *  - rigetta -> amm chiusa
		 * 2) seq (amm) + dec rig + ricorso -> decII
		 * 		a) add amministratore
	     * 			- confisca -> apre una nuova relazione
		 * 			- rigetta -> apre e chiude la relazione
		 *  	b) NO amm -> non fa nulla nè conf nè rig
		 * 	3) seq + dec conf + ricorso -> DECII
		 * 		a) add amministratore
	     * 			- confisca -> apre una nuova relazione
		 * 			- rigetta -> apre e chiude la relazione
		 *  	b) NO amm -> non fa nulla nè conf nè rig
		 *  4) seq + dec conf + ricorso + seqII (amm) -> DECII
		 *  	a) elimina amm
		 *  		- confisca e rigetta -> chiude la relazione
		 *  	b) lascia amm (o modifica aggiungendo??)
		 *  		- confisca -> non faccio niente
		 *  		- rigetta -> chiudo le relazioni (comprese quelle eventualmente aggiunte ex-novo)
		 *  5) seq + dec conf + ricorso + seqII -> DECII -> uguale al caso 3
		 *  	
		 */
		
		/*
		 * - verifica esito dec II (t_rel_bene_atto)
		 * se la relaz bene/atto/proc/owner = 1
		 * - il bene ha una relaz bene/amm esistente in atti II (escl ricorso)?
		 * 		si: 
				 * 	- se rigetta -> chiude relazioni
				 * 	- se confisca
				 *			- era presente in seqII 
				 *				-si: è presente in decII? 
				 *					si -> lascia aperta la relazione 
				 *					no -> chiude relazioni
				 *				- no: è presente in decII?  -> non faccio niente
		 *	     no: non faccio niente	
		 *	se la relaz bene/atto/proc/owner > 1
		 *	- verifica tipo e esito
		 *		= 1 
		 *		> 1
		 */

		// GC 10/03/2021: implementazione algoritmo di chiusura relazioni
		List<TRelProcBeneAttoAmm> listaBeniAmministratiIIgrado = new ArrayList<TRelProcBeneAttoAmm>();
		List<TRelProcBeneAttoAmm> listaAllBeniAmministrati = new ArrayList<TRelProcBeneAttoAmm>();
		List<Long> listaIdBeniAmmII = new ArrayList<Long>();
		List<Long> listaIdBeniAmmSeqII = new ArrayList<Long>();
		List<Long> listaIdBeniAmmDecII = new ArrayList<Long>();
		List<TRelProcProc> listaAllFasc =  new ArrayList<TRelProcProc>();
		

		// VARIABILE CHE CONTIENE TUTTI I FASCICOLI A ME COLLEGATI CHE AGGIORNERO
		ArrayList<Long> strIdFascicoli = new ArrayList<Long>();
		HashMap fascGestione;

		List<TRelBeneAtto> listaEsiti = beneService.getListaRelBeneAtto(idProcedimento, idAttoDecisorio, idUfficio, 0,0);
		List<TRelProcProc> listaFascII = new ArrayList<TRelProcProc>(); 
		List<TRelProcProc> listaFascI = new ArrayList<TRelProcProc>();
		TRelProcProc tempTPP = new TRelProcProc();
		TAnUfficio tempUff = new TAnUfficio();
		TRelProcProcId tempPPId = new TRelProcProcId();
		
		// aggiungo tutti i beni amministrati (sia di I che di II) collegati al mio
		// fascicolo di II
		listaBeniAmministratiIIgrado
				.addAll(beneService.getAmministratoriBeni(idUfficio, idProcedimento, null, null, false, null));
		strIdFascicoli.add(idProcedimento);
		
		// aggiungo il mio fascicolo di gestione
		fascGestione = commonService.getFascicoloGestione(idProcedimento, idUfficio, null);
		if (fascGestione != null) {
			Long idProcGest = (Long) fascGestione.get("idProcGest");
			Long idUffRic = (Long) fascGestione.get("idUffRic");
			if (idProcGest != null && idUffRic != null && idProcGest > 0 && idUffRic > 0) {
				strIdFascicoli.add(idProcGest);
				//P.Q 11/03/2021 - MEV 2020/048: Creo un record falso di T_procProc per averlo nella lista dei fascicoli
				tempUff.setCodiKIdUfficio(idUffRic.intValue());
				tempTPP.setTAnUfficio(tempUff);
				tempPPId.setCodiKIdProc2(idProcGest);
				tempTPP.setId(tempPPId);
				listaAllFasc.add(tempTPP);
			}
		}
		
		// Aggiungo all'elenco fascicoli tutti i fascicoli di I a me collegati
		listaFascI = procedimentoService.getListaProcedimentiCdaTrib(idProcedimento, idUfficio);
		
		if (listaFascI != null && listaFascI.size() > 0 ) {
			Iterator<TRelProcProc> iter = listaFascI.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				TRelProcProc proc = (TRelProcProc) map.get("rel");
				listaAllFasc.add(proc);
				
				// aggiungo alla collection di tutti i beni amministrati tra I e II
				listaAllBeniAmministrati
						.addAll(beneService.getAmministratoriBeni(proc.getTAnUfficio().getCodiKIdUfficio(),
								proc.getId().getCodiKIdProc2(), null, null, false, null));

				// aggiungo id fascicoli I
				strIdFascicoli.add(proc.getId().getCodiKIdProc2());

				// Devo prendere anche il fascicolo di gestione
				fascGestione = commonService.getFascicoloGestione(proc.getId().getCodiKIdProc2(),
						proc.getTAnUfficio().getCodiKIdUfficio(), null);
				if (fascGestione != null) {
					Long idProcGest = (Long) fascGestione.get("idProcGest");
					Long idUffRic = (Long) fascGestione.get("idUffRic");
					if (idProcGest != null && idUffRic != null && idProcGest > 0 && idUffRic > 0) {
						strIdFascicoli.add(idProcGest);
						//P.Q 11/03/2021 - MEV 2020/048: Creo un record falso di T_procProc per averlo nella lista dei fascicoli
						tempUff.setCodiKIdUfficio(idUffRic.intValue());
						tempTPP.setTAnUfficio(tempUff);
						tempPPId.setCodiKIdProc2(idProcGest);
						tempTPP.setId(tempPPId);
						listaAllFasc.add(tempTPP);
					}
				}
			}
		}
		
		// Aggiungo all'elenco fascicoli tutti i fascicoli di II a me collegati
		listaFascII = procedimentoService.getListaProcedimentiTribCda(idProcedimento, idUfficio);
		if (listaFascII != null && listaFascII.size() > 0 ) {
			Iterator<TRelProcProc> iter = listaFascII.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				TRelProcProc proc = (TRelProcProc) map.get("rel");
				listaAllFasc.add(proc);
				
				// aggiungo alla collection di tutti i beni amministrati tra I e II
				listaBeniAmministratiIIgrado
						.addAll(beneService.getAmministratoriBeni(proc.getTAnUfficio().getCodiKIdUfficio(),
								proc.getId().getCodiKIdProc2(), null, null, false, null));

				strIdFascicoli.add(proc.getId().getCodiKIdProc2());

				// Devo prendere anche il fascicolo di gestione
				fascGestione = commonService.getFascicoloGestione(proc.getId().getCodiKIdProc2(),
						proc.getTAnUfficio().getCodiKIdUfficio(), null);
				if (fascGestione != null) {
					Long idProcGest = (Long) fascGestione.get("idProcGest");
					Long idUffRic = (Long) fascGestione.get("idUffRic");
					if (idProcGest != null && idUffRic != null && idProcGest > 0 && idUffRic > 0) {
						strIdFascicoli.add(idProcGest);
						//P.Q 11/03/2021 - MEV 2020/048: Creo un record falso di T_procProc per averlo nella lista dei fascicoli
						tempUff.setCodiKIdUfficio(idUffRic.intValue());
						tempTPP.setTAnUfficio(tempUff);
						tempPPId.setCodiKIdProc2(idProcGest);
						tempTPP.setId(tempPPId);
						listaAllFasc.add(tempTPP);
					}
				}
				// Aggiungo all'elenco fascicoli tutti i fascicoli di I a me collegati
				listaFascI = procedimentoService.getListaProcedimentiCdaTrib(proc.getId().getCodiKIdProc2(), proc.getTAnUfficio().getCodiKIdUfficio());
				if (listaFascI != null && listaFascI.size() > 0 ) {
					Iterator<TRelProcProc> iterI = listaFascI.iterator();
					while (iterI.hasNext()) {
						Map mapI = (Map) iterI.next();
						TRelProcProc procI = (TRelProcProc) mapI.get("rel");
						listaAllFasc.add(procI);
						
						// aggiungo alla collection di tutti i beni amministrati tra I e II
						listaAllBeniAmministrati
								.addAll(beneService.getAmministratoriBeni(procI.getTAnUfficio().getCodiKIdUfficio(),
										procI.getId().getCodiKIdProc2(), null, null, false, null));

						strIdFascicoli.add(procI.getId().getCodiKIdProc2());

						// Devo prendere anche il fascicolo di gestione
						fascGestione = commonService.getFascicoloGestione(procI.getId().getCodiKIdProc2(),
								procI.getTAnUfficio().getCodiKIdUfficio(), null);
						if (fascGestione != null) {
							Long idProcGest = (Long) fascGestione.get("idProcGest");
							Long idUffRic = (Long) fascGestione.get("idUffRic");
							if (idProcGest != null && idUffRic != null && idProcGest > 0 && idUffRic > 0) {
								strIdFascicoli.add(idProcGest);
								//P.Q. 11/03/2021 - MEV 2020/048: Creo un record falso di T_procProc per averlo nella lista dei fascicoli
								tempUff.setCodiKIdUfficio(idUffRic.intValue());
								tempTPP.setTAnUfficio(tempUff);
								tempPPId.setCodiKIdProc2(idProcGest);
								tempTPP.setId(tempPPId);
								listaAllFasc.add(tempTPP);
							}
						}
					}
				}
			}
		}
		
		// aggiungo alla collection di tutti i beni amministrati tra I e II
		listaAllBeniAmministrati.addAll(listaBeniAmministratiIIgrado);
				
		// salvo tutti gli idBeneAmministrati in una lista apposita per i confronti
		for (TRelProcBeneAttoAmm recBeneAmm : listaBeniAmministratiIIgrado) {
			listaIdBeniAmmII.add(recBeneAmm.getId().getCodiKIdBene());

			// popolo la lista con i soli idbene legati a seqII
			if (recBeneAmm.getTAtto().getTTpCatAtto().getCodiKIdCatAtto() == Integer
					.parseInt(Constants.DECRETO_SEQUESTRO_ORDINARIO_II_GRADO)) {
				listaIdBeniAmmSeqII.add(recBeneAmm.getId().getCodiKIdBene());
			}
			// popolo la lista con i soli idbene legati a DecII
			if (recBeneAmm.getTAtto().getTTpCatAtto().getCodiKIdCatAtto() == Integer
					.parseInt(Constants.DECRETO_DECISORIO_II_GRADO)) {
				listaIdBeniAmmDecII.add(recBeneAmm.getId().getCodiKIdBene());
			}
		}

		// verifica esiti dec II
		for (TRelBeneAtto recordEsito : listaEsiti) {
			// todo: se la relaz bene/atto/proc/owner = 1
			// il bene ha una relaz bene/amm esistente in atti II (escl ricorso)?
			if (listaIdBeniAmmII.contains(recordEsito.getId().getCodiKIdBene())) {
				// caso di esiti di confisca
				if (((recordEsito.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() == 118
						|| recordEsito.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() == 119
						|| recordEsito.getTTpEsitoByCodiFkIdEsito1().getCodiKIdEsito() == 123)
						&& recordEsito.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito() == 128)
						|| recordEsito.getTTpEsitoByCodiFkIdEsito2().getCodiKIdEsito() == 130) {

					// era presente in seqII?
					if (listaIdBeniAmmSeqII.contains(recordEsito.getId().getCodiKIdBene())) {
						// si: è presente in decII?
						if (listaIdBeniAmmDecII.contains(recordEsito.getId().getCodiKIdBene())) {
							// si -> lascia aperta la relazione
							continue;
						} else {
							// no -> chiude relazioni
							aggiornaBeneAmministrato(strIdFascicoli, recordEsito.getId().getCodiKIdBene(), dataDeposito);
						}
					} else {
						// no -> non faccio niente
						// non controllo il decII perchè ad ogni modo, anche se presente, devo uscire senza fare niente
						continue;
					}
				} else {
					// caso di esiti di rigetta -> chiude le relazioni
					aggiornaBeneAmministrato(strIdFascicoli, recordEsito.getId().getCodiKIdBene(), dataDeposito);
				}
			} else {
				// no: non faccio niente
				continue;
			}
			// todo: se la relaz bene/atto/proc/owner > 1
		}
		
	
		// P.Q 11/03/2021 - MEV 2020/048: gestione aggiornamento amministratori copertina
		// Lista amministratori collegati al mio fascicolo di II
		List<TRelProcAttoAmm> listaBeniAmministratoriCopertina = new ArrayList<TRelProcAttoAmm>();
		listaBeniAmministratoriCopertina.addAll(listaAmministratoriCopertina(idUfficio, idProcedimento, null, null, false));
		
		if (listaAllFasc != null && listaAllFasc.size() > 0) {
			for (TRelProcProc rPP : listaAllFasc) {
				listaBeniAmministratoriCopertina.addAll(listaAmministratoriCopertina(rPP.getTAnUfficio().getCodiKIdUfficio(), rPP.getId().getCodiKIdProc2(), null, null, false));
			}
		}
		
		for(TRelProcAttoAmm recordRelProcAttoAmm : listaBeniAmministratoriCopertina) {
			boolean daChiudere = true;
			if (listaAllBeniAmministrati != null) {
				for(TRelProcBeneAttoAmm recordRelProcBeneAttoAmm : listaAllBeniAmministrati) {
					if(recordRelProcAttoAmm.getId().getCodiKIdAmmin() == recordRelProcBeneAttoAmm.getId().getCodiKIdAmmin() ) {
						if(recordRelProcBeneAttoAmm.getDataFineIncarico() == null) {
							daChiudere = false;
							break;
						}
					}
				}					
			}
			if(daChiudere == true) {
				recordRelProcAttoAmm.setDataFineIncarico(dataDeposito);
			}				
		}
		// aggiorno i record atrraverso hibernate
		amministratoreDao.aggiornaRecords();
	}

	@Override
	public List<TRelProcAttoAmm> listaAmministratoriCopertina(int idUffOwn, Long idProcedimento, Long idAtto, Long idAmministratore, boolean escludiAgenzia) {
		return beneService.getAmministratoriCopertina(idUffOwn, idProcedimento, idAtto, idAmministratore, escludiAgenzia);
	}

	@Override
	public List<TRelProcBeneAttoAmm> listaAmministratoriBeni(int idUffOwn, Long idProcedimento, Long idAtto, Long idAmministratore, boolean escludiAgenzia, boolean escludiAtto) {
		return beneService.getAmministratoriBeni(idUffOwn, idProcedimento, idAtto, idAmministratore, escludiAgenzia, escludiAtto);
	}

	@Override
	public boolean visualizzaMessaggioAmministratoriPresenti(int idUfficio, long idProcedimento, long idAtto, int grado) {
		
		List<TRelProcBeneAttoAmm> listaBeniAmministratoriDecisorio = beneService.getAmministratoriBeni(idUfficio, idProcedimento, idAtto, null, false, false);
		
		List<TRelProcBeneAttoAmm> listaBeniAmministratoriAltriDecreti = new ArrayList<TRelProcBeneAttoAmm>(); 
		
		if (grado == 1) {
			listaBeniAmministratoriAltriDecreti = beneService.getAmministratoriBeni(idUfficio, idProcedimento, idAtto, null, false, true);	
		} else { // secondo grado , prendo tutti gli amministratori di tutti i decreti di II grado
			List<AttoForm> listaAttiSequestri = attoService.getAttiByProcedimento(idProcedimento, 18l, "322");
			for (AttoForm attoForm : listaAttiSequestri) {
				listaBeniAmministratoriAltriDecreti.addAll(beneService.getAmministratoriBeni(idUfficio, idProcedimento, attoForm.getCodAtto(), null, false, false));	
			}						
		}		
		
		// Controllo se numericamente sono diversi
		if (listaBeniAmministratoriAltriDecreti.size() != listaBeniAmministratoriDecisorio.size() || listaBeniAmministratoriAltriDecreti.isEmpty() && listaBeniAmministratoriDecisorio.isEmpty() ) {
			return false;
		} else { // se numericamente sono uguali devo vedere se tutti i record degli amministratori del decisorio sono gli stessi degli altri decreti
			for(TRelProcBeneAttoAmm recordAltriDecreti : listaBeniAmministratoriAltriDecreti) {
				boolean trovato = false;
				for(TRelProcBeneAttoAmm recordDecisorio : listaBeniAmministratoriDecisorio) {
					if(recordAltriDecreti.getId().getCodiKIdAmmin() == recordDecisorio.getId().getCodiKIdAmmin() && 
					   recordAltriDecreti.getId().getCodiKIdBene() == recordDecisorio.getId().getCodiKIdBene() ) {
						trovato = true;
						break;
					}
				}
				
				if(trovato == false) {
					return false;
				}
			}
		}
		
		return true;
	}


	private void aggiornaBeneAmministrato(final ArrayList<Long> elencoFascicoli, final Long idBene, final Date dataFineIncarico) {
		// ma le eccezioni?? dove le gestisco?
		amministratoreDao.updateBeneAmministrato(elencoFascicoli, idBene, dataFineIncarico);
	}
	
	
}