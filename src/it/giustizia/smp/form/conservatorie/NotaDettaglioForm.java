package it.giustizia.smp.form.conservatorie;

import it.giustizia.smp.dto.pg_conservatorie.BeneAssociabileDto;
import it.giustizia.smp.dto.pg_conservatorie.BeneAssociatoDescDto;
import it.giustizia.smp.dto.pg_conservatorie.SoggettoInteressatoDescDto;
import it.giustizia.smp.dto.pg_conservatorie.SoggettoInteressatoDto;
import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class NotaDettaglioForm
{
	//# Le variabili sono mappate nella classe TNota

	private Long                 numero;             //id		
	private Character            stato;              //flgStato		
	private Character            tipoNotaId;         //corrisponde a codiKIdTipoNota nella classe TTpTipoNota		
	private String               descTipoNota;       //corrisponde a descTipoNota nella classe TTpTipoNota		
	private Long                 catAttoId;         //corrisponde a codiKIdCatAtto nella classe  TTpCatAtto
	private String               descCatAtto;       //corrisponde a descCatAtto nella classe TTpCatAtto 		
	private Long                 tipoAttoId;         //corrisponde a codiKIdTipoAtto nella classe  TTpTipoAtto
	private String               descTipoAtto;       //corrisponde a descTipoAtto nella classe TTpTipoAtto 		
	private String               descAtto;           //corrisponde a DESC_ATTO della tabella TNota 		
	private Long                 numeroRepertorio1;  //numeRepert1	
	private Long                 repertorioAnno;     //numeRepert2	
	private Long                 numeroProcedimento; //chiave procedimento
	private Long                 progressivo;        //numeProgrNota	
	private Long				 conservatoriaId;	 //identificativo della Conservatoria
	private String               conservatoria;      //corrisponde a descConserv nella classe TAnConservatorie
	private String               codiceFiscale;      //descFiscalePu
	private String               titoloDescrizione;  //descTitolo
	@ModernDate
	@DateTimeFormat( pattern = "dd/MM/yyyy" )
	private Date                 data;               //dataTitolo			 	
	private String               pubblicoUfficiale;  //descPubblUff
	private Character            categoria;          //descTipoPubblUff
	private String               sede;               //descSedePubblUff
	private String               provincia;          //descProvPubblUff
	private String               attoSpecie;         //descSpecieAtto
	private Long                 tipoComunicId;      //identificativo della comunicazione 
	private String               tipoComunic;        //sulla maschera appare lalabel 'Descrizione'
	private Long                 unitaNegoziali;     //numeUnitNeg
	private Long                 immobili;           //numeImmobili
	private Long                 soggettiFavore;     //numeSoggFavore
	private Long                 soggettiContro;     //numeSoggContro

	private String               descStato;
	private String               descTipoPubblUff;

	//Dati relativi alla trascrizione 

	private boolean              attoSoggVoltura;
	private boolean              presenzaCondizione;
	private boolean              differita;
	@DateTimeFormat( pattern = "dd/MM/yyyy" )
	private Date                 dataDal;
	private boolean              presenzaTermini;
	@DateTimeFormat( pattern = "dd/MM/yyyy" )
	private Date                 dataFormalita;
	private Integer              numeroFormalita1;
	private Short                numeroFormalita2;
	private boolean              quadroA;
	private boolean              quadroB;
	private boolean              quadroC;

	//Dati relativi all'annotazione 
	@DateTimeFormat( pattern = "dd/MM/yyyy" )
	private Date                 trascrizioneDel;
	private Integer              numeroTrascrizione1;
	private Short                numeroTrascrizione2;
    private String               valoreImmobLibStr;
	private Long                 valoreImmobLib;
	private Byte                 valoreImmobLibParteDecimale;
	private boolean              volturaCatastale;
	private String               formalita;

	private List<BeneAssociatoDescDto>   listaBeni;
	private List<BeneAssociatoDescDto>   listaUnitaNegoziali;
	private List<BeneAssociabileDto>     listaBeniAssociabili;
	private List<SoggettoInteressatoDescDto> listaSoggetti;

// due campi per Quadro D
	private boolean              quadroDPresente;
	private String               oldQuadroD;  		 //descNota della tabella T_Nota_Quadro_D
	private String               quadroD;  			 //descNota della tabella T_Nota_Quadro_D
	private Character            tipoInf;            //corrisponde al campo CODI_K_TIPO_INF della tabella TNotaQuadroD che può assumere valore 0 o 1.	
	
	public Long getNumero()
	{
		return numero;
	}

	public void setNumero( Long numero )
	{
		this.numero = numero;
	}

	public Character getStato()
	{
		return stato;
	}

	public void setStato( Character stato )
	{
		this.stato = stato;
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

	public Long getRepertorioAnno()
	{
		return repertorioAnno;
	}

	public void setRepertorioAnno( Long repertorioAnno )
	{
		this.repertorioAnno = repertorioAnno;
	}

	public Long getNumeroProcedimento()
	{
		return numeroProcedimento;
	}

	public void setNumeroProcedimento( Long numeroProcedimento )
	{
		this.numeroProcedimento = numeroProcedimento;
	}

	public Long getProgressivo()
	{
		return progressivo;
	}

	public void setProgressivo( Long progressivo )
	{
		this.progressivo = progressivo;
	}

	public String getConservatoria()
	{
		return conservatoria;
	}

	public void setConservatoria( String conservatoria )
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

	public Date getData()
	{
		return data;
	}

	public void setData( Date data )
	{
		this.data = data;
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

	public String getSede()
	{
		return sede;
	}

	public void setSede( String sede )
	{
		this.sede = sede;
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

	public Long getUnitaNegoziali()
	{
		return unitaNegoziali;
	}

	public void setUnitaNegoziali( Long unitaNegoziali )
	{
		this.unitaNegoziali = unitaNegoziali;
	}

	public Long getImmobili()
	{
		return immobili;
	}

	public void setImmobili( Long immobili )
	{
		this.immobili = immobili;
	}

	public Long getSoggettiFavore()
	{
		return soggettiFavore;
	}

	public void setSoggettiFavore( Long soggettiFavore )
	{
		this.soggettiFavore = soggettiFavore;
	}

	public Long getSoggettiContro()
	{
		return soggettiContro;
	}

	public void setSoggettiContro( Long soggettiContro )
	{
		this.soggettiContro = soggettiContro;
	}


	public String getDescStato()
	{
		return descStato;
	}

	public void setDescStato( String descStato )
	{
		this.descStato = descStato;
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

	public Date getTrascrizioneDel()
	{
		return trascrizioneDel;
	}

	public void setTrascrizioneDel( Date trascrizioneDel )
	{
		this.trascrizioneDel = trascrizioneDel;
	}

	public Long getValoreImmobLib()
	{
		return valoreImmobLib;
	}

	public void setValoreImmobLib( Long valoreImmobLib )
	{
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

	public String getFormalita()
	{
		return formalita;
	}

	public void setFormalita( String formalita )
	{
		this.formalita = formalita;
	}

	//	public String setDescStato() {
	//        switch (stato) {
	//		 case '0':
	//			 descStato = "In lavorazione";
	//		 break;
	//		 case '1':
	//			 descStato = "Validata";
	//		 break;
	//		 case '2':
	//			 descStato = "Inviata";
	//		 break;
	//		 case '3':
	//			 descStato = "Sostituita";
	//		 break;
	//        }
	//    	return descTipoPubblUff;
	//	}
	public String getDescTipoPubblUff()
	{
		return descTipoPubblUff;
	}

	public void setDescTipoPubblUff( String descTipoPubblUff )
	{
		this.descTipoPubblUff = descTipoPubblUff;
	}

	//	public String setDescTipoPubblUff() {
	//	    switch (categoria) {
	//		 case '1':
	//			 descTipoPubblUff = "Notaio" ;
	//		 break;
	//		 case '2':
	//			 descTipoPubblUff = "Altro pubblico ufficiale";
	//		 break;
	//	    }
	//	return descTipoPubblUff;
	//	}
	public String getTipoComunic()
	{
		return tipoComunic;
	}

	public void setTipoComunic( String tipoComunic )
	{
		this.tipoComunic = tipoComunic;
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

	public Long getConservatoriaId() {
		return conservatoriaId;
	}

	public void setConservatoriaId(Long conservatoriaId) {
		this.conservatoriaId = conservatoriaId;
	}

	public Long getTipoComunicId() {
		return tipoComunicId;
	}

	public void setTipoComunicId(Long tipoComunicId) {
		this.tipoComunicId = tipoComunicId;
	}

	public Long getNumeroRepertorio1() {
		return numeroRepertorio1;
	}

	public void setNumeroRepertorio1(Long numeroRepertorio1) {
		this.numeroRepertorio1 = numeroRepertorio1;
	}

	public Long getCatAttoId() {
		return catAttoId;
	}

	public void setCatAttoId(Long catAttoId) {
		this.catAttoId = catAttoId;
	}

	public String getDescCatAtto() {
		return descCatAtto;
	}

	public void setDescCatAtto(String descCatAtto) {
		this.descCatAtto = descCatAtto;
	}

	public Long getTipoAttoId() {
		return tipoAttoId;
	}

	public void setTipoAttoId(Long tipoAttoId) {
		this.tipoAttoId = tipoAttoId;
	}

	public String getDescTipoAtto() {
		return descTipoAtto;
	}

	public void setDescTipoAtto(String descTipoAtto) {
		this.descTipoAtto = descTipoAtto;
	}

	public String getDescAtto() {
		return descAtto;
	}

	public void setDescAtto(String descAtto) {
		this.descAtto = descAtto;
	}

	public List<SoggettoInteressatoDescDto> getListaSoggetti() {
		return listaSoggetti;
	}

	public void setListaSoggetti(List<SoggettoInteressatoDescDto> listaSoggetti) {
		this.listaSoggetti = listaSoggetti;
	}

	public String getQuadroD()
    {
	    return quadroD;
    }

	public void setQuadroD( String quadroD )
    {
	    this.quadroD = quadroD;
    }

	public Character getTipoInf()
    {
	    return tipoInf;
    }

	public void setTipoInf( Character tipoInf )
    {
	    this.tipoInf = tipoInf;
    }

	public List<BeneAssociatoDescDto> getListaBeni() {
		return listaBeni;
	}

	public void setListaBeni(List<BeneAssociatoDescDto> listaBeni) {
		this.listaBeni = listaBeni;
	}

	public List<BeneAssociabileDto> getListaBeniAssociabili() {
		return listaBeniAssociabili;
	}

	public void setListaBeniAssociabili(List<BeneAssociabileDto> listaBeniAssociabili) {
		this.listaBeniAssociabili = listaBeniAssociabili;
	}

	public boolean isQuadroDPresente()
    {
	    return quadroDPresente;
    }

	public void setQuadroDPresente( boolean quadroDPresente )
    {
	    this.quadroDPresente = quadroDPresente;
    }

	public String getOldQuadroD()
    {
	    return oldQuadroD;
    }

	public void setOldQuadroD( String oldQuadroD )
    {
	    this.oldQuadroD = oldQuadroD;
    }

	public List<BeneAssociatoDescDto> getListaUnitaNegoziali() {
		return listaUnitaNegoziali;
	}

	public void setListaUnitaNegoziali(
			List<BeneAssociatoDescDto> listaUnitaNegoziali) {
		this.listaUnitaNegoziali = listaUnitaNegoziali;
	}

	public Byte getValoreImmobLibParteDecimale()
	{
		return valoreImmobLibParteDecimale;
	}

	public void setValoreImmobLibParteDecimale(Byte valoreImmobLibParteDecimale)
	{
		this.valoreImmobLibParteDecimale = valoreImmobLibParteDecimale;
	}

    public String getValoreImmobLibStr()
    {
        return valoreImmobLibStr;
    }

    public void setValoreImmobLibStr( String valoreImmobLibStr )
    {
        this.valoreImmobLibStr = valoreImmobLibStr;
    }

}
