import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL =
            System.getenv().getOrDefault(
                    "DB_URL",
                    "jdbc:mysql://localhost:3306/filmes"
            );

    private static final String USUARIO =
            System.getenv().getOrDefault("DB_USER", "root");

    private static final String SENHA =
            System.getenv().getOrDefault("DB_PASSWORD", "root");

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.out.println("Erro na conexão: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection conexao = conectar();

        if (conexao != null) {
            System.out.println("Conexão realizada com sucesso!");

            try {
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Falha na conexão.");
        }
    }
}