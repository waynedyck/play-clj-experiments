(ns looping-animation.core
  (:require [play-clj.core :refer :all]
            [play-clj.g2d :refer :all]))

(defn create
  [stand & fly]
  (assoc stand
         :fly-left (animation 0.10
                                fly
                                :set-play-mode (play-mode :loop-pingpong))
         :me? true))

(defn animate
  [screen {:keys [fly-left] :as entity}]
  (merge entity
         (animation->texture screen fly-left)
         {:me? true}))

(defscreen main-screen
  :on-show
  (fn [screen entities]
    (update! screen :camera (orthographic) :renderer (stage))
    (let [sheet (texture "animation.png")
          tiles (texture! sheet :split 64 64)
          player-images (for [col [0 1 2 3 4 5 6 7 8 9]]
                          (texture (aget tiles 0 col)))]
      (apply create player-images)))

  :on-resize
  (fn [screen entities]
    (height! screen 600))

  :on-render
  (fn [screen entities]
    (clear!)
    (->> entities
         (map (fn [entity]
                (->> entity
                     (animate screen))))
         (render! screen))))

(defgame looping-animation-game
  :on-create
  (fn [this]
    (set-screen! this main-screen)))
