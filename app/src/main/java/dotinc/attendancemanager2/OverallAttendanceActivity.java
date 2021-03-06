package dotinc.attendancemanager2;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;

import dotinc.attendancemanager2.Adapters.OverallAttendanceAdapter;
import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.Utils.SubjectDatabase;

public class OverallAttendanceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ArrayList<SubjectsList> arrayList;
    private SubjectDatabase database;
    private OverallAttendanceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_attendance);
        instantiate();
    }


    private void instantiate() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.overall_attendance_activity));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = new SubjectDatabase(this);
        arrayList = database.getAllSubjects();
        recyclerView = (RecyclerView) findViewById(R.id.subjects);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OverallAttendanceAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);
//        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
//                Settings.Secure.ANDROID_ID);


        NativeExpressAdView nativeExpressAdView = (NativeExpressAdView) findViewById(R.id.adView);
        nativeExpressAdView.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.go_to_date_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
