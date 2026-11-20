package it.giustizia.smp.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.giustizia.smp.controller.procedimento.BeneController;
import it.giustizia.smp.dao.BeneDao;
import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.BeneSoggettoForm;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.MisuraForm;
import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.SoggettoForm;
import it.giustizia.smp.model.TRelBeneAtto;
import it.giustizia.smp.model.TRelProcBeneTit;
import it.giustizia.smp.service.BeneService;
import it.giustizia.smp.service.CommonService;
import it.giustizia.smp.service.MisuraService;
import it.giustizia.smp.utility.ComparatorUtil;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Constants.TipologieBeni;
import it.giustizia.smp.utility.Frazione;
import it.giustizia.smp.utility.Message;
import it.giustizia.smp.utility.SottoCategoriaUtils;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.utility.ValidationUtil;

@Component("beneValidation")
public class BeneValidation extends BaseValidation implements Validator {

	@Autowired
	private BeneService		beneService;
	@Autowired
	private MisuraService	misuraService;
	@Autowired
	private BeneDao			beneDao;

	@Autowired
	private CommonService commonService;

	public void setBeneDao(BeneDao beneDao) {

		this.beneDao = beneDao;
	}

	public void setMisuraService(MisuraService misuraService) {

		this.misuraService = misuraService;
	}

	public boolean supports(Class<?> clazz) {

		return BeneController.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

		// serve per implementare validator
	}

