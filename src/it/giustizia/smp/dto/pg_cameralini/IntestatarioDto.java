package it.giustizia.smp.dto.pg_cameralini;

import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.Utility;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class IntestatarioDto {

    private BigDecimal  CODI_K_ID_INTESTATARIO;
    private BigDecimal  CODI_K_ID_UFF_OWNER;
    private BigDecimal  CODI_FK_ID_TIPO_SOC;
    private Character   CODI_FK_ID_TIPO_SOGG;
    private String      DESC_NOME;
    private String      DESC_COGNOME;
    private Character   FLG_SESSO;
    @DateTimeFormat( pattern = "dd/MM/yyyy" )
    private Date        DATA_NASCITA;
    private BigDecimal  CODI_FK_ID_STATO_NASC;
    private BigDecimal  CODI_FK_ID_COM_NASC;
    private String      DESC_COM_ESTERO_NASC;
    private String      DESC_CODICE_FISCALE;
    private String      DESC_RAGIONE_SOC;
    private BigDecimal  CODI_FK_ID_STATO_SEDE;
    private BigDecimal  CODI_FK_ID_COM_SEDE;
    private String      DESC_COM_ESTERO_SEDE;
    private BigDecimal  CODI_FK_ID_TIPO_STRADA;
    private String      DESC_INDIRIZZO_SEDE;
    private String      DESC_PARTITA_IVA;
    private String      DESC_NUME_CCIAA;
    private String      DESC_COD_FISC;
    private String      DESC_CIVICO;
    private String      DESC_CAP;
    private String      DESC_UTENTE;
    private Date        DATA_ULT_AGG;
    private BigDecimal  CODI_FK_ID_SOGG_INT;
    private BigDecimal  CODI_FK_ID_UFF_OWNER;
    private BigDecimal  CODI_FK_ID_VER;
    private String      DESC_COMUNE;
    private String      DESC_COMUNE_SEDE;
    private String      DESC_SIGLA_PROVINCIA;
    private String      DESC_SIGLA_PROVINCIA_SEDE;
    private String      DESC_STATO;
    private String      DESC_STATO_SEDE;
    private String      DESC_TIPO_SOC;
    private String      DESC_TIPO_STRADA;
   
    
	public BigDecimal getCODI_K_ID_INTESTATARIO() {
		return CODI_K_ID_INTESTATARIO;
	}
	public void setCODI_K_ID_INTESTATARIO(BigDecimal cODI_K_ID_INTESTATARIO) {
		CODI_K_ID_INTESTATARIO = cODI_K_ID_INTESTATARIO;
	}
	public BigDecimal getCODI_K_ID_UFF_OWNER() {
		return CODI_K_ID_UFF_OWNER;
	}
	public void setCODI_K_ID_UFF_OWNER(BigDecimal cODI_K_ID_UFF_OWNER) {
		CODI_K_ID_UFF_OWNER = cODI_K_ID_UFF_OWNER;
	}
	public BigDecimal getCODI_FK_ID_TIPO_SOC() {
		return CODI_FK_ID_TIPO_SOC;
	}
	public void setCODI_FK_ID_TIPO_SOC(BigDecimal cODI_FK_ID_TIPO_SOC) {
		CODI_FK_ID_TIPO_SOC = cODI_FK_ID_TIPO_SOC;
	}
	public Character getCODI_FK_ID_TIPO_SOGG() {
		return CODI_FK_ID_TIPO_SOGG;
	}
	public void setCODI_FK_ID_TIPO_SOGG(Character cODI_FK_ID_TIPO_SOGG) {
		CODI_FK_ID_TIPO_SOGG = cODI_FK_ID_TIPO_SOGG;
	}
	public String getDESC_NOME() {
		return DESC_NOME;
	}
	public void setDESC_NOME(String dESC_NOME) {
		DESC_NOME = dESC_NOME;
	}
	public String getDESC_COGNOME() {
		return DESC_COGNOME;
	}
	public void setDESC_COGNOME(String dESC_COGNOME) {
		DESC_COGNOME = dESC_COGNOME;
	}
	public Character getFLG_SESSO() {
		return FLG_SESSO;
	}
	public void setFLG_SESSO(Character fLG_SESSO) {
		FLG_SESSO = fLG_SESSO;
	}
	public Date getDATA_NASCITA() {
		return DATA_NASCITA;
	}
	public void setDATA_NASCITA(Date dATA_NASCITA) {
		DATA_NASCITA = dATA_NASCITA;
	}
	public BigDecimal getCODI_FK_ID_STATO_NASC() {
		return CODI_FK_ID_STATO_NASC;
	}
	public void setCODI_FK_ID_STATO_NASC(BigDecimal cODI_FK_ID_STATO_NASC) {
		CODI_FK_ID_STATO_NASC = cODI_FK_ID_STATO_NASC;
	}
	public BigDecimal getCODI_FK_ID_COM_NASC() {
		return CODI_FK_ID_COM_NASC;
	}
	public void setCODI_FK_ID_COM_NASC(BigDecimal cODI_FK_ID_COM_NASC) {
		CODI_FK_ID_COM_NASC = cODI_FK_ID_COM_NASC;
	}
	public String getDESC_COM_ESTERO_NASC() {
		return DESC_COM_ESTERO_NASC;
	}
	public void setDESC_COM_ESTERO_NASC(String dESC_COM_ESTERO_NASC) {
		DESC_COM_ESTERO_NASC = dESC_COM_ESTERO_NASC;
	}
	public String getDESC_CODICE_FISCALE() {
		return DESC_CODICE_FISCALE;
	}
	public void setDESC_CODICE_FISCALE(String dESC_CODICE_FISCALE) {
		DESC_CODICE_FISCALE = dESC_CODICE_FISCALE;
	}
	public String getDESC_RAGIONE_SOC() {
		return DESC_RAGIONE_SOC;
	}
	public void setDESC_RAGIONE_SOC(String dESC_RAGIONE_SOC) {
		DESC_RAGIONE_SOC = dESC_RAGIONE_SOC;
	}
	public BigDecimal getCODI_FK_ID_STATO_SEDE() {
		return CODI_FK_ID_STATO_SEDE;
	}
	public void setCODI_FK_ID_STATO_SEDE(BigDecimal cODI_FK_ID_STATO_SEDE) {
		CODI_FK_ID_STATO_SEDE = cODI_FK_ID_STATO_SEDE;
	}
	public BigDecimal getCODI_FK_ID_COM_SEDE() {
		return CODI_FK_ID_COM_SEDE;
	}
	public void setCODI_FK_ID_COM_SEDE(BigDecimal cODI_FK_ID_COM_SEDE) {
		CODI_FK_ID_COM_SEDE = cODI_FK_ID_COM_SEDE;
	}
	public String getDESC_COM_ESTERO_SEDE() {
		return DESC_COM_ESTERO_SEDE;
	}
	public void setDESC_COM_ESTERO_SEDE(String dESC_COM_ESTERO_SEDE) {
		DESC_COM_ESTERO_SEDE = dESC_COM_ESTERO_SEDE;
	}
	public BigDecimal getCODI_FK_ID_TIPO_STRADA() {
		return CODI_FK_ID_TIPO_STRADA;
	}
	public void setCODI_FK_ID_TIPO_STRADA(BigDecimal cODI_FK_ID_TIPO_STRADA) {
		CODI_FK_ID_TIPO_STRADA = cODI_FK_ID_TIPO_STRADA;
	}
	public String getDESC_INDIRIZZO_SEDE() {
		return DESC_INDIRIZZO_SEDE;
	}
	public void setDESC_INDIRIZZO_SEDE(String dESC_INDIRIZZO_SEDE) {
		DESC_INDIRIZZO_SEDE = dESC_INDIRIZZO_SEDE;
	}
	public String getDESC_PARTITA_IVA() {
		return DESC_PARTITA_IVA;
	}
	public void setDESC_PARTITA_IVA(String dESC_PARTITA_IVA) {
		DESC_PARTITA_IVA = dESC_PARTITA_IVA;
	}
	public String getDESC_NUME_CCIAA() {
		return DESC_NUME_CCIAA;
	}
	public void setDESC_NUME_CCIAA(String dESC_NUME_CCIAA) {
		DESC_NUME_CCIAA = dESC_NUME_CCIAA;
	}
	public String getDESC_COD_FISC() {
		return DESC_COD_FISC;
	}
	public void setDESC_COD_FISC(String dESC_COD_FISC) {
		DESC_COD_FISC = dESC_COD_FISC;
	}
	public String getDESC_CIVICO() {
		return DESC_CIVICO;
	}
	public void setDESC_CIVICO(String dESC_CIVICO) {
		DESC_CIVICO = dESC_CIVICO;
	}
	public String getDESC_CAP() {
		return DESC_CAP;
	}
	public void setDESC_CAP(String dESC_CAP) {
		DESC_CAP = dESC_CAP;
	}
	public String getDESC_UTENTE() {
		return DESC_UTENTE;
	}
	public void setDESC_UTENTE(String dESC_UTENTE) {
		DESC_UTENTE = dESC_UTENTE;
	}
	public Date getDATA_ULT_AGG() {
		return DATA_ULT_AGG;
	}
	public void setDATA_ULT_AGG(Date dATA_ULT_AGG) {
		DATA_ULT_AGG = dATA_ULT_AGG;
	}
	public BigDecimal getCODI_FK_ID_SOGG_INT() {
		return CODI_FK_ID_SOGG_INT;
	}
	public void setCODI_FK_ID_SOGG_INT(BigDecimal cODI_FK_ID_SOGG_INT) {
		CODI_FK_ID_SOGG_INT = cODI_FK_ID_SOGG_INT;
	}
	public BigDecimal getCODI_FK_ID_UFF_OWNER() {
		return CODI_FK_ID_UFF_OWNER;
	}
	public void setCODI_FK_ID_UFF_OWNER(BigDecimal cODI_FK_ID_UFF_OWNER) {
		CODI_FK_ID_UFF_OWNER = cODI_FK_ID_UFF_OWNER;
	}
	public BigDecimal getCODI_FK_ID_VER() {
		return CODI_FK_ID_VER;
	}
	public void setCODI_FK_ID_VER(BigDecimal cODI_FK_ID_VER) {
		CODI_FK_ID_VER = cODI_FK_ID_VER;
	}
	public String getDESC_COMUNE() {
		return DESC_COMUNE;
	}
	public void setDESC_COMUNE(String dESC_COMUNE) {
		DESC_COMUNE = dESC_COMUNE;
	}
	public String getDESC_SIGLA_PROVINCIA() {
		return DESC_SIGLA_PROVINCIA;
	}
	public void setDESC_SIGLA_PROVINCIA(String dESC_SIGLA_PROVINCIA) {
		DESC_SIGLA_PROVINCIA = dESC_SIGLA_PROVINCIA;
	}
	public String getDESC_STATO() {
		return DESC_STATO;
	}
	public void setDESC_STATO(String dESC_STATO) {
		DESC_STATO = dESC_STATO;
	}
	
	public String getDESC_TIPO_SOC() {
		return DESC_TIPO_SOC;
	}
	public void setDESC_TIPO_SOC(String dESC_TIPO_SOC) {
		DESC_TIPO_SOC = dESC_TIPO_SOC;
	}
	public String getDESC_TIPO_STRADA() {
		return DESC_TIPO_STRADA;
	}
	public void setDESC_TIPO_STRADA(String dESC_TIPO_STRADA) {
		DESC_TIPO_STRADA = dESC_TIPO_STRADA;
	}
	public String getDescrizioneAmministratore() {
        StringBuilder descrizione = new StringBuilder();
        if (Constants.SOGGETTO_FISICO.equals(""+CODI_FK_ID_TIPO_SOGG)) {
            concatenaCampiFisici(descrizione);
        } else
            if (Constants.SOGGETTO_GIURIDICO.equals(""+CODI_FK_ID_TIPO_SOGG)) {
                concatenaCampiGiuridici(descrizione);
            } else {
                concatenaCampiFisici(descrizione);
                concatenaCampiGiuridici(descrizione);
            }
        return descrizione.toString();
    }
	
    private void concatenaCampiFisici(StringBuilder descrizione) {

        if (DESC_COGNOME != null) {
            descrizione.append(DESC_COGNOME);
        }
        if (DESC_NOME != null) {
            descrizione.append(" ");
            descrizione.append(DESC_NOME);
        }
        if ((DATA_NASCITA != null) || (CODI_FK_ID_COM_NASC != null)) {
            descrizione.append(" ");
            if ((FLG_SESSO != null) && (FLG_SESSO.equals('f') || FLG_SESSO.equals('F'))) {
                descrizione.append("nata");
            } else {
                descrizione.append("nato");
            }
            if (((CODI_FK_ID_COM_NASC != null) && (DESC_COMUNE != null)) || (DESC_COM_ESTERO_NASC != null)) {
            	//caso selezione stato italia
                if (CODI_FK_ID_COM_NASC != null && DESC_COMUNE != null) {
                	descrizione.append(" a ");
                    descrizione.append(DESC_COMUNE);
                } else {
                	//caso selezione stato estero
                    if (DESC_COM_ESTERO_NASC != null) {
                    	descrizione.append(" in ");
                    	descrizione.append(DESC_STATO);
                    	descrizione.append(" a ");
                        descrizione.append(DESC_COM_ESTERO_NASC); 
                    }
                }
            }
            if (DATA_NASCITA != null) {
                descrizione.append(" il ");
                descrizione.append(Utility.convertDateToString(DATA_NASCITA));
            }
        }
    }

    private void concatenaCampiGiuridici(StringBuilder descrizione) {

        if (DESC_RAGIONE_SOC != null) {
            descrizione.append(DESC_RAGIONE_SOC);
        }
        if (DESC_STATO != null) 
        {
            descrizione.append(" con sede in ");
            descrizione.append(DESC_STATO);
       }
        if (DESC_COMUNE != null) 
        {
            descrizione.append(" a ");
            descrizione.append(DESC_COMUNE);
       } 
        if (DESC_COM_ESTERO_SEDE != null) 
        {
            descrizione.append(" a ");
            descrizione.append(DESC_COM_ESTERO_SEDE);
       }
        if (DESC_TIPO_STRADA != null) {  
            descrizione.append(" in ");
            descrizione.append(DESC_TIPO_STRADA);
            descrizione.append(" ");
            if (DESC_INDIRIZZO_SEDE != null) {
                descrizione.append(DESC_INDIRIZZO_SEDE);
            } 
            if (DESC_CIVICO != null) {
                descrizione.append(" numero ");
                descrizione.append(DESC_CIVICO);
            } 
        }
        if (DESC_PARTITA_IVA != null) {
            descrizione.append(" partita IVA ");
            descrizione.append(DESC_PARTITA_IVA);
        } 
    }
    
    
    
    public String getDescComuneNascita() {
    	
    	StringBuilder descrizione = new StringBuilder();         
            if (((CODI_FK_ID_COM_NASC != null) && (CODI_FK_ID_COM_NASC != null)) || (DESC_COM_ESTERO_NASC != null)) {
            	//caso selezione stato italia
            	 if (CODI_FK_ID_COM_NASC != null && DESC_COMUNE != null) {
                        descrizione.append(DESC_COMUNE);
                } else {
                	//caso selezione stato estero
                    if (DESC_COM_ESTERO_NASC != null) {
                    	descrizione.append(DESC_COM_ESTERO_NASC);
                    	
                    }
                }
            }
            return descrizione.toString();
        }
   
    public String getComuneProvNasc() {
    	
    	StringBuilder descrizione = new StringBuilder();         
            if (((DESC_COMUNE != null) && (CODI_FK_ID_COM_NASC != null)) || (DESC_COM_ESTERO_NASC != null)) {
            	//caso selezione stato italia
            	 if (CODI_FK_ID_COM_NASC != null && DESC_COMUNE != null) {
                        descrizione.append(DESC_COMUNE);
                    	descrizione.append(" (");
                        descrizione.append(DESC_SIGLA_PROVINCIA);
                        descrizione.append(") ");
                } else {
                	//caso selezione stato estero
                    if (DESC_COM_ESTERO_NASC != null) {
                    	descrizione.append(DESC_COM_ESTERO_NASC);
                    	
                    }
                }
            }
            return descrizione.toString();
        }
   
    public String getComuneSede() {
    	//DA AGGIORNARE CON DESC_COMUNE_SEDE AL POSTO DI DESC_COMUNE
    	StringBuilder descrizione = new StringBuilder(); 
    	if (((DESC_COMUNE != null) && (CODI_FK_ID_COM_SEDE != null)) || (DESC_COM_ESTERO_SEDE != null)) {
    	//if (CODI_FK_ID_STATO_SEDE != null) {
    		//Long codIdStato = CODI_FK_ID_STATO_SEDE.longValue();
    		//if (codIdStato == 1) {
    		//caso selezione stato italia
       	 if (CODI_FK_ID_COM_SEDE != null && DESC_COMUNE != null) {
                descrizione.append(DESC_COMUNE);
            	descrizione.append(" (");
                descrizione.append(DESC_SIGLA_PROVINCIA);
                descrizione.append(") ");
    		} else {
    			if (DESC_COM_ESTERO_SEDE != null) {
    				descrizione.append(DESC_COM_ESTERO_SEDE);
    			}
    		}
    	}
            return descrizione.toString();
        }
	public String getDESC_COMUNE_SEDE() {
		return DESC_COMUNE_SEDE;
	}
	public void setDESC_COMUNE_SEDE(String dESC_COMUNE_SEDE) {
		DESC_COMUNE_SEDE = dESC_COMUNE_SEDE;
	}
	public String getDESC_SIGLA_PROVINCIA_SEDE() {
		return DESC_SIGLA_PROVINCIA_SEDE;
	}
	public void setDESC_SIGLA_PROVINCIA_SEDE(String dESC_SIGLA_PROVINCIA_SEDE) {
		DESC_SIGLA_PROVINCIA_SEDE = dESC_SIGLA_PROVINCIA_SEDE;
	}
	public String getDESC_STATO_SEDE() {
		return DESC_STATO_SEDE;
	}
	public void setDESC_STATO_SEDE(String dESC_STATO_SEDE) {
		DESC_STATO_SEDE = dESC_STATO_SEDE;
	}
   
    
}
    