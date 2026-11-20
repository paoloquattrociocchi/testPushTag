package it.giustizia.smp.xml;

import static it.giustizia.smp.utility.NumbersUtility.getInt;
import static it.giustizia.smp.utility.NumbersUtility.getLong;
import it.giustizia.smp.utility.DateUtils;
import it.giustizia.smp.utility.Utility;
import it.giustizia.smp.webservice.utility.JAXBUtil;
import it.giustizia.smp.xml.amministratori.AnimaliExtType;
import it.giustizia.smp.xml.amministratori.AziendaExtType;
import it.giustizia.smp.xml.amministratori.BeneExtType;
import it.giustizia.smp.xml.amministratori.CategoriaBeneType;
import it.giustizia.smp.xml.amministratori.DatiAziendaGestType;
import it.giustizia.smp.xml.amministratori.DatiMobileGestType;
import it.giustizia.smp.xml.amministratori.ProcedimentoType;
import it.giustizia.smp.xml.amministratori.ProvvedimentoRicezioneType;
import it.giustizia.smp.xml.amministratori.RicezioneRelazione;
import it.giustizia.smp.xml.amministratori.SottoCategoriaBeneType;
import it.giustizia.smp.xml.amministratori.TipoBeneType;
import it.giustizia.smp.xml.amministratori.UbicazioneType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class RicezioneRelazioneAmm extends RelazioneAmm {

	private ProvvedimentoR provvedimento = new ProvvedimentoR();
	private Collection<? extends BeneR> beni = new ArrayList<BeneR>();

	public ProvvedimentoR getProvvedimento() {
		return provvedimento;
	}

	public void setProvvedimento(ProvvedimentoR provvedimento) {
		this.provvedimento = provvedimento;
	}

	public Collection<? extends BeneR> getBeni() {
		return beni;
	}

	public void setBeni(Collection<? extends BeneR> beni) {
		this.beni = beni;
	}

	private Collection<? extends BeneExtType> getBeniR() {
		// TODO Auto-generated method stub
		return null;
	}

	private ProvvedimentoRicezioneType getProvvedimentoR() {
		// TODO Auto-generated method stub
		return null;
	}

	public RicezioneRelazione creaRelazioneObj() {
		RicezioneRelazione out = new RicezioneRelazione();
		out.setProcedimento(getProcedimentoTR());
		out.setProvvedimento(getProvvedimentoR());
		out.getBene().addAll(getBeniR());
		JAXBUtil.printXML(out);
		return out;
	}

	public byte[] creaRelazione() {
		return JAXBUtil.getXML(creaRelazioneObj());
	}

	public RicezioneRelazioneAmm getInstance(String xml) {
		RicezioneRelazioneAmm out = null;
		if (!StringUtils.isEmpty(xml)) {
			try {
				out = getInstance(JAXBUtil.unmarshallPartial(RicezioneRelazione.class, xml));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return out;
	}

	public RicezioneRelazioneAmm getInstance(RicezioneRelazione in) {
		RicezioneRelazioneAmm out = null;
		if (in != null) {
			out = new RicezioneRelazioneAmm();
			out.setProcedimento(getProcedimentoR(in.getProcedimento()));
			out.setProvvedimento(getProvvedimentoR(in.getProvvedimento()));
			out.setBeni(getBeniR(in.getBene()));
		}
		return out;
	}

	private Procedimento getProcedimentoR(ProcedimentoType in) {
		Procedimento out = new Procedimento();
		if (in != null) {
			out.setId(in.getIdentificativoProcedimento());
			out.setUfficioCompetente(getLong(in.getNumeroProcedimento()));
			out.setAnnoProcedimento(getInt(in.getAnnoProcedimento()));
			out.setNumeroProcedimento(in.getNumeroProcedimento());
		}
		return out;
	}

	private ProvvedimentoR getProvvedimentoR(ProvvedimentoRicezioneType in) {
		ProvvedimentoR out = new ProvvedimentoR();
		if (in != null) {
			out.setCodiceFiscale(in.getCodiceFiscale());
			out.setDataInizioPeriodo(DateUtils.convert(in.getDataInizioPeriodo()));
			out.setDataFinePeriodo(DateUtils.convert(in.getDataFinePeriodo()));
			out.setDataPresentazioneRelazione(DateUtils.convert(in.getDataPresentazioneRelazione()));
		}
		return out;
	}

	private Collection<? extends BeneR> getBeniR(List<BeneExtType> in) {
		Collection<? extends BeneR> out = new ArrayList<BeneR>();
		if (Utility.isValid(in)) {
			for (BeneExtType el : in) {
				BeneR b = new BeneR();
				b.setId(el.getIdentificativoBene());
				b.setTipo(getTipologiaBene(el.getTipologia()));
				b.setCategoria(getCategoriaBene(el.getCategoria()));
				b.setSottocategoria(getSottocategoriaBene(el.getSottoCategoria()));
				b.setUbicazione(getUbicazione(el.getUbicazione()));
				b.setGravatoDaPegnoIpoteca(getValue(el.isGravatoDaPegnoIpoteca()));
				getDatiSpecificiDelBene(b, el.getDatiSpecificiBene());
			}
		}
		return out;
	}

	private void getDatiSpecificiDelBene(BeneR b, BeneExtType.DatiSpecificiBene dsb) {
		if (dsb != null) {
			b.setAnimali(getAnimali(dsb.getAnimali()));
			b.setAzienda(getAzienda(dsb.getAzienda()));
		}
	}

	private AziendaR getAzienda(AziendaExtType in) {
		AziendaR out = null;
		if (in != null) {
			out = new AziendaR();
			if (in.getAzienda() != null) {
				out.setRagioneSociale(in.getAzienda().getRagioneSociale());
				out.setPartitaIVA(in.getAzienda().getPartitaIva());
				out.setNumeroCCIA(in.getAzienda().getNumerocciaa());
				out.setCodiceFiscale(in.getAzienda().getCodiceFiscale());
				out.setCapitaleSociale(in.getAzienda().getCapitaleSociale());
				out.setRappresentanteSocieta(in.getAzienda().getDescrizioneRappresentanteSocieta());
				out.setAmministratore(in.getAzienda().getDescrizioneAmministratore());
				out.setNumeroDipendenti(in.getAzienda().getNumeroDipendenti());
			}
			out.setGestione(getDatiAziendaGest(in.getDatiAziendaGest()));
		}
		return out;
	}

	private DatiAziendaGest getDatiAziendaGest(DatiAziendaGestType in) {
		DatiAziendaGest out = null;
		if (in != null) {
			out = new DatiAziendaGest();
			out.setStima(in.getStima());
			out.setDataStima(DateUtils.convert(in.getDataStima()));
			out.setDirittiTerzi(getValue(in.isDirittiTerzi()));
			out.setDescDirittiTerzi(in.getDescDirittiTerzi());
			out.setTrascrizioneSequestro(getValue(in.isTrascrizioneSequestro()));
			out.setDataTrascrizioneSequestro(DateUtils.convert(in.getDataTrascrizioneSequestro()));
			out.setDescStatoAttivitaAziendale(in.getDescStatoAttivitaAziendale());
			out.setProspettivaProsecuzioneAttivita(in.getProspettivaProsecuzioneAttivita());
			out.setDescDestinazioneImpresa(in.getDescDestinazioneImpresa());
			out.setDescTipoOrdineAmministrativo(in.getTipoOrganoAmministrativo());
			out.setNominativoAmministratore(in.getNominativoAmministratore());
			out.setDataNominaAmministratore(DateUtils.convert(in.getDataNominaAmministratore()));
			out.setNominativoRappresentanteLegale(in.getNominativoRappresentanteLegale());
			out.setDataNominaRappresentante(DateUtils.convert(in.getDataNominaRappresentante()));
		}
		return out;
	}

	private AnimaliR getAnimali(AnimaliExtType in) {
		AnimaliR out = null;
		if (in != null) {
			out = new AnimaliR();
			if (in.getAnimali() != null) {
				out.setDescrizione(in.getAnimali().getDescrizione());
				out.setNumero(in.getAnimali().getNumeroAnimali());
			}
			out.setGestione(getdatiMobileGest(in.getDatiMobileGest()));
		}
		return out;
	}

	private DatiMobileGest getdatiMobileGest(DatiMobileGestType in) {
		DatiMobileGest out = null;
		if (in != null) {
			out = new DatiMobileGest();
			out.setStima(in.getStima());
			out.setDataStima(DateUtils.convert(in.getDataStima()));
			out.setDirittiTerzi(getValue(in.isDirittiTerzi()));
			out.setDescDirittiTerzi(in.getDescDirittiTerzi());
			out.setBeneRinvenuto(getValue(in.isBeneRinvenuto()));
			out.setDataPossesso(DateUtils.convert(in.getDataPossesso()));
			out.setDataPerditaPossesso(DateUtils.convert(in.getDataPerditaPossesso()));
			out.setDatiBeneMobile(getdatiBeneMobile(in));
			out.setDescAltriVincoli(in.getDescAltriVincoli());
		}
		return out;
	}

	private DatiBeneMobile getdatiBeneMobile(DatiMobileGestType in) {
		DatiBeneMobile out = null;
		if (in != null) {
			out = new DatiBeneMobile();
			out.setOneriCustodia(getValue(in.isOneriCustodia()));
			out.setValoreOneriCustodia(in.getValoreOneriCustodia());
			out.setDataOneriCustodia(DateUtils.convert(in.getDataOneriCustodia()));
		}
		return out;
	}

	private boolean getValue(Boolean in) {
		return (in != null) ? in.booleanValue() : false;
	}

	private Ubicazione getUbicazione(UbicazioneType in) {
		Ubicazione out = null;
		if (in != null) {
			out = new Ubicazione();
			out.setIdStato(in.getIdStato());
			out.setIdComuneItaliano(in.getIdComuneItaliano());
			out.setDescrizioneComuneEstero(in.getDescrizioneComuneEstero());
		}
		return out;
	}

	private String getSottocategoriaBene(SottoCategoriaBeneType in) {
		return (in != null) ? in.value() : "";
	}

	private String getCategoriaBene(CategoriaBeneType in) {
		return (in != null) ? in.value() : "";
	}

	private String getTipologiaBene(TipoBeneType in) {
		return (in != null) ? in.value() : "";
	}
}
