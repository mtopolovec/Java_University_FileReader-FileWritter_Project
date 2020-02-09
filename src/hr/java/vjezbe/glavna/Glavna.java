package hr.java.vjezbe.glavna;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.Kategorija;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.entitet.Stan;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.iznimke.NemoguceOdreditiGrupuOsiguranjaException;

/**
 * Predstavlja entitet glavnog programa u kojem se nalazi main metoda.
 * 
 * @author Matija Topolovec
 *
 */
public class Glavna {

	////////////////////////////////
	/* 
	 * Logger za log file
	 */
	////////////////////////////////
	
	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
	
	////////////////////////////////
	/*
	 * Konstante
	 */
	////////////////////////////////
	
	static final String DATUM = "dd.MM.yyyy.";
	static final Long ARTIKLUSLUGA = Long.parseLong("1");
	static final Long ARTIKLAUTOMOBIL = Long.parseLong("2");
	static final Long ARTIKLSTAN = Long.parseLong("3");
	static final Long PRIVATNIKORISNIK = Long.parseLong("1");
	static final Long POSLOVNIKORISNIK = Long.parseLong("2");
	
	
	////////////////////////////////
	/*
	 * main metoda
	 */
	////////////////////////////////
	/**
	 * Glavna main metoda u kojoj se nalazi glavni programski kod izvršavanja programa.
	 * 
	 * @param args Argumenti sa konzole koji se mogu proslijediti u main.
	 * 
	 */
	public static void main(String[] args) {
		
		// Klasa scanner za unos podataka sa konzole
		Scanner unos = new Scanner(System.in);
		
		logger.info("Poèetak rada programa!");
		
		int brojKorisnika = provjeraUnosaInt(unos,"Unesite broj korisnika koji želite unijeti: ");

		List<Korisnik> korisnici = new ArrayList<>();
		
		for(int i=0; i<brojKorisnika; i++) {
			System.out.println("Unesite tip " + (i+1) + ". korisnika");
			int tipKorisnika = odabirKorisnika(unos);
			if(tipKorisnika == 1) {
				korisnici.add(unosPrivatnogKorisnika(unos,i+1));
			}
			else {
				korisnici.add(unosPoslovnogKorisnika(unos,i+1));
			}
			
		}
		
		int brojKategorija = provjeraUnosaInt(unos, "Unesite broj kategorija koji želite unijeti: ");
		
		List<Kategorija<Artikl>> kategorije = new ArrayList<>();
		
		Map<Kategorija<Artikl>, List<Artikl>> mapaKategorijaSaSvimArtiklima = new HashMap<>();

		List<Artikl> artikli = new ArrayList<>();
		
		for(int i=0; i<brojKategorija; i++) {
			System.out.print("Unesite id " + (i+1) + ". kategorije: ");
			Long id = Long.parseLong(unos.nextLine());
			String nazivKategorije = unosNazivaKategorije(unos,i+1);
			artikli = unosArtikala(unos);
			Kategorija<Artikl> kategorija = new Kategorija<Artikl>(id,nazivKategorije,artikli);
			kategorije.add(kategorija);
			mapaKategorijaSaSvimArtiklima.put(kategorija, artikli);
		}
		
		int brojOglasa = provjeraUnosaInt(unos, "Unesite broj artikala koji su aktivno na prodaju: ");
		
		List<Prodaja> aktivniOglasi = new ArrayList<>();
		
		for(int i=0; i<brojOglasa; i++) {
			aktivniOglasi.add(unosAktivnihOglasa(unos, korisnici, kategorije, artikli));
		}
		try {
			ispisAktivnihOglasa(aktivniOglasi);
			ispisPoKategorijama(kategorije);
			//ispisMape(mapaKategorijaSaSvimArtiklima);
		}
		catch (NemoguceOdreditiGrupuOsiguranjaException ex1) {
			System.out.println("");
			logger.error("Pogreška prilikom odreðivanja cijene osiguranja!", ex1);
		}
		logger.info("Kraj rada programa!");
		// Zatvori scanner
		unos.close();
	}
	
