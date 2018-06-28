package aplikacija;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by korisnik on 6.5.17..
 */
public class Logovanje {

    private int ID;
    private String korisnicko_ime;
    private String lozinka;

    public Logovanje(String korisnicko_ime, String lozinka) {
        this.korisnicko_ime = korisnicko_ime;
        this.lozinka = lozinka;
    }

    public static Logovanje unosPodataka()
    {
        String korisnicko_ime,lozinka;
        System.out.println("\n####### Unesite korisničko ime i lozinku #######");
        System.out.print("Korisničko ime: ");
        korisnicko_ime = Program.konzola.nextLine();
        System.out.print("Lozinka: ");
        lozinka =  Program.konzola.nextLine();
        Logovanje loginPodaci= new Logovanje(korisnicko_ime,lozinka);
        return  loginPodaci;
    }

    public boolean proveri_korisnika()
    {
        String upit = "SELECT ID_korisnika FROM korisnici WHERE korisnicko_ime = '" + korisnicko_ime + "' AND lozinka = " + lozinka +";";
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().select(upit);
            this.ID= odgovorBaze.getInt("ID_korisnika");
            return true;

        } catch (SQLException e) {

            return false;
        }

    }

    public boolean proveri_admina()
    {
        String upit = "SELECT ID_administratora FROM administratori WHERE korisnicko_ime = '" + korisnicko_ime + "' AND lozinka = " + lozinka +";";
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().select(upit);
            this.ID = odgovorBaze.getInt("ID_administratora");
            return true;

        } catch (SQLException e) {

            return false;
        }

    }


    public void upis_u_datoteku_aktivnosti(String aktivnost)
    {
        FileWriter upis= null;
        try {
            upis = new FileWriter("aktivnosti.log",true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LocalTime thisSec= LocalTime.now();
        LocalDateTime dateTime =LocalDateTime.now();

        try {
            upis.append("Vreme: "+thisSec.getHour()+":"+ thisSec.getMinute()+":"+ thisSec.getSecond()+"  Datum: "+dateTime.getDayOfMonth()+","+dateTime.getMonth() + "," + dateTime.getYear()+"  Administrator: "+ korisnicko_ime+"  Aktivnost: "+aktivnost+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            upis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getID() {
        return ID;
    }

    public boolean validna_uloga_po(String karakter)
    {
        return this.korisnicko_ime.startsWith(karakter);
    }
}
