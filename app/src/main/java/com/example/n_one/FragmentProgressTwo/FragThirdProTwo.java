package com.example.n_one.FragmentProgressTwo;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.n_one.Adapter.RecyclerAdapterSkill;
import com.example.n_one.FlowLayout;
import com.example.n_one.R;
import com.example.n_one.SeeyanaApp;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragThirdProTwo extends Fragment {

    private static final String TAG = "FragThirdProTwo";

    private EditText etSkill;
    private TextView tvCounter;
    private LinearLayout layoutSkill1, layoutSkill2, layoutSkill3;
    private View view;
    private List<String> list;
    private List<String> listFilter;
    private RecyclerView listViewSkills;
    private RecyclerAdapterSkill adapter;
    private TextWatcher textWatcher;
    private FlowLayout flowLayout;
    boolean isOnTextChanged = false;


    public FragThirdProTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag_third_pro_two, container, false);
        list = new ArrayList<>();
        listFilter = new ArrayList<>();
        list.add("skill 1");
        list.add("skill 2");
        list.add("skill 3");
        list.add("skill 4");
        list.add("skill 5");
        list.add("skill 6");
        list.add("skill 7");
        list.add("skill 8");
        list.add("skill 9");
        list.add("skill 10");
        listFilter.addAll(list);
        init();
        setListener();
        initAdapter();
        return view;
    }

    private void setListener() {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                isOnTextChanged = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                listFilter.clear();
                for (int index = 0; index < list.size(); index++) {
                    Log.d("data_change", index + "," + list.size() + " , " + editable.toString());
                    if (list.get(index).contains(editable.toString().trim())) {
                        listFilter.add(list.get(index));
                    }
                }
                initAdapter();
            }
        };
    }

    private void initAdapter() {
        adapter = new RecyclerAdapterSkill(getActivity(), listFilter, new RecyclerAdapterSkill.RecyclerAdapterClick() {
            @Override
            public void getClick(int position) {
                if (flowLayout.getChildCount() < 10)
                    addSkillFlowLayout(position);
            }
        });
        adapter.notifyDataSetChanged();
        listViewSkills.setLayoutManager(new LinearLayoutManager(getActivity()));
        listViewSkills.setAdapter(adapter);
    }

    private void addSkillFlowLayout(int position) {
        if (flowLayout != null) {
            View view = getLayoutInflater().inflate(R.layout.custom_skill_text, null);
            final TextView textView = view.findViewById(R.id.tvCustomSkill);
            String skill = listFilter.get(position);
            textView.setText(listFilter.get(position));
            List<String> skills = SeeyanaApp.getInstance().getProvider().getSkills();
            skills.add(skill);
            Log.d(TAG, "User choose skill: " +skills);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(20, 10, 0, 0);
            textView.setLayoutParams(params);
            flowLayout.addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView textView1 = view.findViewById(R.id.tvCustomSkill);
                    String skill = textView1.getText().toString();
                    flowLayout.removeView(view);
                    skills.remove(skill);
                    Log.d(TAG, "The chosen skills: " + skills);
                    flowLayout.invalidate();
                    tvCounter.setText(flowLayout.getChildCount() + "/10");
                }
            });
            tvCounter.setText(flowLayout.getChildCount() + "/10");

        }
    }

    private void addSkill(int position) {
        TextView textView = new TextView(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 60);
        layoutParams.setMargins(20, 0, 20, 0);
        textView.setText(listFilter.get(position));
        textView.setTextColor(Color.WHITE);
        textView.setBackground(getResources().getDrawable(R.drawable.rect_background_skill));
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_cross_sim, 0, 0, 0);
        textView.setPadding(10, 0, 10, 0);
        textView.setLayoutParams(layoutParams);
        if ((layoutSkill1.getChildCount() + layoutSkill2.getChildCount() + layoutSkill3.getChildCount()) < 10) {
            if (layoutSkill1.getChildCount() < 4) {
                textView.setId(layoutSkill1.getChildCount());
                layoutSkill1.addView(textView);
            } else if (layoutSkill2.getChildCount() < 4) {
                textView.setId(layoutSkill2.getChildCount());
                layoutSkill2.addView(textView);
            } else if (layoutSkill3.getChildCount() < 4) {
                textView.setId(layoutSkill3.getChildCount());
                layoutSkill3.addView(textView);
            }
            tvCounter.setText(((layoutSkill1.getChildCount() + layoutSkill2.getChildCount()) + (layoutSkill3.getChildCount())) + "/10");

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (((ViewGroup) view.getParent()).getId() == R.id.required_skills_layout) {
                        layoutSkill1.removeView(view);
                        layoutSkill1.invalidate();
                    } else if (((ViewGroup) view.getParent()).getId() == R.id.layoutSkill2) {
                        layoutSkill2.removeView(view);
                        layoutSkill2.invalidate();
                    } else {
                        layoutSkill3.removeView(view);
                        layoutSkill3.invalidate();
                    }
                    tvCounter.setText(((layoutSkill1.getChildCount() + layoutSkill2.getChildCount()) + (layoutSkill3.getChildCount())) + "/10");
                }
            });
        }
    }


    private void init() {
        etSkill = view.findViewById(R.id.etSkillText);
        tvCounter = view.findViewById(R.id.tvTextCounter);
        layoutSkill1 = view.findViewById(R.id.required_skills_layout);
        layoutSkill2 = view.findViewById(R.id.layoutSkill2);
        layoutSkill3 = view.findViewById(R.id.layoutSkill3);
        listViewSkills = view.findViewById(R.id.skill_list_view);
        flowLayout = view.findViewById(R.id.flowLayout);

    }

    @Override
    public void onResume() {
        etSkill.addTextChangedListener(textWatcher);
        super.onResume();
    }

    @Override
    public void onPause() {
        etSkill.removeTextChangedListener(textWatcher);
        super.onPause();
    }
}
