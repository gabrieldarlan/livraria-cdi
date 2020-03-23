package br.com.caelum.livraria.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Usuario;

public class UsuarioDao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager manager;

	public boolean existe(Usuario usuario) {

		TypedQuery<Usuario> query = manager
				.createQuery("select u from Usuario u where u.email = :pEmail and u.senha = :pSenha", Usuario.class);
		query.setParameter("pSenha", usuario.getSenha());
		query.setParameter("pEmail", usuario.getEmail());

		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

}
