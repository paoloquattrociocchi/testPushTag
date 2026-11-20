package it.giustizia.smp.form.conservatorie;

import it.giustizia.smp.dto.pg_conservatorie.BeneAssociabileDto;
import it.giustizia.smp.dto.pg_conservatorie.BeneAssociatoDescDto;
import it.giustizia.smp.dto.pg_conservatorie.ComuneProvinciaDto;
import it.giustizia.smp.dto.pg_conservatorie.SoggettoInteressatoDescDto;
import it.giustizia.smp.dto.pg_conservatorie.TitoloDirittoDto;
import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.form.IndirizzoForm;
import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;



public class ComunicazioneBeniForm {
	
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
	
	private List<BeneAssociatoDescDto>  listaBeni;
	private List<BeneAssociabileDto>  listaBeniAssociabili;
	private List<ComuneProvinciaDto>  listaComuniConservatoria;
	private List<TitoloDirittoDto> listaTitoliDiritto;
	private String titoloDirittoSelected;
	private String comuneConservatoria; 
	
	private String beneSelected;
	private String tipoOperazione;
	private Long beneSitMp;
	private String descBene;
	private Long numeQuotaInt;
	private Long numeQuotaDec;
	private Long numeQuotaDen;
	private String numeQuota;
	private Long soggettoSitMp;
	private String cognome;
	private String nome;
	private String denominazione;
	
	
	// ATTRIBUTI BENE su Model TNotaBene
	private String descSedeImmobile;
	private String descCatasto;					// da suddividere su codiceCatastale1  e codiceCatastale2
	private String descCatastoP;				// da suddividere su codiceCatastale1P e codiceCatastale2P
	private String descSezione;
	private String descSezioneP;
	private String descFoglio;
	private String descFoglioP;
	private String descParticella1;
	private String descParticella1P;
	private String descParticella2;
	private String descParticella2P;
	private String descSubalterno1;
	private String descSubalterno1P;
	private String descSubalterno2;
	private String descSubalterno2P;
	private Integer numeDenuncia1;
	private Integer numeDenuncia1P;
	private Short numeDenuncia2;
	private Short numeDenuncia2P;
	private Short numeAnnoDenuncia;
	private Short numeAnnoDenunciaP;
	private Long numeGraffati;
	private Integer numeSupEttari;
	private Integer numeSupAre;
	private Integer numeSupCentiare;
	private Short numeSupMqInt;
	private Short numeSupMqDec;
	private Short numeVaniInt;
	private Short numeVaniDec;
	private Short numeMcInt;
	private Short numeMcDec;
	private String descIndirizzo;
	private Long numeCapitaleInt;
	private Short numeCapitaleDec;
	private String descCivico;
	private String descLotto;
	private String descEdificio;
	private String descScala;
	private String descInterno;
	private String descPiano;
	private Character flgErr;
	private String descUtente;
	private Date dataUltAgg;
	
	
	// ATTRIBUTI BENE
	private Character tipoDenuncia;
	private Character tipoDenunciaP;
	private String descTipoDenuncia;
	private String descTipoDenunciaP;

	private String descComuneBene;
	private String descComuneBeneP;

	private String codiceCatastale1;
	private String codiceCatastale2;
	private String descTipoCatasto;
	private Character tipoCatasto;
	private String descrFondo;
	private Character fondo;
	private String descrNaturaImmobile;
	private Integer idNaturaImmobile;
	private IndirizzoForm indirizzo;
    private String codiceCatastale1P;
    private String codiceCatastale1POld;
    private String codiceCatastale2P;
    private Character tipoCatastoP;
    
    private String beneAssociabileSelected;
    
    private ComboForm categoriaCatastale;
	private ComboForm comuneCatastaleP;
	private ComboForm comune;             	//	combo comune
	
	//rm
	private String tipoSogg;
	
	private SoggettoInteressatoDescDto soggettoDto;
	private List<SoggettoInteressatoDescDto>  listaSoggetti;
	private String soggettoSelected;
	
	private int idTipoInt;
	private String descTipoInt;
	private   ComboForm      listaTipiInteresse;
	 
	//rm;
	
	// Gestione campi
	private boolean   disabledCampi;
	private boolean   disabledCampiT;
	
	private boolean   disabledCampiIdentificazione;
	private boolean   disabledCampiDenuncia;
	
	private boolean   disabledCampiIdentificazioneP;
	private boolean   disabledCampiDenunciaP;
	
	private boolean   identificazioneCatastale;
	private boolean   denunciaAccatastamento;
	
