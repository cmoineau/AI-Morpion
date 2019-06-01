# AI-Morpion

Le but de ce projet est de créer une IA capable de jouer au Morpion. Pour réaliser cela, nous allons utiliser un algorithme de type alpha-béta https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning .

## Implémentation de l'algorithme de base :

Dans un premier temps, nous avons implémenté l'algorithme sans utiliser d'heuristique, cela fonctionnait très bien quand nous avons voulu faire jouer notre algorithme sur un plateau 3x3.
Cependant l'algorithme perdait beaucoups en efficacité sur un plateau de taille 4x4 par exemple car il n'avait pas le temps d'atteindre la profondeur des feuilles de l'arbre de jeu.

## Améliorations :

Pour pallier au problème décris dans le paragraphe précédent, nous avons mis en place une heuristique basique qui récompense l'IA si elle joue des coups proche de ces pions et va nous empêcher de mettre nos pions trop proche les uns des autres.
Pour mettre en place l'heuristique, nous avons décidé d'utiliser une recherche par **profondeur itérative**. En effet, dans notre jeu l'IA se fait couper au bout d'un certain temps il nous faut la certitude qu'elle ait pu réaliser des calculs pertinents.

## Conclusion : 

Suite à ce projet, nous avons une IA qui arrive à jouer sur des plateaux de taille raisonnable, même si elle à du mal à prévoir certains coups sur des plateaux de taille 5 comme par exemple remplir une ligne en laissant deux extrémités vide pour avoir une double condition de victoire. Cela pourrait se régler en améliorant l'heuristique qui est ici très simple.
