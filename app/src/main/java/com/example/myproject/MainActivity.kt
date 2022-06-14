package com.example.myproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.Text as Text
import com.example.myproject.ui.theme.MyProjectTheme as MyProjectTheme1


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyProjectTheme1 {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyScaffold()
                }
            }
        }
    }
}

@Composable
fun Mycom(){
    val count = remember { mutableStateOf("0") }
    val m = remember { mutableStateOf("0") }
    val arrtatal = Array(m.value.toInt()) { i -> i * 1 }
    val arrline = Array(m.value.toInt()*m.value.toInt()) { i -> i * 1 }
    for (i in arrline.indices){
        arrline[i] = 0
    }
    val way = Array(m.value.toInt()) { i -> i * 1 }//路徑
    val time= Array(m.value.toInt()) { i -> i * 1 }//經過點的次數
    var item = "1"
    Column(modifier = Modifier.fillMaxHeight()/*.background(color = Color.Black)*/) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.height(100.dp)) {
            OutlinedTextField(
                value = count.value,
                onValueChange = { count.value = it },
                label = { Text("輸入點的數量") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color.White)
            )
            Button(
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 5.dp)
                    .fillMaxHeight(),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    m.value = count.value
                    for(i in arrtatal.indices){
                        arrtatal[i] = 0
                    }
                }
            ) {
                Text(text ="輸入")
            }
        }
        LazyColumn(
            modifier = Modifier
                .height(450.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)){
            item { Text(text = "點與點之間是否連接：") }
            items(m.value.toInt()) {
                    index -> Text(text = "$index")
                val x = index
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)){
                    items(m.value.toInt()){
                        index -> Text(text = "$index")
                        val y = index
                        val isSelected = remember { mutableStateOf(false)}
                        if(arrline[x*m.value.toInt()+y] == 0){
                            isSelected.value = false
                        }
                        RadioButton(
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Blue,
                                unselectedColor = Color.Gray,
                                disabledColor = Color.White
                            ),
                            enabled = true,
                            selected = isSelected.value,
                            onClick = {
                                isSelected.value = !isSelected.value
                                if(arrline[x*m.value.toInt()+y] == 1){
                                    arrline[x*m.value.toInt()+y] = -1
                                }
                                else{
                                    arrline[x*m.value.toInt()+y] = 1
                                }
                                arrtatal[x] += arrline[x*m.value.toInt()+y]
                            })
                    }
                }

            }
        }
        val openDialog = remember { mutableStateOf(false) }
        val result = remember { mutableStateOf("不符合")}
        Box(modifier = Modifier.padding(bottom = 20.dp)) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 5.dp)
                    .height(50.dp),
                onClick = {
                    result.value = "不符合"
                    //判斷是否符合漢彌爾頓迴路
                    val condition:Int = (m.value.toInt() - 1)/2
                    var number = 0
                    var decide = 0
                    if (m.value.toInt() % 2==1) {
                        for(b in arrtatal) {
                            if (b <= condition) {
                                number++
                            }
                        }
                        if (number <= condition) {
                            decide = 0
                            //printf("TRUE\r\n");
                        }
                        else {
                            decide++
                            //printf("FALSE\r\n");
                        }
                    }
                    for (a in 1..condition) {
                        for (c in arrtatal) {
                            if( c <= a){
                                number++
                            }
                        }
                        if (number < a) {
                            decide = 0
                            //printf("TRUE\r\n");
                        }
                        else {
                            decide++
                            //printf("FALSE\r\n");
                        }
                    }
                    //計算路徑
                    if(decide==0) {
                        result.value = "符合"
                        var turn = 1
                        var coordinate= 0
                        way[0] = 0
                        time[0] = 1
                        var num = 0
                        test@for (z in 0..m.value.toInt()+1) {
                            if (turn == m.value.toInt()-1 ) {
                                /*if (arrline[z*m.value.toInt()+coordinate] == 1 && time[z] != 1 && arrline[z] == 1) {
                                    way[turn] = z
                                    time[z] = 1
                                    turn += 1
                                    break@test
                                }
                                else if (z == m.value.toInt()) {
                                    turn--
                                    time[way[turn]] = 0
                                    coordinate = way[turn-1]
                                    num = way[turn]
                                }*/
                            }
                            else {
                                if (arrline[z*m.value.toInt()+coordinate] == 1 && time[z] != 1) {
                                    way[turn] = z
                                    time[z] = 1
                                    coordinate = z
                                    turn++
                                    num = 0
                                }
                                else if (z == m.value.toInt()) {
                                    turn--
                                    time[way[turn]] = 0
                                    coordinate = way[turn-1]
                                    num = way[turn]
                                }
                            }
                            if (way[0] != 0) {
                                break@test
                            }
                            num++
                        }
                    }
                    for (i in way){
                        item.plus("$i >")
                    }
                    openDialog.value = true
                }
            ) { Text(text = "計算") }
        }
        if (openDialog.value){
            AlertDialog(
                onDismissRequest = { openDialog.value = false },
                title = { Text(text = "${result.value}漢彌爾頓迴路") },
                //text = { Text(text = "路徑:$item 1") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                        }) {
                        Text(text = "確認")
                    }
                }
            )
        }
    }
}

@Composable
fun Greeting() {
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyProjectTheme1 {
        Greeting()
    }
}

@Composable
fun MyScaffold() {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        content = { Mycom()},
        topBar = {MyTopAppBar(scaffoldState = scaffoldState)},
    )
}

@Composable
fun MyTopAppBar(scaffoldState: ScaffoldState) {
    scaffoldState.drawerState

    TopAppBar(
        title = {
            Text(text = "漢彌爾頓迴路", color = Color.White, textAlign = TextAlign.Center)
        },
    )
}
