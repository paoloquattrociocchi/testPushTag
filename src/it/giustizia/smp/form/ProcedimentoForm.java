package it.giustizia.smp.form;

import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

/**
 * ProcedimentoForm
 * 
 * @author Almaviva S.p.A.
 *         classe form per la gestione del procedimento
 */
public class ProcedimentoForm {

    protected Long         idProcedimento;
    @NumberFormat(style = Style.NUMBER, pattern = "####")
    private Short          anno;
    @NumberFormat(style = Style.NUMBER, pattern = "####")
    private Short          annoCriterio;
    @NumberFormat(style = Style.NUMBER, pattern = "####")
    private Short          annoDecretoDecisorio;
    @NumberFormat(style = Style.NUMBER, pattern = "####")
    private Short          annoPadre;
    private boolean        antimafia;
    private boolean        antiterrorismo;
    private String         codiceFiscaleSelezionato;
    private String         cognomeSoggetto;
    private String         comAccertamento;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date           dataApertura;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date           dataApplProvvProc;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date           dataAttivazioneStato;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date           dataEmissioneAccertamento;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date           dataEmissioneProposta;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date           dataIscrizione;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date           dataIscrizioneMax;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date           dataIscrizioneMin;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date           dataTrasmCda;    
    
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date           dataRicezioneAccertamento;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date           dataRicezioneProposta;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date           dataTrasmissioneTribunale;
    private String         descrRuolo;
    private int 		   idRuolo;
    private String         descTipoApplProc;
    private Long           idAtto;
    private Long           idPadre;
    private Long           idSelezionato;
    @Valid
    private MagistratoForm magistratoForm;
    @Valid
    private AmministratoreForm amministratoreForm;
    private boolean        nessuna;
    private String         nomeSoggetto;
    private String         numeDecretoAppl;
    @NumberFormat(style = Style.NUMBER, pattern = "####")
    private Long           numero;
    private Long           numeroDecretoDecisorio;
    @NumberFormat(style = Style.NUMBER, pattern = "####")
    private Long           numeroMax;
    @NumberFormat(style = Style.NUMBER, pattern = "####")
    private Long           numeroMin;
    @NumberFormat(style = Style.NUMBER, pattern = "####")
    private Long           numeroPadre;
    private String         numeroRicerca;
    private boolean        ordinaria;
    private ComboForm      pmAssegnatario;
    private ComboForm      procedente;
    private String         proponente;
    private String         proposta;
    private String		   ragioneSociale;
    private boolean        scegliIpotesi;
    private ComboForm      statoProcedimento;
    private ComboForm      tipologiaPrevenzione;
    private ComboForm      tipologiaPrevenzioneDb;
    private boolean        tipologiaPrevenzioneNew;
    private ComboForm      tipoProcedente;
    private ComboForm      tipoProcedimento;
    private String         tipoRicerca;
    private ComboForm 	   tipoFascicolo;
    private String 		   ufficioPadre;
    private int         tipoFascicoloRicerca;
    private ComboForm 	   listaUffici;
	private String NUM_PROCURA;
	private String NUM_TRIBUNALE;
	private String NUM_PG;
	private String NUM_CDA;	    

    private ComboForm tipoUffRic;
    private ComboForm idUffRic;
    
    private int tipoUfficioNascita;
    
    private String descTribCda;
    
    private int ufficioPU;
    
    /*30/04/2021 inserimento filto ricerca per difensore*/
    private String cognomeDifensore;
    private String nomeDifensore;
    private String codiceFiscaleDifensore;
    /**/
	
	
    
	public int getUfficioPU() {
		return ufficioPU;
	}

	public void setUfficioPU(int ufficioPU) {
		this.ufficioPU = ufficioPU;
	}

	public String getDescTribCda() {
		return descTribCda;
	}

	public void setDescTribCda(String descTribCda) {
		this.descTribCda = descTribCda;
	}

	public int getTipoUfficioNascita() {
		return tipoUfficioNascita;
	}

