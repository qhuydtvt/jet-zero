package game.bases.actions;

import game.bases.GameObject;

/**
 * Created by huynq on 8/1/17.
 */
public abstract class Action {
    public abstract boolean run(GameObject gameObject);
    public void reset() {

    }
}
