package io.game_3d.artemrogov;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {

    private static final float ROTATE_SPEED_BOX_MODEL_INSTANCE = 0.5f;

    private PerspectiveCamera perspectiveCamera;
    private ModelBatch modelBatch;
    private Model box;
    private ModelInstance modelInstance;
    private Environment environment;
    private Texture texture;
    private PerspectiveCameraInputProcessor perspectiveCameraInputProcessor;

    @Override
    public void create() {
        perspectiveCamera = new PerspectiveCamera(40, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        perspectiveCamera.position.set(0f, 5f, -5f);
        perspectiveCamera.lookAt(0f, 0f, 0f);
        perspectiveCamera.near = 0.1f;
        perspectiveCamera.far = 300f;

        modelBatch = new ModelBatch();
        ModelBuilder modelBuilder = new ModelBuilder();

        texture = new Texture(Gdx.files.internal("test_cube.jpg"));
        TextureAttribute textureBoxAttribute = new TextureAttribute(TextureAttribute.Diffuse, texture);
        Material boxMaterial = new Material();
        boxMaterial.set(textureBoxAttribute);
        boxMaterial.set(ColorAttribute.createSpecular(Color.WHITE));
        boxMaterial.set(FloatAttribute.createShininess(8f));

        box = modelBuilder.createBox(
            2f, 2f, 2f,
            boxMaterial,
            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates
        );
        modelInstance = new ModelInstance(box);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));
        perspectiveCameraInputProcessor = new PerspectiveCameraInputProcessor(perspectiveCamera);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.input.setInputProcessor(perspectiveCameraInputProcessor);
        perspectiveCamera.update();

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            float dx = Gdx.input.getDeltaX();
            float dy = Gdx.input.getDeltaY();
            modelInstance.transform.rotate(Vector3.Y, -dx * ROTATE_SPEED_BOX_MODEL_INSTANCE);
            modelInstance.transform.rotate(Vector3.X, dy * ROTATE_SPEED_BOX_MODEL_INSTANCE);
        }

        modelBatch.begin(perspectiveCamera);
        modelBatch.render(modelInstance,environment);
        modelBatch.end();
    }

    @Override
    public void dispose() {
        box.dispose();
        texture.dispose();
        modelBatch.dispose();
    }

}
