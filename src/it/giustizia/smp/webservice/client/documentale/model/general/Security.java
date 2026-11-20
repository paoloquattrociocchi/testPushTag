package it.giustizia.smp.webservice.client.documentale.model.general;

import it.giustizia.smp.webservice.client.documentale.stub.Component;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "ufficioabilitato" })
@XmlRootElement(name = "security")
public final class Security extends Component {

	public static final Security	NONE				= new Security();
	private static final long		serialVersionUID	= 1L;
	private List<BigInteger>		ufficioabilitato;

	public final List<BigInteger> getUfficioabilitato() {

		if (ufficioabilitato == null) {
			ufficioabilitato = new LinkedList<BigInteger>();
		}
		return this.ufficioabilitato;
	}
	
	public void setUfficioabilitato(List<BigInteger> ufficioabilitato) {
		this.ufficioabilitato = ufficioabilitato;
	}
}
