package game.players;


import game.bases.Vector2D;
import game.bases.renderers.Animation;
import game.bases.renderers.Renderer;
import tklibs.SpriteUtils;

import java.awt.*;

/**
 * Created by huynq on 8/30/17.
 */
public class PlayerAnimator implements Renderer {

    private Animation standingAnimation = new Animation(
            20,
            true,
            SpriteUtils.loadImage("assets/images/sprite/jet00001.png"),
            SpriteUtils.loadImage("assets/images/sprite/jet00002.png"),
            SpriteUtils.loadImage("assets/images/sprite/jet00003.png"),
            SpriteUtils.loadImage("assets/images/sprite/jet00004.png")
    );

    @Override
    public void render(Graphics g, Vector2D position) {
        standingAnimation.render(g, position);
    }
}
