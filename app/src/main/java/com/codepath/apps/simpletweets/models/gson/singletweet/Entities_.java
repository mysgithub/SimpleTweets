
package com.codepath.apps.simpletweets.models.gson.singletweet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

@Generated("org.jsonschema2pojo")
public class Entities_ {

    @SerializedName("url")
    @Expose
    private Url__ url;
    @SerializedName("description")
    @Expose
    private Description_ description;

    /**
     * 
     * @return
     *     The url
     */
    public Url__ getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(Url__ url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The description
     */
    public Description_ getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(Description_ description) {
        this.description = description;
    }

}
