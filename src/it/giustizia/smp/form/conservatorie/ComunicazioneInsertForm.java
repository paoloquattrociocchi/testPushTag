package it.giustizia.smp.form.conservatorie;

import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

public class ComunicazioneInsertForm
{
	//# Le variabili sono mappate nella classe TNota

	private String    descUtente;

    private Character flgDecisorio     = ' ';
    private Character flgStato         = '0';
	private Character flgNotaCollegata = '0';
	private Long      idProcedimento;
	private Long      numeroComunicazione;    //id		
	private ComboForm tipoNota;
    private ComboForm categorieAttiA;
    private ComboForm categorieAttiT;
    private String    categoriaAtto;          // id Categoria Atto selezionata
    private String    descrCategoriaAtto;     // descrizione Categoria Atto selezionata
	private String    tipoAtto;
	private String    catAtto;
	private Integer   repertorioAnno;         //numeRepert2	
	private Integer   numero;                 //numeRepert1
	private Short     progressivo;            //numeProgrNota	 	
	private ComboForm conservatoria;          //corrisponde a descConserv nella classe TAnConservatorie
	private String    descrConservatoria;     // descrizione Conservatoria selezionata
	private String    codiceFiscale;          //descFiscalePu
	private String    titoloDescrizione;      //descTitolo
	private String    pubblicoUfficiale;      //descPubblUff
	private Character categoria;              //descTipoPubblUff
	private ComboForm comune;                 // sede: comune + (provincia);
	private String    sede;              	  // sede: solo comune
	private String    provincia;              //descProvPubblUff
	private ComboForm attoSpecie;             //descSpecieAtto
	private ComboForm attoSpecieT;            //descSpecieAtto - TRASCIZIONE
	private ComboForm attoSpecieA;            //descSpecieAtto - ANNOTAZIONE
	private ComboForm rettifica;              //campo digitabile solo per le comunicazioni di tipo trascrizione
	private ComboForm tipoComunic;            //tipo comunicazione visualizzata come 'Descrizione'
	private String tipoComunicT;           	//tipo comunicazione visualizzata come 'Descrizione' - TRASCIZIONE
	private ComboForm tipoComunicA;           //tipo comunicazione visualizzata come 'Descrizione' - ANNOTAZIONE
	private String    descAtto;
	private String    descSpecieAtto;
	private Short     unitaNegoziali    = 0;  //numeUnitNeg
	private Integer   immobili          = 0;  //numeImmobili
	private Short     soggettiFavore    = 0;  //numeSoggFavore
	private Short     soggettiContro    = 0;  //numeSoggContro
	@ModernDate @DateTimeFormat( pattern = "dd/MM/yyyy" )
	private Date      dataTitolo;             //dataTitolo			 	

	private ComboForm tipoComunicAnn;
	private ComboForm tipoComunicTrasc;

	//Dati relativi alla trascrizione 

	private boolean   attoSoggVoltura;
	private boolean   presenzaCondizione;
	private boolean   differita;
	private boolean   presenzaTermini;
	private Integer   numeFormalita1;
	private Short     numeFormalita2;
	private boolean   quadroA;
	private boolean   quadroB;
	private boolean   quadroC;
	private Integer   numeroTrascrizione1;
	private Short     numeroTrascrizione2;

    private String    valoreImmobLib;
	
	
	private boolean   volturaCatastale;
	private String    formalita;
	@DateTimeFormat( pattern = "dd/MM/yyyy" )
	private Date      dataDal;
	@DateTimeFormat( pattern = "dd/MM/yyyy" )
	private Date      dataFormalita;
	@DateTimeFormat( pattern = "dd/MM/yyyy" )
	private Date      trascrizioneDel;
	
	
	private Long    idNota;
    private String  statoComunicazione;
    private String descConservatoria;
    @ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date	dataComunicazione;
    private String notaSelezionata;
    
    private String[] notaSel = null;
	
    List <ComunicazioneListaForm> listaPredisp;
    
    private Boolean listaRichiesta;
    
    private String tenoreMisura;
	
	public Date getTrascrizioneDel()
	{
		return trascrizioneDel;
	}

	public void setTrascrizioneDel( Date trascrizioneDel )
	{
		this.trascrizioneDel = trascrizioneDel;
	}

	public Integer getNumeroTrascrizione1() {
		return numeroTrascrizione1;
	}

