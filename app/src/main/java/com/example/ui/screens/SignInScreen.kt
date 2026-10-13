package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MedixinBlue
import com.example.ui.theme.MedixinBlueDark
import com.example.ui.theme.MedixinBorder
import com.example.ui.theme.MedixinDarkText
import com.example.ui.theme.MedixinMutedText
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    onSignInSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var email by remember { mutableStateOf("patient@medixin.health") }
    var password by remember { mutableStateOf("••••••••••••") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("signin_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                // Header Logo and Wordmark
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MedixinBlue)
                            .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = MedixinBlue.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Accessibility,
                            contentDescription = "Medixin Logo",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "Medixin",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedixinDarkText,
                        letterSpacing = (-0.3).sp
                    )
                }

                // Welcome back header
                Text(
                    text = "Welcome Back 👋",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedixinDarkText,
                    letterSpacing = (-0.4).sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Please sign in to access your appointments and health records.",
                    fontSize = 13.5.sp,
                    color = MedixinMutedText,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Email Address
                Text(
                    text = "Email Address *",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MedixinDarkText
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("email_input"),
                    placeholder = { Text("name@example.com", fontSize = 14.sp, color = Color(0xFF9CA3AF)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.MailOutline,
                            contentDescription = "Email",
                            tint = Color(0xFF9CA3AF),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    isError = emailError,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MedixinBlue,
                        unfocusedBorderColor = MedixinBorder,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password
                Text(
                    text = "Password *",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MedixinDarkText
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("password_input"),
                    placeholder = { Text("Enter your password", fontSize = 14.sp, color = Color(0xFF9CA3AF)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Password",
                            tint = Color(0xFF9CA3AF),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle password visibility",
                                tint = Color(0xFF9CA3AF),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MedixinBlue,
                        unfocusedBorderColor = MedixinBorder,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                // Forgot Password link
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Forgot Password?",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = MedixinBlue,
                        modifier = Modifier
                            .clickable {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Password reset link sent to your registered email.")
                                }
                            }
                            .testTag("forgot_password_btn")
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // CONTINUE Button (52dp high, rounded 12dp, blue gradient)
                Button(
                    onClick = {
                        if (email.isBlank()) {
                            emailError = true
                        } else {
                            onSignInSuccess()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = MedixinBlue.copy(alpha = 0.35f))
                        .testTag("continue_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MedixinBlue,
                        contentColor = Color.White
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "CONTINUE",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Arrow",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Divider: Or continue with
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = MedixinBorder)
                    Text(
                        text = "Or continue with",
                        fontSize = 12.sp,
                        color = Color(0xFF9CA3AF),
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f), color = MedixinBorder)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Social Auth buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Google
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, MedixinBorder, RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .clickable { onSignInSuccess() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Google",
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MedixinDarkText
                        )
                    }

                    // Apple
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, MedixinBorder, RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .clickable { onSignInSuccess() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Apple",
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MedixinDarkText
                        )
                    }
                }
            }

            // Bottom prompt: Don't have an account? Create Account
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ",
                    fontSize = 13.sp,
                    color = MedixinMutedText
                )
                Text(
                    text = "Create Account",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedixinBlue,
                    modifier = Modifier
                        .clickable { onNavigateToRegister() }
                        .testTag("create_account_btn")
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
