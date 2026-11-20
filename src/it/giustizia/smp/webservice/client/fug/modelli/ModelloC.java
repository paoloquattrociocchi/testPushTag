package it.giustizia.smp.webservice.client.fug.modelli;

import it.giustizia.smp.webservice.client.fug.AcquisisciModelloC;
import it.giustizia.smp.webservice.client.fug.BeneficiariTipo;
import it.giustizia.smp.webservice.client.fug.DatiRichiestaInvioModelloC;
import it.giustizia.smp.webservice.client.fug.RichiestaModelloC;
import it.giustizia.smp.webservice.utility.JAXBUtil;

import java.util.ArrayList;
import java.util.List;

public class ModelloC extends ModelloCBase {

	private List<BeneficiariTipo> beneficiari;

	public List<BeneficiariTipo> getBeneficiari() {
		return beneficiari;
	}

	public void setBeneficiari(List<BeneficiariTipo> beneficiari) {
		this.beneficiari = beneficiari;
	}

	public void addBeneficiario(BeneficiariTipo beneficiario) {
		if (beneficiario != null){
			if (beneficiari == null) beneficiari = new ArrayList<BeneficiariTipo>();
			beneficiari.add(beneficiario);
		}
	}

	public void removeBeneficiario(BeneficiariTipo beneficiario) {
		if (beneficiari != null) {
			beneficiari.remove(beneficiario);
			if (beneficiari.size() == 0) beneficiari = null;
		}
	}

	private DatiRichiestaInvioModelloC.InfoProvvedimento getInfoProvvedimento() throws Exception {
		DatiRichiestaInvioModelloC.InfoProvvedimento out = new DatiRichiestaInvioModelloC.InfoProvvedimento();
		out.setDataProvvedimentoSequestro(getData(getDataProvvedimentoSequestro()));
		out.setDataProvvedimentoRestituzione(getData(getDataProvvedimentoRestituzione()));
		out.setFlagParziale(getFlagParzialeTipo());
		return out;
	}

	private DatiRichiestaInvioModelloC getDatiRichiestaInvioModello() throws Exception {
		DatiRichiestaInvioModelloC out = new DatiRichiestaInvioModelloC();
		out.setDatiRisorsa(getDatiRisorsaTipo());
		out.setDatiRegistroFUG(getDatiRegistroFUGTipo());
		out.setInfoProcesso(getInfoProcessoTipo());
		out.setInfoProvvedimento(getInfoProvvedimento());
		out.getBeneficiari().addAll(getBeneficiari());
		return out;
	}

	private RichiestaModelloC getRichiestaModello() throws Exception {
		RichiestaModelloC out = new RichiestaModelloC();
		out.setDatiIdentificativiInvio(getDatiIdentificativiInvioTipo());
		out.setDatiRichiestaInvioModelloC(getDatiRichiestaInvioModello());
		return out;
	}

	public AcquisisciModelloC creaModello() throws Exception {
		if ((getBeneficiari() == null) || (getBeneficiari().size() == 0))
			throw new Exception("La lista dei beneficiari non puo` essere vuota");
		AcquisisciModelloC out = new AcquisisciModelloC();
		out.setRichiestaModelloC(getRichiestaModello());
		JAXBUtil.printXML(out);
		return out;
	}
}
