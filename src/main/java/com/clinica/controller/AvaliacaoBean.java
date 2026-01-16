package com.clinica.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.clinica.model.FichaAvaliacao;
import com.clinica.model.Paciente;
import com.clinica.model.PalpacaoEnum;
import com.clinica.service.avaliacao.AvaliacaoService;

@Named
@FlowScoped("avaliacao")
public class AvaliacaoBean implements Serializable {

	private static final long serialVersionUID = -3782246311081428880L;

	@Inject
	private AvaliacaoService service;

	private FichaAvaliacao avaliacao = new FichaAvaliacao();

	private List<FichaAvaliacao> listaAvaliacoes;

	private Paciente pacienteSelecionado;

	private String filtroNomePaciente;

	public void pesquisar() {
		this.listaAvaliacoes = service.listarPorFiltro(filtroNomePaciente);
	}

	public PalpacaoEnum[] getPalpacoes() {
		return PalpacaoEnum.values();
	}

	public String getDescricaoDor() {
		int dor = avaliacao.getEscalaDor();
		if (dor <= 2)
			return "LEVE"; // 0 a 2 [cite: 10]
		if (dor <= 7)
			return "MODERADA"; // 3 a 7 [cite: 11]
		return "INTENSA"; // 8 a 10 [cite: 11]
	}

	public String getCorDor() {
		int dor = avaliacao.getEscalaDor();
		if (dor <= 2)
			return "#228B22"; // Verde Floresta
		if (dor <= 7)
			return "#DAA520"; // Dourado (Amarelo escuro)
		return "#FF0000"; // Vermelho
	}

	public void inicializar() {
		this.avaliacao = new FichaAvaliacao();
		this.avaliacao.setDataAvaliacao(new java.util.Date());

		Paciente p = (Paciente) FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.get("pacienteParaAvaliacao");

		if (p != null) {
			this.avaliacao.setPaciente(this.pacienteSelecionado);
		}
	}

	public String salvar() {
		service.salvar(avaliacao);
		return "voltarParaPacientes";
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
