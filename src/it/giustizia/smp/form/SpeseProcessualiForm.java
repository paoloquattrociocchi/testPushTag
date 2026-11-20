package it.giustizia.smp.form;


import it.giustizia.smp.model.TTpSpeseProcessuali;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.validation.date.ModernDate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class SpeseProcessualiForm {

	private TTpSpeseProcessuali  speseProcessuali;
	private List<TTpSpeseProcessuali> listaSpeseProcessuali;
	
	@NumberFormat(style = Style.NUMBER, pattern = "###,###.##")
    @Max(value = 1000000000000L)
    private BigDecimal   importoSpese;
  //utilizzato per SpeseProcessuali per contenere id 
    private int						  idSpeseProcessuali;
	
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date				   dataInizioValidita;
   
    @ModernDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date				   dataFineValidita;
    
    private int codiKIdSpeseProcessuali;
	private String descImportoSpese;
	private String descUtente;
	private Date dataUltAgg;

	
	//mev Spese Processuali
		public BigDecimal getImportoSpese() {
			return importoSpese;
		}

		public void setImportoSpese(BigDecimal importoSpese) {
			this.importoSpese = importoSpese;
		}
		
		public int getIdSpeseProcessuali() {
			return idSpeseProcessuali;
		}
		
		public void setIdSpeseProcessuali(int idSpeseProcessuali) {
			this.idSpeseProcessuali = idSpeseProcessuali;
		}
		
	    
	    /**
	     * @return the speseProcessuali
	     */
	    public List<TTpSpeseProcessuali> getListaSpeseProcessuali() {

	        return listaSpeseProcessuali;
	    }
	    
	    /**
	     * @param listaSpeseProcessuali
	     *            the speseProcessuali to set
	     */
	    public void setListaSpeseProcessuali(List<TTpSpeseProcessuali> listaSpeseProcessuali) {

	        this.listaSpeseProcessuali = listaSpeseProcessuali;
	         
	    }

		//mev Spese Processuali

	 public int getCodiKIdSpeseProcessuali() {
			return codiKIdSpeseProcessuali;
	}    

	public void setCodiKIdSpeseProcessuali(int codiKIdSpeseProcessuali) {
		this.codiKIdSpeseProcessuali = codiKIdSpeseProcessuali;
	}

	
	public Date getDataInizioValidita() {
		return this.dataInizioValidita;
	}

	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}


	public Date getDataFineValidita() {
		return this.dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	
	public String getDescImportoSpese() {
		return this.descImportoSpese;
	}

	public void setDescImportoSpese(String descImportoSpese) {
		this.descImportoSpese = descImportoSpese;
	}


	public String getDescUtente() {
		return this.descUtente;
	}

	public void setDescUtente(String descUtente) {
		this.descUtente = descUtente;
	}

	public Date getDataUltAgg() {
		return this.dataUltAgg;
	}

	public void setDataUltAgg(Date dataUltAgg) {
		this.dataUltAgg = dataUltAgg;
	}
	
	public String getImportoSpeseFormattata() {
		return Utility.formattaImporto(importoSpese);
	}


	
}
