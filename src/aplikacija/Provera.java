package aplikacija;

/**
 * Created by Marko on 27.6.2017..
 */
public class Provera {
    public static int proveri_i_unesi_int()
    {
        int vrednost=0;
        try {
            vrednost=Program.konzola.nextInt();
            Program.konzola.nextLine();
            return vrednost;
        }
        catch (Exception e){

            Program.konzola.nextLine();
            System.err.println("Greska! Nije unet broj!");
            return -1;
        }
    }

    public static String proveri_i_unesi_string()
    {
        String vrednost="";
        try {
            vrednost=Program.konzola.nextLine();
            for (int i=0; i<vrednost.length();i++)
            {
                if (vrednost.charAt(i)>='0' && vrednost.charAt(i)<='9'  )
                {

                }
                else  throw new Exception();
            }
            return vrednost;
        }
        catch (Exception e){

            System.err.println("Greska! Nije unet broj!");
            return "0";
        }
    }

    public static String proveri_za_trajanje()
    {
        String vrednost="";
        try {
            vrednost=Program.konzola.nextLine();
            for (int i=0; i<vrednost.length();i++)
            {
                if (vrednost.charAt(i)>='0' && vrednost.charAt(i)<='9'  )
                {

                }
                else  throw new Exception();
            }
            return vrednost;
        }
        catch (Exception e){

            System.err.println("Greska! Nije unet broj!");
            return "-1";
        }
    }
}
