package game.bases.renderers;

import game.bases.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by huynq on 8/30/17.
 */
public class Transform {
    public Vector2D anchor;
    public float angle;
    public Vector2D scale;
    public float alpha;

    public Transform() {
        anchor = Vector2D.ONE.multiply(0.5f);
        angle = 0;
        alpha = 1;
        scale = Vector2D.ONE.clone();
    }

    public void render(Graphics g, BufferedImage image, Vector2D position) {
        AffineTransform trans = new AffineTransform();
        trans.translate(position.x - image.getWidth() * anchor.x, position.y - image.getHeight() * anchor.y);
        trans.rotate(Math.toRadians(angle), image.getWidth() * anchor.x, image.getHeight() * anchor.y);
        trans.scale(scale.x, scale.y);
        Graphics2D g2d = (Graphics2D)g;
        Composite oldComposite = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(image, trans, null);
        g2d.setComposite(oldComposite);
    }
}
