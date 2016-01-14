package projects.scrolltab;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class defaultcheck extends AppCompatActivity {
    CheckBox checkenglish;
    CheckBox checkhindi;
    CheckBox checkbangla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defaultcheck);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         checkenglish = (CheckBox) findViewById(R.id.checkenglish);
         checkhindi = (CheckBox) findViewById(R.id.checkhindi);
         checkbangla = (CheckBox) findViewById(R.id.checkbangla);

    }


    public void setcheck(View view) {
        if (view==checkenglish)
        { Toast.makeText(defaultcheck.this, "english is checked as default", Toast.LENGTH_SHORT).show();
            checkbangla.setChecked(false);
            checkhindi.setChecked(false);
        }
        else if (view==checkhindi) {
            Toast.makeText(defaultcheck.this, "hindi is checked as default", Toast.LENGTH_SHORT).show();
            checkbangla.setChecked(false);
            checkenglish.setChecked(false);
        } else if (view==checkbangla) {
            Toast.makeText(defaultcheck.this, "bangla is checked as default", Toast.LENGTH_SHORT).show();
            checkenglish.setChecked(false);
            checkhindi.setChecked(false);
        }

            Intent i = new Intent(defaultcheck.this, MainActivity.class);
            startActivityForResult(i,0);


    }
}
