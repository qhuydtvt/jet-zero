package game.platforms;

import game.Utils;
import game.bases.GameObject;
import game.bases.renderers.ImageRenderer;

/**
 * Created by huynq on 8/3/17.
 */
public class Platform extends GameObject {
    public Platform() {
        super();
        this.renderer = new ImageRenderer(Utils.loadAssetImage("yellow_square.jpg"));
    }
}
