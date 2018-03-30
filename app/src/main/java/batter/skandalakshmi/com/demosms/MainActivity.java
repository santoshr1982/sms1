package batter.skandalakshmi.com.demosms;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        request = findViewById(R.id.requestReceived);
        request.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Intent approval = new Intent(getApplicationContext(),AprovalForm.class
                );
                String name = (adapterView.getItemAtPosition(position)).toString();
                approval.putExtra("CUSTOMERNAME",name);
                startActivity(approval);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (fetchInbox() != null) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fetchInbox());
            request.setAdapter(adapter);

        }

    }

    private ArrayList<String> fetchInbox() {

        ArrayList<String> sms = new ArrayList<String>();

        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uri, new String[]{"_id", "address", "date", "body"}, null, null, null);

        cursor.moveToFirst();

        while (cursor.moveToNext()) {

            String Address = cursor.getString(1);
            String Body = cursor.getString(3);

            if (Address.equals("+917022948844")) {

                //sms.add("body: " + Body + "\n" + "Mobile No.: " + Address);

                String[] strWords = Body.split("#");

                if((strWords[0]).equals("NCR")){

                    sms.add("\n" + "Name : " + strWords[1] + "\n" + "Mobile No. : " + strWords[2] + "\n" + "Address :" + strWords[3] );

                }

            }


        }

        return sms;

    }
}
