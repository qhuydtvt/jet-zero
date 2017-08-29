package game.players.enginefires;

import game.bases.GameObject;
import game.bases.GameObjectPool;
import game.bases.Vector2D;
import game.bases.physics.Physics;
import game.bases.renderers.Animation;
import game.platforms.Platform;
import game.players.Player;
import game.players.enginedusts.EngineDust;
import tklibs.Mathx;
import tklibs.SpriteUtils;

/**
 * Created by huynq on 8/30/17.
 */
public class EngineFire extends GameObject {
    private Animation animation;
    private Vector2D rootPosition;
    private float angle;
    private float animScale;

    public EngineFire() {
        super();
        addAnimation();
    }

    private void addAnimation() {
        this.animation = new Animation(
                10,
                true,
                SpriteUtils.loadImage("assets/images/sprite/fire00002.png"),
                SpriteUtils.loadImage("assets/images/sprite/fire00003.png"),
                SpriteUtils.loadImage("assets/images/sprite/fire00001.png")
        );
        this.animation.getTransform().anchor.set(0.5f, 0);
        this.animation.getTransform().scale.set(0.5f, 0.5f);
        this.renderer = animation;
        this.angle = 0;
        this.rootPosition = new Vector2D();
        this.animScale = 0;
    }

    public void update(Player player) {
        this.isHidden = !player.isJetRunning();
        if (!this.isHidden) {
            this.angle = player.getAngle();
            this.position.set(rootPosition.rotate(angle));
            this.animation.getTransform().angle = player.getAngle();
            this.animation.getTransform().scale.set(0.5f, this.animScale);
            this.animScale = Mathx.clamp(animScale + 0.03f, 0, 0.7f);
        } else {
            this.animScale = 0.05f;
        }
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        produceDustIfNeeded();
    }

    private void produceDustIfNeeded() {
        if (!isHidden) {
            Vector2D testPoint = this.screenPosition.add(-5, 0).add(Vector2D.DOWN.rotate(angle).multiply(32));
            Platform platform = Physics.bodyAtPoint(testPoint, Platform.class);
            if(platform != null) {
                for (int i = 0; i < 5; i++) {
                    EngineDust dust = GameObjectPool.recycle(EngineDust.class);
                    float deltaY = Mathx.uniformRandom(0, 5);
                    assert dust != null;
                    dust.position.set(testPoint.x + deltaY, platform.getBoxCollider().top());
                    Vector2D dustVelocity = Vector2D.DOWN.multiply(0.5f);
                    if (deltaY > 0) {
                        dustVelocity.x = Mathx.uniformRandom(2, 0.5f);
                    } else {
                        dustVelocity.x = Mathx.uniformRandom(-2, 0.5f);
                    }
                    dust.config(dustVelocity);
                }
            }
        }
    }

    public Vector2D getRootPosition() {
        return rootPosition;
    }
}
