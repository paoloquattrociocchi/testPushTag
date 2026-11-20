package it.giustizia.smp.form;

import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

public class DifensoreForm {

    @Pattern(regexp = Constants.RegExp.CAP)
    private String        capDomicilioLegale;
    @Pattern(regexp = Constants.RegExp.CAP)
    private String        capResidenza;
    private String        codiceFiscale;
    /* 
	 * ISSUE MEV : per la segnalazione INC000001822009, il sistema deve accettare difensori da reginde con
	 * 				qualsiasi carattere speciale nei campi nome e cognome.
	 * 				In alternativa si può inserire la regexp --> [^[:digit:]] //tutti i caratteri tranne i numeri
	 * Numero MEV : 13
	 * Autore    : Gioggi
	 * Data      : 25 giu 2018
	 * Branch    : MEV_13
	 */
//    @Pattern(regexp = Constants.RegExp.COGNOME)
    private String        cognome;
//    @Pattern(regexp = Constants.RegExp.NOME)
    private String        nome;
	//***** FINE INTERVENTO MEV_13 *****//
    private String        comuneDomicilioLegale;
    private ComboForm     comuneDomicilioLegaleCombo;
    private ComboForm     comuneNascitaCombo;
    private String        comuneResidenza;
    private ComboForm     comuneResidenzaCombo;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date          dataNascita;
//  private String        descrizioneDifensore;
//	private String        descrizioneDomicilio;
    private String        emailDomicilioLegale;
    private String        emailResidenza;
    @Pattern(regexp = Constants.RegExp.TELEFONO)
    private String        faxDomicilioLegale;
    @Pattern(regexp = Constants.RegExp.TELEFONO)
    private String        faxResidenza;
    private Long          idDifensore;
    private String        idReginde;
    private String        indirizzoDomicilioLegale;
    private IndirizzoForm indirizzoDomicilioLegaleForm;
    private String        indirizzoResidenza;
    private IndirizzoForm indirizzoResidenzaForm;
    private String        luogoNascita;
    private String        pec;
    private String        provinciaDomicilioLegale;
    private String        provinciaNascita;
    private String        provinciaResidenza;
    private ComboForm     statoDomicilioLegale;
    private ComboForm     statoNascita;
    private ComboForm     statoResidenza;
    private String        status;
    @Pattern(regexp = Constants.RegExp.TELEFONO)
    private String        telefonoDomicilioLegale;
    @Pattern(regexp = Constants.RegExp.TELEFONO)
    private String        telefonoResidenza;

    /**
     * @return the capDomicilioLegale
     */
    public String getCapDomicilioLegale() {

        return capDomicilioLegale;
    }

    /**
     * @return the capResidenza
     */
    public String getCapResidenza() {

        return capResidenza;
    }

    /**
     * @return the codiceFiscale
     */
    public String getCodiceFiscale() {

        return codiceFiscale;
    }

    /**
     * @return the cognome
     */
    public String getCognome() {

        return cognome;
    }

    /**
     * @return the comuneDomicilioLegale
     */
    public String getComuneDomicilioLegale() {

        if (comuneDomicilioLegale != null) {
            return comuneDomicilioLegale;
        }
        StringBuilder comuneDomicilioLegaleCostruito = new StringBuilder();
        if ((statoDomicilioLegale != null) && (statoDomicilioLegale.getDescrizione() !=null) && !statoDomicilioLegale.getDescrizione().equals("-1")) {
            comuneDomicilioLegaleCostruito.append(statoDomicilioLegale.getDescrizione());
        }
        if ((comuneDomicilioLegaleCombo != null) && (comuneDomicilioLegaleCombo.getDescrizione() != null)) {
            comuneDomicilioLegaleCostruito.append(" - ");
            String comuneDomicilioLegaleEstratto = comuneDomicilioLegaleCombo.getDescrizione();
            int indice = comuneDomicilioLegaleCombo.getDescrizione().indexOf("(");
            if (indice > -1) {
                comuneDomicilioLegaleEstratto = comuneDomicilioLegaleCombo.getDescrizione().substring(0, indice);
            }
            comuneDomicilioLegaleCostruito.append(comuneDomicilioLegaleEstratto);
        }
        return comuneDomicilioLegaleCostruito.toString();
    }

    /**
     * @return the comuneDomicilioLegaleCombo
     */
    public ComboForm getComuneDomicilioLegaleCombo() {

        return comuneDomicilioLegaleCombo;
    }

