package com.sklay.track.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.sklay.support.incrementer.DataFieldMaxValueIncrementer;
import com.sklay.track.model.dict.Dict;
import com.sklay.track.model.dict.Item;
import com.sklay.track.service.DictService;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-3-20
 */
public class DictServiceImpl implements DictService {
    private MongoTemplate mongo;
    private DataFieldMaxValueIncrementer dictIncrementer;
    private DataFieldMaxValueIncrementer itemIncrementer;

    @Autowired
    public void setMongo(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    public void setDictIncrementer(DataFieldMaxValueIncrementer dictIncrementer) {
        this.dictIncrementer = dictIncrementer;
    }

    public void setItemIncrementer(DataFieldMaxValueIncrementer itemIncrementer) {
        this.itemIncrementer = itemIncrementer;
    }

    @Override
    public Dict saveDict(Dict dict) {
        if (dict.getId() == null) {
            dict.setId(dictIncrementer.nextIntValue());
            dict.setCreateAt(new Date());
        }
        mongo.save(dict);
        return dict;
    }

    @Override
    public void removeDict(Integer id) {
        mongo.remove(getDict(id));
    }

    @Override
    public Dict getDict(Integer id) {
        return mongo.findById(id, Dict.class);
    }

    @Override
    public Dict getDict(String dictKey) {
        return mongo.findOne(query(where("key").is(dictKey)), Dict.class);
    }

    @Override
    public Dict getDictEx(String dictKey) {
        Dict dict = getDict(dictKey);
        if (dict == null) {
            dict = new Dict();
            dict.setKey(dictKey);
            saveDict(dict);
        }
        return dict;
    }

    @Override
    public List<Dict> getDicts() {
        return mongo.findAll(Dict.class);
    }

    @Override
    public Item saveItem(Item item) {
        if (item.getId() == null) {
            item.setId(itemIncrementer.nextIntValue());
            item.setCreateAt(new Date());
        }
        mongo.save(item);
        return item;
    }

    @Override
    public void removeItem(Integer itemId) {
        mongo.remove(getItem(itemId));
    }

    @Override
    public Item getItem(Integer itemId) {
        return mongo.findById(itemId, Item.class);
    }

    @Override
    public Item getItem(String dictKey, String itemKey) {
        Dict dict = getDict(dictKey);
        if (dict == null) {
            return null;
        }
        return getItem(dict.getId(), itemKey);
    }

    @Override
    public Item getItem(Integer dictId, String itemKey) {
        return mongo.findOne(query(where("dictId").is(dictId).and("key").is(itemKey)), Item.class);
    }

    @Override
    public Item getItemEx(String dictKey, String itemKey) {
        return getItemEx(dictKey, itemKey, null);
    }

    @Override
    public Item getItemEx(String dictKey, String itemKey, Integer parentId) {
        Dict dict = getDictEx(dictKey);
        Item item = getItem(dict.getId(), itemKey);
        if (item == null) {
            item = new Item();
            item.setKey(itemKey);
            item.setDictId(dict.getId());
            item.setParentId(parentId);
            saveItem(item);
        }
        return item;
    }

    @Override
    public Item getItemEx2(String dictKey, String itemKey, Item tpl) {
        Dict dict = getDictEx(dictKey);
        Item item = getItem(dict.getId(), itemKey);
        if (item == null) {
            item = new Item();
            item.setKey(itemKey);
            item.setDictId(dict.getId());
            if (tpl != null) {
                item.setParentId(tpl.getParentId());
                item.setValue(tpl.getValue());
                item.setDesc(tpl.getDesc());
                item.setOrder(tpl.getOrder());
                item.setAttrs(tpl.getAttrs());
            }
            saveItem(item);
        }
        return item;
    }

    @Override
    public List<Item> getItems(String dictKey) {
        Dict dict = getDict(dictKey);
        if (dict == null) {
            return Collections.emptyList();
        }
        return mongo.find(query(where("dictId").is(dict.getId())), Item.class);
    }

    @Override
    public List<Item> getItems(String dictKey, Integer parentId) {
        Dict dict = getDict(dictKey);
        if (dict == null) {
            return Collections.emptyList();
        }
        return mongo.find(query(where("parentId").is(parentId)), Item.class);
    }
}
