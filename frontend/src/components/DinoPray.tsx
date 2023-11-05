import { useEffect, useRef, useState } from "react";
import Dino1 from "../assets/Dino1.png";
import Dino2 from "../assets/Dino2.png";
import GameOver from "../assets/20231104230620.png";
import "./Dino.css";

interface DinoPrayProps {
  speed: number;
}

const DinoPray = (props: DinoPrayProps) => {
  const [imageIndex, setImageIndex] = useState(0);
  const [gameOver, setGameOver] = useState(false);
  const images = [Dino1, Dino2];
  const dinoRef = useRef<HTMLDivElement | null>(null);
  const positionRef = useRef(0);

  useEffect(() => {
    const handleKeyDown = () => {
      setImageIndex((prevIndex) => (prevIndex + 1) % images.length);
    };

    window.addEventListener("keydown", handleKeyDown);

    return () => {
      window.removeEventListener("keydown", handleKeyDown);
    };
  });

  useEffect(() => {
    if (dinoRef.current) {
      let animationDuration = 0;
      if (props.speed >= 60) {
        animationDuration = 100000 / (props.speed - 60);
      } else if (props.speed < 60) {
        animationDuration = 100000 / (props.speed - 60);
      }

      const startPosition = 0;
      const endPosition = 300; // Adjust this value based on how far you want the Dino to move
      const rightBoundary = 500;
      const leftBoundary = -500;

      const animate = () => {
        if (dinoRef.current && positionRef.current < endPosition) {
          let newPosition =
            positionRef.current +
            (endPosition - startPosition) / (animationDuration / 16.67);

          if (newPosition > rightBoundary) {
            newPosition = rightBoundary; // Ensure the Dino doesn't go beyond the maximum position
          } else if (newPosition <= leftBoundary) {
            newPosition = leftBoundary;
            setGameOver(!gameOver);
          }
          dinoRef.current.style.transform = `translateX(${newPosition}px)`;
          positionRef.current = newPosition;
          requestAnimationFrame(animate);
        }
      };

      animate();
    }
  }, [props.speed]);

  const gameOutput = gameOver ? (
    <div>
      <img src={GameOver} alt="gameover"></img>
    </div>
  ) : (
    <div ref={dinoRef} style={{ transform: `translateX(0)` }}>
      <img className="pray" src={images[imageIndex]} alt="dinoPray" />
    </div>
  );

  return <>{gameOutput}</>;
};

export default DinoPray;
