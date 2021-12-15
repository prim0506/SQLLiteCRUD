package com.example.javacrud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DBHelper myDB;

    EditText userName, userAge, userSite, id;
    Button btnSelect, btnInsert, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DBHelper(this);

        userName = findViewById(R.id.userName);
        userAge = findViewById(R.id.userAge);
        userSite = findViewById(R.id.userSite);
        id = findViewById(R.id.id);

        btnSelect = findViewById(R.id.btnSelect);
        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        AddData();
        viewAll();
        UpdateData();
        DeleteData();

    }

    // DB 추가하기
    public void AddData(){
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDB.insertData(userName.getText().toString(),
                        userAge.getText().toString(),
                        userSite.getText().toString());

                if(isInserted == true)
                    Toast.makeText(MainActivity.this,"데이터추가 성공", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this,"데이터추가 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // DB 읽어오기
    public void viewAll(){
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDB.getAllData();
                if(res.getCount() == 0){
                    ShowMessage("실패", "데이터를 찾을 수 없습니다.");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("이름: " + res.getString(1) + "\n");
                    buffer.append("나이: " + res.getString(2) + "\n");
                    buffer.append("지역: " + res.getString(3) + "\n");
                }
                ShowMessage("데이터", buffer.toString());
            }
        });
    }

    // DB 수정하기
    public void UpdateData(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDB.updateData(id.getText().toString(),
                        userName.getText().toString(),
                        userAge.getText().toString(),
                        userSite.getText().toString());

                if(isUpdate == true)
                    Toast.makeText(MainActivity.this, "데이터 수정 성공", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "데이터 수정 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // DB 삭제하기
    public void DeleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows = myDB.deleteData(id.getText().toString());
                if (deleteRows > 0)
                    Toast.makeText(MainActivity.this, "데이터 삭제 성공", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "데이터 삭제 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ShowMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}