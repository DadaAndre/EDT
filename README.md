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

Le projet comporte les dossiers suivants :

- libs → Contient les dépendances en .jar du projet
- src → Contient le code source de l'application
- tests\_file → Contient les fichiers pour tester la Main

# Mode d'emploi

## Compilation

Pour compiler le projet, les actions suivantes sont nécessaires :

1. Créer un dossier "libs" et y placer "scheduleio.jar"
2. Créer un dossier "build" qui contiendra les .class
3. Ouvrir un terminal à la racine du projet
4. Compilez avec la commande ```javac -d build/ -cp "src:libs/scheduleio.jar" src/edt/*.java```

## Lancement

Notre projet contient 3 classes éxécutables :

- Main
- InteractiveScheduling
- Test

### Main

Il s'agit du programme principal. Il prend en paramètre 2 fichiers sans espace dans le chemin (l'un pour les activités, l'autre pour les contraintes) et génère le meilleur emploi du temps possible.

La commande est alors la suivante :
```
java -cp "build:libs/scheduleio.jar" edt.Main tests_file/activities tests_file/constraints
```

### InteractiveScheduling

Permet d'entrer des activités, des contraintes, ainsi qu'un emploi du temps en mode console. On peut ensuite vérifier la cohérence de l'emploi du temps réalisé.

La commande est alors la suivante :
```
java -cp "build:libs/scheduleio.jar" edt.InteractiveScheduling
```

### Test

Permet de tester le bon fonctionnement des différentes classes (tests unitaires)

La commande est alors la suivante :
```
java -cp "build:libs/scheduleio.jar" edt.Test
```

## Javadoc

Il est possible de générer la Javadoc du code source.

1. Créer un dossier "javadoc" à la racine du projet
2. Ouvrir un terminal à la racine du projet
3. Lancer la commande ```javadoc -subpackages edt -private -d javadoc -cp "src:libs/scheduleio.jar"```
4. Ouvrir index.html dans le dossier "javadoc"

# Collaborateurs

Ce projet étant réalisé en groupe, voici la liste des différentes personnes qui ont travaillé dessus :

- 21809742 Lorada ANDRÉ - L2 Info 1A - Groupe CC 40
- 21707781 Théo AUVRAY - L2 Info 1A - Groupe CC 40
- 21806986 Auréline DEROUIN - L2 Info 1A - Groupe CC 40
- 21909920 Justine MARTIN - L2 Info 1A - Groupe CC 40
- 21810751 Maxime THOMAS - L2 Info 1A - Groupe CC 40
