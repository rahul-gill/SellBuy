package com.github.rahul_gill.sellbuy;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.github.rahul_gill.sellbuy.api.VolleySingleton;
import com.github.rahul_gill.sellbuy.databinding.PurchaseSellScreenBinding;
import com.github.rahul_gill.sellbuy.models.ItemModel;
import com.github.rahul_gill.sellbuy.viemodel.AppViewModel;
import com.github.rahul_gill.sellbuy.viemodel.AppViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PurchaseSellFragment extends Fragment {
    private AppViewModel viewModel;
    PurchaseSellScreenBinding binding;

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding =DataBindingUtil.inflate(inflater, R.layout.purchase_sell_screen, container, false);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        Date current = Calendar.getInstance().getTime();
        binding.dateSelectionButton.setText(dateFormatter.format(current).toUpperCase());
        binding.timeSelectionButton.setText(timeFormatter.format(current).toUpperCase());

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


        String buyOrSell = PurchaseSellFragmentArgs.fromBundle(getArguments()).getIsSelling() ?
               Constants.SELL_TRANSACTION  : Constants.PURCHASE_TRANSACTION;

        viewModel.appBarTitle.setValue(buyOrSell);

        final View.OnClickListener onSaveListener = v -> {
            viewModel.addItem(new ItemModel(
                    binding.dateSelectionButton.getText().toString(),
                    binding.timeSelectionButton.getText().toString(),
                    String.valueOf(binding.productNamePrompt.getText()),
                    Integer.parseInt(String.valueOf(binding.pricePrompt.getText())),
                    String.valueOf(binding.unitOfPricePrompt.getText()),
                    Integer.parseInt(String.valueOf(binding.totalQuantityPrompt.getText())),
                    Integer.parseInt(binding.totalMoney.getText().toString()),
                    buyOrSell
            ));
            Navigation.findNavController(view).navigate(
                    PurchaseSellFragmentDirections.actionPurchaseSellFragmentToMainScreenFragment()
            );
        };

        binding.saveButton.setOnClickListener(onSaveListener);

        binding.pricePrompt.addTextChangedListener(getTextWatcher());
        binding.totalQuantityPrompt.addTextChangedListener(getTextWatcher());

        binding.unitOfPricePrompt.setOnClickListener(v -> {
            UnitsBottomSheet bottomSheet = new  UnitsBottomSheet(v1 -> binding.unitOfPricePrompt.setText(String.format("%s", ((Button)v1).getText())));
            bottomSheet.show(requireActivity().getSupportFragmentManager(), UnitsBottomSheet.TAG);
        });
    }

    private void onDataChange(){
        String quantity = String.valueOf(binding.totalQuantityPrompt.getText());
        String pricePerItem = String.valueOf(binding.pricePrompt.getText());
        if(quantity.equals("")) quantity = "0";
        if(pricePerItem.equals("")) pricePerItem = "0";
        int i = Integer.parseInt(quantity) * Integer.parseInt(pricePerItem);
        binding.totalMoney.setText(
                String.valueOf(i)
        );
    }
    private TextWatcher getTextWatcher(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onDataChange();
            }
            @Override
            public void afterTextChanged(Editable s) {
                onDataChange();
            }
        };
    }
}