package com.a32b.plant.data.repository

import com.a32b.plant.data.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class PostRepository(private val db: FirebaseFirestore) {

    // 1️⃣ 실시간 게시글 리스트 가져오기 (ID 주입 로직 추가!)
    fun getPosts(): Flow<List<Post>> = callbackFlow {
        val subscription = db.collection("posts")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener // 에러 발생 시 중단

                // 🚀 핵심: snapshot에서 데이터를 꺼낼 때 문서 ID를 Post 객체에 수동으로 넣어줘야 합니다.
                val posts = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Post::class.java)?.copy(id = doc.id) // ✅ 문서의 진짜 ID를 복사해서 넣어줌!
                } ?: emptyList()

                trySend(posts)
            }

        awaitClose { subscription.remove() }
    }

    // 2️⃣ 게시글 상세 데이터 하나만 가져오기 (상세 페이지용 필수 함수!)
    fun getPostById(postId: String): Flow<Post?> = callbackFlow {
        val subscription = db.collection("posts").document(postId)
            .addSnapshotListener { snapshot, _ ->
                val post = snapshot?.toObject(Post::class.java)?.copy(id = snapshot.id)
                trySend(post)
            }
        awaitClose { subscription.remove() }
    }

    // 3️⃣ 게시글 업로드
    suspend fun uploadPost(post: Post) {
        db.collection("posts").add(post).await()
    }
}