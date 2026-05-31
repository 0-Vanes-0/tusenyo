/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jetsnack.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ProfileBackground = Color(0xFFF7F7F9)

private val ProfileBlue = Color(0xFF2563EB)
private val ProfilePink = Color(0xFFE91E63)
private val ProfileGreen = Color(0xFF2E7D32)
private val ProfilePurple = Color(0xFF7E3FF2)

private val LightBlue = Color(0xFFEAF1FF)
private val LightGreen = Color(0xFFEAF7EC)
private val LightPurple = Color(0xFFF1EAFE)

@Immutable
private data class ProfileStat(
    val value: String,
    val label: String,
    val color: Color
)

@Immutable
private data class RecentActivityItem(
    val title: String,
    val subtitle: String,
    val time: String,
    val iconText: String,
    val iconBackground: Color,
    val iconColor: Color
)

@Composable
fun Profile(
    modifier: Modifier = Modifier,
    onMyDesignsClick: () -> Unit = {}
) {
    val stats = listOf(
        ProfileStat(value = "24", label = "Designs", color = ProfileBlue),
        ProfileStat(value = "156", label = "Likes", color = ProfilePink),
        ProfileStat(value = "8", label = "Sold", color = ProfileGreen)
    )

    val recentActivity = listOf(
        RecentActivityItem(
            title = "Summer Vibes T-Shirt",
            subtitle = "12 new likes",
            time = "2h ago",
            iconText = "♡",
            iconBackground = LightBlue,
            iconColor = ProfileBlue
        ),
        RecentActivityItem(
            title = "Urban Hoodie Design",
            subtitle = "Shared on social",
            time = "5h ago",
            iconText = "↗",
            iconBackground = LightGreen,
            iconColor = ProfileGreen
        ),
        RecentActivityItem(
            title = "Minimal Tee Collection",
            subtitle = "Design updated",
            time = "1d ago",
            iconText = "✎",
            iconBackground = LightPurple,
            iconColor = ProfilePurple
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ProfileBackground),
        contentPadding = PaddingValues(
            start = 20.dp,
            top = 18.dp,
            end = 20.dp,
            bottom = 96.dp
        )
    ) {
        item {
            ProfileTopBar()
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            ProfileCard(
                name = "Jordan Designer",
                subtitle = "Fashion Enthusiast",
                onEditProfileClick = {
                    // TODO: open edit profile screen
                },
                onMyDesignsClick = onMyDesignsClick,
            )
        }

        item {
            Spacer(modifier = Modifier.height(18.dp))
            StatsRow(stats = stats)
        }

        item {
            Spacer(modifier = Modifier.height(26.dp))
            SectionTitle(text = "Recent Activity")
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            RecentActivityCard(items = recentActivity)
        }

        item {
            Spacer(modifier = Modifier.height(26.dp))
            SectionTitle(text = "Settings")
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            SettingsMenuCard()
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ProfileTopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "My Profile",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(12.dp))

        IconButton(
            modifier = Modifier.size(40.dp),
            onClick = {
                // TODO: open settings screen
            }
        ) {
            Text(
                text = "⚙",
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun ProfileCard(
    name: String,
    subtitle: String,
    onEditProfileClick: () -> Unit,
    onMyDesignsClick: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AvatarPlaceholder()

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                onClick = onEditProfileClick
            ) {
                Text(text = "Edit Profile")
            }

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                onClick = onMyDesignsClick
            ) {
                Text(text = "My Designs")
            }
        }
    }
}

@Composable
private fun AvatarPlaceholder() {
    Box(
        modifier = Modifier
            .size(88.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "JD",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun StatsRow(stats: List<ProfileStat>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        stats.forEach { stat ->
            StatCard(
                stat = stat,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StatCard(
    stat: ProfileStat,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(92.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stat.value,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = stat.color
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = stat.label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold
        ),
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun RecentActivityCard(items: List<RecentActivityItem>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items.forEachIndexed { index, item ->
                RecentActivityRow(item = item)

                if (index != items.lastIndex) {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
private fun RecentActivityRow(item: RecentActivityItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(item.iconBackground),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.iconText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = item.iconColor
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = item.time,
            modifier = Modifier.widthIn(min = 44.dp),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1
        )
    }
}

@Composable
private fun SettingsMenuCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        ) {
            SettingsMenuRow(
                title = "Account settings",
                subtitle = "Manage your profile information"
            )

            SettingsDivider()

            SettingsMenuRow(
                title = "Notifications",
                subtitle = "Likes, sales, and design updates"
            )

            SettingsDivider()

            SettingsMenuRow(
                title = "Help & support",
                subtitle = "Get help with your account"
            )
        }
    }
}

@Composable
private fun SettingsMenuRow(
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = "›",
            fontSize = 26.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SettingsDivider() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    )
}