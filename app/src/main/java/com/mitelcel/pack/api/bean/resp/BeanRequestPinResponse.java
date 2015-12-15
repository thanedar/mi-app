package com.mitelcel.pack.api.bean.resp;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sudhanshut on 11/6/15.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BeanRequestPinResponse extends BeanGenericResponse {

    @Expose
    private boolean result;

}
