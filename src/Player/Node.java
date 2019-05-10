package Player;

import Game.Action;
import Game.Etat;

public class Node {
    private Action firstAction;
    private Etat Etat;

    public Node(Action firstAction, Game.Etat etat) {
        this.firstAction = firstAction;
        Etat = etat;
    }

    public void setFirstAction(Action firstAction) {
        this.firstAction = firstAction;
    }

    public void setEtat(Etat etat) {
        Etat = etat;
    }

    public Action getFirstAction() {
        return firstAction;
    }

    public Etat getEtat() {
        return Etat;
    }
}