	public void setNumeroTrascrizione1(Integer numeroTrascrizione1) {
		this.numeroTrascrizione1 = numeroTrascrizione1;
	}

	public Short getNumeroTrascrizione2()
	{
		return numeroTrascrizione2;
	}

	public void setNumeroTrascrizione2( Short numeroTrascrizione2 )
	{
		this.numeroTrascrizione2 = numeroTrascrizione2;
	}

	public String getValoreImmobLib() {
		return valoreImmobLib;
	}

	public void setValoreImmobLib(String valoreImmobLib) {
		this.valoreImmobLib = valoreImmobLib;
	}

	public boolean isVolturaCatastale()
	{
		return volturaCatastale;
	}

	public void setVolturaCatastale( boolean volturaCatastale )
	{
		this.volturaCatastale = volturaCatastale;
	}

	public Long getNumeroComunicazione()
	{
		return numeroComunicazione;
	}

	public void setNumeroComunicazione( Long numeroComunicazione )
	{
		this.numeroComunicazione = numeroComunicazione;
	}

	public ComboForm getTipoNota()
	{
		return tipoNota;
	}

	public void setTipoNota( ComboForm tipoNota )
	{
		this.tipoNota = tipoNota;
	}

	public Integer getRepertorioAnno()
	{
		return repertorioAnno;
	}

	public void setRepertorioAnno( Integer repertorioAnno )
	{
		this.repertorioAnno = repertorioAnno;
	}

	public Integer getNumero()
	{
		return numero;
	}

	public void setNumero( Integer numero )
	{
		this.numero = numero;
	}

	public Short getProgressivo()
	{
		return progressivo;
	}

	public void setProgressivo( Short progressivo )
	{
		this.progressivo = progressivo;
	}

	public ComboForm getConservatoria()
	{
		return conservatoria;
	}

	public void setConservatoria( ComboForm conservatoria )
	{
		this.conservatoria = conservatoria;
	}

	public String getCodiceFiscale()
	{
		return codiceFiscale;
	}

	public void setCodiceFiscale( String codiceFiscale )
	{
		this.codiceFiscale = codiceFiscale;
	}

	public String getTitoloDescrizione()
	{
		return titoloDescrizione;
	}

	public void setTitoloDescrizione( String titoloDescrizione )
	{
		this.titoloDescrizione = titoloDescrizione;
	}

	public Date getDataTitolo()
	{
		return dataTitolo;
	}

	public void setDataTitolo( Date data )
	{
		this.dataTitolo = data;
	}

	public String getPubblicoUfficiale()
	{
		return pubblicoUfficiale;
	}

	public void setPubblicoUfficiale( String pubblicoUfficiale )
	{
		this.pubblicoUfficiale = pubblicoUfficiale;
	}

	public Character getCategoria()
	{
		return categoria;
	}

	public void setCategoria( Character categoria )
	{
		this.categoria = categoria;
	}

	public String getProvincia()
	{
		return provincia;
	}

	public void setProvincia( String provincia )
	{
		this.provincia = provincia;
	}

	public ComboForm getAttoSpecie()
	{
		return attoSpecie;
	}

	public void setAttoSpecie( ComboForm attoSpecie )
	{
		this.attoSpecie = attoSpecie;
	}

	public ComboForm getRettifica()
	{
		return rettifica;
	}

	public void setRettifica( ComboForm rettifica )
	{
		this.rettifica = rettifica;
	}

	public Short getUnitaNegoziali()
	{
		return unitaNegoziali;
	}

	public void setUnitaNegoziali( Short unitaNegoziali )
	{
		this.unitaNegoziali = unitaNegoziali;
	}

	public Integer getImmobili()
	{
		return immobili;
	}

	public void setImmobili( Integer immobili )
	{
		this.immobili = immobili;
	}

	public Short getSoggettiFavore()
	{
		return soggettiFavore;
	}

	public void setSoggettiFavore( Short soggettiFavore )
	{
		this.soggettiFavore = soggettiFavore;
	}

	public Short getSoggettiContro()
	{
		return soggettiContro;
	}

	public void setSoggettiContro( Short soggettiContro )
	{
		this.soggettiContro = soggettiContro;
	}

	public boolean isAttoSoggVoltura()
	{
		return attoSoggVoltura;
	}

