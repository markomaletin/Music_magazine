package aplikacija;

/**
 * Created by Marko on 7.5.2017..
 */
public class Meni {
    public static int meniKorisnik()
    {
        System.out.println("\n###############   Meni   ##############");
        System.out.println("1. Prikaz biblioteke kupljenih albuma i pesama");
        System.out.println("2. Pretraga izvođača u muzičkoj aplikaciji");
        System.out.println("3. Dodavanje pesme u biblioteku");
        System.out.println("4. Dodavanje albuma u biblioteku");
        System.out.println("5. Odjava");

        System.out.print("\nUnesite izbor iz menija (1-5): ");
        while (true)
        {
            int izbor=Provera.proveri_i_unesi_int();
            if (izbor==-1)
            {
                System.out.print("Izbor nije u odovarajućem opsegu. Unesite opet (1-5): ");
                continue;
            }


            if (izbor>0 && izbor<=5)
            {

                return izbor;
            }
            else
            {
                System.out.print("Izbor nije u odovarajućem opsegu. Unesite opet (1-5): ");
            }
        }

    }

    public static int pomocniMeniKorisnik()
    {
        System.out.println("\n###############   Meni   ##############");
        System.out.println("1. Prikaz pesama i albuma");
        System.out.println("2. Dodavanje pesme u biblioteku");
        System.out.println("3. Dodavanje albuma u biblioteku");
        System.out.println("4. Nazad");


        System.out.print("\nUnesite izbor iz menija (1-4): ");
        while (true)
        {
            int izbor=Provera.proveri_i_unesi_int();
            if (izbor==-1)
            {
                System.out.print("Izbor nije u odovarajućem opsegu. Unesite opet (1-4): ");
                continue;
            }

            if (izbor>0 && izbor<=4)
            {
                return izbor;
            }
            else
            {
                System.out.print("Izbor nije u odovarajućem opsegu. Unesite opet (1-5): ");
            }
        }

    }

    public static int meniAdmin()
    {
        System.out.println("\n###############   Meni   ##############");
        System.out.println("1. Unos pesme");
        System.out.println("2. Unos izvođača");
        System.out.println("3. Unos albuma");
        System.out.println("4. Ažuriranje pesme");
        System.out.println("5. Ažuriranje izvođača");
        System.out.println("6. Ažuriranje albuma");
        System.out.println("7. Brisanje pesme");
        System.out.println("8. Brisanje izvođača");
        System.out.println("9. Brisanje albuma");
        System.out.println("10.Odjava");
        System.out.print("\nUnesite izbor iz menija (1-10): ");

        while (true)
        {
            int izbor=Provera.proveri_i_unesi_int();
            if (izbor==-1)
            {
                System.out.print("Izbor nije u odovarajućem opsegu. Unesite opet (1-10): ");
                continue;
            }

            if (izbor>0 && izbor<=10)
            {
                return izbor;
            }
            else
            {
                System.out.print("Izbor nije u odovarajućem opsegu. Unesite opet (1-10): ");
            }
        }

    }
}
