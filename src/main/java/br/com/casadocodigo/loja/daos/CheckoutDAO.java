package br.com.casadocodigo.loja.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.casadocodigo.loja.models.Checkout;

public class CheckoutDAO {
	@PersistenceContext
	private EntityManager manager;
	
	public void save(Checkout checkout) {
		manager.persist(checkout);
	}
	public Checkout findByUuid(String uuid) {
		return manager.createQuery("select c from Checkout c where c.uuid=:uuid",Checkout.class)
				.setParameter("uuid", uuid).getSingleResult();
	}
}
