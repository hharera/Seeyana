package com.example.n_one.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.n_one.R;

import java.util.List;

public class RecyclerAdapterSkill extends RecyclerView.Adapter<RecyclerAdapterSkill.ViewHolderSkill> {

    public interface RecyclerAdapterClick {
        void getClick(int position);
    }


    private final Context context;
    List<String> list;
    RecyclerAdapterClick adapterClick;

    public RecyclerAdapterSkill(Context context, List<String> list,RecyclerAdapterClick adapterClick) {
        this.context = context;
        this.list = list;
        this.adapterClick = adapterClick;
    }

    @NonNull
    @Override
    public ViewHolderSkill onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_list_skill, viewGroup, false);
        return new ViewHolderSkill(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSkill holder, final int i) {
        holder.textView.setText(list.get(i));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapterClick!= null){
                    adapterClick.getClick(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderSkill extends RecyclerView.ViewHolder {

        TextView textView;

        ViewHolderSkill(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvSkillCustom);

        }
    }
}
