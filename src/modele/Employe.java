package modele;

import java.util.Date;

public abstract class Employe {
    protected int id;
    protected String nom;
    protected int age;
    protected String dateEmbauche;


    Employe(int id, String nom, int age, String dateEmbauche) {
        this.id = id;
        this.nom = nom;
        this.age = age;
        this.dateEmbauche = dateEmbauche;
    }

    public Employe(String nom, int age, String dateEmbauche) {

        this.nom = nom;
        this.age = age;
        this.dateEmbauche = dateEmbauche;
    }

    public abstract double calculerSalaire();

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;

    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getDateEmbauche() {
        return dateEmbauche;
    }
    public void setDateEmbauche(String dateEmbauche) {
        this.dateEmbauche = dateEmbauche;}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


}




