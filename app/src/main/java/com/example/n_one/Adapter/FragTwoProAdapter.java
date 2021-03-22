package com.example.n_one.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.n_one.R;
import com.example.n_one.SeeyanaApp;

import java.util.ArrayList;
import java.util.List;

public class FragTwoProAdapter extends RecyclerView.Adapter<FragTwoProAdapter.ViewHolder> {

    private static final String TAG = "FragTwoProAdapter";

    private final Context context;
    private final ArrayList<String> list;

    public FragTwoProAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_second_pro_frag, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = list.get(position);
        holder.tvTitle.setText(title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvTitle.setTextColor(context.getResources().getColor(R.color.yellow));
                String expertise = holder.tvTitle.getText().toString();
                List<String> expertises = SeeyanaApp.getInstance().getProvider().getMainExpertises();
                if (!expertises.contains(expertise)) {
                    expertises.add(expertise);
                } else {
                    expertises.remove(expertise);
                    holder.tvTitle.setTextColor(context.getResources().getColor(R.color.black));
                }

                Log.d(TAG, "The user choose expertise: " + SeeyanaApp.getInstance().getProvider().getMainExpertises());

//                ((SecondProgressActivity) context).setValues(2, true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);

        }
    }

}
