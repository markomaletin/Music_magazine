package aplikacija;
import helper.Trajanje;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Marko on 7.5.2017..
 */
public class Pesma {

    private int ID_pesme;
    private String naziv_pesme;
    private int identifikacioni_broj_izvodjaca;
    private String identifikacioni_broj_albuma;
    private Trajanje trajanje;

    public Pesma(int ID_pesme,String naziv_pesme, int identifikacioni_broj_izvodjaca, String identifikacioni_broj_albuma, Trajanje trajanje) {
        this.ID_pesme=ID_pesme;
        this.naziv_pesme = naziv_pesme;
        this.identifikacioni_broj_izvodjaca = identifikacioni_broj_izvodjaca;
        this.identifikacioni_broj_albuma = identifikacioni_broj_albuma;
        this.trajanje=trajanje;
    }

    public static int novi_ID_pesme()
    {
        String upit= "SELECT ID_pesme FROM pesme ORDER BY ID_pesme DESC LIMIT 1";
        try {
            ResultSet odgovorbaze= BazaPodataka.getInstanca().select(upit);
            return odgovorbaze.getInt("ID_pesme")+1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void sve_pesme()
    {
        String upit = "SELECT * FROM pesme";


        int ID_pesme;
        String naziv_pesme;
        int identifikacioni_broj_izvodjaca;
        String identifikacioni_broj_albuma;
        int trajanje;

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().select(upit);
            System.out.println("\n\nSve pesme:\n");
            while (odgovorBaze.next()) {
                ID_pesme = odgovorBaze.getInt("ID_pesme");
                naziv_pesme = odgovorBaze.getString("naziv_pesme");
                identifikacioni_broj_izvodjaca = odgovorBaze.getInt("ID_izvodjaca");
                identifikacioni_broj_albuma = odgovorBaze.getString("ID_albuma");
                trajanje = odgovorBaze.getInt("trajanje");

                Pesma pesma= new Pesma(ID_pesme,naziv_pesme,identifikacioni_broj_izvodjaca,identifikacioni_broj_albuma,Trajanje.vreme(trajanje));
                System.out.println(pesma.toString());
            }

            System.out.println("\n");

        } catch (SQLException e) {

            e.printStackTrace();
            return;
        }
    }

    public static int unos_pesme()
    {
        System.out.print("\nUnesite naziv pesme: ");
        String naziv= Program.konzola.nextLine();

        Izvodjac.svi_izvodjaci();
        int identifikacioni_broj_izvodjaca=-1;
        while (identifikacioni_broj_izvodjaca==-1)
        {
            System.out.print("Unesite identifikacioni broj izvođača: ");
            identifikacioni_broj_izvodjaca=Provera.proveri_i_unesi_int();

            if ((identifikacioni_broj_izvodjaca<=0 || identifikacioni_broj_izvodjaca>=Izvodjac.novi_ID_izvodjaca()) && identifikacioni_broj_izvodjaca!=-1)
            {
                System.err.println("Ne postoji izvođač u bazi čiji je ID "+identifikacioni_broj_izvodjaca);
                identifikacioni_broj_izvodjaca=-1;
            }
        }

        Album.svi_albumi();
        String identifikacioni_broj_albuma= "0";
        while (identifikacioni_broj_albuma.equals("0"))
        {
            System.out.print("Unesite identifikacioni broj albuma(preskočite ako se ne nalazi u albumu): ");
            identifikacioni_broj_albuma=Provera.proveri_i_unesi_string();

            if (identifikacioni_broj_albuma.length()==0)
            {
                identifikacioni_broj_albuma=null;
                break;
            }

            if (Integer.parseInt(identifikacioni_broj_albuma)<=0 || Integer.parseInt(identifikacioni_broj_albuma)>= Album.novi_ID_albuma())
            {
                System.err.println("Ne postoji album u bazi čiji je ID "+identifikacioni_broj_albuma);
                identifikacioni_broj_albuma="0";
                continue;
            }

        }



        int trajanje_pesme_sat= -1;
        while (trajanje_pesme_sat==-1)
        {
            System.out.print("Unesite trajanje pesme u satima: ");
            trajanje_pesme_sat=Provera.proveri_i_unesi_int();
            if ((trajanje_pesme_sat<0 || trajanje_pesme_sat>24)&& trajanje_pesme_sat!=-1)
            {
                System.err.println("Uneto vreme nije u odgovarajućem formatu");
                trajanje_pesme_sat=-1;
            }
        }

        int trajanje_pesme_min= -1;
        while (trajanje_pesme_min==-1)
        {
            System.out.print("Unesite trajanje pesme u minutima: ");
            trajanje_pesme_min=Provera.proveri_i_unesi_int();
            if ((trajanje_pesme_min<0 || trajanje_pesme_min>60)&& trajanje_pesme_min!=-1)
            {
                System.err.println("Uneto vreme nije u odgovarajućem formatu");
                trajanje_pesme_min=-1;
            }
        }

        int trajanje_pesme_sekunde= -1;
        while (trajanje_pesme_sekunde==-1)
        {
            System.out.print("Unesite trajanje pesme u sekundama: ");
            trajanje_pesme_sekunde=Provera.proveri_i_unesi_int();
            if ((trajanje_pesme_sekunde<0 || trajanje_pesme_sekunde>60)&& trajanje_pesme_sekunde!=-1)
            {
                System.err.println("Uneto vreme nije u odgovarajućem formatu");
                trajanje_pesme_sekunde=-1;
            }
        }

        Trajanje trajanje_pesme= new Trajanje(trajanje_pesme_sat,trajanje_pesme_min,trajanje_pesme_sekunde);


        String upit = "INSERT INTO pesme (ID_pesme, naziv_pesme, ID_izvodjaca, ID_albuma, trajanje)" +
                "VALUES ("+Pesma.novi_ID_pesme()+",'"+ naziv + "'," + identifikacioni_broj_izvodjaca + "," + identifikacioni_broj_albuma + "," + Trajanje.vreme_za_bazu(trajanje_pesme) + ")";
        try {
            return BazaPodataka.getInstanca().iudQuery(upit);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }


    public static int azuriraj_pesmu()
    {

        sve_pesme();
        int ID = -1;
        while (ID == -1)
        {
            System.out.print("Unesi ID pesme: ");
            ID = Provera.proveri_i_unesi_int();
            if ((ID<=0 || ID>=novi_ID_pesme()) && ID!=-1)
            {
                System.err.println("Ne postoji pesma u bazi čiji je ID "+ID);
                ID=-1;
            }
        }

        String upit = "SELECT * FROM pesme WHERE ID_pesme = " + ID;


        String naziv_pesme;
        String identifikacioni_broj_izvodjaca;
        String identifikacioni_broj_albuma;
        String trajanje;

        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().select(upit);
            naziv_pesme = odgovorBaze.getString("naziv_pesme");
            identifikacioni_broj_izvodjaca = odgovorBaze.getString("ID_izvodjaca");
            identifikacioni_broj_albuma = odgovorBaze.getString("ID_albuma");
            trajanje = odgovorBaze.getString("trajanje");



        } catch (SQLException e) {

            System.err.println("Pesma nije pronađena u bazi! ");
            //e.printStackTrace();
            return 0;
        }


        ArrayList<Podaci> podaci_za_izmenu=new ArrayList<Podaci>();

        System.out.print("Naziv pesme: ");
        naziv_pesme=Program.konzola.nextLine();
        if (naziv_pesme.length()!=0)
        {
            podaci_za_izmenu.add(new Podaci("naziv_pesme",naziv_pesme));
        }


        Izvodjac.svi_izvodjaci();
        identifikacioni_broj_izvodjaca ="0";

        while (identifikacioni_broj_izvodjaca.equals("0"))
        {
            System.out.print("ID izvođača: ");
            identifikacioni_broj_izvodjaca =Provera.proveri_i_unesi_string();
            if (identifikacioni_broj_izvodjaca.equals("0")) continue;

            if (identifikacioni_broj_izvodjaca.length()==0) break;

            if (Integer.parseInt(identifikacioni_broj_izvodjaca)<=0 || Integer.parseInt(identifikacioni_broj_izvodjaca)>= Izvodjac.novi_ID_izvodjaca())
            {
                System.err.println("Ne postoji izvodjac u bazi čiji je ID "+identifikacioni_broj_izvodjaca);
                identifikacioni_broj_izvodjaca="0";
                continue;
            }
            else podaci_za_izmenu.add(new Podaci("ID_izvodjaca",identifikacioni_broj_izvodjaca));

        }


        Album.svi_albumi();
        identifikacioni_broj_albuma ="0";
        while (identifikacioni_broj_albuma.equals("0"))
        {
            System.out.print("ID albuma: ");
            identifikacioni_broj_albuma =Provera.proveri_i_unesi_string();
            if (identifikacioni_broj_albuma.equals("0")) continue;

            if (identifikacioni_broj_albuma.length()==0) break;

            if (Integer.parseInt(identifikacioni_broj_albuma)<=0 || Integer.parseInt(identifikacioni_broj_albuma)>= Album.novi_ID_albuma())
            {
                System.err.println("Ne postoji album u bazi čiji je ID "+identifikacioni_broj_albuma);
                identifikacioni_broj_albuma="0";
                continue;
            }
            else podaci_za_izmenu.add(new Podaci("ID_albuma",identifikacioni_broj_albuma));

        }



        Trajanje trajanje1=Trajanje.vreme(Integer.parseInt(trajanje));


        trajanje ="-1";

        while (trajanje.equals("-1"))
        {
            System.out.print("Trajanje u satima: ");
            trajanje =Provera.proveri_za_trajanje();
            if (trajanje.equals("-1")) continue;

            if (trajanje.length()==0) break;

            if (Integer.parseInt(trajanje)<0 || Integer.parseInt(trajanje)>= 60)
            {
                System.err.println("Uneto vreme nije u odgovarajućem formatu");
                trajanje="-1";
                continue;
            }
            else
                trajanje1.set_sati(Integer.parseInt(trajanje));


        }



        trajanje ="-1";
        while (trajanje.equals("-1"))
        {
            System.out.print("Trajanje u minutima: ");
            trajanje =Provera.proveri_za_trajanje();
            if (trajanje.equals("-1")) continue;

            if (trajanje.length()==0) break;

            if (Integer.parseInt(trajanje)<0 || Integer.parseInt(trajanje)>= 60)
            {
                System.err.println("Uneto vreme nije u odgovarajućem formatu");
                trajanje="-1";
                continue;
            }
            else trajanje1.set_minuti(Integer.parseInt(trajanje));

        }


        trajanje ="-1";

        while (trajanje.equals("-1"))
        {
            System.out.print("Trajanje u sekundama: ");
            trajanje =Provera.proveri_za_trajanje();
            if (trajanje.equals("-1")) continue;

            if (trajanje.length()==0) break;

            if (Integer.parseInt(trajanje)<0 || Integer.parseInt(trajanje)>= 60)
            {
                System.err.println("Uneto vreme nije u odgovarajućem formatu");
                trajanje="-1";
                continue;
            }
            else trajanje1.set_sekunde(Integer.parseInt(trajanje));

        }

        int trajanje_ukupno = Trajanje.vreme_za_bazu(trajanje1);
        String aa= String.valueOf(trajanje_ukupno);

        podaci_za_izmenu.add(new Podaci("trajanje", aa ));





        upit = "UPDATE pesme SET ";
        int i = 0;
        for(Podaci podatak : podaci_za_izmenu) {
            upit += podatak.getPoljeUBazi() + "='" + podatak.getVrednost() + "'";
            i++;
            if(i < podaci_za_izmenu.size()) upit +=", ";

        }

        upit += " WHERE ID_pesme = " + ID;

        try {
            System.out.println(upit);
            return BazaPodataka.getInstanca().iudQuery(upit);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }




    public static void obrisi_pesmu(int ID) {
        String upit = "DELETE FROM pesme WHERE ID_pesme = " + ID;
        System.out.print("Da li ste sigurni da želite da izbrišete pesmu(D/N): ");
        String pom = Program.konzola.nextLine();

        if (pom.equals("D") || pom.equals("d"))
        {
            try {

                 if (ID<=0 || ID>=Pesma.novi_ID_pesme()) throw new SQLException();
                 BazaPodataka.getInstanca().iudQuery(upit);
                 System.out.println("Pesma je izbrisana");

            } catch (SQLException e) {
                System.err.println("Ne postoji pesma u bazi čiji je ID "+ID);
                return ;
            }
        }
        else return;

    }

    public static void pesme_od_izvodjaca(int ID_izvodjaca)
    {
        String upit="SELECT * FROM pesme WHERE ID_izvodjaca = "+ ID_izvodjaca+ " AND ID_albuma IS NULL";
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().select(upit);
            System.out.println("\n##### Pesme #####");
            while (odgovorBaze.next())
            {
                int ID_pesme = odgovorBaze.getInt("ID_pesme");
                String naziv_pesme = odgovorBaze.getString("naziv_pesme");
                int identifikacioni_broj_izvodjaca = odgovorBaze.getInt("ID_izvodjaca");
                String identifikacioni_broj_albuma = odgovorBaze.getString("ID_albuma");
                int trajanje = odgovorBaze.getInt("trajanje");
                Pesma pesma = new Pesma(ID_pesme,naziv_pesme,identifikacioni_broj_izvodjaca,identifikacioni_broj_albuma,Trajanje.vreme(trajanje));
                System.out.println(pesma.toString());
            }

        } catch (SQLException e) {

            System.err.println("Izvođač nema ni jednu pesmu");
           // e.printStackTrace();
        }


    }

    public static void pesme_iz_albuma(int ID_izvodjaca) {


        String upit="SELECT * FROM pesme WHERE ID_izvodjaca = "+ ID_izvodjaca+ " AND ID_albuma IS NOT NULL ORDER BY ID_albuma asc";
        int ID_albuma = 0;

        try {
            System.out.println("\n##### Albumi #####");
            ResultSet odgovorBaze = BazaPodataka.getInstanca().select(upit);
            int prethodni=0;
            int br=0;
            int ukupno_trajanje=0;

            while (odgovorBaze.next())
            {
                int ID_pesme = odgovorBaze.getInt("ID_pesme");
                ID_albuma = odgovorBaze.getInt("ID_albuma");
                String naziv_pesme = odgovorBaze.getString("naziv_pesme");
                int trajanje = odgovorBaze.getInt("trajanje");

                if (br==0)
                {
                    prethodni=ID_albuma;
                    br++;
                }

                if (ID_albuma!=prethodni)
                {
                    System.out.println("Ukupno trajanje albuma: "+Trajanje.vreme(ukupno_trajanje).toString()+"\n");
                    ukupno_trajanje=0;
                }
                else    ukupno_trajanje+=trajanje;

                System.out.println("ID albuma :"+ID_albuma+", ID pesme: " + ID_pesme + ", naziv pesme: " + naziv_pesme + ", trajanje: " + Trajanje.vreme(trajanje));
                prethodni=ID_albuma;
            }
            System.out.println("Ukupno trajanje albuma: "+Trajanje.vreme(Trajanje.ukupno_trajanje_albuma(prethodni))+"\n");

        } catch (SQLException e) {

            System.err.println("Izvođač nema album! ");
            // e.printStackTrace();
        }
    }

        @Override
        public String toString () {
            return
                    "ID pesme: " + ID_pesme +
                            ", naziv pesme: " + naziv_pesme +
                            ", ID izvođača: " + Izvodjac.ime_za_izvodjaca_ciji_je_ID(identifikacioni_broj_izvodjaca) +
                            ", ID albuma: " + identifikacioni_broj_albuma +
                            ", trajanje: " + trajanje.toString();
        }
    }

