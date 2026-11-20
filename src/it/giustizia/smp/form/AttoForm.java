package it.giustizia.smp.form;

import it.giustizia.smp.dto.pg_atto.ImpugnazioneCassellarioDTO;
import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

/**
 * AttoForm
 * 
 * @author Almaviva S.p.A.
 *         classe form per la gestione degli atti del procedimento
 */

public class AttoForm extends ProcedimentoForm {

    protected ComboForm            categoriaAtto;
    // di relativo all'atto corrente
    protected long                 codAtto;
//    protected String               contentType;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    protected Date                 dataDeposito;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    protected Date                 dataEmissione;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    protected Date                 dataRegistrazione;
    // campo di supporto per il mapping con dozer
//    protected Blob                 documento;
//    protected CommonsMultipartFile fileData;
    // parte relativa all'upload
//    protected String               fileName;
    protected ComboForm            statoAtto;
    protected ComboForm            tipoAtto;
    protected ComboForm          oraInizioUdienza;
    // attributo per la gestione di checkbox relativo ai soggetti non associati
    List<Long>                     listaIdCheckbox;
    
    boolean						   restituzionePerEquivalente;	
    
    // attributo per la gestione di checkbox relativo ai soggetti già associati
    // alla fonte
    List<Long>                     listaIdCheckbox_associati;
    private String                 altroOrgano;
    private Long                   annoAtto;
    private boolean                attoPerAggravamento;
    
    private Boolean                flgNascondiRicevute;
	private Boolean flgAllegaDatiToolAdmin;
    
    private ComboForm              comuneOrgano;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date                   dataDefinitivita;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date                   dataInformativa;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date                   dataRicezione;
    private boolean                definitivitaToAll = true;
    private ComboForm              destinatario;
    private ComboForm              destinatario2;
    // riferimento all'udienza
//    private long                   fkUdienza;
    
    private long                   fkUdienzaDecreto;
    private ComboForm              fonte;
    // variabile di appoggio per la selezione del radiobutton in fonte.jsp
    private long                   identificativoSoggetto;
    private ComboForm              indirizzoOrgano;
    private ComboForm              modalitaTrasmissione;
    private String                 descAltroMod;
    private String                 note;
    private String                 numeroAtto;
    private String                 numeroRiferimento;
    private ComboForm              organo;
    private ComboForm              provinciaOrgano;
    private String                 tipoSoggetto;
    private long                   versioneSoggetto;
    private ComboForm				esitoOpposizione;
    private int						idUfficio;
    private String					descTipoUfficio;


	private String					descSedeUfficio;
    private String                  descMisura;
    private String                  descVisibilita;
    
    private ComboForm                 magistrato;   
    
    
    private String 					descInfoScheda;
 

	//creati per riportare i dati estratti da DettaglioAttoDto
    private String				    numeAnnoAtto;
    private String					numeAtto;
    
    //numeroAttoOrigine utilizzato in copertina nel caso di Decreto di Revoca o Convalida
    private String 					numeroAttoOrigine;
    
    //stralcio 
    private String 					numeroProcedimentoOrigine;
    
    //atto di origine utilizzato per filtri successivi
    private long					codAttoOrigine;
    
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date					dataRicezioneAttoOrigine;
    private String					descrizioneCategoriaAttoOrigine;
    
    //utilizzato dal copia sposta atto generico
    protected Long         			idProcedimentoDestinatario;
    
    //AttoGenerico
    private ComboForm 				flagSpedRic;
    private String 				    sedeOrgano;
    private ComboForm 				fonteAttoGenerico;   
    
//    private ComboForm				modalitaPervenimento;
   
    private AllegatoForm allegato = new AllegatoForm();
    
	//FILTRI 
    protected ComboForm            	decisorioIGrado;
    protected ComboForm            	decisorioIIGrado;
    protected ComboForm            	soggettiAntimafia;
    protected ComboForm            	soggettiDiretti;
    protected ComboForm            	soggettiTitolari;
    protected ComboForm            	tipoMisure;
    
    protected ComboForm            	seqPerditaEfficacia;
    //filtri del bene
    protected ComboForm            	soggettiDirettiFiltro;
    protected ComboForm            	soggettiTitolariFiltro;

    protected ComboForm            	tipologiaBene;
    protected ComboForm            	categoriaBene;
    protected ComboForm            	sottoCategoriaBene;    
    
// filtro soggetti diretti misure personali
    protected ComboForm            	soggettiDirettiPers;    
//---    
    private List<SoggettoStralcioForm> 	listaSoggettoStralcioForm;
    private List<SoggettoForm>     		soggetti;  
    
    private String                  criteriFiltro;
    private boolean                 visualizzaFiltro;

    private boolean                 visualizzaFiltroPers;
    
    private boolean					flagMisPers;
    private boolean					flagMisPatr;
    
	private long					idSoggettoDiretto;
	private long					idSoggettoDirettoPers;
	
	private long					idSoggettoTitolare;
	private long					idTipologia;
    private long					idCategoria;
    private long					idSottocategoria;
    
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    protected Date                 dataCessazioneMisura;     
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    protected Date                 dataRipresaMisura;      
    
    //utilizzato per i tipi atto 14 (generici,...)
    private String valido;
    
    //utilizzato per il FUG
    @NumberFormat(style = Style.NUMBER, pattern = "###.###,##")
	private String		importo;
    private ImpugnazioneForm impugnazione= new ImpugnazioneForm();
    