    /**
     * @return the comuneNascitaCombo
     */
    public ComboForm getComuneNascitaCombo() {

        return comuneNascitaCombo;
    }

    /**
     * @return the comuneResidenza
     */
    public String getComuneResidenza() {

        if (comuneResidenza != null) {
            return comuneResidenza;
        }
        StringBuilder comuneResidenzaCostruito = new StringBuilder();
        if ((statoResidenza != null) && (statoResidenza.getDescrizione()!=null) && !statoResidenza.getDescrizione().equals("-1")) {
            comuneResidenzaCostruito.append(statoResidenza.getDescrizione());
        }
        if ((comuneResidenzaCombo != null) && (comuneResidenzaCombo.getDescrizione() != null)) {
            comuneResidenzaCostruito.append(" - ");
            String comuneResidenzaEstratto = comuneResidenzaCombo.getDescrizione();
            int indice = comuneResidenzaCombo.getDescrizione().indexOf("(");
            if (indice > -1) {
                comuneResidenzaEstratto = comuneResidenzaCombo.getDescrizione().substring(0, indice);
            }
            comuneResidenzaCostruito.append(comuneResidenzaEstratto);
        }
        return comuneResidenzaCostruito.toString();
    }

    /**
     * @return the comuneResidenzaCombo
     */
    public ComboForm getComuneResidenzaCombo() {

        return comuneResidenzaCombo;
    }

    /**
     * @return the dataNascita
     */
    public Date getDataNascita() {

        return dataNascita;
    }

    /**
     * @return the descrizioneDifensore
     */
    public String getDescrizioneDifensore() {

        StringBuilder descrizione = new StringBuilder();
        if (cognome != null) {
            descrizione.append(cognome);
        }
        if (nome != null) {
            descrizione.append(" ");
            descrizione.append(nome);
        }
        if ((dataNascita != null) || (luogoNascita != null) || (provinciaNascita != null)) {
            descrizione.append(" nato/a");
            if (luogoNascita != null) {
                descrizione.append(" a ");
                descrizione.append(luogoNascita);
                if (provinciaNascita != null) {
                    descrizione.append(" (");
                    descrizione.append(provinciaNascita);
                    descrizione.append(") ");
                }
            }
            if (dataNascita != null) {
                descrizione.append(" il ");
                descrizione.append(Utility.convertDateToString(dataNascita));
            }
        }
        return descrizione.toString();
    }

    /**
     * @return the descrizioneDomicilio
     */
    public String getDescrizioneDomicilio() {

        StringBuilder descrizione = new StringBuilder();
        if (indirizzoDomicilioLegale != null) {
            descrizione.append(indirizzoDomicilioLegale);
        }
        if (comuneDomicilioLegale != null) {
            descrizione.append("-");
            descrizione.append(comuneDomicilioLegale);
            if (provinciaDomicilioLegale != null) {
                descrizione.append(" (");
                descrizione.append(provinciaDomicilioLegale);
                descrizione.append(") ");
            }
        }
        return descrizione.toString();
    }

    /**
     * @return the emailDomicilioLegale
     */
    public String getEmailDomicilioLegale() {

        return emailDomicilioLegale;
    }

    /**
     * @return the emailResidenza
     */
    public String getEmailResidenza() {

        return emailResidenza;
    }

    /**
     * @return the faxDomicilioLegale
     */
    public String getFaxDomicilioLegale() {

        return faxDomicilioLegale;
    }

    /**
     * @return the faxResidenza
     */
    public String getFaxResidenza() {

        return faxResidenza;
    }

    /**
     * @return the idDifensore
     */
    public Long getIdDifensore() {

        return idDifensore;
    }

    /**
     * @return the idReginde
     */
    public String getIdReginde() {

        return idReginde;
    }

