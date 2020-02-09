package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.iznimke.CijenaJePreniskaException;
/**
 * Predstavlja entitet stana koji je definiran naslovom, opisom, cijenom i kvadraturom.
 * 
 * @author Matija Topolovec
 *
 */
public class Stan extends Artikl implements Nekretnina {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4251936291546694530L;

	private static final Logger logger = LoggerFactory.getLogger(Stan.class);

	private int kvadratura;
	/**
	 * Inicijalizira podatak o naslovu, opisu, cijeni i kvadraturi.
	 * 
	 * @param naslov		podatak o naslovu.
	 * @param opis 			podatak o opisu.
	 * @param cijena		podatak o cijeni.
	 * @param kvadratura	podatak o kvadraturi.
	 * @param stanje 		podatak o stanju stana.
	 *
	 */
	public Stan(Long id, String naslov, String opis, BigDecimal cijena, int kvadratura, Stanje stanje) {
		super(id, naslov, opis, cijena, stanje);
		this.kvadratura = kvadratura;
	}
	/**
	 * Dohvaæa parametar kvadratura iz Stan objekta.
	 * 
	 * @return Vraæa int vrijednost kvadrature iz Stan objekta.
	 * 
	 */
	public int getKvadratura() {
		return kvadratura;
	}
	/**
	 * Postavlja parametar kvadratura iz Stan objekta.
	 * 
	 * @param kvadratura podataci o kvadraturi.
	 * 
	 */
	public void setKvadratura(int kvadratura) {
		this.kvadratura = kvadratura;
	}
	/**
	 * Dohvaæa formatirani tekst za ispis teksta oglasa.
	 * <p>
	 * Ukoliko se ne može odrediti porez na nekretninu zbog premale cijene nekretnine.
	 * Onda se proslijeðuje iznimka dalje te ispisuje korisniku poruka.
	 * 
	 * @return Vraæa String formatiranog teksta za ispis oglasa artikla. 
	 * 
	 *
	 */
	@Override
	public String tekstOglasa() {
		BigDecimal porezNekretnine = null;
		String tekst = null;
		try {
			porezNekretnine = izracunajPorez(super.getCijena()).setScale(2, RoundingMode.HALF_EVEN);
			tekst = "Naslov nekretnine: " + super.getNaslov() + "\nOpis nekretnine: " + super.getOpis() + "\nKvadratura nekretnine: " + kvadratura + " m2" + "\nStanje nekretnine: " + super.getStanje() + "\nPorez na nekretnine: " + porezNekretnine + " kn" + "\nCijena nekretnine: " + super.getCijena() + " kn";
		} catch(CijenaJePreniskaException ex1) {
			logger.error("Pogreška prilikom odreðivanja iznosa poreza!", ex1);
			tekst = "Naslov nekretnine: " + super.getNaslov() + "\nOpis nekretnine: " + super.getOpis() + "\nKvadratura nekretnine: " + kvadratura + " m2" + "\nStanje nekretnine: " + super.getStanje() + "\nPorez na nekretnine: " +  "Cijena ne smije biti manja od 10000 kn" + "\nCijena nekretnine: " + super.getCijena() + " kn";
		}
		return tekst;
	}

}
