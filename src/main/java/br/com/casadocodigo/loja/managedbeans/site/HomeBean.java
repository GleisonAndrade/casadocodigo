package br.com.casadocodigo.loja.managedbeans.site;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.com.casadocodigo.loja.daos.BookDAO;
import br.com.casadocodigo.loja.models.Book;

@Model
public class HomeBean {
	
	@Inject
	private BookDAO bookDAO;
	
	public List<Book> olderBooks() {
		return bookDAO.olderBooks();
	}
	
	public List<Book> lastReleases() {
		return bookDAO.lastReleases();
	}
}
