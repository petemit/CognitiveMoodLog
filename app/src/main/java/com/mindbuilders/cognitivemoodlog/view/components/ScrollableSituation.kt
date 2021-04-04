package com.mindbuilders.cognitivemoodlog.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindbuilders.cognitivemoodlog.ui.theme.CognitiveMoodLogTheme

@Composable
fun ScrollableSituation(situation: String) {
    ScrollableText(
        string = "Situation: $situation",
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 8.dp)
    )
}

@Composable
fun ScrollableText(string: String, modifier: Modifier) {
    BasicTextField(
        value = string,
        onValueChange = {},
        readOnly = true,
        modifier = modifier
    )
}

@Preview
@Composable
fun ScrollableSituation_Preview() {
    CognitiveMoodLogTheme {
        ScrollableSituation(
            situation = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut quis egestas ante, eu facilisis nulla. Vestibulum vestibulum scelerisque orci vitae congue. Pellentesque finibus nibh tempor sollicitudin sodales. Suspendisse urna elit, venenatis maximus aliquam vel, condimentum at justo. Quisque condimentum turpis sit amet dolor facilisis pulvinar. Maecenas eu hendrerit lorem, quis luctus ante. Aenean semper iaculis eros, ut viverra metus sollicitudin quis. Aliquam cursus ullamcorper purus ut egestas. Morbi fermentum, nulla quis pretium dictum, nulla nunc faucibus libero, non vehicula magna eros eget purus. Vestibulum feugiat ipsum nec eros consectetur, rutrum faucibus nulla viverra. Fusce nec nulla eu justo tincidunt maximus. Praesent egestas, risus eget commodo posuere, elit diam tincidunt nisl, elementum tincidunt lectus dolor placerat quam. Nulla eget tortor fringilla, venenatis augue et, porttitor mauris.\n" +
                    "\n" +
                    "Aenean odio justo, egestas quis pretium a, hendrerit ut felis. Cras lectus augue, imperdiet at semper et, efficitur vel nibh. Sed imperdiet mi id vulputate faucibus. In tempor enim ipsum. Mauris dictum libero nisi, ut mattis massa consectetur non. Praesent sagittis blandit velit elementum tincidunt. Phasellus non nunc in ex fringilla mollis. Duis malesuada turpis at rhoncus lobortis. Aliquam metus erat, vulputate scelerisque pellentesque nec, aliquet ut turpis.\n" +
                    "\n" +
                    "Quisque mauris ligula, convallis ut nulla at, accumsan dapibus enim. Morbi id egestas urna. Nam mattis dignissim eros at ornare. Sed luctus ac sem sed blandit. Praesent fermentum dui in mattis luctus. Phasellus elit metus, mattis sit amet sapien ut, aliquet maximus justo. Vestibulum tincidunt, mi nec imperdiet dictum, nibh sem ullamcorper elit, quis sodales mauris enim ut massa. Nulla et neque purus. Nunc gravida mi elementum ullamcorper pharetra. Suspendisse tempus, nibh non porta tincidunt, augue quam vulputate massa, in accumsan purus arcu nec tortor. Praesent a diam at neque mattis commodo et dictum nisi. Etiam sit amet nunc non tortor cursus rutrum. Praesent tristique, odio ut suscipit dictum, dui arcu rhoncus ante, eu gravida ante ante ut arcu. Donec eleifend ac nibh quis convallis.\n" +
                    "\n" +
                    "Vivamus ut ultrices augue, quis auctor ex. Etiam lorem nisi, tincidunt at volutpat faucibus, volutpat vel metus. Integer faucibus massa non nisl ultrices volutpat. Mauris sit amet libero ipsum. Interdum et malesuada fames ac ante ipsum primis in faucibus. Vivamus viverra eget ligula et tempor. Phasellus viverra malesuada mattis. Proin molestie lectus et maximus imperdiet. Maecenas tempor elementum magna, ac sagittis nulla fermentum sit amet. Suspendisse potenti. Nulla condimentum eleifend magna vel eleifend. Proin in gravida est. Cras non magna porttitor, laoreet purus sit amet, dapibus nisl. Nullam vitae pharetra dui, ultricies elementum elit. Phasellus dapibus odio non vehicula consequat.\n" +
                    "\n" +
                    "Duis vehicula dolor ut congue rhoncus. Ut ultrices enim sit amet euismod ultricies. Aliquam facilisis sodales quam non vehicula. Sed viverra mi at orci rutrum finibus pellentesque at justo. Integer eget malesuada justo. Nulla consectetur augue at ex mattis rutrum. Aenean ut velit id orci mattis euismod. Quisque elementum metus quis enim vehicula, a cursus massa hendrerit. In vehicula mollis turpis, sit amet consequat nulla scelerisque a. Duis quis semper dui. Nulla volutpat mauris vitae tristique commodo."
        )

    }
}