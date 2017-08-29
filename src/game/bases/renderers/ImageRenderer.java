package game.bases.renderers;

import game.bases.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by huynq on 7/16/17.
 */
public class ImageRenderer implements Renderer {
    public BufferedImage image;
    public Transform transform;

    public ImageRenderer(BufferedImage image) {
        this.image = image;
        this.transform = new Transform();
    }

    public void render(Graphics g, Vector2D position) {
        transform.render(g, this.image, position);
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }
}
