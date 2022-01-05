package com.github.rahul_gill.sellbuy.viemodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.rahul_gill.sellbuy.models.ItemModel;
import com.github.rahul_gill.sellbuy.api.VolleySingleton;
import com.squareup.moshi.JsonAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private MutableLiveData<List<ItemModel>> items = new MutableLiveData<List<ItemModel>>(null);
    private final VolleySingleton mVolley;
    public MutableLiveData<String> appBarTitle;
    public MutableLiveData<Integer> totalPurchases = new MutableLiveData<Integer>(0);
    public MutableLiveData<Integer> totalSells = new MutableLiveData<Integer>(0);
    private static final String API_URL = "";
    private static final String ITEM_LIST_ARRAY_KEY = "";
    private static final String TOTAL_PURCHASES_AND_SELLS_KEY = "";
    private JsonAdapter<ItemModel> itemModelJsonAdapter;

    public AppViewModel(Application app, VolleySingleton volley){
        super(app);
        mVolley = volley;
        appBarTitle = new MutableLiveData<String>("");
    }


    public LiveData<List<ItemModel>> getItems(){
        if (items == null) {
            items = new MutableLiveData<List<ItemModel>>();
        }
        return items;
    }


    public void addItem(ItemModel item){
        List<ItemModel> newList = items.getValue();
        if(newList == null){
            newList = new LinkedList<ItemModel>();
        }
        newList.add(0, item);
        items.setValue(newList);
    }

    public void getRemoteItems(){
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
                                    item.getInt("pricePerItem"),
                                    item.getString("unit"),
                                    item.getInt("quantity"),
                                    item.getInt("totalPrice"),
                                    item.getString("typeOfTransaction")
                            );
                            newList.add(itemModel);
                        }
                        items.setValue(newList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace);

        mVolley.getRequestQueue().add(request);
    }

    public void createItemTransaction(ItemModel itemModel) throws JSONException {
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, API_URL+ "/api/createTransaction",
                itemModel.toJsonObject(),
                null,
                Throwable::printStackTrace
        );
        mVolley.getRequestQueue().add(postRequest);
    }
    public void updateItemTransaction(ItemModel itemModel, int id) throws JSONException {
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PUT, API_URL+ "/api/updateTransaction",
                itemModel.toJsonObjectWithId(id),
                null,
                Throwable::printStackTrace
        );
        mVolley.getRequestQueue().add(postRequest);
    }

    public void getTotalPurchaseAndSell() throws JSONException {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API_URL + "/api/getTransactionList", null,
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

}
