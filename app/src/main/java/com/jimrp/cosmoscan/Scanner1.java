package com.jimrp.cosmoscan;

import androidx.appcompat.app.AppCompatActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

public class Scanner1 extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;
    private static String scanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();

        if (!MainActivity.pak.equals("")){
            Toast.makeText(getApplicationContext(), "Σκανάρεις για το πακέτο: " + MainActivity.pak, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void handleResult(Result result) {
        scanResult = result.getText();
        scanResult = scanResult.replaceAll("[^A-Za-z0-9 ]","");
        scanResult = scanResult.replaceAll(" ", "").toLowerCase();

        Toast.makeText(getApplicationContext(), "Παραγγελία: " + scanResult , Toast.LENGTH_SHORT).show();

        scanResult = "https://cosmomarket.gr/shop/tim/tim11_t.php?aa_paraggelias=" + scanResult + "&pak=" + MainActivity.pak;

        MainActivity.url = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://cosmomarket.gr/shop/vouchers/17OGLX0054GVQ.pdf";

        Intent intent = new Intent(Scanner1.this, MainActivityWebView.class);
        startActivity(intent);

//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
//        startActivity(intent);

//        scannerView.resumeCameraPreview(Scanner1.this);

//        new AlertDialog.Builder(this)
//                .setTitle(getString(R.string.result))
//                .setMessage(scanResult)
//                .setPositiveButton(getString(R.string.searchweb), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        scanResult = scanResult.replaceAll("[^A-Za-z0-9 ]","");
//                        scanResult = scanResult.replaceAll(" ", "+").toLowerCase();
//                        scanResult = "https://cosmomarket.gr/shop/tim/tim11_t.php?aa_paraggelias=" + scanResult + "&pak=" + MainActivity.pak;
//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
//                        startActivity(intent);
//                        dialogInterface.dismiss();
//                        scannerView.resumeCameraPreview(Scanner1.this);
//                    }})
//                .setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                        scannerView.resumeCameraPreview(Scanner1.this);
//                    }})
//                .setOnKeyListener(new DialogInterface.OnKeyListener() {
//                    @Override
//                    public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
//                        if (keyCode == KeyEvent.KEYCODE_BACK) {
//                            arg0.dismiss();
//                            scannerView.resumeCameraPreview(Scanner1.this);
//                        }
//                        return true;
//                    }
//                })
//                .create()
//                .show();
    }

    @Override
    public void onResume(){
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onDestroy(){
        scannerView.stopCamera();
        super.onDestroy();
    }
}