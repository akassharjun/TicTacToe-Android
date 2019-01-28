package com.github.zkashu.tictactoe;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class VSComputerActivity extends AppCompatActivity implements OnClickListener {
	
	TicTacToe ticTacToe;
	
	public void showTokenDialog () {
		final View view = getLayoutInflater().inflate(R.layout.dialog_token, null);
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
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vscomputer);
		
		
		ActionBar actionBar = getSupportActionBar();
		
		if (actionBar != null) {
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			actionBar.setCustomView(R.layout.tb_layout);
		}
		
		TextView tv = findViewById(R.id.header);
		
		tv.setText(getString(R.string.vs_computer_title));
		
		Toolbar toolbar = findViewById(R.id.toolbar);
		
		setSupportActionBar(toolbar);
		
		showTokenDialog();
		
		for (int i = 1; i < 10; i++) {
			
			String string = getResources().getResourceName(R.id.pos1);
			
			Log.d("Name", string);
			
			String spot = "pos" + i;
			Log.d("Spot", spot);
			
			
			int id = getResources().getIdentifier(spot, "id", this.getPackageName());
			Log.d("ResID", String.valueOf(id));
			findViewById(id).setOnClickListener(this);
		}
		
	}
	
	@Override
	public void onBackPressed () {
		super.onBackPressed();
		finish();
	}
	
	private void fillSpot (int position, char token) {
		String spot = "pos" + position;
		int id = getResources().getIdentifier(spot, "id", this.getPackageName());
		
		
		if (id != 0) {
			ImageButton button = findViewById(id);
			
			if (token == 'O') {
				button.setImageResource(R.drawable.ic_default_token_o);
			} else {
				button.setImageResource(R.drawable.ic_token_x);
			}
			
		}
		
	}
	
	private void resetVisualBoard(){
		for (int i = 1; i < 10; i++) {
			String spot = "pos" + i;
			int id = getResources().getIdentifier(spot, "id", this.getPackageName());
			
			ImageButton button = findViewById(id);
			
			button.setImageResource(R.drawable.ic_token_empty);
			
		}
	}
	
	private void showGameOverDialog (char token) {
		final View view = getLayoutInflater().inflate(R.layout.dialog_game_over, null);
		AlertDialog tokenDialog = new Builder(VSComputerActivity.this).create();
		Objects.requireNonNull(tokenDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0x00000000));
		
		TextView message = view.findViewById(R.id.message);
		
		Button playAgain = view.findViewById(R.id.playAgain);
		Button quit = view.findViewById(R.id.quit);
		
		if (ticTacToe.playerToken == token) {
			message.setText(getString(R.string.player_won));
		} else if (ticTacToe.computerToken == token) {
			message.setText(getString(R.string.computer_won));
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
	
	@Override
	public void onClick (View v) {
		int position = Integer.valueOf(getResources().getResourceName(v.getId()).substring(34));
		
		switch (v.getId()) {
			case R.id.pos1:
				if (ticTacToe.isSpotFree(position)) {
					
					// Player Move
					
					ticTacToe.insertToken(position, ticTacToe.playerToken);
					fillSpot(position, ticTacToe.playerToken);
					
					// checking if the player has won
					if (ticTacToe.checkWin(ticTacToe.board, ticTacToe.playerToken)) {
						showGameOverDialog(ticTacToe.playerToken);
						break;
					}
					
					// Computer Move
					
					int move = ticTacToe.computerMove();
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
				
				break;
			
			case R.id.pos2:
				
				if (ticTacToe.isSpotFree(position)) {
					
					// Player Move
					
					ticTacToe.insertToken(position, ticTacToe.playerToken);
					fillSpot(position, ticTacToe.playerToken);
					
					// checking if the player has won
					if (ticTacToe.checkWin(ticTacToe.board, ticTacToe.playerToken)) {
						showGameOverDialog(ticTacToe.playerToken);
						break;
					}
					// Computer Move
					
					int move = ticTacToe.computerMove();
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
				
				break;
			case R.id.pos3:
				if (ticTacToe.isSpotFree(position)) {
					
					// Player Move
					
					ticTacToe.insertToken(position, ticTacToe.playerToken);
					fillSpot(position, ticTacToe.playerToken);
					
					// checking if the player has won
					if (ticTacToe.checkWin(ticTacToe.board, ticTacToe.playerToken)) {
						showGameOverDialog(ticTacToe.playerToken);
						break;
					}
					// Computer Move
					
					int move = ticTacToe.computerMove();
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
				
				break;
			case R.id.pos4:
				if (ticTacToe.isSpotFree(position)) {
					
					// Player Move
					
					ticTacToe.insertToken(position, ticTacToe.playerToken);
					fillSpot(position, ticTacToe.playerToken);
					
					// checking if the player has won
					if (ticTacToe.checkWin(ticTacToe.board, ticTacToe.playerToken)) {
						showGameOverDialog(ticTacToe.playerToken);
						break;
					}
					// Computer Move
					
					int move = ticTacToe.computerMove();
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
				
				break;
			case R.id.pos5:
				if (ticTacToe.isSpotFree(position)) {
					
					// Player Move
					
					ticTacToe.insertToken(position, ticTacToe.playerToken);
					fillSpot(position, ticTacToe.playerToken);
					
					// checking if the player has won
					if (ticTacToe.checkWin(ticTacToe.board, ticTacToe.playerToken)) {
						showGameOverDialog(ticTacToe.playerToken);
						break;
					}
					// Computer Move
					
					int move = ticTacToe.computerMove();
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
				
				break;
			case R.id.pos6:
				if (ticTacToe.isSpotFree(position)) {
					
					// Player Move
					
					ticTacToe.insertToken(position, ticTacToe.playerToken);
					fillSpot(position, ticTacToe.playerToken);
					
					// checking if the player has won
					if (ticTacToe.checkWin(ticTacToe.board, ticTacToe.playerToken)) {
						showGameOverDialog(ticTacToe.playerToken);
						break;
					}
					// Computer Move
					
					int move = ticTacToe.computerMove();
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
				
				break;
			case R.id.pos7:
				if (ticTacToe.isSpotFree(position)) {
					
					// Player Move
					
					ticTacToe.insertToken(position, ticTacToe.playerToken);
					fillSpot(position, ticTacToe.playerToken);
					
					// checking if the player has won
					if (ticTacToe.checkWin(ticTacToe.board, ticTacToe.playerToken)) {
						showGameOverDialog(ticTacToe.playerToken);
						break;
					}
					// Computer Move
					
					int move = ticTacToe.computerMove();
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
				
				break;
			case R.id.pos8:
				if (ticTacToe.isSpotFree(position)) {
					
					// Player Move
					
					ticTacToe.insertToken(position, ticTacToe.playerToken);
					fillSpot(position, ticTacToe.playerToken);
					
					// checking if the player has won
					if (ticTacToe.checkWin(ticTacToe.board, ticTacToe.playerToken)) {
						showGameOverDialog(ticTacToe.playerToken);
						break;
					}
					// Computer Move
					
					int move = ticTacToe.computerMove();
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
				
				break;
			case R.id.pos9:
				if (ticTacToe.isSpotFree(position)) {
					
					// Player Move
					
					ticTacToe.insertToken(position, ticTacToe.playerToken);
					fillSpot(position, ticTacToe.playerToken);
					
					// checking if the player has won
					if (ticTacToe.checkWin(ticTacToe.board, ticTacToe.playerToken)) {
						showGameOverDialog(ticTacToe.playerToken);
						break;
					}
					// Computer Move
					
					int move = ticTacToe.computerMove();
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
				
				break;
			default:
				break;
		}
	}
}
