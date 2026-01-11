package modele;

public class Representant extends Employe{
    protected double chiffreAffaires;


    public Representant(int id, String nom, int age, String dateEmbauche, double chiffreAffaires) {
        super(id, nom, age, dateEmbauche);
        this.chiffreAffaires = chiffreAffaires;

    }

    // Constructeur sans id
    public Representant(String nom, int age, String dateEmbauche, double chiffreAffaires) {
        super(nom, age, dateEmbauche);
        this.chiffreAffaires = chiffreAffaires;
    }
    @Override
    public double calculerSalaire() {
        return 0.2 * chiffreAffaires + 8000;
    }
}
