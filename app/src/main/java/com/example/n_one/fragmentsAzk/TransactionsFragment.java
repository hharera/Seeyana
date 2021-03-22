package com.example.n_one.fragmentsAzk;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.n_one.Adapter.TransactionsAdapter;
import com.example.n_one.R;
import com.example.n_one.models.Transactions;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionsFragment extends Fragment {

    private List<Transactions> list;

    public TransactionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_transactions, container, false);

        list = new ArrayList<>();
        list.add(new Transactions(R.drawable.img_egp_1000, "Milestone payment",
                getString(R.string.professional_bmw_mercedes_audi_all_gerrman_cars_and_model_makes_all_in_a)));
        list.add(new Transactions(R.drawable.img_egp_22, "Fees",
                getString(R.string.professional_bmw_mercedes_audi_all_gerrman_cars_and_model_makes_all_in_a)));
        list.add(new Transactions(R.drawable.img_egp_200, "Withdrawal", "Amount you withdraw from your account."));
        list.add(new Transactions(R.drawable.img_egp_49, "Offer Promotion",
                getString(R.string.professional_bmw_mercedes_audi_all_gerrman_cars_and_model_makes_all_in_a)));
        list.add(new Transactions(R.drawable.img_egp_2000, "Job completion payment",
                getString(R.string.professional_bmw_mercedes_audi_all_gerrman_cars_and_model_makes_all_in_a)));
        list.add(new Transactions(R.drawable.img_egp_27, "Taxes",
                getString(R.string.professional_bmw_mercedes_audi_all_gerrman_cars_and_model_makes_all_in_a)));

        RecyclerView rv = v.findViewById(R.id.main_skill_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new TransactionsAdapter(getActivity(), list));

        return v;
    }

}
