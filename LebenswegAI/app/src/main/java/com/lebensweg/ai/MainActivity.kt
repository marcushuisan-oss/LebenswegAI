package com.lebensweg.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Void = Color(0xFF070809)
private val Panel = Color(0xFF121416)
private val Neon = Color(0xFFFF3038)
private val Muted = Color(0xFF92969C)


data class ChatMessage(val text: String, val fromUser: Boolean)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LebenswegApp() }
    }
}


@Composable
fun LebenswegApp() {
    var draft by remember { mutableStateOf("") }
    var messages by remember {
        mutableStateOf(listOf(ChatMessage("Sistema online. Sou o Lebensweg. Envie uma mensagem para iniciar.", false)))
    }
    Column(Modifier.fillMaxSize().background(Void).padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Box(Modifier.size(42.dp).background(Neon, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Icon(Icons.Filled.Bolt, contentDescription = "Logo", tint = Void)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text("LEBENSWEG", color = Color.White, fontWeight = FontWeight.Black, fontSize = 19.sp, letterSpacing = 2.sp)
                Text("AI  /  ASSISTANT SYSTEM", color = Neon, fontSize = 10.sp, letterSpacing = 1.5.sp)
            }
            Box(Modifier.border(1.dp, Neon.copy(alpha = .5f), RoundedCornerShape(20.dp)).padding(horizontal = 10.dp, vertical = 6.dp)) {
                Text("● ONLINE", color = Neon, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(Modifier.height(18.dp))
        Column(Modifier.fillMaxWidth().border(1.dp, Color(0xFF292B2E), RoundedCornerShape(16.dp)).background(Panel, RoundedCornerShape(16.dp)).padding(14.dp)) {
            Text("ASSISTENTE", color = Muted, fontSize = 10.sp, letterSpacing = 2.sp)
            Spacer(Modifier.height(4.dp))
            Text("Como posso ajudar?", color = Color.White, fontSize = 21.sp, fontWeight = FontWeight.Bold)
            Text("Converse, explore ideias e peça ajuda.", color = Muted, fontSize = 12.sp)
        }
        Spacer(Modifier.height(14.dp))
        LazyColumn(Modifier.weight(1f).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(messages) { message ->
                MessageBubble(message)
            }
        }
        Spacer(Modifier.height(10.dp))
        Row(Modifier.fillMaxWidth().border(1.dp, Color(0xFF383A3D), RoundedCornerShape(16.dp)).background(Panel, RoundedCornerShape(16.dp)).padding(horizontal = 12.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                value = draft,
                onValueChange = { draft = it },
                modifier = Modifier.weight(1f),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 14.sp),
                cursorBrush = androidx.compose.ui.graphics.SolidColor(Neon),
                decorationBox = { inner ->
                    if (draft.isEmpty()) Text("Digite sua mensagem...", color = Muted, fontSize = 14.sp)
                    inner()
                }
            )
            IconButton(onClick = { /* Voice UI placeholder: speech recognition not wired yet. */ }) {
                Icon(Icons.Filled.Mic, contentDescription = "Voz", tint = Muted)
            }
            IconButton(onClick = {
                val text = draft.trim()
                if (text.isNotEmpty()) {
                    messages = messages + ChatMessage(text, true) + ChatMessage("Recebi sua mensagem. O chat visual está pronto; para respostas inteligentes reais, conecte um serviço de IA.", false)
                    draft = ""
                }
            }) {
                Icon(Icons.Filled.Send, contentDescription = "Enviar", tint = Neon)
            }
        }
        Spacer(Modifier.height(8.dp))
        Text("LEBENSWEG AI  •  PROTÓTIPO", color = Color(0xFF55585C), fontSize = 9.sp, letterSpacing = 1.5.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}


@Composable
fun MessageBubble(message: ChatMessage) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = if (message.fromUser) Arrangement.End else Arrangement.Start) {
        Column(
            Modifier.widthIn(max = 300.dp)
                .background(if (message.fromUser) Color(0xFF291316) else Panel, RoundedCornerShape(14.dp))
                .border(1.dp, if (message.fromUser) Neon.copy(alpha = .35f) else Color(0xFF282A2D), RoundedCornerShape(14.dp))
                .padding(12.dp)
        ) {
            Text(if (message.fromUser) "VOCÊ" else "LEBENSWEG", color = if (message.fromUser) Neon else Muted, fontSize = 9.sp, letterSpacing = 1.4.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(5.dp))
            Text(message.text, color = Color(0xFFE8E8E8), fontSize = 14.sp, lineHeight = 20.sp)
        }
    }
}
