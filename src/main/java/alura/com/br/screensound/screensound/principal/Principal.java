package alura.com.br.screensound.screensound.principal;

import alura.com.br.screensound.screensound.model.Artista;
import alura.com.br.screensound.screensound.model.Musica;
import alura.com.br.screensound.screensound.model.TipoArtista;
import alura.com.br.screensound.screensound.repository.ArtistaRepository;
import alura.com.br.screensound.screensound.service.ConsultaChatGPT;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private final ArtistaRepository repository;
    private Scanner leitura = new Scanner(System.in);

    public Principal(ArtistaRepository repository){
        this.repository = repository;
    }
    
    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 9) {
            var menu = """
                    *** Screen Sound Músicas ***                    
                                        
                    1- Cadastrar artistas
                    2- Cadastrar músicas
                    3- Listar músicas
                    4- Buscar músicas por artistas
                    5- Pesquisar dados sobre um artista
                                    
                    9 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicasPorArtista();
                    break;
                case 5:
                    pesquisarDadosDoArtista();
                    break;
                case 9:
                    System.out.println("Encerrando a aplicação!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void pesquisarDadosDoArtista() {
        System.out.println("Pesquisar dados sobre qual Artista?");
        var nome = leitura.nextLine();
        var resposta = ConsultaChatGPT.obterInformacao(nome);
        System.out.println(resposta.trim());

    }

    private void buscarMusicasPorArtista() {
        System.out.println("Buscar lista de músicas de qual Artista?");
        var nome = leitura.nextLine();
        List<Musica> listaMusicas = repository.buscaMusicasPorArtista(nome);
        listaMusicas.forEach(System.out::println);
    }

    private void listarMusicas() {
        List<Artista> artistas = repository.findAll();
        artistas.forEach(System.out::println);
    }

    private void cadastrarMusicas() {
        System.out.println("Cadastrar Música de qual Artista?");
        var nome = leitura.nextLine();
        Optional<Artista> artista = repository.findByNomeContainingIgnoreCase(nome);
        if (artista.isPresent()){
            System.out.println("Informe o nome da Música: ");
            var nomeMusica = leitura.nextLine();
            Musica musica = new Musica(nomeMusica);
            musica.setArtista(artista.get());
            artista.get().getMusicas().add(musica);
            repository.save(artista.get());
        } else {
            System.out.println("Artista não encontrado!");
        }
    }

    private void cadastrarArtistas() {
        var cadastrarNovo = "S";
        while (cadastrarNovo.equalsIgnoreCase("s")) {
            System.out.println("Informe o nome do Artista: ");
            var nome = leitura.nextLine();
            System.out.println("Informe qual o tipo: (SOLO,BANDA,GRUPO ou DUPLA");
            var tipo = leitura.nextLine();
            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
            Artista artista = new Artista(nome, tipoArtista);
            repository.save(artista);
            System.out.println("Cadastrar um novo Artista? (S/N)");
            cadastrarNovo = leitura.nextLine();
        }

    }
}
