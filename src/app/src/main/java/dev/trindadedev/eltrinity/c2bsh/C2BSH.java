package dev.trindadedev.eltrinity.c2bsh;

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

public class C2BSH {
  static {
    System.loadLibrary("c2bsh");
  }

  public static native long convert(final String cCode);

  public static native String getCode(final long c2bshResult);

  public static native String[] getIncludes(final long c2bshResult);

  public static native void close(final long c2bshResult);
}
