package Player;

import Game.Action;
import Game.Egalite;
import Game.EnCours;
import Game.Etat;

import java.util.LinkedList;
import java.util.List;

public class JIAabLargeur extends JoueurIA {

    // Structure de file pour faire une exploration en profondeur
    private static LinkedList<Node> open = new LinkedList<Node>();
    private static LinkedList<Node> close = new LinkedList<Node>();
    /**
     * Constructeur
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
        int alpha = -1, beta = 1;
        int tmpScore;
        Etat tmpState;
        Node tmpNode;

        System.out.println("Le joueur IA joue ...");
        // Initialisation de la file de noeud !
        List<Action> listAction= state.actionsPossibles();
        for (Action tmpA: listAction ) {
            tmpState=state.clone();
            tmpState.jouer(tmpA);
            tmpState.setIdJoueurCourant(tmpState.getIdJoueurCourant()+1);
            open.add(new Node(tmpA,tmpState));
        }

        while (! open.isEmpty()){ // Condition peut être un peu forte ... A changer pour tant qu'on detecte pas un coup gagnant ?
            tmpNode = open.remove();
            tmpScore = evalEtat(tmpNode.getEtat());
            if(tmpNode.getEtat().getIdJoueurCourant() == this.getID() && tmpScore>alpha) {
                alpha=tmpScore;
                System.out.println("On mémorise une nouvelle meilleur action : " + tmpNode.getEtat().getPlateau() );
                this.setActionMemorisee(tmpNode.getAction());
            }if(tmpNode.getEtat().getIdJoueurCourant() != this.getID() && tmpScore<beta) {
                beta=tmpScore;
                System.out.println("On mémorise une nouvelle meilleur action : " + tmpNode.getEtat().getPlateau() );

                this.setActionMemorisee(tmpNode.getAction());
            }
            // On place le noeud dans les états
            close.add(tmpNode);
            enregistrerActionFils(tmpNode);
        }
        System.out.println("L'algo a eu le temps de terminer");
        return this.getActionMemorisee();
    }

    /**
     * @param n A state of the game
     * @return the score : 1 is a win for you, -1 for your opponent  and 0 for a draw
     */
    private int evalEtat(Etat n){
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
            return 0;
    }

    private void enregistrerActionFils (Node node){
        List<Action> listAction= node.getEtat().actionsPossibles();
        Etat tmpState;
        for (Action tmpA: listAction ) {
            tmpState=node.getEtat().clone(); // On penses bien a jouer les actions par rapport à l'état du fils !
            tmpState.jouer(tmpA);
            tmpState.setIdJoueurCourant(tmpState.getIdJoueurCourant()+1);
            open.add(new Node(tmpA,tmpState));
        }
    }
}

