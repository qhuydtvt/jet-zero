package game;

import game.bases.GameObject;
import game.bases.Vector2D;
import game.bases.renderers.Renderer;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by huynq on 8/31/17.
 */
public class ViewPort {

    private Vector2D position;
    private Vector2D followOffset;

    public ViewPort() {
        position = Vector2D.ZERO.clone();
        followOffset = Vector2D.ZERO.clone();
    }

    public void follow(GameObject gameObject) {
        position = gameObject.position.add(followOffset);
    }

    public Vector2D translate(Vector2D screenPosition) {
        return screenPosition.subtract(this.position);
    }

    public Vector2D getFollowOffset() {
        return followOffset;
    }
}
