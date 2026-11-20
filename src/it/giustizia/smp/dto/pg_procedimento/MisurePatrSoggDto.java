package it.giustizia.smp.dto.pg_procedimento;

import it.giustizia.smp.utility.Utility;

import java.math.BigDecimal;
import java.util.Date;

public class MisurePatrSoggDto {

    private Short annoAtto;
	private Long numeroAtto;
	private String richiesta;

	private BigDecimal CODI_K_ID_ATTO;
	private BigDecimal CODI_FK_ID_TIPO_ATTO;
	private BigDecimal CODI_FK_ID_CAT_ATTO;
	
	private Date DATA_DEPOSITO;
    
	private BigDecimal CODI_K_ID_BENE;
    private String     DESCR_BENE;
    private String     DESC_CAT_ATTO;

	private String     DESC_TIPO_MISURA;
    private String     DESC_TIPO_ESITO_FINALE;
    private String     DESC_ESITO1;
    private String     DESC_ESITO2;
    private String     DESC_ESITO3;

    private String     DESC_NORMATIVA;
    private String     DESC_TIPO_INT;
    private BigDecimal NUME_QUOTA;
    private BigDecimal NUME_FRAZIONE_NUM;        
    private BigDecimal NUME_FRAZIONE_DEN;          
    private String     DESC_TIPO_STATO_BENE;
    private Date       DATA_DEFINITIVITA;

    private String     DESC_COGNOME;
    private String     DESC_NOME;
    private Date       DATA_NASCITA;
    private Character  FLG_SESSO;
    private String     DESC_COM_ESTERO_NASC;
    private String     DESC_STATO_FIS;
    private String     DESC_COMUNE_FIS;
    private String     DESC_SIGLA_PROV_FIS;

    private String     DESC_RAGIONE_SOC;
    private String     DESC_PARTITA_IVA;
    private String     DESC_COM_ESTERO_SEDE;

    private String     DESC_STATO_GIUR;
    private String     DESC_COMUNE_GIUR;
    private String     DESC_SIGLA_PROV_GIUR;

