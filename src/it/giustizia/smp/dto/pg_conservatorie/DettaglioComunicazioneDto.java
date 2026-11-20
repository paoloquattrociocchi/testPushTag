package it.giustizia.smp.dto.pg_conservatorie;

import java.math.BigDecimal;
import java.util.Date;

public class DettaglioComunicazioneDto
{

	private BigDecimal CODI_K_ID_NOTA;
	private BigDecimal CODI_K_ID_UFF_OWNER;
	private String     DESC_FISCALE_PU;
	private BigDecimal NUME_REPERT_1;
	private BigDecimal NUME_REPERT_2;
	private BigDecimal NUME_PROGR_NOTA;
	private String     DESC_TITOLO;
	private Date       DATA_TITOLO;
	private Character  DESC_TIPO_PUBBL_UFF;
	private String     DESC_PUBBL_UFF;
	private String     DESC_SEDE_PUBBL_UFF;
	private String     DESC_PROV_PUBBL_UFF;
	private String     DESC_SPECIE_ATTO;
	private String     DESC_ATTO;
	private BigDecimal NUME_UNIT_NEG;
	private BigDecimal NUME_IMMOBILI;
	private BigDecimal NUME_SOGG_FAVORE;
	private BigDecimal NUME_SOGG_CONTRO;
	private String     DESC_CONSERVAT;
	private Character  FLG_NOTA_COLLEGATA;
	private Character  FLG_STATO;
	private String     DESC_UTENTE;
	private Date       DATA_ULT_AGG;
	private BigDecimal CODI_FK_ID_TIPO_ATTO;
	private BigDecimal CODI_FK_ID_CAT_ATTO;
	private Character  CODI_FK_ID_TIPO_NOTA;
	private BigDecimal CODI_K_ID_CONSERV;
	private String     CODI_FK_TIPO_COMUNIC;
	private BigDecimal CODI_FK_ID_PROC;

	private String     DESC_TIPO_NOTA;
	private String     DESC_CAT_ATTO;
	private String     DESC_CONSERV;
	private String     DESC_TIPO_COMUNIC;

	public BigDecimal getCODI_K_ID_NOTA()
	{
		return CODI_K_ID_NOTA;
	}

	public void setCODI_K_ID_NOTA( BigDecimal cODI_K_ID_NOTA )
	{
		CODI_K_ID_NOTA = cODI_K_ID_NOTA;
	}

	public BigDecimal getCODI_K_ID_UFF_OWNER()
	{
		return CODI_K_ID_UFF_OWNER;
	}

	public void setCODI_K_ID_UFF_OWNER( BigDecimal cODI_K_ID_UFF_OWNER )
	{
		CODI_K_ID_UFF_OWNER = cODI_K_ID_UFF_OWNER;
	}

	public Character getCODI_FK_ID_TIPO_NOTA()
	{
		return CODI_FK_ID_TIPO_NOTA;
	}

	public void setCODI_FK_ID_TIPO_NOTA( Character cODI_FK_ID_TIPO_NOTA )
	{
		CODI_FK_ID_TIPO_NOTA = cODI_FK_ID_TIPO_NOTA;
	}

	public String getDESC_FISCALE_PU()
	{
		return DESC_FISCALE_PU;
	}

	public void setDESC_FISCALE_PU( String dESC_FISCALE_PU )
	{
		DESC_FISCALE_PU = dESC_FISCALE_PU;
	}

	public BigDecimal getNUME_REPERT_1()
	{
		return NUME_REPERT_1;
	}

	public void setNUME_REPERT_1( BigDecimal nUME_REPERT_1 )
	{
		NUME_REPERT_1 = nUME_REPERT_1;
	}

	public BigDecimal getNUME_REPERT_2()
	{
		return NUME_REPERT_2;
	}

	public void setNUME_REPERT_2( BigDecimal nUME_REPERT_2 )
	{
		NUME_REPERT_2 = nUME_REPERT_2;
	}

