package Player;

import Game.Action;
import Game.Egalite;
import Game.EnCours;
import Game.Etat;

import java.util.List;

public class JoueurIAMiniMax extends JoueurIA {

    public JoueurIAMiniMax(String nom) {
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
        System.out.println("Le joueur AI joue ...");
        List<Action> listAction= state.actionsPossibles();
        System.out.println("Récupération de  : " + listAction.size() + " actions possible");
        Etat tmpState;
        int tmpScore;
        int bestScore=-1;
        Action bestAction = listAction.get(0); // I initialize using the first action available
        System.out.println("A la recherche du meilleur coup");
        // A loop to find the best action
        for (Action tmpAction : listAction) {
            System.out.println("Test de l'action x : " + tmpAction.getX() + " y  : " + tmpAction.getY());
            tmpState = state.clone();
            tmpState.jouer(tmpAction);
            tmpState.setIdJoueurCourant(tmpState.getIdJoueurCourant()+1); // we switch player manually
            System.out.println("test");
            tmpScore = miniMax(tmpState);
            if(tmpScore>bestScore) {
                bestScore=tmpScore;
                bestAction = tmpAction;
                System.out.println("Oh ! un meilleur coup a été trouvé x : " + tmpAction.getX() + " y  : " + tmpAction.getY());
            }
        }
        return bestAction;
    }

    /**
     * Function to have the best score on a branch
     * @param n A state of the game
     * @return the score : 1 is a win for you, -1 for your opponent  and 0 for a draw
     */
    private int miniMax(Etat n){
        //System.out.println("Utilisation de miniMax !");
        int max = -1; int min = 1;
        Etat tmpState;
        List<Action> listAction= n.actionsPossibles();
        int tmpScore;

        if(isTerminal(n)) {
            return utilite(n);
        }else{
            if(n.getIdJoueurCourant() == this.getID()){ // Max is playing !
                // Trying every action possible

                for (Action tmpAction : listAction) {
                    //System.out.println("Test de l'action x : " + tmpAction.getX() + " y  : " + tmpAction.getY());
                    tmpState = n.clone();
                    tmpState.jouer(tmpAction);
                    tmpState.setIdJoueurCourant(tmpState.getIdJoueurCourant()+1); // we switch player manually
                    tmpScore = miniMax(tmpState);
                    // Update max
                    if(tmpScore > max) max =tmpScore;
                }
                return max;
            }else{  // Min is playing !
                for (Action tmpAction : listAction) {
                    //System.out.println("Test de l'action x : " + tmpAction.getX() + " y  : " + tmpAction.getY());
                    tmpState = n.clone();
                    tmpState.jouer(tmpAction);
                    tmpState.setIdJoueurCourant(tmpState.getIdJoueurCourant()+1); // we switch player manually
                    tmpScore = miniMax(tmpState);
                    // Update min
                    if(tmpScore < min) min = tmpScore;
                }
                return min;
            }
        }
    }

    /**
     * Say if the state correspond to the end of a game
     * @param n State
     * @return if it's the end of the game
     */
    private boolean isTerminal (Etat n){
        return !(n.situationCourante() instanceof EnCours);
    }

    /**
     * Return the score of a leaf
     * @param n Sate
     * @return the score : 1 is a win for you, -1 for your opponent  and 0 for a draw
     */
    private int utilite (Etat n){
        if  (n.situationCourante() instanceof Egalite){
            return 0;
        } else if (n.getIdJoueurCourant()==this.getID()) {
            return 1;
        }
        return -1;
    }

}
