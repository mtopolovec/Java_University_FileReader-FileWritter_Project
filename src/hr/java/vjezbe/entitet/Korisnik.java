package hr.java.vjezbe.entitet;

/**
 * Predstavlja entitet korisnika koji je definiran e-mailom i telefonom.
 * 
 * @author Matija Topolovec
 *
 */
public abstract class Korisnik extends Entitet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2817314347483451116L;
	
	private String email;
	private String telefon;
	/**
	 * Inicijalizira podatak o nazivu i polje Artikala.
	 * 
	 * @param email		podatak o mail adresi.
	 * @param telefon 	podatak o broju telefona.
	 *
	 */
	public Korisnik(Long id, String email, String telefon) {
		super(id);
		this.email = email;
		this.telefon = telefon;
	}
	/**
	 * Dohvaæa parametar String email iz Korisnik objekta.
	 * 
	 * @return Vraæa String vrijednost email adrese iz Korisnik objekta.
	 * 
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Postavlja parametar String email iz Korisnik objekta.
	 * 
	 * @param email String podatak o mail adresi.
	 * 
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Dohvaæa parametar String telefon iz Korisnik objekta.
	 * 
	 * @return Vraæa String vrijednost telefonskog broja iz Korisnik objekta.
	 * 
	 */
	public String getTelefon() {
		return telefon;
	}
	/**
	 * Postavlja parametar String telefon iz Korisnik objekta.
	 * 
	 * @param telefon String podatak o telefonskom broju.
	 * 
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	/**
	 * Dohvaæa formatirani tekst za ispis korisnika.
	 * 
	 * @return Vraæa String formatiranog teksta za ispis kontakt podataka korisnika.
	 *
	 */
	public abstract String dohvatiKontakt();
	
}
