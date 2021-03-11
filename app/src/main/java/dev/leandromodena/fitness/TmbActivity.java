package dev.leandromodena.fitness;

import androidx.annotation.NonNull;
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
import android.widget.Spinner;
import android.widget.Toast;

public class TmbActivity extends AppCompatActivity {

    EditText editTextWeight;
    EditText editTextHeight;
    EditText editTextAge;
    Button btnCalc;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmb);

        editTextAge = findViewById(R.id.editTextAgeTMB);
        editTextHeight = findViewById(R.id.editTextHeightTMB);
        editTextWeight = findViewById(R.id.editTextWidthTMB);
        btnCalc = findViewById(R.id.btnCalcTMB);
        spinner = findViewById(R.id.spinnerTMB);

        btnCalc.setOnClickListener(v -> {
            if (!validate()) {
                Toast.makeText(TmbActivity.this, R.string.fields_message, Toast.LENGTH_LONG).show();
                return;
            }
            String sWeight = editTextWeight.getText().toString();
            String sHeight = editTextHeight.getText().toString();
            String sAge = editTextAge.getText().toString();

            int weight = Integer.parseInt(sWeight);
            int height = Integer.parseInt(sHeight);
            int age = Integer.parseInt(sAge);

            double result = calculateTMB(height, weight, age);

            double tmb = tmbResponse(result);

            AlertDialog dialog = new AlertDialog.Builder(TmbActivity.this)
                    .setMessage(getString(R.string.tmb_response, tmb))
                    .setPositiveButton(android.R.string.ok, (dialog1, which) -> {

                    })
                    .setNegativeButton(R.string.save, ((dialog1, which) -> {
                        new Thread(() -> {
                            long calcId = SqlHelper.getInstance(TmbActivity.this).addItem("tmb", tmb);
                            runOnUiThread(() -> {
                                if (calcId > 0)
                                    Toast.makeText(TmbActivity.this, R.string.calc_saved, Toast.LENGTH_SHORT).show();
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

    private double calculateTMB(int height, int weight, int age) {
        return 66 + (weight * 13.8) + (5 * height) - (6.8 * age);
    }

    private double tmbResponse(double tmb) {
        int index = spinner.getSelectedItemPosition();
        switch (index) {
            case 0:
                return tmb * 1.2;
            case 1:
                return tmb * 1.375;
            case 2:
                return tmb * 1.55;
            case 3:
                return tmb * 1.725;
            case 4:
                return tmb * 1.9;
            default:
                return 0;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list:
                finish();
                openListCalcActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openListCalcActivity() {
        Intent intent = new Intent(TmbActivity.this, ListCalcActivity.class);
        intent.putExtra("type", "tmb");
        startActivity(intent);
    }

    private boolean validate() {
        return (!editTextWeight.getText().toString().startsWith("0"))
                && !editTextWeight.getText().toString().isEmpty()
                && !editTextHeight.getText().toString().startsWith("0")
                && !editTextHeight.getText().toString().isEmpty()
                && !editTextAge.getText().toString().startsWith("0")
                && !editTextAge.getText().toString().isEmpty();
    }


}