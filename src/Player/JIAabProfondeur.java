package Player;

import Game.*;

import java.util.List;
import java.util.Random;

public class JIAabProfondeur extends JoueurIA {

    private static final int MAX_DEPTH = 2;

    public JIAabProfondeur(String nom) {
        super(nom);
    }

    /**
     * Fonction To choose the action to choose
     * @param state of the board
     * @return The action which will lead to the best score
     * @throws Exception
     */
    @Override
    public Action choisirAction(Etat state) throws Exception {
        System.out.println("Le joueur IA joue ...");
        List<Action> listAction= state.actionsPossibles();
        System.out.println("Récupération de  : " + listAction.size() + " actions possible");
        Etat tmpState;
        double tmpScore;
        double alpha = -1, beta = 1;

        System.out.println("A la recherche du meilleur coup");
        // A loop to find the best action
        for (Action tmpAction : listAction) {
            if(alpha > beta) return this.getActionMemorisee();
            //System.out.println("Test de l'action x : " + tmpAction.getX() + " y  : " + tmpAction.getY());
            tmpState = state.clone();
            tmpState.jouer(tmpAction);
            tmpState.setIdJoueurCourant(tmpState.getIdJoueurCourant()+1); // we switch player manually
            //System.out.println("Nouveau joueur : j" +tmpState.getIdJoueurCourant() );
            tmpScore = alphaBeta(tmpState, alpha, beta, 1, tmpAction);
            //System.out.println("Score associé à cette action : "+tmpScore);
            if(tmpScore>alpha) {
                alpha=tmpScore;
                this.setActionMemorisee(tmpAction);
                System.out.println("Oh ! un meilleur coup a été trouvé " + tmpAction + " score associé : " + tmpScore);
            }
        }
        System.out.println("L'ia a pu terminer son calcul ! " );
        return this.getActionMemorisee();
    }

    private double heuristique(Etat n, Action pointCosidere) {
        double voisin = 0;

        if(!loin(pointCosidere)) {
            int i = pointCosidere.getX();
            int j = pointCosidere.getY();
            Symbole s = n.getPlateau().getCase(i, j);
            if (i != 0 && n.getPlateau().getCase(i - 1, j) == s) {
                voisin++;
            }
            if (i != 0 && j != 0 && n.getPlateau().getCase(i - 1, j - 1) == s) {
                voisin++;
            }
            if (j != 0 && n.getPlateau().getCase(i, j - 1) == s) {
                voisin++;
            }
            if (j != 0 && i != n.getPlateau().getTaille() - 1 && n.getPlateau().getCase(i + 1, j - 1) == s) {
                voisin++;
            }
            if (i != n.getPlateau().getTaille() - 1 && n.getPlateau().getCase(i + 1, j) == s) {
                voisin++;
            }
            if (i != n.getPlateau().getTaille() - 1 && j != n.getPlateau().getTaille() - 1 && n.getPlateau().getCase(i + 1, j + 1) == s) {
                voisin++;
            }
            if (j != n.getPlateau().getTaille() - 1 && n.getPlateau().getCase(i, j + 1) == s) {
                voisin++;
            }
            if (j != n.getPlateau().getTaille() - 1 && i != 0 && n.getPlateau().getCase(i - 1, j + 1) == s) {
                voisin++;
            }
        }
        //if(voisin != 0) System.out.println("Action " + pointCosidere + " possède " + voisin + " voisin !");
        if(n.getIdJoueurCourant() == this.getID())   voisin = - voisin;// Si le joueur qui joue ce coup est un joueur min on renvoit un score négatif

        return voisin/8;
    }

    private boolean loin(Action tmp) {

        return false;
    }

    /**
     * Function to have the best score on a branch
     * @param n A state of the game
     * @return the score : 1 is a win for you, -1 for your opponent  and 0 for a draw
     */
    private double alphaBeta(Etat n, double alpha, double beta, int profondeur, Action tmp){
        //System.out.println("Utilisation de alphaBeta !");

        Etat tmpState;
        List<Action> listAction= n.actionsPossibles();
        double tmpScore;

        if(isTerminal(n)) {
            return utilite(n);
        }else {
            if (n.getIdJoueurCourant() == this.getID()) { // Max is playing !
                //System.out.println("Max is playing");
                // Trying every action possible
                for (Action tmpAction : listAction) {
                    if (alpha > beta) return alpha;
                    if(profondeur>MAX_DEPTH) {
                        tmpScore = heuristique(n, tmp);
                    }else{
                    //System.out.println("Test de l'action x : " + tmpAction.getX() + " y  : " + tmpAction.getY());
                    tmpState = n.clone();
                    tmpState.jouer(tmpAction);
                    tmpState.setIdJoueurCourant(tmpState.getIdJoueurCourant() + 1); // we switch player manually
                    //System.out.println("Nouveau joueur : j" +tmpState.getIdJoueurCourant() );

                        tmpScore = alphaBeta(tmpState, alpha, beta, profondeur+1, tmpAction);
                    }
                    // Update alpha
                    if (tmpScore > alpha) alpha = tmpScore;


                }
                return alpha;
            } else {  // Min is playing !
                //System.out.println("Min is playing");
                for (Action tmpAction : listAction) {
                    if (alpha > beta) return beta;
                    //System.out.println("Test de l'action x : " + tmpAction.getX() + " y  : " + tmpAction.getY());
                    if(profondeur>MAX_DEPTH) {
                        tmpScore = heuristique(n, tmp);
                    }else{
                    tmpState = n.clone();
                    tmpState.jouer(tmpAction);
                    tmpState.setIdJoueurCourant(tmpState.getIdJoueurCourant() + 1); // we switch player manually
                    //System.out.println("Nouveau joueur : j" +tmpState.getIdJoueurCourant() );

                        tmpScore = alphaBeta(tmpState, alpha, beta, profondeur+1, tmpAction);
                    }
                    // Update beta
                    if (tmpScore < beta) beta = tmpScore;
                }
                return beta;
            }
        }

    }

    /**
     * Say if the state correspond to the end of a game
     * @param n State
     * @return if it's the end of the game
     */
    private boolean isTerminal (Etat n){
        //System.out.println("On a trouvé une feuille");
        return !(n.situationCourante() instanceof EnCours);
    }

    /**
     * Return the score of a leaf
     * @param n Sate
     * @return the score : 1 is a win for you, -1 for your opponent  and 0 for a draw
     */
    private int utilite (Etat n){
        if  (n.situationCourante() instanceof Egalite){
            //System.out.println("Detection d'une égalité !");
            return 0;
        } else if (n.getIdJoueurCourant()!=this.getID()) {
            //System.out.println("j"+this.getID() + " gagne :)");
            //System.out.println ("###\n" + n.getPlateau().toString());

            return 1;
        }else{
            //System.out.println("j"+n.getIdJoueurCourant()+" gagne :(");
            //System.out.println ("###\n" + n.getPlateau().toString());
            return -1;
        }
    }

}
