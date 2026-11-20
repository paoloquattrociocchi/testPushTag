package it.giustizia.smp.form.conservatorie;

import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.validation.date.ModernDate;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ComunicazioneRipropAnnotaForm extends
	NotaDettaglioForm {
	
	private Long    numeroComunicazione; //id		
	private String  tipoNota;   		// in inserimento è una combo
	private String  categorieAtti; 		// in inserimento è una combo
	private Long   	repertorioAnno;     //numeRepert2	
	private Long  	numero;             //numeRepert1
	private Long    progressivo;        //numeProgrNota

	// Parametro gestione visualizzazzione Annotazione o Riproposizione
	private String  operazione;			// A o R

	//Dati Generali
		private ComboForm conservatoriaCombo; //corrisponde a descConserv nella classe TAnConservatorie
		private String    conservatoriaSelez; // Conservatoria selezionata
		private String    descrConservatoria; // descrizione Conservatoria selezionata
		private String    codiceFiscale;      //descFiscalePu
		private String    titoloDescrizione;  //descTitolo
		@ModernDate
		@DateTimeFormat( pattern = "dd/MM/yyyy" )
		private Date      dataTitolo;         //dataTitolo			 	
		private String    pubblicoUfficiale;  //descPubblUff
		private Character categoria;     	  //in inserimento è Char descTipoPubblUff 
		private ComboForm comune;             //descSedePubblUff
		private String    sede;  			  //sede selezionata
		private String    provincia;          //provincia
		private String    attoSpecie;         //in inserimento è una combo
		private String    descrizione;		  // descrizione
		private ComboForm rettifica;          //campo digitabile solo per le comunicazioni di tipo trascrizione
		
		private String    labelSezAnn ="";
		
	//Dati relativi alla trascrizione 
		@DateTimeFormat( pattern = "dd/MM/yyyy" )
		private Date      dataDal;
		private boolean   presenzaTermini;
		@DateTimeFormat( pattern = "dd/MM/yyyy" )
		private Date      dataFormalita;
		
		private String    volturaCatastaleTrascrizione;

		private String    quadroATrascrizione;
		private String    quadroBTrascrizione;
		private String    quadroCTrascrizione;


		
	//Dati relativi all'annotazione 
		private String    volturaCatastaleAnnotazione;
		private String    formalita;


		public ComboForm getConservatoriaCombo() {
			return conservatoriaCombo;
		}
		public void setConservatoriaCombo(ComboForm conservatoriaCombo) {
			this.conservatoriaCombo = conservatoriaCombo;
		}

		public String getConservatoriaSelez() {
			return conservatoriaSelez;
		}
		public void setConservatoriaSelez(String conservatoriaSelez) {
			this.conservatoriaSelez = conservatoriaSelez;
		}
		public String getDescrConservatoria() {
			return descrConservatoria;
		}
		public void setDescrConservatoria(String descrConservatoria) {
			this.descrConservatoria = descrConservatoria;
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
		public Date getDataTitolo() {
			return dataTitolo;
		}
		public void setDataTitolo(Date dataTitolo) {
			this.dataTitolo = dataTitolo;
		}
		public String getPubblicoUfficiale() {
			return pubblicoUfficiale;
		}
		public void setPubblicoUfficiale(String pubblicoUfficiale) {
			this.pubblicoUfficiale = pubblicoUfficiale;
		}
		public Character getCategoria() {
			return categoria;
		}
		public void setCategoria(Character categoria) {
			this.categoria = categoria;
		}
		public ComboForm getComune() {
			return comune;
		}
		public void setComune(ComboForm comune) {
			this.comune = comune;
		}
		public String getSede() {
			return sede;
		}
		public void setSede(String sede) {
			this.sede = sede;
		}
		public String getProvincia() {
			return provincia;
		}
		public void setProvincia(String provincia) {
			this.provincia = provincia;
		}
		public String getAttoSpecie() {
			return attoSpecie;
		}
		public void setAttoSpecie(String attoSpecie) {
			this.attoSpecie = attoSpecie;
		}
		public ComboForm getRettifica() {
			return rettifica;
		}
		public void setRettifica(ComboForm rettifica) {
			this.rettifica = rettifica;
		}
		public String getLabelSezAnn() {
			return labelSezAnn;
		}
		public void setLabelSezAnn(String labelSezAnn) {
			this.labelSezAnn = labelSezAnn;
		}
		public Date getDataDal() {
			return dataDal;
		}
		public void setDataDal(Date dataDal) {
			this.dataDal = dataDal;
		}
		public boolean isPresenzaTermini() {
			return presenzaTermini;
		}
		public void setPresenzaTermini(boolean presenzaTermini) {
			this.presenzaTermini = presenzaTermini;
		}
		public Date getDataFormalita() {
			return dataFormalita;
		}
		public void setDataFormalita(Date dataFormalita) {
			this.dataFormalita = dataFormalita;
		}
		public String getFormalita() {
			return formalita;
		}
		public void setFormalita(String formalita) {
			this.formalita = formalita;
		}
		public Long getNumeroComunicazione() {
			return numeroComunicazione;
		}
		public void setNumeroComunicazione(Long numeroComunicazione) {
			this.numeroComunicazione = numeroComunicazione;
		}
		public String getTipoNota() {
			return tipoNota;
		}
		public void setTipoNota(String tipoNota) {
			this.tipoNota = tipoNota;
		}
		public String getCategorieAtti() {
			return categorieAtti;
		}
		public void setCategorieAtti(String categorieAtti) {
			this.categorieAtti = categorieAtti;
		}
		public Long getRepertorioAnno() {
			return repertorioAnno;
		}
		public void setRepertorioAnno(Long repertorioAnno) {
			this.repertorioAnno = repertorioAnno;
		}
		public Long getNumero() {
			return numero;
		}
		public void setNumero(Long numero) {
			this.numero = numero;
		}
		public Long getProgressivo() {
			return progressivo;
		}
		public void setProgressivo(Long progressivo) {
			this.progressivo = progressivo;
		}
		public String getDescrizione() {
			return descrizione;
		}
		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}
		public String getVolturaCatastaleAnnotazione() {
			return volturaCatastaleAnnotazione;
		}
		public void setVolturaCatastaleAnnotazione(String volturaCatastaleAnnotazione) {
			this.volturaCatastaleAnnotazione = volturaCatastaleAnnotazione;
		}
		public String getOperazione() {
			return operazione;
		}
		public void setOperazione(String operazione) {
			this.operazione = operazione;
		}
		public String getVolturaCatastaleTrascrizione() {
			return volturaCatastaleTrascrizione;
		}
		public void setVolturaCatastaleTrascrizione(String volturaCatastaleTrascrizione) {
			this.volturaCatastaleTrascrizione = volturaCatastaleTrascrizione;
		}
		public String getQuadroATrascrizione() {
			return quadroATrascrizione;
		}
		public void setQuadroATrascrizione(String quadroATrascrizione) {
			this.quadroATrascrizione = quadroATrascrizione;
		}
		public String getQuadroBTrascrizione() {
			return quadroBTrascrizione;
		}
		public void setQuadroBTrascrizione(String quadroBTrascrizione) {
			this.quadroBTrascrizione = quadroBTrascrizione;
		}
		public String getQuadroCTrascrizione() {
			return quadroCTrascrizione;
		}
		public void setQuadroCTrascrizione(String quadroCTrascrizione) {
			this.quadroCTrascrizione = quadroCTrascrizione;
		}
		
}
