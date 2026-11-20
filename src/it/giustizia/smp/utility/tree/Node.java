package it.giustizia.smp.utility.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Node {

    protected List<Node> children = new ArrayList<Node>(0);
    protected Node       father;
    protected int        id;
    protected Object     info;
    protected int        level;
    protected boolean    root;

    public Node() {

    }

    public Node(Object info) {

        this.info = info;
    }

    public void addChild(Node node) {

        getChildren().add(node);
        node.setFather(this);
    }

    public void addChildren(List<Node> children) {

        Iterator<Node> iter = children.iterator();
        while (iter.hasNext()) {
            Node current = iter.next();
            addChild(current);
        }
    }

    public List<Node> getChildren() {

        return children;
    }

    public Node getFather() {

        return father;
    }

    public int getId() {

        return id;
    }

    public Object getInfo() {

        return info;
    }

    public int getLevel() {

        return level;
    }

    public boolean isRoot() {

        return root;
    }

    public void removeChild(Node node) {

        getChildren().remove(node);
    }

    public void setAsRoot() {

        setRoot(true);
        level = 0;
    }

    public void setFather(Node father) {

        this.father = father;
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setInfo(Object info) {

        this.info = info;
    }

    public void setLevel(int level) {

        this.level = level;
    }

    public void setRoot(boolean root) {

        this.root = root;
    }

    public String toString() {

        return info.toString();
    }
}
