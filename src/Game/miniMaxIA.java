package Game;

import Game.Action;
import Game.Etat;
import Player.JoueurIA;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class miniMaxIA extends JoueurIA {

    public miniMaxIA(int id, String nom) {
        super(id, nom);
    }

    /**
     * Fonction To choose the action to choose
     * @param state of the board
     * @return The action which will lead to the best score
     * @throws Exception
     */
    @Override
    public Action choisirAction(Etat state) throws Exception {
        List<Action> listAction= state.actionsPossibles();
        Etat tmpState;
        int tmpScore;
        int bestScore=-1;
        Action bestAction = listAction.get(0); // I initialize using the first action available

        // A loop to find the best action
        for (Action tmpAction : listAction) {
            tmpState = state.clone();
            tmpState.jouer(tmpAction);
            tmpScore = miniMax(tmpState);
            if(tmpScore>bestScore) {
                bestScore=tmpScore;
                bestAction = tmpAction;
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
        int alpha = -1; int beta = 1;
        Etat tmpState;
        List<Action> listAction= n.actionsPossibles();
        int tmpScore;

        if(isTerminal(n)) {
            return utilite(n);
        }else{
            if(n.getJoueurCourant() == this.getID()){ // Max is playing !
                // Trying every action possible

                for (Action tmpAction : listAction) {
                    tmpState = n.clone();
                    tmpState.jouer(tmpAction);
                    tmpScore = miniMax(tmpState);
                    // Update alpha
                    if(tmpScore > alpha) alpha =tmpScore;
                }
                return alpha;
            }else{  // Min is playing !
                for (Action tmpAction : listAction) {
                    tmpState = n.clone();
                    tmpState.jouer(tmpAction);
                    tmpScore = miniMax(tmpState);
                    // Update beta
                    if(tmpScore < beta) beta = tmpScore;
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
    private boolean isTerminal (Etat n){ //TODO : A coder !
        if (n.situationCourante() instanceof EnCours){
            System.out.println("Non Terminal");
            return false;
        }
        System.out.println("Terminal");
        return true;
    }

    /**
     * Return the score of a leaf
     * @param n Sate
     * @return the score : 1 is a win for you, -1 for your opponent  and 0 for a draw
     */
    private int utilite (Etat n){ // TODO : A compléter, il faut s'assurer qu'un des joueurs n'a pas gagné si plateau complet
        /*
        if(n.getPlateau().estRempli()) return 0;
        if(n.getJoueurCourant() == this.getID()) return 1;
        else return -1;*/
        /*
            La procédure situationCourante vérifie bien les conditions de victoire avant de renvoyer l'égalite ou encours
         */
        if  (n.situationCourante() instanceof Egalite){
            return 0;
        } else if (n.getJoueurCourant()==this.getID()) {
            return 1;
        }
        return -1;
    }


    @Override
    public void proposerAction(Action action) {
        // Void used by the UI
    }
}
