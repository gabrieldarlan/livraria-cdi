package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.util.RedirectView;

@Named
@ViewScoped
public class AutorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Autor autor = new Autor();
	private Integer autorId;

	@Inject
	private AutorDao autorDao; // CDI fas new no AutorDao e injeta

	public void carregarPelaId() {
		this.autor = this.autorDao.buscaPorId(autorId);
	}

	public RedirectView gravar() {

		if (this.autor.getId() == null) {
			this.autorDao.adiciona(this.autor);
		} else {
			this.autorDao.atualiza(this.autor);
		}

		this.setAutor(new Autor());
		return new RedirectView("livro");
	}

	public List<Autor> getAutores() {
		return this.autorDao.listaTodos();
	}

	public void remover(Autor autor) {
		this.autorDao.remove(autor);
	}

	public void carregar(Autor autor) {
		this.autor = autor;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public RedirectView formLivro() {
		return new RedirectView("livro");
	}
}
