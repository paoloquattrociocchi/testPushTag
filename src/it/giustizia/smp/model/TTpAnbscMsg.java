package it.giustizia.smp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TTpAnbscMsg
 */
@Entity
@Table(name = "T_TP_ANBSC_MSG")
public class TTpAnbscMsg implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long codiKIdTipoAnbscMsg;
	private String codiAnbscMsg;
	private String descTipoMsg;

	@Id
	@Column(name = "CODI_K_ID_TIPO_ANBSC_MSG", unique = true, nullable = false, precision = 15, scale = 0)
	public Long getCodiKIdTipoAnbscMsg() {
		return codiKIdTipoAnbscMsg;
	}

	public void setCodiKIdTipoAnbscMsg(Long codiKIdTipoAnbscMsg) {
		this.codiKIdTipoAnbscMsg = codiKIdTipoAnbscMsg;
	}

	@Column(name = "CODI_ANBSC_MSG", nullable = false, length = 2)
	public String getCodiAnbscMsg() {
		return codiAnbscMsg;
	}

	public void setCodiAnbscMsg(String codiAnbscMsg) {
		this.codiAnbscMsg = codiAnbscMsg;
	}


	@Column(name = "DESC_TIPO_MSG", nullable = false, length = 4000)
	public String getDescTipoMsg() {
		return this.descTipoMsg;
	}

	public void setDescTipoMsg(String descTipoMsg) {
		this.descTipoMsg = descTipoMsg;
	}

}
