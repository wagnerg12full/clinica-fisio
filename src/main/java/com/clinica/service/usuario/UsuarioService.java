package com.clinica.service.usuario;

import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.clinica.model.Usuario;

@Stateless
public class UsuarioService {

	@PersistenceContext(unitName = "clinicaPU")
	private EntityManager em;

	public void criar(Usuario usuario) {
		em.persist(usuario);
	}

	/**
	 * Recupera um usuário através do username de forma segura. Utiliza
	 * getResultList para evitar NoResultException.
	 */
	public Optional<Usuario> buscarPorLogin(String username) {
		List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u WHERE u.username = :login", Usuario.class)
				.setParameter("login", username).getResultList();

		return usuarios.isEmpty() ? Optional.empty() : Optional.ofNullable(usuarios.get(0));
	}

	public void atualizar(Usuario usuario) {
		em.merge(usuario);
	}
}