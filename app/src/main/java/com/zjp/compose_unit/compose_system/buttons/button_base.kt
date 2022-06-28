package com.zjp.compose_unit.compose_system.buttons

import android.content.Context
import android.widget.ImageButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * shape:RoundedCornerShape,CutCornerShape,CircleShape
 */
@Composable
fun ButtonShapeAndBorder() {
    Button(
        onClick = { /*TODO*/ },
        shape = CircleShape,
        border = BorderStroke(2.dp, Color.Green)
    ) {
        Text(text = "Button")
    }
    Divider(modifier = Modifier.height(10.dp))
    OutlinedButton(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, Color.Green)
    ) {
        Text(text = "Button")
    }
    Divider(modifier = Modifier.height(10.dp))
    Button(
        onClick = { /*TODO*/ },
        shape = CutCornerShape(10.dp),
        border = BorderStroke(
            2.dp,
            Brush.radialGradient(listOf(Color.White, Color.Red, Color.Green, Color.Black))
        ),
    ) {
        Text(text = "Button")
    }
    Divider(modifier = Modifier.height(10.dp))
}

@Composable
fun ButtonWithCustomColor() {
    Button(
        onClick = {
            //your onclick code
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
    )

    {
        Text(text = "Button with gray background", color = Color.White)
    }
}

@Composable
fun ButtonWithMultipleText() {
    Button(
        onClick = {
            //your onclick code
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
    )

    {
        Text(text = "Click ", color = Color.Magenta)
        Text(text = "Here", color = Color.Green)
    }
}

@Composable
fun ButtonWithIcon() {
    Button(onClick = {}) {
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_add),
            contentDescription = "Button With Icon"
        )
        Text(text = "Add to cart", Modifier.padding(start = 10.dp))
    }
}

@Composable
fun TextButtonBase() {
    TextButton(onClick = { /*TODO*/ }) {
        Text(text = "Add to cart", Modifier.padding(start = 10.dp))
    }
}

@Composable
fun OutlinedButtonBase() {
    OutlinedButton(onClick = { /*TODO*/ }) {
        Text(text = "Add to cart", Modifier.padding(start = 10.dp))
    }
}

@Composable
fun RadioButtonBase() {
    var context = LocalContext.current
    var checked by remember {
        mutableStateOf(false)
    }
    var customChecked by remember {
        mutableStateOf(false)
    }
    Row(modifier = Modifier.height(20.dp)) {
        Row {
            RadioButton(
                selected = checked,
                onClick = {
                    checked = !checked
                    onClick(context = context)
                })
            Text(text = "默认样式")
        }
        Row(modifier = Modifier.height(20.dp)) {
            RadioButton(
                selected = customChecked,
                onClick = {
                    customChecked = !customChecked
                    onClick(context = context)
                }, colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Red,
                    unselectedColor = Color.Gray
                )
            )
            Text(text = "自定义样式")
        }

    }
}


@Composable
fun RadioButtonGroup() {
    var context = LocalContext.current
    val tags = arrayListOf("Java", "Kotlin", "XML", "Compose", "JetPack")
    var selectedTag by remember {
        mutableStateOf("Null")
    }
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        tags.forEach {
            Row(modifier = Modifier.height(20.dp)) {
                RadioButton(
                    selected = it == selectedTag,
                    onClick = {
                        selectedTag = it
                    })
                Text(text = it)
                Spacer(modifier = Modifier.width(0.dp))
            }
        }
    }
}

@Composable
fun CheckBoxBase() {
    var checked by remember { mutableStateOf(false) }
    var customChecked by remember { mutableStateOf(false) }

    Row {
        Row(modifier = Modifier.height(20.dp)) {
            Row {
                Checkbox(
                    checked = customChecked,
                    onCheckedChange = {
                        customChecked = it
                    }
                )
                Text(text = "默认样式")
            }
            Row(modifier = Modifier.height(20.dp)) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                    }, colors = CheckboxDefaults.colors(
                        checkedColor = Color.Red,
                        uncheckedColor = Color.Gray
                    )
                )
                Text(text = "自定义样式")
            }
        }
    }

}

