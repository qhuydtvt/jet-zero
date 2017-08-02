package game.bases.renderers;

import game.bases.FrameCounter;
import game.bases.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

/**
 * Created by huynq on 7/25/17.
 */
public class Animation implements Renderer {

    private List<BufferedImage> images;
    private int imageIndex;
    private FrameCounter frameCounter;
    private boolean finished;
    private boolean repeat;

    public Animation(int delayFrame, boolean repeat, BufferedImage... imageArr) {
        frameCounter = new FrameCounter(delayFrame);
        this.images = Arrays.asList(imageArr);
        this.repeat = repeat;
    }

    public Animation(BufferedImage... imageArr) {
        this(5, true, imageArr);
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public void render(Graphics g, Vector2D position) {
        if (frameCounter.run()) {
            changeIndex();
            frameCounter.reset();
        }
        BufferedImage image = images.get(imageIndex);
        g.drawImage(image,
                (int) (position.x - image.getWidth() / 2),
                (int) (position.y - image.getHeight() / 2),
                null);
    }

    private void changeIndex() {
        if (imageIndex >= images.size() - 1) {
            if (repeat) {
                imageIndex = 0;
            }
            finished = true;
        } else {
            imageIndex ++;
        }
    }

    public void reset() {
        imageIndex = 0;
        finished = false;
    }
}
