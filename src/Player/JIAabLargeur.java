package Player;

import Game.Action;
import Game.Egalite;
import Game.EnCours;
import Game.Etat;

import java.util.LinkedList;
import java.util.List;

public class JIAabLargeur extends JoueurIA {

    private static LinkedList<Node> noeudATraiter = new LinkedList<Node>();

    /**
     * Constructeur
     *
     * @param nom nom du joueur
     */
    public JIAabLargeur(String nom) {
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
        Etat etatCourant;
        int alpha = -1, beta = 1;
        int tmpScore;
        Etat tmpState;
        Node tmpNode;

        System.out.println("Le joueur IA joue ...");
        List<Action> listAction= state.actionsPossibles();
        for (Action tmpA: listAction ) {
            tmpState=state.clone();
            tmpState.jouer(tmpA);
            tmpState.setIdJoueurCourant(tmpState.getIdJoueurCourant()+1);
            noeudATraiter.add(new Node(tmpA,tmpState));
        }

        while (! noeudATraiter.isEmpty()){
            tmpNode = noeudATraiter.remove();
            tmpScore = alphaBeta(tmpNode.getEtat(), alpha, beta);
            if(tmpNode.getEtat().getIdJoueurCourant() == this.getID() && tmpScore>alpha) {
                alpha=tmpScore;
                this.setActionMemorisee(tmpNode.getFirstAction());
            }if(tmpNode.getEtat().getIdJoueurCourant() != this.getID() && tmpScore<beta) {
                beta=tmpScore;
                this.setActionMemorisee(tmpNode.getFirstAction());
            }
            listAction= tmpNode.getEtat().actionsPossibles();
            for (Action tmpA: listAction ) {
                tmpState=state.clone();
                tmpState.jouer(tmpA);
                tmpState.setIdJoueurCourant(tmpState.getIdJoueurCourant()+1);
                noeudATraiter.add(new Node(tmpA,tmpState));
            }
        }
        System.out.println("L'algo a eu le temps de terminer");
        return this.getActionMemorisee();
    }

    /**
     * Function to have the best score on a branch
     * @param n A state of the game
     * @return the score : 1 is a win for you, -1 for your opponent  and 0 for a draw
     */
    private int alphaBeta(Etat n, int alpha, int beta){
        if(isTerminal(n)) {
            return utilite(n);
        }else{
            return heuristique(n);
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
    /**
     * Return an approximation of the score
     * @param n Sate
     * @return the score : 1 is a win for you, -1 for your opponent  and 0 for a draw
     */
    private int heuristique (Etat n){
        if(isTerminal(n)){
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
        else {
            System.out.println("Apparemment pas de vainqueur");
            return 0;
        }
    }
}

