package code;

public class ProductBean {
	int id_prodotto;
	int id_categoria;
	String nome;
	String descrizione;
	float prezzo;
	String firstImm;
	String nomecategoria;
	int id_cat_padre;
	String nomepadre;

	int quantita;
	String taglia;

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public String getTaglia() {
		return taglia;
	}

	public void setTaglia(String taglia) {
		this.taglia = taglia;
	}

	public String getNomecategoria() {
		return nomecategoria;
	}

	public void setNomecategoria(String nomecategoria) {
		this.nomecategoria = nomecategoria;
	}

	public int getId_cat_padre() {
		return id_cat_padre;
	}

	public void setId_cat_padre(int id_cat_padre) {
		this.id_cat_padre = id_cat_padre;
	}

	public String getNomepadre() {
		return nomepadre;
	}

	public void setNomepadre(String nomepadre) {
		this.nomepadre = nomepadre;
	}

	public String getFirstImm() {
		return firstImm;
	}

	public void setFirstImm(String firstImm) {
		this.firstImm = firstImm;
	}

	public int getId_categoria() {
		return id_categoria;
	}

	public void setId_categoria(int id_categoria) {
		this.id_categoria = id_categoria;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public int getId_prodotto() {
		return id_prodotto;
	}

	public void setId_prodotto(int id_prodotto) {
		this.id_prodotto = id_prodotto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
