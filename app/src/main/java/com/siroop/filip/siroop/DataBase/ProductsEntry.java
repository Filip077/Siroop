package com.siroop.filip.siroop.DataBase;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by filip on 21.2.18.
 */

public class ProductsEntry implements BaseColumns {
    public static final String AUTHORITY = "com.siroop.filip.siroop";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_PRODUCTS = "products";

    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_PRODUCTS).build();


    public static final String TABLE_NAME = "products";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_PRODUCT_IMAGE_URL = "product_image_url";
    public static final String COLUMN_PRODUCT_PRICE = "product_price";
    public static final String COLUMN_PRODUCT_URL = "product_url";
}
