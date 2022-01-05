package com.github.rahul_gill.sellbuy.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.github.rahul_gill.sellbuy.Constants;
import com.github.rahul_gill.sellbuy.models.ItemModel;
import com.github.rahul_gill.sellbuy.databinding.ItemBinding;

import java.util.Locale;

public class ItemsListAdapter extends ListAdapter<ItemModel, ItemsListAdapter.ViewHolder> {
    public ItemsListAdapter(){
        super(new ItemDiffCallback());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemBinding binding = ItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemModel item = getItem(position);
        holder.bind(item);

    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private final ItemBinding binding;
        public ViewHolder(ItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
        }
        public void bind(ItemModel item){
            binding.dateSoldOn.setText(
                    String.format(Locale.getDefault(), "%s %s", item.date, item.time)
            );
            binding.itemName.setText(item.name);
            if(item.typeOfTransaction.equals(Constants.PURCHASE_TRANSACTION)){
                binding.purchaseCount.setText(String.valueOf(item.quantity * item.pricePerItem));
                binding.sellCount.setText("");
            }else{
                binding.purchaseCount.setText("");
                binding.sellCount.setText(String.valueOf(item.quantity * item.pricePerItem));
            }
            binding.itemQuantity.setText(
                    String.format(Locale.getDefault(), "%d * %d %s", item.quantity, 1, item.unit)
            );
        }
    }
    static class ItemDiffCallback extends DiffUtil.ItemCallback<ItemModel>{

        @Override
        public boolean areItemsTheSame(@NonNull ItemModel oldItem, @NonNull ItemModel newItem) {
            return oldItem.time.equals(newItem.time) && oldItem.date.equals(newItem.date);
        }

        @Override
        public boolean areContentsTheSame(@NonNull ItemModel oldItem, @NonNull ItemModel newItem) {
            return oldItem.isEqualTo(newItem);
        }
    }
}