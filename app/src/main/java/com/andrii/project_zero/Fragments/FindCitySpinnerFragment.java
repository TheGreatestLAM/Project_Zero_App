package com.andrii.project_zero.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.andrii.project_zero.Activities.OnBackPressedListener;
import com.andrii.project_zero.R;

import static com.andrii.project_zero.Fragments.FindCityFragment.cities;
import static com.andrii.project_zero.Fragments.CalculationFragment.CITY_SENDER;
import static com.andrii.project_zero.Fragments.CalculationFragment.CITY_RECIPIENT;
import static com.andrii.project_zero.Fragments.CalculationFragment.idCitySender;
import static com.andrii.project_zero.Fragments.CalculationFragment.idCityRecipient;

public class FindCitySpinnerFragment extends Fragment implements OnBackPressedListener
{
    Button btn1ffcs;
    Spinner sp1ffcs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_city_spinner, container, false);

        btn1ffcs = (Button) view.findViewById(R.id.btn1ffcs);
        sp1ffcs = (Spinner) view.findViewById(R.id.sp1ffcs);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1ffcs.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(CITY_SENDER == 1) idCitySender = (int) id;
                if(CITY_RECIPIENT == 1) idCityRecipient = (int) id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        sp1ffcs.setOnItemSelectedListener(itemSelectedListener);

        btn1ffcs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                cities.clear();
                if(CITY_SENDER==1) CITY_SENDER=0;
                if(CITY_RECIPIENT==1) CITY_RECIPIENT=0;
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_main, new CalculationFragment())
                        .commit();
            }
        });

        return view;
    }

    @Override
    public void onBackPressed()
    {
        idCitySender = -1;
        idCityRecipient = -1;
        cities.clear();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main, new FindCityFragment())
                .commit();
    }
}
