package dev.trindadedev.eltrinity.ui.activities.main.project;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import dev.trindadedev.eltrinity.beans.ProjectBean;
import dev.trindadedev.eltrinity.databinding.LayoutProjectBinding;
import dev.trindadedev.eltrinity.project.manage.ProjectManager;
import dev.trindadedev.eltrinity.utils.function.Listener;
import java.io.File;

public class ProjectsAdapter extends ListAdapter<File, ProjectsAdapter.ProjectsAdapterViewHolder> {
  private Listener<ProjectBean> onProjectClickListener;

  public ProjectsAdapter() {
    super(new ProjectsAdapterDiffUtil());
  }

  @Override
  public ProjectsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int parentType) {
    return new ProjectsAdapterViewHolder(
        LayoutProjectBinding.inflate(LayoutInflater.from(parent.getContext(), parent, false)));
  }

  @Override
  @NonNull
  public void onBindViewHolder(@NonNull ProjectsAdapterViewHolder holder, int position) {
    var item = getItem(position); // project folder
    // try load project data based in folder.
    var project = ProjectManager.getProjectByPath(item);
    assert project != null;
    holder.binding.name.setText(project.basicInfo.name);
    holder.binding.card.setOnClickListener(
        v -> {
          if (onProjectClickListener != null) onProjectClickListener.call(project);
        });
  }

  public void setOnProjectClick(final Listener<ProjectBean> onProjectClickListener) {
    this.onProjectClickListener = onProjectClickListener;
  }

  public static class ProjectsAdapterViewHolder extends RecyclerView.ViewHolder {
    private LayoutProjectBinding binding;

    public ProjectsAdapterViewHolder(@NonNull LayoutProjectBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }

  public static class ProjectsAdapterDiffUtil extends DiffUtil.ItemCallback<File> {
    @Override
    public boolean areItemsTheSame(@NonNull File oldItem, @NonNull File newItem) {
      return oldItem.getName().equals(newItem.getName());
    }

    @Override
    public boolean areContentsTheSame(@NonNull File oldItem, @NonNull File newItem) {
      return oldItem.equals(newItem);
    }
  }
}
