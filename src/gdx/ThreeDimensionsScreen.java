package dev.trindadedev.bshrunner.gdx;

/*
 * Copyright 2025 Aquiles Trindade (trindadedev).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

// just a test not implemented yet
public class ThreeDimensionsScreen extends ApplicationAdapter {
  private PerspectiveCamera camera;
  private ModelBatch modelBatch;
  private ModelInstance cubeInstance;
  private Environment environment;
  private CameraInputController camController;

  @Override
  public void create() {
    camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera.position.set(5f, 5f, 5f);
    camera.lookAt(0, 0, 0);
    camera.near = 1f;
    camera.far = 100f;

    camController = new CameraInputController(camera);
    Gdx.input.setInputProcessor(camController);

    ModelBuilder modelBuilder = new ModelBuilder();
    Model cubeModel =
        modelBuilder.createBox(
            2f,
            2f,
            2f,
            new Material(ColorAttribute.createDiffuse(Color.RED)),
            Usage.Position | Usage.Normal);

    cubeInstance = new ModelInstance(cubeModel);

    modelBatch = new ModelBatch();
    environment = new Environment();
    environment.set(ColorAttribute.createAmbientLight(0.5f, 0.5f, 0.5f, 1f));
  }

  @Override
  public void render() {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);

    camController.update();

    modelBatch.begin(camera);
    modelBatch.render(cubeInstance, environment);
    modelBatch.end();
  }

  @Override
  public void resize(int width, int height) {
    camera.viewportWidth = width;
    camera.viewportHeight = height;
    camera.update();
  }

  @Override
  public void dispose() {
    modelBatch.dispose();
    cubeInstance.model.dispose();
    // camController.dispose();
  }
}
