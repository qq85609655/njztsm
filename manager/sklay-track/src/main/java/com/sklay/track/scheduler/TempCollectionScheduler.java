package com.sklay.track.scheduler;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;

public class TempCollectionScheduler implements InitializingBean{
	
	public static final String USER_ACTION_TEMP_COLLECTION = "userActionTmp";
	
	public static final String USER_ACTION_OWNER_TEMP_COLLECTION = "userActionOwnerTmp";
	
	@Autowired
	private MongoTemplate mongo;
	
	public void mapreduceLatestUserAction(){
		String mapFunction = "function map(){" +
				"if(this.userId!=null&&this.actions!=null){" +
				"var uid = this.userId;" +
				"this.actions.forEach(function(action){" +
				"if(action.happenAt!=null&&action.bizKey!=null){" +
				"emit(uid+'-'+action.bizKey,action)" +
				"}})" +
				"}}";
		String reduceFunction = "function Reduce(key, values){" +
				"var latest;" +
				"for(var i=0;i<values.length;i++){" +
				"var test = values[i];" +
				"latest=latest==null?test:(test.happenAt>latest.happenAt?test:latest);" +
				"}" +
				"return latest;" +
				"}";
		MapReduceOptions mro = new MapReduceOptions();
		mro.outputTypeMerge();
		mro.outputCollection(USER_ACTION_TEMP_COLLECTION);
		mongo.mapReduce("event", mapFunction, reduceFunction, mro, Map.class);
	}
	
	
	public void mapreduceLatestUserActionOwner(){
		String mapFunction = "function map(){" +
				"if(this.userId!=null&&this.actions!=null){" +
				"var uid = this.userId;" +
				"this.actions.forEach(function(action){" +
				"if(action.happenAt!=null&&action.owner!=null&&action.bizKey!=null){" +
				"emit(uid+'-'+action.bizKey+'-'+action.owner,{'userId':uid,'action':action})" +
				"}})" +
				"}}";
		String reduceFunction = "function Reduce(key, values){" +
				"var latest;" +
				"for(var i=0;i<values.length;i++){" +
				"var test = values[i];" +
				"latest=latest==null?test:(test.action.happenAt>latest.action.happenAt?test:latest);" +
				"}" +
				"return latest;" +
				"}";
		MapReduceOptions mro = new MapReduceOptions();
		mro.outputTypeMerge();
		mro.outputCollection(USER_ACTION_OWNER_TEMP_COLLECTION);
		mongo.mapReduce("event", mapFunction, reduceFunction, mro, Map.class);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(!mongo.collectionExists(USER_ACTION_TEMP_COLLECTION)){
			mongo.createCollection(USER_ACTION_TEMP_COLLECTION);
		}
		if(!mongo.collectionExists(USER_ACTION_OWNER_TEMP_COLLECTION)){
			mongo.createCollection(USER_ACTION_OWNER_TEMP_COLLECTION);
		}
	}
}
