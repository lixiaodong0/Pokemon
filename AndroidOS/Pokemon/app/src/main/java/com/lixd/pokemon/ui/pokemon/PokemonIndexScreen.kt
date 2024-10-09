package com.lixd.pokemon.ui.pokemon

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.lixd.pokemon.data.bean.PokemonIndexBean
import com.lixd.pokemon.navigation.PokemonDescriptionRoute
import com.lixd.pokemon.ui.widget.ImageKeyWidget
import com.lixd.pokemon.ui.widget.NumberKeyWidget
import com.lixd.pokemon.ui.widget.PokeBallIcon
import com.lixd.pokemon.ui.widget.PokeBottomBar
import kotlinx.coroutines.launch

@Composable
fun PokemonIndexScreen(
    viewModel: PokemonIndexViewModel = viewModel(),
    navController: NavHostController,
) {
    val lazyItems = viewModel.getPokemonIndex.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()
    val viewStatus by viewModel.viewStatus.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Column(Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f, true)
            .drawBehind {
                val offset = size.center.x / 3
                //第一层背景
                drawRect(Color(0xfffdfcf8))
                //第二层背景
                val path = Path().apply {
                    moveTo(center.x - offset, size.height)
                    lineTo(center.x, 0f)
                    lineTo(size.width, 0f)
                    lineTo(size.width, size.height)
                    close()
                }
                drawPath(path, Color(0xfff3533b))
                //第三层背景
                val path2 = Path().apply {
                    moveTo(center.x, size.height)
                    lineTo(center.x + offset, 0f)
                    lineTo(size.width, 0f)
                    lineTo(size.width, size.height)
                    close()
                }
                drawPath(path2, Color(0xfffa7346))
            }) {
            PokemonIndexContainer(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxWidth(0.5f)
                    .align(Alignment.CenterEnd),
                datas = lazyItems,
                currentIndex = viewStatus.currentIndex,
                state = lazyListState,
            ) { index, item ->
                if (viewStatus.currentIndex == index) {
                    navController.navigate("${PokemonDescriptionRoute}/${item.number}") {
                        launchSingleTop = true
                    }
                }
                viewModel.updateSelectedIndex(index, item)
            }
            PokemonImageContainer(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.CenterStart),
                data = viewStatus.currentPokemonIndexBean
            )
            IndexTopBarContainer(viewStatus.totalCount)
        }
        PokeBottomBar(content = {
            ImageKeyWidget(image = Icons.Default.KeyboardArrowUp, desc = "Previous") {
                val index = viewStatus.currentIndex - 1
                try {
                    val item = lazyItems[index]
                    viewModel.updateSelectedIndex(index, item!!)
                    coroutineScope.launch {
                        lazyListState.scrollToItem(index)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            ImageKeyWidget(image = Icons.Default.KeyboardArrowDown, desc = "Next") {
                val index = viewStatus.currentIndex + 1
                try {
                    val item = lazyItems[index]
                    viewModel.updateSelectedIndex(index, item!!)
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(index)
//                        lazyListState.scrollToItem(index)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            NumberKeyWidget(key = "A", desc = "See Details") {
                try {
                    lazyItems[viewStatus.currentIndex]?.let {
                        navController.navigate("${PokemonDescriptionRoute}/${it.number}") {
                            launchSingleTop = true
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }, onBack = {
            Toast.makeText(context, "返回", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        })
    }
}

@Composable
fun IndexTopBarContainer(totalCount: Int = 0) {
    val topSpace = 10.dp
    val topBarHeight = 40.dp
    Box(modifier = Modifier
        .fillMaxSize()
        .drawBehind {
            val offset = size.center.x / 3
            val path = Path().apply {
                moveTo(center.x - offset, size.height)
                lineTo(center.x, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width, size.height)
                close()
            }
            clipRect(0f, topSpace.toPx(), size.width, topSpace.toPx() + topBarHeight.toPx()) {
                //第一层背景
                drawRect(Color(0XFFefefef))
                //第二层背景
                drawPath(path, Color(0xE6000000))
            }
        }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = topSpace)
                .height(topBarHeight)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(), contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .background(
                            Color(0xE6000000), shape = RoundedCornerShape(40.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    PokeBallIcon()
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(text = "$totalCount", fontSize = 14.sp, color = Color.White)
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(), contentAlignment = Alignment.Center
            ) {
                Text(text = "By Number", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}


@Composable
fun PokemonImageContainer(modifier: Modifier = Modifier, data: PokemonIndexBean?) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.5f)
            .fillMaxHeight()
    ) {
        data?.let {
            AsyncImage(
                model = it.avatar,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Composable
fun PokemonIndexContainer(
    modifier: Modifier = Modifier,
    state: LazyListState,
    datas: LazyPagingItems<PokemonIndexBean>,
    currentIndex: Int = 0,
    onItemClick: (Int, PokemonIndexBean) -> Unit,
) {
    LazyColumn(modifier = modifier, state = state) {
        items(datas.itemCount) { index ->
            datas[index]?.run {
                PokemonIndexItem(index == currentIndex, this) {
                    onItemClick(index, this)
                }
            }
        }
    }
}

@Composable
fun PokemonIndexItem(
    isSelected: Boolean = false, data: PokemonIndexBean, onClick: () -> Unit
) {
    val leftContentWeight = 0.4f
    val rightContentWeight = 0.6f
    val offset = 20.dp
    val avatarSize = 40.dp
    val arrowSize = 20.dp
    val defaultElevation = if (isSelected) 4.dp else 0.dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp)
            .heightIn(avatarSize)
            .clickable(onClick = onClick)
    ) {
        Surface(
            modifier = Modifier
                .padding(start = avatarSize + arrowSize / 2)
                .align(Alignment.Center),
            shape = RoundedCornerShape(avatarSize),
            color = Color.Transparent,
            contentColor = Color.Transparent,
            shadowElevation = defaultElevation
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                //选中的情况下绘制背景色
                .drawBehind {
                    if (isSelected) {
                        drawRect(Color(0xffeb521d))
                        val path = Path().apply {
                            val startX = size.width * leftContentWeight
                            moveTo(startX, size.height)
                            lineTo(startX + offset.value, 0f)
                            lineTo(size.width, 0f)
                            lineTo(size.width, size.height)
                            close()
                        }
                        drawPath(path, Color(0xff000200))
                    }
                }
                .padding(vertical = 4.dp)) {
                Text(
                    text = "No.${data.number}",
                    fontSize = 14.sp,
                    color = if (isSelected) Color.White else Color.Black,
                    modifier = Modifier
                        .weight(leftContentWeight)
                        .padding(start = offset / 2)
                )
                Text(
                    text = data.name,
                    fontSize = 14.sp,
                    color = if (isSelected) Color.White else Color.Black,
                    modifier = Modifier
                        .weight(rightContentWeight)
                        .padding(start = offset / 2)
                )
            }
        }

        AsyncImage(
            model = data.avatar,
            contentDescription = null,
            modifier = Modifier
                .padding(start = arrowSize / 2)
                .size(avatarSize),
            contentScale = ContentScale.Crop
        )
        if (isSelected) {
            Image(
                painter = rememberVectorPainter(Icons.Default.KeyboardArrowRight),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(arrowSize)
            )
        }
    }
}


@Preview
@Composable
fun IndexTopBarContainerPreview() {
    IndexTopBarContainer()
}