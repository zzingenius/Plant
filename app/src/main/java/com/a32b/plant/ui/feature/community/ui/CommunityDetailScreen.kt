package com.a32b.plant.ui.feature.community.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle // ✅ 이거 꼭 확인!
import com.a32b.plant.R
import com.a32b.plant.ui.feature.community.viewmodel.CommunityDetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityDetailScreen(
    onBack: () -> Unit,
    viewModel: CommunityDetailViewModel
) {

    val postState by viewModel.post.collectAsStateWithLifecycle()

    val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { innerPadding ->


        val currentPost = postState
        if (currentPost == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFC5E1A5))
            }
        } else {

            Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 20.dp)) {

                    // 제목
                    item {
                        Text(
                            text = currentPost.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 20.dp).fillMaxWidth(),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }

                    item {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(40.dp).clip(CircleShape).background(Color.LightGray))
                            Spacer(Modifier.width(12.dp))
                            Text(currentPost.nickName, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.weight(1f))
                            Text(
                                text = dateFormat.format(currentPost.createdAt),
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    }


                    item {
                        Spacer(Modifier.height(30.dp))
                        Text(
                            text = currentPost.content,
                            fontSize = 16.sp,
                            lineHeight = 24.sp
                        )
                        Spacer(Modifier.height(50.dp))
                    }


                    item {
                        Divider(color = Color(0xFFF0F0F0))
                        Row(Modifier.padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(painterResource(R.drawable.ic_community_comment), null, Modifier.size(20.dp), tint = Color(0xFF6750A4))
                            Text(" ${currentPost.commentCount} ", fontSize = 14.sp)

                            Icon(Icons.Default.FavoriteBorder, null, Modifier.size(20.dp), tint = Color(0xFF6750A4))
                            Text(" ${currentPost.likeCount}", fontSize = 14.sp)

                            Spacer(Modifier.weight(1f))

                            Icon(painterResource(R.drawable.ic_edit), null, Modifier.size(20.dp), tint = Color(0xFF6750A4))
                            Spacer(Modifier.width(12.dp))

                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "삭제",
                                modifier = Modifier.size(20.dp).clickable {
                                    viewModel.deletePost { onBack() }
                                },
                                tint = Color(0xFF6750A4)
                            )
                        }
                    }

                    item {
                        CommentInputSection()
                    }
                }
            }
        }
    }
}

@Composable
fun CommentInputSection() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(24.dp).clip(CircleShape).background(Color.LightGray))
                Text("  닉네임", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(8.dp))
            Box(Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(8.dp)).padding(12.dp)) {
                Text("댓글작성", color = Color.LightGray, fontSize = 14.sp)
            }
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                Surface(color = Color(0xFFC5E1A5), shape = RoundedCornerShape(4.dp), modifier = Modifier.padding(top = 8.dp)) {
                    Text("등록", Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontSize = 10.sp, color = Color(0xFF33691E))
                }
            }
        }
    }
}