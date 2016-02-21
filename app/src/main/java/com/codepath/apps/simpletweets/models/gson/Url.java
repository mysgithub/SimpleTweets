
package com.codepath.apps.simpletweets.models.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Url {

    @SerializedName("urls")
    @Expose
    private List<Url_> urls = new ArrayList<Url_>();

    /**
     * 
     * @return
     *     The urls
     */
    public List<Url_> getUrls() {
        return urls;
    }

    /**
     * 
     * @param urls
     *     The urls
     */
    public void setUrls(List<Url_> urls) {
        this.urls = urls;
    }

}
