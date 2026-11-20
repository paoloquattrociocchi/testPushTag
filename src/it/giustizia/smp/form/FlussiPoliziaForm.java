/**
 * 
 */
package it.giustizia.smp.form;

import it.giustizia.smp.validation.date.ModernDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author antonio
 *
 */
public class FlussiPoliziaForm {

	private Long						idFlusso;
	@ModernDate
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date						dataFlusso;
	private Long						idProcedimento = new Long(0);
	private Long						idAtto = new Long(0);
	private Long						idServizio = new Long(0);
	private String						descTipoFlusso;
	private Long						idStato;
	private String						descStato;
	
	private Long						idUffMittente = new Long(0);
	private String						descTipoUffMittente;
	private String						descSedeUffMittente;
	private String						descUffMittente;
	
	private Long						idUffDestinatario = new Long(0);
	private String						descTipoUffDestinatario;
	private String						descSedeUffDestinatario;
	private String						descUffDestinatario;
	
	private char						flgDirezione;
	private String						numProc;
	
	private ProcedimentoForm 			procedimento;
	private List<SoggettoForm> 			listSoggetto = new ArrayList<SoggettoForm>();
	private AttoForm					atto;
	
	private List<SoggettoForm>					listSoggettoFis = new ArrayList<SoggettoForm>();
	private List<SoggettoForm>					listSoggettoGiu = new ArrayList<SoggettoForm>();
	private UfficiForm 							ufficioMittente;
	private UfficiForm							ufficioDestinatario;
	private List<MisuraPersonaleFlussiForm> 	listMisurePersonali = null;
	private List<MisuraPatrimonialeFlussiForm>  listMisurePatrimoniali = null;
	
	//per la visualizzazione del dettaglio idServizio 19
	private List<BeneSoggettoPoliziaForm>       misureSoggetto = null;
	private List<BeneSoggettoPoliziaForm>		beneSoggetto = null;
	
	//attributi per ufficio mittente
	private String 		descrNumProposta;
	private String		dataEmissione;
	private	String		descrNumAccert;
	private String		descrNote;
	
	//attributi per DatiComunicazioneType
	private String 			descrNumCominic;
	private String 			dataEmissioneComunic;
	private String 			idTipoComunic;
	private String 			dataEventoComunic;
	private String 			descrNoteComunic;
	private String 			descrTipoComunic;
	
	//attributi per DatiParereType
	private String 			descrNumParere;
	private String 			dataEmissioneParere;
	private String 			flgParere;
	private String 			descrFlgParere;
	private String 			descrNoteParere;
	
	//attributi per DatiEsitoIndagatiType
	private String 			descrNumEsito;
	private String 			descrEsito;	//DB
	private String 			dataEmissioneEsito;
	private String 			descrNoteEsito;
		
	//attributi per DatiRichiestaAggravamentoType
	private String 			descrNumAggr;
	private String 			dataEmissioneAggr;
	private String 			descrPropostaAggr;	
	private String 			descrNoteAggr;
	
	private String 			enableRegistra = "N";
	private AllegatoForm 	allegato = new AllegatoForm();
	private String			dataRicezione;
	
	private Long 			idCatAtto;
	private String			descCatAtto;
	
