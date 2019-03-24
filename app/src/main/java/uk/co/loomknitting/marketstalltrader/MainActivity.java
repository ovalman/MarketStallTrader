package uk.co.loomknitting.marketstalltrader;

import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper helper;
    List<DatabaseModel> dbList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DatabaseHelper(MainActivity.this);

        listView=findViewById(R.id.listView);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etItem = findViewById(R.id.etItem);
                EditText etDescription = findViewById(R.id.etDescription);
                EditText etAmount = findViewById(R.id.etAmount);

                String item = etItem.getText().toString();
                String description = etDescription.getText().toString();
                String amount = etAmount.getText().toString();

                helper.insertIntoInventoryDB(item, description, amount);
                helper.close();

                Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();

                etItem.setText("");
                etAmount.setText("");
                etDescription.setText("");
            }
        });

        populateListView();

    }
    public void populateListView(){
        dbList=helper.getDataFromInventory();
        Cursor cursor = helper.getReadableDatabase().rawQuery("SELECT * FROM inventory_table order by _id DESC ", null);
        String item = cursor.getColumnName(1);
        String description = cursor.getColumnName(2);
        String amount = cursor.getColumnName(3);
        String[] items = new String[]{item, description, amount};

        int[] toViewIDs = new int[]{R.id.tvItem, R.id.tvDescription, R.id.tvAmount};
        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(this, R.layout.single_inventory_item, cursor, items, toViewIDs, 0);
        listView.setAdapter(myCursorAdapter);

    }
}

