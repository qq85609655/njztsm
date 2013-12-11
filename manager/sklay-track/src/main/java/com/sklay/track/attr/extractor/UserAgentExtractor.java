package com.sklay.track.attr.extractor;

import com.alibaba.fastjson.JSONObject;
import com.sklay.track.attr.AttrExtractContext;
import com.sklay.track.attr.Attrable;
import com.sklay.track.model.dict.Dict;
import com.sklay.track.model.dict.Dicts;
import com.sklay.track.model.dict.Item;

import net.sf.uadetector.OperatingSystem;
import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-15
 */
public class UserAgentExtractor extends AbstractSingleAttrExtractor {
    private UserAgentStringParser userAgentParser = UADetectorServiceFactory.getResourceModuleParser();

    public UserAgentExtractor() {
        setKey("agent");
    }

    public void setUserAgentParser(UserAgentStringParser userAgentParser) {
        this.userAgentParser = userAgentParser;
    }

    @Override
    public void extract(Attrable entity, JSONObject data, AttrExtractContext context) {
        String agentString = getString(getKey(), data, context);
        if (agentString == null) {
            agentString = context.getHeader("User-Agent");
        }
        //entity.setAttr(getTargetKey(), agentString);
        UserAgent agent = userAgentParser.parse(agentString);

        String browserType = agent.getType().name();
        Item browserTypeItem = dictService.getItem(Dicts.BROWSER_TYPE, browserType);
        if (browserTypeItem == null) {
            browserTypeItem = new Item();
            browserTypeItem.setDictId(dictService.getDictEx(Dicts.BROWSER_TYPE).getId());
            browserTypeItem.setKey(browserType);
            browserTypeItem.setAttr("typeName", agent.getType().getName());
            dictService.saveItem(browserTypeItem);
        }


        Item browserItem = dictService.getItem(Dicts.BROWSER, agent.getName());
        if (browserItem == null) {
            browserItem = new Item();
            browserItem.setDictId(dictService.getDictEx(Dicts.BROWSER).getId());
            browserItem.setParentId(browserTypeItem.getId());
            browserItem.setKey(agent.getName());
            browserItem.setAttr("family", agent.getFamily().name());
            browserItem.setAttr("icon", agent.getIcon());
            browserItem.setAttr("producer", agent.getProducer());
            browserItem.setAttr("producerUrl", agent.getProducerUrl());
            browserItem.setAttr("url", agent.getUrl());
            dictService.saveItem(browserItem);
        }
        entity.setAttr(Dicts.BROWSER, browserItem.getId());

        String browserVersion = agent.getVersionNumber().toVersionString();
        Item browserVersionItem = dictService.getItem(Dicts.BROWSER_VERSION, browserVersion);
        if (browserVersionItem == null) {
            browserVersionItem = new Item();
            browserVersionItem.setDictId(dictService.getDictEx(Dicts.BROWSER_VERSION).getId());
            browserItem.setParentId(browserItem.getId());
            browserVersionItem.setKey(browserVersion);
            browserItem.setAttr("major", agent.getVersionNumber().getMajor());
            browserItem.setAttr("minor", agent.getVersionNumber().getMinor());
            browserItem.setAttr("bugfix", agent.getVersionNumber().getBugfix());
            browserItem.setAttr("extension", agent.getVersionNumber().getExtension());
            dictService.saveItem(browserVersionItem);
        }
        entity.setAttr(Dicts.BROWSER_VERSION, browserVersionItem.getId());

        OperatingSystem os = agent.getOperatingSystem();
        String osType = os.getFamily().name();
        Item osTypeItem = dictService.getItem(Dicts.OS_TYPE, osType);
        if (osTypeItem == null) {
            osTypeItem = new Item();
            osTypeItem.setDictId(dictService.getDictEx(Dicts.OS_TYPE).getId());
            osTypeItem.setKey(osType);
            osTypeItem.setAttr("typeName", os.getFamily().getName());
            dictService.saveItem(osTypeItem);
        }

        Item osItem = dictService.getItem(Dicts.OS, os.getName());
        if (osItem == null) {
            osItem = new Item();
            osItem.setDictId(dictService.getDictEx(Dicts.OS).getId());
            osItem.setParentId(osTypeItem.getId());
            osItem.setKey(os.getName());
            osItem.setAttr("icon", os.getIcon());
            osItem.setAttr("producer", os.getProducer());
            osItem.setAttr("producerUrl", os.getProducerUrl());
            osItem.setAttr("url", os.getUrl());
            dictService.saveItem(osItem);
        }
        entity.setAttr(Dicts.OS, osItem.getId());

        String osVersion = os.getVersionNumber().toVersionString();
        Item osVersionItem = dictService.getItem(Dicts.OS_VERSION, osVersion);
        if (osVersionItem == null) {
            osVersionItem = new Item();
            osVersionItem.setDictId(dictService.getDictEx(Dicts.OS_VERSION).getId());
            osVersionItem.setParentId(osItem.getId());
            osVersionItem.setKey(osVersion);
            osVersionItem.setAttr("major", os.getVersionNumber().getMajor());
            osVersionItem.setAttr("minor", os.getVersionNumber().getMinor());
            osVersionItem.setAttr("bugfix", os.getVersionNumber().getBugfix());
            osVersionItem.setAttr("extension", os.getVersionNumber().getExtension());
            dictService.saveItem(osVersionItem);
        }
        entity.setAttr(Dicts.OS_VERSION, osVersionItem.getId());
    }
}
