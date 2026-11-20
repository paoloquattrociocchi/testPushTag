package it.giustizia.smp.form.conservatorie;

import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ComunicazioneModificaForm
{
	//# Le variabili sono mappate nella classe TNota

	private String    descUtente;

	private Long      numeroComunicazione; //idNota		
	private Character stato;
	private Character tipoNotaId;         //corrisponde a codiKIdTipoNota nella classe TTpTipoNota		
	private String    descTipoNota;       //corrisponde a descTipoNota nella classe TTpTipoNota		
	private String    categorieAtti;      // in inserimento è una combo
	private Long      repertorioAnno;     //numeRepert2	
	private Long      numero;             //numeRepert1
	private Long      progressivo;        //numeProgrNota	
	private String    descAtto;			  // descrizione SpecieAtto

	//Dati Generali
	private ComboForm conservatoria;      //corrisponde a descConserv nella classe TAnConservatorie
	private String    conservatoriaSelez; // Conservatoria selezionata
	private String    descrConservatoria; // descrizione Conservatoria selezionata
	private String    codiceFiscale;      //descFiscalePu
	private String    titoloDescrizione;  //descTitolo
	@ModernDate
	@DateTimeFormat( pattern = "dd/MM/yyyy" )
	private Date      dataTitolo;         //dataTitolo			 	
	private String    pubblicoUfficiale;  //descPubblUff
	private Character categoria;          //in inserimento è Char descTipoPubblUff 
	private ComboForm comune;             //descSedePubblUff
	private String    sede;               //sede selezionata
	private String    provincia;          //descProvPubblUff
	private String    attoSpecie;         //in inserimento è una combo
	private ComboForm rettifica;          //campo digitabile solo per le comunicazioni di tipo trascrizione
	private String    rettificaSelez;     //rettifica selezionata
	private ComboForm tipoComunic;        //tipo comunicazione visualizzata come 'Descrizione'
	private String    tipoComunicSelez;   //tipo comunicazione selezionata
	private Short     unitaNegoziali;     //numeUnitNeg
	private Integer   immobili;           //numeImmobili
	private Short     soggettiFavore;     //numeSoggFavore
	private Short     soggettiContro;     //numeSoggContro
	private String    labelSezAnn = "";

	//Dati relativi alla trascrizione 

	private boolean   attoSoggVoltura;
	private boolean   presenzaCondizione;
	private boolean   differita;
	@DateTimeFormat( pattern = "dd/MM/yyyy" )
	private Date      dataDal;
	private boolean   presenzaTermini;
	@DateTimeFormat( pattern = "dd/MM/yyyy" )
	private Date      dataFormalita;

	private Integer   numeroFormalita1;
	private Short     numeroFormalita2;
	private boolean   quadroA;
	private boolean   quadroB;
	private boolean   quadroC;

	//Dati relativi all'annotazione 
	@DateTimeFormat( pattern = "dd/MM/yyyy" )
	private Date      trascrizioneDel;
	private Integer   numeroTrascrizione1;
	private Short     numeroTrascrizione2;
	private String    valoreImmobLib;
	private boolean   volturaCatastale;
	private String    formalita;
	
	private ComboForm tipoComunicAnn;
	private ComboForm tipoComunicTrasc;

	public String getDescUtente()
	{
		return descUtente;
	}

	public void setDescUtente( String descUtente )
	{
		this.descUtente = descUtente;
	}

	public Date getTrascrizioneDel()
	{
		return trascrizioneDel;
	}

	public void setTrascrizioneDel( Date trascrizioneDel )
	{
		this.trascrizioneDel = trascrizioneDel;
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

	public Long getRepertorioAnno()
	{
		return repertorioAnno;
	}

	public void setRepertorioAnno( Long repertorioAnno )
	{
		this.repertorioAnno = repertorioAnno;
	}

	public Long getNumero()
	{
		return numero;
	}

	public void setNumero( Long numero )
	{
		this.numero = numero;
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

	public String getProvincia()
	{
		return provincia;
	}

	public void setProvincia( String provincia )
	{
		this.provincia = provincia;
	}

	public String getAttoSpecie()
	{
		return attoSpecie;
	}

	public void setAttoSpecie( String attoSpecie )
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

	public String getCategorieAtti()
	{
		return categorieAtti;
	}

	public void setCategorieAtti( String categorieAtti )
	{
		this.categorieAtti = categorieAtti;
	}

	public String getLabelSezAnn()
	{
		return labelSezAnn;
	}

	public void setLabelSezAnn( String labelSezAnn )
	{
		this.labelSezAnn = labelSezAnn;
	}

	public Long getProgressivo()
	{
		return progressivo;
	}

	public void setProgressivo( Long progressivo )
	{
		this.progressivo = progressivo;
	}

	public Character getCategoria()
	{
		return categoria;
	}

	public void setCategoria( Character categoria )
	{
		this.categoria = categoria;
	}

	public String getDescrConservatoria()
	{
		return descrConservatoria;
	}

	public void setDescrConservatoria( String descrConservatoria )
	{
		this.descrConservatoria = descrConservatoria;
	}

	public String getConservatoriaSelez()
	{
		return conservatoriaSelez;
	}

	public void setConservatoriaSelez( String conservatoriaSelez )
	{
		this.conservatoriaSelez = conservatoriaSelez;
	}

	public String getSede()
	{
		return sede;
	}

	public void setSede( String sede )
	{
		this.sede = sede;
	}

	public Character getStato()
	{
		return stato;
	}

	public void setStato( Character stato )
	{
		this.stato = stato;
	}

	public String getTipoComunicSelez()
	{
		return tipoComunicSelez;
	}

	public void setTipoComunicSelez( String tipoComunicSelez )
	{
		this.tipoComunicSelez = tipoComunicSelez;
	}

	public String getRettificaSelez()
	{
		return rettificaSelez;
	}

	public void setRettificaSelez( String rettificaSelez )
	{
		this.rettificaSelez = rettificaSelez;
	}

	public Integer getNumeroFormalita1()
	{
		return numeroFormalita1;
	}

	public void setNumeroFormalita1( Integer numeroFormalita1 )
	{
		this.numeroFormalita1 = numeroFormalita1;
	}

	public Short getNumeroFormalita2()
	{
		return numeroFormalita2;
	}

	public void setNumeroFormalita2( Short numeroFormalita2 )
	{
		this.numeroFormalita2 = numeroFormalita2;
	}

	public Integer getNumeroTrascrizione1()
	{
		return numeroTrascrizione1;
	}

	public void setNumeroTrascrizione1( Integer numeroTrascrizione1 )
	{
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

	public Character getTipoNotaId() {
		return tipoNotaId;
	}

	public void setTipoNotaId(Character tipoNotaId) {
		this.tipoNotaId = tipoNotaId;
	}

	public String getDescTipoNota() {
		return descTipoNota;
	}

	public void setDescTipoNota(String descTipoNota) {
		this.descTipoNota = descTipoNota;
	}

	public String getValoreImmobLib() {
		return valoreImmobLib;
	}

	public void setValoreImmobLib(String valoreImmobLib) {
		this.valoreImmobLib = valoreImmobLib;
	}

	public String getDescAtto() {
		return descAtto;
	}

	public void setDescAtto(String descAtto) {
		this.descAtto = descAtto;
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
	
	
}
