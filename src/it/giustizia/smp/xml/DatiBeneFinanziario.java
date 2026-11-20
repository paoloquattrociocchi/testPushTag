package it.giustizia.smp.xml;

import java.math.BigDecimal;
import java.util.Date;

public class DatiBeneFinanziario {

	private String descIntestatarioOriginale;
	private String descIntestatarioAttuale;
	private Boolean comunicazioneFUG;
	private Date dataComunicazioneFug;
	private BigDecimal saldoPrimaComunicazione;
	private BigDecimal saldoPrimaRelazione;

	public String getDescIntestatarioOriginale() {
		return descIntestatarioOriginale;
	}

	public void setDescIntestatarioOriginale(String descIntestatarioOriginale) {
		this.descIntestatarioOriginale = descIntestatarioOriginale;
	}

	public String getDescIntestatarioAttuale() {
		return descIntestatarioAttuale;
	}

	public void setDescIntestatarioAttuale(String descIntestatarioAttuale) {
		this.descIntestatarioAttuale = descIntestatarioAttuale;
	}

	public Boolean getComunicazioneFUG() {
		return comunicazioneFUG;
	}

	public void setComunicazioneFUG(Boolean comunicazioneFUG) {
		this.comunicazioneFUG = comunicazioneFUG;
	}

	public Date getDataComunicazioneFug() {
		return dataComunicazioneFug;
	}

	public void setDataComunicazioneFug(Date dataComunicazioneFug) {
		this.dataComunicazioneFug = dataComunicazioneFug;
	}

	public BigDecimal getSaldoPrimaComunicazione() {
		return saldoPrimaComunicazione;
	}

	public void setSaldoPrimaComunicazione(BigDecimal saldoPrimaComunicazione) {
		this.saldoPrimaComunicazione = saldoPrimaComunicazione;
	}

	public BigDecimal getSaldoPrimaRelazione() {
		return saldoPrimaRelazione;
	}

	public void setSaldoPrimaRelazione(BigDecimal saldoPrimaRelazione) {
		this.saldoPrimaRelazione = saldoPrimaRelazione;
	}
}
