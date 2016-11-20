(ns totem-destroyer.core.desktop-launcher
  (:require [totem-destroyer.core :refer :all])
  (:import [com.badlogic.gdx.backends.lwjgl LwjglApplication]
           [org.lwjgl.input Keyboard])
  (:gen-class))

(defn -main
  []
  (LwjglApplication. totem-destroyer-game "totem-destroyer" 640 480)
  (Keyboard/enableRepeatEvents true))
