package com.a32b.plant.ui.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.a32b.plant.core.navigation.Routes
import com.a32b.plant.data.model.PotInfo
import com.a32b.plant.ui.feature.home.viewmodel.HomeViewModel
import com.a32b.plant.ui.theme.primary
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage // 추가 확인
import com.a32b.plant.R // 본인 패키지명 R 확인

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = viewModel()
    val userName by viewModel.userName.collectAsState()
    val currentDate by viewModel.currentDate.collectAsState() // 로컬 날짜 획득
    val currentPot by viewModel.currentPot.collectAsState()

    Scaffold(
        topBar = {
            Text(
                text = "${userName}의 Garden",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall,
                color = primary,
                fontWeight = FontWeight.Bold
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF9F9F4)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // [홈 1 영역] 상단 메인 카드
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(currentDate,
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(20.dp))

                // 메인 카드 컴포넌트
                @Composable
                fun MainPlantCard(displayPot: PotInfo, onStartClick: () -> Unit ) {
                    Card( /* 스타일 설정 동일 */ ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = displayPot.tag)
                            Text(text = displayPot.name, fontWeight = FontWeight.Bold)

                            // [이미지 분기 처리]
                            Box(modifier = Modifier.size(150.dp)) {
                                if (displayPot.id.isEmpty()) {
                                    // 화분이 없는 경우: logo_plant 표시
                                    Image(
                                        painter = painterResource(id = R.drawable.logo_plant),
                                        contentDescription = null
                                    )
                                } else {
                                    // 마지막 공부 화분 이미지 표시 (Coil 등 사용)
                                    AsyncImage(
                                        model = displayPot.imageUrl,
                                        contentDescription = null,
                                        // 이미지 로딩 실패 시에도 logo_plant를 보여주도록 설정 가능
                                        error = painterResource(id = R.drawable.logo_plant)
                                    )
                                }
                            }

                            // [공부 시간 표시] 자정 기점 오늘 공부한 총 시간 (Long)
                            // 화분이 없으면 0으로 표시됨 (PotInfo 초기값)
                            Text(
                                text = String.format("%02d:%02d:%02d",
                                    displayPot.todayStudyingTime / 3600,
                                    (displayPot.todayStudyingTime % 3600) / 60,
                                    displayPot.todayStudyingTime % 60),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold
                            )

                            // 단순 숫자(Long)만 보여주고 싶다면:
                            // Text(text = "${displayPot.todayStudyingTime}")

                            Button(onClick = onStartClick, enabled = displayPot.id.isNotEmpty()) {
                                Text("공부 시작")
                            }
                        }
                    }
                }
            }

            // [홈 2 영역] 하단 그리드 (예시 데이터 사용, 필요시 mypage 컬렉션 연결)
            // 임시로 같은 데이터를 리스트로 뿌리는 예시입니다.
            val dummyList = List(6) { currentPot }
            val rows = dummyList.chunked(3)

            items(rows) { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowItems.forEach { item ->
                        // 작은 화분 아이템 (따로 정의 필요)
                        Box(modifier = Modifier.weight(1f).aspectRatio(1f).background(Color.White, RoundedCornerShape(12.dp)))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // 추가 버튼
            item {
                IconButton(onClick = { /* 추가 로직 */ }) {
                    Icon(Icons.Default.AddCircle, contentDescription = null, modifier = Modifier.size(48.dp))
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun MainPlantCard(tag: String, title: String, onStartClick: () -> Unit, enabled: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(0.85f),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(color = Color(0xFFE8F5E9), shape = RoundedCornerShape(8.dp)) {
                Text(text = tag, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), color = Color(0xFF4CAF50), fontSize = 12.sp)
            }
            Text(text = title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(100.dp)) // 이미지 들어갈 자리

            Button(
                onClick = onStartClick,
                enabled = enabled,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA5C16C))
            ) {
                Text("공부 시작")
            }
        }
    }
}