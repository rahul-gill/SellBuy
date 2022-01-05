package com.github.rahul_gill.sellbuy.viemodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.rahul_gill.sellbuy.models.ItemModel;
import com.github.rahul_gill.sellbuy.api.VolleySingleton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private MutableLiveData<List<ItemModel>> items;
    private VolleySingleton mVolley;
    public MutableLiveData<String> appBarTitle;

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
}
