package it.giustizia.smp.service.webservices;

import it.giustizia.smp.form.AttoForm;
import it.giustizia.smp.model.TAnUtenzePdoc;
import it.giustizia.smp.model.TAtto;

import java.math.BigInteger;
import java.rmi.ConnectException;
import java.sql.Blob;
import java.util.ArrayList;

public interface IDocumentaleService {

	public static final String SEPARATORE_CAMPI_IN_STRINGA_CODUGNA	= ";";
	
	String testConnessione(TAnUtenzePdoc utenzaPdoc);
	
	boolean esitoTestConnessione(int idUffOwner);
	
	boolean creaFascicolo(String codiceFascicolo, String codiceFascicoloPadre, String oggettoFascicolo, int idUffOwner);
	
//	BigInteger inserimentoDocumento(String oggetto, String tipoDocumento, String fileName, String note, Blob blobFile, String listCodugna);
	
	byte[] recuperoDocumento(BigInteger idDocumento);
	
	byte[] recuperoDocumento(BigInteger idDocumento, int idUffOwner);
	
	boolean associaDocumentoFascicolo(BigInteger idDocumento, String idFascicolo, TAnUtenzePdoc utenzaPdoc);
	
	boolean eliminaDocumento(BigInteger idDocumento, int idUffOwner);
	
	boolean stralcio(String codiceFascicoloOrigine, String codiceFascicoloDestinazione, ArrayList<it.giustizia.smp.webservice.client.documentale.model.general.stralcio.Documento> documenti, String codUgna);
	
	boolean riunione(String codiceFascicoloOrigine, String codiceFascicoloRiunione, ArrayList<BigInteger> documenti, String codUgna);
	
	boolean firmaRemota(BigInteger idDocumento, String username, String password, String pin, int idUffOwner);
	
	BigInteger invioDocumentoPDoc(AttoForm atto, int idUfficio, long idProcedimento);
	
	BigInteger renderPdf(String tipoDocumento, String nomeDocumento, long idAtto, BigInteger idDocumento, long uffOwner, TAnUtenzePdoc utenzaPdoc);
	
	boolean estendiSicurezza(BigInteger idDocumento, String listCodugna, TAnUtenzePdoc utenzaPdoc) throws ConnectException;
	
	BigInteger copiaDocumento(BigInteger idDocumento, String oggetto, String tipoDocumento, String fileName, String note, String listCodugna, int idUffOwner) throws ConnectException;
	
	boolean monitorRenderPdf(TAtto atto, TAnUtenzePdoc utenzaPdoc) throws ConnectException;
	
	BigInteger inserimentoDocumento(String oggetto, String tipoDocumento, String fileName, String note, Blob blobFile, String listCodugna, int idUffOwner);
	
	BigInteger inserimentoDocumento(String oggetto, String tipoDocumento, String fileName, String note, Blob blobFile, String codugna);
		
	
}
