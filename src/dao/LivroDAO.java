package dao;

import java.util.List;
import java.sql.SQLException;
import modelo.Livro;

public interface LivroDAO {

    public void inserir(Livro livro) throws SQLException;

    public void atualizar(Livro livro) throws SQLException;

    public void remover(Livro livro) throws SQLException;

    public Livro buscaId(long id);

    public List<Livro> listar();
}
