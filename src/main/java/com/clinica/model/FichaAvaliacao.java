package com.clinica.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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

	public Date getDataAvaliacao() {
		return dataAvaliacao;
	}

	public void setDataAvaliacao(Date dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}

}
