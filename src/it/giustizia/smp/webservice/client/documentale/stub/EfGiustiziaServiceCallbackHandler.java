
/**
 * EfGiustiziaServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package it.giustizia.smp.webservice.client.documentale.stub;

    /**
     *  EfGiustiziaServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class EfGiustiziaServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public EfGiustiziaServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public EfGiustiziaServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for checkInDocumento method
            * override this method for handling normal response from checkInDocumento operation
            */
           public void receiveResultcheckInDocumento(
                    EfGiustiziaServiceStub.CheckInDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from checkInDocumento operation
           */
            public void receiveErrorcheckInDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for stralcioFascicolo method
            * override this method for handling normal response from stralcioFascicolo operation
            */
           public void receiveResultstralcioFascicolo(
                    EfGiustiziaServiceStub.StralcioFascicoloResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from stralcioFascicolo operation
           */
            public void receiveErrorstralcioFascicolo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for eliminaMail method
            * override this method for handling normal response from eliminaMail operation
            */
           public void receiveResulteliminaMail(
                    EfGiustiziaServiceStub.EliminaMailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from eliminaMail operation
           */
            public void receiveErroreliminaMail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for recuperaRubrica method
            * override this method for handling normal response from recuperaRubrica operation
            */
           public void receiveResultrecuperaRubrica(
                    EfGiustiziaServiceStub.RecuperaRubricaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from recuperaRubrica operation
           */
            public void receiveErrorrecuperaRubrica(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getVideoUrl method
            * override this method for handling normal response from getVideoUrl operation
            */
           public void receiveResultgetVideoUrl(
                    EfGiustiziaServiceStub.GetVideoUrlResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getVideoUrl operation
           */
            public void receiveErrorgetVideoUrl(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for recuperoStatoMigrazione method
            * override this method for handling normal response from recuperoStatoMigrazione operation
            */
           public void receiveResultrecuperoStatoMigrazione(
                    EfGiustiziaServiceStub.RecuperoStatoMigrazioneResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from recuperoStatoMigrazione operation
           */
            public void receiveErrorrecuperoStatoMigrazione(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateSecurityDFascicolo method
            * override this method for handling normal response from updateSecurityDFascicolo operation
            */
           public void receiveResultupdateSecurityDFascicolo(
                    EfGiustiziaServiceStub.UpdateSecurityDFascicoloResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateSecurityDFascicolo operation
           */
            public void receiveErrorupdateSecurityDFascicolo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for cancellaFascicolo method
            * override this method for handling normal response from cancellaFascicolo operation
            */
           public void receiveResultcancellaFascicolo(
                    EfGiustiziaServiceStub.CancellaFascicoloResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from cancellaFascicolo operation
           */
            public void receiveErrorcancellaFascicolo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for testConnessione method
            * override this method for handling normal response from testConnessione operation
            */
           public void receiveResulttestConnessione(
                    EfGiustiziaServiceStub.TestConnessioneResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from testConnessione operation
           */
            public void receiveErrortestConnessione(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for renderDocumento method
            * override this method for handling normal response from renderDocumento operation
            */
           public void receiveResultrenderDocumento(
                    EfGiustiziaServiceStub.RenderDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from renderDocumento operation
           */
            public void receiveErrorrenderDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for cancellaFascicoloPermanente method
            * override this method for handling normal response from cancellaFascicoloPermanente operation
            */
           public void receiveResultcancellaFascicoloPermanente(
                    EfGiustiziaServiceStub.CancellaFascicoloPermanenteResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from cancellaFascicoloPermanente operation
           */
            public void receiveErrorcancellaFascicoloPermanente(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for inserimentoRelazioneDocumento method
            * override this method for handling normal response from inserimentoRelazioneDocumento operation
            */
           public void receiveResultinserimentoRelazioneDocumento(
                    EfGiustiziaServiceStub.InserimentoRelazioneDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from inserimentoRelazioneDocumento operation
           */
            public void receiveErrorinserimentoRelazioneDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for rimuoviDocumenti method
            * override this method for handling normal response from rimuoviDocumenti operation
            */
           public void receiveResultrimuoviDocumenti(
                    EfGiustiziaServiceStub.RimuoviDocumentiResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from rimuoviDocumenti operation
           */
            public void receiveErrorrimuoviDocumenti(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for checkOutDocumento method
            * override this method for handling normal response from checkOutDocumento operation
            */
           public void receiveResultcheckOutDocumento(
                    EfGiustiziaServiceStub.CheckOutDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from checkOutDocumento operation
           */
            public void receiveErrorcheckOutDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for recuperaStoricoFascicolo method
            * override this method for handling normal response from recuperaStoricoFascicolo operation
            */
           public void receiveResultrecuperaStoricoFascicolo(
                    EfGiustiziaServiceStub.RecuperaStoricoFascicoloResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from recuperaStoricoFascicolo operation
           */
            public void receiveErrorrecuperaStoricoFascicolo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for verificaFirmaDigitaleDocumento method
            * override this method for handling normal response from verificaFirmaDigitaleDocumento operation
            */
           public void receiveResultverificaFirmaDigitaleDocumento(
                    EfGiustiziaServiceStub.VerificaFirmaDigitaleDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from verificaFirmaDigitaleDocumento operation
           */
            public void receiveErrorverificaFirmaDigitaleDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for migrazioneFascicolo method
            * override this method for handling normal response from migrazioneFascicolo operation
            */
           public void receiveResultmigrazioneFascicolo(
                    EfGiustiziaServiceStub.MigrazioneFascicoloResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from migrazioneFascicolo operation
           */
            public void receiveErrormigrazioneFascicolo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for verificaDigestDocumento method
            * override this method for handling normal response from verificaDigestDocumento operation
            */
           public void receiveResultverificaDigestDocumento(
                    EfGiustiziaServiceStub.VerificaDigestDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from verificaDigestDocumento operation
           */
            public void receiveErrorverificaDigestDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateDocument method
            * override this method for handling normal response from updateDocument operation
            */
           public void receiveResultupdateDocument(
                    EfGiustiziaServiceStub.UpdateDocumentResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateDocument operation
           */
            public void receiveErrorupdateDocument(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for undoCheckOutDocumento method
            * override this method for handling normal response from undoCheckOutDocumento operation
            */
           public void receiveResultundoCheckOutDocumento(
                    EfGiustiziaServiceStub.UndoCheckOutDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from undoCheckOutDocumento operation
           */
            public void receiveErrorundoCheckOutDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for spostaDocumento method
            * override this method for handling normal response from spostaDocumento operation
            */
           public void receiveResultspostaDocumento(
                    EfGiustiziaServiceStub.SpostaDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from spostaDocumento operation
           */
            public void receiveErrorspostaDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for esportaDocumenti method
            * override this method for handling normal response from esportaDocumenti operation
            */
           public void receiveResultesportaDocumenti(
                    EfGiustiziaServiceStub.EsportaDocumentiResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from esportaDocumenti operation
           */
            public void receiveErroresportaDocumenti(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for aperturaFascicolo method
            * override this method for handling normal response from aperturaFascicolo operation
            */
           public void receiveResultaperturaFascicolo(
                    EfGiustiziaServiceStub.AperturaFascicoloResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from aperturaFascicolo operation
           */
            public void receiveErroraperturaFascicolo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for monitorJob method
            * override this method for handling normal response from monitorJob operation
            */
           public void receiveResultmonitorJob(
                    EfGiustiziaServiceStub.MonitorJobResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from monitorJob operation
           */
            public void receiveErrormonitorJob(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for verificaEsitoMail method
            * override this method for handling normal response from verificaEsitoMail operation
            */
           public void receiveResultverificaEsitoMail(
                    EfGiustiziaServiceStub.VerificaEsitoMailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from verificaEsitoMail operation
           */
            public void receiveErrorverificaEsitoMail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for inviaMail method
            * override this method for handling normal response from inviaMail operation
            */
           public void receiveResultinviaMail(
                    EfGiustiziaServiceStub.InviaMailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from inviaMail operation
           */
            public void receiveErrorinviaMail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for cifraDocumento method
            * override this method for handling normal response from cifraDocumento operation
            */
           public void receiveResultcifraDocumento(
                    EfGiustiziaServiceStub.CifraDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from cifraDocumento operation
           */
            public void receiveErrorcifraDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for firmaDigitaleP7M method
            * override this method for handling normal response from firmaDigitaleP7M operation
            */
           public void receiveResultfirmaDigitaleP7M(
                    EfGiustiziaServiceStub.FirmaDigitaleP7MResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from firmaDigitaleP7M operation
           */
            public void receiveErrorfirmaDigitaleP7M(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for inviaServizioConservazioneDocumento method
            * override this method for handling normal response from inviaServizioConservazioneDocumento operation
            */
           public void receiveResultinviaServizioConservazioneDocumento(
                    EfGiustiziaServiceStub.InviaServizioConservazioneDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from inviaServizioConservazioneDocumento operation
           */
            public void receiveErrorinviaServizioConservazioneDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for firmaRemotaHSM method
            * override this method for handling normal response from firmaRemotaHSM operation
            */
           public void receiveResultfirmaRemotaHSM(
                    EfGiustiziaServiceStub.FirmaRemotaHSMResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from firmaRemotaHSM operation
           */
            public void receiveErrorfirmaRemotaHSM(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for riunioneFascicoli method
            * override this method for handling normal response from riunioneFascicoli operation
            */
           public void receiveResultriunioneFascicoli(
                    EfGiustiziaServiceStub.RiunioneFascicoliResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from riunioneFascicoli operation
           */
            public void receiveErrorriunioneFascicoli(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for recuperaFascicolo method
            * override this method for handling normal response from recuperaFascicolo operation
            */
           public void receiveResultrecuperaFascicolo(
                    EfGiustiziaServiceStub.RecuperaFascicoloResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from recuperaFascicolo operation
           */
            public void receiveErrorrecuperaFascicolo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for firmaDocumento method
            * override this method for handling normal response from firmaDocumento operation
            */
           public void receiveResultfirmaDocumento(
                    EfGiustiziaServiceStub.FirmaDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from firmaDocumento operation
           */
            public void receiveErrorfirmaDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for recuperaDocumentoCifrato method
            * override this method for handling normal response from recuperaDocumentoCifrato operation
            */
           public void receiveResultrecuperaDocumentoCifrato(
                    EfGiustiziaServiceStub.RecuperaDocumentoCifratoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from recuperaDocumentoCifrato operation
           */
            public void receiveErrorrecuperaDocumentoCifrato(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for ricercaDocumenti method
            * override this method for handling normal response from ricercaDocumenti operation
            */
           public void receiveResultricercaDocumenti(
                    EfGiustiziaServiceStub.RicercaDocumentiResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from ricercaDocumenti operation
           */
            public void receiveErrorricercaDocumenti(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for ricercaFascicoli method
            * override this method for handling normal response from ricercaFascicoli operation
            */
           public void receiveResultricercaFascicoli(
                    EfGiustiziaServiceStub.RicercaFascicoliResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from ricercaFascicoli operation
           */
            public void receiveErrorricercaFascicoli(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for recuperaFascicoliDaConsolidare method
            * override this method for handling normal response from recuperaFascicoliDaConsolidare operation
            */
           public void receiveResultrecuperaFascicoliDaConsolidare(
                    EfGiustiziaServiceStub.RecuperaFascicoliDaConsolidareResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from recuperaFascicoliDaConsolidare operation
           */
            public void receiveErrorrecuperaFascicoliDaConsolidare(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for chiusuraFascicolo method
            * override this method for handling normal response from chiusuraFascicolo operation
            */
           public void receiveResultchiusuraFascicolo(
                    EfGiustiziaServiceStub.ChiusuraFascicoloResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from chiusuraFascicolo operation
           */
            public void receiveErrorchiusuraFascicolo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateDocumento method
            * override this method for handling normal response from updateDocumento operation
            */
           public void receiveResultupdateDocumento(
                    EfGiustiziaServiceStub.UpdateDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateDocumento operation
           */
            public void receiveErrorupdateDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for eliminaDocumentoPermanente method
            * override this method for handling normal response from eliminaDocumentoPermanente operation
            */
           public void receiveResulteliminaDocumentoPermanente(
                    EfGiustiziaServiceStub.EliminaDocumentoPermanenteResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from eliminaDocumentoPermanente operation
           */
            public void receiveErroreliminaDocumentoPermanente(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for recuperaDocumento method
            * override this method for handling normal response from recuperaDocumento operation
            */
           public void receiveResultrecuperaDocumento(
                    EfGiustiziaServiceStub.RecuperaDocumentoResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from recuperaDocumento operation
           */
            public void receiveErrorrecuperaDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for cancellazioneRelazioneDocumento method
            * override this method for handling normal response from cancellazioneRelazioneDocumento operation
            */
           public void receiveResultcancellazioneRelazioneDocumento(
                    EfGiustiziaServiceStub.CancellazioneRelazioneDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from cancellazioneRelazioneDocumento operation
           */
            public void receiveErrorcancellazioneRelazioneDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for pubblicaDocumenti method
            * override this method for handling normal response from pubblicaDocumenti operation
            */
           public void receiveResultpubblicaDocumenti(
                    EfGiustiziaServiceStub.PubblicaDocumentiResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from pubblicaDocumenti operation
           */
            public void receiveErrorpubblicaDocumenti(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for recuperaMail method
            * override this method for handling normal response from recuperaMail operation
            */
           public void receiveResultrecuperaMail(
                    EfGiustiziaServiceStub.RecuperaMailResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from recuperaMail operation
           */
            public void receiveErrorrecuperaMail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for recuperoMailOriginale method
            * override this method for handling normal response from recuperoMailOriginale operation
            */
           public void receiveResultrecuperoMailOriginale(
                    EfGiustiziaServiceStub.RecuperoMailOriginaleResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from recuperoMailOriginale operation
           */
            public void receiveErrorrecuperoMailOriginale(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for recuperaDocFascicolo method
            * override this method for handling normal response from recuperaDocFascicolo operation
            */
           public void receiveResultrecuperaDocFascicolo(
                    EfGiustiziaServiceStub.RecuperaDocFascicoloResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from recuperaDocFascicolo operation
           */
            public void receiveErrorrecuperaDocFascicolo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for recuperaStoricoDocumento method
            * override this method for handling normal response from recuperaStoricoDocumento operation
            */
           public void receiveResultrecuperaStoricoDocumento(
                    EfGiustiziaServiceStub.RecuperaStoricoDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from recuperaStoricoDocumento operation
           */
            public void receiveErrorrecuperaStoricoDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for consolidaFascicolo method
            * override this method for handling normal response from consolidaFascicolo operation
            */
           public void receiveResultconsolidaFascicolo(
                    EfGiustiziaServiceStub.ConsolidaFascicoloResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from consolidaFascicolo operation
           */
            public void receiveErrorconsolidaFascicolo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for versioniDocumento method
            * override this method for handling normal response from versioniDocumento operation
            */
           public void receiveResultversioniDocumento(
                    EfGiustiziaServiceStub.VersioniDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from versioniDocumento operation
           */
            public void receiveErrorversioniDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for creaFascicolo method
            * override this method for handling normal response from creaFascicolo operation
            */
           public void receiveResultcreaFascicolo(
                    EfGiustiziaServiceStub.CreaFascicoloResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from creaFascicolo operation
           */
            public void receiveErrorcreaFascicolo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for estrazionePagineDocumento method
            * override this method for handling normal response from estrazionePagineDocumento operation
            */
           public void receiveResultestrazionePagineDocumento(
                    EfGiustiziaServiceStub.EstrazionePagineDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from estrazionePagineDocumento operation
           */
            public void receiveErrorestrazionePagineDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for inserimentoDocumento method
            * override this method for handling normal response from inserimentoDocumento operation
            */
           public void receiveResultinserimentoDocumento(
                    EfGiustiziaServiceStub.InserimentoDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from inserimentoDocumento operation
           */
            public void receiveErrorinserimentoDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for eliminaDocumento method
            * override this method for handling normal response from eliminaDocumento operation
            */
           public void receiveResulteliminaDocumento(
                    EfGiustiziaServiceStub.EliminaDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from eliminaDocumento operation
           */
            public void receiveErroreliminaDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for recuperaSerizioConservazioneDocumento method
            * override this method for handling normal response from recuperaSerizioConservazioneDocumento operation
            */
           public void receiveResultrecuperaSerizioConservazioneDocumento(
                    EfGiustiziaServiceStub.RecuperaSerizioConservazioneDocumentoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from recuperaSerizioConservazioneDocumento operation
           */
            public void receiveErrorrecuperaSerizioConservazioneDocumento(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for uploadDocumentoP7M method
            * override this method for handling normal response from uploadDocumentoP7M operation
            */
           public void receiveResultuploadDocumentoP7M(
                    EfGiustiziaServiceStub.UploadDocumentoP7MResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from uploadDocumentoP7M operation
           */
            public void receiveErroruploadDocumentoP7M(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateFascicolo method
            * override this method for handling normal response from updateFascicolo operation
            */
           public void receiveResultupdateFascicolo(
                    EfGiustiziaServiceStub.UpdateFascicoloResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateFascicolo operation
           */
            public void receiveErrorupdateFascicolo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for inviaMailExternalAttachs method
            * override this method for handling normal response from inviaMailExternalAttachs operation
            */
           public void receiveResultinviaMailExternalAttachs(
                    EfGiustiziaServiceStub.InviaMailExternalAttachsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from inviaMailExternalAttachs operation
           */
            public void receiveErrorinviaMailExternalAttachs(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for recuperaSottoFascicoli method
            * override this method for handling normal response from recuperaSottoFascicoli operation
            */
           public void receiveResultrecuperaSottoFascicoli(
                    EfGiustiziaServiceStub.RecuperaSottoFascicoliResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from recuperaSottoFascicoli operation
           */
            public void receiveErrorrecuperaSottoFascicoli(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for ricercaDocumentiFullText method
            * override this method for handling normal response from ricercaDocumentiFullText operation
            */
           public void receiveResultricercaDocumentiFullText(
                    EfGiustiziaServiceStub.RicercaDocumentiFullTextResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from ricercaDocumentiFullText operation
           */
            public void receiveErrorricercaDocumentiFullText(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for aggiuntaFascicolo method
            * override this method for handling normal response from aggiuntaFascicolo operation
            */
           public void receiveResultaggiuntaFascicolo(
                    EfGiustiziaServiceStub.AggiuntaFascicoloResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from aggiuntaFascicolo operation
           */
            public void receiveErroraggiuntaFascicolo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for riceviMail method
            * override this method for handling normal response from riceviMail operation
            */
           public void receiveResultriceviMail(
                    EfGiustiziaServiceStub.RiceviMailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from riceviMail operation
           */
            public void receiveErrorriceviMail(java.lang.Exception e) {
            }
                


    }
    