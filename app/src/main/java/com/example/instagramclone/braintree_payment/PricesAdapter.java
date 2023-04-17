package com.example.instagramclone.braintree_payment;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;




import java.util.List;


    public class PricesAdapter extends RecyclerView.Adapter<PricesAdapter.PricesViewHolder> {
        private final Context context;
        private final List<PricesModel> pricesModelList;

        String text;
        public PricesAdapter(Context context, List<PricesModel> pricesModelList) {
            this.context = context;
            this.pricesModelList = pricesModelList;
        }

        @NonNull
        @Override
        public PricesAdapter.PricesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.prices_item, parent, false);
            return new PricesAdapter.PricesViewHolder(view,this);
        }
        @Override
        public void onBindViewHolder(@NonNull PricesAdapter.PricesViewHolder holder, int position) {
            holder.setData(pricesModelList.get(position));

        }

        public void setText(String text){
            this.text=text;
        }

        public String getText(){
            return text;
        }
        @Override
        public int getItemCount() {
            return pricesModelList.size();
        }

        public static class PricesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private final TextView month, price, value,saving;
            private final PricesAdapter adapter;
            public PricesViewHolder(View itemView, PricesAdapter adapter) {
                super(itemView);
                this.adapter =adapter;
                month = itemView.findViewById(R.id.tv_month);
                price = itemView.findViewById(R.id.tv_price);
                saving = itemView.findViewById(R.id.tv_savings);
                value = itemView.findViewById(R.id.tv_value);

            }


            public void setData(PricesModel data) {

                month.setText(data.getMonth());
                price.setText(data.getPrice());
                saving.setText(data.getSaving());
                value.setText(data.getValue());
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), PaymentActivity.class);
                view.getContext().startActivity(intent);
            }
        }
    }

