package com.sklay.track.service.impl;

import com.mongodb.*;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.support.mongo.Mongos;
import com.sklay.track.listener.ActionListener;
import com.sklay.track.listener.EventListener;
import com.sklay.track.listener.SessionListener;
import com.sklay.track.model.*;
import com.sklay.track.scheduler.TempCollectionScheduler;
import com.sklay.track.service.TrackDataService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.CollectionCallback;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.CollectionUtils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.sklay.support.mongo.Mongos.id;
import static com.sklay.support.mongo.Mongos.obj;
import static com.sklay.track.util.Helper.uuid;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-8-9
 */
public class TrackDataServiceImpl implements TrackDataService {
	private MongoTemplate mongo;
	private List<SessionListener> sessionListeners = Collections.emptyList();
	private List<EventListener> eventListeners = Collections.emptyList();
	private List<ActionListener> actionListeners = Collections.emptyList();

	@Autowired
	public void setMongo(MongoTemplate mongo) {
		this.mongo = mongo;
	}

	public void setSessionListeners(List<SessionListener> sessionListeners) {
		this.sessionListeners = sessionListeners;
	}

	public void setEventListeners(List<EventListener> eventListeners) {
		this.eventListeners = eventListeners;
	}

	public void setActionListeners(List<ActionListener> actionListeners) {
		this.actionListeners = actionListeners;
	}

	@Override
	public Client saveClient(Client client) {
		if (client.getId() == null) {
			client.setId(uuid());
		}
		if (client.getCreateAt() == null) {
			client.setCreateAt(new Date());
		}
		mongo.save(client);
		return client;
	}

	@Override
	public Client getClient(String id) {
		return getById(id, Client.class);
	}

	@Override
	public Session saveSession(Session session) {
		if (session.getId() == null) {
			session.setId(uuid());
		}
		if (session.getBeginAt() == null) {
			beginSession(session);
		}
		mongo.save(session);
		return session;
	}

	private void beginSession(Session session) {
		session.setBeginAt(new Date());
		for (SessionListener listener : sessionListeners) {
			listener.sessionBegin(session);
		}
	}

	@Override
	public void endSession(String sessionId, Date endAt) {
		for (SessionListener listener : sessionListeners) {
			listener.sessionEnd(sessionId, endAt);
		}
		updateSessionEndAt(sessionId, endAt);
	}

	@Override
	public void updateSessionEndAt(String sessionId, Date endAt) {
		mongo.updateFirst(Mongos.idQuery(sessionId), update("endAt", endAt),
				Session.class);
	}

	@Override
	public Session getSession(String id) {
		return getById(id, Session.class);
	}

	@Override
	public Event saveEvent(Event event) {
		if (event.getId() == null) {
			event.setId(uuid());
		}
		if (event.getBeginAt() == null) {
			beginEvent(event);
		}
		mongo.save(event);
		return event;
	}

	private void beginEvent(Event event) {
		event.setBeginAt(new Date());
		for (EventListener listener : eventListeners) {
			listener.eventBegin(event);
		}
	}

	@Override
	public void refreshEvent(String eventId, String sessionId) {
		for (EventListener listener : eventListeners) {
			listener.eventRefresh(eventId, sessionId);
		}
	}

	@Override
	public void endEvent(String eventId, Date endAt, String sessionId) {
		for (EventListener listener : eventListeners) {
			listener.eventEnd(eventId, endAt, sessionId);
		}
		updateEventEndAt(eventId, endAt);
	}

	@Override
	public void updateEventEndAt(String eventId, Date endAt) {
		mongo.updateFirst(Mongos.idQuery(eventId), update("endAt", new Date()),
				Event.class);
	}

	@Override
	public void incrIdleSecond(final String eventId, final Integer idle) {
		mongo.execute(Event.class, new CollectionCallback<Object>() {
			@Override
			public Object doInCollection(DBCollection collection) {
				collection.findAndModify(id(eventId), null, null, false,
						obj("$inc", obj("idle", idle)), false, true);
				return null;
			}
		});
	}

	@Override
	public Event getEvent(String id) {
		return getById(id, Event.class);
	}

