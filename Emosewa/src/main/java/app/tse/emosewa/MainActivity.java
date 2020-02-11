package app.tse.emosewa;


import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.tse.emosewa.Database.UserDao;
import app.tse.emosewa.Model.User;
import app.tse.emosewa.Model.UserDatabase;

/*
 * mainproject class
 * */
public class MainActivity extends AppCompatActivity {

    TextView textRegister;
    Button btnLogin;

    EditText user,pass;
    ProgressDialog progressDialog;

    UserDao userDao;
    UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Checking User...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        userDatabase = Room.databaseBuilder(this, UserDatabase.class, "emosewa.db")
                .allowMainThreadQueries()
                .build();

        userDao = userDatabase.getUserDao();

        btnLogin=(Button)findViewById(R.id.login_btn);
        textRegister = (TextView) findViewById(R.id.text_register);

        user = (EditText) findViewById(R.id.login_input_username);
        pass = (EditText) findViewById(R.id.login_input_password);


        OnClickButtonListener();
        onClickRegisterListener();
    }

    private void onClickRegisterListener() {
        textRegister.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent= new Intent(MainActivity.this,RegisterActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }

    public void OnClickButtonListener(){
        btnLogin.setOnClickListener(
                                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!emptyValidation()){
                            progressDialog.show();
                            new Handler().postDelayed(()->{
                               User user2Login = userDao.getUser(user.getText().toString(), pass.getText().toString());
                               if(user2Login != null){
                                   Intent intent= new Intent(MainActivity.this,BeginPlace.class);
                                   startActivity(intent);
                                   finish();
                               }else{
                                   Toast.makeText(MainActivity.this, "Unregistered user, or incorrect", Toast.LENGTH_SHORT).show();
                               }
                               progressDialog.dismiss();
                            }, 1000);
                        }else{
                            Toast.makeText(MainActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                        }



                    }
                }
        );
    }
    //when back pressed to confirm whether accidently pressed or not
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        } else {
            Toast.makeText(this, "Press Back again to Exit",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

    public boolean emptyValidation(){
        if(TextUtils.isEmpty(user.getText().toString()) || TextUtils.isEmpty(pass.getText().toString())){
            return true;
        }else{
            return false;
        }
    }

}
