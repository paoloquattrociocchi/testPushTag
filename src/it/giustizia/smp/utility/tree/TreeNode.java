package it.giustizia.smp.utility.tree;

public class TreeNode {

    private boolean attachment;
    private int     fatherId;
    private String  label;
    private String  leafIcon;   // nome dell'icona associata al treenode FOGLIA
    private int     nodeId;
    private String  url;
    private boolean deletable;
    private long idAtto;
    private Node node;

    
    
    public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public TreeNode() {

        super();
    }

    public TreeNode(int nodeId, int fatherId, String label, String url, String icon) {

        super();
        this.nodeId = nodeId;
        this.fatherId = fatherId;
        this.label = label;
        this.url = url;
        leafIcon = icon;
    }

    /**
     * @return the attachment
     */
    public boolean getAttachment() {

        return attachment;
    }

    /**
     * @return the fatherId
     */
    public int getFatherId() {

        return fatherId;
    }

    /**
     * @return the label
     */
    public String getLabel() {

        return label;
    }

    public String getLeafIcon() {

        return leafIcon;
    }

    /**
     * @return the nodeId
     */
    public int getNodeId() {

        return nodeId;
    }

    /**
     * @return the url
     */
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

    /**
     * @param fatherId
     *            the fatherId to set
     */
    public void setFatherId(int fatherId) {

        this.fatherId = fatherId;
    }

    /**
     * @param label
     *            the label to set
     */
    public void setLabel(String label) {

        this.label = label;
    }

    public void setLeafIcon(String leafIcon) {

        this.leafIcon = leafIcon;
    }

    /**
     * @param nodeId
     *            the nodeId to set
     */
    public void setNodeId(int nodeId) {

        this.nodeId = nodeId;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {

        this.url = url;
    }

	public boolean isDeletable() {
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
