package com.example.faizan.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faizan on 10/11/17.
 */
public class ProductDBHelper extends SQLiteOpenHelper {

    static final int DATABASE_VERSION = 1;

    static final String TABLE = "products";

    static final String CREATE_STATEMENT = "CREATE TABLE products (\n" +
            "      productID integer primary key autoincrement,\n" +
            "      name text not null,\n" +
            "      description text null,\n" +
            "      price decimal(10,5) null\n" +
            ")\n";

    public ProductDBHelper(Context context) {
        super(context, TABLE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create the database, using CREATE SQL statement
        sqLiteDatabase.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void close(){
        if(this != null){
            this.close();
        }
    }

    /*
     *@param name name of product
     *@param description description of the product
     *@param price the price of the product
     */
    public Product createProduct(String name,String description,double price) {

        // create a new entity object (Product)
        Product product = new Product(name, description, price);

        // put that data into the database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put("name", name);
        newValues.put("description", description);
        newValues.put("price", price);

        int id = (int) db.insert(TABLE, null, newValues);

        // update the products id
        product.setProductID(id);

        return product;
    }

    /*
     *@param id the id of the product to delete
     */
    public boolean deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int numRows = db.delete(TABLE, "productID = ?", new String[] { "" + id });

        return (numRows == 1);
    }


    /*
     *@return an ArrayList containing all the products
     */
    public List<Product> getAllProducts() {
        List<Product> productsArray = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {"productID", "name", "description", "price"};
        Cursor cursor = db.query(TABLE, columns, null, null, null, null, "productID");

        cursor.moveToFirst();

        do {
            if (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                double price = cursor.getDouble(3);

                Product product = new Product(name, description, price);
                product.setProductID(id);

                productsArray.add(product);
            }

            cursor.moveToNext();
        } while (!cursor.isAfterLast());

        Log.i("SQLite", "getAllProducts(): num = " + productsArray.size());

        return productsArray;
    }
}
