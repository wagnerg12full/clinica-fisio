package com.clinica.service.evolucao;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
			ZonedDateTime agora = ZonedDateTime.now(ZoneId.of("America/Fortaleza"));
			evolucao.setDataRegistro(Date.from(agora.toInstant()));
			em.persist(evolucao);
		} else {
			em.merge(evolucao);
		}
	}

	public List<EvolucaoPaciente> listarPorAvaliacao(FichaAvaliacao avaliacao) {
		TypedQuery<EvolucaoPaciente> query = em.createQuery(
				"SELECT e FROM EvolucaoPaciente e WHERE e.avaliacao = :avaliacao ORDER BY e.dataRegistro DESC",
				EvolucaoPaciente.class);
		query.setParameter("avaliacao", avaliacao);
		return query.getResultList();
	}

	public EvolucaoPaciente buscarPorId(Long id) {
		return em.find(EvolucaoPaciente.class, id);
	}

	public void remover(EvolucaoPaciente evolucao) {
		em.remove(em.contains(evolucao) ? evolucao : em.merge(evolucao));
	}
}
