package com.mindbuilders.cognitivemoodlog;

import java.io.Serializable;

/**
 * Created by Peter on 1/15/2017.
 */

public class thought_cognitivedistortionobj implements Serializable {
    private int thoughtid;
    private int cognitivedistortionid;
    private int id;

    public static String COLUMN_COGNITIVEDISTORTION_ID="cognitivedistortion_id";
    public static String COLUMN_THOUGHT_ID="thought_id";

    public int getThoughtid() {
        return thoughtid;
    }

    public void setThoughtid(int thoughtid) {
        this.thoughtid = thoughtid;
    }

    public int getCognitivedistortionid() {
        return cognitivedistortionid;
    }

    public void setCognitivedistortionid(int cognitivedistortionid) {
        this.cognitivedistortionid = cognitivedistortionid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
