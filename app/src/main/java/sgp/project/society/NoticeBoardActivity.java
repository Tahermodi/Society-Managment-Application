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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoticeBoardActivity extends AppCompatActivity {

    public ListView list_view_notice;
    EditText add_notice;

    DatabaseReference databaseReference;


    List<DummyClassNoticeBoardActivity> notice_list;


    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);



        list_view_notice = (ListView) findViewById(R.id.list_view_notice);

        firebaseAuth = FirebaseAuth.getInstance();

        notice_list = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();




        list_view_notice.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int which_item = position;
                new AlertDialog.Builder(NoticeBoardActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure")
                        .setMessage("Do you want to delete")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DummyClassNoticeBoardActivity dummyClassNoticeBoardActivity = notice_list.get(which_item);
                                String id = dummyClassNoticeBoardActivity.getId();

                                databaseReference.child("notice_data").child(id).removeValue();

                                notice_list.remove(which_item);
                            }
                        })
                        .setNegativeButton("no", null).show();
                return true;
            }
        });

    }

    public void notice() {

        String id = databaseReference.push().getKey();
        String notice = add_notice.getText().toString().trim();

        DummyClassNoticeBoardActivity dataflat = new DummyClassNoticeBoardActivity(id, notice);

        databaseReference.child("notice_data").child(id).setValue(dataflat);

        Toast.makeText(this, "Notice Added", Toast.LENGTH_SHORT).show();
    }

    public void Add_notice(View view) {

        final Dialog popup_dialog;
        popup_dialog = new Dialog(this);

        ((ViewGroup)popup_dialog.getWindow().getDecorView())
                .getChildAt(0).startAnimation(AnimationUtils.loadAnimation(
                this,R.anim.bounce));

        popup_dialog.show();
        popup_dialog.setContentView(R.layout.addnotice);


        add_notice = (EditText) popup_dialog.findViewById(R.id.Add_Notice);

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

                if (!add_notice.getText().toString().isEmpty()) {
                    notice();
                    popup_dialog.dismiss();
                } else {
                    Toast.makeText(NoticeBoardActivity.this, "Please Enter All the details!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.child("notice_data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()) {
                    notice_list.clear();
                    for (DataSnapshot noticeSnapshot : dataSnapshot.getChildren()) {
                        DummyClassNoticeBoardActivity dummyClassNoticeBoardActivity = noticeSnapshot.getValue(DummyClassNoticeBoardActivity.class);
                        notice_list.add(dummyClassNoticeBoardActivity);
                    }

                    ArrayAdapterNoticeBoardActivity arrayAdapterNoticeBoardActivity =
                            new ArrayAdapterNoticeBoardActivity(NoticeBoardActivity.this, notice_list);
                    list_view_notice.setAdapter(arrayAdapterNoticeBoardActivity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
