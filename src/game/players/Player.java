package game.players;

import game.Utils;
import game.bases.GameObject;
import game.bases.GameObjectPool;
import game.bases.Vector2D;
import game.bases.actions.Action;
import game.bases.actions.SequenceAction;
import game.bases.actions.WaitAction;
import game.bases.inputs.InputListener;
import game.bases.inputs.InputManager;
import game.bases.physics.BoxCollider;
import game.bases.physics.Physics;
import game.bases.physics.PhysicsBody;
import game.bases.renderers.ImageRenderer;
import game.platforms.Platform;
import tklibs.Mathx;

import static java.awt.event.KeyEvent.VK_X;

/**
 * Created by huynq on 8/3/17.
 */
public class Player extends GameObject {
    private BoxCollider boxCollider;

    private float angle = 0;
    private final float GRAVITY = 0.4f;
    private final int JET_ENERGY_MAX = 400;
    private final int JET_ENERGY_CONSUME_RATE = 2;
    private final int JET_ENERGY_BOOST_CONSUME_RATE = 10;
    private final int JET_ENERGY_RECHARGE_RATE = 4;

    private final int JET_NORMAL_SPEED = 4;
    private final int JET_BOOST_SPEED = 12;

    private boolean boostDisabled;

    private int jetEnergy;

    private Vector2D velocity;
    private Vector2D jetVelocity;

    public Player() {
        super();
        this.velocity = new Vector2D();
        this.renderer = new ImageRenderer(Utils.loadAssetImage("green_square.png"));
        this.boxCollider = new BoxCollider(30, 30);
        this.children.add(boxCollider);
        this.jetEnergy = JET_ENERGY_MAX;
        this.jetVelocity = new Vector2D();
        this.boostDisabled = false;
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);

        this.jetVelocity.set(Vector2D.ZERO);
        this.velocity.x = 0;

        if (InputManager.instance.rightPressed) {
            angle = Mathx.clamp(angle + 4, -60, 60);
        }

        if (InputManager.instance.leftPressed) {
            angle = Mathx.clamp(angle - 4, -60, 60);
        }

        if (InputManager.instance.upPressed) {
            // Jet
            if (jetEnergy > JET_ENERGY_MAX * 0.2) {
                this.jetVelocity.set(Vector2D.UP.rotate(angle).multiply(JET_NORMAL_SPEED));
                this.jetEnergy -= JET_ENERGY_CONSUME_RATE;
            }
        } else {
            this.jetEnergy = Mathx.clamp(this.jetEnergy + JET_ENERGY_RECHARGE_RATE, 0, JET_ENERGY_MAX);
        }

        if (InputManager.instance.xPressed) {
            if(!this.boostDisabled)
                boostAndFire();
        }

        if (GRAVITY + this.jetVelocity.y <= 0) {
            this.velocity.y = this.jetVelocity.y;
        } else {
            this.velocity.y += Mathx.clamp (GRAVITY + this.jetVelocity.y, -15, 1);
        }
        this.velocity.x = jetVelocity.x;

        moveVertical();
        moveHorizontal();
    }

    private void boostAndFire() {
        if (jetEnergy > JET_ENERGY_MAX * 0.2) {
            this.boostDisabled = true;
            this.addAction(new SequenceAction(
                    new WaitAction(20),
                    // Enable boost
                    new Action() {
                        @Override
                        public boolean run(GameObject gameObject) {
                            Player player = (Player)gameObject;
                            player.boostDisabled = false;
                            return true;
                        }

                        @Override
                        public void reset() {

                        }
                    }
            ));

            this.jetVelocity.set(Vector2D.UP.rotate(angle).multiply(JET_BOOST_SPEED));
            this.jetEnergy -= JET_ENERGY_BOOST_CONSUME_RATE;
            spawnFireBall();
        }
    }

    private void spawnFireBall() {
        PlayerFireBall fireBall = GameObjectPool.recycle(PlayerFireBall.class);
        fireBall.position.set(this.position);
        fireBall.getVelocity().set(Vector2D.UP.rotate(angle).multiply(-1).multiply(5));
    }

    private void moveHorizontal() {
        float deltaX = velocity.x > 0 ? 1 : -1;
        PhysicsBody body = Physics.bodyInRect(position.add(velocity.x, 0), boxCollider.width, boxCollider.height, Platform.class);
        if (body != null) {
            while (Physics.bodyInRect(position.add(deltaX, 0), boxCollider.width, boxCollider.height, Platform.class) == null) {
                position.addUp(deltaX, 0);
            }
            this.velocity.x = 0;
        }
        this.position.x += velocity.x;
    }

    private void moveVertical() {
        float deltaY = velocity.y > 0 ? 1 : -1;
        PhysicsBody body = Physics.bodyInRect(position.add(0, velocity.y), boxCollider.width, boxCollider.height, Platform.class);
        if (body != null) {
            while (Physics.bodyInRect(position.add(0, deltaY), boxCollider.width, boxCollider.height, Platform.class) == null) {
                position.addUp(0, deltaY);
            }
            this.velocity.y = 0;
            this.angle = 0;
        }
        this.position.y += velocity.y;
    }
}
