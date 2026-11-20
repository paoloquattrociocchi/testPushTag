package it.giustizia.smp.utility.tree;

public class JsNodeInfo {

    private boolean attachment;
    private String  label;
    private String  leafIcon;
    private String  url;
    private boolean deletable;
    private long idAtto;

    public JsNodeInfo() {

        super();
    }

    public JsNodeInfo(String label, String url, String leafIcon) {

        super();
        this.label = label;
        this.url = url;
        this.leafIcon = leafIcon;
    }

    /**
     * @return the attachment
     */
    public boolean getAttachment() {

        return attachment;
    }

    public String getLabel() {

        return label;
    }

    public String getLeafIcon() {

        return leafIcon;
    }

    public String getUrl() {

        return url;
    }

    /**
     * @param attachment
     *            the attachment to set
     */
    public void setAttachment(boolean attachment) {

        this.attachment = attachment;
    }

    public void setLabel(String label) {

        this.label = label;
    }

    public void setLeafIcon(String leafIcon) {

        this.leafIcon = leafIcon;
    }

    public void setUrl(String url) {

        this.url = url;
    }

    public String toString() {

        return label;
    }

	public boolean getDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public long getIdAtto() {
		return idAtto;
	}

	public void setIdAtto(long idAtto) {
		this.idAtto = idAtto;
	}


	
}
