package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

@Entity
public class Checkout {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	private SystemUser buyer;
	private BigDecimal total;
	private String jsonCart;
	private String uuid;
	
	@PrePersist
	public void PrePersist() {
		this.uuid = UUID.randomUUID().toString();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public SystemUser getBuyer() {
		return buyer;
	}
	public void setBuyer(SystemUser buyer) {
		this.buyer = buyer;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getJsonCart() {
		return jsonCart;
	}
	public void setJsonCart(String jsonCart) {
		this.jsonCart = jsonCart;
	}
	
	public Checkout() {
		
	}
	
	public Checkout(SystemUser user, ShoppingCart cart) {
		this.buyer = user;
		this.total = cart.getTotal();
		this.jsonCart = cart.toJson();
	}
}
