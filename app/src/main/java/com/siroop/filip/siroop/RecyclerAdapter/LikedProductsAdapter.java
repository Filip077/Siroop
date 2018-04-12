package com.siroop.filip.siroop.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.siroop.filip.siroop.DataBase.ProductsEntry;
import com.siroop.filip.siroop.Description.ProductAttributes;
import com.siroop.filip.siroop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by filip on 21.2.18.
 */

public class LikedProductsAdapter extends RecyclerView.Adapter<LikedProductsAdapter.MyViewHolder> {

    private Cursor mCursor;
    private Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView price;
        public ImageView image;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.product_name);
            price = view.findViewById(R.id.product_price);
            image = view.findViewById(R.id.product_image);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
           int urlIndex = mCursor.getColumnIndex(ProductsEntry.COLUMN_PRODUCT_URL);
           mCursor.moveToPosition(getAdapterPosition());
            String url = "https://siroop.ch" + mCursor.getString(urlIndex);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            mContext.startActivity(i);
        }
    }


    public LikedProductsAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.liked_products_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        int idIndex = mCursor.getColumnIndex(ProductsEntry._ID);
        int NameIndex = mCursor.getColumnIndex(ProductsEntry.COLUMN_PRODUCT_NAME);
        int Image_URL_Index = mCursor.getColumnIndex(ProductsEntry.COLUMN_PRODUCT_IMAGE_URL);
        int PriceIndex = mCursor.getColumnIndex(ProductsEntry.COLUMN_PRODUCT_PRICE);

        mCursor.moveToPosition(position);

        int id = mCursor.getInt(idIndex);

        String name = mCursor.getString(NameIndex);
        String image_url = mCursor.getString(Image_URL_Index);
        String price = mCursor.getString(PriceIndex);

        holder.itemView.setTag(id);

        holder.name.setText(name);

        double amount = Double.parseDouble(price);
        if(amount < 10000 ){
            DecimalFormat formatter = new DecimalFormat("##,##");
            holder.price.setText("Price: " + formatter.format(amount) + " CHF");
        }
        else if(amount > 10000){
            DecimalFormat formatter = new DecimalFormat("###,###,##");
            holder.price.setText("Price: " + formatter.format(amount) + " CHF");
        }


        Picasso.with(mContext).load(image_url).into(holder.image);


    }


    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        if (mCursor == c) {
            return null;
        }
        Cursor temp = mCursor;
        this.mCursor = c;

        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

}
