
package com.codepath.apps.simpletweets.models.gson.singletweet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Url__ {

    @SerializedName("urls")
    @Expose
    private List<Url___> urls = new ArrayList<Url___>();

    /**
     * 
     * @return
     *     The urls
     */
    public List<Url___> getUrls() {
        return urls;
    }

    /**
     * 
     * @param urls
     *     The urls
     */
    public void setUrls(List<Url___> urls) {
        this.urls = urls;
    }

}
