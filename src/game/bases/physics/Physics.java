package game.bases.physics;


import java.util.Vector;

/**
 * Created by huynq on 7/25/17.
 */
public class Physics {

    private static Vector<PhysicsBody> bodies = new Vector<>();

    public static void add(PhysicsBody body) {
        bodies.add(body);
    }

    // Generics
    public static <T extends PhysicsBody> T bodyInRect(BoxCollider boxCollider, Class<T> classz) {
        for(PhysicsBody body: bodies) {
            if (body.isActive() && body.getBoxCollider().collideWith(boxCollider)) {
                if (body.getClass() == classz)
                    return (T) body;
            }
        }

        return null;
    }

    public static void clear() {
        bodies.clear();
    }
}
