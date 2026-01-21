package com.clinica.controller;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.clinica.model.Paciente;
import com.clinica.service.paciente.PacienteService;

@Named
@FlowScoped("paciente") // Deve ser idêntico ao nome da pasta e do ID no XML
public class PacienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PacienteService pacienteService;

	private Paciente paciente;
	private List<Paciente> listaPacientes;

	@PostConstruct
	public void init() {

	}

	public void consultar() {
		this.listaPacientes = pacienteService.listarTodos();
	}

	/**
	 * Salva o paciente e encerra o fluxo, retornando para a Home.
	 */
	public String salvar() {
		try {
			pacienteService.salvar(getPaciente());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Paciente salvo com sucesso!"));

			// Retorna o ID do flow-return definido no XML
			return "paciente";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar: " + e.getMessage()));
			return null;
		}
	}

	/**
	 * Prepara a edição e navega para o nó 'pacientes' (formulário).
	 */
	public String editar(Paciente p) {
		this.paciente = p;
		return "pacientes";
	}

	public String novoPaciente() {
		this.listaPacientes = List.of();
		this.paciente = new Paciente();
		return "pacientes";
	}

	// --- Getters e Setters ---

	public Paciente getPaciente() {
		if (paciente == null) {
			paciente = new Paciente();
		}
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public List<Paciente> getListaPacientes() {
		return listaPacientes;
	}

	public void setListaPacientes(List<Paciente> listaPacientes) {
		this.listaPacientes = listaPacientes;
	}
}