	public Short getAnnoAtto() {
		return annoAtto;
	}
	public void setAnnoAtto(Short annoAtto) {
		this.annoAtto = annoAtto;
	}
	public Long getNumeroAtto() {
		return numeroAtto;
	}
	public void setNumeroAtto(Long numeroAtto) {
		this.numeroAtto = numeroAtto;
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
	public BigDecimal getCODI_K_ID_BENE() {
		return CODI_K_ID_BENE;
	}
	public void setCODI_K_ID_BENE(BigDecimal cODI_K_ID_BENE) {
		CODI_K_ID_BENE = cODI_K_ID_BENE;
	}
	public String getDESCR_BENE() {
		return DESCR_BENE;
	}
	public void setDESCR_BENE(String dESCR_BENE) {
		DESCR_BENE = dESCR_BENE;
	}
	public String getDESC_CAT_ATTO() {
		return DESC_CAT_ATTO;
	}
	public void setDESC_CAT_ATTO(String dESC_CAT_ATTO) {
		DESC_CAT_ATTO = dESC_CAT_ATTO;
	}
	public String getDESC_TIPO_MISURA() {
		return DESC_TIPO_MISURA;
	}
	public void setDESC_TIPO_MISURA(String dESC_TIPO_MISURA) {
		DESC_TIPO_MISURA = dESC_TIPO_MISURA;
	}
	public String getDESC_TIPO_ESITO_FINALE() {
		return DESC_TIPO_ESITO_FINALE;
	}
	public void setDESC_TIPO_ESITO_FINALE(String dESC_TIPO_ESITO_FINALE) {
		DESC_TIPO_ESITO_FINALE = dESC_TIPO_ESITO_FINALE;
	}
	public String getDESC_NORMATIVA() {
		return DESC_NORMATIVA;
	}
	public void setDESC_NORMATIVA(String dESC_NORMATIVA) {
		DESC_NORMATIVA = dESC_NORMATIVA;
	}
	public String getDESC_TIPO_INT() {
		return DESC_TIPO_INT;
	}
	public void setDESC_TIPO_INT(String dESC_TIPO_INT) {
		DESC_TIPO_INT = dESC_TIPO_INT;
	}
	public BigDecimal getNUME_QUOTA() {
		return NUME_QUOTA;
	}
	public void setNUME_QUOTA(BigDecimal nUME_QUOTA) {
		NUME_QUOTA = nUME_QUOTA;
	}
	public String getDESC_TIPO_STATO_BENE() {
		return DESC_TIPO_STATO_BENE;
	}
	public void setDESC_TIPO_STATO_BENE(String dESC_TIPO_STATO_BENE) {
		DESC_TIPO_STATO_BENE = dESC_TIPO_STATO_BENE;
	}
	public Date getDATA_DEFINITIVITA() {
		return DATA_DEFINITIVITA;
	}
	public void setDATA_DEFINITIVITA(Date dATA_DEFINITIVITA) {
		DATA_DEFINITIVITA = dATA_DEFINITIVITA;
	}
	public String getDESC_COGNOME() {
		return DESC_COGNOME;
	}
	public void setDESC_COGNOME(String dESC_COGNOME) {
		DESC_COGNOME = dESC_COGNOME;
	}
	public String getDESC_NOME() {
		return DESC_NOME;
	}
	public void setDESC_NOME(String dESC_NOME) {
		DESC_NOME = dESC_NOME;
	}
	public Date getDATA_NASCITA() {
		return DATA_NASCITA;
	}
	public void setDATA_NASCITA(Date dATA_NASCITA) {
		DATA_NASCITA = dATA_NASCITA;
	}
	public String getDESC_COM_ESTERO_NASC() {
		return DESC_COM_ESTERO_NASC;
	}
	public void setDESC_COM_ESTERO_NASC(String dESC_COM_ESTERO_NASC) {
		DESC_COM_ESTERO_NASC = dESC_COM_ESTERO_NASC;
	}
	public String getDESC_STATO_FIS() {
		return DESC_STATO_FIS;
	}
	public void setDESC_STATO_FIS(String dESC_STATO_FIS) {
		DESC_STATO_FIS = dESC_STATO_FIS;
	}
	public String getDESC_COMUNE_FIS() {
		return DESC_COMUNE_FIS;
	}
	public void setDESC_COMUNE_FIS(String dESC_COMUNE_FIS) {
		DESC_COMUNE_FIS = dESC_COMUNE_FIS;
	}
	public String getDESC_SIGLA_PROV_FIS() {
		return DESC_SIGLA_PROV_FIS;
	}
	public void setDESC_SIGLA_PROV_FIS(String dESC_SIGLA_PROV_FIS) {
		DESC_SIGLA_PROV_FIS = dESC_SIGLA_PROV_FIS;
	}
	public String getDESC_RAGIONE_SOC() {
		return DESC_RAGIONE_SOC;
	}
	public void setDESC_RAGIONE_SOC(String dESC_RAGIONE_SOC) {
		DESC_RAGIONE_SOC = dESC_RAGIONE_SOC;
	}
	public String getDESC_PARTITA_IVA() {
		return DESC_PARTITA_IVA;
	}
	public void setDESC_PARTITA_IVA(String dESC_PARTITA_IVA) {
		DESC_PARTITA_IVA = dESC_PARTITA_IVA;
	}
	public String getDESC_COM_ESTERO_SEDE() {
		return DESC_COM_ESTERO_SEDE;
	}
	public void setDESC_COM_ESTERO_SEDE(String dESC_COM_ESTERO_SEDE) {
		DESC_COM_ESTERO_SEDE = dESC_COM_ESTERO_SEDE;
	}
	public String getDESC_STATO_GIUR() {
		return DESC_STATO_GIUR;
	}
	public void setDESC_STATO_GIUR(String dESC_STATO_GIUR) {
		DESC_STATO_GIUR = dESC_STATO_GIUR;
	}
	public String getDESC_COMUNE_GIUR() {
		return DESC_COMUNE_GIUR;
	}
	public void setDESC_COMUNE_GIUR(String dESC_COMUNE_GIUR) {
		DESC_COMUNE_GIUR = dESC_COMUNE_GIUR;
	}
	public String getDESC_SIGLA_PROV_GIUR() {
		return DESC_SIGLA_PROV_GIUR;
	}
	public void setDESC_SIGLA_PROV_GIUR(String dESC_SIGLA_PROV_GIUR) {
		DESC_SIGLA_PROV_GIUR = dESC_SIGLA_PROV_GIUR;
	}
    
	public String getDescrizioneSogTit() {
		
		if(getDESC_COGNOME()!=null) {
			return getDescrFisica();
		} else if(getDESC_RAGIONE_SOC()!=null) {
			return getDescrGiur();
		} else return null;
			
	}
    private String getDescrFisica() {

    	StringBuilder descrizione = new StringBuilder();
    	
        if (DESC_COGNOME != null) {
            descrizione.append(DESC_COGNOME);
        }
        if (DESC_NOME != null) {
            descrizione.append(" ");
            descrizione.append(DESC_NOME);
        }
        if (DATA_NASCITA != null || DESC_COMUNE_FIS != null) {
            descrizione.append(" ");
            if ((FLG_SESSO != null) && FLG_SESSO.equals("F")) {
                descrizione.append("nata");
            } else {
                descrizione.append("nato");
            }
            if ( DESC_COMUNE_FIS != null || DESC_COM_ESTERO_NASC != null) {
                if (DESC_COMUNE_FIS != null) {
                	descrizione.append(" a ");
                    descrizione.append(DESC_COMUNE_FIS);
                } else {
                    if (DESC_COM_ESTERO_NASC != null) {
                    	descrizione.append(" in ");
                    	descrizione.append(DESC_STATO_FIS);
                    	descrizione.append(" (");
                        descrizione.append(DESC_COM_ESTERO_NASC);
                        descrizione.append(") ");
                    }
                }
            }
            if (DATA_NASCITA != null) {
                descrizione.append(" il ");
                descrizione.append(Utility.convertDateToString(DATA_NASCITA));
            }
        }
        
        return descrizione.toString();
    }

    private String getDescrGiur() {

    	StringBuilder descrizione = new StringBuilder();
        if (DESC_RAGIONE_SOC != null) {
            descrizione.append(DESC_RAGIONE_SOC);
        }
        if (DESC_COMUNE_GIUR != null || DESC_COM_ESTERO_SEDE != null) {
            descrizione.append(" con sede in ");
            if (DESC_COMUNE_GIUR != null) {
                descrizione.append(DESC_COMUNE_GIUR);
            } else
                if (DESC_COM_ESTERO_SEDE != null) {
                    descrizione.append(DESC_COM_ESTERO_SEDE);
                }
        }
        if (DESC_PARTITA_IVA != null) {
            descrizione.append(" partita IVA ");
            descrizione.append(DESC_PARTITA_IVA);
        }
        
        return descrizione.toString();        
    }
    
	public Character getFLG_SESSO() {
		return FLG_SESSO;
	}
	public void setFLG_SESSO(Character fLG_SESSO) {
		FLG_SESSO = fLG_SESSO;
	}

    public String getDESC_ESITO1() {
		return DESC_ESITO1;
	}
	public void setDESC_ESITO1(String dESC_ESITO1) {
		DESC_ESITO1 = dESC_ESITO1;
	}
	public String getDESC_ESITO2() {
		return DESC_ESITO2;
	}
	public void setDESC_ESITO2(String dESC_ESITO2) {
		DESC_ESITO2 = dESC_ESITO2;
	}
	public String getDESC_ESITO3() {
		return DESC_ESITO3;
	}
	public void setDESC_ESITO3(String dESC_ESITO3) {
		DESC_ESITO3 = dESC_ESITO3;
	}
	public BigDecimal getNUME_FRAZIONE_NUM() {
		return NUME_FRAZIONE_NUM;
	}
	public void setNUME_FRAZIONE_NUM(BigDecimal nUME_FRAZIONE_NUM) {
		NUME_FRAZIONE_NUM = nUME_FRAZIONE_NUM;
	}
	public BigDecimal getNUME_FRAZIONE_DEN() {
		return NUME_FRAZIONE_DEN;
	}
	public void setNUME_FRAZIONE_DEN(BigDecimal nUME_FRAZIONE_DEN) {
		NUME_FRAZIONE_DEN = nUME_FRAZIONE_DEN;
	}
	public Date getDATA_DEPOSITO() {
		return DATA_DEPOSITO;
	}
	public void setDATA_DEPOSITO(Date dATA_DEPOSITO) {
		DATA_DEPOSITO = dATA_DEPOSITO;
	}
	public String getRichiesta() {
		return richiesta;
	}
	public void setRichiesta(String richiesta) {
		this.richiesta = richiesta;
	}
}
