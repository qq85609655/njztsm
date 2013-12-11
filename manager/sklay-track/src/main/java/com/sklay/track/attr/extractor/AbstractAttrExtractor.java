package com.sklay.track.attr.extractor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import com.sklay.track.attr.AttrExtractContext;
import com.sklay.track.attr.AttrExtractor;
import com.sklay.track.attr.AttrType;
import com.sklay.track.attr.Attrable;
import com.sklay.track.service.DictService;
import com.sklay.track.util.Pair;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-13
 */
public abstract class AbstractAttrExtractor<T extends Attrable> implements AttrExtractor<T> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractAttrExtractor.class);
    protected DictService dictService;

    @Autowired
    public void setDictService(DictService dictService) {
        this.dictService = dictService;
    }

    protected Object getValue(String key, AttrType type, JSONObject data, AttrExtractContext context) {
        Object value = data.get(key);
        if (value == null) {
            value = context.getAttr(key);
        }
        return value == null ? null : convertValue(key, type, value, context);
    }

    protected static String getString(String key, JSONObject data, AttrExtractContext context) {
        String value = data.getString(key);
        if (value == null) {
            value = context.getAttr(key, String.class);
        }
        return StringUtils.EMPTY.equals(value) ? null : value;
    }

    protected static Boolean getBoolean(String key, JSONObject data, AttrExtractContext context) {
        Boolean value = data.getBoolean(key);
        if (value == null) {
            value = context.getAttr(key, Boolean.class);
        }
        return value;
    }

    protected static Integer getInteger(String key, JSONObject data, AttrExtractContext context) {
        Integer value = data.getInteger(key);
        if (value == null) {
            value = context.getAttr(key, Integer.class);
        }
        return value;
    }

    protected static Long getLong(String key, JSONObject data, AttrExtractContext context) {
        Long value = data.getLong(key);
        if (value == null) {
            value = context.getAttr(key, Long.class);
        }
        return value;
    }

    protected static Double getDouble(String key, JSONObject data, AttrExtractContext context) {
        Double value = data.getDouble(key);
        if (value == null) {
            value = context.getAttr(key, Double.class);
        }
        return value;
    }

    protected static Date getDate(String key, JSONObject data, AttrExtractContext context) {
        Date value = data.getDate(key);
        if (value == null) {
            value = context.getAttr(key, Date.class);
        }
        return context.adjustDate(value);
    }

    protected Integer getDictItemId(String dictKey, String itemKey, Integer parentId) {
        return dictService.getItemEx(dictKey, itemKey, parentId).getId();
    }

    @SuppressWarnings("unchecked")
    protected Object convertValue(Object obj, AttrExtractContext context) {
        if (obj == null) {
            return null;
        }

        ParserConfig config = ParserConfig.getGlobalInstance();
        Class clazz = obj.getClass();
        if (config.isPrimitive(clazz)) {
            if(clazz == BigDecimal.class){
                return ((BigDecimal)obj).doubleValue();
            }
            return obj;
        }
        if (clazz.isEnum()) {
            return ((Enum) obj).name();
        }

        if (obj instanceof Map) {
            Map<Object, Object> map = (Map) obj;
            JSONObject json = new JSONObject(map.size());
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                Pair<String, AttrType> pair = guessType(entry.getKey().toString());
                Object value = convertValue(pair, entry.getValue(), context);
                if (value != null) {
                    json.put(pair.getKey(), value);
                }
            }
            return json;
        }

        if (obj instanceof Collection) {
            Collection collection = (Collection) obj;
            JSONArray array = new JSONArray(collection.size());
            for (Object item : collection) {
                Object jsonValue = convertValue(item, context);
                if (jsonValue != null) {
                    array.add(jsonValue);
                }
            }
            return array;
        }

        if (clazz.isArray()) {
            int len = Array.getLength(obj);
            JSONArray array = new JSONArray(len);
            for (int i = 0; i < len; ++i) {
                Object item = Array.get(obj, i);
                Object jsonValue = convertValue(item, context);
                if (jsonValue != null) {
                    array.add(jsonValue);
                }
            }
            return array;
        }

        try {
            List<FieldInfo> getters = TypeUtils.computeGetters(clazz, null);
            JSONObject json = new JSONObject(getters.size());

            for (FieldInfo field : getters) {
                Pair<String, AttrType> pair = guessType(field.getName());
                Object value = convertValue(pair, field.get(obj), context);
                if (value != null) {
                    json.put(pair.getKey(), value);
                }
            }

            return json;
        } catch (Exception e) {
            throw new JSONException("toJSON error", e);
        }
    }

    protected Pair<String, AttrType> guessType(String key) {
        int index = key.indexOf("_");
        if (index > -1) {
            AttrType type = AttrType.valueOf0(key.substring(0, index));
            if (type != null) {
                return new Pair<String, AttrType>(key.substring(index + 1), type);
            }
        }
        return new Pair<String, AttrType>(key, null);
    }

    protected Object convertValue(Pair<String, AttrType> pair, Object value, AttrExtractContext context) {
        return convertValue(pair.getKey(), pair.getValue(), value, context);
    }

    protected Object convertValue(String key, AttrType type, Object value, AttrExtractContext context) {
        try {
            if (value != null) {
                if (type != null) {
                    switch (type) {
                        case STRING:
                            String s = value.toString();
                            return StringUtils.EMPTY.equals(s) ? null : s;
                        case BOOLEAN:
                            return TypeUtils.castToBoolean(value);
                        case INTEGER:
                            return TypeUtils.castToInt(value);
                        case LONG:
                            return TypeUtils.castToLong(value);
                        case DOUBLE:
                            return TypeUtils.castToDouble(value);
                        case DATE:
                            return context.adjustDate(TypeUtils.castToDate(value));
                        case DICT:
                            return getDictItemId(key, value.toString(), null);
                        default:
                            return convertValue(value, context);
                    }
                } else {
                    return convertValue(value, context);
                }
            }
        } catch (Exception e) {
            LOG.error("Error to convert [key:{},value:value] to type [{}],{}", key, value, type, e.getMessage());
        }
        return null;
    }
}
