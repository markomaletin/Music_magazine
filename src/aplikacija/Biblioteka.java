package aplikacija;

import com.sun.xml.internal.bind.v2.model.core.ID;
import helper.Trajanje;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Marko on 24.5.2017..
 */
public class Biblioteka {

    public static void pesme_iz_biblioteke(int ID_korisnika)
    {

        String upit = "SELECT * FROM pesme WHERE ID_pesme IN ( SELECT ID_pesme FROM pesme_bib WHERE ID_korisnika = " + ID_korisnika +") ORDER BY ID_albuma;";

        try {
            System.out.println("\n###### Pesme ######");
            ResultSet odgovorBaze = BazaPodataka.getInstanca().select(upit);
            while (odgovorBaze.next())
            {
                String ID_pesme = odgovorBaze.getString("ID_pesme");
                String naziv_pesme = odgovorBaze.getString("naziv_pesme");
                int identifikacioni_broj_izvodjaca = odgovorBaze.getInt("ID_izvodjaca");
                String identifikacioni_broj_albuma = odgovorBaze.getString("ID_albuma");
                int trajanje = odgovorBaze.getInt("trajanje");
                String a = odgovorBaze.getString(1);

                Trajanje trajanje_pesme= Trajanje.vreme(trajanje);


                System.out.println("\nID pesme: "+ ID_pesme +"\nNaziv pesme: "+naziv_pesme+"\nIzvođač:\n"+Izvodjac.dohvati_izvodjaca_za_ID(identifikacioni_broj_izvodjaca)+"\nID albuma: "+identifikacioni_broj_albuma+"\nTrajanje: "+trajanje_pesme.toString());
            }

        } catch (SQLException e) {

            System.err.println("Nema pesama u biblioteci! ");
            e.printStackTrace();

        }
    }

    public static void albumi_iz_biblioteke(int ID_korisnika)
    {

        String upit = "SELECT ID_albuma, ID_izvodjaca, naziv_albuma FROM albumi WHERE ID_albuma IN ( SELECT ID_albuma FROM albumi_bib WHERE ID_korisnika = " + ID_korisnika +");";




        try {
            System.out.println("\n###### Albumi ######");
            ResultSet odgovorBaze = BazaPodataka.getInstanca().select(upit);
            while (odgovorBaze.next())
            {
                int ID_albuma= odgovorBaze.getInt("ID_albuma");
                int ID_izvodjaca= odgovorBaze.getInt("ID_izvodjaca");
                String naziv_albuma = odgovorBaze.getString("naziv_albuma");

                System.out.println("\nNaziv albuma: "+ naziv_albuma+"\nNaziv izvođača: "+Izvodjac.ime_za_izvodjaca_ciji_je_ID(ID_izvodjaca)+"\nUkupno trajanje pesama u albumu: "+ Trajanje.vreme(Trajanje.ukupno_trajanje_albuma(ID_albuma)));
            }




        } catch (SQLException e) {

            System.err.println("Nema albuma u biblioteci ");
            e.printStackTrace();

        }
    }

    public static void dodaj_pesmu_u_biblioteku(int ID_korisnika)
    {
        System.out.print("Unesite ID pesme koju želite da stavite u biblioteku: ");

        int ID_pesme=Provera.proveri_i_unesi_int();
        if (ID_pesme==-1) return;


        String upit="SELECT ID_pesme FROM pesme_bib WHERE ID_pesme = "+ ID_pesme + " AND ID_korisnika = " + ID_korisnika;
        try {

            ResultSet odgovorBaze = BazaPodataka.getInstanca().select(upit);
            odgovorBaze.getInt("ID_pesme");
            System.err.println("Pesma već postoji u vašoj biblioteci, ne možete je opet dodati!");
            return;


        } catch (SQLException e) {
            upit="SELECT ID_pesme FROM pesme WHERE ID_pesme = "+ ID_pesme;
            try {

                ResultSet odgovorBaze = BazaPodataka.getInstanca().select(upit);
                odgovorBaze.getInt("ID_pesme");

                upit = "INSERT INTO pesme_bib (ID_pesme, ID_korisnika) " +
                        "VALUES ("+ ID_pesme + "," + ID_korisnika + ")";
                try {
                    BazaPodataka.getInstanca().iudQuery(upit);
                    System.out.println("Dodali ste pesmu u bilioteku.");
                } catch (SQLException a) {
                    a.printStackTrace();
                    return;
                }


            } catch (SQLException k) {

                System.err.println("U bazi ne postoji pesma čiji je ID '"+ID_pesme+"'");
                //k.printStackTrace();
                return;

            }
        }




    }


    public static void dodaj_album_u_biblioteku(int ID_korisnika)
    {
        System.out.print("Unesite ID albuma koju želite da stavite u biblioteku: ");
        int ID_albuma = Provera.proveri_i_unesi_int();
        if (ID_albuma==-1) return;


        String upit="SELECT ID_albuma FROM albumi_bib WHERE ID_albuma = "+ ID_albuma + " AND ID_korisnika = " + ID_korisnika;
        try {

            ResultSet odgovorBaze = BazaPodataka.getInstanca().select(upit);
            odgovorBaze.getInt("ID_albuma");
            System.err.println("Album već postoji u vašoj biblioteci, ne možete je opet dodati!");
            return;


        } catch (SQLException e) {
            upit="SELECT ID_albuma FROM albumi WHERE ID_albuma = "+ ID_albuma;
            try {

                ResultSet odgovorBaze = BazaPodataka.getInstanca().select(upit);
                odgovorBaze.getInt("ID_albuma");

                upit = "INSERT INTO albumi_bib (ID_albuma, ID_korisnika) " +
                        "VALUES ("+ ID_albuma + "," + ID_korisnika + ")";

                try {
                    BazaPodataka.getInstanca().iudQuery(upit);
                    System.out.println("Dodali ste album u bilioteku.");
                } catch (SQLException a) {
                    a.printStackTrace();
                    return;
                }


            } catch (SQLException k) {

                System.err.println("U bazi ne postoji album čiji je ID '"+ID_albuma+"'");
                //k.printStackTrace();
                return;

            }
        }


    }



}
