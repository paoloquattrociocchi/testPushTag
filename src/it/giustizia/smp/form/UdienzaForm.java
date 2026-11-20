package it.giustizia.smp.form;

import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.validation.date.ModernDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.AutoPopulatingList;

public class UdienzaForm extends AttoForm {

    private List<String>       allFlgPresDif              = new AutoPopulatingList<String>(String.class);
    private List<String>       allFlgPresSogConv          = new AutoPopulatingList<String>(String.class);
    private String             altriConv;
    private String             aulaUdienza;
    private String             aulaUdienzaRinvio;
    // DATI DELL'UDIENZA
    private long               codUdienza;
    private String             cognomeCancelliere;
    // secondo il formato data udienza alle ore ora di inizio udienza
    private String             dataOraUdienza;
    // DATI RELATIVI AL RINVIO UDIENZA
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date               dataRinvio;
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date               dataUdienza;
    private String             dataUdienzaStr;
    private ComboForm          esito;
    private boolean            flagAssociaMag;
    // DATI RELATIVI ALLA GESTIONE DI COLLEGIO, PM, SOGGETTI CONVOCATI E
    // DIFENSORI
    private boolean            flagModMag;
    private boolean            flagNuovoMag;
    private String             indirizzoAulaUdienza;
    private String             indirizzoAulaUdienzaRinvio;
    private List<SoggettoForm> listaDifensoriForm         = new ArrayList<SoggettoForm>();
    private ComboForm          listaPm;
    // private ComboForm listaMag;
    private List<SoggettoForm> listaSoggettiConvocatiForm = new ArrayList<SoggettoForm>();
    private ComboForm          minutiFineUdienza;
    private ComboForm          minutiInizioUdienza;
    private ComboForm          minutiUdienzaRinvio;
    private String             nomeCancelliere;
    private String             note;
    private ComboForm          oraFineUdienza;
    // rappresenta l'ora di fine
    // secondo il formato oraFineUdienza:minutiFineUdienza
    private String             oraFineUdienzaStr;
    private ComboForm          oraInizioUdienza;
    // rappresenta l'ora di inizio
    // secondo il formato oraInizioUdienza:minutiInizioUdienza
    private String             oraInizioUdienzaStr;
    private ComboForm          oraUdienzaRinvio;
    // rappresenta l'ora di inizio secondo il formato
    // oraInizioUdienza:minutiInizioUdienza
    private String             oraUdienzaRinvioStr;
    private MagistratoForm     pmForm;
    private boolean            readOnly;           
    private ComboForm          stato;
    
    private UdienzaForm		   udienzaPrecedente;

    public UdienzaForm() {

        super();
    }

    public List<String> getAllFlgPresDif() {

        return allFlgPresDif;
    }

    public List<String> getAllFlgPresSogConv() {

        return allFlgPresSogConv;
    }

    public String getAltriConv() {

        return altriConv;
    }

    public String getAulaUdienza() {

        return aulaUdienza;
    }

    public String getAulaUdienzaRinvio() {

        return aulaUdienzaRinvio;
    }

    public long getCodUdienza() {

        return codUdienza;
    }

    public String getCognomeCancelliere() {

        return cognomeCancelliere;
    }

    
      public void setOraFineUdienzaStr(String oraFineUdienzaStr) {
    	  this.oraFineUdienzaStr = oraFineUdienzaStr;
      }
     
    public String getDataOraUdienza() {

        return Utility.convertDateToString(dataUdienza) + " alle ore " + getOraInizioUdienzaStr();
    }

    public Date getDataRinvio() {

        return dataRinvio;
    }

    public Date getDataUdienza() {

        return dataUdienza;
    }

    public String getDataUdienzaStr() {

        return dataUdienzaStr;
    }

    public ComboForm getEsito() {

        return esito;
    }

    /*
     * public ComboForm getListaMag() {
     * return listaMag;
     * }
     * public void setListaMag(ComboForm listaMag) {
     * this.listaMag = listaMag;
     * }
     */
    public String getIndirizzoAulaUdienza() {

        return indirizzoAulaUdienza;
    }

