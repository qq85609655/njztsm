package com.sklay.track.listener.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.sklay.support.mongo.Mongos;
import com.sklay.track.listener.ActionListener;
import com.sklay.track.listener.EventListener;
import com.sklay.track.listener.SessionListener;
import com.sklay.track.model.Action;
import com.sklay.track.model.Event;
import com.sklay.track.model.Online;
import com.sklay.track.model.Session;

import java.util.Date;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-17
 */
public class OnlineListener implements EventListener, SessionListener,ActionListener {
    private MongoTemplate mongo;

    @Autowired
    public void setMongo(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public void sessionBegin(Session session) {
        Online online = new Online();
        online.setId(session.getId());
        online.setClientId(session.getClientId());
        online.setBeginAt(session.getBeginAt());
        online.setUpdateAt(session.getBeginAt());
        mongo.save(online);
    }

    @Override
    public void sessionEnd(String sessionId, Date endAt) {
        mongo.remove(Mongos.idQuery(sessionId), Online.class);
    }

    @Override
    public void eventBegin(Event event) {
        mongo.updateFirst(Mongos.idQuery(event.getSessionId()), update("eventId", event.getId()).set("appId", event.getAppId()), Online.class);
    }

    @Override
    public void eventRefresh(String eventId, String sessionId) {
        mongo.updateFirst(Mongos.idQuery(sessionId), update("updateAt", new Date()), Online.class);
    }

    @Override
    public void eventEnd(String eventId, Date endAt, String sessionId) {
        mongo.updateFirst(Mongos.idQuery(sessionId), update("updateAt", new Date()), Online.class);
    }

    @Override
    public void actionAdd(Action action) {
    }
}
