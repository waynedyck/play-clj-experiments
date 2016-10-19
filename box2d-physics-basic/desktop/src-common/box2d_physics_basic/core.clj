(ns box2d-physics-basic.core
  (:require [play-clj.core :refer :all]
            [play-clj.g2d :refer :all]
            [play-clj.g2d-physics :refer :all]
            [play-clj.math :refer :all])
  (:import [com.badlogic.gdx.physics.box2d Box2DDebugRenderer]))

(def ^:const pixels-per-meter 30)
(def ^:const game-w (/ 800 pixels-per-meter))
(def ^:const game-h (/ 600 pixels-per-meter))

(defn screen-to-world
  [px]
  (float (/ px pixels-per-meter)))

(defn create-ball-body!
  [screen radius]
  (let [body (add-body! screen (body-def :dynamic))]
    (->> (circle-shape :set-radius radius
                       :set-position (vector-2 (/ game-w 2) (screen-to-world 500)))
         (fixture-def :density 0.5 :friction 0.4 :restitution 0.6 :shape)
         (body! body :create-fixture))
    body))

(defn create-rect-body!
  [screen width height]
  (let [body (add-body! screen (body-def :static))]
    (->> (polygon-shape :set-as-box width height (vector-2 (/ game-w 2) 1) 0)
         (fixture-def :density 1 :shape)
         (body! body :create-fixture))
    body))

(defscreen main-screen
  :on-show
  (fn [screen entities]
    (let [screen (update! screen
                    :renderer (stage)
                    :camera (orthographic :set-to-ortho false game-w game-h)
                    :world (box-2d 0 -10)
                    :debug-renderer (Box2DDebugRenderer.))
          ball (doto {:body (create-ball-body! screen 1)})
          floor (doto {:body (create-rect-body! screen (/ game-w 2) 1)})]
      (width! screen game-w)
      [entities]))

  :on-resize
  (fn [screen entities]
    (height! screen game-h))

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
