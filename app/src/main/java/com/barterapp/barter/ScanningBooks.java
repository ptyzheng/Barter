package com.barterapp.barter;

//ScanningBooks and ScanBarcodeActivity are modified versions of camera implementation from
//Youtube user TryCallFinally.

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;


public class ScanningBooks extends AppCompatActivity {
    TextView barcodeResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_books);
        barcodeResult = (TextView)findViewById(R.id.barcode_result);
    }
    //add click event to scan barcode
    public void scanBarCode(View v){
        Intent intent = new Intent(this, ScanBarcodeActivity.class);
        startActivityForResult(intent, 0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==0){
            if (resultCode== CommonStatusCodes.SUCCESS){
                if(data != null){
                    Barcode barcode = data.getParcelableExtra("barcode");
                    barcodeResult.setText("Barcode Value: "+barcode.displayValue);
                } else{
                    barcodeResult.setText("No Barcode Found");
                }
            }
        }
        else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}
