package it.giustizia.smp.webservice.client.documentale.model.general;

import it.giustizia.smp.webservice.client.documentale.stub.Component;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "codicemessaggio", "messagebody", "esito" })
@XmlRootElement(name = "messaggio")
public final class Messaggio extends Component {

	private static final long	serialVersionUID	= 1L;
	@XmlElement(required = true)
	private BigInteger			codicemessaggio;
	private boolean				esito;
	@XmlElement(required = true)
	private String				messagebody;

	public final BigInteger getCodicemessaggio() {

		return codicemessaggio;
	}

	public final String getMessagebody() {

		return messagebody;
	}

	public boolean isEsito() {

		return esito;
	}

	public final void setCodicemessaggio(final BigInteger codicemessaggio) {

		this.codicemessaggio = codicemessaggio;
	}

	public final void setEsito(final boolean esito) {

		this.esito = esito;
	}

	public final void setMessagebody(final String messagebody) {

		this.messagebody = messagebody;
	}
}
