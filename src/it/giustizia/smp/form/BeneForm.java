package it.giustizia.smp.form;

import it.giustizia.smp.dto.SchedaAttiAmmDto;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.validation.date.ModernDate;
import it.giustizia.smp.xml.DatiBeneAziendaGest;
import it.giustizia.smp.xml.DatiBeneFinanzGest;
import it.giustizia.smp.xml.DatiBeneImmaterialeGest;
import it.giustizia.smp.xml.DatiBeneImmobileGest;
import it.giustizia.smp.xml.DatiBeneMobileGest;
import it.giustizia.smp.xml.DatiBeneVeicoloGest;
import it.giustizia.smp.xml.amministratori.BeneExtType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.util.AutoPopulatingList;

/**
 * BeneForm
 * 
 * @author Almaviva S.p.A.
 *         classe form per la gestione dei beni del procedimento
 */
public class BeneForm extends ProcedimentoForm {

    private String              abi;                                    // Campo
                                                                        // nascosto
                                                                        // codice
                                                                        // ABI
    private boolean beneFromBDC = false;
   
    @Pattern(regexp = Constants.RegExp.COGNOME)
    private String        amministratore;
    @NumberFormat
    private Short         annoDenuncia;

    @NumberFormat
    private Short         annoDenunciaP;

    private String        appartenenza;

    @NumberFormat
    private Integer       are;
    private int           cap;
    @NumberFormat(style = Style.NUMBER, pattern = "###,###.##")
    @Max(value = 1000000000000L)
    private BigDecimal    capitaleSociale;
    private ComboForm     categoria;
    private ComboForm     categoriaCatastale;
    @NumberFormat
    private Integer       centiare;
    private String        codiceAbi;                            // Campo
                                                                // di
                                                                // input
                                                                // inserimento
                                                                // ABI
    private ComboForm     codiceCab;                            // Menu
                                                                // per
                                                                // completamento
                                                                // automatico
    private String        codiceCatastale1;
    private String        codiceCatastale1P;
    private String        codiceCatastale2;
    private String        codiceCatastale2P;
    private String        codiceFiscale;
    private String        codiceIban;
    private String		  codiceSwift;
    @NumberFormat
    private Long          codImmatricolazioneVeicolo;
    private String        coloreVeicolo;
    private ComboForm     comboDisponibilita;
	private ComboForm     comune;
    private ComboForm     comuneCatastaleP;
    private String        costituitoInAzienda;
    private String        criteriFiltro;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date          dataAccatastamento;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date          dataRegistrazione;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date          dataRegistrazioneMax;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date          dataRegistrazioneMin;
    // Mobile Registrato - Bene Immateriale
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date          dataScadenza;
    private ComboForm     decisioneBene = new ComboForm();
    private ComboForm     decisioneBeneIGrado = new ComboForm();
    private String        descComuneCatastale;
    private String        desComuneEstero;
    private ComboForm     descOperFinanz;                       // Menu
                                                                // per
                                                                // completamento
                                                                // automatico
    private String        descrizioneBene;
    // **********************************************************//
    // BENE MOBILE - Animali
    private String        descrizioneCapo;
    // BENE MOBILE - Collezioni
    private String        descrizioneCollezione;
    // bene FINANZIARIO:
    private String        descrizioneEnte;     
    
    //inseriti per la visualizzazione dell'atto correzione errore materiale
    private String        descrizioneBene2; 
    private Long          idBene2;
                                                                        // Campo
                                                                        // nascosto
                                                                        // descrizione
                                                                        // banca
                                                                        // ABI
    private String        descrizioneFiliale;
    private String        descrizioneVincolo;
    private String        descUbicazione;
    // variabile per Dettaglio-Associa Bene
    private String        dettAssBene;
    private String        direttoreProtempore;
    private boolean       disabledCheckDefinitivita = false;
    private boolean       disabledProvincia;
    private boolean       disabledValutaEst;
    private boolean       visualizzaRicercaBeneBDC;
    private String        disponibilita;
    private String        edificio;
    @NumberFormat
    private Integer       ettari;
    private String        foglio;
    private String        foglioP;
    private Character     fondo;
    private String        frazione;
    private String        gravame;
    private Long          idBene;  
    private Integer       idUffOwner;
    private Long versioneBene;
    @NumberFormat
    private Long          idComuneCatastale;
    // variabile di appoggio per la selezione del radiobutton in
    // titoliDiInteresse.jsp
    private long          identificativoSoggettoBeneProcedimento;
    
    private String        identificativoSoggettoBeneProcedimentoTitoloDiritto;
    // variabile di appoggio per la selezione del radiobutton in
    // titoliDiInteresse.jsp
    private long          identificativoSoggettoProcedimento;
    private Long		  idRadio;
    private IndirizzoForm indirizzo;
    private String        indirizzoBeneFin;

	private String interno;
    private String        intestatario;
    // attributo per la gestione di checkbox
    private List<Long>    listaIdCheckbox;
    private List<String>  listaIdCheckboxString;
    private String        localita;
    private String        lotto;
    @NumberFormat
    private Short         metriCubiDecimale;
    @NumberFormat
    private Short         metriCubiIntero;
    @NumberFormat
    private Byte          metriQuadriDecimale;
    @NumberFormat
    private Short         metriQuadriIntero;
    private String        modelloVeicolo;
    private String        note;
    private String        numBollettino;
    @NumberFormat
    private Long          numeroAnimali;
    private String        numeroCCIAA;
    @NumberFormat
    private Integer       numeroDenuncia1;
    @NumberFormat
    private Integer       numeroDenuncia1P;
    @NumberFormat
    private Short         numeroDenuncia2;
    @NumberFormat
    private Short         numeroDenuncia2P;
    @NumberFormat
    private Long          numeroDipendenti;
    private String        numeroIdentificazione;
    @NumberFormat
    private Long          numeroPezzi;
    private String        numeroRegistrazione;
    @NumberFormat
    private Long          numModelloTribunale;
    @NumberFormat
    private Long          numRegBeneMobileRegistrato;            // veicolo
    private String        particella1;
    private String        particella1P;
    private String        particella2;
    private String        particella2P;
    private String        partitaIva;
    private String		  partitaIvaSiglaStato;
    private String		  partitaIvaCompleta;
    private String        piano;
    private ComboForm     provincia;                              // Combo
                                                                // selezione
                                                                // provincia
                                                                // bene AZIENDA:
    private String        provinciaSel;
    private String        ragioneSociale;
    @Pattern(regexp = "[a-zA-z\\s]*")
    private String        rappresentanteSociale;
    @NumberFormat
    private BigDecimal    renditaCatastale;
    @NumberFormat
    private BigDecimal    renditaDominicale;
    // ************************* IMMOBILE ************************///
    private String        scala;
    private String        schedaAccatastamento;
    private ComboForm     settoreAttivita;
    private String        sezione;
    private String        sezioneP;
    private ComboForm     sottocategoria;
    private ComboForm     stato;
    private ComboForm     statoConservazione;
    private String        stima;
    private String        sub1;
    private String        sub1P;
    private String        sub2;
    private String        sub2P;
    private String        switchDenunciaIdentifImmobile;
    private String        switchDenunciaIdentifImmobileP;
    private Long		  numeGraffati;
    // Mobile Registrato
    private String        targa;
    // variabile per gestire il punto di arrivo del taso indietro
    private String        tastoIndietro;
    private String        telaio;
    private String        tipoCatasto;
    private Character     tipoCatastoP;
    private Character     tipoDenuncia;
    private Character     tipoDenunciaP;
    private ComboForm     tipologia;
    private ComboForm     tipoOccupanti;
    private ComboForm     tipoValuta;
    private String        titoliDiDiritto = "NO";
    @NumberFormat(style = Style.NUMBER, pattern = "###,###.##")
    @Max(value = 1000000000000L)
    private BigDecimal    valutaEst;
    @NumberFormat(style = Style.NUMBER, pattern = "###,###.##")
    @Max(value = 1000000000000L)
    private BigDecimal    valutaEur;
    @NumberFormat
    private Short         vaniDecimale;
    @NumberFormat
    private Short         vaniIntero;
    private ComboForm     vincolo;
    private boolean       visualizzaFiltro;
    @NumberFormat
    private Long          zonaCensuaria;
    
    //annullamentoSoggetti
    private int 				      idTipologia;
    private int						  idCategoria;
    private int						  idSottoCategoria;
    
