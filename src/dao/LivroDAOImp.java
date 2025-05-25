package dao;

import modelo.Livro;
import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class LivroDAOImp implements LivroDAO {

    private Connection conn;

    public LivroDAOImp() {
        this.conn = new ConnectionFactory().getConnection();
    }

    @Override
    public void inserir(Livro livro) {
        String sql = "INSERT INTO livro(id_editora,titulo,isbn,nome_autor,assunto,qtde) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, livro.getId_editora());
            ps.setString(2, livro.getTitulo());
            ps.setString(3, livro.getIsbn());
            ps.setString(4, livro.getNome_autor());
            ps.setString(5, livro.getAssunto());
            ps.setInt(6, livro.getQtde());
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir livro: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void atualizar(Livro livro) {
        String sql = "UPDATE livro SET id_editora=?,titulo=?,isbn=?,nome_autor=?,assunto=?,qtde=? WHERE id_livro=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, livro.getId_editora());
            ps.setString(2, livro.getTitulo());
            ps.setString(3, livro.getIsbn());
            ps.setString(4, livro.getNome_autor());
            ps.setString(5, livro.getAssunto());
            ps.setInt(6, livro.getQtde());
            ps.setLong(7, livro.getId_livro());

            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao editar pedido: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void remover(Livro livro) {
        String sql = "UPDATE livro SET ativo = 'N' WHERE id_livro=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, livro.getId_livro());

            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao remover livro: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Livro buscaId(long id) {
        String sql = "SELECT l.*, e.nome AS nome_editora FROM livro l JOIN editora e ON l.id_editora = e.id_editora WHERE l.id_livro=? AND l.ativo='S' AND e.ativo='S'";
        Livro livro = new Livro();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    livro.setId_livro(rs.getLong("id_livro"));
                    livro.setId_editora(rs.getLong("id_editora"));
                    livro.setNomeEditora(rs.getString("nome_editora"));
                    livro.setTitulo(rs.getString("titulo"));
                    livro.setIsbn(rs.getString("isbn"));
                    livro.setNome_autor(rs.getString("nome_autor"));
                    livro.setAssunto(rs.getString("assunto"));
                    livro.setQtde(rs.getInt("qtde"));

                } else {
                    JOptionPane.showMessageDialog(null, "Livro não cadastrado \n", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar livro: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return livro;
    }

    @Override
    public List<Livro> listar() {
        String sql = "SELECT l.*, e.nome AS nome_editora FROM livro l JOIN editora e ON l.id_editora = e.id_editora WHERE l.ativo='S' AND e.ativo='S'";
        List<Livro> livros = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Livro livro = new Livro();

                livro.setId_livro(rs.getLong("id_livro"));
                livro.setId_editora(rs.getLong("id_editora"));
                livro.setNomeEditora(rs.getString("nome_editora"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setIsbn(rs.getString("isbn"));
                livro.setNome_autor(rs.getString("nome_autor"));
                livro.setAssunto(rs.getString("assunto"));
                livro.setQtde(rs.getInt("qtde"));

                livros.add(livro);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar livro: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return livros;
    }
}
