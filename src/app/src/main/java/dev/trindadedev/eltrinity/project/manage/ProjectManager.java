package dev.trindadedev.eltrinity.project.manage;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.reflect.TypeToken;
import dev.trindadedev.eltrinity.ELTrinity;
import dev.trindadedev.eltrinity.base.Contextualizable;
import dev.trindadedev.eltrinity.beans.ProjectBean;
import dev.trindadedev.eltrinity.beans.ProjectBasicInfoBean;
import dev.trindadedev.eltrinity.utils.FileUtil;
import dev.trindadedev.eltrinity.utils.GsonUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectManager extends Contextualizable {
  private String path;

  public ProjectManager(@NonNull final Context context) {
    this(context, null);
  }

  public ProjectManager(@NonNull final Context context, final String scId) {
    super(context);
    this.path = path;
  }

  @Nullable
  public final ProjectBean getCurrentProject() {
    return getProjectByPath(path);
  }

  /**
   * Creates and returns a ProjectBean based in files by scId
   *
   * @param path the source path.
   */
  @Nullable
  public static final ProjectBean getProjectByPath(final String path) {
    var name = path.substring(path.lastIndexOf("."));
    var project = new ProjectBean();

    var projectBasicInfoFileJsonType =
      new TypeToken<ProjectBasicInfoBean>() {}.getType();
    var projectBasicInfoJsonContent =
      FileUtil.readFile(getBasicInfoFile(name));
    var projectBasicInfo =
      GsonUtil.getGson().fromJson(projectBasicInfoJsonContent, projectBasicInfoFileJsonType);

    project.basicInfo = projectBasicInfo;
    return project;
  }

  /**
   * Creates nescessary files of project
   *
   * @param project The instance of ProjectBean with data to be created.
   */
  public static final void createProjectByBean(@NonNull final ProjectBean project) {
    final var projectPath = new File(getProjectsFile(), projectName);
    final var projectBasicInfo = project.basicInfo;
    final var projectBasicInfoJson = GsonUtil.getGson().toJson(projectBasicInfo);
    FileUtil.makeDir(projectPath);
    FileUtil.writeFile(getBasicInfoFile(projectBasicInfo.name), projectBasicInfoJson);
  }

  /** Folder where all projects are stored */
  public static final File getProjectsFile() {
    return new File(ELTrinity.getPublicFolderFile(), "projects/");
  }

  /** The file where basic info of project are stored, like name, packageName */
  public static final File getBasicInfoFile(final String name) {
    return new File(getProjectsFile(), name + "/basic_info.json");
  }
}
