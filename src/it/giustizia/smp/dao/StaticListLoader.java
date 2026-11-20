package it.giustizia.smp.dao;

import it.giustizia.smp.form.ComboForm;
import it.giustizia.smp.service.CommonService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;

@Controller
public class StaticListLoader {

	private static List<ComboForm>	categorieCatastali;
    @Autowired
	private static CommonService	commonService;
	private static List<ComboForm>	condizioniGiuridiche;
	private static List<ComboForm>	disponibilita;
	private static List<ComboForm>	istitutiDetenzione;
	private static List<ComboForm>  categorieAtti;
	private static List<ComboForm>	minuti;
	private static List<ComboForm>	ore;
	private static List<ComboForm>	professioni;
	private static List<ComboForm>	province;
	private static List<ComboForm>	allProvince;
	
	private static List<ComboForm>	settoriAttivita;
	private static List<ComboForm>	stati;
	private static List<ComboForm>	statiPiva;
	private static List<ComboForm>	statiProcedimento;
	private static List<ComboForm>	tipiNota;
	private static List<ComboForm>	tipiAtto;
	private static List<ComboForm>	tipiBene;
	private static List<ComboForm>	tipiEsitoUdienza;
	private static List<ComboForm>	tipiFlgPresenza;
	private static List<ComboForm>	tipiInteresse;
	private static List<ComboForm>	tipiMisura;
	private static List<ComboForm>	tipiMisuraTipoT;
	private static List<ComboForm>	tipiMisuraTipoAP;
	private static List<ComboForm>	tipiPrevenzione;
	private static List<ComboForm>	tipiProcedimento;
	private static List<ComboForm>	tipiSocieta;
	private static List<ComboForm>	tipiSostituzioneMagistrato;
	private static List<ComboForm>	tipiStrada;
	private static List<ComboForm>	tipiTenore;
	private static List<ComboForm>  tipiValuta;
	private static List<ComboForm>	vincoli;
	private static List<ComboForm>  tipoCatasto;
	private static List<ComboForm>  tipoFondo;
	private static List<ComboForm>  tipoDenuncia;
	private static List<ComboForm>  tipoOccupanti;
	private static List<ComboForm>  tipoRuoliMagistrati;
	private static List<ComboForm>  tipoModalitaPervenimento;
	
	private static String softwareVersion;  
	private static String softwareVersion_db;   // nov2019
    private static ReloadableResourceBundleMessageSource configParameterSource;	

    public static List<ComboForm> getCategorieCatastali() {

		if (StaticListLoader.categorieCatastali == null) {
			StaticListLoader.categorieCatastali = StaticListLoader.commonService.getCategorieCatastali();
		}
		return StaticListLoader.categorieCatastali;
	}

    public static List<ComboForm> getCondizioniGiuridiche() {

		if (StaticListLoader.condizioniGiuridiche == null) {
			StaticListLoader.condizioniGiuridiche = StaticListLoader.commonService.getAllCondizioneGiuridica();
		}
		return StaticListLoader.condizioniGiuridiche;
	}

    public static List<ComboForm> getDisponibilita() {

		if (StaticListLoader.disponibilita == null) {
			StaticListLoader.disponibilita = StaticListLoader.commonService.getDisponibilita();
		}
		return StaticListLoader.disponibilita;
	}

    public static List<ComboForm> getIstitutiDetenzione() {

		if (StaticListLoader.istitutiDetenzione == null) {
			StaticListLoader.istitutiDetenzione = StaticListLoader.commonService.getAllIstitutiDetenzione();
		}
		return StaticListLoader.istitutiDetenzione;
	}

	public synchronized static List<ComboForm> getMinuti() {

		if (StaticListLoader.minuti == null) {
			StaticListLoader.minuti = StaticListLoader.commonService.getAllMinuti();
		}
		return StaticListLoader.minuti;
	}

	public synchronized static List<ComboForm> getOre() {

		if (StaticListLoader.ore == null) {
			StaticListLoader.ore = StaticListLoader.commonService.getAllOra();
		}
		return StaticListLoader.ore;
	}

    public static List<ComboForm> getProfessioni() {

		if (StaticListLoader.professioni == null) {
			StaticListLoader.professioni = StaticListLoader.commonService.getAllProfessione();
		}
		return StaticListLoader.professioni;
	}

    public static List<ComboForm> getProvince() {

		if (StaticListLoader.province == null) {
			StaticListLoader.province = StaticListLoader.commonService.getAllProvincia();
		}
		return StaticListLoader.province;
	}
    