@Composable
fun SwitchBase() {
    var checked by remember { mutableStateOf(false) }
    var customChecked by remember { mutableStateOf(false) }

    Row {

        Row {
            Row(modifier = Modifier.height(20.dp)) {
                Row {
                    Switch(
                        checked = checked, onCheckedChange = {
                            checked = it
                        }
                    )
                    Text(text = "默认样式")
                }
                Row(modifier = Modifier.height(20.dp)) {
                    Switch(
                        checked = customChecked, onCheckedChange = {
                            customChecked = it
                        }, colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Red,
                            uncheckedThumbColor = Color.Green,
                            uncheckedTrackColor = Color.Green
                        )
                    )
                    Text(text = "自定义样式")
                }
            }
        }


    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChipBase() {
    Chip(
        onClick = { /* Do something! */ },
        border = ChipDefaults.outlinedBorder,
        colors = ChipDefaults.outlinedChipColors(),
        leadingIcon = {
            Icon(
                Icons.Filled.Settings,
                contentDescription = "Localized description"
            )
        }
    ) {
        Text("Change settings")
    }
}

fun onClick(context: Context) {
    Toast.makeText(context, "点击", Toast.LENGTH_SHORT).show()
}


@Composable
fun ToggleButtonBase() {

    var toggled by remember {
        mutableStateOf(false)
    }
    IconToggleButton(checked = toggled, onCheckedChange = { toggled = it }) {
        val tint by animateColorAsState(if (toggled) Color(0xFFEC407A) else Color(0xFFB0BEC5))
        Icon(Icons.Filled.Favorite, contentDescription = "Localized description", tint = tint)
    }
}


@Composable
fun AlertDialogBase(openDialog: Boolean, dismiss: () -> Unit) {

    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.
                dismiss()
            },
            title = {
                Text(text = "Title")
            },
            text = {
                Text(
                    "This area typically contains the supportive text " +
                            "which presents the details regarding the Dialog's purpose."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        dismiss()
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dismiss()
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BackdropScaffoldBase() {
    val scope = rememberCoroutineScope()
    val selection = remember { mutableStateOf(1) }
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    LaunchedEffect(scaffoldState) {
        scaffoldState.reveal()
    }
    BackdropScaffold(
        scaffoldState = scaffoldState,

        appBar = {
            TopAppBar(
                title = { Text("Backdrop scaffold") },
                navigationIcon = {
                    if (scaffoldState.isConcealed) {
                        IconButton(onClick = { scope.launch { scaffoldState.reveal() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Localized description")
                        }
                    } else {
                        IconButton(onClick = { scope.launch { scaffoldState.conceal() } }) {
                            Icon(Icons.Default.Close, contentDescription = "Localized description")
                        }
                    }
                },
                actions = {
                    var clickCount by remember { mutableStateOf(0) }
                    IconButton(
                        onClick = {
                            // show snackbar as a suspend function
                            scope.launch {
                                scaffoldState.snackbarHostState
                                    .showSnackbar("Snackbar #${++clickCount}")
                            }
                        }
                    ) {
                        Icon(Icons.Default.Favorite, contentDescription = "Localized description")
                    }
                },
                elevation = 0.dp,
                backgroundColor = Color.Transparent
            )
        },
        backLayerContent = {
            LazyColumn(modifier = Modifier.height(200.dp)) {
                items(if (selection.value >= 3) 3 else 5) {
                    ListItem(
                        Modifier.clickable {
                            selection.value = it
                            scope.launch { scaffoldState.conceal() }
                        },
                        text = { Text("Select $it") }
                    )
                }
            }
        },
        frontLayerContent = {
            Text("Selection: ${selection.value}")
            LazyColumn(modifier = Modifier.height(200.dp)) {
                items(50) {
                    ListItem(
                        text = { Text("Item $it") },
                        icon = {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "Localized description"
                            )
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun BadgeBase() {
    BadgedBox(badge = { Badge { Text("8") } }) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Favorite"
        )
    }
}


@Composable
fun BottomAppBarBase() {
    BottomAppBar(cutoutShape = CircleShape) {
        // Leading icons should typically have a high content alpha
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Menu, contentDescription = "Localized description")
            }
        }
        // The actions should be at the end of the BottomAppBar. They use the default medium
        // content alpha provided by BottomAppBar
        Spacer(Modifier.weight(1f, true))
        IconButton(onClick = { /* doSomething() */ }) {
            Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
        }
        IconButton(onClick = { /* doSomething() */ }) {
            Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ButtonDrawerBase() {
    val (gesturesEnabled, toggleGesturesEnabled) = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = gesturesEnabled,
                    onValueChange = toggleGesturesEnabled
                )
        ) {
            Checkbox(gesturesEnabled, null)
            Text(text = if (gesturesEnabled) "Gestures Enabled" else "Gestures Disabled")
        }
        val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
        BottomDrawer(
            gesturesEnabled = gesturesEnabled,
            drawerState = drawerState,
            drawerContent = {
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp),
                    onClick = { scope.launch { drawerState.close() } },
                    content = { Text("Close Drawer") }
                )
                LazyColumn {
                    items(25) {
                        ListItem(
                            text = { Text("Item $it") },
                            icon = {
                                Icon(
                                    Icons.Default.Favorite,
                                    contentDescription = "Localized description"
                                )
                            }
                        )
                    }
                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val openText = if (gesturesEnabled) "▲▲▲ Pull up ▲▲▲" else "Click the button!"
                    Text(text = if (drawerState.isClosed) openText else "▼▼▼ Drag down ▼▼▼")
                    Spacer(Modifier.height(20.dp))
                    Button(onClick = { scope.launch { drawerState.open() } }) {
                        Text("Click to open")
                    }
                }
            }
        )
    }
}


@Preview
@Composable
fun ButtonPre() {
    var openDialog by remember { mutableStateOf(false) }
    Column(Modifier.verticalScroll(rememberScrollState())) {
        ButtonShapeAndBorder()
        ButtonWithCustomColor()
        ButtonWithMultipleText()
        ButtonWithIcon()
        TextButtonBase()
        OutlinedButtonBase()
        RadioButtonBase()
        RadioButtonGroup()
        CheckBoxBase()
        SwitchBase()
        ChipBase()
        ToggleButtonBase()
        OutlinedButton(onClick = { openDialog = true }) {
            Text(text = "showDailog")
        }
        AlertDialogBase(openDialog = openDialog) {
            openDialog = false
        }

        BackdropScaffoldBase()
        BadgeBase()
        BottomAppBarBase()

    }
}