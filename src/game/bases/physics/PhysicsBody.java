package game.bases.physics;

/**
 * Created by huynq on 7/25/17.
 */
public interface PhysicsBody {
    BoxCollider getBoxCollider();
    boolean isActive();
}
