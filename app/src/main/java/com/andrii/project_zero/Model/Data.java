package com.andrii.project_zero.Model;

import com.google.gson.annotations.SerializedName;

public class Data
{
    @SerializedName("AssessedCost")
    private int AssessedCost;

    @SerializedName("Cost")
    private int Cost;

    @SerializedName("CostRedelivery")
    private int CostRedelivery;

    @SerializedName("TotalCount")
    private int TotalCount;

    @SerializedName("Addresses")
    private Addresses Addresses[];

    @SerializedName("Description")
    private String Description;

    @SerializedName("Ref")
    private String Ref;

    public int getAssessedCost()
    {
        return AssessedCost;
    }

    public int getCost()
    {
        return Cost;
    }

    public int getCostRedelivery()
    {
        return CostRedelivery;
    }

    public int getTotalCount()
    {
        return TotalCount;
    }

    public com.andrii.project_zero.Model.Addresses getAddresses(int i)
    {
        return Addresses[i];
    }

    public String getDescription()
    {
        return Description;
    }

    public String getRef()
    {
        return Ref;
    }
}
