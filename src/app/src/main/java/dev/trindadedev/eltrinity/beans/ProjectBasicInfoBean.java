package dev.trindadedev.eltrinity.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import dev.trindadedev.eltrinity.utils.PrintUtil;
import java.util.ArrayList;

public class ProjectBasicInfoBean extends BaseBean implements Parcelable {
  public static final Creator<ProjectBasicInfoBean> CREATOR =
      new Creator<ProjectBasicInfoBean>() {
        public ProjectBasicInfoBean createFromParcel(Parcel parcel) {
          return new ProjectBasicInfoBean(parcel);
        }

        public ProjectBasicInfoBean[] newArray(int size) {
          return new ProjectBasicInfoBean[size];
        }
      };

  @SerializedName("project_name")
  public String name;

  @SerializedName("project_language")
  public String language;

  @SerializedName("project_files")
  public ArrayList<String> files;

  @SerializedName("project_description")
  public String description;

  @SerializedName("project_author_name")
  public String authorName;

  @SerializedName("project_author_user_name")
  public String authorUserName;

  public ProjectBasicInfoBean() {}

  public ProjectBasicInfoBean(final Parcel parcel) {
    name = parcel.readString();
    language = parcel.readString();
    files = (ArrayList) parcel.readSerializable();
    description = parcel.readString();
    authorName = parcel.readString();
    authorUserName = parcel.readString();
  }

  public void copy(final ProjectBasicInfoBean other) {
    name = other.name;
    language = other.language;
    files = other.files;
    description = other.description;
    authorName = other.authorName;
    authorUserName = other.authorUserName;
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
    PrintUtil.print(language);
    PrintUtil.print(files);
    PrintUtil.print(description);
    PrintUtil.print(authorName);
    PrintUtil.print(authorUserName);
  }

  @Override
  public void writeToParcel(final Parcel parcel, final int flags) {
    parcel.writeString(name);
    parcel.writeString(language);
    parcel.writeSerializable(files);
    parcel.writeString(description);
    parcel.writeString(authorName);
    parcel.writeString(authorUserName);
  }
}
