package co.edu.udea.compumovil.gr1.lab1ui;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button dateButton;
    AutoCompleteTextView actvCountry;
    Spinner hobbySpinner;
    EditText firstNameET;
    EditText lastNameET;
    EditText phoneET;
    EditText emailET;
    EditText addressET;
    RadioGroup genderRG;
    CheckBox favoriteCB;

    TextView resTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateButton = (Button)findViewById(R.id.dateButton);
        dateButton.setText("01/01/2016");

        actvCountry = (AutoCompleteTextView)findViewById(R.id.autoCountry);
        String[] countries = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,countries);
        actvCountry.setAdapter(adapter);

        hobbySpinner = (Spinner) findViewById(R.id.hobbiesSpinner);
        ArrayAdapter<CharSequence> hobbyAdapter = ArrayAdapter.createFromResource(this, R.array.hobbies_array, android.R.layout.simple_spinner_item);
        hobbyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hobbySpinner.setAdapter(hobbyAdapter);

        firstNameET = (EditText)findViewById(R.id.firstName);
        lastNameET = (EditText)findViewById(R.id.lastName);
        phoneET = (EditText)findViewById(R.id.phoneNumber);
        emailET = (EditText)findViewById(R.id.email);
        addressET = (EditText)findViewById(R.id.address);
        genderRG = (RadioGroup) findViewById(R.id.genderGroup);
        favoriteCB = (CheckBox) findViewById(R.id.isFavorite);

        resTV = (TextView)findViewById(R.id.resultTextView);
        resTV.setMovementMethod(new ScrollingMovementMethod());
    }

    public void showData(View v){
        String missing = getResources().getString(R.string.missing);
        RadioButton selectedRB;
        if(checkData()){
            resTV.setText(missing);
            return;
        }

        String firstnameLb = getResources().getString(R.string.name);
        String lastnameLb = getResources().getString(R.string.lastname);
        String phoneLb = getResources().getString(R.string.phoneNumber);
        String emailLb = getResources().getString(R.string.email);
        String genderLb = getResources().getString(R.string.gender);
        String hobbyLb = getResources().getString(R.string.hobby);
        String addressLb = getResources().getString(R.string.address);
        String countryLb = getResources().getString(R.string.countryName);
        String birthdateLb = getResources().getString(R.string.birthDate);
        String favoriteLb = getResources().getString(R.string.favorite);

        String firstnameVal = firstNameET.getText().toString();
        String lastnameVal = lastNameET.getText().toString();
        String phoneVal = phoneET.getText().toString();
        String emailVal = emailET.getText().toString();

        selectedRB = (RadioButton) findViewById(genderRG.getCheckedRadioButtonId());

        String genderVal = selectedRB.getText().toString();
        String hobbyVal = hobbySpinner.getSelectedItem().toString();
        String addressVal = addressET.getText().toString();
        String birthdateVal = dateButton.getText().toString();
        String countryVal = actvCountry.getText().toString();
        String favoriteVal = favoriteCB.isChecked() ?
                getResources().getString(R.string.yes) : getResources().getString(R.string.no);


        StringBuilder sb = new StringBuilder();

        sb.append(firstnameLb+": "+firstnameVal);
        if(!"".equals(lastnameVal)){
            sb.append('\n').append(lastnameLb+": "+lastnameVal);
        }

        sb.append('\n').append(phoneLb+": "+phoneVal);
        sb.append('\n').append(emailLb+": "+emailVal);
        sb.append('\n').append(genderLb+": "+genderVal);

        if(hobbySpinner.getSelectedItemPosition() != 0){
            sb.append('\n').append(hobbyLb+": "+hobbyVal);
        }

        if(!"".equals(addressVal)){
            sb.append('\n').append(addressLb+": "+addressVal);
        }
        sb.append('\n').append(countryLb+": "+countryVal);
        sb.append('\n').append(birthdateLb+": "+birthdateVal);
        sb.append('\n').append(favoriteLb+": "+favoriteVal);
        resTV.setText(sb.toString());
    }

    private boolean checkData(){
        boolean badData = false;
        String required = getResources().getString(R.string.required);

        if("".equals(firstNameET.getText().toString().trim())){
            firstNameET.setError(required);
            badData = true;
        }
        if("".equals(phoneET.getText().toString().trim())){
            phoneET.setError(required);
            badData = true;
        }
        if("".equals(emailET.getText().toString().trim())){
            emailET.setError(required);
            badData = true;
        }
        if("".equals(actvCountry.getText().toString().trim())){
            actvCountry.setError(required);
            badData = true;
        }

        return badData;
    }


    public void showDatePickerDialog(View v) {
        DatePickerFragment dpf = new DatePickerFragment();
        dpf.setDateButton((Button)findViewById(R.id.dateButton));
        DialogFragment newFragment = dpf;

        newFragment.show(getFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        Button dateButton;

        public void setDateButton(Button dateButton){
            this.dateButton = dateButton;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            dateButton.setText(day+"/"+(month+1)+"/"+year);
        }
    }
}