	@Override
	public Boolean isFirstViewEvent(String appId, String clientId) {
		return mongo.count(
				query(where("appId").is(appId).and("clientId").is(clientId)),
				Event.class) > 0 ? null : true;
	}

	private void addAction(Action action) {
		for (ActionListener listener : actionListeners) {
			listener.actionAdd(action);
		}
	}

	@Override
	public Action saveAction(Action action) {
		if (action.getId() == null) {
			action.setId(uuid());
		}
		if (action.getHappenAt() == null) {
			action.setHappenAt(new Date());
			addAction(action);
		}
		BasicDBObject actionDoc = new BasicDBObject();
		mongo.getConverter().write(action, actionDoc);
		mongo.findAndModify(Mongos.idQuery(action.getEventId()),
				new Update().push("actions", actionDoc), Event.class);
		return action;
	}

	@Override
	public Action getAction(final String id) {
		return mongo.execute(Event.class, new CollectionCallback<Action>() {
			@Override
			public Action doInCollection(DBCollection collection)
					throws MongoException, DataAccessException {
				BasicDBList list = (BasicDBList) collection
						.aggregate(obj("$match", obj("actions._id", id)),
								obj("$project", obj("actions", 1)),
								obj("$unwind", "$actions"),
								obj("$match", obj("actions._id", id)))
						.getCommandResult().get("result");
				if (list.isEmpty()) {
					return null;
				}
				return mongo.getConverter().read(
						Action.class,
						(BasicDBObject) ((BasicDBObject) list.get(0))
								.get("actions"));
			}
		});
	}

	@Override
	public ErrorLog saveErrorLog(ErrorLog errorLog) {
		if (errorLog.getId() == null) {
			errorLog.setId(uuid());
		}
		if (errorLog.getHappenAt() == null) {
			errorLog.setHappenAt(new Date());
		}
		mongo.save(errorLog);
		return errorLog;
	}

	@Override
	public ErrorLog getErrorLog(String id) {
		return getById(id, ErrorLog.class);
	}

	private <T> T getById(String id, Class<T> clazz) throws SklayException {
		T entity = mongo.findById(id, clazz);
		if (entity == null) {
			throw new SklayException(ErrorCode.ILLEGAL_PARAM, null, clazz, id);
		}
		return entity;
	}

	public List<Event> getEvents(final String basicQuery, final int offset,
			final int limit, final boolean unwind) {
		Query q = new BasicQuery(basicQuery);
		final DBObject queryObject = q.getQueryObject();
		return mongo.execute(Event.class,
				new CollectionCallback<List<Event>>() {
					@Override
					public List<Event> doInCollection(DBCollection collection)
							throws MongoException, DataAccessException {
						BasicDBList list;
						if (offset < 0 || limit <= 0) {
							if (unwind) {
								list = (BasicDBList) collection
										.aggregate(
												obj("$match", queryObject),
												obj("$unwind", "$actions"),
												obj("$match", queryObject),
												obj("$sort",
														obj("actions.happenAt",
																-1)))
										.getCommandResult().get("result");
							} else {
								list = (BasicDBList) collection
										.aggregate(
												obj("$match", queryObject),
												obj("$sort",
														obj("actions.happenAt",
																-1)))
										.getCommandResult().get("result");
							}
						} else {
							if (unwind) {
								list = (BasicDBList) collection
										.aggregate(
												obj("$match", queryObject),
												obj("$unwind", "$actions"),
												obj("$match", queryObject),
												obj("$sort",
														obj("actions.happenAt",
																-1)),
												obj("$skip", offset),
												obj("$limit", limit))
										.getCommandResult().get("result");
							} else {
								list = (BasicDBList) collection
										.aggregate(
												obj("$match", queryObject),
												obj("$sort",
														obj("actions.happenAt",
																-1)),
												obj("$skip", offset),
												obj("$limit", limit))
										.getCommandResult().get("result");
							}
						}
						if (list.isEmpty()) {
							return null;
						}
						List<Event> events = new ArrayList<Event>();
						for (Object aList : list) {
							events.add(mongo.getConverter().read(Event.class,
									(BasicDBObject) aList));
						}
						return events;
					}
				});
	}

