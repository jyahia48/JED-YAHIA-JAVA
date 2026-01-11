create database gestion_ocp;
use gestion_ocp;

CREATE TABLE Employe (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nom VARCHAR(100),
                         age INT,
                         date_entree VARCHAR(50),
                         type_employe VARCHAR(50),
                         valeur DOUBLE,
                         salaire DOUBLE,
                         a_risque BOOLEAN
);