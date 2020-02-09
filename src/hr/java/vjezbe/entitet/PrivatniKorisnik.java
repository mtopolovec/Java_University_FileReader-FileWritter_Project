package hr.java.vjezbe.entitet;

/**
 * Predstavlja entitet privatnog korisnika koji je definiran imenom, prezimenom, mail adresom i telefonskim brojem.
 * 
 * @author Matija Topolovec
 *
 */
public class PrivatniKorisnik extends Korisnik {

	/**
	 * 
	 */
	private static final long serialVersionUID = 610165501757130613L;
	
	private String ime;
	private String prezime;
	/**
	 * Inicijalizira podatak o imenu, prezimenu, mail adresi i telefonskom broju.
	 * 
	 * @param ime		podatak o imenu.
	 * @param prezime 	podatak o prezimenu.
	 * @param email		podatak o mail adresi.
	 * @param telefon	podatak o telefonskom broju.
	 *
	 */
	public PrivatniKorisnik(Long id, String ime, String prezime, String email, String telefon) {
		super(id, email, telefon);
		this.ime = ime;
		this.prezime = prezime;
	}
	/**
	 * Dohvaæa parametar String ime iz PrivatniKorisnik objekta.
	 * 
	 * @return Vraæa String vrijednost imena iz PoslovniKorisnik objekta.
	 * 
	 */
	public String getIme() {
		return ime;
	}
	/**
	 * Postavlja parametar String ime iz PrivatniKorisnik objekta.
	 * 
	 * @param ime String podatak o imenu.
	 * 
	 */
	public void setIme(String ime) {
		this.ime = ime;
	}
	/**
	 * Dohvaæa parametar String prezime iz PrivatniKorisnik objekta.
	 * 
	 * @return Vraæa String vrijednost prezimena iz PoslovniKorisnik objekta.
	 * 
	 */
	public String getPrezime() {
		return prezime;
	}
	/**
	 * Postavlja parametar String prezime iz PrivatniKorisnik objekta.
	 * 
	 * @param prezime String podatak o prezimenu.
	 * 
	 */
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	/**
	 * Dohvaæa formatirani tekst sa kontakt podacima privatnog korisnika.
	 * 
	 * @return Vraæa String formatiranog teksta kontakt podataka privatnog korisnika.
	 * 
	 */
	@Override
	public String dohvatiKontakt() {
		return "Osobni podaci prodavatelja: " + ime + " " + prezime + ", mail: " + super.getEmail() + ", tel: " + super.getTelefon();
	}
	
}
