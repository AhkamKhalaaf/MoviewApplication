package com.example.moviewapplication.models;

import androidx.annotation.Keep;

@Keep
public class ApiResponseData {
    Apiresponse apiresponse;
    Throwable throwable;
    String nodata;

    public ApiResponseData( String nodata) {
        this.apiresponse = null;
        this.throwable = null;
        this.nodata = nodata;
    }

    public ApiResponseData() {
    }

    public String getNodata() {
        return nodata;
    }

    public void setNodata(String nodata) {
        this.nodata = nodata;
    }

    public ApiResponseData(Apiresponse apiresponse ) {
        this.apiresponse = apiresponse;
        this.throwable = null;
    }

    public ApiResponseData( Throwable throwable) {
        this.apiresponse = null;
        this.throwable = throwable;
    }


    public Apiresponse getApiresponse() {
        return apiresponse;
    }

    public void setApiresponse(Apiresponse apiresponse) {
        this.apiresponse = apiresponse;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
