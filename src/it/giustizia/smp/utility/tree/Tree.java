package it.giustizia.smp.utility.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Tree {

    protected HashMap<String, Node> index = new HashMap<String, Node>();
    protected Node                  root;

    public Tree() {

        root = new Node();
        root.setAsRoot();
    }

    public Tree(Object rootInfo, List<Node> children) {

        root.setInfo(rootInfo);
    }

    public static void main(String[] args) {

        Tree tree = new ProcedimentoTree();
        Node root = tree.getRoot();
        root.setInfo(new JsNodeInfo("1", "#", null));
        Node n2 = new Node(new JsNodeInfo("2", "#", null));
        Node n3 = new Node(new JsNodeInfo("3", "#", null));
        Node n4 = new Node(new JsNodeInfo("4", "#", null));
        Node n5 = new Node(new JsNodeInfo("5", "#", null));
        Node n6 = new Node(new JsNodeInfo("6", "#", null));
        Node n7 = new Node(new JsNodeInfo("7", "#", null));
        Node n8 = new Node(new JsNodeInfo("8", "#", null));
        Node n9 = new Node(new JsNodeInfo("9", "#", null));
        n6.addChild(n8);
        n7.addChild(n9);
        List<Node> l1 = new ArrayList<Node>();
        l1.add(n6);
        l1.add(n7);
        n3.addChildren(l1);
        n2.addChild(n4);
        n2.addChild(n5);
        root.addChild(n2);
        root.addChild(n3);
        tree.complete();
        System.out.println(tree.visitaPerLivelli(root));
    }

    // completa la creazione dell'albero assegnando a ciascun nodo un id e
    // calcolandone il livello
    public void complete() {

        int idStart = 0;
        List<Node> nodes = visitaPerLivelli(getRoot());
        Iterator<Node> iter = nodes.iterator();
        while (iter.hasNext()) {
            Node current = iter.next();
            current.setId(++idStart);
            current.setLevel(levelOf(current));
        }
    }

    public HashMap<String, Node> getIndex() {

        return index;
    }

    // ritorna la radice
    public Node getRoot() {

        return root;
    }

    // calcola ricorsivamente il livello di un nodo
    public int levelOf(Node n) {

        int level = 0;
        return calculateLevel(n, level);
    }

    public void setIndex(HashMap<String, Node> index) {

        this.index = index;
    }

    public String toString() {

        return visitaPerLivelli(getRoot()).toString();
    }

    // visita dei nodi per livello
    public List<Node> visitaPerLivelli(Node root) {

        List<Node> lReturn = new ArrayList<Node>();
        List<Node> nodeList = new ArrayList<Node>();
        nodeList.add(root);
        eseguiVisitaPerLivelli(nodeList, lReturn);
        return lReturn;
    }

    // visita in post-ordine
    public List<Node> visitaPostFissa(Node n) {

        List<Node> lReturn = new ArrayList<Node>();
        eseguiVisitaPostFissa(n, lReturn);
        return lReturn;
    }

    // visita in pre-ordine
    public List<Node> visitaPreFissa(Node root) {

        List<Node> lReturn = new ArrayList<Node>();
        // visita il nodo root
        lReturn.add(root);
        eseguiVisitaPreFissa(root, lReturn);
        return lReturn;
    }

    private int calculateLevel(Node n, int level) {

        if (n.getFather() == null) {
            return level;
        }
        level++;
        return calculateLevel(n.getFather(), level);
    }

    private void eseguiVisitaPerLivelli(List<Node> nodes, List<Node> lReturn) {

        if (nodes.size() == 0) {
            return;
        }
        Node n = nodes.get(0);
        // visita il nodo n
        lReturn.add(n);
        List<Node> children = n.getChildren();
        Iterator<Node> iter = children.iterator();
        while (iter.hasNext()) {
            Node current = iter.next();
            nodes.add(current);
        }
        nodes.remove(0);
        eseguiVisitaPerLivelli(nodes, lReturn);
    }

    private void eseguiVisitaPostFissa(Node n, List<Node> lReturn) {

        if (n.getChildren().size() == 0) {
            lReturn.add(n);
            return;
        }
        List<Node> l = n.getChildren();
        Iterator<Node> iter = l.iterator();
        while (iter.hasNext()) {
            Node current = iter.next();
            eseguiVisitaPostFissa(current, lReturn);
        }
        lReturn.add(n);
    }

    private void eseguiVisitaPreFissa(Node n, List<Node> lReturn) {

        if (n.getChildren().size() == 0) {
            return;
        }
        List<Node> l = n.getChildren();
        Iterator<Node> iter = l.iterator();
        while (iter.hasNext()) {
            Node current = iter.next();
            // visita il nodo n
            lReturn.add(current);
            eseguiVisitaPreFissa(current, lReturn);
        }
    }
}
