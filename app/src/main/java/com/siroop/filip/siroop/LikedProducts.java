package com.siroop.filip.siroop;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;

import com.siroop.filip.siroop.BottomNavigation.BottomNavigationViewBehavior;
import com.siroop.filip.siroop.DataBase.ProductsEntry;
import com.siroop.filip.siroop.Description.ProductAttributes;
import com.siroop.filip.siroop.RecyclerAdapter.LikedProductsAdapter;

import java.util.ArrayList;

public class LikedProducts extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = LikedProducts.class.getSimpleName();
    private RecyclerView recyclerView;
    private LikedProductsAdapter mAdapter;
    private BottomNavigationView navigationView;
    private static final int PRODUCT_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_products);

        recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new LikedProductsAdapter(LikedProducts.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int id = (int) viewHolder.itemView.getTag();
                String stringId = Integer.toString(id);
                Uri uri = ProductsEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();
                getContentResolver().delete(uri, null, null);
                getSupportLoaderManager().restartLoader(PRODUCT_LOADER_ID, null, LikedProducts.this);

            }
        }).attachToRecyclerView(recyclerView);






        navigationView = findViewById(R.id.bottom_nav);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.siroop_menu:
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse("https://siroop.ch"));
                        startActivity(i);

                    case R.id.liked_activity:
                    return true;

                    case R.id.search_menu:
                        Intent intent = new Intent(LikedProducts.this,MainActivity.class);
                        startActivity(intent);
                }
                return true;
            }

        });

        navigationView.getMenu().getItem(1).setChecked(true);


        getSupportLoaderManager().initLoader(PRODUCT_LOADER_ID, null, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(PRODUCT_LOADER_ID, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs) {
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mProductData = null;

            @Override
            protected void onStartLoading() {
                if (mProductData != null) {
                    deliverResult(mProductData);
                } else {
                    forceLoad();

                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(ProductsEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                mProductData = data;
                super.deliverResult(data);
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}



