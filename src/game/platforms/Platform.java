package game.platforms;

import game.Utils;
import game.bases.GameObject;
import game.bases.physics.BoxCollider;
import game.bases.physics.PhysicsBody;
import game.bases.renderers.ImageRenderer;
import tklibs.SpriteUtils;

/**
 * Created by huynq on 8/3/17.
 */
public class Platform extends GameObject implements PhysicsBody {
    private BoxCollider boxCollider;

    public Platform() {
        super();

        this.boxCollider = new BoxCollider(32, 32);
        this.children.add(boxCollider);
    }

    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }

    public static Platform create(int platformType) {
        Platform platform = new Platform();
        switch (platformType) {
            case 3:
                platform.renderer = new ImageRenderer(SpriteUtils.loadImage("assets/images/sprite/platform0000.png"));
                break;
            case 2:
                platform.renderer = new ImageRenderer(SpriteUtils.loadImage("assets/images/sprite/spike0000.png"));
                break;
            case 1:
                platform.renderer = new ImageRenderer(SpriteUtils.loadImage("assets/images/sprite/grass0000.png"));
                break;
        }

        return platform;
    }
}
