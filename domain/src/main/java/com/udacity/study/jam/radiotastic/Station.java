/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic;

public class Station {
    private int id;
    private int status;
    private String urlid;
    private String name;
    private String website;
    private String streamurl;
    private String description;
    private String country;
    private String bitrate;

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getUrlid() {
        return urlid;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public String getStreamurl() {
        return streamurl;
    }

    public String getDescription() {
        return description;
    }

    public String getCountry() {
        return country;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUrlid(String urlid) {
        this.urlid = urlid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setStreamurl(String streamurl) {
        this.streamurl = streamurl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        if (id != station.id) return false;
        if (status != station.status) return false;
        if (bitrate != null ? !bitrate.equals(station.bitrate) : station.bitrate != null)
            return false;
        if (country != null ? !country.equals(station.country) : station.country != null)
            return false;
        if (description != null ? !description.equals(station.description) : station.description != null)
            return false;
        if (name != null ? !name.equals(station.name) : station.name != null) return false;
        if (streamurl != null ? !streamurl.equals(station.streamurl) : station.streamurl != null)
            return false;
        if (urlid != null ? !urlid.equals(station.urlid) : station.urlid != null) return false;
        if (website != null ? !website.equals(station.website) : station.website != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + status;
        result = 31 * result + (urlid != null ? urlid.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (streamurl != null ? streamurl.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (bitrate != null ? bitrate.hashCode() : 0);
        return result;
    }
}
