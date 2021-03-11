package dev.leandromodena.fitness;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ImcActivity extends AppCompatActivity {

    private EditText editTextWeight;
    private EditText editTextHeight;
    private Button btnCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);

        editTextWeight = findViewById(R.id.editTextWidthIMC);
        editTextHeight = findViewById(R.id.editTextHeightIMC);
        btnCalc = findViewById(R.id.btnCalcIMC);

        btnCalc.setOnClickListener(view -> {
            if (!validate()){
                Toast.makeText(ImcActivity.this, R.string.fields_message, Toast.LENGTH_LONG).show();
                return;
            }
            String sWeight = editTextWeight.getText().toString();
            String sHeight = editTextHeight.getText().toString();

            int weight = Integer.parseInt(sWeight);
            int height = Integer.parseInt(sHeight);

            double result = calculateIMC(height,weight);

            int imcResponseId = imcResponse(result);


            AlertDialog dialog = new AlertDialog.Builder(ImcActivity.this)
                    .setTitle(getString(R.string.imc_response, result))
                    .setMessage(imcResponseId)
                    .setPositiveButton(android.R.string.ok, (dialog1, which) -> {

                    })
                    .setNegativeButton(R.string.save, ((dialog1, which) -> {
                         new Thread(() -> {
                             long calcId = SqlHelper.getInstance(ImcActivity.this).addItem("imc", result);
                             runOnUiThread(() -> {
                                 if (calcId > 0)
                                    Toast.makeText(ImcActivity.this, R.string.calc_saved, Toast.LENGTH_SHORT).show();
                                    openListCalcActivity();
                             });

                         }).start();
                    }))
                    .create();

            dialog.show();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context
                    .INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(editTextHeight.getWindowToken(), 0);
            inputMethodManager.hideSoftInputFromWindow(editTextWeight.getWindowToken(), 0);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_list:
                finish();
                openListCalcActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openListCalcActivity(){
        Intent intent = new Intent(ImcActivity.this, ListCalcActivity.class);
        intent.putExtra("type", "imc");
        startActivity(intent);
    }


    @StringRes
    private int imcResponse(double imc){
        if (imc < 15)
            return R.string.imc_severely_low_weight;
        else if (imc < 16)
            return  R.string.imc_very_low_weight;
        else if (imc < 18.5)
            return  R.string.imc_low_weight;
        else if (imc < 25)
            return  R.string.normal;
        else if (imc < 30)
            return  R.string.imc_high_weight;
        else if (imc < 35)
            return  R.string.imc_so_high_weight;
        else if (imc < 40)
            return  R.string.imc_severely_high_weight;
        else
            return R.string.imc_extreme_weight;

    }

    private double calculateIMC(int height, int weight){
        return weight / (((double) height / 100) * ((double) height / 100));
    }

    private boolean validate(){
        return (!editTextWeight.getText().toString().startsWith("0"))
                && !editTextWeight.getText().toString().isEmpty()
                && !editTextHeight.getText().toString().startsWith("0")
                && !editTextHeight.getText().toString().isEmpty();
    }

}