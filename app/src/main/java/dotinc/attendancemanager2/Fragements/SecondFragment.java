package dotinc.attendancemanager2.Fragements;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dotinc.attendancemanager2.DetailedAnalysisActivity;
import dotinc.attendancemanager2.GoToDateActivity;
import dotinc.attendancemanager2.GoToDateOldActivity;
import dotinc.attendancemanager2.OverallAttendanceActivity;
import dotinc.attendancemanager2.PredictorActivity;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.Utils.Helper;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {
    private TextView detailedAnalysis;
    private TextView go_to_date;
    private TextView overall_attendance;
    private TextView predictor;
    int year, month, day;
    SimpleDateFormat formatter;
    Date myDate;
    Calendar calendar;

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        detailedAnalysis = (TextView) view.findViewById(R.id.detailed_analysis);
        detailedAnalysis.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), Helper.JOSEFIN_SANS_REGULAR));
        go_to_date = (TextView) view.findViewById(R.id.go_to_date);
        go_to_date.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), Helper.JOSEFIN_SANS_REGULAR));
        overall_attendance = (TextView) view.findViewById(R.id.complete_attendance);
        overall_attendance.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), Helper.JOSEFIN_SANS_REGULAR));
        predictor = (TextView) view.findViewById(R.id.predictor);
        predictor.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), Helper.JOSEFIN_SANS_REGULAR));

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        formatter = new SimpleDateFormat("d-M-yyyy");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        detailedAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.fireBaseAnalyticsLog("Checked Detailed Analysis",
                        Helper.getFromPref(getContext(),Helper.USER_NAME,""),
                        Helper.getFromPref(getContext(),Helper.USER_IMAGE_ID,"0"),
                        getContext());
                startActivity(new Intent(getActivity(), DetailedAnalysisActivity.class));

            }
        });
        go_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.fireBaseAnalyticsLog("Checked Go To Date ",
                        Helper.getFromPref(getContext(),Helper.USER_NAME,""),
                        Helper.getFromPref(getContext(),Helper.USER_IMAGE_ID,"0"),
                        getContext());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, pickerListener, year, month, day);
                    dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    dialog.show();
                } else {
                    Intent intent = new Intent(getActivity(), GoToDateOldActivity.class);
                    startActivity(intent);
                }

            }
        });
        overall_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.fireBaseAnalyticsLog("Checked Overall Attendance",
                        Helper.getFromPref(getContext(),Helper.USER_NAME,""),
                        Helper.getFromPref(getContext(),Helper.USER_IMAGE_ID,"0"),
                        getContext());
                Intent intent = new Intent(getActivity(), OverallAttendanceActivity.class);
                startActivity(intent);
            }
        });
        predictor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.fireBaseAnalyticsLog("Checked Predictor",
                        Helper.getFromPref(getContext(),Helper.USER_NAME,""),
                        Helper.getFromPref(getContext(),Helper.USER_IMAGE_ID,"0"),
                        getContext());
                Intent intent = new Intent(getActivity(), PredictorActivity.class);
                startActivity(intent);
            }
        });
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            int day = selectedDay;
            int month = selectedMonth + 1;
            int year = selectedYear;
            String date = day + "-" + month + "-" + year;
            try {
                myDate = formatter.parse(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            String day_selected = new SimpleDateFormat("EE").format(myDate);
            Intent intent = new Intent(getActivity(), GoToDateActivity.class);
            intent.putExtra("date", date);
            intent.putExtra("day_name", day_selected);
            startActivity(intent);
        }
    };
}