    protected long idProcEmiss;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date      dataInvio;

    protected Long      idMisura; 
    
    
    private SoggettoForm soggetto;
    
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    protected Date    dataRedazioneScheda;
    
    protected String descNormaProc;
    private ComunicazioneForm comunicazioneForm;
    private List<ImpugnazioneCassellarioDTO> impugnazioniPrimoGrado;
    private ImpugnazioneCassellarioDTO sentenzaCassazione;
    
    private String descVersionamento;
    private String	warning;
    
    //attributo utilizzato per la modifica del bene nell'atto di correzione materiale
    //se è false deve generare un nuovo idBene
    //se è true deve modificare quello passato come un normale bene
    private boolean daModificaAttoCorrezione ;
    
    private String pulsanteAnnullaConRinvio;
    
    private ComboForm tipoProponente;
    private ComboForm amministratore;
    private String cognProponente;
    private String nomeProponente;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date periodoRifDal;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date periodoRifAl;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataUdienza;
    
    private String esitoProvv;
    private String descEsitoProvv;
    private String flgImp;
	private String descEsitoImp;
	private String flgCas;
	private String descEsitoCas;
	private String descNote;
	
	private String pulsanteInviaRicorsoInCassazione;
	
	private List<Long> idAmministratoriRevocati;
	
	private Long idProcedimentoCda;
	private Long idProcedimentoCdatrib;
	


	private String descTribCda;
	
	private BeneForm beneForm;
    
	//per i flussi MPS
	private String flgParere;
	
	public String getDescTribCda() {
		return descTribCda;
	}

	public void setDescTribCda(String descTribCda) {
		this.descTribCda = descTribCda;
	}

	public Long getIdProcedimentoCda() {
		return idProcedimentoCda;
	}

	public void setIdProcedimentoCda(Long idProcedimentoCda) {
		this.idProcedimentoCda = idProcedimentoCda;
	}

	public Long getIdProcedimentoCdatrib() {
		return idProcedimentoCdatrib;
	}

	public void setIdProcedimentoCdatrib(Long idProcedimentoCdatrib) {
		this.idProcedimentoCdatrib = idProcedimentoCdatrib;
	}	
	
	public boolean isDaModificaAttoCorrezione() {
		return daModificaAttoCorrezione;
	}

	public void setDaModificaAttoCorrezione(boolean daModificaAttoCorrezione) {
		this.daModificaAttoCorrezione = daModificaAttoCorrezione;
	}

	public ImpugnazioneCassellarioDTO getSentenzaCassazione() {
		return sentenzaCassazione;
	}

	public void setSentenzaCassazione(ImpugnazioneCassellarioDTO sentenzaCassazione) {
		this.sentenzaCassazione = sentenzaCassazione;
	}

	public Date getDataUdienza() {
		return dataUdienza;
	}

	public void setDataUdienza(Date dataUdienza) {
		this.dataUdienza = dataUdienza;
	}

	public String getEsitoProvv() {
		return esitoProvv;
	}

	public void setEsitoProvv(String esitoProvv) {
		this.esitoProvv = esitoProvv;
	}

	public String getDescEsitoProvv() {
		return descEsitoProvv;
	}

	public void setDescEsitoProvv(String descEsitoProvv) {
		this.descEsitoProvv = descEsitoProvv;
	}

	public String getFlgImp() {
		return flgImp;
	}

	public void setFlgImp(String flgImp) {
		this.flgImp = flgImp;
	}

	public String getDescEsitoImp() {
		return descEsitoImp;
	}

	public void setDescEsitoImp(String descEsitoImp) {
		this.descEsitoImp = descEsitoImp;
	}

	public String getFlgCas() {
		return flgCas;
	}

	public void setFlgCas(String flgCas) {
		this.flgCas = flgCas;
	}

	public String getDescEsitoCas() {
		return descEsitoCas;
	}

	public void setDescEsitoCas(String descEsitoCas) {
		this.descEsitoCas = descEsitoCas;
	}

	public String getDescNote() {
		return descNote;
	}

	public void setDescNote(String descNote) {
		this.descNote = descNote;
	}

	public List<ImpugnazioneCassellarioDTO> getImpugnazioniPrimoGrado() {
		return impugnazioniPrimoGrado;
	}

	public void setImpugnazioniPrimoGrado(List<ImpugnazioneCassellarioDTO> impugnazioniPrimoGrado) {
		this.impugnazioniPrimoGrado = impugnazioniPrimoGrado;
	}
    
    
    
    public String getDescInfoScheda() {
 		return descInfoScheda;
 	}

 	public void setDescInfoScheda(String descInfoScheda) {
 		this.descInfoScheda = descInfoScheda;
 	}
    public ComunicazioneForm getComunicazioneForm() {
		return comunicazioneForm;
	}

	public void setComunicazioneForm(ComunicazioneForm comunicazioneForm) {
		this.comunicazioneForm = comunicazioneForm;
	}

	public String getDescNormaProc() {
		return descNormaProc;
	}

	public void setDescNormaProc(String descNormaProc) {
		this.descNormaProc = descNormaProc;
	}

	public Date getDataRedazioneScheda() {
		return dataRedazioneScheda;
	}

	public void setDataRedazioneScheda(Date dataRedazioneScheda) {
		this.dataRedazioneScheda = dataRedazioneScheda;
	}
	

