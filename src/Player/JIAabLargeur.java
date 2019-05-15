package Player;

import Game.*;

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
        double alpha = -1, beta = 1;
        double tmpScore;
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
            tmpScore = evalEtat(tmpNode);
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
     * @param node A state of the game + the action that lead to this state
     * @return the score : 1 is a win for you, -1 for your opponent  and 0 for a draw
     */
    private double evalEtat(Node node){
        if(isTerminal(node.getEtat())) {
            return utilite(node.getEtat());
        }else{
            return heuristique(node.getEtat(), node.getAction());
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
    private double utilite (Etat n){
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
        if(voisin != 0) System.out.println("Action " + pointCosidere + " possède " + voisin + " voisin !");
        if(n.getIdJoueurCourant() == this.getID())   voisin = - voisin;// Si le joueur qui joue ce coup est un joueur min on renvoit un score négatif

        return voisin/8;
    }

    private boolean loin(Action tmp) {

        return false;
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

