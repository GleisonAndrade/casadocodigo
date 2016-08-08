package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import br.com.casadocodigo.loja.models.Book;

@Dependent
public class BookDAO {
	
	@PersistenceContext
	private EntityManager manager;
	
	public void save(Book product) {
		manager.persist(product);
	}
	
	public List<Book> list() {
		return manager.createQuery("select distinct(b) from Book b join fetch b.authors",Book.class).getResultList();
	}
	
	public List<Book> olderBooks() {
		return manager.createQuery("select b from Book b",Book.class).setMaxResults(20).getResultList();
	}
	
	public List<Book> lastReleases() {
		return manager.createQuery("select b from Book b where b.releaseDate<=NOW()",Book.class).getResultList();
	}
	
	public BookDAO() {}
	
	public BookDAO(EntityManager manager) {
		this.manager = manager;
	}
	
	public Book findById(Integer id) {
		return manager.find(Book.class, id);
	}
}
