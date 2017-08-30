package game.bases.actions;

import game.bases.GameObject;

/**
 * Created by huynq on 8/30/17.
 */
public class DeactivateAction extends Action {
    @Override
    public boolean run(GameObject gameObject) {
        gameObject.isActive = false;
        return true;
    }
}
