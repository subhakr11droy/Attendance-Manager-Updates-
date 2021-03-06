package dotinc.attendancemanager2.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.math.BigDecimal;
import java.util.ArrayList;

import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.Utils.AttendanceDatabase;
import dotinc.attendancemanager2.Utils.Helper;
import dotinc.attendancemanager2.Utils.SubjectDatabase;

/**
 * Created by vellapanti on 8/2/16.
 */
public class OverallAttendanceAdapter extends RecyclerView.Adapter<OverallAttendanceAdapter.OverallViewHolder> {

    private Context context;
    private ArrayList<SubjectsList> arrayList;
    private LayoutInflater inflater;
    private AttendanceDatabase database;
    private SubjectDatabase subjectDatabase;
    public OverallAttendanceAdapter(Context context, ArrayList<SubjectsList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
        database = new AttendanceDatabase(context);
        subjectDatabase= new SubjectDatabase(context);
    }

    @Override
    public OverallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_main_row, parent, false);
        OverallViewHolder viewHolder = new OverallViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OverallViewHolder viewHolder, int position) {
        final int id = arrayList.get(position).getId();
        Log.d("subject",arrayList.get(position).getSubjectName());
        int attendedClasses = database.totalPresent(id)+subjectDatabase.getPastAttendedAttendance(id);
        int totalClasses = database.totalClasses(id)+subjectDatabase.getPastTotalAttendance(id);
        viewHolder.checkMark.setVisibility(View.GONE);

        viewHolder.subject.setText(arrayList.get(position).getSubjectName());
        viewHolder.subject.setTypeface(Typeface.createFromAsset(context.getAssets(), Helper.OXYGEN_BOLD));
        viewHolder.attended.setText(context.getResources().getString(R.string.attended) + ": " + attendedClasses);
        viewHolder.attended.setTypeface(Typeface.createFromAsset(context.getAssets(), Helper.OXYGEN_REGULAR));
        viewHolder.total.setText(context.getResources().getString(R.string.total) + ": " + totalClasses);
        viewHolder.total.setTypeface(Typeface.createFromAsset(context.getAssets(), Helper.OXYGEN_REGULAR));
        viewHolder.needClassDetail.setTypeface(Typeface.createFromAsset(context.getAssets(), Helper.JOSEFIN_SANS_REGULAR));
        viewHolder.subject_percentage.setTypeface(Typeface.createFromAsset(context.getAssets(), Helper.JOSEFIN_SANS_BOLD));

        float percentage = ((float) attendedClasses / (float) totalClasses) * 100;
        classesNeeded(attendedClasses, totalClasses, percentage, viewHolder);
        if (!Float.isNaN(percentage)) {
            String perc = String.format("%.1f", percentage);
            BigDecimal decimal = new BigDecimal(perc);
            viewHolder.subject_percentage.setText(" " + decimal.stripTrailingZeros().toPlainString());
        } else {
            viewHolder.subject_percentage.setText("0");
        }

        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, null);
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, null);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void classesNeeded(int attendedClass, int totalClass, float percentage, OverallViewHolder viewHolder) {
        int flag = 0;
        int originalAttended = attendedClass;
        int attendaceCriteria =Integer.parseInt(Helper.getFromPref(context, Helper.ATTENDANCE_CRITERIA, String.valueOf(0)));
        if (percentage >= attendaceCriteria)
            flag = 1;
        else if (attendedClass == 0 && totalClass == 0)
            flag = 2;
        while (percentage < attendaceCriteria) {
            flag = 3;
            attendedClass++;
            totalClass++;
            viewHolder.subject_percentage.setTextColor(ContextCompat.getColor(context, R.color.absentColor));
            percentage = ((float) attendedClass / (float) totalClass) * 100;
        }
        switch (flag) {
            case 1:
                viewHolder.needClassDetail.setVisibility(View.VISIBLE);
                viewHolder.subject_percentage.setTextColor(ContextCompat.getColor(context, R.color.attendedColor));
                viewHolder.needClassDetail.setText(context.getResources().getString(R.string.on_track_message));
                break;

            case 2:
                viewHolder.needClassDetail.setVisibility(View.INVISIBLE);
                break;

            case 3:

                viewHolder.needClassDetail.setVisibility(View.VISIBLE);
                int need = 0;
                need = attendedClass - originalAttended;

                if (need == 1)
                    viewHolder.needClassDetail.setText(Html.fromHtml("Attend next <b><font color='#E64A19'>" + need + "</font></b> class"));
                else
                    viewHolder.needClassDetail.setText(Html.fromHtml("Attend next <b><font color='#E64A19'>" + need + "</font></b> classes"));
                break;
        }

    }

    static class OverallViewHolder extends RecyclerView.ViewHolder {
        private TextView subject;
        private TextView attended;
        private TextView total;
        private TextView subject_percentage;
        private TextView needClassDetail;
        private SwipeLayout swipeLayout;
        private ImageView checkMark;

        public OverallViewHolder(View itemView) {
            super(itemView);
            subject = (TextView) itemView.findViewById(R.id.subject_name);
            attended = (TextView) itemView.findViewById(R.id.attended);
            total = (TextView) itemView.findViewById(R.id.total);
            subject_percentage = (TextView) itemView.findViewById(R.id.sub_perc);
            needClassDetail = (TextView) itemView.findViewById(R.id.sub_detail);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            checkMark = (ImageView) itemView.findViewById(R.id.check_mark);
        }
    }
}