	public void setTipoUfficioNascita(int tipoUfficioNascita) {
		this.tipoUfficioNascita = tipoUfficioNascita;
	}

	public ComboForm getTipoUffRic() {
		return tipoUffRic;
	}

	public void setTipoUffRic(ComboForm tipoUffRic) {
		this.tipoUffRic = tipoUffRic;
	}

	public ComboForm getIdUffRic() {
		return idUffRic;
	}

	public void setIdUffRic(ComboForm idUffRic) {
		this.idUffRic = idUffRic;
	}

	public String getNUM_PROCURA() {
		return NUM_PROCURA;
	}

	public void setNUM_PROCURA(String nUM_PROCURA) {
		NUM_PROCURA = nUM_PROCURA;
	}

	public String getNUM_TRIBUNALE() {
		return NUM_TRIBUNALE;
	}

	public void setNUM_TRIBUNALE(String nUM_TRIBUNALE) {
		NUM_TRIBUNALE = nUM_TRIBUNALE;
	}

	public String getNUM_PG() {
		return NUM_PG;
	}

	public void setNUM_PG(String nUM_PG) {
		NUM_PG = nUM_PG;
	}

	public String getNUM_CDA() {
		return NUM_CDA;
	}

	public void setNUM_CDA(String nUM_CDA) {
		NUM_CDA = nUM_CDA;
	}

	public int getTipoFascicoloRicerca() {
		return tipoFascicoloRicerca;
	}

	public void setTipoFascicoloRicerca(int tipoFascicoloRicerca) {
		this.tipoFascicoloRicerca = tipoFascicoloRicerca;
	}

	public String getUfficioPadre() {
		return ufficioPadre;
	}

	public void setUfficioPadre(String ufficioPadre) {
		this.ufficioPadre = ufficioPadre;
	}

	
	public ComboForm getListaUffici() {
		return listaUffici;
	}

	public void setListaUffici(ComboForm listaUffici) {
		this.listaUffici = listaUffici;
	}


	//Giacomo 09/09/2013
    private String callbackUrl;

    /**
     * @return the anno
     */
    public Short getAnno() {

        return anno;
    }

    /**
     * @return the annoDecretoDecisorio
     */
    public Short getAnnoDecretoDecisorio() {

        return annoDecretoDecisorio;
    }

    public String getAnnoNumeroPadre() {

        return annoPadre + "/" + numeroPadre;
    }

    /**
     * @return the annoNumeroProcedimento
     */
    public String getAnnoNumeroProcedimento() {

        return anno + "/" + numero;
    }

    public Short getAnnoPadre() {

        return annoPadre;
    }

    /**
     * @return the codiceFiscaleSelezionato
     */
    public String getCodiceFiscaleSelezionato() {

        return codiceFiscaleSelezionato;
    }

    /**
     * @return the cognomeSoggetto
     */
    public String getCognomeSoggetto() {

        return cognomeSoggetto;
    }

    /**
     * @return the comAccertamento
     */
    public String getComAccertamento() {

        return comAccertamento;
    }

    /**
     * @return the dataApertura
     */
    public Date getDataApertura() {

        return dataApertura;
    }

    /**
     * @return the dataApplProvvProc
     */
    public Date getDataApplProvvProc() {

        return dataApplProvvProc;
    }

    /**
     * @return the dataAttivazioneStato
     */
    public Date getDataAttivazioneStato() {

        return dataAttivazioneStato;
    }

    /**
     * @return the dataEmissioneAccertamento
     */
    public Date getDataEmissioneAccertamento() {

        return dataEmissioneAccertamento;
    }

    /**
     * @return the dataEmissioneProposta
     */
    public Date getDataEmissioneProposta() {

        return dataEmissioneProposta;
    }

    /**
     * @return the dataIscrizione
     */
    public Date getDataIscrizione() {

        return dataIscrizione;
    }

    /**
     * @return the dataIscrizioneMax
     */
    public Date getDataIscrizioneMax() {

        return dataIscrizioneMax;
    }

    /**
     * @return the dataIscrizioneMin
     */
    public Date getDataIscrizioneMin() {

        return dataIscrizioneMin;
    }

