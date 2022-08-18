package model;


public class Division
{
    private int divisionId;
    private String divisionName;
    private int countryId;


    public Division (int divisionId, String divisionName, int countryId)
    {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }


    public int getDivisionId() {
        return divisionId;
    }

    public String getName() {
        return divisionName;
    }

    public int getCountryId() {
        return countryId;
    }


    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString()
    {
        return (divisionName);
    }

}