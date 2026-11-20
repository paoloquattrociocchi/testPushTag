package it.giustizia.smp.webservice.client.documentale.model.general;

import it.giustizia.smp.webservice.client.documentale.stub.Component;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "codicefascicolo", "codicefascicolopadre", "oggettofascicolo", "security" })
@XmlRootElement(name = "fascicolo")
public final class Fascicolo extends Component {

	public static final String	CODICE_PADRE_NESSUNO	= "0";
	private static final long	serialVersionUID		= 1L;
	@XmlElement(required = true)
	private String				codicefascicolo;
	@XmlElement(required = true, nillable = true)
	private String				codicefascicolopadre;
	@XmlElement(required = true)
	private String				oggettofascicolo;
	@XmlElement(required = true)
	private Security			security;

	public final String getCodicefascicolo() {

		return codicefascicolo;
	}

	public final String getCodicefascicolopadre() {

		return codicefascicolopadre;
	}

	public final String getOggettofascicolo() {

		return oggettofascicolo;
	}

	public final Security getSecurity() {

		return security;
	}

	public final void setCodicefascicolo(final String codicefascicolo) {

		this.codicefascicolo = codicefascicolo;
	}

	public final void setCodicefascicolopadre(final String codicefascicolopadre) {

		this.codicefascicolopadre = codicefascicolopadre;
	}

	public final void setOggettofascicolo(final String oggettofascicolo) {

		this.oggettofascicolo = oggettofascicolo;
	}

	public final void setSecurity(final Security security) {

		this.security = security;
	}

	public static final Fascicolo getInstance(final String codice, final String oggetto, final String codicePadre) {

		final Fascicolo fascicolo = new Fascicolo();
		fascicolo.setCodicefascicolo(codice);
		fascicolo.setOggettofascicolo(oggetto);
		fascicolo.setCodicefascicolopadre(codicePadre);
		fascicolo.setSecurity(Security.NONE);
		return fascicolo;
	}
}
