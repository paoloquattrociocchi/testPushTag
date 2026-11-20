package it.giustizia.smp.validation;

import it.giustizia.smp.controller.procedimento.FugController;
import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.form.FugForm;
import it.giustizia.smp.model.TAnComuni;
import it.giustizia.smp.model.TAnProvincia;
import it.giustizia.smp.model.TAnStato;
import it.giustizia.smp.model.TPersonaFisica;
import it.giustizia.smp.model.TPersonaGiur;
import it.giustizia.smp.model.TRelProcSogint;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Message;
import it.giustizia.smp.utility.TipoBollettino;
import it.giustizia.smp.utility.ValidationUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("fugValidation")
public class FugValidation extends BaseValidation implements Validator {

	private static Map<String, TipoBollettino> mappaTipoBollettino = new LinkedHashMap<String, TipoBollettino>();
	static {
		String[] regoleList = null;
		String regoleDesc = null;
		TipoBollettino tb = null;

		// regoleList = new String[] { "[0-9]{27}" };
		// regoleDesc = "27 cifre";
		// tb = new TipoBollettino("C/C", regoleList, regoleDesc);
		// mappaTipoBollettino.put("1", tb);
		//
		// regoleList = new String[] { "[0-9]{27}" };
		// regoleDesc = "27 cifre";
		// tb = new TipoBollettino("Libretto", regoleList, regoleDesc);
		// mappaTipoBollettino.put("3", tb);
		//
		// regoleList = new String[] { "[0-9]{27}" };
		// regoleDesc = "27 cifre";
		// tb = new TipoBollettino("Libretto di Deposito giudiziario", regoleList, regoleDesc);
		// mappaTipoBollettino.put("98", tb);

		// P.Q. Inizio MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.		
		regoleList = new String[] { "[a-zA-Z0-9].{0,27}" };
		regoleDesc = "min 1 - max 27 caratteri";
		tb = new TipoBollettino("", "C/C", regoleList, regoleDesc, 0, 27);
		mappaTipoBollettino.put("1", tb);
	
		regoleList = new String[] { "[a-zA-Z0-9].{0,27}" };
		regoleDesc = "min 1 - max 27 caratteri";
		tb = new TipoBollettino("", "Libretto", regoleList, regoleDesc, 0, 27);
		mappaTipoBollettino.put("3", tb);
	
		regoleList = new String[] { "[a-zA-Z0-9].{0,27}" };
		regoleDesc = "min 1 - max 27 caratteri";
		tb = new TipoBollettino("", "Libretto di Deposito giudiziario", regoleList, regoleDesc, 0, 27);
		mappaTipoBollettino.put("98", tb);		
		// P.Q. Fine MEV 2021/19 Per il denaro (MODELLO A) il versato su deve avere i valori C/C , Libretto e Libretto Depos. Giud.
		
		regoleList = new String[] { "CRO+[0-9]{16}" };
		regoleDesc = "CRO1234567890123456";
		tb = new TipoBollettino("CRO", "Bonifico", regoleList, regoleDesc, 0, 16);
		mappaTipoBollettino.put("11", tb);

		regoleList = new String[] { "POYP+[0-9]{5}" };
		regoleDesc = "POYP12345";
		tb = new TipoBollettino("POYP", "Bonifico postale", regoleList, regoleDesc, 0, 5);
		mappaTipoBollettino.put("12", tb);

		// regoleList = new String[] { "TRN+\\w{30}", "TRN+\\w{32}", "TRN+\\w{35}", "TRN+\\w{36}" };
		// regoleDesc = "TRN seguito da 30, 32, 35 o 36 caratteri alfanumerici";
		// tb = new TipoBollettino("TRN", "Bonifico SEPA (TRN)", regoleList, regoleDesc, 0, 36);
		// mappaTipoBollettino.put("13", tb);

		regoleList = new String[] { "[0-9]{4}+VCY+[0-9]{4}" };
		regoleDesc = "1234VCY1234";
		tb = new TipoBollettino("VCY", "Bollettino (VCY)", regoleList, regoleDesc, 4, 4);
		mappaTipoBollettino.put("14", tb);

		regoleList = new String[] { "[0-9]{4}+VCYL+[0-9]{4}" };
		regoleDesc = "1234VCYL1234";
		tb = new TipoBollettino("VCYL", "Bollettino (VCYL)", regoleList, regoleDesc, 4, 4);
		mappaTipoBollettino.put("15", tb);

		regoleList = new String[] { "[0-9]{4}+VEY+[0-9]{4}" };
		regoleDesc = "1234VEY1234";
		tb = new TipoBollettino("VEY", "Distinta di versamento (VEY)", regoleList, regoleDesc, 4, 4);
		mappaTipoBollettino.put("16", tb);

		regoleList = new String[] { "[0-9]{4}+LVE+[0-9]{4}" };
		regoleDesc = "1234LVE1234";
		tb = new TipoBollettino("LVE", "Distinta di versamento (LVE)", regoleList, regoleDesc, 4, 4);
		mappaTipoBollettino.put("17", tb);

		regoleList = new String[] { "PGR+[0-9]{4}" };
		regoleDesc = "PGR1234";
		tb = new TipoBollettino("PGR", "Postagiro", regoleList, regoleDesc, 0, 4);
		mappaTipoBollettino.put("18", tb);

	}