    public static List<ComboForm> getAllProvince() {

		if (StaticListLoader.allProvince == null) {
			StaticListLoader.allProvince  = StaticListLoader.commonService.getAllProvincia();
		}
		return StaticListLoader.allProvince ;
	}
	
    public static List<ComboForm> getProvince(long idOrgano) {
		
		StaticListLoader.province = StaticListLoader.commonService.getProvinciaByOrgano(idOrgano);

		return StaticListLoader.province;

	}

    public static List<ComboForm> getSettoriAttivita() {

		if (StaticListLoader.settoriAttivita == null) {
			StaticListLoader.settoriAttivita = StaticListLoader.commonService.listaSettoriAttivita();
		}
		return StaticListLoader.settoriAttivita;
	}

    public static List<ComboForm> getStati() {

		if (StaticListLoader.stati == null) {
			StaticListLoader.stati = StaticListLoader.commonService.getAllStato();
		}
		return StaticListLoader.stati;
	}
    
    public static List<ComboForm> getStatiPiva() {

		if (StaticListLoader.statiPiva == null) {
			StaticListLoader.statiPiva = StaticListLoader.commonService.getAllStatoPiva();
		}
		return StaticListLoader.statiPiva;
	}

    public static List<ComboForm> getStatiProcedimento() {

		if (StaticListLoader.statiProcedimento == null) {
			StaticListLoader.statiProcedimento = StaticListLoader.commonService.listaStatiDelProcedimento();
		}
		return StaticListLoader.statiProcedimento;
	}

    public static List<ComboForm> getTipiNota() {

		if (StaticListLoader.tipiNota == null) {
			StaticListLoader.tipiNota = StaticListLoader.commonService.getTipoNota();
		}
		return StaticListLoader.tipiNota;
	}

    public static List<ComboForm> getTipiAtto() {

		if (StaticListLoader.tipiAtto == null) {
			StaticListLoader.tipiAtto = StaticListLoader.commonService.getTipoAtto();
		}
		return StaticListLoader.tipiAtto;
	}

    public static List<ComboForm> getTipiBene() {

		if (StaticListLoader.tipiBene == null) {
			StaticListLoader.tipiBene = StaticListLoader.commonService.listaDellaTipologiaDelBene();
		}
		return StaticListLoader.tipiBene;
	}

    public static List<ComboForm> getTipiEsitoUdienza() {

		if (StaticListLoader.tipiEsitoUdienza == null) {
			StaticListLoader.tipiEsitoUdienza = StaticListLoader.commonService.getTipiEsitoUdienza();
		}
		return StaticListLoader.tipiEsitoUdienza;
	}

    public static List<ComboForm> getTipiFlgPresenza() {

		if (StaticListLoader.tipiFlgPresenza == null) {
			StaticListLoader.tipiFlgPresenza = StaticListLoader.commonService.getFlgPresenza();
		}
		return StaticListLoader.tipiFlgPresenza;
	}

    public static List<ComboForm> getTipiInteresse() {

		if (StaticListLoader.tipiInteresse == null) {
			StaticListLoader.tipiInteresse = StaticListLoader.commonService.listaTipiInteresse();
		}
		return StaticListLoader.tipiInteresse;
	}

    public static List<ComboForm> getTipiMisuraTipoP() {

		if (StaticListLoader.tipiMisura == null) {
			StaticListLoader.tipiMisura = StaticListLoader.commonService.listaTipiMisuraTipoP();
		}
		return StaticListLoader.tipiMisura;
	}

    public static List<ComboForm> getTipiMisuraTipoT() {

		if (StaticListLoader.tipiMisuraTipoT == null) {
			StaticListLoader.tipiMisuraTipoT = StaticListLoader.commonService.listaTipiMisuraTipoT();
		}
		return StaticListLoader.tipiMisuraTipoT;
	}
    
    public static List<ComboForm> getTipiMisuraTipoAP() {

		if (StaticListLoader.tipiMisuraTipoAP == null) {
			StaticListLoader.tipiMisuraTipoAP = StaticListLoader.commonService.listaTipiMisuraTipoAP();
		}
		return StaticListLoader.tipiMisuraTipoAP;
	}

    public static List<ComboForm> getTipiPrevenzione() {

		if (StaticListLoader.tipiPrevenzione == null) {
			StaticListLoader.tipiPrevenzione = StaticListLoader.commonService.listaTipiPrevenzione();
		}
		return StaticListLoader.tipiPrevenzione;
	}

