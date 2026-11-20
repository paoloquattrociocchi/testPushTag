package it.giustizia.smp.service;

import it.giustizia.smp.model.TAnimali;
import it.giustizia.smp.model.TAzienda;
import it.giustizia.smp.model.TBene;
import it.giustizia.smp.xml.Amministrazione;
import it.giustizia.smp.xml.Animali;
import it.giustizia.smp.xml.Azienda;
import it.giustizia.smp.xml.BeneT;
import it.giustizia.smp.xml.Misura;
import it.giustizia.smp.xml.Persona;
import it.giustizia.smp.xml.Procedimento;
import it.giustizia.smp.xml.ProvvedimentoT;
import it.giustizia.smp.xml.Soggetto;

import java.util.List;

public interface TrasmissioneAmministratoriService {

	Animali getAnimali(Long idBene, int idUffOwn);
	TAnimali getTAnimali(Long idBene, int idUffOwn);

	Azienda getAzienda(Long idBene);
	TAzienda getTAzienda(Long idBene);
	
	ProvvedimentoT getProvvedimento(Long idAtto, int idUffOwner);

	Procedimento getProcedimento(Long idProcedimento);

	BeneT getTBene(Long idBene, int idUffOwn, TBene model);
	
	public List<BeneT> getTBeni(long idProcedimento, long idAtto, int idUffOwner);
	
	public List<Misura> getMisure(long idProc, long idAtto, Integer uffOwner);	

	public List<Persona> getSoggetti(int uffOwner, long idProc, long idAtto);
	
	public List<Amministrazione> getAmministrazioni(long idProcedimento, long idAtto, Integer uffOwner, boolean escludiAgenzia);
	
}