	public BigDecimal getNUME_PROGR_NOTA()
	{
		return NUME_PROGR_NOTA;
	}

	public void setNUME_PROGR_NOTA( BigDecimal nUME_PROGR_NOTA )
	{
		NUME_PROGR_NOTA = nUME_PROGR_NOTA;
	}

	public String getDESC_TITOLO()
	{
		return DESC_TITOLO;
	}

	public void setDESC_TITOLO( String dESC_TITOLO )
	{
		DESC_TITOLO = dESC_TITOLO;
	}

	public Date getDATA_TITOLO()
	{
		return DATA_TITOLO;
	}

	public void setDATA_TITOLO( Date dATA_TITOLO )
	{
		DATA_TITOLO = dATA_TITOLO;
	}

	public Character getDESC_TIPO_PUBBL_UFF()
	{
		return DESC_TIPO_PUBBL_UFF;
	}

	public void setDESC_TIPO_PUBBL_UFF( Character dESC_TIPO_PUBBL_UFF )
	{
		DESC_TIPO_PUBBL_UFF = dESC_TIPO_PUBBL_UFF;
	}

	public String getDESC_PUBBL_UFF()
	{
		return DESC_PUBBL_UFF;
	}

	public void setDESC_PUBBL_UFF( String dESC_PUBBL_UFF )
	{
		DESC_PUBBL_UFF = dESC_PUBBL_UFF;
	}

	public String getDESC_SEDE_PUBBL_UFF()
	{
		return DESC_SEDE_PUBBL_UFF;
	}

	public void setDESC_SEDE_PUBBL_UFF( String dESC_SEDE_PUBBL_UFF )
	{
		DESC_SEDE_PUBBL_UFF = dESC_SEDE_PUBBL_UFF;
	}

	public String getDESC_SPECIE_ATTO()
	{
		return DESC_SPECIE_ATTO;
	}

	public void setDESC_SPECIE_ATTO( String dESC_SPECIE_ATTO )
	{
		DESC_SPECIE_ATTO = dESC_SPECIE_ATTO;
	}

	public String getDESC_ATTO()
	{
		return DESC_ATTO;
	}

	public void setDESC_ATTO( String dESC_ATTO )
	{
		DESC_ATTO = dESC_ATTO;
	}

	public BigDecimal getNUME_UNIT_NEG()
	{
		return NUME_UNIT_NEG;
	}

	public void setNUME_UNIT_NEG( BigDecimal nUME_UNIT_NEG )
	{
		NUME_UNIT_NEG = nUME_UNIT_NEG;
	}

	public BigDecimal getNUME_IMMOBILI()
	{
		return NUME_IMMOBILI;
	}

	public void setNUME_IMMOBILI( BigDecimal nUME_IMMOBILI )
	{
		NUME_IMMOBILI = nUME_IMMOBILI;
	}

	public BigDecimal getNUME_SOGG_FAVORE()
	{
		return NUME_SOGG_FAVORE;
	}

	public void setNUME_SOGG_FAVORE( BigDecimal nUME_SOGG_FAVORE )
	{
		NUME_SOGG_FAVORE = nUME_SOGG_FAVORE;
	}

	public BigDecimal getNUME_SOGG_CONTRO()
	{
		return NUME_SOGG_CONTRO;
	}

	public void setNUME_SOGG_CONTRO( BigDecimal nUME_SOGG_CONTRO )
	{
		NUME_SOGG_CONTRO = nUME_SOGG_CONTRO;
	}

	public Character getFLG_STATO()
	{
		return FLG_STATO;
	}

	public void setFLG_STATO( Character fLG_STATO )
	{
		FLG_STATO = fLG_STATO;
	}

	public BigDecimal getCODI_FK_ID_TIPO_ATTO()
	{
		return CODI_FK_ID_TIPO_ATTO;
	}

	public void setCODI_FK_ID_TIPO_ATTO( BigDecimal cODI_FK_ID_TIPO_ATTO )
	{
		CODI_FK_ID_TIPO_ATTO = cODI_FK_ID_TIPO_ATTO;
	}

