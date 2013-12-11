/*
 * Project:  any
 * Module:   any-track
 * File:     DictManager.java
 * Modifier: xyang
 * Modified: 2012-12-22 19:43
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.track.service;


import java.util.List;

import com.sklay.track.model.dict.Dict;
import com.sklay.track.model.dict.Item;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-12-22
 */
public interface DictService {

    Dict saveDict(Dict dict);

    void removeDict(Integer id);

    Dict getDict(Integer id);

    Dict getDict(String dictKey);

    Dict getDictEx(String dictKey);

    List<Dict> getDicts();

    Item saveItem(Item item);

    void removeItem(Integer itemId);

    Item getItem(Integer itemId);

    Item getItem(String dictKey, String itemKey);

    Item getItem(Integer dictId, String itemKey);

    Item getItemEx(String dictKey, String itemKey);

    Item getItemEx(String dictKey, String itemKey, Integer parentId);

    Item getItemEx2(String dictKey, String itemKey, Item tpl);

    List<Item> getItems(String dictKey);

    List<Item> getItems(String dictKey, Integer parentId);

}
