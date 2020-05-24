package br.com.caelum.livraria.dao;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("livraria");

	// Injetando IEntityManaget a cada requisição
	@Produces
	@RequestScoped
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	// @Diposes é para quando a requisição acabar então IEntityManaget tem que fechar e conexão com banco 
	public void close(@Disposes EntityManager em) {
		em.close();
	}
}
