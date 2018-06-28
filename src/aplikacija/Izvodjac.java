package aplikacija;

import com.sun.xml.internal.bind.v2.model.core.ID;
import helper.Trajanje;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Marko on 7.5.2017..
 */


public class Izvodjac {

    enum  TipIzvodjaca {SOLO,DUO,BEND}

    private String naziv_izvodjaca;
    private TipIzvodjaca tip;
    private int god_osnivanja;
    private String god_raspada;
    private String biografija;

    public Izvodjac(String naziv_izvodjaca, TipIzvodjaca tip, int god_osnivanja, String god_raspada, String biografija) {
        this.naziv_izvodjaca = naziv_izvodjaca;
        this.tip = tip;
        this.god_osnivanja = god_osnivanja;
        this.god_raspada = god_raspada;
        this.biografija = biografija;
    }

    public static TipIzvodjaca provera_tipa(String tip)
    {
        if (tip.toUpperCase().trim().equals("BEND")) return TipIzvodjaca.BEND;
        else if (tip.toUpperCase().trim().equals("DUO")) return TipIzvodjaca.DUO;
        else return TipIzvodjaca.SOLO;

    }

    public static int novi_ID_izvodjaca()
    {
        String upit= "SELECT ID_izvodjaca FROM izvodjaci ORDER BY ID_izvodjaca DESC LIMIT 1";
        try {
            ResultSet odgovorbaze= BazaPodataka.getInstanca().select(upit);
            return odgovorbaze.getInt("ID_izvodjaca")+1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void svi_izvodjaci()
    {
        String upit = "SELECT * FROM izvodjaci";

        int ID_izvodjaca;
        String naziv_izvodjaca;
        String tip;
        String god_osnivanja;
        String god_raspada;
        String biografija;


        try {

            ResultSet odgovorBaze = BazaPodataka.getInstanca().select(upit);
            System.out.println("\n\nSvi izvodjaci: \n");
            while (odgovorBaze.next())
            {
                ID_izvodjaca= odgovorBaze.getInt("ID_izvodjaca");
                naziv_izvodjaca = odgovorBaze.getString("naziv_izvodjaca");
                tip = odgovorBaze.getString("tip");
                god_osnivanja = odgovorBaze.getString("god_osnivanja");
                god_raspada = odgovorBaze.getString("god_raspada");
                biografija = odgovorBaze.getString("biografija");

                System.out.println("ID izvodjaca: "+ID_izvodjaca+", naziv izvodjaca: "+naziv_izvodjaca+", tip: "+tip+", godina osnivanja: "+god_osnivanja+", godina raspada: "+god_raspada);

            }

            System.out.println("\n");

        } catch (SQLException e) {

            e.printStackTrace();
            return;
        }
    }
    public static String dohvati_izvodjaca_za_ID(int ID)
    {
        String upit="SELECT * FROM izvodjaci WHERE ID_izvodjaca="+ID+" LIMIT 1";

        try {
            ResultSet odgovorbaze= BazaPodataka.getInstanca().select(upit);
            //String ID_izvodjaca= odgovorbaze.getString("ID_izvodjaca");
            String ime="";
            String tip="";
            String god_raspada="";
            String biografija="";
            int god_osnivanja=0;

            while (odgovorbaze.next()) {
                 ime = odgovorbaze.getString("naziv_izvodjaca");
                 tip= odgovorbaze.getString("tip");
                 god_osnivanja=odgovorbaze.getInt("god_osnivanja");
                 god_raspada=odgovorbaze.getString("god_raspada");
                 biografija= odgovorbaze.getString("biografija");

            }
            Izvodjac izvodjac=new Izvodjac(ime,provera_tipa(tip),god_osnivanja,god_raspada,biografija);
            return izvodjac.toString();

        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int unos_izvodjaca()
    {
        System.out.print("\nUnesite naziv izvođača: ");
        String naziv= Program.konzola.nextLine();
        System.out.print("Unesite tip izvođača (SOLO, DUO ili BEND): ");
        String tip= Program.konzola.nextLine();

        int god_osnivanja= -1;
        while (god_osnivanja==-1)
        {
            System.out.print("Unesite godinu rođenja (formiranja): ");
            god_osnivanja=Provera.proveri_i_unesi_int();
        }

        String god_raspada= "0";
        while (god_raspada.equals("0"))
        {
            System.out.print("Unesite godinu raspada (ostaviti prazno ako je izvođač aktivan): ");
            god_raspada=Provera.proveri_i_unesi_string();

            if (god_raspada.length()==0)
            {
                god_raspada=null;
                break;
            }
        }




        System.out.print("Kratka biografija: ");
        String biografija= Program.konzola.nextLine();



        String upit = "INSERT INTO izvodjaci (ID_izvodjaca, naziv_izvodjaca, tip, god_osnivanja, god_raspada, biografija)" +
                "VALUES ("+Izvodjac.novi_ID_izvodjaca()+",'"+ naziv + "','" + provera_tipa(tip) + "'," + god_osnivanja + "," + god_raspada + ",'"+biografija+"')";
        try {
            return BazaPodataka.getInstanca().iudQuery(upit);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public static int azuriraj_izvodjaca()
    {
        svi_izvodjaci();
        int ID = -1;
        while (ID == -1)
        {
            System.out.print("Unesi ID izvođača: ");
            ID = Provera.proveri_i_unesi_int();
            if ((ID<=0 || ID>=novi_ID_izvodjaca()) && ID!=-1)
            {
                System.err.println("Ne postoji izvođač u bazi čiji je ID "+ID);
                ID=-1;
            }
        }
        String upit = "SELECT * FROM izvodjaci WHERE ID_izvodjaca = " + ID;


        String naziv_izvodjaca;
        String tip;
        String god_osnivanja;
        String god_raspada;
        String biografija;


        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().select(upit);
            naziv_izvodjaca = odgovorBaze.getString("naziv_izvodjaca");
            tip = odgovorBaze.getString("tip");
            god_osnivanja = odgovorBaze.getString("god_osnivanja");
            god_raspada = odgovorBaze.getString("god_raspada");
            biografija = odgovorBaze.getString("biografija");



        } catch (SQLException e) {

            System.err.println("Izvođač nije pronađen u bazi ");
            // e.printStackTrace();
            return 0;
        }


        ArrayList<Podaci> podaci_za_izmenu=new ArrayList<Podaci>();

        System.out.print("Naziv izvođača: ");
        naziv_izvodjaca=Program.konzola.nextLine();
        if (naziv_izvodjaca.length()!=0)
        {
            podaci_za_izmenu.add(new Podaci("naziv_izvodjaca",naziv_izvodjaca));
        }


        System.out.print("Tip: ");
        tip =Program.konzola.nextLine();
        if (tip.length()!=0)
        {
            podaci_za_izmenu.add(new Podaci("tip",tip));
        }


        god_osnivanja ="0";
        while (god_osnivanja.equals("0"))
        {
            System.out.print("Godina rođenja (formiranja): ");
            god_osnivanja =Provera.proveri_i_unesi_string();
            if (god_osnivanja.equals("0")) continue;

            if (god_osnivanja.length()==0) break;
            else  podaci_za_izmenu.add(new Podaci("god_osnivanja",god_osnivanja));

        }



        god_raspada ="0";

        while (god_raspada.equals("0"))
        {
            System.out.print("Godina raspada: ");
            god_raspada =Provera.proveri_i_unesi_string();
            if (god_raspada.equals("0")) continue;

            if (god_raspada.length()==0) break;
            else   podaci_za_izmenu.add(new Podaci("god_raspada",god_raspada));
        }


        System.out.print("Biografija: ");
        biografija =Program.konzola.nextLine();
        if (biografija.length()!=0)
        {
            podaci_za_izmenu.add(new Podaci("biografija",biografija));
        }


        upit = "UPDATE izvodjaci SET ";

        int i = 0;
        for(Podaci podatak : podaci_za_izmenu) {
            upit += podatak.getPoljeUBazi() + "='" + podatak.getVrednost() + "'";
            i++;
            if(i < podaci_za_izmenu.size()) upit +=", ";

        }
        upit += " WHERE ID_izvodjaca = " + ID;

        try {
            System.out.println(upit);
            return BazaPodataka.getInstanca().iudQuery(upit);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static void obrisi_izvodjaca(int ID) {
        String upit = "DELETE FROM izvodjaci WHERE ID_izvodjaca = " + ID;
        String upit2 = "DELETE FROM pesme WHERE ID_izvodjaca = " + ID;
        String upit3 = "DELETE FROM albumi WHERE ID_izvodjaca = " + ID;

        System.out.print("Da li ste sigurni da želite da izbrišete izvođača(D/N): ");
        String pom= Program.konzola.nextLine();

        if (pom.equals("D") || pom.equals("d")) {

            try {
                if (ID<=0 || ID>=novi_ID_izvodjaca()) throw new SQLException();
                BazaPodataka.getInstanca().iudQuery(upit);
                System.out.println("Izvođač je izbrisan");
            } catch (SQLException e) {
                System.err.println("Ne postoji izvođač čiji je ID "+ID);
                return;
            }

            try {
                BazaPodataka.getInstanca().iudQuery(upit2);
            } catch (SQLException e) {
                System.out.println("Izvođač nema pesme!");
            }

            try {
                BazaPodataka.getInstanca().iudQuery(upit3);
            } catch (SQLException e) {
                System.out.println("Izvođač nema albume!");

            }

        }



    }

    public static int pronadji_izvodjaca()
    {
        System.out.print("Unesite ime izvođača: ");
        String ime = Program.konzola.nextLine();

        String upit="SELECT * FROM izvodjaci WHERE naziv_izvodjaca = '"+ime+"'";
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().select(upit);
            int ID_izvodjaca= odgovorBaze.getInt("ID_izvodjaca");
            String naziv_izvodjaca = odgovorBaze.getString("naziv_izvodjaca");
            String tip = odgovorBaze.getString("tip");
            int god_osnivanja= odgovorBaze.getInt("god_osnivanja");
            String god_raspada = odgovorBaze.getString("god_raspada");
            String biografija = odgovorBaze.getString("biografija");


            Izvodjac izvodjac= new Izvodjac(naziv_izvodjaca,provera_tipa(tip),god_osnivanja,god_raspada,biografija);

            System.out.println("\n####### O izvođaču #######\n"+izvodjac.toString());
            return ID_izvodjaca;

        } catch (SQLException e) {


            System.err.println("Nema izvođača u bazi sa imenom '"+ime+"'");
            //e.printStackTrace();
            return 0;
        }

    }

    public static String ime_za_izvodjaca_ciji_je_ID (int ID)
    {
        String upit= "SELECT naziv_izvodjaca FROM izvodjaci WHERE ID_izvodjaca="+ID;

        try {
            ResultSet odgovorbaze= BazaPodataka.getInstanca().select(upit);
            return odgovorbaze.getString("naziv_izvodjaca");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString()
    {

        String bio="\n\t\t ";
        int pom=0;
        for (int i=0; i<biografija.length(); i++)
        {
            if (pom>90 && biografija.charAt(i)==' ')
            {
                bio+="\n\t\t";
                pom=0;

            }
            bio+=biografija.charAt(i);
            pom++;
        }
        return
                "  - Naziv izvođača: " + naziv_izvodjaca +
                "\n  - Tip izvođača: " + tip.toString() +
                "\n  - Godina rođenja (formiranja): " + god_osnivanja +
                "\n  - Godina raspada: " + god_raspada  +
                "\n  - Biografija: " + bio ;
    }
}
