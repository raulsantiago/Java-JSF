package raul.livraria.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import raul.livraria.dao.UsuarioDao;
import raul.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {
	
	private Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}

	public String efetuaLogin() {
		System.out.println("Fazendo login do usuaro: " + this.usuario.getEmail());
		
		// Vai guardar na chave "usuarioLogado" qual o usuário está logado
		FacesContext context = FacesContext.getCurrentInstance();
		boolean existe = new UsuarioDao().existe(this.usuario);
		if(existe) {			
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return "livro?faces-redirect=true";			
		}
		
		// Manter as mensagens abaixo entre duas requisições
		context.getExternalContext().getFlash().setKeepMessages(true);
		// Exibe essa mensagem na view quando chamar esse metodo
		context.addMessage(null, new FacesMessage("Usuário não encontrado"));
		
		return "login?faces-redirect=true";
	}
	
	public String deslogar() {
		// Vai remover a chave "usuarioLogado" qual o usuário está logado e não mais
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		
		return "login?faces-redirect=true";
		
	}
	
	
	
}
