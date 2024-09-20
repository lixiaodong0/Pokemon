package com.lixd.pokemon.ui.desc.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lixd.pokemon.ui.widget.PokemonAttributeWidget
import okhttp3.internal.wait

@Composable
fun PokemonInfoCardContainer() {

}


@Composable
fun AttributeInfoItem(title: String = "属性", primaryAttrName: String, secondaryAttrName: String) {
    BasicInfoItem(leftContent = {
        Text(
            text = title,
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }, rightContent = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.Center)
        ) {
            Surface(modifier = Modifier.weight(1f), color = Color.Transparent) {
                if (primaryAttrName.isNotEmpty()) {
                    PokemonAttributeWidget(attrIcon = "", attrName = primaryAttrName)
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            Surface(modifier = Modifier.weight(1f), color = Color.Transparent) {
                if (secondaryAttrName.isNotEmpty()) {
                    PokemonAttributeWidget(attrIcon = "", attrName = secondaryAttrName)
                }
            }
        }
    })
}


@Composable
fun NameInfoItem(key: String = "名称", value: String) {
    BasicInfoItem(leftContent = {
        Text(
            text = key,
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }, rightContent = {
        Text(
            text = value,
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 16.dp)
        )
    })
}


@Composable
fun BasicInfoItem(
    leftContent: @Composable BoxScope.() -> Unit,
    rightContent: @Composable BoxScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.Gray),
        ) {
            leftContent()
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.White),
        ) {
            rightContent()
        }
    }
}

@Preview
@Composable
fun BasicInfoItemPreview() {
    Column {
        NameInfoItem("名称", "水军")
        AttributeInfoItem(primaryAttrName = "飞行", secondaryAttrName = "草")
    }
}