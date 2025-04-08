package dev.trindadedev.eltrinity.utils.function;

@FunctionalInterface
public interface Listener<T> {
  void call(final T value);
}
