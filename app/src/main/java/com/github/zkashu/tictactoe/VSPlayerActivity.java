package com.github.zkashu.tictactoe;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;

public class VSPlayerActivity extends AppCompatActivity implements View.OnClickListener {
	
	TicTacToe ticTacToe;
	boolean isPlayerOneTurn;
	TextView turnHeader;
	
	@Override
	protected void onCreate (@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		for (int i = 1; i < 10; i++) {
			String name = "spot" + i;
			int id = getResources().getIdentifier(name, "id", this.getPackageName());
			findViewById(id).setOnClickListener(this::onClick);
		}
		
		turnHeader = findViewById(R.id.turnHeader);
		
		ImageView imageView = findViewById(R.id.resetBoard);
		
		imageView.setOnClickListener(v -> {
			ticTacToe.resetBoard();
			resetVisualBoard();
			showTokenDialog();
		});
		
		showTokenDialog();
		
	}
	
	public void showTokenDialog () {
		final View view = getLayoutInflater().inflate(R.layout.dialog_select_token, null);
		AlertDialog tokenDialog = new Builder(VSPlayerActivity.this).create();
		Objects.requireNonNull(tokenDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0x00000000));
		
		ImageButton tokenO = view.findViewById(R.id.tokenO);
		ImageButton tokenX = view.findViewById(R.id.tokenX);
		
		tokenO.setOnClickListener(v -> {
					ticTacToe = new TicTacToe('O', 'X', true);
					turnHeader.setVisibility(View.VISIBLE);
					findViewById(R.id.playerOnePointsLayer).setVisibility(View.VISIBLE);
					findViewById(R.id.playerTwoPointsLayer).setVisibility(View.VISIBLE);
					tokenDialog.dismiss();
				}
		);
		
		tokenX.setOnClickListener(v -> {
					ticTacToe = new TicTacToe('X', 'O', true);
					turnHeader.setVisibility(View.VISIBLE);
					findViewById(R.id.playerOnePointsLayer).setVisibility(View.VISIBLE);
					findViewById(R.id.playerTwoPointsLayer).setVisibility(View.VISIBLE);
					tokenDialog.dismiss();
				}
		);
		
		tokenDialog.setCancelable(false);
		tokenDialog.setView(view);
		tokenDialog.show();
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
	
	private void gameTurn (int pos) {
		
		if (ticTacToe.isPlayerOneTurn) {
			// Player 1's turn.
			
			if (ticTacToe.isSpotFree(pos)) {
				
				// Player Move
				ticTacToe.insertToken(pos, ticTacToe.playerOneToken);
				fillSpot(pos, ticTacToe.playerOneToken);
				
				// checking if the player has won
				if (ticTacToe.checkWin(ticTacToe.board, ticTacToe.playerOneToken)) {
					showGameOverDialog(ticTacToe.playerOneToken);
					return;
				}
				
				
				ticTacToe.isPlayerOneTurn = false;
				
				turnHeader.setText("Player 2's turn. (X)");
				
			} else {
				Toast.makeText(this, "This spot isn't free.", Toast.LENGTH_SHORT).show();
			}
			
			
			if (ticTacToe.isBoardFull()) {
				showGameOverDialog('-');
			}
			
			
		} else {
			// Player 2's turn.
			
			if (ticTacToe.isSpotFree(pos)) {
				
				// Player Move
				ticTacToe.insertToken(pos, ticTacToe.playerTwoToken);
				fillSpot(pos, ticTacToe.playerTwoToken);
				
				// checking if the player has won
				if (ticTacToe.checkWin(ticTacToe.board, ticTacToe.playerTwoToken)) {
					showGameOverDialog(ticTacToe.playerTwoToken);
					return;
				}
				
				ticTacToe.isPlayerOneTurn = true;
				
				turnHeader.setText("Player 1's turn. (O)");
				
			} else {
				Toast.makeText(this, "This spot isn't free.", Toast.LENGTH_SHORT).show();
			}
			
			
			if (ticTacToe.isBoardFull()) {
				showGameOverDialog('-');
			}
			
			
		}
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
	
	private void showGameOverDialog (char token) {
		
		//initializing the game over alert dialog
		final View view = getLayoutInflater().inflate(R.layout.dialog_game_over, null);
		AlertDialog tokenDialog = new Builder(VSPlayerActivity.this).create();
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
		
		if (ticTacToe.playerOneToken == token) {
			message.setText(("Player 1 won! +10 pts"));
			
			int id = getResources().getIdentifier(ticTacToe.getWinRow().toLowerCase(), "id", this.getPackageName());
			
			TextView playerOnePoints = findViewById(R.id.playerOnePoints);
			int pts = Integer.parseInt(playerOnePoints.getText().toString().replace(" pts", "")) + 10;
			playerOnePoints.setText(String.format(Locale.US, "%d pts", pts));
			
		} else if (ticTacToe.playerTwoToken == token) {
			message.setText(("Player 2 won! +10 pts"));
			
			TextView playerTwoPoints = findViewById(R.id.playerTwoPoints);
			int pts = Integer.parseInt(playerTwoPoints.getText().toString().replace(" pts", "")) + 10;
			playerTwoPoints.setText(String.format(Locale.US, "%d pts", pts));
		} else {
			message.setText(getString(R.string.draw));
		}
		
		int id = getResources().getIdentifier(ticTacToe.getWinRow().toLowerCase(), "id", this.getPackageName());
		//setting the win stroke
		view.findViewById(id).setVisibility(View.VISIBLE);
		
		
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
