package com.clinica.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class HomeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// O método irParaPacientes foi removido.
	// Agora o botão na home.xhtml deve usar action="paciente" diretamente.

	public String irParaHome() {
		return "irParaHome";
	}
	
	public String irParaPacientes() {
		return "paciente";
	}
}