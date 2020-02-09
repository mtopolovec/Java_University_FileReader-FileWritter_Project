package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
/**
 * Predstavlja entitet usluge koji je definiran naslovom, opisom i cijenom.
 * 
 * @author Matija Topolovec
 *
 */
public class Usluga extends Artikl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8418379135128057504L;
	/**
	 * Inicijalizira podatak o naslovu, opisu, cijeni i stanju.
	 * 
	 * @param naslov		podatak o naslovu.
	 * @param opis 			podatak o opisu.
	 * @param cijena		podatak o cijeni.
	 * @param stanje 		podatak o stanju usluge.
	 *
	 */
	public Usluga(Long id, String naslov, String opis, BigDecimal cijena, Stanje stanje) {
		super(id, naslov, opis, cijena, stanje);
	}
	/**
	 * Dohvaæa formatirani tekst sa tekstom oglasa usluge.
	 * 
	 * @return Vraæa String formatiranog teksta oglasa usluge.
	 * 
	 */
	@Override
	public String tekstOglasa() {
		return "Naslov usluge: " + super.getNaslov() + "\nOpis usluge: " + super.getOpis() +/* + "\nStanje usluge: " + super.getStanje() +*/ "\nCijena usluge: " + super.getCijena() + " kn";
	}

}
