package it.giustizia.smp.dto.pg_conservatorie;

import java.math.BigDecimal;

public class ComuneConservatoriaDto
{
	private String	   CHIAVECOMBO;
    private BigDecimal IDCOMUNE;
	private String     DESCCOMUNE;
    private String     SIGLAPROVINCIA;
    private String     CODICECATASTO;

	public ComuneConservatoriaDto()
	{}

	public String getCHIAVECOMBO() {
		return CHIAVECOMBO;
	}

	public void setCHIAVECOMBO(String cHIAVECOMBO) {
		CHIAVECOMBO = cHIAVECOMBO;
	}

	public BigDecimal getIDCOMUNE() {
		return IDCOMUNE;
	}

	public void setIDCOMUNE(BigDecimal iDCOMUNE) {
		IDCOMUNE = iDCOMUNE;
	}

	public String getDESCCOMUNE() {
		return DESCCOMUNE;
	}

	public void setDESCCOMUNE(String dESCCOMUNE) {
		DESCCOMUNE = dESCCOMUNE;
	}

	public String getSIGLAPROVINCIA() {
		return SIGLAPROVINCIA;
	}

	public void setSIGLAPROVINCIA(String sIGLAPROVINCIA) {
		SIGLAPROVINCIA = sIGLAPROVINCIA;
	}

	public String getCODICECATASTO() {
		return CODICECATASTO;
	}

	public void setCODICECATASTO(String cODICECATASTO) {
		CODICECATASTO = cODICECATASTO;
	}

}
