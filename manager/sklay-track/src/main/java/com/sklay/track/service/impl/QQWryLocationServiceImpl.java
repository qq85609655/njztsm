package com.sklay.track.service.impl;

import java.io.IOException;

import org.springframework.core.io.Resource;


import com.sklay.core.ex.SklayException;
import com.sklay.track.ip.IPSeeker;
import com.sklay.track.ip.Location;
import com.sklay.track.service.LocationService;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 2010-11-26
 */
public class QQWryLocationServiceImpl implements LocationService {
    private Resource ipFile;
    private IPSeeker ipSeeker;

    public void setIpFile(Resource ipFile) {
        this.ipFile = ipFile;
    }

    public Location getLocation(String ip) {
        if (ipSeeker == null) {
            try {
                ipSeeker = new IPSeeker(ipFile.getFile());
            } catch (IOException e) {
                throw new SklayException(e);
            }
        }
        String country = ipSeeker.getCountry(ip);
        //if (country == null || "本机地址".equals(country) || country.contains("局域网")) {
        //    return null;
        //}
        return new Location(country, ipSeeker.getArea(ip));
    }
}
