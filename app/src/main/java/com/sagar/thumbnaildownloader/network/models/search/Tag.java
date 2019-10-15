package com.sagar.thumbnaildownloader.network.models.search;

/**
 * Created by SAGAR MAHOBIA on 19-Nov-18. at 13:39
 */
public class Tag {
    private String tag;
    private boolean checked;


    public Tag(String tag) {
        this.tag = tag;
        checked = true;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
