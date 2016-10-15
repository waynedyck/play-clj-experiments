## Introduction

This simple [play-clj](https://github.com/oakes/play-clj) animation builds on the code used in the [looping-animation](https://github.com/waynedyck/play-clj-experiments/tree/master/looping-animation) example.

The graphics and animation concepts are based on the libGDX [2D Animation](https://github.com/libgdx/libgdx/wiki/2D-Animation) tutorial. The tutorial shows you how to create a complete running cycle consisting of 30 frames from a 6 column, 5 row spritesheet.

In your terminal, go to the `desktop` directory and type `lein run` and you will see a smooth animation of a man running.

![Running man animation](https://github.com/waynedyck/play-clj-experiments/blob/master/looping-animation-2/desktop/resources/sprite-animation3.png)

## Contents

* `android/src` Android-specific code
* `desktop/resources` Images, audio, and other files
* `desktop/src` Desktop-specific code
* `desktop/src-common` Cross-platform game code