    public static List<ComboForm> getTipiProcedimento() {

		if (StaticListLoader.tipiProcedimento == null) {
			StaticListLoader.tipiProcedimento = StaticListLoader.commonService.listaTipiDiProcedimento(0);
		}
		return StaticListLoader.tipiProcedimento;
	}

    public static List<ComboForm> getTipiSocieta() {

		if (StaticListLoader.tipiSocieta == null) {
			StaticListLoader.tipiSocieta = StaticListLoader.commonService.getAllTipiSocieta();
		}
		return StaticListLoader.tipiSocieta;
	}

    public static Object getTipiSostituzioneMagistrato() {

		if (StaticListLoader.tipiSostituzioneMagistrato == null) {
			StaticListLoader.tipiSostituzioneMagistrato = StaticListLoader.commonService.getTipiSostituzioneMagistrato();
		}
		return StaticListLoader.tipiSostituzioneMagistrato;
	}

    public static List<ComboForm> getTipiStrada() {

		if (StaticListLoader.tipiStrada == null) {
			StaticListLoader.tipiStrada = StaticListLoader.commonService.listaTipiStrada();
		}
		return StaticListLoader.tipiStrada;
	}

    public static List<ComboForm> getTipiTenore() {

		if (StaticListLoader.tipiTenore == null) {
			StaticListLoader.tipiTenore = StaticListLoader.commonService.listaTipiTenore();
		}
		return StaticListLoader.tipiTenore;
	}

    public synchronized static List<ComboForm> getTipiValuta() {
		
		if(tipiValuta == null){
		    tipiValuta = commonService.listaTipiValuta();
		}
		return tipiValuta;
	}

    public static List<ComboForm> getVincoli() {

		if (StaticListLoader.vincoli == null) {
			StaticListLoader.vincoli = StaticListLoader.commonService.getVincoli();
		}
		return StaticListLoader.vincoli;
	}
	
	public static void setCommonService(CommonService commonService) {

		StaticListLoader.commonService = commonService;
	}
	
	
    /**
     * @param configParameterSource the configParameterSource to set
     */
    public static void setConfigParameterSource(ReloadableResourceBundleMessageSource configParameterSource) {
    
        StaticListLoader.configParameterSource = configParameterSource;
    }
    
	public static String getSoftwareVersion() {
	    if( softwareVersion == null ){
	        softwareVersion = configParameterSource.getMessage("SOFTWARE_VER", null, null);
	    }
        return softwareVersion;
	}
	
	public static void setSoftwareVersion(String softwareVersionDb) {
		softwareVersion = softwareVersionDb;
    }

	
	public static List<ComboForm> getTipoCatasto() {

		if (StaticListLoader.tipoCatasto == null) {
			StaticListLoader.tipoCatasto = StaticListLoader.commonService.listaTipoCatasto();
		}
		return StaticListLoader.tipoCatasto;
	}
	
	public static List<ComboForm> getTipoFondo() {

		if (StaticListLoader.tipoFondo == null) {
			StaticListLoader.tipoFondo = StaticListLoader.commonService.listaTipoFondo();
		}
		return StaticListLoader.tipoFondo;
	}
	
	public static List<ComboForm> getTipoDenuncia() {

		if (StaticListLoader.tipoDenuncia == null) {
			StaticListLoader.tipoDenuncia = StaticListLoader.commonService.listaTipoDenuncia();
		}
		return StaticListLoader.tipoDenuncia;
	}
	
	public static List<ComboForm> getTipoOccupanti() {

		if (StaticListLoader.tipoOccupanti == null) {
			StaticListLoader.tipoOccupanti = StaticListLoader.commonService.listaTipoOccupanti();
		}
		return StaticListLoader.tipoOccupanti;
	}
	
	 public static List<ComboForm> getTipoRuoliMagistrati() {

			if (StaticListLoader.tipoRuoliMagistrati == null) {
				StaticListLoader.tipoRuoliMagistrati = StaticListLoader.commonService.getAllTipoRuoliMagistratiCollegio();
			}
			return StaticListLoader.tipoRuoliMagistrati;
		}

	 public static List<ComboForm> getTipoModalitaPervenimento() {

			if (StaticListLoader.tipoModalitaPervenimento == null) {
				StaticListLoader.tipoModalitaPervenimento = StaticListLoader.commonService.getAllTipoModalitaPervenimento();
			}
			return StaticListLoader.tipoModalitaPervenimento;
		}

	
}
