package com.sklay.track.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sklay.track.attr.*;
import com.sklay.track.model.*;
import com.sklay.track.service.TrackDataExtractor;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-3-29
 */
public class TrackDataExtractorImpl implements TrackDataExtractor {
    private Map<AppType, Map<AttrExtractorType, List<AttrExtractor>>> mapping;

    public void setMapping(Map<AppType, Map<AttrExtractorType, List<AttrExtractor>>> mapping) {
        this.mapping = mapping;
    }

    @Override
    public Client extractClient(Client client, JSONObject json, App app, HttpServletRequest request) {
        AttrExtractContext context = createContext(request);
        extract(client, json, context, app.getType(), AttrExtractorType.CLIENT);
        return client;
    }

    @Override
    public Session extractSession(Session session, JSONObject json, App app, HttpServletRequest request) {
        AttrExtractContext context = createContext(request);
        extract(session, json, context, app.getType(), AttrExtractorType.SESSION);
        return session;
    }

    @Override
    public Event extractEvent(Event event, JSONObject json, App app, HttpServletRequest request) {
        AttrExtractContext context = createContext(request);
        extract(event, json, context, app.getType(), AttrExtractorType.EVENT);
        return event;
    }

    @Override
    public Action extractAction(Action action, JSONObject json, App app, HttpServletRequest request) {
        AttrExtractContext context = createContext(request);
        extract(action, json, context, app.getType(), AttrExtractorType.ACTION);
        return action;
    }

    @Override
    public ErrorLog extractErrorLog(ErrorLog errorLog, JSONObject json, App app, HttpServletRequest request) {
        AttrExtractContext context = createContext(request);
        extract(errorLog, json, context, app.getType(), AttrExtractorType.ERROR_LOG);
        return errorLog;
    }

    private void extract(Attrable entity, JSONObject json, AttrExtractContext context, AppType appType, AttrExtractorType extractorType) {
        Map<AttrExtractorType, List<AttrExtractor>> map = mapping.get(AppType.GENERIC);
        if (map != null) {
            extract(entity, json, context, map.get(extractorType));
        }
        map = mapping.get(appType);
        if (map != null) {
            extract(entity, json, context, map.get(extractorType));
        }
    }

    @SuppressWarnings("unchecked")
    private void extract(Attrable entity, JSONObject json, AttrExtractContext context, List<AttrExtractor> extractors) {
        if (extractors == null) {
            return;
        }
        for (AttrExtractor extractor : extractors) {
            extractor.extract(entity, json, context);
        }
    }

    protected AttrExtractContext createContext(HttpServletRequest request) {
        return new AttrExtractContextImpl(request);
    }
}
