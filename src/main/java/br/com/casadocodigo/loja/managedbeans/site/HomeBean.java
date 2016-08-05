package br.com.casadocodigo.loja.managedbeans.site;

import java.util.List;

import javax.inject.Inject;

import br.com.casadocodigo.loja.daos.BookDAO;
import br.com.casadocodigo.loja.models.Book;

public class HomeBean {
	@Inject
	private BookDAO bookDAO;
	
	public List<Book> olderBooks() {
		return bookDAO.olders();
	}
	
	public List<Book> releasedBooks() {
		return bookDAO.released();
	}
}
