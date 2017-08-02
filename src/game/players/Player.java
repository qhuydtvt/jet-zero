package game.players;

import game.Utils;
import game.bases.GameObject;
import game.bases.Vector2D;
import game.bases.inputs.InputManager;
import game.bases.physics.BoxCollider;
import game.bases.physics.Physics;
import game.bases.physics.PhysicsBody;
import game.bases.renderers.ImageRenderer;
import game.platforms.Platform;

import javax.swing.*;

/**
 * Created by huynq on 8/3/17.
 */
public class Player extends GameObject {
    private BoxCollider boxCollider;

    private float gravity = 2f;
    private Vector2D velocity;

    public Player() {
        super();
        this.velocity = new Vector2D();
        this.renderer = new ImageRenderer(Utils.loadAssetImage("green_square.png"));
        this.boxCollider = new BoxCollider(30, 30);
        this.children.add(boxCollider);
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);

        this.velocity.y += gravity;
        this.velocity.x = 0;

        if (InputManager.instance.leftPressed) {
            this.velocity.x -= 5;
        }

        if (InputManager.instance.rightPressed) {
            this.velocity.x += 5;
        }

        if (InputManager.instance.upPressed) {
            if (Physics.bodyInRect(position.add(0, 1), boxCollider.width, boxCollider.height, Platform.class) != null) {
                this.velocity.y = -15;
            }
        }

        moveVertical();
        moveHorizontal();

        this.position.addUp(this.velocity);
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
    }
}
