package com.mindbuilders.cognitivemoodlog;

import android.provider.BaseColumns;

import java.security.Timestamp;
import java.util.Date;

/**
 * Created by Peter on 12/30/2016.
 */

public class CogMoodLogDatabaseContract {

    public static final class cognitivedistortion implements BaseColumns{
        public static String TABLE_NAME="cognitivedistortion";
        public static String COLUMN_COGID="cogid";
        public static String COLUMN_NAME="name";
        public static String COLUMN_DESCRIPTION="description";
    }

    public static final class emotion implements BaseColumns {
        public static String TABLE_NAME="emotion";
        public static String COLUMN_EMOID="emoid";
        public static String COLUMN_NAME="name";
        public static String COLUMN_EMOTIONCATEGORY_ID="emotioncategory_id";
    }

    public static final class  emotion_logentry_belief implements BaseColumns{
        public static String TABLE_NAME = "emotion_logentry_belief";
        public static String COLUMN_EMOTION_ID="emotion_id";
        public static String COLUMN_LOGENTRY_ID="logentry_id";
        public static String COLUMN_BELIEFBEFORE="beliefbefore";
        public static String COLUMN_BELIEFAFTER="beliefafter";

    }

    public static final class emotioncategory implements  BaseColumns {
        public static String TABLE_NAME = "emotioncategory";
        public static String COLUMN_EMOCATID="emocatid";
        public static String COLUMN_NAME ="name";
    }

    public static final class logentry implements BaseColumns {
        public static String TABLE_NAME="logentry";
        public static String COLUMN_USER_ID="user_id";
        public static String COLUMN_LOGENTRY="logentry";
        public static String COLUMN_ISFINISHED="isfinished";
        public static String COLUMN_TIMESTAMP = "timestamp";
    }

    public static final class thought implements BaseColumns {
        public static String TABLE_NAME = "thought";
        public static String COLUMN_LOGENTRY_ID = "logentry_id";
        public static String COLUMN_NEGATIVETHOUGHT = "negativethought";
        public static String COLUMN_NEGATIVEBELIEFBEFORE ="negativebeliefbefore";
        public static String COLUMN_NEGATIVEBELIEFAFTER ="negativebeliefafter";
        public static String COLUMN_POSITIVETHOUGHT = "positivethought";
        public static String COLUMN_POSITIVEBELIEFBEFORE ="positivebeliefbefore";
        public static String COLUMN_POSITIVEBELIEFAFTER ="positivebeliefafter";
    }

    public static final class thought_cognitivedistortion implements BaseColumns {
        public static String TABLE_NAME="thought_cognitivedistortion";
        public static String COLUMN_COGNITIVEDISTORTION_ID="cognitivedistortion_id";
        public static String COLUMN_THOUGHT_ID="thought_id";
    }

    public static final class troubleshootingguidelines implements BaseColumns{
        public static String TABLE_NAME="troubleshootingguidelines";
        public static String COLUMN_TROUBLESHOOTID="troubleshootid";
        public static String COLUMN_QUESTION="question";
        public static String COLUMN_EXPLANATION="explanation";
    }

}
