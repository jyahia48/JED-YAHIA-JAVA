Projet Java — Gestion des Salaires (OCP)

Ce dépôt contient une application Java de gestion des salaires pour différents types d’employés au sein de l’entreprise OCP.
L’application utilise JavaFX pour l’interface graphique et MySQL pour la base de données, avec JDBC pour assurer la persistance des données.

Ce document présente la structure du projet et explique le rôle de chaque fichier et dossier afin de faciliter la compréhension et la contribution sur GitHub.

Arborescence
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
    Employe.java
    Commercial.java
    Vendeur.java
    Representant.java
    Producteur.java
    Manutentionnaire.java
    PrimeR.java
    ProdARisque.java
    ManutARisque.java
  view/
    Interface.fxml

projetjava.iml

Description des éléments
projetjava.iml

Fichier de configuration du projet pour IntelliJ IDEA.
Il décrit les modules, dépendances et paramètres spécifiques à l’IDE.
Ne contient pas de code source.

src/

Répertoire racine contenant l’ensemble des sources Java et des ressources du projet.

src/basedonne.sql

Script SQL permettant la création et l’initialisation de la base de données MySQL.
Il définit notamment la table Employe utilisée par la couche d’accès aux données.
Ce script doit être exécuté avant le lancement de l’application.

src/Main.java

Point d’entrée de l’application JavaFX.
Il lance l’interface graphique définie dans Interface.fxml et configure la fenêtre principale.

src/connection/DBConnection.java

Classe responsable de l’établissement de la connexion JDBC avec la base de données MySQL.
Elle gère l’URL, les identifiants et le chargement du driver JDBC.
Elle est utilisée par les classes DAO.

src/Controller/Controller.java

Contrôleur JavaFX associé à Interface.fxml.
Il gère la logique de l’interface utilisateur, la validation des données et les actions sur les boutons.
Il communique avec la couche métier et la base de données via GestionEmployeDB.

src/implementation/GestionEmployeDB.java

Classe d’accès aux données (DAO) pour la table Employe.
Elle exécute les requêtes SQL à l’aide de JDBC et implémente les opérations CRUD
(ajout, lecture, modification et suppression des employés).

src/modele/

Contient les classes métier représentant les différents types d’employés et les règles de calcul des salaires.

Employe.java
Classe de base des employés contenant les attributs communs et la méthode de calcul du salaire.

Commercial.java
Employé de type commercial dont le salaire dépend des ventes ou des commissions.

Vendeur.java
Spécialisation de Commercial, orientée ventes.

Representant.java
Spécialisation de Commercial, liée à l’activité de représentation.

Producteur.java
Employé dont le salaire est calculé en fonction de la production.

Manutentionnaire.java
Employé dont le salaire dépend généralement des heures de travail.

PrimeR.java
Classe liée à la gestion des primes de risque.

ProdARisque.java
Variante à risque du Producteur intégrant une prime supplémentaire.

ManutARisque.java
Variante à risque du Manutentionnaire intégrant une prime supplémentaire.

src/view/Interface.fxml

Fichier FXML décrivant l’interface graphique JavaFX.
Il contient les composants de l’interface utilisateur et est lié au contrôleur Controller.java.

Flux principal de l’application

Main.java démarre l’application et charge l’interface graphique.
Controller.java gère les interactions utilisateur et crée les objets métier.
GestionEmployeDB assure la persistance des données dans la base MySQL via DBConnection.

Configuration et exécution

Prérequis :

Java 8 ou supérieur

JavaFX (selon le JDK utilisé)

MySQL

MySQL Connector/J (driver JDBC)

Base de données :

Exécuter le script basedonne.sql

Configurer les paramètres de connexion dans DBConnection.java

Lancement :

Exécuter Main.java

L’interface graphique s’ouvre et permet la gestion des employés
