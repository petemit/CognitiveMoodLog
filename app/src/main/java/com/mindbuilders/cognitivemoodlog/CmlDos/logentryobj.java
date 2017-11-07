package com.mindbuilders.cognitivemoodlog.CmlDos;

/**
 * Created by Peter on 1/29/2017.
 */

public class logentryobj {
    private int logid;
    private String timestamp;
    private String situation;

    public int getLogid() {
        return logid;
    }

    public void setLogid(int logid) {
        this.logid = logid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation= situation;
    }

    @Override
    public String toString() {
    String truncated="";
    String results="";
        if (getSituation().length()>37){
            truncated=situation.substring(0,36);
        }
        else{
            truncated=getSituation();
        }
        results=(timestamp+ "  " + truncated);
        return (results);
    }
}
