package com.github.rahul_gill.sellbuy.viemodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.rahul_gill.sellbuy.models.ItemModel;
import com.github.rahul_gill.sellbuy.api.VolleySingleton;
import com.squareup.moshi.JsonAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private MutableLiveData<List<ItemModel>> items = new MutableLiveData<List<ItemModel>>(null);
    private final VolleySingleton mVolley;
    public MutableLiveData<String> appBarTitle;
    public MutableLiveData<Integer> totalPurchases = new MutableLiveData<Integer>(0);
    public MutableLiveData<Integer> totalSells = new MutableLiveData<Integer>(0);
    private static final String API_URL = "http://5654-2405-201-3017-105f-bd0a-270f-67fb-90b1.ngrok.io/";
    private static final String ITEM_LIST_ARRAY_KEY = "response";
    private static final String TOTAL_PURCHASES_AND_SELLS_KEY = "";

    public AppViewModel(Application app, VolleySingleton volley){
        super(app);
        mVolley = volley;
        appBarTitle = new MutableLiveData<String>("");
        fetchRemoteItems();
        try {
            fetchTotalPurchaseAndSell();
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    public void fetchRemoteItems(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API_URL + "/api/getTransactionList", null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray(ITEM_LIST_ARRAY_KEY);
                        List<ItemModel> newList = new LinkedList<ItemModel>();
                        for (int i=0; i< jsonArray.length(); i++){
                            JSONObject item = jsonArray.getJSONObject(i);
                            ItemModel itemModel = new ItemModel(
                                item.getString("date"),
                                item.getString("time"),
                                    item.getString("name"),
                                    100,//item.getInt("pricePerItem")
                                    item.getString("unit"),
                                    item.getInt("quantity"),
                                    item.getInt("totalPrice"),
                                    item.getString("typeOfTransaction")
                            );
                            newList.add(itemModel);
                        }
                        if(items.getValue() != null) {
                            Log.e("dbig", "first" + items.getValue().toString());
                        }
                        items.setValue(newList);
                        Log.e("dbig", "after" + items.getValue().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace);

        mVolley.getRequestQueue().add(request);
    }

    public void createItemTransaction(ItemModel itemModel) {
        try {
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, API_URL+ "/api/createTransaction",
                    itemModel.toJsonObject(),
                    response -> {
                        Log.e("working","working");
                        fetchRemoteItems();
                    },
                    Throwable::printStackTrace
            );
            mVolley.getRequestQueue().add(postRequest);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public void updateItemTransaction(ItemModel itemModel, int id) throws JSONException {
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PUT, API_URL+ "/api/updateTransaction",
                itemModel.toJsonObjectWithId(id),
                null,
                Throwable::printStackTrace
        );
        mVolley.getRequestQueue().add(postRequest);
    }

    public void fetchTotalPurchaseAndSell() throws JSONException {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API_URL + "/api/GetTotalPurchaseAndSell", null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray(TOTAL_PURCHASES_AND_SELLS_KEY);
                        totalPurchases.setValue(jsonArray.getInt(0));
                        totalSells.setValue(jsonArray.getInt(1));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace);

        mVolley.getRequestQueue().add(request);
    }

    public LiveData<List<ItemModel>> getItems(){
        return items;
    }

    public LiveData<Integer> getTotalPurchases(){
        return totalPurchases;
    }
    public LiveData<Integer> getTotalSells(){
        return totalSells;
    }

}
