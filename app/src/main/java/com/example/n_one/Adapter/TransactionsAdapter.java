package com.example.n_one.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.n_one.R;
import com.example.n_one.models.Transactions;

import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {

    private final Context context;
    private final List<Transactions> transactionsList;

    public TransactionsAdapter(Context context, List<Transactions> transactionsList) {
        this.context = context;
        this.transactionsList = transactionsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_transactions,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transactions transactions = transactionsList.get(position);

        holder.ivImage.setImageResource(transactions.getImg());
        holder.tvTitle.setText(transactions.getTitle());
        holder.tvDesc.setText(transactions.getDesc());

        if (position%2==0) {
            holder.layoutMain.setBackground(context.getResources().getDrawable(R.drawable.cat_back_two));
        }

        if (position == 0) {
            holder.layPaidBy.setVisibility(View.VISIBLE);
        } else if (position == 4) {
            holder.layPaidBy.setVisibility(View.VISIBLE);
        } else {
            holder.layPaidBy.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivImage;
        private final TextView tvTitle;
        private final TextView tvDesc;
        private final LinearLayout layoutMain;
        private final LinearLayout layPaidBy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutMain = itemView.findViewById(R.id.layout_main);
            ivImage = itemView.findViewById(R.id.iv_image);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            layPaidBy = itemView.findViewById(R.id.layout_paid_by);

        }
    }

}
