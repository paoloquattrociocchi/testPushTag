/**
 * 
 */
package it.giustizia.smp.form;

import java.util.ArrayList;
import java.util.List;

/**
 * @author antonio
 *
 */
public class BeneSoggettoPoliziaForm {

    private SoggettoForm           				soggetto;
    private List<MisuraPersonaleFlussiForm>     misurePersonali = new ArrayList<MisuraPersonaleFlussiForm>();
    private String 								numeProgPersona;
    private List<MisuraPatrimonialeFlussiForm>	misurePatrimoniali = new ArrayList<MisuraPatrimonialeFlussiForm>();
    
	public SoggettoForm getSoggetto() {
		return soggetto;
	}
	public void setSoggetto(SoggettoForm soggetto) {
		this.soggetto = soggetto;
	}
	public List<MisuraPersonaleFlussiForm> getMisurePersonali() {
		return misurePersonali;
	}
	public void setMisurePersonali(List<MisuraPersonaleFlussiForm> misurePersonali) {
		this.misurePersonali = misurePersonali;
	}
	public String getNumeProgPersona() {
		return numeProgPersona;
	}
	public void setNumeProgPersona(String numeProgPersona) {
		this.numeProgPersona = numeProgPersona;
	}
	public List<MisuraPatrimonialeFlussiForm> getMisurePatrimoniali() {
		return misurePatrimoniali;
	}
	public void setMisurePatrimoniali(
			List<MisuraPatrimonialeFlussiForm> misurePatrimoniali) {
		this.misurePatrimoniali = misurePatrimoniali;
	}
    
}
