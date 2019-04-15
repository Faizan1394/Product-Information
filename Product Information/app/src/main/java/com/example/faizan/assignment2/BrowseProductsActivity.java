package com.example.faizan.assignment2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/*
 *Muhammad Faizan Ali(100518916)
 *
 */

public class BrowseProductsActivity extends AppCompatActivity {

    private ProductDBHelper helper;
    private ArrayList<Product> products;
    EditText productName;
    EditText description;
    EditText priceCad;
    EditText priceBit;

    Button prev;
    Button next;

    URL  url;

    int currentProduct = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_products);

        // get all the edit text views
        productName = (EditText) findViewById(R.id.product_txt);
        description = (EditText) findViewById(R.id.description_txt);
        priceCad = (EditText) findViewById(R.id.price_cad_txt);
        priceBit = (EditText) findViewById(R.id.price_bit_txt);

        // make all the edit text views uneditable
        productName.setEnabled(false);
        description.setEnabled(false);
        priceCad.setEnabled(false);
        priceBit.setEnabled(false);

        // create instance of ProductDBHelper class
        helper = new ProductDBHelper(this);

        next = (Button) findViewById(R.id.next_btn);
        prev = (Button) findViewById(R.id.prev_btn);

    }

    @Override
    protected void onStart() {
        super.onStart();

        //get all the products in the database and save them to the products arraylist
        products = (ArrayList<Product>) helper.getAllProducts();

        // disable next and prev buttons
        next.setEnabled(false);
        prev.setEnabled(false);


        if(currentProduct > 0)
            prev.setEnabled(true);

        // if there is atleast 1 product in the database
        if(products.size() > 0){

            //call the showProduct method with the first product as argument
            showProduct(products.get(currentProduct));

            // if there is more than 1 product in the database
            // enable the next button
            if(products.size() > 1 && currentProduct != products.size()-1) {
                next.setEnabled(true);
            }
        }
    }

    public void bitPrice(double price){

        priceBit.setText(String.valueOf(price));
    }


    public void showProduct(Product product){

        productName.setText(product.getName());
        description.setText(product.getDescription());
        priceCad.setText("$" +product.getPrice());

        // url used to find bitcoin value of the price
        try {
            url = new URL("https://blockchain.info/tobtc?currency=CAD&value=" + String.valueOf(product.getPrice()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // download the bit coin value of the price
        downloadBitPrice bitPrice = new downloadBitPrice(this);
        bitPrice.execute(new URL[] {url});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // if the add menu item is selected
        if(item.getItemId() == R.id.save_product) {

            // call the add product activity
            Intent intent = new Intent(this, AddProductActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);



    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void delete(View view) {
        helper.deleteProduct(products.get(currentProduct).getProductID());
        products.remove(currentProduct);

        if(currentProduct == products.size())
            currentProduct--;

        prev.setEnabled(true);
        next.setEnabled(true);

        if(currentProduct == 0)
            prev.setEnabled(false);

        if(currentProduct == products.size()-1)
            next.setEnabled(false);

        products = (ArrayList<Product>) helper.getAllProducts();
        showProduct(products.get(currentProduct));
    }

    public void prevBtnClick(View view) {
        currentProduct--;
        showProduct(products.get(currentProduct));
        next.setEnabled(true);

        if(currentProduct == 0){
            // disable prev buttons
            prev.setEnabled(false);
        }
    }

    public void nextBtnClick(View view) {
        currentProduct++;
        showProduct(products.get(currentProduct));
        prev.setEnabled(true);
        if(currentProduct == products.size()-1){
            // disable next buttons
            next.setEnabled(false);

        }
    }
}