	public long getIdProcEmiss() {
		return idProcEmiss;
	}

	public void setIdProcEmiss(long idProcEmiss) {
		this.idProcEmiss = idProcEmiss;
	}

	/**
     * @return the altroOrgano
     */
    public String getAltroOrgano() {

        return altroOrgano;
    }

    /**
     * @return the annoAtto
     */
    public Long getAnnoAtto() {

        return annoAtto;
    }

    public ComboForm getCategoriaAtto() {

        return categoriaAtto;
    }

    /**
     * @return the codAtto
     */
    public long getCodAtto() {

        return codAtto;
    }

    /**
     * @return the codAttoOrigine
     */
    public long getCodAttoOrigine() {

        return codAttoOrigine;
    }

    /**
     * @return the comuneOrgano
     */
    public ComboForm getComuneOrgano() {

        return comuneOrgano;
    }

//    /**
//     * @return the contentType
//     */
//    public String getContentType() {
//
//        return contentType;
//    }

    /**
     * @return the dataDefinitivita
     */
    public Date getDataDefinitivita() {

        return dataDefinitivita;
    }

    /**
     * @return the dataDeposito
     */
    public Date getDataDeposito() {

        return dataDeposito;
    }

    /**
     * @return the dataEmissione
     */
    public Date getDataEmissione() {

        return dataEmissione;
    }

    /**
     * @return the dataInformativa
     */
    public Date getDataInformativa() {

        return dataInformativa;
    }

    /**
     * @return the dataRegistrazione
     */
    public Date getDataRegistrazione() {

        return dataRegistrazione;
    }

    /**
     * @return the dataRicezione
     */
    public Date getDataRicezione() {

        return dataRicezione;
    }

    /**
     * @return the definitivitaToAll
     */
    public boolean getDefinitivitaToAll() {

        return definitivitaToAll;
    }

    /**
     * @return the destinatario
     */
    public ComboForm getDestinatario() {

        return destinatario;
    }

    public ComboForm getDestinatario2() {

        return destinatario2;
    }

//    /**
//     * @return the documento
//     */
//    public Blob getDocumento() {
//
//        return documento;
//    }

//    /**
//     * @return the fileData
//     */
//    public CommonsMultipartFile getFileData() {
//
//        return fileData;
//    }

//    /**
//     * @return the fileName
//     */
//    public String getFileName() {
//
//        return fileName;
//    }

//    /**
//     * @return the fkUdienza
//     */
//    public long getFkUdienza() {
//
//        return fkUdienza;
//    }

    /**
     * @return the fonte
     */
    public ComboForm getFonte() {

        return fonte;
    }

    /**
     * @return the identificativoSoggetto
     */
    public long getIdentificativoSoggetto() {

        return identificativoSoggetto;
    }

    /**
     * @return the indirizzoOrgano
     */
    public ComboForm getIndirizzoOrgano() {

        return indirizzoOrgano;
    }

    /**
     * @return the listaIdCheckbox
     */
    public List<Long> getListaIdCheckbox() {

        return listaIdCheckbox;
    }

    public List<Long> getListaIdCheckbox_associati() {

        return listaIdCheckbox_associati;
    }

   
    /**
     * @return the note
     */
    public String getNote() {

        return Utility.limitaStringa(note, Constants.TATTO_CAMPO_NOTE_LEN);
    }

    /**
     * @return the numeroAtto
     */
    public String getNumeroAtto() {
    	if(numeAnnoAtto!=null && numeAnnoAtto.trim().length()>0 && numeAtto!=null && numeAtto.trim().length()>0){
    		numeroAtto =  numeAnnoAtto.concat(Constants.FormattazioneAnnoNumeroAtto.DIVISORE_ANNO_NUMERO_ATTO).concat(numeAtto);
    	}else{
    		numeroAtto = Constants.FormattazioneAnnoNumeroAtto.NUMERO_ATTO_NON_DISP;
    	}
        return numeroAtto;
    }

    /**
     * @return the numeroRiferimento
     */
    public String getNumeroRiferimento() {

        return numeroRiferimento;
    }

    /**
     * @return the organo
     */
    public ComboForm getOrgano() {

        return organo;
    }

    /**
     * @return the provinciaOrgano
     */
    public ComboForm getProvinciaOrgano() {

        return provinciaOrgano;
    }

    /**
     * @return the statoAtto
     */
    public ComboForm getStatoAtto() {

        return statoAtto;
    }

    /**
     * @return the tipoAtto
     */
    public ComboForm getTipoAtto() {

        return tipoAtto;
    }

    public String getTipoSoggetto() {

        return tipoSoggetto;
    }

    /**
     * @return the versioneSoggetto
     */
    public long getVersioneSoggetto() {

        return versioneSoggetto;
    }

    public boolean isAttoPerAggravamento() {

        return attoPerAggravamento;
    }

    /**
     * @param altroOrgano
     *            the altroOrgano to set
     */
    public void setAltroOrgano(String altroOrgano) {

        this.altroOrgano = altroOrgano;
    }

    /**
     * @param annoAtto
     *            the annoAtto to set
     */
    public void setAnnoAtto(Long annoAtto) {

        this.annoAtto = annoAtto;
    }

    public void setAttoPerAggravamento(boolean attoPerAggravamento) {

        this.attoPerAggravamento = attoPerAggravamento;
    }

    public void setCategoriaAtto(ComboForm categoriaAtto) {

        this.categoriaAtto = categoriaAtto;
    }

