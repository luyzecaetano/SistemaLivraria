package modelo;

public class Livro {

    long id_livro;
    long id_editora;
    String isbn;
    String titulo;

    public long getId_livro() {
        return id_livro;
    }

    public void setId_livro(long id_livro) {
        this.id_livro = id_livro;
    }

    public long getId_editora() {
        return id_editora;
    }

    public void setId_editora(long id_editora) {
        this.id_editora = id_editora;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
