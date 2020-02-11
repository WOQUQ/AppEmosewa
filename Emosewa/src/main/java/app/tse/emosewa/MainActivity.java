package app.tse.emosewa;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*
 * mainproject class
 * */
public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complex_activity_main);
        btnLogin=(Button)findViewById(R.id.login_btn);
        OnClickButtonListener();
    }

    public void OnClickButtonListener(){
        btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent= new Intent(MainActivity.this,BeginPlace.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }

}
