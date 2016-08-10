package br.com.casadocodigo.loja.managedbeans.admin;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.com.casadocodigo.loja.listeners.websockets.ConnectedUsers;
import br.com.casadocodigo.loja.models.Book;
import br.com.casadocodigo.loja.models.Sale;

@Model
public class AdminSalesBean {
	@Inject
	private ConnectedUsers connectedUsers;
	
	private Sale sale = new Sale();
	
	@PostConstruct
	private void configure() {
		this.sale.setBook(new Book());
	}
	
	public String save() {
		connectedUsers.send(sale.toJson());
		return "/livros/promocoes/form.xhtml?faces-redirect=true";
	}
	
	public Sale getSale() {
		return this.sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}
}
