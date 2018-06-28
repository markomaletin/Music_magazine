package aplikacija;

import helper.Trajanje;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Marko on 24.5.2017..
 */
public class Album {
    private int ID_izvodjaca;
    private String naziv_albuma;
    private int god_izdanja;
    private String zanr;

    public Album(int ID_izvodjaca, String naziv_albuma, int god_izdanja, String zanr) {
        this.ID_izvodjaca = ID_izvodjaca;
        this.naziv_albuma = naziv_albuma;
        this.god_izdanja = god_izdanja;
        this.zanr = zanr;
    }

    public static int novi_ID_albuma()
    {
        String upit= "SELECT ID_albuma FROM albumi ORDER BY ID_albuma DESC LIMIT 1";
        try {
            ResultSet odgovorbaze= BazaPodataka.getInstanca().select(upit);
            return odgovorbaze.getInt("ID_albuma")+1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void svi_albumi()
    {
        String upit = "SELECT * FROM albumi";

        int ID_albuma;
        String ID_izvodjaca;
        String naziv_albuma;
        String god_izdanja;
        String zanr;

        try {

            ResultSet odgovorBaze = BazaPodataka.getInstanca().select(upit);
            System.out.println("\n\nSvi albumi:\n");
            while (odgovorBaze.next())
            {
                ID_albuma=odgovorBaze.getInt("ID_albuma");
                ID_izvodjaca = odgovorBaze.getString("ID_izvodjaca");
                naziv_albuma = odgovorBaze.getString("naziv_albuma");
                god_izdanja = odgovorBaze.getString("god_izdanja");
                zanr = odgovorBaze.getString("zanr");

                System.out.println("ID albuma: "+ID_albuma+", ID izvodjaca: "+ID_izvodjaca+", naziv albuma: "+naziv_albuma+", godina izdanja: "+god_izdanja+", zanr: "+zanr);
            }

            System.out.println("\n");

        } catch (SQLException e) {

            e.printStackTrace();
            return;
        }
    }

    public static void unos_albuma()
    {
        Izvodjac.svi_izvodjaci();

        int ID_izvodjaca= -1;
        while (ID_izvodjaca==-1)
        {
            System.out.print("Unesite ID izvođača: ");
            ID_izvodjaca=Provera.proveri_i_unesi_int();
            if ((ID_izvodjaca<=0 || ID_izvodjaca>Izvodjac.novi_ID_izvodjaca()) && ID_izvodjaca!=-1)
            {
                System.err.println("Ne postoji izvođač u bazi čiji je ID "+ID_izvodjaca);
                ID_izvodjaca=-1;
            }
        }

        System.out.print("Unesite naziv albuma: ");
        String naziv_albuma= Program.konzola.nextLine();


        int god_izdanja= -1;
        while (god_izdanja==-1)
        {
            System.out.print("Unesite godinu izdanja: ");
            god_izdanja=Provera.proveri_i_unesi_int();
        }

        System.out.print("Unesite zanr albuma: ");
        String zanr= Program.konzola.nextLine();



        String upit = "INSERT INTO albumi (ID_albuma,ID_izvodjaca, naziv_albuma , god_izdanja, zanr)" +
                "VALUES ("+Album.novi_ID_albuma()+"," + ID_izvodjaca + ",'" + naziv_albuma + "'," + god_izdanja + ",'"+zanr+"')";
        try {
            BazaPodataka.getInstanca().iudQuery(upit);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("\nUnesite pesme iz albuma (za prekid pritisnite ENTER za naziv pesme)");
        while(true)
        {

            System.out.print("\nUnesite naziv pesme: ");
            String naziv= Program.konzola.nextLine();
            if (naziv.length()==0) return;

            System.out.print("Unesite trajanje pesme u satima: ");
            int trajanje_pesme_sat= Program.konzola.nextInt();
            Program.konzola.nextLine();

            System.out.print("Unesite trajanje pesme u minutima: ");
            int trajanje_pesme_min= Program.konzola.nextInt();
            Program.konzola.nextLine();

            System.out.print("Unesite trajanje pesme u sekundama: ");
            int trajanje_pesme_sek= Program.konzola.nextInt();
            Program.konzola.nextLine();

            Trajanje trajanje=new Trajanje(trajanje_pesme_sat,trajanje_pesme_min,trajanje_pesme_sek);


            upit = "INSERT INTO pesme (ID_pesme, naziv_pesme, ID_izvodjaca, ID_albuma, trajanje)" +
                    "VALUES ("+Pesma.novi_ID_pesme()+",'"+ naziv + "'," + ID_izvodjaca + "," + (Album.novi_ID_albuma()-1) + "," + Trajanje.vreme_za_bazu(trajanje) + ")";
            try {
                BazaPodataka.getInstanca().iudQuery(upit);
            } catch (SQLException e) {
                e.printStackTrace();

            }


        }


    }


    public static int azuriraj_albuma()
    {
        svi_albumi();
        int ID = -1;
        while (ID == -1)
        {
            System.out.print("Unesi ID albuma: ");
            ID = Provera.proveri_i_unesi_int();
            if ((ID<=0 || ID>=novi_ID_albuma()) && ID!=-1)
            {
                System.err.println("Ne postoji album u bazi čiji je ID "+ID);
                ID=-1;
            }
        }

        String upit = "SELECT * FROM albumi WHERE ID_albuma = " + ID;

        String ID_izvodjaca;
        String naziv_albuma;
        String god_izdanja;
        String zanr;

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().select(upit);
            ID_izvodjaca = odgovorBaze.getString("ID_izvodjaca");
            naziv_albuma = odgovorBaze.getString("naziv_albuma");
            god_izdanja = odgovorBaze.getString("god_izdanja");
            zanr = odgovorBaze.getString("zanr");



        } catch (SQLException e) {

            System.err.println("Album nije pronadjen u bazi ");
            // e.printStackTrace();
            return 0;
        }


        ArrayList<Podaci> podaci_za_izmenu=new ArrayList<Podaci>();
        Izvodjac.svi_izvodjaci();
        ID_izvodjaca="0";
        while (ID_izvodjaca.equals("0"))
        {
            System.out.print("ID izvođača: ");
            ID_izvodjaca =Provera.proveri_i_unesi_string();
            if (ID_izvodjaca.equals("0")) continue;

            if (ID_izvodjaca.length()==0) break;

            if (Integer.parseInt(ID_izvodjaca)<=0 || Integer.parseInt(ID_izvodjaca)>= Izvodjac.novi_ID_izvodjaca())
            {
                System.err.println("Ne postoji izvodjac u bazi čiji je ID "+ID_izvodjaca);
                ID_izvodjaca="0";
                continue;
            }
            else podaci_za_izmenu.add(new Podaci("ID_izvodjaca",ID_izvodjaca));

        }



        System.out.print("Naziv albuma: ");
        naziv_albuma =Program.konzola.nextLine();
        if (naziv_albuma.length()!=0)
        {
            podaci_za_izmenu.add(new Podaci("naziv_albuma",naziv_albuma));
        }


        god_izdanja ="0";

        while (god_izdanja.equals("0"))
        {
            System.out.print("Godina izdanja: ");
            god_izdanja =Provera.proveri_i_unesi_string();
            if (god_izdanja.equals("0")) continue;

            if (god_izdanja.length()==0) break;
            else podaci_za_izmenu.add(new Podaci("god_izdanja",god_izdanja));

        }

        System.out.print("Zanr: ");
        zanr =Program.konzola.nextLine();
        if (zanr.length()!=0)
        {
            podaci_za_izmenu.add(new Podaci("zanr",zanr));
        }



        upit = "UPDATE albumi SET ";
        int i = 0;
        for(Podaci podatak : podaci_za_izmenu) {
            upit += podatak.getPoljeUBazi() + "='" + podatak.getVrednost() + "'";
            i++;
            if(i < podaci_za_izmenu.size()) upit +=", ";

        }
        upit += " WHERE ID_albuma = " + ID;

        try {
            System.out.println(upit);
            return BazaPodataka.getInstanca().iudQuery(upit);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static void obrisi_album(int ID) {

        String upit1 = "DELETE FROM albumi WHERE ID_albuma = " + ID;
        String upit2 = "DELETE FROM pesme WHERE ID_albuma = " + ID;


        System.out.print("Da li ste sigurni da želite da izbrišete izvođača(D/N): ");

        String pom= Program.konzola.nextLine();

        if (pom.equals("D") || pom.equals("d")) {
            try {
                if (ID<=0 || ID>=novi_ID_albuma()) throw new SQLException();
                BazaPodataka.getInstanca().iudQuery(upit1);
                System.out.println("Album je izbrisan.");
            } catch (SQLException e) {
                System.err.println("Ne postoji album u bazi čiji je ID "+ID);
                return;
            }

            try {
                BazaPodataka.getInstanca().iudQuery(upit2);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    }


}
