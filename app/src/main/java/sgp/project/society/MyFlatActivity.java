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

import java.util.ArrayList;
import java.util.List;

public class MyFlatActivity extends AppCompatActivity {

    public ListView list_view_members;
    EditText add_name,mobile1;

    DatabaseReference databaseReference;


    List<DummyClassMyFlatActivity> member_list;


    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_flat);

        list_view_members = (ListView) findViewById(R.id.list_view_members);

        firebaseAuth = FirebaseAuth.getInstance();

        member_list = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getCurrentUser().getUid());




        list_view_members.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int which_item = position;
                new AlertDialog.Builder(MyFlatActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure")
                        .setMessage("Do you want to delete")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DummyClassMyFlatActivity dummyClassMyFlatActivity = member_list.get(which_item);
                                String id = dummyClassMyFlatActivity.getId();

                                databaseReference.child("member_data").child(id).removeValue();

                                member_list.remove(which_item);
                            }
                        })
                        .setNegativeButton("no", null).show();
                return true;
            }
        });

    }

    public void flat() {

        String id = databaseReference.push().getKey();
        String Name = add_name.getText().toString().trim();
        String Mobile = mobile1.getText().toString();

        DummyClassMyFlatActivity dataflat = new DummyClassMyFlatActivity(id, Name, Mobile);

        databaseReference.child("member_data").child(id).setValue(dataflat);

        Toast.makeText(this, "Member Added", Toast.LENGTH_SHORT).show();
    }

    public void add_user(View view) {

        final Dialog popup_dialog;
        popup_dialog = new Dialog(this);

        ((ViewGroup)popup_dialog.getWindow().getDecorView())
                .getChildAt(0).startAnimation(AnimationUtils.loadAnimation(
                this,R.anim.bounce));

        popup_dialog.show();
        popup_dialog.setContentView(R.layout.addmembersmyflat);


        add_name = (EditText) popup_dialog.findViewById(R.id.Add_Name);
        mobile1 = (EditText) popup_dialog.findViewById(R.id.Add_Number);

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

                if (!add_name.getText().toString().isEmpty() && !mobile1.getText().toString().isEmpty()) {
                    flat();
                    popup_dialog.dismiss();
                } else {
                    Toast.makeText(MyFlatActivity.this, "Please Enter All the details!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.child("member_data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()) {
                    member_list.clear();
                    for (DataSnapshot memberSnapshot : dataSnapshot.getChildren()) {
                        DummyClassMyFlatActivity dummyClassMyFlatActivity = memberSnapshot.getValue(DummyClassMyFlatActivity.class);
                        member_list.add(dummyClassMyFlatActivity);
                    }

                    ArrayAdapterMyFlatActivity arrayAdapterMyFlatActivity =
                            new ArrayAdapterMyFlatActivity(MyFlatActivity.this, member_list);
                    list_view_members.setAdapter(arrayAdapterMyFlatActivity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}