package com.clinica.service.paciente;

import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.clinica.model.Paciente;

@Stateless
public class PacienteService {

	@PersistenceContext(unitName = "clinicaPU")
	private EntityManager em;

	/**
	 * Salva ou atualiza um paciente.
	 */
	public void salvar(Paciente paciente) {
		if (paciente.getId() == null) {
			em.persist(paciente);
		} else {
			em.merge(paciente);
		}
	}

	/**
	 * Remove um paciente do banco de dados.
	 */
	public void excluir(Paciente paciente) {
		// Garante que o objeto esteja no estado 'managed' antes de remover
		paciente = em.merge(paciente);
		em.remove(paciente);
	}

	/**
	 * Busca um paciente pelo ID utilizando Optional.
	 */
	public Optional<Paciente> buscarPorId(Long id) {
		return Optional.ofNullable(em.find(Paciente.class, id));
	}

	/**
	 * Retorna a lista de todos os pacientes cadastrados.
	 */
	public List<Paciente> listarTodos() {
		return em.createQuery("SELECT p FROM Paciente p ORDER BY p.nome", Paciente.class).getResultList();
	}

	/**
	 * Busca pacientes por parte do nome (Case Insensitive).
	 */
	public List<Paciente> buscarPorNome(String nome) {
		return em.createQuery("SELECT p FROM Paciente p WHERE UPPER(p.nome) LIKE :nome", Paciente.class)
				.setParameter("nome", "%" + nome.toUpperCase() + "%").getResultList();
	}
}