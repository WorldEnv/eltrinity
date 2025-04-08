package dev.trindadedev.eltrinity.ui.activities.main.project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dev.trindadedev.eltrinity.project.manage.ProjectManager;
import dev.trindadedev.eltrinity.utils.PrintUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectsViewModel extends ViewModel {
  private final MutableLiveData<List<File>> projects = new MutableLiveData<>();

  public final void fetch() {
    var ogFiles = ProjectManager.getProjectsFile().listFiles();
    PrintUtil.print(ogFiles);
    if (ogFiles == null) return;
    projects.setValue(toFiles(Arrays.asList(ogFiles)));
  }

  private final List<File> toFiles(final List<java.io.File> ogFiles) {
    var toReturnList = new ArrayList<File>();
    ogFiles.forEach(
        ogFile -> {
          toReturnList.add(new File(ogFile.getAbsolutePath()));
        });
    return toReturnList;
  }

  public final LiveData<List<File>> getProjects() {
    return projects;
  }
}
