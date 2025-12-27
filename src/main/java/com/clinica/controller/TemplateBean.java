package com.clinica.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped // Mantém o estado (aberto/fechado) durante toda a navegação do usuário
public class TemplateBean implements Serializable {

	private static final long serialVersionUID = -7364017616419030616L;
	private boolean menuRecolhido = false;

	public void toggleMenu() {
		menuRecolhido = !menuRecolhido;
	}

	public boolean isMenuRecolhido() {
		return menuRecolhido;
	}

	public void setMenuRecolhido(boolean menuRecolhido) {
		this.menuRecolhido = menuRecolhido;
	}
}