	////////////////////////////////
	/*
	 *  Metode za unos
	 */
	////////////////////////////////
	/**
	 * Unos privatnog korisnika tj njegovog imena, prezimena, mail adrese i telefona sa konzole.
	 * 
	 * @param unos Scanner Klasa kako bi mogli unositi podatke sa konzole
	 * @param brojKorisnika Broj korisnika kojeg unosimo.
	 * 
	 * @return Vraæa privatnog korisnika sa svim parametrima (Imenom, prezimenom, mail adresom i brojem telefona).
	 * 
	 */
	// Unos Privatnog Korisnika
	private static PrivatniKorisnik unosPrivatnogKorisnika(Scanner unos, int brojKorisnika) {
		System.out.print("Unesite id " + brojKorisnika + ". osobe: ");
		Long id = Long.parseLong(unos.nextLine());
		System.out.print("Unesite ime " + brojKorisnika + ". osobe: ");
		String ime = unos.nextLine();
		System.out.print("Unesite prezime " + brojKorisnika + ". osobe: ");
		String prezime = unos.nextLine();
		System.out.print("Unesite e-Mail " + brojKorisnika + ". osobe: ");
		String email = unos.nextLine();
		System.out.print("Unesite telefon " + brojKorisnika + ". osobe: ");
		String telefon = unos.nextLine();
		return new PrivatniKorisnik(id, ime, prezime, email, telefon);
	}
	/**
	 * Unos poslovnog korisnika tj njegovog naziva, mail adrese, web adrese i telefona sa konzole.
	 * 
	 * @param unos Scanner Klasa kako bi mogli unositi podatke sa konzole
	 * @param brojKorisnika Broj korisnika kojeg unosimo.
	 * 
	 * @return Vraæa poslovnog korisnika sa svim parametrima (Nazivom, web adresom, mail adresom i brojem telefona).
	 * 
	 */
	// Unos Poslovnog Korisnika
	private static PoslovniKorisnik unosPoslovnogKorisnika(Scanner unos, int brojKorisnika) {
		System.out.print("Unesite id " + brojKorisnika + ". osobe: ");
		Long id = Long.parseLong(unos.nextLine());
		System.out.print("Unesite naziv " + brojKorisnika + ". tvrtke: ");
		String naziv = unos.nextLine();
		System.out.print("Unesite e-Mail " + brojKorisnika + ". tvrtke: ");
		String email = unos.nextLine();
		System.out.print("Unesite web " + brojKorisnika + ". tvrtke: ");
		String web = unos.nextLine();
		System.out.print("Unesite telefon " + brojKorisnika + ". tvrtke: ");
		String telefon = unos.nextLine();
		return new PoslovniKorisnik(id, naziv,web,email,telefon);
	}
	/*
	// Unos Kategorije
	private static Kategorija unosKategorije(Scanner unos, int brojKategorije) {
		System.out.print("Unesite naziv " + brojKategorije + ". kategorije: ");
		String naziv = unos.nextLine();
		// Unos artikla unutar kategorije
		int brojArtikla = provjeraUnosaInt(unos, "Unesite broj artikala koji želite unijeti za unesenu kategoriju: ");
		
		Set<Artikl> artikli = new HashSet<>();
		
		for(int i=0; i<brojArtikla; i++) {
			System.out.println("Unesite tip " + (i+1) + ". artikla");
			int tipArtikla = odabirArtikla(unos);
			if(tipArtikla == 1) {
				artikli.add(unosArtiklaUsluga(unos,i+1));
			} else if(tipArtikla == 2) {
				artikli.add(unosArtiklaAutomobil(unos,i+1));
			} else {
				artikli.add(unosArtiklaStan(unos,i+1));
			}
		}
		return new Kategorija(naziv,artikli);
	}
	*/
	/**
	 * Unos kategorije tj njegovog naziva i njegove artikle.
	 * 
	 * @param unos 				Scanner Klasa kako bi mogli unositi podatke sa konzole
	 * @param brojKategorije 	Broj kategorije koju unosimo.
	 * 
	 * @return Vraæa String naziva kategorije.
	 * 
	 */
	// Unos naziva kategorije
	private static String unosNazivaKategorije(Scanner unos, int brojKategorije) {
		System.out.print("Unesite naziv " + brojKategorije + ". kategorije: ");
		String naziv = unos.nextLine();
		
		return naziv;
	}
	/**
	 * Unos kategorije tj njegovog naziva i njegove artikle.
	 * 
	 * @param unos 				Scanner Klasa kako bi mogli unositi podatke sa konzole
	 * 
	 * @return artikli vraæa listu unešenih artikala.
	 * 
	 */
	private static List<Artikl> unosArtikala(Scanner unos) {
		int brojArtikla = provjeraUnosaInt(unos, "Unesite broj artikala koji želite unijeti za unesenu kategoriju: ");
		
		List<Artikl> artikli = new ArrayList<>();
		
		for(int i=0; i<brojArtikla; i++) {
			System.out.println("Unesite tip " + (i+1) + ". artikla");
			int tipArtikla = odabirArtikla(unos);
			if(tipArtikla == 1) {
				artikli.add(unosArtiklaUsluga(unos,i+1));
			} else if(tipArtikla == 2) {
				artikli.add(unosArtiklaAutomobil(unos,i+1));
			} else {
				artikli.add(unosArtiklaStan(unos,i+1));
			}
		}
		return artikli;
	}
	/**
	 * Unos usluge tj njezinog naslova, opisa i cijene.
	 * 
	 * @param unos			Scanner Klasa kako bi mogli unositi podatke sa konzole
	 * @param brojArtikla 	Broj artikla kojeg unosimo.
	 * @return Vraæa uslugu sa svim parametrima (Nazivom, opisom i cijenom).
	 * 
	 */
	// Unos Artikla Usluga
	private static Usluga unosArtiklaUsluga(Scanner unos, int brojArtikla) {
		System.out.print("Unesite id " + brojArtikla + ". oglasa usluge: ");
		Long id = Long.parseLong(unos.nextLine());
		System.out.print("Unesite naslov " + brojArtikla + ". oglasa usluge: ");
		String naslov = unos.nextLine();
		System.out.print("Unesite opis " + brojArtikla + ". oglasa usluge: ");
		String opis = unos.nextLine();
		BigDecimal cijena = provjeraUnosaBigDecimal(unos, "Unesite cijenu " + brojArtikla + ". oglasa usluge: ");
		Stanje stanje = unosStanja(unos);
		return new Usluga(id,naslov,opis,cijena,stanje);
	}
	/**
	 * Unos automobila tj njegovog naslova, opisa, snage i cijene.
	 * 
	 * @param unos 			Scanner Klasa kako bi mogli unositi podatke sa konzole
	 * @param brojArtikla 	Broj artikla kojeg unosimo.
	 * 
	 * @return Vraæa automobil sa svim parametrima (Naslovom, opisom, cijenom i snagom).
	 * 
	 */
	// Unos Artikla Automobil
	private static Automobil unosArtiklaAutomobil(Scanner unos, int brojArtikla) {
		System.out.print("Unesite id " + brojArtikla + ". oglasa automobila: ");
		Long id = Long.parseLong(unos.nextLine());
		System.out.print("Unesite naslov " + brojArtikla + ". oglasa automobila: ");
		String naslov = unos.nextLine();
		System.out.print("Unesite opis " + brojArtikla + ". oglasa automobila: ");
		String opis = unos.nextLine();
		BigDecimal snaga = provjeraUnosaBigDecimal(unos,"Unesite snagu " + brojArtikla + ". (u ks) oglasa automobila: ");
		BigDecimal cijena = provjeraUnosaBigDecimal(unos, "Unesite cijenu " + brojArtikla + ". oglasa automobila: ");
		Stanje stanje = unosStanja(unos);
		return new Automobil(id,naslov,opis,cijena,snaga, stanje);
	}
	/**
	 * Unos stana tj njegovog naslova, opisa, kvadrature i cijene.
	 * 
	 * @param unos 			Scanner Klasa kako bi mogli unositi podatke sa konzole
	 * @param brojArtikla	Broj artikla kojeg unosimo.
	 * 
	 * @return Vraæa stan sa svim parametrima (Naslovom, opisom, cijenom i kvadraturom).
	 * 
	 */
	// Unos Artikla Stan
	private static Stan unosArtiklaStan(Scanner unos, int brojArtikla) {
		System.out.print("Unesite id " + brojArtikla + ". oglasa stana: ");
		Long id = Long.parseLong(unos.nextLine());
		System.out.print("Unesite naslov " + brojArtikla + ". oglasa stana: ");
		String naslov = unos.nextLine();
		System.out.print("Unesite opis " + brojArtikla + ". oglasa stana: ");
		String opis = unos.nextLine();
		int kvadratura = provjeraUnosaInt(unos,"Unesite kvadraturu " + brojArtikla + ". oglasa stana: ");
		BigDecimal cijena = provjeraUnosaBigDecimal(unos, "Unesite cijenu " + brojArtikla + ". oglasa stana: ");
		Stanje stanje = unosStanja(unos);
		return new Stan(id, naslov,opis,cijena,kvadratura,stanje);
	}
	/**
	 * Unos prodaje tj odabir njezinog korisnika, kategorije i artikla.
	 * 
	 * @param unos Scanner 	Klasa kako bi mogli unositi podatke sa konzole
	 * @param korisnici 	lista korisnika sa svim njihovim podacima.
	 * @param kategorije 	lista kategorija sa svim njihovim podacima.
	 * @param artikli 		lista artikala sa svim njihovim podacima.
	 * 
	 * @return Vraæa oglas prodaje sa svim parametrima (Artiklom, korisnikom i datumom unosa).
	 * 
	 */
	// Unos Prodaje
	private static Prodaja unosAktivnihOglasa(Scanner unos, List<Korisnik> korisnici, List<Kategorija<Artikl>> kategorije, List<Artikl> artikli) {
		System.out.print("Unesite id " + "oglasa: ");
		Long id = Long.parseLong(unos.nextLine());
		Korisnik korisnik = odabirKorisnika(unos, korisnici, "Odaberite korisnika: ");
		Kategorija<Artikl> kategorija = odabirKategorije(unos, kategorije, "Odaberite kategoriju: ");
		Artikl artikl = odabirArtikla(unos, kategorija, artikli, "Odaberite artikl: ");
		return new Prodaja(id, artikl, korisnik, LocalDate.now());
	}
	/**
	 * Unos prodaje tj odabir njezinog korisnika, kategorije i artikla.
	 * 
	 * @param unos Scanner 			Klasa kako bi mogli unositi podatke sa konzole
	 * 
	 * @return Vraæa vrijednost stanja.
	 * 
	 */
	// Unos stanja
	private static Stanje unosStanja(Scanner unos) {
		for (int i = 0; i < Stanje.values().length; i++) {
			System.out.println((i + 1) + ". " + Stanje.values()[i]);
		}
		Integer stanjeRedniBroj = null;
		while (true) {
			stanjeRedniBroj = provjeraUnosaInt(unos, "Odabir stanja artikla >> ");
			if (stanjeRedniBroj >= 1 && stanjeRedniBroj <= Stanje.values().length) {
				return Stanje.values()[stanjeRedniBroj - 1];
			} else {
				System.out.println("Neispravan unos!");
			}
		}
	}
	////////////////////////////////////////////
	/* 
	 * Metode za odabir 
	 */ 
	///////////////////////////////////////////
	/**
	 * Odabir tipa korisnika tj odabir Privatnog ili Poslovnog korisnika.
	 * 
	 * @param unos Scanner 	Klasa kako bi mogli unositi podatke sa konzole
	 * 
	 * @return Vraæa broj odabranog korisnika 1. za privatnog i 2. za poslovnog korisnika.
	 * 
	 */
	// Odabir tipa korisnika
	private static Integer odabirKorisnika(Scanner unos) {
		Integer izlaz = null;
		do {
			String poruka = "1. Privatni\n2. Poslovni\nOdabir>>\n";
			izlaz = provjeraUnosaInt(unos, poruka);
			if(izlaz <= 0 || izlaz > 2) {
				System.out.println("Krivi odabir molimo odaberite ponovno!");
			}
		} while(izlaz <= 0 || izlaz > 2);
		return izlaz;
	}
	/**
	 * Odabir tipa artikla tj odabir Usluge, automobila ili stana.
	 * 
	 * @param unos Scanner 	Klasa kako bi mogli unositi podatke sa konzole
	 * 
	 * @return Vraæa broj odabranog artikla 1. za uslugu i 2. za automobil i 3. za stan.
	 * 
	 */
	// Odabir tipa Artikla
	private static Integer odabirArtikla(Scanner unos) {
		Integer izlaz = null;
		do {
			String poruka = "1. Usluga\n2. Automobil\n3. Stan\nOdabir>>\n";
			izlaz = provjeraUnosaInt(unos, poruka);
			if(izlaz <= 0 || izlaz > 3) {
				System.out.println("Krivi odabir molimo odaberite ponovno!");
			}
		} while(izlaz <= 0 || izlaz > 3);
		return izlaz;
	}
	/**
	 * Odabir tipa korisnika tj odabir unešenih korisnika.
	 * 
	 * @param unos Scanner 	Klasa kako bi mogli unositi podatke sa konzole
	 * @param korisnici 	Korisnici i njihovi podaci.
	 * @param upit			Pitanje za ispis na konzolu (Odaberite korisnika: ).
	 * 
	 * @return Vraæa odabranog korisnika po indeksu polja.
	 * 
	 */
	// Odabir korisnika
	private static Korisnik odabirKorisnika(Scanner unos,List<Korisnik> korisnici, String upit) {
		
		Integer index = null;
		do {	
			int i = 1;
			String poruka = upit + "\n";
			for(Korisnik k : korisnici) {
				poruka = poruka + (i++) + ". " + k.dohvatiKontakt() + "\n"; 
			}
			poruka = poruka + "Odabir >>\n";
			index = provjeraUnosaInt(unos, poruka);
			if(index <= 0 || index > korisnici.size()) {
				System.out.println("Krivi odabir molimo odaberite ponovno!");
			}
		} while(index <= 0 || index > korisnici.size());
		return korisnici.get(index-1);
	}
	/**
	 * Odabir kategorije tj odabir unešenih kategorija.
	 * 
	 * @param unos Scanner 	Klasa kako bi mogli unositi podatke sa konzole
	 * @param kategorije 	Kategorije i njihovi podaci.
	 * @param upit			Pitanje za ispis na konzolu (Odaberite kategoriju: ).
	 * 
	 * @return Vraæa odabranu kategoriju po indeksu polja.
	 * 
	 */
	// Odabir kategorije
	private static Kategorija<Artikl> odabirKategorije(Scanner unos, List<Kategorija<Artikl>> kategorije, String upit) {
		
		Integer index = null;
		do {
			int i = 1;
			String poruka = upit + "\n";
			for(Kategorija<Artikl> k : kategorije) {
				poruka = poruka + (i++) + ". " + k.getNaziv() + "\n";
			}
			poruka = poruka + "Odabir >>\n";
			index = provjeraUnosaInt(unos, poruka);
			if(index <=0 || index > kategorije.size()) {
				System.out.println("Krivi odabir molimo odaberite ponovno!");
			}
		} while(index <= 0 || index > kategorije.size());
		return kategorije.get(index-1);
	}
	/**
	 * Odabir artikla tj odabir unešenih artikala.
	 * 
	 * @param unos Scanner 	Klasa kako bi mogli unositi podatke sa konzole
	 * @param kategorije 	Kategorije i njihovi podaci(U njima se nalaze artikli koje trebamo).
	 * @param upit			Pitanje za ispis na konzolu (Odaberite artikl: ).
	 * @param artikli 		lista artikala sa svim njihovim podacima.
	 * 
	 * @return Vraæa odabrani artikl po indeksu polja.
	 * 
	 */
	// Odabir artikla
	private static Artikl odabirArtikla(Scanner unos, Kategorija<Artikl> kategorije, List<Artikl> artikli, String upit) {
		
		Integer index = null;
		do {
			int i = 1;
			String poruka = upit + "\n";
			for(Artikl a : artikli) {
				poruka = poruka + (i++) + ". " + a.getNaslov() + "\n";
			}
			poruka = poruka + "Odabir >>\n";
			index = provjeraUnosaInt(unos, poruka);
			if(index <=0 || index > kategorije.getArtikli().size()) {
				System.out.println("Krivi odabir molimo odaberite ponovno!");
			}
		} while (index <=0 || index > kategorije.getArtikli().size());
		return artikli.get(index-1);
	}
	/**
	 * Ispis aktivnih oglasa na prodaji.
	 * 
	 * @param aktivniOglasi oglasi koje æemo ispisivati.
	 * 
	 * @throws NemoguceOdreditiGrupuOsiguranjaException Ovdje proslijeðuje metodi tekstOglasa da obradi iznimku.
	 * 
	 */
	// Ispis aktivnih oglasa na prodaji!
	private static void ispisAktivnihOglasa(List<Prodaja> aktivniOglasi) throws NemoguceOdreditiGrupuOsiguranjaException {
	
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
	}
	/**
	 * Ispis kategorija.
	 * 
	 * @param kategorije kategorije koje æemo ispisivati.
	 * 
	 * @throws NemoguceOdreditiGrupuOsiguranjaException Ovdje proslijeðuje metodi tekstOglasa da obradi iznimku.
	 * 
	 */
	// Ispis po kategorijama!
	private static void ispisPoKategorijama(List<Kategorija<Artikl>> kategorije) throws NemoguceOdreditiGrupuOsiguranjaException {
		System.out.println("Ispis po kategorijama: ");
		System.out.println("-------------------------------------------------------------------------------------------------------------------");
		for(Kategorija<Artikl> k : kategorije) {
			System.out.println("Kategorija: " + k.getNaziv());
			ispisArtikala(k.getArtikli());
		}
	}
	/**
	 * Ispis artikala.
	 * 
	 * @param artikli artikli koje æemo ispisivati.
	 * 
	 * @throws NemoguceOdreditiGrupuOsiguranjaException Ovdje proslijeðuje metodi tekstOglasa da obradi iznimku.
	 * 
	 */
	// Ispis artikala sortirano prvo po naslovu pa ako je isti naslov po opisu!
	private static void ispisArtikala(List<Artikl> artikli) throws NemoguceOdreditiGrupuOsiguranjaException {
		
		List<Artikl> listaArtikala = artikli.stream().sorted(Comparator.comparing(Artikl::getNaslov).thenComparing(Artikl::getOpis)).collect(Collectors.toList());
		for(Artikl a : listaArtikala) {
			System.out.println("-------------------------------------------------------------------------------------------------------------------");
			System.out.println(a.tekstOglasa());
		}
		System.out.println("-------------------------------------------------------------------------------------------------------------------");
	}
	/**
	 * Ispis mape.
	 * 
	 * @param mapaKategorijaSaSvimArtiklima mapa sa svim kategorijama i artiklima koji su bili unešeni.
	 * 
	 * @throws NemoguceOdreditiGrupuOsiguranjaException Ovdje proslijeðuje metodi tekstOglasa da obradi iznimku.
	 * 
	 */
	/*
	private static void ispisMape(Map<Kategorija<Artikl>, List<Artikl>> mapaKategorijaSaSvimArtiklima) throws NemoguceOdreditiGrupuOsiguranjaException {
		String crtice = "-------------------------------------------------------------------------------------------------------------------";
		System.out.println("Ispis mape: ");
		for(Kategorija<Artikl> key : mapaKategorijaSaSvimArtiklima.keySet()) {
			System.out.println(crtice);
			System.out.println("Kategorija: " + key.getNaziv());
			key.getArtikli().forEach(a->System.out.println(crtice + "\n" + a.tekstOglasa()));
		}
		System.out.println(crtice);
	}
	*/
	////////////////////////////////////////////
	/* 
	* Metode za provjeru ulaznih podataka 
	*/ 
	///////////////////////////////////////////
	/**
	 * Provjera int unosa sa konzole.
	 * 
	 * @param unos Scanner 	Klasa kako bi mogli unositi podatke sa konzole
	 * @param ispis Poruka koju æemo ponavljati ukoliko je korisnik unio krive podatke sve dok ne unese ispravne podatke.
	 * 
	 * @return Vraæamo provjereni izlazni parametar int tipa.
	 */
	// Provjera da je int tip unosa ispravan ako ne ponavljamo proces dok se ne unese ispravno!
	private static int provjeraUnosaInt(Scanner unos, String ispis) {
		boolean nastaviPetlju = false;
		Integer izlaz = null;
		do {
			try {
				System.out.print(ispis);
				izlaz = Integer.parseInt(unos.nextLine());
				nastaviPetlju = false;
			}
			catch(NumberFormatException ex1) {
				System.out.println("Neispravan unos! Potrebno je unijeti cijeli broj.");
				logger.error("Pogreška prilikom unosa int tipa podatka", ex1);
				nastaviPetlju = true;
			}
		} while(nastaviPetlju);
		return izlaz;
	}
	/**
	 * Provjera BigDecimal unosa sa konzole.
	 * 
	 * @param unos Scanner 	Klasa kako bi mogli unositi podatke sa konzole
	 * @param ispis Poruka koju æemo ponavljati ukoliko je korisnik unio krive podatke sve dok ne unese ispravne podatke.
	 * 
	 * @return Vraæamo provjereni izlazni parametar BigDecimal tipa.
	 */
	// Provjera da je BigDecimal tip unosa ispravan ako ne ponavljamo proces dok se ne unese ispravno!
	private static BigDecimal provjeraUnosaBigDecimal(Scanner unos, String ispis) {
		boolean nastaviPetlju = false;
		BigDecimal izlaz= new BigDecimal(0);
		do {
			try {
				System.out.print(ispis);
				izlaz = unos.nextBigDecimal();
				unos.nextLine();
				nastaviPetlju = false;
			}
			catch(InputMismatchException ex1) {
				System.out.println("Neispravan unos! Potrebno je unijeti cijeli broj.");
				logger.error("Pogreška prilikom unosa BigDecimal tipa podataka", ex1);
				unos.nextLine();
				nastaviPetlju = true;
			}
		} while(nastaviPetlju);
		return izlaz;
	}
	
}