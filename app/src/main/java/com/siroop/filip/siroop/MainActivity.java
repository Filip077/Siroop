package com.siroop.filip.siroop;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import com.siroop.filip.siroop.ContentProvider.MySuggestionProvider;
import com.siroop.filip.siroop.Description.ProductAttributes;
import com.siroop.filip.siroop.RecyclerAdapter.ProductsAdapter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private Button search_button;
//    private String BASE_URL = "https://api.siroop.ch/";
    private BottomNavigationView navigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        recyclerView = findViewById(R.id.recycler_view);
        search_button = findViewById(R.id.search_button);



//
        navigationView = findViewById(R.id.bottom_nav);






        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.siroop_menu:
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse("https://siroop.ch"));
                        startActivity(i);
                    case R.id.search_menu:
                        return true;

                    case R.id.liked_activity:
                        Intent intent = new Intent(MainActivity.this, LikedProducts.class);
                        startActivity(intent);

                }
                return true;
            }

        });



         final SearchView search_editText = findViewById(R.id.simpleSearchView);
        search_editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);



        search_button.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(search_editText.getQuery().toString().length() == 0){
//                    search_editText.error("Please enter product name");
                }
                else {
                    Intent i = new Intent(MainActivity.this, DisplayProducts.class);
                    i.putExtra("search_term", search_editText.getQuery().toString());
                    startActivity(i);
                }
            }
//
        });
    }

}

