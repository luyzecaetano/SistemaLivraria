package dao;

import java.util.List;
import modelo.Livro;

public interface LivroDAO {

    public void inserir(Livro livro);

    public void atualizar(Livro livro);

    public void remover(Livro livro);

    public Livro buscaId(long id);

    public List<Livro> listar();
}
