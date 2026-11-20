package it.giustizia.smp.utility;

import it.giustizia.smp.form.BeneForm;
import it.giustizia.smp.xml.DatiBeneAziendaGest;
import it.giustizia.smp.xml.DatiBeneFinanzGest;
import it.giustizia.smp.xml.DatiBeneImmobileGest;
import it.giustizia.smp.xml.DatiBeneMobileGest;
import it.giustizia.smp.xml.DatiBeneVeicoloGest;
import it.giustizia.smp.xml.DatiBeneImmaterialeGest;
import it.giustizia.smp.xml.DatiBeneMobileGest;
import it.giustizia.smp.xml.amministratori.BeneExtType;
import it.giustizia.smp.xml.amministratori.DatiAziendaGestType;
import it.giustizia.smp.xml.amministratori.DatiFinanzGestType;
import it.giustizia.smp.xml.amministratori.DatiImmobileGestType;
import it.giustizia.smp.xml.amministratori.DatiMobileGestType;
import it.giustizia.smp.xml.amministratori.DatiVeicoloGestType;
import it.giustizia.smp.xml.amministratori.DatiImmaterialeGestType;
import it.giustizia.smp.xml.amministratori.DatiMobileGestType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.dozer.DozerBeanMapper;

public class ToolAmmMapper {

	static DozerBeanMapper mapperBean;
	static {

		List<String> myMappingFiles = new ArrayList<String>();
		myMappingFiles.add("toolAmmMapping.xml");
		mapperBean = new DozerBeanMapper(myMappingFiles);


	}

