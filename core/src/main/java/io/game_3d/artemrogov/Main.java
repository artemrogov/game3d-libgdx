package io.game_3d.artemrogov;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter implements InputProcessor {

    private PerspectiveCamera perspectiveCamera;
    private ModelBatch modelBatch;
    private Model box;
    private ModelInstance modelInstance;
    private Environment environment;


    @Override
    public void create() {
        perspectiveCamera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        perspectiveCamera.position.set(0f, 0f, 3f); // позиция камеры
        perspectiveCamera.lookAt(0f, 0f, 0f); // начало координат сцены (уточнить в документации)
        perspectiveCamera.near = 0.1f; // нижняя граница обзора (уточнить в документации)
        perspectiveCamera.far = 300f; // дальность обзора камеры

        modelBatch = new ModelBatch();
        ModelBuilder modelBuilder = new ModelBuilder();

        Material boxMaterial = new Material(ColorAttribute.createDiffuse(Color.BLUE));
        box = modelBuilder.createBox(2f, 2f, 2f, boxMaterial, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance = new ModelInstance(box);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f)); // сероватый, + интенсивность - 1

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        perspectiveCamera.update();
        modelBatch.begin(perspectiveCamera);
        modelBatch.render(modelInstance,environment);
        modelBatch.end();
    }

    @Override
    public void dispose() { // очистка мусора
        box.dispose();
        modelBatch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT){
            perspectiveCamera.rotateAround(new Vector3(0f,0f,0f),new Vector3(0f,1f,0f),1f);
        }
        if(keycode == Input.Keys.RIGHT){
            perspectiveCamera.rotateAround(new Vector3(0f,0f,0f),new Vector3(0f,-1f,0f),1f);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
