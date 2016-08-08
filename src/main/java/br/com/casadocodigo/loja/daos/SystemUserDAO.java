package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.casadocodigo.loja.models.SystemUser;


public class SystemUserDAO {

    @PersistenceContext
    private EntityManager manager;


    public void save(SystemUser systemUser){    	
        manager.persist(systemUser);
    }

    public List<SystemUser> list() {
        return manager.createQuery("select s from SystemUser s", SystemUser.class).getResultList();
    }

    public void delete(SystemUser user) {
        user = manager.merge(user);
        manager.remove(user);
    }
           
}