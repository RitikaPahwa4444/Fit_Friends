package com.example.fitfriends

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.fitfriends.databinding.ActivityGamePlayBinding
import kotlin.random.Random

class GamePlay : AppCompatActivity() {
    // All the main handling of game play is handled in this activity
    private var binding: ActivityGamePlayBinding? = null
    private lateinit var chronometer: Chronometer

    var choice : Int = 1
    var flaggedMines = 0
    var fastestTime = " NA "
    var lastGameTime = " NA "
    var status = Status.ONGOING
    private var isPlay = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamePlayBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        chronometer = findViewById(R.id.timer)
        if(!isPlay){
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()
            Toast.makeText(this, "Game Starts!", Toast.LENGTH_SHORT).show()
            isPlay = true
        }

        val intent = intent
        var flag = intent.getIntExtra("flag", 2)

        // Receiving the level through intent
        if(flag == 1){
            var level = intent.getStringExtra("selectedLevel")
            if (level != null) {
                if(level.equals("easy")){
                    setUpBoard(8, 8, 12)
                }else if(level.equals("medium")){
                    setUpBoard(12, 12 ,24)
                }else if(level.equals("hard")){
                    setUpBoard(16, 16, 30)
                }
            }
        }else{
            var row = intent.getIntExtra("height", 3)
            var col = intent.getIntExtra("width", 3)
            var mine = intent.getIntExtra("mines", 1)
            setUpBoard(row, col, mine)
        }

        binding?.restart?.setOnClickListener {
            gameRestart()
        }

    }


    // This function will be setting up the board and buttons according to the level selected
    private fun setUpBoard(row:Int, col:Int, mine:Int){
        //Showing the total number of mines
        binding?.minesLeft?.text = ""+mine
        //binding?.mines_left?.text = ""+mine

        val cellBoard = Array(row) { Array(col) {MineCell(this)} }
        binding?.mineFlag?.setOnClickListener {
            if(choice == 1){
                binding?.mineFlag?.setImageResource(R.drawable.flagi)
                choice = 2
            }else{
                binding?.mineFlag?.setImageResource(R.drawable.bombi)
                choice = 1
            }
        }

        var counter = 1
        var isFirstClick = true

        val params1 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0
        )
        val params2 = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
