package com.sklay.track.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.google.common.collect.Lists;
import com.sklay.support.mongo.Mongos;
import com.sklay.track.model.User;
import com.sklay.track.service.UserProvider;
import com.sklay.track.service.UserService;

import java.util.Date;
import java.util.List;

/**
 * .
 * <p/>
 * 
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-3-18
 */
public class UserServiceImpl implements UserService {
	public static final int BATCH_SIZE = 256;
	private MongoTemplate mongo;
	private int batchSize = BATCH_SIZE;

	@Autowired
	public void setMongo(MongoTemplate mongo) {
		this.mongo = mongo;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	@Override
	public User saveUser(User user) {
		mongo.save(user);
		return user;
	}

	@Override
	public User saveUserIfNotExist(User user) {
		if (mongo.count(Mongos.idQuery(user.getId()), User.class) == 0) {
			saveUser(user);
		}
		return user;
	}

	@Override
	public User getUser(String id) {
		return mongo.findById(id, User.class);
	}

	@Override
	public void reloadUsers(Date startTime, Date endTime) {
		List<User> users;
		for (int start = 0;; start += batchSize) {
			users = Lists.newArrayList();
			// userProvider.getUsers(startTime, endTime, start, batchSize);
			for (User user : users) {
				saveUser(user);
			}
			if (users.size() < batchSize)
				break;
		}
	}
}
