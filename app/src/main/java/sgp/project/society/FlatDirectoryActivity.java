package sgp.project.society;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FlatDirectoryActivity extends AppCompatActivity {

    public ListView list_view_members;

    DatabaseReference databaseReference;


    List<DummyClassMyFlatActivity> member_list;


    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_directory);


        list_view_members = (ListView) findViewById(R.id.list_view_members);

        firebaseAuth = FirebaseAuth.getInstance();

        member_list = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getCurrentUser().getUid());


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
                            new ArrayAdapterMyFlatActivity(FlatDirectoryActivity.this, member_list);
                    list_view_members.setAdapter(arrayAdapterMyFlatActivity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
