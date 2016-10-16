(ns box2d-physics-basic.core
  (:require [play-clj.core :refer :all]
            [play-clj.g2d :refer :all]
            [play-clj.g2d-physics :refer :all])
  (:import [com.badlogic.gdx.physics.box2d Box2DDebugRenderer]))

(defscreen main-screen
  :on-show
  (fn [screen entities]
    (let [screen (update! screen
                    :renderer (stage)
                    :camera (orthographic)
                    :world (box-2d 0 0)
                    :debug-renderer (Box2DDebugRenderer.))]
      [entities]))

  :on-resize
  (fn [screen entities]
    (height! screen 600))

  :on-render
  (fn [screen entities]
    (let [debug-renderer (:debug-renderer screen)
          world (:world screen)
          camera (:camera screen)]
      (clear!)
      (->> entities
           (step! screen) ; Runs physics simulations for a single frame
           (render! screen))
      (.render debug-renderer world (.combined camera)))))

(defgame box2d-physics-basic-game
  :on-create
  (fn [this]
    (set-screen! this main-screen)))
