package it.giustizia.smp.webservice.client.agenziaentrate;
/***
 * Wrapper della classe restituita dal ws di controllo del Codice Fiscale 
 * @author m.prisco
 *
 */
public class EsitoControlloCFePIWrapper {
	private Boolean invocazioneFallita = false;
	private String codiceFiscale;
	private String partitaIva;
	private String errore;
	
	public EsitoControlloCFePIWrapper(DatiPersonaFisicaType datiPersonaFisicaType) {
		if (datiPersonaFisicaType==null) {
			invocazioneFallita = true;
			errore = "Servizio non disponibile, riprovare piu' tardi.";
		} else  if (datiPersonaFisicaType.getMessaggio()!=null) {
			invocazioneFallita = true;
			errore = "Il servizio ha restituito il seguente errore: " + datiPersonaFisicaType.getMessaggio().getDescrizioneMessaggio();
		} else {
			codiceFiscale = datiPersonaFisicaType.getPersonaFisica().getCodiceFiscale();
		}
	}

	public EsitoControlloCFePIWrapper(DatiSoggettoDiversoType datiSoggettoDiversoType) {
		if (datiSoggettoDiversoType==null) {
			invocazioneFallita = true;
			errore = "Servizio non disponibile, riprovare piu' tardi.";
		} else  if (datiSoggettoDiversoType.getMessaggio()!=null) {
			invocazioneFallita = true;
			errore = "Il servizio ha restituito il seguente errore: " + datiSoggettoDiversoType.getMessaggio().getDescrizioneMessaggio();
		} else {
			partitaIva = datiSoggettoDiversoType.getSoggettoDiverso().getCodiceFiscale();
		}
	}
	
	
	public Boolean isInvocazioneFallita() {
		return invocazioneFallita;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public String getErrore() {
		return errore;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	
	
	
	
	
}
