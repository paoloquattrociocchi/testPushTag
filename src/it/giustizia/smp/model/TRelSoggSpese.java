package it.giustizia.smp.model;

// Generated 21-giu-2013 10.39.30 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name = "T_REL_SOGG_SPESE" , uniqueConstraints = @UniqueConstraint(columnNames = {
		"CODI_K_ID_PROC", "CODI_K_ID_SOGG_INT", "CODI_FK_ID_VER",
		"CODI_FK_ID_RUOLO",  "CODI_K_ID_UFF_OWNER", "CODI_K_ID_ATTO", "CODI_K_ID_SPESE_PROCESSUALI" }))
public class TRelSoggSpese implements java.io.Serializable {

	private TRelSoggSpeseId id;
	private String descUtente;
	private Date dataUltAgg;

	public TRelSoggSpese() {
	}

	public TRelSoggSpese(TRelSoggSpeseId id) {
		this.id = id;
		
	}

	public TRelSoggSpese(TRelSoggSpeseId id, String descUtente, Date dataUltAgg) {
		this.id = id;
		this.descUtente= descUtente;
		this.dataUltAgg= dataUltAgg;
	
	}
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codiKIdProc", column = @Column(name = "CODI_K_ID_PROC", nullable = false, precision = 15, scale = 0)),
			@AttributeOverride(name = "codiKIdSoggInt", column = @Column(name = "CODI_K_ID_SOGG_INT", nullable = false, precision = 15, scale = 0)),
			@AttributeOverride(name = "codiFkIdVer", column = @Column(name = "CODI_FK_ID_VER", nullable = false, precision = 3, scale = 0)),
			@AttributeOverride(name = "codiFkIdRuolo", column = @Column(name = "CODI_FK_ID_RUOLO", nullable = false, precision = 6, scale = 0)),
			@AttributeOverride(name = "codiKIdUffOwner", column = @Column(name = "CODI_K_ID_UFF_OWNER", nullable = false, precision = 6, scale = 0)),	
			@AttributeOverride(name = "codiKIdAtto", column = @Column(name = "CODI_K_ID_ATTO", nullable = false, precision = 15, scale = 0)),
			@AttributeOverride(name = "codiKIdSpeseProcessuali", column = @Column(name = "CODI_K_ID_SPESE_PROCESSUALI", nullable = false, precision = 6, scale = 0)) })
	public TRelSoggSpeseId getId() {
		return this.id;
	}

	public void setId(TRelSoggSpeseId id) {
		this.id = id;
	}
    
    @Column(name = "DESC_UTENTE", length = 64)
	public String getDescUtente() {
		return this.descUtente;
	}

	public void setDescUtente(String descUtente) {
		this.descUtente = descUtente;
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
