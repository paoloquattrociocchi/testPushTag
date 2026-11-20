package it.giustizia.smp.form;

import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.validation.date.ModernDate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

/**
 * SoggettoForm
 * 
 * @author Almaviva S.p.A.
 *         classe form per la gestione del soggetto
 */
public class SoggettoForm extends ProcedimentoForm {

    @Pattern(regexp = Constants.RegExp.COGNOME)
    private String                    amministratore;
    @NumberFormat(style = Style.NUMBER, pattern = "####")
    private Short                     annoRegistroGratuitoPatrocinio;
    private String                    annotazioni;
    @NumberFormat(pattern = "###,###.###")
    private BigDecimal                capitaleSociale;
    private String                    codiceFiscale;
    private String                    codiMotivo;
    @Pattern(regexp = Constants.RegExp.COGNOME)
    private String                    cognome;
    @Pattern(regexp = Constants.RegExp.COGNOME)
    private String                    cognomeMaternita;
    @Pattern(regexp = Constants.RegExp.COGNOME)
    private String                    cognomePaternita;
    private ComboForm                 comune;
    private ComboForm                 comuneDimora;
    private ComboForm                 comuneDomicilio;
    private ComboForm                 comuneDomicilioEletto;
    private ComboForm                 comuneMorte;
    private ComboForm                 comuneNascita;
    private ComboForm                 comuneResidenza;
    private ComboForm                 comuneSedeLegale;
    private ComboForm                 condizioneGiuridica;
    private ComboForm                 decisioneErroreMaterialeIGrado;
    private ComboForm                 decisioneErroreMaterialeIIIGrado;    
    private String                    cui;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date                      dataEsecuzione;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date                      dataIscrizione;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date                      dataIstanzaGratuitoPatrocinio;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date                      dataMorte;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date                      dataNascita;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date                      dataProvvedimentoGratuitoPatrocinio;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date                      dataRevocaGratuitoPatrocinio;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date                      dataEmissioneAtto;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date                      dataRicezioneSentenza;  
    
    private String				    numeAnnoSentenza;
    private String					numeSentenza;
    
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date                      dataDepositoAtto;
    private Integer                   denominatoreFrazioneQuota;
    private String                    descComuneEsteroDimora;
    private String                    descComuneEsteroDomicilio;
    private String                    descComuneEsteroDomicilioEletto;
    private String                    descComuneEsteroResidenza;
    private String                    descIstitutoDetenzioneEstero;
    // campo usato per visualizzare in tabella il comune della dimora, che
    // sia estero oppure italiano. Il controllo su cosa restituire è effettuato
    // nel metodo GETTER
    private String                    descrizioneComuneDimoraItalianoEstero;
    // campo usato per visualizzare in tabella il comune del domicilio eletto,
    // che
    // sia estero oppure italiano. Il controllo su cosa restituire è effettuato
    // nel metodo GETTER
    private String                    descrizioneComuneDomicilioElettoItalianoEstero;
    // campo usato per visualizzare in tabella il comune del domicilio, che
    // sia estero oppure italiano. Il controllo su cosa restituire è effettuato
    // nel metodo GETTER
    private String                    descrizioneComuneDomicilioItalianoEstero;
    private String                    descrizioneComuneEsteroMorte;
    private String                    descrizioneComuneEsteroNascita;
    private String                    descrizioneComuneEsteroSedeLegale;
    // campo usato per visualizzare in tabella il comune di morte, che
    // sia estero oppure italiano. Il controllo su cosa restituire è effettuato
    // nel metodo GETTER
    private String                    descrizioneComuneMorteItalianoEstero;
    private String                    descrizioneComuneNascitaItalianoEstero;
    // campo usato per visualizzare in tabella il comune della residenza, che
    // sia estero oppure italiano. Il controllo su cosa restituire è effettuato
    // nel metodo GETTER
    private String                    descrizioneComuneResidenzaItalianoEstero;
    // campo usato per visualizzare in tabella il comune della sede legale, che
    // sia estero oppure italiano. Il controllo su cosa restituire è effettuato
    // nel metodo GETTER
    private String                    descrizioneComuneSedeLegaleItalianoEstero;
    private String                    descrRuolo;
    private int						  idRuolo;
    private String                    descrSesso;
    private String                    descSoggetto;
    private String                    descValido;
    private boolean                   disabledCheckDefinitivita = false;
    @Valid
    private EredeConviventeForm       eredeConviventeForm;
    private List<EredeConviventeForm> eredi;
    private String                    flgAssociato;
    private String                    flgPresenza;
    private String                    flgValido;
    private String					  from;
    private Boolean                   gestioneEredi;
    private Boolean                   gestioneInserimentoModificaErede;
    private Boolean                   gratuitoPatrocinio;
    private Long                      idFonte;
    private Long                      idSoggetto;
    private Long                      idSoggettoInterveniente;
    private long                      idTipoSocieta;
    private int                       idUfficio;
    private Long                      idVersione;
    private IndirizzoForm             indirizzoDimora;
    private IndirizzoForm             indirizzoDomicilio;
    private IndirizzoForm             indirizzoDomicilioEletto;
    private IndirizzoForm             indirizzoResidenza;
    private IndirizzoForm             indirizzoSedeLegale;
    private ComboForm                 ipotesiMisura;
    private ComboForm                 istitutoDiDetenzione;
    @Valid
    private MisuraForm                misura;
    private List<String>              misuraInfo;
    @Pattern(regexp = Constants.RegExp.NOME)
    private String                    nome;
    @Pattern(regexp = Constants.RegExp.NOME)
    private String                    nomeMaternita;
    @Pattern(regexp = Constants.RegExp.NOME)
    private String                    nomePaternita;
    private ComboForm                 normativa;
    private Integer                   numeratoreFrazioneQuota;
    private String                    numeroCCIAA;
    // conta i difensori associati
    private int                       numeroDifensori;
    @NumberFormat(style = Style.NUMBER, pattern = "####")
    private Long                      numeroRegistroGratuitoPatrocinio;
    private String                    partitaIva;
    private String					  partitaIvaSiglaStato;
    private String					  partitaIvaCompleta;
    private boolean                   persDaIdent;
    private String                    pressoIndirizzoDomicilioEletto;
    private String                    pressoIndirizzoDomicilioElettoSpecifica;
    private boolean                   presunta;
    private ComboForm                 professione;
    private ComboForm                 provincia;
    private ComboForm                 provinciaDimora;
    private ComboForm                 provinciaDomicilio;
    private ComboForm                 provinciaDomicilioEletto;
    private ComboForm                 provinciaMorte;
    private ComboForm                 provinciaNascita;
    private ComboForm                 provinciaResidenza;
    @NumberFormat(pattern = "###.##")
    private Double                    quotaInteresse;
    private String                    ragioneSociale;
    @Pattern(regexp = Constants.RegExp.COGNOME)
    private String                    rappresentanteSociale;
    private String                    sesso;
    private ComboForm                 settoreAttivita;
    private String                    siglaProvincia;
    private String                    siglaProvinciaDimora;
    private String                    siglaProvinciaDomicilio;
    private String                    siglaProvinciaDomicilioEletto;
    private String                    siglaProvinciaMorte;
    private String                    siglaProvinciaNascita;
    private String                    siglaProvinciaResidenza;
    private String                    siglaProvinciaSedeLegale;
    private ComboForm                 stato;
    private ComboForm                 statoCittadinanza;
    private ComboForm                 statoDimora;
    private ComboForm                 statoDomicilio;
    private ComboForm                 statoDomicilioEletto;
    private ComboForm                 statoMorte;
    private ComboForm                 statoNascita;
    private ComboForm                 statoResidenza;
    private ComboForm                 statoSedeLegale;
    private ComboForm                 tenoreProvvedimentoGratuitoPatrocinio;
    private ComboForm                 tipoInteresse;
    private String                    tipoSocieta;
    private String                    tipoSoggetto;
    private String 					  schermataDiProvenienza;

    private ComboForm                 tipologiaPrevenzione;
    
    private String 					  descrizioneProcedimentoDestinazione;
    private String					  decisioneSuDecreto;
    
   //riunione: se il soggetto esiste sia nel proc riunito che riunente; in tal caso specificare il ruolo.
    private String 					  esisteInProcedeimentoRiunito;
    
    //annullamentoSoggetti
    private Long					  idSoggettoDuplicato;
    private Long					  idVersioneDuplicato;
    private String					  tipoSoggettoDuplicato;
    private String					  descrRuoloDuplicato;
    
    private String					  descNoteGp;
    
    private String					  descAltro1;
    private String					  descAltro3;
    
    private String					  descRichiesta;
    
    private String                    visibilitaIIGrado="";
    private String					  descSiglaStato;
    
    private ComboForm                 magistrato;
    
    
    @NumberFormat(style = Style.NUMBER, pattern = "###.###,##")
    @Max(value = 1000000000000L)
    private BigDecimal    impoCassaAmmenda;
    
