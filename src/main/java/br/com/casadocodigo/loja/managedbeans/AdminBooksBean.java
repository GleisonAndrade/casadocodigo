package br.com.casadocodigo.loja.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import br.com.casadocodigo.loja.daos.AuthorDAO;
import br.com.casadocodigo.loja.daos.BookDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.infra.MessagesHelper;
import br.com.casadocodigo.loja.models.Author;
import br.com.casadocodigo.loja.models.Book;

@Model
public class AdminBooksBean {
	private Book product = new Book();
	private List<Integer> selectedAuthorsIds = new ArrayList<>();
	private List<Author> authors = new ArrayList<>();
	private Part summary;
	@Inject
	private FileSaver fileSaver;
	
	public Part getSummary() {
		return summary;
	}

	public void setSummary(Part summary) {
		this.summary = summary;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public List<Integer> getSelectedAuthorsIds() {
		return selectedAuthorsIds;
	}

	public void setSelectedAuthorsIds(List<Integer> selectedAuthorsIds) {
		this.selectedAuthorsIds = selectedAuthorsIds;
	}

	@Inject
	private BookDAO bookDAO;
	@Inject 
	private AuthorDAO authorDAO;
	@Inject 
	private MessagesHelper messagesHelper;
	
	@Transactional
	public String save() {
		String summaryPath = fileSaver.write("summaries", summary);
		product.setSummaryPath(summaryPath);
		bookDAO.save(product);
		messagesHelper.addFlash(new FacesMessage("Cadastro Efetuado com Sucesso"));
		
		return "/livros/lista?faces-redirect=true";
	}
	
	public Book getProduct() {
		return product;
	}
	
	private void populateBookAuthor() {
		selectedAuthorsIds.stream().map( (id) -> {
			return new Author(id);
		}).forEach(product::add);
	}
	private void clearObjects() {
		this.product = new Book();
		this.selectedAuthorsIds.clear();
	}
	
	@PostConstruct
	public void loadObjects() {
		this.authors = authorDAO.list();
	}
}
