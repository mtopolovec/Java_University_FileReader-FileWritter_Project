package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

/**
 * Predstavlja entitet kategorije koji je definiran nazivom i setom Artikala.
 * 
 * @author Matija Topolovec
 *
 */

public class Kategorija<T extends Artikl> extends Entitet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5401132629765773468L;
	
	private String naziv;
	private List<T> artikli = new ArrayList<>();
	/**
	 * Inicijalizira podatak o nazivu i set Artikala.
	 * 
	 * @param naziv		podatak o nazivu.
	 * @param artikli 	lista koja sadrži sve artikale.
	 *
	 */
	public Kategorija(Long id, String naziv, List<T> artikli) {
		super(id);
		this.naziv = naziv;
		this.artikli = artikli;
	}
	/**
	 * Dohvaæa parametar String naziv iz Kategorija objekta.
	 * 
	 * @return Vraæa String vrijednost naziva iz Kategorija objekta.
	 * 
	 */
	public String getNaziv() {
		return naziv;
	}
	/**
	 * Postavlja parametar String naziv iz Kategorija objekta.
	 * 
	 * @param naziv String podatak o nazivu.
	 * 
	 */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	/**
	 * Dohvaæa set artikala iz Kategorija objekta.
	 * 
	 * @return Vraæa polje artikala iz Kategorija objekta.
	 * 
	 */
	public List<T> getArtikli() {
		return artikli;
	}
	public void setArtikli(List<T> artikli) {
		this.artikli = artikli;
	}
	// Metoda za dodavanje artikla
	public void dodajArtikl(T artikl) {
		this.artikli.add(artikl);
	}
	public T dohvatiArtikl(int index) {
		return artikli.get(index-1);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Kategorija<?> other = (Kategorija<?>) obj;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		return true;
	}
	
}
