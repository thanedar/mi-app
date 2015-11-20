package com.mitelcel.pack.ui.fragment;

import com.mitelcel.pack.bean.ui.OfferItemHolder;

import java.util.List;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public interface IFragCommunication {

    void updateAdapter(List<OfferItemHolder> data, boolean refreshed);
    void error(int errorCode);
}
