package Player;

//Dans cette classe on ajoute une heuristique afin de pouvoir limiter la profondeur
//Si on ne limite pas la profondeur, le calcul est trop long à partir d'une grille de taille 4

//Heuristique choisi : nombre d'alignements disponible pour le joueurIA - nombres d'alignements disponibles pour l'adversaire

import Game.Action;
import Game.Egalite;
import Game.EnCours;
import Game.Etat;

import java.util.List;

public class JoueurIAH extends JoueurIA {

    public JoueurIAH(String nom) {
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
        int tmpScore;
        int alpha = -1; int beta = 1;
        System.out.println("A la recherche du meilleur coup");
        // A loop to find the best action
        for (Action tmpAction : listAction) {
            if(alpha > beta) return this.getActionMemorisee();
            System.out.println("Test de l'action x : " + tmpAction.getX() + " y  : " + tmpAction.getY());
            tmpState = state.clone();
            tmpState.jouer(tmpAction);
            tmpState.setIdJoueurCourant(tmpState.getIdJoueurCourant()+1); // we switch player manually
            //System.out.println("Nouveau joueur : j" +tmpState.getIdJoueurCourant() );
            tmpScore = alphaBeta(tmpState, alpha, beta);
            //System.out.println("Score associé à cette action : "+tmpScore);
            if(tmpScore>alpha) {
                alpha=tmpScore;
                this.setActionMemorisee(tmpAction);
                System.out.println("Oh ! un meilleur coup a été trouvé x : " + tmpAction.getX() + " y  : " + tmpAction.getY() + " score associé : " + tmpScore);
            }
        }
        System.out.println("L'ia à pu terminer son calcul ! " );
        return this.getActionMemorisee();
    }

    /**
     * Function to have the best score on a branch
     * @param n A state of the game
     * @return the score : 1 is a win for you, -1 for your opponent  and 0 for a draw
     */
    private int alphaBeta(Etat n, int alpha, int beta){
        //System.out.println("Utilisation de alphaBeta !");

        Etat tmpState;
        List<Action> listAction= n.actionsPossibles();
        int tmpScore;

        if(isTerminal(n)) {
            return utilite(n);
        }else{
            if(n.getIdJoueurCourant() == this.getID()){ // Max is playing !
                //System.out.println("Max is playing");
                // Trying every action possible
                for (Action tmpAction : listAction) {
                    if(alpha > beta) return alpha;

                    //System.out.println("Test de l'action x : " + tmpAction.getX() + " y  : " + tmpAction.getY());
                    tmpState = n.clone();
                    tmpState.jouer(tmpAction);
                    tmpState.setIdJoueurCourant(tmpState.getIdJoueurCourant()+1); // we switch player manually
                    //System.out.println("Nouveau joueur : j" +tmpState.getIdJoueurCourant() );
                    tmpScore = alphaBeta(tmpState, alpha, beta);
                    // Update alpha
                    if(tmpScore > alpha) alpha =tmpScore;


                }
                return alpha;
            }else{  // Min is playing !
                //System.out.println("Min is playing");
                for (Action tmpAction : listAction) {
                    if(alpha > beta) return beta;
                    //System.out.println("Test de l'action x : " + tmpAction.getX() + " y  : " + tmpAction.getY());
                    tmpState = n.clone();
                    tmpState.jouer(tmpAction);
                    tmpState.setIdJoueurCourant(tmpState.getIdJoueurCourant()+1); // we switch player manually
                    //System.out.println("Nouveau joueur : j" +tmpState.getIdJoueurCourant() );
                    tmpScore = alphaBeta(tmpState, alpha, beta);
                    // Update beta
                    if(tmpScore < beta) beta = tmpScore;


                }
                return beta;
            }
        }
    }

    private int heuristique(Etat n){
        int h = 0;
        int x=0;
        int y=0;
        int cpt;
        //horizontale
        for (int i=0; i <= n.getPlateau().getTaille();i++){
            cpt = 0;
            for (int j=0; j<= n.getPlateau().getTaille();j++){
               if (n.getPlateau().getCase(i,j)!=null){
                   cpt++;
               }
               if (cpt>=4){x++;}

            }
        }
        h=x-y;
        return h;
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
            System.out.println("Detection d'une égalité !");
            return 0;
        } else if (n.getIdJoueurCourant()!=this.getID()) {
            System.out.println("j"+this.getID() + " gagne :)");
            System.out.println ("###\n" + n.getPlateau().toString());

            return 1;
        }else{
            System.out.println("j"+n.getIdJoueurCourant()+" gagne :(");
            System.out.println ("###\n" + n.getPlateau().toString());
            return -1;
        }
    }
}
