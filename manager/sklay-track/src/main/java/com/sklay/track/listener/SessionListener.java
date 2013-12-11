package com.sklay.track.listener;


import java.util.Date;

import com.sklay.track.model.Session;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-17
 */
public interface SessionListener {

    void sessionBegin(Session session);

    void sessionEnd(String sessionId, Date endAt);
}
