package com.github.rahul_gill.sellbuy;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.github.rahul_gill.sellbuy.databinding.PriceUnitsBottomsheetBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class UnitsBottomSheet extends BottomSheetDialogFragment {
    static public String TAG = "price_units_bottomsheet";
    private final OnBottomSheetButtonClick onClick;
    private BottomSheetBehavior<View> behavior;

    public UnitsBottomSheet(UnitsBottomSheet.OnBottomSheetButtonClick onClick){
        this.onClick = onClick;
    }

    @Override
    public void onStart() {
        super.onStart();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ){
        PriceUnitsBottomsheetBinding binding = PriceUnitsBottomsheetBinding.inflate(inflater, container, false);
        binding.b1.setOnClickListener(this::actualOnClick);
        binding.b2.setOnClickListener(this::actualOnClick);
        binding.b3.setOnClickListener(this::actualOnClick);
        binding.b4.setOnClickListener(this::actualOnClick);
        binding.b5.setOnClickListener(this::actualOnClick);
        binding.b6.setOnClickListener(this::actualOnClick);
        binding.b7.setOnClickListener(this::actualOnClick);
        binding.b8.setOnClickListener(this::actualOnClick);
        binding.b9.setOnClickListener(this::actualOnClick);
        binding.b10.setOnClickListener(this::actualOnClick);
        binding.b11.setOnClickListener(this::actualOnClick);
        binding.b12.setOnClickListener(this::actualOnClick);
        binding.closeBottomsheetButton.setOnClickListener(v -> this.dismiss());

        behavior = BottomSheetBehavior.from(binding.unitsBottomSheet);

        return binding.getRoot();
    }

    public interface OnBottomSheetButtonClick{
        void onClick(View v);
    }
    
    private void actualOnClick(View v){
        onClick.onClick(v);
        this.dismiss();
    }
}