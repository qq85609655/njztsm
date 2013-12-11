/*
 * Project:  any
 * Module:   any-track
 * File:     TrackService.java
 * Modifier: xyang
 * Modified: 2012-12-22 21:35
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.track.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sklay.track.model.Action;
import com.sklay.track.model.Client;
import com.sklay.track.model.ErrorLog;
import com.sklay.track.model.Event;
import com.sklay.track.model.Session;


/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-12-22
 */
public interface TrackDataService {

    Client saveClient(Client client);

    Client getClient(String id);

    Session saveSession(Session session);

    void endSession(String sessionId, Date endAt);

    void updateSessionEndAt(String sessionId, Date endAt);

    Session getSession(String id);

    Event saveEvent(Event event);

    void refreshEvent(String eventId, String sessionId);

    void endEvent(String eventId, Date endAt, String sessionId);

    void updateEventEndAt(String eventId, Date endAt);

    void incrIdleSecond(String eventId, Integer idle);

    Event getEvent(String id);

    Boolean isFirstViewEvent(String appId, String clientId);

    Action saveAction(Action action);

    Action getAction(String id);

    ErrorLog saveErrorLog(ErrorLog errorLog);

    ErrorLog getErrorLog(String id);

    List<Event> getEvents(final String basicQuery,final int offset,final int limit, final boolean unwind);
    
    int countEvents(final String basicQuery, final boolean unwind);

	Map<String, Action> getLatestUserActions(String bizKey, Set<String> userIds, int fetchSize);

	Map<String, Integer> getUserActionCount(String bizKey, Set<String> userIds);

	String executeCommand(String jsonCommand);
}
