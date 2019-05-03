package Game;

import Player.Joueur;

/**
 * Classe représentant une situation de victoire
 */
public class Victoire implements Situation {

	/** Joueur vainqueur */
	private Joueur vainqueur;

	/**
	 * Constructeur
	 *
	 * @param j Joueur vaiqueur
	 */
	public Victoire(Joueur j) {
		this.vainqueur = j;
	}

	/**
	 * Retourne le vainqueur
	 *
	 * @return un joueur
	 */
	public Joueur getVainqueur() {
		return vainqueur;
	}
}