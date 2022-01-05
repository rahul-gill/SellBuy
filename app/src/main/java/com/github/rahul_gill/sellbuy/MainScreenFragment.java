package com.github.rahul_gill.sellbuy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.github.rahul_gill.sellbuy.adapters.ItemsListAdapter;
import com.github.rahul_gill.sellbuy.api.VolleySingleton;
import com.github.rahul_gill.sellbuy.databinding.MainScreenBinding;
import com.github.rahul_gill.sellbuy.models.ItemModel;
import com.github.rahul_gill.sellbuy.viemodel.AppViewModel;
import com.github.rahul_gill.sellbuy.viemodel.AppViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainScreenFragment extends Fragment {
    private AppViewModel viewModel;
    private ItemsListAdapter adapter;
    MainScreenBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_screen, container, false);
        adapter = new ItemsListAdapter();
        binding.itemDetailsList.setAdapter(adapter);
        binding.purchaseButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(
                    MainScreenFragmentDirections.actionMainScreenFragmentToPurchaseSellFragment(false)
            );
        });

        binding.sellButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(
                    MainScreenFragmentDirections.actionMainScreenFragmentToPurchaseSellFragment(true)
            );
        });
        binding.todayDate.setText(
                new SimpleDateFormat("d/M/yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime())
        );
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(
            requireActivity(), new AppViewModelFactory(
                requireActivity().getApplication(),
                VolleySingleton.getInstance(requireActivity().getApplication())
            )
        ).get(AppViewModel.class);
        viewModel.appBarTitle.setValue("");

        final Observer<List<ItemModel>> itemListObserver = itemModels -> {
            adapter.submitList(itemModels);
//            adapter.notifyItemChanged(adapter.items.size() -1);
            int totalPurchases = 0, totalSells = 0;
            for(ItemModel i: itemModels){
                if(i.typeOfTransaction.equals("purchase"))
                    totalPurchases += i.totalPrice;
                else
                    totalSells += i.totalPrice;
            }
            binding.purchaseCountTotal.setText(String.valueOf(totalPurchases));
            binding.purchaseCountTotalHeader.setText(String.valueOf(totalPurchases));
            binding.sellCountTotal.setText(String.valueOf(totalSells));
            binding.sellCountTotalHeader.setText(String.valueOf(totalSells));
        };
        viewModel.getItems().observe(this, itemListObserver);
//        adapter.notifyDataSetChanged();
    }


}