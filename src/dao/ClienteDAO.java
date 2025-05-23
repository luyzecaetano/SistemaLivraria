package dao;

import java.util.List;
import modelo.Cliente;
import java.sql.SQLException;

public interface ClienteDAO {

    public void inserir(Cliente cliente) throws SQLException;

    public void atualizar(Cliente cliente) throws SQLException;

    public void remover(Cliente cliente) throws SQLException;

    public Cliente buscaId(long id);

    public List<Cliente> listar();
}
