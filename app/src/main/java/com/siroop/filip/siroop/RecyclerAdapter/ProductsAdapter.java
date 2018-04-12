package com.siroop.filip.siroop.RecyclerAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.siroop.filip.siroop.DataBase.ProductsEntry;
import com.siroop.filip.siroop.Description.ProductAttributes;
import com.siroop.filip.siroop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by filip on 15.2.18.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private List<ProductAttributes> items;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name;
        public TextView price;
        public ImageView image;
        public FloatingActionButton like;




        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.product_name);
            price = view.findViewById(R.id.product_price);
            image = view.findViewById(R.id.product_image);
            like = view.findViewById(R.id.button_like);
            view.setOnClickListener(this);

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    ProductAttributes item = items.get(position);

                    String name = item.getName();
                    String image_url = item.getImage().getHighres();
                    String price = item.getPrice();
                    String url = item.getUrl();

                    ContentValues contentValues = new ContentValues();

                    contentValues.put(ProductsEntry.COLUMN_PRODUCT_NAME, name);
                    contentValues.put(ProductsEntry.COLUMN_PRODUCT_IMAGE_URL, image_url);
                    contentValues.put(ProductsEntry.COLUMN_PRODUCT_PRICE, price);
                    contentValues.put(ProductsEntry.COLUMN_PRODUCT_URL,url);

                    Uri uri = context.getContentResolver().insert(ProductsEntry.CONTENT_URI, contentValues);



                    if(uri != null) {
                        Toast.makeText(context,"Product Liked!",Toast.LENGTH_LONG).show();
                    }

                }
            });
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            ProductAttributes item = items.get(position);
            String url = "https://siroop.ch" + item.getUrl();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        }


    }


    public ProductsAdapter(Context context, List<ProductAttributes> items) {
        this.context =context;
        this.items = items;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ProductAttributes item = items.get(position);

        holder.name.setText(item.getName());


        String price = item.getPrice();
        double amount = Double.parseDouble(price);
        if(amount < 10000 ){
            DecimalFormat formatter = new DecimalFormat("##,##");
            holder.price.setText("Price: " + formatter.format(amount) + " CHF");
        }
        else if(amount > 10000){
            DecimalFormat formatter = new DecimalFormat("###,###,##");
            holder.price.setText("Price: " + formatter.format(amount) + " CHF");
        }


        Picasso.with(context).load(item.getImage().getHighres()).error(R.drawable.ic_error_black_24dp).into(holder.image);

    }
    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }


}
