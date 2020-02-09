package hr.java.vjezbe.entitet;

/**
 * Predstavlja entitet enumeracije koji je definiran stanjima artikla.
 * 
 * @author Matija Topolovec
 *
 */
public enum Stanje {
	Novo(1, "Novo"),
	Izvrsno(2, "Izvrsno"),
	Rabljeno(3, "Rabljeno"),
	Neispravno(4, "Neispravno"),
	Usluga(5, "Nema stanja");
	
	private Integer kod;
	private String opis;
	
	/**
	 * Inicijalizira podatak o statusu.
	 * 
	 * @param kod podatak o indexu stanja.
	 * @param opis podatak o opisu.
	 *
	 */	
	private Stanje(Integer kod, String opis) {
		this.kod = kod;
		this.opis = opis;
	}
	/**
	 * Dohvaæa parametar Integer kod iz Stanje enumeracije.
	 * 
	 * @return Vraæa String vrijednost naslova iz Artikl objekta.
	 * 
	 */
	public Integer getKod() {
		return kod;
	}
	/**
	 * Dohvaæa parametar String opis iz Stanje enumeracije.
	 * 
	 * @return Vraæa String vrijednost naslova iz Artikl objekta.
	 * 
	 */
	public String getOpis() {
		return opis;
	}
}
