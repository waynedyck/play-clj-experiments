(ns looping-animation-2.core.desktop-launcher
  (:require [looping-animation-2.core :refer :all])
  (:import [com.badlogic.gdx.backends.lwjgl LwjglApplication]
           [org.lwjgl.input Keyboard])
  (:gen-class))

(defn -main
  []
  (LwjglApplication. looping-animation-2-game "looping-animation-2" 800 600)
  (Keyboard/enableRepeatEvents true))
