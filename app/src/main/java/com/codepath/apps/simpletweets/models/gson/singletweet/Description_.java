
package com.codepath.apps.simpletweets.models.gson.singletweet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Description_ {

    @SerializedName("urls")
    @Expose
    private List<Url____> urls = new ArrayList<Url____>();

    /**
     * 
     * @return
     *     The urls
     */
    public List<Url____> getUrls() {
        return urls;
    }

    /**
     * 
     * @param urls
     *     The urls
     */
    public void setUrls(List<Url____> urls) {
        this.urls = urls;
    }

}
