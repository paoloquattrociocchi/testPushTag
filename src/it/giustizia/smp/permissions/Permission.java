package it.giustizia.smp.permissions;

public enum Permission {
	MODIFICA, VISUALIZZAZIONE;

	public final static Permission forName(final String name) {

		if (name == null) {
			throw new NullPointerException("Il parametro 'name' non può essere null");
		}
		for (Permission permission : values()) {
			if (name.equalsIgnoreCase(permission.name())) {
				return permission;
			}
		}
		throw new IllegalArgumentException("Non è stato trovato alcun permesso chiamato '" + name + "'");
	}
}