    /**
     * @return the indirizzoDomicilioLegale
     */
    public String getIndirizzoDomicilioLegale() {

        if (indirizzoDomicilioLegale != null) {
            return indirizzoDomicilioLegale;
        }
        StringBuilder indirizzoDomicilioLegaleCostruito = new StringBuilder();
        if (indirizzoDomicilioLegaleForm != null) {
            if ((indirizzoDomicilioLegaleForm.getToponimo() != null) && !indirizzoDomicilioLegaleForm.getToponimo().getDescrizione().equals("-1")) {
                indirizzoDomicilioLegaleCostruito.append(indirizzoDomicilioLegaleForm.getToponimo().getDescrizione());
            }
            if (indirizzoDomicilioLegaleForm.getDenominazione() != null) {
                indirizzoDomicilioLegaleCostruito.append(" ");
                indirizzoDomicilioLegaleCostruito.append(indirizzoDomicilioLegaleForm.getDenominazione());
            }
            if (indirizzoDomicilioLegaleForm.getCivico() != null) {
                indirizzoDomicilioLegaleCostruito.append(" n° ");
                indirizzoDomicilioLegaleCostruito.append(indirizzoDomicilioLegaleForm.getCivico());
            }
        }
        return indirizzoDomicilioLegaleCostruito.toString();
    }

    /**
     * @return the indirizzoDomicilioLegaleForm
     */
    public IndirizzoForm getIndirizzoDomicilioLegaleForm() {

        return indirizzoDomicilioLegaleForm;
    }

    /**
     * @return the indirizzoResidenza
     */
    public String getIndirizzoResidenza() {

        if (indirizzoResidenza != null) {
            return indirizzoResidenza;
        }
        StringBuilder indirizzoResidenzaCostruito = new StringBuilder();
        if (indirizzoResidenzaForm != null) {
            if ((indirizzoResidenzaForm.getToponimo() != null) && !indirizzoResidenzaForm.getToponimo().getDescrizione().equals("-1")) {
                indirizzoResidenzaCostruito.append(indirizzoResidenzaForm.getToponimo().getDescrizione());
            }
            if (indirizzoResidenzaForm.getDenominazione() != null) {
                indirizzoResidenzaCostruito.append(" ");
                indirizzoResidenzaCostruito.append(indirizzoResidenzaForm.getDenominazione());
            }
            if (indirizzoResidenzaForm.getCivico() != null) {
                indirizzoResidenzaCostruito.append(" ");
                indirizzoResidenzaCostruito.append(indirizzoResidenzaForm.getCivico());
            }
        }
        return indirizzoResidenzaCostruito.toString();
    }

    /**
     * @return the indirizzoResidenzaForm
     */
    public IndirizzoForm getIndirizzoResidenzaForm() {

        return indirizzoResidenzaForm;
    }

    /**
     * @return the luogoNascita
     */
    public String getLuogoNascita() {

        if (luogoNascita != null) {
            return luogoNascita;
        }
        StringBuilder luogoNascitaCostruito = new StringBuilder();
        if ((statoNascita != null) && (statoNascita.getDescrizione()!=null) && !statoNascita.getDescrizione().equals("-1")) {
            luogoNascitaCostruito.append(statoNascita.getDescrizione());
        }
        if ((comuneNascitaCombo != null) && (comuneNascitaCombo.getDescrizione() != null)) {
            luogoNascitaCostruito.append(" - ");
            String comuneNascitaEstratto = comuneNascitaCombo.getDescrizione();
            int indice = comuneNascitaCombo.getDescrizione().indexOf("(");
            if (indice > -1) {
                comuneNascitaEstratto = comuneNascitaCombo.getDescrizione().substring(0, indice);
            }
            luogoNascitaCostruito.append(comuneNascitaEstratto);
        }
        return luogoNascitaCostruito.toString();
    }

    /**
     * @return the nome
     */
    public String getNome() {

        return nome;
    }

    /**
     * @return the pec
     */
    public String getPec() {

        return pec;
    }

    /**
     * @return the provinciaDomicilioLegale
     */
    public String getProvinciaDomicilioLegale() {

        if (provinciaDomicilioLegale != null) {
            return provinciaDomicilioLegale;
        } else {
            String provinciaDomicilioLegaleEstratta = null;
            if ((comuneDomicilioLegaleCombo != null) && (comuneDomicilioLegaleCombo.getDescrizione() != null)) {
                int indice = comuneDomicilioLegaleCombo.getDescrizione().indexOf("(");
                if (indice > -1) {
                    provinciaDomicilioLegaleEstratta = comuneDomicilioLegaleCombo.getDescrizione().substring(indice + 1, indice + 3);
                }
            }
            return provinciaDomicilioLegaleEstratta;
        }
    }

