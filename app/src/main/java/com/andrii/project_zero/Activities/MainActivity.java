package com.andrii.project_zero.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.andrii.project_zero.Fragments.MainFragment;
import com.andrii.project_zero.R;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main, new MainFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed()
    {
        FragmentManager fm = getSupportFragmentManager();
        OnBackPressedListener backPressedListener = null;
        for (Fragment fragment: fm.getFragments())
        {
            try
            {
                backPressedListener = (OnBackPressedListener) fragment;
                break;
            }

            catch (Exception e)
            {}

//            if (fragment instanceof  OnBackPressedListener)
//            {
//                backPressedListener = (OnBackPressedListener) fragment;
//                break;
//            }
        }

        if (backPressedListener != null)
        {
            backPressedListener.onBackPressed();
        }

        else
        {
            super.onBackPressed();
        }
    }
}