package it.giustizia.smp.webservice.client.documentale.model.general;

import it.giustizia.smp.webservice.client.documentale.stub.Component;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "codiceapplicazione", "password", "idutente", "idufficio" })
@XmlRootElement(name = "autenticazione")
public final class Autenticazione extends Component {

	private static final long	serialVersionUID	= 1L;
	@XmlElement(required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	private String				codiceapplicazione;
	@XmlElement(required = true)
	private BigInteger			idufficio;
	@XmlElement(required = true)
	private BigInteger			idutente;
	@XmlElement(required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	private String				password;

	public final String getCodiceapplicazione() {

		return codiceapplicazione;
	}

	public final BigInteger getIdufficio() {

		return idufficio;
	}

	public final BigInteger getIdutente() {

		return idutente;
	}

	public final String getPassword() {

		return password;
	}

	public final void setCodiceapplicazione(final String codiceApplicazione) {

		if (codiceApplicazione == null) {
			throw new IllegalArgumentException("Il codice applicazione deve essere definito");
		}
		this.codiceapplicazione = codiceApplicazione;
	}

	public final void setIdufficio(final BigInteger idUfficio) {

		if (idUfficio == null) {
			throw new IllegalArgumentException("L'id ufficio deve essere definito");
		}
		this.idufficio = idUfficio;
	}

	public final void setIdutente(final BigInteger idUtente) {

		if (idUtente == null) {
			throw new IllegalArgumentException("L'id utente deve essere definito");
		}
		this.idutente = idUtente;
	}

	public final void setPassword(final String password) {

		if (password == null) {
			throw new IllegalArgumentException("La password deve essere definita");
		}
		this.password = password;
	}
}