	public BigDecimal getCODI_FK_ID_CAT_ATTO()
	{
		return CODI_FK_ID_CAT_ATTO;
	}

	public void setCODI_FK_ID_CAT_ATTO( BigDecimal cODI_FK_ID_CAT_ATTO )
	{
		CODI_FK_ID_CAT_ATTO = cODI_FK_ID_CAT_ATTO;
	}

	public BigDecimal getCODI_K_ID_CONSERV()
	{
		return CODI_K_ID_CONSERV;
	}

	public void setCODI_K_ID_CONSERV( BigDecimal cODI_K_ID_CONSERV )
	{
		CODI_K_ID_CONSERV = cODI_K_ID_CONSERV;
	}

	public String getDESC_TIPO_NOTA()
	{
		return DESC_TIPO_NOTA;
	}

	public void setDESC_TIPO_NOTA( String dESC_TIPO_NOTA )
	{
		DESC_TIPO_NOTA = dESC_TIPO_NOTA;
	}

	public String getDESC_CAT_ATTO()
	{
		return DESC_CAT_ATTO;
	}

	public void setDESC_CAT_ATTO( String dESC_CAT_ATTO )
	{
		DESC_CAT_ATTO = dESC_CAT_ATTO;
	}

	public String getDESC_CONSERV()
	{
		return DESC_CONSERV;
	}

	public void setDESC_CONSERV( String dESC_CONSERV )
	{
		DESC_CONSERV = dESC_CONSERV;
	}

	public String getDESC_PROV_PUBBL_UFF()
	{
		return DESC_PROV_PUBBL_UFF;
	}

	public void setDESC_PROV_PUBBL_UFF( String dESC_PROV_PUBBL_UFF )
	{
		DESC_PROV_PUBBL_UFF = dESC_PROV_PUBBL_UFF;
	}

	public BigDecimal getCODI_FK_ID_PROC()
    {
	    return CODI_FK_ID_PROC;
    }

	public void setCODI_FK_ID_PROC( BigDecimal cODI_FK_ID_PROC )
    {
	    CODI_FK_ID_PROC = cODI_FK_ID_PROC;
    }

	public String getDESC_CONSERVAT()
    {
	    return DESC_CONSERVAT;
    }

	public void setDESC_CONSERVAT( String dESC_CONSERVAT )
    {
	    DESC_CONSERVAT = dESC_CONSERVAT;
    }

	public Character getFLG_NOTA_COLLEGATA()
    {
	    return FLG_NOTA_COLLEGATA;
    }

	public void setFLG_NOTA_COLLEGATA( Character fLG_NOTA_COLLEGATA )
    {
	    FLG_NOTA_COLLEGATA = fLG_NOTA_COLLEGATA;
    }

	public String getDESC_UTENTE()
    {
	    return DESC_UTENTE;
    }

	public void setDESC_UTENTE( String dESC_UTENTE )
    {
	    DESC_UTENTE = dESC_UTENTE;
    }

	public Date getDATA_ULT_AGG()
    {
	    return DATA_ULT_AGG;
    }

	public void setDATA_ULT_AGG( Date dATA_ULT_AGG )
    {
	    DATA_ULT_AGG = dATA_ULT_AGG;
    }

	public String getCODI_FK_TIPO_COMUNIC() {
		return CODI_FK_TIPO_COMUNIC;
	}

	public void setCODI_FK_TIPO_COMUNIC(String cODI_FK_TIPO_COMUNIC) {
		CODI_FK_TIPO_COMUNIC = cODI_FK_TIPO_COMUNIC;
	}

	public String getDESC_TIPO_COMUNIC() {
		return DESC_TIPO_COMUNIC;
	}

	public void setDESC_TIPO_COMUNIC(String dESC_TIPO_COMUNIC) {
		DESC_TIPO_COMUNIC = dESC_TIPO_COMUNIC;
	}

}
