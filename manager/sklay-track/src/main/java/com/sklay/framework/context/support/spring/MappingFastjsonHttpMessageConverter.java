/*
 * Project:  any
 * Module:   framework
 * File:     MappingFastJsonHttpMessageConverter.java
 * Modifier: xyang
 * Modified: 2012-07-03 21:08
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.framework.context.support.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 11-12-29
 */
public class MappingFastjsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    private String jsonpParameterName = "callback";

    public void setJsonpParameterName(String jsonpParameterName) {
        this.jsonpParameterName = jsonpParameterName;
    }

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public MappingFastjsonHttpMessageConverter() {
        super(new MediaType("application", "json", DEFAULT_CHARSET));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return JSON.parseObject(FileCopyUtils.copyToByteArray(inputMessage.getBody()), clazz);
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        SerializeWriter out = new SerializeWriter();
        out.config(SerializerFeature.DisableCircularReferenceDetect, true);
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.write(o);
        String jsonpCallback = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getParameter(jsonpParameterName);
        Writer writer = new OutputStreamWriter(outputMessage.getBody(), DEFAULT_CHARSET);
        try {
            if (StringUtils.isEmpty(jsonpCallback)) {
                out.writeTo(writer);
            } else {
                writer.write(jsonpCallback + "(");
                out.writeTo(writer);
                writer.write(");");
            }
            writer.flush();
        } finally {
            out.close();
        }
    }
}
