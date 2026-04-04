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
import com.clinica.model.EvolucaoPaciente;
import com.clinica.model.FichaAvaliacao;
import com.clinica.model.Paciente;
import com.clinica.model.PalpacaoEnum;
import com.clinica.model.PatologiaAssociadaEnum;
import com.clinica.model.TipoDorEnum;
import com.clinica.model.TratamentoPrevioEnum;
import com.clinica.service.avaliacao.AvaliacaoService;
import com.clinica.service.evolucao.EvolucaoService;
import com.clinica.service.paciente.PacienteService;

@Named
@FlowScoped("avaliacao")
public class AvaliacaoBean implements Serializable {

	private static final long serialVersionUID = -3782246311081428880L;

	private static final String PACIENTE_NAO_SELECIONADO = "Paciente não foi selecionado.";

	private static final String PACIENTE_SEM_AVALIACAO = "Não há avaliação para o paciente selecionado.";

	private static final String ERRO_SISTEMA = "Erro ao salvar: ";

	private static final String MSG_SUCESSO = "Avaliação salva com sucesso.";

	private static final String MSG_SUCESSO_EVOLUCAO = "Evolução registrada com sucesso.";

	@Inject
	private AvaliacaoService service;

	@Inject
	private PacienteService pacienteService;

	@Inject
	private EvolucaoService evolucaoService;

	private FichaAvaliacao avaliacao = new FichaAvaliacao();

	private List<FichaAvaliacao> listaAvaliacoes;

	private List<Paciente> listaPacientes;

	private Paciente pacienteSelecionado;

	private String filtroNomePaciente;

	// Atributos para evolução do paciente
	private EvolucaoPaciente novaEvolucao = new EvolucaoPaciente();
	private List<EvolucaoPaciente> listaEvolucoes;

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

	// Métodos para evolução do paciente
	public String iniciarEvolucao(Paciente paciente) {
		if (Objects.isNull(paciente)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, PACIENTE_NAO_SELECIONADO, PACIENTE_NAO_SELECIONADO));
			return null;
		}
		
		this.pacienteSelecionado = paciente;
		
		// Carrega a avaliação mais recente do paciente
		Optional<FichaAvaliacao> optFicha = service.buscarAvaliacaoMaisRecente(paciente);
		if (optFicha.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"Paciente não possui avaliação. É necessário criar uma avaliação primeiro.", 
					"Paciente não possui avaliação. É necessário criar uma avaliação primeiro."));
			return null;
		}
		
		this.avaliacao = optFicha.get();
		this.novaEvolucao = new EvolucaoPaciente();
		this.novaEvolucao.setAvaliacao(this.avaliacao);
		
		// Carrega as evoluções existentes
		this.listaEvolucoes = evolucaoService.listarPorAvaliacao(this.avaliacao);
		
		return "evolucao";
	}
	
	public String registrarEvolucao() {
		try {
			if (Objects.isNull(novaEvolucao.getDescricao()) || novaEvolucao.getDescricao().trim().isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Descrição da evolução é obrigatória.", 
						"Descrição da evolução é obrigatória."));
				return null;
			}
			
			evolucaoService.salvar(novaEvolucao);
			
			// Atualiza a lista de evoluções
			this.listaEvolucoes = evolucaoService.listarPorAvaliacao(this.avaliacao);
			
			// Limpa o formulário para nova entrada
			this.novaEvolucao = new EvolucaoPaciente();
			this.novaEvolucao.setAvaliacao(this.avaliacao);
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_SUCESSO_EVOLUCAO, MSG_SUCESSO_EVOLUCAO));
			
			return null; // Permanece na mesma página
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					ERRO_SISTEMA + e.getMessage(), ERRO_SISTEMA + e.getMessage()));
			return null;
		}
	}
	
	public String removerEvolucao() {
		try {
			if (Objects.nonNull(novaEvolucao) && Objects.nonNull(novaEvolucao.getId())) {
				evolucaoService.remover(novaEvolucao);
				
				// Atualiza a lista de evoluções
				this.listaEvolucoes = evolucaoService.listarPorAvaliacao(this.avaliacao);
				
				// Limpa o objeto
				this.novaEvolucao = new EvolucaoPaciente();
				this.novaEvolucao.setAvaliacao(this.avaliacao);
				
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, 
						"Evolução removida com sucesso.", 
						"Evolução removida com sucesso."));
			}
			return null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao remover evolução: " + e.getMessage(), 
					"Erro ao remover evolução: " + e.getMessage()));
			return null;
		}
	}
	
	public String voltarParaAvaliacao() {
		this.pacienteSelecionado = null;
		this.avaliacao = null;
		this.novaEvolucao = null;
		this.listaEvolucoes = null;
		return "avaliacao";
	}
	
	// Getters e Setters para evolução
	public EvolucaoPaciente getNovaEvolucao() {
		return novaEvolucao;
	}

	public void setNovaEvolucao(EvolucaoPaciente novaEvolucao) {
		this.novaEvolucao = novaEvolucao;
	}

	public List<EvolucaoPaciente> getListaEvolucoes() {
		return listaEvolucoes;
	}

	public void setListaEvolucoes(List<EvolucaoPaciente> listaEvolucoes) {
		this.listaEvolucoes = listaEvolucoes;
	}

}
