package com.sklay.track.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.sklay.track.model.Action;
import com.sklay.track.model.Event;


@Component
public class Converter {
	
	private static MongoConverter converter;
	
	@Autowired
	public Converter(MongoConverter c){
		converter = c;
	}



	private static Set<String> toSet(String[] attr) {
		if(attr != null&&attr.length>0){
			Set<String> result = new HashSet<String>();
			for (String string : attr) {
				result.add(string);
			}
			return result;
		}
		return Collections.EMPTY_SET;
	}

	private static Map<String, String> toData(Map<String, Object> attrs) {
		if(!CollectionUtils.isEmpty(attrs)){
			Map<String, String> result = new HashMap<String, String>();
			for (String atkey : attrs.keySet()) {
				result.put(atkey, JSON.toJSON(attrs.get(atkey)).toString());
			}
			return result;
		}
		return Collections.EMPTY_MAP;
	}

}