	public void validateBeneNonAssociatoTitoliDiritto(List<BeneSoggettoForm> beniSoggetti, HttpServletRequest request, BindingResult result) {

		if ((beniSoggetti == null) || beniSoggetti.isEmpty()) {
			result.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "bene.senza.titolo.diritto", null, false);
		}
	}

	public void validateCampiObbligatoriInserimentoOModificaTitoloDiDiritto(BeneSoggettoForm beneSoggettoForm, Errors errors, HttpServletRequest request,AttoForm attoForm) {

		if (beneSoggettoForm.getQuotaInteresse()==null && beneSoggettoForm.getNumeratoreFrazioneQuota()==null && beneSoggettoForm.getDenominatoreFrazioneQuota()==null) {
			super.setMessage(Constants.MESSAGE, "benesoggetto.quota.error.obbligatoria", null, false);
			errors.reject(Constants.MESSAGE);
			return;
		}

		if (Integer.valueOf(beneSoggettoForm.getIdTipoInteresse()).equals(Integer.valueOf(Constants.NO_ITEM_SELECTED))) {
			super.setMessage(Constants.MESSAGE, "benesoggetto.tipoInteresse.error.obbligatorio", null, false);
			errors.reject(Constants.MESSAGE);
			return;
		}
		boolean isIstanzaMisurePatrimoniali = request.getParameter("isIstanzaModificaMisurePatrimoniali") != null ? Boolean.valueOf(request.getParameter("isIstanzaModificaMisurePatrimoniali")) : false;

		if (((attoForm == null) ||
				((attoForm!=null)
						&& (!attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_DECISORIO_I_GRADO))
						&& (!attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_DECISORIO_II_GRADO))))
				&& !isIstanzaMisurePatrimoniali) {

			if ((beneSoggettoForm.getSoggetto() == null) || (beneSoggettoForm.getSoggetto().getMisura() == null) || !Utility.isDefinito(beneSoggettoForm.getSoggetto().getMisura().getTipoMisura())) {
				super.setMessage(Constants.MESSAGE, "benesoggetto.tipoMisura.error.obbligatorio", null, false);
				errors.reject(Constants.MESSAGE);
				return;
			}
			if ((beneSoggettoForm.getSoggetto() == null) || !Utility.isDefinito(beneSoggettoForm.getSoggetto().getNormativa())) {
				super.setMessage(Constants.MESSAGE, "benesoggetto.normativa.error.obbligatorio", null, false);
				errors.reject(Constants.MESSAGE);
				return;
			}
		}
	}

	public String validateCodiceAbi(String codiceAbi, BindingResult result) {

		String descEnte = beneService.getDescEnte(codiceAbi);
		if (descEnte == null) {
			result.rejectValue("codiceAbi", "bene.finanziario.abi.errato");
			return null;
		} else return descEnte;
	}

	public void validateCodiceAbiDescEnte(String codiceAbi, String descEnteAttuale, String descEnteDb, BindingResult result) {

		// Controllo la congruenza della Descrizione Ente
		if (!descEnteAttuale.equals(descEnteDb)) {
			super.setMessage(Constants.MESSAGE, "bene.finanziario.abi.descrizioneente.errore", null, false);
			result.reject(Constants.MESSAGE);
		}
		return;
	}

	public boolean validateCodiceIban(BeneForm beneForm, BindingResult result) {

		boolean errore = false;
		String codiIban = beneForm.getCodiceIban();
		String paese="";
		if(codiIban!=null && codiIban.trim().length()>2){
			paese = codiIban.substring(0, 2);

			//Effettuo il controllo dell'IBAN solo per l'Italia
			if (Constants.ITALIA_SIGLA.equalsIgnoreCase(paese)){
				if (codiIban.length() < 27) {
					result.rejectValue("codiceIban", "bene.finanziario.iban.errore");
					errore = true;
				} else {
					paese = codiIban.substring(0, 2);
					String contr = codiIban.substring(2, 4);
					String cin = codiIban.substring(4, 5);
					String abi = codiIban.substring(5, 10);
					String cab = codiIban.substring(10, 15);
					String conto = codiIban.substring(15, 27);
					// Se il codice del Paese o il Cin non sono lettere ...
					if (!paese.matches("\\p{Upper}{2}") || !cin.matches("\\p{Upper}{1}")) {
						result.rejectValue("codiceIban", "bene.finanziario.iban.errore");
						errore = true;
					}
					// Se codice controlli, ABI, CAB o Numero del conto
					// non sono numerici ...
					else
						if (!contr.matches("\\d{2}") || !abi.matches("\\d{5}") || !cab.matches("\\d{5}") || !conto.matches("\\d{12}")) {
							result.rejectValue("codiceIban", "bene.finanziario.iban.errore");
							errore = true;
						}
				}
			}else if(codiIban.length() > 34){
				result.rejectValue("codiceIban", "bene.finanziario.iban.errore");
				errore = true;
			}
		}
		return errore;
	}

	public void validateConfermaModificaTitoloDiDiritto(BeneSoggettoForm beneSoggettoForm, BindingResult result, HttpServletRequest request,AttoForm attoForm) {

		if (beneSoggettoForm.getQuotaInteresse() != null && ((beneSoggettoForm.getQuotaInteresse() <= 0) || (beneSoggettoForm.getQuotaInteresse() > 100))) {
			super.setMessage(Constants.MESSAGE, "soggetto.titolididiritto.error.quotaIncorretta", null, false);
			result.reject(Constants.MESSAGE);
			return;
		}
		int idTipoInteresse = beneSoggettoForm.getIdTipoInteresse();
		if (idTipoInteresse == 0) {
			super.setMessage(Constants.MESSAGE, "soggetto.titolididiritto.error.tipoInteresseObbligatorio", null, false);
			result.reject(Constants.MESSAGE);
			return;
		}
		boolean isIstanzaMisurePatrimoniali = request.getParameter("isIstanzaModificaMisurePatrimoniali") != null ? Boolean.valueOf(request.getParameter("isIstanzaModificaMisurePatrimoniali")) : false;

		if (((attoForm == null) ||
				((attoForm!=null)
						&& (!attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_DECISORIO_I_GRADO))
						&& (!attoForm.getCategoriaAtto().getId().equals(Constants.DECRETO_DECISORIO_II_GRADO))))
				&& !isIstanzaMisurePatrimoniali) {
			MisuraForm misura = beneSoggettoForm.getSoggetto().getMisura();

			if (((misura == null) || (misura.getTipoMisura() == null) || (misura.getTipoMisura().getId() == null))) {
				super.setMessage(Constants.MESSAGE, "soggetto.titolididiritto.error.tipoMisuraObbligatorio", null, false);
				result.reject(Constants.MESSAGE);
				return;
			}
			ComboForm normativa = beneSoggettoForm.getSoggetto().getNormativa();
			if (((normativa == null) || (normativa.getId() == null))) {
				super.setMessage(Constants.MESSAGE, "error.soggetto.normativa.obbligatoria", null, false);
				result.reject(Constants.MESSAGE);
				return;
			}
		}
	}

	public void validateFrazioneCoerente(BeneSoggettoForm beneSoggettoForm, Errors errors, HttpServletRequest request) {

		if ((beneSoggettoForm.getNumeratoreFrazioneQuota() == null) && (beneSoggettoForm.getDenominatoreFrazioneQuota() == null)) {
			return;
		}
		if ((beneSoggettoForm.getNumeratoreFrazioneQuota() == null) && (beneSoggettoForm.getDenominatoreFrazioneQuota() != null)) {
			super.setMessage(Constants.MESSAGE, "benesoggetto.frazioneQuota.error.numeratoreObbligatorio", null, false);
			errors.reject(Constants.MESSAGE);
			return;
		}
		if ((beneSoggettoForm.getDenominatoreFrazioneQuota() == null) && (beneSoggettoForm.getNumeratoreFrazioneQuota() != null)) {
			super.setMessage(Constants.MESSAGE, "benesoggetto.frazioneQuota.error.denominatoreObbligatorio", null, false);
			errors.reject(Constants.MESSAGE);
			return;
		}
		if (beneSoggettoForm.getDenominatoreFrazioneQuota() == 0) {
			super.setMessage(Constants.MESSAGE, "benesoggetto.frazioneQuota.error.denominatoreNullo", null, false);
			errors.reject(Constants.MESSAGE);
			return;
		}
	}

	public void validateInserimentoBeneFinanziario_Polizza(BeneForm beneForm, BindingResult result) {
		
		if (StringUtils.isNotEmpty(beneForm.getNumeroPolizza()) && beneForm.getNumeroPolizza().length() > 10) {
			result.rejectValue("numeroPolizza", "Max", new Object[] { null, 10L }, null);
		}
		
		//F.C. REQ-STM-019-02
		if (beneForm.isEnteFittizio()) return;
		//F.C. REQ-STM-019-02
		
		if (StringUtils.isEmpty(beneForm.getDescOperFinanz().getId())) {
			result.rejectValue("descOperFinanz", "NotNull");
		}
		
	}
	
	public BeneForm checkEnteFittizio(BeneForm beneF,BindingResult result)
	
	{
		boolean isEnteFittizio = validateEnteFittizio(beneF, result);
		beneF.setEnteFittizio(isEnteFittizio);
		if (isEnteFittizio) {
			//dato che l'utente non ha selezionato nulla
			//associo il bene ad  un ente fittizio		
			
			beneF.setAbi(Constants.EnteFittizio.ABI);
			beneF.setCodiceAbi(Constants.EnteFittizio.ABI);
			ComboForm codiceCab = new ComboForm();		
			codiceCab.setId(Constants.EnteFittizio.CAB);			
			beneF.setCodiceCab(codiceCab );
			beneF.setDescrizioneEnte(Constants.EnteFittizio.DESCRIZIONE);
			beneF.setCodiceFiscale(Constants.EnteFittizio.CODICE_FISCALE);
			//beneF.setNumeroRegistrazione(Constants.EnteFittizio.NUMERO_REGISTRAZIONE);
			ComboForm provincia = new ComboForm();
			provincia.setDescrizione(Constants.EnteFittizio.PROVINCIA);
			beneF.setProvincia(provincia);
			beneF.setDescOperFinanz(
					new ComboForm(Constants.EnteFittizio.ID, Constants.EnteFittizio.DESCRIZIONE));
			

			
		}
		return beneF;
	}

	public String validateInserimentoBeneFinanziario_Denaro(BeneForm beneForm, BindingResult result) {

		String ritorno = "";
		// INIZIO CATEGORIA E SOTTOCATEGORIA OBBLIGATORI
		long categoria = 0;
		long sottocategoria = 0;
		if ((beneForm.getCategoria() != null) && (beneForm.getCategoria().getId() != null)) {
			categoria = Long.parseLong(beneForm.getCategoria().getId());
		}
		if ((beneForm.getSottocategoria() != null) && (beneForm.getSottocategoria().getId() != null)) {
			sottocategoria = Long.parseLong(beneForm.getSottocategoria().getId());
		}
		if (categoria < 1l) {
			result.rejectValue("categoria.id", "NotNull");
		}
		if (sottocategoria < 1l) {
			result.rejectValue("sottocategoria.id", "NotNull");
		}
		// FINE CATEGORIA E SOTTOCATEGORIA OBBLIGATORI
		// INIZIO VALIDAZIONE ABI, DESCRIZIONE ENTE e CAB
		boolean beneDefinito=true;
		//vado a verificare che sia un bene definito a livello di sottocategoria
		if (!result.hasErrors())
			//28/05/2021 tolto controllo se denaro non definito
			//deve fare comunque tutti i controlli se bene non definito
			//vedi jira SITMP-341 e REQ-STM-009-06
			//beneDefinito=beneDao.isDefinito(beneForm);

			// se è un bene definito vanno fatti i controlli
			if (beneDefinito){
				String abi = beneForm.getCodiceAbi();
				String abiPrec = beneForm.getAbi();
				String cab = null;
				String numConto = beneForm.getNumeroRegistrazione();
				String descEnte = beneForm.getDescOperFinanz().getDescrizione();
				String descEntePrec = beneForm.getDescrizioneEnte();
				boolean validIban = false;
				// VALIDAZIONE CODICE IBAN
				if (beneForm.getCodiceIban() != null) {
					boolean errore = validateCodiceIban(beneForm, result);
					if (!errore)
						validIban = true;// IBAN presente e valido
				}
				if (beneForm.getCodiceSwift() != null) {
					if(beneForm.getCodiceSwift().trim().length()!=8 && beneForm.getCodiceSwift().trim().length()!=11){
						ritorno = "newSwiftNotValid";
						result.rejectValue("codiceSwift", "bene.finanziario.swift.errore");
					}
				}
				// Prendo il Codice CAB, se presente
				if (beneForm.getCodiceCab() != null && beneForm.getCodiceCab().getId() != null) {
					if (!beneForm.getCodiceCab().getId().equals("0"))
						cab = beneForm.getCodiceCab().getId();
				}
				// Controllo l'ABI presente attualmente
				if (abi != null) {
					// Se è presente un ABI inserito in precedenza
					if (abiPrec != null) {
						// Se ho inserito un nuovo ABI
						if (!abi.equals(abiPrec)) {
							// Prendo la Descrizione Ente associata al nuovo ABI
							String descEnteDb = validateCodiceAbi(abi, result);
							if (descEnteDb == null) {
								// Ho inserito un nuovo ABI non valido
								// Devo annullare tutti gli altri campi, tranne l'ABI
								ritorno = "newAbiNotValid";
							} else {
								// Ho inserito un nuovo ABI valido, diverso dal
								// precedente
								validateCodiceAbiDescEnte(abi, descEntePrec, descEnteDb, result);
								if (result.hasErrors()) {
									// In caso di ABI diverso mi darà errore di non
									// congruenza
									// tra l'ABI e la Descrizioni Ente precedente
									ritorno = "newAbiValidDiffDescEnte";
								}
							}
						}
					}
					if (ritorno.equals("")) {
						// Se è presente l'ABI o non è stato modificato
						// Devono essere presenti anche Descrizione Ente e CAB
						if (descEnte == null) {
							result.rejectValue("descOperFinanz.id", "bene.finanziario.obbligatorio.abi");
							ritorno = "abiNotNulldescNull";
						}
					}
					if (ritorno.equals("")) {
						// P.Q. 28/06/2021 Inizio
						//if(! (categoria == 6 && sottocategoria == 107)) {
							if (cab == null)
								result.rejectValue("codiceCab.id", "bene.finanziario.obbligatorio.abi");
							// P.Q. MEV 2021/19 Operatore Economico Estero non ha il numero conto obbligatorio
							if (numConto == null && 
									!Constants.OperatoreEstero.DESCRIZIONE.equalsIgnoreCase(beneForm.getDescOperFinanz().getDescrizione()) &&
									!Constants.EnteFittizio.DESCRIZIONE.equalsIgnoreCase(beneForm.getDescOperFinanz().getDescrizione()))
								result.rejectValue("numeroRegistrazione", "bene.finanziario.obbligatorio.abi");
						//}
					}
				} else {
					//Se l'ABI non è presente
					//Deve essere presente l'IBAN

					if(beneForm.getSottocategoria() != null 
							&& !"83".equalsIgnoreCase(beneForm.getSottocategoria().getId()) && !"84".equalsIgnoreCase(beneForm.getSottocategoria().getId())
							&& !"85".equalsIgnoreCase(beneForm.getSottocategoria().getId()) && !"92".equalsIgnoreCase(beneForm.getSottocategoria().getId())
							&& !"93".equalsIgnoreCase(beneForm.getSottocategoria().getId()) && !"94".equalsIgnoreCase(beneForm.getSottocategoria().getId())
							&& !"98".equalsIgnoreCase(beneForm.getSottocategoria().getId()) && !"99".equalsIgnoreCase(beneForm.getSottocategoria().getId())) {

						//se il bene e' denaro e la sottocategoria e' CONTANTE o NON DEFINITO
						//verifico se l'utente vuole inserire un ente fittizio
						if ("6".equalsIgnoreCase(beneForm.getCategoria().getId()) && ("107".equalsIgnoreCase(beneForm.getSottocategoria().getId()) ||
								"35".equalsIgnoreCase(beneForm.getSottocategoria().getId()))) {
							    beneForm = checkEnteFittizio(beneForm,result);
							    if (beneForm.isEnteFittizio()) {
							    	//REQ-STM-019-02 se e' un bene di tipo denaro-contante 
							    	//se è un ente fittizio imposto il numero di registrazione 
							    	if ("35".equalsIgnoreCase(beneForm.getSottocategoria().getId()))
							    		beneForm.setNumeroRegistrazione(numConto);
							    	return ritorno; 
							    }
							    	
						}
							

						//REQ-STM-019-02  per i beni finanziari verifico se l'utente vuole inserire un ente fittizio
						//per i beni delle sotto-categorie diverse da Altro o Non-Definito 
						if ("4".equals(beneForm.getTipologia().getId()) && 
								(!"117".equals(beneForm.getSottocategoria().getId()) && 
								 !"99".equals(beneForm.getSottocategoria().getId())) ) {
							    beneForm = checkEnteFittizio(beneForm,result);
							    if (beneForm.isEnteFittizio()) {
							    	beneForm.setNumeroRegistrazione(numConto);
							    	return ritorno;
						}



						}



						if (beneForm.getCodiceIban() == null) {
							result.rejectValue("codiceAbi", "bene.finanziario.obbligatorio.abi.iban");
							ritorno = "abiNullDescNotNull";
						}
					}

					// Se non è presente l'ABI
					// Non deve essere presente la Descrizione Ente
					if (descEnte != null) {
						// Se presente, lancio errore e ripulisco i campi
						result.rejectValue("codiceAbi", "bene.finanziario.obbligatorio.descrizioneente");
						ritorno = "abiNullDescNotNull";
					}
					else {
						// Verifico gli altri campi in assenza di ABI e Descrizione Ente
						if (cab == null) {
							// Non sono presenti ABI, Descrizione Ente e CAB
							//if (!validIban) {
							// Se l'IBAN è vuoto o non valido
							// Non sono obbligatori ABI, CAB e Numero del Conto
							if (numConto != null) {
								// Se è presente il numero di Conto
								// ABI (Descrizione Ente) e CAB sono obbligatori
								result.rejectValue("codiceAbi", "bene.finanziario.obbligatorio.conto");
								result.rejectValue("codiceCab.id", "bene.finanziario.obbligatorio.conto");
							} 
							//}
						}
					}
				}

			}


		if(result.hasErrors()) {
			Message message = null;
			if(request.getAttribute(Constants.MESSAGE)!=null) {
				message = (Message)request.getAttribute(Constants.MESSAGE);
			}
			if(message==null || !message.getCode().equalsIgnoreCase("errors.AtLeastOne.required")) {
				super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
			}
		}
		// FINE VALIDAZIONE ABI, DESCRIZIONE ENTE e CAB
		return ritorno;
	}

	public void validateNumeroRegistrazione(BeneForm beneForm, BindingResult result, HttpServletRequest request) {
		String numReg = beneForm.getNumeroRegistrazione();
		if (numReg != null && !numReg.matches("[0-9]+")) {
			super.setMessage(Constants.MESSAGE, "bene.inserimento.errore.numeroregistrazione", null, false);
			result.reject(Constants.MESSAGE);
		}
	}
	/* GC 28/08/2024: TT#202407020117
		 * Modificata la chiamata per distinguere i beni finanziari, diventati alfanumerici, dagli altri
	 */
	public void validateNumeroRegistrazioneBeneFin(BeneForm beneForm, BindingResult result, HttpServletRequest request) {
		String numReg = beneForm.getNumeroRegistrazione();
		if (numReg != null && !numReg.matches("[a-zA-Z0-9]+")) {
			super.setMessage(Constants.MESSAGE, "bene.inserimento.errore.numeroregistrazioneBeneFin", null, false);
			result.reject(Constants.MESSAGE);
		}
	}
	
	//F.C. REQ-STM-019-02
	//Metodo che Controlla se la sub categoria sia uguale a ALTRO o NON DEFINITO
	private boolean checkSubCategoria(String subCategoriaId) {
		String matchSubCategoria1 = "ALTRO"; 
		String matchSubCategoria2 = "DEFINITO"; 
		
		
		if (subCategoriaId == null || "0".equals(subCategoriaId)) return false;
		ComboForm combo = commonService.getSottoCategoriaBene(subCategoriaId);
		
		if (combo == null) return false;
				
		String subCat = combo.getDescrizione();
		String[] sentence = subCat.split(" "); 
		for(String word: sentence) { 
			if(word.equals(matchSubCategoria1)) return true; 
			
			if(word.equals(matchSubCategoria2)) return true; 
		} 
		return false; 
	}
	
	public void validateNumeroRegistrazioneImporto(BeneForm beneForm, BindingResult result, HttpServletRequest request) {
		String numReg = beneForm.getNumeroRegistrazione();
		String importo = beneForm.getImporto();
		String importoPolizza = null;
		
		if (beneForm.getValutaEur() != null) importoPolizza = beneForm.getValutaEur().toString();
		
		String numPolizza = beneForm.getNumeroPolizza();
		if (numReg != null &&  !"0".equals(numReg)) {
			return;
		}
		
		if (numPolizza != null &&  !"0".equals(numPolizza)) {
			return;
		}
		
		if (importo != null) {
			return;
		}
		
		if (importoPolizza != null &&  !"0".equals(importoPolizza)){
			return;
		}
		
	
		
		if (beneForm.getDescrizioneEnte() == null && beneForm.getDescOperFinanz().getDescrizione() == null) {
				//F.C. REQ-STM-019-02
				//Verifico se la sub categoria sia uguale a ALTRO o NON DEFINITO
				if (checkSubCategoria(beneForm.getSottocategoria().getId()))
					return;
			
				//se non e' una polizza
				if (!"32".equals(beneForm.getCategoria().getId())) {
					if ( beneForm.getCodiceIban() == null) {
						if (!"6".equals(beneForm.getCategoria().getId())) {//se non e' un bene mobile denaro
							super.setMessage(Constants.MESSAGE, "bene.inserimento.errore.numeroregistrazione.importo", null, false);
						}
						else {//se  e' un bene mobile denaro
							super.setMessage(Constants.MESSAGE, "bene.inserimento.errore.numerolibretto.importo", null, false);
						}
						
						result.reject(Constants.MESSAGE);
					}	
				}
				else {//nel caso la categoria e' una polizza		
					//se non è una polizza gestita da banca
					if (!"123".equals(beneForm.getSottocategoria().getId())) {
						super.setMessage(Constants.MESSAGE, "bene.inserimento.errore.numeropolizza.importo", null, false);
						result.reject(Constants.MESSAGE);
					}
					else { //e' una polizza gestita da banca
						if ( beneForm.getCodiceIban() == null) {
							super.setMessage(Constants.MESSAGE, "bene.inserimento.errore.numeroregistrazione.importo", null, false);
							result.reject(Constants.MESSAGE);
						}
					}
				}
				
				
				return;
			
		}
		
	}
	
	
	// SITMP-204 Adeguamento applicativo dei campi che utilizzano T_BENE_FIN.DESC_REGISTRAZIONE - aggiunto controllo lato server sul numero polizza che sia numerico
	public void validateNumeroPolizza(BeneForm beneForm, BindingResult result, HttpServletRequest request) {
		String numPol = beneForm.getNumeroPolizza();
		if (numPol != null && !numPol.matches("[0-9]+")) {
			super.setMessage(Constants.MESSAGE, "bene.inserimento.errore.numeropolizza", null, false);
			result.reject(Constants.MESSAGE);
		}
	}

	public boolean validateEnteFittizio(BeneForm beneForm, BindingResult errors) {
		
		//FC. 23/06/2022 Ticket#20220623016 nel caso che l'operatore finanziario è null
		//il metodo esce valore di ritorno false.
		if (beneForm.getDescOperFinanz() == null) return false;

		if (Constants.EnteFittizio.DESCRIZIONE.equalsIgnoreCase(beneForm.getDescOperFinanz().getDescrizione()))
			return true;

		//ValidationUtil.required("modelloVeicolo", errors);
		//F.C. 09/11/2021 tolto controllo iban per validare se e' un ente fittizio
		//F.C. 21/12/2021 tolto controllo "numeroRegistrazione" per validare se è un ente fittizio
		if(ValidationUtil.checkAllEmpty(errors, "codiceAbi","codiceCab.descrizione","provincia.id","descOperFinanz.descrizione","descrizioneEnte")){
			return true;
		}
		return false;
	}

	public void validateInserimentoBeneNonDuplicato(BeneForm beneForm, BindingResult result, HttpServletRequest request) {

		boolean esistonoIdentici = beneService.esistonoIdentici(beneForm);
		if (esistonoIdentici) {
			super.setMessage(Constants.MESSAGE, "bene.inserimento.errore.duplicato", null, false);
			result.reject(Constants.MESSAGE);
		}
	}

	// ***************** BENE ****************//
	/**
	 * Validazione campi obbligatori inserimento o modifica di un bene
	 * @param beneForm
	 * @param result
	 */
	public void validateInserisciBene(BeneForm beneForm, BindingResult result) {

		long tipologia = 0;
		long categoria = 0;
		long sottocategoria = 0;
		if ((beneForm.getTipologia() != null) && (beneForm.getTipologia().getId() != null)) {
			tipologia = Long.parseLong(beneForm.getTipologia().getId());
		}
		if ((beneForm.getCategoria() != null) && (beneForm.getCategoria().getId() != null)) {
			categoria = Long.parseLong(beneForm.getCategoria().getId());
		}
		if ((beneForm.getSottocategoria() != null) && (beneForm.getSottocategoria().getId() != null)) {
			sottocategoria = Long.parseLong(beneForm.getSottocategoria().getId());
		}
		//		if (categoria < 1l) {
		//			result.rejectValue("categoria.id", "NotNull");
		//		}

		ValidationUtil.requiredComboForm(beneForm.getCategoria(), "categoria.id", result);


		if (ComparatorUtil.containsValue(tipologia, TipologieBeni.ID_TIPO_IMMOBILE, TipologieBeni.ID_TIPO_MOBILE_REGISTRATO, TipologieBeni.ID_TIPO_MOBILE)
				&& !Utility.isDefinito(beneForm.getSottocategoria())) {
			ValidationUtil.required("sottocategoria.id", result);
		}
		//Se ho come categoria società non definita escludo le validazioni
		if(ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_SOCIETA_NON_DEFINITA)){
			return;
		}
		//Se ho come Sottocategoria una non definita 
		if(sottocategoria>0 && SottoCategoriaUtils.isSottoCategoriaNonDefinita(sottocategoria)){
			if (ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_DENARO_CONTANTE) && ("107".equalsIgnoreCase(beneForm.getSottocategoria().getId()) ||
					"35".equalsIgnoreCase(beneForm.getSottocategoria().getId()))) {
				
					
				beneForm = checkEnteFittizio(beneForm,result);
				if (beneForm.isEnteFittizio()) return;
			}
			
		}

		if(ComparatorUtil.containsValue(tipologia, Constants.TipologieBeni.ID_TIPO_MOBILE_AZIENDA)){
			validaCampiObbligatoriAzienda(beneForm, result);
		}

		//Giacomo 27/08/2013 Spostato la validazione del bene immobile dal controller al validator
		if(ComparatorUtil.containsValue(tipologia, Constants.TipologieBeni.ID_TIPO_IMMOBILE)){
			validateCampiObbligatoriImmobile(beneForm, result);
			validateBeneImmobile(beneForm, result);

			if (beneForm.getStato().getId().equalsIgnoreCase(Constants.NO_ITEM_SELECTED)) {
				result.rejectValue("stato.id", "NotNull");
			}  

			ValidationUtil.checkComuneSelezionato(beneForm.getComune(), null, result, "comune.descrizione");

			if (beneForm.getTipoCatasto() == null || (beneForm.getTipoCatasto() != null && beneForm.getTipoCatasto().equals(""))){
				result.rejectValue("tipoCatasto", "NotNull");
			} 


		}

		//Giacomo 27/08/2013 Aggiunti i campi obbligatori per bene di tipo mobile registato
		if(ComparatorUtil.containsValue(tipologia, Constants.TipologieBeni.ID_TIPO_MOBILE_REGISTRATO)){
			// categoria Bene Immateriale
			if(ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_IMMATERIALE)){
				validateCampiObbligatoriBeneImmateriale(beneForm, result);
			}
			//categoria vaeicolo
			if(ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_VEICOLO)){
				validateCampiObbligatoriVeicolo(beneForm, result);
			}
		}
		
		//Giacomo 28/08/2013 Aggiunti i campi obbligatori per bene di tipo mobile
		if(ComparatorUtil.containsValue(tipologia, Constants.TipologieBeni.ID_TIPO_MOBILE)){
			//categoria animali
			if(ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_ANIMALI)){
				validateCampiObbligatoriAnimali(beneForm, result);
			}
			// categoria collezione
			if(ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_COLLEZIONE)){
				validateCampiObbligatoriCollezioni(beneForm, result);
			}
			//altri oggetti
			if(ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_ALTRI_OGGETTI)){
				validateCampiObbligatoriAltriOggetti(beneForm, result);
			}

			//se il bene e' denaro e la sottocategoria e' CONTANTE o NON DEFINITO
			//verifico se l'utente vuole inserire un ente fittizio
			if (ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_DENARO_CONTANTE) && ("107".equalsIgnoreCase(beneForm.getSottocategoria().getId()) ||
					"35".equalsIgnoreCase(beneForm.getSottocategoria().getId()))) {
				
					
				beneForm = checkEnteFittizio(beneForm,result);
				if (beneForm.isEnteFittizio()) return;
			
			}
			
			//Giacomo 02/09/2013 validazione bene finanziario o denaro 
			if(ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_DENARO_CONTANTE)){
				validateCampiObbligatoriDenaro(beneForm, result);
			}
			
			
		}

		//Giacomo 02/09/2013 validazione bene finanziario o denaro
		if(ComparatorUtil.containsValue(tipologia, Constants.TipologieBeni.ID_TIPO_FINANZIARIO)){
			if (	(!"117".equals(beneForm.getSottocategoria().getId()) && 
					 !"99".equals(beneForm.getSottocategoria().getId())) ) {
				validateNumeroRegistrazioneImporto(beneForm, result, request);
				if (result.hasErrors()) {
					return ;
				}
				
				validateInserimentoBeneFinanziario_Denaro(beneForm, result);
			}
			else {
			validateCampiObbligatoriBeneFinanziario(beneForm, result);
		}

			
		}

		if(result.hasErrors()) {
			Message message = null;
			if(request.getAttribute(Constants.MESSAGE)!=null) {
				message = (Message)request.getAttribute(Constants.MESSAGE);
			}
			if(message==null || !message.getCode().equalsIgnoreCase("errors.AtLeastOne.required")) {
				super.setMessage(Constants.MESSAGE, "errors.campi.controllo", null, false);
			}
		}
	}
	/**
	 * Validazione dei campi obbligatori per la tipologia bene Azienda
	 * @param beneForm
	 * @param result
	 */
	public void validaCampiObbligatoriAzienda(BeneForm beneForm, BindingResult errors){

		ValidationUtil.required("ragioneSociale", errors);
		if(ValidationUtil.requiredCfAndOrPIva("codiceFiscale", "partitaIva", errors)){
			super.setMessage(Constants.MESSAGE, "errors.codiceFiscale.partitaIva.required", null, false);
		}


		ValidationUtil.requiredComboForm(beneForm.getStato(), "stato.id", errors);

		Utility.controlloPartitaIva(null, errors, beneForm);
	}
	
	
	/**
	 * Validazione campi obbligatori Bene Immobile
	 * @param beneForm
	 * @param errors
	 */
	public void validateCampiObbligatoriImmobile(BeneForm beneForm, BindingResult errors){
		if(StringUtils.equals(beneForm.getSwitchDenunciaIdentifImmobile(), "D")){
			ValidationUtil.required("annoDenuncia", errors);
			ValidationUtil.required("numeroDenuncia1", errors);
			ValidationUtil.required("numeroDenuncia2", errors);
		}

		if(StringUtils.equals(beneForm.getSwitchDenunciaIdentifImmobile(), "I")){
			ValidationUtil.required("foglio", errors);

			ValidationUtil.required("particella1", errors);
			//ValidationUtil.required("particella2", errors);

			//			ValidationUtil.required("sub1", errors);
			//			ValidationUtil.required("sub2", errors);
		}
	}
	/**
	 * Validazione campi obbligatori Bene Finanziario
	 * @param beneForm
	 * @param errors
	 */
	public void validateCampiObbligatoriBeneFinanziario(BeneForm beneForm, BindingResult errors){
		if(ValidationUtil.requiredAtLeastOne(errors, "codiceAbi","codiceCab.id","provincia.id","descOperFinanz.id","numeroRegistrazione","codiceIban")){
			super.setMessage(Constants.MESSAGE, "errors.AtLeastOne.required", "Codice ABI, Codice CAB, Provincia Descrizione Ente,Numero Registrazione c/o Ente e/o Codice Iban", false);
			return;
		}

		validateInserimentoBeneFinanziario_Denaro(beneForm, errors);
	}
	/**
	 * Validazione campi obbligatori Denaro e Contanti
	 * @param beneForm
	 * @param errors
	 */
	public void validateCampiObbligatoriDenaro(BeneForm beneForm, BindingResult errors){
		if(ValidationUtil.requiredAtLeastOne(errors, "codiceAbi","codiceCab.id","provincia.id","descOperFinanz.id","numeroRegistrazione","codiceIban")){
			super.setMessage(Constants.MESSAGE, "errors.AtLeastOne.required", "Codice ABI, Codice CAB, Provincia Descrizione Ente,Num.Libretto/Conto corrente e/o Codice Iban", false);
			return;
		}

		validateInserimentoBeneFinanziario_Denaro(beneForm, errors);
	}
	/**
	 * Validazione campi obbligatori Bene Immateriale
	 * @param beneForm
	 * @param errors
	 */
	public void validateCampiObbligatoriBeneImmateriale(BeneForm beneForm, BindingResult errors){
		ValidationUtil.required("numRegBeneMobileRegistrato", errors);
		ValidationUtil.required("dataRegistrazione", errors);
	}
	/**
	 * Validazione campi obbligatori Bene Veicolo
	 * @param beneForm
	 * @param errors
	 */
	public void validateCampiObbligatoriVeicolo(BeneForm beneForm, BindingResult errors){
		//ValidationUtil.required("modelloVeicolo", errors);
		if(ValidationUtil.requiredAtLeastOne(errors, "targa","telaio")){
			super.setMessage(Constants.MESSAGE, "errors.AtLeastOne.required", "Numero di Targa, Numero di Telaio", false);
			//			super.setMessage(Constants.MESSAGE, "errors.targa.telaio.required", null, false);
		}
	}
	/**
	 * Validazione campi obbligatori Bene Animale
	 * @param beneForm
	 * @param errors
	 */
	public void validateCampiObbligatoriAnimali(BeneForm beneForm, BindingResult errors){
		ValidationUtil.required("descrizioneCapo", errors);
	}
	/**
	 * Validazione campi obbligatori Bene Collezione
	 * @param beneForm
	 * @param errors
	 */
	public void validateCampiObbligatoriCollezioni(BeneForm beneForm, BindingResult errors){
		ValidationUtil.required("descrizioneCollezione", errors);
		ValidationUtil.required("numeroPezzi", errors);
	}
	/**
	 * Validazione campi obbligatori Altri Oggetti
	 * @param beneForm
	 * @param errors
	 */
	public void validateCampiObbligatoriAltriOggetti(BeneForm beneForm, BindingResult errors){
		ValidationUtil.required("descrizioneBene", errors);
	}

	public void validateInteresseNonDuplicato(BeneForm beneForm, BeneSoggettoForm beneSoggettoForm, long idProcedimento, Integer idUfficioUtente, BindingResult result, HttpServletRequest request, long idAtto) {

		beneSoggettoForm.setBene(beneForm);
		if (idAtto == 0) {
			idAtto = beneService.getIdAttoByUfficioEProcedimento(idUfficioUtente, idProcedimento);
		}
		TRelBeneAtto relazione = beneService.getRelazioneBeneProcedimentoById(beneSoggettoForm, idProcedimento, idUfficioUtente, idAtto);
		if (!(relazione == null)) {
			super.setMessage(Constants.MESSAGE, "bene.titolidiinteresse.error.interesseDuplicato", null, false);
			result.reject(Constants.MESSAGE);
		}
	}

	public void validateInteresseTotaleCoerente(BeneForm beneForm, BeneSoggettoForm beneSoggettoForm, long idProcedimento, Integer idUfficioUtente, BindingResult result, HttpServletRequest request, long idAtto, String tipoAtto) {

		beneSoggettoForm.setBene(beneForm);
		if (idAtto == 0) {
			idAtto = beneService.getIdAttoByUfficioEProcedimento(idUfficioUtente, idProcedimento);
		}
		//se non è proposta effettuo i controlli su T_REL_BENE_ATTO 
		//altrimenti su T_REL_PROC_BENE_TIT
		if(tipoAtto!=null && !tipoAtto.equalsIgnoreCase(Constants.TipoAtto.TIPO_ATTO+"")){
			controllaLimiteMassimoTitoliInteresse(beneSoggettoForm, idProcedimento,
					idUfficioUtente, result, idAtto, false);
		}else{
			controllaLimiteMassimoTitoliInteressePerAtto(beneSoggettoForm, idProcedimento,
					idUfficioUtente, result, idAtto, false);
		}
	}

	public void validateInteresseTotaleCoerentePerModifica(BeneForm beneForm, BeneSoggettoForm beneSoggettoForm, long idProcedimento, Integer idUfficioUtente, BindingResult result, HttpServletRequest request, String tipoAtto) {

		beneSoggettoForm.setBene(beneForm);
		// se provengo dalla maschera dell'atto decreto di sequestro
		long idAtto = 0l;
		if ((request.getSession().getAttribute(Constants.ATTO) != null) && (((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto() > idAtto)) {
			idAtto = ((AttoForm) (request.getSession().getAttribute(Constants.ATTO))).getCodAtto();
		} else {
			idAtto = beneService.getIdAttoByUfficioEProcedimento(idUfficioUtente, idProcedimento);
		}

		//se non è proposta effettuo i controlli su T_REL_BENE_ATTO 
		//altrimenti su T_REL_PROC_BENE_TIT
		if(tipoAtto!=null && !tipoAtto.equalsIgnoreCase(Constants.TipoAtto.TIPO_ATTO+"")){
			controllaLimiteMassimoTitoliInteresse(beneSoggettoForm, idProcedimento,
					idUfficioUtente, result, idAtto, true);
		}else{
			controllaLimiteMassimoTitoliInteressePerAtto(beneSoggettoForm, idProcedimento,
					idUfficioUtente, result, idAtto, true);
		}
	}

	private void controllaLimiteMassimoTitoliInteresse(
			BeneSoggettoForm beneSoggettoForm, long idProcedimento,
			Integer idUfficioUtente, BindingResult result, long idAtto, boolean modifica) {
		List<TRelProcBeneTit> relazioni = beneService.getRelazioniProcedimentoBeneTitolare(beneSoggettoForm, idProcedimento, idUfficioUtente);

		if (relazioni == null) {
			return;
		}

		double interesseQuotaComplessivo = 0;
		double interesseDerivataComplessivo = 0;
		boolean esisteDecretoAttivo = false;
		for (TRelProcBeneTit tmp : relazioni) {
			//incrementa i parziali se è insert o se è modifica non deve sommare le quote/frazioni del soggetto modificato e dello stesso atto
			if((modifica && (tmp.getId().getCodiKIdSoggTit() != beneSoggettoForm.getSoggetto().getIdSoggetto())) 
					|| !modifica){

				if(tmp.getNumeQuotaSeq() !=null){
					interesseQuotaComplessivo += tmp.getNumeQuotaSeq().doubleValue();
				}else{
					if (tmp.getNumeFrazioneNumSeq()!= null && tmp.getNumeFrazioneDenSeq()!= null)
						interesseDerivataComplessivo +=100 * (tmp.getNumeFrazioneNumSeq().doubleValue() / tmp.getNumeFrazioneDenSeq().doubleValue());
				}

				if((tmp.getNumeQuotaSeq()!=null &&  tmp.getNumeQuotaSeq().intValue() > 0 ) || (tmp.getNumeFrazioneNumSeq()!=null && tmp.getNumeFrazioneNumSeq()>0) || (tmp.getNumeFrazioneDenSeq()!=null && tmp.getNumeFrazioneDenSeq()>0)){
					esisteDecretoAttivo=true;
				}
			}

		}

		boolean valoriConformi = true;
		if(interesseQuotaComplessivo>0 && (beneSoggettoForm.getNumeratoreFrazioneQuota() != null ||  (beneSoggettoForm.getDenominatoreFrazioneQuota() != null))){
			super.setMessage(Constants.MESSAGE, "bene.titolidiinteresse.error.conforme.al.precedente.tipologia", null, false);
			result.reject(Constants.MESSAGE);
			valoriConformi= false;
		}

		if(interesseDerivataComplessivo>0 && (beneSoggettoForm.getQuotaInteresse()!=null)){
			super.setMessage(Constants.MESSAGE, "bene.titolidiinteresse.error.conforme.al.precedente.tipologia", null, false);
			result.reject(Constants.MESSAGE);
			valoriConformi = false;
		}

		if(valoriConformi && esisteDecretoAttivo){
			double quotaDerivata = 0;
			if ((beneSoggettoForm.getNumeratoreFrazioneQuota() != null) && (beneSoggettoForm.getDenominatoreFrazioneQuota() != null)) {
				quotaDerivata = 100 * (beneSoggettoForm.getNumeratoreFrazioneQuota().doubleValue() / beneSoggettoForm.getDenominatoreFrazioneQuota().doubleValue());
				interesseDerivataComplessivo += quotaDerivata;
			}else{
				quotaDerivata = beneSoggettoForm.getQuotaInteresse().doubleValue();
				interesseQuotaComplessivo += quotaDerivata;
			}
			if (!(interesseDerivataComplessivo <= 100) || ! (interesseQuotaComplessivo <=100)) {
				super.setMessage(Constants.MESSAGE, "bene.titolidiinteresse.error.totaleInteresseIncoerente", null, false);
				result.reject(Constants.MESSAGE);
			}
			beneSoggettoForm.setQuotaSequestroOriginaria(new Double(0));
			beneSoggettoForm.setQuotaFrazioneNumeratoreSeqOriginaria(0);
			beneSoggettoForm.setQuotaFrazioneDenominatoreSeqOriginaria(0);
		}


	}

	private void controllaLimiteMassimoTitoliInteressePerAtto(
			BeneSoggettoForm beneSoggettoForm, long idProcedimento,
			Integer idUfficioUtente, BindingResult result, long idAtto, boolean modifica) {
		List<TRelBeneAtto> relazioni = beneService.getRelazioniByTipoInteresse(beneSoggettoForm, idProcedimento, idUfficioUtente, idAtto);
		if (relazioni == null) {
			return;
		}

		double interesseQuotaComplessivo = 0;
		double interesseDerivataComplessivo = 0;

		for (TRelBeneAtto tmp : relazioni) {
			//incrementa i parziali se è insert o se è modifica non deve sommare le quote/frazioni del soggetto modificato e dello stesso atto
			if((modifica && (tmp.getId().getCodiKIdSoggTit() != beneSoggettoForm.getSoggetto().getIdSoggetto())) 
					|| !modifica){

				if(tmp.getNumeQuota() !=null){
					interesseQuotaComplessivo += tmp.getNumeQuota().doubleValue();
				}else{
					interesseDerivataComplessivo +=100 * (tmp.getNumeFrazioneNum().doubleValue() / tmp.getNumeFrazioneDen().doubleValue());
				}

			}

		}

		boolean valoriConformi = true;
		if(interesseQuotaComplessivo>0 && (beneSoggettoForm.getNumeratoreFrazioneQuota() != null ||  (beneSoggettoForm.getDenominatoreFrazioneQuota() != null))){
			super.setMessage(Constants.MESSAGE, "bene.titolidiinteresse.error.conforme.al.precedente.tipologia", null, false);
			result.reject(Constants.MESSAGE);
			valoriConformi= false;
		}

		if(interesseDerivataComplessivo>0 && (beneSoggettoForm.getQuotaInteresse()!=null)){
			super.setMessage(Constants.MESSAGE, "bene.titolidiinteresse.error.conforme.al.precedente.tipologia", null, false);
			result.reject(Constants.MESSAGE);
			valoriConformi = false;
		}

		if(valoriConformi){
			double quotaDerivata = 0;
			if ((beneSoggettoForm.getNumeratoreFrazioneQuota() != null) && (beneSoggettoForm.getDenominatoreFrazioneQuota() != null)) {
				quotaDerivata = 100 * (beneSoggettoForm.getNumeratoreFrazioneQuota().doubleValue() / beneSoggettoForm.getDenominatoreFrazioneQuota().doubleValue());
				interesseDerivataComplessivo += quotaDerivata;
			}else{
				quotaDerivata = beneSoggettoForm.getQuotaInteresse().doubleValue();
				interesseQuotaComplessivo += quotaDerivata;
			}
			if (!(interesseDerivataComplessivo <= 100) || ! (interesseQuotaComplessivo <=100)) {
				super.setMessage(Constants.MESSAGE, "bene.titolidiinteresse.error.totaleInteresseIncoerente", null, false);
				result.reject(Constants.MESSAGE);
			}
			beneSoggettoForm.setQuotaSequestroOriginaria(new Double(0));
			beneSoggettoForm.setQuotaFrazioneNumeratoreSeqOriginaria(0);
			beneSoggettoForm.setQuotaFrazioneDenominatoreSeqOriginaria(0);
		}


	}

	public void validateListaBeni(BeneForm bene, HttpServletRequest request, BindingResult result) {

		if (bene.getIdBene() == null) {
			result.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
		}
	}

	public void validatePresenzaComeMisuraPatrimoniale(long idPersona, long idProcedimento, Integer idUfficioUtente, long idBene, int idTipoInteresse, Errors errors, HttpServletRequest request) {

		boolean operazioneConsentita = !beneService.controllaPresenzaSoggettoInMisuraPatrimoniale(idPersona, idProcedimento, idUfficioUtente, idBene, idTipoInteresse);
		if (!operazioneConsentita) {
			super.setMessage(Constants.MESSAGE, "bene.titolidiinteresse.elimina.error.misuraPatrimoniale", null, false);
			errors.reject(Constants.MESSAGE);
			return;
		}
	}

	public void validateQuotaInteresseCoerente(BeneSoggettoForm beneSoggettoForm, Errors errors, HttpServletRequest request) {

		//		if (beneSoggettoForm.getQuotaInteresse() == null) {
		//			super.setMessage(Constants.MESSAGE, "benesoggetto.quota.error.obbligatoria", null, false);
		//			errors.reject(Constants.MESSAGE);
		//			return;
		//		}
		if ((beneSoggettoForm.getQuotaInteresse() !=null) && ((beneSoggettoForm.getQuotaInteresse() <= 0) || (beneSoggettoForm.getQuotaInteresse() > 100))) {
			super.setMessage(Constants.MESSAGE, "benesoggetto.quota.error.outOfBounds", null, false);
			errors.reject(Constants.MESSAGE);
			return;
		}
	}

	public void validateQuotaOFrazione(BeneSoggettoForm beneSoggettoForm, Errors errors) {

		if (beneSoggettoForm.getQuotaInteresse() != null && (beneSoggettoForm.getNumeratoreFrazioneQuota() != null || beneSoggettoForm.getDenominatoreFrazioneQuota() != null)) {
			super.setMessage(Constants.MESSAGE, "benesoggetto.quotaOFrazione.error.obbligatorio", null, false);
			errors.reject(Constants.MESSAGE);
			return;
		}
	}

	public void validateRadioButtonSoggetto(BeneForm bene, BindingResult result, HttpServletRequest request) {

		if ((bene != null) && (bene.getIdentificativoSoggettoProcedimento() == 0)) {
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			result.reject(Constants.MESSAGE);
		}
	}

	public void validateRadioButtonTitoliDiritto(BeneForm bene, BindingResult result, HttpServletRequest request) {

		if ((bene != null) && (bene.getIdentificativoSoggettoBeneProcedimentoTitoloDiritto() == null)) {
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			result.reject(Constants.MESSAGE);
		}
	}

	public void validateSalvaDecisorioBeni(BeneSoggettoForm form, List<BeneForm> beniSelezionati, long idProcedimento, Integer idUfficioUtente, HttpServletRequest request, BindingResult result) {

		System.out.println("validateSalvaDecisorioBeni"  );
		if ((beniSelezionati == null) || beniSelezionati.isEmpty()) {
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			result.reject(Constants.MESSAGE);
			return;
		}

		if (form.getDecisioneBeni().getId().equals(Constants.NO_ITEM_SELECTED)) {
			result.reject(Constants.MESSAGE);
			super.setMessage(Constants.MESSAGE, "error.misuraPersonale.decisione", null, false);
		}

		// se l'esito e' confisca non ci posso eesere beni non definito e vanno
		// trasformati

		String idDecisioneBeni = form.getDecisioneBeni().getId();
		if (idDecisioneBeni.equals(Constants.ESITO_CONFISCA) || idDecisioneBeni.equals(Constants.ESITO_CONFISCA_SEG_SOSP) || idDecisioneBeni.equals(Constants.ESITO_CONFISCA_SEQ)) {
			for (BeneForm tmp : beniSelezionati) {

				BeneForm beneCheck = beneService.beneById(idUfficioUtente, tmp.getIdBene().longValue(), idProcedimento);
				long tipologia = 0;
				long categoria = 0;
				long sottocategoria = 0;
				if ((beneCheck.getTipologia() != null) && (beneCheck.getTipologia().getId() != null)) {
					tipologia = Long.parseLong(beneCheck.getTipologia().getId());
				}
				if ((beneCheck.getCategoria() != null) && (beneCheck.getCategoria().getId() != null)) {
					categoria = Long.parseLong(beneCheck.getCategoria().getId());
				}
				if ((beneCheck.getSottocategoria() != null) && (beneCheck.getSottocategoria().getId() != null)) {
					sottocategoria = Long.parseLong(beneCheck.getSottocategoria().getId());
				}

				if (ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_SOCIETA_NON_DEFINITA)) {
					result.reject(Constants.MESSAGE);
					super.setMessage(Constants.MESSAGE, "error.beni.no.definiti", null, false);
					return;
				}
				// Se ho come Sottocategoria una non definita escludo le
				// validazioni
				if (sottocategoria > 0 && SottoCategoriaUtils.isSottoCategoriaNonDefinita(sottocategoria)) {
					result.reject(Constants.MESSAGE);
					super.setMessage(Constants.MESSAGE, "error.beni.no.definiti", null, false);
					return;
				}
			}
		}
	}

	public void controlloDecisioneBeniNonDefiniti(List<BeneSoggettoForm> beni, int idUfficioUtente, long idProcedimento, BindingResult result, HttpServletRequest request, boolean confermaInvia) {
		List<Message> msgs = new ArrayList<Message>();
		if (beni != null && beni.size() > 0) {

			for (BeneSoggettoForm beneSoggettoForm : beni) {
				List<SoggettoForm> soggetti = beneSoggettoForm.getSoggetti();
				BeneForm bene = beneSoggettoForm.getBene();
				for (SoggettoForm soggettoForm : soggetti) {
					MisuraForm misura = soggettoForm.getMisura();
					String idDecisioneBene = misura.getDecisioneMisuraPersonale().getId();
					if (idDecisioneBene.equals(Constants.ESITO_CONFISCA) || idDecisioneBene.equals(Constants.ESITO_CONFISCA_SEG_SOSP) || idDecisioneBene.equals(Constants.ESITO_CONFISCA_SEQ)) {
						BeneForm beneCheck = beneService.beneById(idUfficioUtente, bene.getIdBene().longValue(), idProcedimento);
						long tipologia = 0;
						long categoria = 0;
						long sottocategoria = 0;
						if ((beneCheck.getTipologia() != null) && (beneCheck.getTipologia().getId() != null)) {
							tipologia = Long.parseLong(beneCheck.getTipologia().getId());
						}
						if ((beneCheck.getCategoria() != null) && (beneCheck.getCategoria().getId() != null)) {
							categoria = Long.parseLong(beneCheck.getCategoria().getId());
						}
						if ((beneCheck.getSottocategoria() != null) && (beneCheck.getSottocategoria().getId() != null)) {
							sottocategoria = Long.parseLong(beneCheck.getSottocategoria().getId());
						}

						if (ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_SOCIETA_NON_DEFINITA)) {
							result.reject(Constants.MESSAGE);
							//super.setMessage(Constants.MESSAGE, "error.beni.no.definiti", null, false);
							super.addMessageIntoList(msgs, Constants.MESSAGE, "error.beni.no.definiti", null);
							super.setMessages(msgs, true);
							return;
						}
						// Se ho come Sottocategoria una non definita escludo le
						// validazioni
						if (sottocategoria > 0 && SottoCategoriaUtils.isSottoCategoriaNonDefinita(sottocategoria)) {
							result.reject(Constants.MESSAGE);
							//super.setMessage(Constants.MESSAGE, "error.beni.no.definiti", null, false);
							super.addMessageIntoList(msgs, Constants.MESSAGE, "error.beni.no.definiti", null);
							super.setMessages(msgs, true);
							return;
						}
					}
				}
				//F.C. Ticket#20220506018
				//Incorporata la parte sotto fatta da P.Q.  MEV 2021/19
				if (confermaInvia) {
					ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
		    		int idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
		    		BeneForm beneForm = beneService.beneById(idUffOwn, bene.getIdBene(), procedimento.getIdProcedimento());
		    		
		    		if (beneForm.getCategoria() != null && beneForm.getCategoria().getId() != null) {
		    			// Categoria 4 = Mobile - Denaro
		    			// Tipo 4 = Finanziario
		    			if(beneForm.getCategoria().getId().equals("6") || beneForm.getTipologia().getId().equals("4")) {
		            	    if ((beneForm.getDescOperFinanz() == null ||
	            	    		beneForm.getDescOperFinanz().getDescrizione() == null ||
	            	    				beneForm.getDescOperFinanz().getDescrizione().equals("")) || 
	            	    		beneForm.getDescOperFinanz().getDescrizione().equals(Constants.EnteFittizio.DESCRIZIONE) ) {
		                	    	
		                            //super.setMessage(Constants.MESSAGE, "error.esecuzione.sequestro", null, false);
		            	    		super.addMessageIntoList(msgs, Constants.MESSAGE, "error.esecuzione.sequestro", null);
		            	    		super.setMessages(msgs, true);
		                            result.reject(Constants.MESSAGE);
		                            return;        	    	
		            	    }        				
		    			}
		    		}   
				}
			}
		}

 		// P.Q. Inizio MEV 2021/19		
		//F.C. Ticket#20220506018
		//Questo codice è stato incorporato sopra, quindi lo commento
		/*if (confermaInvia) {
			for (BeneSoggettoForm beneSoggettoForm : beni) {
				
				BeneForm beneTemp = beneSoggettoForm.getBene();			
	    		ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute(Constants.ID_PROCEDIMENTO);
	    		int idUffOwn = (Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE);
	    		BeneForm bene = beneService.beneById(idUffOwn, beneTemp.getIdBene(), procedimento.getIdProcedimento());
	    		
	    		if (bene.getCategoria() != null && bene.getCategoria().getId() != null) {
	    			// Categoria 4 = Mobile - Denaro
	    			// Tipo 4 = Finanziario
	    			if(bene.getCategoria().getId().equals("6") || bene.getTipologia().getId().equals("4")) {
	            	    if ( (bene.getDescOperFinanz() == null ||
	                	      bene.getDescOperFinanz().getDescrizione() == null ||
	                	      bene.getDescOperFinanz().getDescrizione().equals("")) || 
	            	    	  bene.getDescOperFinanz().getDescrizione().equals(Constants.EnteFittizio.DESCRIZIONE) ) {
	                	    	
	                            //super.setMessage(Constants.MESSAGE, "error.esecuzione.sequestro", null, false);
	            	    		super.addMessageIntoList(msgs, Constants.MESSAGE, "error.esecuzione.sequestro", null);
	            	    		super.setMessages(msgs, true);
	                            result.reject(Constants.MESSAGE);
	                            return;        	    	
	            	    }        				
	    			}
	    		}   			
			}			
		}*/
 		// P.Q. Fine MEV 2021/19				
	}

	public void validateSceltaCheckbox(BeneForm bene, BindingResult result, HttpServletRequest request) {

		if ((bene.getListaIdCheckbox() == null) || ((bene.getListaIdCheckbox() != null) && bene.getListaIdCheckbox().isEmpty())) {
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			result.reject(Constants.MESSAGE);
		}
	}

	public void validateSceltaCheckboxAlmenoDueBeni(BeneForm bene, BindingResult result, HttpServletRequest request) {

		if ((bene.getListaIdCheckbox() == null) || ((bene.getListaIdCheckbox() != null) && bene.getListaIdCheckbox().isEmpty())) {
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			result.reject(Constants.MESSAGE);
			return;
		}
		if (bene.getListaIdCheckbox().size() < 2) {
			super.setMessage(Constants.MESSAGE, "table.item.selection.required.two.elements", null, false);
			result.reject(Constants.MESSAGE);
		}
	}

	public void validateSceltaListaBeneForm(List<BeneForm> beniSelezionati, BindingResult result, HttpServletRequest request) {

		if ((beniSelezionati == null) || beniSelezionati.isEmpty()) {
			super.setMessage(Constants.MESSAGE, "soggetto.patrimoniale.error.select", null, false);
			result.reject(Constants.MESSAGE);
			return;
		}
		for (BeneForm tmp : beniSelezionati) {
			if ((tmp.getIdBene() != null) && (tmp.getListaIdCheckbox() != null) && !tmp.getListaIdCheckbox().isEmpty()) {
				return;
			}
		}
		super.setMessage(Constants.MESSAGE, "soggetto.patrimoniale.error.select", null, false);
		result.reject(Constants.MESSAGE);
	}

	public void validateSceltaListaBeni(List<Long> listaId, BindingResult result, HttpServletRequest request) {

		if ((listaId == null) || listaId.isEmpty()) {
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			result.reject(Constants.MESSAGE);
			return;
		}
	}

	public void validateSceltaListaStringBeneForm(List<BeneForm> beniSelezionati, BindingResult result, HttpServletRequest request) {

		if ((beniSelezionati == null) || beniSelezionati.isEmpty()) {
			super.setMessage(Constants.MESSAGE, "soggetto.patrimoniale.error.select", null, false);
			result.reject(Constants.MESSAGE);
			return;
		}
		for (BeneForm tmp : beniSelezionati) {
			if ((tmp.getIdBene() != null) && (tmp.getListaIdCheckboxString() != null) && !tmp.getListaIdCheckboxString().isEmpty()) {
				return;
			}
		}
		super.setMessage(Constants.MESSAGE, "soggetto.patrimoniale.error.select", null, false);
		result.reject(Constants.MESSAGE);
	}

	public void validateSceltaSingolaCheckbox(BeneForm bene, BindingResult result, HttpServletRequest request) {

		if ((bene.getListaIdCheckbox() == null) || ((bene.getListaIdCheckbox() != null) && (bene.getListaIdCheckbox().isEmpty() || (bene.getListaIdCheckbox().size() > 1)))) {
			if ((bene.getListaIdCheckboxString() == null) || ((bene.getListaIdCheckboxString() != null) && (bene.getListaIdCheckboxString().isEmpty() || (bene.getListaIdCheckboxString().size() > 1)))) {
				super.setMessage(Constants.MESSAGE, "table.item.selection.single", null, false);
				result.reject(Constants.MESSAGE);
			}
		}


	}

	public void validateSceltaSingolaCheckboxTrasforma(BeneForm bene, int idUfficioUtente,long idProcedimento,BindingResult result, HttpServletRequest request) {

		if ((bene.getListaIdCheckbox() == null) || ((bene.getListaIdCheckbox() != null) && (bene.getListaIdCheckbox().isEmpty() || (bene.getListaIdCheckbox().size() > 1)))) {
			if ((bene.getListaIdCheckboxString() == null) || ((bene.getListaIdCheckboxString() != null) && (bene.getListaIdCheckboxString().isEmpty() || (bene.getListaIdCheckboxString().size() > 1)))) {
				super.setMessage(Constants.MESSAGE, "table.item.selection.single", null, false);
				result.reject(Constants.MESSAGE);
			}
		}

		else {
			long idBene=bene.getListaIdCheckbox().get(0);
			BeneForm beneCheck=beneService.beneById(idUfficioUtente, idBene, idProcedimento);	
			long tipologia = 0;
			long categoria = 0;
			long sottocategoria = 0;
			if ((beneCheck.getTipologia() != null) && (beneCheck.getTipologia().getId() != null)) {
				tipologia = Long.parseLong(beneCheck.getTipologia().getId());
			}
			if ((beneCheck.getCategoria() != null) && (beneCheck.getCategoria().getId() != null)) {
				categoria = Long.parseLong(beneCheck.getCategoria().getId());
			}
			if ((beneCheck.getSottocategoria() != null) && (beneCheck.getSottocategoria().getId() != null)) {
				sottocategoria = Long.parseLong(beneCheck.getSottocategoria().getId());
			}

			if(ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_SOCIETA_NON_DEFINITA)){

				return;
			}
			//Se ho come Sottocategoria una non definita escludo le validazioni
			if(sottocategoria>0 && SottoCategoriaUtils.isSottoCategoriaNonDefinita(sottocategoria)){

				return;
			}
			super.setMessage(Constants.MESSAGE, "error.bene.trasforma.no.definito", null, false);
			result.reject(Constants.MESSAGE);

		}
	}

	public void verificaPresenzaTitoliDiritto(long idProcedimento, long idBene, Integer idUfficioUtente, BindingResult result, HttpServletRequest request) {

		List<TRelBeneAtto> relBeneAtto = beneService.getTRelBeneAtto(idProcedimento, idBene, idUfficioUtente, 0l);
		if (relBeneAtto.size() > 0) {
			super.setMessage(Constants.MESSAGE, "bene.eliminabene.errore", null, false);
			result.reject(Constants.MESSAGE);
		}
	}

	public void validateCopiaTitoliDiDiritto(List<BeneForm> beni, Integer idUffOwn, long idProc, Errors result) {

		long vidProposta = beneService.getIdAttoByUfficioEProcedimento(idUffOwn, idProc);
		Iterator iter = beni.iterator();
		while (iter.hasNext()) {
			BeneForm b = (BeneForm) iter.next();
			List<TRelBeneAtto> lista = beneService.getTRelBeneAtto(idProc, b.getIdBene(), idUffOwn, vidProposta);
			if (lista != null && lista.size() > 0) {
				result.reject(Constants.MESSAGE);
				super.setMessage(Constants.MESSAGE, "bene.copia.titolididiritto.controlloTitoliPresenti", null, false);
				break;
			}
		}
	}

	public void validateBeneImmobile(BeneForm beneForm, BindingResult result) {

		String part1 = beneForm.getParticella1();
		String part2 = beneForm.getParticella2();
		String subPart1 = beneForm.getSub1();
		String subPart2 = beneForm.getSub2();

		List<Message> customMessageList = new ArrayList<Message>();

		if(part2!=null && part1==null){
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "bene.immobile.particella.errore", null);
		}
		if(subPart2!=null && subPart1==null){
			super.addMessageIntoList(customMessageList, Constants.MESSAGE, "bene.immobile.sub.errore", null);
		}
		if (customMessageList.size() > 0) {
			result.reject(Constants.MESSAGE);
			super.setMessages(customMessageList, false);
		}
	}
	/**
	 * Validazione Trasformazione Bene in Denaro
	 * @param beneForm
	 * @param result
	 */
	public void validateTrasformazioneBeneDenaro(BeneForm beneForm, BindingResult result){
		if(Utility.isDefinito(beneForm.getTipoValuta())){
			ValidationUtil.required("valutaEst", result);
		}else{
			ValidationUtil.required("valutaEur", result);
		}

		// validateCampiObbligatoriBeneFinanziario(beneForm, result);
	}


	/**
	 * Data una frazione e il limite massimo controlla che non sia superata l'unità e che il numeratore non sia inferiore a 0.
	 * @param frazioneRitorno
	 * @param limiteMax
	 * @param tipoCarattereMisura
	 * @param errors
	 */
	public void validateFrazioneSequestrata(Frazione frazioneRitorno,
			int limiteMax, String tipoCarattereMisura, Errors errors){
		try{
			int numeratore = frazioneRitorno.getNumeratore();
			int denominatore = frazioneRitorno.getDenominatore();

			if(tipoCarattereMisura!=null && (
					(tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_ORDINARIO))
					|| (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_URGENTE))
					|| (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_ANTICIPATO))
					)){



				//ho superato l'unità (ES: 5 su 4)
				if(numeratore/denominatore>limiteMax){
					super.setMessage(Constants.MESSAGE, "sequestro.superato.limite.max.unita", null, false);
					errors.reject(Constants.MESSAGE);
				}

			}

			if(tipoCarattereMisura!=null 
					&& (tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_REVOCA)
							|| tipoCarattereMisura.equalsIgnoreCase(Constants.TipoCarattereMisura.SEQUESTRO_NON_CONVALIDATO))){

				if(numeratore<0){
					super.setMessage(Constants.MESSAGE, "sequestro.numeratore.inferiore.a.zero", null, false);
					errors.reject(Constants.MESSAGE);
				}
			}

		}catch (Exception e) {
		}

	}


	/**
	 * Passata la quota finale controlla che non sia superiore al limite massimo 
	 * e che non sia inferiore a 0
	 * @param quota
	 * @param limiteMax
	 * @param errors
	 */
	public void validateQuotaPercentuale(double quota,
			int limiteMax,Errors errors) {
		if(quota>limiteMax){
			super.setMessage(Constants.MESSAGE, "sequestro.limite.superato", null, false);
			errors.reject(Constants.MESSAGE);

		}
		if(quota<0){
			super.setMessage(Constants.MESSAGE, "sequestro.sotto.limite", null, false);
			errors.reject(Constants.MESSAGE);

		}
	}


	public void validateRevocaSequestroPossibile(BindingResult result,
			BeneForm beneForm, long idProcedimento, Integer idUfficioUtente,
			long idPersona, int idTipoInteresse, BeneSoggettoForm formConInfo) {

		TRelProcBeneTit tRis = beneService.getRelazioniProcedimentoBeneTitolare(idProcedimento, beneForm.getIdBene().longValue(), idUfficioUtente.intValue(), idTipoInteresse, idPersona);

		if(tRis!=null){
			double numQuota = tRis.getNumeQuotaSeq()!=null? tRis.getNumeQuotaSeq().doubleValue():0;
			int numFrazioneNumeratore = tRis.getNumeFrazioneNumSeq()!=null? tRis.getNumeFrazioneNumSeq().intValue():0;
			int numFrazioneDenominatore = tRis.getNumeFrazioneDenSeq()!=null? tRis.getNumeFrazioneDenSeq().intValue():0;

			if(numQuota<=0 && numFrazioneNumeratore <=0 && numFrazioneDenominatore <=0){
				super.setMessage(Constants.MESSAGE, "bene.titolidiinteresse.error.revoca.sequestro.non.valida", null, false);
				result.reject(Constants.MESSAGE);
			}
		}else{
			super.setMessage(Constants.MESSAGE, "bene.titolidiinteresse.error.revoca.sequestro.non.valida", null, false);
			result.reject(Constants.MESSAGE);
		}

	}

	public void validaBeneDaAnnullare(BeneForm beneForm, BeneForm beneFormValido, BeneForm beneFormDuplicato, Errors errors) {

		List<Message> customMessageList = new ArrayList<Message>();
		long tipologia = 0;
		long categoria = 0;
		long sottocategoria = 0;
		if ((beneForm.getTipologia() != null) && (beneForm.getTipologia().getId() != null)) {
			tipologia = Long.parseLong(beneForm.getTipologia().getId());
		}
		if ((beneForm.getCategoria() != null) && (beneForm.getCategoria().getId() != null)) {
			categoria = Long.parseLong(beneForm.getCategoria().getId());
		}
		if ((beneForm.getSottocategoria() != null) && (beneForm.getSottocategoria().getId() != null)) {
			sottocategoria = Long.parseLong(beneForm.getSottocategoria().getId());
		}

		//Se ho come categoria società non definita escludo le validazioni
		if(ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_SOCIETA_NON_DEFINITA)){
			return;
		}
		//Se ho come Sottocategoria una non definita escludo le validazioni
		if(sottocategoria>0 && SottoCategoriaUtils.isSottoCategoriaNonDefinita(sottocategoria)){
			return;
		}

		/*************inizio controllo congruenza tra bene valido e bene duplicato********************/
		/*
			if(ComparatorUtil.containsValue(tipologia, Constants.TipologieBeni.ID_TIPO_MOBILE_AZIENDA)){
				validaCampiObbligatoriAzienda(beneForm, result);
			}

			//Giacomo 27/08/2013 Spostato la validazione del bene immobile dal controller al validator
			if(ComparatorUtil.containsValue(tipologia, Constants.TipologieBeni.ID_TIPO_IMMOBILE)){
				validateCampiObbligatoriImmobile(beneForm, result);
	            validateBeneImmobile(beneForm, result);
	        }

			//Giacomo 27/08/2013 Aggiunti i campi obbligatori per bene di tipo mobile registato
			if(ComparatorUtil.containsValue(tipologia, Constants.TipologieBeni.ID_TIPO_MOBILE_REGISTRATO)){
				// categoria Bene Immateriale
				if(ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_IMMATERIALE)){
					validateCampiObbligatoriBeneImmateriale(beneForm, result);
				}
				//categoria vaeicolo
				if(ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_VEICOLO)){
					validateCampiObbligatoriVeicolo(beneForm, result);
				}
			}
			//Giacomo 28/08/2013 Aggiunti i campi obbligatori per bene di tipo mobile
			if(ComparatorUtil.containsValue(tipologia, Constants.TipologieBeni.ID_TIPO_MOBILE)){
				//categoria animali
				if(ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_ANIMALI)){
					validateCampiObbligatoriAnimali(beneForm, result);
				}
				// categoria collezione
				if(ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_COLLEZIONE)){
					validateCampiObbligatoriCollezioni(beneForm, result);
				}
				//altri oggetti
				if(ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_ALTRI_OGGETTI)){
					validateCampiObbligatoriAltriOggetti(beneForm, result);
				}

				//Giacomo 02/09/2013 validazione bene finanziario o denaro
				if(ComparatorUtil.containsValue(categoria, Constants.CategoriaBeni.ID_TIPO_DENARO_CONTANTE)){
					validateCampiObbligatoriDenaro(beneForm, result);
				}
			}

			//Giacomo 02/09/2013 validazione bene finanziario o denaro
			if(ComparatorUtil.containsValue(tipologia, Constants.TipologieBeni.ID_TIPO_FINANZIARIO)){
				validateCampiObbligatoriBeneFinanziario(beneForm, result);
			}

			super.setMessages(customMessageList, true, errors);
		 */

	}



}
