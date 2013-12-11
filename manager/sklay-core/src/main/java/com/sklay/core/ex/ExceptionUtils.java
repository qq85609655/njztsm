/*
 * Project:  any-parent
 * Module:   any-framework
 * File:     ExUtils.java
 * Modifier: ozn
 * Modified: 2012-08-12 15:29
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.core.ex;

import org.springframework.core.NestedRuntimeException;

import com.sklay.core.message.NLS;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 *  .
 * <p/>
 *
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-2
 */
public class ExceptionUtils {
    private ExceptionUtils() {
    }

    public static Throwable getRootCause(Throwable cause) {
        Throwable rootCause = null;
        while (cause != null && cause != rootCause) {
            rootCause = cause;
            cause = cause.getCause();
        }
        return rootCause;
    }

    public static String buildMessage(int code, Object[] args, String defaultMessage, Throwable cause) {
        String message = null;
        if (code != ErrorCode.SERVER_ERROR) {
            message = NLS.getMessage("error." + code, args, null);
        }
        if (defaultMessage != null) {
            message = message == null ? defaultMessage : message + "; " + defaultMessage;
        }
        if (cause != null) {
            StringBuilder sb = new StringBuilder();
            if (message != null) {
                sb.append(message);
            }
            Set<Throwable> visitedExceptions = new HashSet<Throwable>();
            Throwable tmpEx = cause;
            do {
                sb.append("; nested exception is ").append(cause);
                visitedExceptions.add(tmpEx);
                tmpEx = tmpEx.getCause();
            } while (!(tmpEx == null || visitedExceptions.contains(tmpEx) || tmpEx instanceof SklayException || tmpEx instanceof NestedRuntimeException));
            return sb.toString();
        } else {
            return message;
        }
    }

    public static String buildStackTrace(Throwable cause) {
        StringWriter sw = new StringWriter();
        cause.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
