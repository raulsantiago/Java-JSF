package raul.livraria.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import raul.livraria.dao.DAO;
import raul.livraria.modelo.Autor;
//import raul.livraria.util.RedirectView;

@ManagedBean
@ViewScoped
public class AutorBean {
	private Autor autor = new Autor();
	private Integer autorId;
	
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public void carregarAutorId() {
		 //Integer id = this.autor.getId();
		   this.autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
//		   if (this.autor == null) {
//		            this.autor = new Autor();
//		   }
	}
	
	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());
		
		if(this.autor.getId() == null) {
			new DAO<Autor>(Autor.class).adiciona(this.autor);
		}else {
			new DAO<Autor>(Autor.class).atualiza(this.autor);
		}		
		
		// para limpar o formulário após digitar o nome autor
		this.autor = new Autor();
		
		//Se a classe fossse tipo String
		return "livro?faces-redirect=true";
		
		// Para clase tipo RedirectView
		//return new RedirectView("livro");		
	}
	
	public void remover(Autor autor) {
		System.out.println("Removendo autor " + autor.getNome());		
		new DAO<Autor>(Autor.class).remove(autor);
	}
	
	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	
	public Autor getAutor() {		
		return this.autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}	
	
	
	
	public void alterar(Autor autor) {
		System.out.println("Alterando autor" + this.autor.getNome() );
		this.autor = autor;
		
	}
}