	public void setAttoSoggVoltura( boolean attoSoggVoltura )
	{
		this.attoSoggVoltura = attoSoggVoltura;
	}

	public boolean isPresenzaCondizione()
	{
		return presenzaCondizione;
	}

	public void setPresenzaCondizione( boolean presenzaCondizione )
	{
		this.presenzaCondizione = presenzaCondizione;
	}

	public boolean isDifferita()
	{
		return differita;
	}

	public void setDifferita( boolean differita )
	{
		this.differita = differita;
	}

	public Date getDataDal()
	{
		return dataDal;
	}

	public void setDataDal( Date dataDal )
	{
		this.dataDal = dataDal;
	}

	public boolean isPresenzaTermini()
	{
		return presenzaTermini;
	}

	public void setPresenzaTermini( boolean presenzaTermini )
	{
		this.presenzaTermini = presenzaTermini;
	}

	public Date getDataFormalita()
	{
		return dataFormalita;
	}

	public void setDataFormalita( Date dataFormalita )
	{
		this.dataFormalita = dataFormalita;
	}

	public boolean isQuadroA()
	{
		return quadroA;
	}

	public void setQuadroA( boolean quadroA )
	{
		this.quadroA = quadroA;
	}

	public boolean isQuadroB()
	{
		return quadroB;
	}

	public void setQuadroB( boolean quadroB )
	{
		this.quadroB = quadroB;
	}

	public boolean isQuadroC()
	{
		return quadroC;
	}

	public void setQuadroC( boolean quadroC )
	{
		this.quadroC = quadroC;
	}

	public String getFormalita()
	{
		return formalita;
	}

	public void setFormalita( String formalita )
	{
		this.formalita = formalita;
	}

	public ComboForm getComune()
	{
		return comune;
	}

	public void setComune( ComboForm comune )
	{
		this.comune = comune;
	}

	public ComboForm getTipoComunic()
	{
		return tipoComunic;
	}

	public void setTipoComunic( ComboForm tipoComunic )
	{
		this.tipoComunic = tipoComunic;
	}

	public Long getIdProcedimento()
	{
		return idProcedimento;
	}

	public void setIdProcedimento( Long idProcedimento )
	{
		this.idProcedimento = idProcedimento;
	}

	public Character getflgStato()
	{
		return flgStato;
	}

	public void setflgStato( Character flgStato )
	{
		this.flgStato = flgStato;
	}

	public Character getflgNotaCollegata()
	{
		return flgNotaCollegata;
	}

	public void setflgNotaCollegata( Character flgNotaCollegata )
	{
		this.flgNotaCollegata = flgNotaCollegata;
	}

	public String getDescrConservatoria()
	{
		return descrConservatoria;
	}

	public void setDescrConservatoria( String descrConservatoria )
	{
		this.descrConservatoria = descrConservatoria;
	}

	public String getDescAtto()
    {
	    return descAtto;
    }

	public void setDescAtto( String descAtto )
    {
	    this.descAtto = descAtto;
    }

	public String getDescSpecieAtto() {
		return descSpecieAtto;
	}

	public void setDescSpecieAtto(String descSpecieAtto) {
		this.descSpecieAtto = descSpecieAtto;
	}

	public String getTipoAtto()
    {
	    return tipoAtto;
    }

	public void setTipoAtto( String tipoAtto )
    {
	    this.tipoAtto = tipoAtto;
    }

	public String getCatAtto()
    {
	    return catAtto;
    }

	public void setCatAtto( String catAtto )
    {
	    this.catAtto = catAtto;
    }

	public String getDescUtente()
    {
	    return descUtente;
    }

	public void setDescUtente( String descUtente )
    {
	    this.descUtente = descUtente;
    }

	public Integer getNumeFormalita1()
    {
	    return numeFormalita1;
    }

	public void setNumeFormalita1( Integer numeFormalita1 )
    {
	    this.numeFormalita1 = numeFormalita1;
    }

	public Short getNumeFormalita2()
    {
	    return numeFormalita2;
    }

	public void setNumeFormalita2( Short numeFormalita2 )
    {
	    this.numeFormalita2 = numeFormalita2;
    }

	public ComboForm getAttoSpecieT() {
		return attoSpecieT;
	}

	public void setAttoSpecieT(ComboForm attoSpecieT) {
		this.attoSpecieT = attoSpecieT;
	}

	public ComboForm getAttoSpecieA() {
		return attoSpecieA;
	}

