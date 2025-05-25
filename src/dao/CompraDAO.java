package dao;

import modelo.Compra;
import java.util.List;

public interface CompraDAO {

    public void inserir(Compra compra);

    public void atualizar(List<Compra> compras);

    public void remover(int codPedido);

    public List<Compra> buscaCod(int codPedido);

    public List<Compra> listar();
    
    public int buscarUltimoCod();
    
    public List<Compra> listarPorCliente(Long idCliente);
}
