package br.com.casadocodigo.loja.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.com.casadocodigo.loja.daos.BookDAO;
import br.com.casadocodigo.loja.models.Book;

@Model
public class AdminListBooksBean {
	
	@Inject
	private BookDAO bookDAO;
	private List<Book> books = new ArrayList<>();
	
	@PostConstruct
	public void loadObjects() {
		this.books = bookDAO.list();
//		System.out.println("opa");
	}
	
	public List<Book> getBooks() {
		return books;
	}
	
	
}
