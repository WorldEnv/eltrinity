package dev.trindadedev.eltrinity.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import dev.trindadedev.eltrinity.utils.PrintUtil;

public class ProjectBean extends BaseBean implements Parcelable {
  public static final Creator<ProjectBean> CREATOR =
      new Creator<ProjectBean>() {
        public ProjectBean createFromParcel(Parcel parcel) {
          return new ProjectBean(parcel);
        }

        public ProjectBean[] newArray(int size) {
          return new ProjectBean[size];
        }
      };

  public String name;

  public ProjectBean() {
    name = "Project";
  }

  public ProjectBean(final Parcel parcel) {
    name = parcel.readString();
  }

  public void copy(final ProjectBean other) {
    name = other.name;
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
  public void print() {
    PrintUtil.print(name);
  }

  @Override
  public void writeToParcel(final Parcel parcel, final int flags) {
    parcel.writeString(name);
  }
}
