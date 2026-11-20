package it.giustizia.smp.utility;

public class TipoBollettino {

	private String tag;
	private String label;
	private String[] regoleList;
	private String regoleDesc;
	private int sizeSx;
	private int sizeDx;

	public TipoBollettino(String tag, String label, String[] regoleList, String regoleDesc, int sizeSx, int sizeDx) {
		super();
		this.tag = tag;
		this.label = label;
		this.regoleList = regoleList;
		this.regoleDesc = regoleDesc;
		this.sizeSx = sizeSx;
		this.sizeDx = sizeDx;
	}

	public String getTag() {
		return tag;
	}

	public String getLabel() {
		return label;
	}

	public String[] getRegoleList() {
		return regoleList;
	}

	public String getRegoleDesc() {
		return regoleDesc;
	}

	public String getSizeSx() {
		return "" + sizeSx;
	}

	public String getSizeDx() {
		return "" + sizeDx;
	}

}
