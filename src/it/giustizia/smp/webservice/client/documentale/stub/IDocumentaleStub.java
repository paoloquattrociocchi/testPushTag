package it.giustizia.smp.webservice.client.documentale.stub;

import it.giustizia.smp.webservice.client.documentale.model.request.AggiuntaFascicoloRequest;
import it.giustizia.smp.webservice.client.documentale.model.request.CreaFascicoloRequest;
import it.giustizia.smp.webservice.client.documentale.model.request.InserimentoDocumentoRequest;
import it.giustizia.smp.webservice.client.documentale.model.request.RecuperoDocumentoRequest;
import it.giustizia.smp.webservice.client.documentale.model.request.TestConnessioneRequest;
import it.giustizia.smp.webservice.client.documentale.model.response.AggiuntaFascicoloResponse;
import it.giustizia.smp.webservice.client.documentale.model.response.CreaFascicoloResponse;
import it.giustizia.smp.webservice.client.documentale.model.response.InserimentoDocumentoResponse;
import it.giustizia.smp.webservice.client.documentale.model.response.RecuperoDocumentoResponse;
import it.giustizia.smp.webservice.client.documentale.model.response.TestConnessioneResponse;

import java.io.IOException;
import java.rmi.RemoteException;

public interface IDocumentaleStub {

	AggiuntaFascicoloResponse aggiuntaFascicolo(final AggiuntaFascicoloRequest request) throws RemoteException, IOException;

	CreaFascicoloResponse creaFascicolo(final CreaFascicoloRequest request) throws RemoteException, IOException;

	InserimentoDocumentoResponse inserimentoDocumento(final InserimentoDocumentoRequest request) throws RemoteException, IOException;

	RecuperoDocumentoResponse recuperaDocumento(final RecuperoDocumentoRequest request) throws RemoteException, IOException;

	TestConnessioneResponse testConnessione(final TestConnessioneRequest request) throws RemoteException, IOException;
}
