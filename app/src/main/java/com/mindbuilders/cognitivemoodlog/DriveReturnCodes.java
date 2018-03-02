package com.mindbuilders.cognitivemoodlog;

/**
 * Created by Peter on 3/1/2018.
 */

public class DriveReturnCodes {
    public static int ERROR = -1;
    public static int EMPTYSET = 404;
    public static int FOUNDFILE = 100;
    public static int NOFILEFOUND = 101;
    public static int FILESDELETED = 102;
    public static int BACKUPRESTORED = 103;
    public static int DBBACKEDUP = 104;

    public static String getMessage(int code){
        switch(code) {
            case -1:
                return "An error occurred";
            case 404:
                return "No data found";
            case 100:
                return "Found a Match";
            case 101:
                return "No File Found";
            case 102:
                return "Files Deleted";
            case 103:
                return "Backup Restored";
            case 104:
                return "Database Backed Up";
            default:
                return "No message match";
        }
    }

}
