(ns totem-destroyer.core
  (:require [play-clj.core :refer :all]
            [play-clj.g2d :refer :all]
            [play-clj.g2d-physics :refer :all]
            [play-clj.math :refer :all])
  (:import [com.badlogic.gdx.physics.box2d Box2DDebugRenderer]
           [com.badlogic.gdx.physics.box2d QueryCallback]))

(def ^:const world-scale 30) ; pixels per meter

(defn create-brick-body!
  [screen width height]
  (let [body (add-body! screen (body-def :dynamic))
        brick-w (/ width world-scale)
        brick-h (/ height world-scale)
        x (/ (/ width 2) world-scale)
        y (/ (/ height 2) world-scale)]
    (->> (polygon-shape :set-as-box (/ brick-w 2) (/ brick-h 2) (vector-2 x y) 0)
         (fixture-def :density 2 :restitution 0.4 :friction 0.5 :shape)
         (body! body :create-fixture))
    body))

(defn create-brick-entity!
  [screen brick width height]
  (assoc brick
         :body (create-brick-body! screen width height)
         :width (/ width world-scale) :height (/ height world-scale)))

(defn create-idol-body!
  [screen x y]
  (let [body (add-body! screen (body-def :dynamic))
        center-x (/ x world-scale)
        center-y (/ y world-scale)]
    (->> (polygon-shape :set-as-box (/ 5 world-scale) (/ 20 world-scale) (vector-2 center-x center-y) 0)
         (fixture-def :density 1 :restitution 0.4 :friction 0.5 :shape)
         (body! body :create-fixture))
    (->> (polygon-shape :set-as-box (/ 5 world-scale) (/ 20 world-scale)
                        (vector-2 center-x (+ center-y (/ 10 world-scale)))
                        (/ Math/PI -4))
         (fixture-def :density 1 :restitution 0.4 :friction 0.5 :shape)
         (body! body :create-fixture))
    (->> (polygon-shape :set-as-box (/ 5 world-scale) (/ 20 world-scale)
                        (vector-2 center-x (+ center-y (/ 10 world-scale)))
                        (/ Math/PI 4))
         (fixture-def :density 1 :restitution 0.4 :friction 0.5 :shape)
         (body! body :create-fixture))
    (->> [(+ center-x (/ -15 world-scale)) (+ center-y (/ -25 world-scale))
          (+ center-x 0) (+ center-y (/ -40 world-scale))
          (+ center-x (/ 15 world-scale)) (+ center-y (/ -25 world-scale))
          (+ center-x 0) (+ center-y (/ -10 world-scale))]
         float-array
         (polygon-shape :set)
         (fixture-def :density 1 :restitution 0.4 :friction 0.5 :shape)
         (body! body :create-fixture))
    body))

(defn create-floor-body!
  [screen x y width height]
  (let [body (add-body! screen (body-def :static))]
    (->> (polygon-shape :set-as-box (/ (/ width 2) world-scale) (/ (/ height 2) world-scale)
                        (vector-2 (/ x world-scale) (/ y world-scale)) 0)
         (fixture-def :density 2 :restitution 0.4 :friction 0.5 :shape)
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
                          :world (box-2d 0 5)
                          :debug-renderer (Box2DDebugRenderer.))
          brick (texture "1x1-transparent.png")
          brick-1 (doto (create-brick-entity! screen brick 30 30) (body-position! (/ 275 world-scale) (/ 435 world-scale) 0))
          brick-2 (doto (create-brick-entity! screen brick 30 30) (body-position! (/ 365 world-scale) (/ 435 world-scale) 0))
          brick-3 (doto (create-brick-entity! screen brick 120 30) (body-position! (/ 275 world-scale) (/ 395 world-scale) 0))
          brick-4 (doto (create-brick-entity! screen brick 60 30) (body-position! (/ 305 world-scale) (/ 365 world-scale) 0))
          brick-5 (doto (create-brick-entity! screen brick 90 30) (body-position! (/ 275 world-scale) (/ 335 world-scale) 0))
          brick-6 (doto (create-brick-entity! screen brick 120 60) (body-position! (/ 275 world-scale) (/ 280 world-scale) 0))
          idol (doto {:body (create-idol-body! screen 320 242)})
          floor (doto {:body (create-floor-body! screen 320 470 640 20)})]
      (width! screen game-w)

      ; Return the entities
      [(assoc brick-1 :brick? true)
       (assoc brick-2 :brick? true)
       (assoc brick-3 :brick? true)
       (assoc brick-4 :brick? true)
       (assoc brick-5 :brick? true)
       (assoc brick-6 :brick? true)
       (assoc idol :idol? true)
       (assoc floor :floor? true)]))
  
  :on-render
  (fn [screen entities]
    (let [debug-renderer (:debug-renderer screen)
          world (:world screen)
          camera (:camera screen)]
      (clear!)
      (->> entities
           (step! screen) ; runs physics simulations for a single frame
           (render! screen))
      (.render debug-renderer world (.combined camera))))

  :on-touch-down
  (fn [screen entities]
    (let [coords (input->screen screen (input! :get-x) (input! :get-y))
          world (:world screen)
          bb [(- (:x coords) 0.1) (- (:y coords) 0.1) (+ (:x coords) 0.1) (+ (:y coords) 0.1)]
          pt-vec2 (vector-2 (input! :get-x) (input! :get-y))
          cb (reify QueryCallback
               (reportFixture [_ fixture]
                 (if (.testPoint fixture pt-vec2)
                   true)))])))

(defgame totem-destroyer-game
  :on-create
  (fn [this]
    (set-screen! this main-screen)))
