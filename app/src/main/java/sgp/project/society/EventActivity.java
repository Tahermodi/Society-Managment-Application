package sgp.project.society;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    private ListView list_view_event;

    DatabaseReference databaseReference;

    List<DummyClassEventActivity> event_list;

    FirebaseAuth firebaseAuth;




    public EditText event_name;
    public EditText event_date;
    public EditText event_day;
    public EditText event_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        list_view_event = findViewById(R.id.list_view_event);
        event_list = new ArrayList();

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("event_data");

        list_view_event.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int which_item = position;
                new AlertDialog.Builder(EventActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure")
                        .setMessage("do you want to delete")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DummyClassEventActivity dummyClassEventActivity = event_list.get(which_item);
                                String id = dummyClassEventActivity.getId();

                                databaseReference.child(id).removeValue();

                                event_list.remove(which_item);

                            }
                        })
                        .setNegativeButton("no", null).show();

                return true;
            }
        });

    }

    public void AddEvent(View view) {

        final Dialog popup_dialog;
        popup_dialog = new Dialog(this);

        ((ViewGroup)popup_dialog.getWindow().getDecorView())
                .getChildAt(0).startAnimation(AnimationUtils.loadAnimation(
                this,R.anim.bounce));

        popup_dialog.show();
        popup_dialog.setContentView(R.layout.addevent);


        event_name = (EditText) popup_dialog.findViewById(R.id.ename);
        event_date = (EditText) popup_dialog.findViewById(R.id.edate);
        event_day = (EditText) popup_dialog.findViewById(R.id.eday);
        event_desc = (EditText) popup_dialog.findViewById(R.id.ediscription);

        Button cancel = (Button) popup_dialog.findViewById(R.id.cancel);
        Button ok = (Button) popup_dialog.findViewById(R.id.ok);

        popup_dialog.setCanceledOnTouchOutside(false);

        popup_dialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!event_name.getText().toString().isEmpty() && !event_date.getText().toString().isEmpty() &&
                        !event_day.getText().toString().isEmpty() && !event_desc.getText().toString().isEmpty())
                {

                        add_event_to_database();
                        popup_dialog.dismiss();

                }
                else
                {
                    Toast.makeText(EventActivity.this, "Enter All Details!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void add_event_to_database() {

        String id = databaseReference.push().getKey();
        String event_nam = event_name.getText().toString().trim();
        String event_dat = event_date.getText().toString().trim();
        String event_dy = event_day.getText().toString().trim();
        String event_des = event_desc.getText().toString().trim();

        DummyClassEventActivity dummyClassEventActivity =
                new DummyClassEventActivity(id, event_nam, event_dat, event_dy, event_des);

        databaseReference.child(id).setValue(dummyClassEventActivity);

        Toast.makeText(this, "Event Details Added", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()) {
                    event_list.clear();
                    for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                        DummyClassEventActivity dummyClassEventActivity =
                                eventSnapshot.getValue(DummyClassEventActivity.class);

                        event_list.add(dummyClassEventActivity);
                    }

                    ArrayAdapterEventActivity arrayAdapterEventActivity =
                            new ArrayAdapterEventActivity(EventActivity.this, event_list);
                    list_view_event.setAdapter(arrayAdapterEventActivity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
