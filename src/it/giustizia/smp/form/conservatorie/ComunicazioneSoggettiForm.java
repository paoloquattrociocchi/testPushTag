package it.giustizia.smp.form.conservatorie;

import it.giustizia.smp.dto.pg_conservatorie.SoggettoInteressatoDescDto;
import it.giustizia.smp.dto.pg_conservatorie.SoggettoInteressatoDto;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class ComunicazioneSoggettiForm {
	
	// ATTRIBUTI COMUNICAZIONE
	
	private Long                 numeroComunicazione;  //id		
	private Character            stato;              //flgStato		
	private Character            tipoNotaId;         //corrisponde a codiKIdTipoNota nella classe TTpTipoNota		
	private String               descTipoNota;       //corrisponde a descTipoNota nella classe TTpTipoNota		
	private Long                 catAttoId;          //corrisponde a codiKIdCatAtto nella classe  TTpCatAtto
	private String               descCatAtto;        //corrisponde a descCatAtto nella classe TTpCatAtto 		
	private Long                 tipoAttoId;         //corrisponde a codiKIdTipoAtto nella classe  TTpTipoAtto
	private String               descTipoAtto;       //corrisponde a descTipoAtto nella classe TTpTipoAtto 		
	private String               descAtto;           //corrisponde a DESC_ATTO della tabella TNota 		
	private Long                 numeroRepertorio1;  //numeRepert1	
	private Long                 repertorioAnno;     //repertorio anno
	private Long                 progressivoNota;    //numeProgrNota
	private Long                 numeroProcedimento; //chiave procedimento
	private Long				 conservatoriaId;	 //identificativo della Conservatoria
	private String               conservatoria;      //corrisponde a descConserv nella classe TAnConservatorie
	private String               codiceFiscale;      //descFiscalePu
	private String               titoloDescrizione;  //descTitolo
	@ModernDate
	@DateTimeFormat( pattern = "dd/MM/yyyy" )
	private Date                 data;               //dataTitolo	
	
    private ComboForm comboRegime;
	private ComboForm comboStatiBelfiore;               //  combo Stati  PF
    private ComboForm comboStatiIstat;                  //  combo Stati  PNF
    private ComboForm comboComuniBelfiore;              //  combo Comuni PF
    private ComboForm comboComuniIstat;                 //  combo Comuni PNF
    private ComboForm comboPF;

	
	private SoggettoInteressatoDescDto soggettoDto;
	private List<SoggettoInteressatoDescDto>  listaSoggetti;
	private String soggettoSelected;
	

	
	private String tipoOperazione;
	private Character flgTipoSoggetto; 	// "F = persona fisica e <> da F persona Giuridica"
	
	private SoggettoInteressatoDto soggettoAssociabileDto;
	private List<SoggettoInteressatoDto>  listaSoggettiAssociabiliDto;
	private SoggettoInteressatoDescDto soggettoAssociabileDescDto;
	private List<SoggettoInteressatoDescDto>  listaSoggettiAssociabili;
	private String soggettoAssociabileSelected;

	public Long getNumeroComunicazione()
	{
		return numeroComunicazione;
	}
	public void setNumeroComunicazione(Long numeroComunicazione)
	{
		this.numeroComunicazione = numeroComunicazione;
	}
	public Character getStato()
	{
		return stato;
	}
	public void setStato(Character stato)
	{
		this.stato = stato;
	}
	public Character getTipoNotaId()
	{
		return tipoNotaId;
	}
	public void setTipoNotaId(Character tipoNotaId)
	{
		this.tipoNotaId = tipoNotaId;
	}
	public String getDescTipoNota()
	{
		return descTipoNota;
	}
	public void setDescTipoNota(String descTipoNota)
	{
		this.descTipoNota = descTipoNota;
	}
	public Long getCatAttoId()
	{
		return catAttoId;
	}
	public void setCatAttoId(Long catAttoId)
	{
		this.catAttoId = catAttoId;
	}
	public String getDescCatAtto()
	{
		return descCatAtto;
	}
	public void setDescCatAtto(String descCatAtto)
	{
		this.descCatAtto = descCatAtto;
	}
	public Long getTipoAttoId()
	{
		return tipoAttoId;
	}
	public void setTipoAttoId(Long tipoAttoId)
	{
		this.tipoAttoId = tipoAttoId;
	}
	public String getDescTipoAtto()
	{
		return descTipoAtto;
	}
	public void setDescTipoAtto(String descTipoAtto)
	{
		this.descTipoAtto = descTipoAtto;
	}
	public String getDescAtto()
	{
		return descAtto;
	}
	public void setDescAtto(String descAtto)
	{
		this.descAtto = descAtto;
	}
	public Long getNumeroRepertorio1()
	{
		return numeroRepertorio1;
	}
	public void setNumeroRepertorio1(Long numeroRepertorio1)
	{
		this.numeroRepertorio1 = numeroRepertorio1;
	}
	public Long getRepertorioAnno()
	{
		return repertorioAnno;
	}
	public void setRepertorioAnno(Long repertorioAnno)
	{
		this.repertorioAnno = repertorioAnno;
	}
	public Long getProgressivoNota()
	{
		return progressivoNota;
	}
	public void setProgressivoNota(Long progressivoNota)
	{
		this.progressivoNota = progressivoNota;
	}
	public Long getNumeroProcedimento()
	{
		return numeroProcedimento;
	}
	public void setNumeroProcedimento(Long numeroProcedimento)
	{
		this.numeroProcedimento = numeroProcedimento;
	}
	public Long getConservatoriaId()
	{
		return conservatoriaId;
	}
	public void setConservatoriaId(Long conservatoriaId)
	{
		this.conservatoriaId = conservatoriaId;
	}
	public String getConservatoria()
	{
		return conservatoria;
	}
	public void setConservatoria(String conservatoria)
	{
		this.conservatoria = conservatoria;
	}
	public String getCodiceFiscale()
	{
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale)
	{
		this.codiceFiscale = codiceFiscale;
	}
	public String getTitoloDescrizione()
	{
		return titoloDescrizione;
	}
	public void setTitoloDescrizione(String titoloDescrizione)
	{
		this.titoloDescrizione = titoloDescrizione;
	}
	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}
	public List<SoggettoInteressatoDescDto> getListaSoggetti()
	{
		return listaSoggetti;
	}
	public void setListaSoggetti(List<SoggettoInteressatoDescDto> listaSoggetti)
	{
		this.listaSoggetti = listaSoggetti;
	}
	public String getSoggettoSelected()
	{
		return soggettoSelected;
	}
	public void setSoggettoSelected(String soggettoSelected)
	{
		this.soggettoSelected = soggettoSelected;
	}
	public String getTipoOperazione()
	{
		return tipoOperazione;
	}
	public void setTipoOperazione(String tipoOperazione)
	{
		this.tipoOperazione = tipoOperazione;
	}
	public Character getFlgTipoSoggetto()
	{
		return flgTipoSoggetto;
	}
	public void setFlgTipoSoggetto(Character flgTipoSoggetto)
	{
		this.flgTipoSoggetto = flgTipoSoggetto;
	}
	public SoggettoInteressatoDescDto getSoggettoDto()
    {
	    return soggettoDto;
    }
	public void setSoggettoDto( SoggettoInteressatoDescDto soggettoDto )
    {
	    this.soggettoDto = soggettoDto;
    }
	public ComboForm getComboStatiIstat()
    {
	    return comboStatiIstat;
    }
	public void setComboStatiIstat( ComboForm comboStatiIstat )
    {
	    this.comboStatiIstat = comboStatiIstat;
    }
    public ComboForm getComboStatiBelfiore()
    {
        return comboStatiBelfiore;
    }
    public void setComboStatiBelfiore( ComboForm comboStatiBelfiore )
    {
        this.comboStatiBelfiore = comboStatiBelfiore;
    }
	public SoggettoInteressatoDto getSoggettoAssociabileDto()
	{
		return soggettoAssociabileDto;
	}
	public void setSoggettoAssociabileDto(SoggettoInteressatoDto soggettoAssociabileDto)
	{
		this.soggettoAssociabileDto = soggettoAssociabileDto;
	}	
	public SoggettoInteressatoDescDto getSoggettoAssociabileDescDto() {
		return soggettoAssociabileDescDto;
	}
	public void setSoggettoAssociabileDescDto(SoggettoInteressatoDescDto soggettoAssociabileDescDto) {
		this.soggettoAssociabileDescDto = soggettoAssociabileDescDto;
	}
		public List<SoggettoInteressatoDto> getListaSoggettiAssociabiliDto() {
		return listaSoggettiAssociabiliDto;
	}
	public void setListaSoggettiAssociabiliDto(	List<SoggettoInteressatoDto> listaSoggettiAssociabiliDto) {
		this.listaSoggettiAssociabiliDto = listaSoggettiAssociabiliDto;
	}
	public List<SoggettoInteressatoDescDto> getListaSoggettiAssociabili() {
		return listaSoggettiAssociabili;
	}
	public void setListaSoggettiAssociabili(List<SoggettoInteressatoDescDto> listaSoggettiAssociabili) {
		this.listaSoggettiAssociabili = listaSoggettiAssociabili;
	}
	public String getSoggettoAssociabileSelected()
	{
		return soggettoAssociabileSelected;
	}
	public void setSoggettoAssociabileSelected(String soggettoAssociabileSelected)
	{
		this.soggettoAssociabileSelected = soggettoAssociabileSelected;
	}
    public ComboForm getComboRegime()
    {
        return comboRegime;
    }
    public void setComboRegime( ComboForm comboRegime )
    {
        this.comboRegime = comboRegime;
    }
    public ComboForm getComboComuniBelfiore()
    {
        return comboComuniBelfiore;
    }
    public void setComboComuniBelfiore( ComboForm comboComuniBelfiore )
    {
        this.comboComuniBelfiore = comboComuniBelfiore;
    }
    public ComboForm getComboComuniIstat()
    {
        return comboComuniIstat;
    }
    public void setComboComuniIstat( ComboForm comboComuniIstat )
    {
        this.comboComuniIstat = comboComuniIstat;
    }
    public ComboForm getComboPF()
    {
        return comboPF;
    }
    public void setComboPF( ComboForm comboPF )
    {
        this.comboPF = comboPF;
    }
    
}
