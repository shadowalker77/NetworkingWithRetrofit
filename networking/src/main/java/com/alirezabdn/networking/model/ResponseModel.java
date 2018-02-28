package com.alirezabdn.networking.model;

import com.google.gson.Gson;

/**
 * Created by Administrator on 6/10/2017.
 */

public class ResponseModel {
    public String toString() {
        return new Gson().toJson(this);
    }
}
