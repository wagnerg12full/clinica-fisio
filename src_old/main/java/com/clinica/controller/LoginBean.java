package com.clinica.controller;

import java.io.Serializable;
import java.util.Optional;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.clinica.model.Usuario;
import com.clinica.service.usuario.UsuarioService;

@Named
@SessionScoped // Essencial para manter o estado 'logado' entre as páginas
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String usuario;
	private String senha;
	private boolean logado = false;

	@Inject
	private UsuarioService usuarioService;

	/**
	 * Realiza a autenticação. O sufixo ?faces-redirect=true instrui o JSF a enviar
	 * um HTTP 302, mudando a URL no navegador e evitando o erro de "URL presa".
	 * 
	 * psql -h ip_servidor -p 5432 -U postgres -d clinicafisio
	 * 
	 * insert into usuario values ( 1, current_timestamp, true, true, 'admin',
	 * 'admin');
	 */
	public String efetuarLogin() {

		Optional<Usuario> usuarioOpt = usuarioService.buscarPorLogin(this.usuario);

		if (usuarioOpt.isPresent() && usuarioOpt.get().isFlgAtivo()
				&& usuarioOpt.get().getPassword().equals(this.senha)) {

			this.logado = true;

			// Grava o username na sessão conforme sua instrução anterior
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);

			return "irParaHome";
		}

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Login ou senha inválidos"));
		return null;
	}

	public String logout() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		// Invalida a sessão atual no servidor (WildFly)
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "irParaLogin";
	}

	// Getters e Setters
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isLogado() {
		return logado;
	}
}