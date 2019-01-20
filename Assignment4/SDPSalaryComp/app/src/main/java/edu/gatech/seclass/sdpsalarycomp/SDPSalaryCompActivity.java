package edu.gatech.seclass.sdpsalarycomp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SDPSalaryCompActivity extends AppCompatActivity {

    int cur_city_index = 0;
    int new_city_index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdpsalary_comp);

        final EditText baseSalaryTxt = (EditText) findViewById(R.id.baseSalary);
        final EditText targetSalaryTxt = (EditText) findViewById(R.id.targetSalary);
        final Spinner current_city_spin = (Spinner) findViewById(R.id.currentCity);
        Spinner new_city_spin = (Spinner) findViewById(R.id.newCity);

        current_city_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String index = parent.getItemAtPosition(position).toString();
                switch (index)
                {
                    case "Atlanta, GA":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) / 160 ));
                        break;

                    case "Austin, TX":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) / 152 ));
                        break;

                    case "Boston, MA":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) / 197 ));
                        break;

                    case "Honolulu, HI":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) / 201 ));
                        break;

                    case "Las Vegas, NV":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) / 153 ));
                        break;

                    case "Mountain View, CA":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) / 244 ));
                        break;

                    case "New York City, NY":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) / 232 ));
                        break;

                    case "San Francisco, CA":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) / 241 ));
                        break;

                    case "Seattle, WA":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) / 198 ));
                        break;

                    case "Springfield, MO":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) / 114 ));
                        break;

                    case "Tampa, FL":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) / 139 ));
                        break;

                    case "Washington D.C.":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) / 217 ));
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        new_city_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String index = parent.getItemAtPosition(position).toString();
                switch (index)
                {
                    case "Atlanta, GA":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) * 160 ));
                        double tmp = Double.parseDouble(targetSalaryTxt.getText().toString());
                        long new_tmp = Math.round(tmp);
                        targetSalaryTxt.setText(String.valueOf(new_tmp));
                        break;

                    case "Austin, TX":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) * 152 ));
                        double a = Double.parseDouble(targetSalaryTxt.getText().toString());
                        long b = Math.round(a);
                        targetSalaryTxt.setText(String.valueOf(b));

                        break;

                    case "Boston, MA":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) * 197 ));
                        double c = Double.parseDouble(targetSalaryTxt.getText().toString());
                        long d = Math.round(c);
                        targetSalaryTxt.setText(String.valueOf(d));
                        break;

                    case "Honolulu, HI":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) * 201 ));
                        double e = Double.parseDouble(targetSalaryTxt.getText().toString());
                        long f = Math.round(e);
                        targetSalaryTxt.setText(String.valueOf(f));
                        break;

                    case "Las Vegas, NV":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) * 153 ));
                        double g = Double.parseDouble(targetSalaryTxt.getText().toString());
                        long h = Math.round(g);
                        targetSalaryTxt.setText(String.valueOf(h));
                        break;

                    case "Mountain View, CA":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) * 244 ));
                        double i = Double.parseDouble(targetSalaryTxt.getText().toString());
                        long j = Math.round(i);
                        targetSalaryTxt.setText(String.valueOf(j));
                        break;

                    case "New York City, NY":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) * 232 ));
                        double k = Double.parseDouble(targetSalaryTxt.getText().toString());
                        long l = Math.round(k);
                        targetSalaryTxt.setText(String.valueOf(l));
                        break;

                    case "San Francisco, CA":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) * 241 ));
                        double m = Double.parseDouble(targetSalaryTxt.getText().toString());
                        long n = Math.round(m);
                        targetSalaryTxt.setText(String.valueOf(n));
                        break;

                    case "Seattle, WA":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) * 198 ));
                        double o = Double.parseDouble(targetSalaryTxt.getText().toString());
                        long p = Math.round(o);
                        targetSalaryTxt.setText(String.valueOf(p));
                        break;

                    case "Springfield, MO":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) * 114 ));
                        double q = Double.parseDouble(targetSalaryTxt.getText().toString());
                        long r = Math.round(q);
                        targetSalaryTxt.setText(String.valueOf(r));
                        break;

                    case "Tampa, FL":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) * 139 ));
                        double s = Double.parseDouble(targetSalaryTxt.getText().toString());
                        long t = Math.round(s);
                        targetSalaryTxt.setText(String.valueOf(t));
                        break;

                    case "Washington D.C.":
                        targetSalaryTxt.setText(Double.toString(Double.parseDouble(targetSalaryTxt.getText().toString()) * 217 ));
                        double u = Double.parseDouble(targetSalaryTxt.getText().toString());
                        long v = Math.round(u);
                        targetSalaryTxt.setText(String.valueOf(v));
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        baseSalaryTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 String strBaseSalary = baseSalaryTxt.getText().toString();
                 if (strBaseSalary == "" || strBaseSalary == " ")
                 {
                     targetSalaryTxt.setText("");
                 }
                 double doubleBaseSalary = Double.parseDouble(strBaseSalary);
                 //Math.round(doubleBaseSalary);
                 targetSalaryTxt.setText(Long.toString(Math.round(doubleBaseSalary)));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


/*        public static void calculateSalary(double base, int cur_city, int new_city)
        {
            targetSalaryTxt.setText(Double.toString(base * new_city / cur_city));
        }
*/
        //listener();
    }
/*    public void setTextView(){
        textView=(TextView) findViewById(R.id.targetSalary);
        editText=(EditText) findViewById(R.id.baseSalary);
        textView.setText(editText.getText().toString());

    }
*/
/*    public void listener(){
        EditText txt = findViewById(R.id.baseSalary);
        TextView tmp = findViewById(R.id.targetSalary);
        tmp.setText(txt.getText());
    }*/

}
