package com.example.faizan.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by faizan on 15/11/17.
 */

public class AddProductActivity extends AppCompatActivity {

    EditText productName;
    EditText description;
    EditText priceCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_activity);
    }


    public void save(View view) {
        productName = (EditText) findViewById(R.id.add_product_txt);
        description = (EditText) findViewById(R.id.add_description_txt);
        priceCad = (EditText) findViewById(R.id.add_price_cad_txt);


        ProductDBHelper helper = new ProductDBHelper(this);
        helper.createProduct(productName.getText().toString(),
                description.getText().toString(),Double.parseDouble((priceCad.getText()).toString()));

        productName.setText("");
        description.setText("");
        priceCad.setText("");

        // close the activity
        this.finish();
    }

    public void clear(View view) {
        productName = (EditText) findViewById(R.id.add_product_txt);
        description = (EditText) findViewById(R.id.add_description_txt);
        priceCad = (EditText) findViewById(R.id.add_price_cad_txt);

        productName.setText("");
        description.setText("");
        priceCad.setText("");

        // close the activity
        this.finish();
    }
}
