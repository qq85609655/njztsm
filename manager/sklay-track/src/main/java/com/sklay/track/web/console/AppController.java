package com.sklay.track.web.console;

import com.google.common.collect.Maps;
import com.sklay.track.Constants;
import com.sklay.track.model.Action;
import com.sklay.track.model.App;
import com.sklay.track.model.ErrorLog;
import com.sklay.track.model.Event;
import com.sklay.track.service.AppService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-17
 */
@Controller
@RequestMapping(value = "console/app")
public class AppController extends BaseController {

    @Value("${track.url}")
    private String trackUrl;

    @Autowired
    private AppService appService;

    @Autowired
    private MongoTemplate mongo;

    @ModelAttribute("app")
    public App getFile(@RequestParam(value = "id", required = false) String id) throws Exception {
        return id == null ? new App() : appService.getAppEx(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) throws Exception {
        List<App> apps= appService.getApps();
        Map<String, Long> events = new HashMap<String, Long>();
        for (App app : apps) {
            events.put(app.getId(), mongo.count(Query.query(Criteria.where("appId").is(app.getId())), Event.class));
        }
        model.addAttribute("events", events);
        model.addAttribute("apps", apps);
        return "app/index";
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id") String id) throws Exception {
        appService.removeApp(id);
        return "redirect:/app";
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit() throws Exception {
        return "app/edit";
    }

    @RequestMapping(value = "code", method = RequestMethod.GET)
    public String code(Model model) throws Exception {
        model.addAttribute("trackUrl", trackUrl);
        return "app/code";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String save(@ModelAttribute("app") App app, RedirectAttributes ra, HttpServletRequest request) throws Exception {
        app.setConfigs(parseConfigs(request));
        app.setLastModified(System.currentTimeMillis());
        String sDomain = app.getAttr(Constants.SESSION_DOMAIN);
        if (StringUtils.isBlank(sDomain)) {
            app.getAttrs().remove(Constants.SESSION_DOMAIN);
        } else {
            app.setAttr(Constants.SESSION_DOMAIN, sDomain.trim());
        }
        appService.saveApp(app);
        success(ra);
        return "redirect:/console/app";
    }

    private Map<String, Serializable> parseConfigs(HttpServletRequest request) {
        Map<String, Serializable> vars = Maps.newLinkedHashMap();
        String[] varKey = request.getParameterValues("varKey");
        String[] varValue = request.getParameterValues("varValue");
        if (varKey != null) {
            for (int i = 0, len = varKey.length; i < len; i++) {
                String key = StringUtils.trim(varKey[i]);
                if (StringUtils.isNotEmpty(key)) {
                    vars.put(key, StringUtils.trim(varValue[i]));
                }
            }
        }
        return vars;
    }
}
