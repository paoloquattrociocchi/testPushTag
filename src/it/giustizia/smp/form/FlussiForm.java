package it.giustizia.smp.form;

import it.giustizia.smp.dto.flussi.T_ATTODto;
import it.giustizia.smp.dto.flussi.T_DECRETODto;
import it.giustizia.smp.dto.flussi.T_IMPUGNAZIONEDto;
import it.giustizia.smp.dto.flussi.T_ISTANZADto;
import it.giustizia.smp.dto.flussi.T_REL_ATTO_PROCDto;
import it.giustizia.smp.dto.flussi.T_PERSONA_FISICADto;
import it.giustizia.smp.dto.flussi.T_PERSONA_GIURDto;
import it.giustizia.smp.dto.flussi.T_PROCEDIMENTODto;
import it.giustizia.smp.dto.flussi.T_REL_ATTIDto;
import it.giustizia.smp.dto.flussi.T_REL_PROC_PROCDto;
import it.giustizia.smp.dto.flussi.T_REL_UDIENZA_STATO_UDDto;
import it.giustizia.smp.dto.flussi.T_UDIENZADto;
import it.giustizia.smp.validation.date.ModernDate;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * BeneForm
 * 
 * @author Almaviva S.p.A.
 *         classe form per la gestione dei beni del procedimento
 */
public class FlussiForm {

	private T_ATTODto					attoDto;
	private T_ISTANZADto				istanzaDto;
	private T_DECRETODto				decretoDto;
	private T_IMPUGNAZIONEDto		    impugnazioneDto;	
	private T_REL_ATTO_PROCDto			attoProcDto;
	private String						contentType;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date						dataFlusso;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")	
	private Date						dataDeposito;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date						dataRegistrazione;
	private String						descSedeUffMittente;
	private String						descSedeUffMittenteUnita;
	private String						descTipoMessaggio;
	private String						descUffMittente;
	// campo di supporto per il mapping con dozer
//	private Blob						documento;
//	private CommonsMultipartFile		fileData;
	// parte relativa all'upload
//	private String						fileName;
	private Long                        idAccertamento;
	private Long						idFlusso;
	// tipo
	// messaggio
	private Long						idTipoMessaggio;
	private Integer						idUffMittente;
	private String                      numProcDest;
	private String 						numeroSezionale;
	// @Override
	// public int hashCode() {
	//
	// final int prime = 31;
	// int result = 1;
	// result = prime * result + ((idFlusso == null) ? 0 : idFlusso.hashCode());
	// return result;
	// }
	//
	// @Override
	// public boolean equals(Object obj) {
	//
	// if (this == obj) {
	// return true;
	// }
	// if (obj == null) {
	// return false;
	// }
	// if (getClass() != obj.getClass()) {
	// return false;
	// }
	// FlussiForm other = (FlussiForm) obj;
	// if (idFlusso == null) {
	// if (other.idFlusso != null) {
	// return false;
	// }
	// } else if (!idFlusso.equals(other.idFlusso)) {
	// return false;
	// }
	// return true;
	// }
	private String						      include;															// codice
	private List<SoggettoForm>			      listaSoggettoForm			      = new ArrayList<SoggettoForm>();
	private List<T_PERSONA_FISICADto>	      listaT_PERSONA_FISICADto	      = new ArrayList<T_PERSONA_FISICADto>();
	private List<T_PERSONA_GIURDto>		      listaT_PERSONA_GIURDto		  = new ArrayList<T_PERSONA_GIURDto>();
	private List<T_REL_UDIENZA_STATO_UDDto>   listaT_REL_UDIENZA_STATO_UDDto  = new ArrayList<T_REL_UDIENZA_STATO_UDDto>();
    private List<T_UDIENZADto>                listaT_UDIENZADto               = new ArrayList<T_UDIENZADto>();
	private T_PROCEDIMENTODto			      procedimentoDto;
	private T_REL_PROC_PROCDto			      relProcProcDto;
	private T_REL_ATTIDto				      relAttiDto;
	private Short						      annoPadre;
	private Long						      numeroPadre;
	private Long                              idProcedimentoCda;
	private boolean							  accettazioneDeposito  = true;
	
