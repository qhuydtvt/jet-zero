package game.bases.scenes;

/**
 * Created by huynq on 8/1/17.
 */
public class SceneManager {
    private Scene currentScene;
    private Scene nextScene;

    public static final SceneManager instance = new SceneManager();

    private SceneManager() {

    }

    public void changeSceneIfNeeded() {
        if (nextScene != null) {
            if (currentScene != null)
                currentScene.deInit();
            nextScene.init();

            currentScene = nextScene;
            nextScene = null;
        }
    }

    public void requestChangeScene(Scene scene) {
        nextScene = scene;
    }
}