    /**
     * @return the provinciaNascita
     */
    public String getProvinciaNascita() {

        if (provinciaNascita != null) {
            return provinciaNascita;
        } else {
            String provinciaNascitaEstratta = null;
            if ((comuneNascitaCombo != null) && (comuneNascitaCombo.getDescrizione() != null)) {
                int indice = comuneNascitaCombo.getDescrizione().indexOf("(");
                if (indice > -1) {
                    provinciaNascitaEstratta = comuneNascitaCombo.getDescrizione().substring(indice + 1, indice + 3);
                }
            }
            return provinciaNascitaEstratta;
        }
    }

    /**
     * @return the provinciaResidenza
     */
    public String getProvinciaResidenza() {

        if (provinciaResidenza != null) {
            return provinciaResidenza;
        } else {
            String provinciaResidenzaEstratta = null;
            if ((comuneResidenzaCombo != null) && (comuneResidenzaCombo.getDescrizione() != null)) {
                int indice = comuneResidenzaCombo.getDescrizione().indexOf("(");
                if (indice > -1) {
                    provinciaResidenzaEstratta = comuneResidenzaCombo.getDescrizione().substring(indice + 1, indice + 3);
                }
            }
            return provinciaResidenzaEstratta;
        }
    }

    /**
     * @return the statoDomicilioLegale
     */
    public ComboForm getStatoDomicilioLegale() {

        return statoDomicilioLegale;
    }

    /**
     * @return the statoNascita
     */
    public ComboForm getStatoNascita() {

        return statoNascita;
    }

    /**
     * @return the statoResidenza
     */
    public ComboForm getStatoResidenza() {

        return statoResidenza;
    }

    /**
     * @return the status
     */
    public String getStatus() {

        return status;
    }

    /**
     * @return the telefonoDomicilioLegale
     */
    public String getTelefonoDomicilioLegale() {

        return telefonoDomicilioLegale;
    }

    /**
     * @return the telefonoResidenza
     */
    public String getTelefonoResidenza() {

        return telefonoResidenza;
    }

    /**
     * @param capDomicilioLegale
     *            the capDomicilioLegale to set
     */
    public void setCapDomicilioLegale(String capDomicilioLegale) {

        this.capDomicilioLegale = capDomicilioLegale;
    }

    /**
     * @param capResidenza
     *            the capResidenza to set
     */
    public void setCapResidenza(String capResidenza) {

        this.capResidenza = capResidenza;
    }

    /**
     * @param codiceFiscale
     *            the codiceFiscale to set
     */
    public void setCodiceFiscale(String codiceFiscale) {

        this.codiceFiscale = codiceFiscale;
    }

    /**
     * @param cognome
     *            the cognome to set
     */
    public void setCognome(String cognome) {

        this.cognome = cognome;
    }

    /**
     * @param comuneDomicilioLegale
     *            the comuneDomicilioLegale to set
     */
    public void setComuneDomicilioLegale(String comuneDomicilioLegale) {

        this.comuneDomicilioLegale = comuneDomicilioLegale;
    }

    /**
     * @param comuneDomicilioLegaleCombo
     *            the comuneDomicilioLegaleCombo to set
     */
    public void setComuneDomicilioLegaleCombo(ComboForm comuneDomicilioLegaleCombo) {

        this.comuneDomicilioLegaleCombo = comuneDomicilioLegaleCombo;
    }

    /**
     * @param comuneNascitaCombo
     *            the comuneNascitaCombo to set
     */
    public void setComuneNascitaCombo(ComboForm comuneNascitaCombo) {

        this.comuneNascitaCombo = comuneNascitaCombo;
    }

    /**
     * @param comuneResidenza
     *            the comuneResidenza to set
     */
    public void setComuneResidenza(String comuneResidenza) {

        this.comuneResidenza = comuneResidenza;
    }

    /**
     * @param comuneResidenzaCombo
     *            the comuneResidenzaCombo to set
     */
    public void setComuneResidenzaCombo(ComboForm comuneResidenzaCombo) {

        this.comuneResidenzaCombo = comuneResidenzaCombo;
    }

    /**
     * @param dataNascita
     *            the dataNascita to set
     */
    public void setDataNascita(Date dataNascita) {

        this.dataNascita = dataNascita;
    }

    /**
     * @param emailDomicilioLegale
     *            the emailDomicilioLegale to set
     */
    public void setEmailDomicilioLegale(String emailDomicilioLegale) {

        this.emailDomicilioLegale = emailDomicilioLegale;
    }

