package dao;

import modelo.Editora;
import java.util.List;

public interface EditoraDAO {
    
    public void inserir(Editora editora);
    
    public void atualizar(Editora editora);
    
    public void remover(Editora editora);
    
    public Editora buscaId(long id);
    
    public List<Editora> listar();
}
