package com.siroop.filip.siroop.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by filip on 21.2.18.
 */

public class ProductsDbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME ="products.db";
        private static final int DATABASE_VERSION = 1;


    public ProductsDbHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_PRODUCTS_TABLE ="CREATE TABLE " + ProductsEntry.TABLE_NAME + " (" +
                                ProductsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                ProductsEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, " +
                                ProductsEntry.COLUMN_PRODUCT_IMAGE_URL + " TEXT NOT NULL, " +
                                ProductsEntry.COLUMN_PRODUCT_PRICE + " TEXT NOT NULL, " +
                                ProductsEntry.COLUMN_PRODUCT_URL + " TEXT NOT NULL" +
                                "); " ;
        sqLiteDatabase.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ProductsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
