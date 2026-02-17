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

}
