package it.giustizia.smp.xml;

import static it.giustizia.smp.utility.NumbersUtility.getBigInteger;
import it.giustizia.smp.xml.amministratori.ProcedimentoType;

public abstract class RelazioneAmm {

	private Procedimento procedimento = new Procedimento();

	public Procedimento getProcedimento() {
		return procedimento;
	}

	public void setProcedimento(Procedimento procedimento) {
		this.procedimento = (procedimento != null) ? procedimento : getProcedimento();
	}

	ProcedimentoType getProcedimentoTR() {
		ProcedimentoType out = new ProcedimentoType();
		out.setAnnoProcedimento(getBigInteger(getProcedimento().getAnnoProcedimento()));
		out.setIdentificativoProcedimento(getProcedimento().getId());
		out.setNumeroProcedimento(getProcedimento().getNumeroProcedimento());
		out.setUfficioCompetente(getBigInteger(getProcedimento().getUfficioCompetente()));
		return out;
	}

}
