package com.example.instagramclone.braintree_payment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;
import com.example.instagramclone.main_tabs.ProfileTab.ProfileTab;
import com.example.instagramclone.main_tabs.ProfileTab.profile_page.ImageAdapter;
import com.example.instagramclone.main_tabs.ProfileTab.profile_page.ProfilePage;


import java.util.List;


    public class PricesAdapter extends RecyclerView.Adapter<PricesAdapter.PricesViewHolder> {
        private final Context context;
        private final List<PricesModel> pricesModelList;

        public PricesAdapter.PricesViewHolder holder;
        View.OnClickListener listener;
        String text;
        View view;
        public PricesAdapter(Context context, List<PricesModel> pricesModelList,View.OnClickListener listener) {
            this.context = context;
            this.listener = listener;
            this.pricesModelList = pricesModelList;
        }

        @NonNull
        @Override
        public PricesAdapter.PricesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
             view = inflater.inflate(R.layout.prices_item, parent, false);
            return new PricesAdapter.PricesViewHolder(view,context,listener);
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
            private final Context context;
            Button pay_btn;

            public PricesViewHolder(View itemView, Context context,View.OnClickListener listener) {
                super(itemView);

                this.context =context;
                month = itemView.findViewById(R.id.tv_month);
                price = itemView.findViewById(R.id.tv_price);
                saving = itemView.findViewById(R.id.tv_savings);
                value = itemView.findViewById(R.id.tv_value);
                pay_btn = itemView.findViewById(R.id.pay_button);
                pay_btn.setOnClickListener(listener);
                if (context instanceof PaymentActivity) {
                    pay_btn.setVisibility(View.VISIBLE);
                }

            }


            public String getPrice(){
                return price.getText().toString();
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

                if (!(context instanceof PaymentActivity)) {
                    Intent intent = new Intent(view.getContext(), PaymentActivity.class);
                    view.getContext().startActivity(intent);
                }
            }
        }
    }

