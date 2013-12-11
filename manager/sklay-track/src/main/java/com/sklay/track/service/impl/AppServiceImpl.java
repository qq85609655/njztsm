package com.sklay.track.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.support.incrementer.DataFieldMaxValueIncrementer;
import com.sklay.track.model.App;
import com.sklay.track.service.AppService;


import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-3-20
 */
public class AppServiceImpl implements AppService {
    private MongoTemplate mongo;
    private DataFieldMaxValueIncrementer incrementer;

    @Autowired
    public void setMongo(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    public void setIncrementer(DataFieldMaxValueIncrementer incrementer) {
        this.incrementer = incrementer;
    }

    @Override
    public App saveApp(App app) {
        if (app.getId() == null) {
            app.setId(incrementer.nextStringValue());
            app.setCreateAt(new Date());
        }
        app.setLastModified(System.currentTimeMillis());
        mongo.save(app);
        return app;
    }

    @Override
    public void removeApp(String id) {
        mongo.remove(getApp(id));
    }

    @Override
    public App getApp(String id) {
        App app = getAppEx(id);
        if (!app.isEnabled()) {
            throw new SklayException(ErrorCode.ILLEGAL_PARAM, "App " + id + " is not enable");
        }
        return app;
    }

    @Override
    public App getAppEx(String id) {
        App app = mongo.findById(id, App.class);
        if (app == null) {
            throw new SklayException(ErrorCode.ILLEGAL_PARAM,null,App.class, id);
        }
        return app;
    }

    @Override
    public App getAppByKey(String appKey) {
        return mongo.findOne(query(where("key").is(appKey)), App.class);
    }

    @Override
    public List<App> getApps() {
        return mongo.findAll(App.class);
    }
}
