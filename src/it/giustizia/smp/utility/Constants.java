package it.giustizia.smp.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Constants {
	
	public final static int ID_UFFICIO_CASSAZIONE = 7001;
	
	public final static String	ATTO																				= "atto";
	public final static String	ATTO_FORM																			= "attoForm";
	
	// costanti relative alla T_TIPO_MSG
	public final static String	PROPOSTA_MSG																		= "1401";
	public final static String	PROPOSTA_DA_TRIBUNALE_MSG															= "1425";
	public final static String	ISTANZA_DA_TRIBUNALE_MSG															= "1600";
	public final static String	SEQUESTRO_MSG_PU																	= "1426";
	public final static String	SEQUESTRO_MSG_PG																	= "1427";
	public final static String	SEQUESTRO_MSG_CDA																	= "1428";
	
	public final static String	SEQUESTRO_ANT_MSG_PU																= "1460";
	public final static String	SEQUESTRO_ANT_MSG_PG																= "1461";
	
	public final static String	SEQUESTRO_URG_MSG_PU																= "1462";
	public final static String	SEQUESTRO_URG_MSG_PG																= "1463";
	
	//public final static String  DECRETO_CONVALIDA_SEQ_PU_MSG														= "1414";
	public final static String  DECRETO_CONVALIDA_SEQ_MSG														    = "1414";
	//public final static String  DECRETO_CONVALIDA_SEQ_PP_MSG														= "1415";
	public final static String	DECRETO_DECISORIO_I_GRADO_PU_MSG													= "1411";
	public final static String	DECRETO_DECISORIO_I_GRADO_PP_MSG													= "1412";
	public final static String	DECRETO_DECISORIO_I_GRADO_PG_MSG													= "1413";

	public final static String	DECRETO_DECISORIO_I_GRADO_PU_CAM_CON_FASCICOLO										= "1343";
	public final static String	DECRETO_DECISORIO_I_GRADO_PG_CAM_CON_FASCICOLO										= "1344";
	public final static String	DECRETO_DECISORIO_I_GRADO_AGGR_CON_FASCICOLO										= "1345";
	
	public final static String	DEFINITIVITA_PU_MSG																	= "1417";
	public final static String	DEFINITIVITA_PP_MSG																	= "1418";
	public final static String	DEFINITIVITA_PG_MSG																	= "1419";
	public final static String	DEFINITIVITA_REVOCAZIONE_CONFISCA_MSG 												= "1522";
	public final static String	DEFINITIVITA_CDA_MSG 																= "220";
	public final static String	DEFINITIVITA_II_GRADO_MSG															= "448";
	public final static String	DECRETO_INCOMPETENZA_PROCURA_MSG													= "1458";
	public final static String	DECRETO_INCOMPETENZA_RESTITUZIONE_ATTI_MSG											= "1416";
	public final static String	DECRETO_INCOMPETENZA_TRASF_ALTRO_TRIBUNALE_MSG										= "1416";
	public final static String  DECRETO_REVOCA_SEQUESTRO_MSG														= "1453";
	public final static String  DECRETO_RIGETTO_TOTALE_MSG															= "1516";
	public final static String	PROPOSTA_AGGRAVAMENTO_MSG															= "2000";
	public final static String	PROPOSTA_AGGRAVAMENTO_DA_TRIBUNALE_MSG												= "2001";
	public final static String	ISTANZA_DIFFIDA_SOGGETTO_MSG														= "9000";
	public final static String	DECRETO_ANTICIPAZIONE_UDIENZA_MSG													= "1487";
	public final static String	DECRETO_RINVIO_UDIENZA_MSG															= "1488";
	public final static String  VERBALE_RINVIO_UDIENZA_MSG                                                          = "1475";	
	public final static String	DECRETO_FISSAZIONE_UDIENZA_MSG														= "1430";
	public final static String	DECRETO_UDIENZA_CON_FASCICOLO														= "1311";
	public final static String  DECRETO_REMISSIONE_SU_RUOLO_MSG                                                     = "1496";
	public final static String  DECRETO_ASSEGNAZIONE_NUOVO_RUOLO_MSG                                                = "1493";
	public final static String	OPPOSIZIONE_AL_DISSEQUESTRO_MSG												        = "504";
	public final static String	OPPOSIZIONE_AL_DISSEQUESTRO_ATTO_MSG												= "505";
	public final static String	REVOCA_OPPOSIZIONE_AL_DISSEQUESTRO_MSG												= "1525";	
	public final static String	REVOCAZIONE_CONFISCA_MSG												            = "1517";
	public final static String	REVOCAZIONE_CONFISCA_ATTO_MSG												        = "1521";
	public final static String	SENTENZA_REVOCAZIONE_CONFISCA_MSG												    = "1519";	
	public final static String	RICORSO_IN_APPELLO_CDA_MSG												        	= "1499";
	public final static String	RICORSO_IN_APPELLO_PU_A_TRIB_MSG												    = "1506";
	public final static String	RICORSO_IN_APPELLO_PG_A_TRIB_MSG												    = "1507";
	public final static String	RICORSO_IN_APPELLO_TRIB_A_PU_MSG												    = "1508";
	public final static String	RICORSO_IN_APPELLO_TRIB_A_PG_MSG												    = "1509";	
	public final static String	TIPO_MSG_RIC_AP_SUCCESSIVO														    = "1513";
	public final static String	TIPO_MSG_RIC_AP_TARDIVO															    = "1514";
	public final static String	RICORSO_IN_CASSAZIONE_DA_TRIB_MSG												    = "1350";
	public final static String	TIPO_MSG_ATTO_RIFIUTATO															    = "1520";	
	public final static String	SENTENZA_OPPOSIZIONE_AL_DISSEQUESTRO_MSG											= "512";
	public final static String	ANNULLAMENTO_SOGGETTO_MSG												            = "2002";
	public final static String	TIPO_MSG_DECRETO_DECISORIO_II_GRADO  									            = "1442";
	public final static String	TIPO_MSG_PROVV_ISTANZA_CDA_CC  									          		    = "1595";
	public final static String	TIPO_MSG_PROVV_ISTANZA_FASCICOLO  									          	    = "1598";
	public final static String	TIPO_MSG_DECRETO_DECISORIO_II_GRADO_CAM  									        = "1348";
	public final static String	TIPO_MSG_DECRETO_DECISORIO_II_GRADO_AGGRAVAMENTO  									= "1349";
	public final static String	NRG_DELLA_CASSAZIONE_MSG						  									= "1540";
	
	public final static String	TIPO_MSG_RICHIESTA_PARERE	  									            		= "1523";
	public final static String	TIPO_MSG_PARERE	  									            					= "1524";
    public final static String	TIPO_MSG_PARERE_CON_FASCICOLO	  									            	= "1312";
	public final static String	TIPO_MSG_VERSIONAMENTO																= "6666";
	public final static String	RICORSO_IN_CASSAZIONE_PG_A_CDA_MSG												    = "1452";
	public final static String	RICORSO_IN_CASSAZIONE_CDA_A_PG_MSG												    = "1451";
	public final static String	PROROGA_SEQUESTRO_MSG												    			= "1526";
	public final static String	SOSPENSIONE_TEMP_MSG												    			= "1586";
	public final static String	PROROGA_SOSPENSIONE_MSG																= "1587";
	public final static String	SEQUESTRO_SOSPENSIONE_MSG															= "1588";
	public final static String	REVOCA_APPLICAZIONE_PROVV_MSG													    = "1444";
	public final static String	DECRETO_APPLICAZIONE_PROVV_MSG														= "1464";
	
	public final static String	SENTENZA_CASSAZIONE_DA_CDA_MSG												        = "1528";	
	
	public final static String	TIPO_MSG_RIC_TARD_CASS_DA_CDA												        = "1590";	
	public final static String	TIPO_MSG_RIC_TARD_CASS_DA_PG												        = "1591";
	
	public final static String	SENTENZA_CASSAZIONE_DA_CASS_A_CDA_MSG											    = "1530";
	public final static String	SENTENZA_CASSAZIONE_DA_CASS_A_TRIB_MSG											    = "1535";
	
	public final static String	SENTENZA_CASSAZIONE_TRIB_CC											 			    = "1531";
	public final static String	SENTENZA_CASSAZIONE_CDA_CC											   				= "1532";

	public final static String	SENTENZA_CASSAZIONE_REVOCAZIONE_CONFISCA    						   				= "1545";	
	
	public final static String	TIPO_MSG_PER_EFFI_SEQ_A_PU_PG												        = "1592";	

	/* 
	 * ISSUE MEV : aggiunta costante per gestire i verbali di udienza di riserva
	 * Numero MEV : 15b
	 * Autore    : Gioggi
	 * Data      : 07 ago 2018
	 * Branch    : MEV_15b
	 */
	public final static String VERBALE_UDIENZA_RISERVA_MSG                                                          = "6656";
	//***** FINE INTERVENTO MEV_15b *****//
	
	public final static long	ANNULLAMENTO_CON_RINVIO_CASS_MSG													= 9100l;
	public final static long	ANNULLAMENTO_CON_RINVIO_CDA_IN_TRI_MSG											    = 9115l;
	public final static long	ANNULLAMENTO_CON_RINVIO_CAS_IN_TRI_MSG												= 9125l;
	public final static long	ANNULLAMENTO_CON_RINVIO_CAS_IN_TRI_CC_MSG											= 9135l;
	public final static long	ANNULLAMENTO_CON_RINVIO_CAS_IN_CDA_CC_MSG											= 9145l;
	
	// costanti relative alla categoria dell'atto
	public final static String 	DECRETO_SEQUESTRO_SOSPENSIONE_TEMPORANEA	     									= "3";		
	public final static String	DECRETO_DECISORIO_I_GRADO															= "4";
	public final static String	DECRETO_DECISORIO_I_GRADO_CAMERE													= "355";
	
	public final static String	RICORSO_IN_APPELLO																	= "5";
	public final static String	RICORSO_IN_CASSAZIONE															    = "24";
	public final static String	RICORSO_IN_CASSAZIONE_DA_TRIB														= "7";
	public final static String	DECRETO_OPPOSIZIONE_AL_DISSEQUESTRO													= "6";
	public final static String	DECRETO_FISSAZIONE_UDIENZA															= "8";
	public final static String	DECRETO_FISSAZIONE_UDIENZA_II_GRADO													= "25";
	public final static String	DECRETO_DECISORIO_II_GRADO															= "22";
	public final static String	DECRETO_DECISORIO_II_GRADO_CAM														= "359";
	public final static String	DECRETO_DECISORIO_PROVV_ISTANZA														= "373";
	public final static String	SENTENZA_OPPOSIZIONE_AL_DISSEQUESTRO												= "23";		
	public final static String 	DECRETO_RIGETTO_TOTALE_SEQUESTRO													= "30";	
	public final static String 	DECRETO_APPLICAZIONE_PROVVISORIA												    = "31";
	public final static String 	SENTENZA_SU_RICORSO_II_GRADO_DA_TRIB										    	= "39";	
	public final static String 	SENTENZA_SU_RICORSO_II_GRADO												    	= "40";	
	public final static String	DECRETO_ANTICIPAZIONE_UDIENZA														= "57";
	public final static String	DECRETO_ANTICIPAZIONE_UDIENZA_II_GRADO												= "59";
	public final static String	DECRETO_RINVIO_UDIENZA																= "58";
	public final static String	DECRETO_RINVIO_UDIENZA_II_GRADO														= "60";
	public final static String 	DECRETO_REVOCA_APPLICAZIONE_PROVVISORIA												= "61";	
	public final static String  DECRETO_REMISSIONE_SU_RUOLO                                                         = "64";
	public final static String  DECRETO_REMISSIONE_SU_RUOLO_II_GRADO                                                = "66";
	public final static String  DECRETO_ASSEGNAZIONE_NUOVO_RUOLO                                                    = "63";
	public final static String  DECRETO_ASSEGNAZIONE_NUOVO_RUOLO_II_GRADO                                           = "65";
	public final static String 	DECRETO_RIGETTO_STATO_RICHIESTA_SEQUESTRO											= "67";	
	public final static String 	DECRETO_PROROGA_SEQUESTRO															= "68";	
	public final static String 	DECRETO_PROROGA_SEQUESTRO_CDA														= "385";	
	public final static String 	DECRETO_PROROGA_SOSPENSIONE_TRIB													= "69";	
	public final static String 	DECRETO_PROROGA_SOSPENSIONE_CDA														= "339";
	public final static String 	SENTENZA_SU_RICORSO_II_GRADO_DA_CDA_CAM												= "365";	
	
	public final static String 	DECRETO_SOSPENSIONE_TEMP_TRIB														= "303";
	public final static String 	DECRETO_SOSPENSIONE_TEMP_CDA														= "323";
	
	public final static String 	DECRETO_SEQUESTRO_SOSPENSIONE_TRIB													= "304";	
	public final static String 	DECRETO_SEQUESTRO_SOSPENSIONE_CDA													= "324";
	public final static Long 	DECRETO_RICHIESTA_PARERE_QUESTORE													= 374L;
	public final static String 	DECRETO_RICHIESTA_PARERE_QUESTORE_S													= String.valueOf(DECRETO_RICHIESTA_PARERE_QUESTORE);

	public final static Integer	CAT_ATTO_RAPP_TRASMISSIONE														    = 387;
	
	//NUOVI CODICI RICORSI
	//public final static String	RICORSO_TARDIVO																	= "70";
	//public final static String	RICORSO_SUCCESSIVO															    = "71";
	
	public final static String	RICORSO_TARDIVO																	    = "337";
	public final static String	RICORSO_SUCCESSIVO																	= "338";
	
	public final static String	RICORSO_TARDIVO_CASSAZIONE															= "335";
	public final static String	RICORSO_SUCCESSIVO_CASSAZIONE														= "336";
	
	public final static String 	ATTO_GENERICO																		= "16";	
	public final static String 	TRASMISSIONE_CONFLITTO_COMPETENZA													= "26";	
	public final static String 	DECISIONE_CONFLITTO_COMPETENZA														= "27";	
	public final static String 	RICHIESTA_INDAGINI																	= "28";	
	public final static String 	ESITO_INDAGINI																		= "29";	
	public final static String 	VISTO_ATTI																			= "32";	
	public final static String 	CERTIFICATO_PENALE																	= "50";	
	public final static String 	CERTIFICATO_CARICHI_PENDENTI														= "51";	
	public final static String 	CERTIFICATO_ANAGRAFICO																= "52";	
	public final static String 	CERTIFICATO_MORTE																	= "53";	
	public final static String 	RICHIESTA_PROROGA_SEQUESTRO															= "56";	
	public final static String  CORREZIONE_ERRORE_MATERIALE															= "314";	
	public final static String  VERBALE_UDIENZA																		= "313";
	public final static String  VERBALE_RINVIO_UDIENZA																= "21";
	
	public final static String	DECRETO_CONVALIDA_SEQUESTRO_URGENTE													= "305";
	public final static String	DECRETO_CONVALIDA_SEQUESTRO_ANTICIPATO												= "306";
	public final static String	DECRETO_INCOMPETENZA_PROCURA														= "310";
	public final static String	DECRETO_INCOMPETENZA_RESTITUZIONE_ATTI_PROCURA										= "308";
	public final static String	DECRETO_INCOMPETENZA_TRASF_ALTRO_TRIBUNALE											= "309";
	public final static String	DECRETO_REVOCA_SEQUESTRO															= "307";
	public final static String	DECRETO_REVOCA_SEQUESTRO_II_GRADO													= "327";
	public final static String	DECRETO_SEQUESTRO_ANTICIPATO														= "301";
	public final static String	DECRETO_SEQUESTRO_BENI																= "304";
	public final static String	DECRETO_SEQUESTRO_ORDINARIO															= "302";
	public final static String	ACCERTAMENTO_QUESTURA																= "312";
	public final static String	DECRETO_SEQUESTRO_ORDINARIO_II_GRADO												= "322";
	public final static String	DECRETO_SEQUESTRO_URGENTE															= "300";
		
	public final static String	REVOCAZIONE_DELLA_CONFISCA															= "328";
	public final static String	SENTENZA_REVOCAZIONE_DELLA_CONFISCA													= "329";
	public final static String	RICHIESTA_PARERE																	= "330";
	public final static String	PARERE																				= "331";
	public final static String	REVOCA_DECRETO_OPPOSIZIONE_DISSEQUESTRO												= "332";
	public final static String	REVOCA_DECRETO_OPPOSIZIONE_DISSEQUESTRO_II_GRADO									= "333";
	
	public final static String	DECRETO_ORDINANZA_REST_BENI															= "334";
	
	public final static String	REDAZIONE_SCHEDA																	= "101";
	public final static String	FOGLIO_COMPLEMENTARE																= "104";
	
	public final static String	ATTO_VERSIONAMENTO_SOGGETTO							    						    = "55";
	public final static String  SENTENZA_DI_CASSAZIONE_DA_CDA														= "21";	
	
	public final static String AGENZIA_BENI_CONFISCATI 																= "9999990000000";
	
	public final static String ISTANZA_AUTORIZZAZIONE_UTILIZZO_BENI													= "344";
	public final static String ISTANZA_AUTORIZZAZIONE_AMMINISTRARE_BENI_IN_COMUNIONE								= "345";
	public final static String ISTANZA_AMMINISTRAZIONE_ORDINARIA													= "346";
	public final static String ISTANZA_AMMINISTRAZIONE_STRAORDINARIA												= "347";	
	public final static String PROVVEDIMENTO_SU_ISTANZA_DELLE_PARTI_E_AMMINISTRATORE								= "348";	
	public final static String ISTANZA_CONTO_GESTIONE																= "356";
	public final static String PROVVEDIMENTO_CONTO_GESTIONE															= "357";
	public final static String ORDINANZA_ART14_CODICE_ANTIMAFIA														= "358";
	public final static String ORDINANZA_ART15_CODICE_ANTIMAFIA														= "363";
	public final static String AUTORIZ_INTERCETTAZIONI_TELEFONICHE													= "367";
	public final static String PROVV_DISTRUZIONE_INTERCETTAZIONI_TEL 											    = "368";
	public final static String PERDITA_EFFICACIA_SEQUESTRO 														    = "369";
	
	public final static String RICHIESTA_RINUNCIA_AMMINISTRATORI													= "370";
	public final static String PROVVEDIMENTO_RINUNCIA_AMMINISTRATORI												= "371";
	public final static String RELAZIONE_AMMINISTRATORE																= "372";
	  
	public final static String PROVVEDIMENTO_NOMINA_AMMINISTRATORI													= "362";
	public final static String PROVVEDIMENTO_REVOCA_AMMINISTRATORI													= "366";
	
	public final static String ORDINANZA_VENDITA_BENI             													= "386";
	
	// costante relativa alla condizione giuridica 'DETENUTO'
	public final static int		DETENUTO																			= 3;
	public final static String	FONTE_ALTRO																			= "12";
	public final static String	ID_UTENTE																			= "utente";
	public final static String	ID_CODICE_UTENTE																	= "codUtente";
	public final static String	ID_PROCEDIMENTO																		= "procedimento";
	public final static String	ID_TIPO_PROCEDIMENTO																= "idTipoProc";
	public final static String	ID_UFFICIO_UTENTE																	= "idUffOwner";
	public final static String	ISTANZA_DIFFIDA_SOGGETTO															= "311";
	// costante relativa all'istituto di detenzione estero
	public final static String	ISTITUTO_DETENZIONE_ESTERO															= "279";
	// codice relativo allo Stato Estero 'ITALIA'
	public final static int		ITALIA																				= 1;
	public final static String	ITALIA_SIGLA																		= "IT";
	public static String		MESSAGE																				= "customMessage";
	public static String		MESSAGE_ARGUMENTS																	= "customMessageArguments";
	public static String		MESSAGELIST																			= "customMessageList";
	public static String		LISTAMESSAGGIERRORI																	= "listaMessaggiErrori";
	// valore di default per selezione nulla da combo-box
	public final static String	NO_ITEM_SELECTED																	= "-1";
	public final static String	ORGANO_SEGNALANTE																	= "OS";
	public final static String	ORGANO_POLIZIA_GIUDIZIARIA															= "PG";
	public final static String	ORGANO_AUTORITA_GIUDIZIARIA															= "AG";
	public final static String	PAGE_PARAMETER_NAME																	= "page";
	public final static String	PROFILO																				= "profilo";
	// costanti relative alla categoria degli atti
	public final static String	PROPOSTA																			= "2";
	public final static String	RIAPERTO_PER_INCOMP_TRIB															= "38";
	public final static String	RUOLO_DIRETTO																		= "DIRETTO";
	public final static String	RUOLO_TERZO																			= "TERZO";
	public final static String	RUOLO_INTERVENTIENTE																= "INTERVENTIENTE";
	public final static String	ISTANZA_MODIFICA_PERSONALE															= "341";
	public final static String	ISTANZA_MODIFICA_PATRIMONIALE														= "340";
	public final static String	ISTANZA_PERMESSI															        = "342";
	public final static String	ISTANZA_CAUZIONE															        = "343";
	public final static String	ISTANZA_LIQUIDAZIONE_AMM													        = "354";
	public final static String	ISTANZA_TERZO_INTERVENIENTE															= "360";
	public final static String	ISTANZA_CORREZIONE_ERRORI_MATERIALI												    = "361";
	public final static int     ID_RUOLO_DIRETTO																    =1;
	public final static int	    ID_RUOLO_TERZO																	    =2;
	public final static int	    ID_RUOLO_INTERVENIENTE															    =3;
	// identifica i soggetti (diretti e terzi) che sono intestatari di un bene
	public final static int	    ID_RUOLO_TITOLARI																    =4;
	// identifica il tipo di soggetto
	public final static String	SOGGETTO_FISICO																		= "F";
	public final static String	SOGGETTO_GIURIDICO																	= "G";
	public final static long	STATOATTO_IN_LAVORAZIONE															= 1;
	public final static String	STATOATTO_IN_LAVORAZIONE_S															= "1";
	public final static String	STATOATTO_ARCHIVIATO															    = "11";
	public final static long	STATOATTO_INOLTRATO																	= 4;
	public final static long	STATOATTO_REGISTRATO																= 3;
	public final static long	STATOATTO_ACQUISITO																	= 6;
	public final static long	STATOPROC_GIUDIZIO_DEFINITIVO														= 22;
	public final static String	STATOPROC_IN_ACQUISIZ_PROPOSTA														= "5";
	public final static String	STATOPROC_IN_ACQUISIZ_ISTANZA														= "47";
	public final static long	STATOPROC_IN_LAVORAZIONE															= 34;
	public final static String	STATOPROC_ISCRITTO																	= "2";
	public final static String	STATOPROC_ISCRITTO_PER_OPPOSIZIONE													= "25";
	public final static String	STATOPROC_ISCRITTO_PER_REVOCAZIONE													= "43";
	public final static String	STATOPROC_ISTRUTTORIA																= "21";
	public final static String	STATOPROC_TRATTAZIONE_I_GRADO														= "6";
	public final static String	STATOPROC_TRATTAZIONE_II_GRADO														= "13";
	public final static String	STATOPROC_TRATTAZIONE_OPPOSIZIONE													= "26";
	public final static String	STATOPROC_TRATTAZIONE_REVOCAZIONE													= "44";
	public final static String	STATOPROC_ASSEGNATO																	= "29";
	public final static String	STATOPROC_IMPUGNATO_CDA															    = "12";
	public final static String	STATOPROC_DEFINITO_I_GRADO															= "11";
	public final static String	STATOPROC_DEFINITO_II_GRADO															= "23";
	public static final String	STATOPROC_INVIATO_AL_TRIBUNALE														= "3";
	public final static String	STATOPROC_RIAPERTO_PER_INCOMPETENZA_TRIBUNALE										= "38";
	public final static String	STATOPROC_INVIATO_IN_CDA										                    = "42";
	public static final String	STATOPROC_DEFINITO_IN_I_GRADO_PER_INCOMPETENZA_CON_RESTITUZIONE_ATTI_ALLA_PROCURA	= "35";
	public static final String	STATOPROC_DEFINITO_IN_I_GRADO_PER_INCOMPETENZA_CON_INVIO_ATTI_AL_TRIBUNALE			= "36";
	public static final String	STATOPROC_DEFINITO_IN_I_GRADO_PER_INCOMPETENZA_DELLA_PROCURA						= "37";
	public static final String	STATOPROC_ISCRITTO_IN_I_GRADO_DI_GIUDIZIO											= "4";
	public static final String	STATOPROC_RIUNITO																	= "20";
	public static final String	STATOPROC_TRATTAZIONE_GESTIONE														= "46";
	public static final String	STATOPROC_IMPUGNATO_CASSAZIONE														= "15";
	public static final int	    STATOPROC_RIUNITO_INTEGER   														= 20;
	public static String		SUCCESS_MESSAGE																		= "successCustomMessage";
	public static String		SUCCESS_MESSAGE_LIST																= "successCustomMessageList";
	public final static String	TIPO_ATTO_ACCERTAMENTO																= "26";
	public final static String	TIPO_ATTO_DECRETO																	= "3";
	public final static String	TIPO_ATTO_VERBALE																	= "16";
	public final static String	TIPO_ATTO_DECRETO_II_GRADO															= "18";
	public final static String	TIPO_ATTO_SENTENZA_CASSAZIONE														= "20";
	public final static String	TIPO_ATTO_SENTENZA_CASSAZIONE_DA_CDA 												= "21";
	public final static String	TIPO_ATTO_FONTE																		= "12";
	public final static String	TIPO_ATTO_ISTANZA																	= "25";
	public final static String	TIPO_ATTO_RELAZIONE_AMM																= "29";
	public final static String	TIPO_ATTO_PROVVEDIMENTO															    = "28";
	public final static String	TIPO_ATTO_IMPUGNAZIONE																= "4";
	public final static String	TIPO_ATTO_IMPUGNAZIONE_II_GRADO  													= "19";
	public final static String	TIPO_ATTO_VERSIONAMENTO  															= "24";
	public final static String	TIPO_ATTO_PROPOSTA																	= "9";
	public final static String	TIPO_ATTO_ISTANZA_CAM																= "5";
	public final static String	TIPO_ATTO_ARCHIVIAZIONE																= "15";
	public final static String	TIPO_ATTO_ATTI_IN_ARCHIVIO															= "22";
	public final static String	TIPO_ATTO_ESECUTIVO																	= "17";
	public final static String	TIPO_UFFICIO_PROCURA																= "17";
	public final static Long TIPO_UFFICIO_PROCURA_L 																= Long.valueOf(TIPO_UFFICIO_PROCURA);
	public final static String	TIPO_UFFICIO_TRIBUNALE																= "19";
	public final static long	TIPO_UFFICIO_TRIBUNALE_L															= 19;
	public final static String	TIPO_UTENTE																			= "tipoUtente";
	public final static String	TIPO_UTENTE_SMP																		= "SMP";
	public final static String	TIPO_UTENTE_SMP_GIU																	= "SMP_GIU";
	public final static String	TIPOPREV_ANTIMAFIA																	= "2";
	public final static String	TIPOPREV_ANTITERRORISMO																= "3";
	public final static String	TIPOPREV_ORDINARIA																	= "1";
	//public final static String	TIPOPROC_AGGRAVAMENTO																= "9";
	public final static String	TIPOPROC_PATRIMONIALE																= "2";
	public final static String	TIPOPROC_PERSONALE																	= "4";
	public final static String	TIPOPROC_PERSONALE_E_PATRIMONIALE													= "1";
	public final static String	TIPOPROC_PATRIMONIALE_ACCERTAMENTO													= "6";
	public final static String	TIPOPROC_PERSONALE_ACCERTAMENTO														= "7";
	public final static String	TIPOPROC_PERSONALE_E_PATRIMONIALE_ACCERTAMENTO										= "5";
	public final static String	TIPOPROC_ACCERTAMENTO																= "8";
	public final static String	TIPOPROC_REVOCAZIONE_CONFISCA														= "10";
	public final static String	TIPOPROC_RIESAME_PROVV_QUESTURA														= "11";
	public final static String  TIPOPROC_AGGRAVAMENTO_PERSONALE														= "8";
	public final static String  TIPOPROC_OPPOSIZIONE_ALLA_RESTITUZIONE												= "9";
	public final static String  TIPOPROC_OPPOSIZIONE_DIVIETI_QUESTORE												= "11";
	public final static String  TIPOPROC_GESTIONE_BENI																= "12";
	public final static long	TIPOPROC_MODIFICA_PATRIMONIALE_CAM													= 13;
	public final static long	TIPOPROC_MODIFICA_PERSONALE_CAM													    = 14;
	public final static long	TIPOPROC_PERMESSI_CAM													    		= 16;
	public final static long	TIPOPROC_CAUZIONI_CAM													    		= 15;
	public final static long	TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM												= 17;
	public final static long	TIPOPROC_TERZO_INTERVENIENTE_CAM													= 18;
	public final static long	TIPOPROC_CORREZIONE_ERRORI_CAM														= 19;
				
	public final static long	TIPO_MISURA_SEQUESTRO																= 5;
	public final static long	TIPO_MISURA_SEQUESTRO_URGENTE														= 6;
	public final static long	TIPO_MISURA_SEQUESTRO_ANTICIPATO													= 16;
	public final static long  	TIPO_MISURA_SEQUESTRO_NON_CONVALIDATO												= 22;
	public final static long  	TIPO_MISURA_CONFISCA_PREVIO_SEQUESTRO												= 4;
	public final static long  	TIPO_MISURA_CONFISCA																= 9;
	public final static long  	TIPO_MISURA_CONFISCA_PREVIO_SEQUESTRO_URGENTE										= 14;
	public final static long  	TIPO_MISURA_CONFISCA_PREVIO_SEQUESTRO_ANTICIPATO									= 17;
	
	public final static String CONVALIDA ="C";
	public final static String REVOCA = "R";
		
	//tipi di fascicolo
	public final static String	TIPO_FASCICOLO																		="TIPO_FASCICOLO";
	public final static String	TIPO_FASCICOLO_COLLEGATO															="TIPO_FASCICOLO_COLLEGATO";
	
	public final static int 	TIPO_FASCICOLO_MERITO																=1;
	public final static String 	TIPO_FASCICOLO_MERITO_S																= String.valueOf(TIPO_FASCICOLO_MERITO);
	public final static int 	TIPO_FASCICOLO_ACCERTAMENTO															=2;
	public final static String 	TIPO_FASCICOLO_ACCERTAMENTO_S														= String.valueOf(TIPO_FASCICOLO_ACCERTAMENTO);
	public final static int 	TIPO_FASCICOLO_AGGRAVAMENTO															=3;
	public final static String 	TIPO_FASCICOLO_AGGRAVAMENTO_S														= String.valueOf(TIPO_FASCICOLO_AGGRAVAMENTO);
	public final static int 	TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE													=4;
	public final static String 	TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE_S												= String.valueOf(TIPO_FASCICOLO_OPP_DIVIETI_QUESTORE);
	public final static int 	TIPO_FASCICOLO_OPP_REVOCA_SEQUESTRO													=5;
	public final static String 	TIPO_FASCICOLO_OPP_REVOCA_SEQUESTRO_S												= String.valueOf(TIPO_FASCICOLO_OPP_REVOCA_SEQUESTRO);
	public final static int 	TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO												    =6;
	public final static String 	TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO_S												= String.valueOf(TIPO_FASCICOLO_CAMERA_DI_CONSIGLIO);
	public final static int 	TIPO_FASCICOLO_REVOCAZIONE														    =7;
	public final static String 	TIPO_FASCICOLO_REVOCAZIONE_S														= String.valueOf(TIPO_FASCICOLO_REVOCAZIONE);
	public final static int 	TIPO_FASCICOLO_GESTIONE														    	=8;
	public final static String 	TIPO_FASCICOLO_GESTIONE_S															= String.valueOf(TIPO_FASCICOLO_GESTIONE);
	
	public final static long	ID_TIPO_PROCEDIMENTO_AGGRAVAMENTO	= 8L;
	
	public final static int TATTO_CAMPO_NOTE_LEN = 4000; // Lunghezza massima del campo note nella tabella T_ATTO

	public static final String EUR = "EUR";

	public final static class RegExp {
		// 20180625: modificata la regExp --> A-z --> A-Z per cognome, nome e stato
		public static final String	COGNOME		= "[a-zA-Z\\s']*";
		public static final String	NOME		= "[a-zA-Z\\s']*";
		public static final String	CAP			= "[0123456789\\s]*";
		public static final String	TELEFONO	= "[0123456789\\s]*";
		public static final String	STATO		= "[a-zA-Z\\s']*";
	}

	//TODO commentare
	public final static class Key {
		public static final String	DATI_PER_INDIETRO	= "datiPerIndietro";
	}
	
    public final static class SezioniConstants {
        public final static String K_SEZIONE_ORDINARIA = "1";
        public final static String K_SEZIONE_ESPERTI = "2";
        public final static String K_SEZIONE_ALL = "3";
    }	
    
    public final static class OrdineConstants {
        public final static String K_ORDINE_COMMERCIALISTI = "C";
        public final static String K_ORDINE_AVVOCATI = "A";
    }    

	public static final String	ID_PER_MENU_DETTAGLIO_AGGRAVAMENTO				= "45";
	public static final String	ID_PER_MENU_DETTAGLIO_RIESAME					= "16";
	public static final String	ID_PER_MENU_DETTAGLIO_PREVENZIONE				= "14";
	public static final String	ID_PER_MENU_DETTAGLIO_ACCERTAMENTO				= "17";
	public static final String	ID_PER_MENU_PROCEDIMENTO_COLLEGATO				= "173";
	public final static String	ID_K_MENU_CONSERVATORIE							= "109";
	public final static String	ID_K_MENU_CONSERVATORIE_AGGIORNA_COMUNICAZIONE	= "111";
	public static final String	ID_PER_MENU_DETTAGLIO_GESTIONE				    = "141";	
	public final static int		ID_K_MENU_NUOVO_SOGGETTO_FISICO_PROCURA			= 60;
	public final static int		ID_K_MENU_NUOVO_SOGGETTO_GIURIDICO_PROCURA		= 61;
	public final static int		ID_K_MENU_NUOVO_ATTO_PROCURA					= 24;
	public final static int		ID_K_MENU_NUOVO_ATTO_AGGRAVAMENTO_PROCURA		= 65;
	public final static int		ID_K_MENU_NUOVO_BENE_PROCURA					= 31;
	public final static int		ID_K_MENU_NUOVO_SOGGETTO_FISICO_TRIBUNALE		= 41;
	public final static int		ID_K_MENU_NUOVO_SOGGETTO_GIURIDICO_TRIBUNALE	= 42;
	public final static int		ID_K_MENU_NUOVO_ATTO_TRIBUNALE					= 39;
	public final static int		ID_K_MENU_NUOVO_ATTO_AGGRAVAMENTO_TRIBUNALE		= 74;
	public final static int		ID_K_MENU_NUOVO_BENE_TRIBUNALE					= 44;
	public final static int		ID_K_MENU_ATTO_BENI								= 71;
	public final static int		ID_K_MENU_ATTO_COLLEGIO							= 72;
	public final static int     ID_K_MENU_UDIENZE_DECRETO_SEQUESTRO_CONVALIDA   = 83;
	
	public final static int		ID_K_MENU_ATTO_AMMINISTRATORI		   			= 54;

	public final static int		ID_K_MENU_ATTO_SOGGETTI_TRIBUNALE				= 49;
	public final static int		ID_K_MENU_ATTO_BENI_TRIBUNALE					= 50;
	public final static int		ID_K_MENU_ATTO_BENI_NUOVO_TRIBUNALE				= 57;
	public final static int		ID_K_MENU_ATTO_BENI_NUOVO_PROCURA				= 74;
	public final static int		ID_K_MENU_ATTO_AMMINISTRATORE_NUOVO 			= 65;
	
	public final static int		ID_K_MENU_SOGGETTI_DUPLICATI				    = 91;
	
	public final static int		ID_K_MENU_ATTO_UDIENZE_PROCURA					= 96;
	
	public final static int		ID_K_MENU_SOGGETTI_ELENCO_INTERVENIENTI			= 189;
	public final static int		ID_K_MENU_SOGGETTI_NUOVO_INTERVENIENTE_FISICO   = 190;
	public final static int		ID_K_MENU_SOGGETTI_NUOVO_INTERVENIENTE_GIUR     = 191;
	
	public static final String	ID_TIPO_UFFICIO									= "idTipoUfficio";
	public static final int		TIPO_UFFICIO_CORTE_D_APPELLO					= 6;
	public static final String TIPO_UFFICIO_CORTE_D_APPELLO_S = String.valueOf(TIPO_UFFICIO_CORTE_D_APPELLO);
	public static final String	ID_PADRE_MENU									= "1";
	public static final long	TIPO_UFFICIO_PROCURA_GENERALE					= 8L;
	public static final int		TIPO_UFFICIO_PROCURA_M					        = 17;  // MOD GINESTI PER GEST BENIDELPROCEDMENTO
	
	//RUOLI MAGISTRATI
//	public static final int ID_TIPO_RUOLO_PM									=1;
	public static final String ID_TIPO_RUOLO_PM									="1";
	public static final String ID_TIPO_RUOLO_PRES								="2";
	public static final String ID_TIPO_RUOLO_GIU_LAT							="3";
	public static final String ID_TIPO_RUOLO_GIU_DEL							="4";
	
	// CAMERALINI
	public final static String	ID_K_MENU_PROCEDIMENTO							= "1";
	
	public static final String CARATTERE_DIVISORE_CONC_CAMPI ="§";
	public static final String KEY_BREADCRUMB_MANUAL = "KEY_BREADCRUMB_MANUAL";
	public static final String NAME_DA_DISABILITARE = "NAME_DA_DISABILITARE";
	
	public static final String KEY_DEFAULT_CAMPO_HIDDEN = "9999";
	public static final String PREFISSO_FASCICOLO = "MP";
	
	
	public final class AuthRichiestaCertificatoPenale {
		public static final String AUTH_USERNAME = "richCertPenaleUsername";
		public static final String AUTH_PASSWORD = "richCertPenalePassword";
		
	}
	
	public final class ComunicazioniCasellario {
		public static final String STATO_COMUNICAZIONE_INVIATA = "2";
		public static final String STATO_COMUNICAZIONE_CANCELLATA = "4";
		public static final String STATO_COMUNICAZIONE_OMONIMI = "1";
	}

	/**
	 * Tipologie Bene
	 * @author g.gabriele
	 *
	 */
	public final class TipologieBeni{
		public static final long ID_TIPO_IMMOBILE						=1L;
		public static final long ID_TIPO_MOBILE							=2L;
		public static final long ID_TIPO_MOBILE_REGISTRATO				=3L;
		public static final long ID_TIPO_FINANZIARIO					=4L;
		public static final long ID_TIPO_MOBILE_AZIENDA					=5L;
		public static final String ID_TIPO_IMMOBILE_S					="1";
		public static final String ID_TIPO_MOBILE_S						="2";
		public static final String ID_TIPO_MOBILE_REGISTRATO_S			="3";
		public static final String ID_TIPO_FINANZIARIO_S				="4";
		public static final String ID_TIPO_MOBILE_AZIENDA_S				="5";
		
	}
	
	/**
	 * Categorie Beni
	 * @author g.gabriele
	 *
	 */
	public static class CategoriaBeni{
		public static final long ID_TIPO_DENARO_CONTANTE		=6L;
		public static final long ID_TIPO_COLLEZIONE				=7L;
		public static final long ID_TIPO_ANIMALI				=8L;
		public static final long ID_ALTRI_OGGETTI				=9L;
		public static final long ID_TIPO_VEICOLO				=10L;
		public static final long ID_TIPO_IMMATERIALE			=11L;
		public static final long ID_ALTRI_BENI_FINANZIARI = 16L;
		public static final long ID_POLIZZE = 32L;
		
		public static final String ID_TIPO_DENARO_CONTANTE_S	= String.valueOf(ID_TIPO_DENARO_CONTANTE);
		public static final String ID_TIPO_COLLEZIONE_S			= String.valueOf(ID_TIPO_COLLEZIONE);
		public static final String ID_TIPO_ANIMALI_S			= String.valueOf(ID_TIPO_ANIMALI);
		public static final String ID_ALTRI_OGGETTI_S			= String.valueOf(ID_ALTRI_OGGETTI);
		public static final String ID_TIPO_VEICOLO_S			= String.valueOf(ID_TIPO_VEICOLO);
		public static final String ID_TIPO_IMMATERIALE_S		= String.valueOf(ID_TIPO_IMMATERIALE);
		public static final String ID_ALTRI_BENI_FINANZIARI_S = String.valueOf(ID_ALTRI_BENI_FINANZIARI);
		public static final String ID_POLIZZE_S = String.valueOf(ID_POLIZZE);
		
		public static final long ID_TIPO_SOCIETA_NON_DEFINITA		= 30L;
		public static final String ID_TIPO_SOCIETA_NON_DEFINITA_S	= String.valueOf(ID_TIPO_SOCIETA_NON_DEFINITA);
	}
	
	/**
	 * SottoCategorie
	 * @author g.gabriele
	 *
	 */
	public static class SottoCategoriaBeni{
		public static final long CONTANTE = 35L;
		public static final long LIBRETTO_POSTALE = 36L;
		public static final long LIBRETTO_BANCARIO = 37L;
		public static final long CC_POSTALE = 38L;
		public static final long CC_BANCARIO = 39L;
		public static final long TITOLO_DI_STATO = 86L;
		public static final long CERTIFICATO_DI_DEPOSITO = 87L;
		public static final long OBBLIGAZIONE = 88L;
		public static final long AZIONI = 89L;
		public static final long STRUMENTI_FINANZIARI_PARTECIPATIVI = 90L;
		public static final long TITOLI_ATIPICI = 91L;
		public static final long CONTRATTO_LEASING = 95L;
		public static final long CREDITI_VARI = 96L;
		public static final long POLIZZA_ASSICURATIVA = 97L;

		public static final long TIPI_NON_DEFINITI_MIN_RANGE	= 102L;
		public static final long TIPI_NON_DEFINITI_MAX_RANGE	= 117L;
		
		// sottocategorie altri beni finanziari (polizze)
		public static final long POLIZZA_ASSICURATIVA_GESTITA_DA_BANCA = 123L;
		public static final long POLIZZA_TEMPORANEA_CASO_MORTE = 181L;
		public static final long POLIZZA_CASO_VITA = 182L;
		public static final long POLIZZA_CASO_MORTE_A_VITA_INTERA = 183L;
		public static final long POLIZZA_CASO_VITA_CON_CONTROASSICURAZIONE = 184L;
		public static final long POLIZZA_VITA_MISTA = 185L;
		public static final long POLIZZA_CONTRATTO_CAPITALIZZAZIONE = 186L;
		public static final long POLIZZA_ADESIONE_FONDI_PENSIONE = 187L;
		public static final long POLIZZA_ALTRA_POLIZZA = 188L;
		public static final long POLIZZA_FONDI_PENSIONE = 203L;

		public static final String POLIZZA_ASSICURATIVA_GESTITA_DA_BANCA_S = String.valueOf(POLIZZA_ASSICURATIVA_GESTITA_DA_BANCA);
		public static final String POLIZZA_TEMPORANEA_CASO_MORTE_S = String.valueOf(POLIZZA_TEMPORANEA_CASO_MORTE);
		public static final String POLIZZA_CASO_VITA_S = String.valueOf(POLIZZA_CASO_VITA);
		public static final String POLIZZA_CASO_MORTE_A_VITA_INTERA_S = String.valueOf(POLIZZA_CASO_MORTE_A_VITA_INTERA);
		public static final String POLIZZA_CASO_VITA_CON_CONTROASSICURAZIONE_S = String.valueOf(POLIZZA_CASO_VITA_CON_CONTROASSICURAZIONE);
		public static final String POLIZZA_VITA_MISTA_S = String.valueOf(POLIZZA_VITA_MISTA);
		public static final String POLIZZA_CONTRATTO_CAPITALIZZAZIONE_S = String.valueOf(POLIZZA_CONTRATTO_CAPITALIZZAZIONE);
		public static final String POLIZZA_ADESIONE_FONDI_PENSIONE_S = String.valueOf(POLIZZA_ADESIONE_FONDI_PENSIONE);
		public static final String POLIZZA_ALTRA_POLIZZA_S = String.valueOf(POLIZZA_ALTRA_POLIZZA);
		public static final String POLIZZA_FONDI_PENSIONI_S = String.valueOf(POLIZZA_FONDI_PENSIONE);

		public static final long CARTE_DI_CREDITO = 200L;
		public static final long CASSETTE_DI_SICUREZZA = 201L;
		public static final long CONTRATTI_DERIVATI = 202L;
		public static final long GESTIONE_COLLETTIVA_DEL_RISPARMIO = 204L;
		public static final long GESTIONE_PATRIMONIALE = 205L;
		public static final long DENARO_RICAVATO_DA_VENDITA = 206L;
}
	
	public static class TipoCarattereMisura{
		public static final String SEQUESTRO_ORDINARIO = "SO";
		public static final String SEQUESTRO_ANTICIPATO = "SA";
		public static final String SEQUESTRO_URGENTE = "SU";
		
		public static final String SEQUESTRO_REVOCA = "SR";
		public static final String SEQUESTRO_NON_CONVALIDATO = "SN";
	}
	
	
	public static class TipoAtto{
		public static final int TIPO_ATTO 						= 9;
		public static final int TIPO_DECRETO 					= 3;	
		public static final int TIPO_ATTO_GENERICO 				= 14;
		public static final int TIPO_ATTO_RICEVUTA 				= 27;		
		public static final String TIPO_ATTO_GENERICO_S 		= String.valueOf(TIPO_ATTO_GENERICO);
	}
	
	public static class CategoriaAtto{
		public static final int CAT_CORREZIONE_ERRORE_MATERIALE = 314;
		public static final String CAT_RIUNIONE = "13";
		public static final String CAT_STRALCIO = "14";
		
		public static final String CAT_CORREZIONE_ERRORE_MATERIALE_S = String.valueOf(CAT_CORREZIONE_ERRORE_MATERIALE);
		
		public final static String	CERTIFICATO_PENALE = "50";
		public final static String	ATTI_IN_ARCHIVIO = "46";
		public final static String	RESTITUZIONE_ATTI_IN_TRIBUNALE = "54";
		public final static String	SENTENZA_SU_RICORSO_II_GRADO = "40";
		public final static String DECRETO_OPPOSIZIONE_DISSEQUESTRO = "23";
	}
	
	public static class TipiOperazioneCRUD{
		public static final String CREATE 						= "INS";
		public static final String READ 						= "READ";
		public static final String UPDATE 						= "MOD";
		public static final String DELETE 						= "CANC";
	}
	
	public static class TipiFlagDecretoRevoca{
		public static final String REVOCA 						= "REV";
		public static final String DECRETO 						= "DEC";
	}
	
	public static class TipiOperazioneConvalidaNonConvalida{
		public static final String CONVALIDA  					="CONV";
		public static final String NON_CONVALIDA				="NO_CONV";			
	}
	
	/**
	 * Tipi di trasformazione
	 * @author g.gabriele
	 *
	 */
	public static class TipiTrasformazione{
		public static final Character TRASFORMAZIONE 			= Character.valueOf('T');
		public static final Character LEGAME 					= Character.valueOf('L');
		
		public static final int TIPO_VENDITA 					= 12;
		public static final String TIPO_VENDITA_S 				= String.valueOf(TIPO_VENDITA);
		
		public static final int ALTRO_TIPO 						= 13;
		public static final String ALTRO_TIPO_S 				= String.valueOf(ALTRO_TIPO);
	    
		public static final int FRAZIONAMENTO 						= 1;
		public static final String FRAZIONAMENTO_S 				= String.valueOf(FRAZIONAMENTO);
		
	}
	
	  public final static class GenericConstants {

	        public final static String K_STATO_ITALIA               	= "1";
	        public final static String ID_HASHMAP_SESSION            	= "idHashMap";
	        public final static String K_STATO_ANAGRAFICA_ISCRITTO   	= "4";
	        public final static String K_STATO_ANAGRAFICA_SOSPESO    	= "5";
	        public final static String K_STATO_ANAGRAFICA_CANCELLATO 	= "6";
	    }
	  
	  public final static class TipoMisura {
	        public final static String PATRIMONIALE_ID              	=  "PATR";
	        public final static String PERSONALE_ID            			=  "PERS";
	        public final static String PATRIMONIALE_DESCR           	=  "Patrimoniale";
	        public final static String PERSONALE_DESCR           		=  "Personale";
	        public final static String NO_MIS_ID           	       		=  "NO_MIS";
	        
	        //Tipi misura su table T_TP_TIPO_MISURA
	    	public static final String REVOCA_SEQUESTRO 				= "20";
	  }
	  
	  public final static class TipoMessaggiDb{
		  
		  	public static final String OK 								= "OK";
		  	public static final String KO 								= "KO";
		  	public static final String MESSAGE							= "MESSAGGIO";
		  	public static final String STATUS							= "STATUS";
		  	public static final String KEY								= "KEY";	
	  }
	  
	  public final static class FormattazioneAnnoNumeroAtto{
			public final static String  NUMERO_ATTO_NON_DISP 			= " " + NON_DISPONIBILE + " ";
			public final static String 	DIVISORE_ANNO_NUMERO_ATTO 		= " / ";
	  }
	  
	  public final static class Flag{
			public final static Character S 								= 'S';
			public final static Character N 								= 'N';
			
			public final static String SI 									= "SI";
			public final static String NO 									= "NO";
			
			public final static String SPEDITO								="S";
			public final static String SPEDITO_LABEL						="Spedito";
			public final static String RICEVUTO								="R";
			public final static String RICEVUTO_LABEL						="Ricevuto";
	  }
	 
	  public final static String  ESITO_CONFISCA					         	= "118";
	  public final static String  ESITO_CONFISCA_SEG_SOSP						= "119";
	  public final static String  ESITO_CONFISCA_SEQ						    = "123";
	  
	  public final static String  ESITO_RIGETTA_CONFISCA						= "120";

	  public final static String  FUG_FLAG_PREVENTIVO						="P";
	  public final static String  FUG_FLAG_CONSERVATIVO						="C";
	  public final static String  FUG_FLAG_PROBATORIO						="B";
	  public final static String  FUG_TIPO_REGISTRO_RPRO3 			="RPRO3";
	  
	  public final static String  ANNULLA_MODELLO_FUG						="AN";
	  public final static String  FUG_MODELLO_A								="A";
	  public final static String  FUG_MODELLO_D								="D";
	  public final static String  FUG_MODELLO_C								="C";
	  public final static String  FUG_MODELLO_H								="H";
	  public final static String  FUG_MODELLO_C_BIS							="C2";
	  public final static String  FUG_STATO_INVIATO 						="I";
	  public final static String  FUG_STATO_ACQUISITO						="A";
	  public final static String  FUG_STATO_NON_VALIDATO					="N";
	  public final static String  FUG_STATO_INVIATO_CON_ERRORI				="E";
	  public final static String  FUG_RICHIESTO_ANNULLAMENTO				="0";
	  public final static String  FUG_INVIO_ANNULLATO				="1";
	  public final static String  FUG_INVIO_NON_ANNULLABILE				="2";
	  public final static String  FUG_RICHIESTO_ANNULLAMENTO_STR = "Richiesto annullamento";
	  public final static String  FUG_INVIO_ANNULLATO_STR = "Annullato";
	  public final static String  FUG_INVIO_NON_ANNULLABILE_STR = "Non annullabile";
	  public final static String  FUG_DEFINITA_POSIZIONE_STR = "Definita pos.";
	  public final static String  FUG_INVIO_NON_VALIDATO_STR = "Non validato";

	  public final static String  FUG_RICHIESTA = "RICHIESTAXML";
	  public final static String  FUG_ESITO = "ESITOXML";

	  public final static String  FLAG_SEQUESTRO = "S";
	  public final static String  FLAG_CONFISCA = "C";

	  // I valori seguenti sono nomi di proprieta` contenute nel 
	  // file di sistema di JBoss "properties-service.xml"
	  // nella cartella "<JBOSS_INST_DIR>/jboss-as/server/default/deploy"
	  public static final String URI_WS_ALBO = "smp.ws.albo";
	  public static final String URI_WS_EQUITALIA_FUG = "smp.ws.equitalia.fug";
	  public static final String URI_WS_CASELLARIO = "smp.ws.casellario";
	  public static final String URI_WS_CASELLARIO_FC = "smp.ws.casellario.FC";
	  public static final String URI_WS_CASELLARIO_RICERCA = "smp.ws.casellario.ricerca";
	  public static final String URI_WS_SERVIZI_TRASMISSIONE = "smp.ws.servizi.trasmissione";
	  public static final String URI_WS_SERVIZI_DEPOSITI = "smp.ws.servizi.depositi";
	  public static final String URI_WS_SERVIZI_ACCETTAZIONE = "smp.ws.servizi.accettazione";
	  public static final String URI_WS_RICORSO_CASSAZIONE = "smp.ws.ricorso.cassazione";
	  public static final String URI_WS_AGENZIA_ENTRATE = "smp.ws.agenzia.entrate";
	  public static final String URI_WS_REGINDE = "smp.ws.reginde";
	  public static final String URI_WS_ANBSC = "smp.ws.anbsc";
	  public static final String URI_WS_POLIZIA = "smp.ws.polizia";
	  // 20180625: LOGOUT spostato su file di configurazione di JBOSS "properties-service.xml"
	  public static final String IAA_LOGOUT_URL = "iaa.logout.url";

	  @SuppressWarnings("serial")
	  public static Map<String, String> modello = new HashMap<String, String>() {{
			put(FUG_MODELLO_A, "MOD. A");
			put(FUG_MODELLO_D, "MOD. D");
			put(FUG_MODELLO_C, "MOD. C");
			put(FUG_MODELLO_H, "MOD. H");
			put(FUG_MODELLO_C_BIS, "MOD. C-Bis");
		}};
		public static String getModello(String in) {
			return modello.get(in);
		
		}
		
		@SuppressWarnings("serial")
		public static Map<String, String> statoInvio = new HashMap<String, String>() {{
			put(FUG_STATO_INVIATO, "Inviato");
			put(FUG_STATO_ACQUISITO, "Acquisito");
			put(FUG_STATO_NON_VALIDATO, FUG_INVIO_NON_VALIDATO_STR);
			put(FUG_STATO_INVIATO_CON_ERRORI, "Non inviato");
		}};
		public static String getStatoInvio(Character in) {
			return statoInvio.get(Character.toString(getValid(in)));
		}

		@SuppressWarnings("serial")
		public static Map<String, String> statoRichiesta = new HashMap<String, String>() {{
			put(FUG_RICHIESTO_ANNULLAMENTO, "Richiesto annullamento");
			put(FUG_INVIO_ANNULLATO, "Annullato");
			put(FUG_INVIO_NON_ANNULLABILE, "Non annullabile");
		}};
		public static String getStatoRichiesta(Character in) {
			return statoRichiesta.get(Character.toString(getValid(in)));
		}

		private static Character getValid(Character in) {
			return (in != null) ? in : ' ';
		}

		/**
		 * Tipo risorse Equitalia
		 * @author 
		 */
		public final class TipoRisorseEquitalia{
			public static final String CONTANTE									="1";
			public static final String LIBRETTO									="3";
			public static final String LIBRETTO_DEPOSITO_GIUDIZIARIO			="98";
			public static final String CONTO_CORRENTE							="1";
			public static final String TITOLO_DI_STATO   						="2";
			public static final String CERTIFICATO_DI_DEPOSITO					="7";
			public static final String OBBLIGAZIONE		   						="2";
			public static final String AZIONI			   						="2";
			public static final String STRUMENTI_FINANZIARI_PARTECIPATIVI		="2";
			public static final String TITOLI_ATIPICI	 						="2";
			public static final String CONTRATTO_LEASING   						="18";
			public static final String CREDITI_VARI		   						="99";
			public static final String POLIZZA_ASSICURATIVA_GESTITA_DA_BANCA 	="23";
			public static final String POLIZZA_ASSICURATIVA_VITA_CASO_MORTE		="81"; // Temporanea caso morte
			public static final String POLIZZA_ASSICURATIVA_VITA_CASO_VITA		="82"; // Caso vita
			public static final String POLIZZA_ASSICURATIVA_VITA_MVI			="83"; // Caso morte a vita intera	
			public static final String POLIZZA_ASSICURATIVA_VITA_C				="84"; // Caso vita con controassicurazione
			public static final String POLIZZA_ASSICURATIVA_VITA_MISTA			="85"; // Mista
			public static final String CONTRATTO_DI_CAPITALIZZAZIONE			="86";
			public static final String CONTRATTO_DI_ADESIONE_A_FONDI_PENSIONE_APERTI ="87";
			public static final String ALTRA_POLIZZA_CONTRATTI					="88";
			public static final String CARTE_DI_CREDITO							="15";
			public static final String CASSETTE_DI_SICUREZZA					="12";
			public static final String CONTRATTI_DERIVATI						="14";
			public static final String FONDI_PENSIONE							="19";
			public static final String GESTIONE_COLLETTIVA_DEL_RISPARMIO		="5";
			public static final String GESTIONE_PATRIMONIALE					="6";
			public static final String DENARO_RICAVATO_DA_VENDITA				="1"; //o 3
		}
		
	//inizio tipi messaggio per POLIZIA
	public final static int POLIZIA_SEQUESTRO												=1;
	public final static int POLIZIA_REVOCA_SEQUESTRO										=2;
	public final static int POLIZIA_PROPOSTE_MIS_PERS										=3;
	public final static int POLIZIA_PROPOSTE_MIS_PATR										=4;
	public final static int POLIZIA_PROPOSTE_INDAGINI_SOGG									=5;
	public final static int POLIZIA_DECRETI_SOSPENSIONE_REST_BENI							=6;
	public final static int POLIZIA_DECRETI_PROVVISORI_APPLICAZIONE							=7;
	public final static int POLIZIA_DECRETI_PROVVISORI_REVOCA								=8;
	public final static int POLIZIA_DECRETI_DECISORI_I_GRADO_PERS							=9;
	public final static int POLIZIA_DECRETI_DECISORI_I_GRADO_PATR							=10;
	public final static int POLIZIA_DECRETI_DECISORI_II_GRADO_PERS							=11;
	public final static int POLIZIA_DECRETI_DECISORI_II_GRADO_PATR							=12;
	public final static int POLIZIA_SENTENZA_CASSAZIONE_PERS								=13;
	public final static int POLIZIA_SENTENZA_CASSAZIONE_PATR								=14;
	public final static int POLIZIA_DEFINITIVITA_PERS										=15;
	public final static int POLIZIA_DEFINITIVITA_PATR										=16;
	public final static int POLIZIA_INDAGINI_SOGGETTI_PROPOSTI_TR_CA						=26;
	public final static int POLIZIA_PARERE_REVOCA_MODIFICA_MISURE_PERSONALI					=27;
	public final static int POLIZIA_VARIAZIONE_ANAGRAFICA_SOGGETTI_INTERESSATI				=28;
	public final static int POLIZIA_RICHIESTA_AGGRAVAMENTO_MISURE_PERSONALI					=29;
	public final static int POLIZIA_DECISORIO_AGGRAVAMENTO_MISURE_PERSONALI					=30;
	public final static int POLIZIA_DEFINITIVITA_AGGRAVAMENTO_MISURE_PERSONALI				=31;
	public final static int POLIZIA_RICHIESTA_MODIFICA_REVOCA_PATRIMONIALE_PERSONALE		=32;
	public final static int POLIZIA_DECISORIO_MODIFICA_REVOCA_PATRIMONIALE_PERSONALE		=33;
	public final static int POLIZIA_DEFINITIVITA_MODIFICA_REVOCA_PATRIMONIALE_PERSONALE		=34;
	public final static int POLIZIA_DEFINITIVITA_VARIAZIONE_ANAGRAFICA_SOGGETTI_INTERESSATI	=35;
	
	//servizi di ricezione da MPS
	public final static int POLIZIA_ISCRIZIONE_INDAGINI 		= 17;
	public final static int POLIZIA_ESITO_INDAGINE 				= 18;
	public final static int POLIZIA_PROPOSTA_MIS_PERS_PATR 		= 19;
	public final static int POLIZIA_STATO_SOTTOP_MIS_PERS	 	= 21;
	public final static int POLIZIA_APPL_MIS_SIC_LIB_VIG 		= 20;
	public final static int POLIZIA_VIOLAZIONE_SOTT_MIS_PERS 	= 22;
	public final static int POLIZIA_AGGRAVAMENTO_MIS_PER 		= 23; 
	public final static int POLIZIA_REVOCA_MODIFICA_MIS_PERS 	= 24;
	public final static int POLIZIA_TERMINE_CESSAZIONE 			= 25;
	
	//procedure per i flussi in ingresso di MPS
	public final static String PROCEDURE_POLIZIA_ISCRIZIONE_INDAGINI 		= "PG_POLIZIA_inserisci_fascicolo_indagine";	//17
	public final static String PROCEDURE_POLIZIA_ESITO_INDAGINE 			= "PG_POLIZIA_esito_indagini";//18
	public final static String PROCEDURE_POLIZIA_PROPOSTA_MIS_PERS_PATR 	= "PG_POLIZIA_proposta_misure_patr_pers";//19
	public final static String PROCEDURE_POLIZIA_STATO_SOTTOP_MIS_PERS	 	= "PG_POLIZIA_stato_sottopos_mis_pers";//21
	public final static String PROCEDURE_POLIZIA_APPL_MIS_SIC_LIB_VIG 		= "PG_POLIZIA_apll_mis_sic_lib_vig";//20
	public final static String PROCEDURE_POLIZIA_VIOLAZIONE_SOTT_MIS_PERS 	= "PG_POLIZIA_violazione_sott_mis_pers";//22
	public final static String PROCEDURE_POLIZIA_AGGRAVAMENTO_MIS_PER 		= "PG_POLIZIA_aggravamento_mis_pers"; //23
	public final static String PROCEDURE_POLIZIA_REVOCA_MODIFICA_MIS_PERS 	= "PG_POLIZIA_revoca_modifica_mis_pers";//24
	public final static String PROCEDURE_POLIZIA_TERMINE_CESSAZIONE 		= "PG_POLIZIA_termini_cessazione";//25
	
	public final static int POLIZIA_STATO_SERVIZIO_ERRORE						= 1;
	public final static int POLIZIA_STATO_SERVIZIO_INSERITO						= 2;
	public final static int POLIZIA_STATO_SERVIZIO_INVIATO						= 3;
	public final static int POLIZIA_STATO_SERVIZIO_RICEVUTO						= 4;
	public final static int	POLIZIA_STATO_SERVIZIO_ELABORATO_OK					= 5;
	public final static int	POLIZIA_STATO_SERVIZIO_ELABORATO_KO					= 6;
	
	//CATEGORIA ATTO MPS PER Trasmissione dei dati misura sicurezza, liberta' vigilata, commissione reato - 20
	public final static String COMUNICAZIONE_COMMISSIONE_DI_REATO 				= "377";
	public final static String COMUNICAZIONE_SOTTOPOSIZIONE_MISURE_SICUREZZA	= "378";
	public final static String COMUNICAZIONE_SOTTOPOSIZIONE_LIBERTA_VIGILATA	= "379";

	//CATEGORIA ATTO MPS PER Trasmissione dei dati relativi allo stato di sottoposizione delle misure personali - 21
	public final static String COMUNICAZIONE_TERMINE_ESECUZIONE_MISPERS			= "381";
	public final static String COMUNICAZIONE_RIPRESA_SOTTOPOSIZIONE_MISPERS		= "382";
	public final static String COMUNICAZIONE_SOSPENSIONE_SOTTOPOSIZIONE_MISPERS	= "383";
	public final static String VERBALE_INIZIO_SOTTOPOSIZIONE_MISPERS			= "384";
	
	//CATEGORIA ATTO MPS PER Trasmissione dei dati sospensione, cessazione dell obbligo di soggiorno - 25
	public final static String COMUNICAZIONE_SOSPENSIONE_OBBLIGO_SOGGIORNO		= "376";
	public final static String COMUNICAZIONE_CESSAZIONE_OBBLIGO_SOGGIORNO		= "363";
	
	//CATEGORIA ATTO MPS PER Trasmissione dei dati relativi allo stato di sottoposizione delle misure personali - 24
	public final static String PARERI_REVOCA_MODIFICA_MISURE_PERSONALI			= "375";
	
	//CATEGORIA ATTO MPS PER Trasmissione dei dati relativi alla violazione sottoposizione misure personali - 22
	public final static String VIOLAZIONE_SOTTOPOSIZIONE_MISURE_PERSONALI		= "380";
	
	//CATEGORIA ATTO MPS PER Trasmissione dei dati esito indagine- 18
	public final static String ESITO_INDAGINE									= "29";
	
	public final static String TIPO_UFFICIO_QUESTURA							= "5";
	public final static String TIPO_UFFICIO_DIA									= "22";

	public final static Long TIPO_RUOLO_AMMINISTRATORE = 2l;
	//FINE messaggi polizia

	public static final String CODICE_IBAN_TRASFORMA_BENE = "IT77R0760103200001014318867";
	
	public static final  ArrayList<Long> ATTI_INVIABILI_AD_AMMINISTRATORI = new ArrayList<Long>(Arrays.asList(300l,301l, 302l,303l,323l));

	public static final String NON_DISPONIBILE = "n.d.";
	
	public static final String UNDEFINED = "UNDEFINED";
	
	public final static class EnteFittizio{
		public static final String ABI 								= "00000";
	  	public static final String CAB 								= "00000";
	  	// In caso di modifica della DESCRIZIONE aggiornare anche la costante nel package PG_BENE
	  	public static final String DESCRIZIONE						= "ENTE FITTIZIO";
	  	public static final String NUMERO_REGISTRAZIONE				= "0";
	  	public static final String CODICE_FISCALE					= "0000000000000000";
	  	public static final String PROVINCIA						= "ZZ";
	  	public static final String INDIRIZZO						= "N.D.";
	  	public static final String ID								= "99998"; //id ente fittizio nella tabella T_AN_ALTRI_OPER
	  	
  }
	
	public final static class OperatoreEstero{
		public static final String ABI 								= "99999";
	  	public static final String CAB 								= "99999";
	  	public static final String DESCRIZIONE						= "OPERATORE ECONOMICO ESTERO";
	  	public static final String FLG_TIPO_OPER					= "3";
	  	public static final String CODICE_FISCALE					= "9999999999999999";
	  	public static final String PROVINCIA						= "ZZ";
	  	public static final String INDIRIZZO						= "N.D.";
	  	public static final String COMUNE							= "N.D.";
	  	
  }
	
	// P.Q. Mev 2020/48 Inizio
	public static final char NESSUNA_OPERAZIONE = ' ';
	public static final char AGGIORNA_DATA_FINE_INCARICO_DECRETI_PRECEDENTI = '1';
	public static final char AGGIORNA_DATA_INIZIO_INCARIO_DECRETO_ATTUALE = '2';
	public static final char AGGIORNA_DATA_INIZIO_INCARIO_NUOVO_AMMINISTRATORE = '3';
	public static final char AGGIORNA_DATA_FINE_INCARICO_DECRETI_NON_CONFISCA = '4';
	public static final int PRIMO_GRADO = 1;
	public static final int SECONDO_GRADO = 2;
	// P.Q. Mev 2020/48 Fine
	
	public static final String STRINGAVUOTA = " ";
}