    private Long					  idBeneDuplicato;
    private Integer					  idUffOwnerDuplicato;
    private int 				      idTipologiaDuplicato;
    private int						  idCategoriaDuplicato;
    private int						  idSottoCategoriaDuplicato;
    
    private String 					  tipologiaCategoriaSottoCategoria;
    
    private ComboForm     tipoTrasformazione;
    
    private String        altraTrasformazione;
    ///// campi per il FUG
    private ComboForm     modelloFugSelected;
    private String 		  uffMittente;
    private String 		  cancelliere;
    private String 		  codiceOperatore;
    protected long        codAtto;
    protected String      numeAtto;
    private String 		  protocollo;
    private String 		  protocolloRif;
    @NumberFormat(style = Style.NUMBER, pattern = "###.###,##")
    private String 		  importo;
    private Long          idInvio;
    private Long          idSoggetto;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date          dataDeposito; 			//Mod A
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date          dataDefinitivita; 		//Mod D
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date          dataNotifica;				//Mod C-Bis
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date          dataIrrevocabileRevoca;	//Mod C-Bis
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date          dataTrasferimento; 			//Mod H
    private List<AttoForm> listaQuote = new ArrayList<AttoForm>();
    @SuppressWarnings("rawtypes")
    private List listaSoggetti = new ArrayList();
    ///// fine FUG
    //gestione codici Equitalia
    private ComboForm     flgVersamento;
   
   //dati relativi all'Amministratore 
    private Long          idAmministratore;  
    private String        nominativoAmministratore;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date	dataInizioIncarico;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date	dataFineIncarico;
    private String		  warning;
    private String		  isBeneInIstanzaCamerale;
    
    private Long          	idDeposito;

    private String numeroPolizza;
    
    private boolean isEnteFittizio = false;

	public String getNumeroPolizza() {
		return numeroPolizza;
	}

	public void setNumeroPolizza(String numeroPolizza) {
		this.numeroPolizza = numeroPolizza;
	}

	private String provinciaEntePolizza;

	public String getProvinciaEntePolizza() {
		return provinciaEntePolizza;
	}

	public void setProvinciaEntePolizza(String provinciaEntePolizza) {
		this.provinciaEntePolizza = provinciaEntePolizza;
	}

	public BeneForm() {

        super();
        listaIdCheckbox = new AutoPopulatingList<Long>(Long.class);
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
        BeneForm other = (BeneForm) obj;
        if (idBene == null) {
            if (other.idBene != null) {
                return false;
            }
        } else
            if (!idBene.equals(other.idBene)) {
                return false;
            }
        return true;
    }
    
    /**
     * @return the abi
     */
    public String getAbi() {

        return abi;
    }
    
    /**
     * @return the amministratore
     */
    public String getAmministratore() {

        return amministratore;
    }
    
    /**
     * @return the annoDenuncia
     */
    public Short getAnnoDenuncia() {

        return annoDenuncia;
    }
    
    /**
     * @return the annoDenunciaP
     */
    public Short getAnnoDenunciaP() {

        return annoDenunciaP;
    }
    
    /**
     * @return the appartenenza
     */
    public String getAppartenenza() {

        return appartenenza;
    }
    
    /**
     * @return the are
     */
    public Integer getAre() {

        return are;
    }
    
    /**
     * @return the cap
     */
    public int getCap() {

        return cap;
    }
    
    /**
     * @return the capitaleSociale
     */
    public BigDecimal getCapitaleSociale() {

        return capitaleSociale;
    }

    /**
     * @return the categoria
     */
    public ComboForm getCategoria() {

        return categoria;
    }

    /**
     * @return the categoriaCatastale
     */
    public ComboForm getCategoriaCatastale() {

        return categoriaCatastale;
    }

    /**
     * @return the centiare
     */
    public Integer getCentiare() {

        return centiare;
    }

    /**
     * @return the codiceAbi
     */
    public String getCodiceAbi() {

        return codiceAbi;
    }

    /**
     * @return the codiceCab
     */
    public ComboForm getCodiceCab() {

        return codiceCab;
    }

    /**
     * @return the codiceCatastale1
     */
    public String getCodiceCatastale1() {

        return codiceCatastale1;
    }

    /**
     * @return the codiceCatastale1P
     */
    public String getCodiceCatastale1P() {

        return codiceCatastale1P;
    }

    /**
     * @return the codiceCatastale2
     */
    public String getCodiceCatastale2() {

        return codiceCatastale2;
    }

    /**
     * @return the codiceCatastale2P
     */
    public String getCodiceCatastale2P() {

        return codiceCatastale2P;
    }

    /**
     * @return the codiceFiscale
     */
    public String getCodiceFiscale() {

        return codiceFiscale;
    }

    /**
     * @return the codiceIban
     */
    public String getCodiceIban() {

        return codiceIban;
    }

    /**
     * @return the codImmatricolazioneVeicolo
     */
    public Long getCodImmatricolazioneVeicolo() {

        return codImmatricolazioneVeicolo;
    }

    /**
     * @return the coloreVeicolo
     */
    public String getColoreVeicolo() {

        return coloreVeicolo;
    }

    /**
     * @return the comboDisponibilita
     */
    public ComboForm getComboDisponibilita() {

        return comboDisponibilita;
    }

    /**
     * @return the comune
     */
    public ComboForm getComune() {

        return comune;
    }

    /**
     * @return the comuneCatastaleP
     */
    public ComboForm getComuneCatastaleP() {

        return comuneCatastaleP;
    }

    /**
     * @return the costituitoInAzienda
     */
    public String getCostituitoInAzienda() {

        return costituitoInAzienda;
    }

    /**
     * @return the criteriFiltro
     */
    public String getCriteriFiltro() {

        if (idBene != null || (tipologia != null && tipologia.getId() != null && !tipologia.getId().equals("-1")) || (categoria != null && categoria.getId() != null && !categoria.getId().equals("-1")) || (sottocategoria != null && sottocategoria.getId() != null && !sottocategoria.getId().equals("-1"))) {
            StringBuilder descrizione = new StringBuilder();
            descrizione.append(" con");
            if (idBene != null) {
                descrizione.append(" IdBene: ");
                descrizione.append(idBene);
                if ((tipologia != null && tipologia.getId() != null && !tipologia.getId().equals("-1")) || (categoria != null && categoria.getId() != null && !categoria.getId().equals("-1")) || (sottocategoria != null && sottocategoria.getId() != null && !sottocategoria.getId().equals("-1"))) {
                    descrizione.append(";");
                }
            }
            if (tipologia != null && tipologia.getId() != null && !tipologia.getId().equals("-1")) {
                descrizione.append(" Tipologia: ");
                descrizione.append(tipologia.getDescrizione());
                if ((categoria != null && categoria.getId() != null && !categoria.getId().equals("-1")) || (sottocategoria != null && sottocategoria.getId() != null && !sottocategoria.getId().equals("-1"))) {
                    descrizione.append(";");
                }
            }
            if (categoria != null && categoria.getId() != null && !categoria.getId().equals("-1")) {
                descrizione.append(" Categoria: ");
                descrizione.append(categoria.getDescrizione());
                if ((sottocategoria != null && sottocategoria.getId() != null && !sottocategoria.getId().equals("-1"))) {
                    descrizione.append(";");
                }
            }
            if (sottocategoria != null && sottocategoria.getId() != null && !sottocategoria.getId().equals("-1")) {
                descrizione.append(" Sottocategoria: ");
                descrizione.append(sottocategoria.getDescrizione());
            }
            this.criteriFiltro = descrizione.toString();
        } else criteriFiltro = null;
        return criteriFiltro;
    }

    /**
     * @return the dataAccatastamento
     */
    public Date getDataAccatastamento() {

        return dataAccatastamento;
    }

    public Date getDataRegistrazione() {

        return dataRegistrazione;
    }

    public Date getDataRegistrazioneMax() {

        return dataRegistrazioneMax;
    }

    public Date getDataRegistrazioneMin() {

        return dataRegistrazioneMin;
    }

    /**
     * @return the dataScadenza
     */
    public Date getDataScadenza() {

        return dataScadenza;
    }

    /**
     * @return the decisioneBene
     */
    public ComboForm getDecisioneBene() {

        return decisioneBene;
    }

    /**
     * @return the descComuneCatastale
     */
    public String getDescComuneCatastale() {

        return descComuneCatastale;
    }

