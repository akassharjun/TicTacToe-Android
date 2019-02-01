package com.github.zkashu.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button vsComputer = findViewById(R.id.vsComputer);
		
		Toolbar toolbar = findViewById(R.id.toolbar);
		
		setSupportActionBar(toolbar);
		
		
		vsComputer.setOnClickListener(v -> {
			Intent intent = new Intent(this, Frag.class);
			
			intent.putExtra("mode", "normal");
			
			startActivity(intent);
		});
		
	}
}
