package com.andrii.project_zero.Model;

import com.google.gson.annotations.SerializedName;

public class Addresses
{
    @SerializedName("Warehouses")
    private int Warehouses;

    @SerializedName("MainDescription")
    private String MainDescription;

    @SerializedName("Area")
    private String area;

    @SerializedName("Region")
    private String Region;

    @SerializedName("SettlementTypeCode")
    private String SettlementTypeCode;

    @SerializedName("Ref")
    private String Ref;

    @SerializedName("DeliveryCity")
    private String DeliveryCity;

    public int getWarehouses()
    {
        return Warehouses;
    }

    public String getMainDescription()
    {
        return MainDescription;
    }

    public String getArea()
    {
        return area;
    }

    public String getRegion()
    {
        return Region;
    }

    public String getSettlementTypeCode()
    {
        return SettlementTypeCode;
    }

    public String getRef()
    {
        return Ref;
    }

    public String getDeliveryCity()
    {
        return DeliveryCity;
    }
}
