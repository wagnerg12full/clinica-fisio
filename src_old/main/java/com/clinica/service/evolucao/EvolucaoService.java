package com.clinica.service.evolucao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.clinica.model.EvolucaoPaciente;
import com.clinica.model.FichaAvaliacao;

@Stateless
public class EvolucaoService {
	@PersistenceContext
	private EntityManager em;

	public void salvar(EvolucaoPaciente evolucao) {
		if (evolucao.getId() == null) {
			evolucao.setDataRegistro(new Date()); // Preenchimento automático da data atual
			em.persist(evolucao);
		} else {
			em.merge(evolucao);
		}
	}

	/**
	 * Lista todas as evoluções de uma avaliação específica, ordenadas por data decrescente
	 */
	public List<EvolucaoPaciente> listarPorAvaliacao(FichaAvaliacao avaliacao) {
		TypedQuery<EvolucaoPaciente> query = em.createQuery(
			"SELECT e FROM EvolucaoPaciente e WHERE e.avaliacao = :avaliacao ORDER BY e.dataRegistro DESC",
			EvolucaoPaciente.class
		);
		query.setParameter("avaliacao", avaliacao);
		return query.getResultList();
	}

	/**
	 * Busca uma evolução pelo ID
	 */
	public EvolucaoPaciente buscarPorId(Long id) {
		return em.find(EvolucaoPaciente.class, id);
	}

	/**
	 * Remove uma evolução (embora a especificação diga que não será excluída,
	 * mantemos o método para casos excepcionais)
	 */
	public void remover(EvolucaoPaciente evolucao) {
		em.remove(em.contains(evolucao) ? evolucao : em.merge(evolucao));
	}
}