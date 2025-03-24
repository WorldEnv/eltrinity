package dev.trindadedev.eltrinity.opengl;

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

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ELTrinityGLRenderer implements GLSurfaceView.Renderer {

  public RendererEvents events;

  public ELTrinityGLRenderer() {
    events = new RendererEvents();
  }

  @Override
  public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    events.onSurfaceCreated.onCallEvent(gl, config);
  }

  @Override
  public void onSurfaceChanged(GL10 gl, int width, int height) {
    events.onSurfaceChanged.onCallEvent(gl, width, height);
  }

  @Override
  public void onDrawFrame(GL10 gl) {
    events.onDrawFrame.onCallEvent(gl);
  }

  public static class RendererEvents {
    // use default values for events to avoid crashes.
    public OnSurfaceCreated onSurfaceCreated =
        new OnSurfaceCreated() {
          @Override
          public void onCallEvent(GL10 gl, EGLConfig config) {}
        };
    public OnSurfaceChanged onSurfaceChanged =
        new OnSurfaceChanged() {
          @Override
          public void onCallEvent(GL10 gl, int width, int height) {}
        };
    public OnDrawFrame onDrawFrame =
        new OnDrawFrame() {
          @Override
          public void onCallEvent(GL10 gl) {}
        };

    public void setOnSurfaceCreated(final OnSurfaceCreated onSurfaceCreated) {
      this.onSurfaceCreated = onSurfaceCreated;
    }

    public void setOnSurfaceChanged(final OnSurfaceChanged onSurfaceChanged) {
      this.onSurfaceChanged = onSurfaceChanged;
    }

    public void setOnDrawFrame(final OnDrawFrame onDrawFrame) {
      this.onDrawFrame = onDrawFrame;
    }

    public interface OnSurfaceCreated {
      void onCallEvent(GL10 gl, EGLConfig config);
    }

    public interface OnSurfaceChanged {
      void onCallEvent(GL10 gl, int width, int height);
    }

    public interface OnDrawFrame {
      void onCallEvent(GL10 gl);
    }
  }
}