    /**
     * @param codAtto
     *            the codAtto to set
     */
    public void setCodAtto(long codAtto) {

        this.codAtto = codAtto;
    }

    /**
     * @param codAttoOrigine
     *            the codAttoOrigine to set
     */
    public void setCodAttoOrigine(long codAttoOrigine) {

        this.codAttoOrigine = codAttoOrigine;
    }

    /**
     * @param comuneOrgano
     *            the comuneOrgano to set
     */
    public void setComuneOrgano(ComboForm comuneOrgano) {

        this.comuneOrgano = comuneOrgano;
    }

//    /**
//     * @param contentType
//     *            the contentType to set
//     */
//    public void setContentType(String contentType) {
//
//        this.contentType = contentType;
//    }

    /**
     * @param dataDefinitivita
     *            the dataDefinitivita to set
     */
    public void setDataDefinitivita(Date dataDefinitivita) {

        this.dataDefinitivita = dataDefinitivita;
    }

    /**
     * @param dataDeposito
     *            the dataDeposito to set
     */
    public void setDataDeposito(Date dataDeposito) {

        this.dataDeposito = dataDeposito;
    }

    /**
     * @param dataEmissione
     *            the dataEmissione to set
     */
    public void setDataEmissione(Date dataEmissione) {

        this.dataEmissione = dataEmissione;
    }

    /**
     * @param dataInformativa
     *            the dataInformativa to set
     */
    public void setDataInformativa(Date dataInformativa) {

        this.dataInformativa = dataInformativa;
    }

    /**
     * @param dataRegistrazione
     *            the dataRegistrazione to set
     */
    public void setDataRegistrazione(Date dataRegistrazione) {

        this.dataRegistrazione = dataRegistrazione;
    }

    /**
     * @param dataRicezione
     *            the dataRicezione to set
     */
    public void setDataRicezione(Date dataRicezione) {

        this.dataRicezione = dataRicezione;
    }

    /**
     * @param definitivitaToAll
     *            the definitivitaToAll to set
     */
    public void setDefinitivitaToAll(boolean definitivitaToAll) {

        this.definitivitaToAll = definitivitaToAll;
    }

    /**
     * @param destinatario
     *            the destinatario to set
     */
    public void setDestinatario(ComboForm destinatario) {

        this.destinatario = destinatario;
    }

    public void setDestinatario2(ComboForm destinatario2) {

        this.destinatario2 = destinatario2;
    }

//    /**
//     * @param documento
//     *            the documento to set
//     */
//    public void setDocumento(Blob documento) {
//
//        this.documento = documento;
//    }
//
//    /**
//     * @param fileData
//     *            the fileData to set
//     */
//    public void setFileData(CommonsMultipartFile fileData) {
//
//        this.fileData = fileData;
//    }

//    /**
//     * @param fileName
//     *            the fileName to set
//     */
//    public void setFileName(String fileName) {
//
//        this.fileName = fileName;
//    }

//    /**
//     * @param fkUdienza
//     *            the fkUdienza to set
//     */
//    public void setFkUdienza(long fkUdienza) {
//
//        this.fkUdienza = fkUdienza;
//    }

    /**
     * @param fonte
     *            the fonte to set
     */
    public void setFonte(ComboForm fonte) {

        this.fonte = fonte;
    }

    /**
     * @param identificativoSoggetto
     *            the identificativoSoggetto to set
     */
    public void setIdentificativoSoggetto(long identificativoSoggetto) {

        this.identificativoSoggetto = identificativoSoggetto;
    }

    /**
     * @param indirizzoOrgano
     *            the indirizzoOrgano to set
     */
    public void setIndirizzoOrgano(ComboForm indirizzoOrgano) {

        this.indirizzoOrgano = indirizzoOrgano;
    }

    /**
     * @param listaIdCheckbox
     *            the listaIdCheckbox to set
     */
    public void setListaIdCheckbox(List<Long> listaIdCheckbox) {

        this.listaIdCheckbox = listaIdCheckbox;
    }

    public void setListaIdCheckbox_associati(List<Long> listaIdCheckbox_associati) {

        this.listaIdCheckbox_associati = listaIdCheckbox_associati;
    }

    

    public ComboForm getModalitaTrasmissione() {
		return modalitaTrasmissione;
	}

	public void setModalitaTrasmissione(ComboForm modalitaTrasmissione) {
		this.modalitaTrasmissione = modalitaTrasmissione;
	}

	/**
     * @param note
     *            the note to set
     */
    public void setNote(String note) {

        this.note = Utility.limitaStringa(note, Constants.TATTO_CAMPO_NOTE_LEN);
    }

    /**
     * @param numeroAtto
     *            the numeroAtto to set
     */
    public void setNumeroAtto(String numeroAtto) {

        this.numeroAtto = numeroAtto;
    }

    /**
     * @param numeroRiferimento
     *            the numeroRiferimento to set
     */
    public void setNumeroRiferimento(String numeroRiferimento) {

        this.numeroRiferimento = numeroRiferimento;
    }

    /**
     * @param organo
     *            the organo to set
     */
    public void setOrgano(ComboForm organo) {

        this.organo = organo;
    }

    /**
     * @param provinciaOrgano
     *            the provinciaOrgano to set
     */
    public void setProvinciaOrgano(ComboForm provinciaOrgano) {

        this.provinciaOrgano = provinciaOrgano;
    }

