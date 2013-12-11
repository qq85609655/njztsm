package com.sklay.track.scheduler;

import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.sklay.core.ex.SklayException;
import com.sklay.track.model.Event;
import com.sklay.track.model.Online;
import com.sklay.track.service.TrackDataService;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Date;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-17
 */
public class SessionScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(SessionScheduler.class);
    private MongoTemplate mongo;
    private TrackDataService trackDataService;
    private int sessionTimeout = 1800000;

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    @Autowired
    public void setMongo(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Autowired
    public void setTrackDataService(TrackDataService trackDataService) {
        this.trackDataService = trackDataService;
    }

    public void sessionTimeoutJob() {
        LOG.debug("Start to cleanup timeout session");
        Date checkPoint = new Date(System.currentTimeMillis() - sessionTimeout);
        mongo.executeQuery(query(where("updateAt").lt(checkPoint)), mongo.getCollectionName(Online.class), new DocumentCallbackHandler() {
            @Override
            public void processDocument(DBObject dbObject) throws MongoException, DataAccessException {
                Online online = mongo.getConverter().read(Online.class, dbObject);
                LOG.debug("Found timeout session,[{}]", online);
                Date endAt;
                if (online.getEventId() != null) {
                    Event event = trackDataService.getEvent(online.getEventId());
                    endAt = event.getEndAt();
                    if (endAt == null) {
                        endAt = fixEventEndAt(event);
                    }
                } else { //找不到对应事件,设置为一分钟
                    endAt = DateUtils.addMinutes(online.getBeginAt(), 1);
                }
                trackDataService.endSession(online.getId(), endAt);
            }
        });
        mongo.executeQuery(query(where("beginAt").is(checkPoint).and("endAt").exists(false)), mongo.getCollectionName(Event.class), new DocumentCallbackHandler() {
            @Override
            public void processDocument(DBObject dbObject) throws MongoException, DataAccessException {
                LOG.debug("Found event without endAt,[{}]", dbObject);
                fixEventEndAt(mongo.getConverter().read(Event.class, dbObject));
            }
        });
    }

    private Date fixEventEndAt(Event event) {
        Date endAt;
        String fromEventId = event.getFromEventId();
        if (fromEventId != null) {//如果来源event能找到,则设置时长为上一次event持续时间,否则设置为一分钟
            Event fromEvent;
            try {
                fromEvent = trackDataService.getEvent(fromEventId);
                endAt = DateUtils.addMilliseconds(event.getBeginAt(), fromEvent.getDuration());
            } catch (SklayException e) {
                endAt = DateUtils.addMinutes(event.getBeginAt(), 1);
            }
        } else {
            endAt = DateUtils.addMinutes(event.getBeginAt(), 1);
        }
        trackDataService.updateEventEndAt(event.getId(), endAt);
        return endAt;
    }
}
