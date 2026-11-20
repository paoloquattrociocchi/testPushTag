package it.giustizia.smp.dao;

import it.giustizia.smp.dto.pg_conservatorie.ComunicazioneDto;

import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;

@SuppressWarnings("rawtypes")
public class MyHibernateCallback implements HibernateCallback
{

	private String tipo; private String tipoAtto; private Short anno; private Long dalNumero;
	private Long alNumero; private String conservatoria; private String cognome; private String nome;
	private String denominazione; private Integer idUfficioUtente;

	public MyHibernateCallback(String tipo, String tipoAtto, Short anno,
			Long dalNumero, Long alNumero, String conservatoria,
			String cognome, String nome, String denominazione,
			Integer idUfficioUtente) {
		super();
		this.tipo = tipo;
		this.tipoAtto = tipoAtto;
		this.anno = anno;
		this.dalNumero = dalNumero;
		this.alNumero = alNumero;
		this.conservatoria = conservatoria;
		this.cognome = cognome;
		this.nome = nome;
		this.denominazione = denominazione;
		this.idUfficioUtente = idUfficioUtente;
	}
	
	public Object doInHibernate(final Session session) throws HibernateException, SQLException 
	{

        Query q = session.getNamedQuery("PG_CONSERVATORIE_lista_comunicazioni");
		q.setParameter("tipo", tipo, Hibernate.STRING);
		q.setParameter("tipoAtto", tipoAtto, Hibernate.STRING);
		q.setParameter("anno", anno, Hibernate.SHORT);
		q.setParameter("dalNumero", dalNumero, Hibernate.LONG);
		q.setParameter("alNumero", alNumero, Hibernate.LONG);
		q.setParameter("conservatoria", conservatoria, Hibernate.STRING);
		q.setParameter("cognome", cognome, Hibernate.STRING);
		q.setParameter("nome", nome, Hibernate.STRING);
		q.setParameter("denominazione", denominazione, Hibernate.STRING);
		q.setParameter("idUfficioUtente", idUfficioUtente, Hibernate.INTEGER);
		q.setResultTransformer(Transformers.aliasToBean(ComunicazioneDto.class));

		return q.list();
	}
}