// Setting up the grid using linear layout
        for (i in 0 until row){
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.layoutParams = params1
            params1.weight = 1.0F

            for(j in 0 until col){
                val button = MineCell(this)

                cellBoard[i][j] = button

                button.id = counter
                button.textSize = 18.0F

                button.layoutParams = params2
                params2.weight = 1.0F
                button.setBackgroundResource(R.drawable.ten)
                button.setOnClickListener{
                    //Calling the function to set mines on the first click
                    if(isFirstClick){
                        isFirstClick = false

                        setMines(i, j, mine, cellBoard, row, col)

                        startTimer()
                    }
                    move(choice, i, j, cellBoard, row, col, mine)
                    display(cellBoard)
                }
                linearLayout.addView(button)
                counter++
            }
            binding?.board?.addView(linearLayout)
        }

    }



    // Function to set up mines at random locations on the board
    private fun setMines(row: Int, col: Int,mine: Int, cellBoard: Array<Array<MineCell>>, rowSize: Int, colSize: Int){
        var mineCount = mine
        var i = 1
        while(i<=mineCount){
            var r = (Random(System.nanoTime()).nextInt(0, rowSize))
            var c = (Random(System.nanoTime()).nextInt(0, colSize))
            // If the selected random coordinates already contain a mine, then continue
            // Otherwise set isMine true and update the neighbours

            if(r==row||cellBoard[r][c].isMine){
                continue
            }
            cellBoard[r][c].isMine = true
            cellBoard[r][c].value = -1
            updateNeighbours(r,c,cellBoard,rowSize,colSize)
            i++;
        }
    }
    //After a bomb has been set, neighbouring cells are uppdated
    private fun updateNeighbours(row: Int,column: Int,cellBoard: Array<Array<MineCell>>,rowSize:Int,colSize:Int) {
        for (i in movement) {
            for (j in movement) {
                if(((row+i) in 0 until rowSize) && ((column+j) in 0 until colSize) && cellBoard[row+i][column+j].value != MINE)
                    cellBoard[row+i][column+j].value++
            }
        }
    }

    //This function is responsible for starting the timer
    private fun startTimer(){
        chronometer = findViewById(R.id.timer)
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()
    }

    //This function is responsible for moving, in order to reveal all the adjacent non-bomb cells
    public fun move(choice: Int, x: Int, y: Int, cellBoard: Array<Array<MineCell>>, rowSize: Int,
               colSize: Int, mine: Int) : Boolean {
        if(choice == 1){
            if(cellBoard[x][y].isMarked || cellBoard[x][y].isRevealed){
                return false
            }
            if(cellBoard[x][y].value==MINE){
                status = Status.LOST
                updateScore()
                return true
            }
            else if (cellBoard[x][y].value>0){
                cellBoard[x][y].isRevealed=true
                checkStatus(cellBoard, rowSize, colSize)
                return true
            }
            else if (cellBoard[x][y].value == 0){
                handleZero(x, y, cellBoard, rowSize, colSize)
                checkStatus(cellBoard, rowSize, colSize)
                return true
            }
        }
        if (choice == 2){
            if(cellBoard[x][y].isRevealed){
                return false
            }
            else if (cellBoard[x][y].isMarked){
                flaggedMines--
                cellBoard[x][y].setBackgroundResource(R.drawable.ten)
                cellBoard[x][y].isMarked = false
                checkStatus(cellBoard,rowSize,colSize)
            }
            else{
                if (flaggedMines==mine){
                    Toast.makeText(this, "You can not mark more than $mine mines", Toast.LENGTH_LONG).show()
                    return false
                }
                flaggedMines++
                cellBoard[x][y].isMarked=true
                checkStatus(cellBoard, rowSize, colSize)

            }
            var finalMineCount = mine-flaggedMines
            //binding?.mines_left?.text = ""+finalMineCount
            binding?.minesLeft?.text = ""+finalMineCount
            return true
        }
        return false
    }

    //These two arrays give all the possible eight neighbouring cells by forming the coordinates
    private val xDir = intArrayOf(-1, -1, 0, 1, 1, 1, 0, -1)
    private val yDir = intArrayOf(0, 1, 1, 1, 0, -1, -1, -1)

    //When the value of the Mine cell is zero, then it is handled by this function
    private fun handleZero(x: Int, y: Int, cellBoard: Array<Array<MineCell>>, rowSize: Int, colSize: Int){
        cellBoard[x][y].isRevealed=true
        for (i in 0..7){
            var xstep = x+xDir[i]
            var ystep = y+yDir[i]
            if(xstep<0 || xstep>=rowSize || ystep<0 || ystep>=colSize){
                continue
            }
            if (cellBoard[xstep][ystep].value > 0 && !cellBoard[xstep][ystep].isMarked){
                cellBoard[xstep][ystep].isRevealed = true
            }else if (!cellBoard[xstep][ystep].isRevealed && !cellBoard[xstep][ystep].isMarked && cellBoard[xstep][ystep].value ==0){
                handleZero(xstep, ystep, cellBoard, rowSize, colSize)
            }
        }
    }

    //We have to check status after every move
    private fun checkStatus(cellBoard: Array<Array<MineCell>>, rowSize: Int, colSize: Int){
        var flag1 = 0
        var flag2 = 0
        for (i in 0 until rowSize){
            for (j in 0 until colSize){
                if (cellBoard[i][j].value== MINE && !cellBoard[i][j].isMarked){
                    flag1 = 1
                }
                if (cellBoard[i][j].value!=MINE && !cellBoard[i][j].isRevealed){
                    flag2 = 1
                }
            }
        }
        if (flag1 == 0 || flag2==0){
            status = Status.WON
        }else{
            status = Status.ONGOING
        }

        if (status == Status.WON){
            updateScore()
        }
    }

    //This is responsible for restarting the game from the beginning
    private fun gameRestart(){
        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Do you want to restart the game? (o_o) ")
        builder.setTitle("Alert!")
        builder.setCancelable(false)

        builder.setPositiveButton("Yes"){
                dialog, which ->
            val intent = getIntent()
            finish()
            startActivity(intent)
        }

        builder.setNegativeButton("No", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

            }
        })
        val alertDialog = builder.create()
        alertDialog.show()
    }


    //This function is responsible for preventing unwanted exit from the game if the back-button is pressed mistakenly
    override fun onBackPressed() {
        val builder : AlertDialog.Builder= AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to exit the game?")
        builder.setTitle("Game is still ongoing!")
        builder.setCancelable(false)

        builder.setPositiveButton("Yes"){
                dialog, which ->
            updateScore()
            toMainActivity()
            finish()
            super.onBackPressed()
        }

        builder.setNegativeButton("No", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
            }
        })
        val alertDialog = builder.create()
        alertDialog.show()
    }

    //This function updates the score in terms of time
    private fun updateScore(){
        chronometer.stop()

        val elapsedTime = SystemClock.elapsedRealtime() - chronometer.base
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val lastTime = elapsedTime.toInt()

        var highScore = sharedPref.getInt("highscore", Integer.MAX_VALUE)
        var isHighScore = false

        if(status == Status.WON){
            if(lastTime<highScore){
                highScore = lastTime
                isHighScore = true
            }
            with(sharedPref.edit()){
                putInt("highscore", highScore)
                putInt("lastTime", lastTime)
                commit()
            }

            lastGameTime = ""+((lastTime/1000)/60)+" m "+((lastTime/1000)%60)+" s"

        }
        else{
            lastGameTime = " Lost! "
            fastestTime = " NA "
        }
        if (highScore==Integer.MAX_VALUE){
            fastestTime = " NA "

        }
        else{
            fastestTime = "" +((highScore / 1000) / 60) + " m " + ((highScore / 1000) % 60) + " s "
        }

        if(status == Status.WON){
            var currentTime=binding?.timer?.text.toString()
            println("current time $currentTime" )
            val sharedPreferences: SharedPreferences =
                this.getSharedPreferences("time", Context.MODE_PRIVATE)

            val best=sharedPreferences.getString("Best","00.00")
            if(best!! > currentTime){
                sharedPreferences.edit().putString("Best",currentTime).apply()
            }
            binding?.timer?.stop()

            val intent= Intent(this, results::class.java).apply{
                putExtra("result","Congratulations 😎 \nYOU WON")
            }
            startActivity(intent)
        }

        else if (status == Status.LOST){
            var currentTime = binding?.timer?.text.toString()
            println("Current time $currentTime")

            val sharedPreferences : SharedPreferences =
                this.getSharedPreferences("time", Context.MODE_PRIVATE)

            sharedPreferences.edit().putString("Last", currentTime).apply()
            //timer.stop()

            val intent = Intent(this, results::class.java).apply {
                putExtra("result", "You Lost\nTry Again")
            }
            startActivity(intent)
        }
    }

    //We are giving the lastGameTime and fastestTime to the main activity for Display
    private fun toMainActivity(){
        Log.d("MainActivity", "inside main $fastestTime $lastGameTime")
        val intent = Intent(this@GamePlay, MainActivity::class.java)
        intent.putExtra("Best", fastestTime)
        intent.putExtra("Last", lastGameTime)
        startActivity(intent)
    }

    //This function is responsible for displaying the mine-field and other features associated with it's state
    private fun display(cellBoard: Array<Array<MineCell>>){
        cellBoard.forEach { row->
            row.forEach {
                if (it.isRevealed){
                    setNumberImage(it)
                }else if (it.isMarked){
                    it.setBackgroundResource(R.drawable.flag)
                }else if (status == Status.LOST && it.value == MINE){
                    binding?.restart?.setImageResource(R.drawable.sad_face)
                    it.setBackgroundResource(R.drawable.mine)
                }

                if(status==Status.LOST && it.isMarked && !it.isMine){
                    it.setBackgroundResource(R.drawable.crossedflag)
                }
                else if (status==Status.WON && it.value == MINE){
                    it.setBackgroundResource(R.drawable.flag)
                    binding?.restart?.setImageResource(R.drawable.won)
                }
                else{
                    it.text = " "
                }
            }

        }
    }


    //Here we are setting number images corresponding to the values of the mine cell
    private fun setNumberImage(button:MineCell) {
        if(button.value==0) button.setBackgroundResource(R.drawable.zero)
        if(button.value==1) button.setBackgroundResource(R.drawable.one)
        if(button.value==2) button.setBackgroundResource(R.drawable.two)
        if(button.value==3) button.setBackgroundResource(R.drawable.three)
        if(button.value==4) button.setBackgroundResource(R.drawable.four)
        if(button.value==5) button.setBackgroundResource(R.drawable.five)
        if(button.value==6) button.setBackgroundResource(R.drawable.six)
        if(button.value==7) button.setBackgroundResource(R.drawable.seven)
        if(button.value==8) button.setBackgroundResource(R.drawable.eight)

    }

    //The Minecell where we have a bomb, has a value of -1
    companion object{
        const val MINE = -1
        val movement = intArrayOf(-1, 0, 1)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}


enum class Status{
    WON,
    ONGOING,
    LOST
}