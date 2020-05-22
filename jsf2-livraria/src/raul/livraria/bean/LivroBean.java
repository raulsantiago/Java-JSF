package raul.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import raul.livraria.dao.DAO;
import raul.livraria.modelo.Autor;
import raul.livraria.modelo.Livro;

@ManagedBean
@ViewScoped		// Para pode fazer vários request
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Livro livro = new Livro();
	private Integer autorId;
	private Integer livroId;

	private List<Livro> livros;
	
	
	public Livro getLivro() {
		return livro;
	}

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	
	public List<Autor> getAutores(){
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public List<Livro> getLivros(){
		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		if(this.livros == null) {
			this.livros = dao.listaTodos();
		}		
		return livros;
		
	}
	
	public List<Autor> getAutoresDoLivro(){
		return this.livro.getAutores();
	}
	
	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
		System.out.println("Escrito por: " + autor.getNome());
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo() );		
		if (livro.getAutores().isEmpty()) {
			// throw new RuntimeException("Livro deve ter pelo menos um Autor.");
			// Vamos apresentar uma mensagem de exceção mais elegante
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}
		
		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		if(this.livro.getId() == null) {
			dao.adiciona(this.livro);
			this.livros = dao.listaTodos();
		}else {
			new DAO<Livro>(Livro.class).atualiza(this.livro);
		}
		this.livro = new Livro();
	}
	
	public void alterar(Livro livro) {
		System.out.println("Alterando livro " + this.livro.getTitulo() );
		this.livro = livro;
		
	}
	
	public void remover(Livro livro) {
		System.out.println("Revomendo livro " + this.livro.getTitulo() );
		new DAO<Livro>(Livro.class).remove(livro);
	}
	
	// Removendo Foren Key
	public void removerAutorDoLivro(Autor autor) {
		System.out.println("Revomendo auto do livro ");
		// Não é uma boa prática
		//this.livro.getAutores().remove(autor);
		
		// Melhor pratica criar o metodo tmb
		this.livro.removeAutor(autor);
		
	}
	
	public String formAutor() {
		System.out.println("Chamando o formulário do Autor");
		return "autor?faces-redirect=true";
		
	}
	
	// Validador personalizado
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
	    String valor = value.toString();
	    if (!valor.startsWith("9")) {
	        throw new ValidatorException(new FacesMessage("ISBN deve começar com 9"));
	    }
	}
	
	public void carregarLivroId() {
		// Integer id = this.livro.getId();
		   this.livro = new DAO<Livro>(Livro.class).buscaPorId(livroId);
//		   if (this.livro == null) {
//		            this.livro = new Livro();
//		   }
	}
	
	

}
