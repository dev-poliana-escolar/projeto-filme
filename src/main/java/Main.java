import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FilmeDAO filmeDAO = new FilmeDAO();
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n========== MENU FILMES ==========");
            System.out.println("1 - Cadastrar Filme");
            System.out.println("2 - Listar Todos os Filmes");
            System.out.println("3 - Buscar Filme por ID");
            System.out.println("4 - Atualizar Filme");
            System.out.println("5 - Deletar Filme");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do teclado

            switch (opcao) {
                case 1:
                    System.out.print("\nDigite o nome do filme: ");
                    String nomeNovo = scanner.nextLine();

                    Filme novoFilme = new Filme();
                    novoFilme.setNomeFilme(nomeNovo);
                    filmeDAO.salvar(novoFilme);
                    break;

                case 2:
                    System.out.println("\n--- Lista de Filmes ---");
                    List<Filme> filmes = filmeDAO.listarTodos();

                    if (filmes.isEmpty()) {
                        System.out.println("Nenhum filme cadastrado.");
                    } else {
                        for (Filme f : filmes) {
                            System.out.println("ID: " + f.getId() + " | Nome: " + f.getNomeFilme());
                        }
                    }
                    break;

                case 3:
                    System.out.print("\nDigite o ID do filme para buscar: ");
                    int idBusca = scanner.nextInt();

                    Filme filmeEncontrado = filmeDAO.buscarPorId(idBusca);
                    if (filmeEncontrado != null) {
                        System.out.println("Filme encontrado -> ID: " + filmeEncontrado.getId() + " | Nome: " + filmeEncontrado.getNomeFilme());
                    } else {
                        System.out.println("Filme não encontrado para o ID " + idBusca);
                    }
                    break;

                case 4:
                    System.out.print("\nDigite o ID do filme que deseja atualizar: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine(); // Limpa o buffer

                    Filme filmeParaEditar = filmeDAO.buscarPorId(idAtualizar);
                    if (filmeParaEditar != null) {
                        System.out.print("Digite o novo nome para o filme '" + filmeParaEditar.getNomeFilme() + "': ");
                        String novoNome = scanner.nextLine();

                        filmeParaEditar.setNomeFilme(novoNome);
                        filmeDAO.atualizar(filmeParaEditar);
                    } else {
                        System.out.println("Filme com ID " + idAtualizar + " não encontrado.");
                    }
                    break;

                case 5:
                    System.out.print("\nDigite o ID do filme que deseja deletar: ");
                    int idDeletar = scanner.nextInt();

                    filmeDAO.deletar(idDeletar);
                    break;

                case 0:
                    System.out.println("\nSaindo do sistema... Até mais!");
                    break;

                default:
                    System.out.println("\nOpção inválida! Tente novamente.");
            }
        }

        scanner.close();
    }
}