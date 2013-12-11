/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.njztsm.app.chart;

import org.achartengine.GraphicalView;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Defines the demo charts.
 */
public interface IDemoChart {
  //TODO step1:图标有名称 有描述  还有执行这个图片方法
  /** A constant for the name field in a list activity. */
  String NAME = "name";
  /** A constant for the description field in a list activity. */
  String DESC = "desc";

  
  String getName();

 
  String getDesc();

 
  Intent execute(Context context);
  GraphicalView executeForView(Context context);

}
