package com.siroop.filip.siroop;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.siroop.filip.siroop.BottomNavigation.BottomNavigationViewBehavior;
import com.siroop.filip.siroop.Description.ProductAttributes;
import com.siroop.filip.siroop.GET.SiroopProduct;
import com.siroop.filip.siroop.RecyclerAdapter.ProductsAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisplayProducts extends AppCompatActivity {
    private ArrayList<ProductAttributes> productList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProductsAdapter mAdapter;
    private String BASE_URL = "https://api.siroop.ch/";
    private BottomNavigationView navigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_products);

        recyclerView = findViewById(R.id.recycler_view);



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        navigationView = findViewById(R.id.bottom_nav);

        Intent i = getIntent();
        String search = i.getStringExtra("search_term");





        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.siroop_menu:
                        String url = "https://siroop.ch";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                        finish();


                    case R.id.search_menu:
                        Intent intent1 = new Intent(DisplayProducts.this, MainActivity.class);
                        startActivity(intent1);
                        finish();
                        return true;

                    case R.id.liked_activity:
                        Intent intent = new Intent(DisplayProducts.this, LikedProducts.class);
                        startActivity(intent);
                        finish();
                        return true;
                }
                return false;
            }

        });

            getProducts(search);
    }


    public void getProducts(String search){
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(loggingInterceptor);
        }


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build());

        Retrofit retrofit = builder.build();

        SiroopProduct product = retrofit.create(SiroopProduct.class);

        Call<List<ProductAttributes>> call = product.products(search, 100,
                "b8bXpvFLIs64gZtqnCDAHalMBdhaBRxf4OM4BvyA");


        final ProgressDialog dialog = new ProgressDialog(DisplayProducts.this,ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        call.enqueue(new Callback<List<ProductAttributes>>() {
            @Override
            public void onResponse(Call<List<ProductAttributes>> call, Response<List<ProductAttributes>> response) {


                if(!response.isSuccessful() || response.body().isEmpty()){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(DisplayProducts.this);
                    builder1.setTitle("No results")
                            .setMessage("Your search term doesn't match any product.")
                            .setCancelable(false)
                            .setIcon(R.drawable.ic_error_black_24dp)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(DisplayProducts.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .show();
                    if(dialog.isShowing()) {
                        dialog.dismiss();
                    }

                }else {
                    List<ProductAttributes> prod = response.body();
                    recyclerView.setAdapter(new ProductsAdapter(DisplayProducts.this, prod));
                    if(dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }


            }

            @Override
            public void onFailure(Call<List<ProductAttributes>> call, Throwable t) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(DisplayProducts.this);
                builder1.setTitle("Network problems")
                        .setMessage("Check your internet connection and search again.")
                        .setCancelable(false)
                        .setIcon(R.drawable.ic_error_black_24dp)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(DisplayProducts.this,MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
                if(dialog.isShowing()) {
                    dialog.dismiss();
                }

            }
        });
    }

}








