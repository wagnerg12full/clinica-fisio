package com.clinica.service.avaliacao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.clinica.model.FichaAvaliacao;

@Stateless
public class AvaliacaoService {
	@PersistenceContext
	private EntityManager em;

	public void salvar(FichaAvaliacao ficha) {
		if (ficha.getId() == null) {
			ficha.setDataAvaliacao(new Date()); // Preenchimento automático [cite: 14]
			em.persist(ficha);
		} else {
			em.merge(ficha);
		}
	}

	/**
	 * Lista as avaliações filtrando pelo nome do paciente. Se o filtro for nulo ou
	 * vazio, retorna todas as avaliações.
	 */
	public List<FichaAvaliacao> listarPorFiltro(String nomePaciente) {
		// Usamos JOIN FETCH para carregar o objeto Paciente junto com a Ficha
		// Evitando múltiplas consultas ao banco durante a renderização da tabela
		StringBuilder jpql = new StringBuilder("SELECT f FROM FichaAvaliacao f JOIN FETCH f.paciente p ");

		if (nomePaciente != null && !nomePaciente.trim().isEmpty()) {
			jpql.append("WHERE UPPER(p.nome) LIKE UPPER(:nome) ");
		}

		jpql.append("ORDER BY f.dataAvaliacao DESC");

		TypedQuery<FichaAvaliacao> query = em.createQuery(jpql.toString(), FichaAvaliacao.class);

		if (nomePaciente != null && !nomePaciente.trim().isEmpty()) {
			query.setParameter("nome", "%" + nomePaciente + "%");
		}

		return query.getResultList();
	}
}
