package game.players;

import game.Utils;
import game.bases.FrameCounter;
import game.bases.GameObject;
import game.bases.Vector2D;
import game.bases.inputs.InputManager;
import game.bases.physics.BoxCollider;
import game.bases.physics.Physics;
import game.bases.physics.PhysicsBody;
import game.bases.renderers.ImageRenderer;
import game.platforms.Platform;
import tklibs.Mathx;

/**
 * Created by huynq on 8/3/17.
 */
public class Player extends GameObject {
    private BoxCollider boxCollider;

    private final float GRAVITY = 2f;
    private final int JET_ENERGY_MAX = 100;
    private final int JET_ENERGY_CONSUME_RATE = 2;
    private final int JET_ENERGY_RECHARGE_RATE = 1;

    private int jetEnergy;

    private Vector2D velocity;

    public Player() {
        super();
        this.velocity = new Vector2D();
        this.renderer = new ImageRenderer(Utils.loadAssetImage("green_square.png"));
        this.boxCollider = new BoxCollider(30, 30);
        this.children.add(boxCollider);
        this.jetEnergy = JET_ENERGY_MAX;
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);

        this.velocity.y += GRAVITY;

        this.velocity.x = 0;

        if (InputManager.instance.leftPressed) {
            this.velocity.x -= 5;
        }

        if (InputManager.instance.rightPressed) {
            this.velocity.x += 5;
        }

        if (InputManager.instance.upPressed) {
            if (Physics.bodyInRect(position.add(0, 1), boxCollider.width, boxCollider.height, Platform.class) != null) {
                // Feet on ground
                this.velocity.y = -10;
            } else {
                // Jet
                if(jetEnergy > 0) {
                    this.velocity.y = -2;
                    this.jetEnergy -= JET_ENERGY_CONSUME_RATE;
                }
            }
        } else {
            this.jetEnergy = Mathx.clamp(this.jetEnergy + JET_ENERGY_RECHARGE_RATE, 0, JET_ENERGY_MAX);
        }

        moveVertical();
        moveHorizontal();
    }

    private void moveHorizontal() {
        float deltaX = velocity.x > 0 ? 1: -1;
        PhysicsBody body = Physics.bodyInRect(position.add(velocity.x, 0), boxCollider.width, boxCollider.height, Platform.class);
        if (body != null) {
            while(Physics.bodyInRect(position.add(deltaX, 0), boxCollider.width, boxCollider.height, Platform.class) == null) {
                position.addUp(deltaX, 0);
            }
            this.velocity.x = 0;
        }
        this.position.x += velocity.x;
    }

    private void moveVertical() {
        float deltaY = velocity.y > 0 ? 1: -1;
        PhysicsBody body = Physics.bodyInRect(position.add(0, velocity.y), boxCollider.width, boxCollider.height, Platform.class);
        if (body != null) {
            while(Physics.bodyInRect(position.add(0, deltaY), boxCollider.width, boxCollider.height, Platform.class) == null) {
                position.addUp(0, deltaY);
            }
            this.velocity.y = 0;
        }
        this.position.y += velocity.y;
    }
}
