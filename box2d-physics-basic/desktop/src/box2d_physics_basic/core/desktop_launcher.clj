(ns box2d-physics-basic.core.desktop-launcher
  (:require [box2d-physics-basic.core :refer :all])
  (:import [com.badlogic.gdx.backends.lwjgl LwjglApplication]
           [org.lwjgl.input Keyboard])
  (:gen-class))

(defn -main
  []
  (LwjglApplication. box2d-physics-basic-game "box2d-physics-basic" 800 600)
  (Keyboard/enableRepeatEvents true))
