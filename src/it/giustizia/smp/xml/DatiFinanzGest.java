package it.giustizia.smp.xml;


public class DatiFinanzGest extends DatiFinanziari {

	private DatiBeneFinanziario datiBeneFinanziario;
	private DatiBeneMobile datiBeneMobile;

	public DatiBeneFinanziario getDatiBeneFinanziario() {
		return datiBeneFinanziario;
	}

	public void setDatiBeneFinanziario(DatiBeneFinanziario datiBeneFinanziario) {
		this.datiBeneFinanziario = datiBeneFinanziario;
	}

	public DatiBeneMobile getDatiBeneMobile() {
		return datiBeneMobile;
	}

	public void setDatiBeneMobile(DatiBeneMobile datiBeneMobile) {
		this.datiBeneMobile = datiBeneMobile;
	}
}
