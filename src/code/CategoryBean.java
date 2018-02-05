package code;

public class CategoryBean {

	int id_categoria;
	int id_cat_padre;
	String nome;
	String nomepadre;

	public String getNomepadre() {
		return nomepadre;
	}

	public void setNomepadre(String nomepadre) {
		this.nomepadre = nomepadre;
	}

	public int getId_categoria() {
		return id_categoria;
	}

	public void setId_categoria(int id_categoria) {
		this.id_categoria = id_categoria;
	}

	public int getId_cat_padre() {
		return id_cat_padre;
	}

	public void setId_cat_padre(int id_cat_padre) {
		this.id_cat_padre = id_cat_padre;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
