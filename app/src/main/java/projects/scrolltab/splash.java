package projects.scrolltab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                SharedPreferences sharedPreferences=getSharedPreferences("mydata", CONTEXT_IGNORE_SECURITY);

                boolean check=sharedPreferences.getBoolean("install",false);
                if(check==true)
                {
                    Intent i = new Intent(splash.this, MainActivity.class);
                    Bundle bun;
                    startActivity(i);
                    Toast.makeText(splash.this, "check is true", Toast.LENGTH_SHORT).show();
                }
                else
                {    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean("install",true);
                    editor.commit();
                    Toast.makeText(splash.this, "check was false", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(splash.this,defaultcheck.class);
                    startActivity(i);
                }
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);



    }
}