	public static void map(BeneExtType bene, BeneForm beneForm) {
		if (bene.getDatiSpecificiBene().getAltriOggetti() != null) {
			beneForm.setDatiBeneMobileGest( new DatiBeneMobileGest());
			DatiMobileGestType in = bene.getDatiSpecificiBene().getAltriOggetti().getDatiMobileGest();  
			if (in != null) {
				mapperBean.map(in, beneForm.getDatiBeneMobileGest());
				beneForm.getDatiBeneMobileGest().setDirittiTerzi(in.isDirittiTerzi());
				beneForm.getDatiBeneMobileGest().setBeneRinvenuto(in.isBeneRinvenuto());
				beneForm.getDatiBeneMobileGest().setOneriCustodia(in.isOneriCustodia());
			}
		}
		if (bene.getDatiSpecificiBene().getAzienda() != null) {
			beneForm.setDatiBeneAziendaGest(new DatiBeneAziendaGest());
			DatiAziendaGestType in = bene.getDatiSpecificiBene().getAzienda().getDatiAziendaGest();
			if (in != null) {
				mapperBean.map(in, beneForm.getDatiBeneAziendaGest());
				beneForm.getDatiBeneAziendaGest().setAddettiIrregolari(in.isAddettiIrregolari());
				beneForm.getDatiBeneAziendaGest().setContenziosi(in.isContenziosi());
				beneForm.getDatiBeneAziendaGest().setDirittiTerzi(in.isDirittiTerzi());
				beneForm.getDatiBeneAziendaGest().setLavoratoriFormazione(in.isLavoratoriFormazione());
				beneForm.getDatiBeneAziendaGest().setLavoratoriStranieri(in.isLavoratoriStranieri());
				beneForm.getDatiBeneAziendaGest().setRispettoNormeSicurezza(in.isRispettoNormeSicurezza());
				beneForm.getDatiBeneAziendaGest().setTrascrizioneSequestro(in.isTrascrizioneSequestro());
			}
		}
	    if (bene.getDatiSpecificiBene().getFinanziario() != null) {
	    	beneForm.setDatiBeneFinanzGest( new DatiBeneFinanzGest());
			DatiFinanzGestType in = bene.getDatiSpecificiBene().getFinanziario().getDatiFinanzGest();  
			if (in != null) {
				mapperBean.map(in, beneForm.getDatiBeneFinanzGest());
				beneForm.getDatiBeneFinanzGest().setDirittiTerzi(in.isDirittiTerzi());
				beneForm.getDatiBeneFinanzGest().setBeneRinvenuto(in.isBeneRinvenuto());
				if (in.getFinanziario() != null) {
					beneForm.getDatiBeneFinanzGest().getFinanziario().setComunicazioneFUG(in.getFinanziario().isComunicazioneFUG());
				}
				if (in.getMobile() != null) {	
					beneForm.getDatiBeneFinanzGest().getMobile().setOneriCustodia(in.getMobile().isOneriCustodia());
				}
			}
	    }
		if (bene.getDatiSpecificiBene().getAnimali() != null) {
	    	beneForm.setDatiBeneMobileGest( new DatiBeneMobileGest());
			DatiMobileGestType in = bene.getDatiSpecificiBene().getAnimali().getDatiMobileGest();
			if (in != null) {
				mapperBean.map(in, beneForm.getDatiBeneMobileGest());
				beneForm.getDatiBeneMobileGest().setDirittiTerzi(in.isDirittiTerzi());
				beneForm.getDatiBeneMobileGest().setBeneRinvenuto(in.isBeneRinvenuto());
				beneForm.getDatiBeneMobileGest().setOneriCustodia(in.isOneriCustodia());
			}
		}
		if (bene.getDatiSpecificiBene().getImmobile() != null) {
	    	beneForm.setDatiBeneImmobileGest( new DatiBeneImmobileGest());
			DatiImmobileGestType in = bene.getDatiSpecificiBene().getImmobile().getDatiImmobileGest();
			if (in != null) {
				//mapperBean.map(in, beneForm.getDatiBeneImmobileGest());
				// convert any single element ...
				
				beneForm.getDatiBeneImmobileGest().setStima(in.getStima());
				beneForm.getDatiBeneImmobileGest().setDataStima(in.getDataStima());
				if (in.getDirittiTerzi() != null)
					beneForm.getDatiBeneImmobileGest().setDirittiTerzi(in.getDirittiTerzi().getValue());
				if (in.getDescDirittiTerzi() != null)
					beneForm.getDatiBeneImmobileGest().setDescDirittiTerzi(in.getDescDirittiTerzi().getValue());
				if (in.getDataPossesso() != null)
					beneForm.getDatiBeneImmobileGest().setDataPossesso(in.getDataPossesso().getValue());
			    if (in.getStimaOneriRistrutturazione() != null)
			    	beneForm.getDatiBeneImmobileGest().setStimaOneriRistrutturazione(in.getStimaOneriRistrutturazione().getValue());
			    if (in.getConfiscaParziale() != null)
			    	beneForm.getDatiBeneImmobileGest().setConfiscaParziale(in.getConfiscaParziale().getValue());
			    if (in.getBeneDivisibile() != null)
			    	beneForm.getDatiBeneImmobileGest().setBeneDivisibile(in.getBeneDivisibile().getValue());
			    if (in.getUtilizzoAutonomoQuota() != null)
			    	beneForm.getDatiBeneImmobileGest().setUtilizzoAutonomoQuota(in.getUtilizzoAutonomoQuota().getValue());
			    if (in.getVocazioneTuristica() != null)
			    	beneForm.getDatiBeneImmobileGest().setVocazioneTuristica(in.getVocazioneTuristica().getValue());
			    if (in.getFabbricatoInteramenteConfiscato() != null)
			    	beneForm.getDatiBeneImmobileGest().setFabbricatoInteramenteConfiscato(in.getFabbricatoInteramenteConfiscato().getValue());
			    if (in.getImmobileOccupato() != null)
			    	beneForm.getDatiBeneImmobileGest().setImmobileOccupato(in.getImmobileOccupato().getValue());
			    if (in.getDescTipoOccupazione() != null)
			    	beneForm.getDatiBeneImmobileGest().setDescTipoOccupazione(in.getDescTipoOccupazione().getValue());
			    if (in.getDescAltroTipoOccupazione() != null)
			    	beneForm.getDatiBeneImmobileGest().setDescAltroTipoOccupazione(in.getDescAltroTipoOccupazione().getValue());
			    if (in.getDataStipulaContratto() != null)
			    	beneForm.getDatiBeneImmobileGest().setDataStipulaContratto(in.getDataStipulaContratto());
			    if (in.getDataScadenzaContratto() != null)
			    	beneForm.getDatiBeneImmobileGest().setDataScadenzaContratto(in.getDataScadenzaContratto());
			    if (in.getCanoneAnnuoLocazione() != null)
			    	beneForm.getDatiBeneImmobileGest().setCanoneAnnuoLocazione(in.getCanoneAnnuoLocazione().getValue());
			    if (in.getMensilePattuito() != null)
			    	beneForm.getDatiBeneImmobileGest().setMensilePattuito(in.getMensilePattuito().getValue());
			    if (in.getMensileVersato() != null)
			    	beneForm.getDatiBeneImmobileGest().setMensileVersato(in.getMensileVersato().getValue());
			    if (in.getRegolaritaPagamenti() != null)
			    	beneForm.getDatiBeneImmobileGest().setRegolaritaPagamenti(in.getRegolaritaPagamenti().getValue());
			    if (in.getDescrizionePagamenti() != null)
			    	beneForm.getDatiBeneImmobileGest().setDescrizionePagamenti(in.getDescrizionePagamenti().getValue());
			    if (in.getImportoDovuto() != null)
			    	beneForm.getDatiBeneImmobileGest().setImportoDovuto(in.getImportoDovuto().getValue());
			    if (in.getImportoDovutoDal() != null)
			    	beneForm.getDatiBeneImmobileGest().setImportoDovutoDal(in.getImportoDovutoDal().getValue());
			    if (in.getImportoDovutoAl() != null)
			    	beneForm.getDatiBeneImmobileGest().setImportoDovutoAl(in.getImportoDovutoAl().getValue());
			    if (in.getCondominioArretrato() != null)
			    	beneForm.getDatiBeneImmobileGest().setCondominioArretrato(in.getCondominioArretrato().getValue());
			    if (in.getCondominioArretratoDal() != null)
			    	beneForm.getDatiBeneImmobileGest().setCondominioArretratoDal(in.getCondominioArretratoDal().getValue());
			    if (in.getCondominioArretratoAl() != null)
			    	beneForm.getDatiBeneImmobileGest().setCondominioArretratoAl(in.getCondominioArretratoAl().getValue());
			    if (in.getCondominioInScadenza() != null)
			    	beneForm.getDatiBeneImmobileGest().setCondominioInScadenza(in.getCondominioInScadenza().getValue());
			    if (in.getDescTipoOccupanti() != null)
			    	beneForm.getDatiBeneImmobileGest().setDescTipoOccupanti(in.getDescTipoOccupanti().getValue());
			    if (in.getPresenzaServitu() != null)
			    	beneForm.getDatiBeneImmobileGest().setPresenzaServitu(in.getPresenzaServitu().getValue());
			    if (in.getUsiCivici() != null)
			    	beneForm.getDatiBeneImmobileGest().setUsiCivici(in.getUsiCivici().getValue());
			    if (in.getAltriVincoli() != null)
			    	beneForm.getDatiBeneImmobileGest().setAltriVincoli(in.getAltriVincoli().getValue());
			}
		}
		if (bene.getDatiSpecificiBene().getVeicolo() != null) {
	    	beneForm.setDatiBeneVeicoloGest( new DatiBeneVeicoloGest());
			DatiVeicoloGestType in = bene.getDatiSpecificiBene().getVeicolo().getDatiVeicoloGest();  
			
			if (in != null) {
				//mapperBean.map(in, beneForm.getDatiBeneVeicoloGest());
				beneForm.getDatiBeneVeicoloGest().setStima(in.getStima());
				beneForm.getDatiBeneVeicoloGest().setDataStima(in.getDataStima());
				if (in.getDirittiTerzi() != null)
					beneForm.getDatiBeneVeicoloGest().setDirittiTerzi(in.getDirittiTerzi().getValue());
				if (in.getDescDirittiTerzi() != null)
					beneForm.getDatiBeneVeicoloGest().setDescDirittiTerzi(in.getDescDirittiTerzi());
				if (in.getBeneRinvenuto() != null)
					beneForm.getDatiBeneVeicoloGest().setBeneRinvenuto(in.getBeneRinvenuto().getValue());
				if (in.getDataPossesso() != null)
					beneForm.getDatiBeneVeicoloGest().setDataPossesso(in.getDataPossesso());
				if (in.getDataPerditaPossesso() != null)
					beneForm.getDatiBeneVeicoloGest().setDataPerditaPossesso(in.getDataPerditaPossesso());
				if (in.getDescStatoManutenzione() != null)
					beneForm.getDatiBeneVeicoloGest().setDescStatoManutenzione(in.getDescStatoManutenzione());
				if (in.getTrascrizioneSequestro() != null)
					beneForm.getDatiBeneVeicoloGest().setTrascrizioneSequestro(in.getTrascrizioneSequestro().getValue());
				if (in.getDataTrascrizioneSequestro() != null)
					beneForm.getDatiBeneVeicoloGest().setDataTrascrizioneSequestro(in.getDataTrascrizioneSequestro());
				if (in.getDescCustode() != null)
					beneForm.getDatiBeneVeicoloGest().setDescCustode(in.getDescCustode());
				if (in.getOneriCustodia() != null)
					beneForm.getDatiBeneVeicoloGest().setOneriCustodia(in.getOneriCustodia().getValue());
				if (in.getValoreOneriCustodia() != null)
					beneForm.getDatiBeneVeicoloGest().setValoreOneriCustodia(in.getValoreOneriCustodia());
				if (in.getDataOneriCustodia() != null)
					beneForm.getDatiBeneVeicoloGest().setDataOneriCustodia(in.getDataOneriCustodia());
				if (in.getDescAltriVincoli() != null)
					beneForm.getDatiBeneVeicoloGest().setDescAltriVincoli(in.getDescAltriVincoli());
			}
		}

		if (bene.getDatiSpecificiBene().getImmateriale() != null) {
			beneForm.setDatiBeneImmaterialeGest(new DatiBeneImmaterialeGest());
			DatiImmaterialeGestType in = bene.getDatiSpecificiBene().getImmateriale().getDatiImmaterialeGest();
			if (in != null) {
				mapperBean.map(in, beneForm.getDatiBeneImmaterialeGest());
				beneForm.getDatiBeneImmaterialeGest().setDirittiTerzi(in.isDirittiTerzi());
				beneForm.getDatiBeneImmaterialeGest().setTrascrizioneSequestro(in.isTrascrizioneSequestro());
			}
		}
		if (bene.getDatiSpecificiBene().getCollezioni() != null) {
			beneForm.setDatiBeneMobileGest(new DatiBeneMobileGest());
			DatiMobileGestType in = bene.getDatiSpecificiBene().getCollezioni().getDatiMobileGest();
			if (in != null) {
				mapperBean.map(in, beneForm.getDatiBeneMobileGest());
				beneForm.getDatiBeneMobileGest().setBeneRinvenuto(in.isBeneRinvenuto());
				beneForm.getDatiBeneMobileGest().setDirittiTerzi(in.isDirittiTerzi());
				beneForm.getDatiBeneMobileGest().setOneriCustodia(in.isOneriCustodia());
			}
		}
	
		beneForm.setDatiSpecificiBene(bene.getDatiSpecificiBene());
		
	}
	
}
