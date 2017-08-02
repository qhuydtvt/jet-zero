package game.players;

import game.Utils;
import game.bases.GameObject;
import game.bases.renderers.ImageRenderer;

/**
 * Created by huynq on 8/3/17.
 */
public class Player extends GameObject {
    public Player() {
        super();
        this.renderer = new ImageRenderer(Utils.loadAssetImage("green_square.png"));
    }
}
