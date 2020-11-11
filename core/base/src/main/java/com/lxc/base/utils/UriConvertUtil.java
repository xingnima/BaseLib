package com.lxc.base.utils;

import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Uri转换工具
 * Created by lcy on 2019/1/17.
 * email:lcyzxin@gmail.com
 * version 1.0
 */
public class UriConvertUtil {

    public static List<Uri> convert(List<String> data) {
        if (data == null || data.size() == 0) return null;
        List<Uri> list = new ArrayList<>();
        for (String d : data) {
            if (!TextUtils.isEmpty(d)) {
                if (d.startsWith("http")) {
                    list.add(Uri.parse(d));
                } else {
                    list.add(UriUtils.file2Uri(new File(d)));
                }
            }
        }
        return list;
    }
}
