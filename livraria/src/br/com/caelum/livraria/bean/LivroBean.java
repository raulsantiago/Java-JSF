package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.tx.Transacional;

@Named
@ViewScoped 
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Livro livro = new Livro();
	private Integer autorId;
	private List<Livro> livros;
	
	@Inject
	private LivroDao daoLivro;
	
	@Inject
	private AutorDao daoAutor;
	
	@Inject
	FacesContext context;

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public Livro getLivro() {
		return livro;
	}

	public List<Livro> getLivros() {
		if(this.livros == null) {
			this.livros = this.daoLivro.listaTodos();
		}
		return livros;
	}

	public List<Autor> getAutores() {
		return this.daoAutor.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public void carregarLivroPelaId() {
		this.livro = this.daoLivro.buscaPorId(this.livro.getId());
	}
	
	public void gravarAutor() {
		Autor autor = this.daoAutor.buscaPorId(autorId);
		this.livro.adicionaAutor(autor);
		System.out.println("Escrito por: " + autor.getNome());
	}

	// Anotação criada para associar esta classe para outro metodos de outra classe
	// Para executa a classe GerenciadorDeTransacao com BEGIN e COMMIT
	@Transacional
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());
		if (livro.getAutores().isEmpty()) {
			context.addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}
		if(this.livro.getId() == null) {
			this.daoLivro.adiciona(this.livro);
			this.livros = this.daoLivro.listaTodos();
		} else {
			this.daoLivro.atualiza(this.livro);
		}
		this.livro = new Livro();
	}

	@Transacional
	public void remover(Livro livro) {
		System.out.println("Removendo livro");
		this.daoLivro.remove(livro);
		this.livros = this.daoLivro.listaTodos();
	}
	
	public void removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}
	
	public void carregar(Livro livro) {
		System.out.println("Carregando livro");
		this.livro = this.daoLivro.buscaPorId(livro.getId());
	}
	
	public String formAutor() {
		System.out.println("Chamanda do formulário do Autor.");
		return "autor?faces-redirect=true";
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component,
			Object value) throws ValidatorException {

		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage(
					"ISBN deveria começar com 1"));
		}
	}
}