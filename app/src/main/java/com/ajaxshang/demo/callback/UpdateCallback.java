/*
 * $filename: UpdateCallback.java,v $
 * $Date: 2014-9-19  $
 * Copyright (C) ZhengHaibo, Inc. All rights reserved.
 * This software is Made by Zhenghaibo.
 */
package com.ajaxshang.demo.callback;

/*
 *@author: ZhengHaibo  
 *blog:     http://blog.csdn.net/nuptboyzhb
 *mail:    zhb931706659@126.com
 *web:     http://www.mobctrl.net
 *2014-9-19  Nanjing,njupt,China
 */
public interface UpdateCallback {
    void startProgress(long downloadId, int position);
}
