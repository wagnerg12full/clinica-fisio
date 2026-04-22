package com.clinica.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ficha_avaliacao")
public class FichaAvaliacao implements Serializable {
	private static final long serialVersionUID = -8749899048403981049L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false) // Paciente obrigatório [cite: 7, 8]
	@JoinColumn(name = "paciente_id")
	private Paciente paciente;

	@Column(length = 1000)
	private String hda; // [cite: 2]

	@Column(name = "procurou_ortopedista")
	private boolean procurouOrtopedista; // [cite: 3]

	@Column(length = 1000)
	private String diagnosticoMedico;

	@Column(length = 1000)
	private String examesImagem; // [cite: 4]

	@Column(length = 1000)
	private String testesFisicos;

	@Column(length = 1000)
	private String testesForca; // [cite: 5]

	@Enumerated(EnumType.STRING)
	private PalpacaoEnum palpacao; // [cite: 5]

	@Column(name = "escala_dor")
	private int escalaDor; // [cite: 6]

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_dor")
	private TipoDorEnum tipoDor; // Aguda ou Crônica

	@ElementCollection(targetClass = PatologiaAssociadaEnum.class)
	@CollectionTable(name = "ficha_patologias", joinColumns = @JoinColumn(name = "ficha_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "patologia")
	private Set<PatologiaAssociadaEnum> patologiasAssociadas = new HashSet<>();

	@Column(name = "outro_patologia")
	private String outroPatologia; // Para o campo de texto ao lado de "Outros"

	@Column(name = "flag_cancer_perda_peso")
	private Boolean flagCancerPerdaPeso; // Teve câncer, perda de peso ou febre?

	@Column(name = "desc_cancer_perda_peso")
	private String descCancerPerdaPeso; // Descrição para o "Descreva:"

	@Column(length = 2000, name = "diagnosticos_previos")
	private String diagnosticosPreviosRecebidos; // Área de texto grande da imagem

	@ElementCollection(targetClass = TratamentoPrevioEnum.class)
	@CollectionTable(name = "ficha_tratamentos", joinColumns = @JoinColumn(name = "ficha_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "tratamento")
	private Set<TratamentoPrevioEnum> tratamentosPrevios = new HashSet<>();

	@Column(name = "outro_tratamento")
	private String outroTratamento; // Para o campo de texto ao lado de "Outros"

	// --- Novos Preditores de Dor (Padrão EVA 0-10) ---

	@Column(name = "catastrofizacao")
	private Integer catastrofizacao; // 0 (Nunca) a 10 (Sempre)

	@Column(name = "estresse")
	private Integer estresse; // 0 (Discordo) a 10 (Concordo)

	@Column(name = "medo_movimento")
	private Integer medoMovimento; // 0 (Discordo) a 10 (Concordo)

	@Column(name = "ansiedade")
	private Integer ansiedade; // 0 (Nada) a 10 (Muito)

	// --- Qualidade do Sono (Escala 0-3) ---

	@Column(name = "qualidade_sono")
	private Integer qualidadeSono; // 0 (Nada) a 3 (Sério)

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_avaliacao", nullable = false)
	private Date dataAvaliacao; // [cite: 7, 14]

	// --- Tarefa 1: Queixas e Histórico ---

	@Column(name = "dor_noturna", length = 1000)
	private String dorNoturna;

	@Column(name = "queixas_viscerais", length = 1000)
	private String queixasViscerais;

	@Column(name = "fatores_agravo", length = 1000)
	private String fatoresAgravo;

	@Column(name = "fatores_alivio", length = 1000)
	private String fatoresAlivio;

	@Column(name = "cirurgias", length = 1000)
	private String cirurgias;

	// --- Tarefa 1: Escala Funcional Específica ---

	@Column(name = "atividade_1_nome", length = 1000)
	private String atividade1Nome;

	@Column(name = "atividade_1_nota")
	private Integer atividade1Nota;

	@Column(name = "atividade_2_nome", length = 1000)
	private String atividade2Nome;

	@Column(name = "atividade_2_nota")
	private Integer atividade2Nota;

	@Column(name = "atividade_3_nome", length = 1000)
	private String atividade3Nome;

	@Column(name = "atividade_3_nota")
	private Integer atividade3Nota;

	// --- Tarefa 1: Achados Clínicos ---

	@ElementCollection(targetClass = AchadosClinicosEnum.class)
	@CollectionTable(name = "ficha_achados_clinicos", joinColumns = @JoinColumn(name = "ficha_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "achado")
	private Set<AchadosClinicosEnum> achadosClinicos = new HashSet<>();

	// --- Tarefa 1: Inclinometria e Dinamometria ---

	@Column(name = "inclinometria", length = 1000)
	private String inclinometria;

	@Column(name = "inclinometria_interpretacao", length = 1000)
	private String inclinometriaInterpretacao;

	@Column(name = "dinamometria", length = 1000)
	private String dinamometria;

	@Column(name = "dinamometria_interpretacao", length = 1000)
	private String dinamometriaInterpretacao;

	// --- Tarefa 2: Nova Aba "Diagnóstico e Ações" ---
	
	@Column(name = "diagnostico_cinesio_funcional", length = 1000)
	private String diagnosticoCinesioFuncional;
	
	@Column(name = "conduta_fisioterapeutica", length = 1000)
	private String condutaFisioterapeutica;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public String getHda() {
		return hda;
	}

	public void setHda(String hda) {
		this.hda = hda;
	}

	public boolean isProcurouOrtopedista() {
		return procurouOrtopedista;
	}

	public void setProcurouOrtopedista(boolean procurouOrtopedista) {
		this.procurouOrtopedista = procurouOrtopedista;
	}

	public String getDiagnosticoMedico() {
		return diagnosticoMedico;
	}

	public void setDiagnosticoMedico(String diagnosticoMedico) {
		this.diagnosticoMedico = diagnosticoMedico;
	}

	public String getExamesImagem() {
		return examesImagem;
	}

	public void setExamesImagem(String examesImagem) {
		this.examesImagem = examesImagem;
	}

	public String getTestesFisicos() {
		return testesFisicos;
	}

	public void setTestesFisicos(String testesFisicos) {
		this.testesFisicos = testesFisicos;
	}

	public String getTestesForca() {
		return testesForca;
	}

	public void setTestesForca(String testesForca) {
		this.testesForca = testesForca;
	}

	public PalpacaoEnum getPalpacao() {
		return palpacao;
	}

	public void setPalpacao(PalpacaoEnum palpacao) {
		this.palpacao = palpacao;
	}

	public int getEscalaDor() {
		return escalaDor;
	}

	public void setEscalaDor(int escalaDor) {
		this.escalaDor = escalaDor;
	}

	public TipoDorEnum getTipoDor() {
		return tipoDor;
	}

	public void setTipoDor(TipoDorEnum tipoDor) {
		this.tipoDor = tipoDor;
	}

	public Set<PatologiaAssociadaEnum> getPatologiasAssociadas() {
		return patologiasAssociadas;
	}

	public void setPatologiasAssociadas(Set<PatologiaAssociadaEnum> patologiasAssociadas) {
		this.patologiasAssociadas = patologiasAssociadas;
	}

	public String getOutroPatologia() {
		return outroPatologia;
	}

	public void setOutroPatologia(String outroPatologia) {
		this.outroPatologia = outroPatologia;
	}

	public Boolean getFlagCancerPerdaPeso() {
		return flagCancerPerdaPeso;
	}

	public void setFlagCancerPerdaPeso(Boolean flagCancerPerdaPeso) {
		this.flagCancerPerdaPeso = flagCancerPerdaPeso;
	}

	public String getDescCancerPerdaPeso() {
		return descCancerPerdaPeso;
	}

	public void setDescCancerPerdaPeso(String descCancerPerdaPeso) {
		this.descCancerPerdaPeso = descCancerPerdaPeso;
	}

	public String getDiagnosticosPreviosRecebidos() {
		return diagnosticosPreviosRecebidos;
	}

	public void setDiagnosticosPreviosRecebidos(String diagnosticosPreviosRecebidos) {
		this.diagnosticosPreviosRecebidos = diagnosticosPreviosRecebidos;
	}

	public Set<TratamentoPrevioEnum> getTratamentosPrevios() {
		return tratamentosPrevios;
	}

	public void setTratamentosPrevios(Set<TratamentoPrevioEnum> tratamentosPrevios) {
		this.tratamentosPrevios = tratamentosPrevios;
	}

	public String getOutroTratamento() {
		return outroTratamento;
	}

	public void setOutroTratamento(String outroTratamento) {
		this.outroTratamento = outroTratamento;
	}

	public Date getDataAvaliacao() {
		return dataAvaliacao;
	}

	public Integer getCatastrofizacao() {
		return catastrofizacao;
	}

	public void setCatastrofizacao(Integer catastrofizacao) {
		this.catastrofizacao = catastrofizacao;
	}

	public Integer getEstresse() {
		return estresse;
	}

	public void setEstresse(Integer estresse) {
		this.estresse = estresse;
	}

	public Integer getMedoMovimento() {
		return medoMovimento;
	}

	public void setMedoMovimento(Integer medoMovimento) {
		this.medoMovimento = medoMovimento;
	}

	public Integer getAnsiedade() {
		return ansiedade;
	}

	public void setAnsiedade(Integer ansiedade) {
		this.ansiedade = ansiedade;
	}

	public Integer getQualidadeSono() {
		return qualidadeSono;
	}

	public void setQualidadeSono(Integer qualidadeSono) {
		this.qualidadeSono = qualidadeSono;
	}

	public void setDataAvaliacao(Date dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}

	public String getDorNoturna() {
		return dorNoturna;
	}

	public void setDorNoturna(String dorNoturna) {
		this.dorNoturna = dorNoturna;
	}

	public String getQueixasViscerais() {
		return queixasViscerais;
	}

	public void setQueixasViscerais(String queixasViscerais) {
		this.queixasViscerais = queixasViscerais;
	}

	public String getFatoresAgravo() {
		return fatoresAgravo;
	}

	public void setFatoresAgravo(String fatoresAgravo) {
		this.fatoresAgravo = fatoresAgravo;
	}

	public String getFatoresAlivio() {
		return fatoresAlivio;
	}

	public void setFatoresAlivio(String fatoresAlivio) {
		this.fatoresAlivio = fatoresAlivio;
	}

	public String getCirurgias() {
		return cirurgias;
	}

	public void setCirurgias(String cirurgias) {
		this.cirurgias = cirurgias;
	}

	public String getAtividade1Nome() {
		return atividade1Nome;
	}

	public void setAtividade1Nome(String atividade1Nome) {
		this.atividade1Nome = atividade1Nome;
	}

	public Integer getAtividade1Nota() {
		return atividade1Nota;
	}

	public void setAtividade1Nota(Integer atividade1Nota) {
		this.atividade1Nota = atividade1Nota;
	}

	public String getAtividade2Nome() {
		return atividade2Nome;
	}

	public void setAtividade2Nome(String atividade2Nome) {
		this.atividade2Nome = atividade2Nome;
	}

	public Integer getAtividade2Nota() {
		return atividade2Nota;
	}

	public void setAtividade2Nota(Integer atividade2Nota) {
		this.atividade2Nota = atividade2Nota;
	}

	public String getAtividade3Nome() {
		return atividade3Nome;
	}

	public void setAtividade3Nome(String atividade3Nome) {
		this.atividade3Nome = atividade3Nome;
	}

	public Integer getAtividade3Nota() {
		return atividade3Nota;
	}

	public void setAtividade3Nota(Integer atividade3Nota) {
		this.atividade3Nota = atividade3Nota;
	}

	public Set<AchadosClinicosEnum> getAchadosClinicos() {
		return achadosClinicos;
	}

	public void setAchadosClinicos(Set<AchadosClinicosEnum> achadosClinicos) {
		this.achadosClinicos = achadosClinicos;
	}

	public String getInclinometria() {
		return inclinometria;
	}

	public void setInclinometria(String inclinometria) {
		this.inclinometria = inclinometria;
	}

	public String getInclinometriaInterpretacao() {
		return inclinometriaInterpretacao;
	}

	public void setInclinometriaInterpretacao(String inclinometriaInterpretacao) {
		this.inclinometriaInterpretacao = inclinometriaInterpretacao;
	}

	public String getDinamometria() {
		return dinamometria;
	}

	public void setDinamometria(String dinamometria) {
		this.dinamometria = dinamometria;
	}

	public String getDinamometriaInterpretacao() {
		return dinamometriaInterpretacao;
	}

	public void setDinamometriaInterpretacao(String dinamometriaInterpretacao) {
		this.dinamometriaInterpretacao = dinamometriaInterpretacao;
	}

	public String getDiagnosticoCinesioFuncional() {
		return diagnosticoCinesioFuncional;
	}

	public void setDiagnosticoCinesioFuncional(String diagnosticoCinesioFuncional) {
		this.diagnosticoCinesioFuncional = diagnosticoCinesioFuncional;
	}

	public String getCondutaFisioterapeutica() {
		return condutaFisioterapeutica;
	}

	public void setCondutaFisioterapeutica(String condutaFisioterapeutica) {
		this.condutaFisioterapeutica = condutaFisioterapeutica;
	}

}
