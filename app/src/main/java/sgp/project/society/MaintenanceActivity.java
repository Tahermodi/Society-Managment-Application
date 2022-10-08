package sgp.project.society;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MaintenanceActivity extends AppCompatActivity {

    EditText flat_number;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ListView listView;
    List<uploadPDF> uploadPDFS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        Button upload=(Button)findViewById(R.id.upload);
        Button addmain=(Button) findViewById(R.id.addbutton);
        flat_number=findViewById(R.id.flat_number);
        uploadPDFS=new ArrayList<>();
        listView=(ListView)findViewById(R.id.maintaince);

        viewallfiles();

        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                uploadPDF uploadPDF=uploadPDFS.get(position);
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uploadPDF.getUrl()));
                startActivity(intent);
            }
        });

    }

    private void viewallfiles() {
        databaseReference=FirebaseDatabase.getInstance().getReference("uploads");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploadPDFS.clear();
                for (DataSnapshot data: snapshot.getChildren()){

                    uploadPDF uploadPDF1=data.getValue(uploadPDF.class);
                    uploadPDFS.add(uploadPDF1);

                }
                String[] uploads= new String [ uploadPDFS.size() ] ;

                for(int i = 0; i<uploads.length; i++){
                    uploads[i]=uploadPDFS.get(i).getFlat_no();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,uploads){

                    @Override
                    public View getView(int position,  View convertView, ViewGroup parent) {
                        View view=super.getView(position, convertView, parent);
                        TextView mytext=(TextView) view.findViewById(android.R.id.text1);
                        mytext.setTextColor(Color.BLACK);

                        return view;
                    }
                };
                listView.setAdapter(adapter);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void selectpdf() {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Select Pdf"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data !=null)

            uploadpdf(data.getData());
    }

    private void uploadpdf(Uri data) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading..");
        progressDialog.show();

        StorageReference Reference=storageReference.child("uploads/"+System.currentTimeMillis()+".PDF");
        Reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri= taskSnapshot.getStorage().getDownloadUrl();
                while (!uri.isComplete());
                Uri url = uri.getResult();
                uploadPDF uploadPDF=new uploadPDF(flat_number.getText().toString(),url.toString());
                databaseReference.child(databaseReference.push().getKey()).setValue(uploadPDF);
                Toast.makeText(MaintenanceActivity.this, "Uploaded Success", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded: "+(int)progress+"%");
            }
        });
    }

    public void Add_notice(View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(MaintenanceActivity.this);
        View mview = getLayoutInflater().inflate(R.layout.maintaince, null);
        flat_number = (EditText) mview.findViewById(R.id.flat_number);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss ");
        final String currentDateandTime = sdf.format(new Date());

        Button addb = (Button) mview.findViewById(R.id.addbutton);
        Button ok = (Button) mview.findViewById(R.id.upload);
        Button cancel = (Button) mview.findViewById(R.id.cancle);
        alert.setView(mview);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        addb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MaintenanceActivity.this, addmaintaince.class);
                startActivity(intent);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectpdf();

            }

        });
        alertDialog.show();
    }

}