package it.giustizia.smp.utility.tree;

import it.giustizia.smp.utility.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProcedimentoTree extends Tree {

	public final static String	ISCRIZIONE_PROCEDIMENTO 		= "ISCRIZIONE_PROCEDIMENTO";
	public final static String	APERTURA_ISTRUTTORIA 			= "APERTURA_ISTRUTTORIA";
	public final static String	CHIUSURA_ISTRUTTORIA 			= "CHIUSURA_ISTRUTTORIA";
	public final static String	DECRETI_DECISORI				= "DECRETI_DECISORI";
	public final static String	DECRETI_DECISORI_SECONDO_GRADO  = "DECRETI_DECISORI_SECONDO_GRADO";
	public final static String	DECRETI_INTERLOCUTORI			= "DECRETI_INTERLOCUTORI";
	public final static String	DECRETI_INTERLOCUTORI_SECONDO_GRADO	= "DECRETI_INTERLOCUTORI_SECONDO_GRADO";
	public final static String	DIBATTIMENTO_PRIMO_GRADO		= "DIBATTIMENTO_PRIMO_GRADO";
	public final static String	DIBATTIMENTO_SECONDO_GRADO		= "DIBATTIMENTO_SECONDO_GRADO";
	public final static String	POST_DIBATTIMENTO_PRIMO_GRADO	= "POST_DIBATTIMENTO_PRIMO_GRADO";
	public final static String	IMPUGNAZIONI_PRIMO_GRADO	    = "IMPUGNAZIONI_PRIMO_GRADO";
	public final static String	IMPUGNAZIONI				    = "IMPUGNAZIONI";
	public final static String	RICORSO_PER_CASSAZIONE		    = "RICORSO_PER_CASSAZIONE";
	public final static String	POST_DIBATTIMENTO_SECONDO_GRADO	= "POST_DIBATTIMENTO_SECONDO_GRADO";
	public final static String	FONTE							= "FONTE";
	public final static String  DECRETI_OPPOSIZIONI				= "DECRETI_OPPOSIZIONI";
	public final static String  ATTI_GENERICI					= "ATTI_GENERICI";
	
	public final static String	PROPOSTA						= "PROPOSTA";
	public final static String	ACCERTAMENTO					= "ACCERTAMENTO";
	public final static String	OPPOSIZIONE_RESTITUZIONE		= "OPPOSIZIONE";
	public final static String	REVOCAZIONE_CONFISCA 			= "REVOCAZIONE_CONFISCA";

	public final static String	ISTANZA_SOGG_DIFFIDATO			= "ISTANZA_SOGG_DIFFIDATO";
	public final static String	CHIUSURA_ACCERTAMENTO			= "CHIUSURA_ACCERTAMENTO";
	public final static String	UDIENZA							= "UDIENZA";
	public final static String	VERBALI_UDIENZE					= "VERBALI_UDIENZE";
	public final static String	CERTIFICATO_RITO				= "CERTIFICATO_RITO";
	public final static String	GIUDIZIO_LEGITTIMITA			= "GIUDIZIO_LEGITTIMITA";
	public final static String	GIUDIZIO_MERITO					= "GIUDIZIO_MERITO";
	public final static String	ESECUZIONE_GIUDIZIO_DEFINITIVO	= "ESECUZIONE_GIUDIZIO_DEFINITIVO";
	public final static String	ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PERS	= "ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PERS";
	public final static String	ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PATR	= "ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PATR";
	public final static String	ECCEZIONE_DI_INCOSTITUZIONALITA = "ECCEZIONE_DI_INCOSTITUZIONALITA";

	public final static long	PROCURA_REP						= 17l;
	public final static long	TRIBUNALE						= 19l;
	public final static long	CORTE_D_APPELLO					= 6l;
	public final static long	PG_CDA							= 8l;
			
	private final static long	ID_FASCICOLO_AGGRAVAMENTO		= 3L;
	private final static long	ID_FASCICOLO_ACCERTAMENTO		= 8L;
	private final static long	ID_FASCICOLO_OPPOSIZIONE		= 10L;
	private final static long	ID_FASCICOLO_OPPOSIZIONE_RESTITUZIONE = 5L;
	private final static long	ID_FASCICOLO_OPPOSIZIONE_DIVIETI_QUESTORE = 4L;
	private final static long	ID_FASCICOLO_REVOCAZIONE = 7L;
	private final static long	ID_FASCICOLO_CAMERE_CONSIGLIO = 6L;

	public static ProcedimentoTree getDefaultTree(long tipoUfficio) {

		ProcedimentoTree tree = new ProcedimentoTree();
		Node root = tree.getRoot();
		root.setInfo(new JsNodeInfo("Fascicolo prevenzione", "/procedimento/atti/elenco.htm", null));
		if (tipoUfficio == ProcedimentoTree.PROCURA_REP) {
			// albero associato all'ufficio 'Procura della Repubblica'
			Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "/procedimento/atti/elenco.htm", null));
			Node n2 = new Node(new JsNodeInfo("Apertura Istruttoria", "/procedimento/atti/elenco.htm", null));
			Node n3 = new Node(new JsNodeInfo("Conflitto di competenza", "/procedimento/atti/elenco.htm", null));
			Node n4 = new Node(new JsNodeInfo("Chiusura Istruttoria", "/procedimento/atti/elenco.htm", "folderopen.gif"));
			Node n5 = new Node(new JsNodeInfo("Accertamento", "/procedimento/atti/elenco.htm", null));
			Node n6 = new Node(new JsNodeInfo("Fonte", "/procedimento/atti/elenco.htm", null));
			Node n8 = new Node(new JsNodeInfo("Dibattimento primo grado", "/procedimento/atti/elenco.htm", null));
			Node n9 = new Node(new JsNodeInfo("Udienze", "/procedimento/atti/elenco.htm", null));
			Node n10 = new Node(new JsNodeInfo("Verbale", "/procedimento/atti/elenco.htm", null));
			Node n11 = new Node(new JsNodeInfo("Decreti interlocutori", "/procedimento/atti/elenco.htm", null));
			Node n13 = new Node(new JsNodeInfo("Post dibattimento primo grado", "/procedimento/atti/elenco.htm", null));
			Node n14 = new Node(new JsNodeInfo("Impugnazioni decreto primo grado", "/procedimento/atti/elenco.htm", null));
			Node n15 = new Node(new JsNodeInfo("Dibattimento secondo grado", "/procedimento/atti/elenco.htm", null));
			Node n16 = new Node(new JsNodeInfo("Giudizio di legittimità", "/procedimento/atti/elenco.htm", null));
			Node n17 = new Node(new JsNodeInfo("Atti Generici", "/procedimento/atti/elenco.htm", null));

			root.addChild(n1);
			tree.index.put(ProcedimentoTree.ISCRIZIONE_PROCEDIMENTO, n1);
			root.addChild(n2);
			root.addChild(n3);
			root.addChild(n4);
			n1.addChild(n5);
			tree.index.put(ProcedimentoTree.ACCERTAMENTO, n5);
			n1.addChild(n6);
			tree.index.put(ProcedimentoTree.PROPOSTA, n4);
			tree.index.put(ProcedimentoTree.FONTE, n6);
			root.addChild(n8);
			n8.addChild(n9);
			tree.index.put(ProcedimentoTree.UDIENZA, n9);
			n8.addChild(n10);
			tree.index.put(ProcedimentoTree.VERBALI_UDIENZE, n10);
			n8.addChild(n11);
			tree.index.put(ProcedimentoTree.DECRETI_INTERLOCUTORI, n11);
			root.addChild(n13);
			tree.index.put(ProcedimentoTree.POST_DIBATTIMENTO_PRIMO_GRADO, n13);
			n13.addChild(n14);
			tree.index.put(ProcedimentoTree.IMPUGNAZIONI_PRIMO_GRADO, n14);
			root.addChild(n15);
			tree.index.put(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO, n15);
			root.addChild(n16);
			tree.index.put(ProcedimentoTree.GIUDIZIO_LEGITTIMITA, n16);
			root.addChild(n17);
			tree.index.put(ProcedimentoTree.ATTI_GENERICI, n17);


		} else if (tipoUfficio == ProcedimentoTree.TRIBUNALE) {
			Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "/procedimento/atti/elenco.htm", null));
			Node n4 = new Node(new JsNodeInfo("Dibattimento primo grado", "/procedimento/atti/elenco.htm", null));
			Node n5 = new Node(new JsNodeInfo("Udienze", "/procedimento/atti/elenco.htm", null));
			Node n10 = new Node(new JsNodeInfo("Verbale", "/procedimento/atti/elenco.htm", null));
			Node n6 = new Node(new JsNodeInfo("Decreti interlocutori", "/procedimento/atti/elenco.htm", null));
			Node n7 = new Node(new JsNodeInfo("Post dibattimento primo grado", "/procedimento/atti/elenco.htm", null));
			Node n11 = new Node(new JsNodeInfo("Impugnazioni", "/procedimento/atti/elenco.htm", null));
			Node n12 = new Node(new JsNodeInfo("Dibattimento secondo grado", "/procedimento/atti/elenco.htm", null));
			Node n15 = new Node(new JsNodeInfo("Decreti interlocutori secondo grado", "/procedimento/atti/elenco.htm", null));
			Node n13 = new Node(new JsNodeInfo("Giudizio di legittimità", "/procedimento/atti/elenco.htm", null));
			Node n14 = new Node(new JsNodeInfo("Esecuzione giudizio definitivo", "/procedimento/atti/elenco.htm", null));
			Node n14_1 = new Node(new JsNodeInfo("Esecuzione misure personali", "/procedimento/atti/elenco.htm", null));
			Node n14_2 = new Node(new JsNodeInfo("Esecuzione misure patrimoniali", "/procedimento/atti/elenco.htm", null));
		
			
			Node n9 = new Node(new JsNodeInfo("Atti Generici", "/procedimento/atti/elenco.htm", null));
			root.addChild(n1);
			root.addChild(n4);
			root.addChild(n7);
			n4.addChild(n5);
			n4.addChild(n6);
			n4.addChild(n10);
			n7.addChild(n11);
			root.addChild(n12);
			n12.addChild(n15);
			root.addChild(n13);
			root.addChild(n14);
			
			n14.addChild(n14_1);
			n14.addChild(n14_2);
			
			root.addChild(n9);
			tree.index.put(ProcedimentoTree.PROPOSTA, n1);
			tree.index.put(ProcedimentoTree.UDIENZA, n5);
			tree.index.put(ProcedimentoTree.VERBALI_UDIENZE, n10);
			tree.index.put(ProcedimentoTree.DECRETI_INTERLOCUTORI, n6);
			tree.index.put(ProcedimentoTree.POST_DIBATTIMENTO_PRIMO_GRADO, n7);
			tree.index.put(ProcedimentoTree.IMPUGNAZIONI_PRIMO_GRADO, n11);
			tree.index.put(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO, n12);
			tree.index.put(ProcedimentoTree.GIUDIZIO_LEGITTIMITA, n13);
			tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO, n14);
			tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PERS, n14_1);
			tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PATR, n14_2);
			tree.index.put(ProcedimentoTree.DECRETI_INTERLOCUTORI_SECONDO_GRADO, n15); 
			tree.index.put(ProcedimentoTree.ATTI_GENERICI, n9);
		} else if (tipoUfficio == ProcedimentoTree.PG_CDA) {
			// albero associato all'ufficio Procura Generale presso CDA
			Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "/procedimento/atti/elenco.htm", null));
			Node n2 = new Node(new JsNodeInfo("Dibattimento primo grado", "/procedimento/atti/elenco.htm", null));
			Node n8 = new Node(new JsNodeInfo("Decreti interlocutori", "/procedimento/atti/elenco.htm", null));
			Node n3 = new Node(new JsNodeInfo("Post dibattimento di I grado", "/procedimento/atti/elenco.htm", null));
			Node n10 = new Node(new JsNodeInfo("Impugnazioni", "/procedimento/atti/elenco.htm", null));
			Node n4 = new Node(new JsNodeInfo("Dibattimento secondo grado", "/procedimento/atti/elenco.htm", null));
			Node n13 = new Node(new JsNodeInfo("Decreti interlocutori secondo grado", "/procedimento/atti/elenco.htm", null));
			Node n11 = new Node(new JsNodeInfo("Udienze", "/procedimento/atti/elenco.htm", null));
			Node n12 = new Node(new JsNodeInfo("Verbale", "/procedimento/atti/elenco.htm", null));
			Node n5 = new Node(new JsNodeInfo("Post dibattimento di secondo grado", "/procedimento/atti/elenco.htm", null));
			Node n6 = new Node(new JsNodeInfo("Giudizio di legittimità", "/procedimento/atti/elenco.htm", null));
			Node n7 = new Node(new JsNodeInfo("Atti Generici", "/procedimento/atti/elenco.htm", null));

			root.addChild(n1);
			root.addChild(n2);
			n2.addChild(n8);
			root.addChild(n3);
			n3.addChild(n10);
			root.addChild(n4);
			n4.addChild(n11);
			n4.addChild(n12);
			n4.addChild(n13);
			root.addChild(n5);
			root.addChild(n6);
			root.addChild(n7);

			tree.index.put(ProcedimentoTree.PROPOSTA, n1);					
			tree.index.put(ProcedimentoTree.DIBATTIMENTO_PRIMO_GRADO, n2);
			tree.index.put(ProcedimentoTree.DECRETI_INTERLOCUTORI, n8);
			tree.index.put(ProcedimentoTree.POST_DIBATTIMENTO_PRIMO_GRADO, n3);
			tree.index.put(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO, n4);
			tree.index.put(ProcedimentoTree.POST_DIBATTIMENTO_SECONDO_GRADO, n5);
			tree.index.put(ProcedimentoTree.UDIENZA, n11);
			tree.index.put(ProcedimentoTree.VERBALI_UDIENZE, n12);
			tree.index.put(ProcedimentoTree.IMPUGNAZIONI_PRIMO_GRADO, n10);
			tree.index.put(ProcedimentoTree.ATTI_GENERICI, n7);
			tree.index.put(ProcedimentoTree.DECRETI_INTERLOCUTORI_SECONDO_GRADO, n13);
			tree.index.put(ProcedimentoTree.GIUDIZIO_LEGITTIMITA, n6);
			
			
		} else if (tipoUfficio == ProcedimentoTree.CORTE_D_APPELLO) {
			Node iscrizioneNode = new Node(new JsNodeInfo("Iscrizione procedimento", "/procedimento/atti/elenco.htm", null));
			Node dibattimentoIIGradoNode = new Node(new JsNodeInfo("Dibattimento II grado", "/procedimento/atti/elenco.htm", null));
			Node certificatoRitoNode = new Node(new JsNodeInfo("Certificato di rito", "/procedimento/atti/elenco.htm", null));
			Node udienzeNode = new Node(new JsNodeInfo("Udienze", "/procedimento/atti/elenco.htm", null));
			Node decretiInterlocutoriNode = new Node(new JsNodeInfo("Decreti interlocutori", "/procedimento/atti/elenco.htm", null));
			Node verbaliNode = new Node(new JsNodeInfo("Verbali", "/procedimento/atti/elenco.htm", null));
			Node postDibattimentoIIGradoNode = new Node(new JsNodeInfo("Post dibattimento II grado", "/procedimento/atti/elenco.htm", null));
			Node giudizioLegittimitaNode = new Node(new JsNodeInfo("Giudizio di legittimità", "/procedimento/atti/elenco.htm", null));
			Node esecuzioneGiudDefNode = new Node(new JsNodeInfo("Esecuzione giudizio definitivo", "/procedimento/atti/elenco.htm", null));
			
			Node esecuzioneGiudDefNode_1 = new Node(new JsNodeInfo("Esecuzione misure personali", "/procedimento/atti/elenco.htm", null));
			Node esecuzioneGiudDefNode_2 = new Node(new JsNodeInfo("Esecuzione misure patrimoniali", "/procedimento/atti/elenco.htm", null));
		
			
			
			Node attiGenericiNode = new Node(new JsNodeInfo("Atti Generici", "/procedimento/atti/elenco.htm", null));
			Node eccezioneIncostituzNode = new Node(new JsNodeInfo("Eccezione di incostituzionalità", "/procedimento/atti/elenco.htm", null));

			root.addChild(iscrizioneNode); 
			root.addChild(dibattimentoIIGradoNode); 
			root.addChild(postDibattimentoIIGradoNode); 
			root.addChild(giudizioLegittimitaNode); 
			root.addChild(esecuzioneGiudDefNode);
			esecuzioneGiudDefNode.addChild(esecuzioneGiudDefNode_1);
			esecuzioneGiudDefNode.addChild(esecuzioneGiudDefNode_2);
			
			root.addChild(attiGenericiNode);
			root.addChild(eccezioneIncostituzNode);

			dibattimentoIIGradoNode.addChild(certificatoRitoNode);
			dibattimentoIIGradoNode.addChild(udienzeNode);
			dibattimentoIIGradoNode.addChild(decretiInterlocutoriNode);
			dibattimentoIIGradoNode.addChild(verbaliNode);
			
			tree.index.put(ProcedimentoTree.ISCRIZIONE_PROCEDIMENTO, iscrizioneNode);				
			tree.index.put(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO, dibattimentoIIGradoNode);	
			tree.index.put(ProcedimentoTree.CERTIFICATO_RITO, certificatoRitoNode);
			tree.index.put(ProcedimentoTree.UDIENZA, udienzeNode);
			tree.index.put(ProcedimentoTree.DECRETI_INTERLOCUTORI, decretiInterlocutoriNode);
			tree.index.put(ProcedimentoTree.VERBALI_UDIENZE, verbaliNode);
			tree.index.put(ProcedimentoTree.POST_DIBATTIMENTO_SECONDO_GRADO, postDibattimentoIIGradoNode);
			tree.index.put(ProcedimentoTree.GIUDIZIO_LEGITTIMITA, giudizioLegittimitaNode);
			tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO, esecuzioneGiudDefNode);
			
			tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PERS, esecuzioneGiudDefNode_1);
			tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PATR, esecuzioneGiudDefNode_2);
			
			tree.index.put(ProcedimentoTree.ATTI_GENERICI, attiGenericiNode);
			tree.index.put(ProcedimentoTree.ECCEZIONE_DI_INCOSTITUZIONALITA, eccezioneIncostituzNode);
		}		
			
		tree.complete();
		return tree;
	}

	public static ProcedimentoTree getDefaultTree(long tipoUfficio, Long idTipoFascicolo, Long idTipoProcedimento) {

 		if (idTipoFascicolo.equals(ProcedimentoTree.ID_FASCICOLO_CAMERE_CONSIGLIO)) {
			ProcedimentoTree tree = new ProcedimentoTree();
			Node root = tree.getRoot();
			root.setInfo(new JsNodeInfo("Fascicolo camere consiglio", "#", null));
			if (tipoUfficio == ProcedimentoTree.PROCURA_REP || tipoUfficio == ProcedimentoTree.PG_CDA) {
				// albero associato all'ufficio 'Procura della Repubblica'
				Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "/procedimento/atti/elenco.htm", null));
				Node n2 = new Node(new JsNodeInfo("Apertura Istruttoria", "/procedimento/atti/elenco.htm", null));
				Node n3 = new Node(new JsNodeInfo("Chiusura Istruttoria", "/procedimento/atti/elenco.htm", "folderopen.gif"));
				Node n3_1 = new Node(new JsNodeInfo("Udienze", "/procedimento/atti/elenco.htm", null));
				Node n3_2 = new Node(new JsNodeInfo("Verbale", "/procedimento/atti/elenco.htm", null));
				Node n3_3 = new Node(new JsNodeInfo("Appello decreto I Grado", "/procedimento/atti/elenco.htm", "folderopen.gif"));
				Node n4 = new Node(new JsNodeInfo("Dibattimento secondo grado", "/procedimento/atti/elenco.htm", null));
				Node n5 = new Node(new JsNodeInfo("Giudizio di legittimità", "/procedimento/atti/elenco.htm", null));
				Node n6 = new Node(new JsNodeInfo("Atti Generici", "/procedimento/atti/elenco.htm", null));

				root.addChild(n1);
				root.addChild(n2);
				root.addChild(n3);
				n3.addChild(n3_1);
				n3.addChild(n3_2);
				n3.addChild(n3_3);
				root.addChild(n4);
				root.addChild(n5);
				root.addChild(n6);
				
				tree.index.put(ProcedimentoTree.ISCRIZIONE_PROCEDIMENTO, n1);
				tree.index.put(ProcedimentoTree.APERTURA_ISTRUTTORIA, n2);
				tree.index.put(ProcedimentoTree.CHIUSURA_ISTRUTTORIA, n3);
				tree.index.put(ProcedimentoTree.UDIENZA, n3_1);
				tree.index.put(ProcedimentoTree.VERBALI_UDIENZE, n3_2);
				tree.index.put(ProcedimentoTree.IMPUGNAZIONI_PRIMO_GRADO, n3_3);
				tree.index.put(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO, n4);
				tree.index.put(ProcedimentoTree.GIUDIZIO_LEGITTIMITA, n5);
				tree.index.put(ProcedimentoTree.ATTI_GENERICI, n6);
				
			} else if (tipoUfficio == ProcedimentoTree.TRIBUNALE) {
				// albero associato all'ufficio 'Tribunale'
				Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "/procedimento/atti/elenco.htm", null));
				Node n2 = new Node(new JsNodeInfo("Dibattimento primo grado", "/procedimento/atti/elenco.htm", null));
				Node n2_1 = new Node(new JsNodeInfo("Udienze", "/procedimento/atti/elenco.htm", null));
				Node n2_2 = new Node(new JsNodeInfo("Verbale", "/procedimento/atti/elenco.htm", null));
				Node n3 = new Node(new JsNodeInfo("Post dibattimento", "/procedimento/atti/elenco.htm", "folderopen.gif"));
				Node n3_1 = new Node(new JsNodeInfo("Appello decreto I Grado", "/procedimento/atti/elenco.htm", "folderopen.gif"));
				Node n4 = new Node(new JsNodeInfo("Dibattimento secondo grado", "/procedimento/atti/elenco.htm", null));
				Node n5 = new Node(new JsNodeInfo("Giudizio di legittimità", "/procedimento/atti/elenco.htm", null));
				Node n6 = new Node(new JsNodeInfo("Esecuzione giudizio definitivo", "/procedimento/atti/elenco.htm", "folderopen.gif"));
				Node n6_1 = new Node(new JsNodeInfo("Esecuzione misure personali", "/procedimento/atti/elenco.htm", null));
				Node n6_2 = new Node(new JsNodeInfo("Esecuzione misure patrimoniali", "/procedimento/atti/elenco.htm", null));
				
				Node n7 = new Node(new JsNodeInfo("Atti Generici", "/procedimento/atti/elenco.htm", null));

				root.addChild(n1);
				root.addChild(n2);
				n2.addChild(n2_1);
				n2.addChild(n2_2);
				root.addChild(n3);
				n3.addChild(n3_1);
				if(idTipoProcedimento!=null && idTipoProcedimento.intValue() != Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM) {
					root.addChild(n4);
				}
				root.addChild(n5);
				if(idTipoProcedimento!=null && idTipoProcedimento.intValue() != Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM) {
					root.addChild(n6);
					if(idTipoProcedimento!=null && idTipoProcedimento.intValue() != Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM) {
						n6.addChild(n6_1);
					}
					n6.addChild(n6_2);
				}			
				
				root.addChild(n7);
				
				tree.index.put(ProcedimentoTree.ISCRIZIONE_PROCEDIMENTO, n1);
				tree.index.put(ProcedimentoTree.DIBATTIMENTO_PRIMO_GRADO, n2);
				tree.index.put(ProcedimentoTree.UDIENZA, n2_1);
				tree.index.put(ProcedimentoTree.VERBALI_UDIENZE, n2_2);
				tree.index.put(ProcedimentoTree.POST_DIBATTIMENTO_PRIMO_GRADO, n3);
				tree.index.put(ProcedimentoTree.IMPUGNAZIONI_PRIMO_GRADO, n3_1);
				tree.index.put(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO, n4);
				tree.index.put(ProcedimentoTree.GIUDIZIO_LEGITTIMITA, n5);
				if(idTipoProcedimento!=null && idTipoProcedimento.intValue() != Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM) {
					tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO, n6);
					
					if(idTipoProcedimento!=null && idTipoProcedimento.intValue() != Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM) {
						n6.addChild(n6_1);
						tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PERS, n6_1);
					}
					tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PATR, n6_2);
				}
				tree.index.put(ProcedimentoTree.ATTI_GENERICI, n7);
			} else if (tipoUfficio ==  ProcedimentoTree.CORTE_D_APPELLO) {
				// albero associato all'ufficio 'Corte di Appello'
				Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "/procedimento/atti/elenco.htm", null));
				Node n2 = new Node(new JsNodeInfo("Dibattimento secondo grado", "/procedimento/atti/elenco.htm", null));
				Node n2_1 = new Node(new JsNodeInfo("Udienze", "/procedimento/atti/elenco.htm", null));
				Node n2_2 = new Node(new JsNodeInfo("Verbale", "/procedimento/atti/elenco.htm", null));
				Node n3 = new Node(new JsNodeInfo("Post dibattimento secondo grado", "/procedimento/atti/elenco.htm", "folderopen.gif"));
				Node n4 = new Node(new JsNodeInfo("Giudizio di legittimità", "/procedimento/atti/elenco.htm", null));
				Node n5 = new Node(new JsNodeInfo("Esecuzione giudizio definitivo", "/procedimento/atti/elenco.htm", null));
				Node n5_1 = new Node(new JsNodeInfo("Esecuzione misure personali", "/procedimento/atti/elenco.htm", null));
				Node n5_2 = new Node(new JsNodeInfo("Esecuzione misure patrimoniali", "/procedimento/atti/elenco.htm", null));
				
				Node n6 = new Node(new JsNodeInfo("Atti Generici", "/procedimento/atti/elenco.htm", null));

				root.addChild(n1);
				if(idTipoProcedimento!=null && idTipoProcedimento.intValue() != Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM) {
					root.addChild(n2);
					n2.addChild(n2_1);
					n2.addChild(n2_2);
					root.addChild(n3);
				}
				root.addChild(n4);
				if(idTipoProcedimento!=null && idTipoProcedimento.intValue() != Constants.TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM) {
					root.addChild(n5);
					n5.addChild(n5_1);
					n5.addChild(n5_2);
				}
				root.addChild(n6);
				
				tree.index.put(ProcedimentoTree.ISCRIZIONE_PROCEDIMENTO, n1);
				tree.index.put(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO, n2);
				tree.index.put(ProcedimentoTree.UDIENZA, n2_1);
				tree.index.put(ProcedimentoTree.VERBALI_UDIENZE, n2_2);
				tree.index.put(ProcedimentoTree.POST_DIBATTIMENTO_SECONDO_GRADO, n3);
				tree.index.put(ProcedimentoTree.GIUDIZIO_LEGITTIMITA, n4);
				tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO, n5);
				tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PERS, n5_1);
				tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PATR, n5_2);
				tree.index.put(ProcedimentoTree.ATTI_GENERICI, n6);
				

			}
			tree.complete();
			return tree;
		}
 		
 		if (idTipoFascicolo.equals(ProcedimentoTree.ID_FASCICOLO_AGGRAVAMENTO)) {
			ProcedimentoTree tree = new ProcedimentoTree();
			Node root = tree.getRoot();
			root.setInfo(new JsNodeInfo("Fascicolo aggravamento", "#", null));
			if (tipoUfficio == ProcedimentoTree.PROCURA_REP) {
				// albero associato all'ufficio 'Procura della Repubblica'
				Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "/procedimento/atti/elenco.htm", null));
				Node n2 = new Node(new JsNodeInfo("Apertura Istruttoria", "/procedimento/atti/elenco.htm", null));
				Node n3 = new Node(new JsNodeInfo("Chiusura Istruttoria", "/procedimento/atti/elenco.htm", "folderopen.gif"));
				Node n3_1 = new Node(new JsNodeInfo("Udienze", "/procedimento/atti/elenco.htm", null));
				Node n3_2 = new Node(new JsNodeInfo("Verbale", "/procedimento/atti/elenco.htm", null));
				Node n3_3 = new Node(new JsNodeInfo("Appello decreto I Grado", "/procedimento/atti/elenco.htm", "folderopen.gif"));
				Node n4 = new Node(new JsNodeInfo("Dibattimento secondo grado", "/procedimento/atti/elenco.htm", null));
				Node n5 = new Node(new JsNodeInfo("Giudizio di legittimità", "/procedimento/atti/elenco.htm", null));
				Node n6 = new Node(new JsNodeInfo("Atti Generici", "/procedimento/atti/elenco.htm", null));

				root.addChild(n1);
				root.addChild(n2);
				root.addChild(n3);
				n3.addChild(n3_1);
				n3.addChild(n3_2);
				n3.addChild(n3_3);
				root.addChild(n4);
				root.addChild(n5);
				root.addChild(n6);
				
				tree.index.put(ProcedimentoTree.ISCRIZIONE_PROCEDIMENTO, n1);
				tree.index.put(ProcedimentoTree.APERTURA_ISTRUTTORIA, n2);
				tree.index.put(ProcedimentoTree.CHIUSURA_ISTRUTTORIA, n3);
				tree.index.put(ProcedimentoTree.UDIENZA, n3_1);
				tree.index.put(ProcedimentoTree.VERBALI_UDIENZE, n3_2);
				tree.index.put(ProcedimentoTree.IMPUGNAZIONI_PRIMO_GRADO, n3_3);
				tree.index.put(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO, n4);
				tree.index.put(ProcedimentoTree.GIUDIZIO_LEGITTIMITA, n5);
				tree.index.put(ProcedimentoTree.ATTI_GENERICI, n6);
				
			} else if (tipoUfficio == ProcedimentoTree.TRIBUNALE) {
				// albero associato all'ufficio 'Tribunale'
				Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "/procedimento/atti/elenco.htm", null));
				Node n2 = new Node(new JsNodeInfo("Dibattimento primo grado", "/procedimento/atti/elenco.htm", null));
				Node n2_1 = new Node(new JsNodeInfo("Udienze", "/procedimento/atti/elenco.htm", null));
				Node n2_2 = new Node(new JsNodeInfo("Verbale", "/procedimento/atti/elenco.htm", null));
				Node n3 = new Node(new JsNodeInfo("Post dibattimento", "/procedimento/atti/elenco.htm", "folderopen.gif"));
				Node n3_1 = new Node(new JsNodeInfo("Appello decreto I Grado", "/procedimento/atti/elenco.htm", "folderopen.gif"));
				Node n4 = new Node(new JsNodeInfo("Dibattimento secondo grado", "/procedimento/atti/elenco.htm", null));
				Node n5 = new Node(new JsNodeInfo("Giudizio di legittimità", "/procedimento/atti/elenco.htm", null));
				Node n6 = new Node(new JsNodeInfo("Esecuzione giudizio definitivo", "/procedimento/atti/elenco.htm", "folderopen.gif"));
				Node n6_1 = new Node(new JsNodeInfo("Esecuzione misure personali", "/procedimento/atti/elenco.htm", null));
				Node n6_2 = new Node(new JsNodeInfo("Esecuzione misure patrimoniali", "/procedimento/atti/elenco.htm", null));
				
				Node n7 = new Node(new JsNodeInfo("Atti Generici", "/procedimento/atti/elenco.htm", null));

				root.addChild(n1);
				root.addChild(n2);
				n2.addChild(n2_1);
				n2.addChild(n2_2);
				root.addChild(n3);
				n3.addChild(n3_1);
				root.addChild(n4);
				root.addChild(n5);
				root.addChild(n6);
				n6.addChild(n6_1);
				n6.addChild(n6_2);
			
				
				root.addChild(n7);
				
				tree.index.put(ProcedimentoTree.ISCRIZIONE_PROCEDIMENTO, n1);
				tree.index.put(ProcedimentoTree.DIBATTIMENTO_PRIMO_GRADO, n2);
				tree.index.put(ProcedimentoTree.UDIENZA, n2_1);
				tree.index.put(ProcedimentoTree.VERBALI_UDIENZE, n2_2);
				tree.index.put(ProcedimentoTree.POST_DIBATTIMENTO_PRIMO_GRADO, n3);
				tree.index.put(ProcedimentoTree.IMPUGNAZIONI_PRIMO_GRADO, n3_1);
				tree.index.put(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO, n4);
				tree.index.put(ProcedimentoTree.GIUDIZIO_LEGITTIMITA, n5);
				tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO, n6);
				tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PERS, n6_1);
				tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PATR, n6_2);				
				
				
				tree.index.put(ProcedimentoTree.ATTI_GENERICI, n7);
			} else if (tipoUfficio ==  ProcedimentoTree.CORTE_D_APPELLO) {
				// albero associato all'ufficio 'Tribunale'
				Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "/procedimento/atti/elenco.htm", null));
				Node n1_1 = new Node(new JsNodeInfo("Iscrizione procedimento", "/procedimento/atti/elenco.htm", null));
				Node n2 = new Node(new JsNodeInfo("Dibattimento secondo grado", "/procedimento/atti/elenco.htm", null));
				Node n2_1 = new Node(new JsNodeInfo("Udienze", "/procedimento/atti/elenco.htm", null));
				Node n2_2 = new Node(new JsNodeInfo("Verbale", "/procedimento/atti/elenco.htm", null));
				Node n3 = new Node(new JsNodeInfo("Post dibattimento secondo grado", "/procedimento/atti/elenco.htm", "folderopen.gif"));
				Node n4 = new Node(new JsNodeInfo("Giudizio di legittimità", "/procedimento/atti/elenco.htm", null));
				Node n5 = new Node(new JsNodeInfo("Esecuzione giudizio definitivo", "/procedimento/atti/elenco.htm", null));
				Node n5_1 = new Node(new JsNodeInfo("Esecuzione misure personali", "/procedimento/atti/elenco.htm", null));
				Node n5_2 = new Node(new JsNodeInfo("Esecuzione misure patrimoniali", "/procedimento/atti/elenco.htm", null));
				
				Node n6 = new Node(new JsNodeInfo("Atti Generici", "/procedimento/atti/elenco.htm", null));

				root.addChild(n1);
				root.addChild(n2);
				n2.addChild(n2_1);
				n2.addChild(n2_2);
				root.addChild(n3);
				root.addChild(n4);
				root.addChild(n5);
				n5.addChild(n5_1);
				n5.addChild(n5_2);
				root.addChild(n6);
				
				tree.index.put(ProcedimentoTree.ISCRIZIONE_PROCEDIMENTO, n1);
				tree.index.put(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO, n2);
				tree.index.put(ProcedimentoTree.UDIENZA, n2_1);
				tree.index.put(ProcedimentoTree.VERBALI_UDIENZE, n2_2);
				tree.index.put(ProcedimentoTree.POST_DIBATTIMENTO_SECONDO_GRADO, n3);
				tree.index.put(ProcedimentoTree.GIUDIZIO_LEGITTIMITA, n4);
				tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO, n5);
				tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PERS, n5_1);
				tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO_MISURE_PATR, n5_2);				
				tree.index.put(ProcedimentoTree.ATTI_GENERICI, n6);
			}else if (tipoUfficio ==  ProcedimentoTree.PG_CDA) {
				// albero associato all'ufficio 'Procura Generale'
				Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "/procedimento/atti/elenco.htm", null));
				Node n1_1 = new Node(new JsNodeInfo("Impugnazioni", "/procedimento/atti/elenco.htm", null));
				Node n2 = new Node(new JsNodeInfo("Dibattimento secondo grado", "/procedimento/atti/elenco.htm", null));
				Node n2_1 = new Node(new JsNodeInfo("Udienze", "/procedimento/atti/elenco.htm", null));
				Node n2_2 = new Node(new JsNodeInfo("Verbale", "/procedimento/atti/elenco.htm", null));
				Node n3 = new Node(new JsNodeInfo("Post dibattimento secondo grado", "/procedimento/atti/elenco.htm", "folderopen.gif"));
				Node n3_1 = new Node(new JsNodeInfo("Ricorso per Cassazione", "/procedimento/atti/elenco.htm", null));
				Node n4 = new Node(new JsNodeInfo("Giudizio di legittimità", "/procedimento/atti/elenco.htm", null));
				Node n5 = new Node(new JsNodeInfo("Atti Generici", "/procedimento/atti/elenco.htm", null));

				root.addChild(n1);
				n1.addChild(n1_1);
				root.addChild(n2);
				n2.addChild(n2_1);
				n2.addChild(n2_2);
				root.addChild(n3);
				n3.addChild(n3_1);
				root.addChild(n4);
				root.addChild(n5);
				
				tree.index.put(ProcedimentoTree.ISCRIZIONE_PROCEDIMENTO, n1);
				tree.index.put(ProcedimentoTree.IMPUGNAZIONI, n1_1);
				tree.index.put(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO, n2);
				tree.index.put(ProcedimentoTree.UDIENZA, n2_1);
				tree.index.put(ProcedimentoTree.VERBALI_UDIENZE, n2_2);
				tree.index.put(ProcedimentoTree.POST_DIBATTIMENTO_SECONDO_GRADO, n3);
				tree.index.put(ProcedimentoTree.RICORSO_PER_CASSAZIONE, n3_1);
				tree.index.put(ProcedimentoTree.GIUDIZIO_LEGITTIMITA, n4);
				tree.index.put(ProcedimentoTree.ATTI_GENERICI, n5);
			}
			tree.complete();
			return tree;
		}
		if (idTipoFascicolo.equals(ProcedimentoTree.ID_FASCICOLO_ACCERTAMENTO)) {
			ProcedimentoTree tree = new ProcedimentoTree();
			Node root = tree.getRoot();
			root.setInfo(new JsNodeInfo("Fascicolo accertamento", "#", null));
			if (tipoUfficio == ProcedimentoTree.PROCURA_REP) {
				// albero associato all'ufficio 'Procura della Repubblica'
				Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "#", "folderopen.gif"));
				root.addChild(n1);
				tree.index.put(ProcedimentoTree.ACCERTAMENTO, n1);
				Node n2 = new Node(new JsNodeInfo("Chiusura accertamento", "#", "folderopen.gif"));
				Node n3 = new Node(new JsNodeInfo("Archiviazione", "#", "folderopen.gif"));
				root.addChild(n2);
				n2.addChild(n3);
				tree.index.put(ProcedimentoTree.CHIUSURA_ACCERTAMENTO, n2);
			} else if (tipoUfficio == ProcedimentoTree.TRIBUNALE) {
				Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "#", "folderopen.gif"));
				root.addChild(n1);
				tree.index.put(ProcedimentoTree.ACCERTAMENTO, n1);
				Node n2 = new Node(new JsNodeInfo("Chiusura accertamento", "#", "folderopen.gif"));
				root.addChild(n2);
				tree.index.put(ProcedimentoTree.CHIUSURA_ACCERTAMENTO, n2);
			} else if (tipoUfficio == ProcedimentoTree.CORTE_D_APPELLO) {
				Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "#", "folderopen.gif"));
				root.addChild(n1);
				tree.index.put(ProcedimentoTree.ACCERTAMENTO, n1);
				Node n2 = new Node(new JsNodeInfo("Chiusura accertamento", "#", "folderopen.gif"));
				root.addChild(n2);
				tree.index.put(ProcedimentoTree.CHIUSURA_ACCERTAMENTO, n2);
			}
			
		}
		//simone
		if (idTipoFascicolo.equals(ProcedimentoTree.ID_FASCICOLO_OPPOSIZIONE_RESTITUZIONE)) {
			ProcedimentoTree tree = new ProcedimentoTree();
			Node root = tree.getRoot();
			root.setInfo(new JsNodeInfo("Fascicolo opposizione restituzione", "#", null));
			if (tipoUfficio == ProcedimentoTree.PROCURA_REP) {
				// albero associato all'ufficio 'Procura della Repubblica'
				Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "#", "folderopen.gif"));
				root.addChild(n1);
				tree.index.put(ProcedimentoTree.ACCERTAMENTO, n1);
				Node n2 = new Node(new JsNodeInfo("Chiusura accertamento", "#", "folderopen.gif"));
				Node n3 = new Node(new JsNodeInfo("Archiviazione", "#", "folderopen.gif"));
				root.addChild(n2);
				n2.addChild(n3);
				tree.index.put(ProcedimentoTree.CHIUSURA_ACCERTAMENTO, n2);
			} else if (tipoUfficio == ProcedimentoTree.TRIBUNALE) {
				Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "#", "folderopen.gif"));
				root.addChild(n1);
				tree.index.put(ProcedimentoTree.ACCERTAMENTO, n1);
				Node n2 = new Node(new JsNodeInfo("Chiusura accertamento", "#", "folderopen.gif"));
				root.addChild(n2);
				tree.index.put(ProcedimentoTree.CHIUSURA_ACCERTAMENTO, n2);
			} else if (tipoUfficio == ProcedimentoTree.CORTE_D_APPELLO) {
				Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "#", "folderopen.gif"));
				root.addChild(n1);
				tree.index.put(ProcedimentoTree.OPPOSIZIONE_RESTITUZIONE, n1);
				
				Node n2 = new Node(new JsNodeInfo("Dibattimento", "#", "folderopen.gif"));
				tree.index.put(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO, n2);
				root.addChild(n2);
				
				Node n3 = new Node(new JsNodeInfo("Udienze", "#", "folderopen.gif"));
				n2.addChild(n3);
				tree.index.put(ProcedimentoTree.UDIENZA, n3);
			}else if (tipoUfficio == ProcedimentoTree.PG_CDA) {
				Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "#", "folderopen.gif"));
				root.addChild(n1);
				tree.index.put(ProcedimentoTree.OPPOSIZIONE_RESTITUZIONE, n1);
				
				Node n2 = new Node(new JsNodeInfo("Dibattimento", "#", "folderopen.gif"));
				tree.index.put(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO, n2);
				root.addChild(n2);
				
				Node n3 = new Node(new JsNodeInfo("Udienze", "#", "folderopen.gif"));
				n2.addChild(n3);
				tree.index.put(ProcedimentoTree.UDIENZA, n3);
			}
			tree.complete();
			
			return tree;
			
			
		}
		
		if (idTipoFascicolo.equals(ProcedimentoTree.ID_FASCICOLO_OPPOSIZIONE_DIVIETI_QUESTORE)) {
			ProcedimentoTree tree = new ProcedimentoTree();
			Node root = tree.getRoot();
			root.setInfo(new JsNodeInfo("Fascicolo opposizione divieti del Questore", "#", null));
			Node n1 = new Node(new JsNodeInfo("Istanza soggetto diffidato", "#", "folderopen.gif"));
			Node n2 = new Node(new JsNodeInfo("Dibattimento primo grado", "#", "folderopen.gif"));
			Node n2_1 = new Node(new JsNodeInfo("Udienze", "#", "folderopen.gif"));
			Node n2_2 = new Node(new JsNodeInfo("Verbali", "#", "folderopen.gif"));
			Node n3 = new Node(new JsNodeInfo("Post dibattimento di primo grado", "#", "folderopen.gif"));
			Node n3_1 = new Node(new JsNodeInfo("Impugnazioni", "#", "folderopen.gif"));
			Node n4 = new Node(new JsNodeInfo("Giudizio di legittimità", "#", "folderopen.gif"));
			Node n4_1 = new Node(new JsNodeInfo("Esecuzione giudizio definitivo", "/procedimento/atti/elenco.htm", "folderopen.gif"));
			Node n5 = new Node(new JsNodeInfo("Atti generici", "#", "folderopen.gif"));
			
			root.addChild(n1);
			root.addChild(n2);
			n2.addChild(n2_1);
			n2.addChild(n2_2);
			root.addChild(n3);
			n3.addChild(n3_1);
			root.addChild(n4);
			root.addChild(n4_1);
			root.addChild(n5);
			tree.index.put(ProcedimentoTree.ISTANZA_SOGG_DIFFIDATO, n1);
			tree.index.put(ProcedimentoTree.DIBATTIMENTO_PRIMO_GRADO, n2);
			tree.index.put(ProcedimentoTree.UDIENZA, n2_1);
			tree.index.put(ProcedimentoTree.VERBALI_UDIENZE, n2_2);
			tree.index.put(ProcedimentoTree.POST_DIBATTIMENTO_PRIMO_GRADO, n3);
			tree.index.put(ProcedimentoTree.IMPUGNAZIONI_PRIMO_GRADO, n3_1);
			tree.index.put(ProcedimentoTree.GIUDIZIO_LEGITTIMITA, n4);
			tree.index.put(ProcedimentoTree.ESECUZIONE_GIUDIZIO_DEFINITIVO, n4_1);
			tree.index.put(ProcedimentoTree.ATTI_GENERICI, n5);

			tree.complete();
			return tree;
		}
	
		
		//simone
		if (idTipoFascicolo.equals(ProcedimentoTree.ID_FASCICOLO_OPPOSIZIONE)) {
			ProcedimentoTree tree = new ProcedimentoTree();
			Node root = tree.getRoot();
			root.setInfo(new JsNodeInfo("Fascicolo Opposizione ai divieti del Questore", "#", null));
			if (tipoUfficio == ProcedimentoTree.TRIBUNALE || tipoUfficio == ProcedimentoTree.PROCURA_REP) {
				
				
				Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "#", "folderopen.gif"));
				Node n2 = new Node(new JsNodeInfo("Dibattimento primo grado", "#", "folderopen.gif"));
				Node n3 = new Node(new JsNodeInfo("Post dibattimento primo grado", "#", "folderopen.gif"));
				Node n4 = new Node(new JsNodeInfo("Giudizio di legittimità", "#", "folderopen.gif"));
				Node n5 = new Node(new JsNodeInfo("Esecuzione giudizio definitivo", "#", "folderopen.gif"));
				Node n6 = new Node(new JsNodeInfo("Atti generici", "#", "folderopen.gif"));
				Node n7 = new Node(new JsNodeInfo("Certificati di rito", "#", "folderopen.gif"));
				Node n8 = new Node(new JsNodeInfo("Udienze", "#", "folderopen.gif"));
				Node n9 = new Node(new JsNodeInfo("Verbali", "#", "folderopen.gif"));
				Node n10 = new Node(new JsNodeInfo("Impugnazioni", "#", "folderopen.gif"));
				Node n11 = new Node(new JsNodeInfo("Sentenza/ordinanza della Cassazione", "#", "folderopen.gif"));
				
				root.addChild(n1);
				root.addChild(n2);
				root.addChild(n3);
				root.addChild(n4);
				root.addChild(n5);
				root.addChild(n6);
				
				n2.addChild(n7);
				n2.addChild(n8);
				n2.addChild(n9);
				n3.addChild(n10);
				n4.addChild(n11);
				
				tree.index.put(ProcedimentoTree.ISTANZA_SOGG_DIFFIDATO, n1);
				tree.index.put(ProcedimentoTree.POST_DIBATTIMENTO_PRIMO_GRADO, n3);
				tree.index.put(ProcedimentoTree.UDIENZA, n8);
				tree.index.put(ProcedimentoTree.VERBALI_UDIENZE, n9);
				
			} else if (tipoUfficio == ProcedimentoTree.CORTE_D_APPELLO) {
				Node n1 = new Node(new JsNodeInfo("Iscrizione procedimento", "#", "folderopen.gif"));
				root.addChild(n1);
				tree.index.put(ProcedimentoTree.ACCERTAMENTO, n1);
				Node n2 = new Node(new JsNodeInfo("Chiusura accertamento", "#", "folderopen.gif"));
				root.addChild(n2);
				tree.index.put(ProcedimentoTree.CHIUSURA_ACCERTAMENTO, n2);
			}
		tree.complete();
		return tree;
	} 		
	if (idTipoFascicolo.equals(ProcedimentoTree.ID_FASCICOLO_REVOCAZIONE)) {
		ProcedimentoTree tree = new ProcedimentoTree();
		Node root = tree.getRoot();
		root.setInfo(new JsNodeInfo("Fascicolo revocazione confisca", "#", null));

		Node iscrizioneProcNode = new Node(new JsNodeInfo("Iscrizione procedimento", "#", "folderopen.gif"));
		root.addChild(iscrizioneProcNode);
		tree.index.put(ProcedimentoTree.REVOCAZIONE_CONFISCA, iscrizioneProcNode);
		
		Node dibattimentoIIGradoNode = new Node(new JsNodeInfo("Dibattimento di secondo grado", "#", "folderopen.gif"));
		root.addChild(dibattimentoIIGradoNode);
		tree.index.put(ProcedimentoTree.DIBATTIMENTO_SECONDO_GRADO, dibattimentoIIGradoNode);
		
		Node udienzeNode = new Node(new JsNodeInfo("Udienze", "#", "folderopen.gif"));
		dibattimentoIIGradoNode.addChild(udienzeNode);
		tree.index.put(ProcedimentoTree.UDIENZA, udienzeNode);
		
		Node verbaliNode = new Node(new JsNodeInfo("Verbali", "#", "folderopen.gif"));
		dibattimentoIIGradoNode.addChild(verbaliNode);
		tree.index.put(ProcedimentoTree.VERBALI_UDIENZE, verbaliNode);		
		
		Node postDibattimentoIIGrado = new Node(new JsNodeInfo("Post dibattimento di II grado", "#", "folderopen.gif"));
		root.addChild(postDibattimentoIIGrado);
		tree.index.put(ProcedimentoTree.POST_DIBATTIMENTO_SECONDO_GRADO, postDibattimentoIIGrado);

		Node giudizioLegittimita = new Node(new JsNodeInfo("Giudizio di legittimità", "#", "folderopen.gif"));
		root.addChild(giudizioLegittimita);

		Node attiGenerici = new Node(new JsNodeInfo("Atti generici", "#", "folderopen.gif"));
		root.addChild(attiGenerici);

		tree.complete();
		return tree;
	}
		return getDefaultTree(tipoUfficio);
	}

	// ritorna una lista di TreeNode
	public List<TreeNode> toTreeNode() {

		List<Node> nodes = visitaPerLivelli(getRoot());
		List<TreeNode> lReturn = new ArrayList<TreeNode>();
		Iterator<Node> iter = nodes.iterator();
		while (iter.hasNext()) {
			Node current = iter.next();
			TreeNode t = new TreeNode(current.getId(), current.getFather() == null ? 0 : current.getFather().getId(), ((JsNodeInfo) current.getInfo()).getLabel(), ((JsNodeInfo) current.getInfo()).getUrl(), ((JsNodeInfo) current.getInfo()).getLeafIcon());
			t.setAttachment(((JsNodeInfo) current.getInfo()).getAttachment());
			t.setDeletable(((JsNodeInfo) current.getInfo()).getDeletable());
			t.setIdAtto(((JsNodeInfo) current.getInfo()).getIdAtto());
			//System.out.println("ATTO " + t.getNodeId() + " id Atto:" + ((JsNodeInfo) current.getInfo()).getIdAtto() + " url:" + ((JsNodeInfo) current.getInfo()).getUrl() );
			t.setNode(current);
			lReturn.add(t);
		}
		return lReturn;
	}
	
	
	
	
}
