package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout
import com.example.tic.R

class MainActivity : AppCompatActivity() {

    private var currentPlayer = "X"
    private val board = Array(3) { arrayOfNulls<String>(3) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttons = arrayOf(
            arrayOf(
                findViewById<Button>(R.id.button00),
                findViewById(R.id.button01),
                findViewById(R.id.button02)
            ),
            arrayOf(
                findViewById(R.id.button10),
                findViewById(R.id.button11),
                findViewById(R.id.button12)
            ),
            arrayOf(
                findViewById(R.id.button20),
                findViewById(R.id.button21),
                findViewById(R.id.button22)
            )
        )

        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                buttons[i][j].setOnClickListener {
                    onButtonClick(buttons[i][j], i, j)
                }
            }
        }

        findViewById<Button>(R.id.restartButton).setOnClickListener {
            resetGame(buttons)
        }
    }

    private fun onButtonClick(button: Button, row: Int, col: Int) {
        if (button.text.isEmpty()) {
            button.text = currentPlayer
            board[row][col] = currentPlayer
            if (checkWin()) {
                Toast.makeText(this, "Player $currentPlayer wins!", Toast.LENGTH_LONG).show()
                disableBoard()
            } else if (isBoardFull()) {
                Toast.makeText(this, "It's a draw!", Toast.LENGTH_LONG).show()
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
            }
        }
    }

    private fun checkWin(): Boolean {
        // Check rows, columns, and diagonals
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) return true
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true
        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in board) {
            if (row.contains(null)) return false
        }
        return true
    }

    private fun disableBoard() {
        findViewById<GridLayout>(R.id.gameBoard).let { gridLayout ->
            for (i in 0 until gridLayout.childCount) {
                val button = gridLayout.getChildAt(i) as Button
                button.isEnabled = false
            }
        }
    }

    private fun resetGame(buttons: Array<Array<Button>>) {
        currentPlayer = "X"
        for (i in board.indices) {
            for (j in board[i].indices) {
                board[i][j] = null
                buttons[i][j].text = ""
                buttons[i][j].isEnabled = true
            }
        }
    }
}