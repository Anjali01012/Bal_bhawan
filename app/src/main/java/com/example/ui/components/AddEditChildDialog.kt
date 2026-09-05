package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.model.Child
import com.example.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddEditChildDialog(
    child: Child?,
    onDismiss: () -> Unit,
    onSave: (
        id: String?,
        name: String,
        age: Int,
        gender: String,
        imageUrl: String?,
        tags: List<String>,
        needsCheckup: Boolean,
        schoolGrade: String,
        caregiver: String,
        bloodGroup: String,
        hobbies: String,
        healthNotes: String
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var name by remember { mutableStateOf(child?.name ?: "") }
    var ageText by remember { mutableStateOf(child?.age?.toString() ?: "") }
    var gender by remember { mutableStateOf(child?.gender ?: "Boy") }
    var imageUrl by remember { mutableStateOf(child?.imageUrl ?: "") }
    var schoolGrade by remember { mutableStateOf(child?.schoolGrade ?: "") }
    var caregiver by remember { mutableStateOf(child?.caregiver ?: "Sunita Devi") }
    var bloodGroup by remember { mutableStateOf(child?.bloodGroup ?: "B+") }
    var hobbies by remember { mutableStateOf(child?.hobbies ?: "") }
    var healthNotes by remember { mutableStateOf(child?.healthNotes ?: "") }
    var needsCheckup by remember { mutableStateOf(child?.needsCheckup ?: false) }

    val availableTags = listOf("Education", "Sports", "Vocational", "Joined 2022", "Joined 2023", "Arts & Music")
    var selectedTags by remember {
        mutableStateOf(
            child?.tags?.filter { it != "Needs Checkup" }?.toSet() ?: setOf("Education")
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (child == null) "Register New Child" else "Edit Child Information",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                IconButton(onClick = onDismiss, modifier = Modifier.testTag("close_add_child_dialog")) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name *") },
                placeholder = { Text("e.g. Aarav Sharma") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("child_name_input"),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = ageText,
                    onValueChange = { if (it.all { ch -> ch.isDigit() }) ageText = it },
                    label = { Text("Age (Years) *") },
                    placeholder = { Text("8") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("child_age_input"),
                    singleLine = true
                )

                OutlinedTextField(
                    value = gender,
                    onValueChange = { gender = it },
                    label = { Text("Gender") },
                    placeholder = { Text("Boy / Girl") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("Photo URL (Optional)") },
                placeholder = { Text("https://...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = schoolGrade,
                onValueChange = { schoolGrade = it },
                label = { Text("School / Class") },
                placeholder = { Text("3rd Grade - Govt School") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = caregiver,
                    onValueChange = { caregiver = it },
                    label = { Text("Caregiver") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                OutlinedTextField(
                    value = bloodGroup,
                    onValueChange = { bloodGroup = it },
                    label = { Text("Blood Group") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = hobbies,
                onValueChange = { hobbies = it },
                label = { Text("Hobbies & Talents") },
                placeholder = { Text("Cricket, Drawing, Singing") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = healthNotes,
                onValueChange = { healthNotes = it },
                label = { Text("Health Notes") },
                placeholder = { Text("Vaccinations, allergies, or conditions") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Needs Checkup Checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = needsCheckup,
                    onCheckedChange = { needsCheckup = it },
                    modifier = Modifier.testTag("needs_checkup_checkbox")
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Flag for Medical Checkup / Attention",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Program Tags",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                availableTags.forEach { tag ->
                    val isSelected = tag in selectedTags
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            selectedTags = if (isSelected) selectedTags - tag else selectedTags + tag
                        },
                        label = { Text(tag) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        val finalTags = (selectedTags.toList() + if (needsCheckup) listOf("Needs Checkup") else emptyList()).distinct()
                        onSave(
                            child?.id,
                            name.ifBlank { "Child" },
                            ageText.toIntOrNull() ?: 7,
                            gender.ifBlank { "Boy" },
                            imageUrl.ifBlank { null },
                            finalTags,
                            needsCheckup,
                            schoolGrade.ifBlank { "Shelter Daycare" },
                            caregiver.ifBlank { "Sunita Devi" },
                            bloodGroup.ifBlank { "B+" },
                            hobbies.ifBlank { "General play" },
                            healthNotes.ifBlank { "Healthy" }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("save_child_button")
                ) {
                    Text("Save Record")
                }
            }
        }
    }
}
