package it.giustizia.smp.xml;

import java.math.BigDecimal;
import javax.xml.datatype.XMLGregorianCalendar;

public class DatiBeneFinanzGest {

    private BigDecimal stima;
    private XMLGregorianCalendar dataStima;
    private Boolean dirittiTerzi;
    private String descDirittiTerzi;
    private Boolean beneRinvenuto;
    private XMLGregorianCalendar dataPossesso;
    private XMLGregorianCalendar dataPerditaPossesso;
    private String descAltriVincoli;
    private DatiBeneFinanzGest.Finanziario finanziario;
    private DatiBeneFinanzGest.Mobile mobile;
    
    public BigDecimal getStima() {
		return stima;
	}
	public XMLGregorianCalendar getDataStima() {
		return dataStima;
	}
	public Boolean getDirittiTerzi() {
		return dirittiTerzi;
	}
	public String getDescDirittiTerzi() {
		return descDirittiTerzi;
	}
	public Boolean getBeneRinvenuto() {
		return beneRinvenuto;
	}
	public XMLGregorianCalendar getDataPossesso() {
		return dataPossesso;
	}
	public XMLGregorianCalendar getDataPerditaPossesso() {
		return dataPerditaPossesso;
	}
	public String getDescAltriVincoli() {
		return descAltriVincoli;
	}
	public DatiBeneFinanzGest.Finanziario getFinanziario() {
		return finanziario;
	}
	public DatiBeneFinanzGest.Mobile getMobile() {
		return mobile;
	}
	public void setStima(BigDecimal stima) {
		this.stima = stima;
	}
	public void setDataStima(XMLGregorianCalendar dataStima) {
		this.dataStima = dataStima;
	}
	public void setDirittiTerzi(Boolean dirittiTerzi) {
		this.dirittiTerzi = dirittiTerzi;
	}
	public void setDescDirittiTerzi(String descDirittiTerzi) {
		this.descDirittiTerzi = descDirittiTerzi;
	}
	public void setBeneRinvenuto(Boolean beneRinvenuto) {
		this.beneRinvenuto = beneRinvenuto;
	}
	public void setDataPossesso(XMLGregorianCalendar dataPossesso) {
		this.dataPossesso = dataPossesso;
	}
	public void setDataPerditaPossesso(XMLGregorianCalendar dataPerditaPossesso) {
		this.dataPerditaPossesso = dataPerditaPossesso;
	}
	public void setDescAltriVincoli(String descAltriVincoli) {
		this.descAltriVincoli = descAltriVincoli;
	}
	public void setFinanziario(DatiBeneFinanzGest.Finanziario finanziario) {
		this.finanziario = finanziario;
	}
	public void setMobile(DatiBeneFinanzGest.Mobile mobile) {
		this.mobile = mobile;
	}
	public static class Finanziario {

        private String descIntestatarioOriginale;
        private String descIntestatarioAttuale;
        private Boolean comunicazioneFUG;
        private XMLGregorianCalendar dataComunicazioneFug;
        private BigDecimal saldoPrimaComunicazione;
        private BigDecimal saldoPrimaRelazione;
		public String getDescIntestatarioOriginale() {
			return descIntestatarioOriginale;
		}
		public String getDescIntestatarioAttuale() {
			return descIntestatarioAttuale;
		}
		public Boolean getComunicazioneFUG() {
			return comunicazioneFUG;
		}
		public XMLGregorianCalendar getDataComunicazioneFug() {
			return dataComunicazioneFug;
		}
		public BigDecimal getSaldoPrimaComunicazione() {
			return saldoPrimaComunicazione;
		}
		public BigDecimal getSaldoPrimaRelazione() {
			return saldoPrimaRelazione;
		}
		public void setDescIntestatarioOriginale(String descIntestatarioOriginale) {
			this.descIntestatarioOriginale = descIntestatarioOriginale;
		}
		public void setDescIntestatarioAttuale(String descIntestatarioAttuale) {
			this.descIntestatarioAttuale = descIntestatarioAttuale;
		}
		public void setComunicazioneFUG(Boolean comunicazioneFUG) {
			this.comunicazioneFUG = comunicazioneFUG;
		}
		public void setDataComunicazioneFug(XMLGregorianCalendar dataComunicazioneFug) {
			this.dataComunicazioneFug = dataComunicazioneFug;
		}
		public void setSaldoPrimaComunicazione(BigDecimal saldoPrimaComunicazione) {
			this.saldoPrimaComunicazione = saldoPrimaComunicazione;
		}
		public void setSaldoPrimaRelazione(BigDecimal saldoPrimaRelazione) {
			this.saldoPrimaRelazione = saldoPrimaRelazione;
		}
        
    }
    public static class Mobile {

        private Boolean oneriCustodia;
        private BigDecimal valoreOneriCustodia;
        private XMLGregorianCalendar dataOneriCustodia;
		public Boolean getOneriCustodia() {
			return oneriCustodia;
		}
		public BigDecimal getValoreOneriCustodia() {
			return valoreOneriCustodia;
		}
		public XMLGregorianCalendar getDataOneriCustodia() {
			return dataOneriCustodia;
		}
		public void setOneriCustodia(Boolean oneriCustodia) {
			this.oneriCustodia = oneriCustodia;
		}
		public void setValoreOneriCustodia(BigDecimal valoreOneriCustodia) {
			this.valoreOneriCustodia = valoreOneriCustodia;
		}
		public void setDataOneriCustodia(XMLGregorianCalendar dataOneriCustodia) {
			this.dataOneriCustodia = dataOneriCustodia;
		}
    }

}