    /**
     * @param emailResidenza
     *            the emailResidenza to set
     */
    public void setEmailResidenza(String emailResidenza) {

        this.emailResidenza = emailResidenza;
    }

    /**
     * @param faxDomicilioLegale
     *            the faxDomicilioLegale to set
     */
    public void setFaxDomicilioLegale(String faxDomicilioLegale) {

        this.faxDomicilioLegale = faxDomicilioLegale;
    }

    /**
     * @param faxResidenza
     *            the faxResidenza to set
     */
    public void setFaxResidenza(String faxResidenza) {

        this.faxResidenza = faxResidenza;
    }

    /**
     * @param idDifensore
     *            the idDifensore to set
     */
    public void setIdDifensore(Long idDifensore) {

        this.idDifensore = idDifensore;
    }

    /**
     * @param idReginde
     *            the idReginde to set
     */
    public void setIdReginde(String idReginde) {

        this.idReginde = idReginde;
    }

    /**
     * @param indirizzoDomicilioLegale
     *            the indirizzoDomicilioLegale to set
     */
    public void setIndirizzoDomicilioLegale(String indirizzoDomicilioLegale) {

        this.indirizzoDomicilioLegale = indirizzoDomicilioLegale;
    }

    /**
     * @param indirizzoDomicilioLegaleForm
     *            the indirizzoDomicilioLegaleForm to set
     */
    public void setIndirizzoDomicilioLegaleForm(IndirizzoForm indirizzoDomicilioLegaleForm) {

        this.indirizzoDomicilioLegaleForm = indirizzoDomicilioLegaleForm;
    }

    /**
     * @param indirizzoResidenza
     *            the indirizzoResidenza to set
     */
    public void setIndirizzoResidenza(String indirizzoResidenza) {

        this.indirizzoResidenza = indirizzoResidenza;
    }

    /**
     * @param indirizzoResidenzaForm
     *            the indirizzoResidenzaForm to set
     */
    public void setIndirizzoResidenzaForm(IndirizzoForm indirizzoResidenzaForm) {

        this.indirizzoResidenzaForm = indirizzoResidenzaForm;
    }

    /**
     * @param luogoNascita
     *            the luogoNascita to set
     */
    public void setLuogoNascita(String luogoNascita) {

        this.luogoNascita = luogoNascita;
    }

    /**
     * @param nome
     *            the nome to set
     */
    public void setNome(String nome) {

        this.nome = nome;
    }

    /**
     * @param pec
     *            the pec to set
     */
    public void setPec(String pec) {

        this.pec = pec;
    }

    /**
     * @param provinciaDomicilioLegale
     *            the provinciaDomicilioLegale to set
     */
    public void setProvinciaDomicilioLegale(String provinciaDomicilioLegale) {

        this.provinciaDomicilioLegale = provinciaDomicilioLegale;
    }

    /**
     * @param provinciaNascita
     *            the provinciaNascita to set
     */
    public void setProvinciaNascita(String provinciaNascita) {

        this.provinciaNascita = provinciaNascita;
    }

    /**
     * @param provinciaResidenza
     *            the provinciaResidenza to set
     */
    public void setProvinciaResidenza(String provinciaResidenza) {

        this.provinciaResidenza = provinciaResidenza;
    }

    /**
     * @param statoDomicilioLegale
     *            the statoDomicilioLegale to set
     */
    public void setStatoDomicilioLegale(ComboForm statoDomicilioLegale) {

        this.statoDomicilioLegale = statoDomicilioLegale;
    }

    /**
     * @param statoNascita
     *            the statoNascita to set
     */
    public void setStatoNascita(ComboForm statoNascita) {

        this.statoNascita = statoNascita;
    }

    /**
     * @param statoResidenza
     *            the statoResidenza to set
     */
    public void setStatoResidenza(ComboForm statoResidenza) {

        this.statoResidenza = statoResidenza;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {

        this.status = status;
    }

    /**
     * @param telefonoDomicilioLegale
     *            the telefonoDomicilioLegale to set
     */
    public void setTelefonoDomicilioLegale(String telefonoDomicilioLegale) {

        this.telefonoDomicilioLegale = telefonoDomicilioLegale;
    }

    /**
     * @param telefonoResidenza
     *            the telefonoResidenza to set
     */
    public void setTelefonoResidenza(String telefonoResidenza) {

        this.telefonoResidenza = telefonoResidenza;
    }
}
