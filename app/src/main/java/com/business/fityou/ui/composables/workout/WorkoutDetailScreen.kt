package com.business.fitrack.ui.composables.workout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.business.fitrack.R
import com.business.fitrack.data.models.equipments
import com.business.fitrack.ui.composables.FloatingAddButton
import com.business.fitrack.ui.composables.RegularButton
import com.business.fitrack.ui.composables.home.WorkoutInfo
import com.business.fitrack.ui.composables.home.Heading
import com.business.fitrack.ui.composables.home.SubHeading
import com.business.fitrack.ui.composables.home.Title
import com.business.fitrack.ui.theme.darkBlue
import com.business.fitrack.ui.theme.holoGreen
import com.business.fitrack.ui.theme.lightBlue
import com.business.fitrack.util.DifficultyLevels.Companion.Intermediate
import com.business.fitrack.viewmodel.WorkoutViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WorkoutDetailScreen(
    navController: NavHostController,
    workoutViewModel: WorkoutViewModel
) = with(workoutViewModel) {

    getWorkouts()

    val state = workoutState
    val workoutPlan = workoutPlanState.workoutPlan
    var openDialog by remember { mutableStateOf(false) }
    var exBoxExpanded by remember { mutableStateOf(false) }
    var selectedExercise by remember { mutableStateOf(userExercisesList[0]) }
    val equipments = equipments().toSet().toList()
    var eqBoxExpanded by remember { mutableStateOf(false) }
    var selectedEquipment by remember { mutableStateOf(equipments[0]) }
    var setAmount by remember { mutableStateOf(1) }

    if (openDialog) {
        Dialog(
            onDismissRequest = { openDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false),

        )
        {
            Surface(
                modifier = Modifier.width(300.dp),
                color = lightBlue,
                shape = RoundedCornerShape(40.dp)
            ) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(30.dp)

                ) {
                    SubHeading(text = stringResource(R.string.add_exercise_heading), color = holoGreen)

                    ExposedDropdownMenuBox(
                        expanded = exBoxExpanded,
                        onExpandedChange = {
                            exBoxExpanded = !exBoxExpanded
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = selectedExercise.name.toString(),
                            onValueChange = { value ->
                                selectedExercise = userExercisesList.first { it.name == value }
                            },
                            label = { Text(stringResource(id = R.string.exercise), color = holoGreen) },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = exBoxExpanded,

                                    )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                trailingIconColor = holoGreen,
                                focusedTrailingIconColor = holoGreen,
                                disabledTrailingIconColor = holoGreen
                            ),
                            textStyle = TextStyle(fontSize = 20.sp)

                        )
                        ExposedDropdownMenu(
                            expanded = exBoxExpanded,
                            onDismissRequest = {
                                exBoxExpanded = false
                            }
                        ) {
                            userExercisesList.forEach { selectionOption ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedExercise = selectionOption
                                        exBoxExpanded = false
                                    }
                                ) {
                                    Text(text = selectionOption.name!!)
                                }
                            }
                        }
                    }

                    ExposedDropdownMenuBox(
                        expanded = eqBoxExpanded,
                        onExpandedChange = {
                            eqBoxExpanded = !eqBoxExpanded
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = stringResource(selectedEquipment.name!!),
                            onValueChange = { },
                            label = { Text(stringResource(id = R.string.equipment), color = holoGreen) },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = eqBoxExpanded,

                                    )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                trailingIconColor = holoGreen,
                                focusedTrailingIconColor = holoGreen,
                                disabledTrailingIconColor = holoGreen
                            ),
                            textStyle = TextStyle(fontSize = 20.sp)
                        )
                        ExposedDropdownMenu(
                            expanded = eqBoxExpanded,
                            onDismissRequest = {
                                eqBoxExpanded = false
                            }
                        ) {
                            equipments.forEach { selectionOption ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedEquipment = selectionOption
                                        eqBoxExpanded = false
                                    }
                                ) {
                                    Text(text = stringResource(selectionOption.name!!))
                                }
                            }
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        SubHeading(text = stringResource(id = R.string.sets), modifier = Modifier)

                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(
                                20.dp,
                                Alignment.CenterHorizontally
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {


                            IconButton(onClick = {
                                if (setAmount > 0) {
                                    setAmount--
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_remove),
                                    contentDescription = null,
                                    tint = holoGreen,
                                    modifier = Modifier.size(25.dp)
                                )
                            }

                            Title(text = setAmount.toString())

                            IconButton(
                                onClick = { setAmount++ },
                            )
                            {
                                Icon(
                                    imageVector = Icons.Rounded.AddCircle,
                                    contentDescription = null,
                                    tint = holoGreen,
                                    modifier = Modifier.size(25.dp)
                                )
                            }
                        }
                    }

                    RegularButton(
                        text = stringResource(id = R.string.add),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            openDialog = false
                            addExerciseToWorkout(
                                exerciseName = selectedExercise.name.toString(),
                                equipments = selectedEquipment, sets = setAmount
                            )
                            getWorkouts()
                        }
                    )
                }

            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = darkBlue,
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Heading(
                text = workoutPlan?.name?.replaceFirstChar { it.uppercase() }.toString(),
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            WorkoutInfo(
                duration = workoutPlan?.duration.toString(),
                difficulty = Intermediate,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
            ExerciseItemsDisplay(
                modifier = Modifier.height(500.dp),
                workoutViewModel = workoutViewModel
            )

            FloatingAddButton(Modifier.align(Alignment.CenterHorizontally), onClick = {
                openDialog = true
            })

        }


    }

}




