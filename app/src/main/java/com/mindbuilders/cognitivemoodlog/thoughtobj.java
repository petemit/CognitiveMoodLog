package com.mindbuilders.cognitivemoodlog;

/**
 * Created by Peter on 1/5/2017.
 */

public class thoughtobj {
    private int id;
    private String negativethought;
    private int negativebeliefBefore;
    private int negativebeliefAfter;
    private String positivethought;
    private int positivebeliefbefore;
    private int positivebeliefafter;
    private boolean isadded=false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNegativethought() {
        return negativethought;
    }

    public void setNegativethought(String negativethought) {
        this.negativethought = negativethought;
    }

    public int getNegativebeliefBefore() {
        return negativebeliefBefore;
    }

    public void setNegativebeliefBefore(int negativebeliefBefore) {
        this.negativebeliefBefore = negativebeliefBefore;
    }

    public int getNegativebeliefAfter() {
        return negativebeliefAfter;
    }

    public void setNegativebeliefAfter(int negativebeliefAfter) {
        this.negativebeliefAfter = negativebeliefAfter;
    }

    public String getPositivethought() {
        return positivethought;
    }

    public void setPositivethought(String positivethought) {
        this.positivethought = positivethought;
    }

    public int getPositivebeliefbefore() {
        return positivebeliefbefore;
    }

    public void setPositivebeliefbefore(int positivebeliefbefore) {
        this.positivebeliefbefore = positivebeliefbefore;
    }

    public int getPositivebeliefafter() {
        return positivebeliefafter;
    }

    public void setPositivebeliefafter(int positivebeliefafter) {
        this.positivebeliefafter = positivebeliefafter;
    }

    public boolean isadded() {
        return isadded;
    }

    public void setIsadded(boolean isadded) {
        this.isadded = isadded;
    }
}