	private boolean testRegEx(String str, String[] regList) {
		boolean ret = true;
		if (str != null && !str.equals("") && regList != null) {
			ret = false;
			for (String reg : regList) {
				if (str.matches(reg)) {
					ret = true;
				}
			}
		} else {
			ret = false;
		}
		return ret;
	}

	private String dimensionaCella(String size) {
		String ret = size;
		try {
			int appo = Integer.parseInt(ret);
			if (appo > 0) {
				appo = appo + 3; // su internet explorer appare una fastidiosa x
				if (appo < 10) {
					appo = 10; // valore minimo per farla apparire
				}
				ret = "" + appo;
			}
		} catch (Exception e) {
		}
		return ret;
	}

	public void validate(Object target, Errors errors) {
	}

	public boolean supports(Class<?> clazz) {
		return FugController.class.isAssignableFrom(clazz);
	}

	public static Map<String, String> getElencoListaVersato(boolean isModelloA) {
		Map<String, String> ret = new LinkedHashMap<String, String>();
		for (Map.Entry<String, TipoBollettino> elem : mappaTipoBollettino.entrySet()) {
			TipoBollettino tb = elem.getValue();
			if(isModelloA) {				
				if (elem.getKey().equals("1") || elem.getKey().equals("3") || elem.getKey().equals("98")) {
			ret.put(elem.getKey(), tb.getLabel());
		}
			} else {
				if (!elem.getKey().equals("1") && !elem.getKey().equals("3") && !elem.getKey().equals("98")) {
					ret.put(elem.getKey(), tb.getLabel());
				}
			}
		}
		return ret;
	}

	public static String getTagTipoBollettino(String idBollettino) {
		String ret = "";
		if (mappaTipoBollettino.containsKey(idBollettino)) {
			ret = mappaTipoBollettino.get(idBollettino).getTag();
		}
		return ret;
	}

	public void parseNumBollettino(BeneForm bene, ModelMap modelMap, HttpServletRequest request) {
		// valori di default
		String tag = "";
		String maxSx = "0";
		String maxDx = "4";
		String numSx = "";
		String numDx = "";

		String numSxForm = request.getParameter("numBollettinoSx");
		String numDxForm = request.getParameter("numBollettinoDx");

		try {
			// analizza il tipo bollettino selezionato
			String tipoVersamento = bene.getFlgVersamento().getId();
			TipoBollettino tb = mappaTipoBollettino.get(tipoVersamento);

			if (tb != null) {
				// predispone i valori di formattazione per il tipo bollettino scelto
				tag = tb.getTag();
				maxSx = tb.getSizeSx();
				maxDx = tb.getSizeDx();

				try {
					// scompone il numero di bollettino
					String numBollettino = bene.getNumBollettino();
					String[] sector = numBollettino.split(tag, 2);
					numSx = sector[0];
					numDx = sector[1];
				} catch (Exception e) {
				}

			}
		} catch (Exception e) {
		}

		String sizeSx = dimensionaCella(maxSx);
		String sizeDx = dimensionaCella(maxDx);

		modelMap.addAttribute("mappaTipoBollettino", mappaTipoBollettino);
		modelMap.addAttribute("numBollettinoSx", numSx);
		modelMap.addAttribute("numBollettinoDx", numDx);
		modelMap.addAttribute("numBollettinoCx", tag);
		modelMap.addAttribute("maxBollettinoSx", maxSx);
		modelMap.addAttribute("sizeBollettinoSx", sizeSx);
		modelMap.addAttribute("maxBollettinoDx", maxDx);
		modelMap.addAttribute("sizeBollettinoDx", sizeDx);
	}