    public String getIndirizzoAulaUdienzaRinvio() {

        return indirizzoAulaUdienzaRinvio;
    }

    public List<SoggettoForm> getListaDifensoriForm() {

        return listaDifensoriForm;
    }

    public ComboForm getListaPm() {

        return listaPm;
    }

    public List<SoggettoForm> getListaSoggettiConvocatiForm() {

        return listaSoggettiConvocatiForm;
    }

    public ComboForm getMinutiFineUdienza() {

        return minutiFineUdienza;
    }

    public ComboForm getMinutiInizioUdienza() {

        return minutiInizioUdienza;
    }

    public ComboForm getMinutiUdienzaRinvio() {

        return minutiUdienzaRinvio;
    }

    public String getNomeCancelliere() {

        return nomeCancelliere;
    }

    public String getNote() {

        return note;
    }

    public ComboForm getOraFineUdienza() {

        /*if(oraFineUdienza!=null)
            return oraFineUdienza;
        else return new ComboForm("-1","00");*/
        return oraFineUdienza;
    }

    public String getOraFineUdienzaStr() {

        String oraFine = "";
        if (oraFineUdienza != null && oraFineUdienza.getId() != null && !oraFineUdienza.getId().equals("-1")) {
            oraFine = oraFineUdienza.getId() + ":" + minutiFineUdienza.getId();
        }else if(oraFineUdienzaStr != null){
        	oraFine = oraFineUdienzaStr;
        }
        return oraFine;
    }

    public ComboForm getOraInizioUdienza() {

        /*if(oraInizioUdienza!=null)
            return oraInizioUdienza;
        else return new ComboForm("-1","00");*/
        return oraInizioUdienza;
    }

    public String getOraInizioUdienzaStr() {

        String oraInizio = "";
        if(oraInizioUdienza!=null && oraInizioUdienza !=  null && oraInizioUdienza.getId() != null && !oraInizioUdienza.getId().equals("-1")){
                oraInizio = oraInizioUdienza.getId() + ":" + minutiInizioUdienza.getId();
          
        }else if(oraInizioUdienzaStr != null){
        	oraInizio = oraInizioUdienzaStr;
        }
        	
        return oraInizio;
        //return oraInizioUdienza.getId() + ":" + minutiInizioUdienza.getId();
    }

    public ComboForm getOraUdienzaRinvio() {

        return oraUdienzaRinvio;
    }

    public String getOraUdienzaRinvioStr() {

        String oraUdienza = "";
        if (!oraUdienzaRinvio.getId().equals("-1")) {
            oraUdienza = oraUdienzaRinvio.getId() + ":" + minutiUdienzaRinvio.getId();
        }
        return oraUdienza;
    }

    public MagistratoForm getPmForm() {

        return pmForm;
    }

    
    /**
     * @return the readOnly
     */
    public boolean isReadOnly() {
    
        return readOnly;
    }

    public ComboForm getStato() {

        return stato;
    }

    /**
	 * @return the udienzaPrecedente
	 */
	public UdienzaForm getUdienzaPrecedente() {
		return udienzaPrecedente;
	}

	public boolean isFlagAssociaMag() {

        return flagAssociaMag;
    }

    public boolean isFlagModMag() {

        return flagModMag;
    }

    public boolean isFlagNuovoMag() {

        return flagNuovoMag;
    }

    public void setAllFlgPresDif(List<String> allFlgPresDif) {

        this.allFlgPresDif = allFlgPresDif;
    }

    public void setAllFlgPresSogConv(List<String> allFlgPresSogConv) {

        this.allFlgPresSogConv = allFlgPresSogConv;
    }

    public void setAltriConv(String altriConv) {

        this.altriConv = altriConv;
    }

    public void setAulaUdienza(String aulaUdienza) {

        this.aulaUdienza = aulaUdienza;
    }

    public void setAulaUdienzaRinvio(String aulaUdienzaRinvio) {

        this.aulaUdienzaRinvio = aulaUdienzaRinvio;
    }

