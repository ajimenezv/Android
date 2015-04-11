package com.example.andrescamilo.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

    Button loginB;
    EditText userName;
    EditText password;
    EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginB=(Button)findViewById(R.id.loginButton);
        password=(EditText)findViewById(R.id.passwordText);
        userName=(EditText)findViewById(R.id.nameText);
        final Intent intent= new Intent(this,Lobby.class);
        final Bundle bundle =new Bundle();


        loginB.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View view){
                    String user=userName.getText().toString();
                    String pass=password.getText().toString();

                    if(user.compareTo("admin")==0 && pass.compareTo("admin")==0){
                        String myValue="Hola admin";
                        bundle.putString("myValue",myValue);
                        intent.putExtras(bundle);
                        MainActivity.this.startActivity(intent);
                    }
                }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
