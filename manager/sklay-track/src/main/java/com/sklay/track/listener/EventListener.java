package com.sklay.track.listener;


import java.util.Date;

import com.sklay.track.model.Event;
import com.sklay.track.model.Session;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-17
 */
public interface EventListener {

    void eventBegin(Event event);

    void eventRefresh(String eventId, String sessionId);

    void eventEnd(String eventId, Date endAt, String sessionId);
}
