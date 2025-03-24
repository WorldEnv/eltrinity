package dev.trindadedev.eltrinity.ui.activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import dev.trindadedev.eltrinity.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

  @NonNull
  private ActivityMainBinding binding;

  @Override
  protected void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
  }
}