package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Venda;

public class VendaDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager manager;

	public List<Venda> getVendas() {
		List<Venda> vendas = this.manager.createQuery("select v from Venda v", Venda.class).getResultList();
		return vendas;
	}
}
