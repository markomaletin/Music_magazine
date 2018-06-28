package aplikacija;

/**
 * Created by Marko on 22.5.2017..
 */
public class Podaci {
    String poljeUBazi;
    String vrednost;

    public Podaci(String poljeUBazi, String vrednost) {
        this.poljeUBazi = poljeUBazi;
        this.vrednost = vrednost;
    }

    public String getPoljeUBazi() {
        return poljeUBazi;
    }

    public String getVrednost() {
        return vrednost;
    }
}