    /**
     * @return the desComuneEstero
     */
    public String getDesComuneEstero() {

        if (stato != null && !"1".equalsIgnoreCase(stato.getId()) && comune != null && comune.getDescrizione() != null) {
            desComuneEstero = comune.getDescrizione();
        }
        return desComuneEstero;
    }

    public ComboForm getDescOperFinanz() {

        return descOperFinanz;
    }

    /**
     * @return the descrizioneBene
     */
    public String getDescrizioneBene() {

        return descrizioneBene;
    }

    /**
     * @return the descrizioneCapo
     */
    public String getDescrizioneCapo() {

        return descrizioneCapo;
    }

    /**
     * @return the descrizioneCollezione
     */
    public String getDescrizioneCollezione() {

        return descrizioneCollezione;
    }

    /**
     * @return the descrizioneEnte
     */
    public String getDescrizioneEnte() {

        return descrizioneEnte;
    }

    /**
     * @return the descrizioneFiliale
     */
    public String getDescrizioneFiliale() {

        return descrizioneFiliale;
    }

    /**
     * @return the descrizioneVincolo
     */
    public String getDescrizioneVincolo() {

        return descrizioneVincolo;
    }

    /**
     * @return the descUbicazione
     */
    public String getDescUbicazione() {

        return descUbicazione;
    }

    /**
     * @return the dettAssBene
     */
    public String getDettAssBene() {

        return dettAssBene;
    }

    /**
     * @return the direttoreProtempore
     */
    public String getDirettoreProtempore() {

        return direttoreProtempore;
    }

    /**
     * @return the disponibilita
     */
    public String getDisponibilita() {

        return disponibilita;
    }

    /**
     * @return the edificio
     */
    public String getEdificio() {

        return edificio;
    }

    /**
     * @return the ettari
     */
    public Integer getEttari() {

        return ettari;
    }

    /**
     * @return the foglio
     */
    public String getFoglio() {

        return foglio;
    }

    /**
     * @return the foglioP
     */
    public String getFoglioP() {

        return foglioP;
    }

    /**
     * @return the fondo
     */
    public Character getFondo() {

        return fondo;
    }

    /**
     * @return the frazione
     */
    public String getFrazione() {
    
        return frazione;
    }
    
    /**
     * @return the gravame
     */
    public String getGravame() {

        return gravame;
    }

    /**
     * @return the idBene
     */
    public Long getIdBene() {

        return idBene;
    }

    /**
     * @return the idComuneCatastale
     */
    public Long getIdComuneCatastale() {

        return idComuneCatastale;
    }

    /**
     * @return the identificativoSoggettoBeneProcedimento
     */
    public long getIdentificativoSoggettoBeneProcedimento() {

        return identificativoSoggettoBeneProcedimento;
    }

    /**
     * @return the identificativoSoggettoProcedimento
     */
    public long getIdentificativoSoggettoProcedimento() {

        return identificativoSoggettoProcedimento;
    }

    public Long getIdRadio() {
		return idRadio;
	}
	/**
     * @return the indirizzo
     */
    public IndirizzoForm getIndirizzo() {

        return indirizzo;
    }

    /**
     * @return the indirizzoBeneFin
     */
    public String getIndirizzoBeneFin() {
    
        return indirizzoBeneFin;
    }

    /**
     * @return the interno
     */
    public String getInterno() {

        return interno;
    }

    /**
     * @return the intestatario
     */
    public String getIntestatario() {
    
        return intestatario;
    }

    /**
     * @return the listaIdCheckbox
     */
    public List<Long> getListaIdCheckbox() {

        return listaIdCheckbox;
    }

    /**
     * @return the listaIdCheckboxString
     */
    public List<String> getListaIdCheckboxString() {

        return listaIdCheckboxString;
    }

    /**
     * @return the localita
     */
    public String getLocalita() {
    
        return localita;
    }

    /**
     * @return the lotto
     */
    public String getLotto() {

        return lotto;
    }

    /**
     * @return the metriCubiDecimale
     */
    public Short getMetriCubiDecimale() {

        return metriCubiDecimale;
    }
    
    /**
     * @return the metriCubiIntero
     */
    public Short getMetriCubiIntero() {

        return metriCubiIntero;
    }

    /**
     * @return the metriQuadriDecimale
     */
    public Byte getMetriQuadriDecimale() {

        return metriQuadriDecimale;
    }

    /**
     * @return the metriQuadriIntero
     */
    public Short getMetriQuadriIntero() {

        return metriQuadriIntero;
    }

    /**
     * @return the modelloVeicolo
     */
    public String getModelloVeicolo() {

        return modelloVeicolo;
    }

    /**
     * @return the note
     */
    public String getNote() {

        return note;
    }

    /**
     * @return the numBollettino
     */
    public String getNumBollettino() {
    
        return numBollettino;
    }

    /**
     * @return the numeroAnimali
     */
    public Long getNumeroAnimali() {

        return numeroAnimali;
    }

    /**
     * @return the numeroCCIAA
     */
    public String getNumeroCCIAA() {

        return numeroCCIAA;
    }

    /**
     * @return the numeroDenuncia1
     */
    public Integer getNumeroDenuncia1() {

        return numeroDenuncia1;
    }

    /**
     * @return the numeroDenuncia1P
     */
    public Integer getNumeroDenuncia1P() {

        return numeroDenuncia1P;
    }

    /**
     * @return the numeroDenuncia2
     */
    public Short getNumeroDenuncia2() {

        return numeroDenuncia2;
    }

    /**
     * @return the numeroDenuncia2P
     */
    public Short getNumeroDenuncia2P() {

        return numeroDenuncia2P;
    }

    /**
     * @return the numeroDipendenti
     */
    public Long getNumeroDipendenti() {

        return numeroDipendenti;
    }

    /**
     * @return the numeroIdentificazione
     */
    public String getNumeroIdentificazione() {

        return numeroIdentificazione;
    }

    /**
     * @return the numeroPezzi
     */
    public Long getNumeroPezzi() {

        return numeroPezzi;
    }

    /**
     * @return the numeroRegistrazione
     */
    public String getNumeroRegistrazione() {

        return numeroRegistrazione;
    }

    /**
     * @return the numModelloTribunale
     */
    public Long getNumModelloTribunale() {
    
        return numModelloTribunale;
    }

    /**
     * @return the numRegBeneMobileRegistrato
     */
    public Long getNumRegBeneMobileRegistrato() {

        return numRegBeneMobileRegistrato;
    }

    /**
     * @return the particella1
     */
    public String getParticella1() {

        return particella1;
    }

    /**
     * @return the particella1P
     */
    public String getParticella1P() {

        return particella1P;
    }

    /**
     * @return the particella2
     */
    public String getParticella2() {

        return particella2;
    }

    /**
     * @return the particella2P
     */
    public String getParticella2P() {

        return particella2P;
    }

    /**
     * @return the partitaIva
     */
    public String getPartitaIva() {

        return partitaIva;
    }

    /**
     * @return the piano
     */
    public String getPiano() {

        return piano;
    }

    public ComboForm getProvincia() {

        return provincia;
    }

