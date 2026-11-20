package it.giustizia.smp.webservice.client.documentale;

import it.giustizia.smp.model.TAnUtenzePdoc;
import it.giustizia.smp.model.TAtto;
import it.giustizia.smp.webservice.client.documentale.model.general.Risposta;
import it.giustizia.smp.webservice.client.documentale.model.response.*;

import java.math.BigInteger;
import java.rmi.ConnectException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public interface IDocumentaleClient {

	Risposta testConnessione(TAnUtenzePdoc utenzaPdoc) throws ConnectException;
	
	Risposta creaFascicolo(String codiceFascicolo, String codiceFascicoloPadre, String oggettoFascicolo, List<BigInteger> GL, TAnUtenzePdoc utenzaPdoc) throws ConnectException;
	
	InserimentoDocumentoResponse inserimentoDocumento(String oggetto, String tipoDocumento, String fileName, String note, Blob blobFile, List<BigInteger> codugnaList, TAnUtenzePdoc utenzaPdoc) throws ConnectException;
	
	byte[] recuperoDocumento(BigInteger idDocumento, TAnUtenzePdoc utenzaPdoc) throws ConnectException;
	
	boolean associaDocumentoFascicolo(BigInteger idDocumento, String idFascicolo, TAnUtenzePdoc utenzaPdoc) throws ConnectException;
	
	boolean eliminaDocumento(BigInteger idDocumento, TAnUtenzePdoc utenzaPdoc) throws ConnectException;
	
	boolean stralcio(String codiceFascicoloOrigine, String codiceFascicoloDestinazione, ArrayList<it.giustizia.smp.webservice.client.documentale.model.general.stralcio.Documento> documenti, TAnUtenzePdoc utenzaPdoc) throws ConnectException;
	
	boolean riunione(String codiceFascicoloOrigine, String codiceFascicoloRiunione, ArrayList<BigInteger> documenti, TAnUtenzePdoc utenzaPdoc) throws ConnectException;
	
	Risposta firmaRemota(BigInteger idDocumento, String username, String password, String pin, TAnUtenzePdoc utenzaPdoc) throws ConnectException;
	
	BigInteger renderPdf(BigInteger idDocumento, TAnUtenzePdoc utenzaPdoc) throws ConnectException;
	
	boolean estendiSicurezza(BigInteger idDocumento, BigInteger codugna, TAnUtenzePdoc utenzaPdoc) throws ConnectException;
	
	BigInteger copiaDocumento(BigInteger idDocumento, String oggetto, String tipoDocumento, String fileName, String note, List<BigInteger> codugnaList, TAnUtenzePdoc utenzaPdoc) throws ConnectException;
	
	boolean monitorRenderPdf(BigInteger idTransazione, TAnUtenzePdoc utenzaPdoc, TAtto atto) throws ConnectException;
}