    /**
     * @param statoAtto
     *            the statoAtto to set
     */
    public void setStatoAtto(ComboForm statoAtto) {

        this.statoAtto = statoAtto;
    }

    /**
     * @param tipoAtto
     *            the tipoAtto to set
     */
    public void setTipoAtto(ComboForm tipoAtto) {

        this.tipoAtto = tipoAtto;
    }

    public void setTipoSoggetto(String tipoSoggetto) {

        this.tipoSoggetto = tipoSoggetto;
    }

    /**
     * @param versioneSoggetto
     *            the versioneSoggetto to set
     */
    public void setVersioneSoggetto(long versioneSoggetto) {

        this.versioneSoggetto = versioneSoggetto;
    }

	/**
	 * @return the esitoOpposizione
	 */
	public ComboForm getEsitoOpposizione() {
		return esitoOpposizione;
	}

	/**
	 * @param esitoOpposizione the esitoOpposizione to set
	 */
	public void setEsitoOpposizione(ComboForm esitoOpposizione) {
		this.esitoOpposizione = esitoOpposizione;
	}

	/**
	 * @return the idUfficio
	 */
	public int getIdUfficio() {
		return idUfficio;
	}

	/**
	 * @param idUfficio the idUfficio to set
	 */
	public void setIdUfficio(int idUfficio) {
		this.idUfficio = idUfficio;
	}

	/**
	 * @return the descTipoUfficio
	 */
	public String getDescTipoUfficio() {
		return descTipoUfficio;
	}

	/**
	 * @param descTipoUfficio the descTipoUfficio to set
	 */
	public void setDescTipoUfficio(String descTipoUfficio) {
		this.descTipoUfficio = descTipoUfficio;
	}

	/**
	 * @return the descSedeUfficio
	 */
	public String getDescSedeUfficio() {
		return descSedeUfficio;
	}

	/**
	 * @param descSedeUfficio the descSedeUfficio to set
	 */
	public void setDescSedeUfficio(String descSedeUfficio) {
		this.descSedeUfficio = descSedeUfficio;
	}
    
    /**
     * @return the descMisura
     */
    public String getDescMisura() {
    
        return descMisura;
    }

    
    /**
     * @param descMisura the descMisura to set
     */
    public void setDescMisura(String descMisura) {
    
        this.descMisura = descMisura;
    }

	public long getFkUdienzaDecreto() {
		return fkUdienzaDecreto;
	}

	public void setFkUdienzaDecreto(long fkUdienzaDecreto) {
		this.fkUdienzaDecreto = fkUdienzaDecreto;
	}

	public AllegatoForm getAllegato() {
		return allegato;
	}

	public void setAllegato(AllegatoForm allegato) {
		this.allegato = allegato;
	}

	public String getNumeAnnoAtto() {
		return numeAnnoAtto;
	}

	public void setNumeAnnoAtto(String numeAnnoAtto) {
		this.numeAnnoAtto = numeAnnoAtto;
	}

	public String getNumeAtto() {
		return numeAtto;
	}

	public void setNumeAtto(String numeAtto) {
		this.numeAtto = numeAtto;
	}

	public String getNumeroAttoOrigine() {
		return numeroAttoOrigine;
	}

	public void setNumeroAttoOrigine(String numeroAttoOrigine) {
		this.numeroAttoOrigine = numeroAttoOrigine;
	}

	public String getNumeroProcedimentoOrigine() {
		return numeroProcedimentoOrigine;
	}

	public void setNumeroProcedimentoOrigine(String numeroProcedimentoOrigine) {
		this.numeroProcedimentoOrigine = numeroProcedimentoOrigine;
	}

	
	

	public String getSedeOrgano() {
		return sedeOrgano;
	}

	public void setSedeOrgano(String sedeOrgano) {
		this.sedeOrgano = sedeOrgano;
	}

	public ComboForm getFonteAttoGenerico() {
		return fonteAttoGenerico;
	}

	public void setFonteAttoGenerico(ComboForm fonteAttoGenerico) {
		this.fonteAttoGenerico = fonteAttoGenerico;
	}

	public ComboForm getFlagSpedRic() {
		return flagSpedRic;
	}

	public void setFlagSpedRic(ComboForm flagSpedRic) {
		this.flagSpedRic = flagSpedRic;
	}

	public ComboForm getSoggettiDiretti() {
		return soggettiDiretti;
	}

	public void setSoggettiDiretti(ComboForm soggettiDiretti) {
		this.soggettiDiretti = soggettiDiretti;
	}

	public ComboForm getSoggettiTitolari() {
		return soggettiTitolari;
	}

	public void setSoggettiTitolari(ComboForm soggettiTitolari) {
		this.soggettiTitolari = soggettiTitolari;
	}

	public ComboForm getSoggettiDirettiFiltro() {
		return soggettiDirettiFiltro;
	}

	public void setSoggettiDirettiFiltro(ComboForm soggettiDirettiFiltro) {
		this.soggettiDirettiFiltro = soggettiDirettiFiltro;
	}

	public ComboForm getSoggettiDirettiPers() {
		return soggettiDirettiPers;
	}

	public void setSoggettiDirettiPers(ComboForm soggettiDirettiPers) {
		this.soggettiDirettiPers = soggettiDirettiPers;
	}

	public ComboForm getSoggettiTitolariFiltro() {
		return soggettiTitolariFiltro;
	}

