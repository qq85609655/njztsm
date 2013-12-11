package com.sklay.track.web.console;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sklay.track.model.Action;
import com.sklay.track.model.Client;
import com.sklay.track.model.ErrorLog;
import com.sklay.track.model.Event;
import com.sklay.track.model.Online;
import com.sklay.track.service.AppService;
import com.sklay.track.service.TrackDataService;
import com.sklay.track.util.Page;


/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-17
 */
@Controller
@RequestMapping(value = "console")
public class ReportController extends BaseController {

    @Autowired
    private MongoTemplate mongo;
    
    @Autowired
    private AppService appService;
    
    @Autowired
    private TrackDataService trackDataService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping(value = "online", method = RequestMethod.GET)
    public String online(Model model) throws Exception {
        List<Online> onlines = mongo.findAll(Online.class);
        Map<String, Event> events = new HashMap<String, Event>();
        Map<String, Client> clients = new HashMap<String, Client>();
        for (Online online : onlines) {
            events.put(online.getEventId(), mongo.findById(online.getEventId(), Event.class));
            clients.put(online.getClientId(), mongo.findById(online.getClientId(), Client.class));
        }
        model.addAttribute("onlines", onlines);
        model.addAttribute("events", events);
        model.addAttribute("clients", clients);
        return "report/online";
    }

    @RequestMapping(value = "event", method = RequestMethod.GET)
    public String event(Model model,
                        @RequestParam(value = "appId",required = false) String appId,
                        @RequestParam(value = "index", defaultValue = "1") int index,
                        @RequestParam(value = "size", defaultValue = "20") int size,
                        String path,
                        Long userId,
                        String jsonQuery,
                        boolean unwind,
                        Date startDate,Date endDate) throws Exception {
    	
    	Page<Event> page = null;
    	List<Event> events = null;
    	long total = 0;
    	int offset = Page.toStart(index, size);
    	if(StringUtils.isEmpty(jsonQuery)){
	    	Query query = new Query();
	        if(StringUtils.isNotBlank(appId)){
	            query.addCriteria(Criteria.where("appId").is(appId));
	        }
	        if(startDate!=null){
	        	query.addCriteria(Criteria.where("beginAt").gte(startDate));
	        }
	        if(endDate!=null){
	        	query.addCriteria(Criteria.where("beginAt").lte(endDate));
	        }
	        if(StringUtils.isNotBlank(path)){
	        	query.addCriteria(Criteria.where("path").regex(".*"+path+".*"));
	        }
	        if(userId!=null){
	        	query.addCriteria(Criteria.where("userId").is(userId));
	        }
	        total=mongo.count(query, Event.class);
	        events = mongo.find(query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "beginAt"))).skip(offset).limit(size), Event.class);
    	}else{
    		try{
	    		total = trackDataService.countEvents(jsonQuery, unwind);
	    		events = trackDataService.getEvents(jsonQuery, offset, size, unwind);
    		}catch (Exception e) {
    			//ignore invalid query
			}
    	}
    	page=new Page<Event>(index,size);
        page.setItems(events);
        page.setTotal((int)total);
    	Map<String, Long> errors = new HashMap<String, Long>();
    	if(CollectionUtils.isNotEmpty(events)){
	    	for (Event event : events) {
	            errors.put(event.getId(), mongo.count(Query.query(Criteria.where("eventId").is(event.getId())), ErrorLog.class));
	        }
    	}
        model.addAttribute("page", page);
        model.addAttribute("errors", errors);
        
        model.addAttribute("apps",appService.getApps());
        return "report/event";
    }

    @RequestMapping(value = "action/{id}", method = RequestMethod.GET)
    public String action(@PathVariable("id") String id, Model model) throws Exception {
        model.addAttribute("actions", mongo.find(Query.query(Criteria.where("eventId").is(id)), Action.class));
        return "report/action";
    }

    @RequestMapping(value = "error/{id}", method = RequestMethod.GET)
    public String error(@PathVariable("id") String id, Model model) throws Exception {
        model.addAttribute("errors", mongo.find(Query.query(Criteria.where("eventId").is(id)), ErrorLog.class));
        return "report/error";
    }
}
