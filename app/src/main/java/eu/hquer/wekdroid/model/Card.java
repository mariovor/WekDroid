package eu.hquer.wekdroid.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mariovor on 03.03.18.
 */

public class Card implements Parcelable {
    String _id;
    String title;
    String description;
    String boardId;
    String listId;
    String swimlaneId;
    Boolean archived;


    protected Card(Parcel in) {
        _id = in.readString();
        title = in.readString();
        description = in.readString();
        boardId = in.readString();
        listId = in.readString();
        swimlaneId = in.readString();
        archived = Boolean.getBoolean(in.readString());
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(boardId);
        parcel.writeString(listId);
        parcel.writeString(swimlaneId);
        if (archived == null) {
            parcel.writeString("false");
        } else {
            parcel.writeString(archived.toString());
        }

    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getSwimlaneId() {
        return swimlaneId;
    }

    public void setSwimlaneId(String swimlaneId) {
        this.swimlaneId = swimlaneId;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }
}
