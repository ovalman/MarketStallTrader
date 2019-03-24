package uk.co.loomknitting.marketstalltrader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="market_stall";
    public static final int DATABASE_VERSION=1;

    public static final String SALES_TABLE="sales_table";
    public static final String INVENTORY_TABLE="inventory_table";
    public static final String COMPANY_TABLE="company_table";


    public static final String AMOUNT_PAID=" amount_paid,";
    public static final String DATE_TIME_MILLIS=" date_time_millis,";
    public static final String PAYMENT_METHOD=" payment_method";
    private static final String SALES= "create table " + SALES_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + AMOUNT_PAID + DATE_TIME_MILLIS + PAYMENT_METHOD + ")";

    public static final String OBJECT=" object,";
    public static final String DESCRIPTION=" description,";
    public static final String COST=" cost";
    private static final String INVENTORY= "create table " + INVENTORY_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + OBJECT + DESCRIPTION + COST + ")";

    public static final String NAME=" name,";
    public static final String ADDRESS1=" address1,";
    public static final String ADDRESS2=" address2,";
    public static final String CITY=" city,";
    public static final String ZIP=" zip,";
    public static final String PHONE=" phone,";
    public static final String EMAIL=" email";
    private static final String COMPANY= "create table " + COMPANY_TABLE + "(_id INTEGER PRIMARY KEY NOT NULL, " + NAME + ADDRESS1 + ADDRESS2 + CITY + ZIP + PHONE + EMAIL + ")";


    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SALES);
        db.execSQL(INVENTORY);
        db.execSQL(COMPANY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SALES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INVENTORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + COMPANY_TABLE);

        onCreate(db);
    }

    public void insertIntoSalesDB (String amountPaid, String dateTimeMillisPaid, Integer paymentMethod ){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("amountpaid", amountPaid);
        values.put("datetimemillispaid", dateTimeMillisPaid);
        values.put("paymentmethod", paymentMethod);

        db.insert(SALES_TABLE, null, values);
        db.close();
    }
    public void insertIntoInventoryDB (String object, String description, String cost ){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("object", object);
        values.put("description", description);
        values.put("cost", cost);

        db.insert(INVENTORY_TABLE, null, values);
        db.close();
    }

    public void insertIntoCompanyDB (String name, String address1, String address2, String city, String zip, String phone, String email){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("address1", address1);
        values.put("address2", address2);
        values.put("city", city);
        values.put("zip", zip);
        values.put("phone", phone);
        values.put("email", email);

        db.insert(COMPANY_TABLE, null, values);
        db.close();
    }
    // TODO: 19/03/2019 add updates

    public List<DatabaseModel> getDataFromSales(){
        List<DatabaseModel> modelList= new ArrayList<>();
        String query= "select * from " + SALES_TABLE;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor= db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                DatabaseModel model = new DatabaseModel();
                model.setIdSales(cursor.getInt(0));
                model.setAmountPaid(cursor.getString(1));
                model.setDateTimePaid(cursor.getString(2));
                model.setPaymentMethod(cursor.getInt(3));

                modelList.add(model);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return modelList;
    }
    public List<DatabaseModel> getDataFromInventory(){
        List<DatabaseModel> modelList= new ArrayList<>();
        String query= "select * from " + INVENTORY_TABLE;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor= db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                DatabaseModel model = new DatabaseModel();
                model.setIdInventory(cursor.getInt(0));
                model.setObject(cursor.getString(1));
                model.setDescription(cursor.getString(2));
                model.setCost(cursor.getString(3));

                modelList.add(model);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return modelList;
    }

}
