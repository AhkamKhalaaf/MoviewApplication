package com.example.moviewapplication.viewmodel;

import android.util.Log;

import com.example.moviewapplication.api.Apiinterface;
import com.example.moviewapplication.api.Retrofitclient;
import com.example.moviewapplication.models.ApiResponseData;
import com.example.moviewapplication.models.Apiresponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppViewmodel extends ViewModel {
    MutableLiveData<ApiResponseData>mutableLiveData;
    private Apiinterface apiinterface;
    private ApiResponseData apiResponseData;



    public LiveData<ApiResponseData>getData(String api_key , String Query_search , int Page)
    {
        apiinterface = Retrofitclient.getmInstatnace().getApi();
        apiResponseData = new ApiResponseData();
        mutableLiveData = new MutableLiveData<>();
        Call<Apiresponse> call = apiinterface.getDatamovies(api_key,Query_search,Page);
        call.enqueue(new Callback<Apiresponse>() {
            @Override
            public void onResponse(Call<Apiresponse> call, Response<Apiresponse> response) {
                if (response.isSuccessful() && response.body()!=null && response.body().getResults().size()>0 && response.body().getTotal_results()>0)
                {
                   apiResponseData.setApiresponse(response.body());
                   mutableLiveData.postValue(apiResponseData);
                    Log.d("SYCCESSS","succ");
                }
                else
                {

                    apiResponseData.setNodata("nodata");
                    mutableLiveData.postValue(apiResponseData);
                    Log.d("SYCCESSS","np dataa ");
                }
            }

            @Override
            public void onFailure(Call<Apiresponse> call, Throwable t) {
               apiResponseData.setThrowable(t);
               mutableLiveData.postValue(apiResponseData);

            }
        });
        return mutableLiveData;
    }
}
