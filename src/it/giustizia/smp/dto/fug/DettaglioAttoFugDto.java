package it.giustizia.smp.dto.fug;

import java.math.BigDecimal;
import java.util.Date;

public class DettaglioAttoFugDto {

//		--T_ATTO---
	   private BigDecimal CODI_K_ID_ATTO;
	   private BigDecimal CODI_FK_ID_TIPO_ATTO;
	   private BigDecimal CODI_FK_ID_CAT_ATTO;
	
	   private Date DATA_REGISTRAZIONE;
	   private String DESC_TIPO_ATTO;
	   private String DESC_CAT_ATTO;
	   private String DESC_STATO_ATTO;
	   private BigDecimal NUME_ANNO_ATTO;
	   private BigDecimal NUME_ATTO;
	   
	   private BigDecimal CODI_K_ID_INVIO;
	   private BigDecimal CODI_FK_ID_ATTO;
	   private BigDecimal CODI_FK_ID_PROC;
	   private BigDecimal CODI_FK_ID_UFF_OWNER;
	   private String DESC_UTENTE;
	   private Date DATA_ULT_AGG;
	   private BigDecimal CODI_FK_ID_BENE;
	   private Date DATA_DEPOSITO;
	   private Date DATA_DEFINITIVITA;
	   private String DESC_MODELLO;
	   private Character FLG_ESITO_ANNULL;
	   private String DESC_PROTOCOLLO;
	   private String DESC_PROTOCOLLO_RIF;
	   private String DESCR_BENE;
	   private Date DATA_INVIO;
	   private Character FLG_STATO_INVIO;
	   private Character FLG_DEF_POS;
	   private Date DATA_DEF_POS;

	   private BigDecimal CODI_FK_ID_SOG_TIT;
	   private Date DATA_NOTIFICA;
	   private Date DATA_IRR_PROVV;

	   private String NUME_RISORSA_EQUI;
	   private String NUME_PROVV_EQUI;
	   private String DESC_NOTIF_EQUI;
	   private String DESC_NOTE_EQUI;
	private BigDecimal IMPO_EURO;
	private String CODI_FK_ID_VALUTA;
	private BigDecimal IMPO_VALUTA;
	public BigDecimal getIMPO_VALUTA() {
		return IMPO_VALUTA;
	}

	public void setIMPO_VALUTA(BigDecimal iMPO_VALUTA) {
		IMPO_VALUTA = iMPO_VALUTA;
	}



	public String getCODI_FK_ID_VALUTA() {
		return CODI_FK_ID_VALUTA;
	}

	public void setCODI_FK_ID_VALUTA(String cODI_FK_ID_VALUTA) {
		CODI_FK_ID_VALUTA = cODI_FK_ID_VALUTA;
	}

	public BigDecimal getIMPO_EURO() {
		return IMPO_EURO;
	}