  //utilizzato per SpeseProcessuali per contenere importo
    @NumberFormat(style = Style.NUMBER, pattern = "###.###,##")
    @Max(value = 1000000000000L)
    private BigDecimal   importoSpese;
  //utilizzato per SpeseProcessuali per contenere id 
    private int						  idSpeseProcessuali;
  //Utilizzato per check SpeseProcessulai
    private boolean                   checkTutti;
    
    private ComboForm                 speseProcessuali;
  //Utilizzato per le spese processuali
    private List<ComboForm>                listaSpeseProcessuali;
    
    //utilizzato per il FUG
    @NumberFormat(style = Style.NUMBER, pattern = "###.###,##")
	private String		importoQuota;
   
    private List<Long>    listaIdCheckbox;
    private List<String>  listaIdCheckboxString;
	private Boolean interveniente;
	
    private Long idProcedimentoCda;
	
	private String note;
	private String subCheck;
    private String flgDecretoSequestro;
    
	public Long getIdProcedimentoCda() {
		return idProcedimentoCda;
	}

	public void setIdProcedimentoCda(Long idProcedimentoCda) {
		this.idProcedimentoCda = idProcedimentoCda;
	}
    
    
	public Boolean getInterveniente() {
		return interveniente;
	}

	public void setInterveniente(Boolean interveniente) {
		this.interveniente = interveniente;
	}

	public List<Long> getListaIdCheckbox()
	{
		return listaIdCheckbox;
	}

	public void setListaIdCheckbox(List<Long> listaIdCheckbox)
	{
		this.listaIdCheckbox = listaIdCheckbox;
	}

	public List<String> getListaIdCheckboxString()
	{
		return listaIdCheckboxString;
	}

	public void setListaIdCheckboxString(List<String> listaIdCheckboxString)
	{
		this.listaIdCheckboxString = listaIdCheckboxString;
	}

	@Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SoggettoForm other = (SoggettoForm) obj;
        if (idSoggetto == null) {
            if (other.idSoggetto != null) {
                return false;
            }
        } else
            if (!idSoggetto.equals(other.idSoggetto)) {
                return false;
            }
        return true;
    }

    /**
     * @return the amministratore
     */
    public String getAmministratore() {

        return amministratore;
    }

    /**
     * @return the annoRegistroGratuitoPatrocinio
     */
    public Short getAnnoRegistroGratuitoPatrocinio() {

        return annoRegistroGratuitoPatrocinio;
    }

    public Date getDataEmissioneAtto() {
		return dataEmissioneAtto;
	}

	public void setDataEmissioneAtto(Date dataEmissioneAtto) {
		this.dataEmissioneAtto = dataEmissioneAtto;
	}

	public Date getDataDepositoAtto() {
		return dataDepositoAtto;
	}

	public void setDataDepositoAtto(Date dataDepositoAtto) {
		this.dataDepositoAtto = dataDepositoAtto;
	}

	/**
     * @return the annotazioni
     */
    public String getAnnotazioni() {

        return annotazioni;
    }

    /**
     * @return the capitaleSociale
     */
    public BigDecimal getCapitaleSociale() {

        return capitaleSociale;
    }

    /**
     * @return the chiave
     */
    public String getChiave() {

        if ((getIdSoggetto() == null) && (getIdVersione() == null)) {
            return null;
        }
        return "" + getIdSoggetto() + "," + getIdVersione() + "," + getTipoSoggetto();
    }


    /**
     * @return the codiceFiscale
     */
    public String getCodiceFiscale() {

        return codiceFiscale;
    }

    public String getCodiMotivo() {

        return codiMotivo;
    }

    /**
     * @return the cognome
     */
    public String getCognome() {

        return cognome;
    }

    /**
     * @return the cognomeMaternita
     */
    public String getCognomeMaternita() {

        return cognomeMaternita;
    }

    public String getCognomePaternita() {

        return cognomePaternita;
    }

    /**
     * @return the comune
     */
    public ComboForm getComune() {

        return comune;
    }

    /**
     * @return the comuneDimora
     */
    public ComboForm getComuneDimora() {

        return comuneDimora;
    }

    /**
     * @return the comuneDomicilio
     */
    public ComboForm getComuneDomicilio() {

        return comuneDomicilio;
    }

    /**
     * @return the comuneDomicilioEletto
     */
    public ComboForm getComuneDomicilioEletto() {

        return comuneDomicilioEletto;
    }

    /**
     * @return the comuneMorte
     */
    public ComboForm getComuneMorte() {

        return comuneMorte;
    }

    /**
     * @return the comuneNascita
     */
    public ComboForm getComuneNascita() {

        return comuneNascita;
    }

    /**
     * @return the comuneResidenza
     */
    public ComboForm getComuneResidenza() {

        return comuneResidenza;
    }

    public ComboForm getComuneSedeLegale() {

        return comuneSedeLegale;
    }

    /**
     * @return the condizioneGiuridica
     */
    public ComboForm getCondizioneGiuridica() {

        return condizioneGiuridica;
    }

    /**
     * @return the cui
     */
    public String getCui() {

        return cui;
    }

    /**
     * @return the dataEsecuzione
     */
    public Date getDataEsecuzione() {

        return dataEsecuzione;
    }

    /**
     * @return the dataIscrizione
     */
    public Date getDataIscrizione() {

        return dataIscrizione;
    }

    /**
     * @return the dataIstanzaGratuitoPatrocinio
     */
    public Date getDataIstanzaGratuitoPatrocinio() {

        return dataIstanzaGratuitoPatrocinio;
    }

    /**
     * @return the dataMorte
     */
    public Date getDataMorte() {

        return dataMorte;
    }

    /**
     * @return the dataNascita
     */
    public Date getDataNascita() {

        return dataNascita;
    }

    /**
     * @return the dataProvvedimentoGratuitoPatrocinio
     */
    public Date getDataProvvedimentoGratuitoPatrocinio() {

        return dataProvvedimentoGratuitoPatrocinio;
    }

    /**
     * @return the dataRevocaGratuitoPatrocinio
     */
    public Date getDataRevocaGratuitoPatrocinio() {

        return dataRevocaGratuitoPatrocinio;
    }

    public Integer getDenominatoreFrazioneQuota() {

        return denominatoreFrazioneQuota;
    }

    public String getDescComuneEsteroDimora() {

        return Utility.getDescrizioneCampoEstero(descComuneEsteroDimora, comuneDimora);
    }

    public String getDescComuneEsteroDomicilio() {

        return Utility.getDescrizioneCampoEstero(descComuneEsteroDomicilio, comuneDomicilio);
    }

    public String getDescComuneEsteroDomicilioEletto() {

        return Utility.getDescrizioneCampoEstero(descComuneEsteroDomicilioEletto, comuneDomicilioEletto);
    }

    /**
     * @return the descComuneEsteroResidenza
     */
    public String getDescComuneEsteroResidenza() {

        return Utility.getDescrizioneCampoEstero(descComuneEsteroResidenza, comuneResidenza);
    }

    /**
     * @return the descIstitutoDetenzioneEstero
     */
    public String getDescIstitutoDetenzioneEstero() {

        return descIstitutoDetenzioneEstero;
    }

    /**
     * @return the descrizioneComuneDimoraItalianoEstero
     */
    public String getDescrizioneComuneDimoraItalianoEstero() {

        if (descComuneEsteroDimora != null) {
            return descComuneEsteroDimora;
        } else
            if ((comuneDimora != null) && (siglaProvinciaDimora != null)) {
                return comuneDimora.getDescrizione() + " (" + siglaProvinciaDimora + ")";
            } else
                if (comuneDimora != null) {
                    return comuneDimora.getDescrizione();
                } else {
                    return null;
                }
    }

    /**
     * @return the descrizioneComuneDomicilioElettoItalianoEstero
     */
    public String getDescrizioneComuneDomicilioElettoItalianoEstero() {

        if (descComuneEsteroDomicilioEletto != null) {
            return descComuneEsteroDomicilioEletto;
        } else
            if ((comuneDomicilioEletto != null) && (siglaProvinciaDomicilioEletto != null)) {
                return comuneDomicilioEletto.getDescrizione() + " (" + siglaProvinciaDomicilioEletto + ")";
            } else
                if (comuneDomicilioEletto != null) {
                    return comuneDomicilioEletto.getDescrizione();
                } else {
                    return null;
                }
    }

    /**
     * @return the descrizioneComuneDomicilioItalianoEstero
     */
    public String getDescrizioneComuneDomicilioItalianoEstero() {

        if (descComuneEsteroDomicilio != null) {
            return descComuneEsteroDomicilio;
        } else
            if ((comuneDomicilio != null) && (siglaProvinciaDomicilio != null)) {
                return comuneDomicilio.getDescrizione() + " (" + siglaProvinciaDomicilio + ")";
            } else
                if (comuneDomicilio != null) {
                    return comuneDomicilio.getDescrizione();
                } else {
                    return null;
                }
    }

    public String getDescrizioneComuneEsteroMorte() {

        return Utility.getDescrizioneCampoEstero(descrizioneComuneEsteroMorte, comuneMorte);
    }

    /**
     * @return the descrizioneComuneEsteroNascita
     */
    public String getDescrizioneComuneEsteroNascita() {

        return Utility.getDescrizioneCampoEstero(descrizioneComuneEsteroNascita, comuneNascita);
    }

    /**
     * @return the descrizioneComuneEsteroSedeLegale
     */
    public String getDescrizioneComuneEsteroSedeLegale() {

        return Utility.getDescrizioneCampoEstero(descrizioneComuneEsteroSedeLegale, comuneSedeLegale);
    }

    public String getDescrizioneComuneMorteItalianoEstero() {

        if (descrizioneComuneEsteroMorte != null) {
            return descrizioneComuneEsteroMorte;
        } else
            if ((comuneMorte != null) && (siglaProvinciaMorte != null)) {
                return comuneMorte.getDescrizione() + " (" + siglaProvinciaMorte + ")";
            } else
                if (comuneMorte != null) {
                    return comuneMorte.getDescrizione();
                } else {
                    return null;
                }
    }

    /**
     * @return the descrizioneComuneNascitaItalianoEstero
     */
    public String getDescrizioneComuneNascitaItalianoEstero() {

        if (descrizioneComuneEsteroNascita != null) {
            return descrizioneComuneEsteroNascita;
        } else
            if ((comuneNascita != null) && (comuneNascita.getId() != null)) {
                return comuneNascita.getDescrizione() + " (" + siglaProvinciaNascita + ")";
            } else {
                return null;
            }
    }

    /**
     * @return the descrizioneComuneResidenzaItalianoEstero
     */
    public String getDescrizioneComuneResidenzaItalianoEstero() {

        if (descComuneEsteroResidenza != null) {
            return descComuneEsteroResidenza;
        } else
            if ((comuneResidenza != null) && (siglaProvinciaResidenza != null)) {
                return comuneResidenza.getDescrizione() + " (" + siglaProvinciaResidenza + ")";
            } else
                if (comuneResidenza != null) {
                    return comuneResidenza.getDescrizione();
                } else {
                    return null;
                }
    }

    /**
     * @return the descrizioneComuneSedeLegaleItalianoEstero
     */
    public String getDescrizioneComuneSedeLegaleItalianoEstero() {

        if (descrizioneComuneEsteroSedeLegale != null) {
            return descrizioneComuneEsteroSedeLegale;
        } else
            if ((comuneSedeLegale != null) && (siglaProvinciaSedeLegale != null)) {
                return comuneSedeLegale.getDescrizione() + " (" + siglaProvinciaSedeLegale + ")";
            } else
                if (comuneSedeLegale != null) {
                    return comuneSedeLegale.getDescrizione();
                } else {
                    return null;
                }
    }

    public String getDescrizioneFrazioneOQuota() {

        if (Utility.isDefinito(numeratoreFrazioneQuota) && Utility.isDefinito(denominatoreFrazioneQuota)) {
            return "" + numeratoreFrazioneQuota + "/" + denominatoreFrazioneQuota;
        }
        //quotaInteresse è un intero
        if(Utility.isDefinito(quotaInteresse) && java.lang.Math.floor(quotaInteresse)==quotaInteresse)
        	return String.valueOf(quotaInteresse.intValue())+"%";
        	
       String quota = String.valueOf(quotaInteresse).replace('.',',');
       if(quota!=null && !quota.equalsIgnoreCase("null")){
       if(quota.substring(quota.indexOf(',')+1).length()!=2)
    	   quota = quota + "0";
       }else{
    	   quota = "0";
       }
       return quota+"%";
    }

    /**
     * @return the descrizioneSoggetto
     */
    // public String getDescrizioneSoggetto() {
    //
    // String descr = "";
    // if (this.descrizioneSoggetto == null) {
    // if (Constants.SOGGETTO_FISICO.equals(getTipoSoggetto())) {
    // if (getCognome() != null)
    // descr += getCognome();
    // if (getNome() != null)
    // descr += " " + getNome();
    // if (getDescrizioneComuneNascitaItalianoEstero() != null) {
    // descr += " nato a " + getDescrizioneComuneNascitaItalianoEstero();
    // }
    // if (getDataNascita() != null) {
    // if (getDescrizioneComuneNascitaItalianoEstero() == null) {
    // descr += " nato";
    // }
    // descr += " il " + new Utility().convertDateToString(getDataNascita());
    // }
    // } else
    // if (Constants.SOGGETTO_GIURIDICO.equals(getTipoSoggetto())) {
    // if (getRagioneSociale() != null) {
    // descr += getRagioneSociale();
    // }
    // if (getDescrizioneComuneSedeLegaleItalianoEstero() != null) {
    // descr += " sede in " + getDescrizioneComuneSedeLegaleItalianoEstero();
    // }
    // if (getPartitaIva() != null) {
    // descr += " partita IVA " + getPartitaIva();
    // }
    // }
    // if (getIdVersione() != null) {
    // descr += " - Versione n. " + getIdVersione();
    // }
    // this.descrizioneSoggetto = descr;
    // }
    // return this.descrizioneSoggetto;
    // }
    public String getDescrizioneSoggetto() {

        StringBuilder descrizione = new StringBuilder();
        if (Constants.SOGGETTO_FISICO.equals(tipoSoggetto)) {
            concatenaCampiFisici(descrizione);
        } else
            if (Constants.SOGGETTO_GIURIDICO.equals(getTipoSoggetto())) {
                concatenaCampiGiuridici(descrizione);
            } else {
                concatenaCampiFisici(descrizione);
                concatenaCampiGiuridici(descrizione);
            }
       //gennaro 5/02/2014 tolto l'id versione dalla descrizione del soggetto
        /* if (idVersione != null) {
            descrizione.append(" - Ver. n. ");
            descrizione.append(idVersione);
        }
        */
        return descrizione.toString();
    }
    //gennaro 02/05/2014 inserimento metodo che tira fuori le generalità con versione
    public String getDescrizioneSoggettoConVersione() {

        StringBuilder descrizione = new StringBuilder();
        if (Constants.SOGGETTO_FISICO.equals(tipoSoggetto)) {
            concatenaCampiFisici(descrizione);
        } else
            if (Constants.SOGGETTO_GIURIDICO.equals(getTipoSoggetto())) {
                concatenaCampiGiuridici(descrizione);
            } else {
                concatenaCampiFisici(descrizione);
                concatenaCampiGiuridici(descrizione);
            }
       if (idVersione != null) {
            descrizione.append(" - Ver. n. ");
            descrizione.append(idVersione);
        }
        
        return descrizione.toString();
    }
