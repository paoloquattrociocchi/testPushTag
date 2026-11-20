package it.giustizia.smp.form;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class Credenziali {

	private final String			nome;
	private final String			cognome;
	private final String			codiceFiscale;
	private final String 			commonName;
	private final String 			multiProfilo;
	private final List<ProfiloForm> profili;
	
	
	
	public Credenziali(final String nome, final String cognome, final String codiceFiscale, final String commonName, final String multiProfilo, final List<ProfiloForm> profili) {

		if (nome == null) {
			throw new IllegalArgumentException("Il parametro 'nome' del costruttore non può essere null");
		}
		if (cognome == null) {
			throw new IllegalArgumentException("Il parametro 'cognome' del costruttore non può essere null");
		}
		if (profili == null) {
			throw new IllegalArgumentException("Il parametro 'profili' del costruttore non può essere null");
		}
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.commonName = commonName;
		this.multiProfilo = multiProfilo;
		this.profili = profili;
	}

	public final String getNome() {

		return nome;
	}

	public final String getCognome() {

		return cognome;
	}
	
	public String getCodiceFiscale() {
		
		return codiceFiscale;
		
	}


	public final List<ProfiloForm> getProfili() {

		return Collections.unmodifiableList(profili);
	}

	public final boolean isEmpty() {

		return profili.isEmpty();
	}

	public final boolean contains(final ProfiloForm profilo) {

		return profili.contains(profilo);
	}

	public final boolean add(final ProfiloForm profilo) {

		return profili.add(profilo);
	}

	public final boolean addAll(final Collection<? extends ProfiloForm> profili) {

		return this.profili.addAll(profili);
	}

	public final boolean hasUnicoProfilo() {

		return profili.size() == 1;
	}

	public final ProfiloForm getProfilo(final int index) {

		return profili.get(index);
	}

	public String getCommonName() {
		return commonName;
	}

	public String getMultiProfilo() {
		return multiProfilo;
	}
	
	
}
