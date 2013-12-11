package com.sklay.track.service;


import com.sklay.track.ip.Location;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 2010-11-26
 */
public interface LocationService {
    Location getLocation(String ip);
}
