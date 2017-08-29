package game.players.enginefires;

import game.bases.GameObject;
import game.bases.Vector2D;
import game.bases.renderers.Animation;
import game.players.Player;
import tklibs.Mathx;
import tklibs.SpriteUtils;

/**
 * Created by huynq on 8/30/17.
 */
public class EngineFire extends GameObject {
    private Animation animation;
    private Vector2D rootPosition;
    private float animScale;

    public EngineFire() {
        super();
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
        this.rootPosition = new Vector2D();
        this.animScale = 0;
    }

    public void update(Player player) {
        this.isHidden = !player.isJetRunning();
        if (!this.isHidden) {
            this.position.set(rootPosition.rotate(player.getAngle()));
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
    }

    public Vector2D getRootPosition() {
        return rootPosition;
    }
}
