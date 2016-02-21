
package com.codepath.apps.simpletweets.models.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

@Generated("org.jsonschema2pojo")
public class Status {

    @SerializedName("contributors")
    @Expose
    private Object contributors;
    @SerializedName("coordinates")
    @Expose
    private Coordinates coordinates;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("favorited")
    @Expose
    private Boolean favorited;
    @SerializedName("geo")
    @Expose
    private Geo geo;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("id_str")
    @Expose
    private String idStr;
    @SerializedName("in_reply_to_screen_name")
    @Expose
    private String inReplyToScreenName;
    @SerializedName("in_reply_to_status_id")
    @Expose
    private Long inReplyToStatusId;
    @SerializedName("in_reply_to_status_id_str")
    @Expose
    private String inReplyToStatusIdStr;
    @SerializedName("in_reply_to_user_id")
    @Expose
    private Long inReplyToUserId;
    @SerializedName("in_reply_to_user_id_str")
    @Expose
    private String inReplyToUserIdStr;
    @SerializedName("place")
    @Expose
    private Place place;
    @SerializedName("retweet_count")
    @Expose
    private Long retweetCount;
    @SerializedName("retweeted")
    @Expose
    private Boolean retweeted;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("truncated")
    @Expose
    private Boolean truncated;

    /**
     * 
     * @return
     *     The contributors
     */
    public Object getContributors() {
        return contributors;
    }

    /**
     * 
     * @param contributors
     *     The contributors
     */
    public void setContributors(Object contributors) {
        this.contributors = contributors;
    }

    /**
     * 
     * @return
     *     The coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * 
     * @param coordinates
     *     The coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The favorited
     */
    public Boolean getFavorited() {
        return favorited;
    }

    /**
     * 
     * @param favorited
     *     The favorited
     */
    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    /**
     * 
     * @return
     *     The geo
     */
    public Geo getGeo() {
        return geo;
    }

    /**
     * 
     * @param geo
     *     The geo
     */
    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The idStr
     */
    public String getIdStr() {
        return idStr;
    }

    /**
     * 
     * @param idStr
     *     The id_str
     */
    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    /**
     * 
     * @return
     *     The inReplyToScreenName
     */
    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    /**
     * 
     * @param inReplyToScreenName
     *     The in_reply_to_screen_name
     */
    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    /**
     * 
     * @return
     *     The inReplyToStatusId
     */
    public Long getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    /**
     * 
     * @param inReplyToStatusId
     *     The in_reply_to_status_id
     */
    public void setInReplyToStatusId(Long inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    /**
     * 
     * @return
     *     The inReplyToStatusIdStr
     */
    public String getInReplyToStatusIdStr() {
        return inReplyToStatusIdStr;
    }

    /**
     * 
     * @param inReplyToStatusIdStr
     *     The in_reply_to_status_id_str
     */
    public void setInReplyToStatusIdStr(String inReplyToStatusIdStr) {
        this.inReplyToStatusIdStr = inReplyToStatusIdStr;
    }

    /**
     * 
     * @return
     *     The inReplyToUserId
     */
    public Long getInReplyToUserId() {
        return inReplyToUserId;
    }

    /**
     * 
     * @param inReplyToUserId
     *     The in_reply_to_user_id
     */
    public void setInReplyToUserId(Long inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    /**
     * 
     * @return
     *     The inReplyToUserIdStr
     */
    public String getInReplyToUserIdStr() {
        return inReplyToUserIdStr;
    }

    /**
     * 
     * @param inReplyToUserIdStr
     *     The in_reply_to_user_id_str
     */
    public void setInReplyToUserIdStr(String inReplyToUserIdStr) {
        this.inReplyToUserIdStr = inReplyToUserIdStr;
    }

    /**
     * 
     * @return
     *     The place
     */
    public Place getPlace() {
        return place;
    }

    /**
     * 
     * @param place
     *     The place
     */
    public void setPlace(Place place) {
        this.place = place;
    }

    /**
     * 
     * @return
     *     The retweetCount
     */
    public Long getRetweetCount() {
        return retweetCount;
    }

    /**
     * 
     * @param retweetCount
     *     The retweet_count
     */
    public void setRetweetCount(Long retweetCount) {
        this.retweetCount = retweetCount;
    }

    /**
     * 
     * @return
     *     The retweeted
     */
    public Boolean getRetweeted() {
        return retweeted;
    }

    /**
     * 
     * @param retweeted
     *     The retweeted
     */
    public void setRetweeted(Boolean retweeted) {
        this.retweeted = retweeted;
    }

    /**
     * 
     * @return
     *     The source
     */
    public String getSource() {
        return source;
    }

    /**
     * 
     * @param source
     *     The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 
     * @return
     *     The truncated
     */
    public Boolean getTruncated() {
        return truncated;
    }

    /**
     * 
     * @param truncated
     *     The truncated
     */
    public void setTruncated(Boolean truncated) {
        this.truncated = truncated;
    }

}
