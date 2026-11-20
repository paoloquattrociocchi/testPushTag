package it.giustizia.smp.xml;

import it.giustizia.smp.utility.Constants;
import it.giustizia.smp.utility.DateUtils;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.utility.JAXBUtil;
import it.giustizia.smp.xml.amministratori.AltriOggettiType;
import it.giustizia.smp.xml.amministratori.AmministrazioneType;
import it.giustizia.smp.xml.amministratori.AnimaliType;
import it.giustizia.smp.xml.amministratori.AziendaType;
import it.giustizia.smp.xml.amministratori.BeneType;
import it.giustizia.smp.xml.amministratori.BeneType.DatiSpecificiBene;
import it.giustizia.smp.xml.amministratori.CategoriaBeneType;
import it.giustizia.smp.xml.amministratori.CategoriaCatastaleType;
import it.giustizia.smp.xml.amministratori.CollezioniType;
import it.giustizia.smp.xml.amministratori.DecisioneType;
import it.giustizia.smp.xml.amministratori.FinanziarioType;
import it.giustizia.smp.xml.amministratori.ImmaterialeType;
import it.giustizia.smp.xml.amministratori.ImmobileType;
import it.giustizia.smp.xml.amministratori.MisuraType;
import it.giustizia.smp.xml.amministratori.PersonaFisicaType;
import it.giustizia.smp.xml.amministratori.PersonaGiuridicaType;
import it.giustizia.smp.xml.amministratori.ProvvedimentoTrasmissioneType;
import it.giustizia.smp.xml.amministratori.SettoreAttivitaType;
import it.giustizia.smp.xml.amministratori.SoggettoType;
import it.giustizia.smp.xml.amministratori.SottoCategoriaBeneType;
import it.giustizia.smp.xml.amministratori.TipoBeneType;
import it.giustizia.smp.xml.amministratori.TipoCatastoType;
import it.giustizia.smp.xml.amministratori.TipoFinanziarioType;
import it.giustizia.smp.xml.amministratori.TipoSocietaType;
import it.giustizia.smp.xml.amministratori.TipoValutaType;
import it.giustizia.smp.xml.amministratori.TrasmissioneRelazione;
import it.giustizia.smp.xml.amministratori.UbicazioneType;
import it.giustizia.smp.xml.amministratori.VeicoloType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class TrasmissioneRelazioneAmm extends RelazioneAmm {

	private Logger logger = Logger.getLogger(getClass());
	private static String NON_CONFORME = " non conforme alle specifiche";
	private static int ABI_CAB_LEN = 5;
	private static int NUMCC_LEN = 12;

	private ProvvedimentoT provvedimento = new ProvvedimentoT();
	private Collection<? extends BeneT> beni = new ArrayList<BeneT>();
	private Collection<? extends Persona> soggetti = new ArrayList<Persona>();
	private Collection<? extends Misura> misure = new ArrayList<Misura>();
	private Collection<? extends Amministrazione> amministrazioni = null;
	
	public ProvvedimentoT getProvvedimento() {
		return provvedimento;
	}

	public void setProvvedimento(ProvvedimentoT provvedimento) {
		this.provvedimento = (provvedimento != null) ? provvedimento : getProvvedimento();
	}

	public Collection<? extends BeneT> getBeni() {
		return beni;
	}

	public void setBeni(Collection<? extends BeneT> beni) {
		this.beni = (beni != null) ? beni : getBeni();
	}

	public Collection<? extends Persona> getSoggetti() {
		return soggetti;
	}

	public void setSoggetti(Collection<? extends Persona> soggetti) {
		this.soggetti = (soggetti != null) ? soggetti : getSoggetti();
	}

	public Collection<? extends Misura> getMisure() {
		return misure;
	}

	public void setMisure(Collection<? extends Misura> misure) {
		this.misure = (misure != null) ? misure : getMisure();;
	}

	public Collection<? extends Amministrazione> getAmministrazioni() {
		return amministrazioni;
	}

	public void setAmministrazioni(Collection<? extends Amministrazione> amministrazioni) {
		this.amministrazioni = amministrazioni;
	}

	private CategoriaBeneType getCategoria(String in){
		CategoriaBeneType out = null;
		if (StringUtils.isNotEmpty(in)) {
			Collection<? extends CategoriaBeneType> list = Arrays.asList(CategoriaBeneType.values());
			for (CategoriaBeneType el : list) {
				if (el.value().equals(in)) {
					out = el;
					break;
				}
			}
		}
		return out;
	}

	private CategoriaCatastaleType getCategoriaCatastale(String in){
		CategoriaCatastaleType out = null;
		if (StringUtils.isNotEmpty(in)) {
			Collection<? extends CategoriaCatastaleType> list = Arrays.asList(CategoriaCatastaleType.values());
			for (CategoriaCatastaleType el : list) {
				if (el.value().equals(in)) {
					out = el;
					break;
				}
			}
		}
		return out;
	}

	private SottoCategoriaBeneType getSottoCategoria(String in){
		SottoCategoriaBeneType out = null;
		if (StringUtils.isNotEmpty(in)) {
			Collection<? extends SottoCategoriaBeneType> list = Arrays.asList(SottoCategoriaBeneType.values());
			for (SottoCategoriaBeneType el : list) {
				if (el.value().equals(in)) {
					out = el;
					break;
				}
			}
		}
		return out;
	}

	private TipoValutaType getTipoValuta(String in){
		TipoValutaType out = null;
		if (StringUtils.isNotEmpty(in)) {
			Collection<? extends TipoValutaType> list = Arrays.asList(TipoValutaType.values());
			for (TipoValutaType el : list) {
				if (el.value().equals(in)) {
					out = el;
					break;
				}
			}
		}
		return out;
	}

	private TipoFinanziarioType getTipoFinanziario(String in){
		TipoFinanziarioType out = null;
		if (StringUtils.isNotEmpty(in)) {
			Collection<? extends TipoFinanziarioType> list = Arrays.asList(TipoFinanziarioType.values());
			for (TipoFinanziarioType el : list) {
				if (el.value().equals(in)) {
					out = el;
					break;
				}
			}
		}
		return out;
	}

	
	private TipoSocietaType getTipoSocieta(String in){
		TipoSocietaType out = null;
		if (StringUtils.isNotEmpty(in)) {
			Collection<? extends TipoSocietaType> list = Arrays.asList(TipoSocietaType.values());
			for (TipoSocietaType el : list) {
				if (el.value().equals(in)) {
					out = el;
					break;
				}
			}
		}
		return out;
	}

	private DecisioneType getTipoDecisione(String in) {
		DecisioneType out = null;
		if (StringUtils.isNotEmpty(in)) {
			Collection<? extends DecisioneType> list = Arrays.asList(DecisioneType.values());
			for (DecisioneType el : list) {
				if (el.value().equals(in)) {
					out = el;
					break;
				}
			}
		}
		return out;
	}

	private TipoCatastoType getTipoCatasto(String in) {
		TipoCatastoType out = null;
		if (StringUtils.isNotEmpty(in)) {
			Collection<? extends TipoCatastoType> list = Arrays.asList(TipoCatastoType.values());
			for (TipoCatastoType el : list) {
				if (el.value().equals(in)) {
					out = el;
					break;
				}
			}
		}
		return out;
	}

	private TipoBeneType getTipologia(String in) {
		TipoBeneType out = null;
		if (StringUtils.isNotEmpty(in)) {
			Collection<? extends TipoBeneType> list = Arrays.asList(TipoBeneType.values());
			for (TipoBeneType el : list) {
				if (el.value().equals(in)) {
					out = el;
					break;
				}
			}
		}
		return out;
	}

	private SettoreAttivitaType getSettoreAttivita(String in) {
		SettoreAttivitaType out = null;
		if (StringUtils.isNotEmpty(in)) {
			Collection<? extends SettoreAttivitaType> list = Arrays.asList(SettoreAttivitaType.values());
			for (SettoreAttivitaType el : list) {
				if (el.value().equals(in)) {
					out = el;
					break;
				}
			}
		}
		return out;
	}

	private UbicazioneType getUbicazione(Ubicazione in) {
		UbicazioneType out = null;
		if ((in != null) && (in.getIdStato() != null) &&
				(!StringUtils.isEmpty(in.getIdComuneItaliano()) || !StringUtils.isEmpty(in.getDescrizioneComuneEstero()))) {
			out = new UbicazioneType();
			out.setIdStato(in.getIdStato());
			if (!StringUtils.isEmpty(in.getIdComuneItaliano()))
				out.setIdComuneItaliano(in.getIdComuneItaliano());
			else
				out.setDescrizioneComuneEstero(in.getDescrizioneComuneEstero());
			
			out.setIndirizzo(in.getIndirizzo());
			out.setCap(in.getCap());
			out.setCivico(in.getCivico());
			out.setTipoStrada(in.getTipoStrada());
		}
		return out;
	}

	private Collection<? extends AmministrazioneType> getAmministrazioniT() {
		Collection<AmministrazioneType> out = new ArrayList<AmministrazioneType>();
		if (Utility.isValid(getAmministrazioni())) {
			for (Amministrazione el : getAmministrazioni()) {
				AmministrazioneType amministrazione = new AmministrazioneType();
				amministrazione.setCodiceFiscale(el.getCodiceFiscale());
				amministrazione.getIdentificativoBene().addAll(el.getIdentificativoBene());
				amministrazione.setDataInizioIncarico(DateUtils.convert(el.getDataInizioIncarico()));
				amministrazione.setDataFineIncarico(DateUtils.convert(el.getDataFineIncarico()));
				out.add(amministrazione);
			}
		}
		return out;
	}

	private Collection<? extends MisuraType> getMisureT() {
		Collection<MisuraType> out = new ArrayList<MisuraType>();
		if (Utility.isValid(getMisure())) {
			for (Misura el : getMisure()) {
				MisuraType misura = new MisuraType();
				misura.setIdentificativoBene(el.getIdentificativoBene());
				misura.setTipoInteresse(el.getTipoInteresse());
				misura.setIdentificativoSoggettoTitolare(el.getIdentificativoSoggettoTitolare());
				misura.setQuota(el.getQuota());
				misura.getIdentificativoSoggettoProposto().addAll(el.getIdentificativoSoggettoProposto());
				misura.setIdentificativoNormativa(el.getIdentificativoNormativa());
				misura.setDecisione(getTipoDecisione(el.getDecisione()));
				out.add(misura);
			}
		}
		return out;
	}

	private Collection<? extends SoggettoType> getSoggettiT() {
		Collection<SoggettoType> out = new ArrayList<SoggettoType>();
		if (Utility.isValid(soggetti)) {
			for (Persona el : getSoggetti()) {
				SoggettoType soggetto = new SoggettoType();
				soggetto.setIdentificativo(el.getIdentificativo());
				if (el instanceof PersonaFisica) {
					PersonaFisicaType persona = new PersonaFisicaType();
					persona.setCognome(((PersonaFisica) el).getCodiceFiscale());
					persona.setNome(((PersonaFisica) el).getNome());
					persona.setSesso(((PersonaFisica) el).getSesso());
					persona.setDataNascita(DateUtils.convert(((PersonaFisica) el).getDataNascita()));
					UbicazioneType ubi = getUbicazione(((PersonaFisica) el).getLuogoNascita());
					if (ubi != null) persona.setLuogoNascita(ubi);
					persona.setCodiceFiscale(((PersonaFisica) el).getCodiceFiscale());
					persona.setCui(((PersonaFisica) el).getCui());
					soggetto.setPersonaFisica(persona);
				} else if (el instanceof PersonaGiuridica) {
					PersonaGiuridicaType persona = new PersonaGiuridicaType();
					persona.setDenominazione(((PersonaGiuridica) el).getDenominazione());
					persona.setPartitaIVA(((PersonaGiuridica) el).getPartitaIVA());
					persona.setTipologia(getTipoSocieta(((PersonaGiuridica) el).getTipologia()));
					persona.setSedeLegale(getSedeLegale(((PersonaGiuridica) el).getSedeLegale()));
					soggetto.setPersonaGiuridica(persona);
				}
				out.add(soggetto);
			}
		}
		return out;
	}

	private PersonaGiuridicaType.SedeLegale getSedeLegale(SedeLegale in) {
		PersonaGiuridicaType.SedeLegale out = null;
		if (in != null) {
			out = new PersonaGiuridicaType.SedeLegale();
			out.setIndirizzoSedeLegale(in.getIndirizzo());
			UbicazioneType ubi = getUbicazione(in.getUbicazione());
			if (ubi != null) out.setUbicazione(ubi);
		}
		return out;
	}

	private Collection<? extends BeneType> getBeniT() {
		Collection<BeneType> out = new ArrayList<BeneType>();
		for (BeneT el : getBeni()) {
			BeneType b = new BeneType();
			b.setIdentificativoBene(el.getId());
			b.setTipologia(getTipologia(el.getTipo()));
			b.setCategoria(getCategoria(el.getCategoria()));
			b.setSottoCategoria(getSottoCategoria(el.getSottocategoria()));
			UbicazioneType ubi = getUbicazione(el.getUbicazione());
			if (ubi != null) b.setUbicazione(ubi);
			b.setDatiSpecificiBene(getDatiSpecificiBene(el));
			b.setGravatoDaPegnoIpoteca(el.isGravatoDaPegnoIpoteca());
			b.setDescBene(el.getDescBene());
			out.add(b);
		}
		return out;
	}

	private BeneType.DatiSpecificiBene getDatiSpecificiBene(BeneT in) {
		BeneType.DatiSpecificiBene out = new DatiSpecificiBene();
		if (in != null) {
			if (in.getAltriOggettiDescr() != null) out.setAltriOggetti(getAltriOggetti(in.getAltriOggettiDescr()));
			else if (in.getAnimali() != null) out.setAnimali(getAnimaliType(in.getAnimali()));
			else if (in.getAzienda() != null) out.setAzienda(getAziendaType(in.getAzienda()));
			else if (in.getFinanziario() != null) out.setFinanziario(getFinanziarioType(in.getFinanziario()));
			else if (in.getImmateriale() != null) out.setImmateriale(getImmateriale(in.getImmateriale()));
			else if (in.getImmobile() != null) out.setImmobile(getImmobileType(in.getImmobile()));
			else if (in.getCollezioni() != null) out.setCollezioni(getCollezioniType(in.getCollezioni()));
			else if (in.getVeicolo() != null) out.setVeicolo(getVeicoloType(in.getVeicolo()));
		}
		return out;
	}

	private AltriOggettiType getAltriOggetti(String in) {
		AltriOggettiType out = new AltriOggettiType();
		out.setDescrizione(in);
		return out;
	}

	private VeicoloType getVeicoloType(Veicolo in) {
		VeicoloType out = new VeicoloType();
		if (in != null) {
			out.setNumeroRegistro(in.getNumeroRegistro());
			out.setDataRegistrazione(DateUtils.convert(in.getDataRegistrazione()));
			out.setNumeroIdentificativo(in.getNumeroIdentificativo());
			out.setCodiceImmatricolazione(in.getCodiceImmatricolazione());
			out.setDescrizioneModello(in.getDescrizioneModello());
			out.setNumeroTarga(in.getNumeroTarga());
			out.setNumeroTelaio(in.getNumeroTelaio());
			out.setDescrizioneColore(in.getDescrizioneColore());
		}
		return out;
	}

	private ImmobileType getImmobileType(Immobile in) {
		ImmobileType out = new ImmobileType();
		if (in != null) {
			out.setEdificio(in.getEdificio());
			out.setScala(in.getScala());
			out.setPiano(in.getPiano());
			out.setInterno(in.getInterno());
			out.setVolumeInt(in.getVolumeInt());
			out.setVolumeDec(in.getVolumeDec());
			out.setVaniInt(in.getVaniInt());
			out.setVaniDec(in.getVaniDec());
			out.setLotto(in.getLotto());
			out.setSuperficieInt(in.getSuperficieInt());
			out.setSuperficieDec(in.getSuperficieDec());
			out.setEttari(in.getEttari());
			out.setCentiare(in.getCentiare());
			out.setDatiCatastali(getDatiCatastali(in));
			out.setNumeroDomandaAccatastamento(in.getNumeroDomandaAccatastamento());
			out.setDataDomandaAccatastamento(DateUtils.convert(in.getDataDomandaAccatastamento()));
			out.setAppartenenzaaFabbricatoConfiscato(in.getAppartenenzaaFabbricatoConfiscato());
		}
		return out;
	}

	private ImmobileType.DatiCatastali getDatiCatastali(Immobile in) {
		ImmobileType.DatiCatastali out = new ImmobileType.DatiCatastali();
		if (in != null) {
			out.setTipoCatasto(getTipoCatasto(in.getTipoCatasto()));
			out.setIdentificativoComuneCatasto(in.getIdentificativoComuneCatasto());
			out.setSezioneCatasto(in.getSezioneCatasto());
			out.setFoglioCatasto(in.getFoglioCatasto());
			out.setParticellaCatasto1(in.getParticellaCatasto1());
			out.setParticellaCatasto2(in.getParticellaCatasto2());
			out.setSubparticellaCatasto1(in.getSubparticellaCatasto1());
			out.setSubparticellaCatasto2(in.getSubparticellaCatasto2());
			out.setIdentificativoCategoriaCatastale(getCategoriaCatastale(in.getIdentificativoCategoriaCatastale()));
			out.setImportoRenditaCatastale(in.getImportoRenditaCatastale());
			out.setImportoRenditaDominicale(in.getImportoRenditaDominicale());
		}
		return out;
	}

	private ImmaterialeType getImmateriale(Immateriale in) {
		ImmaterialeType out = new ImmaterialeType();
		if (in != null) {
			out.setNumeroRegistrazione(in.getNumeroRegistrazione());
			out.setDataRegistrazione(DateUtils.convert(in.getDataRegistrazione()));
		}
		return out;
	}

	private BeneType.DatiSpecificiBene.Finanziario getFinanziarioType(Finanziario in) {
		BeneType.DatiSpecificiBene.Finanziario out = new BeneType.DatiSpecificiBene.Finanziario();
		if (in != null) {
			out.setTipoFinanziario(getTipoFinanziario(in.getTipo()));
			out.setDescEnte(in.getDescEnte());
			out.setFilialeModulo(in.getFilialeModulo());
			out.setEnte(getEnte(in.getSwift(), in.getIban(), in.getAbi(), in.getCab(), in.getNnumerocc()));
			if (out.getEnte() == null)
				out.setAssicurazione(getAssicurazione(in.getCodiceFiscale(), in.getNumeroPolizza()));
			out.setTipoValuta(getTipoValuta(in.getTipoValuta()));
			out.setImportoValuta(in.getImportoValuta());
			out.setImportoEuro(in.getImportoEuro());
			out.setIdent(in.getIdent());
		}
		return out;
	}

	private FinanziarioType.Assicurazione getAssicurazione(String codFisc, String numPolizza) {
		FinanziarioType.Assicurazione out = null;
		if (StringUtils.isNotEmpty(codFisc) || StringUtils.isNotEmpty(numPolizza)) {
			out = new FinanziarioType.Assicurazione();
			out.setCodFiscaleEnte(StringUtils.defaultString(codFisc));
			out.setNumeroPolizza(StringUtils.defaultString(numPolizza));
		}
		return out;
	}

	private FinanziarioType.Ente getEnte(String swift, String iban, String abi, String cab, String numCC) {
		FinanziarioType.Ente out = null;
		if (StringUtils.isNotEmpty(swift) || StringUtils.isNotEmpty(iban) || StringUtils.isNotEmpty(abi) || StringUtils.isNotEmpty(cab) || StringUtils.isNotEmpty(numCC)) {
			out = new FinanziarioType.Ente();
			if (StringUtils.isNotEmpty(swift))
				out.setSwift(swift);
			out.setIban(iban);
			out.setAbi(checkAbiCab(abi, "ABI"));
			out.setCab(checkAbiCab(cab, "CAB"));
			out.setNumeroContoCorrente(checkNummCC(numCC));
		}
		return out;
	}

	private String checkNummCC(String in) {
		return (!StringUtils.isEmpty(in)) ? StringUtils.leftPad(StringUtils.defaultString(in).trim(), NUMCC_LEN, '0') : null;
	}

	private String checkAbiCab(String in, String cod) {
		String out = null;
		if (!StringUtils.isEmpty(in)) {
			String codice = StringUtils.leftPad(StringUtils.defaultString(in).trim(), ABI_CAB_LEN, '0');
			if (!StringUtils.isNumeric(codice))
				logger.error("Codice '" + cod + "' specificato ('" + in + "')" + NON_CONFORME);
			else
				out = codice;
		}
		return out;
	}


	private CollezioniType getCollezioniType(Collezioni in) {
		CollezioniType out = new CollezioniType();
		if (in != null) {
			out.setDescrizioneCollezione(in.getDescrizioneCollezione());
			out.setNumeroPezzi(in.getNumeroPezzi());
		}
		return out;
	}

	private AziendaType getAziendaType(Azienda in) {
		AziendaType out = new AziendaType();
		if (in != null) {
			out.setCapitaleSociale(in.getCapitaleSociale());
			out.setCodiceFiscale(in.getCodiceFiscale());
			out.setDescrizioneAmministratore(in.getAmministratore());
			out.setDescrizioneRappresentanteSocieta(in.getRappresentanteSocieta());
			out.setNumerocciaa(in.getNumeroCCIA());
			out.setNumeroDipendenti(in.getNumeroDipendenti());
			out.setPartitaIva(in.getPartitaIVA());
			out.setRagioneSociale(in.getRagioneSociale());
			out.setSettoreAttivita(getSettoreAttivita(in.getSettore()));
		}
		return out;
	}

//	private AziendaType getAziendaType(Long idBene) {
//		TAzienda azienda = trasmissioneService.getTAzienda(idBene);
//		AziendaType out = new AziendaType();
//		out.setCapitaleSociale(getBigInteger(azienda.getImpoCapitaleSoc()));
//		out.setCodiceFiscale(azienda.getDescCodFisc());
//		out.setDescrizioneAmministratore(azienda.getDescAmministratore());
//		out.setDescrizioneRappresentanteSocieta(azienda.getDescRappresSoc());
//		out.setNumerocciaa(azienda.getDescCciaa());
//		out.setNumeroDipendenti(getBigInteger(azienda.getNumeDipendenti()));
//		out.setPartitaIva(azienda.getDescPIva());
//		out.setRagioneSociale(azienda.getDescRagioneSoc());
//		out.setSettoreAttivita(SettoreAttivitaType.valueOf(azienda.getTTpSettoreAtt().getDescSettoreAtt()));
//		return out;
//	}
//
	private AnimaliType getAnimaliType(Animali in) {
		AnimaliType out = new AnimaliType();
		out.setDescrizione(StringUtils.isNotBlank(in.getDescrizione()) ? in.getDescrizione() : Constants.NON_DISPONIBILE);
		out.setNumeroAnimali(in.getNumero());
		return out;
	}

	private ProvvedimentoTrasmissioneType getProvvedimentoT() {
		ProvvedimentoTrasmissioneType out = new ProvvedimentoTrasmissioneType();
		ProvvedimentoT p = getProvvedimento();
		out.setIdentificativoProvvedimento(p.getId());
		out.setAnnoProvvedimento(p.getAnnoProvvedimento());
		out.setDataDepositoProvv(DateUtils.convert(p.getDataDeposito()));
		out.setNumProvvedimento(p.getNumero());
		return out;
	}

	public TrasmissioneRelazione creaRelazioneObj() {
		TrasmissioneRelazione out = new TrasmissioneRelazione();
		out.setProcedimento(getProcedimentoTR());
		out.setProvvedimento(getProvvedimentoT());
		out.getBene().addAll(getBeniT());
		out.getSoggetto().addAll(getSoggettiT());
		out.getMisura().addAll(getMisureT());
		if (Utility.isValid(getAmministrazioni())) out.getAmministrazione().addAll(getAmministrazioniT());
		JAXBUtil.printXML(out);
		return out;
	}

	public byte[] creaRelazione() {
		return JAXBUtil.getXML(creaRelazioneObj());
	}
}
