Projet Java — Gestion des Salaires (OCP)

Ce dépôt contient une application Java de gestion des salaires pour différents types d’employés au sein de l’entreprise OCP.
L’application repose sur :

JavaFX pour l’interface graphique

MySQL pour la base de données

JDBC pour la communication entre Java et la base de données

Ce document décrit la structure du projet, le rôle de chaque fichier/dossier et le fonctionnement général afin de faciliter la compréhension, l’utilisation et la contribution au projet.


src/
 ├─ basedonne.sql
 ├─ Main.java
 ├─ connection/
 │   └─ DBConnection.java
 ├─ Controller/
 │   └─ Controller.java
 ├─ implementation/
 │   └─ GestionEmployeDB.java
 ├─ modele/
 │   ├─ Employe.java
 │   ├─ Commercial.java
 │   ├─ Vendeur.java
 │   ├─ Representant.java
 │   ├─ Producteur.java
 │   ├─ Manutentionnaire.java
 │   ├─ PrimeR.java
 │   ├─ ProdARisque.java
 │   └─ ManutARisque.java
 └─ view/
     └─ Interface.fxml

projetjava.iml

 Description des éléments
🔹 projetjava.iml

Fichier de configuration du projet pour IntelliJ IDEA.
Il contient les informations relatives aux modules, dépendances et paramètres de l’IDE.
➡️ Ne contient pas de code source.

🔹 src/

Répertoire racine contenant l’ensemble du code source Java et les ressources du projet.

🔹 src/basedonne.sql

Script SQL permettant :

la création de la base de données MySQL

la définition des tables (notamment la table Employe)

l’initialisation éventuelle des données

➡️ À exécuter dans votre SGBD avant de lancer l’application.

🔹 src/Main.java

Point d’entrée de l’application JavaFX.

Lance l’interface graphique

Charge le fichier Interface.fxml

Configure la fenêtre principale (titre, scène, affichage)

🔹 src/connection/DBConnection.java

Classe responsable de la connexion à la base de données MySQL.

Définit l’URL, le nom d’utilisateur et le mot de passe

Charge le driver JDBC

Fournit une connexion réutilisable pour les classes DAO

🔹 src/Controller/Controller.java

Contrôleur JavaFX associé à Interface.fxml.

Gère les interactions utilisateur (boutons, champs, sélections)

Valide les données saisies

Communique avec la couche métier et la base de données

Appelle les méthodes CRUD de GestionEmployeDB

🔹 src/implementation/GestionEmployeDB.java

Couche d’accès aux données (DAO).

Exécute les requêtes SQL via JDBC

Implémente les opérations CRUD :
INSERT, SELECT, UPDATE, DELETE

Assure la persistance des employés dans la base de données

⚠️ Important :
Chaque requête préparée doit définir tous les paramètres (?) conformément aux colonnes de la table.

🔹 src/modele/

Contient les classes métier représentant les différents types d’employés et les règles de calcul des salaires.

▪ Employe.java

Classe de base des employés.

Propriétés communes (id, nom, âge, date d’embauche, etc.)

Méthode calculerSalaire() redéfinie selon le type d’employé

▪ Commercial.java

Employé commercial dont le salaire dépend du chiffre d’affaires et des commissions.

▪ Vendeur.java

Spécialisation de Commercial.
Salaire lié au volume des ventes.

▪ Representant.java

Autre spécialisation de Commercial.
Salaire basé sur les performances de représentation.

▪ Producteur.java

Employé producteur.
Salaire calculé selon le nombre d’unités produites.

▪ Manutentionnaire.java

Employé chargé de manutention.
Salaire généralement basé sur les heures ou la charge de travail.

▪ PrimeR.java

Classe liée à la gestion des primes de risque.
Permet d’ajouter une prime supplémentaire au salaire.

▪ ProdARisque.java

Variante « à risque » du Producteur.
Inclut une prime de risque dans le calcul du salaire.

▪ ManutARisque.java

Variante « à risque » du Manutentionnaire.

📌 Note métier :
Seuls Producteur et Manutentionnaire peuvent être marqués « à risque ».

🔹 src/view/Interface.fxml

Fichier FXML décrivant l’interface graphique JavaFX.

Champs de saisie (TextField)

Sélection du type d’employé (ComboBox)

Option « à risque » (CheckBox)

Boutons d’actions (ajouter, modifier, supprimer)

Lié à Controller.java via fx:controller

🔄 Flux principal de l’application

Main.java démarre l’application JavaFX

Chargement de Interface.fxml

Controller.java gère les actions utilisateur

Création des objets métier (Employe, Producteur, etc.)

GestionEmployeDB enregistre les données via JDBC

Les données sont stockées dans MySQL selon basedonne.sql

⚠️ Points d’attention et bonnes pratiques
✔ Cohérence schéma / DAO

La requête SQL doit correspondre exactement aux colonnes de la table.

Exemple :

INSERT INTO Employe (nom, age, date_entree, type_employe, valeur, salaire, a_risque)
VALUES (?, ?, ?, ?, ?, ?, ?)


➡️ Si id est AUTO_INCREMENT, ne pas l’inclure dans la requête.

L’erreur
No value specified for parameter X
signifie qu’un paramètre ? n’a pas reçu de valeur.

✔ Gestion du risque

La case « à risque » doit être activée uniquement pour :

Producteur

Manutentionnaire

La DAO doit gérer ce champ correctement

✔ JavaFX

Vérifier la correspondance entre fx:id et @FXML

Vérifier le chemin du FXML dans Main.java

⚙️ Configuration et exécution
🔧 Prérequis

Java 8+

JavaFX (selon le JDK)

MySQL

MySQL Connector/J (JDBC)

🗄 Base de données

Exécuter basedonne.sql

Configurer DBConnection.java

▶ Lancement

Exécuter Main.java

L’interface s’ouvre et permet la gestion des employés
