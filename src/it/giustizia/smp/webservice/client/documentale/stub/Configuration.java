package it.giustizia.smp.webservice.client.documentale.stub;

import it.giustizia.smp.webservice.client.documentale.model.general.Autenticazione;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Configuration {

	public final static String	NAMESPACE_GIUSTIZIA	= "http://webservice.components.efgiustizia";
	public final static String	NAMESPACE_XSD		= "http://model.webservice.components.efgiustizia/xsd";
	public final static String	QNAME_PREFIX		= "ns2";
	public final static String	QNAME_PREFIX_XSD	= "ns1";
	public final static String	SCHEMA_INSTANCE		= "http://www.w3.org/2001/XMLSchema-instance";
	public final static String	SERVICE_NAME		= "EfGiustiziaService";
	
//	private static final Autenticazione	autenticazione	= new Autenticazione();
	private static final String			BUNDLE_NAME		= "srcConfig";
	private static final ResourceBundle	RESOURCE_BUNDLE	= ResourceBundle.getBundle(BUNDLE_NAME);
//	static {
//		final String codiceApplicazione = getProperty("ws.documentale.Autenticazione.CodiceApplicazione");
//		final String password = getProperty("ws.documentale.Autenticazione.Password");
//		final String idUtenteString = getProperty("ws.documentale.Autenticazione.IDUtente");
//		final BigInteger idUtente = BigInteger.valueOf(Long.valueOf(idUtenteString));
//		final String idUfficioString = getProperty("ws.documentale.Autenticazione.IDUfficio");
//		final BigInteger idUfficio = BigInteger.valueOf(Long.valueOf(idUfficioString));
//		autenticazione.setCodiceapplicazione(codiceApplicazione);
//		autenticazione.setPassword(password);
//		autenticazione.setIdutente(idUtente);
//		autenticazione.setIdufficio(idUfficio);
//	}
	
	private Configuration() {

		throw new UnsupportedOperationException("La classe non è instanziabile");
	}
	
//	public static final Autenticazione getAutenticazione() {
//
//		return autenticazione;
//	}

//	public static final String getProxyPassword() {
//
//		return getProperty("ws.documentale.ProxyPassword");
//	}
//
//	public static final String getProxyUsername() {
//
//		return getProperty("ws.documentale.ProxyUsername");
//	}

	public static final String getURL() {

		String urlString = getProperty("ws.documentale.URL");
//		if (isConnessioneInterna()) {
//			urlString = getProperty("ws.documentale.URLinterno");
//		}
		return urlString;
	}

	public static final boolean isConnessioneInterna() {

		return !Boolean.valueOf(getProperty("ConnessioneReteGiustizia"));
	}
	
	public static final boolean isAbilitaANBSC() {

		return Boolean.valueOf(getProperty("ws.anbsc"));
	}
	
	public static final boolean isAbilitaCasellario() {

		return Boolean.valueOf(getProperty("ws.casellario"));
	}
	
	public static final boolean isAbilitaCassazione() {

		return Boolean.valueOf(getProperty("ws.cassazione"));
	}
	
	public static final boolean isAbilitaFug() {

		return Boolean.valueOf(getProperty("ws.fug"));
	}
	
	public static final boolean isAbilitaPOLIZIA() {

		return Boolean.valueOf(getProperty("ws.polizia"));
	}
	
//	public static final boolean isProxyAbilitato() {
//		
//		return Boolean.valueOf(getProperty("ws.documentale.ProxyAbilitato"));
//	}

	private static String getProperty(final String key) {

		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (final MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
