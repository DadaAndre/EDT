# Présentation

Il s'agit de réaliser une petite application Java, à utiliser en ligne de commande, capable de prendre en entrée des fichiers décrivant, d'une part, des activités et événements à planifier, et d'autre part des contraintes entre ces activités, et de calculer, sur une période de temps donnée, un emploi du temps comprenant toutes les activités à planifier et respectant toutes les contraintes.

# Objectifs de l'application

L'application devra :

- Être exécutable
- Prendre en argument au minimum un fichier décrivant les activités et un fichier décrivant des contraintes de précédence
- Prendre en argument (optionnellement) un fichier décrivant des contraintes de type « meet » et des contraintes de type « max span »
- Calculer un emploi du temps respectant les contraintes de précédence
- Afficher l'emploi du temps calculé (ou annoncer qu'aucun ne peut satisfaire les contraintes de précédence)
- Annoncer s'il satisfait toutes les contraintes

# Structuration du projet

Le projet comporte 2 dossiers :
- doc → Regroupe les différents documents importants du projet
- src → Contient le code source de l'application

