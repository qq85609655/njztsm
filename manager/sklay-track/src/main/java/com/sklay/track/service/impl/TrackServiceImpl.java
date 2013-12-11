package com.sklay.track.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sklay.core.ex.SklayException;
import com.sklay.track.Constants;
import com.sklay.track.model.*;
import com.sklay.track.service.AppService;
import com.sklay.track.service.TrackDataExtractor;
import com.sklay.track.service.TrackDataService;
import com.sklay.track.service.TrackService;
import com.sklay.track.util.Helper;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

/**
 * 
 *  .
 * <p/>
 *
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-8-9
 */
public class TrackServiceImpl implements ServletContextAware, InitializingBean, TrackService {
    private static final Logger LOG = LoggerFactory.getLogger(TrackServiceImpl.class);
    public static final String JS_CONTENT_TYPE = "application/x-javascript; charset=utf-8";
    private static long LAST_MODIFIED = (System.currentTimeMillis() / 1000l) * 1000l;

    private byte[] emptyGif;
    private Map<String, String> jss = new HashMap<String, String>();
    private ServletContext servletContext;

    private boolean debug;
    private String cookieDomain;
    private Map<String, String> configs;
    @Autowired
    private AppService appService;
    @Autowired
    private TrackDataService trackDataService;
    @Autowired
    private TrackDataExtractor trackDataExtractor;

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    public void setConfigs(Map<String, String> configs) {
        this.configs = configs;
    }

    public Map<String, Serializable> getAppConfigs(App app) {
        Map<String, Serializable> configs = new HashMap<String, Serializable>(this.configs);
        configs.putAll(app.getConfigs());
        configs.put("appId", app.getId());
        if (debug) {
            configs.put("debug", true);
        }
        if (app.getSessionTimeout() > 0) {
            configs.put(Constants.SESSION_TIMEOUT, app.getSessionTimeout());
        }
        String domain = app.getAttr(Constants.SESSION_DOMAIN);
        if (StringUtils.isNotEmpty(domain)) {
            configs.put(Constants.SESSION_DOMAIN, domain);
        }
        return configs;
    }

    @Override
    public void boot(String appId, String version, HttpServletRequest request, HttpServletResponse response) throws IOException {
        App app;
        try {
            app = appService.getApp(appId);
        } catch (Exception e) {
            response.setContentType(JS_CONTENT_TYPE);
            response.getWriter().write("//app [" + appId + "] not found or disabled\n");
            return;
        }
        Map<String, Serializable> configs = getAppConfigs(app);

        String clientId = Helper.getCookie(Constants.COOKIE_CLIENT_ID, request);
        boolean tracked = false;
        if (clientId != null) {
            configs.put("tracked", tracked = true);
            configs.put("clientId", clientId);
        } else {
            configs.put("clientId", generateClientId(response));
        }

        long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        if (tracked && LAST_MODIFIED == ifModifiedSince) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }

        response.setContentType(JS_CONTENT_TYPE);
        response.setHeader("Cache-Control", "private, no-cache, no-cache=Set-Cookie, proxy-revalidate");
        response.setDateHeader("Expires", 0l);

