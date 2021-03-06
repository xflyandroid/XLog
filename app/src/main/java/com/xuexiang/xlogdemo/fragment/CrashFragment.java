/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xuexiang.xlogdemo.fragment;

import android.os.Environment;

import com.xuexiang.xaop.annotation.Permission;
import com.xuexiang.xaop.consts.PermissionConsts;
import com.xuexiang.xlog.crash.CrashHandler;
import com.xuexiang.xlog.crash.SendEmailCrashListener;
import com.xuexiang.xlog.crash.ToastCrashListener;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageSimpleListFragment;
import com.xuexiang.xutil.system.PermissionUtils;

import java.util.List;

/**
 * <pre>
 *     desc   :
 *     author : xuexiang
 *     time   : 2018/5/13 下午11:22
 * </pre>
 */
@Page(name = "程序崩溃处理")
public class CrashFragment extends XPageSimpleListFragment {

    private final static String CRASH_PATH = Environment.getExternalStorageDirectory() + "/xlog/crash_logs/";
    /**
     * 初始化例子
     *
     * @param lists
     * @return
     */
    @Override
    protected List<String> initSimpleData(List<String> lists) {
        lists.add("崩溃处理：简单的toast提示 + 程序自动启动。");
        lists.add("崩溃处理：发送崩溃日志邮件。");
        lists.add("设置崩溃日志输出根目录为绝对路径：" + CRASH_PATH);
        return lists;
    }

    @Override
    protected void initViews() {
        super.initViews();
        PermissionUtils.requestSystemAlertWindow(getActivity());
    }

    /**
     * 条目点击
     *
     * @param position
     */
    @Override
    protected void onItemClick(int position) {
        switch(position) {
            case 0:
                CrashHandler.getInstance().setOnCrashListener(new ToastCrashListener());
                crash();
                break;
            case 1:
                CrashHandler.getInstance().setOnCrashListener(new SendEmailCrashListener());
                crash();
                break;
            case 2:
                setAbsolutePath();
                break;
            default:
                break;
        }
    }

    /**
     * 设置崩溃日志输出根目录为绝对路径，路径为外部存储需要申请权限
     */
    @Permission(PermissionConsts.STORAGE)
    public void setAbsolutePath() {
        CrashHandler.getInstance().setAbsolutePath(true).setCrashLogDir(CRASH_PATH);
    }


    private void crash() {
        throw new NullPointerException("崩溃啦！");
    }
}