    /**
     * @return the getProvinciaSel
     */
    public String getProvinciaSel() {
        
        return provinciaSel;
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
     * @return the renditaCatastale
     */
    public BigDecimal getRenditaCatastale() {

        return renditaCatastale;
    }

    /**
     * @return the renditaDominicale
     */
    public BigDecimal getRenditaDominicale() {

        return renditaDominicale;
    }

    /**
     * @return the scala
     */
    public String getScala() {

        return scala;
    }
    
    /**
     * @return the schedaAccatastamento
     */
    public String getSchedaAccatastamento() {

        return schedaAccatastamento;
    }

    /**
     * @return the settoreAttivita
     */
    public ComboForm getSettoreAttivita() {

        return settoreAttivita;
    }

    /**
     * @return the sezione
     */
    public String getSezione() {

        return sezione;
    }

    /**
     * @return the sezioneP
     */
    public String getSezioneP() {

        return sezioneP;
    }

    /**
     * @return the sottocategoria
     */
    public ComboForm getSottocategoria() {

        return sottocategoria;
    }

    /**
     * @return the stato
     */
    public ComboForm getStato() {

        return stato;
    }

    /**
     * @return the statoConservazione
     */
    public ComboForm getStatoConservazione() {

        return statoConservazione;
    }

    /**
     * @return the stima
     */
    public String getStima() {

        return stima;
    }

    /**
     * @return the sub1
     */
    public String getSub1() {

        return sub1;
    }

    /**
     * @return the sub1P
     */
    public String getSub1P() {

        return sub1P;
    }

    /**
     * @return the sub2
     */
    public String getSub2() {

        return sub2;
    }

    /**
     * @return the sub2P
     */
    public String getSub2P() {

        return sub2P;
    }

    /**
     * @return the switchDenunciaIdentifImmobile
     */
    public String getSwitchDenunciaIdentifImmobile() {

        return switchDenunciaIdentifImmobile;
    }

    /**
     * @return the switchDenunciaIdentifImmobileP
     */
    public String getSwitchDenunciaIdentifImmobileP() {

        return switchDenunciaIdentifImmobileP;
    }

    public String getTarga() {

        return targa;
    }

    /**
     * @return the tastoIndietro
     */
    public String getTastoIndietro() {

        return tastoIndietro;
    }

    /**
     * @return the telaio
     */
    public String getTelaio() {

        return telaio;
    }

    /**
     * @return the tipoCatasto
     */
    public String getTipoCatasto() {

        return tipoCatasto;
    }

    /**
     * @return the tipoCatastoP
     */
    public Character getTipoCatastoP() {

        return tipoCatastoP;
    }

    /**
     * @return the tipoDenuncia
     */
    public Character getTipoDenuncia() {

        return tipoDenuncia;
    }

    /**
     * @return the tipoDenunciaP
     */
    public Character getTipoDenunciaP() {

        return tipoDenunciaP;
    }

    /**
     * @return the tipologia
     */
    public ComboForm getTipologia() {

        return tipologia;
    }

    /**
     * @return the tipoOccupanti
     */
    public ComboForm getTipoOccupanti() {

        return tipoOccupanti;
    }

    public ComboForm getTipoValuta() {

        return tipoValuta;
    }

    public String getTitoliDiDiritto() {
		return titoliDiDiritto;
	}
	public BigDecimal getValutaEst() {

        return valutaEst;
    }

    public BigDecimal getValutaEur() {

        return valutaEur;
    }

    /**
     * @return the vaniDecimale
     */
    public Short getVaniDecimale() {

        return vaniDecimale;
    }

    /**
     * @return the vaniIntero
     */
    public Short getVaniIntero() {

        return vaniIntero;
    }

    /**
     * @return the vincolo
     */
    public ComboForm getVincolo() {

        return vincolo;
    }

    /**
     * @return the zonaCensuaria
     */
    public Long getZonaCensuaria() {

        return zonaCensuaria;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((idBene == null) ? 0 : idBene.hashCode());
        return result;
    }
    
    /**
     * @return the disabledCheckDefinitivita
     */
    public boolean isDisabledCheckDefinitivita() {
    
        return disabledCheckDefinitivita;
    }
    
    /**
     * @return the disabledProvincia
     */
    public boolean isDisabledProvincia() {
    
        return disabledProvincia;
    }

    /**
     * @return the disabledValutaEst
     */
    public boolean isDisabledValutaEst() {
    
        return disabledValutaEst;
    }

    /**
     * @return the visualizzaFiltro
     */
    public boolean isVisualizzaFiltro() {

        return visualizzaFiltro;
    }

    public void setAbi(String abi) {

        this.abi = abi;
    }

    /**
     * @param amministratore
     *            the amministratore to set
     */
    public void setAmministratore(String amministratore) {

        this.amministratore = amministratore;
    }

    /**
     * @param annoDenuncia
     *            the annoDenuncia to set
     */
    public void setAnnoDenuncia(Short annoDenuncia) {

        this.annoDenuncia = annoDenuncia;
    }

    /**
     * @param annoDenunciaP
     *            the annoDenunciaP to set
     */
    public void setAnnoDenunciaP(Short annoDenunciaP) {

        this.annoDenunciaP = annoDenunciaP;
    }

    /**
     * @param appartenenza
     *            the appartenenza to set
     */
    public void setAppartenenza(String appartenenza) {

        this.appartenenza = appartenenza;
    }

    /**
     * @param are
     *            the are to set
     */
    public void setAre(Integer are) {

        this.are = are;
    }

    /**
     * @param cap
     *            the cap to set
     */
    public void setCap(int cap) {

        this.cap = cap;
    }

    /**
     * @param capitaleSociale
     *            the capitaleSociale to set
     */
    public void setCapitaleSociale(BigDecimal capitaleSociale) {

        this.capitaleSociale = capitaleSociale;
    }

    /**
     * @param categoria
     *            the categoria to set
     */
    public void setCategoria(ComboForm categoria) {

        this.categoria = categoria;
    }

    /**
     * @param categoriaCatastale
     *            the categoriaCatastale to set
     */
    public void setCategoriaCatastale(ComboForm categoriaCatastale) {

        this.categoriaCatastale = categoriaCatastale;
    }

    /**
     * @param centiare
     *            the centiare to set
     */
    public void setCentiare(Integer centiare) {

        this.centiare = centiare;
    }

    /**
     * @param codiceAbi
     *            the codiceAbi to set
     */
    public void setCodiceAbi(String codiceAbi) {

        this.codiceAbi = codiceAbi;
    }

    /**
     * @param codiceCab
     *            the codiceCab to set
     */
    public void setCodiceCab(ComboForm codiceCab) {

        this.codiceCab = codiceCab;
    }

    /**
     * @param codiceCatastale1
     *            the codiceCatastale1 to set
     */
    public void setCodiceCatastale1(String codiceCatastale1) {

        this.codiceCatastale1 = codiceCatastale1;
    }

    /**
     * @param codiceCatastale1P
     *            the codiceCatastale1P to set
     */
    public void setCodiceCatastale1P(String codiceCatastale1P) {

        this.codiceCatastale1P = codiceCatastale1P;
    }

    /**
     * @param codiceCatastale2
     *            the codiceCatastale2 to set
     */
    public void setCodiceCatastale2(String codiceCatastale2) {

        this.codiceCatastale2 = codiceCatastale2;
    }

    /**
     * @param codiceCatastale2P
     *            the codiceCatastale2P to set
     */
    public void setCodiceCatastale2P(String codiceCatastale2P) {

        this.codiceCatastale2P = codiceCatastale2P;
    }

    /**
     * @param codiceFiscale
     *            the codiceFiscale to set
     */
    public void setCodiceFiscale(String codiceFiscale) {

        this.codiceFiscale = codiceFiscale;
    }

    /**
     * @param codiceIban
     *            the codiceIban to set
     */
    public void setCodiceIban(String codiceIban) {

        this.codiceIban = codiceIban;
    }

    /**
     * @param codImmatricolazioneVeicolo
     *            the codImmatricolazioneVeicolo to set
     */
    public void setCodImmatricolazioneVeicolo(Long codImmatricolazioneVeicolo) {

        this.codImmatricolazioneVeicolo = codImmatricolazioneVeicolo;
    }

    /**
     * @param coloreVeicolo
     *            the coloreVeicolo to set
     */
    public void setColoreVeicolo(String coloreVeicolo) {

        this.coloreVeicolo = coloreVeicolo;
    }

    /**
     * @param comboDisponibilita
     *            the comboDisponibilita to set
     */
    public void setComboDisponibilita(ComboForm comboDisponibilita) {

        this.comboDisponibilita = comboDisponibilita;
    }

    /**
     * @param comune
     *            the comune to set
     */
    public void setComune(ComboForm comune) {

        this.comune = comune;
    }

    /**
     * @param comuneCatastaleP
     *            the comuneCatastaleP to set
     */
    public void setComuneCatastaleP(ComboForm comuneCatastaleP) {

        this.comuneCatastaleP = comuneCatastaleP;
    }

    /**
     * @param costituitoInAzienda
     *            the costituitoInAzienda to set
     */
    public void setCostituitoInAzienda(String costituitoInAzienda) {

        this.costituitoInAzienda = costituitoInAzienda;
    }

    /**
     * @param criteriFiltro
     *            the criteriFiltro to set
     */
    public void setCriteriFiltro(String criteriFiltro) {

        this.criteriFiltro = criteriFiltro;
    }

    /**
     * @param dataAccatastamento
     *            the dataAccatastamento to set
     */
    public void setDataAccatastamento(Date dataAccatastamento) {

        this.dataAccatastamento = dataAccatastamento;
    }

    public void setDataRegistrazione(Date dataRegistrazione) {

        this.dataRegistrazione = dataRegistrazione;
    }

    public void setDataRegistrazioneMax(Date dataRegistrazioneMax) {

        this.dataRegistrazioneMax = dataRegistrazioneMax;
    }

    public void setDataRegistrazioneMin(Date dataRegistrazioneMin) {

        this.dataRegistrazioneMin = dataRegistrazioneMin;
    }

    /**
     * @param dataScadenza
     *            the dataScadenza to set
     */
    public void setDataScadenza(Date dataScadenza) {

        this.dataScadenza = dataScadenza;
    }

    /**
     * @param decisioneBene
     *            the decisioneBene to set
     */
    public void setDecisioneBene(ComboForm decisioneBene) {

        this.decisioneBene = decisioneBene;
    }

    /**
     * @param descComuneCatastale
     *            the descComuneCatastale to set
     */
    public void setDescComuneCatastale(String descComuneCatastale) {

        this.descComuneCatastale = descComuneCatastale;
    }

    /**
     * @param desComuneEstero
     *            the desComuneEstero to set
     */
    public void setDesComuneEstero(String desComuneEstero) {

        this.desComuneEstero = desComuneEstero;
    }

    public void setDescOperFinanz(ComboForm descOperFinanz) {

        this.descOperFinanz = descOperFinanz;
    }

    /**
     * @param descrizioneBene
     *            the descrizioneBene to set
     */
    public void setDescrizioneBene(String descrizioneBene) {

        this.descrizioneBene = descrizioneBene;
    }

    /**
     * @param descrizioneCapo
     *            the descrizioneCapo to set
     */
    public void setDescrizioneCapo(String descrizioneCapo) {

        this.descrizioneCapo = descrizioneCapo;
    }

    /**
     * @param descrizioneCollezione
     *            the descrizioneCollezione to set
     */
    public void setDescrizioneCollezione(String descrizioneCollezione) {

        this.descrizioneCollezione = descrizioneCollezione;
    }

    /**
     * @param descrizioneEnte
     *            the descrizioneEnte to set
     */
    public void setDescrizioneEnte(String descrizioneEnte) {

        this.descrizioneEnte = descrizioneEnte;
    }

    /**
     * @param descrizioneFiliale
     *            the descrizioneFiliale to set
     */
    public void setDescrizioneFiliale(String descrizioneFiliale) {

        this.descrizioneFiliale = descrizioneFiliale;
    }

    /**
     * @param descrizioneVincolo
     *            the descrizioneVincolo to set
     */
    public void setDescrizioneVincolo(String descrizioneVincolo) {

        this.descrizioneVincolo = descrizioneVincolo;
    }

    /**
     * @param descUbicazione
     *            the descUbicazione to set
     */
    public void setDescUbicazione(String descUbicazione) {

        this.descUbicazione = descUbicazione;
    }

    /**
     * @param dettAssBene
     *            the dettAssBene to set
     */
    public void setDettAssBene(String dettAssBene) {

        this.dettAssBene = dettAssBene;
    }

    /**
     * @param direttoreProtempore
     *            the direttoreProtempore to set
     */
    public void setDirettoreProtempore(String direttoreProtempore) {

        this.direttoreProtempore = direttoreProtempore;
    }

    /**
     * @param disabledCheckDefinitivita
     *            the disabledCheckDefinitivita to set
     */
    public void setDisabledCheckDefinitivita(boolean disabledCheckDefinitivita) {
    
        this.disabledCheckDefinitivita = disabledCheckDefinitivita;
    }
    
    /**
     * @param disabledProvincia
     *            the disabledProvincia to set
     */
    public void setDisabledProvincia(boolean disabledProvincia) {
    
        this.disabledProvincia = disabledProvincia;
    }

    /**
     * @param disabledValutaEst
     *            the disabledValutaEst to set
     */
    public void setDisabledValutaEst(boolean disabledValutaEst) {
    
        this.disabledValutaEst = disabledValutaEst;
    }

    /**
     * @param disponibilita
     *            the disponibilita to set
     */
    public void setDisponibilita(String disponibilita) {

        this.disponibilita = disponibilita;
    }

    /**
     * @param edificio
     *            the edificio to set
     */
    public void setEdificio(String edificio) {

        this.edificio = edificio;
    }
    
    /**
     * @param ettari
     *            the ettari to set
     */
    public void setEttari(Integer ettari) {

        this.ettari = ettari;
    }

    /**
     * @param foglio
     *            the foglio to set
     */
    public void setFoglio(String foglio) {

        this.foglio = foglio;
    }

    /**
     * @param foglioP
     *            the foglioP to set
     */
    public void setFoglioP(String foglioP) {

        this.foglioP = foglioP;
    }

    /**
     * @param fondo
     *            the fondo to set
     */
    public void setFondo(Character fondo) {

        this.fondo = fondo;
    }

    /**
     * @param frazione
     *            the gravame to frazione
     */
    public void setFrazione(String frazione) {
    
        this.frazione = frazione;
    }

    /**
     * @param gravame
     *            the gravame to set
     */
    public void setGravame(String gravame) {

        this.gravame = gravame;
    }

    /**
     * @param idBene
     *            the idBene to set
     */
    public void setIdBene(Long idBene) {

        this.idBene = idBene;
    }
    
    /**
     * @param idComuneCatastale
     *            the idComuneCatastale to set
     */
    public void setIdComuneCatastale(Long idComuneCatastale) {

        this.idComuneCatastale = idComuneCatastale;
    }

    /**
     * @param identificativoSoggettoBeneProcedimento
     *            the identificativoSoggettoBeneProcedimento to set
     */
    public void setIdentificativoSoggettoBeneProcedimento(long identificativoSoggettoBeneProcedimento) {

        this.identificativoSoggettoBeneProcedimento = identificativoSoggettoBeneProcedimento;
    }

    /**
     * @param identificativoSoggettoProcedimento
     *            the identificativoSoggettoProcedimento to set
     */
    public void setIdentificativoSoggettoProcedimento(long identificativoSoggettoProcedimento) {

        this.identificativoSoggettoProcedimento = identificativoSoggettoProcedimento;
    }

    public void setIdRadio(Long idRadio) {
		this.idRadio = idRadio;
	}
	/**
     * @param indirizzo
     *            the indirizzo to set
     */
    public void setIndirizzo(IndirizzoForm indirizzo) {

        this.indirizzo = indirizzo;
    }

    /**
     * @param indirizzoBeneFin
     *            the indirizzoBeneFin to set
     */
    public void setIndirizzoBeneFin(String indirizzoBeneFin) {
    
        this.indirizzoBeneFin = indirizzoBeneFin;
    }

    /**
     * @param interno
     *            the interno to set
     */
    public void setInterno(String interno) {

        this.interno = interno;
    }

    /**
     * @param setIntestatario
     *            the setIntestatario to set
     */
    public void setIntestatario(String intestatario) {
    
        this.intestatario = intestatario;
    }

    /**
     * @param listaIdCheckbox
     *            the listaIdCheckbox to set
     */
    public void setListaIdCheckbox(List<Long> listaIdCheckbox) {

        this.listaIdCheckbox = listaIdCheckbox;
    }

    /**
     * @param listaIdCheckboxString
     *            the listaIdCheckboxString to set
     */
    public void setListaIdCheckboxString(List<String> listaIdCheckboxString) {

        this.listaIdCheckboxString = listaIdCheckboxString;
    }

    /**
     * @param localita
     *            the localita to set
     */
    public void setLocalita(String localita) {
    
        this.localita = localita;
    }

    /**
     * @param lotto
     *            the lotto to set
     */
    public void setLotto(String lotto) {

        this.lotto = lotto;
    }

    /**
     * @param metriCubiDecimale
     *            the metriCubiDecimale to set
     */
    public void setMetriCubiDecimale(Short metriCubiDecimale) {

        this.metriCubiDecimale = metriCubiDecimale;
    }

    /**
     * @param metriCubiIntero
     *            the metriCubiIntero to set
     */
    public void setMetriCubiIntero(Short metriCubiIntero) {

        this.metriCubiIntero = metriCubiIntero;
    }

    /**
     * @param metriQuadriDecimale
     *            the metriQuadriDecimale to set
     */
    public void setMetriQuadriDecimale(Byte metriQuadriDecimale) {

        this.metriQuadriDecimale = metriQuadriDecimale;
    }

    /**
     * @param metriQuadriIntero
     *            the metriQuadriIntero to set
     */
    public void setMetriQuadriIntero(Short metriQuadriIntero) {

        this.metriQuadriIntero = metriQuadriIntero;
    }

    /**
     * @param modelloVeicolo
     *            the modelloVeicolo to set
     */
    public void setModelloVeicolo(String modelloVeicolo) {

        this.modelloVeicolo = modelloVeicolo;
    }

    /**
     * @param note
     *            the note to set
     */
    public void setNote(String note) {

        this.note = note;
    }

    /**
     * @param numBollettino
     *            the numBollettino to set
     */
    public void setNumBollettino(String numBollettino) {
    
        this.numBollettino = numBollettino;
    }

    /**
     * @param numeroAnimali
     *            the numeroAnimali to set
     */
    public void setNumeroAnimali(Long numeroAnimali) {

        this.numeroAnimali = numeroAnimali;
    }

    /**
     * @param numeroCCIAA
     *            the numeroCCIAA to set
     */
    public void setNumeroCCIAA(String numeroCCIAA) {

        this.numeroCCIAA = numeroCCIAA;
    }

    /**
     * @param numeroDenuncia1
     *            the numeroDenuncia1 to set
     */
    public void setNumeroDenuncia1(Integer numeroDenuncia1) {

        this.numeroDenuncia1 = numeroDenuncia1;
    }

    /**
     * @param numeroDenuncia1P
     *            the numeroDenuncia1P to set
     */
    public void setNumeroDenuncia1P(Integer numeroDenuncia1P) {

        this.numeroDenuncia1P = numeroDenuncia1P;
    }

    /**
     * @param numeroDenuncia2
     *            the numeroDenuncia2 to set
     */
    public void setNumeroDenuncia2(Short numeroDenuncia2) {

        this.numeroDenuncia2 = numeroDenuncia2;
    }

    /**
     * @param numeroDenuncia2P
     *            the numeroDenuncia2P to set
     */
    public void setNumeroDenuncia2P(Short numeroDenuncia2P) {

        this.numeroDenuncia2P = numeroDenuncia2P;
    }

    /**
     * @param numeroDipendenti
     *            the numeroDipendenti to set
     */
    public void setNumeroDipendenti(Long numeroDipendenti) {

        this.numeroDipendenti = numeroDipendenti;
    }

    /**
     * @param numeroIdentificazione
     *            the numeroIdentificazione to set
     */
    public void setNumeroIdentificazione(String numeroIdentificazione) {

        this.numeroIdentificazione = numeroIdentificazione;
    }

    /**
     * @param numeroPezzi
     *            the numeroPezzi to set
     */
    public void setNumeroPezzi(Long numeroPezzi) {

        this.numeroPezzi = numeroPezzi;
    }

    /**
     * @param numeroRegistrazione
     *            the numeroRegistrazione to set
     */
    public void setNumeroRegistrazione(String numeroRegistrazione) {

        this.numeroRegistrazione = numeroRegistrazione;
    }

    /**
     * @param numModelloTribunale
     *            the numModelloTribunale to set
     */
    public void setNumModelloTribunale(Long numModelloTribunale) {
    
        this.numModelloTribunale = numModelloTribunale;
    }

    /**
     * @param numRegBeneMobileRegistrato
     *            the numRegBeneMobileRegistrato to set
     */
    public void setNumRegBeneMobileRegistrato(Long numRegBeneMobileRegistrato) {

        this.numRegBeneMobileRegistrato = numRegBeneMobileRegistrato;
    }

    /**
     * @param particella1
     *            the particella1 to set
     */
    public void setParticella1(String particella1) {

        this.particella1 = particella1;
    }

    /**
     * @param particella1p
     *            the particella1P to set
     */
    public void setParticella1P(String particella1p) {

        particella1P = particella1p;
    }

    /**
     * @param particella2
     *            the particella2 to set
     */
    public void setParticella2(String particella2) {

        this.particella2 = particella2;
    }

    /**
     * @param particella2p
     *            the particella2P to set
     */
    public void setParticella2P(String particella2p) {

        particella2P = particella2p;
    }

    /**
     * @param partitaIva
     *            the partitaIva to set
     */
    public void setPartitaIva(String partitaIva) {

        this.partitaIva = partitaIva;
    }

    /**
     * @param piano
     *            the piano to set
     */
    public void setPiano(String piano) {

        this.piano = piano;
    }

    /**
     * @param provincia
     *            the provincia to set
     */
    public void setProvincia(ComboForm provincia) {

        this.provincia = provincia;
    }
    
    /**
     * @param provinciaSel
     *            the provinciaSel to set
     */
    public void setProvinciaSel(String provinciaSel) {
        
        this.provinciaSel = provinciaSel;
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
     * @param renditaCatastale
     *            the renditaCatastale to set
     */
    public void setRenditaCatastale(BigDecimal renditaCatastale) {

        this.renditaCatastale = renditaCatastale;
    }

    /**
     * @param renditaDominicale
     *            the renditaDominicale to set
     */
    public void setRenditaDominicale(BigDecimal renditaDominicale) {

        this.renditaDominicale = renditaDominicale;
    }

    /**
     * @param scala
     *            the scala to set
     */
    public void setScala(String scala) {

        this.scala = scala;
    }

    /**
     * @param schedaAccatastamento
     *            the schedaAccatastamento to set
     */
    public void setSchedaAccatastamento(String schedaAccatastamento) {

        this.schedaAccatastamento = schedaAccatastamento;
    }

    /**
     * @param settoreAttivita
     *            the settoreAttivita to set
     */
    public void setSettoreAttivita(ComboForm settoreAttivita) {

        this.settoreAttivita = settoreAttivita;
    }

    /**
     * @param sezione
     *            the sezione to set
     */
    public void setSezione(String sezione) {

        this.sezione = sezione;
    }

    /**
     * @param sezioneP
     *            the sezioneP to set
     */
    public void setSezioneP(String sezioneP) {

        this.sezioneP = sezioneP;
    }

    /**
     * @param sottocategoria
     *            the sottocategoria to set
     */
    public void setSottocategoria(ComboForm sottocategoria) {

        this.sottocategoria = sottocategoria;
    }

    /**
     * @param stato
     *            the stato to set
     */
    public void setStato(ComboForm stato) {

        this.stato = stato;
    }

    /**
     * @param statoConservazione
     *            the statoConservazione to set
     */
    public void setStatoConservazione(ComboForm statoConservazione) {

        this.statoConservazione = statoConservazione;
    }

    /**
     * @param stima
     *            the stima to set
     */
    public void setStima(String stima) {

        this.stima = stima;
    }

    /**
     * @param sub1
     *            the sub1 to set
     */
    public void setSub1(String sub1) {

        this.sub1 = sub1;
    }

    /**
     * @param sub1p
     *            the sub1P to set
     */
    public void setSub1P(String sub1p) {

        sub1P = sub1p;
    }

    /**
     * @param sub2
     *            the sub2 to set
     */
    public void setSub2(String sub2) {

        this.sub2 = sub2;
    }

    /**
     * @param sub2p
     *            the sub2P to set
     */
    public void setSub2P(String sub2p) {

        sub2P = sub2p;
    }

    /**
     * @param switchDenunciaIdentifImmobile
     *            the switchDenunciaIdentifImmobile to set
     */
    public void setSwitchDenunciaIdentifImmobile(String switchDenunciaIdentifImmobile) {

        this.switchDenunciaIdentifImmobile = switchDenunciaIdentifImmobile;
    }

    /**
     * @param switchDenunciaIdentifImmobileP
     *            the switchDenunciaIdentifImmobileP to set
     */
    public void setSwitchDenunciaIdentifImmobileP(String switchDenunciaIdentifImmobileP) {

        this.switchDenunciaIdentifImmobileP = switchDenunciaIdentifImmobileP;
    }

    public void setTarga(String targa) {

        this.targa = targa;
    }

    /**
     * @param tastoIndietro
     *            the tastoIndietro to set
     */
    public void setTastoIndietro(String tastoIndietro) {

        this.tastoIndietro = tastoIndietro;
    }

    /**
     * @param telaio
     *            the telaio to set
     */
    public void setTelaio(String telaio) {

        this.telaio = telaio;
    }

    /**
     * @param tipoCatasto
     *            the tipoCatasto to set
     */
    public void setTipoCatasto(String tipoCatasto) {

        this.tipoCatasto = tipoCatasto;
    }

    /**
     * @param tipoCatastoP
     *            the tipoCatastoP to set
     */
    public void setTipoCatastoP(Character tipoCatastoP) {

        this.tipoCatastoP = tipoCatastoP;
    }

    /**
     * @param tipoDenuncia
     *            the tipoDenuncia to set
     */
    public void setTipoDenuncia(Character tipoDenuncia) {

        this.tipoDenuncia = tipoDenuncia;
    }

    /**
     * @param tipoDenunciaP
     *            the tipoDenunciaP to set
     */
    public void setTipoDenunciaP(Character tipoDenunciaP) {

        this.tipoDenunciaP = tipoDenunciaP;
    }

    /**
     * @param tipologia
     *            the tipologia to set
     */
    public void setTipologia(ComboForm tipologia) {

        this.tipologia = tipologia;
    }

    /**
     * @param tipoOccupanti
     *            the tipoOccupanti to set
     */
    public void setTipoOccupanti(ComboForm tipoOccupanti) {

        this.tipoOccupanti = tipoOccupanti;
    }

    public void setTipoValuta(ComboForm tipoValuta) {

        this.tipoValuta = tipoValuta;
    }

    public void setTitoliDiDiritto(String titoliDiDiritto) {
		this.titoliDiDiritto = titoliDiDiritto;
	}
	public void setValutaEst(BigDecimal valutaEst) {

        this.valutaEst = valutaEst;
    }

    public void setValutaEur(BigDecimal valutaEur) {

        this.valutaEur = valutaEur;
    }

    /**
     * @param vaniDecimale
     *            the vaniDecimale to set
     */
    public void setVaniDecimale(Short vaniDecimale) {

        this.vaniDecimale = vaniDecimale;
    }

    /**
     * @param vaniIntero
     *            the vaniIntero to set
     */
    public void setVaniIntero(Short vaniIntero) {

        this.vaniIntero = vaniIntero;
    }

    /**
     * @param vincolo
     *            the vincolo to set
     */
    public void setVincolo(ComboForm vincolo) {

        this.vincolo = vincolo;
    }

    /**
     * @param visualizzaFiltro
     *            the visualizzaFiltro to set
     */
    public void setVisualizzaFiltro(boolean visualizzaFiltro) {

        this.visualizzaFiltro = visualizzaFiltro;
    }

    /**
     * @param zonaCensuaria
     *            the zonaCensuaria to set
     */
    public void setZonaCensuaria(Long zonaCensuaria) {

        this.zonaCensuaria = zonaCensuaria;
    }

	public String getCapSocDescr(){
		
		String descr = "noInt";
        if(capitaleSociale!=null && java.lang.Math.floor(capitaleSociale.doubleValue())==capitaleSociale.doubleValue())
        	descr = "int";       
        return descr;
	}
    
    /**
     * @return the versioneBene
     */
    public Long getVersioneBene() {
    
        return versioneBene;
    }
    
    /**
     * @param versioneBene the versioneBene to set
     */
    public void setVersioneBene(Long versioneBene) {
    
        this.versioneBene = versioneBene;
    }
    
    /**
     * @return the visualizzaRicercaBeneBDC
     */
    public boolean isVisualizzaRicercaBeneBDC() {
    
        return visualizzaRicercaBeneBDC;
    }
    
    /**
     * @param visualizzaRicercaBeneBDC the visualizzaRicercaBeneBDC to set
     */
    public void setVisualizzaRicercaBeneBDC(boolean visualizzaRicercaBeneBDC) {
    
        this.visualizzaRicercaBeneBDC = visualizzaRicercaBeneBDC;
    }
    
    /**
     * @return the beneFromBDC
     */
    public boolean isBeneFromBDC() {
    
        return beneFromBDC;
    }
    
    /**
     * @param beneFromBDC the beneFromBDC to set
     */
    public void setBeneFromBDC(boolean beneFromBDC) {
    
        this.beneFromBDC = beneFromBDC;
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
	public String getCodiceSwift() {
		return codiceSwift;
	}
	public void setCodiceSwift(String codiceSwift) {
		this.codiceSwift = codiceSwift;
	}
	public String getIdentificativoSoggettoBeneProcedimentoTitoloDiritto() {
		return identificativoSoggettoBeneProcedimentoTitoloDiritto;
	}
	public void setIdentificativoSoggettoBeneProcedimentoTitoloDiritto(
			String identificativoSoggettoBeneProcedimentoTitoloDiritto) {
		this.identificativoSoggettoBeneProcedimentoTitoloDiritto = identificativoSoggettoBeneProcedimentoTitoloDiritto;
	}
	public ComboForm getTipoTrasformazione() {
		return tipoTrasformazione;
	}
	public void setTipoTrasformazione(ComboForm tipoTrasformazione) {
		this.tipoTrasformazione = tipoTrasformazione;
	}
	public Integer getIdUffOwner() {
		return idUffOwner;
	}
	public void setIdUffOwner(Integer idUffOwner) {
		this.idUffOwner = idUffOwner;
	}
	public Long getIdBeneDuplicato() {
		return idBeneDuplicato;
	}
	public void setIdBeneDuplicato(Long idBeneDuplicato) {
		this.idBeneDuplicato = idBeneDuplicato;
	}
	public int getIdTipologia() {
		idTipologia = 0;
		if(tipologia!=null && tipologia.getId()!=null){
			idTipologia = Integer.parseInt(tipologia.getId());
		}
		return idTipologia;
	}
	public void setIdTipologia(int idTipologia) {
		this.idTipologia = idTipologia;
	}
	public int getIdCategoria() {
		idCategoria = 0;
		if(categoria!=null && categoria.getId()!=null){
			idCategoria = Integer.parseInt(categoria.getId());
		}
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public int getIdTipologiaDuplicato() {
		return idTipologiaDuplicato;
	}
	public void setIdTipologiaDuplicato(int idTipologiaDuplicato) {
		this.idTipologiaDuplicato = idTipologiaDuplicato;
	}
	public int getIdCategoriaDuplicato() {
		return idCategoriaDuplicato;
	}
	public void setIdCategoriaDuplicato(int idCategoriaDuplicato) {
		this.idCategoriaDuplicato = idCategoriaDuplicato;
	}
	public int getIdSottoCategoria() {
		idSottoCategoria = 0;
		if(sottocategoria !=null && sottocategoria.getId()!=null){
			idSottoCategoria = Integer.parseInt(sottocategoria.getId());
		}
		return idSottoCategoria;
	}
	public void setIdSottoCategoria(int idSottoCategoria) {
		this.idSottoCategoria = idSottoCategoria;
	}
	public int getIdSottoCategoriaDuplicato() {
		return idSottoCategoriaDuplicato;
	}
	public void setIdSottoCategoriaDuplicato(int idSottoCategoriaDuplicato) {
		this.idSottoCategoriaDuplicato = idSottoCategoriaDuplicato;
	}
	public String getTipologiaCategoriaSottoCategoria() {
		String tip = "";
		String cat = "";
		String sottoCat = "";
		
		if(tipologia.getDescrizione()!=null){
			tip = tipologia.getDescrizione().concat(" / ");
		}
		if(categoria.getDescrizione()!=null){
			cat = categoria.getDescrizione().concat(" / ");
		}
		if(sottocategoria.getDescrizione()!=null){
			sottoCat = sottocategoria.getDescrizione();
		}
		
		tipologiaCategoriaSottoCategoria= tip.concat(cat).concat(sottoCat);
		
		return tipologiaCategoriaSottoCategoria;
	}
	public Integer getIdUffOwnerDuplicato() {
		return idUffOwnerDuplicato;
	}
	public void setIdUffOwnerDuplicato(Integer idUffOwnerDuplicato) {
		this.idUffOwnerDuplicato = idUffOwnerDuplicato;
	}
	public ComboForm getDecisioneBeneIGrado() {
		return decisioneBeneIGrado;
	}
	public void setDecisioneBeneIGrado(ComboForm decisioneBeneIGrado) {
		this.decisioneBeneIGrado = decisioneBeneIGrado;
	}  
	
	public ComboForm getModelloFugSelected() {
		return modelloFugSelected;
	}
	public void setModelloFugSelected(ComboForm modelloFugSelected) {
		this.modelloFugSelected = modelloFugSelected;
	}
	public String getCancelliere() {
		return cancelliere;
	}
	public void setCancelliere(String cancelliere) {
		this.cancelliere = cancelliere;
	}
	public String getCodiceOperatore() {
		return codiceOperatore;
	}
	public void setCodiceOperatore(String codiceOperatore) {
		this.codiceOperatore = codiceOperatore;
	}
	public List<AttoForm> getListaQuote() {
		return listaQuote;
	}
	public void setListaQuote(List<AttoForm> listaQuote) {
		this.listaQuote = listaQuote;
	}
	public long getCodAtto() {
		return codAtto;
	}
	public void setCodAtto(long codAtto) {
		this.codAtto = codAtto;
	}
	public String getNumeAtto() {
		return numeAtto;
	}
	public void setNumeAtto(String numeAtto) {
		this.numeAtto = numeAtto;
	}
	public String getImporto() {
		return importo;
	}
	public void setImporto(String importo) {
		this.importo = importo;
	}
	public Date getDataDeposito() {
		return dataDeposito;
	}
	public void setDataDeposito(Date dataDeposito) {
		this.dataDeposito = dataDeposito;
	}
	public Date getDataDefinitivita() {
		return dataDefinitivita;
	}
	public void setDataDefinitivita(Date dataDefinitivita) {
		this.dataDefinitivita = dataDefinitivita;
	}
	public Date getDataNotifica() {
		return dataNotifica;
	}
	public void setDataNotifica(Date dataNotifica) {
		this.dataNotifica = dataNotifica;
	}
	public Date getDataIrrevocabileRevoca() {
		return dataIrrevocabileRevoca;
	}
	public void setDataIrrevocabileRevoca(Date dataIrrevocabileRevoca) {
		this.dataIrrevocabileRevoca = dataIrrevocabileRevoca;
	}
	public Date getDataTrasferimento() {
		return dataTrasferimento;
	}
	public void setDataTrasferimento(Date dataTrasferimento) {
		this.dataTrasferimento = dataTrasferimento;
	}
	public ComboForm getFlgVersamento() {
		return flgVersamento;
	}
	public void setFlgVersamento(ComboForm flgVersamento) {
		this.flgVersamento = flgVersamento;
	}
	public List getListaSoggetti() {
		return listaSoggetti;
	}
	public void setListaSoggetti(List listaSoggetti) {
		this.listaSoggetti = listaSoggetti;
	}
	public Long getIdAmministratore() {
		return idAmministratore;
	}
	public void setIdAmministratore(Long idAmministratore) {
		this.idAmministratore = idAmministratore;
	}
	public String getNominativoAmministratore() {
		return nominativoAmministratore;
	}
	public void setNominativoAmministratore(String nominativoAmministratore) {
		this.nominativoAmministratore = nominativoAmministratore;
	}
	public String getWarning() {
		return warning;
	}
	public void setWarning(String warning) {
		this.warning = warning;
	}
	public Date getDataInizioIncarico() {
		return dataInizioIncarico;
	}
	public void setDataInizioIncarico(Date dataInizioIncarico) {
		this.dataInizioIncarico = dataInizioIncarico;
	}
	public Date getDataFineIncarico() {
		return dataFineIncarico;
	}
	public void setDataFineIncarico(Date dataFineIncarico) {
		this.dataFineIncarico = dataFineIncarico;
	}
	public String getUffMittente() {
		return uffMittente;
	}
	public void setUffMittente(String uffMittente) {
		this.uffMittente = uffMittente;
	}
	public String getProtocollo() {
		return protocollo;
	}
	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}
	public String getProtocolloRif() {
		return protocolloRif;
	}
	public void setProtocolloRif(String protocolloRif) {
		this.protocolloRif = protocolloRif;
	}
	public Long getIdInvio() {
		return idInvio;
	}
	public void setIdInvio(Long idInvio) {
		this.idInvio = idInvio;
	}
	public Long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getDescrizioneBene2() {
		return descrizioneBene2;
	}
	public void setDescrizioneBene2(String descrizioneBene2) {
		this.descrizioneBene2 = descrizioneBene2;
	}
	public Long getIdBene2() {
		return idBene2;
	}
	public void setIdBene2(Long idBene2) {
		this.idBene2 = idBene2;
	}
	public String getIsBeneInIstanzaCamerale() {
		return isBeneInIstanzaCamerale;
	}
	public void setIsBeneInIstanzaCamerale(String isBeneInIstanzaCamerale) {
		this.isBeneInIstanzaCamerale = isBeneInIstanzaCamerale;
	}
	
	public String getValutaEurFormattata() {
		return Utility.formattaImporto(valutaEur);
	}

	public String getValutaEstFormattata() {
		return Utility.formattaImporto(valutaEst);
	}
	
	// scheda atti dell'amministratore sul dettaglio bene
    private List<SchedaAttiAmmDto> listaSchedaAttiAmm = new ArrayList<SchedaAttiAmmDto>();
        
	public List<SchedaAttiAmmDto> getListaSchedaAttiAmm() {
		return listaSchedaAttiAmm;
	}

	public void setListaSchedaAttiAmm(List<SchedaAttiAmmDto> listaSchedaAttiAmm) {
		this.listaSchedaAttiAmm = listaSchedaAttiAmm;
	}

	private BeneExtType.DatiSpecificiBene  datiSpecificiBene;

	public BeneExtType.DatiSpecificiBene getDatiSpecificiBene() {
		return datiSpecificiBene;
	}

	public void setDatiSpecificiBene(BeneExtType.DatiSpecificiBene datiSpecificiBene) {
		this.datiSpecificiBene = datiSpecificiBene;
	}

	public String getAltraTrasformazione() {
		return altraTrasformazione;
	}

	public void setAltraTrasformazione(String altraTrasformazione) {
		this.altraTrasformazione = altraTrasformazione;
	}

	public Long getIdDeposito() {
		return idDeposito;
	}

	public void setIdDeposito(Long deposito) {
		this.idDeposito = deposito;
	}

	// struttura per contenere i dati di gestione dell'amministratore
	private DatiBeneAziendaGest 	datiBeneAziendaGest;
	private DatiBeneFinanzGest 		datiBeneFinanzGest;
	private DatiBeneImmaterialeGest datiBeneImmaterialeGest;
	private DatiBeneImmobileGest 	datiBeneImmobileGest;
	private DatiBeneMobileGest 		datiBeneMobileGest;
	private DatiBeneVeicoloGest 	datiBeneVeicoloGest;

	public DatiBeneAziendaGest getDatiBeneAziendaGest() {
		return datiBeneAziendaGest;
	}

	public DatiBeneFinanzGest getDatiBeneFinanzGest() {
		return datiBeneFinanzGest;
	}

	public DatiBeneImmaterialeGest getDatiBeneImmaterialeGest() {
		return datiBeneImmaterialeGest;
	}

	public DatiBeneImmobileGest getDatiBeneImmobileGest() {
		return datiBeneImmobileGest;
	}

	public DatiBeneMobileGest getDatiBeneMobileGest() {
		return datiBeneMobileGest;
	}

	public DatiBeneVeicoloGest getDatiBeneVeicoloGest() {
		return datiBeneVeicoloGest;
	}

	public void setDatiBeneAziendaGest(DatiBeneAziendaGest datiBeneAziendaGest) {
		this.datiBeneAziendaGest = datiBeneAziendaGest;
	}

	public void setDatiBeneFinanzGest(DatiBeneFinanzGest datiBeneFinanzGest) {
		this.datiBeneFinanzGest = datiBeneFinanzGest;
	}

	public void setDatiBeneImmaterialeGest(
			DatiBeneImmaterialeGest datiBeneImmaterialeGest) {
		this.datiBeneImmaterialeGest = datiBeneImmaterialeGest;
	}

	public void setDatiBeneImmobileGest(DatiBeneImmobileGest datiBeneImmobileGest) {
		this.datiBeneImmobileGest = datiBeneImmobileGest;
	}

	public void setDatiBeneMobileGest(DatiBeneMobileGest datiBeneMobileGest) {
		this.datiBeneMobileGest = datiBeneMobileGest;
	}

	public void setDatiBeneVeicoloGest(DatiBeneVeicoloGest datiBeneVeicoloGest) {
		this.datiBeneVeicoloGest = datiBeneVeicoloGest;
	}

	public Long getNumeGraffati() {
		return numeGraffati;
	}

	public void setNumeGraffati(Long numeGraffati) {
		this.numeGraffati = numeGraffati;
	}

	public boolean isEnteFittizio() {
		return isEnteFittizio;
	}

	public void setEnteFittizio(boolean isEnteFittizio) {
		this.isEnteFittizio = isEnteFittizio;
	}
	
	
}
