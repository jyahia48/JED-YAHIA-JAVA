package modele;

public class ProdARisque extends Producteur implements PrimeR {
    public ProdARisque(int id, String nom, int age, String dateEmbauche, double unite) {
        super(id, nom, age, dateEmbauche, unite);
    }

    // Constructeur sans id (utilisé côté UI)
    public ProdARisque(String nom, int age, String dateEmbauche, double unite) {
        super(nom, age, dateEmbauche, unite);
    }

    @Override
    public double calculerSalaire() {
        return super.calculerSalaire() + prime;
    }
}
