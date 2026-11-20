package it.giustizia.smp.webservice.client.documentale.model.general;

import it.giustizia.smp.webservice.client.documentale.stub.Component;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "oggetto", "tipodocumento", "filename", "note", "security" })
@XmlRootElement(name = "documento")
public final class Documento extends Component {

	private static final long	serialVersionUID	= 1L;
	@XmlElement(required = true, nillable = true)
	private String				filename;
	@XmlElement(required = true, nillable = true)
	private String				note;
	@XmlElement(required = true)
	private String				oggetto;
	private Security			security;
	@XmlAttribute(name = "streaming")
	private Boolean				streaming;
	@XmlElement(required = true)
	private String				tipodocumento;
	@XmlAttribute(name = "voluminoso")
	private Boolean				voluminoso;

	public final String getFilename() {

		return filename;
	}

	public final String getNote() {

		return note;
	}

	public final String getOggetto() {

		return oggetto;
	}

	public final Security getSecurity() {

		return security;
	}

	public final String getTipodocumento() {

		return tipodocumento;
	}

	public final Boolean isStreaming() {

		return streaming;
	}

	public final Boolean isVoluminoso() {

		return voluminoso;
	}

	public final void setFilename(final String filename) {

		this.filename = filename;
	}

	public final void setNote(final String note) {

		this.note = note;
	}

	public final void setOggetto(final String oggetto) {

		this.oggetto = oggetto;
	}

	public final void setSecurity(final Security security) {

		this.security = security;
	}

	public final void setStreaming(final Boolean streaming) {

		this.streaming = streaming;
	}

	public final void setTipodocumento(final String tipodocumento) {

		this.tipodocumento = tipodocumento;
	}

	public final void setVoluminoso(final Boolean voluminoso) {

		this.voluminoso = voluminoso;
	}

	public static final Documento getInstance(final String oggetto, final String tipoDocumento, final String fileName) {

		final Documento documento = new Documento();
		documento.setOggetto(oggetto);
		documento.setTipodocumento(tipoDocumento);
		documento.setFilename(fileName);
		documento.setNote(null);
		documento.setSecurity(Security.NONE);
		documento.setStreaming(false);
		documento.setVoluminoso(false);
		return documento;
	}
}
