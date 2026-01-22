package com.clinica.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.clinica.model.FichaAvaliacao;
import com.clinica.model.Paciente;
import com.clinica.model.PalpacaoEnum;
import com.clinica.service.avaliacao.AvaliacaoService;
import com.clinica.service.paciente.PacienteService;

@Named
@FlowScoped("avaliacao")
public class AvaliacaoBean implements Serializable {

	private static final long serialVersionUID = -3782246311081428880L;

	@Inject
	private AvaliacaoService service;

	@Inject
	private PacienteService pacienteService;

	private FichaAvaliacao avaliacao = new FichaAvaliacao();

	private List<FichaAvaliacao> listaAvaliacoes;

	private List<Paciente> listaPacientes;

	private Paciente pacienteSelecionado;

	private String filtroNomePaciente;

	public void pesquisar() {
		this.listaPacientes = pacienteService.buscarPorNome(filtroNomePaciente);
	}

	public PalpacaoEnum[] getPalpacoes() {
		return PalpacaoEnum.values();
	}

	@PostConstruct
	public void inicializar() {
		this.avaliacao = new FichaAvaliacao();
	}

	public String salvar() {
		try {
			avaliacao.setPaciente(pacienteSelecionado);
			avaliacao.setDataAvaliacao(new java.util.Date());
			service.salvar(avaliacao);
			reset();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Avaliação salva com sucesso", "Avaliação salva com sucesso."));
			return "avaliacao";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao salvar: " + e.getMessage(), "Erro ao salvar: " + e.getMessage()));
			return null;
		}
	}

	private void reset() {
		setFiltroNomePaciente(null);
		this.listaPacientes = List.of();
		this.pacienteSelecionado = null;
		inicializar();
	}

	public String cancelar() {
		reset();
		return "avaliacao";
	}

	public String iniciarAvaliacao(Paciente p) {
		if (Objects.isNull(p)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Paciente não foi selecionado.", "Paciente não foi selecionado."));
			return null;
		}
		this.pacienteSelecionado = p;
		return "avaliacoes";
	}

	// Getters e Setters
	public FichaAvaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(FichaAvaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	// Getters e Setters
	public List<FichaAvaliacao> getListaAvaliacoes() {
		return listaAvaliacoes;
	}

	public void setListaAvaliacoes(List<FichaAvaliacao> listaAvaliacoes) {
		this.listaAvaliacoes = listaAvaliacoes;
	}

	public List<Paciente> getListaPacientes() {
		return listaPacientes;
	}

	public void setListaPacientes(List<Paciente> listaPacientes) {
		this.listaPacientes = listaPacientes;
	}

	public String getFiltroNomePaciente() {
		return filtroNomePaciente;
	}

	public void setFiltroNomePaciente(String filtroNomePaciente) {
		this.filtroNomePaciente = filtroNomePaciente;
	}

	public Paciente getPacienteSelecionado() {
		return pacienteSelecionado;
	}

	public void setPacienteSelecionado(Paciente pacienteSelecionado) {
		this.pacienteSelecionado = pacienteSelecionado;
	}

}
