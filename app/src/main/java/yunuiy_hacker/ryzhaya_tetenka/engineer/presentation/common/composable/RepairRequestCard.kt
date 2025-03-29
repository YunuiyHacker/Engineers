package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.common.composable

import android.R.attr.fontWeight
import android.R.attr.maxLines
import android.R.attr.text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yunuiy_hacker.ryzhaya_tetenka.engineer.R
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.model.RepairRequest

@Composable
fun RepairRequestCard(modifier: Modifier = Modifier, repairRequest: RepairRequest) {
    Card(
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = if (repairRequest.nomenclature.isNotEmpty()) repairRequest.nomenclature else stringResource(
                        R.string.title_not_entered
                    ),
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (repairRequest.malfunction.trim().isNotEmpty()) {
                    if (repairRequest.nomenclature.trim().isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    Text(
                        text = repairRequest.malfunction,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (repairRequest.malfunction_description.trim()
                        .isNotEmpty() || !repairRequest.malfunction.trim().equals(
                        repairRequest.malfunction_description.trim()
                    )
                ) {
                    if (repairRequest.nomenclature.trim()
                            .isNotEmpty() || repairRequest.malfunction.trim().isNotEmpty()
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    Text(
                        text = repairRequest.malfunction_description,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}