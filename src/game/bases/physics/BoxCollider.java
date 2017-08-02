package game.bases.physics;

import game.bases.GameObject;
import tklibs.Mathx;

/**
 * Created by huynq on 7/23/17.
 */
public class BoxCollider extends GameObject {
    public float width;
    public float height;

    public BoxCollider(float width, float height) {
        super();
        this.width = width;
        this.height = height;
    }

    public BoxCollider() {
        this(0, 0);
    }

    public float left() {
        return this.screenPosition.x - width / 2;
    }

    public float right() {
        return this.screenPosition.x + width / 2;
    }

    public float top() {
        return this.screenPosition.y - height / 2;
    }

    public float bottom() {
        return this.screenPosition.y + height / 2;
    }

    public boolean collideWith(BoxCollider other) {
        boolean xOverlap = Mathx.inRange(other.left(), this.left(), this.right())
                || Mathx.inRange(this.left(), other.left(), other.right());

        boolean yOverlap = Mathx.inRange(other.top(), this.top(), this.bottom())
                || Mathx.inRange(this.top(), other.top(), other.bottom());

        return xOverlap && yOverlap;

//        Rectangle rect1 = new Rectangle((int)left(), (int)top(), (int)width, (int)height);
//        Rectangle rect2 = new Rectangle((int)other.left(), (int)other.top(), (int)other.width, (int)other.height);
//        return rect1.intersects(rect2);
    }

    @Override
    public String toString() {
        return "BoxCollider{" +
                "width=" + width +
                ", height=" + height +
                ", screenPosition=" + screenPosition +
                '}';
    }
}
