package com.example.prc;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mydata;
    EditText txt_Fname, txt_Lname,txt_MInitial,txt_DateOfBirth,
             txt_Address,txt_City,txt_Relationship;
    RadioGroup rGroup_gender;
    RadioButton rbtn_Male,rbtn_Female,rbtn_Others;
    Button btn_add,btn_view,btn_update,btn_delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydata = new DatabaseHelper(this);
        txt_Fname = findViewById(R.id.txt_Fname);
        txt_Lname = findViewById(R.id.txt_Lname);
        txt_MInitial = findViewById(R.id.txt_MInitial);
        txt_DateOfBirth = findViewById(R.id.txt_DateOfBirth);
        txt_Address = findViewById(R.id.txt_Address);
        txt_City = findViewById(R.id.txt_City);
        txt_Relationship = findViewById(R.id.txt_Relationship);

        rGroup_gender = findViewById(R.id.rGroup_gender);
        rbtn_Male = findViewById(R.id.rbtn_Male);
        rbtn_Female = findViewById(R.id.rbtn_Female);
        rbtn_Others = findViewById(R.id.rbtn_Others);

        btn_add = findViewById(R.id.btn_add);
        btn_view = findViewById(R.id.btn_view);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);

        addData();
        viewData();
    }

    public void addData(){
        btn_add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //name validation
                        String firstName = txt_Fname.getText().toString().trim();
                        String lastName = txt_Lname.getText().toString().trim();

                        if(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)){
                            Toast.makeText(MainActivity.this, "please input first name or last name ", Toast.LENGTH_SHORT).show();
                        }



                        //gender
                        String gender = "";
                        if(rbtn_Male.isChecked()){
                            gender = "MALE";
                        } else if (rbtn_Female.isChecked()) {
                            gender = "FEMALE";
                        }else if(rbtn_Others.isChecked()){
                            gender = "OTHERS";
                        }

                        Boolean isInserted = mydata.insertData(
                                firstName,
                                lastName,
                                txt_MInitial.getText().toString(),
                                txt_DateOfBirth.getText().toString(),
                                gender,
                                txt_Address.getText().toString(),
                                txt_City.getText().toString(),
                                txt_Relationship.getText().toString()
                        );

                        if(isInserted == true){
                            Toast.makeText(MainActivity.this, "INFORMATION ADDED", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "NOT ADDED", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );
    }

    public void viewData(){
        btn_view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = mydata.getAllData();
                        if(res.getCount() == 0){
                            //show message
                            showMessage("DATA ERROR ", "NOTHING TO SHOW");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("ID: " + res.getString(0) + "\n");
                            buffer.append("FIRST NAME: " + res.getString(1) + "\n");
                            buffer.append("LAST NAME: " + res.getString(2) + "\n");
                            buffer.append("MI: " + res.getString(3) + "\n");
                            buffer.append("Date of Birth: " + res.getString(4) + "\n");
                            buffer.append("GENDER: " + res.getString(5)+  "\n");
                            buffer.append("ADDRESS: " + res.getString(6) + "\n");
                            buffer.append("CITY: " + res.getString(7) + "\n");
                            buffer.append("RELATIONSHIP: " + res.getString(8 ) + "\n\n");
                        }
                        res.close();

                        showMessage("INFORMATION LIST " , buffer.toString());
                    }
                }
        );
    }



    public void showMessage(String title, String message){
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setCancelable(true);
        build.setTitle(title);
        build.setMessage(message);
        build.show();
    }


}