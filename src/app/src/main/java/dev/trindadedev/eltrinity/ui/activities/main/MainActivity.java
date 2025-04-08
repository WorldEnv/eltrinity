package dev.trindadedev.eltrinity.ui.activities.main;

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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import dev.trindadedev.eltrinity.beans.ProjectBean;
import dev.trindadedev.eltrinity.databinding.ActivityMainBinding;
import dev.trindadedev.eltrinity.ui.activities.main.project.ProjectsAdapter;
import dev.trindadedev.eltrinity.ui.activities.main.project.ProjectsViewModel;
import dev.trindadedev.eltrinity.ui.activities.runner.RunnerActivity;
import dev.trindadedev.eltrinity.ui.activities.runner.RunnerState;
import dev.trindadedev.eltrinity.ui.base.BaseAppCompatActivity;

public class MainActivity extends BaseAppCompatActivity {

  @NonNull private ActivityMainBinding binding;

  private ProjectsViewModel projectsViewModel;
  private ProjectsAdapter projectsAdapter;

  @Override
  @NonNull
  protected View bindLayout() {
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    return binding.getRoot();
  }

  @Override
  protected void onBindLayout(@Nullable final Bundle savedInstanceState) {
    projectsViewModel = new ViewModelProvider(this).get(ProjectsViewModel.class);
    projectsAdapter = new ProjectsAdapter();
    projectsAdapter.setOnProjectClick(this::openProject);
    projectsViewModel.fetch();
    projectsViewModel.getProjects().observe(this, projectsAdapter::submitList);
    binding.list.setAdapter(projectsAdapter);
  }

  private void openProject(final ProjectBean project) {
    final var runnerState = new RunnerState();
    runnerState.project = project;
    final var intent = new Intent(this, RunnerActivity.class);
    intent.putExtra("runner_state", runnerState);
    startActivity(intent);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    this.binding = null;
  }
}
