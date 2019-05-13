package Player;

import Game.Action;
import Game.Etat;

public class Node {
    private Action action;
    private Etat etat;

    public Node(Action action, Etat etat) {
        this.action = action;
        this.etat = etat;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Action getAction() {
        return this.action;
    }

    public Etat getEtat() {
        return this.etat;
    }
}
