package com.clinica.scheduler;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.clinica.model.Paciente;

@Singleton
@Startup
public class AniversarioScheduler {

	@PersistenceContext
	private EntityManager em;

	@Inject
	@JMSConnectionFactory("java:/JmsXA")
	private JMSContext jmsContext;

	@Resource(lookup = "java:/jms/queue/EmailQueue")
	private Queue emailQueue;

	@Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
	public void verificarAniversariantes() {

		System.out.println(">>> [DEBUG] Verificando aniversariantes no PostgreSQL...");

		// JPQL para buscar por dia e mês ignorando o ano
		List<Paciente> aniversariantes = em.createQuery(
				"SELECT p FROM Paciente p WHERE FUNCTION('day', p.dataNascimento) = FUNCTION('day', CURRENT_DATE) "
						+ "AND FUNCTION('month', p.dataNascimento) = FUNCTION('month', CURRENT_DATE)",
				Paciente.class).getResultList();

		if (aniversariantes.isEmpty()) {
			System.out.println(">>> [DEBUG] Nenhum aniversariante encontrado neste segundo.");
		}

		for (Paciente p : aniversariantes) {
			String corpo = "Prezado " + p.getNome()
					+ ", hoje é o dia mais importante da sua vida, desejamos toda a sorte de alegrias. Feliz aniversário";

			// Enviamos apenas os dados necessários para a fila (Título|Email|Corpo)
			// Em uma arquitetura real, poderíamos enviar um objeto JSON ou Serializado
			String mensagem = p.getEmail() + ";" + corpo;
			jmsContext.createProducer().send(emailQueue, mensagem);

			System.out.println(">>> [JMS] Mensagem enviada para a fila para o paciente: " + p.getNome());

		}
	}
}