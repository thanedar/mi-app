package com.mitelcel.pack.bean.api.resp;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sudhanshut on 11/25/15.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class BeanDeleteFrequentNumberResponse extends BeanGenericResponse {

    @Expose
    private boolean result;

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }}
