package com.example.bhavikarana.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InvoiceAdapter adapter;
    private ArrayList<Invoice> invoiceArrayList;
    private static ArrayList<Transaction> transactionList=new ArrayList<>();

    DatabaseReference databaseReference;


    //private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_invoice:
                    //mTextMessage.setText(R.string.title_invoice);
                    return true;
                case R.id.navigation_pay:
                    startActivity(new Intent(Dashboard.this, QRCode.class));
                    return true;
                case R.id.navigation_investment:
                    startActivity(new Intent(Dashboard.this, TransactionList.class));
                    //mTextMessage.setText(R.string.title_investment);
                    return true;
                case R.id.navigation_settings:
                    //mTextMessage.setText(R.string.title_settings);
                    return true;
            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        invoiceArrayList = new ArrayList<>();
        //transactionList=new ArrayList<>();

        int n=transactionList.size();

        for(int i=0; i<n;i++) {
            Invoice in1 = new Invoice("GROCERY", transactionList.get(i).getMerchantName(), "PAYTM", transactionList.get(i).getCash() + "");
            invoiceArrayList.add(in1);
        }
        adapter = new InvoiceAdapter(this, invoiceArrayList);
        recyclerView.setAdapter(adapter);

        //insertData();


        databaseReference = FirebaseDatabase.getInstance().getReference("transactions");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot transactionShot : dataSnapshot.getChildren()) {
                    Transaction transaction = transactionShot.getValue(Transaction.class);
                    transactionList.add(transaction);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



   // private void insertData(){


//        Invoice in1=new Invoice("GROCERY", "GROFERS", "PAYTM", "236");
//        invoiceArrayList.add(in1);
//
//        Invoice in2=new Invoice("GROCERY", "GROFERS", "PAYTM", "236");
//        invoiceArrayList.add(in2);
//
//        Invoice in3=new Invoice("GROCERY", "GROFERS", "PAYTM", "236");
//        invoiceArrayList.add(in3);
//
//        Invoice in4=new Invoice("GROCERY", "GROFERS", "PAYTM", "236");
//        invoiceArrayList.add(in4);
//
//        Invoice in5=new Invoice("GROCERY", "GROFERS", "PAYTM", "236");
//        invoiceArrayList.add(in5);
   // }

}
