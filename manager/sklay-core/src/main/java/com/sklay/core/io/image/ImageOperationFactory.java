/*
 * Project:  any
 * Module:   sklay-core
 * File:     ImageOperationFactory.java
 * Modifier: zhouning
 * Modified: 2012-12-9 下午8:11:02 
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.core.io.image;

import com.sklay.core.web.util.SpringUtils;
import com.sklay.core.io.image.AwtImageOperation;
import com.sklay.core.io.image.ImageOperation;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-17
 */
public class ImageOperationFactory {

	private static ImageOperation imageOperation;

	public static ImageOperation getImageOperation() {
		if (imageOperation == null) {
			try {
				imageOperation = SpringUtils.getBean(ImageOperation.class);
			} catch (Exception e) {
				if (imageOperation == null) {
					imageOperation = new AwtImageOperation();
				}
			}
			if (imageOperation == null) {
				imageOperation = new AwtImageOperation();
			}
		}
		return imageOperation;
	}

}
