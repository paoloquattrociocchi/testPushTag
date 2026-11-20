package it.giustizia.smp.form;

import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class ImpugnazioneForm {

	
	private ComboForm     tipoDeposito;
	private ComboForm     istitutoDiDetenzione;
	private ComboForm     altriUffici;
	private ComboForm     direttiInteressati;
	private ComboForm     soggettiTitolari;
	private ComboForm     avvocatiDifensori;
	private ComboForm     soggettiDifensori;
	private AmministratoreForm amministratore;


	private List<String>     idsDirettiInteressati;
	private List<String>     idsSoggettiTitolari;
	private List<String>     idsSoggettiDifensori;	
	

	@ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date          dataImpugnazione;
	private String        orarioImpugnazione;
	@ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date          dataRegistrazione;
	


	private String        orarioRegistrazione;
	
	private ComboForm     tipoModalitaPervenimento;
    private String        modalitaPervenimentoDesc; 
    
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date          dataSpedizione;
    private String        orarioSpedizione;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date          dataPervenimento;
    private String        orarioPervenimento;
    private String        descTipoProp;
    
    private ComboForm parte;
    private String nome;
    private String cognome;
    
    private String descProvPosta;
    
    private String descNomeAvv;
    private String descCognAvv;
    
    private long idSoggInv;
    
    
	public String getOrarioImpugnazione() {
		return orarioImpugnazione;
	}

	public void setOrarioImpugnazione(String orarioImpugnazione) {
		this.orarioImpugnazione = orarioImpugnazione;
	}

	public String getOrarioRegistrazione() {
		return orarioRegistrazione;
	}

	public void setOrarioRegistrazione(String orarioRegistrazione) {
		this.orarioRegistrazione = orarioRegistrazione;
	}
	public ComboForm getTipoDeposito() {
		return tipoDeposito;
	}

	public void setTipoDeposito(ComboForm tipoDeposito) {
		this.tipoDeposito = tipoDeposito;
	}

	

	public ComboForm getIstitutoDiDetenzione() {
		return istitutoDiDetenzione;
	}

	public void setIstitutoDiDetenzione(ComboForm istitutoDiDetenzione) {
		this.istitutoDiDetenzione = istitutoDiDetenzione;
	}

	public Date getDataImpugnazione() {
		return dataImpugnazione;
	}

	public void setDataImpugnazione(Date dataImpugnazione) {
		this.dataImpugnazione = dataImpugnazione;
	}

	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public ComboForm getTipoModalitaPervenimento() {
		return tipoModalitaPervenimento;
	}

	public void setTipoModalitaPervenimento(ComboForm tipoModalitaPervenimento) {
		this.tipoModalitaPervenimento = tipoModalitaPervenimento;
	}

	public String getModalitaPervenimentoDesc() {
		return modalitaPervenimentoDesc;
	}

	public void setModalitaPervenimentoDesc(String modalitaPervenimentoDesc) {
		this.modalitaPervenimentoDesc = modalitaPervenimentoDesc;
	}

	public Date getDataSpedizione() {
		return dataSpedizione;
	}

	public void setDataSpedizione(Date dataSpedizione) {
		this.dataSpedizione = dataSpedizione;
	}

	public Date getDataPervenimento() {
		return dataPervenimento;
	}

	public void setDataPervenimento(Date dataPervenimento) {
		this.dataPervenimento = dataPervenimento;
	}

	public String getOrarioSpedizione() {
		return orarioSpedizione;
	}

	public void setOrarioSpedizione(String orarioSpedizione) {
		this.orarioSpedizione = orarioSpedizione;
	}

	public String getOrarioPervenimento() {
		return orarioPervenimento;
	}

	public void setOrarioPervenimento(String orarioPervenimento) {
		this.orarioPervenimento = orarioPervenimento;
	}

	public ComboForm getParte() {
		return parte;
	}

	public void setParte(ComboForm parte) {
		this.parte = parte;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public ComboForm getDirettiInteressati() {
		return direttiInteressati;
	}

	public void setDirettiInteressati(ComboForm direttiInteressati) {
		this.direttiInteressati = direttiInteressati;
	}

	public ComboForm getSoggettiTitolari() {
		return soggettiTitolari;
	}

	public void setSoggettiTitolari(ComboForm soggettiTitolari) {
		this.soggettiTitolari = soggettiTitolari;
	} 
   
	public ComboForm getAvvocatiDifensori() {
		return avvocatiDifensori;
	}

	public void setAvvocatiDifensori(ComboForm avvocatiDifensori) {
		this.avvocatiDifensori = avvocatiDifensori;
	}

	public ComboForm getSoggettiDifensori() {
		return soggettiDifensori;
	}

	public void setSoggettiDifensori(ComboForm soggettiDifensori) {
		this.soggettiDifensori = soggettiDifensori;
	}

	public ComboForm getAltriUffici() {
		return altriUffici;
	}

	public void setAltriUffici(ComboForm altriUffici) {
		this.altriUffici = altriUffici;
	}

	public String getDescTipoProp() {
		return descTipoProp;
	}

	public void setDescTipoProp(String descTipoProp) {
		this.descTipoProp = descTipoProp;
	}

	public String getDescProvPosta() {
		return descProvPosta;
	}

	public void setDescProvPosta(String descProvPosta) {
		this.descProvPosta = descProvPosta;
	}

	public String getDescNomeAvv() {
		return descNomeAvv;
	}

	public void setDescNomeAvv(String descNomeAvv) {
		this.descNomeAvv = descNomeAvv;
	}

	public String getDescCognAvv() {
		return descCognAvv;
	}

	public void setDescCognAvv(String descCognAvv) {
		this.descCognAvv = descCognAvv;
	}

	public List<String> getIdsDirettiInteressati() {
		return idsDirettiInteressati;
	}

	public void setIdsDirettiInteressati(List<String> idsDirettiInteressati) {
		this.idsDirettiInteressati = idsDirettiInteressati;
	}

	public List<String> getIdsSoggettiTitolari() {
		return idsSoggettiTitolari;
	}

	public void setIdsSoggettiTitolari(List<String> idsSoggettiTitolari) {
		this.idsSoggettiTitolari = idsSoggettiTitolari;
	}

	public List<String> getIdsSoggettiDifensori() {
		return idsSoggettiDifensori;
	}

	public void setIdsSoggettiDifensori(List<String> idsSoggettiDifensori) {
		this.idsSoggettiDifensori = idsSoggettiDifensori;
	}

	public AmministratoreForm getAmministratore() {
		return amministratore;
	}

	public void setAmministratore(AmministratoreForm amministratore) {
		this.amministratore = amministratore;
	}

	public long getIdSoggInv() {
		return idSoggInv;
	}

	public void setIdSoggInv(long idSoggInv) {
		this.idSoggInv = idSoggInv;
	}


   
}
