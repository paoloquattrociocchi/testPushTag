package it.giustizia.smp.form;

import java.sql.Blob;

import org.springframework.web.multipart.commons.CommonsMultipartFile;


public class AllegatoForm {
    
    private Long idDocumento;
    private Long idAnagrafica;
    // id del documento corrente presente sulla piattaforma documentale
    private Long idDocumentale;
    private ComboForm tipoDocumento;
    private String fileName;
    private String contentType;
    private Blob documento;
    
	private CommonsMultipartFile fileData;
    private FirmaForm firmaRemota;
    private String documentoFirmato;
    private boolean allegatoRimosso;
    
    /**
     * @return the fileName
     */
    public String getFileName() {
    
        return fileName;
    }
    
    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
    
        this.fileName = fileName;
    }   
    
    
    /**
     * @return the contentType
     */
    public String getContentType() {
    
        return contentType;
    }

    
    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
    
        this.contentType = contentType;
    }

    
    /**
     * @return the fileData
     */
    public CommonsMultipartFile getFileData() {
    
        return fileData;
    }

    
    /**
     * @param fileData the fileData to set
     */
    public void setFileData(CommonsMultipartFile fileData) {
    
        this.fileData = fileData;
    }

    
    /**
     * @return the idDocumento
     */
    public Long getIdDocumento() {
    
        return idDocumento;
    }

    
    /**
     * @param idDocumento the idDocumento to set
     */
    public void setIdDocumento(Long idDocumento) {
    
        this.idDocumento = idDocumento;
    }

    
    /**
     * @return the tipoDocumento
     */
    public ComboForm getTipoDocumento() {
    
        return tipoDocumento;
    }

    
    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(ComboForm tipoDocumento) {
    
        this.tipoDocumento = tipoDocumento;
    }

    
    /**
     * @return the idAnagrafica
     */
    public Long getIdAnagrafica() {
    
        return idAnagrafica;
    }

    
    /**
     * @param idAnagrafica the idAnagrafica to set
     */
    public void setIdAnagrafica(Long idAnagrafica) {
    
        this.idAnagrafica = idAnagrafica;
    }

    
    /**
     * @return the firmaRemota
     */
    public FirmaForm getFirmaRemota() {
    
        return firmaRemota;
    }

    
    /**
     * @param firmaRemota the firmaRemota to set
     */
    public void setFirmaRemota(FirmaForm firmaRemota) {
    
        this.firmaRemota = firmaRemota;
    }
    
    /**
     * @return the documentoFirmato
     */
    public String getDocumentoFirmato() {
    
        return documentoFirmato;
    }
    
    /**
     * @param documentoFirmato the documentoFirmato to set
     */
    public void setDocumentoFirmato(String documentoFirmato) {
    
        this.documentoFirmato = documentoFirmato;
    }

    
    /**
     * @return the idDocumentale
     */
    public Long getIdDocumentale() {
    
        return idDocumentale;
    }

    
    /**
     * @param idDocumentale the idDocumentale to set
     */
    public void setIdDocumentale(Long idDocumentale) {
    
        this.idDocumentale = idDocumentale;
    }

	public boolean isAllegatoRimosso() {
		return allegatoRimosso;
	}

	public void setAllegatoRimosso(boolean allegatoRimosso) {
		this.allegatoRimosso = allegatoRimosso;
	} 

	
    public Blob getDocumento() {
		return documento;
	}

	public void setDocumento(Blob documento) {
		this.documento = documento;
	}
    
}
