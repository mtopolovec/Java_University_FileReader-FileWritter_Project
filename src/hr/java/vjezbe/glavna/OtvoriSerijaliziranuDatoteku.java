package hr.java.vjezbe.glavna;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.entitet.Prodaja;

/**
 * Predstavlja entitet glavnog programa u kojem se uèitavaju podaci iz serijalizirane datoteke.
 * 
 * @author Matija Topolovec
 *
 */

public class OtvoriSerijaliziranuDatoteku {
	
	////////////////////////////////
	/*
	* Konstante
	*/
	////////////////////////////////

	static final String DATUM = "dd.MM.yyyy.";
	
	static final String SERIALIZATIONFILENAME = "dat/serijalizacija.dat";
	
	////////////////////////////////
	/* 
	* Logger za log file
	*/
	////////////////////////////////
	
	private static final Logger logger = LoggerFactory.getLogger(OtvoriSerijaliziranuDatoteku.class);
	// Morao sam supressat warrning za unchecked cast na liniji 57 | aktivniOglasi = (List<Prodaja>) in.readObject(); |
	@SuppressWarnings("unchecked")
	
	/**
	 * Glavna main metoda u kojoj se nalazi glavni programski kod izvršavanja programa.
	 * 
	 * @param args Argumenti sa konzole koji se mogu proslijediti u main.
	 * 
	 */
	
	public static void main(String[] args) {
		
		logger.info("Poèetak rada sa serijaliziranom datotekom!");
		
		List<Prodaja> aktivniOglasi = null;
		
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(SERIALIZATIONFILENAME))) {
			aktivniOglasi = (List<Prodaja>) in.readObject();
			
			System.out.println("Podaci o proèitanim objektima:");
			System.out.println("-------------------------------------------------------------------------------------------------------------------");
			System.out.println("Trenutno su oglasi na prodaju: ");
			for(Prodaja p : aktivniOglasi) {
				System.out.println("-------------------------------------------------------------------------------------------------------------------");
				System.out.println(p.getArtikl().tekstOglasa());
				String datum = p.getDatumObjave().format(DateTimeFormatter.ofPattern(DATUM));
				System.out.println("Datum objave: " + datum);
				System.out.println(p.getKorisnik().dohvatiKontakt());
			}
			System.out.println("-------------------------------------------------------------------------------------------------------------------");
			
		} catch (IOException ex) {
			System.err.println(ex);
		} catch (ClassNotFoundException ex) {
			System.err.println(ex);
		}
		
		logger.info("Kraj rada sa serijaliziranom datotekom!");
	}

}
