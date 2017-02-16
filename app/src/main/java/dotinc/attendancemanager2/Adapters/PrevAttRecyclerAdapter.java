package dotinc.attendancemanager2.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.PrevAttendanceActivity;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.Utils.Helper;

/**
 * Created by vellapanti on 16/2/17.
 */

public class PrevAttRecyclerAdapter extends RecyclerView.Adapter<PrevAttRecyclerAdapter.MyViewholder> {

    private Context context;
    private ArrayList<SubjectsList> list;
    private ArrayList<SubjectsList> saveList;
    private boolean[] array;

    public PrevAttRecyclerAdapter(Context context, ArrayList<SubjectsList> list) {
        Log.d("option_prev", "cons");
        this.context = context;
        this.list = list;
        this.saveList = list;
        array = new boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = true;
        }
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("option_prev", "view");
        View view = LayoutInflater.from(context).inflate(R.layout.custom_prev_attendance, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, final int position) {

        holder.subjectName.setText(list.get(position).getSubjectName());
        holder.attended.setText("" + list.get(position).getPast_attended_classes());
        holder.total.setText("" + list.get(position).getPast_total_classes());

        holder.attended.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("options", editable.toString() + "" + position);
                SubjectsList list = saveList.get(position);
                if (!editable.toString().equals("")) {
                    int attend = Integer.parseInt(editable.toString());
                    if (attend > list.getPast_total_classes()) {
                        holder.errorText.setVisibility(View.VISIBLE);
                        holder.errorText.setText("Attended cannot be greater than total");
                        array[position] = false;
                    } else {
                        saveList.get(position).setPast_attended_classes(attend);
                        holder.errorText.setVisibility(View.GONE);
                        array[position] = true;
                    }
                } else {
                    holder.errorText.setVisibility(View.GONE);
                    array[position] = false;
                }
            }
        });

        holder.total.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("options", editable.toString() + "" + position);
                SubjectsList list = saveList.get(position);
                if (!editable.toString().equals("")) {
                    int total = Integer.parseInt(editable.toString());
                    if (total >= list.getPast_attended_classes()) {
                        saveList.get(position).setPast_total_classes(total);
                        holder.errorText.setVisibility(View.GONE);
                        array[position] = true;
                    } else {
                        holder.errorText.setVisibility(View.VISIBLE);
                        holder.errorText.setText("Total can't be less than attended");
                        array[position] = false;
                    }
                } else {
                    holder.errorText.setVisibility(View.GONE);
                    array[position] = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewholder extends RecyclerView.ViewHolder {
        EditText attended, total;
        TextView subjectName;
        TextView errorText;

        public MyViewholder(View itemView) {
            super(itemView);
            Log.d("option_prev", "holder");
            attended = (EditText) itemView.findViewById(R.id.prev_attended);
            attended.setTypeface(Typeface.createFromAsset(context.getAssets(), Helper.OXYGEN_REGULAR));
            total = (EditText) itemView.findViewById(R.id.prev_total);
            total.setTypeface(Typeface.createFromAsset(context.getAssets(), Helper.OXYGEN_REGULAR));
            subjectName = (TextView) itemView.findViewById(R.id.subject_name);
            subjectName.setTypeface(Typeface.createFromAsset(context.getAssets(), Helper.OXYGEN_BOLD));
            errorText = (TextView) itemView.findViewById(R.id.errorText);
            errorText.setTypeface(Typeface.createFromAsset(context.getAssets(), Helper.JOSEFIN_SANS_REGULAR));
        }
    }

    public ArrayList<SubjectsList> retriveUpdatedAttendance() {
        int flag = 1;
        for (int i = 0; i < list.size(); i++) {
            if (array[i])
                continue;
            else {
                flag = 0;
                break;
            }
        }
        if (flag == 0)
            return null;
        return saveList;
    }
}