    public void setCodUdienza(long codUdienza) {

        this.codUdienza = codUdienza;
    }

    public void setCognomeCancelliere(String cognomeCancelliere) {

        this.cognomeCancelliere = cognomeCancelliere;
    }

    public void setDataRinvio(Date dataRinvio) {

        this.dataRinvio = dataRinvio;
    }

    public void setDataUdienza(Date dataUdienza) {

        this.dataUdienza = dataUdienza;
    }

    public void setDataUdienzaStr(String dataUdienzaStr) {

        this.dataUdienzaStr = dataUdienzaStr;
    }

    public void setEsito(ComboForm esito) {

        this.esito = esito;
    }

    public void setFlagAssociaMag(boolean flagAssociaMag) {

        this.flagAssociaMag = flagAssociaMag;
    }

    public void setFlagModMag(boolean flagModMag) {

        this.flagModMag = flagModMag;
    }

    public void setFlagNuovoMag(boolean flagNuovoMag) {

        this.flagNuovoMag = flagNuovoMag;
    }

    public void setIndirizzoAulaUdienza(String indirizzoAulaUdienza) {

        this.indirizzoAulaUdienza = indirizzoAulaUdienza;
    }

    public void setIndirizzoAulaUdienzaRinvio(String indirizzoAulaUdienzaRinvio) {

        this.indirizzoAulaUdienzaRinvio = indirizzoAulaUdienzaRinvio;
    }

    public void setListaDifensoriForm(List<SoggettoForm> listaDifensoriForm) {

        this.listaDifensoriForm = listaDifensoriForm;
    }

    public void setListaPm(ComboForm listaPm) {

        this.listaPm = listaPm;
    }

    public void setListaSoggettiConvocatiForm(List<SoggettoForm> listaSoggettiConvocatiForm) {

        this.listaSoggettiConvocatiForm = listaSoggettiConvocatiForm;
    }

    public void setMinutiFineUdienza(ComboForm minutiFineUdienza) {

        this.minutiFineUdienza = minutiFineUdienza;
    }

    public void setMinutiInizioUdienza(ComboForm minutiInizioUdienza) {

        this.minutiInizioUdienza = minutiInizioUdienza;
    }

    public void setMinutiUdienzaRinvio(ComboForm minutiUdienzaRinvio) {

        this.minutiUdienzaRinvio = minutiUdienzaRinvio;
    }

    public void setNomeCancelliere(String nomeCancelliere) {

        this.nomeCancelliere = nomeCancelliere;
    }

    public void setNote(String note) {

        this.note = note;
    }

    public void setOraFineUdienza(ComboForm oraFineUdienza) {

        this.oraFineUdienza = oraFineUdienza;
    }

    public void setOraInizioUdienza(ComboForm oraInizioUdienza) {

        this.oraInizioUdienza = oraInizioUdienza;
    }

    /**
     * @param oraInizioUdienzaStr
     *            the oraInizioUdienzaStr to set
     */
    public void setOraInizioUdienzaStr(String oraInizioUdienzaStr) {

        this.oraInizioUdienzaStr = oraInizioUdienzaStr;
    }

    public void setOraUdienzaRinvio(ComboForm oraUdienzaRinvio) {

        this.oraUdienzaRinvio = oraUdienzaRinvio;
    }

    public void setOraUdienzaRinvioStr(String oraUdienzaRinvioStr) {

        this.oraUdienzaRinvioStr = oraUdienzaRinvioStr;
    }

    public void setPmForm(MagistratoForm pmForm) {

        this.pmForm = pmForm;
    }

    
    /**
     * @param readOnly the readOnly to set
     */
    public void setReadOnly(boolean readOnly) {
    
        this.readOnly = readOnly;
    }

    public void setStato(ComboForm stato) {

        this.stato = stato;
    }

	/**
	 * @param udienzaPrecedente the udienzaPrecedente to set
	 */
	public void setUdienzaPrecedente(UdienzaForm udienzaPrecedente) {
		this.udienzaPrecedente = udienzaPrecedente;
	}
}