    /**
     * @return the dataRicezioneAccertamento
     */
    public Date getDataRicezioneAccertamento() {

        return dataRicezioneAccertamento;
    }

    /**
     * @return the dataRicezioneProposta
     */
    public Date getDataRicezioneProposta() {

        return dataRicezioneProposta;
    }

    /**
     * @return the dataTrasmissioneTribunale
     */
    public Date getDataTrasmissioneTribunale() {

        return dataTrasmissioneTribunale;
    }

    /* GETTERS & SETTERS */
    /**
     * @return the descrRuolo
     */
    public String getDescrRuolo() {

        return descrRuolo;
    }

    /**
     * @return the descTipoApplProc
     */
    public String getDescTipoApplProc() {

        return descTipoApplProc;
    }

    public Long getIdAtto() {

        return idAtto;
    }

    public Long getIdPadre() {

        return idPadre;
    }

    /**
     * @return the idProcedimento
     */
    public Long getIdProcedimento() {

        return idProcedimento;
    }

    public Long getIdSelezionato() {

        return idSelezionato;
    }

    /**
     * @return the magistratoForm
     */
    public MagistratoForm getMagistratoForm() {

        return magistratoForm;
    }
    
    /**
     * @return the magistratoForm
     */
    public AmministratoreForm getAmministratoreForm() {

        return amministratoreForm;
    }
    
    /**
     * @return the nomeSoggetto
     */
    public String getNomeSoggetto() {

        return nomeSoggetto;
    }

    /**
     * @return the numeDecretoAppl
     */
    public String getNumeDecretoAppl() {

        return numeDecretoAppl;
    }

    /**
     * @return the numero
     */
    public Long getNumero() {

        return numero;
    }

    /**
     * @return the numeroDecretoDecisorio
     */
    public Long getNumeroDecretoDecisorio() {

        return numeroDecretoDecisorio;
    }

    /**
     * @return the numeroMax
     */
    public Long getNumeroMax() {

        return numeroMax;
    }

    /**
     * @return the numeroMin
     */
    public Long getNumeroMin() {

        return numeroMin;
    }

    public Long getNumeroPadre() {

        return numeroPadre;
    }

    /**
     * @return the numeroRicerca
     */
    public String getNumeroRicerca() {

        return numeroRicerca;
    }

    /**
     * @return the pmAssegnatario
     */
    public ComboForm getPmAssegnatario() {

        return pmAssegnatario;
    }

    /**
     * @return the procedente
     */
    public ComboForm getProcedente() {

        return procedente;
    }

    /**
     * @return the proponente
     */
    public String getProponente() {

        return proponente;
    }

    /**
     * @return the proposta
     */
    public String getProposta() {

        return proposta;
    }

    /**
	 * @return the ragioneSociale
	 */
	public String getRagioneSociale() {
		return ragioneSociale;
	}

	/**
     * @return the statoProcedimento
     */
    public ComboForm getStatoProcedimento() {

        return statoProcedimento;
    }

    /**
     * @return the getTipologiaPrevenzione
     */
    public ComboForm getTipologiaPrevenzione() {

        return tipologiaPrevenzione;
    }

    /**
     * @return the getTipologiaPrevenzioneDb
     */
    public ComboForm getTipologiaPrevenzioneDb() {

        return tipologiaPrevenzioneDb;
    }
    
    /**
     * @return the tipologiaPrevenzioneNew
     */
    public boolean isTipologiaPrevenzioneNew() {
    
        return tipologiaPrevenzioneNew;
    }

    /**
     * @return the tipoProcedente
     */
    public ComboForm getTipoProcedente() {

        return tipoProcedente;
    }

    /**
     * @return the tipoProcedimento
     */
    public ComboForm getTipoProcedimento() {

        return tipoProcedimento;
    }

    /**
     * @return the tipoRicerca
     */
    public String getTipoRicerca() {

        return tipoRicerca;
    }

    /**
     * @return the antimafia
     */
    public boolean isAntimafia() {

        return antimafia;
    }