//fine gennaro 02/05/2014
    
    
    public String getDescrizioneSoggettoNoVersione() {

        StringBuilder descrizione = new StringBuilder();
        if (Constants.SOGGETTO_FISICO.equals(tipoSoggetto)) {
            concatenaCampiFisici(descrizione);
        } else
            if (Constants.SOGGETTO_GIURIDICO.equals(getTipoSoggetto())) {
                concatenaCampiGiuridici(descrizione);
            } else {
                concatenaCampiFisici(descrizione);
                concatenaCampiGiuridici(descrizione);
            }
        
        return descrizione.toString();
    }
    /**
     * @return the descrRuolo
     */
    public String getDescrRuolo() {

        return descrRuolo;
    }

    /**
     * @return the descrSesso
     */
    public String getDescrSesso() {

        if ((sesso != null) && sesso.equalsIgnoreCase("m")) {
            descrSesso = "MASCHIO";
        } else
            if ((sesso != null) && sesso.equalsIgnoreCase("f")) {
                descrSesso = "FEMMINA";
            }
        return descrSesso;
    }

    /**
     * @return the descSoggetto
     */
    public String getDescSoggetto() {

        return descSoggetto;
    }

    public String getDescValido() {

        return descValido;
    }

    /**
     * @return the disabledCheckDefinitivita
     */
    public boolean isDisabledCheckDefinitivita() {
    
        return disabledCheckDefinitivita;
    }

    /**
     * @return the eredeConviventeForm
     */
    public EredeConviventeForm getEredeConviventeForm() {

        return eredeConviventeForm;
    }

    /**
     * @return the eredi
     */
    public List<EredeConviventeForm> getEredi() {

        return eredi;
    }

    public String getFlgAssociato() {

        return flgAssociato;
    }

    public String getFlgPresenza() {

        return flgPresenza;
    }

    public String getFlgValido() {

        return flgValido;
    }

    public String getFrom() {
		return from;
	}

	/**
     * @return the gestioneEredi
     */
    public Boolean getGestioneEredi() {

        return gestioneEredi;
    }

    /**
     * @return the gestioneInserimentoModificaErede
     */
    public Boolean getGestioneInserimentoModificaErede() {

        return gestioneInserimentoModificaErede;
    }

    /**
     * @return the gratuitoPatrocinio
     */
    public Boolean getGratuitoPatrocinio() {

        return gratuitoPatrocinio;
    }

    public Long getIdFonte() {

        return idFonte;
    }

    /**
     * @return the idSoggetto
     */
    public Long getIdSoggetto() {

        return idSoggetto;
    }

    /**
     * @return the idTipoSocieta
     */
    public long getIdTipoSocieta() {

        return idTipoSocieta;
    }

    public int getIdUfficio() {

        return idUfficio;
    }

    public Long getIdVersione() {

        return idVersione;
    }

    /**
     * @return the indirizzoDimora
     */
    public IndirizzoForm getIndirizzoDimora() {

        return indirizzoDimora;
    }

    /**
     * @return the indirizzoDomicilio
     */
    public IndirizzoForm getIndirizzoDomicilio() {

        return indirizzoDomicilio;
    }

    /**
     * @return the indirizzoDomicilioEletto
     */
    public IndirizzoForm getIndirizzoDomicilioEletto() {

        return indirizzoDomicilioEletto;
    }

    /**
     * @return the indirizzoResidenza
     */
    public IndirizzoForm getIndirizzoResidenza() {

        return indirizzoResidenza;
    }

    /**
     * @return the indirizzoSedeLegale
     */
    public IndirizzoForm getIndirizzoSedeLegale() {

        return indirizzoSedeLegale;
    }

    public ComboForm getIpotesiMisura() {

        return ipotesiMisura;
    }

    /**
     * @return the istitutoDiDetenzione
     */
    public ComboForm getIstitutoDiDetenzione() {

        return istitutoDiDetenzione;
    }

    /**
     * @return the misura
     */
    public MisuraForm getMisura() {

        return misura;
    }

    /**
     * @return the misuraInfo
     */    
    public List<String> getMisuraInfo() {
    
        return misuraInfo;
    }

    /**
     * @return the nome
     */
    public String getNome() {

        return nome;
    }

    /**
     * @return the nomeMaternita
     */
    public String getNomeMaternita() {

        return nomeMaternita;
    }

    /**
     * @return the nomePaternita
     */
    public String getNomePaternita() {

        return nomePaternita;
    }

    public ComboForm getNormativa() {

        return normativa;
    }

    public Integer getNumeratoreFrazioneQuota() {

        return numeratoreFrazioneQuota;
    }

    /**
     * @return the numeroCCIAA
     */
    public String getNumeroCCIAA() {

        return numeroCCIAA;
    }

    public int getNumeroDifensori() {

        return numeroDifensori;
    }

    /**
     * @return the numeroRegistroGratuitoPatrocinio
     */
    public Long getNumeroRegistroGratuitoPatrocinio() {

        return numeroRegistroGratuitoPatrocinio;
    }

    /**
     * @return the partitaIva
     */
    public String getPartitaIva() {

        return partitaIva;
    }

    /**
     * @return the pressoIndirizzoDomicilioEletto
     */
    public String getPressoIndirizzoDomicilioEletto() {

        return pressoIndirizzoDomicilioEletto;
    }

    /**
     * @return the pressoIndirizzoDomicilioElettoSpecifica
     */
    public String getPressoIndirizzoDomicilioElettoSpecifica() {

        return pressoIndirizzoDomicilioElettoSpecifica;
    }

    /**
     * @return the professione
     */
    public ComboForm getProfessione() {

        return professione;
    }

    /**
     * @return the provincia
     */
    public ComboForm getProvincia() {

        return provincia;
    }

    /**
     * @return the provinciaDimora
     */
    public ComboForm getProvinciaDimora() {

        return provinciaDimora;
    }

    /**
     * @return the provinciaDomicilio
     */
    public ComboForm getProvinciaDomicilio() {

        return provinciaDomicilio;
    }

    /**
     * @return the provinciaDomicilioEletto
     */
    public ComboForm getProvinciaDomicilioEletto() {

        return provinciaDomicilioEletto;
    }

    /**
     * @return the provinciaMorte
     */
    public ComboForm getProvinciaMorte() {

        return provinciaMorte;
    }

    /**
     * @return the provinciaNascita
     */
    public ComboForm getProvinciaNascita() {

        return provinciaNascita;
    }

    /**
     * @return the provinciaResidenza
     */
    public ComboForm getProvinciaResidenza() {

        return provinciaResidenza;
    }

    public Double getQuotaInteresse() {

        return quotaInteresse;
    }

    /**
     * @return the ragioneSociale
     */
    public String getRagioneSociale() {

        return ragioneSociale;
    }

    /**
     * @return the rappresentanteSociale
     */
    public String getRappresentanteSociale() {

        return rappresentanteSociale;
    }

    /**
     * @return the sesso
     */
    public String getSesso() {

        return sesso;
    }

    /**
     * @return the settoreAttivita
     */
    public ComboForm getSettoreAttivita() {

        return settoreAttivita;
    }

    /**
     * @return the siglaProvincia
     */
    public String getSiglaProvincia() {

        return siglaProvincia;
    }

    /**
     * @return the siglaProvinciaDimora
     */
    public String getSiglaProvinciaDimora() {

        return siglaProvinciaDimora;
    }

    /**
     * @return the siglaProvinciaDomicilio
     */
    public String getSiglaProvinciaDomicilio() {

        return siglaProvinciaDomicilio;
    }

    /**
     * @return the siglaProvinciaDomicilioEletto
     */
    public String getSiglaProvinciaDomicilioEletto() {

        return siglaProvinciaDomicilioEletto;
    }

    public String getSiglaProvinciaMorte() {

        return siglaProvinciaMorte;
    }

    /**
     * @return the siglaProvinciaNascita
     */
    public String getSiglaProvinciaNascita() {

        return siglaProvinciaNascita;
    }

    /**
     * @return the siglaProvinciaResidenza
     */
    public String getSiglaProvinciaResidenza() {

        return siglaProvinciaResidenza;
    }

    public String getSiglaProvinciaSedeLegale() {

        return siglaProvinciaSedeLegale;
    }

    /**
     * @return the stato
     */
    public ComboForm getStato() {

        return stato;
    }

    public ComboForm getStatoCittadinanza() {

        return statoCittadinanza;
    }

    /**
     * @return the statoDimora
     */
    public ComboForm getStatoDimora() {

        return statoDimora;
    }

    /**
     * @return the statoDomicilio
     */
    public ComboForm getStatoDomicilio() {

        return statoDomicilio;
    }

    /**
     * @return the statoDomicilioEletto
     */
    public ComboForm getStatoDomicilioEletto() {

        return statoDomicilioEletto;
    }

    /**
     * @return the statoMorte
     */
    public ComboForm getStatoMorte() {

        return statoMorte;
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

    public ComboForm getStatoSedeLegale() {

        return statoSedeLegale;
    }

    /**
     * @return the tenoreProvvedimentoGratuitoPatrocinio
     */
    public ComboForm getTenoreProvvedimentoGratuitoPatrocinio() {

        return tenoreProvvedimentoGratuitoPatrocinio;
    }

    public ComboForm getTipoInteresse() {

        return tipoInteresse;
    }

    /**
     * @return the tipoSocieta
     */
    public String getTipoSocieta() {

        return tipoSocieta;
    }

    /**
     * @return the tipoSoggetto
     */
    public String getTipoSoggetto() {

        return tipoSoggetto;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((idSoggetto == null) ? 0 : idSoggetto.hashCode());
        return result;
    }

    public boolean isPersDaIdent() {

        return persDaIdent;
    }

    public boolean isPresunta() {

        return presunta;
    }

    /**
     * @param amministratore
     *            the amministratore to set
     */
    public void setAmministratore(String amministratore) {

        this.amministratore = amministratore;
    }

    /**
     * @param annoRegistroGratuitoPatrocinio
     *            the annoRegistroGratuitoPatrocinio to set
     */
    public void setAnnoRegistroGratuitoPatrocinio(Short annoRegistroGratuitoPatrocinio) {

        this.annoRegistroGratuitoPatrocinio = annoRegistroGratuitoPatrocinio;
    }

    /**
     * @param annotazioni
     *            the annotazioni to set
     */
    public void setAnnotazioni(String annotazioni) {

        this.annotazioni = annotazioni;
    }

    /**
     * @param capitaleSociale
     *            the capitaleSociale to set
     */
    public void setCapitaleSociale(BigDecimal capitaleSociale) {

        this.capitaleSociale = capitaleSociale;
    }

    /**
     * @param codiceFiscale
     *            the codiceFiscale to set
     */
    public void setCodiceFiscale(String codiceFiscale) {

        this.codiceFiscale = codiceFiscale;
    }

    public void setCodiMotivo(String codiMotivo) {

        this.codiMotivo = codiMotivo;
    }

    /**
     * @param cognome
     *            the cognome to set
     */
    public void setCognome(String cognome) {

        this.cognome = cognome;
    }

    /**
     * @param cognomeMaternita
     *            the cognomeMaternita to set
     */
    public void setCognomeMaternita(String cognomeMaternita) {

        this.cognomeMaternita = cognomeMaternita;
    }

    public void setCognomePaternita(String cognomePaternita) {

        this.cognomePaternita = cognomePaternita;
    }

    /**
     * @param comune
     *            the comune to set
     */
    public void setComune(ComboForm comune) {

        this.comune = comune;
    }

    /**
     * @param comuneDimora
     *            the comuneDimora to set
     */
    public void setComuneDimora(ComboForm comuneDimora) {

        this.comuneDimora = comuneDimora;
    }

    /**
     * @param comuneDomicilio
     *            the comuneDomicilio to set
     */
    public void setComuneDomicilio(ComboForm comuneDomicilio) {

        this.comuneDomicilio = comuneDomicilio;
    }

    /**
     * @param comuneDomicilioEletto
     *            the comuneDomicilioEletto to set
     */
    public void setComuneDomicilioEletto(ComboForm comuneDomicilioEletto) {

        this.comuneDomicilioEletto = comuneDomicilioEletto;
    }

    /**
     * @param comuneMorte
     *            the comuneMorte to set
     */
    public void setComuneMorte(ComboForm comuneMorte) {

        this.comuneMorte = comuneMorte;
    }

    /**
     * @param comuneNascita
     *            the comuneNascita to set
     */
    public void setComuneNascita(ComboForm comuneNascita) {

        this.comuneNascita = comuneNascita;
    }

    /**
     * @param comuneResidenza
     *            the comuneResidenza to set
     */
    public void setComuneResidenza(ComboForm comuneResidenza) {

        this.comuneResidenza = comuneResidenza;
    }

    public void setComuneSedeLegale(ComboForm comuneSedeLegale) {

        this.comuneSedeLegale = comuneSedeLegale;
    }

    /**
     * @param condizioneGiuridica
     *            the condizioneGiuridica to set
     */
    public void setCondizioneGiuridica(ComboForm condizioneGiuridica) {

        this.condizioneGiuridica = condizioneGiuridica;
    }

    /**
     * @param cui
     *            the cui to set
     */
    public void setCui(String cui) {

        this.cui = cui;
    }
    
    /**
     * @param dataEsecuzione
     *            the dataEsecuzione to set
     */
    public void setDataEsecuzione(Date dataEsecuzione) {

        this.dataEsecuzione = dataEsecuzione;
    }

    /**
     * @param dataIscrizione
     *            the dataIscrizione to set
     */
    public void setDataIscrizione(Date dataIscrizione) {

        this.dataIscrizione = dataIscrizione;
    }

    /**
     * @param dataIstanzaGratuitoPatrocinio
     *            the dataIstanzaGratuitoPatrocinio to set
     */
    public void setDataIstanzaGratuitoPatrocinio(Date dataIstanzaGratuitoPatrocinio) {

        this.dataIstanzaGratuitoPatrocinio = dataIstanzaGratuitoPatrocinio;
    }

    /**
     * @param dataMorte
     *            the dataMorte to set
     */
    public void setDataMorte(Date dataMorte) {

        this.dataMorte = dataMorte;
    }

    /**
     * @param dataNascita
     *            the dataNascita to set
     */
    public void setDataNascita(Date dataNascita) {

        this.dataNascita = dataNascita;
    }

    /**
     * @param dataProvvedimentoGratuitoPatrocinio
     *            the dataProvvedimentoGratuitoPatrocinio to set
     */
    public void setDataProvvedimentoGratuitoPatrocinio(Date dataProvvedimentoGratuitoPatrocinio) {

        this.dataProvvedimentoGratuitoPatrocinio = dataProvvedimentoGratuitoPatrocinio;
    }

    /**
     * @param dataRevocaGratuitoPatrocinio
     *            the dataRevocaGratuitoPatrocinio to set
     */
    public void setDataRevocaGratuitoPatrocinio(Date dataRevocaGratuitoPatrocinio) {

        this.dataRevocaGratuitoPatrocinio = dataRevocaGratuitoPatrocinio;
    }

    public void setDenominatoreFrazioneQuota(Integer denominatoreFrazioneQuota) {

        this.denominatoreFrazioneQuota = denominatoreFrazioneQuota;
    }

    public void setDescComuneEsteroDimora(String descComuneEsteroDimora) {

        this.descComuneEsteroDimora = Utility.getDescrizioneCampoEstero(descComuneEsteroDimora, comuneDimora);
    }

    public void setDescComuneEsteroDomicilio(String descComuneEsteroDomicilio) {

        this.descComuneEsteroDomicilio = Utility.getDescrizioneCampoEstero(descComuneEsteroDomicilio, comuneDomicilio);
    }

    public void setDescComuneEsteroDomicilioEletto(String descComuneEsteroDomicilioEletto) {

        this.descComuneEsteroDomicilioEletto = Utility.getDescrizioneCampoEstero(descComuneEsteroDomicilioEletto, comuneDomicilioEletto);
    }

    /**
     * @param descComuneEsteroResidenza
     *            the descComuneEsteroResidenza to set
     */
    public void setDescComuneEsteroResidenza(String descComuneEsteroResidenza) {

        this.descComuneEsteroResidenza = Utility.getDescrizioneCampoEstero(descComuneEsteroResidenza, comuneResidenza);
    }

    /**
     * @param descIstitutoDetenzioneEstero
     *            the descIstitutoDetenzioneEstero to set
     */
    public void setDescIstitutoDetenzioneEstero(String descIstitutoDetenzioneEstero) {

        this.descIstitutoDetenzioneEstero = descIstitutoDetenzioneEstero;
    }

    /**
     * @param descrizioneComuneDimoraItalianoEstero
     *            the descrizioneComuneDimoraItalianoEstero to set
     */
    public void setDescrizioneComuneDimoraItalianoEstero(String descrizioneComuneDimoraItalianoEstero) {

        this.descrizioneComuneDimoraItalianoEstero = descrizioneComuneDimoraItalianoEstero;
    }

    /**
     * @param descrizioneComuneDomicilioElettoItalianoEstero
     *            the descrizioneComuneDomicilioElettoItalianoEstero to set
     */
    public void setDescrizioneComuneDomicilioElettoItalianoEstero(String descrizioneComuneDomicilioElettoItalianoEstero) {

        this.descrizioneComuneDomicilioElettoItalianoEstero = descrizioneComuneDomicilioElettoItalianoEstero;
    }

    /**
     * @param descrizioneComuneDomicilioItalianoEstero
     *            the descrizioneComuneDomicilioItalianoEstero to set
     */
    public void setDescrizioneComuneDomicilioItalianoEstero(String descrizioneComuneDomicilioItalianoEstero) {

        this.descrizioneComuneDomicilioItalianoEstero = descrizioneComuneDomicilioItalianoEstero;
    }

    public void setDescrizioneComuneEsteroMorte(String descrizioneComuneEsteroMorte) {

        this.descrizioneComuneEsteroMorte = Utility.getDescrizioneCampoEsteroAndSetComuneCombo(this.descrizioneComuneEsteroMorte, descrizioneComuneEsteroMorte, comuneMorte);
    }

    /**
     * @param descrizioneComuneEsteroNascita
     *            the descrizioneComuneEsteroNascita to set
     */
    public void setDescrizioneComuneEsteroNascita(String descrizioneComuneEsteroNascita) {

        this.descrizioneComuneEsteroNascita = Utility.getDescrizioneCampoEstero(descrizioneComuneEsteroNascita, comuneNascita);
    }

    /**
     * @param descrizioneComuneEsteroSedeLegale
     *            the descrizioneComuneEsteroSedeLegale to set
     */
    public void setDescrizioneComuneEsteroSedeLegale(String descrizioneComuneEsteroSedeLegale) {

        this.descrizioneComuneEsteroSedeLegale = Utility.getDescrizioneCampoEsteroAndSetComuneCombo(this.descrizioneComuneEsteroSedeLegale, descrizioneComuneEsteroSedeLegale, comuneSedeLegale);
    }

    public void setDescrizioneComuneMorteItalianoEstero(String descrizioneComuneMorteItalianoEstero) {

        this.descrizioneComuneMorteItalianoEstero = descrizioneComuneMorteItalianoEstero;
    }

    /**
     * @param descrizioneComuneNascitaItalianoEstero
     *            the descrizioneComuneNascitaItalianoEstero to set
     */
    public void setDescrizioneComuneNascitaItalianoEstero(String descrizioneComuneNascitaItalianoEstero) {

        this.descrizioneComuneNascitaItalianoEstero = descrizioneComuneNascitaItalianoEstero;
    }

    /**
     * @param descrizioneComuneResidenzaItalianoEstero
     *            the descrizioneComuneResidenzaItalianoEstero to set
     */
    public void setDescrizioneComuneResidenzaItalianoEstero(String descrizioneComuneResidenzaItalianoEstero) {

        this.descrizioneComuneResidenzaItalianoEstero = descrizioneComuneResidenzaItalianoEstero;
    }

    /**
     * @param descrizioneComuneSedeLegaleItalianoEstero
     *            the descrizioneComuneSedeLegaleItalianoEstero to set
     */
    public void setDescrizioneComuneSedeLegaleItalianoEstero(String descrizioneComuneSedeLegaleItalianoEstero) {

        this.descrizioneComuneSedeLegaleItalianoEstero = descrizioneComuneSedeLegaleItalianoEstero;
    }

    /**
     * @param descrRuolo
     *            the descrRuolo to set
     */
    public void setDescrRuolo(String descrRuolo) {

        this.descrRuolo = descrRuolo;
    }

    /**
     * @param descrSesso
     *            the descrSesso to set
     */
    public void setDescrSesso(String descrSesso) {

        this.descrSesso = descrSesso;
    }

    /**
     * @param descSoggetto
     *            the descSoggetto to set
     */
    public void setDescSoggetto(String descSoggetto) {

        this.descSoggetto = descSoggetto;
    }

    public void setDescValido(String descValido) {

        this.descValido = descValido;
    }

    /**
     * @param disabledCheckDefinitivita
     *            the disabledCheckDefinitivita to set
     */
    public void setDisabledCheckDefinitivita(boolean disabledCheckDefinitivita) {
    
        this.disabledCheckDefinitivita = disabledCheckDefinitivita;
    }

    /**
     * @param eredeConviventeForm
     *            the eredeConviventeForm to set
     */
    public void setEredeConviventeForm(EredeConviventeForm eredeConviventeForm) {

        this.eredeConviventeForm = eredeConviventeForm;
    }

    /**
     * @param eredi
     *            the eredi to set
     */
    public void setEredi(List<EredeConviventeForm> eredi) {

        this.eredi = eredi;
    }

    public void setFlgAssociato(String flgAssociato) {

        this.flgAssociato = flgAssociato;
    }

    public void setFlgPresenza(String flgPresenza) {

        this.flgPresenza = flgPresenza;
    }

    public void setFlgValido(String flgValido) {

        this.flgValido = flgValido;
    }

    public void setFrom(String from) {
		this.from = from;
	}

	/**
     * @param gestioneEredi
     *            the gestioneEredi to set
     */
    public void setGestioneEredi(Boolean gestioneEredi) {

        this.gestioneEredi = gestioneEredi;
    }

    /**
     * @param gestioneInserimentoModificaErede
     *            the gestioneInserimentoModificaErede to set
     */
    public void setGestioneInserimentoModificaErede(Boolean gestioneInserimentoModificaErede) {

        this.gestioneInserimentoModificaErede = gestioneInserimentoModificaErede;
    }

    /**
     * @param gratuitoPatrocinio
     *            the gratuitoPatrocinio to set
     */
    public void setGratuitoPatrocinio(Boolean gratuitoPatrocinio) {

        this.gratuitoPatrocinio = gratuitoPatrocinio;
    }

    public void setIdFonte(Long idFonte) {

        this.idFonte = idFonte;
    }

    /**
     * @param idSoggetto
     *            the idSoggetto to set
     */
    public void setIdSoggetto(Long idSoggetto) {

        this.idSoggetto = idSoggetto;
    }

    /**
     * @param idTipoSocieta
     *            the idTipoSocieta to set
     */
    public void setIdTipoSocieta(long idTipoSocieta) {

        this.idTipoSocieta = idTipoSocieta;
    }

    public void setIdUfficio(int idUfficio) {

        this.idUfficio = idUfficio;
    }

    public void setIdVersione(Long idVersione) {

        this.idVersione = idVersione;
    }

    /**
     * @param indirizzoDimora
     *            the indirizzoDimora to set
     */
    public void setIndirizzoDimora(IndirizzoForm indirizzoDimora) {

        this.indirizzoDimora = indirizzoDimora;
    }

    /**
     * @param indirizzoDomicilio
     *            the indirizzoDomicilio to set
     */
    public void setIndirizzoDomicilio(IndirizzoForm indirizzoDomicilio) {

        this.indirizzoDomicilio = indirizzoDomicilio;
    }

    /**
     * @param indirizzoDomicilioEletto
     *            the indirizzoDomicilioEletto to set
     */
    public void setIndirizzoDomicilioEletto(IndirizzoForm indirizzoDomicilioEletto) {

        this.indirizzoDomicilioEletto = indirizzoDomicilioEletto;
    }

    /**
     * @param indirizzoResidenza
     *            the indirizzoResidenza to set
     */
    public void setIndirizzoResidenza(IndirizzoForm indirizzoResidenza) {

        this.indirizzoResidenza = indirizzoResidenza;
    }

    /**
     * @param indirizzoSedeLegale
     *            the indirizzoSedeLegale to set
     */
    public void setIndirizzoSedeLegale(IndirizzoForm indirizzoSedeLegale) {

        this.indirizzoSedeLegale = indirizzoSedeLegale;
    }

    public void setIpotesiMisura(ComboForm ipotesiMisura) {

        this.ipotesiMisura = ipotesiMisura;
    }

    /**
     * @param istitutoDiDetenzione
     *            the istitutoDiDetenzione to set
     */
    public void setIstitutoDiDetenzione(ComboForm istitutoDiDetenzione) {

        this.istitutoDiDetenzione = istitutoDiDetenzione;
    }

    /**
     * @param misura
     *            the misura to set
     */
    public void setMisura(MisuraForm misura) {

        this.misura = misura;
    }
    
    /**
     * @param misuraInfo
     *            the misuraInfo to set
     */
    public void setMisuraInfo(List<String> misuraInfo) {
    
        this.misuraInfo = misuraInfo;
    }

    /**
     * @param nome
     *            the nome to set
     */
    public void setNome(String nome) {

        this.nome = nome;
    }

    /**
     * @param nomeMaternita
     *            the nomeMaternita to set
     */
    public void setNomeMaternita(String nomeMaternita) {

        this.nomeMaternita = nomeMaternita;
    }

    /**
     * @param nomePaternita
     *            the nomePaternita to set
     */
    public void setNomePaternita(String nomePaternita) {

        this.nomePaternita = nomePaternita;
    }

    public void setNormativa(ComboForm normativa) {

        this.normativa = normativa;
    }

    public void setNumeratoreFrazioneQuota(Integer numeratoreFrazioneQuota) {

        this.numeratoreFrazioneQuota = numeratoreFrazioneQuota;
    }

    /**
     * @param numeroCCIAA
     *            the numeroCCIAA to set
     */
    public void setNumeroCCIAA(String numeroCCIAA) {

        this.numeroCCIAA = numeroCCIAA;
    }

    public void setNumeroDifensori(int numeroDifensori) {

        this.numeroDifensori = numeroDifensori;
    }

    /**
     * @param numeroRegistroGratuitoPatrocinio
     *            the numeroRegistroGratuitoPatrocinio to set
     */
    public void setNumeroRegistroGratuitoPatrocinio(Long numeroRegistroGratuitoPatrocinio) {

        this.numeroRegistroGratuitoPatrocinio = numeroRegistroGratuitoPatrocinio;
    }

    /**
     * @param partitaIva
     *            the partitaIva to set
     */
    public void setPartitaIva(String partitaIva) {

        this.partitaIva = partitaIva;
    }

    public void setPersDaIdent(boolean persDaIdent) {

        this.persDaIdent = persDaIdent;
    }

    /**
     * @param pressoIndirizzoDomicilioEletto
     *            the pressoIndirizzoDomicilioEletto to set
     */
    public void setPressoIndirizzoDomicilioEletto(String pressoIndirizzoDomicilioEletto) {

        this.pressoIndirizzoDomicilioEletto = pressoIndirizzoDomicilioEletto;
    }

    /**
     * @param pressoIndirizzoDomicilioElettoSpecifica
     *            the pressoIndirizzoDomicilioElettoSpecifica to set
     */
    public void setPressoIndirizzoDomicilioElettoSpecifica(String pressoIndirizzoDomicilioElettoSpecifica) {

        this.pressoIndirizzoDomicilioElettoSpecifica = pressoIndirizzoDomicilioElettoSpecifica;
    }

    public void setPresunta(boolean presunta) {

        this.presunta = presunta;
    }

    /**
     * @param professione
     *            the professione to set
     */
    public void setProfessione(ComboForm professione) {

        this.professione = professione;
    }

    /**
     * @param provincia
     *            the provincia to set
     */
    public void setProvincia(ComboForm provincia) {

        this.provincia = provincia;
    }

    /**
     * @param provinciaDimora
     *            the provinciaDimora to set
     */
    public void setProvinciaDimora(ComboForm provinciaDimora) {

        this.provinciaDimora = provinciaDimora;
    }

    /**
     * @param provinciaDomicilio
     *            the provinciaDomicilio to set
     */
    public void setProvinciaDomicilio(ComboForm provinciaDomicilio) {

        this.provinciaDomicilio = provinciaDomicilio;
    }

    /**
     * @param provinciaDomicilioEletto
     *            the provinciaDomicilioEletto to set
     */
    public void setProvinciaDomicilioEletto(ComboForm provinciaDomicilioEletto) {

        this.provinciaDomicilioEletto = provinciaDomicilioEletto;
    }

    /**
     * @param provinciaMorte
     *            the provinciaMorte to set
     */
    public void setProvinciaMorte(ComboForm provinciaMorte) {

        this.provinciaMorte = provinciaMorte;
    }

    /**
     * @param provinciaNascita
     *            the provinciaNascita to set
     */
    public void setProvinciaNascita(ComboForm provinciaNascita) {

        this.provinciaNascita = provinciaNascita;
    }

    /**
     * @param provinciaResidenza
     *            the provinciaResidenza to set
     */
    public void setProvinciaResidenza(ComboForm provinciaResidenza) {

        this.provinciaResidenza = provinciaResidenza;
    }

    public void setQuotaInteresse(Double quotaInteresse) {

        this.quotaInteresse = quotaInteresse;
    }

    /**
     * @param ragioneSociale
     *            the ragioneSociale to set
     */
    public void setRagioneSociale(String ragioneSociale) {

        this.ragioneSociale = ragioneSociale;
    }

    /**
     * @param rappresentanteSociale
     *            the rappresentanteSociale to set
     */
    public void setRappresentanteSociale(String rappresentanteSociale) {

        this.rappresentanteSociale = rappresentanteSociale;
    }

    /**
     * @param sesso
     *            the sesso to set
     */
    public void setSesso(String sesso) {

        this.sesso = sesso;
    }

    /**
     * @param settoreAttivita
     *            the settoreAttivita to set
     */
    public void setSettoreAttivita(ComboForm settoreAttivita) {

        this.settoreAttivita = settoreAttivita;
    }

    /**
     * @param siglaProvincia
     *            the siglaProvincia to set
     */
    public void setSiglaProvincia(String siglaProvincia) {

        this.siglaProvincia = siglaProvincia;
    }

    /**
     * @param siglaProvinciaDimora
     *            the siglaProvinciaDimora to set
     */
    public void setSiglaProvinciaDimora(String siglaProvinciaDimora) {

        this.siglaProvinciaDimora = siglaProvinciaDimora;
    }

    /**
     * @param siglaProvinciaDomicilio
     *            the siglaProvinciaDomicilio to set
     */
    public void setSiglaProvinciaDomicilio(String siglaProvinciaDomicilio) {

        this.siglaProvinciaDomicilio = siglaProvinciaDomicilio;
    }

    /**
     * @param siglaProvinciaDomicilioEletto
     *            the siglaProvinciaDomicilioEletto to set
     */
    public void setSiglaProvinciaDomicilioEletto(String siglaProvinciaDomicilioEletto) {

        this.siglaProvinciaDomicilioEletto = siglaProvinciaDomicilioEletto;
    }

    public void setSiglaProvinciaMorte(String siglaProvinciaMorte) {

        this.siglaProvinciaMorte = siglaProvinciaMorte;
    }

    /**
     * @param siglaProvinciaNascita
     *            the siglaProvinciaNascita to set
     */
    public void setSiglaProvinciaNascita(String siglaProvinciaNascita) {

        this.siglaProvinciaNascita = siglaProvinciaNascita;
    }

    /**
     * @param siglaProvinciaResidenza
     *            the siglaProvinciaResidenza to set
     */
    public void setSiglaProvinciaResidenza(String siglaProvinciaResidenza) {

        this.siglaProvinciaResidenza = siglaProvinciaResidenza;
    }

    public void setSiglaProvinciaSedeLegale(String siglaProvinciaSedeLegale) {

        this.siglaProvinciaSedeLegale = siglaProvinciaSedeLegale;
    }

    /**
     * @param stato
     *            the stato to set
     */
    public void setStato(ComboForm stato) {

        this.stato = stato;
    }

    public void setStatoCittadinanza(ComboForm statoCittadinanza) {

        this.statoCittadinanza = statoCittadinanza;
    }

    /**
     * @param statoDimora
     *            the statoDimora to set
     */
    public void setStatoDimora(ComboForm statoDimora) {

        this.statoDimora = statoDimora;
    }

    /**
     * @param statoDomicilio
     *            the statoDomicilio to set
     */
    public void setStatoDomicilio(ComboForm statoDomicilio) {

        this.statoDomicilio = statoDomicilio;
    }

    /**
     * @param statoDomicilioEletto
     *            the statoDomicilioEletto to set
     */
    public void setStatoDomicilioEletto(ComboForm statoDomicilioEletto) {

        this.statoDomicilioEletto = statoDomicilioEletto;
    }

    /**
     * @param statoMorte
     *            the statoMorte to set
     */
    public void setStatoMorte(ComboForm statoMorte) {

        this.statoMorte = statoMorte;
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

    public void setStatoSedeLegale(ComboForm statoSedeLegale) {

        this.statoSedeLegale = statoSedeLegale;
    }

    /**
     * @param tenoreProvvedimentoGratuitoPatrocinio
     *            the tenoreProvvedimentoGratuitoPatrocinio to set
     */
    public void setTenoreProvvedimentoGratuitoPatrocinio(ComboForm tenoreProvvedimentoGratuitoPatrocinio) {

        this.tenoreProvvedimentoGratuitoPatrocinio = tenoreProvvedimentoGratuitoPatrocinio;
    }

    public void setTipoInteresse(ComboForm tipoInteresse) {

        this.tipoInteresse = tipoInteresse;
    }

    /**
     * @param tipoSocieta
     *            the tipoSocieta to set
     */
    public void setTipoSocieta(String tipoSocieta) {

        this.tipoSocieta = tipoSocieta;
    }

    /**
     * @param tipoSoggetto
     *            the tipoSoggetto to set
     */
    public void setTipoSoggetto(String tipoSoggetto) {

        this.tipoSoggetto = tipoSoggetto;
    }

    private void concatenaCampiFisici(StringBuilder descrizione) {

        if (cognome != null) {
            descrizione.append(cognome);
        }
        if (nome != null) {
            descrizione.append(" ");
            descrizione.append(nome);
        }
        if ((dataNascita != null) || ((comuneNascita != null) && (comuneNascita.getDescrizione() != null))) {
            descrizione.append(" ");
            if ((sesso != null) && sesso.equalsIgnoreCase("f")) {
                descrizione.append("nata");
            } else {
                descrizione.append("nato");
            }
            if (((comuneNascita != null) && (comuneNascita.getDescrizione() != null)) || (descrizioneComuneEsteroNascita != null)) {
            		//caso selezione stato italia
                if (comuneNascita != null && comuneNascita.getId() != null && comuneNascita.getDescrizione() != null) {
                	descrizione.append(" a ");
                    descrizione.append(comuneNascita.getDescrizione());
                } else {
                	//caso selezione stato estero
                	
                	//setto su descrizioneComuneEsteroNascita la DESCRZIONE inserita nel campo "Comune"
                	setDescrizioneComuneEsteroNascita(descrizioneComuneEsteroNascita);
                    if (descrizioneComuneEsteroNascita != null) {
                    	descrizione.append(" in ");
                    	descrizione.append(statoNascita.getDescrizione());
                    	descrizione.append(" (");
                        descrizione.append(descrizioneComuneEsteroNascita);
                        descrizione.append(") ");
                    }
                }
            }
            if (dataNascita != null) {
                descrizione.append(" il ");
                descrizione.append(Utility.convertDateToString(dataNascita));
            }
        }
    }

    private void concatenaCampiGiuridici(StringBuilder descrizione) {

        if (ragioneSociale != null) {
            descrizione.append(ragioneSociale);
        }
        if (((comuneSedeLegale != null) && (comuneSedeLegale.getDescrizione() != null)) || (descrizioneComuneSedeLegaleItalianoEstero != null)) {
            descrizione.append(" con sede in ");
            if (((comuneSedeLegale != null) && (comuneSedeLegale.getDescrizione() != null))) {
                descrizione.append(comuneSedeLegale.getDescrizione());
            } else
                if (descrizioneComuneSedeLegaleItalianoEstero != null) {
                    descrizione.append(descrizioneComuneSedeLegaleItalianoEstero);
                }
        }
        if (partitaIva != null) {
            descrizione.append(" partita IVA ");
            descrizione.append(partitaIva);
        }
    }

	public String getSchermataDiProvenienza() {

		return schermataDiProvenienza;
	}

	public void setSchermataDiProvenienza(String schermataDiProvenienza) {

		this.schermataDiProvenienza = schermataDiProvenienza;
	}
	
	public String getCapSocDescr(){
		
		String descr = "noInt";
        if(capitaleSociale!=null && java.lang.Math.floor(capitaleSociale.doubleValue())==capitaleSociale.doubleValue())
        	descr = "int";      
        return descr;
	}

	public int getIdRuolo() {
		return idRuolo;
	}

	public ComboForm getTipologiaPrevenzione() {
		return tipologiaPrevenzione;
	}

	public void setTipologiaPrevenzione(ComboForm tipologiaPrevenzione) {
		this.tipologiaPrevenzione = tipologiaPrevenzione;
	}

	public void setIdRuolo(int idRuolo) {
		this.idRuolo = idRuolo;
	}

	public String getPartitaIvaSiglaStato() {
		return partitaIvaSiglaStato;
	}

	public void setPartitaIvaSiglaStato(String partitaIvaSiglaStato) {
		this.partitaIvaSiglaStato = partitaIvaSiglaStato;
	}

	public String getPartitaIvaCompleta() {
		partitaIvaSiglaStato=partitaIvaSiglaStato==null?"":partitaIvaSiglaStato;
		partitaIva=partitaIva==null?"":partitaIva;
		partitaIvaCompleta = partitaIvaSiglaStato+partitaIva;
		if(partitaIva!=null && partitaIva.trim().length()<3){
			partitaIvaCompleta="";
		}
		return partitaIvaCompleta;
	}

	public void setPartitaIvaCompleta(String partitaIvaCompleta) {
		this.partitaIvaCompleta = partitaIvaCompleta;
		if(partitaIvaCompleta!=null && partitaIvaCompleta.trim().length()>2){
			this.partitaIvaSiglaStato =partitaIvaCompleta.substring(0, 2);
			this.partitaIva = partitaIvaCompleta.substring(2);
		}	
	}

	public String getDescrizioneProcedimentoDestinazione() {
		return descrizioneProcedimentoDestinazione;
	}

	public void setDescrizioneProcedimentoDestinazione(
			String descrizioneProcedimentoDestinazione) {
		this.descrizioneProcedimentoDestinazione = descrizioneProcedimentoDestinazione;
	}

	public String getEsisteInProcedeimentoRiunito() {
		return esisteInProcedeimentoRiunito;
	}

	public void setEsisteInProcedeimentoRiunito(String esisteInProcedeimentoRiunito) {
		this.esisteInProcedeimentoRiunito = esisteInProcedeimentoRiunito;
	}

	public Long getIdSoggettoDuplicato() {
		return idSoggettoDuplicato;
	}

	public void setIdSoggettoDuplicato(Long idSoggettoDuplicato) {
		this.idSoggettoDuplicato = idSoggettoDuplicato;
	}

	public Long getIdVersioneDuplicato() {
		return idVersioneDuplicato;
	}

	public void setIdVersioneDuplicato(Long idVersioneDuplicato) {
		this.idVersioneDuplicato = idVersioneDuplicato;
	}

	public String getTipoSoggettoDuplicato() {
		return tipoSoggettoDuplicato;
	}

	public void setTipoSoggettoDuplicato(String tipoSoggettoDuplicato) {
		this.tipoSoggettoDuplicato = tipoSoggettoDuplicato;
	}

	public String getDescrRuoloDuplicato() {
		return descrRuoloDuplicato;
	}

	public void setDescrRuoloDuplicato(String descrRuoloDuplicato) {
		this.descrRuoloDuplicato = descrRuoloDuplicato;
	}

	public String getVisibilitaIIGrado() {
		return visibilitaIIGrado;
	}

	public void setVisibilitaIIGrado(String visibilitaIIGrado) {
		this.visibilitaIIGrado = visibilitaIIGrado;
	}

	public String getDescSiglaStato() {
		return descSiglaStato;
	}

	public void setDescSiglaStato(String descSiglaStato) {
		this.descSiglaStato = descSiglaStato;
	}

	public String getImportoQuota() {
		return importoQuota;
	}

	public void setImportoQuota(String importoQuota) {
		this.importoQuota = importoQuota;
	}

	public ComboForm getMagistrato() {
		return magistrato;
	}

	public void setMagistrato(ComboForm magistrato) {
		this.magistrato = magistrato;
	}

	public BigDecimal getImpoCassaAmmenda() {
		return impoCassaAmmenda;
	}

	public void setImpoCassaAmmenda(BigDecimal impoCassaAmmenda) {
		this.impoCassaAmmenda = impoCassaAmmenda;
	}

	//mev Spese Processuali
	public BigDecimal getImportoSpese() {
		return importoSpese;
	}

	public void setImportoSpese(BigDecimal importoSpese) {
		this.importoSpese = importoSpese;
	}
	
	public int getIdSpeseProcessuali() {
		return idSpeseProcessuali;
	}
	
	public void setIdSpeseProcessuali(int idSpeseProcessuali) {
		this.idSpeseProcessuali = idSpeseProcessuali;
	}
	
	/**
     * @return the checkTutti
     */
    public boolean isCheckTutti() {
    
        return checkTutti;
    }
      
    /**
     * @param checkTutti
     *            the checkTutti to set
     */
    public void setCheckTutti(boolean checkTutti) {
    
        this.checkTutti = checkTutti;
    } 
    
    /**
     * @return the speseProcessuali
     */
    public List<ComboForm> getListaSpeseProcessuali() {

        return listaSpeseProcessuali;
    }
    
    /**
     * @param listaSpeseProcessuali
     *            the speseProcessuali to set
     */
    public void setListaSpeseProcessuali(List<ComboForm> listaSpeseProcessuali) {

        this.listaSpeseProcessuali = listaSpeseProcessuali;
         
    }

	//mev Spese Processuali
	
	public String getDescNoteGp() {
		return descNoteGp;
	}

	public void setDescNoteGp(String descNoteGp) {
		this.descNoteGp = descNoteGp;
	}

	public Date getDataRicezioneSentenza() {
		return dataRicezioneSentenza;
	}

	public void setDataRicezioneSentenza(Date dataRicezioneSentenza) {
		this.dataRicezioneSentenza = dataRicezioneSentenza;
	}

	public String getNumeAnnoSentenza() {
		return numeAnnoSentenza;
	}

	public void setNumeAnnoSentenza(String numeAnnoSentenza) {
		this.numeAnnoSentenza = numeAnnoSentenza;
	}

	public String getNumeSentenza() {
		return numeSentenza;
	}

	public void setNumeSentenza(String numeSentenza) {
		this.numeSentenza = numeSentenza;
	}

	public String getDescRichiesta() {
		return descRichiesta;
	}

	public void setDescRichiesta(String descRichiesta) {
		this.descRichiesta = descRichiesta;
	}

	public ComboForm getDecisioneErroreMaterialeIGrado() {
		return decisioneErroreMaterialeIGrado;
	}

	public void setDecisioneErroreMaterialeIGrado(
			ComboForm decisioneErroreMaterialeIGrado) {
		this.decisioneErroreMaterialeIGrado = decisioneErroreMaterialeIGrado;
	}

	public ComboForm getDecisioneErroreMaterialeIIIGrado() {
		return decisioneErroreMaterialeIIIGrado;
	}

	public void setDecisioneErroreMaterialeIIIGrado(
			ComboForm decisioneErroreMaterialeIIIGrado) {
		this.decisioneErroreMaterialeIIIGrado = decisioneErroreMaterialeIIIGrado;
	}

	public String getDescAltro1() {
		return descAltro1;
	}

	public void setDescAltro1(String descAltro1) {
		this.descAltro1 = descAltro1;
	}

	public String getDescAltro3() {
		return descAltro3;
	}

	public void setDescAltro3(String descAltro3) {
		this.descAltro3 = descAltro3;
	}

	public Long getIdSoggettoInterveniente() {
		return idSoggettoInterveniente;
	}

	public void setIdSoggettoInterveniente(Long idSoggettoInterveniente) {
		this.idSoggettoInterveniente = idSoggettoInterveniente;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	// flag per sapere se ci sono soggetti associati all'interveniente
	
	private String        flgIntervSoggAssociati = "NO";

	public String getFlgIntervSoggAssociati() {
		return flgIntervSoggAssociati;
	}

	public void setFlgIntervSoggAssociati(String flgIntervSoggAssociati) {
		this.flgIntervSoggAssociati = flgIntervSoggAssociati;
	}
    
	 public String getDecisioneSuDecreto() {
		return decisioneSuDecreto;
	}

	public void setDecisioneSuDecreto(String decisioneSuDecreto) {
		this.decisioneSuDecreto = decisioneSuDecreto;
	}	
	
	public String getSubCheck() {
		return subCheck;
	}

	public void setSubCheck(String subCheck) {
		this.subCheck = subCheck;
	}

	public String getFlgDecretoSequestro() {
		return flgDecretoSequestro;
	}

	public void setFlgDecretoSequestro(String flgDecretoSequestro) {
		this.flgDecretoSequestro = flgDecretoSequestro;
	}
	
	 /**
     * @return the spese Processuali
     */
    public ComboForm getSpeseProcessuali() {

        return speseProcessuali;
    }
    
    /**
     * @param speseprocessuali
     *            the speseProcessuali to set
     */
    public void setSpeseProcessuali(ComboForm speseProcessuali) {

        this.speseProcessuali = speseProcessuali;
    }

}
