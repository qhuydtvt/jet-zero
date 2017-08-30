package game.players.enginedusts;

import game.bases.GameObject;
import game.bases.Vector2D;
import game.bases.actions.*;
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
        updatePhysics();
        updateAlpha();
    }

    private void updateAlpha() {
        this.imageRenderer.getTransform().alpha = this.imageAlpha;
    }

    private void updatePhysics() {
        if (Physics.bodyAtPoint(this.screenPosition.add(velocity), Platform.class) != null) {
            this.velocity.y = 0;
        }
    }

    @Override
    public void refresh() {
        super.refresh();
        imageAlpha = 1.0f;
    }

    public void config(Vector2D velocity, int frameAlive) {
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

        this.addAction(new SequenceAction(
                new RepeatAction(
                        new Action() {
                            @Override
                            public boolean run(GameObject gameObject) {
                                EngineDust dust = (EngineDust) gameObject;
                                dust.imageAlpha = Mathx.clamp(dust.imageAlpha - (1f / frameAlive), 0, 1);
                                return true;
                            }
                        },
                        frameAlive
                ),
                new DeactivateAction()
        ));
    }
}
