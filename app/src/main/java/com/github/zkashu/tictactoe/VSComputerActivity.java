package com.github.zkashu.tictactoe;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class VSComputerActivity extends AppCompatActivity implements View.OnClickListener {
	
	TicTacToe ticTacToe;
	String difficulty;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		TextView tv = findViewById(R.id.header);
		
		tv.setText(getString(R.string.vs_computer_title));
		
		Toolbar toolbar = findViewById(R.id.toolbar);
		
		setSupportActionBar(toolbar);
		
		Objects.requireNonNull(getSupportActionBar()).setTitle("");
		
		showTokenDialog();
		
		difficulty = getIntent().getStringExtra("difficulty");
		
		for (int i = 1; i < 10; i++) {
			String name = "spot" + i;
			int id = getResources().getIdentifier(name, "id", this.getPackageName());
			findViewById(id).setOnClickListener(this::onClick);
		}
		
		findViewById(R.id.back).setOnClickListener(v -> finish());
		
	}
	
	private void resetVisualBoard () {
		for (int i = 1; i < 10; i++) {
			String spot = "spot" + i;
			int id = getResources().getIdentifier(spot, "id", this.getPackageName());
			
			ImageView imageView = findViewById(id);
			
			if (imageView.getDrawable() != null) {
				imageView.setImageDrawable(null);
			}
			
		}
	}
	
	private void fillSpot (int spot, char token) {
		String pos = "spot" + spot;
		int id = getResources().getIdentifier(pos, "id", this.getPackageName());
		
		
		if (id != 0) {
			ImageView spotImage = findViewById(id);
			
			if (token == 'O') {
				spotImage.setImageResource(R.drawable.ic_default_token_o);
			} else {
				spotImage.setImageResource(R.drawable.ic_default_token_x);
			}
			
		}
		
	}
	
	public void showTokenDialog () {
		final View view = getLayoutInflater().inflate(R.layout.dialog_select_token, null);
		AlertDialog tokenDialog = new Builder(VSComputerActivity.this).create();
		Objects.requireNonNull(tokenDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0x00000000));
		
		ImageButton tokenO = view.findViewById(R.id.tokenO);
		ImageButton tokenX = view.findViewById(R.id.tokenX);
		
		tokenO.setOnClickListener(v -> {
					ticTacToe = new TicTacToe('O', 'X');
					tokenDialog.dismiss();
				}
		);
		
		tokenX.setOnClickListener(v -> {
					ticTacToe = new TicTacToe('X', 'O');
					tokenDialog.dismiss();
				}
		);
		
		tokenDialog.setCancelable(false);
		tokenDialog.setView(view);
		tokenDialog.show();
	}
	
	private void showGameOverDialog (char token) {
		
		//initializing the game over alert dialog
		final View view = getLayoutInflater().inflate(R.layout.dialog_game_over, null);
		AlertDialog tokenDialog = new Builder(VSComputerActivity.this).create();
		Objects.requireNonNull(tokenDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0x00000000));
		
		TextView message = view.findViewById(R.id.message);
		
		Button playAgain = view.findViewById(R.id.playAgain);
		Button quit = view.findViewById(R.id.quit);
		
		// setting the final board of the tic tac toe board
		
		for (int pos = 1; pos < 10; pos++) {
			
			String mainName = "spot" + pos;
			int mainID = getResources().getIdentifier(mainName, "id", this.getPackageName());
			
			ImageView mainImageView = findViewById(mainID);
			
			String name = "pos" + pos;
			int id = getResources().getIdentifier(name, "id", this.getPackageName());
			
			ImageView imageView = view.findViewById(id);
			
			if (mainImageView.getDrawable() != null) {
				imageView.setImageDrawable(mainImageView.getDrawable());
			}
		}
		
		// setting the game over message
		
		if (ticTacToe.playerToken == token) {
			message.setText(getString(R.string.player_won));
			int id = getResources().getIdentifier(ticTacToe.getWinRow().toLowerCase(), "id", this.getPackageName());
			
			view.findViewById(id).setVisibility(View.VISIBLE);
		} else if (ticTacToe.computerToken == token) {
			message.setText(getString(R.string.computer_won));
			int id = getResources().getIdentifier(ticTacToe.getWinRow().toLowerCase(), "id", this.getPackageName());
			
			view.findViewById(id).setVisibility(View.VISIBLE);
		} else {
			message.setText(getString(R.string.draw));
			
		}
		
		playAgain.setOnClickListener(v -> {
			tokenDialog.dismiss();
			ticTacToe.resetBoard();
			resetVisualBoard();
			showTokenDialog();
		});
		
		quit.setOnClickListener(v -> {
			finish();
		});
		
		tokenDialog.setCancelable(false);
		tokenDialog.setView(view);
		tokenDialog.show();
	}
	
	private void gameTurn (int pos) {
		
		if (ticTacToe.isSpotFree(pos)) {
			
			// Player Move
			ticTacToe.insertToken(pos, ticTacToe.playerToken);
			fillSpot(pos, ticTacToe.playerToken);
			
			// checking if the player has won
			if (ticTacToe.checkWin(ticTacToe.board, ticTacToe.playerToken)) {
				showGameOverDialog(ticTacToe.playerToken);
				return;
			}
			
			// Computer Move
			
			int move;
			
			if (difficulty.equalsIgnoreCase("EASY")) {
				move = ticTacToe.computerMoveEasy();
			} else {
				move = ticTacToe.computerMove();
			}
			
			ticTacToe.insertToken(move, ticTacToe.computerToken);
			fillSpot(move, ticTacToe.computerToken);
			
			// checking if the computer has won
			if (ticTacToe.checkWin(ticTacToe.board, ticTacToe.computerToken)) {
				showGameOverDialog(ticTacToe.computerToken);
			}
		} else {
			Toast.makeText(this, "This spot isn't free.", Toast.LENGTH_SHORT).show();
		}
		
		
		if (ticTacToe.isBoardFull()) {
			showGameOverDialog('-');
		}
	}
	
	@Override
	public void onClick (View v) {
		
		for (int pos = 1; pos < 10; pos++) {
			String name = "spot" + pos;
			int id = getResources().getIdentifier(name, "id", this.getPackageName());
			
			if (v.getId() == id) {
				gameTurn(pos);
				break;
			}
			
		}
		
	}
}
