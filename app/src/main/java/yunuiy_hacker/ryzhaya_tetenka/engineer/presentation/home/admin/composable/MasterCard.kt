package yunuiy_hacker.ryzhaya_tetenka.engineer.presentation.home.admin.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.model.User

@Composable
fun MasterCard(modifier: Modifier = Modifier, user: User) {
    Card(
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "${user.surname} ${user.name} ${user.lastname}",
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}