	public void setSoggettiTitolariFiltro(ComboForm soggettiTitolariFiltro) {
		this.soggettiTitolariFiltro = soggettiTitolariFiltro;
	}

	public ComboForm getTipoMisure() {
		return tipoMisure;
	}

	public void setTipoMisure(ComboForm tipoMisure) {
		this.tipoMisure = tipoMisure;
	}

	public ComboForm getTipologiaBene() {
		return tipologiaBene;
	}

	public void setTipologiaBene(ComboForm tipologiaBene) {
		this.tipologiaBene = tipologiaBene;
	}

	public ComboForm getCategoriaBene() {
		return categoriaBene;
	}

	public void setCategoriaBene(ComboForm categoriaBene) {
		this.categoriaBene = categoriaBene;
	}

	public ComboForm getSottoCategoriaBene() {
		return sottoCategoriaBene;
	}

	public void setSottoCategoriaBene(ComboForm sottoCategoriaBene) {
		this.sottoCategoriaBene = sottoCategoriaBene;
	}

	public ComboForm getDecisorioIGrado() {
		return decisorioIGrado;
	}

	public void setDecisorioIGrado(ComboForm decisorioIGrado) {
		this.decisorioIGrado = decisorioIGrado;
	}

	public ComboForm getDecisorioIIGrado() {
		return decisorioIIGrado;
	}

	public void setDecisorioIIGrado(ComboForm decisorioIIGrado) {
		this.decisorioIIGrado = decisorioIIGrado;
	}

	public String getValido() {
		return valido;
	}

	public void setValido(String valido) {
		this.valido = valido;
	}

	public Long getIdProcedimentoDestinatario() {
		return idProcedimentoDestinatario;
	}

	public void setIdProcedimentoDestinatario(Long idProcedimentoDestinatario) {
		this.idProcedimentoDestinatario = idProcedimentoDestinatario;
	}

	public List<SoggettoStralcioForm> getListaSoggettoStralcioForm() {
		return listaSoggettoStralcioForm;
	}

	public void setListaSoggettoStralcioForm(
			List<SoggettoStralcioForm> listaSoggettoStralcioForm) {
		this.listaSoggettoStralcioForm = listaSoggettoStralcioForm;
	}

	public ImpugnazioneForm getImpugnazione() {
		return impugnazione;
	}

	public void setImpugnazione(ImpugnazioneForm impugnazione) {
		this.impugnazione = impugnazione;
	}

	public String getDescAltroMod() {
		return descAltroMod;
	}

	public SoggettoForm getSoggetto() {
		return soggetto;
	}

	public void setSoggetto(SoggettoForm soggetto) {
		this.soggetto = soggetto;
	}

	public void setDescAltroMod(String descAltroMod) {
		this.descAltroMod = descAltroMod;
	}
	
	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	public List<SoggettoForm> getSoggetti() {
		return soggetti;
	}

	public void setSoggetti(List<SoggettoForm> soggetti) {
		this.soggetti = soggetti;
	}

	public Long getIdMisura() {
		return idMisura;
	}

	public void setIdMisura(Long idMisura) {
		this.idMisura = idMisura;
	}



	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getDescVersionamento() {
		return descVersionamento;
	}

	public void setDescVersionamento(String descVersionamento) {
		this.descVersionamento = descVersionamento;
	}

	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}

	public String getPulsanteAnnullaConRinvio() {
		return pulsanteAnnullaConRinvio;
	}

	public void setPulsanteAnnullaConRinvio(String pulsanteAnnullaConRinvio) {
		this.pulsanteAnnullaConRinvio = pulsanteAnnullaConRinvio;
	}

	public ComboForm getTipoProponente() {
		return tipoProponente;
	}

	public void setTipoProponente(ComboForm tipoProponente) {
		this.tipoProponente = tipoProponente;
	}

	public ComboForm getAmministratore() {
		return amministratore;
	}

	public void setAmministratore(ComboForm amministratore) {
		this.amministratore = amministratore;
	}

	public String getCognProponente() {
		return cognProponente;
	}

	public void setCognProponente(String cognProponente) {
		this.cognProponente = cognProponente;
	}

	public String getNomeProponente() {
		return nomeProponente;
	}

	public void setNomeProponente(String nomeProponente) {
		this.nomeProponente = nomeProponente;
	}

	public Boolean getFlgNascondiRicevute() {
		return flgNascondiRicevute;
	}

	public void setFlgNascondiRicevute(Boolean flgNascondiRicevute) {
		this.flgNascondiRicevute = flgNascondiRicevute;
	}
	
	public Date getPeriodoRifDal() {
		return periodoRifDal;
	}

	public void setPeriodoRifDal(Date periodoRifDal) {
		this.periodoRifDal = periodoRifDal;
	}

	public Date getPeriodoRifAl() {
		return periodoRifAl;
	}

	public void setPeriodoRifAl(Date periodoRifAl) {
		this.periodoRifAl = periodoRifAl;
	}

	public ComboForm getSoggettiAntimafia() {
		return soggettiAntimafia;
	}

	public void setSoggettiAntimafia(ComboForm soggettiAntimafia) {
		this.soggettiAntimafia = soggettiAntimafia;
	}

	public Date getDataCessazioneMisura() {
		return dataCessazioneMisura;
	}

	public void setDataCessazioneMisura(Date dataCessazioneMisura) {
		this.dataCessazioneMisura = dataCessazioneMisura;
	}

	public Date getDataRipresaMisura() {
		return dataRipresaMisura;
	}

	public void setDataRipresaMisura(Date dataRipresaMisura) {
		this.dataRipresaMisura = dataRipresaMisura;
	}

	public List<Long> getIdAmministratoriRevocati() {
		return idAmministratoriRevocati;
	}

	public void setIdAmministratoriRevocati(List<Long> idAmministratoriRevocati) {
		this.idAmministratoriRevocati = idAmministratoriRevocati;
	}

	public ComboForm getMagistrato() {
		return magistrato;
	}

	public void setMagistrato(ComboForm magistrato) {
		this.magistrato = magistrato;
	}

	public ComboForm getSeqPerditaEfficacia() {
		return seqPerditaEfficacia;
	}

	public void setSeqPerditaEfficacia(ComboForm seqPerditaEfficacia) {
		this.seqPerditaEfficacia = seqPerditaEfficacia;
	}

	
