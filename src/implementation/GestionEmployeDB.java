package implementation;


import java.sql.*;
import connection.DBConnection;
import modele.*;
import java.util.*;

public class GestionEmployeDB {
    public static void addEmploye(Employe e,String type,double value,boolean risk){
        // 7 colonnes => 7 placeholders
        String sql="insert into Employe (nom,age,date_entree,type_employe,valeur,salaire,a_risque) values (?,?,?,?,?,?,?)";

        try(PreparedStatement stmt= DBConnection.getConnection().prepareStatement(sql)){
            stmt.setString(1, e.getNom());
            stmt.setInt(2, e.getAge());
            stmt.setString(3, e.getDateEmbauche());
            stmt.setString(4, type);
            stmt.setDouble(5, value);
            stmt.setDouble(6, e.calculerSalaire());
            stmt.setBoolean(7, risk);

            stmt.executeUpdate();
            System.out.println("Employé ajouté avec succès.");
    } catch (SQLException ex) {
        ex.printStackTrace();
        }

    }

    // Wrapper compatible avec le Controller qui attend getAllEmployees()
    public static List<Employe> getAllEmployees() {
        return getALLEmployes();
    }

  public static List<Employe> getALLEmployes() {
      List<Employe> employees = new ArrayList<>();
      String sql = "select * from Employe";
      try (Statement stmt = DBConnection.getConnection().createStatement()) {
          ResultSet rs = stmt.executeQuery(sql);

          while (rs.next()) {
              int id = rs.getInt("id");
              String nom = rs.getString("nom");
              int age = rs.getInt("age");
              String dateEntree = rs.getString("date_entree");
              String type = rs.getString("type_employe");
              double value = rs.getDouble("valeur");
              boolean risk = rs.getBoolean("a_risque");

              Employe e = createEmployeFromType(id, nom, age, dateEntree, type, value, risk);
              employees.add(e);
          }
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
      return employees;
  }

  public static void updateEmployee(int id,String name,int age,String date,double value,boolean risk){
        String sql = "update Employe set nom=?, age=?, date_entree=?, valeur=?, a_risque=? where id=?";
        try(PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)){
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, date);
            stmt.setDouble(4, value);
            stmt.setBoolean(5, risk);
            stmt.setInt(6, id);

            int rows = stmt.executeUpdate();
            if(rows > 0){
                System.out.println("Employé mis à jour avec succès.");
            }else
                System.out.println("Aucun employé trouvé avec l'ID spécifié.");


        }catch (SQLException ex) {
            ex.printStackTrace();
        }
  }
  public static void deleteEmployee(int id){
        String sql = "delete from Employe where id=?";
        try(PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)){
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if(rows > 0){
                System.out.println("Employé supprimé avec succès.");
            }else
                System.out.println("Aucun employé trouvé avec l'ID spécifié.");
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
  }

  public static double getAverageSalary() {
      String sql = "SELECT AVG(salaire) AS avg_salaire FROM Employe";
      try (Statement stmt = DBConnection.getConnection().createStatement()) {
          ResultSet rs = stmt.executeQuery(sql);
          if (rs.next()) {
              return rs.getDouble("avg_salaire");
          }
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
      return 0.0;
  }



  private static Employe createEmployeFromType(int id, String nom, int age, String dateEntree, String type, double value,boolean risk) {
         switch(type.toUpperCase()){
            case "VENDEUR" :
                return new Vendeur(id, nom, age, dateEntree, value);
            case "REPRESENTANT" :
                return new Representant(id, nom, age, dateEntree, value);
            case "PRODUCTEUR" :
                return risk ? new ProdARisque(id,nom,age,dateEntree,value): new Producteur(id,nom,age,dateEntree,value);
            case "MANUTENTIONNAIRE" :
                return risk ? new ManutARisque(id,nom,age,dateEntree,value): new Manutentionnaire(id,nom,age,dateEntree,value);
            default:
                throw new IllegalArgumentException("Invalid type "+type);
         }
  }
}
