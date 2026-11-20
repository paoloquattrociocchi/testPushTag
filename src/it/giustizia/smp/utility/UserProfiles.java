/** 
 * Classe per la gestione dei profili esternalizzati con il mock di autenticazione
 */
package it.giustizia.smp.utility;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Giacomo Cipriani
 * @version 1.0
 * @return Il file user_profile viene inserito nel path classes solo utilizzando i profili maven relativi a sviluppo ed eng
 */
public class UserProfiles {
	private static final String BUNDLE_NAME = "user_profile"; //$NON-NLS-1$

	private static ResourceBundle RESOURCE_BUNDLE = null;

	static {
		try {
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
		} catch (Exception ex) {
		}
	}

	private UserProfiles() {
	}

	public static String getString(String key) {
		if (RESOURCE_BUNDLE != null) {
			try {
				return RESOURCE_BUNDLE.getString(key);
			} catch (MissingResourceException e) {
				return "NESSUNPROFILO|NESSUNPROFILO|NESSUNPROFILO|NESSUNPROFILO$MANCAFILE|USERPROFILE|MANCAFILE|USERPROFILE";
			}
		}else {
			return "NESSUNPROFILO|NESSUNPROFILO|NESSUNPROFILO|NESSUNPROFILO$MANCAFILE|USERPROFILE|MANCAFILE|USERPROFILE";
		}
	}
		
}
