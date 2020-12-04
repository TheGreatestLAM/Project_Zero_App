package com.andrii.project_zero.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.andrii.project_zero.Activities.OnBackPressedListener;
import com.andrii.project_zero.Model.MethodProperties;
import com.andrii.project_zero.Internet.NetworkService;
import com.andrii.project_zero.Model.Post;
import com.andrii.project_zero.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.andrii.project_zero.Fragments.CalculationFragment.API_KEY;
import static com.andrii.project_zero.Fragments.CalculationFragment.CITY_SENDER;
import static com.andrii.project_zero.Fragments.CalculationFragment.CITY_RECIPIENT;
import static com.andrii.project_zero.Fragments.CalculationFragment.postRespSender;
import static com.andrii.project_zero.Fragments.CalculationFragment.postRespRecipient;

public class FindCityFragment extends Fragment implements OnBackPressedListener
{
    EditText et1ffc;
    Button btn1ffc;

    static ArrayList<String> cities = new ArrayList<String>();

    int limit=32;
    MethodProperties methodPropertiesCity = new MethodProperties(limit);
    Post post = new Post(API_KEY, "Address", "searchSettlements", methodPropertiesCity);
    Post postResp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_city, container, false);

        et1ffc = (EditText) view.findViewById(R.id.et1ffc);
        btn1ffc = (Button) view.findViewById(R.id.btn1ffc);

        btn1ffc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(et1ffc.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(),
                            "Enter text!", Toast.LENGTH_SHORT)
                        .show();
                    return;
                }

                post.methodProperties.setCityName(et1ffc.getText().toString());
                new AsyncTaskFindCity().execute();
            }
        });

        return view;
    }

    @Override
    public void onBackPressed()
    {
        if(CITY_SENDER==1) CITY_SENDER=0;
        if(CITY_RECIPIENT==1) CITY_RECIPIENT=0;
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main, new CalculationFragment())
                .commit();
    }

    class AsyncTaskFindCity extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            NetworkService.getInstance()
                    .getJSONApi()
                    .postData(post)
                    .enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                            postResp = response.body();
                            int counter;

                            if(postResp.getSuccess()=="true")
                            {
                                if(postResp.getData(0).getTotalCount()==0)
                                {
                                    Toast.makeText(getActivity(),
                                            "Search error!", Toast.LENGTH_SHORT)
                                            .show();
                                }

                                else
                                {
                                    if(postResp.getData(0).getTotalCount()>limit)
                                    {
                                        counter = limit;
                                    }

                                    else
                                    {
                                        counter = postResp.getData(0).getTotalCount();
                                    }

                                    for(int i=0;i<counter;i++)
                                    {
                                        if(postResp.getData(0).getAddresses(i).getRegion().equals(""))
                                        {
                                            cities.add(i, postResp.getData(0).getAddresses(i).getSettlementTypeCode() + " " +
                                                    postResp.getData(0).getAddresses(i).getMainDescription() + " " +
                                                    postResp.getData(0).getAddresses(i).getArea() + " область ");
                                        }

                                        else
                                        {
                                            cities.add(i, postResp.getData(0).getAddresses(i).getSettlementTypeCode() + " " +
                                                    postResp.getData(0).getAddresses(i).getMainDescription() + " " +
                                                    postResp.getData(0).getAddresses(i).getArea() + " область " +
                                                    postResp.getData(0).getAddresses(i).getRegion() + " район");
                                        }
                                    }

                                    if(CITY_SENDER == 1)
                                        postRespSender=postResp;
                                    if(CITY_RECIPIENT == 1)
                                        postRespRecipient=postResp;

                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.activity_main, new FindCitySpinnerFragment())
                                            .commit();
                                }

                            }

                            else
                            {
                                Toast.makeText(getActivity(),
                                        "Search error!", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                            Toast.makeText(getActivity(),
                                    "Error occurred while getting request!", Toast.LENGTH_SHORT)
                                    .show();
                            t.printStackTrace();
                        }
                    });

            return null;
        }
    }
}


