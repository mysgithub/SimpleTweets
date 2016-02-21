
package com.codepath.apps.simpletweets.models.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Entities {

    @SerializedName("urls")
    @Expose
    private List<Object> urls = new ArrayList<Object>();
    @SerializedName("hashtags")
    @Expose
    private List<Hashtag> hashtags = new ArrayList<Hashtag>();
    @SerializedName("user_mentions")
    @Expose
    private List<Object> userMentions = new ArrayList<Object>();

    /**
     * 
     * @return
     *     The urls
     */
    public List<Object> getUrls() {
        return urls;
    }

    /**
     * 
     * @param urls
     *     The urls
     */
    public void setUrls(List<Object> urls) {
        this.urls = urls;
    }

    /**
     * 
     * @return
     *     The hashtags
     */
    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    /**
     * 
     * @param hashtags
     *     The hashtags
     */
    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    /**
     * 
     * @return
     *     The userMentions
     */
    public List<Object> getUserMentions() {
        return userMentions;
    }

    /**
     * 
     * @param userMentions
     *     The user_mentions
     */
    public void setUserMentions(List<Object> userMentions) {
        this.userMentions = userMentions;
    }

}
