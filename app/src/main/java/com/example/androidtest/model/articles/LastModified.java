package com.example.androidtest.model.articles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastModified {

    @SerializedName("date_db")
    @Expose
    private String dateDb;
    @SerializedName("month_year")
    @Expose
    private String monthYear;
    @SerializedName("time_passed")
    @Expose
    private String timePassed;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    public String getDateDb() {
        return dateDb;
    }

    public void setDateDb(String dateDb) {
        this.dateDb = dateDb;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public String getTimePassed() {
        return timePassed;
    }

    public void setTimePassed(String timePassed) {
        this.timePassed = timePassed;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}