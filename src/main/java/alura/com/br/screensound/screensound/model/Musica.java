package alura.com.br.screensound.screensound.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Musicas")
public class Musica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Titulo;
    @ManyToOne
    private Artista artista;

    public Musica(){}

    public Musica(String nomeMusica) {
        this.Titulo = nomeMusica;

    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Titulo: " + Titulo;
    }
}
