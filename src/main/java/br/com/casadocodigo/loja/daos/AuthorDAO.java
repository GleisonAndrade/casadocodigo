package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import org.hibernate.jpa.QueryHints;

import br.com.casadocodigo.loja.models.Author;

public class AuthorDAO {
	
	@PersistenceContext//(type=PersistenceContextType.EXTENDED)
	private EntityManager manager;
	
	public List<Author> list(){
		return manager.createQuery("select a from Author a", Author.class)
				.setHint(QueryHints.HINT_CACHEABLE, true)
				.getResultList();
	}

	public Author findById(Integer id) {
		return manager.find(Author.class, id);
	}

	public void save(Author author) {
		manager.persist(author);
		
	}

	public void update(Author author) {
		manager.persist(author);
		
	}

	public void remove(Author author) {
		manager.remove(author);
	}
}