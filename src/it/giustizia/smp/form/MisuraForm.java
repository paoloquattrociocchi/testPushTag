package it.giustizia.smp.form;

import it.giustizia.smp.validation.date.ModernDate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

/**
 * @author Almaviva S.p.A.
 *         classe per la gestione dei tipi di misura, sia sui beni che sui
 *         soggetti
 */
public class MisuraForm {

	@NumberFormat
	@Min(value = 0)
	@Max(value = 5)
	private Short			anni;
	private String			art9c1;
	private String			art9c1Prop;	
	private String			art67;
	private String          art67Prop;
	private String			cauzione;
	private String          cauzioneApp = null;
	private String          cauzioneProp;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date            dataDefinitivita;
	private ComboForm		decisioneMisuraPersonale;
	private ComboForm		decisioneMisuraPersonaleIIGrado;
	private ComboForm		decisioneMisuraPersonaleSentenzaCassazione;
	
	private ComboForm		decisioneCauzioneSentenzaCassazione;
	private ComboForm		decisioneArt67SentenzaCassazione;
	private String			descrizioneMisura;				// patrimoniale,
	private boolean         disabledCheckDefinitivita = false;
	private String          durata;
	@NumberFormat
	@Min(value = 0)
	@Max(value = 31)
	private Short			giorni;
	// personale o
	// entrambe
	private long			idBene;
	private long			idMisura;
	private String			idMisureCaricate;
	private List<Long>		idSelezionati;
	private long			idSoggetto;
	private List<Long>		idVecchiePrescrizioni;
	@NumberFormat(pattern = "###,###.##")
	private BigDecimal		importo;
   @NumberFormat(pattern = "###,###.##")
    private BigDecimal      importoApp;
	private String			localita;
	@NumberFormat
	@Min(value = 0)
	@Max(value = 12)
	private Short			mesi;
	private ComboForm		normativaDiRiferimento;
	private String			note;
	private String          noteApp;
	private ComboForm		prescrizione;
	private ComboForm		tipoMisura;
	private String			ulterioriPrescrizioni;
	private String			ulterioriPrescrizioniVecchie;
	private List<ComboForm>	vecchiePrescrizioni;

	private String descEsito;
	private String descEsito2;
	private long idMisuraPersonale;
	private Character		flagMisuraValido;
	private String carattereMisura="";
	
	@NumberFormat(pattern = "###,###.##")
	private BigDecimal		importoCauzione;
	
	/**
	 * @return the anni
	 */
	public Short getAnni() {

		return anni;
	}

	/**
	 * @return the art67
	 */
	public String getArt67() {

		return art67;
	}

	
    /**
     * @return the art67Prop
     */
    public String getArt67Prop() {
    
        return art67Prop;
    }

    /**
	 * @return the cauzione
	 */
	public String getCauzione() {

		return cauzione;
	}

	
    /**
     * @return the cauzioneApp
     */
    public String getCauzioneApp() {
    
        return cauzioneApp;
    }

    /**
     * @return the cauzioneProp
     */
    public String getCauzioneProp() {
    
        return cauzioneProp;
    }

    /**
     * @return the dataDefinitivita
     */
    public Date getDataDefinitivita() {
    
        return dataDefinitivita;
    }

    /**
	 * @return the decisioneMisuraPersonale
	 */
	public ComboForm getDecisioneMisuraPersonale() {

		return decisioneMisuraPersonale;
	}

	/**
	 * @return the descrizioneMisura
	 */
	public String getDescrizioneMisura() {

		return descrizioneMisura;
	}

	
    /**
     * @return the disabledCheckDefinitivita
     */
    public boolean isDisabledCheckDefinitivita() {
    
        return disabledCheckDefinitivita;
    }

