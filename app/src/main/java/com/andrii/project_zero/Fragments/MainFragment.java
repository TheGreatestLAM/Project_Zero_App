package com.andrii.project_zero.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.AsyncTask;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.andrii.project_zero.Model.NetworkService;
import com.andrii.project_zero.Model.Post;
import com.andrii.project_zero.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.andrii.project_zero.Fragments.CalculationFragment.REQUEST_STATUS;
import static com.andrii.project_zero.Fragments.CalculationFragment.CargoTypes;
import static com.andrii.project_zero.Fragments.CalculationFragment.postCargoTypes;
import static com.andrii.project_zero.Fragments.CalculationFragment.postServiceTypes;

public class MainFragment extends Fragment
{
    Button btn1fm;

    private int FLAG=-1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        btn1fm = (Button) view.findViewById(R.id.btn1fm);

        if(REQUEST_STATUS==0)
        {
            new AsyncTaskMain().execute();
            REQUEST_STATUS=1;
        }

        btn1fm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_main, new CalculationFragment())
                        .commit();
            }
        });

        return view;
    }

    class AsyncTaskMain extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            NetworkService.getInstance()
                    .getJSONApi()
                    .postData(postServiceTypes)
                    .enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                            postServiceTypes = response.body();

                            if(postServiceTypes.getSuccess()=="false")
                            {
                                FLAG=0;
                                publishProgress(FLAG);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                            FLAG=1;
                            publishProgress(FLAG);
                            t.printStackTrace();
                        }
                    });

            NetworkService.getInstance()
                    .getJSONApi()
                    .postData(postCargoTypes)
                    .enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                            postCargoTypes = response.body();

                            if(postCargoTypes.getSuccess()=="true")
                            {
                                for(int i=0;i<postCargoTypes.getDataArrayLength()-2;i++)
                                {
                                    CargoTypes.add(i, postCargoTypes.getData(i).getDescription());
                                }
                            }

                            else
                            {
                                FLAG=0;
                                publishProgress(FLAG);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                            FLAG=1;
                            publishProgress(FLAG);
                            t.printStackTrace();
                        }
                    });

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            switch (FLAG)
            {
                case 0:
                    Toast.makeText(getActivity(),
                            "Request error!", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case 1:
                    Toast.makeText(getActivity(),
                            "Error occurred while getting request!", Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }
    }
}