    /**
     * @return the antiterrorismo
     */
    public boolean isAntiterrorismo() {

        return antiterrorismo;
    }

    /**
     * @return the nessuna
     */
    public boolean isNessuna() {

        return nessuna;
    }

    /**
     * @return the ordinaria
     */
    public boolean isOrdinaria() {

        return ordinaria;
    }

    /**
     * @return the scegliIpotesi
     */
    public boolean isScegliIpotesi() {

        return scegliIpotesi;
    }

    /**
     * @param anno
     *            the anno to set
     */
    public void setAnno(Short anno) {

        this.anno = anno;
    }

    /**
     * @param annoDecretoDecisorio
     *            the annoDecretoDecisorio to set
     */
    public void setAnnoDecretoDecisorio(Short annoDecretoDecisorio) {

        this.annoDecretoDecisorio = annoDecretoDecisorio;
    }

    public Short getAnnoCriterio() {
		return annoCriterio;
	}

	public void setAnnoCriterio(Short annoCriterio) {
		this.annoCriterio = annoCriterio;
	}

	public void setAnnoPadre(Short annoPadre) {

        this.annoPadre = annoPadre;
    }

    /**
     * @param antimafia
     *            the antimafia to set
     */
    public void setAntimafia(boolean antimafia) {

        this.antimafia = antimafia;
    }

    /**
     * @param antiterrorismo
     *            the antiterrorismo to set
     */
    public void setAntiterrorismo(boolean antiterrorismo) {

        this.antiterrorismo = antiterrorismo;
    }

    /**
     * @param codiceFiscaleSelezionato
     *            the codiceFiscaleSelezionato to set
     */
    public void setCodiceFiscaleSelezionato(String codiceFiscaleSelezionato) {

        this.codiceFiscaleSelezionato = codiceFiscaleSelezionato;
    }

    /**
     * @param cognomeSoggetto
     *            the cognomeSoggetto to set
     */
    public void setCognomeSoggetto(String cognomeSoggetto) {

        this.cognomeSoggetto = cognomeSoggetto;
    }

    /**
     * @param comAccertamento
     *            the comAccertamento to set
     */
    public void setComAccertamento(String comAccertamento) {

        this.comAccertamento = comAccertamento;
    }

    /**
     * @param dataApertura
     *            the dataApertura to set
     */
    public void setDataApertura(Date dataApertura) {

        this.dataApertura = dataApertura;
    }

    /**
     * @param dataApplProvvProc
     *            the dataApplProvvProc to set
     */
    public void setDataApplProvvProc(Date dataApplProvvProc) {

        this.dataApplProvvProc = dataApplProvvProc;
    }

    /**
     * @param dataAttivazioneStato
     *            the dataAttivazioneStato to set
     */
    public void setDataAttivazioneStato(Date dataAttivazioneStato) {

        this.dataAttivazioneStato = dataAttivazioneStato;
    }

    /**
     * @param dataEmissioneAccertamento
     *            the dataEmissioneAccertamento to set
     */
    public void setDataEmissioneAccertamento(Date dataEmissioneAccertamento) {

        this.dataEmissioneAccertamento = dataEmissioneAccertamento;
    }

    /**
     * @param dataEmissioneProposta
     *            the dataEmissioneProposta to set
     */
    public void setDataEmissioneProposta(Date dataEmissioneProposta) {

        this.dataEmissioneProposta = dataEmissioneProposta;
    }

    /**
     * @param dataIscrizione
     *            the dataIscrizione to set
     */
    public void setDataIscrizione(Date dataIscrizione) {

        this.dataIscrizione = dataIscrizione;
    }

    /**
     * @param dataIscrizioneMax
     *            the dataIscrizioneMax to set
     */
    public void setDataIscrizioneMax(Date dataIscrizioneMax) {

        this.dataIscrizioneMax = dataIscrizioneMax;
    }

    /**
     * @param dataIscrizioneMin
     *            the dataIscrizioneMin to set
     */
    public void setDataIscrizioneMin(Date dataIscrizioneMin) {

        this.dataIscrizioneMin = dataIscrizioneMin;
    }

