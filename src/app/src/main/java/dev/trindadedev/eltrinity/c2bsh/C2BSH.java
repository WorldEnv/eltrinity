package dev.trindadedev.eltrinity.c2bsh;

public class C2BSH {
  static {
    System.loadLibrary("c2bsh");
  }

  public native String convert(final String c_code);
}