package com.clinica.controller;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.clinica.model.AchadosClinicosEnum;
import com.clinica.model.FichaAvaliacao;
import com.clinica.model.Paciente;
import com.clinica.model.PalpacaoEnum;
import com.clinica.model.PatologiaAssociadaEnum;
import com.clinica.model.TipoDorEnum;
import com.clinica.model.TratamentoPrevioEnum;
import com.clinica.service.avaliacao.AvaliacaoService;
import com.clinica.service.paciente.PacienteService;

@Named
@FlowScoped("avaliacao")
public class AvaliacaoBean implements Serializable {

	private static final long serialVersionUID = -3782246311081428880L;

	private static final String PACIENTE_NAO_SELECIONADO = "Paciente não foi selecionado.";

	private static final String PACIENTE_SEM_AVALIACAO = "Não há avaliação para o paciente selecionado.";

	private static final String ERRO_SISTEMA = "Erro ao salvar: ";

	private static final String MSG_SUCESSO = "Avaliação salva com sucesso.";

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

	public TipoDorEnum[] getTiposDor() {
		return TipoDorEnum.values();
	}

	public PatologiaAssociadaEnum[] getPatologias() {
		return PatologiaAssociadaEnum.values();
	}

	public TratamentoPrevioEnum[] getTratamentos() {
		return TratamentoPrevioEnum.values();
	}

	public AchadosClinicosEnum[] getAchadosClinicosValues() {
		return AchadosClinicosEnum.values();
	}

	@PostConstruct
	public void inicializar() {
		this.avaliacao = new FichaAvaliacao();
		this.avaliacao.setPatologiasAssociadas(new HashSet<>());
		this.avaliacao.setTratamentosPrevios(new HashSet<>());
		this.avaliacao.setAchadosClinicos(new HashSet<>());
		this.avaliacao.setCatastrofizacao(0);
		this.avaliacao.setEstresse(0);
		this.avaliacao.setMedoMovimento(0);
		this.avaliacao.setAnsiedade(0);
		this.avaliacao.setQualidadeSono(0);
		this.avaliacao.setAtividade1Nota(0);
		this.avaliacao.setAtividade2Nota(0);
		this.avaliacao.setAtividade3Nota(0);
	}

	public String salvar() {
		try {
			avaliacao.setPaciente(pacienteSelecionado);
			if (Objects.isNull(avaliacao.getId())) {
				avaliacao.setDataAvaliacao(new java.util.Date());
			}
			service.salvar(avaliacao);
			reset();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_SUCESSO, MSG_SUCESSO));
			return "avaliacao";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					ERRO_SISTEMA + e.getMessage(), ERRO_SISTEMA + e.getMessage()));
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
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, PACIENTE_NAO_SELECIONADO, PACIENTE_NAO_SELECIONADO));
			return null;
		}
		this.pacienteSelecionado = p;
		return "avaliacoes";
	}

	public String carregarAvaliacaoAtual(Paciente p) {
		if (Objects.nonNull(p)) {
			Optional<FichaAvaliacao> optFicha = service.buscarAvaliacaoMaisRecente(p);
			if (optFicha.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, PACIENTE_SEM_AVALIACAO, PACIENTE_SEM_AVALIACAO));
				return null;
			}
			this.pacienteSelecionado = p;
			this.avaliacao = optFicha.get();

			// Tratamento para registros antigos com campos nulos
			if (this.avaliacao.getCatastrofizacao() == null)
				this.avaliacao.setCatastrofizacao(0);
			if (this.avaliacao.getEstresse() == null)
				this.avaliacao.setEstresse(0);
			if (this.avaliacao.getMedoMovimento() == null)
				this.avaliacao.setMedoMovimento(0);
			if (this.avaliacao.getAnsiedade() == null)
				this.avaliacao.setAnsiedade(0);
			if (this.avaliacao.getQualidadeSono() == null)
				this.avaliacao.setQualidadeSono(0);
			if (this.avaliacao.getAtividade1Nota() == null)
				this.avaliacao.setAtividade1Nota(0);
			if (this.avaliacao.getAtividade2Nota() == null)
				this.avaliacao.setAtividade2Nota(0);
			if (this.avaliacao.getAtividade3Nota() == null)
				this.avaliacao.setAtividade3Nota(0);

			this.avaliacao.setAchadosClinicos(new HashSet<>(this.avaliacao.getAchadosClinicos()));
			this.avaliacao.setPatologiasAssociadas(new HashSet<>(this.avaliacao.getPatologiasAssociadas()));
			this.avaliacao.setTratamentosPrevios(new HashSet<>(this.avaliacao.getTratamentosPrevios()));

			return "avaliacoes";
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, PACIENTE_NAO_SELECIONADO, PACIENTE_NAO_SELECIONADO));
		return null;
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
