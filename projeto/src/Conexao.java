

import java.sql.*;


public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    public static Connection conectar(){
        try {
            return DriverManager.getConnection(URL,USUARIO,SENHA);
        } catch (SQLException e) {
            System.out.println("Erro na conexão" + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection conexao = conectar();

//DICA DO PROF : É CONTRA INJECTION
//        Statement s=conexao.createStatement();
//        ResultSet rs =s.executeQuery("SELECT * FROM poliana;");
//
//        while(rs.next()){
//            System.out.println(rs.getString("nome_filme"));
//        }

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
