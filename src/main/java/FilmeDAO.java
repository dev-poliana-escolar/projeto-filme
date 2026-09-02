import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmeDAO {

    // 1. CREATE (Salvar)
    public void salvar(Filme filme) {
        String sql = "INSERT INTO poliana (nome_filme) VALUES (?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, filme.getNomeFilme());
            stmt.executeUpdate();
            System.out.println("Filme '" + filme.getNomeFilme() + "' cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao salvar filme: " + e.getMessage());
        }
    }

    // 2. READ (Listar Todos)
    public List<Filme> listarTodos() {
        String sql = "SELECT * FROM poliana";
        List<Filme> filmes = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Filme filme = new Filme();
                filme.setId(rs.getInt("id"));
                filme.setNomeFilme(rs.getString("nome_filme"));
                filmes.add(filme);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar filmes: " + e.getMessage());
        }

        return filmes;
    }

    // 2.1 READ (Buscar por ID)
    public Filme buscarPorId(int id) {
        String sql = "SELECT * FROM poliana WHERE id = ?";
        Filme filme = null;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    filme = new Filme();
                    filme.setId(rs.getInt("id"));
                    filme.setNomeFilme(rs.getString("nome_filme"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar filme por ID: " + e.getMessage());
        }

        return filme;
    }

    // 3. UPDATE (Atualizar)
    public void atualizar(Filme filme) {
        String sql = "UPDATE poliana SET nome_filme = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, filme.getNomeFilme());
            stmt.setInt(2, filme.getId());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Filme com ID " + filme.getId() + " atualizado com sucesso!");
            } else {
                System.out.println("Nenhum filme encontrado com o ID " + filme.getId());
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar filme: " + e.getMessage());
        }
    }

    // 4. DELETE (Deletar)
    public void deletar(int id) {
        String sql = "DELETE FROM poliana WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Filme com ID " + id + " removido com sucesso!");
            } else {
                System.out.println("Nenhum filme encontrado com o ID " + id);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao deletar filme: " + e.getMessage());
        }
    }
}