    private AllegatoForm allegato = new AllegatoForm();
	
	/**
	 * @return the istanzaDto
	 */
	public T_ISTANZADto getIstanzaDto() {
		return istanzaDto;
	}

	/**
	 * @param istanzaDto
	 *            the istanzaDto to set
	 */
	public void setIstanzaDto(T_ISTANZADto istanzaDto) {
		this.istanzaDto = istanzaDto;
	}

	/**
	 * @return the attoDto
	 */
	public T_ATTODto getAttoDto() {

		return attoDto;
	}

	/**
	 * @return the attoProcDto
	 */
	public T_REL_ATTO_PROCDto getAttoProcDto() {

		return attoProcDto;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {

		return contentType;
	}

	/**
	 * @return the dataFlusso
	 */
	public Date getDataFlusso() {

		return dataFlusso;
	}

	public Date getDataDeposito() {
		return dataDeposito;
	}

	public void setDataDeposito(Date dataDeposito) {
		this.dataDeposito = dataDeposito;
	}

	/**
	 * @return the dataRegistrazione
	 */
	public Date getDataRegistrazione() {

		return dataRegistrazione;
	}

	/**
	 * @return the descSedeUffMittente
	 */
	public String getDescSedeUffMittente() {

		return descSedeUffMittente;
	}

	/**
	 * @return the descSedeUffMittenteUnita
	 */
	public String getDescSedeUffMittenteUnita() {

		return descSedeUffMittenteUnita;
	}

	/**
	 * @return the descTipoMessaggio
	 */
	public String getDescTipoMessaggio() {

		return descTipoMessaggio;
	}

	/**
	 * @return the descUffMittente
	 */
	public String getDescUffMittente() {

		return descUffMittente;
	}

//	/**
//	 * @return the documento
//	 */
//	public Blob getDocumento() {
//
//		return documento;
//	}
//
//	/**
//	 * @return the fileData
//	 */
//	public CommonsMultipartFile getFileData() {
//
//		return fileData;
//	}
//
//	/**
//	 * @return the fileName
//	 */
//	public String getFileName() {
//
//		return fileName;
//	}

	/**
	 * @return the idFlusso
	 */
	public Long getIdFlusso() {

		return idFlusso;
	}

	/**
	 * @return the idTipoMessaggio
	 */
	public Long getIdTipoMessaggio() {

		return idTipoMessaggio;
	}

	/**
	 * @return the idUffMittente
	 */
	public Integer getIdUffMittente() {

		return idUffMittente;
	}

	public String getNumeroSezionale() {
		return numeroSezionale;
	}

	public void setNumeroSezionale(String numeroSezionale) {
		this.numeroSezionale = numeroSezionale;
	}

	/**
	 * @return the include
	 */
	public String getInclude() {

		return include;
	}

	public String getNumProcDest() {
		return numProcDest;
	}

	public void setNumProcDest(String numProcDest) {
		this.numProcDest = numProcDest;
	}

	/**
	 * @return the listaSoggettoForm
	 */
	public List<SoggettoForm> getListaSoggettoForm() {

		return listaSoggettoForm;
	}

	// //////////////////////////////////////DTO
	// //////////////////////////////////////////////////////
	/**
	 * @return the listaT_PERSONA_FISICADto
	 */
	public List<T_PERSONA_FISICADto> getListaT_PERSONA_FISICADto() {

		return listaT_PERSONA_FISICADto;
	}

	/**
	 * @return the listaT_PERSONA_GIURDto
	 */
	public List<T_PERSONA_GIURDto> getListaT_PERSONA_GIURDto() {

		return listaT_PERSONA_GIURDto;
	}

	
    
    /**
     * @return the listaT_REL_UDIENZA_STATO_UDDto
     */
    public List<T_REL_UDIENZA_STATO_UDDto> getListaT_REL_UDIENZA_STATO_UDDto() {
    
        return listaT_REL_UDIENZA_STATO_UDDto;
    }

    /**
     * @return the listaT_UDIENZADto
     */
    public List<T_UDIENZADto> getListaT_UDIENZADto() {
    
        return listaT_UDIENZADto;
    }

    /**
	 * @return the procedimentoDto
	 */
	public T_PROCEDIMENTODto getProcedimentoDto() {

		return procedimentoDto;
	}

	/**
	 * @param attoDto
	 *            the attoDto to set
	 */
	public void setAttoDto(T_ATTODto attoDto) {

		this.attoDto = attoDto;
	}

	/**
	 * @param attoProcDto
	 *            the attoProcDto to set
	 */
	public void setAttoProcDto(T_REL_ATTO_PROCDto attoProcDto) {

		this.attoProcDto = attoProcDto;
	}

	/**
	 * @param contentType
	 *            the contentType to set
	 */
	public void setContentType(String contentType) {

		this.contentType = contentType;
	}

	/**
	 * @param dataFlusso
	 *            the dataFlusso to set
	 */
	public void setDataFlusso(Date dataFlusso) {

		this.dataFlusso = dataFlusso;
	}

	/**
	 * @param dataRegistrazione
	 *            the dataRegistrazione to set
	 */
	public void setDataRegistrazione(Date dataRegistrazione) {

		this.dataRegistrazione = dataRegistrazione;
	}

	/**
	 * @param descSedeUffMittente
	 *            the descSedeUffMittente to set
	 */
	public void setDescSedeUffMittente(String descSedeUffMittente) {

		this.descSedeUffMittente = descSedeUffMittente;
	}

	/**
	 * @param descSedeUffMittenteUnita
	 *            the descSedeUffMittenteUnita to set
	 */
	public void setDescSedeUffMittenteUnita(String descSedeUffMittenteUnita) {

		this.descSedeUffMittenteUnita = descSedeUffMittenteUnita;
	}

	/**
	 * @param descMessaggio
	 *            the descTipoMessaggio to set
	 */
	public void setDescTipoMessaggio(String descTipoMessaggio) {

		this.descTipoMessaggio = descTipoMessaggio;
	}

	/**
	 * @param descUffMittente
	 *            the descUffMittente to set
	 */
	public void setDescUffMittente(String descUffMittente) {

		this.descUffMittente = descUffMittente;
	}

//	/**
//	 * @param documento
//	 *            the documento to set
//	 */
//	public void setDocumento(Blob documento) {
//
//		this.documento = documento;
//	}
//
//	/**
//	 * @param fileData
//	 *            the fileData to set
//	 */
//	public void setFileData(CommonsMultipartFile fileData) {
//
//		this.fileData = fileData;
//	}
//
//	/**
//	 * @param fileName
//	 *            the fileName to set
//	 */
//	public void setFileName(String fileName) {
//
//		this.fileName = fileName;
//	}

	/**
	 * @param idFlusso
	 *            the idFlusso to set
	 */
	public void setIdFlusso(Long idFlusso) {

		this.idFlusso = idFlusso;
	}

	/**
	 * @param idTipoMessaggio
	 *            the idTipoMessaggio to set
	 */
	public void setIdTipoMessaggio(Long idTipoMessaggio) {

		this.idTipoMessaggio = idTipoMessaggio;
	}

	/**
	 * @param idUffMittente
	 *            the idUffMittente to set
	 */
	public void setIdUffMittente(Integer idUffMittente) {

		this.idUffMittente = idUffMittente;
	}

	/**
	 * @param include
	 *            the include to set
	 */
	public void setInclude(String include) {

		this.include = include;
	}

	/**
	 * @param listaSoggettoForm
	 *            the listaSoggettoForm to set
	 */
	public void setListaSoggettoForm(List<SoggettoForm> listaSoggettoForm) {

		this.listaSoggettoForm = listaSoggettoForm;
	}

	/**
	 * @param listaT_PERSONA_FISICADto
	 *            the listaT_PERSONA_FISICADto to set
	 */
	public void setListaT_PERSONA_FISICADto(List<T_PERSONA_FISICADto> listaT_PERSONA_FISICADto) {

		this.listaT_PERSONA_FISICADto = listaT_PERSONA_FISICADto;
	}

	/**
	 * @param listaT_PERSONA_GIURDto
	 *            the listaT_PERSONA_GIURDto to set
	 */
	public void setListaT_PERSONA_GIURDto(List<T_PERSONA_GIURDto> listaT_PERSONA_GIURDto) {

		this.listaT_PERSONA_GIURDto = listaT_PERSONA_GIURDto;
	}

	
    
    /**
     * @param listaT_REL_UDIENZA_STATO_UDDto the listaT_REL_UDIENZA_STATO_UDDto to set
     */
    public void setListaT_REL_UDIENZA_STATO_UDDto(List<T_REL_UDIENZA_STATO_UDDto> listaT_REL_UDIENZA_STATO_UDDto) {
    
        this.listaT_REL_UDIENZA_STATO_UDDto = listaT_REL_UDIENZA_STATO_UDDto;
    }

    /**
     * @param listaT_UDIENZADto the listaT_UDIENZADto to set
     */
    public void setListaT_UDIENZADto(List<T_UDIENZADto> listaT_UDIENZADto) {
    
        this.listaT_UDIENZADto = listaT_UDIENZADto;
    }

    /**
	 * @param procedimentoDto
	 *            the procedimentoDto to set
	 */
	public void setProcedimentoDto(T_PROCEDIMENTODto procedimentoDto) {

		this.procedimentoDto = procedimentoDto;
	}

	public T_REL_PROC_PROCDto getRelProcProcDto() {

		return relProcProcDto;
	}

	public void setRelProcProcDto(T_REL_PROC_PROCDto relProcProcDto) {

		this.relProcProcDto = relProcProcDto;
	}

	public Short getAnnoPadre() {

		return annoPadre;
	}

	public void setAnnoPadre(Short annoPadre) {

		this.annoPadre = annoPadre;
	}

	public Long getNumeroPadre() {

		return numeroPadre;
	}

	public void setNumeroPadre(Long numeroPadre) {

		this.numeroPadre = numeroPadre;
	}

	/**
	 * @return the idAccertamento
	 */
	public Long getIdAccertamento() {
		return idAccertamento;
	}

	/**
	 * @param idAccertamento the idAccertamento to set
	 */
	public void setIdAccertamento(Long idAccertamento) {
		this.idAccertamento = idAccertamento;
	}

	/**
	 * @return the relAttiDto
	 */
	public T_REL_ATTIDto getRelAttiDto() {
		return relAttiDto;
	}

	/**
	 * @param relAttiDto the relAttiDto to set
	 */
	public void setRelAttiDto(T_REL_ATTIDto relAttiDto) {
		this.relAttiDto = relAttiDto;
	}

	public T_DECRETODto getDecretoDto() {
		return decretoDto;
	}

	public void setDecretoDto(T_DECRETODto decretoDto) {
		this.decretoDto = decretoDto;
	}
	
	public AllegatoForm getAllegato() {
		return allegato;
	}

	public void setAllegato(AllegatoForm allegato) {
		this.allegato = allegato;
	}

	public T_IMPUGNAZIONEDto getImpugnazioneDto() {
		return impugnazioneDto;
	}

	public void setImpugnazioneDto(T_IMPUGNAZIONEDto impugnazioneDto) {
		this.impugnazioneDto = impugnazioneDto;
	}

	public Long getIdProcedimentoCda() {
		return idProcedimentoCda;
	}

	public void setIdProcedimentoCda(Long idProcedimentoCda) {
		this.idProcedimentoCda = idProcedimentoCda;
	}

	public boolean isAccettazioneDeposito() {
		return accettazioneDeposito;
	}

	public void setAccettazioneDeposito(boolean accettazioneDeposito) {
		this.accettazioneDeposito = accettazioneDeposito;
	}
}
