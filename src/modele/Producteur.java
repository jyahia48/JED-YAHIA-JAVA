package modele;

public class Producteur extends Employe{
    protected double unite;

    public Producteur(int id, String nom, int age, String dateEmbauche, double unite) {
        super(id, nom, age, dateEmbauche);
        this.unite = unite;
    }

    // Constructeur sans id
    public Producteur(String nom, int age, String dateEmbauche, double unite) {
        super(nom, age, dateEmbauche);
        this.unite = unite;
    }
    @Override
    public double calculerSalaire() {
        return 5 * unite;
    }
}
