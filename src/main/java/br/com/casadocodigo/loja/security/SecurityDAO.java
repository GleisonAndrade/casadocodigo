package br.com.casadocodigo.loja.security;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.casadocodigo.loja.models.SystemUser;

public class SecurityDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public SystemUser loadByUserName(String userName) {
		String jpql = "select u from SystemUser u where u.email = :login";
		SystemUser user = entityManager.createQuery(jpql,SystemUser.class)
				.setParameter("login", userName).getSingleResult();
		return user;
	}
}
