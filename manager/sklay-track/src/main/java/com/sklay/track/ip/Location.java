package com.sklay.track.ip;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 2010-11-26
 */
public class Location {
    public Location(String country, String area) {
        this.country = country;
        this.area = area;
    }

    public String country;
    public String area;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return country + " " + area;
    }
}
