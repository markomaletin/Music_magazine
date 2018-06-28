package helper;

import aplikacija.BazaPodataka;
import aplikacija.Program;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Marko on 22.5.2017..
 */
public class Trajanje {

    private int trajanje_pesme_sati;
    private int trajanje_pesme_minuti;
    private int trajanje_pesme_sekunde;

    public static Trajanje vreme(int vreme)
    {

        int trajanje_pesme_sati= vreme / 3600;
        vreme%= 3600;
        int trajanje_pesme_minuti=vreme/60;
        vreme%=60;
        int trajanje_pesme_sekunde=vreme;

        Trajanje trajanje=new Trajanje(trajanje_pesme_sati,trajanje_pesme_minuti,trajanje_pesme_sekunde);
        return trajanje;

    }

    public static int vreme_za_bazu(Trajanje trajanje)
    {
        return trajanje.trajanje_pesme_sati*3600+trajanje.trajanje_pesme_minuti*60+trajanje.trajanje_pesme_sekunde;
    }

    public void set_sati(int trajanje_pesme_sati) {
        this.trajanje_pesme_sati = trajanje_pesme_sati;
    }

    public void set_minuti(int trajanje_pesme_minuti) {
        this.trajanje_pesme_minuti = trajanje_pesme_minuti;
    }

    public void set_sekunde(int trajanje_pesme_sekunde) {
        this.trajanje_pesme_sekunde = trajanje_pesme_sekunde;
    }

    @Override
    public String toString() {
        return  trajanje_pesme_sati +
                ":" + trajanje_pesme_minuti +
                ":" + trajanje_pesme_sekunde;
    }

    public Trajanje(int trajanje_pesme_sati, int trajanje_pesme_minuti, int trajanje_pesme_sekunde) {
        this.trajanje_pesme_sati = trajanje_pesme_sati;
        this.trajanje_pesme_minuti = trajanje_pesme_minuti;
        this.trajanje_pesme_sekunde = trajanje_pesme_sekunde;
    }


    public static int ukupno_trajanje_albuma( int ID_albuma)
    {

        String upit = "SELECT trajanje FROM pesme WHERE ID_albuma = " + ID_albuma ;


        int ukupno_trajanje=0;

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().select(upit);
            while (odgovorBaze.next())
            {
                ukupno_trajanje += odgovorBaze.getInt("trajanje");
            }
            return ukupno_trajanje;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
