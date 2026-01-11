package modele;

public class Commercial extends Employe{
    protected double chiffreAffaires;
    protected double prixfixe;

    public Commercial(int id, String nom, int age, String dateEmbauche, double chiffreAffaires, double prixfixe) {
        super(id, nom, age, dateEmbauche);
        this.chiffreAffaires = chiffreAffaires;
        this.prixfixe = prixfixe;
    }
    @Override
    public double calculerSalaire() {
        return 0.2 * chiffreAffaires + prixfixe;
    }
}
