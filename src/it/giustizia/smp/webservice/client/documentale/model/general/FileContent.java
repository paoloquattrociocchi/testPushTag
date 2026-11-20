package it.giustizia.smp.webservice.client.documentale.model.general;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "filecontent")
public class FileContent {
	private byte[] filecontent;

	public byte[] getFilecontent() {
		return filecontent;
	}

	public void setFilecontent(byte[] filecontent) {
		this.filecontent = filecontent;
	}

}