    /**
     * @return the durata
     */
    public String getDurata() {
    
        durata = "";
        short anni = 0;
        short mesi = 0;
        short giorni = 0;
        if(this.getAnni()!=null)
            anni = this.getAnni();
        if(this.getMesi()!=null)
            mesi = this.getMesi();
        if(this.getGiorni()!=null)
            giorni = this.getGiorni();
        
        if(anni>0){
            if(anni==1)
                durata = durata + anni + " anno ";
            else durata = durata + anni + " anni ";
        }
        if(mesi>0){
            if(mesi==1)
                durata = durata + "e " + mesi + " mese ";
            else durata = durata + "e " + mesi + " mesi ";
        }
        if(giorni>0){
            if(giorni==1)
                durata = durata + "e " + giorni + " giorno ";
            else durata = durata + "e " + giorni + " giorni ";
        }          
        return durata;
    }

    /**
	 * @return the giorni
	 */
	public Short getGiorni() {

		return giorni;
	}

	/**
	 * @return the idBene
	 */
	public long getIdBene() {

		return idBene;
	}

	/**
	 * @return the idMisura
	 */
	public long getIdMisura() {

		return idMisura;
	}

	/**
	 * @return the idMisureCaricate
	 */
	public String getIdMisureCaricate() {

		return idMisureCaricate;
	}

	/**
	 * @return the idSelezionati
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Long> getIdSelezionati() {

		if ((idSelezionati == null) && (idVecchiePrescrizioni != null)) {
			List<Long> idSelVecchiePresc = new ArrayList();
			for (int i = 0; i < idVecchiePrescrizioni.size(); i++) {
				idSelVecchiePresc.add(idVecchiePrescrizioni.get(i));
			}
			return idSelVecchiePresc;
		}
		if ((idSelezionati != null) && (idVecchiePrescrizioni != null)) {
			for (int i = 0; i < idVecchiePrescrizioni.size(); i++) {
				idSelezionati.add(idVecchiePrescrizioni.get(i));
			}
		}
		return idSelezionati;
	}

	/**
	 * @return the idSoggetto
	 */
	public long getIdSoggetto() {

		return idSoggetto;
	}

	/**
	 * @return the idVecchiePrescrizioni
	 */
	public List<Long> getIdVecchiePrescrizioni() {

		return idVecchiePrescrizioni;
	}

	/**
	 * @return the importo
	 */
	public BigDecimal getImporto() {

		return importo;
	}

	
    /**
     * @return the importoApp
     */
    public BigDecimal getImportoApp() {
    
        return importoApp;
    }

    /**
	 * @return the localita
	 */
	public String getLocalita() {

		return localita;
	}

	/**
	 * @return the mesi
	 */
	public Short getMesi() {

		return mesi;
	}

	/**
	 * @return the normativaDiRiferimento
	 */
	public ComboForm getNormativaDiRiferimento() {

		return normativaDiRiferimento;
	}

	/**
	 * @return the note
	 */
	public String getNote() {

		return note;
	}

	
    /**
     * @return the noteApp
     */
    public String getNoteApp() {
    
        return noteApp;
    }

    /**
	 * @return the prescrizione
	 */
	public ComboForm getPrescrizione() {

		return prescrizione;
	}

	/**
	 * @return the tipoMisura
	 */
	public ComboForm getTipoMisura() {

		return tipoMisura;
	}

	/**
	 * @return the ulterioriPrescrizioni
	 */
	public String getUlterioriPrescrizioni() {

		return ulterioriPrescrizioni;
	}

	/**
	 * @return the ulterioriPrescrizioniVecchie
	 */
	public String getUlterioriPrescrizioniVecchie() {

		return ulterioriPrescrizioniVecchie;
	}

	/**
	 * @return the vecchiePrescrizioni
	 */
	public List<ComboForm> getVecchiePrescrizioni() {

		return vecchiePrescrizioni;
	}

	/**
	 * @param anni
	 *            the anni to set
	 */
	public void setAnni(Short anni) {

		this.anni = anni;
	}

	/**
	 * @param art67
	 *            the art67 to set
	 */
	public void setArt67(String art67) {

		this.art67 = art67;
	}
	
    /**
     * @param art67Prop the art67Prop to set
     */
    public void setArt67Prop(String art67Prop) {
    
        this.art67Prop = art67Prop;
    }

