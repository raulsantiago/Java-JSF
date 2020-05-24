package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Livro;

@SuppressWarnings("serial")
public class LivroDao implements Serializable {	
	
		// Injeção de dependencia com CDI
		@Inject
		EntityManager em; // new EntityManager e é uma interface
		
		private DAO<Livro> dao;
		
		// Usando o DAO como auxilio
		@PostConstruct
		void init() {
			this.dao = new DAO<Livro>(this.em, Livro.class);
		}

		public void adiciona(Livro t) {
			dao.adiciona(t);
		}

		public void remove(Livro t) {
			dao.remove(t);
		}

		public void atualiza(Livro t) {
			dao.atualiza(t);
		}

		public List<Livro> listaTodos() {
			return dao.listaTodos();
		}

		public Livro buscaPorId(Integer id) {
			return dao.buscaPorId(id);
		}
		
		

}
