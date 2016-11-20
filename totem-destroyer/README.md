## Introduction

This [play-clj](https://github.com/oakes/play-clj) Box2D simulation is based on the code and concepts from Chapter 2 of the [Box2D for Flash Games](http://a.co/76QHM1t) book. The focus of the chapter is on applying what you have learned to create a level from the Flash game, [Totem Destroyer](http://armorgames.com/play/1871/totem-destroyer). The top and third brick down are indestructable while clicking the others will destroy them causing the idol to fall.

In order for the code to work correctly you will need to use the current `1.2.0-SNAPSHOT` source of the play-clj library. You will also need to apply [this pull request](https://github.com/oakes/play-clj/pull/99) which corrects the alignment of textures on bodies when those bodies are rotated.

In your terminal, go to the `desktop` directory and type `lein run` and you will see a screen similar to the one below.

![Example Totem Destroyer level](https://github.com/waynedyck/play-clj-experiments/blob/master/totem-destroyer/desktop/resources/screen-shot.png)

## Contents

* `android/src` Android-specific code
* `desktop/resources` Images, audio, and other files
* `desktop/src` Desktop-specific code
* `desktop/src-common` Cross-platform game code