	public void setIMPO_EURO(BigDecimal iMPO_EURO) {
		IMPO_EURO = iMPO_EURO;
	}
	public BigDecimal getCODI_K_ID_ATTO() {
		return CODI_K_ID_ATTO;
	}
	public void setCODI_K_ID_ATTO(BigDecimal cODI_K_ID_ATTO) {
		CODI_K_ID_ATTO = cODI_K_ID_ATTO;
	}
	public BigDecimal getCODI_FK_ID_TIPO_ATTO() {
		return CODI_FK_ID_TIPO_ATTO;
	}
	public void setCODI_FK_ID_TIPO_ATTO(BigDecimal cODI_FK_ID_TIPO_ATTO) {
		CODI_FK_ID_TIPO_ATTO = cODI_FK_ID_TIPO_ATTO;
	}
	public BigDecimal getCODI_FK_ID_CAT_ATTO() {
		return CODI_FK_ID_CAT_ATTO;
	}
	public void setCODI_FK_ID_CAT_ATTO(BigDecimal cODI_FK_ID_CAT_ATTO) {
		CODI_FK_ID_CAT_ATTO = cODI_FK_ID_CAT_ATTO;
	}
	public Date getDATA_REGISTRAZIONE() {
		return DATA_REGISTRAZIONE;
	}
	public void setDATA_REGISTRAZIONE(Date dATA_REGISTRAZIONE) {
		DATA_REGISTRAZIONE = dATA_REGISTRAZIONE;
	}
	public Date getDATA_DEPOSITO() {
		return DATA_DEPOSITO;
	}
	public void setDATA_DEPOSITO(Date dATA_DEPOSITO) {
		DATA_DEPOSITO = dATA_DEPOSITO;
	}
	public Date getDATA_DEFINITIVITA() {
		return DATA_DEFINITIVITA;
	}
	public void setDATA_DEFINITIVITA(Date dATA_DEFINITIVITA) {
		DATA_DEFINITIVITA = dATA_DEFINITIVITA;
	}
	public String getDESC_TIPO_ATTO() {
		return DESC_TIPO_ATTO;
	}
	public void setDESC_TIPO_ATTO(String dESC_TIPO_ATTO) {
		DESC_TIPO_ATTO = dESC_TIPO_ATTO;
	}
	public String getDESC_CAT_ATTO() {
		return DESC_CAT_ATTO;
	}
	public void setDESC_CAT_ATTO(String dESC_CAT_ATTO) {
		DESC_CAT_ATTO = dESC_CAT_ATTO;
	}
	public String getDESC_STATO_ATTO() {
		return DESC_STATO_ATTO;
	}
	public void setDESC_STATO_ATTO(String dESC_STATO_ATTO) {
		DESC_STATO_ATTO = dESC_STATO_ATTO;
	}
	public BigDecimal getNUME_ANNO_ATTO() {
		return NUME_ANNO_ATTO;
	}
	public void setNUME_ANNO_ATTO(BigDecimal nUME_ANNO_ATTO) {
		NUME_ANNO_ATTO = nUME_ANNO_ATTO;
	}
	public BigDecimal getNUME_ATTO() {
		return NUME_ATTO;
	}
	public void setNUME_ATTO(BigDecimal nUME_ATTO) {
		NUME_ATTO = nUME_ATTO;
	}
	public Date getDATA_INVIO() {
		return DATA_INVIO;
	}
	public void setDATA_INVIO(Date dATA_INVIO) {
		DATA_INVIO = dATA_INVIO;
	}
	public BigDecimal getCODI_FK_ID_BENE() {
		return CODI_FK_ID_BENE;
	}
	public void setCODI_FK_ID_BENE(BigDecimal cODI_FK_ID_BENE) {
		CODI_FK_ID_BENE = cODI_FK_ID_BENE;
	}
	public String getDESC_MODELLO() {
		return DESC_MODELLO;
	}
	public void setDESC_MODELLO(String dESC_MODELLO) {
		DESC_MODELLO = dESC_MODELLO;
	}
	public String getDESC_PROTOCOLLO() {
		return DESC_PROTOCOLLO;
	}
	public void setDESC_PROTOCOLLO(String dESC_PROTOCOLLO) {
		DESC_PROTOCOLLO = dESC_PROTOCOLLO;
	}
	public BigDecimal getCODI_K_ID_INVIO() {
		return CODI_K_ID_INVIO;
	}
	public void setCODI_K_ID_INVIO(BigDecimal cODI_K_ID_INVIO) {
		CODI_K_ID_INVIO = cODI_K_ID_INVIO;
	}
	public BigDecimal getCODI_FK_ID_ATTO() {
		return CODI_FK_ID_ATTO;
	}
	public void setCODI_FK_ID_ATTO(BigDecimal cODI_FK_ID_ATTO) {
		CODI_FK_ID_ATTO = cODI_FK_ID_ATTO;
	}
	public BigDecimal getCODI_FK_ID_PROC() {
		return CODI_FK_ID_PROC;
	}
	public void setCODI_FK_ID_PROC(BigDecimal cODI_FK_ID_PROC) {
		CODI_FK_ID_PROC = cODI_FK_ID_PROC;
	}
	public BigDecimal getCODI_FK_ID_UFF_OWNER() {
		return CODI_FK_ID_UFF_OWNER;
	}
	public void setCODI_FK_ID_UFF_OWNER(BigDecimal cODI_FK_ID_UFF_OWNER) {
		CODI_FK_ID_UFF_OWNER = cODI_FK_ID_UFF_OWNER;
	}
	public String getDESC_UTENTE() {
		return DESC_UTENTE;
	}
	public void setDESC_UTENTE(String dESC_UTENTE) {
		DESC_UTENTE = dESC_UTENTE;
	}
	public Character getFLG_ESITO_ANNULL() {
		return FLG_ESITO_ANNULL;
	}
	public void setFLG_ESITO_ANNULL(Character fLG_ESITO_ANNULL) {
		FLG_ESITO_ANNULL = fLG_ESITO_ANNULL;
	}
	public String getDESC_PROTOCOLLO_RIF() {
		return DESC_PROTOCOLLO_RIF;
	}
	public void setDESC_PROTOCOLLO_RIF(String dESC_PROTOCOLLO_RIF) {
		DESC_PROTOCOLLO_RIF = dESC_PROTOCOLLO_RIF;
	}
	public String getDESCR_BENE() {
		return DESCR_BENE;
	}
	public void setDESCR_BENE(String dESCR_BENE) {
		DESCR_BENE = dESCR_BENE;
	}
	public Date getDATA_ULT_AGG() {
		return DATA_ULT_AGG;
	}
	public void setDATA_ULT_AGG(Date dATA_ULT_AGG) {
		DATA_ULT_AGG = dATA_ULT_AGG;
	}
	public Character getFLG_STATO_INVIO() {
		return FLG_STATO_INVIO;
	}
	public void setFLG_STATO_INVIO(Character fLG_STATO_INVIO) {
		FLG_STATO_INVIO = fLG_STATO_INVIO;
	}
	public Character getFLG_DEF_POS() {
		return FLG_DEF_POS;
	}
	public void setFLG_DEF_POS(Character fLG_DEF_POS) {
		FLG_DEF_POS = fLG_DEF_POS;
	}
	public Date getDATA_DEF_POS() {
		return DATA_DEF_POS;
	}
	public void setDATA_DEF_POS(Date dATA_DEF_POS) {
		DATA_DEF_POS = dATA_DEF_POS;
	}
	public BigDecimal getCODI_FK_ID_SOG_TIT() {
		return CODI_FK_ID_SOG_TIT;
	}
	public void setCODI_FK_ID_SOG_TIT(BigDecimal cODI_FK_ID_SOG_TIT) {
		CODI_FK_ID_SOG_TIT = cODI_FK_ID_SOG_TIT;
	}
	public Date getDATA_NOTIFICA() {
		return DATA_NOTIFICA;
	}
	public void setDATA_NOTIFICA(Date dATA_NOTIFICA) {
		DATA_NOTIFICA = dATA_NOTIFICA;
	}
	public Date getDATA_IRR_PROVV() {
		return DATA_IRR_PROVV;
	}
	public void setDATA_IRR_PROVV(Date dATA_IRR_PROVV) {
		DATA_IRR_PROVV = dATA_IRR_PROVV;
	}
	public String getNUME_RISORSA_EQUI() {
		return NUME_RISORSA_EQUI;
	}
	public void setNUME_RISORSA_EQUI(String nUME_RISORSA_EQUI) {
		NUME_RISORSA_EQUI = nUME_RISORSA_EQUI;
	}
	public String getNUME_PROVV_EQUI() {
		return NUME_PROVV_EQUI;
	}
	public void setNUME_PROVV_EQUI(String nUME_PROVV_EQUI) {
		NUME_PROVV_EQUI = nUME_PROVV_EQUI;
	}
	public String getDESC_NOTIF_EQUI() {
		return DESC_NOTIF_EQUI;
	}
	public void setDESC_NOTIF_EQUI(String dESC_NOTIF_EQUI) {
		DESC_NOTIF_EQUI = dESC_NOTIF_EQUI;
	}
	public String getDESC_NOTE_EQUI() {
		return DESC_NOTE_EQUI;
	}
	public void setDESC_NOTE_EQUI(String dESC_NOTE_EQUI) {
		DESC_NOTE_EQUI = dESC_NOTE_EQUI;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CODI_FK_ID_ATTO == null) ? 0 : CODI_FK_ID_ATTO.hashCode());
		result = prime * result + ((CODI_FK_ID_BENE == null) ? 0 : CODI_FK_ID_BENE.hashCode());
		result = prime * result + ((CODI_FK_ID_CAT_ATTO == null) ? 0 : CODI_FK_ID_CAT_ATTO.hashCode());
		result = prime * result + ((CODI_FK_ID_PROC == null) ? 0 : CODI_FK_ID_PROC.hashCode());
		result = prime * result + ((CODI_FK_ID_SOG_TIT == null) ? 0 : CODI_FK_ID_SOG_TIT.hashCode());
		result = prime * result + ((CODI_FK_ID_TIPO_ATTO == null) ? 0 : CODI_FK_ID_TIPO_ATTO.hashCode());
		result = prime * result + ((CODI_FK_ID_UFF_OWNER == null) ? 0 : CODI_FK_ID_UFF_OWNER.hashCode());
		result = prime * result + ((CODI_K_ID_ATTO == null) ? 0 : CODI_K_ID_ATTO.hashCode());
		result = prime * result + ((CODI_K_ID_INVIO == null) ? 0 : CODI_K_ID_INVIO.hashCode());
		result = prime * result + ((DATA_DEFINITIVITA == null) ? 0 : DATA_DEFINITIVITA.hashCode());
		result = prime * result + ((DATA_DEF_POS == null) ? 0 : DATA_DEF_POS.hashCode());
		result = prime * result + ((DATA_DEPOSITO == null) ? 0 : DATA_DEPOSITO.hashCode());
		result = prime * result + ((DATA_INVIO == null) ? 0 : DATA_INVIO.hashCode());
		result = prime * result + ((DATA_IRR_PROVV == null) ? 0 : DATA_IRR_PROVV.hashCode());
		result = prime * result + ((DATA_NOTIFICA == null) ? 0 : DATA_NOTIFICA.hashCode());
		result = prime * result + ((DATA_REGISTRAZIONE == null) ? 0 : DATA_REGISTRAZIONE.hashCode());
		result = prime * result + ((DATA_ULT_AGG == null) ? 0 : DATA_ULT_AGG.hashCode());
		result = prime * result + ((DESCR_BENE == null) ? 0 : DESCR_BENE.hashCode());
		result = prime * result + ((DESC_CAT_ATTO == null) ? 0 : DESC_CAT_ATTO.hashCode());
		result = prime * result + ((DESC_MODELLO == null) ? 0 : DESC_MODELLO.hashCode());
		result = prime * result + ((DESC_NOTE_EQUI == null) ? 0 : DESC_NOTE_EQUI.hashCode());
		result = prime * result + ((DESC_NOTIF_EQUI == null) ? 0 : DESC_NOTIF_EQUI.hashCode());
		result = prime * result + ((DESC_PROTOCOLLO == null) ? 0 : DESC_PROTOCOLLO.hashCode());
		result = prime * result + ((DESC_PROTOCOLLO_RIF == null) ? 0 : DESC_PROTOCOLLO_RIF.hashCode());
		result = prime * result + ((DESC_STATO_ATTO == null) ? 0 : DESC_STATO_ATTO.hashCode());
		result = prime * result + ((DESC_TIPO_ATTO == null) ? 0 : DESC_TIPO_ATTO.hashCode());
		result = prime * result + ((DESC_UTENTE == null) ? 0 : DESC_UTENTE.hashCode());
		result = prime * result + ((FLG_DEF_POS == null) ? 0 : FLG_DEF_POS.hashCode());
		result = prime * result + ((FLG_ESITO_ANNULL == null) ? 0 : FLG_ESITO_ANNULL.hashCode());
		result = prime * result + ((FLG_STATO_INVIO == null) ? 0 : FLG_STATO_INVIO.hashCode());
		result = prime * result + ((IMPO_EURO == null) ? 0 : IMPO_EURO.hashCode());
		result = prime * result + ((NUME_ANNO_ATTO == null) ? 0 : NUME_ANNO_ATTO.hashCode());
		result = prime * result + ((NUME_ATTO == null) ? 0 : NUME_ATTO.hashCode());
		result = prime * result + ((NUME_PROVV_EQUI == null) ? 0 : NUME_PROVV_EQUI.hashCode());
		result = prime * result + ((NUME_RISORSA_EQUI == null) ? 0 : NUME_RISORSA_EQUI.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DettaglioAttoFugDto other = (DettaglioAttoFugDto) obj;
		if (CODI_FK_ID_ATTO == null) {
			if (other.CODI_FK_ID_ATTO != null)
				return false;
		} else if (!CODI_FK_ID_ATTO.equals(other.CODI_FK_ID_ATTO))
			return false;
		if (CODI_FK_ID_BENE == null) {
			if (other.CODI_FK_ID_BENE != null)
				return false;
		} else if (!CODI_FK_ID_BENE.equals(other.CODI_FK_ID_BENE))
			return false;
		if (CODI_FK_ID_CAT_ATTO == null) {
			if (other.CODI_FK_ID_CAT_ATTO != null)
				return false;
		} else if (!CODI_FK_ID_CAT_ATTO.equals(other.CODI_FK_ID_CAT_ATTO))
			return false;
		if (CODI_FK_ID_PROC == null) {
			if (other.CODI_FK_ID_PROC != null)
				return false;
		} else if (!CODI_FK_ID_PROC.equals(other.CODI_FK_ID_PROC))
			return false;
		if (CODI_FK_ID_SOG_TIT == null) {
			if (other.CODI_FK_ID_SOG_TIT != null)
				return false;
		} else if (!CODI_FK_ID_SOG_TIT.equals(other.CODI_FK_ID_SOG_TIT))
			return false;
		if (CODI_FK_ID_TIPO_ATTO == null) {
			if (other.CODI_FK_ID_TIPO_ATTO != null)
				return false;
		} else if (!CODI_FK_ID_TIPO_ATTO.equals(other.CODI_FK_ID_TIPO_ATTO))
			return false;
		if (CODI_FK_ID_UFF_OWNER == null) {
			if (other.CODI_FK_ID_UFF_OWNER != null)
				return false;
		} else if (!CODI_FK_ID_UFF_OWNER.equals(other.CODI_FK_ID_UFF_OWNER))
			return false;
		if (CODI_K_ID_ATTO == null) {
			if (other.CODI_K_ID_ATTO != null)
				return false;
		} else if (!CODI_K_ID_ATTO.equals(other.CODI_K_ID_ATTO))
			return false;
		if (CODI_K_ID_INVIO == null) {
			if (other.CODI_K_ID_INVIO != null)
				return false;
		} else if (!CODI_K_ID_INVIO.equals(other.CODI_K_ID_INVIO))
			return false;
		if (DATA_DEFINITIVITA == null) {
			if (other.DATA_DEFINITIVITA != null)
				return false;
		} else if (!DATA_DEFINITIVITA.equals(other.DATA_DEFINITIVITA))
			return false;
		if (DATA_DEF_POS == null) {
			if (other.DATA_DEF_POS != null)
				return false;
		} else if (!DATA_DEF_POS.equals(other.DATA_DEF_POS))
			return false;
		if (DATA_DEPOSITO == null) {
			if (other.DATA_DEPOSITO != null)
				return false;
		} else if (!DATA_DEPOSITO.equals(other.DATA_DEPOSITO))
			return false;
		if (DATA_INVIO == null) {
			if (other.DATA_INVIO != null)
				return false;
		} else if (!DATA_INVIO.equals(other.DATA_INVIO))
			return false;
		if (DATA_IRR_PROVV == null) {
			if (other.DATA_IRR_PROVV != null)
				return false;
		} else if (!DATA_IRR_PROVV.equals(other.DATA_IRR_PROVV))
			return false;
		if (DATA_NOTIFICA == null) {
			if (other.DATA_NOTIFICA != null)
				return false;
		} else if (!DATA_NOTIFICA.equals(other.DATA_NOTIFICA))
			return false;
		if (DATA_REGISTRAZIONE == null) {
			if (other.DATA_REGISTRAZIONE != null)
				return false;
		} else if (!DATA_REGISTRAZIONE.equals(other.DATA_REGISTRAZIONE))
			return false;
		if (DATA_ULT_AGG == null) {
			if (other.DATA_ULT_AGG != null)
				return false;
		} else if (!DATA_ULT_AGG.equals(other.DATA_ULT_AGG))
			return false;
		if (DESCR_BENE == null) {
			if (other.DESCR_BENE != null)
				return false;
		} else if (!DESCR_BENE.equals(other.DESCR_BENE))
			return false;
		if (DESC_CAT_ATTO == null) {
			if (other.DESC_CAT_ATTO != null)
				return false;
		} else if (!DESC_CAT_ATTO.equals(other.DESC_CAT_ATTO))
			return false;
		if (DESC_MODELLO == null) {
			if (other.DESC_MODELLO != null)
				return false;
		} else if (!DESC_MODELLO.equals(other.DESC_MODELLO))
			return false;
		if (DESC_NOTE_EQUI == null) {
			if (other.DESC_NOTE_EQUI != null)
				return false;
		} else if (!DESC_NOTE_EQUI.equals(other.DESC_NOTE_EQUI))
			return false;
		if (DESC_NOTIF_EQUI == null) {
			if (other.DESC_NOTIF_EQUI != null)
				return false;
		} else if (!DESC_NOTIF_EQUI.equals(other.DESC_NOTIF_EQUI))
			return false;
		if (DESC_PROTOCOLLO == null) {
			if (other.DESC_PROTOCOLLO != null)
				return false;
		} else if (!DESC_PROTOCOLLO.equals(other.DESC_PROTOCOLLO))
			return false;
		if (DESC_PROTOCOLLO_RIF == null) {
			if (other.DESC_PROTOCOLLO_RIF != null)
				return false;
		} else if (!DESC_PROTOCOLLO_RIF.equals(other.DESC_PROTOCOLLO_RIF))
			return false;
		if (DESC_STATO_ATTO == null) {
			if (other.DESC_STATO_ATTO != null)
				return false;
		} else if (!DESC_STATO_ATTO.equals(other.DESC_STATO_ATTO))
			return false;
		if (DESC_TIPO_ATTO == null) {
			if (other.DESC_TIPO_ATTO != null)
				return false;
		} else if (!DESC_TIPO_ATTO.equals(other.DESC_TIPO_ATTO))
			return false;
		if (DESC_UTENTE == null) {
			if (other.DESC_UTENTE != null)
				return false;
		} else if (!DESC_UTENTE.equals(other.DESC_UTENTE))
			return false;
		if (FLG_DEF_POS == null) {
			if (other.FLG_DEF_POS != null)
				return false;
		} else if (!FLG_DEF_POS.equals(other.FLG_DEF_POS))
			return false;
		if (FLG_ESITO_ANNULL == null) {
			if (other.FLG_ESITO_ANNULL != null)
				return false;
		} else if (!FLG_ESITO_ANNULL.equals(other.FLG_ESITO_ANNULL))
			return false;
		if (FLG_STATO_INVIO == null) {
			if (other.FLG_STATO_INVIO != null)
				return false;
		} else if (!FLG_STATO_INVIO.equals(other.FLG_STATO_INVIO))
			return false;
		if (IMPO_EURO == null) {
			if (other.IMPO_EURO != null)
				return false;
		} else if (!IMPO_EURO.equals(other.IMPO_EURO))
			return false;
		if (NUME_ANNO_ATTO == null) {
			if (other.NUME_ANNO_ATTO != null)
				return false;
		} else if (!NUME_ANNO_ATTO.equals(other.NUME_ANNO_ATTO))
			return false;
		if (NUME_ATTO == null) {
			if (other.NUME_ATTO != null)
				return false;
		} else if (!NUME_ATTO.equals(other.NUME_ATTO))
			return false;
		if (NUME_PROVV_EQUI == null) {
			if (other.NUME_PROVV_EQUI != null)
				return false;
		} else if (!NUME_PROVV_EQUI.equals(other.NUME_PROVV_EQUI))
			return false;
		if (NUME_RISORSA_EQUI == null) {
			if (other.NUME_RISORSA_EQUI != null)
				return false;
		} else if (!NUME_RISORSA_EQUI.equals(other.NUME_RISORSA_EQUI))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DettaglioAttoFugDto [CODI_K_ID_ATTO=" + CODI_K_ID_ATTO + ", CODI_FK_ID_TIPO_ATTO=" + CODI_FK_ID_TIPO_ATTO + ", CODI_FK_ID_CAT_ATTO=" + CODI_FK_ID_CAT_ATTO + ", DATA_REGISTRAZIONE=" + DATA_REGISTRAZIONE + ", DESC_TIPO_ATTO="
				+ DESC_TIPO_ATTO + ", DESC_CAT_ATTO=" + DESC_CAT_ATTO + ", DESC_STATO_ATTO=" + DESC_STATO_ATTO + ", NUME_ANNO_ATTO=" + NUME_ANNO_ATTO + ", NUME_ATTO=" + NUME_ATTO + ", CODI_K_ID_INVIO=" + CODI_K_ID_INVIO
				+ ", CODI_FK_ID_ATTO=" + CODI_FK_ID_ATTO + ", CODI_FK_ID_PROC=" + CODI_FK_ID_PROC + ", CODI_FK_ID_UFF_OWNER=" + CODI_FK_ID_UFF_OWNER + ", DESC_UTENTE=" + DESC_UTENTE + ", DATA_ULT_AGG=" + DATA_ULT_AGG + ", CODI_FK_ID_BENE="
				+ CODI_FK_ID_BENE + ", DATA_DEPOSITO=" + DATA_DEPOSITO + ", DATA_DEFINITIVITA=" + DATA_DEFINITIVITA + ", DESC_MODELLO=" + DESC_MODELLO + ", FLG_ESITO_ANNULL=" + FLG_ESITO_ANNULL + ", DESC_PROTOCOLLO=" + DESC_PROTOCOLLO
				+ ", DESC_PROTOCOLLO_RIF=" + DESC_PROTOCOLLO_RIF + ", DESCR_BENE=" + DESCR_BENE + ", DATA_INVIO=" + DATA_INVIO + ", FLG_STATO_INVIO=" + FLG_STATO_INVIO + ", FLG_DEF_POS=" + FLG_DEF_POS + ", DATA_DEF_POS=" + DATA_DEF_POS
				+ ", CODI_FK_ID_SOG_TIT=" + CODI_FK_ID_SOG_TIT + ", DATA_NOTIFICA=" + DATA_NOTIFICA + ", DATA_IRR_PROVV=" + DATA_IRR_PROVV + ", NUME_RISORSA_EQUI=" + NUME_RISORSA_EQUI + ", NUME_PROVV_EQUI=" + NUME_PROVV_EQUI
				+ ", DESC_NOTIF_EQUI=" + DESC_NOTIF_EQUI + ", DESC_NOTE_EQUI=" + DESC_NOTE_EQUI + ", IMPO_EURO=" + IMPO_EURO + "]";
	}
	
}

