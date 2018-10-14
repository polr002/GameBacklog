package com.example.roy.gamebacklog;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "gameBacklog")
public class gameObject implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo (name = "title")
    private String title;

    @ColumnInfo (name = "platform")
    private String platform;

    @ColumnInfo (name = "status")
    private String status;

//    @ColumnInfo (name = "dateAdded")
//    private String dateAdded;

    public gameObject(String title, String platform, String status) {

        this.id = id;
        this.title = title;
        this.platform = platform;
        this.status = status;
//        this.dateAdded = dateAdded;
    }

    protected gameObject(Parcel in) {
        id = in.readLong();
        title = in.readString();
        platform = in.readString();
        status = in.readString();
//        dateAdded = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(platform);
        dest.writeString(status);
//        dest.writeString(dateAdded);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<gameObject> CREATOR = new Creator<gameObject>() {
        @Override
        public gameObject createFromParcel(Parcel in) {
            return new gameObject(in);
        }

        @Override
        public gameObject[] newArray(int size) {
            return new gameObject[size];
        }
    };

    public Long getId() {
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

 //   public String getDateAdded() {
   //     return dateAdded;
  //  }
   // public void setDateAdded(String dateAdded) {
  //      this.dateAdded = dateAdded;
   // }
}
