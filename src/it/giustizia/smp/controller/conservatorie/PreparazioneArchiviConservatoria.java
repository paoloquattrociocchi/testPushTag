package it.giustizia.smp.controller.conservatorie;

import it.giustizia.smp.controller.BreadcrumbsController;
import it.giustizia.smp.dto.pg_conservatorie.BeneAssociatoDescDto;
import it.giustizia.smp.dto.pg_conservatorie.SoggettoInteressatoDescDto;
import it.giustizia.smp.dto.pg_conservatorie.TipoDomicilioDto;
import it.giustizia.smp.form.conservatorie.NotaDettaglioForm;
import it.giustizia.smp.service.ConservatorieService;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PreparazioneArchiviConservatoria extends BreadcrumbsController
{
    class Quota
    {
        String descInteresse;
        Float numeratore;
        Long denominatore;
        public Quota( String descInteresse, Float numeratore, Long denominatore )
        {
            this.descInteresse = descInteresse;
            this.numeratore = numeratore;
            this.denominatore = denominatore;
        }
    }

    @Autowired
    private ConservatorieService conservatorieService;

    @RequestMapping( value = "/conservatorie/PreparazioneArchiviConservatoria.htm", method = RequestMethod.GET )
    public String generaXml( NotaDettaglioForm dettaglio, List<SoggettoInteressatoDescDto> listaSoggettiUnitaNeg, Integer idUfficioUtente,
        Long idNota )
    {
        try
        {
            String xmlString = "";
            int progressivoSoggetto = 0;
            // Elemento radice
            Element root = new Element( "Telematico" );
            // Documento
            Document document = new Document( root );

            Element datiTelematico = new Element( "DatiTelematico" );

            Element chiaveFile = new Element( "ChiaveFile" );

            Element codiceFiscalePU = new Element( "CodiceFiscalePU" );
            codiceFiscalePU.setText( dettaglio.getCodiceFiscale() );
            chiaveFile.addContent( codiceFiscalePU );

            Element pubblicoUfficiale = new Element( "PubblicoUfficiale" );
            if( dettaglio.getSede() != null )
                pubblicoUfficiale.setAttribute( "Comune", dettaglio.getSede() );
            if( dettaglio.getProvincia() != null )
                pubblicoUfficiale.setAttribute( "Provincia", dettaglio.getProvincia() );
            if( dettaglio.getPubblicoUfficiale() != null )
                pubblicoUfficiale.setAttribute( "DenominazionePU", dettaglio.getPubblicoUfficiale() );
            if( dettaglio.getCategoria() != null )
                pubblicoUfficiale.setAttribute( "TipoPU", dettaglio.getCategoria().toString() );
            chiaveFile.addContent( pubblicoUfficiale );

            datiTelematico.addContent( chiaveFile );

            Element adempimento = new Element( "Adempimento" );

            Element chiaveAdempimento = new Element( "ChiaveAdempimento" );

            if( dettaglio.getNumeroRepertorio1() != null )
            {
                Element primoNumeroRepertorio = new Element( "PrimoNumeroRepertorio" );
                primoNumeroRepertorio.setText( dettaglio.getNumeroRepertorio1().toString() );
                chiaveAdempimento.addContent( primoNumeroRepertorio );
            }

            if( dettaglio.getRepertorioAnno() != null )
            {
                Element secondoNumeroRepertorio = new Element( "SecondoNumeroRepertorio" );
                secondoNumeroRepertorio.setText( dettaglio.getRepertorioAnno().toString() );
                chiaveAdempimento.addContent( secondoNumeroRepertorio );
            }

            // Elemento già presente in ChiaveFile
            Element codiceFiscalePUChiaveAdempimento = new Element( "CodiceFiscalePU" );
            codiceFiscalePUChiaveAdempimento.setText( dettaglio.getCodiceFiscale() );
            chiaveAdempimento.addContent( codiceFiscalePUChiaveAdempimento );

            adempimento.addContent( chiaveAdempimento );

            Element datiTitolo = new Element( "DatiTitolo" );
            // Elaborazione da valorizzare con 2 solo Uffici del Territorio
            datiTitolo.setAttribute( "Elaborazione", "2" );
            // TipoBollo da valorizzare con 1 atto esente imposta di bollo
            datiTitolo.setAttribute( "TipoBollo", "1" );

            Element titolo = new Element( "Titolo" );
            // AttoEsenteRegistrazione da valorizzare con 1 atto non soggetto a registrazione
            titolo.setAttribute( "AttoEsenteRegistrazione", "1" );

            if( dettaglio.getTitoloDescrizione() != null )
                titolo.setAttribute( "Descrizione", dettaglio.getTitoloDescrizione() );

            if( dettaglio.getData() != null )
                titolo.setAttribute( "DataAtto", formattaData( dettaglio.getData().toString() ) );

            if( dettaglio.getCodiceFiscale() != null )
                titolo.setAttribute( "CodiceFiscaleRichiedente", dettaglio.getCodiceFiscale() );

            // PresentazioneTitoloInOriginale da valorizzare con 0 copia presentata non unica originale
            titolo.setAttribute( "PresentazioneTitoloInOriginale", "0" );

            // RestituzioneDuploCartaceo da valorizzare con 1 restituzione certificato eseguita
            titolo.setAttribute( "RestituzioneDuploCartaceo", "1" );

            // Sezione Modello due da non riportare nel file XML
            /*
            	Element modelloDue = new Element("ModelloDue");
            		Element estremiRegistrazione = new Element("EstremiRegistrazione");
            		estremiRegistrazione.setAttribute("CodUffRegistro", "");
            		estremiRegistrazione.setAttribute("Anno", "");
            		estremiRegistrazione.setAttribute("SerieVolume", "");
            		estremiRegistrazione.setAttribute("TipoAtto", "");
            		estremiRegistrazione.setAttribute("NumRegistrazioneUno", "");
            	modelloDue.addContent(estremiRegistrazione);	
            		Element file = new Element("File");
            		file.setAttribute("FormatoFile", "");
            	modelloDue.addContent(file);	
            	modelloDue.setAttribute("PresenzaDenuncia", "");
            titolo.addContent(modelloDue);
             */

            datiTitolo.addContent( titolo );
            adempimento.addContent( datiTitolo );

            Element datiConvenzione = new Element( "DatiConvenzione" );
            // valore inserito da verificare
            datiConvenzione.setAttribute( "ProgressivoConvenzione", "1" );

            Element convenzione = new Element( "Convenzione" );

            if( dettaglio.getTipoComunic() != null && dettaglio.getTipoComunic().length() > 3 )
                convenzione.setAttribute( "CodAtto", dettaglio.getTipoComunic().substring( dettaglio.getTipoComunic().length() - 3,
                    dettaglio.getTipoComunic().length() ) );
            else
                convenzione.setAttribute( "CodAtto", "600" );

            // RinunciaDiIpotecaLegale da valorizzare con 1 presenza di rinuncia
            convenzione.setAttribute( "RinunciaDiIpotecaLegale", "1" );
            if( dettaglio.getDescAtto() != null )
                convenzione.setAttribute( "Descrizione", dettaglio.getDescAtto() );
            if( dettaglio.getTipoNotaId() != null )
                convenzione.setAttribute( "TipoNota", dettaglio.getTipoNotaId().toString() );
            if( dettaglio.getDescCatAtto() != null )
                convenzione.setAttribute( "Specie", dettaglio.getAttoSpecie() );
            // AccettabileConRiserva da valorizzare con 0 NON accetabile con riserva
            convenzione.setAttribute( "AccettabileConRiserva", "0" );
            if( dettaglio.getConservatoriaId() != null )
            {
                Element codiceConservatoria = new Element( "CodiceConservatoria" );
                codiceConservatoria.setText( "" + dettaglio.getConservatoriaId() );
                convenzione.addContent( codiceConservatoria );
            }

            datiConvenzione.addContent( convenzione );

            Element tributiTerritorio = new Element( "TributiTerritorio" );

            // TipoLiquidazione da valorizzare con 1 formalità esente: liquidazione gratuita
            tributiTerritorio.setAttribute( "TipoLiquidazione", "1" );

            datiConvenzione.addContent( tributiTerritorio );

            List<BeneAssociatoDescDto> listaUnitaNegoziali = conservatorieService.listaUnitaNegoziali( idNota, idUfficioUtente );

            if( listaUnitaNegoziali != null )
            {
                if( !listaUnitaNegoziali.isEmpty() )
                {
                    Iterator<BeneAssociatoDescDto> iterUnita = listaUnitaNegoziali.iterator();
                    // CICLO PER UNITA' NEGOZIALI
                    while( iterUnita.hasNext() )
                    {
                        Element unitaNegoziali = new Element( "UnitaNegoziali" );
                        BeneAssociatoDescDto unita = iterUnita.next();

                        Iterator<BeneAssociatoDescDto> iterBeni = dettaglio.getListaBeni().iterator();
                        // CICLO PER BENI
                        String idImmobileCon = "";
                        while( iterBeni.hasNext() )
                        {
                            BeneAssociatoDescDto bene = iterBeni.next();
                            if( bene.getPROGUNEG().intValue() == unita.getPROGUNEG().intValue() )
                            {
                                idImmobileCon = idImmobileCon + " I" + formattaValore( "" + bene.getPROGRESSIVO() );
                            }
                        }
                        unitaNegoziali.setAttribute( "IdUnitaNegoziale", "U" + formattaValore( "" + unita.getPROGUNEG() ) );
                        unitaNegoziali.setAttribute( "IdImmobile", idImmobileCon );
                        datiConvenzione.addContent( unitaNegoziali );
                    }

                    Iterator<SoggettoInteressatoDescDto> iterUN = listaSoggettiUnitaNeg.iterator();
                    // CICLO DEI SOGGETTI UNITA NEGOZIALI
                    while( iterUN.hasNext() )
                    {
                        SoggettoInteressatoDescDto soggetto = iterUN.next();
                        Element datiAssociazione = new Element( "DatiAssociazione" );
                        datiAssociazione.setAttribute( "IdSoggetto", "S" + formattaValore( "" + soggetto.getProgSoggetto() ) );
                        datiAssociazione.setAttribute( "IdUnitaNegoziali", "U" + formattaValore( "" + soggetto.getProgUnitaNegoziale() ) );

                        if( soggetto.getProgSoggetto().intValue() > progressivoSoggetto )
                            progressivoSoggetto = soggetto.getProgSoggetto().intValue();

                        Element datiTitolarita = new Element( "DatiTitolarita" );
                        Element qualifica = new Element( "Qualifica" );
                        if( soggetto.getQualificaUnitaNegoziale() != null )
                            qualifica.setAttribute( "Qualifica", "CONTRO" );

                        qualifica.setAttribute( "TipoQualifica", "C" );
                        datiTitolarita.addContent( qualifica );

                        Element dirittiReali = new Element( "DirittiReali" );
                        if( soggetto.getNumeQuotaIntUnitaNegoziale() != null )
                        {
                            String millesimi = ("000" + soggetto.getNumeQuotaDecUnitaNegoziale());
                            millesimi = millesimi.substring( millesimi.length() - 3 );
                            String quota = "" + soggetto.getNumeQuotaIntUnitaNegoziale() + "," + millesimi + "/"
                                           + soggetto.getNumeQuotaDenomUnitaNegoziale();
                            dirittiReali.setAttribute( "Quota", quota );
                        }
                        if( soggetto.getTitoloInteresseUnitaNegoziale() != null )
                            dirittiReali.setAttribute( "CodiceDiritto", "" + soggetto.getTitoloInteresseUnitaNegoziale() );
                        if( soggetto.getDescrInteresseUnitaNegoziale() != null )
                            dirittiReali.setAttribute( "Descrizione", soggetto.getDescrInteresseUnitaNegoziale() );
                        if( soggetto.getIdregimeUnitaNegoziale() != null )
                            dirittiReali.setAttribute( "RegimeConiugi", "" + soggetto.getIdregimeUnitaNegoziale() );
                        if( soggetto.getRegimeConiugiUnitaNegoziale() != null )
                            dirittiReali.setAttribute( "IdSoggetto", "S" + formattaValore( "" + soggetto.getRegimeConiugiUnitaNegoziale() ) );
                        datiTitolarita.addContent( dirittiReali );
                        datiAssociazione.addContent( datiTitolarita );
                        datiConvenzione.addContent( datiAssociazione );
                    }

                    List<BeneAssociatoDescDto> unitaNegoziali = conservatorieService.listaUnitaNegoziali( idNota, idUfficioUtente );
                    int unitaNeg = 0;
                    Iterator<BeneAssociatoDescDto> iterUN2 = unitaNegoziali.iterator();
                    progressivoSoggetto++;
                    // CICLO PER UNITA' NEGOZIALI
                    while( iterUN2.hasNext() )
                    {
                        BeneAssociatoDescDto bene = iterUN2.next();
                        if( bene.getPROGUNEG().intValue() > unitaNeg )
                        {
                            int progrUn = bene.getPROGUNEG().intValue();

                            Hashtable<Long, Quota> tipiInt = new Hashtable<Long, Quota>();
                            Iterator<SoggettoInteressatoDescDto> soggIter = listaSoggettiUnitaNeg.iterator();
                            while( soggIter.hasNext() )
                            {
                                SoggettoInteressatoDescDto soggUn = soggIter.next();
                                if( soggUn.getProgUnitaNegoziale().intValue() == progrUn )
                                {
                                    Long tipoInt = soggUn.getTitoloInteresseUnitaNegoziale();
                                    Float numeratore = (float)soggUn.getNumeQuotaIntUnitaNegoziale() + 
                                                 (float)soggUn.getNumeQuotaDecUnitaNegoziale() / 1000;
                                    String descrInteresse = soggUn.getDescrInteresseUnitaNegoziale();
                                    Long denominatore = soggUn.getNumeQuotaDenomUnitaNegoziale(); // il denominatore non cambia nell'Unità Negoz.

                                    if( tipiInt.containsKey( tipoInt ) )
                                        numeratore += tipiInt.get( tipoInt ).numeratore;
                                    
                                    tipiInt.put( tipoInt, new Quota( descrInteresse, numeratore, denominatore ) );
                                }
                            }
                            Enumeration<Long> enumTipiInt = tipiInt.keys();
                            while( enumTipiInt.hasMoreElements() )
                            {
                                // ERARIO
                                Element datiAssociazioneErario = new Element( "DatiAssociazione" );
                                datiAssociazioneErario.setAttribute( "IdSoggetto", "S" + formattaValore( "" + progressivoSoggetto ) );
                                datiAssociazioneErario.setAttribute( "IdUnitaNegoziali", "U" + formattaValore( "" + bene.getPROGUNEG() ) );

                                Long tipoInt = enumTipiInt.nextElement();
                                String strQuota = "" + tipiInt.get( tipoInt ).numeratore + "/" + tipiInt.get( tipoInt ).denominatore;
                                strQuota = strQuota.replace( '.', ',' );
                                Element datiTitolarita = new Element( "DatiTitolarita" );
                                Element qualifica = new Element( "Qualifica" );
                                qualifica.setAttribute( "Qualifica", "FAVORE" );
                                qualifica.setAttribute( "TipoQualifica", "F" );
                                datiTitolarita.addContent( qualifica );

                                Element dirittiReali = new Element( "DirittiReali" );
                                dirittiReali.setAttribute( "Quota", strQuota );
                                dirittiReali.setAttribute( "CodiceDiritto", "" + tipoInt );
                                if( tipiInt.get( tipoInt ).descInteresse != null )
                                    dirittiReali.setAttribute( "Descrizione", tipiInt.get( tipoInt ).descInteresse );

                                datiTitolarita.addContent( dirittiReali );
                                datiAssociazioneErario.addContent( datiTitolarita );

                                datiConvenzione.addContent( datiAssociazioneErario );
                                unitaNeg = bene.getPROGUNEG().intValue();
                            }
                        }
                    }

                }
            }

            //------------- INIZIO NODO DatiTrascrizione in alternativa con DatiAnnotazione
            if( "TRASCRIZIONE".equals( dettaglio.getDescTipoNota().toUpperCase() ) )
            {
                Element datiTrascrizione = new Element( "DatiTrascrizione" );
                // SuccessioneTestamentaria da valorizzare con 0 ASSENZA
                datiTrascrizione.setAttribute( "SuccessioneTestamentaria", "0" );

                if( dettaglio.isPresenzaTermini() )
                {
                    datiTrascrizione.setAttribute( "TerminiEfficaciaAtto", "1" );
                } else
                {
                    datiTrascrizione.setAttribute( "TerminiEfficaciaAtto", "0" );
                }

                if( dettaglio.isPresenzaCondizione() )
                {
                    datiTrascrizione.setAttribute( "Condizione", "1" );
                } else
                {
                    datiTrascrizione.setAttribute( "Condizione", "0" );
                }

                // RinunciaTestamentaria da valorizzare con 0 ASSENZA
                datiTrascrizione.setAttribute( "RinunciaTestamentaria", "0" );

                if( dettaglio.isQuadroA() )
                {
                    datiTrascrizione.setAttribute( "PLquadroA", "1" );
                } else
                {
                    datiTrascrizione.setAttribute( "PLquadroA", "0" );
                }

                if( dettaglio.isQuadroB() )
                {
                    datiTrascrizione.setAttribute( "PLquadroB", "1" );
                } else
                {
                    datiTrascrizione.setAttribute( "PLquadroB", "0" );
                }

                if( dettaglio.isQuadroC() )
                {
                    datiTrascrizione.setAttribute( "PLquadroC", "1" );
                } else
                {
                    datiTrascrizione.setAttribute( "PLquadroC", "0" );
                }

                if( dettaglio.isDifferita() )
                {
                    datiTrascrizione.setAttribute( "VolturaDifferitaCatastale", "1" );
                } else
                {
                    datiTrascrizione.setAttribute( "VolturaDifferitaCatastale", "0" );
                }

                if( dettaglio.isAttoSoggVoltura() )
                {
                    datiTrascrizione.setAttribute( "ConvenzioneSoggettaVoltura", "1" );
                } else
                {
                    datiTrascrizione.setAttribute( "ConvenzioneSoggettaVoltura", "0" );
                }

                if( dettaglio.getDataDal() != null )
                    datiTrascrizione.setAttribute( "TermineDifferimento", formattaData( dettaglio.getDataDal().toString() ) );

                // NON VALORIZZATO
                // datiTrascrizione.setAttribute("DataMorte", "");

                if( dettaglio.getDataFormalita() != null && dettaglio.getNumeroFormalita1() != null )
                {

                    Element formalitaRiferimento = new Element( "FormalitaRiferimento" );

                    if( dettaglio.getNumeroFormalita1() != null )
                        formalitaRiferimento.setAttribute( "RegistroParticolareUno", "" + dettaglio.getNumeroFormalita1() );
                    if( dettaglio.getNumeroFormalita2() != null )
                        formalitaRiferimento.setAttribute( "RegistroParticolareDue", "" + dettaglio.getNumeroFormalita2() );
                    if( dettaglio.getDataFormalita() != null )
                        formalitaRiferimento.setAttribute( "Data", formattaData( dettaglio.getDataFormalita().toString() ) );

                    formalitaRiferimento.setAttribute( "TipoNota", dettaglio.getTipoNotaId().toString() );

                    datiTrascrizione.addContent( formalitaRiferimento );

                }

                datiConvenzione.addContent( datiTrascrizione );
            }
            //------------- FINE NODO DatiTrascrizione in alternativa con DatiAnnotazione

            //------------- INIZIO NODO DatiAnnotazione in alternativa con DatiTrascrizione	
            if( "ANNOTAZIONE".equals( dettaglio.getDescTipoNota().toUpperCase() ) )
            {
                Element datiAnnotazione = new Element( "DatiAnnotazione" );

                if( dettaglio.isVolturaCatastale() )
                {
                    datiAnnotazione.setAttribute( "VolturaCatastaleAttoOrig", "1" );

                } else
                {
                    datiAnnotazione.setAttribute( "VolturaCatastaleAttoOrig", "0" );
                }

                //					datiAnnotazione.setAttribute("RidSommaIpotecaDa", "");
                //					datiAnnotazione.setAttribute("RidSommaIpotecaA", "");
                String centesimi = ("00" + dettaglio.getValoreImmobLibParteDecimale());
                centesimi = centesimi.substring( centesimi.length() - 2 );
                datiAnnotazione.setAttribute( "ValImmobiliLiberati", "" + dettaglio.getValoreImmobLib() + "," + centesimi );
                //					datiAnnotazione.setAttribute("RidSommaDovutaDa", "");
                //					datiAnnotazione.setAttribute("RidSommaDovutaA", "");

                if( dettaglio.getTipoNotaId() != null || dettaglio.getTrascrizioneDel() != null || dettaglio.getNumeroTrascrizione1() != null
                    || dettaglio.getNumeroTrascrizione2() != null )
                {
                    Element formalitaAnnotata = new Element( "FormalitaAnnotata" );
                    formalitaAnnotata.setAttribute( "TipoNotadaAnnotare", "" + dettaglio.getTipoNotaId() );

                    Element formalitaRiferimentoAnnotazione = new Element( "FormalitaRiferimento" );
                    if( dettaglio.getTipoNotaId() != null )
                        formalitaRiferimentoAnnotazione.setAttribute( "TipoNota", "" + dettaglio.getTipoNotaId() );
                    if( dettaglio.getTrascrizioneDel() != null )
                        formalitaRiferimentoAnnotazione.setAttribute( "Data", formattaData( dettaglio.getTrascrizioneDel().toString() ) );
                    if( dettaglio.getNumeroTrascrizione1() != null )
                        formalitaRiferimentoAnnotazione.setAttribute( "RegistroParticolareUno", "" + dettaglio.getNumeroTrascrizione1() );
                    if( dettaglio.getNumeroTrascrizione2() != null )
                        formalitaRiferimentoAnnotazione.setAttribute( "RegistroParticolareDue", "" + dettaglio.getNumeroTrascrizione2() );

                    formalitaAnnotata.addContent( formalitaRiferimentoAnnotazione );

                    datiAnnotazione.addContent( formalitaAnnotata );
                }

                datiConvenzione.addContent( datiAnnotazione );
            }
            //------------- FINE NODO DatiAnnotazione in alternativa con DatiTrascrizione
            String descrizioneQD = valorizzaDescrizioneQD( idUfficioUtente, idNota );
            if( !descrizioneQD.equals( "" ) )
            {
                Element datiQuadroD = new Element( "DatiQuadroD" );
                Element descrizione = new Element( "Descrizione" );
                descrizione.setText( descrizioneQD );
                datiQuadroD.addContent( descrizione );
                datiConvenzione.addContent( datiQuadroD );
            }
            adempimento.addContent( datiConvenzione );

            // IMMOBILI da riportare tutti i valori presenti in Dati Convenzione
            // CICLO PER COMPOSIZIONE UNITA' NEGOZIALI
            if( dettaglio.getListaBeni() != null )
            {
                if( !dettaglio.getListaBeni().isEmpty() )
                {
                    Iterator<BeneAssociatoDescDto> iter = dettaglio.getListaBeni().iterator();
                    while( iter.hasNext() )
                    {
                        BeneAssociatoDescDto bene = iter.next();
                        Element datiImmobile = new Element( "DatiImmobile" );
                        datiImmobile.setAttribute( "IdImmobile", "I" + formattaValore( "" + bene.getPROGRESSIVO() ) );
                        if( bene.getComune() != null )
                        {
                            Element immobileUT = new Element( "ImmobileUT" );
                            immobileUT.setAttribute( "Comune", bene.getComune() );
                            immobileUT.setAttribute( "ComuneCatastale", bene.getComune() );
                            if( bene.getCodiFkIdFondo() != null )
                                immobileUT.setAttribute( "FondoDS", "" + bene.getCodiFkIdFondo() );
                            if( bene.getProvincia() != null )
                            {
                                immobileUT.setAttribute( "Provincia", bene.getProvincia() );
                                immobileUT.setAttribute( "ProvinciaCatastale", bene.getProvincia() );
                            }else
                            {
                                immobileUT.setAttribute( "Provincia", "" );
                            }
                            if( bene.getCatasto() != null )
                            {
                                if( bene.getCatasto().length() == 5 )
                                    immobileUT.setAttribute( "SezCensuaria", bene.getCatasto().substring( 4, 5 ) );
                            }

                            datiImmobile.addContent( immobileUT );
                        }

                        //------------- INIZIO NODO ImmobileU in alternativa con ImmobileT
                        if( "U".equals( bene.getCodTipoCatasto().toString() ) )
                        {
                            Element immobileU = new Element( "ImmobileU" );

                            //------------- INIZIO NODO IdentificativoDefinitivo in alternativa con IdentificativoProvvisorio o Graffati
                            if( (bene.getDescFoglio() != null) || (bene.getDescSezione() != null) || (bene.getDescParticella1() != null)
                                || (bene.getDescSubalterno1() != null) )
                            {

                                Element identificativoDefinitivo = new Element( "IdentificativoDefinitivo" );
                                if( bene.getDescFoglio() != null )
                                    identificativoDefinitivo.setAttribute( "Foglio", bene.getDescFoglio() );
                                if( bene.getDescSezione() != null )
                                    identificativoDefinitivo.setAttribute( "SezUrbana", bene.getDescSezione() );
                                if( bene.getDescParticella1() != null )
                                    identificativoDefinitivo.setAttribute( "ParticellaUno", bene.getDescParticella1() );
                                if( bene.getDescParticella2() != null )
                                    identificativoDefinitivo.setAttribute( "ParticellaDue", bene.getDescParticella2() );
                                if( bene.getDescSubalterno1() != null )
                                    identificativoDefinitivo.setAttribute( "SubalternoUno", bene.getDescSubalterno1() );
                                if( bene.getDescSubalterno2() != null )
                                    identificativoDefinitivo.setAttribute( "SubalternoDue", bene.getDescSubalterno2() );

                                immobileU.addContent( identificativoDefinitivo );
                            }
                            //------------- FINE NODO IdentificativoDefinitivo in alternativa con IdentificativoProvvisorio o Graffati

                            //------------- INIZIO NODO IdentificativoProvvisorio in alternativa con IdentificativoDefinitivo o Graffati
                            else
                                if( (bene.getNumeAnnoDenuncia() != null) || (bene.getNumeDenuncia1() != null)
                                    || (bene.getDescTipoDenuncia() != null) )
                                {
                                    Element identificativoProvvisorio = new Element( "IdentificativoProvvisorio" );
                                    if( bene.getNumeAnnoDenuncia() != null )
                                        identificativoProvvisorio.setAttribute( "Anno", "" + bene.getNumeAnnoDenuncia() );
                                    if( bene.getNumeDenuncia1() != null )
                                        identificativoProvvisorio.setAttribute( "Numero", "" + bene.getNumeDenuncia1() );
                                    if( bene.getDescTipoDenuncia() != null )
                                        identificativoProvvisorio.setAttribute( "Tipo", "" + bene.getDescTipoDenuncia() );

                                    immobileU.addContent( identificativoProvvisorio );
                                }
                                //------------- FINE NODO IdentificativoProvvisorio in alternativa con IdentificativoDefinitivo  o Graffati

                                //------------- INIZIO NODO Graffati  in alternativa con IdentificativoProvvisorio o IdentificativoDefinitivo
                                else
                                    if( bene.getNumeGraffati() != null )
                                    {
                                        Element graffati = new Element( "Graffati" );

                                        Element progGraffato = new Element( "ProgGraffato" );
                                        graffati.addContent( progGraffato );

                                        Element identificativoDefinitivoGraffati = new Element( "IdentificativoDefinitivo" );
                                        if( bene.getDescFoglio() != null )
                                            identificativoDefinitivoGraffati.setAttribute( "Foglio", bene.getDescFoglio() );
                                        if( bene.getDescSezione() != null )
                                            identificativoDefinitivoGraffati.setAttribute( "SezUrbana", bene.getDescSezione() );
                                        if( bene.getDescParticella1() != null )
                                            identificativoDefinitivoGraffati.setAttribute( "ParticellaUno", bene.getDescParticella1() );
                                        if( bene.getDescParticella2() != null )
                                            identificativoDefinitivoGraffati.setAttribute( "ParticellaDue", bene.getDescParticella2() );
                                        if( bene.getDescSubalterno1() != null )
                                            identificativoDefinitivoGraffati.setAttribute( "SubalternoUno", bene.getDescSubalterno1() );
                                        if( bene.getDescSubalterno2() != null )
                                            identificativoDefinitivoGraffati.setAttribute( "SubalternoDue", bene.getDescSubalterno2() );
                                        graffati.addContent( identificativoDefinitivoGraffati );

                                        immobileU.addContent( graffati );
                                    }

                            //------------- FINE Graffati NODO in alternativa con IdentificativoProvvisorio o IdentificativoDefinitivo

                            Element consistenzaU = new Element( "ConsistenzaU" );
                            // Legge154 da valorizzare con 0 NO
                            consistenzaU.setAttribute( "Legge154", "0" );
                            // InteresseSA da valorizzare con 0 NO
                            consistenzaU.setAttribute( "InteresseSA", "0" );

                            //------------- INIZIO NODO ImmobileClassato in alternativa con ImmobileNonClassato					
                            // Individuo se Bene CLASSATO
                            if( bene.getDescrBreveCat() != null )
                            {

                                if( verificaBeneClassato( bene.getDescrBreveCat() ) )
                                {
                                    Element immobileClassato = new Element( "ImmobileClassato" );
                                    immobileClassato.setAttribute( "Categoria", bene.getDescrBreveCat() );

                                    //------------- INIZIO NODO Vani  in alternativa con MetriQuadrati e Cubi
                                    if( bene.getNumeVaniInt() != null )
                                    {
                                        Element vani = new Element( "Vani" );
                                        vani.setText( bene.getNumeVaniInt() + "," + bene.getNumeVaniDec() );
                                        immobileClassato.addContent( vani );
                                    }
                                    //------------- FINE NODO Vani  in alternativa con MetriQuadrati e Cubi	
                                    //------------- INIZIO NODO MetriQuadrati e Cubi in alternativa con Vani 
                                    if( bene.getNumeSupMqInt() != null )
                                    {
                                        Element metriQuadrati = new Element( "MetriQuadrati" );
                                        metriQuadrati.setText( bene.getNumeSupMqInt() + "," + bene.getNumeSupMqDec() );
                                        immobileClassato.addContent( metriQuadrati );
                                    }

                                    if( bene.getNumeMcInt() != null )
                                    {
                                        Element metriCubi = new Element( "MetriCubi" );
                                        metriCubi.setText( bene.getNumeMcInt() + "," + bene.getNumeMcDec() );
                                        immobileClassato.addContent( metriCubi );
                                    }

                                    //------------- FINE NODO MetriQuadrati e Cubi in alternativa con Vani
                                    consistenzaU.addContent( immobileClassato );
                                }
                            }

                            //------------- FINE NODO ImmobileClassato in alternativa con ImmobileNonClassato	

                            //------------- INIZIO NODO ImmobileNonClassato in alternativa con ImmobileClassato	

                            if( bene.getDescrBreveCat() != null )
                            {
                                if( !verificaBeneClassato( bene.getDescrBreveCat() ) )
                                {

                                    Element immobileNonClassato = new Element( "ImmobileNonClassato" );
                                    immobileNonClassato.setAttribute( "Natura", bene.getDescrBreveCat() );

                                    consistenzaU.addContent( immobileNonClassato );

                                }
                            }
                            //------------- FINE NODO ImmobileNonClassato in alternativa con ImmobileClassato					

                            immobileU.addContent( consistenzaU );

                            datiImmobile.addContent( immobileU );
                            //------------- FINE NODO ImmobileU in alternativa con ImmobileT					

                        } else
                            if( "T".equals( bene.getCodTipoCatasto().toString() ) )
                            {
                                //------------- INIZIO NODO ImmobileT in alternativa con ImmobileU
                                Element immobileT = new Element( "ImmobileT" );

                                Element identificativoDefinitivoImmobileT = new Element( "IdentificativoDefinitivo" );
                                if( bene.getDescFoglio() != null )
                                    identificativoDefinitivoImmobileT.setAttribute( "Foglio", bene.getDescFoglio() );
                                if( bene.getDescSezione() != null )
                                    identificativoDefinitivoImmobileT.setAttribute( "SezUrbana", bene.getDescSezione() );
                                if( bene.getDescParticella1() != null )
                                    identificativoDefinitivoImmobileT.setAttribute( "ParticellaUno", bene.getDescParticella1() );
                                if( bene.getDescParticella2() != null )
                                    identificativoDefinitivoImmobileT.setAttribute( "ParticellaDue", bene.getDescParticella2() );
                                if( bene.getDescSubalterno1() != null )
                                    identificativoDefinitivoImmobileT.setAttribute( "SubalternoUno", bene.getDescSubalterno1() );
                                if( bene.getDescSubalterno2() != null )
                                    identificativoDefinitivoImmobileT.setAttribute( "SubalternoDue", bene.getDescSubalterno2() );

                                immobileT.addContent( identificativoDefinitivoImmobileT );

                                Element consistenzaT = new Element( "ConsistenzaT" );
                                if( bene.getTipoTerreno() != null )
                                {
                                    consistenzaT.setAttribute( "TipoTerreno", valorizzaTipoTerreno( bene.getTipoTerreno() ) );
                                }else
                                {
                                    consistenzaT.setAttribute( "TipoTerreno", "5" );
                                }
                                consistenzaT.setAttribute( "Natura", bene.getDescrBreveCat() );

                                Element superficieTotale = new Element( "SuperficieTotale" );

                                Element superficieST = new Element( "Superficie" );
                                if( bene.getNumeSupCentiare() != null )
                                    superficieST.setAttribute( "Centiare", "" + bene.getNumeSupCentiare() );
                                if( bene.getNumeSupAre() != null )
                                    superficieST.setAttribute( "Are", "" + bene.getNumeSupAre() );
                                if( bene.getNumeSupEttari() != null )
                                    superficieST.setAttribute( "Ettari", "" + bene.getNumeSupEttari() );
                                if( bene.getNumeSupMqInt() != null )
                                    superficieST.setAttribute( "MetriQuadrati", bene.getNumeSupMqInt() + "," + bene.getNumeSupMqDec() );

                                superficieTotale.addContent( superficieST );

                                consistenzaT.addContent( superficieTotale );

                                Element superficieEdificabile = new Element( "SuperficieEdificabile" );

                                Element superficieSE = new Element( "Superficie" );
                                if( bene.getNumeSupCentiare() != null )
                                    superficieSE.setAttribute( "Centiare", "" + bene.getNumeSupCentiare() );
                                if( bene.getNumeSupAre() != null )
                                    superficieSE.setAttribute( "Are", "" + bene.getNumeSupAre() );
                                if( bene.getNumeSupEttari() != null )
                                    superficieSE.setAttribute( "Ettari", "" + bene.getNumeSupEttari() );
                                if( bene.getNumeSupMqInt() != null )
                                    superficieSE.setAttribute( "MetriQuadrati", bene.getNumeSupMqInt() + "," + bene.getNumeSupMqDec() );

                                superficieEdificabile.addContent( superficieSE );

                                consistenzaT.addContent( superficieEdificabile );

                                Element SuperficieUtile = new Element( "SuperficieUtile" );

                                Element superficieSU = new Element( "Superficie" );
                                if( bene.getNumeSupCentiare() != null )
                                    superficieSU.setAttribute( "Centiare", "" + bene.getNumeSupCentiare() );
                                if( bene.getNumeSupAre() != null )
                                    superficieSU.setAttribute( "Are", "" + bene.getNumeSupAre() );
                                if( bene.getNumeSupEttari() != null )
                                    superficieSU.setAttribute( "Ettari", "" + bene.getNumeSupEttari() );
                                if( bene.getNumeSupMqInt() != null )
                                    superficieSU.setAttribute( "MetriQuadrati", bene.getNumeSupMqInt() + "," + bene.getNumeSupMqDec() );

                                SuperficieUtile.addContent( superficieSU );

                                consistenzaT.addContent( SuperficieUtile );

                                immobileT.addContent( consistenzaT );

                                datiImmobile.addContent( immobileT );
                                //------------- FINE NODO ImmobileT in alternativa con ImmobileU						
                            }

                        if( (bene.getNumeCapitaleInt() != null || bene.getNumeCapitaleDec() != null) )
                        {

                            Element quoteFrazionate = new Element( "QuoteFrazionate" );

                            if( bene.getNumeCapitaleInt() != null || bene.getNumeCapitaleDec() != null )
                                quoteFrazionate.setAttribute( "Capitale", bene.getNumeCapitaleInt() + "," + bene.getNumeCapitaleDec() );
                            datiImmobile.addContent( quoteFrazionate );
                        }

                        if( bene.getDescIndirizzo() != null )
                        {

                            Element datiIndirizzo = new Element( "DatiIndirizzo" );

                            if( bene.getDescScala() != null )
                                datiIndirizzo.setAttribute( "Scala", bene.getDescScala() );

                            if( bene.getDescEdificio() != null )
                                datiIndirizzo.setAttribute( "Edificio", bene.getDescEdificio() );

                            if( bene.getDescLotto() != null )
                                datiIndirizzo.setAttribute( "Lotto", bene.getDescLotto() );

                            Element indirizzoImm = new Element( "IndirizzoImm" );
                            indirizzoImm.setAttribute( "Indirizzo", bene.getDescIndirizzo() );

                            if( bene.getDescCivico() != null )
                            {
                                Element numeroCivico = new Element( "NumeroCivico" );
                                numeroCivico.setText( bene.getDescCivico() );
                                indirizzoImm.addContent( numeroCivico );
                            }
                            datiIndirizzo.addContent( indirizzoImm );

                            if( bene.getDescInterno() != null )
                            {
                                Element interno = new Element( "Interno" );
                                interno.setText( bene.getDescInterno() );
                                datiIndirizzo.addContent( interno );
                            }
                            if( bene.getDescPiano() != null )
                            {
                                Element piano = new Element( "Piano" );
                                piano.setText( bene.getDescPiano() );
                                datiIndirizzo.addContent( piano );
                            }

                            datiImmobile.addContent( datiIndirizzo );
                        }

                        if( bene.getDescCatastoP() != null && (bene.getDescFoglioP() != null || bene.getDescParticella1P() != null) )
                        {

                            Element estremiPrecedenti = new Element( "EstremiPrecedenti" );
                            if( bene.getComune() != null )
                            {
                                estremiPrecedenti.setAttribute( "Comune", bene.getComune() );
                                estremiPrecedenti.setAttribute( "Provincia", bene.getProvincia() );
                                estremiPrecedenti.setAttribute( "ComuneCatastale", bene.getComune() );
                                estremiPrecedenti.setAttribute( "ProvinciaCatastale", "" );
                            }
                            if( bene.getDescCatastoP() != null )
                            {
                                if( bene.getDescCatastoP().length() == 5 )
                                    estremiPrecedenti.setAttribute( "SezCensuaria", bene.getDescCatastoP().substring( 4, 5 ) );
                            }
                            if( bene.getCodTipoCatastoP() != null )
                                estremiPrecedenti.setAttribute( "TipoCatasto", "" + bene.getCodTipoCatastoP() );
                            estremiPrecedenti.setAttribute("ProgressivoEstremoPrecedente", "0");

                            if( bene.getDescFoglioP() != null || bene.getDescParticella1P() != null )
                            {
                                Element identificativoDefinitivoEstremiPrecedenti = new Element( "IdentificativoDefinitivo" );
                                if( bene.getDescFoglioP() != null )
                                    identificativoDefinitivoEstremiPrecedenti.setAttribute( "Foglio", bene.getDescFoglioP() );
                                if( bene.getDescParticella1P() != null )
                                    identificativoDefinitivoEstremiPrecedenti.setAttribute( "ParticellaUno", bene.getDescParticella1P() );
                                estremiPrecedenti.addContent( identificativoDefinitivoEstremiPrecedenti );
                            }

                            datiImmobile.addContent( estremiPrecedenti );
                        }
                        adempimento.addContent( datiImmobile );
                    }

                }
            }
            // FINE CICLO IMMOBILI

            // CICLO PER SOGGETTI
            if( dettaglio.getListaSoggetti() != null )
            {
                if( !dettaglio.getListaSoggetti().isEmpty() )
                {
                    Iterator<SoggettoInteressatoDescDto> iter = dettaglio.getListaSoggetti().iterator();
                    while( iter.hasNext() )
                    {
                        Element datiSoggetto = new Element( "DatiSoggetto" );
                        SoggettoInteressatoDescDto soggetto = iter.next();
                        datiSoggetto.setAttribute( "IdSoggetto", "S" + formattaValore( "" + soggetto.getProgressivo() ) );
                        String tipoDomicilio = valorizzaTipoDomicilio( idUfficioUtente, dettaglio.getNumeroProcedimento(),
                            soggetto.getSoggettoSitMp() );
                        if( !tipoDomicilio.equals( "" ) )
                        {
                            Element residenzaSede = new Element( "ResidenzaSede" );
                            residenzaSede.setAttribute( "TipoDomicilio", valorizzaTipoDomicilio( idUfficioUtente,
                                dettaglio.getNumeroProcedimento(), soggetto.getIdSoggetto().longValue() ) );
                            Element indirizzoAnagraficoSede = new Element( "IndirizzoAnagrafico" );
                            indirizzoAnagraficoSede.setAttribute( "Comune", "" );
                            indirizzoAnagraficoSede.setAttribute( "Provincia", "" );
                            indirizzoAnagraficoSede.setAttribute( "Stato", "" );
                            residenzaSede.addContent( indirizzoAnagraficoSede );
                            datiSoggetto.addContent( residenzaSede );
                        }

                        //------------- INIZIO NODO SoggettoF in alternativa con SoggettoN
                        if( "F".equals( soggetto.getTipologia().toString() ) )
                        {
                            if( soggetto.getCodiceFiscalePF() != null )
                            {
                                Element soggettoF = new Element( "SoggettoF" );
                                if( soggetto.getFlgSesso() != null )
                                    soggettoF.setAttribute( "Sesso", "" + soggetto.getFlgSesso() );
                                if( soggetto.getDataNascita() != null )
                                    soggettoF.setAttribute( "DataNascita", formattaData( soggetto.getDataNascita().toString() ) );
                                if( soggetto.getNome() != null )
                                    soggettoF.setAttribute( "Nome", soggetto.getNome() );
                                if( soggetto.getProvNascita() != null )
                                {
                                    soggettoF.setAttribute( "Provincia", soggetto.getProvNascita() );
                                } else
                                {
                                    soggettoF.setAttribute( "Provincia", "EE" );
                                }
                                if( soggetto.getCognome() != null )
                                    soggettoF.setAttribute( "Cognome", soggetto.getCognome() );
                                if( soggetto.getComuneNascita() != null )
                                    soggettoF.setAttribute( "ComuneNascita", soggetto.getComuneNascita() );
                                if( soggetto.getCodiceFiscalePF() != null )
                                    soggettoF.setAttribute( "CodiceFiscale", soggetto.getCodiceFiscalePF() );
                                if( soggetto.getRegime() != null )
                                {
                                    if( !("S".equals( soggetto.getRegime().toString() ) || "C".equals( soggetto.getRegime().toString() )) )
                                    {
                                        soggettoF.setAttribute( "StatoCivile", soggetto.getRegime().toString() );
                                    } else
                                        if( soggetto.getCodProgrSoggC() != null )
                                        {
                                            soggettoF.setAttribute( "IdConiugeNonComparente", "" + soggetto.getCodProgrSoggC() );
                                        }
                                }
                                datiSoggetto.addContent( soggettoF );
                            }
                        }
                        //------------- FINE NODO SoggettoF in alternativa con SoggettoN

                        //------------- INIZIO NODO SoggettoN in alternativa con SoggettoF

                        if( "N".equals( soggetto.getTipologia().toString() ) )
                        {
                            if( soggetto.getDenominazione() != null )
                            {
                                Element soggettoN = new Element( "SoggettoN" );
                                if( soggetto.getDenominazione() != null )
                                    soggettoN.setAttribute( "Denominazione", soggetto.getDenominazione() );
                                if( soggetto.getPartitaIva() != null )
                                    soggettoN.setAttribute( "CodiceFiscale", soggetto.getPartitaIva() );

                                datiSoggetto.addContent( soggettoN );
                            }
                            //------------- FINE NODO SoggettoN in alternativa con SoggettoF
                        }
                        adempimento.addContent( datiSoggetto );
                    }
                }
            }
            // FINE CICLO SOGGETTI

            // ERARIO
            Element datiSoggettoErario = new Element( "DatiSoggetto" );
            datiSoggettoErario.setAttribute( "IdSoggetto", "S" + formattaValore( "" + progressivoSoggetto ) );

            Element residenzaSedeErario = new Element( "ResidenzaSede" );
            residenzaSedeErario.setAttribute( "TipoDomicilio", "1" );
            Element indirizzoAnagraficoSede = new Element( "IndirizzoAnagrafico" );
            indirizzoAnagraficoSede.setAttribute( "Comune", "ROMA" );
            indirizzoAnagraficoSede.setAttribute( "Provincia", "RM" );
            indirizzoAnagraficoSede.setAttribute( "Stato", "Italia" );
            residenzaSedeErario.addContent( indirizzoAnagraficoSede );
            datiSoggettoErario.addContent( residenzaSedeErario );

            Element soggettoN = new Element( "SoggettoN" );
            soggettoN.setAttribute( "Denominazione", "ERARIO DELLO STATO" );
            soggettoN.setAttribute( "CodiceFiscale", "80004700797" );
            datiSoggettoErario.addContent( soggettoN );

            adempimento.addContent( datiSoggettoErario );

            Element testoAtto = new Element( "TestoAtto" );
            Element testoLibero = new Element( "TestoLibero" );
            if( dettaglio.getDescAtto() != null )
                testoLibero.setText( dettaglio.getDescAtto() );
            testoAtto.addContent( testoLibero );
            Element primoNumeroRepertorioTestoAtto = new Element( "PrimoNumeroRepertorio" );
            primoNumeroRepertorioTestoAtto.setText( dettaglio.getNumeroRepertorio1().toString() );
            testoAtto.addContent( primoNumeroRepertorioTestoAtto );
            Element secondoNumeroRepertorioTestoAtto = new Element( "SecondoNumeroRepertorio" );
            secondoNumeroRepertorioTestoAtto.setText( dettaglio.getRepertorioAnno().toString() );
            testoAtto.addContent( secondoNumeroRepertorioTestoAtto );

            adempimento.addContent( testoAtto );

            datiTelematico.addContent( adempimento );

            root.addContent( datiTelematico );

            // Creazione dell'oggetto XMLOutputter
            XMLOutputter outputter = new XMLOutputter();
            // Imposto il formato dell'outputter come "bel formato"
            outputter.setFormat( Format.getPrettyFormat() );
            xmlString = outputter.outputString( document );

            System.out.println( xmlString );
            return xmlString;

        } catch( Exception e )
        {
            System.err.println( "Errore durante l'elaborazione del documento" );
            e.printStackTrace();
        }
        return null;
    }

    private boolean verificaBeneClassato( String descrizione )
    {
        if( descrizione.length() < 2 )
        {
            return false;
        } else
            if( ((descrizione.substring( 1, 2 )).equals( "0" ) ||
                 (descrizione.substring( 1, 2 )).equals( "1" ) ||
                 (descrizione.substring( 1, 2 )).equals( "2" ) ||
                 (descrizione.substring( 1, 2 )).equals( "3" ) ||
                 (descrizione.substring( 1, 2 )).equals( "4" ) ||
                 (descrizione.substring( 1, 2 )).equals( "5" ) ||
                 (descrizione.substring( 1, 2 )).equals( "6" ) ||
                 (descrizione.substring( 1, 2 )).equals( "7" ) ||
                 (descrizione.substring( 1, 2 )).equals( "8" ) || (descrizione.substring( 1, 2 )).equals( "9" )) )
            {
                return true;
            }
        return false;
    }

    private String formattaValore( String valore )
    {
        if( valore.length() == 1 )
            valore = "00000" + valore;
        else
            if( valore.length() == 2 )
                valore = "0000" + valore;
            else
                if( valore.length() == 3 )
                    valore = "000" + valore;
                else
                    if( valore.length() == 4 )
                        valore = "00" + valore;
                    else
                        if( valore.length() == 5 )
                            valore = "0" + valore;
        return valore;
    }

    private String formattaData( String valore )
    {
        // formato YYYY-MM-DD
        String giorno = valore.substring( 8, 10 );
        String mese = valore.substring( 5, 7 );
        String anno = valore.substring( 0, 4 );
        return giorno + mese + anno;
    }

    private String valorizzaTipoTerreno( String valore )
    {
        String tipoTerreno = "";
        if( "32".equals( valore ) )
        {
            tipoTerreno = "0";
        }
        else
            if( "34".equals( valore ) )
            {
                tipoTerreno = "1";
            }
            else
            {
                tipoTerreno = "5"; // per i vecchi valori "33" || "106"
            }
        return tipoTerreno;
    }

    private String valorizzaTipoDomicilio( Integer idUff, Long numeroProcedimento, Long idSoggetto )
    {
        String tipoDomicilio = "";
        TipoDomicilioDto domicilio = conservatorieService.getTipoDomicilio( idUff, numeroProcedimento, idSoggetto );
        if( domicilio != null )
        {
            if( domicilio.getDescIndirDom() != null )
                tipoDomicilio = "0";
            if( domicilio.getDescIndirRes() != null )
                tipoDomicilio = "1";
        }
        return tipoDomicilio;
    }

    private String valorizzaDescrizioneQD( Integer idUff, Long idNota )
    {
        String descrizione = conservatorieService.getDescrizioneQuadroD( idUff, idNota );
        return descrizione;
    }
}