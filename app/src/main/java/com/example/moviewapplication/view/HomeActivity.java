package com.example.moviewapplication.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviewapplication.InternetConnection;
import com.example.moviewapplication.R;
import com.example.moviewapplication.adapters.AutoSuggestAdapter;
import com.example.moviewapplication.adapters.MoviewSearchAdapter;
import com.example.moviewapplication.databinding.ActivityHomeBinding;
import com.example.moviewapplication.models.ApiResponseData;
import com.example.moviewapplication.models.Apiresponse;
import com.example.moviewapplication.viewmodel.AppViewmodel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    ActivityHomeBinding activityHomeBinding;
    AppViewmodel appViewmodel;
    private int Pagenumber = 1;
    private String Api_key = "b3070a5d3abfb7c241d2688d066914e7";
    List<String> Moviewnames;
    List<Apiresponse.OneMovie>listSearch;
    MoviewSearchAdapter moviewSearchAdapter;
    AutoSuggestAdapter autoSuggestAdapter;
    Handler handler;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    int totalpage;
    int nextpage=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        activityHomeBinding.setLifecycleOwner(this);
        //make shared prefrences to keep the of autocompletetextview when fron activity to anther
        sharedPreferences = getSharedPreferences("searchvale",Context.MODE_PRIVATE);
       // activityHomeBinding.autoCompleteTextView.setDropDownBackgroundResource();
        //get the suugestions when the text is changed
        activityHomeBinding.autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);

            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });
        // take the value selected of autocompletetextview
        activityHomeBinding.autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //hide the keyboard phone
                InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(HomeActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                String selectevalue = (String)parent.getItemAtPosition(position);
                activityHomeBinding.autoCompleteTextView.setText(selectevalue);
                loadDataSearch(Api_key , selectevalue,1);
               //save the search value in sharedprefreces
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("searchtext",selectevalue);
                editor.commit();



            }
        });

        //make action when user click search ih the phone keyboard
        activityHomeBinding.autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    //hide the dropdown menu
                    activityHomeBinding.autoCompleteTextView.dismissDropDown();
                    //hide the keyboard phone
                    InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(HomeActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    loadDataSearch(Api_key , activityHomeBinding.autoCompleteTextView.getText().toString(),1);
                    //save the search value in sharedprefreces
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("searchtext",activityHomeBinding.autoCompleteTextView.getText().toString());
                    editor.commit();
                    return true;
                }
                return false;
            }
        });

        //hander to autocompletetextview
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(activityHomeBinding.autoCompleteTextView.getText())) {
                     loadsuggestions(Api_key,activityHomeBinding.autoCompleteTextView.getText().toString(),1);
                    }
                }
                return false;
            }
        });


        activityHomeBinding.recycerviewsearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    NextPageLoad(Api_key,activityHomeBinding.autoCompleteTextView.getText().toString());

                }
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        String searchtext = sharedPreferences.getString("searchtext","");
        activityHomeBinding.autoCompleteTextView.setText(searchtext);
        loadDataSearch(Api_key , searchtext,1);
        activityHomeBinding.autoCompleteTextView.dismissDropDown();
        //empty the sharedprefrences
       // Toast.makeText(HomeActivity.this,"resume",Toast.LENGTH_SHORT).show();




    }



    @Override
    protected void onRestart() {
        super.onRestart();
        String searchtext = sharedPreferences.getString("searchtext","");
        activityHomeBinding.autoCompleteTextView.setText(searchtext);
        loadDataSearch(Api_key , searchtext,1);
        //hide the dropdown menu
        activityHomeBinding.autoCompleteTextView.dismissDropDown();
      // Toast.makeText(HomeActivity.this,"restart",Toast.LENGTH_SHORT).show();


    }

    public void  loadsuggestions(String apikey, String query , int number)
    {
        if (InternetConnection.Isconected(HomeActivity.this))
        {
            Moviewnames = new ArrayList<>();
            appViewmodel = ViewModelProviders.of(this).get(AppViewmodel.class);
            appViewmodel.getData(apikey,query,number).observe(this, new Observer<ApiResponseData>() {
                @Override
                public void onChanged(ApiResponseData apiResponseData ) {

                    if (apiResponseData.getApiresponse() !=null)
                    {
                        for (int i = 0; i <apiResponseData.getApiresponse().getResults().size() ; i++) {
                            Moviewnames.add(apiResponseData.getApiresponse().getResults().get(i).getTitle());
                        }
                        autoSuggestAdapter = new AutoSuggestAdapter(HomeActivity.this,
                                R.layout.dropdownmenuitem);
                        autoSuggestAdapter.setData(Moviewnames);
                        activityHomeBinding.autoCompleteTextView.setAdapter(autoSuggestAdapter);
                        activityHomeBinding.autoCompleteTextView.setThreshold(1);



                    }

                }
            });

        }
        else
        {
            activityHomeBinding.nodata.setVisibility(View.GONE);
            activityHomeBinding.loading.setVisibility(View.GONE);
            activityHomeBinding.datalayout.setVisibility(View.GONE);
            activityHomeBinding.nonternet.setVisibility(View.VISIBLE);
        }

    }

    public void loadDataSearch(String apikey,String query , int number)
    {
        if (InternetConnection.Isconected(HomeActivity.this))
        {
            activityHomeBinding.loading.setVisibility(View.VISIBLE);
            listSearch = new ArrayList<>();
            appViewmodel = ViewModelProviders.of(this).get(AppViewmodel.class);
            appViewmodel.getData(apikey,query,number).observe(this, new Observer<ApiResponseData>() {
                @Override
                public void onChanged(ApiResponseData apiResponseData) {
                    if (apiResponseData.getApiresponse()!=null && apiResponseData.getApiresponse().getTotal_results()>0)
                    {
                        totalpage = apiResponseData.getApiresponse().getTotal_pages();
                        listSearch = apiResponseData.getApiresponse().getResults();
                        moviewSearchAdapter = new MoviewSearchAdapter(listSearch,HomeActivity.this);
                        activityHomeBinding.recycerviewsearch.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                        activityHomeBinding.recycerviewsearch.hasFixedSize();
                        activityHomeBinding.recycerviewsearch.setAdapter(moviewSearchAdapter);
                        activityHomeBinding.datalayout.setVisibility(View.VISIBLE);
                        activityHomeBinding.loading.setVisibility(View.GONE);
                        activityHomeBinding.nodata.setVisibility(View.GONE);
                        activityHomeBinding.nonternet.setVisibility(View.GONE);


                    }
                    else
                    {
                        activityHomeBinding.nodata.setVisibility(View.VISIBLE);
                        activityHomeBinding.datalayout.setVisibility(View.GONE);
                        activityHomeBinding.loading.setVisibility(View.GONE);
                        activityHomeBinding.nonternet.setVisibility(View.GONE);


                    }

                 }
            });
        }
        else
        {
           activityHomeBinding.nodata.setVisibility(View.GONE);
            activityHomeBinding.loading.setVisibility(View.GONE);
            activityHomeBinding.datalayout.setVisibility(View.GONE);
            activityHomeBinding.nonternet.setVisibility(View.VISIBLE);
        }



    }


    public void NextPageLoad(String apikey,String query )
    {
        if (InternetConnection.Isconected(HomeActivity.this))
        {
            if (nextpage<totalpage)
            {
                nextpage = nextpage+1;
                activityHomeBinding.loading.setVisibility(View.VISIBLE);
                appViewmodel = ViewModelProviders.of(this).get(AppViewmodel.class);
                appViewmodel.getData(apikey,query,nextpage).observe(this, new Observer<ApiResponseData>() {
                    @Override
                    public void onChanged(ApiResponseData apiResponseData) {
                        if (apiResponseData.getApiresponse()!=null &&
                                apiResponseData.getApiresponse().getTotal_results()>0)
                        {

                            listSearch = apiResponseData.getApiresponse().getResults();
                            moviewSearchAdapter = new MoviewSearchAdapter(listSearch,HomeActivity.this);
                            activityHomeBinding.recycerviewsearch.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                            activityHomeBinding.recycerviewsearch.hasFixedSize();
                            moviewSearchAdapter.notifyDataSetChanged();
                            activityHomeBinding.recycerviewsearch.setAdapter(moviewSearchAdapter);
                            activityHomeBinding.datalayout.setVisibility(View.VISIBLE);
                            activityHomeBinding.loading.setVisibility(View.GONE);
                            activityHomeBinding.nodata.setVisibility(View.GONE);
                            activityHomeBinding.nonternet.setVisibility(View.GONE);
                            Toast.makeText(HomeActivity.this,"more data is loaded",Toast.LENGTH_SHORT).show();





                        }
                        else
                        {
                            activityHomeBinding.nodata.setVisibility(View.VISIBLE);
                            activityHomeBinding.datalayout.setVisibility(View.GONE);
                            activityHomeBinding.loading.setVisibility(View.GONE);
                            activityHomeBinding.nonternet.setVisibility(View.GONE);


                        }

                    }
                });
            }
            else
            {
                Toast.makeText(HomeActivity.this,"there is no more data  ",Toast.LENGTH_SHORT).show();

            }

        }
        else
        {
            activityHomeBinding.nodata.setVisibility(View.GONE);
            activityHomeBinding.loading.setVisibility(View.GONE);
            activityHomeBinding.datalayout.setVisibility(View.GONE);
            activityHomeBinding.nonternet.setVisibility(View.VISIBLE);
        }


    }

}