	public void setAttoSpecieA(ComboForm attoSpecieA) {
		this.attoSpecieA = attoSpecieA;
	}

	

	

	

	public String getTipoComunicT()
	{
		return tipoComunicT;
	}

	public void setTipoComunicT(String tipoComunicT)
	{
		this.tipoComunicT = tipoComunicT;
	}

	public ComboForm getTipoComunicA() {
		return tipoComunicA;
	}

	public void setTipoComunicA(ComboForm tipoComunicA) {
		this.tipoComunicA = tipoComunicA;
	}

	public String getSede()
    {
	    return sede;
    }

	public void setSede( String sede )
    {
	    this.sede = sede;
    }

	public Character getFlgStato() {
		return flgStato;
	}

	public void setFlgStato(Character flgStato) {
		this.flgStato = flgStato;
	}

	public Character getFlgNotaCollegata() {
		return flgNotaCollegata;
	}

	public void setFlgNotaCollegata(Character flgNotaCollegata) {
		this.flgNotaCollegata = flgNotaCollegata;
	}

	public ComboForm getTipoComunicAnn() {
		return tipoComunicAnn;
	}

	public void setTipoComunicAnn(ComboForm tipoComunicAnn) {
		this.tipoComunicAnn = tipoComunicAnn;
	}

	public ComboForm getTipoComunicTrasc() {
		return tipoComunicTrasc;
	}

	public void setTipoComunicTrasc(ComboForm tipoComunicTrasc) {
		this.tipoComunicTrasc = tipoComunicTrasc;
	}
	

	public Long getIdNota() {
		return idNota;
	}

	public void setIdNota(Long idNota) {
		this.idNota = idNota;
	}

	public String getStatoComunicazione() {
		return statoComunicazione;
	}

	public void setStatoComunicazione(String statoComunicazione) {
		this.statoComunicazione = statoComunicazione;
	}

	public String getDescConservatoria() {
		return descConservatoria;
	}

	public void setDescConservatoria(String descConservatoria) {
		this.descConservatoria = descConservatoria;
	}

	public Date getDataComunicazione() {
		return dataComunicazione;
	}

	public void setDataComunicazione(Date dataComunicazione) {
		this.dataComunicazione = dataComunicazione;
	}

	public String getNotaSelezionata() {
		return notaSelezionata;
	}

	public void setNotaSelezionata(String notaSelezionata) {
		this.notaSelezionata = notaSelezionata;
	}

	public List<ComunicazioneListaForm> getListaPredisp() {
		return listaPredisp;
	}

	public void setListaPredisp(List<ComunicazioneListaForm> listaPredisp) {
		this.listaPredisp = listaPredisp;
	}

	public String getTenoreMisura() {
		return tenoreMisura;
	}

	public void setTenoreMisura(String tenoreMisura) {
		this.tenoreMisura = tenoreMisura;
	}

    public Boolean getListaRichiesta()
    {
        return listaRichiesta;
    }

    public void setListaRichiesta( Boolean listaRichiesta )
    {
        this.listaRichiesta = listaRichiesta;
    }

	public String[] getNotaSel() {
		return notaSel;
	}

	public void setNotaSel(String[] notaSel) {
		this.notaSel = notaSel;
	}

    public String getDescrCategoriaAtto()
    {
        return descrCategoriaAtto;
    }

    public void setDescrCategoriaAtto( String descrCategoriaAtto )
    {
        this.descrCategoriaAtto = descrCategoriaAtto;
    }

    public String getCategoriaAtto()
    {
        return categoriaAtto;
    }

    public void setCategoriaAtto( String categoriaAtto )
    {
        this.categoriaAtto = categoriaAtto;
    }

    public ComboForm getCategorieAttiA()
    {
        return categorieAttiA;
    }

    public void setCategorieAttiA( ComboForm categorieAttiA )
    {
        this.categorieAttiA = categorieAttiA;
    }

    public ComboForm getCategorieAttiT()
    {
        return categorieAttiT;
    }

    public void setCategorieAttiT( ComboForm categorieAttiT )
    {
        this.categorieAttiT = categorieAttiT;
    }

    public Character getFlgDecisorio()
    {
        return flgDecisorio;
    }

    public void setFlgDecisorio( Character flgDecisorio )
    {
        this.flgDecisorio = flgDecisorio;
    }
}