	public Long getIdFlusso() {
		return idFlusso;
	}
	public void setIdFlusso(Long idFlusso) {
		this.idFlusso = idFlusso;
	}
	public Date getDataFlusso() {
		return dataFlusso;
	}
	public void setDataFlusso(Date dataFlusso) {
		this.dataFlusso = dataFlusso;
	}
	public Long getIdProcedimento() {
		return idProcedimento;
	}
	public void setIdProcedimento(Long idProcedimento) {
		this.idProcedimento = idProcedimento;
	}
	public Long getIdAtto() {
		return idAtto;
	}
	public void setIdAtto(Long idAtto) {
		this.idAtto = idAtto;
	}
	public Long getIdServizio() {
		return idServizio;
	}
	public void setIdServizio(Long idServizio) {
		this.idServizio = idServizio;
	}
	public String getDescTipoFlusso() {
		return descTipoFlusso;
	}
	public void setDescTipoFlusso(String descTipoFlusso) {
		this.descTipoFlusso = descTipoFlusso;
	}
	public Long getIdStato() {
		return idStato;
	}
	public void setIdStato(Long idStato) {
		this.idStato = idStato;
	}
	public String getDescStato() {
		return descStato;
	}
	public void setDescStato(String descStato) {
		this.descStato = descStato;
	}
	public Long getIdUffMittente() {
		return idUffMittente;
	}
	public void setIdUffMittente(Long idUffMittente) {
		this.idUffMittente = idUffMittente;
	}
	public String getDescTipoUffMittente() {
		return descTipoUffMittente;
	}
	public void setDescTipoUffMittente(String descTipoUffMittente) {
		this.descTipoUffMittente = descTipoUffMittente;
	}
	public String getDescSedeUffMittente() {
		return descSedeUffMittente;
	}
	public void setDescSedeUffMittente(String descSedeUffMittente) {
		this.descSedeUffMittente = descSedeUffMittente;
	}
	public String getDescUffMittente() {
		return descUffMittente;
	}
	public void setDescUffMittente(String descUffMittente) {
		this.descUffMittente = descUffMittente;
	}
	public Long getIdUffDestinatario() {
		return idUffDestinatario;
	}
	public void setIdUffDestinatario(Long idUffDestinatario) {
		this.idUffDestinatario = idUffDestinatario;
	}
	public String getDescTipoUffDestinatario() {
		return descTipoUffDestinatario;
	}
	public void setDescTipoUffDestinatario(String descTipoUffDestinatario) {
		this.descTipoUffDestinatario = descTipoUffDestinatario;
	}
	public String getDescSedeUffDestinatario() {
		return descSedeUffDestinatario;
	}
	public void setDescSedeUffDestinatario(String descSedeUffDestinatario) {
		this.descSedeUffDestinatario = descSedeUffDestinatario;
	}
	public String getDescUffDestinatario() {
		return descUffDestinatario;
	}
	public void setDescUffDestinatario(String descUffDestinatario) {
		this.descUffDestinatario = descUffDestinatario;
	}
	public char getFlgDirezione() {
		return flgDirezione;
	}
	public void setFlgDirezione(char flgDirezione) {
		this.flgDirezione = flgDirezione;
	}
	public String getNumProc() {
		return numProc;
	}
	public void setNumProc(String numProc) {
		this.numProc = numProc;
	}
	public ProcedimentoForm getProcedimento() {
		return procedimento;
	}
	public void setProcedimento(ProcedimentoForm procedimento) {
		this.procedimento = procedimento;
	}
	public List<SoggettoForm> getListSoggetto() {
		return listSoggetto;
	}
	public void setListSoggetto(List<SoggettoForm> listSoggetto) {
		this.listSoggetto = listSoggetto;
	}
	public AttoForm getAtto() {
		return atto;
	}
	public void setAtto(AttoForm atto) {
		this.atto = atto;
	}
	public UfficiForm getUfficioMittente() {
		return ufficioMittente;
	}
	public void setUfficioMittente(UfficiForm ufficioMittente) {
		this.ufficioMittente = ufficioMittente;
	}
	public UfficiForm getUfficioDestinatario() {
		return ufficioDestinatario;
	}
	public void setUfficioDestinatario(UfficiForm ufficioDestinatario) {
		this.ufficioDestinatario = ufficioDestinatario;
	}
	public List<SoggettoForm> getListSoggettoFis() {
		return listSoggettoFis;
	}
	public void setListSoggettoFis(List<SoggettoForm> listSoggettoFis) {
		this.listSoggettoFis = listSoggettoFis;
	}
	public List<SoggettoForm> getListSoggettoGiu() {
		return listSoggettoGiu;
	}
	public void setListSoggettoGiu(List<SoggettoForm> listSoggettoGiu) {
		this.listSoggettoGiu = listSoggettoGiu;
	}
	public String getDescrNumProposta() {
		return descrNumProposta;
	}
	public void setDescrNumProposta(String descrNumProposta) {
		this.descrNumProposta = descrNumProposta;
	}
	public String getDataEmissione() {
		return dataEmissione;
	}
	public void setDataEmissione(String dataEmissione) {
		this.dataEmissione = dataEmissione;
	}
	public String getDescrNumAccert() {
		return descrNumAccert;
	}
	public void setDescrNumAccert(String descrNumAccert) {
		this.descrNumAccert = descrNumAccert;
	}
	public String getDescrNote() {
		return descrNote;
	}
	public void setDescrNote(String descrNote) {
		this.descrNote = descrNote;
	}
	public List<MisuraPersonaleFlussiForm> getListMisurePersonali() {
		return listMisurePersonali;
	}
	public void setListMisurePersonali(
			List<MisuraPersonaleFlussiForm> listMisurePersonali) {
		this.listMisurePersonali = listMisurePersonali;
	}
	public List<MisuraPatrimonialeFlussiForm> getListMisurePatrimoniali() {
		return listMisurePatrimoniali;
	}
	public void setListMisurePatrimoniali(
			List<MisuraPatrimonialeFlussiForm> listMisurePatrimoniali) {
		this.listMisurePatrimoniali = listMisurePatrimoniali;
	}
	public String getDescrNumCominic() {
		return descrNumCominic;
	}
	public void setDescrNumCominic(String descrNumCominic) {
		this.descrNumCominic = descrNumCominic;
	}
	public String getDataEmissioneComunic() {
		return dataEmissioneComunic;
	}
	public void setDataEmissioneComunic(String dataEmissioneComunic) {
		this.dataEmissioneComunic = dataEmissioneComunic;
	}
	public String getIdTipoComunic() {
		return idTipoComunic;
	}
	public void setIdTipoComunic(String idTipoComunic) {
		this.idTipoComunic = idTipoComunic;
	}
	public String getDescrNoteComunic() {
		return descrNoteComunic;
	}
	public void setDescrNoteComunic(String descrNoteComunic) {
		this.descrNoteComunic = descrNoteComunic;
	}
	public String getDescrTipoComunic() {
		return descrTipoComunic;
	}
	public void setDescrTipoComunic(String descrTipoComunic) {
		this.descrTipoComunic = descrTipoComunic;
	}
	public String getDataEventoComunic() {
		return dataEventoComunic;
	}
	public void setDataEventoComunic(String dataEventoComunic) {
		this.dataEventoComunic = dataEventoComunic;
	}
	public String getDescrNumParere() {
		return descrNumParere;
	}
	public void setDescrNumParere(String descrNumParere) {
		this.descrNumParere = descrNumParere;
	}
	public String getDataEmissioneParere() {
		return dataEmissioneParere;
	}
	public void setDataEmissioneParere(String dataEmissioneParere) {
		this.dataEmissioneParere = dataEmissioneParere;
	}
	public String getFlgParere() {
		return flgParere;
	}
	public void setFlgParere(String flgParere) {
		this.flgParere = flgParere;
	}
	public String getDescrNoteParere() {
		return descrNoteParere;
	}
	public void setDescrNoteParere(String descrNoteParere) {
		this.descrNoteParere = descrNoteParere;
	}
	public String getDescrNumEsito() {
		return descrNumEsito;
	}
	public void setDescrNumEsito(String descrNumEsito) {
		this.descrNumEsito = descrNumEsito;
	}
	public String getDataEmissioneEsito() {
		return dataEmissioneEsito;
	}
	public void setDataEmissioneEsito(String dataEmissioneEsito) {
		this.dataEmissioneEsito = dataEmissioneEsito;
	}
	public String getDescrNoteEsito() {
		return descrNoteEsito;
	}
	public void setDescrNoteEsito(String descrNoteEsito) {
		this.descrNoteEsito = descrNoteEsito;
	}
	public String getDescrEsito() {
		return descrEsito;
	}
	public void setDescrEsito(String descrEsito) {
		this.descrEsito = descrEsito;
	}
	public String getDescrNumAggr() {
		return descrNumAggr;
	}
	public void setDescrNumAggr(String descrNumAggr) {
		this.descrNumAggr = descrNumAggr;
	}
	public String getDataEmissioneAggr() {
		return dataEmissioneAggr;
	}
	public void setDataEmissioneAggr(String dataEmissioneAggr) {
		this.dataEmissioneAggr = dataEmissioneAggr;
	}
	public String getDescrPropostaAggr() {
		return descrPropostaAggr;
	}
	public void setDescrPropostaAggr(String descrPropostaAggr) {
		this.descrPropostaAggr = descrPropostaAggr;
	}
	public String getDescrNoteAggr() {
		return descrNoteAggr;
	}
	public void setDescrNoteAggr(String descrNoteAggr) {
		this.descrNoteAggr = descrNoteAggr;
	}
	public String getEnableRegistra() {
		return enableRegistra;
	}
	public void setEnableRegistra(String enableRegistra) {
		this.enableRegistra = enableRegistra;
	}
	public String getDescrFlgParere() {
		return descrFlgParere;
	}
	public void setDescrFlgParere(String descrFlgParere) {
		this.descrFlgParere = descrFlgParere;
	}
	public List<BeneSoggettoPoliziaForm> getMisureSoggetto() {
		return misureSoggetto;
	}
	public void setMisureSoggetto(List<BeneSoggettoPoliziaForm> misureSoggetto) {
		this.misureSoggetto = misureSoggetto;
	}
	public List<BeneSoggettoPoliziaForm> getBeneSoggetto() {
		return beneSoggetto;
	}
	public void setBeneSoggetto(List<BeneSoggettoPoliziaForm> beneSoggetto) {
		this.beneSoggetto = beneSoggetto;
	}
	public AllegatoForm getAllegato() {
		return allegato;
	}
	public void setAllegato(AllegatoForm allegato) {
		this.allegato = allegato;
	}
	public String getDataRicezione() {
		return dataRicezione;
	}
	public void setDataRicezione(String dataRicezione) {
		this.dataRicezione = dataRicezione;
	}
	public String getDescCatAtto() {
		return descCatAtto;
	}
	public void setDescCatAtto(String descCatAtto) {
		this.descCatAtto = descCatAtto;
	}
	public Long getIdCatAtto() {
		return idCatAtto;
	}
	public void setIdCatAtto(Long idCatAtto) {
		this.idCatAtto = idCatAtto;
	}

}
