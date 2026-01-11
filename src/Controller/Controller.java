package Controller;

import implementation.GestionEmployeDB;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modele.*;

import java.util.List;

public class Controller {

    @FXML private TextField txtName;
    @FXML private TextField txtAge;
    @FXML private TextField txtDate;
    @FXML private TextField txtValue;

    @FXML private ComboBox<String> comboProducteur;
    @FXML private CheckBox chkRisk; // nouvelle case à cocher

    @FXML private TableView<Employe> table;

    @FXML private TableColumn<Employe, Integer> colId;
    @FXML private TableColumn<Employe, String> colName;
    @FXML private TableColumn<Employe, String> colDate;
    @FXML private TableColumn<Employe, Double> colSalary;

    @FXML private Label lblSalaireMoyen; // Champ ajouté pour le salaire moyen

    private final ObservableList<Employe> employeeList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Remplir la ComboBox
        comboProducteur.getItems().addAll(
                "VENDEUR",
                "REPRESENTANT",
                "PRODUCTEUR",
                "MANUTENTIONNAIRE"
        );

        // Activer/désactiver la case À risque selon le type
        comboProducteur.valueProperty().addListener((obs, old, type) -> {
            boolean riskApplicable = "PRODUCTEUR".equals(type) || "MANUTENTIONNAIRE".equals(type);
            if (chkRisk != null) {
                chkRisk.setDisable(!riskApplicable);
                if (!riskApplicable) chkRisk.setSelected(false);
            }
            // Adapter le prompt de la valeur
            if (txtValue != null) {
                if ("VENDEUR".equals(type) || "REPRESENTANT".equals(type)) {
                    txtValue.setPromptText("Chiffre d'affaires");
                } else if ("PRODUCTEUR".equals(type)) {
                    txtValue.setPromptText("Unités produites");
                } else if ("MANUTENTIONNAIRE".equals(type)) {
                    txtValue.setPromptText("Heures");
                } else {
                    txtValue.setPromptText("Valeur");
                }
            }
        });

        // Configuration des colonnes TableView
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateEmbauche"));

        // Salaire calculé dynamiquement
        colSalary.setCellValueFactory(param ->
                new SimpleDoubleProperty(param.getValue().calculerSalaire()).asObject()
        );

        table.setItems(employeeList);

        afficherAction(); // charger au démarrage
    }

    @FXML
    private void ajouterAction() {
        try {
            String name = txtName.getText();
            int age = Integer.parseInt(txtAge.getText());
            String date = txtDate.getText();
            String type = comboProducteur.getValue();
            double value = Double.parseDouble(txtValue.getText());
            boolean risk = chkRisk != null && chkRisk.isSelected();

            if (type == null || type.isEmpty()) {
                throw new IllegalArgumentException("Veuillez sélectionner un type d'employé.");
            }

            Employe emp;
            switch (type) {
                case "VENDEUR":
                    emp = new Vendeur(name, age, date, value);
                    break;
                case "REPRESENTANT":
                    emp = new Representant(name, age, date, value);
                    break;
                case "PRODUCTEUR":
                    // si risk=true, salaire ProdARisque; insertion en DB avec type PRODUCTEUR + a_risque=true
                    emp = risk ? new ProdARisque(name, age, date, value) : new Producteur(name, age, date, value);
                    break;
                case "MANUTENTIONNAIRE":
                    // idem pour manutentionnaire
                    emp = risk ? new ManutARisque(name, age, date, value) : new Manutentionnaire(name, age, date, value);
                    break;
                default:
                    throw new IllegalArgumentException("Type invalide");
            }

            GestionEmployeDB.addEmploye(emp, type, value, risk);

            afficherAction();
            clearFields();
        } catch (Exception e) {
            showAlert("Erreur", e.getMessage());
        }
    }

    @FXML
    private void modifierAction() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Modifier un employé");
        dialog.setHeaderText("Entrez l'ID de l'employé à modifier :");
        dialog.setContentText("ID :");

        dialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                boolean risk = chkRisk != null && chkRisk.isSelected();

                GestionEmployeDB.updateEmployee(
                        id,
                        txtName.getText(),
                        Integer.parseInt(txtAge.getText()),
                        txtDate.getText(),
                        Double.parseDouble(txtValue.getText()),
                        risk
                );

                afficherAction();
                clearFields();
            } catch (Exception e) {
                showAlert("Erreur", e.getMessage());
            }
        });
    }

    @FXML
    private void supprimerAction() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Supprimer un employé");
        dialog.setHeaderText("Entrez l'ID de l'employé à supprimer :");
        dialog.setContentText("ID :");

        dialog.showAndWait().ifPresent(idStr -> {
            try {
                GestionEmployeDB.deleteEmployee(Integer.parseInt(idStr));
                afficherAction();
            } catch (Exception e) {
                showAlert("Erreur", e.getMessage());
            }
        });
    }

    @FXML
    private void afficherAction() {
        List<Employe> list = GestionEmployeDB.getAllEmployees();
        employeeList.setAll(list);
    }

    @FXML
    private void salaireMoyenAction() {
        double avg = GestionEmployeDB.getAverageSalary();
        // Mettre à jour le label dans l'interface
        if (lblSalaireMoyen != null) {
            lblSalaireMoyen.setText(String.format("Salaire moyen : %.2f Dhs", avg));
        }
        showAlert("Salaire Moyen", String.format("Salaire moyen : %.2f Dhs", avg));
    }

    private void clearFields() {
        txtName.clear();
        txtAge.clear();
        txtDate.clear();
        txtValue.clear();
        comboProducteur.setValue(null);
        if (chkRisk != null) chkRisk.setSelected(false);
        if (chkRisk != null) chkRisk.setDisable(true);
        if (txtValue != null) txtValue.setPromptText("Valeur");
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
