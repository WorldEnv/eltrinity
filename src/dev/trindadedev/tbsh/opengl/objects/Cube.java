package dev.trindadedev.tbsh.opengl.objects;

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

import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Cube implements BaseObject {
  private final FloatBuffer vertexBuffer;
  private FloatBuffer colorBuffer;
  private final int program;

  private final int COORDS_PER_VERTEX = 3;
  private final float[] cubeCoords = {
    -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f,
    -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f
  };

  // default colors for the cube vertices.
  private float[] colors = {
    1.0f, 1.0f, 1.0f, 1.0f, // white vertex 1
    1.0f, 1.0f, 1.0f, 1.0f, // white vertex 2
    1.0f, 1.0f, 1.0f, 1.0f, // white vertex 3
    1.0f, 1.0f, 1.0f, 1.0f, // white vertex 4
    1.0f, 1.0f, 1.0f, 1.0f, // white vertex 5
    1.0f, 1.0f, 1.0f, 1.0f, // white vertex 6
    1.0f, 1.0f, 1.0f, 1.0f, // white vertex 7
    1.0f, 1.0f, 1.0f, 1.0f // white vertex 8
  };

  private final short[] drawOrder = {
    0, 1, 2, 0, 2, 3,
    4, 5, 6, 4, 6, 7,
    0, 1, 5, 0, 5, 4,
    2, 3, 7, 2, 7, 6,
    0, 3, 7, 0, 7, 4,
    1, 2, 6, 1, 6, 5
  };

  private final String vertexShaderCode =
      "uniform mat4 uMVPMatrix;"
          + "attribute vec4 vPosition;"
          + "attribute vec4 vColor;"
          + "varying vec4 vColorVarying;"
          + "void main() {"
          + "  gl_Position = uMVPMatrix * vPosition;"
          + "  vColorVarying = vColor;"
          + "}";

  private final String fragmentShaderCode =
      "precision mediump float;"
          + "varying vec4 vColorVarying;"
          + "void main() {"
          + "  gl_FragColor = vColorVarying;"
          + "}";

  public Cube() {
    // initialize vertex buffer
    ByteBuffer bb = ByteBuffer.allocateDirect(cubeCoords.length * 4);
    bb.order(ByteOrder.nativeOrder());
    vertexBuffer = bb.asFloatBuffer();
    vertexBuffer.put(cubeCoords);
    vertexBuffer.position(0);

    // initialize color buffer with default colors
    updateColorBuffer();

    // Load shaders and create program
    int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
    int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

    program = GLES20.glCreateProgram();
    GLES20.glAttachShader(program, vertexShader);
    GLES20.glAttachShader(program, fragmentShader);
    GLES20.glLinkProgram(program);
  }

  public void draw(float[] mvpMatrix) {
    GLES20.glUseProgram(program);

    // define vertex position
    int positionHandle = GLES20.glGetAttribLocation(program, "vPosition");
    GLES20.glEnableVertexAttribArray(positionHandle);
    GLES20.glVertexAttribPointer(
        positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, vertexBuffer);

    // define vertex color
    int colorHandle = GLES20.glGetAttribLocation(program, "vColor");
    GLES20.glEnableVertexAttribArray(colorHandle);
    GLES20.glVertexAttribPointer(colorHandle, 4, GLES20.GL_FLOAT, false, 0, colorBuffer);

    // define model-view-projection matrix
    int matrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");
    GLES20.glUniformMatrix4fv(matrixHandle, 1, false, mvpMatrix, 0);

    // draw cube
    ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2); // 2 bytes per short
    dlb.order(ByteOrder.nativeOrder());
    ShortBuffer drawListBuffer = dlb.asShortBuffer();
    drawListBuffer.put(drawOrder);
    drawListBuffer.position(0);
    GLES20.glDrawElements(
        GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

    // disable vertex attributes
    GLES20.glDisableVertexAttribArray(positionHandle);
    GLES20.glDisableVertexAttribArray(colorHandle);
  }

  private int loadShader(int type, String shaderCode) {
    int shader = GLES20.glCreateShader(type);
    GLES20.glShaderSource(shader, shaderCode);
    GLES20.glCompileShader(shader);
    return shader;
  }

  // Changes the colors of cube vertices.
  public void setColors(float[] colors) {
    this.colors = colors;
    updateColorBuffer();
  }

  private void updateColorBuffer() {
    // recreate the color buffer with the new colors
    ByteBuffer cb = ByteBuffer.allocateDirect(colors.length * 4);
    cb.order(ByteOrder.nativeOrder());
    colorBuffer = cb.asFloatBuffer();
    colorBuffer.put(colors);
    colorBuffer.position(0);
  }
}
