import { useState, useEffect } from "react";
import "./App.css";
import PictureBox from "./components/PictureBox";
import TextContainer from "./components/TextContainer";
import DinoChaser from "./components/Dino";
import DinoPray from "./components/DinoPray";
import Road from "./components/Road";
import "bootstrap/dist/css/bootstrap.css";
import axios from "axios";

function App() {
  const [time, setTime] = useState(0);
  const [isClockRunning, setIsClockRunning] = useState(false);
  const [typeSpeed, setTypeSpeed] = useState(60);

  useEffect(() => {
    const interval = setInterval(() => {
      if (isClockRunning) {
        setTime((prevTime) => prevTime + 1);
      }
    }, 1000);
    return () => clearInterval(interval);
  }, [isClockRunning]);

  const formattedTime = new Date(time * 1000).toISOString().substr(14, 5);

  function suspendClock() {
    setIsClockRunning(true);
  }

  function startClock() {
    setIsClockRunning(false);
    setTime(0);
  }
  
  function HandleSetTypeSpeed(speed: number){
    setTypeSpeed(speed*10);
  }

  return (
    <>
      <div className="Dino">
        <h1 className="title">DinoType</h1>
        <div className="animation">
          <DinoChaser />
          <DinoPray speed={typeSpeed} />
        </div>
        <Road width={1000} bottom={1000} />
      </div>
      <header className="timer">
        <h1>{formattedTime}</h1>
      </header>
      <TextContainer
        onResetTimer={startClock}
        onStartTimer={suspendClock}
        HandleSetTypeSpeed={HandleSetTypeSpeed}
      ></TextContainer>
    </>
  );
}

export default App;
