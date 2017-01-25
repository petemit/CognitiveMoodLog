package com.mindbuilders.cognitivemoodlog;

import java.io.Serializable;

/**
 * Created by Peter on 1/5/2017.
 */

public class thoughtobj implements Serializable {
    private int id;
    private String negativethought;
    private int negativebeliefBefore;
    private int negativebeliefAfter;
    private String positivethought;
    private int positivebeliefbefore;
    private int positivebeliefafter;
    private boolean isaddedToCogPicker=false;
    private boolean isaddedToPosThoughtAdd=false;
    private boolean isnegthoughtreviewdone=false;

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

    public boolean getisIsaddedToCogPicker() {return isaddedToCogPicker;
    }

    public void setisIsaddedToCogPicker(boolean isadded) {
        this.isaddedToCogPicker = isadded;
    }

    public boolean getIsIsaddedToPosThoughtAdd() {
        return isaddedToPosThoughtAdd;
    }

    public void setIsIsaddedToPosThoughtAdd(boolean isadded) {this.isaddedToPosThoughtAdd = isadded;
    }

    public boolean getIsNegThoughtReviewDone() {
        return isnegthoughtreviewdone;
    }

    public void setIsNegThoughtReviewDone(boolean isnegthoughtreviewdone) {
        this.isnegthoughtreviewdone = isnegthoughtreviewdone;
    }
}
