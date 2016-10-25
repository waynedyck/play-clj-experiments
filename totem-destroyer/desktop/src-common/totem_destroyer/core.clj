(ns totem-destroyer.core
  (:require [play-clj.core :refer :all]
            [play-clj.g2d :refer :all]
            [play-clj.g2d-physics :refer :all]
            [play-clj.math :refer :all])
  (:import [com.badlogic.gdx.physics.box2d Box2DDebugRenderer]))

(def ^:const world-scale 30) ; pixels per meter

(defn create-brick-body!
  [screen x y width height]
  (let [body (add-body! screen (body-def :dynamic))]
    (->> (polygon-shape :set-as-box (/ (/ width 2) world-scale) (/ (/ height 2) world-scale)
                        (vector-2 (/ x world-scale) (/ y world-scale)) 0)
         (fixture-def :density 2 :restitution 0.4 :friction 0.5 :shape)
         (body! body :create-fixture))
    body))

(defn create-idol-body!
  [screen x y]
  (let [body (add-body! screen (body-def :dynamic))]
    (->> (polygon-shape :set-as-box (/ 5 world-scale) (/ 20 world-scale)
                        (vector-2 (/ x world-scale) (/ y world-scale)) 0)
         (fixture-def :density 1 :restitution 0.4 :friction 0.5 :shape)
         (body! body :create-fixture))
    body))

(defscreen main-screen
  :on-show
  (fn [screen entities]
    (let [game-w (/ (game :width) world-scale)
          game-h (/ (game :height) world-scale)
          screen (update! screen
                          :renderer (stage)
                          :camera (orthographic :set-to-ortho true game-w game-h)
                          :world (box-2d 0 0)
                          :debug-renderer (Box2DDebugRenderer.))
          brick-1 (doto {:body (create-brick-body! screen 275 435 30 30)})
          brick-2 (doto {:body (create-brick-body! screen 365 435 30 30)})
          brick-3 (doto {:body (create-brick-body! screen 320 405 120 30)})
          brick-4 (doto {:body (create-brick-body! screen 320 375 60 30)})
          brick-5 (doto {:body (create-brick-body! screen 305 345 90 30)})
          brick-6 (doto {:body (create-brick-body! screen 320 300 120 60)})
          idol (doto {:body (create-idol-body! screen 320 242)})]
      (width! screen game-w)))
  
  :on-render
  (fn [screen entities]
    (let [debug-renderer (:debug-renderer screen)
          world (:world screen)
          camera (:camera screen)]
      (clear!)
      (->> entities
           (step! screen) ; runs physics simulations for a single frame
           (render! screen))
      (.render debug-renderer world (.combined camera)))))

(defgame totem-destroyer-game
  :on-create
  (fn [this]
    (set-screen! this main-screen)))
