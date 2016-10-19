## Introduction

This simple [play-clj](https://github.com/oakes/play-clj) and Box2D based example defines a world with gravity and two bodies; one dynamic and one static. A dynamic ball shape falls with gravity to bounce on a static floor below.

In your terminal, go to the `desktop` directory and type `lein run` and you should see a ball drop and bounce a few times on the floor below.

![Simple Box2D animation](https://github.com/waynedyck/play-clj-experiments/blob/master/box2d-physics-basic/desktop/resources/screenshot.png)

## Contents

* `android/src` Android-specific code
* `desktop/resources` Images, audio, and other files
* `desktop/src` Desktop-specific code
* `desktop/src-common` Cross-platform game code
