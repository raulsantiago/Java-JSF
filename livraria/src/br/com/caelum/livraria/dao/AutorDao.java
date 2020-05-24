package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Autor;

@SuppressWarnings("serial")
public class AutorDao implements Serializable {

	// Injeção de dependencia com CDI
	@Inject
	EntityManager em; // new EntityManager e é uma interface
	
	private DAO<Autor> dao;
	
	// Usando o DAO como auxilio
	@PostConstruct
	void init() {
		this.dao = new DAO<Autor>(this.em, Autor.class);
	}

	// Source >> Genetege Delegate Method | Gera automatico todos os métodos abaixo 
	public void adiciona(Autor t) {
		dao.adiciona(t);
	}

	public void remove(Autor t) {
		dao.remove(t);
	}

	public void atualiza(Autor t) {
		dao.atualiza(t);
	}

	public List<Autor> listaTodos() {
		return dao.listaTodos();
	}

	public Autor buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	

}
