package com.mindbuilders.cognitivemoodlog;

/**
 * Created by Peter on 1/5/2017.
 */

public class emotionobj {
    private String name;
    private int id;
    private int emotioncategoryid;
    private int feelingstrengthBefore;
    private int getFeelingstrengthAfter;
    private int logentryid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmotioncategoryid() {
        return emotioncategoryid;
    }

    public void setEmotioncategoryid(int emotioncategoryid) {
        this.emotioncategoryid = emotioncategoryid;
    }

    public int getFeelingStrengthBefore() {
        return feelingstrengthBefore;
    }

    public void setFeelingstrengthBefore(int feelingstrengthBefore) {
        this.feelingstrengthBefore = feelingstrengthBefore;
    }

    public int getGetFeelingstrengthAfter() {
        return getFeelingstrengthAfter;
    }

    public void setGetFeelingstrengthAfter(int getFeelingstrengthAfter) {
        this.getFeelingstrengthAfter = getFeelingstrengthAfter;
    }

    public int getLogentryid() {
        return logentryid;
    }

    public void setLogentryid(int logentryid) {
        this.logentryid = logentryid;
    }
}
