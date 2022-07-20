package com.example.signuppage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.signuppage.ui.theme.SignUpPageTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignUpPageTheme {
                val scaffoldState   = rememberScaffoldState()
                val scope =  rememberCoroutineScope()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) { Scaffold(scaffoldState = scaffoldState)
                {
                    MyApp(modifier = Modifier.fillMaxSize(), myViewmodel = MyViewmodel(),scope,scaffoldState)
                }
                }
            }
        }
    }
}

@Composable
fun MyApp(
    modifier: Modifier,
    myViewmodel: MyViewmodel,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
){
    Column(modifier=modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
       text="SingUp Page",
       style= MaterialTheme.typography.h5
   )
      SignupScreen(
          value=myViewmodel.text,
          onValueChange ={ myViewmodel.onTextChanged(it)},
        label="Username"
    )
    SignupScreen(value=myViewmodel.pass ,
        onValueChange = {myViewmodel
        .onPasswordChanged(it)},
        label = "Password",
        visualTransformation = PasswordVisualTransformation(),
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
    Button(
        onClick = { scope.launch { scaffoldState.snackbarHostState.showSnackbar("Heyy ${myViewmodel.text}") } },
    enabled =myViewmodel.text.isNotBlank() &&myViewmodel.pass.isNotBlank()
    ) {
        Text(text = "sumbit")
    }    
}
}

@Composable
fun SignupScreen(
    label:String,
    visualTransformation: VisualTransformation=VisualTransformation.None,
    keyboardOptions: KeyboardOptions=KeyboardOptions.Default,
    value:String,
    onValueChange:(String)->Unit
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {Text(label)},
        visualTransformation=visualTransformation,
        keyboardOptions=keyboardOptions
        )


}












class MyViewmodel: ViewModel(){
    var text by mutableStateOf("")
    var  pass by mutableStateOf("")


    //event
    fun onTextChanged(newString: String){
      text  =  newString
    }
    fun onPasswordChanged(newString: String){
     pass= newString
    }
}

