package game.bases.actions;

import game.bases.GameObject;

/**
 * Created by huynq on 8/1/17.
 */
public interface Action {
    boolean run(GameObject gameObject);
    void reset();
}
