package com.sklay.track.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sklay.track.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-14
 */
public interface TrackService {

    Map<String, Serializable> getAppConfigs(App app);

    void boot(String appId, String version, HttpServletRequest request, HttpServletResponse response) throws IOException;

    void collect(String appId, HttpServletRequest request, HttpServletResponse response) throws IOException;

    void outputEmptyGif(HttpServletResponse response) throws IOException;

    String generateClientId(HttpServletResponse response);

    Client trackClient(String clientId, JSONObject data, App app, HttpServletRequest request);

    Session trackSession(String sessionId, String clientId, JSONObject data, App app, HttpServletRequest request);

    String trackEvent(String eventId, String sessionId, String clientId, Integer type, JSONObject data, App app, HttpServletRequest request);

    List<Action> trackActions(String eventId, JSONArray arr, App app, HttpServletRequest request);

    List<ErrorLog> trackErrorLogs(String eventId, JSONArray arr, App app, HttpServletRequest request);
}
