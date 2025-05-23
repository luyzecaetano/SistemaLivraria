package dao;

import modelo.Compra;
import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CompraDAOImp implements CompraDAO {
    
    private Connection conn;
    
    public CompraDAOImp() {
        this.conn = new ConnectionFactory().getConnection();
    }
    
    @Override
    public void inserir(Compra compra) throws SQLException {
        String sql = "INSERT INTO compra(id_cliente,id_livro,data_compra,qtde) VALUES (?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, compra.getId_cliente());
            ps.setLong(2, compra.getId_livro());
            ps.setString(3, compra.getData_compra());
            ps.setInt(4, compra.getQtde());

            ps.executeUpdate();
        }
    }
    
    @Override
    public void atualizar(Compra compra) throws SQLException {
        String sql = "UPDATE compra SET id_cliente=?,id_livro=?,data_compra=?,qtde=? WHERE id_compra=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, compra.getId_cliente());
            ps.setLong(2, compra.getId_livro());
            ps.setString(3, compra.getData_compra());
            ps.setInt(4, compra.getQtde());
            ps.setLong(5, compra.getId_compra());

            ps.executeUpdate();
        }
    }
    
    @Override
    public void remover(Compra compra) throws SQLException {
        String sql = "DELETE from compra WHERE id_compra=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, compra.getId_compra());

            ps.executeUpdate();
        }
    }
    
    @Override
    public Compra buscaId(long id) {
        String sql = "SELECT * FROM compra WHERE id_compra=?";
        Compra compra = new Compra();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
                                
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {              
                compra.setId_compra(rs.getLong("id_compra"));
                compra.setId_cliente(rs.getLong("id_cliente"));
                compra.setId_livro(rs.getLong("id_livro"));
                compra.setData_compra(rs.getString("data_compra"));
                compra.setQtde(rs.getInt("qtde"));
                
            } else {
                JOptionPane.showMessageDialog(null, "Pedido com o ID informado não existe \n", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar pedido: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return compra;
    }
    
    @Override
    public List<Compra> listar() {
        String sql = "SELECT * FROM pedido";
        List<Compra> compras = new ArrayList<Compra>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Compra compra = new Compra();
                compra.setId_compra(rs.getLong("id_compra"));
                compra.setId_cliente(rs.getLong("id_cliente"));
                compra.setId_livro(rs.getLong("id_livro"));
                compra.setData_compra(rs.getString("data_compra"));
                compra.setQtde(rs.getInt("qtde"));

                compras.add(compra);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar pedido: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        return compras;
    }
}
