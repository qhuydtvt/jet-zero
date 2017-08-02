package game.bases.renderers;

import game.bases.Vector2D;

import java.awt.*;

/**
 * Created by huynq on 7/25/17.
 */
public interface Renderer {
    void render(Graphics g, Vector2D position);
}
