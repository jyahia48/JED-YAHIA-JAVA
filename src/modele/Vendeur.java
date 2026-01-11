package modele;

public class Vendeur extends Employe {
    protected double chiffreAffaires;
    double valeur;
    public Vendeur(int id, String nom, int age, String dateEmbauche, double chiffreAffaires) {
        super(id,nom,age, dateEmbauche);
        this.chiffreAffaires = chiffreAffaires;
    }

    // Constructeur sans id (utilisé côté UI avant insertion en DB)
    public Vendeur(String nom, int age, String dateEmbauche, double chiffreAffaires) {
        super(nom, age, dateEmbauche);
        this.chiffreAffaires = chiffreAffaires;
    }

    @Override
    public double calculerSalaire() {
        return 0.2 * chiffreAffaires + 4000;
    }
}
