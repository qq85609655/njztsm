package com.sklay.track.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sklay.track.Constants;
import com.sklay.track.service.TrackService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-3-29
 */
@Controller
public class JsApiController {
    private static final Logger LOG = LoggerFactory.getLogger(JsApiController.class);

    @Autowired
    private TrackService trackService;

    @RequestMapping(value = "/boot/{id}")
    @ResponseBody
    public Callable boot(
            @PathVariable("id") String id,
            HttpServletRequest request, HttpServletResponse response) {
        return boot("v1", id, request, response);
    }

    @RequestMapping(value = "/boot/{v}/{id}")
    @ResponseBody
    public Callable boot(
            @PathVariable("v") final String version,
            @PathVariable("id") final String id,
            final HttpServletRequest request, final HttpServletResponse response) {
        return new Callable() {
            @Override
            public String call() throws Exception {
                try {
                    trackService.boot(id, version, request, response);
                } catch (Throwable e) {
                    LOG.error("Render track js error", e);
                }
                return null;
            }
        };
    }

    @RequestMapping(value = "/track/{id}")
    @ResponseBody
    public Callable track(
            @PathVariable("id") final String id,
            final HttpServletRequest request, final HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding(Constants.DEFAULT_CHARSET);
        return new Callable() {
            @Override
            public String call() throws Exception {
                try {
                    trackService.collect(id, request, response);
                } catch (Throwable e) {
                    LOG.error("collect track data error", e);
                }
                if ("POST".equals(request.getMethod())) {
                    response.getWriter().write("<html></html>");
                } else {
                    trackService.outputEmptyGif(response);
                }
                return null;
            }
        };
    }
}
