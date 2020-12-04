package com.andrii.project_zero.Model;

import com.google.gson.annotations.SerializedName;

public class MethodProperties
{
    @SerializedName("CitySender")
    private String CitySender;

    @SerializedName("CityRecipient")
    private String CityRecipient;

    @SerializedName("Weight")
    private double Weight;

    @SerializedName("ServiceType")
    private String ServiceType;

    @SerializedName("Cost")
    private int Cost;

    @SerializedName("CargoType")
    private String CargoType;

    @SerializedName("SeatsAmount")
    private int SeatsAmount;

    @SerializedName("CityName")
    private String CityName;

    @SerializedName("Limit")
    private int Limit;

    public MethodProperties()
    {
    }

    public MethodProperties(int limit)
    {
        Limit = limit;
    }

    public String getCitySender()
    {
        return CitySender;
    }

    public void setCitySender(String citySender)
    {
        CitySender = citySender;
    }

    public String getCityRecipient()
    {
        return CityRecipient;
    }

    public void setCityRecipient(String cityRecipient)
    {
        CityRecipient = cityRecipient;
    }

    public double getWeight()
    {
        return Weight;
    }

    public void setWeight(double weight)
    {
        Weight = weight;
    }

    public String getServiceType()
    {
        return ServiceType;
    }

    public void setServiceType(String serviceType)
    {
        ServiceType = serviceType;
    }

    public int getCost()
    {
        return Cost;
    }

    public void setCost(int cost)
    {
        Cost = cost;
    }

    public String getCargoType()
    {
        return CargoType;
    }

    public void setCargoType(String cargoType)
    {
        CargoType = cargoType;
    }

    public int getSeatsAmount()
    {
        return SeatsAmount;
    }

    public void setSeatsAmount(int seatsAmount)
    {
        SeatsAmount = seatsAmount;
    }

    public String getCityName()
    {
        return CityName;
    }

    public void setCityName(String cityName)
    {
        CityName = cityName;
    }

    public int getLimit()
    {
        return Limit;
    }

    public void setLimit(int limit)
    {
        Limit = limit;
    }
}
