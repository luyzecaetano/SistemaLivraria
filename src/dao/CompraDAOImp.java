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
    public void inserir(Compra compra) {
        String sql = "INSERT INTO compra(id_cliente,id_livro,data_compra,cod_pedido) VALUES (?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, compra.getId_cliente());
            ps.setLong(2, compra.getId_livro());
            ps.setString(3, compra.getData_compra());
            ps.setInt(4, compra.getCod_pedido());

            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir pedido: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public void atualizar(List<Compra> compras) {
        if (compras == null || compras.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Erro, pedidos não encontrados");
            return;
        }

        int codPedido = compras.get(0).getCod_pedido();

        try {
            String sqlDelete = "DELETE from compra WHERE cod_pedido=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlDelete)) {
                ps.setInt(1, codPedido);
                ps.executeUpdate();
            }

            String sqlInsert = "INSERT INTO compra(id_cliente,id_livro,data_compra,cod_pedido) VALUES (?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                for (Compra compra : compras) {
                    ps.setLong(1, compra.getId_cliente());
                    ps.setLong(2, compra.getId_livro());
                    ps.setString(3, compra.getData_compra());
                    ps.setInt(4, compra.getCod_pedido());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar pedido: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void remover(int codPedido) {
        String sql = "DELETE from compra WHERE cod_pedido=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, codPedido);

            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao remover pedido: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public List<Compra> buscaCod(int codPedido) {
        String sql = "SELECT c.*, cli.nome AS nome_cliente, l.titulo AS titulo_livro FROM compra c "
                + "JOIN cliente cli ON c.id_cliente = cli.id_cliente "
                + "JOIN livro l ON c.id_livro = l.id_livro "
                + "WHERE cod_pedido=? AND cli.ativo='S' AND l.ativo='S'";
        List<Compra> compras = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setInt(1, codPedido);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Compra compra = new Compra();
                    compra.setId_compra(rs.getLong("id_compra"));
                    compra.setId_cliente(rs.getLong("id_cliente"));
                    compra.setNomeCliente(rs.getString("nome_cliente"));
                    compra.setId_livro(rs.getLong("id_livro"));
                    compra.setData_compra(rs.getString("data_compra"));
                    compra.setTituloLivro(rs.getString("titulo_livro"));
                    compra.setCod_pedido(rs.getInt("cod_pedido"));

                    compras.add(compra);
                }
            }

            if (compras.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pedido com o ID informado não existe \n", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar pedido: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return compras;
    }

    @Override
    public List<Compra> listar() {
        String sql = "SELECT c.*, cli.nome AS nome_cliente, l.titulo AS titulo_livro FROM compra c "
                + "JOIN cliente cli ON c.id_cliente = cli.id_cliente "
                + "JOIN livro l ON c.id_livro = l.id_livro "
                + "WHERE cli.ativo='S' AND l.ativo='S'";

        List<Compra> compras = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Compra compra = new Compra();
                compra.setId_cliente(rs.getLong("id_cliente"));
                compra.setNomeCliente(rs.getString("nome_cliente"));
                compra.setId_livro(rs.getLong("id_livro"));
                compra.setData_compra(rs.getString("data_compra"));
                compra.setTituloLivro(rs.getString("titulo_livro"));
                compra.setCod_pedido(rs.getInt("cod_pedido"));

                compras.add(compra);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar pedido: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return compras;
    }

    @Override
    public int buscarUltimoCod() {
        String sql = "SELECT MAX(cod_pedido) FROM compra";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar código: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    @Override
    public List<Compra> listarPorCliente(Long idCliente) {
        String sql = "SELECT c.*, l.titulo AS titulo_livro FROM compra c JOIN livro l ON c.id_livro = l.id_livro WHERE c.id_cliente=?";
        List<Compra> listaClienteCompras = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Compra compra = new Compra();
                    compra.setCod_pedido(rs.getInt("cod_pedido"));
                    compra.setTituloLivro(rs.getString("titulo_livro"));
                    compra.setData_compra(rs.getDate("data_compra").toString());

                    listaClienteCompras.add(compra);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar pedidos: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return listaClienteCompras;
    }

}
