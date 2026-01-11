# Projet Java — Gestion des Salaires (OCP)

Ce dépôt contient une application Java (JavaFX + MySQL) de gestion des salaires pour différents types d’employés. Ce document présente la structure du projet et explique le rôle de chaque fichier/dossier pour faciliter la compréhension et la contribution sur GitHub.

## Arborescence

```
src/
  basedonne.sql
  Main.java
  connection/
    DBConnection.java
  Controller/
    Controller.java
  implementation/
    GestionEmployeDB.java
  modele/
    Commercial.java
    Employe.java
    ManutARisque.java
    Manutentionnaire.java
    PrimeR.java
    ProdARisque.java
    Producteur.java
    Representant.java
    Vendeur.java
  view/
    Interface.fxml
projetjava.iml
```

## Description des éléments

- projetjava.iml
  - Fichier de configuration du projet pour IntelliJ IDEA. Il décrit les modules, dépendances et paramètres spécifiques à l’IDE. Ne contient pas de code.

### src/
Racine des sources Java et des ressources.

- src/basedonne.sql
  - Script SQL de création et initialisation de la base de données MySQL (tables, colonnes, types). À exécuter dans votre SGBD pour préparer l’environnement.
  - Contient notamment la définition de la table `Employe` utilisée par `GestionEmployeDB`.

- src/Main.java
  - Point d’entrée de l’application JavaFX.
  - Lance l’interface graphique définie dans `view/Interface.fxml`.
  - Configure la fenêtre principale (titre, scène, affichage).

#### src/connection/
- DBConnection.java
  - Fournit une méthode pour obtenir une connexion JDBC à MySQL.
  - Gère l’URL, l’utilisateur, le mot de passe et le chargement du driver.
  - Utilisé par les classes d’accès aux données (DAO) comme `GestionEmployeDB`.

#### src/Controller/
- Controller.java
  - Contrôleur JavaFX associé à `Interface.fxml`.
  - Contient la logique de l’interface: récupération des champs, validation, actions sur boutons, interaction avec le modèle et la base.
  - Appelle `GestionEmployeDB` pour les opérations CRUD (ajouter un employé, etc.).

#### src/implementation/
- GestionEmployeDB.java
  - Couche d’accès aux données (DAO) pour la table `Employe`.
  - Prépare et exécute des requêtes SQL via JDBC (INSERT, SELECT, UPDATE, DELETE).
  - Exemple: `addEmploye(Employe e, String type, double value, boolean risk)` insère un employé dans la base.
  - Attention: la requête préparée doit définir toutes les valeurs des paramètres (indexes 1..N) en cohérence avec les colonnes.

#### src/modele/
Modèles métiers (objets représentant les types d’employés et les règles de calcul de salaire).

- Employe.java
  - Classe de base (abstraite ou concrète selon votre implémentation) pour un employé.
  - Propriétés communes: id, nom, âge, date d’embauche, etc.
  - Méthode `calculerSalaire()` à surcharger/implémenter selon le type d’employé.

- Commercial.java
  - Employé de type commercial. Salaire basé sur chiffre d’affaires et/ou commissions.

- Vendeur.java
  - Spécialisation possible de Commercial (selon votre conception). Salaire lié aux ventes.

- Representant.java
  - Spécialisation possible de Commercial pour les représentants. Salaire lié au volume de représentation.

- Producteur.java
  - Employé produisant des pièces/unité. Salaire généralement basé sur le nombre d’unités produites.

- Manutentionnaire.java
  - Employé dont le salaire dépend souvent des heures/manutentions.

- PrimeR.java
  - Composant/stratégie de calcul de primes de risque (si applicable). Peut ajouter une prime au salaire pour certains types d’employés exposés.

- ProdARisque.java
  - Variante « à risque » du Producteur (salaire différent avec prime de risque). Si votre logique métier précise que seuls certains types sont à risque, ajustez l’utilisation de cette classe.

- ManutARisque.java
  - Variante « à risque » du Manutentionnaire (salaire différent avec prime de risque).

Note métier: Vous avez indiqué que seuls 2 types d’employés peuvent être à risque (Producteur et Manutentionnaire). Assurez-vous que l’interface et la base reflètent cela (champ `a_risque` pertinent uniquement pour ces types).

#### src/view/
- Interface.fxml
  - Vue JavaFX (FXML) décrivant l’interface utilisateur.
  - Liée à `Controller.java` via `fx:controller`.
  - Contient les nœuds UI (TextField, ComboBox pour le type d’employé, CheckBox pour « à risque » s’il s’applique, boutons d’actions, tableaux/lists).

## Flux principal de l’application
1. `Main.java` démarre JavaFX et charge `Interface.fxml`.
2. `Controller.java` initialise les composants UI, écoute les actions utilisateur et valide les données.
3. À l’ajout d’un employé, `Controller` construit un objet du modèle (`Producteur`, `Manutentionnaire`, etc.) et appelle `GestionEmployeDB`.
4. `GestionEmployeDB` utilise `DBConnection` pour insérer les données dans MySQL conformément au schéma défini dans `basedonne.sql`.

## Points d’attention et bonnes pratiques
- Cohérence schéma/DAO:
  - La requête `INSERT` doit correspondre exactement aux colonnes. Par exemple:
    - `INSERT INTO Employe (id, nom, age, date_entree, type_employe, valeur, salaire, a_risque) VALUES (?, ?, ?, ?, ?, ?, ?, ?)`
    - Il faut définir 8 paramètres: `setInt`/`setLong` pour `id` si géré manuellement, `setString` pour `nom`, etc. Si `id` est AUTO_INCREMENT, ne l’incluez pas dans la liste des colonnes et réduisez le nombre de `?`.
  - L’erreur « No value specified for parameter 8 » signifie qu’un des placeholders `?` n’a pas reçu de valeur (par exemple, `id` ou `a_risque`).
- Types « à risque »:
  - Si seuls Producteur et Manutentionnaire sont à risque, la UI doit activer la case « à risque » uniquement pour ces types et la DAO doit gérer ce champ en conséquence.
- JavaFX Controller:
  - Assurez-vous que `fx:id` dans le FXML correspond aux champs annotés `@FXML` dans `Controller.java`.
  - Vérifiez que le chemin du FXML dans `Main.java` est correct: `FXMLLoader.load(getClass().getResource("/Interface.fxml"))` implique que `Interface.fxml` est dans le classpath racine (resources). Si nécessaire, déplacez-le ou ajustez le chemin.

## Configuration et exécution
- Dépendances requises:
  - Java 8+ (JavaFX si non inclus selon votre JDK).
  - MySQL Connector/J (driver JDBC) dans le classpath.
- Base de données:
  - Exécutez `src/basedonne.sql` pour créer la base et les tables.
  - Mettez à jour `DBConnection.java` avec vos identifiants et URL.
- Lancement:
  - Exécutez `Main.java`. L’UI se lance et vous pouvez ajouter des employés.


