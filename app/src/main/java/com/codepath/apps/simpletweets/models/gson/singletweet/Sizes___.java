
package com.codepath.apps.simpletweets.models.gson.singletweet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

@Generated("org.jsonschema2pojo")
public class Sizes___ {

    @SerializedName("medium")
    @Expose
    private Medium_______ medium;
    @SerializedName("thumb")
    @Expose
    private Thumb___ thumb;
    @SerializedName("large")
    @Expose
    private Large___ large;
    @SerializedName("small")
    @Expose
    private Small___ small;

    /**
     * 
     * @return
     *     The medium
     */
    public Medium_______ getMedium() {
        return medium;
    }

    /**
     * 
     * @param medium
     *     The medium
     */
    public void setMedium(Medium_______ medium) {
        this.medium = medium;
    }

    /**
     * 
     * @return
     *     The thumb
     */
    public Thumb___ getThumb() {
        return thumb;
    }

    /**
     * 
     * @param thumb
     *     The thumb
     */
    public void setThumb(Thumb___ thumb) {
        this.thumb = thumb;
    }

    /**
     * 
     * @return
     *     The large
     */
    public Large___ getLarge() {
        return large;
    }

    /**
     * 
     * @param large
     *     The large
     */
    public void setLarge(Large___ large) {
        this.large = large;
    }

    /**
     * 
     * @return
     *     The small
     */
    public Small___ getSmall() {
        return small;
    }

    /**
     * 
     * @param small
     *     The small
     */
    public void setSmall(Small___ small) {
        this.small = small;
    }

}
