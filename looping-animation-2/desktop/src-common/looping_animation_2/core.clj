(ns looping-animation-2.core
  (:require [play-clj.core :refer :all]
            [play-clj.g2d :refer :all]))

(defn create
  [stand & run]
  (assoc stand
         :run-right (animation 0.025
                               run
                               :set-play-mode (play-mode :loop))
         :x 50
         :y 50
         :me? true))

(defn animate
  [screen {:keys [run-right] :as entity}]
  (merge entity
         (animation->texture screen run-right)
         {:me? true}))

(defscreen main-screen
  :on-show
  (fn [screen entities]
    (update! screen :camera (orthographic) :renderer (stage))
    (let [sheet (texture "animation_sheet.png")
          tiles (texture! sheet :split
                          (/ (texture! sheet :get-region-width) 6)
                          (/ (texture! sheet :get-region-height) 5))
          player-images (for [row [0 1 2 3 4]
                              col [0 1 2 3 4 5]]
                          (texture (aget tiles row col)))]
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

(defgame looping-animation-2-game
  :on-create
  (fn [this]
    (set-screen! this main-screen)))
