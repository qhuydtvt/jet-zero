package game.bases.actions;

import game.bases.GameObject;

/**
 * Created by huynq on 8/1/17.
 */
public class RepeatAction extends Action {
    private Action action;
    private int maxCount;
    private int count;

    public RepeatAction(Action action, int maxCount) {
        this.action = action;
        this.maxCount = maxCount;
        this.count = 0;
    }


    @Override
    public boolean run(GameObject gameObject) {
        if (this.count >= this.maxCount) {
            return true;
        } else {
            if(this.action.run(gameObject)) {
                this.count++;
            }
            return false;
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.count = 0;
        this.action.reset();
    }
}
