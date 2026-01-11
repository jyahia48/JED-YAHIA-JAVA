package modele;

public class ManutARisque extends Manutentionnaire implements PrimeR {
    public ManutARisque(int id, String nom, int age, String dateEmbauche, double heures) {
        super(id, nom, age, dateEmbauche, heures);
    }

    // Constructeur sans id (utilisé côté UI)
    public ManutARisque(String nom, int age, String dateEmbauche, double heures) {
        super(nom, age, dateEmbauche, heures);
    }

    @Override
    public double calculerSalaire() {
        return super.calculerSalaire() + prime;
    }
}
