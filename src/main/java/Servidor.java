import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Servidor {

    private static final FilmeDAO filmeDAO = new FilmeDAO();

    public static void main(String[] args) throws Exception {

        HttpServer servidor = HttpServer.create(
                new InetSocketAddress(8080),
                0
        );

        servidor.createContext("/", Servidor::paginaInicial);
        servidor.createContext("/filmes/salvar", Servidor::salvarFilme);
        servidor.createContext("/style.css", Servidor::estilo);

        servidor.start();

        System.out.println("Servidor iniciado!");
        System.out.println("Acesse: http://localhost:8080");
    }

    private static void paginaInicial(HttpExchange exchange) throws IOException {

        List<Filme> filmes = filmeDAO.listarTodos();

        StringBuilder linhas = new StringBuilder();

        for (Filme filme : filmes) {
            linhas.append("""
                    <tr>
                        <td>%d</td>
                        <td>%s</td>
                    </tr>
                    """.formatted(
                    filme.getId(),
                    filme.getNomeFilme()
            ));
        }

        if (filmes.isEmpty()) {
            linhas.append("""
                    <tr>
                        <td colspan="2">Nenhum filme cadastrado.</td>
                    </tr>
                    """);
        }

        Path arquivo = Path.of("src/main/resources/web/index.html");
        String html = Files.readString(arquivo);
        html = html.replace("{{FILMES}}", linhas.toString());
        enviarResposta(exchange, html, "text/html");
    }

    private static void salvarFilme(HttpExchange exchange) throws IOException {

        String corpo = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );

        String nomeFilme = corpo.replace("nomeFilme=", "");
        nomeFilme = URLDecoder.decode(
                nomeFilme,
                StandardCharsets.UTF_8
        );

        Filme filme = new Filme();
        filme.setNomeFilme(nomeFilme);

        filmeDAO.salvar(filme);
        exchange.getResponseHeaders().set(
                "Location",
                "/"
        );
        exchange.sendResponseHeaders(303, -1);
        exchange.close();
    }

    private static void estilo(HttpExchange exchange) throws IOException {

        Path arquivo = Path.of("src/main/resources/web/style.css");
        String css = Files.readString(arquivo);
        enviarResposta(exchange, css, "text/css");
    }

    private static void enviarResposta(
            HttpExchange exchange,
            String conteudo,
            String tipo
    ) throws IOException {

        byte[] resposta = conteudo.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set(
                "Content-Type",
                tipo + "; charset=UTF-8"
        );

        exchange.sendResponseHeaders(
                200,
                resposta.length
        );
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(resposta);
        }
    }
}