	public void validateSceltaCheck(BeneForm bene, BindingResult result, HttpServletRequest request) {
		if ((bene.getListaIdCheckbox() == null) || ((bene.getListaIdCheckbox() != null) && (bene.getListaIdCheckbox().isEmpty() || (bene.getListaIdCheckbox().size() > 1)))) {
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			result.reject(Constants.MESSAGE);
		}
	}

	public void validateComboModello(BeneForm beneForm, Errors errors, HttpServletRequest request) {
		if (beneForm.getModelloFugSelected().getId().equals("-1")) {
			errors.rejectValue("modelloFugSelected.id", "NotNull");
		}
	}

	public void validateSelLista(long sel, BindingResult errors, HttpServletRequest request) {
		if (sel <= 0) {
			super.setMessage(Constants.MESSAGE, "table.item.selection", null, false);
			errors.reject(Constants.MESSAGE);
		}
	}
	
	
	
	
	public void validaBeneOpFittizioFug(BeneForm beneForm, BindingResult errors) {
				
		//ValidationUtil.required("modelloVeicolo", errors);
		//F.C. 09/11/2021 tolto controllo iban per validare se e' un ente fittizio
		if(ValidationUtil.checkAllEmpty(errors, "codiceAbi","codiceCab.id","provincia.id","descOperFinanz.id","numeroRegistrazione","descrizioneEnte")){
			// P.Q. Inizio MEV 2021/19 Se denaro ricavato da vendita (sottocategoria 206) ne permetto l'invio 
			if (beneForm.getSottocategoria() != null && !beneForm.getSottocategoria().getId().equals("206")) {
				super.setMessage(Constants.MESSAGE, "error.bene.fug.ente.fittizio", null, false);
				errors.reject(Constants.MESSAGE);
			}
			// P.Q. Fine MEV 2021/19 Se denaro ricavato da vendita (sottocategoria 206) ne permetto l'invio			
		}		
	}

	public void validaBene(BeneForm beneForm, BindingResult result) {
		if (beneForm.getSottocategoria().getId().equals("35") || beneForm.getSottocategoria().getId().equals("206")) {
			if (beneForm.getFlgVersamento().getId().equals("-1") || beneForm.getFlgVersamento().getId().equals("-1,")) {
				result.rejectValue("flgVersamento.id", "NotNull");
			} else {
				validaBollettino(beneForm, result);
			}
		}
		if (beneForm.getValutaEur() == null || beneForm.getValutaEur().equals("")) {
			if ((beneForm.getTipoValuta() == null || beneForm.getTipoValuta().getId() == null) && beneForm.getValutaEst() == null)
				result.rejectValue("valutaEur", "NotNull");
			else if (beneForm.getTipoValuta() != null && beneForm.getTipoValuta().getId() != null && beneForm.getValutaEst() == null)
				result.rejectValue("valutaEst", "NotNull");
		}
		if (beneForm.getModelloFugSelected().getId().equals("-1")) {
			result.rejectValue("modelloFugSelected.id", "NotNull");
		}
	}

	public void validaAggBene(BeneForm beneForm, BindingResult result) {
		if (beneForm.getSottocategoria().getId().equals("35") || beneForm.getSottocategoria().getId().equals("206")) {
			if (beneForm.getFlgVersamento().getId().equals("-1") || beneForm.getFlgVersamento().getId().equals("-1,")) {
				result.rejectValue("flgVersamento.id", "NotNull");
			} else {
				validaBollettino(beneForm, result);
			}
		}
		if ((beneForm.getValutaEur() == null || beneForm.getValutaEur().equals("")) && beneForm.getTipoValuta().getId().equals("-1")) {
			result.rejectValue("valutaEur", "NotNull");
		}
		if ((beneForm.getValutaEst() == null || beneForm.getValutaEst().equals("")) && !beneForm.getTipoValuta().getId().equals("-1")) {
			result.rejectValue("valutaEst", "NotNull");
		}
	}

