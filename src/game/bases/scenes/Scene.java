package game.bases.scenes;

import game.bases.GameObject;

/**
 * Created by huynq on 8/1/17.
 */
public abstract class Scene {

    public abstract void init();

    public void deInit() {
        GameObject.clear();
    }
}
