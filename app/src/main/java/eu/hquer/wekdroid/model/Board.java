package eu.hquer.wekdroid.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mariovor on 23.02.18.
 */

public class Board implements Parcelable{
    String _id;
    String title;

    protected Board(Parcel in) {
        _id = in.readString();
        title = in.readString();
    }

    public static final Creator<Board> CREATOR = new Creator<Board>() {
        @Override
        public Board createFromParcel(Parcel in) {
            return new Board(in);
        }

        @Override
        public Board[] newArray(int size) {
            return new Board[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(title);
    }
}
