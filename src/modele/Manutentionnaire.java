package modele;

public class Manutentionnaire extends Employe{
    protected double heures;

    public Manutentionnaire(int id, String nom, int age, String dateEmbauche, double heures) {
        super(id, nom, age, dateEmbauche);
        this.heures = heures;
    }

    // Constructeur sans id
    public Manutentionnaire(String nom, int age, String dateEmbauche, double heures) {
        super(nom, age, dateEmbauche);
        this.heures = heures;
    }
    @Override
    public double calculerSalaire() {
        return 650 * heures;
    }
}
