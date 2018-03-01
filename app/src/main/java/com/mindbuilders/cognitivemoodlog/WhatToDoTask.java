package com.mindbuilders.cognitivemoodlog;

import android.app.Activity;

/**
 * Created by Peter on 2/28/2018.
 */

public class WhatToDoTask {
    private Activity activity;
    private String op;

    public WhatToDoTask(Activity activity, String op) {
        this.op = op;
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
}
