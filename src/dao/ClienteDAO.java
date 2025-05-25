package dao;

import modelo.Cliente;
import java.util.List;

public interface ClienteDAO {

    public void inserir(Cliente cliente);

    public void atualizar(Cliente cliente);

    public void remover(Cliente cliente);

    public Cliente buscaId(long id);

    public List<Cliente> listar();
}
