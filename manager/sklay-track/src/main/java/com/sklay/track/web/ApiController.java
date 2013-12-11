package com.sklay.track.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.ExceptionUtils;
import com.sklay.core.ex.SklayException;
import com.sklay.track.Constants;
import com.sklay.track.model.*;
import com.sklay.track.service.AppService;
import com.sklay.track.service.TrackDataExtractor;
import com.sklay.track.service.TrackDataService;
import com.sklay.track.service.TrackService;
import com.sklay.track.util.Helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.net.BindException;
import java.util.List;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-3-29
 */
@Controller
public class ApiController {
    private static final Logger LOG = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private AppService appService;
    @Autowired
    private TrackService trackService;
    @Autowired
    private TrackDataService trackDataService;
    @Autowired
    private TrackDataExtractor trackDataExtractor;

    @ModelAttribute
    private void setupTs(HttpServletRequest request) {
        Helper.setupTs(request);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Map<String, Serializable> handleException(Exception ex, HttpServletRequest request) {
        LOG.debug("handle exception from request [" + request.getRequestURI() + "]", ex);
        int code = ErrorCode.SERVER_ERROR;
        if (ex instanceof SklayException) {
            code = ((SklayException) ex).getCode();
        } else if (ex instanceof MissingServletRequestParameterException) {
            code = ErrorCode.MISS_PARAM;
        } else if (ex instanceof TypeMismatchException || ex instanceof BindException || ex instanceof IllegalArgumentException) {
            code = ErrorCode.ILLEGAL_PARAM;
        }
        Map<String, Serializable> map = Maps.newHashMap();
        map.put("ret", code);
        map.put("msg", ex.toString());
        if (LOG.isDebugEnabled()) {
            map.put("detail", ExceptionUtils.buildStackTrace(ex));
        }
        return map;
    }

    @RequestMapping(value = "/api/{appId}/app")
    @ResponseBody
    public Map<String, Serializable> app(@PathVariable("appId") String appId) {
        return trackService.getAppConfigs(appService.getApp(appId));
    }

    @RequestMapping(value = "/api/{appId}/client")
    @ResponseBody
    public Client client(@PathVariable("appId") String appId,
                         @RequestParam(value = Constants.DATA, defaultValue = "{}") String data,
                         HttpServletRequest request, HttpServletResponse response) {
        App app = appService.getApp(appId);
        JSONObject json = JSON.parseObject(data);

        String clientId = getClientId(json, request);
        if (clientId == null) {
            clientId = json.getString(Constants.ID);
        }

        if (clientId == null) {
            clientId = trackService.generateClientId(response);
            json.put(Constants.IS_NEW, true);
        }
        return trackService.trackClient(clientId, json, app, request);
    }

    @RequestMapping(value = "/api/{appId}/session")
    @ResponseBody
    public Session session(@PathVariable("appId") String appId,
                           @RequestParam(value = Constants.DATA, defaultValue = "{}") String data,
                           HttpServletRequest request) {
        App app = appService.getApp(appId);
        JSONObject json = JSON.parseObject(data);
        return trackService.trackSession(json.getString(Constants.ID), getClientId(json, request), json, app, request);
    }

    @RequestMapping(value = "/api/{appId}/event")
    @ResponseBody
    public Event event(@PathVariable("appId") String appId,
                       @RequestParam(value = Constants.DATA) String data,
                       HttpServletRequest request) {
        App app = appService.getApp(appId);
        JSONObject json = JSON.parseObject(data);
        return trackDataService.getEvent(trackService.trackEvent(json.getString(Constants.ID), json.getString(Constants.SESSION_ID), null, json.getInteger(Constants.TYPE), json, app, request));
    }

    @RequestMapping(value = "/api/{appId}/action")
    @ResponseBody
    public List<Action> action(@PathVariable("appId") String appId,
                               @RequestParam(value = Constants.DATA) String data,
                               HttpServletRequest request) {
        App app = appService.getApp(appId);
        JSONObject json = JSON.parseObject(data);
        return trackService.trackActions(json.getString(Constants.EVENT_ID), json.getJSONArray("as"), app, request);
    }

    @RequestMapping(value = "/api/{appId}/error")
    @ResponseBody
    public List<ErrorLog> error(@PathVariable("appId") String appId,
                                @RequestParam(value = Constants.DATA) String data,
                                HttpServletRequest request) {
        App app = appService.getApp(appId);
        JSONObject json = JSON.parseObject(data);
        return trackService.trackErrorLogs(json.getString(Constants.EVENT_ID), json.getJSONArray("es"), app, request);
    }

    private String getClientId(JSONObject json, HttpServletRequest request) {
        String clientId = request.getParameter(Constants.CLIENT_ID);
        if (clientId != null) {
            return clientId;
        }
        clientId = json.getString(Constants.CLIENT_ID);
        if (clientId != null) {
            return clientId;
        }
        return Helper.getCookie(Constants.COOKIE_CLIENT_ID, request);
    }
}
