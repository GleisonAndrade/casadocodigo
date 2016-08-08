package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import br.com.casadocodigo.loja.models.Author;

public class AuthorDAO {
	
	@PersistenceContext//(type=PersistenceContextType.EXTENDED)
	private EntityManager manager;
	
	public List<Author> list() {
		return manager.createQuery("select a from Author a",Author.class).getResultList();
	}
}
