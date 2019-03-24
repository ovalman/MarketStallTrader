package uk.co.loomknitting.marketstalltrader;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CompanyDetails extends AppCompatActivity {
    DatabaseHelper helper;
    EditText etName, etAddress, etAddress2, etCity, etZip, etPhone, etEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        etName=findViewById(R.id.etCompanyName);
        etAddress=findViewById(R.id.etCompanyAddress1);
        etAddress2=findViewById(R.id.etCompanyAddress2);
        etCity=findViewById(R.id.etCompanyCity);
        etZip=findViewById(R.id.etCompanyZip);
        etPhone=findViewById(R.id.etCompanyPhone);
        etEmail=findViewById(R.id.etCompanyEmail);

        helper = new DatabaseHelper(CompanyDetails.this);
        helper.getReadableDatabase();
        Cursor cursor = helper.getReadableDatabase().rawQuery("SELECT * FROM customer_table ", null);
        if(cursor!=null){
            String name = cursor.getString(1);
            etName.setText(name);
        }


        Button btnSubmit=findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=etName.getText().toString();
                String address1=etAddress.getText().toString();
                String address2=etAddress2.getText().toString();
                String city=etCity.getText().toString();
                String zip=etZip.getText().toString();
                String phone=etPhone.getText().toString();
                String email=etEmail.getText().toString();

                helper.insertIntoCompanyDB(name, address1, address2, city, zip, phone, email);
                helper.close();
            }
        });



    }
}
