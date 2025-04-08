package dev.trindadedev.eltrinity.ui.activities.runner;

import android.os.Parcel;
import android.os.Parcelable;
import dev.trindadedev.eltrinity.beans.BaseBean;
import dev.trindadedev.eltrinity.beans.ProjectBean;
import dev.trindadedev.eltrinity.utils.ParcelUtil;

public class RunnerState extends BaseBean implements Parcelable {

  public static final Creator<RunnerState> CREATOR =
      new Creator<RunnerState>() {

        public RunnerState createFromParcel(Parcel parcel) {
          return new RunnerState(parcel);
        }

        public RunnerState[] newArray(int i) {
          return new RunnerState[i];
        }
      };

  public ProjectBean project;

  public RunnerState() {}

  public RunnerState(final Parcel parcel) {
    this.project = ParcelUtil.readParcelable(parcel, ProjectBean.class);
  }

  @Override
  public Creator getCreator() {
    return CREATOR;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(final Parcel parcel, int flags) {
    parcel.writeParcelable(project, flags);
  }

  @Override
  public void print() {
    project.print();
  }
}
