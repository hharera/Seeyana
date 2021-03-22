package com.example.n_one.FragmentProgressTwo;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.example.n_one.R;
import com.example.n_one.SeeyanaApp;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragFifthProTwo extends Fragment {
private static final String TAG = "FragFifthProTwo";
    private Spinner mSpinnerCountry;
    private Spinner mSpinnerCity;
    private CheckBox mCheckBoxWillTravel;

    private List<String> countries;
    private List<String> cities;

    public FragFifthProTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_frag_fifth_pro_two, container, false);
        init(v);
        return v;
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("countries.json");
            int size = is.available();
            byte[]buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void init(View v) {
        assignCountriesFromJSON();
        countries.add(0, "Select Country");
        mSpinnerCountry = v.findViewById(R.id.city_spinner);
        mSpinnerCountry.setPrompt("Select Country");
        mSpinnerCity = v.findViewById(R.id.area_spinner);
        mSpinnerCity.setPrompt("Select City");
        mCheckBoxWillTravel = v.findViewById(R.id.will_travel_check_box);

        ArrayAdapter<String> adapterCountries = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                countries);
        adapterCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCountry.setAdapter(adapterCountries);
        mSpinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    try {
                        String Country = String.valueOf(adapterView.getItemAtPosition(i));
                            assignCitiesFromJSON(Country);
                                Log.d(TAG, "User chose Country: " + Country);
                                SeeyanaApp.getInstance().getProvider().setCountry(Country);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        cities = new ArrayList<>();
                    }
                } else {
                    SeeyanaApp.getInstance().getProvider().setCountry(null);
                    cities = new ArrayList<>();
                    Log.d(TAG, "User chose Country: " + null);
                }

                if (cities == null) {
                    cities = new ArrayList<>();
                }

                cities.add(0, "Select City");
                ArrayAdapter<String> adapterCities = new ArrayAdapter<>(
                        getContext(),
                        android.R.layout.simple_spinner_item,
                        cities);
                adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerCity.setAdapter(adapterCities);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    String city = String.valueOf(adapterView.getItemAtPosition(i));

                        Log.d(TAG, "User chose city: " + city);
                        SeeyanaApp.getInstance().getProvider().setCity(city);
                } else {
                    SeeyanaApp.getInstance().getProvider().setCity("");
                    Log.d(TAG, "User chose city: " + null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mCheckBoxWillTravel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
          if (b) {
              Log.d(TAG, "User can travel");
          } else {
              Log.d(TAG, "User can't travel");
          }
                    SeeyanaApp.getInstance().getProvider().setWillingTravel(b);

            }
        });
    }

    @NotNull
    private void assignCitiesFromJSON(String city) throws JSONException {
        JSONObject obj = new JSONObject(loadJSONFromAsset());
        JSONArray jsonArrayCities = obj.getJSONArray(city);
        if (cities == null) {
            cities = new ArrayList<>();
        }
        for (int j = 0; j < jsonArrayCities.length() ; j++) {
            cities.add(jsonArrayCities.getString(j));
        }
    }

    private void assignCountriesFromJSON() {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            Iterator<String> keys = obj.keys();
            countries = new ArrayList<>();
            while (keys.hasNext()) {
                Log.d(TAG, "Country: " + keys.next());
                countries.add(keys.next());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