    /**
	 * @param cauzione
	 *            the cauzione to set
	 */
	public void setCauzione(String cauzione) {

		this.cauzione = cauzione;
	}

	   
    /**
     * @param cauzioneApp the cauzioneApp to set
     */
    public void setCauzioneApp(String cauzioneApp) {
    
        this.cauzioneApp = cauzioneApp;
    }

    /**
     * @param cauzioneProp
     *            the cauzioneProp to set
     */
    public void setCauzioneProp(String cauzioneProp) {
    
        this.cauzioneProp = cauzioneProp;
    }

    /**
     * @param dataDefinitivita the dataDefinitivita to set
     */
    public void setDataDefinitivita(Date dataDefinitivita) {
    
        this.dataDefinitivita = dataDefinitivita;
    }

    /**
	 * @param decisioneMisuraPersonale
	 *            the decisioneMisuraPersonale to set
	 */
	public void setDecisioneMisuraPersonale(ComboForm decisioneMisuraPersonale) {

		this.decisioneMisuraPersonale = decisioneMisuraPersonale;
	}

	/**
	 * @param descrizioneMisura
	 *            the descrizioneMisura to set
	 */
	public void setDescrizioneMisura(String descrizioneMisura) {

		this.descrizioneMisura = descrizioneMisura;
	}

	
    /**
     * @param disabledCheckDefinitivita the disabledCheckDefinitivita to set
     */
    public void setDisabledCheckDefinitivita(boolean disabledCheckDefinitivita) {
    
        this.disabledCheckDefinitivita = disabledCheckDefinitivita;
    }

    /**
	 * @param giorni
	 *            the giorni to set
	 */
	public void setGiorni(Short giorni) {

		this.giorni = giorni;
	}

	/**
	 * @param idBene
	 *            the idBene to set
	 */
	public void setIdBene(long idBene) {

		this.idBene = idBene;
	}

	/**
	 * @param idMisura
	 *            the idMisura to set
	 */
	public void setIdMisura(long idMisura) {

		this.idMisura = idMisura;
	}

	/**
	 * @param idMisureCaricate
	 *            the idMisureCaricate to set
	 */
	public void setIdMisureCaricate(String idMisureCaricate) {

		this.idMisureCaricate = idMisureCaricate;
	}

	/**
	 * @param idSelezionati
	 *            the idSelezionati to set
	 */
	public void setIdSelezionati(List<Long> idSelezionati) {

		this.idSelezionati = idSelezionati;
	}

	/**
	 * @param idSoggetto
	 *            the idSoggetto to set
	 */
	public void setIdSoggetto(long idSoggetto) {

		this.idSoggetto = idSoggetto;
	}

	/**
	 * @param idVecchiePrescrizioni
	 *            the idVecchiePrescrizioni to set
	 */
	public void setIdVecchiePrescrizioni(List<Long> idVecchiePrescrizioni) {

		this.idVecchiePrescrizioni = idVecchiePrescrizioni;
	}

	/**
	 * @param importo
	 *            the importo to set
	 */
	public void setImporto(BigDecimal importo) {

		this.importo = importo;
	}

	
    /**
     * @param importoApp the importoApp to set
     */
    public void setImportoApp(BigDecimal importoApp) {
    
        this.importoApp = importoApp;
    }

    /**
	 * @param localita
	 *            the localita to set
	 */
	public void setLocalita(String localita) {

		this.localita = localita;
	}

	/**
	 * @param mesi
	 *            the mesi to set
	 */
	public void setMesi(Short mesi) {

		this.mesi = mesi;
	}

