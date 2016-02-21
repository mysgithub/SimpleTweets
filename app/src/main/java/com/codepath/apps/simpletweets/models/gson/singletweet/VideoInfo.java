
package com.codepath.apps.simpletweets.models.gson.singletweet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class VideoInfo {

    @SerializedName("aspect_ratio")
    @Expose
    private List<Long> aspectRatio = new ArrayList<Long>();
    @SerializedName("duration_millis")
    @Expose
    private Long durationMillis;
    @SerializedName("variants")
    @Expose
    private List<Variant> variants = new ArrayList<Variant>();

    /**
     * 
     * @return
     *     The aspectRatio
     */
    public List<Long> getAspectRatio() {
        return aspectRatio;
    }

    /**
     * 
     * @param aspectRatio
     *     The aspect_ratio
     */
    public void setAspectRatio(List<Long> aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    /**
     * 
     * @return
     *     The durationMillis
     */
    public Long getDurationMillis() {
        return durationMillis;
    }

    /**
     * 
     * @param durationMillis
     *     The duration_millis
     */
    public void setDurationMillis(Long durationMillis) {
        this.durationMillis = durationMillis;
    }

    /**
     * 
     * @return
     *     The variants
     */
    public List<Variant> getVariants() {
        return variants;
    }

    /**
     * 
     * @param variants
     *     The variants
     */
    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }

}