	public int countEvents(final String basicQuery, final boolean unwind) {
		Query q = new BasicQuery(basicQuery);
		final DBObject queryObject = q.getQueryObject();
		return mongo.execute(Event.class, new CollectionCallback<Integer>() {
			@Override
			public Integer doInCollection(DBCollection collection)
					throws MongoException, DataAccessException {
				BasicDBList list;
				if (unwind) {
					list = (BasicDBList) collection
							.aggregate(
									obj("$match", queryObject),
									obj("$unwind", "$actions"),
									obj("$match", queryObject),
									obj("$group",
											obj("_id", null).append("count",
													obj("$sum", 1))))
							.getCommandResult().get("result");
				} else {
					list = (BasicDBList) collection
							.aggregate(
									obj("$match", queryObject),
									obj("$group",
											obj("_id", null).append("count",
													obj("$sum", 1))))
							.getCommandResult().get("result");
				}
				if (list.isEmpty()) {
					return 0;
				}
				return (Integer) ((BasicDBObject) list.get(0)).get("count");
			}
		});
	}

	@Override
	public Map<String, Action> getLatestUserActions(final String bizKey,
			Set<String> userIds, final int fetchSize) {
		Query query = new Query();
		if (CollectionUtils.isEmpty(userIds)) {
			query.addCriteria(Criteria.where("_id").regex(".*-" + bizKey));
		} else {
			List<String> uids = new ArrayList<String>();
			for (String uid : userIds) {
				uids.add(uid + '-' + bizKey);
			}
			query.addCriteria(Criteria.where("_id").in(uids));
		}
		query.with(new Sort(new Sort.Order(Sort.Direction.DESC,
				"value.happenAt")));
		query.skip(0);
		query.limit(fetchSize);
		final Map<String, Action> userLatestActions = new LinkedHashMap<String, Action>();
		mongo.executeQuery(query,
				TempCollectionScheduler.USER_ACTION_TEMP_COLLECTION,
				new DocumentCallbackHandler() {
					@Override
					public void processDocument(DBObject dbObject)
							throws MongoException, DataAccessException {
						String uidAndActionBizKey = (String) dbObject
								.get("_id");
						Action action = mongo.getConverter().read(Action.class,
								(DBObject) dbObject.get("value"));
						userLatestActions.put(uidAndActionBizKey.substring(0,
								uidAndActionBizKey.indexOf('-')), action);
					}
				});
		return userLatestActions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getUserActionCount(String bizKey,
			Set<String> userIds) {
		if (CollectionUtils.isEmpty(userIds)) {
			return Collections.EMPTY_MAP;
		} else {
			Query query = new Query();
			query.addCriteria(Criteria.where("value.userId").in(userIds));
			if (StringUtils.isNotBlank(bizKey)) {
				query.addCriteria(Criteria.where("value.action.bizKey").is(
						bizKey));
			}
			final DBObject queryObject = query.getQueryObject();
			return mongo.execute(
					TempCollectionScheduler.USER_ACTION_OWNER_TEMP_COLLECTION,
					new CollectionCallback<Map<String, Integer>>() {
						@Override
						public Map<String, Integer> doInCollection(
								DBCollection collection) throws MongoException,
								DataAccessException {
							BasicDBList list = (BasicDBList) collection
									.aggregate(
											obj("$match", queryObject),
											obj("$group",
													obj("_id", "$value.userId")
															.append("count",
																	obj("$sum",
																			1))))
									.getCommandResult().get("result");
							if (list != null && list.size() > 0) {
								Map<String, Integer> result = new HashMap<String, Integer>();
								for (int i = 0; i < list.size(); i++) {
									BasicDBObject bdb = (BasicDBObject) list
											.get(i);
									result.put(bdb.getString("_id"),
											bdb.getInt("count"));
								}
								return result;
							}
							return Collections.EMPTY_MAP;
						}
					});
		}
	}

	@Override
	public String executeCommand(String jsonCommand) {
		return mongo.executeCommand(jsonCommand).toString();
	}
}