	/**
	 * @param normativaDiRiferimento
	 *            the normativaDiRiferimento to set
	 */
	public void setNormativaDiRiferimento(ComboForm normativaDiRiferimento) {

		this.normativaDiRiferimento = normativaDiRiferimento;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {

		this.note = note;
	}

	
    /**
     * @param noteApp the noteApp to set
     */
    public void setNoteApp(String noteApp) {
    
        this.noteApp = noteApp;
    }

    /**
	 * @param prescrizioni
	 *            the prescrizione to set
	 */
	public void setPrescrizione(ComboForm prescrizione) {

		this.prescrizione = prescrizione;
	}

	/**
	 * @param tipoMisura
	 *            the tipoMisura to set
	 */
	public void setTipoMisura(ComboForm tipoMisura) {

		this.tipoMisura = tipoMisura;
	}

	/**
	 * @param ulterioriPrescrizioni
	 *            the ulterioriPrescrizioni to set
	 */
	public void setUlterioriPrescrizioni(String ulterioriPrescrizioni) {

		this.ulterioriPrescrizioni = ulterioriPrescrizioni;
	}

	/**
	 * @param ulterioriPrescrizioniVecchie
	 *            the ulterioriPrescrizioniVecchie to set
	 */
	public void setUlterioriPrescrizioniVecchie(String ulterioriPrescrizioniVecchie) {

		this.ulterioriPrescrizioniVecchie = ulterioriPrescrizioniVecchie;
	}

	/**
	 * @param vecchiePrescrizioni
	 *            the vecchiePrescrizioni to set
	 */
	public void setVecchiePrescrizioni(List<ComboForm> vecchiePrescrizioni) {

		this.vecchiePrescrizioni = vecchiePrescrizioni;
	}

	public Character getFlagMisuraValido() {
		return flagMisuraValido;
	}

	public void setFlagMisuraValido(Character flagMisuraValido) {
		this.flagMisuraValido = flagMisuraValido;
	}

	public long getIdMisuraPersonale() {
		return idMisuraPersonale;
	}

	public void setIdMisuraPersonale(long idMisuraPersonale) {
		this.idMisuraPersonale = idMisuraPersonale;
	}

	public String getDescEsito() {
		return descEsito;
	}

	public void setDescEsito(String descEsito) {
		this.descEsito = descEsito;
	}
	
	public String getDescEsito2() {
		return descEsito2;
	}

	public void setDescEsito2(String descEsito2) {
		this.descEsito2 = descEsito2;
	}	

	public ComboForm getDecisioneMisuraPersonaleIIGrado() {
		return decisioneMisuraPersonaleIIGrado;
	}

	public void setDecisioneMisuraPersonaleIIGrado(
			ComboForm decisioneMisuraPersonaleIIGrado) {
		this.decisioneMisuraPersonaleIIGrado = decisioneMisuraPersonaleIIGrado;
	}

	public String getCarattereMisura() {
		return carattereMisura;
	}

	public void setCarattereMisura(String carattereMisura) {
		this.carattereMisura = carattereMisura;
	}

	public ComboForm getDecisioneMisuraPersonaleSentenzaCassazione() {
		return decisioneMisuraPersonaleSentenzaCassazione;
	}

	public void setDecisioneMisuraPersonaleSentenzaCassazione(
			ComboForm decisioneMisuraPersonaleSentenzaCassazione) {
		this.decisioneMisuraPersonaleSentenzaCassazione = decisioneMisuraPersonaleSentenzaCassazione;
	}

	public String getArt9c1() {
		return art9c1;
	}

	public void setArt9c1(String art9c1) {
		this.art9c1 = art9c1;
	}

	public ComboForm getDecisioneCauzioneSentenzaCassazione() {
		return decisioneCauzioneSentenzaCassazione;
	}

	public void setDecisioneCauzioneSentenzaCassazione(
			ComboForm decisioneCauzioneSentenzaCassazione) {
		this.decisioneCauzioneSentenzaCassazione = decisioneCauzioneSentenzaCassazione;
	}

	public ComboForm getDecisioneArt67SentenzaCassazione() {
		return decisioneArt67SentenzaCassazione;
	}

	public void setDecisioneArt67SentenzaCassazione(
			ComboForm decisioneArt67SentenzaCassazione) {
		this.decisioneArt67SentenzaCassazione = decisioneArt67SentenzaCassazione;
	}
	
	public BigDecimal getImportoCauzione() {
		return importoCauzione;
	}

	public void setImportoCauzione(BigDecimal importoCauzione) {
		this.importoCauzione = importoCauzione;
	}	
	
}
