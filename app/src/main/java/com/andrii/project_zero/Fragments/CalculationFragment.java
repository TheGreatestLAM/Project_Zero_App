package com.andrii.project_zero.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.andrii.project_zero.Activities.OnBackPressedListener;
import com.andrii.project_zero.Model.MethodProperties;
import com.andrii.project_zero.Model.NetworkService;
import com.andrii.project_zero.Model.Post;
import com.andrii.project_zero.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalculationFragment extends Fragment implements OnBackPressedListener
{
    Button btn1fc, btn2fc, btn3fc;
    EditText et1fc, et2fc, et3fc;
    TextView tv8fc;
    Spinner sp1fc;

    static int REQUEST_STATUS=0;
    static final String API_KEY = "bae36b024d30b4f7a74f6b0e5176b41b";
    static int CITY_SENDER=0; // флаг города отправителя
    static int CITY_RECIPIENT=0; // флаг города получателя
    static int idCitySender=-1; // id города отправителя
    static int idCityRecipient=-1; // id города получателя
    static Post postRespSender, postRespRecipient;
    static Post postServiceTypes = new Post(API_KEY, "Common", "getServiceTypes");
    static Post postCargoTypes = new Post(API_KEY, "Common", "getCargoTypes");
    static ArrayList<String> CargoTypes = new ArrayList<String>();

    private int FLAG=-1;
    int idCargoType;
    String strSender, strRecipient;
    MethodProperties methodProperties = new MethodProperties();
    Post postCalc = new Post(API_KEY, "InternetDocument", "getDocumentPrice", methodProperties);
    Post[] postRes = new Post[4];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculation, container, false);

        btn1fc = (Button) view.findViewById(R.id.btn1fc);
        btn2fc = (Button) view.findViewById(R.id.btn2fc);
        btn3fc = (Button) view.findViewById(R.id.btn3fc);
        et1fc = (EditText) view.findViewById(R.id.et1fc);
        et2fc = (EditText) view.findViewById(R.id.et2fc);
        et3fc = (EditText) view.findViewById(R.id.et3fc);
        tv8fc = (TextView) view.findViewById(R.id.tv8fc);
        sp1fc = (Spinner) view.findViewById(R.id.sp1fc);

        postCalc.methodProperties.setCost(300); // значение по умолчанию

        if(idCitySender!=-1)
        {
            strSender = postRespSender.getData(0).getAddresses(idCitySender).getSettlementTypeCode() + " " +
                    postRespSender.getData(0).getAddresses(idCitySender).getMainDescription();
            btn1fc.setText(strSender);
        }

        if(idCityRecipient!=-1)
        {
            strRecipient = postRespRecipient.getData(0).getAddresses(idCityRecipient).getSettlementTypeCode() + " " +
                    postRespRecipient.getData(0).getAddresses(idCityRecipient).getMainDescription();
            btn2fc.setText(strRecipient);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, CargoTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1fc.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCargoType = (int) id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        sp1fc.setOnItemSelectedListener(itemSelectedListener);

        btn1fc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CITY_SENDER = 1;
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_main, new FindCityFragment())
                        .commit();
            }
        });

        btn2fc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CITY_RECIPIENT = 1;
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_main, new FindCityFragment())
                        .commit();
            }
        });

        btn3fc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tv8fc.setText("");

                if(idCitySender==-1)
                {
                    Toast.makeText(getActivity(),
                            "Enter sender city!", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                if(idCityRecipient==-1)
                {
                    Toast.makeText(getActivity(),
                            "Enter recipient city!", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                if(et1fc.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(),
                            "Enter weight!", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                if(et2fc.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(),
                            "Enter seats amount!", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                postCalc.methodProperties.setCitySender(postRespSender.getData(0).getAddresses(idCitySender).getDeliveryCity());
                postCalc.methodProperties.setCityRecipient(postRespRecipient.getData(0).getAddresses(idCityRecipient).getDeliveryCity());
                postCalc.methodProperties.setCargoType(postCargoTypes.getData(idCargoType).getRef());
                postCalc.methodProperties.setWeight(Double.parseDouble(et1fc.getText().toString()));
                postCalc.methodProperties.setSeatsAmount(Integer.parseInt(et2fc.getText().toString()));

                if(!et3fc.getText().toString().equals(""))
                {
                    postCalc.methodProperties.setCost(Integer.parseInt(et3fc.getText().toString()));
                }

                new AsyncTaskCalculation().execute();
            }
        });

        return view;
    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main, new MainFragment())
                .commit();
    }

    class AsyncTaskCalculation extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            for(int i=0; i<postServiceTypes.getDataArrayLength(); i++)
            {
                final int finalI = i;
                postCalc.methodProperties.setServiceType(postServiceTypes.getData(finalI).getRef());
                NetworkService.getInstance()
                        .getJSONApi()
                        .postData(postCalc)
                        .enqueue(new Callback<Post>() {
                            @Override
                            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                                postRes[finalI] = response.body();

                                if(postRes[finalI].getSuccess()=="true")
                                {
                                    tv8fc.append(postServiceTypes.getData(finalI).getDescription() + " ");
                                    tv8fc.append(postRes[finalI].getData(0).getCost() + " грн" + "\n" + "\n");
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
            }

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
