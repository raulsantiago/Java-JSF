package raul.livraria.bean;

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
public class LivroBean {
	
	private Livro livro = new Livro();
	private Integer autorId;
	
	

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Livro getLivro() {
		return livro;
	}
	
	public List<Autor> getAutores(){
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public List<Livro> getLivros(){
		return new DAO<Livro>(Livro.class).listaTodos();
	}
	
	public List<Autor> getAutoresDoLivro(){
		return this.livro.getAutores();
	}
	
	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo() );		
		if (livro.getAutores().isEmpty()) {
			// throw new RuntimeException("Livro deve ter pelo menos um Autor.");
			// Vamos apresentar uma mensagem de exceção mais elegante
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}
		new DAO<Livro>(Livro.class).adiciona(this.livro);
		}
	
	// Validador personalizado 
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
	    String valor = value.toString();
	    if (!valor.startsWith("1")) {
	        throw new ValidatorException(new FacesMessage("ISBN deve começar com 1"));
	    }
	}
	

}
