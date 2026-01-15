package com.clinica.service.email;

import javax.ejb.Stateless;

@Stateless
public class EmailService {

	public void enviar(String remetente, String destinatario, String assunto, String corpo) {
		// Simulação do envio
		System.out.println("Enviando e-mail de: " + remetente);
		System.out.println("Para: " + destinatario);
		System.out.println("Assunto: " + assunto);
		System.out.println("Mensagem: " + corpo);
	}
}
