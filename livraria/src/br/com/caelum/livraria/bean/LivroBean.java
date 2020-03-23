package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.tx.Transacional;
import br.com.caelum.livraria.util.RedirectView;

@Named
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();

	private Integer autorId;

	@Inject
	private LivroDao livroDao;

	@Inject
	private AutorDao autorDao;

	@Inject
	private FacesContext context;

	public Livro getLivro() {
		return livro;
	}

	private List<Livro> livros;

	public void gravarAutor() {
		if (autorId != null) {
			Autor autor = autorDao.buscaPorId(autorId);
			this.livro.adicionaAutor(autor);
		} else {
			context.addMessage("messages", new FacesMessage("O autor é obrigatário"));
			return;
		}
	}

	@Transacional
	public void gravar() {

		if (livro.getAutores().isEmpty()) {
			context.addMessage(null, new FacesMessage("O autor é obrigatário"));
			return;
		}

		if (this.livro.getId() == null) {
			livroDao.adiciona(this.livro);
			this.livros = livroDao.listaTodos();
		} else {
			livroDao.atualiza(this.livro);
		}

		this.livro = new Livro();
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public void comecaComDigitoUm(FacesContext tc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "ISBN deve começar com o número",
					"ISBN deve começar com o número"));
		}
	}

	public RedirectView formAutor() {
		return new RedirectView("autor");
	}

	@Transacional
	public void remove(Livro livro) {
		try {
			livroDao.remove(livro);
			this.livros = livroDao.listaTodos();
		} catch (Exception e) {
			throw new RuntimeException("Erro na remoção do livro");
		}
	}

	public void carregar(Livro livro) {
		this.livro = this.livroDao.buscaPorId(livro.getId());
	}

	public void removeAutorDoLivro(Autor autor) {
		this.livro.remove(autor);
	}

	public void limpar() {
		this.livro = new Livro();
	}

	public List<Autor> getAutores() {
		return autorDao.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public List<Livro> getLivros() {
		if (this.livros == null) {
			this.livros = livroDao.listaTodos();
		}
		return livros;

	}
}
