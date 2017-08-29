package game.players.enginedusts;

import game.bases.GameObject;
import game.bases.Vector2D;
import game.bases.actions.Action;
import game.bases.actions.SequenceAction;
import game.bases.actions.WaitAction;
import game.bases.physics.Physics;
import game.bases.renderers.ImageRenderer;
import game.platforms.Platform;
import tklibs.Mathx;
import tklibs.SpriteUtils;

/**
 * Created by huynq on 8/30/17.
 */
public class EngineDust extends GameObject {

    private Vector2D velocity;
    private ImageRenderer imageRenderer;
    private float imageAlpha;

    public EngineDust() {
        super();
        this.imageRenderer = new ImageRenderer(SpriteUtils.loadImage("assets/images/white_square_tiny.png"));
        this.renderer = this.imageRenderer;
        this.imageRenderer.getTransform().anchor.set(0.5f, 1);
        this.imageAlpha = 1;
        this.velocity = Vector2D.ZERO.clone();
    }

    @Override
    public void run(Vector2D parentPosition) {
        this.position.addUp(this.velocity);
        super.run(parentPosition);
        fadeOut();
        updatePhysics();
    }

    private void updatePhysics() {
        if (Physics.bodyAtPoint(this.screenPosition.add(velocity), Platform.class) != null) {
            this.velocity.y = 0;
        }
    }

    private void fadeOut() {
        this.imageAlpha = Mathx.clamp(this.imageAlpha - 0.02f, 0, 1);
        this.imageRenderer.getTransform().alpha = this.imageAlpha;
        if (imageAlpha == 0) {
            this.isActive = false;
        }
    }

    @Override
    public void refresh() {
        super.refresh();
        imageAlpha = 1.0f;
    }

    public void config(Vector2D velocity) {

        this.addAction(new SequenceAction(
                new WaitAction(2),
                new Action() {
                    @Override
                    public boolean run(GameObject gameObject) {
                        EngineDust dust = (EngineDust) gameObject;
                        dust.velocity.set(velocity);
                        return true;
                    }
                },
                new WaitAction(3),
                new Action() {
                    @Override
                    public boolean run(GameObject gameObject) {
                        EngineDust dust = (EngineDust) gameObject;
                        dust.velocity.y = -0.7f;
                        dust.velocity.x /= 8;
                        return true;
                    }
                }
        ));
    }
}
