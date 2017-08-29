package game.players;

import game.bases.GameObject;
import game.bases.Vector2D;
import game.bases.physics.BoxCollider;
import game.bases.physics.Physics;
import game.bases.renderers.ImageRenderer;
import game.platforms.Platform;
import tklibs.SpriteUtils;

/**
 * Created by huynq on 8/30/17.
 */
public class PlayerFireBall extends GameObject {
    private Vector2D velocity;
    private BoxCollider boxCollider;

    public PlayerFireBall() {
        super();
        this.velocity = new Vector2D();
        this.renderer = new ImageRenderer(SpriteUtils.loadImage("assets/images/green_square_small.png"));
        this.children.add(boxCollider = new BoxCollider(15,15));
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        this.position.addUp(velocity);
        hitPlatformer();
    }

    private void hitPlatformer() {
        if (Physics.bodyInRect(this.boxCollider, Platform.class) != null) {
            this.isActive = false;
        }
    }
}
