import { useEffect, useState } from "react";
import Dino1 from "../assets/Dino1.png";
import Dino2 from "../assets/Dino2.png";
import "./Dino.css";

const DinoChaser = () => {
  const [imageIndex, setImageIndex] = useState(0);
  const images = [Dino1, Dino2];

  useEffect(() => {
    const interval = setInterval(() => {
      setImageIndex((prevIndex) => (prevIndex + 1) % images.length);
    }, 100); // Change images every 2 seconds

    return () => clearInterval(interval); // Clear the interval when the component unmounts
  }, []);

  return (
    <>
      <div>
        <img className="chaser" src={images[imageIndex]} alt="dinoImage" />
      </div>
    </>
  );
};

export default DinoChaser;
