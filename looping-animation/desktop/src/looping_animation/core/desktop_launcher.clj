(ns looping-animation.core.desktop-launcher
  (:require [looping-animation.core :refer :all])
  (:import [com.badlogic.gdx.backends.lwjgl LwjglApplication]
           [org.lwjgl.input Keyboard])
  (:gen-class))

(defn -main
  []
  (LwjglApplication. looping-animation-game "looping-animation" 800 600)
  (Keyboard/enableRepeatEvents true))
