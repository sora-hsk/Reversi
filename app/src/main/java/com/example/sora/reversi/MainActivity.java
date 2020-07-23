package com.example.sora.reversi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.nio.file.attribute.BasicFileAttributeView;

public class MainActivity extends AppCompatActivity {
    Reversi rv;
    Koma koma = new Koma();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (Reversi) findViewById(R.id.view);
        rv.setFirstFlg(true);
    }

    public void reset_onClick(View view){
        rv = (Reversi) findViewById(R.id.view);
        koma.reset(rv.getMin());
        rv.setCpuFlg(false);
        rv.setTouchFlg(true);
        rv.invalidate();
    }

    public void cpu_onClick(View view){
        rv = (Reversi) findViewById(R.id.view);
        rv.changeCpuFlg();
        rv.invalidate();
    }
}