	private boolean   identificazioneCatastaleChecked;
	private boolean   denunciaAccatastamentoChecked;
	
	private boolean   identificazioneCatastaleP;
	private boolean   denunciaAccatastamentoP;
	
	
	
	
	public Long getSoggettoSitMp() {
		return soggettoSitMp;
	}
	public void setSoggettoSitMp(Long soggettoSitMp) {
		this.soggettoSitMp = soggettoSitMp;
	}
	public String getTipoSogg() {
		return tipoSogg;
	}
	public void setTipoSogg(String tipoSogg) {
		this.tipoSogg = tipoSogg;
	}
	public Long getNumeQuotaInt() {
		return numeQuotaInt;
	}
	public void setNumeQuotaInt(Long numeQuotaInt) {
		this.numeQuotaInt = numeQuotaInt;
	}
	public Long getNumeQuotaDec() {
		return numeQuotaDec;
	}
	public void setNumeQuotaDec(Long numeQuotaDec) {
		this.numeQuotaDec = numeQuotaDec;
	}
	public Long getNumeQuotaDen() {
		return numeQuotaDen;
	}
	public void setNumeQuotaDen(Long numeQuotaDen) {
		this.numeQuotaDen = numeQuotaDen;
	}
	public String getNumeQuota() {
		return numeQuota;
	}
	public void setNumeQuota(String numeQuota) {
		this.numeQuota = numeQuota;
	}
	
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getTitoloDirittoSelected() {
		return titoloDirittoSelected;
	}
	public void setTitoloDirittoSelected(String titoloDirittoSelected) {
		this.titoloDirittoSelected = titoloDirittoSelected;
	}
	public Long getNumeroComunicazione() {
		return numeroComunicazione;
	}
	public void setNumeroComunicazione(Long numeroComunicazione) {
		this.numeroComunicazione = numeroComunicazione;
	}
	public Character getStato() {
		return stato;
	}
	public void setStato(Character stato) {
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
	public Long getNumeroRepertorio1() {
		return numeroRepertorio1;
	}
	public void setNumeroRepertorio1(Long numeroRepertorio1) {
		this.numeroRepertorio1 = numeroRepertorio1;
	}
	public Long getRepertorioAnno() {
		return repertorioAnno;
	}
	public void setRepertorioAnno(Long repertorioAnno) {
		this.repertorioAnno = repertorioAnno;
	}
	public Long getProgressivoNota() {
		return progressivoNota;
	}
	public void setProgressivoNota(Long progressivoNota) {
		this.progressivoNota = progressivoNota;
	}
	public Long getNumeroProcedimento() {
		return numeroProcedimento;
	}
	public void setNumeroProcedimento(Long numeroProcedimento) {
		this.numeroProcedimento = numeroProcedimento;
	}
	public Long getConservatoriaId() {
		return conservatoriaId;
	}
	public void setConservatoriaId(Long conservatoriaId) {
		this.conservatoriaId = conservatoriaId;
	}
	public String getConservatoria() {
		return conservatoria;
	}
	public void setConservatoria(String conservatoria) {
		this.conservatoria = conservatoria;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getTitoloDescrizione() {
		return titoloDescrizione;
	}
	public void setTitoloDescrizione(String titoloDescrizione) {
		this.titoloDescrizione = titoloDescrizione;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
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
	public String getBeneSelected() {
		return beneSelected;
	}
	public void setBeneSelected(String beneSelected) {
		this.beneSelected = beneSelected;
	}
	public String getTipoOperazione() {
		return tipoOperazione;
	}
	public void setTipoOperazione(String tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}
	public String getDescSedeImmobile() {
		return descSedeImmobile;
	}
	public void setDescSedeImmobile(String descSedeImmobile) {
		this.descSedeImmobile = descSedeImmobile;
	}
	public String getDescCatasto() {
		if (codiceCatastale2 != null) {
			descCatasto = codiceCatastale1 + codiceCatastale2;
		} else {
			descCatasto = codiceCatastale1;
		}
		return descCatasto;
	}
	public void setDescCatasto(String descCatasto) {
		if (codiceCatastale2 != null) {
			this.descCatasto = codiceCatastale1 + codiceCatastale2;
		} else {
			this.descCatasto = codiceCatastale1;
		}
	}
	public String getDescCatastoP() {
		if (codiceCatastale2P != null) {
			descCatastoP = codiceCatastale1P + codiceCatastale2P;
		} else {
			descCatastoP = codiceCatastale1P;
		}
		return descCatastoP;
	}
	public void setDescCatastoP(String descCatastoP) {
		if (codiceCatastale2P != null) {
			this.descCatastoP = codiceCatastale1P + codiceCatastale2P;
		} else {
			this.descCatastoP = codiceCatastale1P;
		}
	}
	public String getDescSezione() {
		return descSezione;
	}
	public void setDescSezione(String descSezione) {
		this.descSezione = descSezione;
	}
	public String getDescSezioneP() {
		return descSezioneP;
	}
	public void setDescSezioneP(String descSezioneP) {
		this.descSezioneP = descSezioneP;
	}
	public String getDescFoglio() {
		return descFoglio;
	}
	public void setDescFoglio(String descFoglio) {
		this.descFoglio = descFoglio;
	}
	public String getDescFoglioP() {
		return descFoglioP;
	}
	public void setDescFoglioP(String descFoglioP) {
		this.descFoglioP = descFoglioP;
	}
	public String getDescParticella1() {
		return descParticella1;
	}
	public void setDescParticella1(String descParticella1) {
		this.descParticella1 = descParticella1;
	}
	public String getDescParticella1P() {
		return descParticella1P;
	}
	public void setDescParticella1P(String descParticella1P) {
		this.descParticella1P = descParticella1P;
	}
	public String getDescParticella2() {
		return descParticella2;
	}
	public void setDescParticella2(String descParticella2) {
		this.descParticella2 = descParticella2;
	}
	public String getDescParticella2P() {
		return descParticella2P;
	}
	public void setDescParticella2P(String descParticella2P) {
		this.descParticella2P = descParticella2P;
	}
	public String getDescSubalterno1() {
		return descSubalterno1;
	}
	public void setDescSubalterno1(String descSubalterno1) {
		this.descSubalterno1 = descSubalterno1;
	}
	public String getDescSubalterno1P() {
		return descSubalterno1P;
	}
	public void setDescSubalterno1P(String descSubalterno1P) {
		this.descSubalterno1P = descSubalterno1P;
	}
	public String getDescSubalterno2() {
		return descSubalterno2;
	}
	public void setDescSubalterno2(String descSubalterno2) {
		this.descSubalterno2 = descSubalterno2;
	}
	public String getDescSubalterno2P() {
		return descSubalterno2P;
	}
	public void setDescSubalterno2P(String descSubalterno2P) {
		this.descSubalterno2P = descSubalterno2P;
	}
	public Integer getNumeDenuncia1() {
		return numeDenuncia1;
	}
	public void setNumeDenuncia1(Integer numeDenuncia1) {
		this.numeDenuncia1 = numeDenuncia1;
	}
	public Integer getNumeDenuncia1P() {
		return numeDenuncia1P;
	}
	public void setNumeDenuncia1P(Integer numeDenuncia1P) {
		this.numeDenuncia1P = numeDenuncia1P;
	}
	public Short getNumeDenuncia2() {
		return numeDenuncia2;
	}
	public void setNumeDenuncia2(Short numeDenuncia2) {
		this.numeDenuncia2 = numeDenuncia2;
	}
	public Short getNumeDenuncia2P() {
		return numeDenuncia2P;
	}
	public void setNumeDenuncia2P(Short numeDenuncia2P) {
		this.numeDenuncia2P = numeDenuncia2P;
	}
	public Short getNumeAnnoDenuncia() {
		return numeAnnoDenuncia;
	}
	public void setNumeAnnoDenuncia(Short numeAnnoDenuncia) {
		this.numeAnnoDenuncia = numeAnnoDenuncia;
	}
	public Short getNumeAnnoDenunciaP() {
		return numeAnnoDenunciaP;
	}
	public void setNumeAnnoDenunciaP(Short numeAnnoDenunciaP) {
		this.numeAnnoDenunciaP = numeAnnoDenunciaP;
	}
	public Long getNumeGraffati() {
		return numeGraffati;
	}
	public void setNumeGraffati(Long numeGraffati) {
		this.numeGraffati = numeGraffati;
	}
	public Integer getNumeSupEttari() {
		return numeSupEttari;
	}
	public void setNumeSupEttari(Integer numeSupEttari) {
		this.numeSupEttari = numeSupEttari;
	}
	public Integer getNumeSupAre() {
		return numeSupAre;
	}
	public void setNumeSupAre(Integer numeSupAre) {
		this.numeSupAre = numeSupAre;
	}
	public Integer getNumeSupCentiare() {
		return numeSupCentiare;
	}
	public void setNumeSupCentiare(Integer numeSupCentiare) {
		this.numeSupCentiare = numeSupCentiare;
	}
	public Short getNumeSupMqInt() {
		return numeSupMqInt;
	}
	public void setNumeSupMqInt(Short numeSupMqInt) {
		this.numeSupMqInt = numeSupMqInt;
	}
	public Short getNumeSupMqDec() {
		return numeSupMqDec;
	}
	public void setNumeSupMqDec(Short numeSupMqDec) {
		this.numeSupMqDec = numeSupMqDec;
	}
	public Short getNumeVaniInt() {
		return numeVaniInt;
	}
	public void setNumeVaniInt(Short numeVaniInt) {
		this.numeVaniInt = numeVaniInt;
	}
	public Short getNumeVaniDec() {
		return numeVaniDec;
	}
	public void setNumeVaniDec(Short numeVaniDec) {
		this.numeVaniDec = numeVaniDec;
	}
	public Short getNumeMcInt() {
		return numeMcInt;
	}
	public void setNumeMcInt(Short numeMcInt) {
		this.numeMcInt = numeMcInt;
	}
	public Short getNumeMcDec() {
		return numeMcDec;
	}
	public void setNumeMcDec(Short numeMcDec) {
		this.numeMcDec = numeMcDec;
	}
	public String getDescIndirizzo() {
		return descIndirizzo;
	}
	public void setDescIndirizzo(String descIndirizzo) {
		this.descIndirizzo = descIndirizzo;
	}
	public Long getNumeCapitaleInt() {
		return numeCapitaleInt;
	}
	public void setNumeCapitaleInt(Long numeCapitaleInt) {
		this.numeCapitaleInt = numeCapitaleInt;
	}
	public Short getNumeCapitaleDec() {
		return numeCapitaleDec;
	}
	public void setNumeCapitaleDec(Short numeCapitaleDec) {
		this.numeCapitaleDec = numeCapitaleDec;
	}
	public String getDescCivico() {
		return descCivico;
	}
	public void setDescCivico(String descCivico) {
		this.descCivico = descCivico;
	}
	public String getDescLotto() {
		return descLotto;
	}
	public void setDescLotto(String descLotto) {
		this.descLotto = descLotto;
	}
	public String getDescEdificio() {
		return descEdificio;
	}
	public void setDescEdificio(String descEdificio) {
		this.descEdificio = descEdificio;
	}
	public String getDescScala() {
		return descScala;
	}
	public void setDescScala(String descScala) {
		this.descScala = descScala;
	}
	public String getDescInterno() {
		return descInterno;
	}
	public void setDescInterno(String descInterno) {
		this.descInterno = descInterno;
	}
	public String getDescPiano() {
		return descPiano;
	}
	public void setDescPiano(String descPiano) {
		this.descPiano = descPiano;
	}
	public Character getFlgErr() {
		return flgErr;
	}
	public void setFlgErr(Character flgErr) {
		this.flgErr = flgErr;
	}
	public String getDescUtente() {
		return descUtente;
	}
	public void setDescUtente(String descUtente) {
		this.descUtente = descUtente;
	}
	public Date getDataUltAgg() {
		return dataUltAgg;
	}
	public void setDataUltAgg(Date dataUltAgg) {
		this.dataUltAgg = dataUltAgg;
	}
	public String getDescComuneBene() {
		return descComuneBene;
	}
	public void setDescComuneBene(String descComuneBene) {
		this.descComuneBene = descComuneBene;
	}
	public String getCodiceCatastale1() {
		return codiceCatastale1;
	}
	public void setCodiceCatastale1(String codiceCatastale1) {
		this.codiceCatastale1 = codiceCatastale1;
	}
	public String getCodiceCatastale2() {
		return codiceCatastale2;
	}
	public void setCodiceCatastale2(String codiceCatastale2) {
		this.codiceCatastale2 = codiceCatastale2;
	}
	public Character getTipoCatasto() {
		return tipoCatasto;
	}
	public void setTipoCatasto(Character tipoCatasto) {
		this.tipoCatasto = tipoCatasto;
	}
	public Character getFondo() {
		return fondo;
	}
	public void setFondo(Character fondo) {
		this.fondo = fondo;
	}
	public String getDescrNaturaImmobile() {
		return descrNaturaImmobile;
	}
	public void setDescrNaturaImmobile(String descrNaturaImmobile) {
		this.descrNaturaImmobile = descrNaturaImmobile;
	}
	public Integer getIdNaturaImmobile() {
		return idNaturaImmobile;
	}
	public void setIdNaturaImmobile(Integer idNaturaImmobile) {
		this.idNaturaImmobile = idNaturaImmobile;
	}
	public IndirizzoForm getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(IndirizzoForm indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getCodiceCatastale1P() {
		return codiceCatastale1P;
	}
	public void setCodiceCatastale1P(String codiceCatastale1P) {
		this.codiceCatastale1P = codiceCatastale1P;
	}
	public String getCodiceCatastale2P() {
		return codiceCatastale2P;
	}
	public void setCodiceCatastale2P(String codiceCatastale2P) {
		this.codiceCatastale2P = codiceCatastale2P;
	}
	public Character getTipoCatastoP() {
		return tipoCatastoP;
	}
	public void setTipoCatastoP(Character tipoCatastoP) {
		this.tipoCatastoP = tipoCatastoP;
	}
	public ComboForm getComuneCatastaleP() {
		return comuneCatastaleP;
	}
	public void setComuneCatastaleP(ComboForm comuneCatastaleP) {
		this.comuneCatastaleP = comuneCatastaleP;
	}
	public ComboForm getComune() {
		return comune;
	}
	public void setComune(ComboForm comune) {
		this.comune = comune;
	}
	public String getBeneAssociabileSelected() {
		return beneAssociabileSelected;
	}
	public void setBeneAssociabileSelected(String beneAssociabileSelected) {
		this.beneAssociabileSelected = beneAssociabileSelected;
	}
	public String getDescTipoCatasto() {
		return descTipoCatasto;
	}
	public void setDescTipoCatasto(String descTipoCatasto) {
		this.descTipoCatasto = descTipoCatasto;
	}
	public String getDescrFondo() {
		return descrFondo;
	}
	public void setDescrFondo(String descrFondo) {
		this.descrFondo = descrFondo;
	}
	public ComboForm getCategoriaCatastale() {
		return categoriaCatastale;
	}
	public void setCategoriaCatastale(ComboForm categoriaCatastale) {
		this.categoriaCatastale = categoriaCatastale;
	}
	public Long getBeneSitMp() {
		return beneSitMp;
	}
	public void setBeneSitMp(Long beneSitMp) {
		this.beneSitMp = beneSitMp;
	}
	public boolean isDisabledCampi() {
		return disabledCampi;
	}
	public void setDisabledCampi(boolean disabledCampi) {
		this.disabledCampi = disabledCampi;
	}
	public boolean isDisabledCampiT() {
		return disabledCampiT;
	}
	public void setDisabledCampiT(boolean disabledCampiT) {
		this.disabledCampiT = disabledCampiT;
	}
	public Character getTipoDenuncia() {
		return tipoDenuncia;
	}
	public void setTipoDenuncia(Character tipoDenuncia) {
		this.tipoDenuncia = tipoDenuncia;
	}
	public Character getTipoDenunciaP() {
		return tipoDenunciaP;
	}
	public void setTipoDenunciaP(Character tipoDenunciaP) {
		this.tipoDenunciaP = tipoDenunciaP;
	}
	public String getDescTipoDenuncia() {
		return descTipoDenuncia;
	}
	public void setDescTipoDenuncia(String descTipoDenuncia) {
		this.descTipoDenuncia = descTipoDenuncia;
	}
	public String getDescTipoDenunciaP() {
		return descTipoDenunciaP;
	}
	public void setDescTipoDenunciaP(String descTipoDenunciaP) {
		this.descTipoDenunciaP = descTipoDenunciaP;
	}
	public boolean isIdentificazioneCatastale() {
		return identificazioneCatastale;
	}
	public void setIdentificazioneCatastale(boolean identificazioneCatastale) {
		this.identificazioneCatastale = identificazioneCatastale;
	}
	public boolean isDenunciaAccatastamento() {
		return denunciaAccatastamento;
	}
	public void setDenunciaAccatastamento(boolean denunciaAccatastamento) {
		this.denunciaAccatastamento = denunciaAccatastamento;
	}
	public boolean isIdentificazioneCatastaleP() {
		return identificazioneCatastaleP;
	}
	public void setIdentificazioneCatastaleP(boolean identificazioneCatastaleP) {
		this.identificazioneCatastaleP = identificazioneCatastaleP;
	}
	public boolean isDenunciaAccatastamentoP() {
		return denunciaAccatastamentoP;
	}
	public void setDenunciaAccatastamentoP(boolean denunciaAccatastamentoP) {
		this.denunciaAccatastamentoP = denunciaAccatastamentoP;
	}
	public boolean isIdentificazioneCatastaleChecked() {
		return identificazioneCatastaleChecked;
	}
	public void setIdentificazioneCatastaleChecked(
			boolean identificazioneCatastaleChecked) {
		this.identificazioneCatastaleChecked = identificazioneCatastaleChecked;
	}
	public boolean isDenunciaAccatastamentoChecked() {
		return denunciaAccatastamentoChecked;
	}
	public void setDenunciaAccatastamentoChecked(
			boolean denunciaAccatastamentoChecked) {
		this.denunciaAccatastamentoChecked = denunciaAccatastamentoChecked;
	}
	public List<ComuneProvinciaDto> getListaComuniConservatoria() {
		return listaComuniConservatoria;
	}
	public void setListaComuniConservatoria(
			List<ComuneProvinciaDto> listaComuniConservatoria) {
		this.listaComuniConservatoria = listaComuniConservatoria;
	}
	public String getComuneConservatoria() {
		return comuneConservatoria;
	}
	public void setComuneConservatoria(String comuneConservatoria) {
		this.comuneConservatoria = comuneConservatoria;
	}
	public boolean isDisabledCampiIdentificazione() {
		return disabledCampiIdentificazione;
	}
	public void setDisabledCampiIdentificazione(boolean disabledCampiIdentificazione) {
		this.disabledCampiIdentificazione = disabledCampiIdentificazione;
	}
	public boolean isDisabledCampiDenuncia() {
		return disabledCampiDenuncia;
	}
	public void setDisabledCampiDenuncia(boolean disabledCampiDenuncia) {
		this.disabledCampiDenuncia = disabledCampiDenuncia;
	}
	public boolean isDisabledCampiIdentificazioneP() {
		return disabledCampiIdentificazioneP;
	}
	public void setDisabledCampiIdentificazioneP(
			boolean disabledCampiIdentificazioneP) {
		this.disabledCampiIdentificazioneP = disabledCampiIdentificazioneP;
	}
	public boolean isDisabledCampiDenunciaP() {
		return disabledCampiDenunciaP;
	}
	public void setDisabledCampiDenunciaP(boolean disabledCampiDenunciaP) {
		this.disabledCampiDenunciaP = disabledCampiDenunciaP;
	}
	public List<TitoloDirittoDto> getListaTitoliDiritto() {
		return listaTitoliDiritto;
	}
	public void setListaTitoliDiritto(List<TitoloDirittoDto> listaTitoliDiritto) {
		this.listaTitoliDiritto = listaTitoliDiritto;
	}
	public String getDescBene() {
		return descBene;
	}
	public void setDescBene(String descBene) {
		this.descBene = descBene;
	}
	public int getIdTipoInt() {
		return idTipoInt;
	}
	public void setIdTipoInt(int idTipoInt) {
		this.idTipoInt = idTipoInt;
	}
	public String getDescTipoInt() {
		return descTipoInt;
	}
	public void setDescTipoInt(String descTipoInt) {
		this.descTipoInt = descTipoInt;
	}
	public ComboForm getListaTipiInteresse() {
		return listaTipiInteresse;
	}
	public void setListaTipiInteresse(ComboForm listaTipiInteresse) {
		this.listaTipiInteresse = listaTipiInteresse;
	}
	public String getSoggettoSelected() {
		return soggettoSelected;
	}
	public void setSoggettoSelected(String soggettoSelected) {
		this.soggettoSelected = soggettoSelected;
	}
	public SoggettoInteressatoDescDto getSoggettoDto() {
		return soggettoDto;
	}
	public void setSoggettoDto(SoggettoInteressatoDescDto soggettoDto) {
		this.soggettoDto = soggettoDto;
	}
	public List<SoggettoInteressatoDescDto> getListaSoggetti() {
		return listaSoggetti;
	}
	public void setListaSoggetti(List<SoggettoInteressatoDescDto> listaSoggetti) {
		this.listaSoggetti = listaSoggetti;
	}
	public String getDescComuneBeneP() {
		return descComuneBeneP;
	}
	public void setDescComuneBeneP(String descComuneBeneP) {
		this.descComuneBeneP = descComuneBeneP;
	}
	public String getCodiceCatastale1POld() {
		return codiceCatastale1POld;
	}
	public void setCodiceCatastale1POld(String codiceCatastale1POld) {
		this.codiceCatastale1POld = codiceCatastale1POld;
	}
}