	private void validaBollettino(BeneForm beneForm, BindingResult result) {
		boolean test = true;
		String bollId = null;
		String bollLabel = null;
		String[] ruleList = null;
		String ruleDesc = null;
		if (beneForm != null && beneForm.getFlgVersamento() != null && beneForm.getFlgVersamento().getId() != null) {
			bollId = beneForm.getFlgVersamento().getId();
			if (mappaTipoBollettino.containsKey(bollId)) {
				TipoBollettino tb = mappaTipoBollettino.get(bollId);
				bollLabel = tb.getLabel();
				ruleList = tb.getRegoleList();
				ruleDesc = tb.getRegoleDesc();
				test = testRegEx(beneForm.getNumBollettino(), ruleList);
			}
		}

		if (!test) {
			String error = "error.bollettino.formato";
			String args[] = { bollLabel, ruleDesc };
			result.rejectValue("numBollettino", error, args, null);
		}
	}

	public void validateDate(FugForm fugForm, BindingResult errors, HttpServletRequest request) {

		if ((fugForm.getDataInvioDa() != null && fugForm.getDataInvioA() != null) && fugForm.getDataInvioDa().after(fugForm.getDataInvioA())) {
			super.setMessage(Constants.MESSAGE, "error.dataDa.dataA", null, false);
			errors.reject(Constants.MESSAGE);
		}
	}

	@SuppressWarnings("rawtypes")
	public void validaSoggetti(BeneForm beneForm, Errors errors, HttpServletRequest request, ModelMap modelMap) {

		List<Message> customMessageList = new ArrayList<Message>();

		List listaSoggetti = beneForm.getListaSoggetti();
		if (listaSoggetti != null) {
			Iterator iterSogg = listaSoggetti.iterator();
			while (iterSogg.hasNext()) {
				Map<String, Object> row = (Map<String, Object>) iterSogg.next();
				TPersonaFisica currentPersonaF = (TPersonaFisica) row.get("pers_fisica");
				TPersonaGiur currentPersonaG = (TPersonaGiur) row.get("pers_giuridica");
				TAnStato currentStato = (TAnStato) row.get("stato");
				TAnComuni currentComune = (TAnComuni) row.get("comuni");
				TAnProvincia currentProvincia = (TAnProvincia) row.get("provincia");
				TRelProcSogint currentSogint = (TRelProcSogint) row.get("sogInt");
				String SoggettoMessage = "";
				if (currentPersonaF != null) {
					if (currentPersonaF.getDescCodiceFiscale() == null) {
						SoggettoMessage = currentPersonaF.getDescCognome() + " " + currentPersonaF.getDescNome();
						super.addMessageIntoList(customMessageList, Constants.MESSAGE, "error.soggeto.codiceFiscale", SoggettoMessage);
					}
				}
				if (currentSogint != null) {
					if (currentSogint.getDescIndirDom() == null) {
						super.addMessageIntoList(customMessageList, Constants.MESSAGE, "error.soggeto.domicilio", SoggettoMessage);
					}
				}
				if (currentPersonaG != null) {
					SoggettoMessage = currentPersonaG.getDescRagioneSoc();
					currentPersonaG.getDescPartitaIva();
					if (currentPersonaG.getDescPartitaIva() == null) {
						super.addMessageIntoList(customMessageList, Constants.MESSAGE, "error.soggeto.partitaIVA", SoggettoMessage);
					}
				}
				if (currentStato != null) {
					if (currentStato.getDescSiglaStato() == null) {
						super.addMessageIntoList(customMessageList, Constants.MESSAGE, "error.soggeto.statoNascita", SoggettoMessage);
					}
				}
			}
		}
		super.setMessages(customMessageList, false);
	}

}
