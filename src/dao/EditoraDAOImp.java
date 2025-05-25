package dao;

import modelo.Editora;
import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class EditoraDAOImp implements EditoraDAO {

    private Connection conn;
    
    public EditoraDAOImp() {
        this.conn = new ConnectionFactory().getConnection();
    }
    
    @Override
    public void inserir(Editora editora) {
        String sql = "INSERT INTO editora(nome,gerente,codigo,endereco,telefone) VALUES (?,?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, editora.getNome());
            ps.setString(2, editora.getGerente());
            ps.setString(3, editora.getCodigo());
            ps.setString(4, editora.getEndereco());
            ps.setString(5, editora.getTelefone());
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir editora: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void atualizar(Editora editora) {
        String sql = "UPDATE editora SET nome=?,gerente=?,codigo=?,endereco=?,telefone=? WHERE id_editora=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, editora.getNome());
            ps.setString(2, editora.getGerente());
            ps.setString(3, editora.getCodigo());
            ps.setString(4, editora.getEndereco());
            ps.setString(5, editora.getTelefone());
            ps.setLong(6, editora.getId_editora());

            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao editar editora: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void remover(Editora editora) {
        String sql = "UPDATE editora SET ativo = 'N' WHERE id_editora=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, editora.getId_editora());
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao remover editora: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Editora buscaId(long id) {
        String sql = "SELECT * FROM editora WHERE id_editora=? AND ativo='S'";
        Editora editora = new Editora();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    editora.setId_editora(rs.getLong("id_editora"));
                    editora.setNome(rs.getString("nome"));
                    editora.setGerente(rs.getString("gerente"));
                    editora.setCodigo(rs.getString("codigo"));
                    editora.setEndereco(rs.getString("endereco"));
                    editora.setTelefone(rs.getString("telefone"));

                } else {
                    JOptionPane.showMessageDialog(null, "Editora não cadastrada \n", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }     
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar editora: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return editora;
    }

    @Override
    public List<Editora> listar() {
        String sql = "SELECT * FROM editora WHERE ativo='S'";
        List<Editora> editoras = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Editora editora = new Editora();
                
                editora.setId_editora(rs.getLong("id_editora"));
                editora.setNome(rs.getString("nome"));
                editora.setGerente(rs.getString("gerente"));
                editora.setCodigo(rs.getString("codigo"));
                editora.setEndereco(rs.getString("endereco"));
                editora.setTelefone(rs.getString("telefone"));

                editoras.add(editora);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar editora: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return editoras;
    }

}
