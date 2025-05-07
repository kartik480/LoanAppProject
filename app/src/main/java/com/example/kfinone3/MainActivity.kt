package com.example.kfinone3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.kfinone3.ui.theme.Kfinone3Theme
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Kfinone3Theme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF0F0F0))
                ) {
                    var showMainScreen by remember { mutableStateOf(false) }
                    var showLoginDialog by remember { mutableStateOf(true) }
                    var showRegisterDialog by remember { mutableStateOf(false) }

                    if (!showMainScreen) {
                        // Show Login/Register at start
                        if (showLoginDialog) {
                            LoginDialog(
                                onDismiss = { /* Prevent dismissal */ },
                                onLogin = { email, password ->
                                    // Handle login
                                    showMainScreen = true
                                },
                                onRegisterClick = {
                                    showLoginDialog = false
                                    showRegisterDialog = true
                                }
                            )
                        }

                        if (showRegisterDialog) {
                            RegisterDialog(
                                onDismiss = { 
                                    showRegisterDialog = false
                                    showLoginDialog = true
                                },
                                onRegister = { name, email, phone, password ->
                                    // Handle registration
                                    showMainScreen = true
                                }
                            )
                        }
                    } else {
                        MainScreen()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun MainScreen() {
    var showMenu by remember { mutableStateOf(false) }
    var showLoginDialog by remember { mutableStateOf(false) }
    var showRegisterDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var selectedItem by remember { mutableStateOf(0) }
    
    // Add your JPEG images to res/drawable folder first
    val carouselImages = listOf(
        R.drawable.image1,  // Replace with your JPEGs
        R.drawable.image2,
        R.drawable.image3
    )

    val pagerState = rememberPagerState()

    // Auto-scroll every 3 seconds
    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % carouselImages.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    modifier = Modifier.height(64.dp),
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "App Logo",
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(start = 8.dp)
                            )

                            IconButton(
                                onClick = { showMenu = !showMenu },
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Icon(
                                    Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,  // Back to default white
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                    )
                )
                
                // Menu Box
                if (showMenu) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF5F5F5)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            MenuItem(
                                text = stringResource(R.string.menu_become_dsa),
                                onClick = { /* Handle DSA click */ }
                            )
                            Divider(color = Color(0xFFE0E0E0))
                            
                            // Loans with sub-options
                            var showLoanOptions by remember { mutableStateOf(false) }
                            Column {
                                TextButton(
                                    onClick = { showLoanOptions = !showLoanOptions },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp, horizontal = 16.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.menu_loans),
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                if (showLoanOptions) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 32.dp)
                                    ) {
                                        TextButton(
                                            onClick = { /* Handle Personal Loan click */ },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = "Personal Loan",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                        TextButton(
                                            onClick = { /* Handle Credit Card click */ },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = "Credit Card",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                        TextButton(
                                            onClick = { /* Handle Business Loan click */ },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = "Business Loan",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                }
                            }
                            Divider(color = Color(0xFFE0E0E0))
                            
                            // Investments with sub-options
                            var showInvestmentOptions by remember { mutableStateOf(false) }
                            Column {
                                TextButton(
                                    onClick = { showInvestmentOptions = !showInvestmentOptions },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp, horizontal = 16.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.menu_investments),
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                if (showInvestmentOptions) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 32.dp)
                                    ) {
                                        TextButton(
                                            onClick = { /* Handle Mutual Funds click */ },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = "Mutual Funds",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                        TextButton(
                                            onClick = { /* Handle Fixed Deposits click */ },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = "Fixed Deposits",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                        TextButton(
                                            onClick = { /* Handle Stocks click */ },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = "Stocks",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                }
                            }
                            Divider(color = Color(0xFFE0E0E0))
                            
                            // Insurance with sub-options
                            var showInsuranceOptions by remember { mutableStateOf(false) }
                            Column {
                                TextButton(
                                    onClick = { showInsuranceOptions = !showInsuranceOptions },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp, horizontal = 16.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.menu_insurance),
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                if (showInsuranceOptions) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 32.dp)
                                    ) {
                                        TextButton(
                                            onClick = { /* Handle Life Insurance click */ },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = "Life Insurance",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                        TextButton(
                                            onClick = { /* Handle Health Insurance click */ },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = "Health Insurance",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                        TextButton(
                                            onClick = { /* Handle Vehicle Insurance click */ },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = "Vehicle Insurance",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                }
                            }
                            Divider(color = Color(0xFFE0E0E0))
                            
                            // Login with sub-options
                            var showLoginOptions by remember { mutableStateOf(false) }
                            Column {
                                TextButton(
                                    onClick = { showLoginOptions = !showLoginOptions },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp, horizontal = 16.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.menu_login),
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                if (showLoginOptions) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 32.dp)
                                    ) {
                                        TextButton(
                                            onClick = { showLoginDialog = true },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = "Customer Login",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                        TextButton(
                                            onClick = { showLoginDialog = true },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = "DSA Login",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                        TextButton(
                                            onClick = { showRegisterDialog = true },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = "Register",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = selectedItem == 0,
                    onClick = { selectedItem = 0 },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "My Home",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { 
                        Text(
                            "My Home",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                )
                NavigationBarItem(
                    selected = selectedItem == 1,
                    onClick = { selectedItem = 1 },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Loan",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { 
                        Text(
                            "Loan",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                )
                NavigationBarItem(
                    selected = selectedItem == 2,
                    onClick = { selectedItem = 2 },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "My Account",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { 
                        Text(
                            "My Account",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState)  // Make the content scrollable
            ) {
                // Image Carousel
                HorizontalPager(
                    count = carouselImages.size,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)  // Adjust height as needed
                ) { page ->
                    Image(
                        painter = painterResource(id = carouselImages[page]),
                        contentDescription = "Carousel Image ${page + 1}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                // Page indicators
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    activeColor = MaterialTheme.colorScheme.primary,
                    inactiveColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )

                // Image Frame
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),  // Replace with your image
                            contentDescription = "Frame Image",
                            modifier = Modifier
                                .size(200.dp)
                                .padding(8.dp),
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            text = "Welcome to KFinOne",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(
                            text = "Your Financial Partner",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                // Company Information
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Our Loans",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    // Horizontal Scrollable Image Frames
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // 5 Image Frames
                        repeat(5) { index ->
                            Card(
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(200.dp),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 4.dp
                                ),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.logo),
                                        contentDescription = "Partner Logo ${index + 1}",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                            }
                        }
                    }
                }

                // Loan Products Box
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Our Loan Products",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Discover our comprehensive range of loan products designed to meet your financial needs. From personal loans to business financing, we offer competitive rates and flexible terms to help you achieve your goals.",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // 12 Image Frames
                        for (i in 1..12) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 2.dp
                                ),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFF5F5F5)
                                )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)  // Medium size height
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.logo),  // Replace with your product image
                                        contentDescription = "Product Image $i",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(8.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                            }
                        }
                    }
                }

                // Insurance Products Box
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Our Insurance Products",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Protect what matters most with our comprehensive insurance solutions. From life insurance to health coverage, we offer tailored protection plans for you and your loved ones.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                // 2x2 Grid of Image Frames
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Top Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // First Image Frame
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF5F5F5)
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo),
                                    contentDescription = "Insurance Product 1",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }

                        // Second Image Frame
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF5F5F5)
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo),
                                    contentDescription = "Insurance Product 2",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }

                    // Bottom Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Third Image Frame
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF5F5F5)
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo),
                                    contentDescription = "Insurance Product 3",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }

                        // Fourth Image Frame
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF5F5F5)
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo),
                                    contentDescription = "Insurance Product 4",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }

                    // Horizontal Scrollable Image Frames
                    Text(
                        text = "Featured Products",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // 5 Image Frames
                        repeat(5) { index ->
                            Card(
                                modifier = Modifier
                                    .size(120.dp),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 4.dp
                                ),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                shape = CircleShape
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.logo),
                                        contentDescription = "Featured Product ${index + 1}",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Add the dialogs
            if (showLoginDialog) {
                LoginDialog(
                    onDismiss = { showLoginDialog = false },
                    onLogin = { email, password ->
                        // Handle login
                        showLoginDialog = false
                    },
                    onRegisterClick = {
                        showLoginDialog = false
                        showRegisterDialog = true
                    }
                )
            }

            if (showRegisterDialog) {
                RegisterDialog(
                    onDismiss = { showRegisterDialog = false },
                    onRegister = { name, email, phone, password ->
                        // Handle registration
                        showRegisterDialog = false
                    }
                )
            }
        }
    )
}

@Composable
fun MenuItem(
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun LoginDialog(
    onDismiss: () -> Unit,
    onLogin: (String, String) -> Unit,
    onRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Button(
                    onClick = { onLogin(email, password) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Login")
                }

                TextButton(
                    onClick = onRegisterClick,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Don't have an account? Register here")
                }
            }
        }
    }
}

@Composable
fun RegisterDialog(
    onDismiss: () -> Unit,
    onRegister: (String, String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var showOtpField by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Register",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                if (showOtpField) {
                    OutlinedTextField(
                        value = otp,
                        onValueChange = { otp = it },
                        label = { Text("OTP") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                Button(
                    onClick = { 
                        if (!showOtpField) {
                            showOtpField = true
                        } else {
                            onRegister(name, email, phoneNumber, password)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(if (!showOtpField) "Send OTP" else "Register")
                }

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Already have an account? Login here")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Kfinone3Theme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0F0F0))
        ) {
            MainScreen()
        }
    }
}