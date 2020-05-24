package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;


// Anotação criada para associar esta classe para outro metodos de outra classe
@SuppressWarnings("serial")
@Transacional
@Interceptor // Tem que adicionar a classe no arquivo beans.xml
public class GerenciadorDeTransacao implements Serializable {
	
	@Inject
	EntityManager manager;
	
	@AroundInvoke
	public Object executaTX(InvocationContext contexto) throws Exception {
		
		// abre transacao
		manager.getTransaction().begin();
		System.out.println("Abrindo TX com Begin");
		
		// chamar os daos qyue precisam de um TX
		Object resultado = contexto.proceed();		

		// commita a transacao
		manager.getTransaction().commit();
		System.out.println("Fechando TX com Commit");
		
		return resultado;
	}

}
