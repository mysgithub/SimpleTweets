
package com.codepath.apps.simpletweets.models.gson.singletweet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

@Generated("org.jsonschema2pojo")
public class Entities {

    @SerializedName("url")
    @Expose
    private Url url;
    @SerializedName("description")
    @Expose
    private com.codepath.apps.simpletweets.models.gson.Description description;

    /**
     * 
     * @return
     *     The url
     */
    public Url getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(Url url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The description
     */
    public com.codepath.apps.simpletweets.models.gson.Description getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(com.codepath.apps.simpletweets.models.gson.Description description) {
        this.description = description;
    }

}
