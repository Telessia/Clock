package fr.licence.clock.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.zip.Inflater;

import fr.licence.clock.R;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        //@Override
        public Dialog onCreateDialog(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //return inflater.inflate(R.layout.date_picker_fragment, container, false);

            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int dayofmonth = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of TimePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, dayofmonth);
        }

        public void onDateSet(DatePicker view, int year, int month , int dayofmonth) {
            // Do something with the time chosen by the user
        }
    }
