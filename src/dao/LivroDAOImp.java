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

//    Tabela: livro
//    id_livro — INT (PK)
//    id_editora — INT (FK) 
//    titulo — VARCHAR(150)
//    isbn — VARCHAR(20)
    @Override
    public void inserir(Livro livro) throws SQLException {
        String sql = "INSERT INTO livro(id_editora,titulo,isbn) VALUES (?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, livro.getId_editora());
            ps.setString(2, livro.getTitulo());
            ps.setString(3, livro.getIsbn());
            ps.executeUpdate();
        }
    }

    @Override
    public void atualizar(Livro livro) throws SQLException {
        String sql = "UPDATE livro SET id_editora=?,titulo=?,isbn=? WHERE id_livro=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, livro.getId_editora());
            ps.setString(2, livro.getTitulo());
            ps.setString(3, livro.getIsbn());
            ps.setLong(6, livro.getId_livro());

            ps.executeUpdate();
        }
    }

    @Override
    public void remover(Livro livro) throws SQLException {
        String sql = "DELETE from livro WHERE id_livro=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, livro.getId_livro());

            ps.executeUpdate();
        }
    }

    @Override
    public Livro buscaId(long id) {
        String sql = "SELECT * FROM cliente WHERE id_livro=?";
        Livro livro = new Livro();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                livro.setId_livro(rs.getLong("id_livro"));
                livro.setId_editora(rs.getLong("id_editora"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setIsbn(rs.getString("isbn"));

            } else {
                JOptionPane.showMessageDialog(null, "Livro não cadastrado \n", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar livro: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return livro;
    }

    @Override
    public List<Livro> listar() {
        String sql = "SELECT * FROM livro";
        List<Livro> livros = new ArrayList<Livro>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Livro livro = new Livro();
                
                livro.setId_livro(rs.getLong("id_livro"));
                livro.setId_editora(rs.getLong("id_editora"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setIsbn(rs.getString("isbn"));

                livros.add(livro);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar livro: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return livros;
    }
}
