package com.service.business.net;

import com.google.gson.Gson;
import com.service.business.ui.utils.MyLog;

/**
 * Created by JimGong on 2016/6/23.
 */
public class JsonGenericsSerializator implements IGenericsSerializator {
    Gson mGson = new Gson();

    @Override
    public <T> T transform(String response, Class<T> classOfT) {
       //MyLog.show(response);
        return mGson.fromJson(response, classOfT);
    }

}
