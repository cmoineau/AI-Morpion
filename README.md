# AI-Morpion

## MiniMaxIA :

La version MiniMax n'est pas concluante car l'algorithme n'a pas le temps de calculer toute les branches de l'arbre, il renvoit donc une réponse aléatoire.

Pour pallier à ce problème, on pourrait faire de la programmation dynamique et stocker les résultat des calculs de minimax(Etat) dans un Set.

## Alpha-Beta :

Cette version fonctionne bien pour tableau de taille 3x3.

Étudier comment lui faire trouver le meilleur résultat sur un tableau plus grand ? 