        PrintWriter writer = response.getWriter();
        writer.write("var _atk=_atk||[];_atk.push(['cfs'," + JSON.toJSONString(configs) + "]);");
        String customJs = app.getAttr("customJs");
        if (StringUtils.isNotEmpty(customJs)) {
            writer.write(customJs+";");
        }
        if (debug) {
            writer.write(IOUtils.toString(servletContext.getResourceAsStream("/WEB-INF/js/boot-" + version + ".js"), Constants.CHARSET));
        } else {
            if (tracked) {
                response.setDateHeader("Last-Modified", LAST_MODIFIED);
            }
            String body = jss.get(version);
            if (body == null) {
                try {
                    body = IOUtils.toString(servletContext.getResourceAsStream("/static/js/boot-" + version + ".js"), Constants.CHARSET);
                } catch (IOException e) {
                    body = "//version [" + version + "] not found\n";
                }
                jss.put(version, body);
            }
            writer.write(body);
        }
        writer.write("_atk=new Atk().init();");
    }

    @Override
    public void collect(String appId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Helper.setupTs(request);
        App app = appService.getApp(appId);
        JSONObject data = JSON.parseObject(request.getParameter(Constants.DATA));
        if (data == null) {
            return;
        }
        String clientId = data.getString(Constants.CLIENT_ID);
        String sessionId = data.getString(Constants.SESSION_ID);
        String eventId = data.getString(Constants.EVENT_ID);
        int type = data.getIntValue(Constants.TYPE);

        if (type == Event.EVENT_BEGIN) {
            if (clientId == null) {
                clientId = Helper.getCookie(Constants.COOKIE_CLIENT_ID, request);
            }

            JSONObject clientJSON = data.getJSONObject("client");
            if (clientJSON != null) {
                clientJSON.put(Constants.IS_NEW, true);
                trackClient(clientId, clientJSON, app, request);
            }

            JSONObject sessionJSON = data.getJSONObject("session");
            if (sessionJSON != null) {
                sessionJSON.put(Constants.IS_NEW, true);
                trackSession(sessionId, clientId, sessionJSON, app, request);
            }
        } else if (type == Event.EVENT_END) {
            Integer idle = data.getInteger("idle");
            if (idle != null) {
                trackDataService.incrIdleSecond(eventId, idle);
            }
        }

        JSONObject eventJSON = data.getJSONObject("e");
        JSONObject outerCustomsJSON = data.getJSONObject(Constants.CUSTOMS_KEY);
        if (MapUtils.isNotEmpty(outerCustomsJSON)) {
            if (eventJSON == null) {
                eventJSON = new JSONObject();
                eventJSON.put(Constants.CUSTOMS_KEY, outerCustomsJSON);
            } else {
                JSONObject customsJSON = eventJSON.getJSONObject(Constants.CUSTOMS_KEY);
                if (customsJSON == null) {
                    eventJSON.put(Constants.CUSTOMS_KEY, outerCustomsJSON);
                } else {
                    customsJSON.putAll(outerCustomsJSON);
                }
            }
        }
        trackEvent(eventId, sessionId, clientId, type, eventJSON, app, request);

        trackActions(eventId, data.getJSONArray("as"), app, request);
        trackErrorLogs(eventId, data.getJSONArray("es"), app, request);
    }

    public String generateClientId(HttpServletResponse response) {
        String clientId = Helper.uuid();
        Cookie cookie = new Cookie(Constants.COOKIE_CLIENT_ID, clientId);
        if (cookieDomain != null) {
            cookie.setDomain(cookieDomain);
        }
        cookie.setMaxAge(24 * 60 * 60 * 365 * 10);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setHeader("P3P", Constants.P3P);
        return clientId;
    }

    private boolean isOnlyId(JSONObject data) {
        return data.size() == 1 && data.containsKey(Constants.ID);
    }

    private boolean isNew(String id, JSONObject json) {
        return StringUtils.isEmpty(id) || json.getBooleanValue(Constants.IS_NEW);
    }

    private String assertNotEmpty(String key, String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("this String argument [" + key + "] must have length; it must not be null or empty");
        }
        return value;
    }

    public Client trackClient(String clientId, JSONObject data, App app, HttpServletRequest request) {
        Client client;
        if (isNew(clientId, data)) {
            client = new Client();
            client.setId(clientId);
        } else {
            try {
                client = trackDataService.getClient(clientId);
            } catch (SklayException e) {
                client = new Client();
                client.setId(clientId);
                trackDataService.saveClient(client);
            }
            if (data.isEmpty() || isOnlyId(data)) {
                return client;
            }
        }

        return trackDataService.saveClient(trackDataExtractor.extractClient(client, data, app, request));
    }

    public Session trackSession(String sessionId, String clientId, JSONObject data, App app, HttpServletRequest request) {
        Session session;
        if (isNew(sessionId, data)) {
            session = new Session();
            session.setId(sessionId);
            session.setClientId(assertNotEmpty("clientId", clientId));
        } else {
            try {
                session = trackDataService.getSession(sessionId);
            } catch (SklayException e) {
                session = new Session();
                session.setId(sessionId);
                session.setClientId(clientId);
                trackDataService.saveSession(session);
            }
            if (isOnlyId(data)) {
                return session;
            }
        }
        return trackDataService.saveSession(trackDataExtractor.extractSession(session, data, app, request));
    }

    public String trackEvent(String eventId, String sessionId, String clientId, Integer type, JSONObject data, App app, HttpServletRequest request) {
        Event event;
        if (type == null) {
            if (isNew(eventId, data)) {
                type = Event.EVENT_BEGIN;
            } else {
                return eventId;//非新建,则直接返回id
            }
        }
        switch (type) {
            case Event.EVENT_BEGIN:
                event = new Event();
                event.setId(eventId);
                event.setAppId(app.getId());
                event.setSessionId(assertNotEmpty("sessionId", sessionId));
                if (clientId == null) {
                    clientId = trackDataService.getSession(sessionId).getClientId();
                }
                event.setClientId(clientId);
                event.setFirstView(trackDataService.isFirstViewEvent(app.getId(), clientId));
                event.setFromEventId(data.getString("fei"));
                event.setTags(data.getObject("tags", String[].class));
                event.setUrl(data.getString("url"));
                event.setUserId(data.getString("ui"));
                event.setAppSessionId(data.getString("asi"));
                return trackDataService.saveEvent(trackDataExtractor.extractEvent(event, data, app, request)).getId();
            case Event.EVENT_END:
                trackDataService.endEvent(eventId, new Date(), sessionId);
                break;
            case Event.EVENT_HEARTBEAT:
                trackDataService.refreshEvent(eventId, sessionId);
                break;
        }
        if (MapUtils.isNotEmpty(data)) {
            trackDataService.saveEvent(trackDataExtractor.extractEvent(trackDataService.getEvent(eventId), data, app, request));
        }
        return eventId;
    }

    public List<Action> trackActions(String eventId, JSONArray arr, App app, HttpServletRequest request) {
        if (arr != null) {
            List<Action> actions = Lists.newArrayListWithCapacity(arr.size());
            for (int i = 0, len = arr.size(); i < len; i++) {
                Object value = arr.get(i);
                if (value instanceof String) {
                    actions.add(trackDataService.getAction((String) value));
                    continue;
                }
                JSONObject actionJSON = arr.getJSONObject(i);
                String id = actionJSON.getString(Constants.ID);
                Action action;
                if (isNew(id, actionJSON)) {
                    action = new Action();
                    action.setType(actionJSON.getIntValue(Constants.TYPE));
                    action.setName(actionJSON.getString("name"));
                    action.setEventId(assertNotEmpty("eventId", eventId));
                } else {
                    action = trackDataService.getAction(id);
                    String name = actionJSON.getString("name");
                    if (name != null) {
                        action.setName(name);
                    }
                }
                actions.add(trackDataService.saveAction(trackDataExtractor.extractAction(action, actionJSON, app, request)));
            }
            return actions;
        }
        return Collections.emptyList();
    }

    public List<ErrorLog> trackErrorLogs(String eventId, JSONArray arr, App app, HttpServletRequest request) {
        if (arr != null) {
            List<ErrorLog> errorLogs = Lists.newArrayListWithCapacity(arr.size());
            for (int i = 0, len = arr.size(); i < len; i++) {
                Object value = arr.get(i);
                if (value instanceof String) {
                    errorLogs.add(trackDataService.getErrorLog((String) value));
                    continue;
                }

                JSONObject errorLogJSON = arr.getJSONObject(i);
                String id = errorLogJSON.getString(Constants.ID);
                ErrorLog errorLog;
                if (isNew(id, errorLogJSON)) {
                    errorLog = new ErrorLog();
                    errorLog.setMsg(errorLogJSON.getString("msg"));
                    errorLog.setEventId(assertNotEmpty("eventId", eventId));
                } else {
                    errorLog = trackDataService.getErrorLog(id);
                    String msg = errorLogJSON.getString("msg");
                    if (msg != null) {
                        errorLog.setMsg(msg);
                    }
                }
                errorLogs.add(trackDataService.saveErrorLog(trackDataExtractor.extractErrorLog(errorLog, errorLogJSON, app, request)));
            }
            return errorLogs;
        }
        return Collections.emptyList();
    }

    public void outputEmptyGif(HttpServletResponse response) throws IOException {
        response.setContentType("image/gif");
        response.getOutputStream().write(emptyGif);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (servletContext != null) {
            emptyGif = IOUtils.toByteArray(servletContext.getResourceAsStream("/static/img/empty.gif"));
        }
    }
}
