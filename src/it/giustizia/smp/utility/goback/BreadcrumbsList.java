package it.giustizia.smp.utility.goback;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Giacomo Stefano Gabriele
 * 
 */
public class BreadcrumbsList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2094581147427855590L;

	private List<Breadcrumbs> list = null;

	/**
	 * Costruttore di default
	 */
	public BreadcrumbsList() {
		list = new LinkedList<Breadcrumbs>();
	}

	/**
	 * Costruttore con lista
	 * 
	 * @param list
	 */
	public BreadcrumbsList(List<Breadcrumbs> list) {
		super();
		this.list = list;
	}

	/**
	 * clean tutta la lista Breadcrumbs
	 */
	public void cleanBreadcrumbs() {
		list.clear();
	}

	/**
	 * cancella tutte le {@link Breadcrumbs} a partire dalla {@link Breadcrumbs}
	 * che ha come action e namespace quelli passati
	 * 
	 * @param method
	 * @param action
	 * @param param
	 */
	public void cleanBreadcrumb(String method, String action, String param) {
		Breadcrumbs breadcrumb = new Breadcrumbs(method, action, param);
		int level = recuperaLivello(breadcrumb);
		if (level >= 0 && level < list.size()) {
			list = list.subList(0, level + 1);
		}
	}
	public void cleanBreadcrumb(String method, String action, String param,String elimina) {
		Breadcrumbs breadcrumb = new Breadcrumbs(method, action, param);
		int level = recuperaLivello(breadcrumb);
		if (level >= 0 && level <= list.size()) {
			list = list.subList(0, level-1);
		}
	}

	/**
	 * Aggiunge una {@link Breadcrumbs} con la Label, action e namespace passati
	 * 
	 * @param method
	 * @param action
	 * @param params
	 * @return
	 */
	public Breadcrumbs addBreadcrumbs(String method, String action, String params) {
		Breadcrumbs Breadcrumbs = new Breadcrumbs(method, action, params);
		return addBreadcrumbs(Breadcrumbs);
	}

	/**
	 * Aggiunge la {@link Breadcrumbs}
	 * 
	 * @param breadcrumbs
	 * @return Breadcrumbs
	 */
	public Breadcrumbs addBreadcrumbs(Breadcrumbs breadcrumbs) {
		int level = recuperaLivello(breadcrumbs);
		if (level > list.size()) {
			level = list.size();
		}
		list.add(level, breadcrumbs);
		list = list.subList(0, level + 1);
		return breadcrumbs;
	}

	/**
	 * Recupera il livello della {@link Breadcrumbs} passata
	 * 
	 * @param breadcrumbs
	 * @return livello se esiste -1 se non esiste
	 */
	private int recuperaLivello(Breadcrumbs breadcrumbs) {
		int level = list.lastIndexOf(breadcrumbs);
		if (level == -1) {
			return list.size();
		}
		return level;
	}

	public Breadcrumbs getLast() {
		if (list.isEmpty()) {
			return null;
		}

		int index = list.size() - 2;

		if (index < 0) {
			index = 0;
		}

		return list.get(index);
	}

	/**
	 * @return the list
	 */
	public final List<Breadcrumbs> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public final void setList(List<Breadcrumbs> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return list.toString();
	}

}
