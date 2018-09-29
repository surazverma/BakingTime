# BakingTime
Baking Time is a very useful application which allows users to select recipe and see video guided steps for how to finish it.
Also the recipes are provided by the Udacity resident baker-in-chef Miriam.

## Project Overview
In this project we have productionized an app from a functional state to a production ready state. In this project we have designed the app
according to the required [mockup](https://d17h27t6h515a5.cloudfront.net/topher/2017/March/58dee986_bakingapp-mocks/bakingapp-mocks.pdf) design. To make it a production ready application we have find and handled error cases, added a widget and
a library also.

## Features
* User can select recipe from the recipe list from the main screen.
* User can get the list of ingredients and the step by step instruction of the recipe.
* User can also watch the videos of some instruction step.
* User can add the list of ingredients to a widget.
* Also the UI changes when the app is used on a tablet

## Resources
The [Recipes](https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json) resources are provided by udacity which is in a json format.

## Screens
<img src="../master/Screenshot/Screen_1.png" width="200"><img src="../master/Screenshot/Screen_2.png" width="200"><img src="../master/Screenshot/Screen_3.png" width="200">
<img src="../master/Screenshot/Screen_land_1.png" width="600">
<img src="../master/Screenshot/Screen_Tab_1.png" width="600">
<img src="../master/Screenshot/Screen_Tab_2.png" width="600">


## Libraries Used
* [Retrofit](https://square.github.io/retrofit/) - Retrofit is used to handle the network calls.
* [Picasso](https://square.github.io/picasso/) - Picasso is used for loading the images.
* [Exoplayer](https://github.com/google/ExoPlayer) - Exoplayer is used as video player to show the recipe videos.
