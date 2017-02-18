package dotinc.attendancemanager2;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dotinc.attendancemanager2.Adapters.PrevAttRecyclerAdapter;
import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.Utils.Helper;
import dotinc.attendancemanager2.Utils.SubjectDatabase;

public class PrevAttendanceActivity extends AppCompatActivity {

    private RecyclerView pastRecycler;
    private Context context;
    private ImageView tickMark;
    private ArrayList<SubjectsList> subjectsLists;
    private ArrayList<SubjectsList> saveList;
    private SubjectDatabase subjectDatabase;
    private PrevAttRecyclerAdapter adapter;
    private CoordinatorLayout coordinatorLayout;

    private Typeface oxyBold, josefinReg;

    void instantiate() {
        context = PrevAttendanceActivity.this;
        tickMark = (ImageView) findViewById(R.id.tickmark);
        pastRecycler = (RecyclerView) findViewById(R.id.prev_att_recycler);
        pastRecycler.setHasFixedSize(true);
        pastRecycler.setLayoutManager(new LinearLayoutManager(context));
        subjectDatabase = new SubjectDatabase(this);
        subjectsLists = subjectDatabase.getAllSubjects();
//        for (int i = 0; i < subjectsLists.size(); i++) {
//            Log.d("Lsit", subjectsLists.get(i).getSubjectName() + "  atte-" + subjectsLists.get(i).getPast_attended_classes() + "tot-" + subjectsLists.get(i).getPast_total_classes());
//        }
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.root);

        oxyBold = Typeface.createFromAsset(getAssets(), Helper.OXYGEN_BOLD);
        josefinReg = Typeface.createFromAsset(getAssets(), Helper.JOSEFIN_SANS_REGULAR);

        ((TextView) findViewById(R.id.page_title)).setTypeface(oxyBold);
        ((TextView) findViewById(R.id.help_text)).setTypeface(josefinReg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prev_attendance);
        instantiate();
        populateSubjects();
        tickMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePastAddendance();
            }
        });
    }


    void populateSubjects() {
        adapter = new PrevAttRecyclerAdapter(context, subjectsLists);
        pastRecycler.setAdapter(adapter);
    }

    void savePastAddendance() {
        saveList = adapter.retriveUpdatedAttendance();
        if (saveList == null) {
            Snackbar.make(coordinatorLayout, "Please fill in valid entries!", Snackbar.LENGTH_LONG).show();
        } else {
            subjectDatabase.addPastAttendace(saveList);
            finish();
        }
    }
}
