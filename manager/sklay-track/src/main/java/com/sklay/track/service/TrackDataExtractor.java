package com.sklay.track.service;

import com.alibaba.fastjson.JSONObject;
import com.sklay.track.model.*;

import javax.servlet.http.HttpServletRequest;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-14
 */
public interface TrackDataExtractor {
    Client extractClient(Client client, JSONObject json, App app, HttpServletRequest request);

    Session extractSession(Session session, JSONObject json, App app, HttpServletRequest request);

    Event extractEvent(Event event, JSONObject json, App app, HttpServletRequest request);

    Action extractAction(Action action, JSONObject json, App app, HttpServletRequest request);

    ErrorLog extractErrorLog(ErrorLog errorLog, JSONObject json, App app, HttpServletRequest request);
}
