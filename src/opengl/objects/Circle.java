package dev.trindadedev.bshrunner.opengl.objects;

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

public class Circle {
  private static final int COORDS_PER_VERTEX = 3;
  private FloatBuffer vertexBuffer;
  private final int program;
  private int numVertices;

  // shader code
  private final String vertexShaderCode =
    "uniform mat4 uMVPMatrix;" +
    "attribute vec4 vPosition;" +
    "void main() {" +
    "  gl_Position = uMVPMatrix * vPosition;" +
    "}";

  private final String fragmentShaderCode =
    "precision mediump float;" +
    "uniform vec4 vColor;" +
    "void main() {" +
    "  gl_FragColor = vColor;" +
    "}";

  // circle color (RGBA)
  private float[] color = { 1.0f, 1.0f, 1.0f, 1.0f }; // default color white

  public Circle(float radius, int segments) {
    numVertices = segments + 2;
    float[] circleCoords = new float[numVertices * COORDS_PER_VERTEX];

    // center vertex
    circleCoords[0] = 0.0f; // X
    circleCoords[1] = 0.0f; // Y
    circleCoords[2] = 0.0f; // Z

    // generate circle vertices
    for (int i = 1; i < numVertices; i++) {
      float angle = (float) (2.0f * Math.PI * (i - 1) / segments);
      circleCoords[i * 3] = (float) Math.cos(angle) * radius;
      circleCoords[i * 3 + 1] = (float) Math.sin(angle) * radius;
      circleCoords[i * 3 + 2] = 0.0f;
    }

    // buffer setup
    ByteBuffer bb = ByteBuffer.allocateDirect(circleCoords.length * 4);
    bb.order(ByteOrder.nativeOrder());
    vertexBuffer = bb.asFloatBuffer();
    vertexBuffer.put(circleCoords);
    vertexBuffer.position(0);

    // compile shaders
    int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
    int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

    // link program
    program = GLES20.glCreateProgram();
    GLES20.glAttachShader(program, vertexShader);
    GLES20.glAttachShader(program, fragmentShader);
    GLES20.glLinkProgram(program);
  }

  public void draw(float[] mvpMatrix) {
    GLES20.glUseProgram(program);

    int positionHandle = GLES20.glGetAttribLocation(program, "vPosition");
    GLES20.glEnableVertexAttribArray(positionHandle);
    GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, vertexBuffer);

    int colorHandle = GLES20.glGetUniformLocation(program, "vColor");
    GLES20.glUniform4fv(colorHandle, 1, color, 0);

    int matrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");
    GLES20.glUniformMatrix4fv(matrixHandle, 1, false, mvpMatrix, 0);

    // draw the circle as a Triangle Fan
    GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, numVertices);

    GLES20.glDisableVertexAttribArray(positionHandle);
  }

  private int loadShader(int type, String shaderCode) {
    int shader = GLES20.glCreateShader(type);
    GLES20.glShaderSource(shader, shaderCode);
    GLES20.glCompileShader(shader);
    return shader;
  }

  public void setColor(final float[] color) {
    this.color = color;
  }
}