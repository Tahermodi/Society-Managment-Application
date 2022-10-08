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
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ParkingActivity extends AppCompatActivity {

    private ListView list_view_vehicle;

    DatabaseReference databaseReference;

    List<DummyClassParkingActivity> parking_list;

    FirebaseAuth firebaseAuth;




    public EditText vehicle_name;
    public EditText vehicle_number;
    public String vehicle_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);

        list_view_vehicle = (ListView) findViewById(R.id.list_view_vehicle);

        firebaseAuth = FirebaseAuth.getInstance();

        parking_list = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("parking_data");


        list_view_vehicle.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int which_item = position;
                new AlertDialog.Builder(ParkingActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure")
                        .setMessage("do you want to delete")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DummyClassParkingActivity dummyClassParkingActivity = parking_list.get(which_item);
                                String id = dummyClassParkingActivity.getId();

                                databaseReference.child(id).removeValue();

                                parking_list.remove(which_item);

                            }
                        })
                        .setNegativeButton("no", null).show();

                return true;
            }
        });

    }

    public boolean isvalid(String vehicleno) {
        return Pattern.compile("^([A-Z|a-z]{2}\\s{1}\\d{2}\\s{1}[A-Z|a-z]{1,2}\\s{1}\\d{1,4})?([A-Z|a-z]{3}\\s{1}\\d{1,4})?$").matcher(vehicleno).matches();
    }

    public void add_user(View view) {

        final Dialog popup_dialog;
        popup_dialog = new Dialog(this);

        ((ViewGroup)popup_dialog.getWindow().getDecorView())
                .getChildAt(0).startAnimation(AnimationUtils.loadAnimation(
                this,R.anim.bounce));

        popup_dialog.show();
        popup_dialog.setContentView(R.layout.addvehicle);


        vehicle_name = (EditText) popup_dialog.findViewById(R.id.Add_Name);
        vehicle_number = (EditText) popup_dialog.findViewById(R.id.Vehicle_number);
        final PowerSpinnerView powerspinner_view = (PowerSpinnerView) popup_dialog.findViewById(R.id.powerspinner_view);
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

        powerspinner_view.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
            @Override public void onItemSelected(int position, String item) {
                vehicle_type = item;
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!vehicle_name.getText().toString().isEmpty() && !vehicle_number.getText().toString().isEmpty())
                {
                    if (isvalid(vehicle_number.getText().toString()))
                    {
                        add_vehicle_to_database();
                        popup_dialog.dismiss();
                    }
                    else
                    {
                        Toast.makeText(ParkingActivity.this, "Enter valid vehicle number!", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(ParkingActivity.this, "Enter vehicle name!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void add_vehicle_to_database() {

        String id = databaseReference.push().getKey();
        String vehicle_nam = vehicle_name.getText().toString().trim();
        String vehicle_no = vehicle_number.getText().toString();
        String vehicle_typ = vehicle_type;

        DummyClassParkingActivity dummyClassParkingActivity = new DummyClassParkingActivity(id, vehicle_nam, vehicle_no, vehicle_typ);

        databaseReference.child(id).setValue(dummyClassParkingActivity);

        Toast.makeText(this, "Vehicle Details Added", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()) {
                    parking_list.clear();
                    for (DataSnapshot parkingSnapshot : dataSnapshot.getChildren()) {
                        DummyClassParkingActivity dummyClassParkingActivity = parkingSnapshot.getValue(DummyClassParkingActivity.class);
                        parking_list.add(dummyClassParkingActivity);
                    }

                    ArrayAdapterMyParkingActivity arrayAdapterMyParkingActivity =
                            new ArrayAdapterMyParkingActivity(ParkingActivity.this, parking_list);
                    list_view_vehicle.setAdapter(arrayAdapterMyParkingActivity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
