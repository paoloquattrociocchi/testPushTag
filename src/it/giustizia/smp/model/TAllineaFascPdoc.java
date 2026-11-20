package it.giustizia.smp.model;

// Creata GC 14/07/2020

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_ALLINEA_FASC_PDOC")
public class TAllineaFascPdoc implements java.io.Serializable {

	private long codiKIdAllineaFascPdoc;
	private long codiFkIdProc;
	private long codiFkIdUffOwner;
	private String descFascPdoc;
	private String flgEsitoAllineamento;
	private String descEsitoAllineamento;
	private Date dataUltAgg;

	public TAllineaFascPdoc() {
	}

	public TAllineaFascPdoc(long codiKIdAllineaFascPdoc) {
		this.codiKIdAllineaFascPdoc = codiKIdAllineaFascPdoc;
	}

	public TAllineaFascPdoc(long codiKIdAllineaFascPdoc, long codiFkIdProc,
			long codiFkIdUffOwner, String descFascPdoc, String flgEsitoAllineamento,
			String descEsitoAllineamento, Date dataUltAgg) {
		this.codiKIdAllineaFascPdoc = codiKIdAllineaFascPdoc;
		this.codiFkIdProc = codiFkIdProc;
		this.codiFkIdUffOwner = codiFkIdUffOwner;
		this.descFascPdoc = descFascPdoc;
		this.flgEsitoAllineamento = flgEsitoAllineamento;
		this.descEsitoAllineamento = descEsitoAllineamento;
		this.dataUltAgg = dataUltAgg;
	}

	@Id
	@Column(name = "CODI_K_ID_ALLINEA_FASC_PDOC", unique = true, nullable = false, precision = 15, scale = 0)
	public long getcodiKIdAllineaFascPdoc() {
		return this.codiKIdAllineaFascPdoc;
	}

	public void setcodiKIdAllineaFascPdoc(long codiKIdAllineaFascPdoc) {
		this.codiKIdAllineaFascPdoc = codiKIdAllineaFascPdoc;
	}

	@Column(name = "CODI_FK_ID_PROC", nullable = false, precision = 15, scale = 0)
	public long getCodiFkIdProc() {
		return this.codiFkIdProc;
	}

	public void setCodiFkIdProc(long codiFkIdProc) {
		this.codiFkIdProc = codiFkIdProc;
	}

	@Column(name = "CODI_FK_ID_UFF_OWNER", nullable = false, precision = 6, scale = 0)
	public long getCodiFkIdUffOwner() {
		return this.codiFkIdUffOwner;
	}

	public void setCodiFkIdUffOwner(long codiFkIdUffOwner) {
		this.codiFkIdUffOwner = codiFkIdUffOwner;
	}

	@Column(name = "DESC_FASCICOLO_PDOC", length = 200)
	public String getDescFascPdoc() {
		return this.descFascPdoc;
	}

	public void setDescFascPdoc(String descFascPdoc) {
		this.descFascPdoc = descFascPdoc;
	}

	@Column(name = "FLG_ESITO_ALLINEAMENTO", length = 1)
	public String getFlgEsitoAllineamento() {
		return this.flgEsitoAllineamento;
	}

	public void setFlgEsitoAllineamento(String flgEsitoAllineamento) {
		this.flgEsitoAllineamento = flgEsitoAllineamento;
	}

	@Column(name = "DESC_ESITO_ALLINEAMENTO", length = 4000)
	public String getDescEsitoAllineamento() {
		return this.descEsitoAllineamento;
	}

	public void setDescEsitoAllineamento(String descEsitoAllineamento) {
		this.descEsitoAllineamento = descEsitoAllineamento;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_ULT_AGG", length = 7)
	public Date getDataUltAgg() {
		return this.dataUltAgg;
	}

	public void setDataUltAgg(Date dataUltAgg) {
		this.dataUltAgg = dataUltAgg;
	}

}
