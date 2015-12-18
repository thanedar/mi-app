package com.mitelcel.pack.bean.api.response;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sudhanshu.thanedar on 11/24/15.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BeanGetFrequentNumbersResponse extends BeanGenericResponse {

    @Expose
    private List<FreqNumbers> result = new ArrayList<>();

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Data
    public static class FreqNumbers {
        @Expose
        private int order;
        @Expose
        private String msisdn;
    }
}
