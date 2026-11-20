package it.giustizia.smp.dao;

import java.util.List;

import it.giustizia.smp.model.TAllineaFascPdoc;

public interface AllineaFascPdocDao {

	public Object updateTAllineaFascPdoc(final long idMsg, final String esito, final String risposta);

	public List<TAllineaFascPdoc> getDaInviare();

}