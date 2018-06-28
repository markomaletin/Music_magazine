package aplikacija;
import java.util.Scanner;


/**
 * <h1>Muzički servis</h1>
 * Muzički servis je aplikacija sa bazom koja ima izvođače,
 * albume i pesme, kao i korisnike koji koriste aplikaciju i korisnike koji ažuriraju muzički servis.
 * <p>
 *
 * @author Marko Maletin
 * @version 1.0
 * @since 2017-06-27
 */
public class Program {

    public final static Scanner konzola = new Scanner(System.in);

    public static void main(String[] args)
    {

        BazaPodataka.putanja=args[0];

        int br_pokusaja_logovanja = 0;
        final int MAX_BROJ_POKUSAJA = 3;
        final String CHAR_KORISNIK = "k";
        final String CHAR_ADMIN = "a";
        final String CHAR_YES = "y";
        Logovanje loginPodaci;



        while (true) {
            if (br_pokusaja_logovanja == MAX_BROJ_POKUSAJA)
            {
                kraj("Maksimalan broj logovanja. Aplikacija prestaje sa radom. ", 1);
            }
            else
            {
                loginPodaci = Logovanje.unosPodataka();
                br_pokusaja_logovanja++;

                prijava:

                if (loginPodaci.validna_uloga_po(CHAR_KORISNIK) || loginPodaci.validna_uloga_po(CHAR_ADMIN))



                    if (loginPodaci.proveri_korisnika() || loginPodaci.proveri_admina())
                    {
                        meni2:
                        if (loginPodaci.validna_uloga_po(CHAR_KORISNIK))
                        {

                            while(br_pokusaja_logovanja!=0)
                            {
                                meni:

                                switch (Meni.meniKorisnik())
                                {
                                        case 1: {
                                            Biblioteka.pesme_iz_biblioteke(loginPodaci.getID());
                                            Biblioteka.albumi_iz_biblioteke(loginPodaci.getID());
                                            break;
                                        }
                                        case 2: {

                                            int ID_izvodjaca = Izvodjac.pronadji_izvodjaca();
                                            if (ID_izvodjaca==0) break ;

                                            while(true)
                                            {
                                                switch (Meni.pomocniMeniKorisnik()) {
                                                    case 1: {
                                                        Pesma.pesme_od_izvodjaca(ID_izvodjaca);
                                                        Pesma.pesme_iz_albuma(ID_izvodjaca);
                                                        break;

                                                    }

                                                    case 2: {
                                                        Biblioteka.dodaj_pesmu_u_biblioteku(loginPodaci.getID());
                                                        break;
                                                    }
                                                    case 3: {
                                                        Biblioteka.dodaj_album_u_biblioteku(loginPodaci.getID());
                                                        break;
                                                    }
                                                    case 4: {
                                                        break meni ;
                                                    }
                                                }
                                            }
                                        }

                                        case 3: {
                                            Pesma.sve_pesme();
                                            Biblioteka.dodaj_pesmu_u_biblioteku(loginPodaci.getID());
                                            break;
                                        }
                                        case 4: {
                                            Album.svi_albumi();
                                            Biblioteka.dodaj_album_u_biblioteku(loginPodaci.getID());
                                            break;
                                        }
                                        case 5: {
                                            br_pokusaja_logovanja = 0;
                                            break prijava;
                                        }
                                }
                            }


                        }
                        else
                        {
                            loginPodaci.upis_u_datoteku_aktivnosti("Prijava");
                            while (true)
                            {

                               switch (Meni.meniAdmin())

                               {
                                   case 1: {
                                       Pesma.unos_pesme();
                                       loginPodaci.upis_u_datoteku_aktivnosti("Uneo novu pesmu");
                                       break;
                                   }
                                   case 2: {
                                       Izvodjac.unos_izvodjaca();
                                       loginPodaci.upis_u_datoteku_aktivnosti("Uneo novog izvođača");
                                       break;
                                   }
                                   case 3: {
                                       Album.unos_albuma();
                                       loginPodaci.upis_u_datoteku_aktivnosti("Uneo novi album");
                                       break;
                                   }
                                   case 4: {
                                       Pesma.azuriraj_pesmu();
                                       loginPodaci.upis_u_datoteku_aktivnosti("Ažurirao pesmu");
                                       break;
                                   }
                                   case 5: {
                                       Izvodjac.azuriraj_izvodjaca();
                                       loginPodaci.upis_u_datoteku_aktivnosti("Ažurirao izvođača");
                                       break;
                                   }
                                   case 6: {
                                       Album.azuriraj_albuma();
                                       loginPodaci.upis_u_datoteku_aktivnosti("Ažurirao album");
                                       break;
                                   }
                                   case 7: {

                                       Pesma.sve_pesme();

                                       int ID = -1;
                                       while (ID == -1)
                                       {
                                           System.out.print("Unesite indeks pesme kojeg želite da izbrišete: ");
                                           ID = Provera.proveri_i_unesi_int();
                                       }

                                       Pesma.obrisi_pesmu(ID);
                                       loginPodaci.upis_u_datoteku_aktivnosti("Obrisao pesmu");
                                       break;
                                   }

                                   case 8: {
                                       Izvodjac.svi_izvodjaci();

                                       int ID = -1;
                                       while (ID == -1)
                                       {
                                           System.out.print("Unesite indeks izvođača kojeg želite da izbrišete: ");
                                           ID = Provera.proveri_i_unesi_int();
                                       }
                                       Izvodjac.obrisi_izvodjaca(ID);
                                       loginPodaci.upis_u_datoteku_aktivnosti("Obrisao izvođača");
                                       break;
                                   }

                                   case 9: {
                                       Album.svi_albumi();

                                       int ID = -1;
                                       while (ID == -1)
                                       {
                                           System.out.print("Unesite indeks albuma kojeg želite da izbrišete: ");
                                           ID = Provera.proveri_i_unesi_int();
                                       }
                                       Album.obrisi_album(ID);
                                       loginPodaci.upis_u_datoteku_aktivnosti("Obrisao album");
                                       break;
                                   }

                                   case 10: {
                                       br_pokusaja_logovanja = 0;
                                       loginPodaci.upis_u_datoteku_aktivnosti("Odjava\n");
                                       break prijava;
                                   }

                               }


                            }

                        }

                        break;

                    } else {
                        System.err.println("Pogrešno korisnicko ime ili lozinka! ");
                    }

                else
                {
                    System.out.print("Prijava (Y) ili  gašenje (N): ");
                    String izbor_korisnika = konzola.nextLine();

                    if (!izbor_korisnika.equals(CHAR_YES))
                    {
                        kraj("Izbor korisnika. Aplikacija prestaje sa radom. ", 0);
                    }
                }

            }


        }




    }

    public static void kraj(String poruka, int status)
    {
        System.out.println(poruka);
        System.exit(status);
    }



}

