package hr.java.vjezbe.entitet;

import java.time.LocalDate;
/**
 * Predstavlja entitet prodaje koji je definiran artiklom, korisnikom i datumom objave.
 * 
 * @author Matija Topolovec
 *
 */
public class Prodaja extends Entitet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3851441136808755430L;
	
	private Artikl artikl;
	private Korisnik korisnik;
	private LocalDate datumObjave;
	/**
	 * Inicijalizira podatak o imenu, prezimenu, mail adresi i telefonskom broju.
	 * 
	 * @param artikl		podatak o artiklu.
	 * @param korisnik 		podatak o korisniku.
	 * @param datumObjave	podatak o datumu objave.
	 *
	 */
	public Prodaja(Long id, Artikl artikl, Korisnik korisnik, LocalDate datumObjave) {
		super(id);
		this.artikl = artikl;
		this.korisnik = korisnik;
		this.datumObjave = datumObjave;
	}
	/**
	 * Dohvaæa parametar Artikl iz Prodaja objekta.
	 * 
	 * @return Vraæa Artikl vrijednost artikla iz Prodaja objekta.
	 * 
	 */
	public Artikl getArtikl() {
		return artikl;
	}
	/**
	 * Postavlja parametar Artikl iz Prodaja objekta.
	 * 
	 * @param artikl podataci o artiklu.
	 * 
	 */
	public void setArtikl(Artikl artikl) {
		this.artikl = artikl;
	}
	/**
	 * Dohvaæa parametar Korisnik iz Prodaja objekta.
	 * 
	 * @return Vraæa Korisnik vrijednost korisnika iz Prodaja objekta.
	 * 
	 */
	public Korisnik getKorisnik() {
		return korisnik;
	}
	/**
	 * Postavlja parametar Korisnik iz Prodaja objekta.
	 * 
	 * @param korisnik podataci o korisniku.
	 * 
	 */
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	/**
	 * Dohvaæa parametar datumObjave iz Prodaja objekta.
	 * 
	 * @return Vraæa LocalDate vrijednost datumObjave iz Prodaja objekta.
	 * 
	 */
	public LocalDate getDatumObjave() {
		return datumObjave;
	}
	/**
	 * Postavlja parametar datumObjave iz Prodaja objekta.
	 * 
	 * @param datumObjave podataci o datumu objave.
	 * 
	 */
	public void setDatumObjave(LocalDate datumObjave) {
		this.datumObjave = datumObjave;
	}
	
}
