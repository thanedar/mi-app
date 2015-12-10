package com.mitelcel.pack.ui.widget;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import com.mitelcel.pack.Config;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.bean.req.BeanGetOfferList;
import com.mitelcel.pack.api.bean.resp.BeanGetOfferListResponse;
import com.mitelcel.pack.bean.ui.OfferItemHolder;
import com.mitelcel.pack.ui.fragment.IFragCommunication;
import com.mitelcel.pack.utils.MiLog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sudhanshu.thanedar on 19/11/2015.
 */
public class LoadOffersAsyncTask extends AsyncTask<MiApiClient, Void, LoadOffersAsyncTask.InternalBean> {

    WeakReference<IFragCommunication> reference;
    BeanGetOfferList beanGetOfferList;
    private boolean refresh;

    public LoadOffersAsyncTask(IFragCommunication reference, int start, int limit) {
        this.reference = new WeakReference<>(reference);
        this.beanGetOfferList = new BeanGetOfferList(start, limit);
    }

    public LoadOffersAsyncTask(IFragCommunication reference, int start, int limit, boolean refresh) {
        this.reference = new WeakReference<>(reference);
        this.beanGetOfferList = new BeanGetOfferList(start, limit);
        this.refresh = refresh;
    }

    @Override
    protected InternalBean doInBackground(MiApiClient... params) {
        try {

            BeanGetOfferListResponse res;
            MiApiClient miApiClient = params[0];

            res = miApiClient.get_offer_list_async(beanGetOfferList);

            Context context = reference.get() != null ? ((Fragment) reference.get()).getActivity().getApplicationContext() : null;

            if (res.getError().getCode() != Config.SUCCESS)
                return new InternalBean(null, res.getError().getCode());

            List<OfferItemHolder> mOfferItemHolders = offerListToArray(res, context);

            return new InternalBean(mOfferItemHolders, res.getError().getCode());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(InternalBean bean) {
        super.onPostExecute(bean);
        try {
            if (reference.get() == null || bean == null) {
                MiLog.i("LoadAsync onPostExecute", "reference bean NULL");
                reference.get().error(Config.NO_CONNECTION);
            }
            else {
                MiLog.i("LoadAsync onPostExecute", bean.toString());
                if (bean.error_code == Config.SUCCESS) {
                    MiLog.i("LoadAsync onPostExecute", "Calling updateAdapter");
                    reference.get().updateAdapter(bean.offerItemHolders, refresh);
                } else {
                    MiLog.i("LoadAsync onPostExecute", "not SUCCESS " + bean.error_code);
                    reference.get().error(bean.error_code);
                }
            }
        } catch (Exception e) {
            MiLog.i("LoadAsync onPostExecute", "In catch block");
            e.printStackTrace();
        }

    }

    /**
     * Support bean class
     */
    static class InternalBean {
        public List<OfferItemHolder> offerItemHolders;
        public int error_code = 1;

        public InternalBean(List<OfferItemHolder> offerItemHolders, int error_code) {
            this.offerItemHolders = offerItemHolders;
            this.error_code = error_code;
        }

        public InternalBean(List<OfferItemHolder> offerItemHolders) {
            this.offerItemHolders = offerItemHolders;
        }
    }

    public static List<OfferItemHolder> offerListToArray(BeanGetOfferListResponse res, Context context) {

        if (res == null || res.getResult() == null || res.getResult() == null || res.getResult().size() == 0 || context == null)
            return new ArrayList<>();

        List<BeanGetOfferListResponse.Offer> list = res.getResult();
        List<OfferItemHolder> results = new ArrayList<>(list.size());

        MiLog.i("offerListToArray", "offerListToArray----------------------------------------------------");
        MiLog.i("offerListToArray", "offerListToArray original[" + list.size() + "]");

        for (BeanGetOfferListResponse.Offer bean : list) {
            results.add(new OfferItemHolder(bean));
        }

        for(OfferItemHolder bean : results)
            MiLog.i("offer list data",bean.toString());

        MiLog.i("offerListToArray","offerListToArray filtered[" + results.size() + "]");

        MiLog.i("offerListToArray","offerListToArray----------------------------------------------------");

        return results;
    }

}