package app.tse.emosewa;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.tse.emosewa.Database.UserDao;
import app.tse.emosewa.Model.User;
import app.tse.emosewa.Model.UserDatabase;


public class RegisterActivity extends AppCompatActivity {

    private TextView textBack;
    private EditText user,pass;
    private Button btn_register;

    private UserDao userDao;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpage);


        // ProgressDialog config
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Registering...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        textBack = (TextView) findViewById(R.id.text_back);
        onClickTextBackListener();

        btn_register = (Button) findViewById(R.id.register_btn);
        user = (EditText) findViewById(R.id.login_input_username);
        pass = (EditText) findViewById(R.id.login_input_password);

        btn_register.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if(!isEmpty()){
                            progressDialog.show();
                            new Handler().postDelayed(()->{
                                User newUser = new User(user.getText().toString(), pass.getText().toString());
                                userDao.insert(newUser);
                                progressDialog.dismiss();

                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            },1000);

                        }else{
                            Toast.makeText(RegisterActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );


        userDao = Room.databaseBuilder(this, UserDatabase.class, "emosewa.db")
                .allowMainThreadQueries()
                .build()
                .getUserDao();

    }



    private boolean isEmpty(){
        if(TextUtils.isEmpty(user.getText().toString()) ||
            TextUtils.isEmpty(pass.getText().toString())){
            return true;
        }
        else{
            return false;
        }
    }


    private void onClickTextBackListener() {
        textBack.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }
}

