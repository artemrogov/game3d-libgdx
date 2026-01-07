package io.game_3d.artemrogov;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.UBJsonReader;

public class Main extends ApplicationAdapter {

    public static final String ANIMATION_ID = "Armature|Armature|mixamo.com|Layer0";

    private PerspectiveCamera perspectiveCamera;
    private ModelBatch modelBatch;
    private Model model;
    private Model placeModel;
    private ModelInstance modelInstance;
    private ModelInstance placeModelInstance;
    private Environment environment;
    private AnimationController animationController;



    @Override
    public void create() {
        perspectiveCamera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        perspectiveCamera.position.set(0f, 245f, 300f);
        perspectiveCamera.lookAt(0f, 0f, 0f);

        perspectiveCamera.near = 0.1f;
        perspectiveCamera.far = 1700f;

        modelBatch = new ModelBatch();

        ModelBuilder placeModelBuilder = new ModelBuilder();

        UBJsonReader jsonReader = new UBJsonReader();
        G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
        model = modelLoader.loadModel(Gdx.files.getFileHandle("test2/test4.g3db", Files.FileType.Internal));
        modelInstance = new ModelInstance(model);


        Material boxMaterial = new Material(ColorAttribute.createDiffuse(Color.BLUE));
        placeModel = placeModelBuilder.createBox(500f, 0.1f, 500f, boxMaterial, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        placeModelInstance = new ModelInstance(placeModel);

        placeModelInstance.transform.translate(0f,-3f,0f);


        environment = new Environment();
        ColorAttribute colorAttribute = new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f);
        environment.set(colorAttribute);

        animationController = new AnimationController(modelInstance);

    }

    @Override
    public void render() {

        boolean wasMoving = false;
        float rotateSpeed = 0.5f; // Скорость вращения камеры

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        perspectiveCamera.update();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            modelInstance.transform.rotate(Vector3.Y, -2f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            modelInstance.transform.rotate(Vector3.Y, 2f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            modelInstance.transform.translate(0f,0f,1f);
            wasMoving = true;
        }

        if (wasMoving){
            animationController.paused = false;
            animationController.setAnimation(ANIMATION_ID,-1);
        }

        if (!wasMoving){
            animationController.paused = true;
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            int dx = Gdx.input.getDeltaX(); // Смещение мыши по горизонтальной оси
            // Горизонтальное вращение (вокруг оси Y)
            perspectiveCamera.rotateAround(Vector3.Zero, Vector3.Y, -dx * rotateSpeed);
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
            int dy = Gdx.input.getDeltaY(); // Смещение мыши по вертикальной оси
            // Вертикальное вращение (вокруг оси X)
            perspectiveCamera.rotateAround(Vector3.Zero, Vector3.X, dy * rotateSpeed);
        }


        animationController.update(Gdx.graphics.getDeltaTime());
        modelBatch.begin(perspectiveCamera);
        modelBatch.render(modelInstance,environment);
        modelBatch.render(placeModelInstance,environment);
        modelBatch.end();
    }

    @Override
    public void dispose() {
        model.dispose();
        placeModel.dispose();
        modelBatch.dispose();
    }
}