    /**
     * @param dataRicezioneAccertamento
     *            the dataRicezioneAccertamento to set
     */
    public void setDataRicezioneAccertamento(Date dataRicezioneAccertamento) {

        this.dataRicezioneAccertamento = dataRicezioneAccertamento;
    }

    /**
     * @param dataRicezioneProposta
     *            the dataRicezioneProposta to set
     */
    public void setDataRicezioneProposta(Date dataRicezioneProposta) {

        this.dataRicezioneProposta = dataRicezioneProposta;
    }

    /**
     * @param dataTrasmissioneTribunale
     *            the dataTrasmissioneTribunale to set
     */
    public void setDataTrasmissioneTribunale(Date dataTrasmissioneTribunale) {

        this.dataTrasmissioneTribunale = dataTrasmissioneTribunale;
    }

    /**
     * @param descrRuolo
     *            the descrRuolo to set
     */
    public void setDescrRuolo(String descrRuolo) {

        this.descrRuolo = descrRuolo;
    }

    /**
     * @param descTipoApplProc
     *            the descTipoApplProc to set
     */
    public void setDescTipoApplProc(String descTipoApplProc) {

        this.descTipoApplProc = descTipoApplProc;
    }

    public void setIdAtto(Long idAtto) {

        this.idAtto = idAtto;
    }

    public void setIdPadre(Long idPadre) {

        this.idPadre = idPadre;
    }

    /**
     * @param idProcedimento
     *            the idProcedimento to set
     */
    public void setIdProcedimento(Long idProcedimento) {

        this.idProcedimento = idProcedimento;
    }

    public void setIdSelezionato(Long idSelezionato) {

        this.idSelezionato = idSelezionato;
    }

    /**
     * @param magistratoForm
     *            the magistratoForm to set
     */
    public void setMagistratoForm(MagistratoForm magistratoForm) {

        this.magistratoForm = magistratoForm;
    }

    /**
     * @param nessuna
     *            the nessuna to set
     */
    public void setNessuna(boolean nessuna) {

        this.nessuna = nessuna;
    }

    /**
     * @param nomeSoggetto
     *            the nomeSoggetto to set
     */
    public void setNomeSoggetto(String nomeSoggetto) {

        this.nomeSoggetto = nomeSoggetto;
    }

    /**
     * @param numeDecretoAppl
     *            the numeDecretoAppl to set
     */
    public void setNumeDecretoAppl(String numeDecretoAppl) {

        this.numeDecretoAppl = numeDecretoAppl;
    }

    /**
     * @param numero
     *            the numero to set
     */
    public void setNumero(Long numero) {

        this.numero = numero;
    }

    /**
     * @param numeroDecretoDecisorio
     *            the numeroDecretoDecisorio to set
     */
    public void setNumeroDecretoDecisorio(Long numeroDecretoDecisorio) {

        this.numeroDecretoDecisorio = numeroDecretoDecisorio;
    }

    /**
     * @param numeroMax
     *            the numeroMax to set
     */
    public void setNumeroMax(Long numeroMax) {

        this.numeroMax = numeroMax;
    }

    /**
     * @param numeroMin
     *            the numeroMin to set
     */
    public void setNumeroMin(Long numeroMin) {

        this.numeroMin = numeroMin;
    }

    public void setNumeroPadre(Long numeroPadre) {

        this.numeroPadre = numeroPadre;
    }

    /**
     * @param numeroRicerca
     *            the numeroRicerca to set
     */
    public void setNumeroRicerca(String numeroRicerca) {

        this.numeroRicerca = numeroRicerca;
    }

    /**
     * @param ordinaria
     *            the ordinaria to set
     */
    public void setOrdinaria(boolean ordinaria) {

        this.ordinaria = ordinaria;
    }

    /**
     * @param pmAssegnatario
     *            the pmAssegnatario to set
     */
    public void setPmAssegnatario(ComboForm pmAssegnatario) {

        this.pmAssegnatario = pmAssegnatario;
    }

