package dao;

import modelo.Compra;
import java.util.List;
import java.sql.SQLException;

public interface CompraDAO {

    public void inserir(Compra compra) throws SQLException;

    public void atualizar(Compra compra) throws SQLException;

    public void remover(Compra compra) throws SQLException;

    public Compra buscaId(long id);

    public List<Compra> listar();
}
