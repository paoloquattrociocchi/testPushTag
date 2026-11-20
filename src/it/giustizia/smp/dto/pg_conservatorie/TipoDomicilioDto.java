package it.giustizia.smp.dto.pg_conservatorie;


public class TipoDomicilioDto {

	private String descIndirRes;  /* T_REL_PROC_SOGINT.DESC_INDIR_RES Descrizione indirizzo di residenza  */
	private String descIndirDom;  /* T_REL_PROC_SOGINT.DESC_INDIR_DOM Descrizione indirizzo del domicilio */
	
	public String getDescIndirRes() {
		return descIndirRes;
	}
	public void setDescIndirRes(String descIndirRes) {
		this.descIndirRes = descIndirRes;
	}
	public String getDescIndirDom() {
		return descIndirDom;
	}
	public void setDescIndirDom(String descIndirDom) {
		this.descIndirDom = descIndirDom;
	}
}
