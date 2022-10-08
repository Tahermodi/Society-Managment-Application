package sgp.project.society;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RulesActivity extends AppCompatActivity {

    private ListView list_view_rules;

    DatabaseReference databaseReference;

    List<DummyClassRulesActivity> rule_list;

    FirebaseAuth firebaseAuth;


    public EditText et_rule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);


        list_view_rules = findViewById(R.id.list_view_rules);
        rule_list = new ArrayList();

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("rule_data");

        list_view_rules.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int which_item = position;
                new AlertDialog.Builder(RulesActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure")
                        .setMessage("do you want to delete")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DummyClassRulesActivity dummyClassRulesActivity = rule_list.get(which_item);
                                String id = dummyClassRulesActivity.getId();

                                databaseReference.child(id).removeValue();

                                rule_list.remove(which_item);

                            }
                        })
                        .setNegativeButton("no", null).show();

                return true;
            }
        });


    }

    public void add_user(View view) {

        final Dialog popup_dialog;
        popup_dialog = new Dialog(this);

        ((ViewGroup)popup_dialog.getWindow().getDecorView())
                .getChildAt(0).startAnimation(AnimationUtils.loadAnimation(
                this,R.anim.bounce));

        popup_dialog.show();
        popup_dialog.setContentView(R.layout.addrules);


        et_rule = (EditText) popup_dialog.findViewById(R.id.Add_rules);

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

                if (!et_rule.getText().toString().isEmpty())
                {

                    add_rule_to_database();
                    popup_dialog.dismiss();

                }
                else
                {
                    Toast.makeText(RulesActivity.this, "Enter All Details!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void add_rule_to_database() {

        String id = databaseReference.push().getKey();
        String rule = et_rule.getText().toString().trim();

        DummyClassRulesActivity dummyClassRulesActivity =
                new DummyClassRulesActivity(id, rule);

        databaseReference.child(id).setValue(dummyClassRulesActivity);

        Toast.makeText(this, "Rules Detail Added", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()) {
                    rule_list.clear();
                    for (DataSnapshot ruleSnapshot : dataSnapshot.getChildren()) {
                        DummyClassRulesActivity dummyClassRulesActivity =
                                ruleSnapshot.getValue(DummyClassRulesActivity.class);

                        rule_list.add(dummyClassRulesActivity);
                    }

                    ArrayAdapterRulesActivity arrayAdapterRulesActivity =
                            new ArrayAdapterRulesActivity(RulesActivity.this, rule_list);
                    list_view_rules.setAdapter(arrayAdapterRulesActivity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
