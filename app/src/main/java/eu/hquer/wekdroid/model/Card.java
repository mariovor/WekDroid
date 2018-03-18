package eu.hquer.wekdroid.model;

import android.os.Parcel;

/**
 * Created by mariovor on 03.03.18.
 */

public class Card extends Board{
    String description;

    protected Card(Parcel in) {
        super(in);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
