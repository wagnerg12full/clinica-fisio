package com.clinica.messaging;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.clinica.service.email.EmailService;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/EmailQueue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class EmailMDB implements MessageListener {

	@Inject
	private EmailService emailService;

	@Override
	public void onMessage(Message rcvMessage) {
		try {
			if (rcvMessage instanceof TextMessage) {
				String payload = ((TextMessage) rcvMessage).getText();
				String[] dados = payload.split(";");

				String emailDestino = dados[0];
				String mensagem = dados[1];

				emailService.enviar("contato@clinicafisioterapia.com", emailDestino, "Feliz Aniversário!", mensagem);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Aqui o JMS gerencia o rollback e retentativa
		}
	}
}