//	public ComboForm getModalitaPervenimento() {
//		return modalitaPervenimento;
//	}
//
//	public void setModalitaPervenimento(ComboForm modalitaPervenimento) {
//		this.modalitaPervenimento = modalitaPervenimento;
//	}

    /**
     * @return the visualizzaFiltro
     */
    public boolean isVisualizzaFiltro() {

        return visualizzaFiltro;
    }

    public long getIdSoggettoDiretto() {
		idSoggettoDiretto = 0;
		if(soggettiDiretti!=null && soggettiDiretti.getId()!=null){
			idSoggettoDiretto = Long.parseLong(soggettiDiretti.getId());
		}
		return idSoggettoDiretto;
	}
	
    public void setIdSoggettoDiretto(long idSoggettoDiretto) {
		this.idSoggettoDiretto = idSoggettoDiretto;
	}
    
    public long getIdSoggettoDirettoPers() {    	
		idSoggettoDirettoPers = 0;
		if(soggettiDirettiPers!=null && soggettiDirettiPers.getId()!=null){
			idSoggettoDirettoPers = Long.parseLong(soggettiDirettiPers.getId());
		}    	    	    	
		return idSoggettoDirettoPers;
	}

	public void setIdSoggettoDirettoPers(long idSoggettoDirettoPers) {
		this.idSoggettoDirettoPers = idSoggettoDirettoPers;
	}

	public long getIdSoggettoTitolare() {
		idSoggettoTitolare = 0;
		if(soggettiTitolari!=null && soggettiTitolari.getId()!=null){
			idSoggettoTitolare = Long.parseLong(soggettiTitolari.getId());
		}
		return idSoggettoTitolare;
	}
	
    public void setIdSoggettoTitolare(long idSoggettoTitolare) {
		this.idSoggettoTitolare = idSoggettoTitolare;
	}
    
	public long getIdTipologia() {
		idTipologia = 0;
		if(tipologiaBene!=null && tipologiaBene.getId()!=null){
			idTipologia = Integer.parseInt(tipologiaBene.getId());
		}
		return idTipologia;
	}
	
    public void setIdTipologia(long idTipologia) {
		this.idTipologia = idTipologia;
	}
	
	public long getIdCategoria() {
		idCategoria = 0;
		if(categoriaBene!=null && categoriaBene.getId()!=null){
			idCategoria = Integer.parseInt(categoriaBene.getId());
		}
		return idCategoria;
	}
	
	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public long getIdSottocategoria() {
		idSottocategoria = 0;
		if(sottoCategoriaBene != null && sottoCategoriaBene.getId()!=null){
			idSottocategoria = Integer.parseInt(sottoCategoriaBene.getId());
		}
		return idSottocategoria;
	}
	
	public void setIdSottocategoria(long idSottocategoria) {
		this.idSottocategoria = idSottocategoria;
	}

    /**
     * @return the criteriFiltro
     */
 
    public String getCriteriFiltro() {
         if ((soggettiDiretti!= null && soggettiDiretti.getId() != null && !soggettiDiretti.getId().equals("-1")) || 
        		 (soggettiTitolari!= null && soggettiTitolari.getId() != null && !soggettiTitolari.getId().equals("-1")) ||
        	(tipologiaBene != null && tipologiaBene.getId() != null && !tipologiaBene.getId().equals("-1")) || 
        	(categoriaBene != null && categoriaBene.getId() != null && !categoriaBene.getId().equals("-1")) || 
        	(sottoCategoriaBene != null && sottoCategoriaBene.getId() != null && !sottoCategoriaBene.getId().equals("-1"))) {
            StringBuilder descrizione = new StringBuilder();
            descrizione.append(" con");
            if (soggettiDiretti != null && soggettiDiretti.getId() != null && !soggettiDiretti.getId().equals("-1")) {
                descrizione.append(" Soggetti Diretti: ");
                descrizione.append(soggettiDiretti.getDescrizione());
                if ((soggettiTitolari != null && soggettiTitolari.getId() != null && !soggettiTitolari.getId().equals("-1")) || (tipologiaBene != null && tipologiaBene.getId() != null && !tipologiaBene.getId().equals("-1")) || (categoriaBene != null && categoriaBene.getId() != null && !categoriaBene.getId().equals("-1")) || (sottoCategoriaBene != null && sottoCategoriaBene.getId() != null && !sottoCategoriaBene.getId().equals("-1"))) {
                    descrizione.append(";");
                }
            }
            if (soggettiTitolari != null && soggettiTitolari.getId() != null && !soggettiTitolari.getId().equals("-1")) {
                descrizione.append(" Soggetti Titolari: ");
                descrizione.append(soggettiTitolari.getDescrizione());
                if ((tipologiaBene != null && tipologiaBene.getId() != null && !tipologiaBene.getId().equals("-1")) || (categoriaBene != null && categoriaBene.getId() != null && !categoriaBene.getId().equals("-1")) || (sottoCategoriaBene != null && sottoCategoriaBene.getId() != null && !sottoCategoriaBene.getId().equals("-1"))) {
                    descrizione.append(";");
                }
            }
            if (tipologiaBene != null && tipologiaBene.getId() != null && !tipologiaBene.getId().equals("-1")) {
                descrizione.append(" Tipologia: ");
                descrizione.append(tipologiaBene.getDescrizione());
                if ((categoriaBene != null && categoriaBene.getId() != null && !categoriaBene.getId().equals("-1")) || (sottoCategoriaBene != null && sottoCategoriaBene.getId() != null && !sottoCategoriaBene.getId().equals("-1"))) {
                    descrizione.append(";");
                }
            }
            if (categoriaBene != null && categoriaBene.getId() != null && !categoriaBene.getId().equals("-1")) {
                descrizione.append(" Categoria: ");
                descrizione.append(categoriaBene.getDescrizione());
                if ((sottoCategoriaBene != null && sottoCategoriaBene.getId() != null && !sottoCategoriaBene.getId().equals("-1"))) {
                    descrizione.append(";");
                }
            }
            if (sottoCategoriaBene != null && sottoCategoriaBene.getId() != null && !sottoCategoriaBene.getId().equals("-1")) {
                descrizione.append(" Sottocategoria: ");
                descrizione.append(sottoCategoriaBene.getDescrizione());
            }
            this.criteriFiltro = descrizione.toString();
        } else criteriFiltro = null;
        return criteriFiltro;
    }

    /**
     * @param criteriFiltro
     *            the criteriFiltro to set
     */
    public void setCriteriFiltro(String criteriFiltro) {

        this.criteriFiltro = criteriFiltro;
    }

    /**
     * @param visualizzaFiltro
     *            the visualizzaFiltro to set
     */
    public void setVisualizzaFiltro(boolean visualizzaFiltro) {

        this.visualizzaFiltro = visualizzaFiltro;
    }

	public boolean isVisualizzaFiltroPers() {
		return visualizzaFiltroPers;
	}

	public void setVisualizzaFiltroPers(boolean visualizzaFiltroPers) {
		this.visualizzaFiltroPers = visualizzaFiltroPers;
	}

	public boolean isFlagMisPers() {
		return flagMisPers;
	}

	public void setFlagMisPers(boolean flagMisPers) {
		this.flagMisPers = flagMisPers;
	}

	public boolean isFlagMisPatr() {
		return flagMisPatr;
	}

	public void setFlagMisPatr(boolean flagMisPatr) {
		this.flagMisPatr = flagMisPatr;
	}


	public Date getDataRicezioneAttoOrigine() {
		return dataRicezioneAttoOrigine;
	}

	public void setDataRicezioneAttoOrigine(Date dataRicezioneAttoOrigine) {
		this.dataRicezioneAttoOrigine = dataRicezioneAttoOrigine;
	}

	public String getDescVisibilita() {
		return descVisibilita;
	}

	public void setDescVisibilita(String descVisibilita) {
		this.descVisibilita = descVisibilita;
	}

	public String getDescrizioneCategoriaAttoOrigine() {
		return descrizioneCategoriaAttoOrigine;
	}

	public void setDescrizioneCategoriaAttoOrigine(
			String descrizioneCategoriaAttoOrigine) {
		this.descrizioneCategoriaAttoOrigine = descrizioneCategoriaAttoOrigine;
	}

	public Boolean getFlgAllegaDatiToolAdmin() {
		return flgAllegaDatiToolAdmin;
	}

	public void setFlgAllegaDatiToolAdmin(Boolean flgAllegaDatiToolAdmin) {
		this.flgAllegaDatiToolAdmin = flgAllegaDatiToolAdmin;
	}

	public BeneForm getBeneForm() {
		return beneForm;
	}

	public void setBeneForm(BeneForm beneForm) {
		this.beneForm = beneForm;
	}

	public String getFlgParere() {
		return flgParere;
	}

	public void setFlgParere(String flgParere) {
		this.flgParere = flgParere;
	}

	public String getPulsanteInviaRicorsoInCassazione() {
		return pulsanteInviaRicorsoInCassazione;
	}

	public void setPulsanteInviaRicorsoInCassazione(
			String pulsanteInviaRicorsoInCassazione) {
		this.pulsanteInviaRicorsoInCassazione = pulsanteInviaRicorsoInCassazione;
	}
	
    public boolean isRestituzionePerEquivalente() {
		return restituzionePerEquivalente;
	}

	public void setRestituzionePerEquivalente(boolean restituzionePerEquivalente) {
		this.restituzionePerEquivalente = restituzionePerEquivalente;
	}

	public ComboForm getOraInizioUdienza() {
		return oraInizioUdienza;
	}

	public void setOraInizioUdienza(ComboForm oraInizioUdienza) {
		this.oraInizioUdienza = oraInizioUdienza;
	}

}
