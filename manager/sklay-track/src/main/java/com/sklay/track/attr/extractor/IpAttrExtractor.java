package com.sklay.track.attr.extractor;

import com.alibaba.fastjson.JSONObject;
import com.sklay.track.attr.AttrExtractContext;
import com.sklay.track.attr.Attrable;
import com.sklay.track.ip.Location;
import com.sklay.track.model.dict.Dicts;
import com.sklay.track.model.dict.Item;
import com.sklay.track.service.LocationService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-15
 */
public class IpAttrExtractor extends AbstractSingleAttrExtractor {
    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public IpAttrExtractor() {
        setKey("ip");
    }

    @Override
    public void extract(Attrable entity, JSONObject data, AttrExtractContext context) {
        //entity.setAttr(getTargetKey(), context.getIp());
        String ip = context.getIp();
        Item ipItem = dictService.getItem(Dicts.IP, ip);
        if (ipItem == null) {
            ipItem = new Item();
            ipItem.setDictId(dictService.getDictEx(Dicts.IP).getId());
            ipItem.setKey(ip);
            Location location = locationService.getLocation(context.getIp());
            if (location != null) {
                ipItem.setAttr("country", dictService.getItemEx(Dicts.COUNTRY, location.getCountry()).getId());
                ipItem.setAttr("area", location.getArea());
            }
            dictService.saveItem(ipItem);
        }
        entity.setAttr(getTargetKey(), ipItem.getValue());
    }
}
