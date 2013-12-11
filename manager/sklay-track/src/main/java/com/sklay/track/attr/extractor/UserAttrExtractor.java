package com.sklay.track.attr.extractor;

import com.alibaba.fastjson.JSONObject;
import com.sklay.track.attr.AttrExtractContext;
import com.sklay.track.attr.AttrExtractor;
import com.sklay.track.model.Event;
import com.sklay.track.model.User;
import com.sklay.track.service.UserProvider;
import com.sklay.track.service.UserService;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-15
 */
public class UserAttrExtractor implements AttrExtractor<Event> {
//    private UserProvider userProvider;
	
    private UserService userService;

//    public void setUserProvider(UserProvider userProvider) {
//        this.userProvider = userProvider;
//    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //TODO
    @Override
    public void extract(Event event, JSONObject data, AttrExtractContext context) {
        String appSessionId = event.getAppSessionId();
        if (appSessionId == null) {
//            event.setAppSessionId(appSessionId = userProvider.getSessionId(context.getRequest()));
        	
        	 event.setAppSessionId(appSessionId = "1234");
        }
        if (appSessionId != null && event.getUserId() == null) {
            User user = null ;//userProvider.getUserBySessionId(appSessionId);
            if (user != null) {
                userService.saveUserIfNotExist(user);
                event.setUserId(user.getId());
            }
        }
    }
}
