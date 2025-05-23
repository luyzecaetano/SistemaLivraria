package dao;

import modelo.Cliente;
import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ClienteDAOImp implements ClienteDAO {
    
    private Connection conn;
    
    public ClienteDAOImp() {
        this.conn = new ConnectionFactory().getConnection();
    }
    
    @Override
    public void inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente(nome,endereco,telefone,cpf_cnpj,tipo_cliente) VALUES (?,?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEndereco());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getCpf_cnpj());
            ps.setString(5, cliente.getTipo_cliente());

            ps.executeUpdate();
        }
    }
    
    @Override
    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nome=?,endereco=?,telefone=?,cpf_cnpj=?,tipo_cliente=? WHERE id_cliente=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEndereco());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getCpf_cnpj());
            ps.setString(5, cliente.getTipo_cliente());
            ps.setLong(6, cliente.getIdcliente());

            ps.executeUpdate();
        }
    }
    
    @Override
    public void remover(Cliente cliente) throws SQLException {
        String sql = "DELETE from cliente WHERE id_cliente=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, cliente.getIdcliente());

            ps.executeUpdate();
        }
    }
    
    @Override
    public Cliente buscaId(long id) {
        String sql = "SELECT * FROM cliente WHERE id_cliente=?";
        Cliente cliente = new Cliente();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
                                
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {              
                cliente.setIdcliente(rs.getLong("id_cliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setCpf_cnpj(rs.getString("cpf_cnpj"));
                cliente.setTipo_cliente(rs.getString("endereco"));
                
            } else {
                JOptionPane.showMessageDialog(null, "Usuário com o ID informado não existe \n", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar usuário: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return cliente;
    }
    
    @Override
    public List<Cliente> listar() {
        String sql = "SELECT * FROM cliente";
        List<Cliente> clientes = new ArrayList<Cliente>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdcliente(rs.getLong("id_cliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setCpf_cnpj(rs.getString("cpf_cnpj"));
                cliente.setTipo_cliente(rs.getString("tipo_cliente"));

                clientes.add(cliente);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar usuário: \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        return clientes;
    }
}
