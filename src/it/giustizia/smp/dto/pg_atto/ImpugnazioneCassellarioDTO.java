package it.giustizia.smp.dto.pg_atto;

import java.math.BigDecimal;
import java.util.Date;


/***
 * Contiene le informazioni relative al decreto impugnato da trasferire al casellario
 * e mappa il risultato della procedura LISTA_IMPUGNAZIONI_GRADO_PREC
 * @author m.prisco
 *
 */
public class ImpugnazioneCassellarioDTO {
	
	private BigDecimal ID_ATTO_DECISORIO;
	private BigDecimal ID_UFF_DECISORIO;
	private BigDecimal ID_ESITO_DEC_DECISORIO_1;
	private String DESC_ESITO_DEC_DECISORIO_NSC_1;
	private String DESC_ESITO_DEC_DECISORIO_1;
	private BigDecimal ID_ESITO_DEC_DECISORIO_2;
	private String DESC_ESITO_DEC_DECISORIO_NSC_2;
	private String DESC_ESITO_DEC_DECISORIO_2;
	private Date DATA_EMISSIONE_DECISORIO;
	private String DESC_UFFICIO_OWNER;
	private String CODI_AUTORITA_NSC;
	private String CODI_SEDE_AUTORITA_NSC;
	private BigDecimal ANNO_ATTO;
	private BigDecimal NUMERO_ATTO;
	private String DESC_CAT_ATTO;
	
	
	
	public String getDESC_CAT_ATTO() {
		return DESC_CAT_ATTO;
	}
	public void setDESC_CAT_ATTO(String dESC_CAT_ATTO) {
		DESC_CAT_ATTO = dESC_CAT_ATTO;
	}
	public BigDecimal getID_ATTO_DECISORIO() {
		return ID_ATTO_DECISORIO;
	}
	public void setID_ATTO_DECISORIO(BigDecimal iD_ATTO_DECISORIO) {
		ID_ATTO_DECISORIO = iD_ATTO_DECISORIO;
	}
	public BigDecimal getID_UFF_DECISORIO() {
		return ID_UFF_DECISORIO;
	}
	public void setID_UFF_DECISORIO(BigDecimal iD_UFF_DECISORIO) {
		ID_UFF_DECISORIO = iD_UFF_DECISORIO;
	}
	public BigDecimal getID_ESITO_DEC_DECISORIO_1() {
		return ID_ESITO_DEC_DECISORIO_1;
	}
	public void setID_ESITO_DEC_DECISORIO_1(BigDecimal iD_ESITO_DEC_DECISORIO_1) {
		ID_ESITO_DEC_DECISORIO_1 = iD_ESITO_DEC_DECISORIO_1;
	}
	public String getDESC_ESITO_DEC_DECISORIO_NSC_1() {
		return DESC_ESITO_DEC_DECISORIO_NSC_1;
	}
	public void setDESC_ESITO_DEC_DECISORIO_NSC_1(String dESC_ESITO_DEC_DECISORIO_NSC_1) {
		DESC_ESITO_DEC_DECISORIO_NSC_1 = dESC_ESITO_DEC_DECISORIO_NSC_1;
	}
	public String getDESC_ESITO_DEC_DECISORIO_1() {
		return DESC_ESITO_DEC_DECISORIO_1;
	}
	public void setDESC_ESITO_DEC_DECISORIO_1(String dESC_ESITO_DEC_DECISORIO_1) {
		DESC_ESITO_DEC_DECISORIO_1 = dESC_ESITO_DEC_DECISORIO_1;
	}
	public BigDecimal getID_ESITO_DEC_DECISORIO_2() {
		return ID_ESITO_DEC_DECISORIO_2;
	}
	public void setID_ESITO_DEC_DECISORIO_2(BigDecimal iD_ESITO_DEC_DECISORIO_2) {
		ID_ESITO_DEC_DECISORIO_2 = iD_ESITO_DEC_DECISORIO_2;
	}
	public String getDESC_ESITO_DEC_DECISORIO_NSC_2() {
		return DESC_ESITO_DEC_DECISORIO_NSC_2;
	}
	public void setDESC_ESITO_DEC_DECISORIO_NSC_2(String dESC_ESITO_DEC_DECISORIO_NSC_2) {
		DESC_ESITO_DEC_DECISORIO_NSC_2 = dESC_ESITO_DEC_DECISORIO_NSC_2;
	}
	public String getDESC_ESITO_DEC_DECISORIO_2() {
		return DESC_ESITO_DEC_DECISORIO_2;
	}
	public void setDESC_ESITO_DEC_DECISORIO_2(String dESC_ESITO_DEC_DECISORIO_2) {
		DESC_ESITO_DEC_DECISORIO_2 = dESC_ESITO_DEC_DECISORIO_2;
	}
	public Date getDATA_EMISSIONE_DECISORIO() {
		return DATA_EMISSIONE_DECISORIO;
	}
	public void setDATA_EMISSIONE_DECISORIO(Date dATA_EMISSIONE_DECISORIO) {
		DATA_EMISSIONE_DECISORIO = dATA_EMISSIONE_DECISORIO;
	}
	public String getDESC_UFFICIO_OWNER() {
		return DESC_UFFICIO_OWNER;
	}
	public void setDESC_UFFICIO_OWNER(String dESC_UFFICIO_OWNER) {
		DESC_UFFICIO_OWNER = dESC_UFFICIO_OWNER;
	}
	public String getCODI_AUTORITA_NSC() {
		return CODI_AUTORITA_NSC;
	}
	public void setCODI_AUTORITA_NSC(String cODI_AUTORITA_NSC) {
		CODI_AUTORITA_NSC = cODI_AUTORITA_NSC;
	}
	public String getCODI_SEDE_AUTORITA_NSC() {
		return CODI_SEDE_AUTORITA_NSC;
	}
	public void setCODI_SEDE_AUTORITA_NSC(String cODI_SEDE_AUTORITA_NSC) {
		CODI_SEDE_AUTORITA_NSC = cODI_SEDE_AUTORITA_NSC;
	}
	public BigDecimal getANNO_ATTO() {
		return ANNO_ATTO;
	}
	public void setANNO_ATTO(BigDecimal aNNO_ATTO) {
		ANNO_ATTO = aNNO_ATTO;
	}
	public BigDecimal getNUMERO_ATTO() {
		return NUMERO_ATTO;
	}
	public void setNUMERO_ATTO(BigDecimal nUMERO_ATTO) {
		NUMERO_ATTO = nUMERO_ATTO;
	}
	
	
}