    /**
     * @param procedente
     *            the procedente to set
     */
    public void setProcedente(ComboForm procedente) {

        this.procedente = procedente;
    }

    /**
     * @param proponente
     *            the proponente to set
     */
    public void setProponente(String proponente) {

        this.proponente = proponente;
    }

    /**
     * @param proposta
     *            the proposta to set
     */
    public void setProposta(String proposta) {

        this.proposta = proposta;
    }

    /**
	 * @param ragioneSociale the ragioneSociale to set
	 */
	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	/**
     * @param scegliIpotesi
     *            the scegliIpotesi to set
     */
    public void setScegliIpotesi(boolean scegliIpotesi) {

        this.scegliIpotesi = scegliIpotesi;
    }

    /**
     * @param statoProcedimento
     *            the statoProcedimento to set
     */
    public void setStatoProcedimento(ComboForm statoProcedimento) {

        this.statoProcedimento = statoProcedimento;
    }

    /**
     * @param tipologiaPrevenzione
     *            the tipologiaPrevenzione to set
     */
    public void setTipologiaPrevenzione(ComboForm tipologiaPrevenzione) {

        this.tipologiaPrevenzione = tipologiaPrevenzione;
    }

    /**
     * @param tipologiaPrevenzioneDb
     *            the tipologiaPrevenzioneDb to set
     */
    public void setTipologiaPrevenzioneDb(ComboForm tipologiaPrevenzioneDb) {

        this.tipologiaPrevenzioneDb = tipologiaPrevenzioneDb;
    }
    
    /**
     * @param tipologiaPrevenzioneNew the tipologiaPrevenzioneNew to set
     */
    public void setTipologiaPrevenzioneNew(boolean tipologiaPrevenzioneNew) {
    
        this.tipologiaPrevenzioneNew = tipologiaPrevenzioneNew;
    }

    /**
     * @param tipoProcedente
     *            the tipoProcedente to set
     */
    public void setTipoProcedente(ComboForm tipoProcedente) {

        this.tipoProcedente = tipoProcedente;
    }

    /**
     * @param tipoProcedimento
     *            the tipoProcedimento to set
     */
    public void setTipoProcedimento(ComboForm tipoProcedimento) {

        this.tipoProcedimento = tipoProcedimento;
    }

    /**
     * @param tipoRicerca
     *            the tipoRicerca to set
     */
    public void setTipoRicerca(String tipoRicerca) {

        this.tipoRicerca = tipoRicerca;
    }

	public int getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(int idRuolo) {
		this.idRuolo = idRuolo;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public ComboForm getTipoFascicolo() {
		return tipoFascicolo;
	}

	public void setTipoFascicolo(ComboForm tipoFascicolo) {
		this.tipoFascicolo = tipoFascicolo;
	}

    public void setAmministratoreForm(AmministratoreForm amministratoreForm) {
		this.amministratoreForm = amministratoreForm;
	}

	public Integer getCalcoloTipologiaPrevenzione() {

        if(antimafia) return 2;
        if(antiterrorismo) return 3;
        if(ordinaria) return 1;
        else return null;
        
    }

	public Date getDataTrasmCda() {
		return dataTrasmCda;
	}

	public void setDataTrasmCda(Date dataTrasmCda) {
		this.dataTrasmCda = dataTrasmCda;
	}

	public String getCognomeDifensore() {
		return cognomeDifensore;
	}

	public void setCognomeDifensore(String cognomeDifensore) {
		this.cognomeDifensore = cognomeDifensore;
	}

	public String getNomeDifensore() {
		return nomeDifensore;
	}

	public void setNomeDifensore(String nomeDifensore) {
		this.nomeDifensore = nomeDifensore;
	}

	public String getCodiceFiscaleDifensore() {
		return codiceFiscaleDifensore;
	}

	public void setCodiceFiscaleDifensore(String codiceFiscaleDifensore) {
		this.codiceFiscaleDifensore = codiceFiscaleDifensore;
	}
	
}
