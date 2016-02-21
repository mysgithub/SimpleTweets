
package com.codepath.apps.simpletweets.models.gson.singletweet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Url {

    @SerializedName("urls")
    @Expose
    private List<com.codepath.apps.simpletweets.models.gson.Url_> urls = new ArrayList<com.codepath.apps.simpletweets.models.gson.Url_>();

    /**
     * 
     * @return
     *     The urls
     */
    public List<com.codepath.apps.simpletweets.models.gson.Url_> getUrls() {
        return urls;
    }

    /**
     * 
     * @param urls
     *     The urls
     */
    public void setUrls(List<com.codepath.apps.simpletweets.models.gson.Url_> urls) {
        this.urls = urls;
    }

}
