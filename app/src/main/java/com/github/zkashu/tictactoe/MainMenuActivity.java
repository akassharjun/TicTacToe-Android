package com.github.zkashu.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainMenuActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		Button vsComputer = findViewById(R.id.vsComputer);
		Button vsPlayer = findViewById(R.id.vsPlayer);
		
		Toolbar toolbar = findViewById(R.id.toolbar);
		
		setSupportActionBar(toolbar);
		
		
		vsComputer.setOnClickListener(v -> {
			Intent intent = new Intent(this, VSComputerActivity.class);
			
			intent.putExtra("difficulty", "easy");
			
			startActivity(intent);
		});
		
		vsPlayer.setOnClickListener(v -> startActivity(new Intent(this, VSPlayerActivity.class)));
		
		
	}
}
