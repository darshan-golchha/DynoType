import React from "react";
import axios from "axios";

let typedWordCount = 0;
let wrongWordCount = 0;
let startTime = Date.now();
let timeElapsed = 0;



function TextContainer(props: {

    onResetTimer: () => void;
    onStartTimer: () => void;
    HandleSetTypeSpeed: (speed:number) => void;
}) {
    let [importedText, setImportedText] = React.useState(
        `Dose sentiment betray knot stunning angel pause original snub unrest drain damn wrong digital waste mud lost direction thank seem fever dialogue miscarriage voyage password dull float gate conspiracy match joystick selection arena mosque banner tear blame run resort manner throat skeleton hand publication privacy grand fraud mature father kidnap district dawn boat week pledge suit romantic process runner movement population domination manager raw nose print diplomat exemption presentation infection technique escape shock girl addition fuel raise month owner preference`
    );

    //typed text
    let [text, setText] = React.useState("");

    let [index, setIndex] = React.useState(0);

    let [wrong, setWrong] = React.useState(0);

    let [sessionId, setSessionId] = React.useState("");

    let [wpm, setWpm] = React.useState(0);
    let [accuracy, setAccuracy] = React.useState(0);

    function keyDownHandler(event: KeyboardEvent) {
        
        if (event.key === "Backspace") {
            if (index === 0) return;
            setText(text.slice(0, -1));
            setIndex(index - 1);
            if (wrong > 0) {
                setWrong(wrong - 1);
            }
            return;
        }


        if (event.key === importedText[index] || (event.shiftKey && event.key.toUpperCase() === importedText[index])) {
            console.log("correct: " + event.key);
            setText(text + event.key);
            setIndex(index + 1);
            if (wrong > 0) setWrong(wrong + 1);         
        }
        else {
            if(event.key === "Shift") return;
            console.log(
                "incorrect: " + event.key + " instead of: " + importedText[index]
            );
            setText(text + event.key);
            setIndex(index + 1);
            setWrong(wrong + 1);
        }
    }

    function classString(letterIndex: number) {
        if (letterIndex >= index - wrong && letterIndex < index) {
            return "letter wrongLetter";
        } else if (letterIndex < index) {
            return "letter typedLetter";
        } else if (letterIndex === index) {
            return "letter currentLetter";
        } else {
            return "letter";
        }
    }

    let displayText = importedText.split("").map((letter, index) => {
        if (letter === " ") {
            return (
                <div className={classString(index)} key={index}>
                    &nbsp;
                </div>
            );
        } else {
            return (
                <div className={classString(index)} key={index}>
                    {letter}
                </div>
            );
        }
    });

    let rows = [];
    for (let i = 0; i < displayText.length; i += 110) {
        rows.push(displayText.slice(i, i + 110));
    }

    function updateGameState(): void {

        if (!startTime || !sessionId) {
            // If "Initialize" hasn't been clicked or sessionId is not available, do nothing
            return;
        }

        axios
            .get("https://dynotype.onrender.com/updateState", {
                params: {
                    sessionId: sessionId,
                    timeElapsed: Math.floor(timeElapsed / 1000),
                    correct: typedWordCount,
                    wrong: wrongWordCount,
                },
            })
            .then((response) => {
                props.HandleSetTypeSpeed(response.data.preyPosition);
            })
            .catch((error) => {
                console.error("Update state error: ", error);
            });
    };

    React.useEffect(() => {
        addEventListener("keydown", keyDownHandler);

        return () => {
            removeEventListener("keydown", keyDownHandler);
        };
    }, [text]);

    React.useEffect(() => {
        const interval = setInterval(() => {
            const letterElements = document.getElementsByClassName("typedLetter");
            typedWordCount = 0;
            for (let i = 0; i < letterElements.length; i++) {
                if (letterElements[i].innerHTML === "&nbsp;") {
                    typedWordCount++;
                }
            }

            const wrongLetterElements =
                document.getElementsByClassName("wrongLetter");
            wrongWordCount = 0;
            for (let i = 0; i < wrongLetterElements.length; i++) {
                if (wrongLetterElements[i].innerHTML === "&nbsp;") {
                    wrongWordCount++;
                }
            }

            // console.log("Typed: " + typedWordCount);
            // console.log("Wrong: " + wrongWordCount);

            timeElapsed = Date.now() - startTime;
            updateGameState();
        }, 1000);

        return () => clearInterval(interval);
    }, []);

    function resetTimer() {
        startTime = Date.now();
        timeElapsed = 0;
        typedWordCount = 0;
        wrongWordCount = 0;
        setText("");
        setIndex(0);
        setWrong(0);
        setWpm(0);
        setAccuracy(0);
        props.onResetTimer();
    }

    function fetchRandomText() {
        axios
            .get("https://dynotype.onrender.com/getText", {
                params: {
                    sessionId: sessionId,
                },
            })
            .then((response) => {
                setImportedText(response.data);
            })
            .catch((error) => {
                console.error("Text fetch error: ", error);
            });
    }

    function startTimer() {
        startTime = Date.now();
        axios
            .post("https://dynotype.onrender.com/initialize?username=darshan")
            .then((response) => {
                // Retrieve the sessionId from the response and store it in the state
                sessionId = response.data;
                setSessionId(sessionId);
                console.log("Session ID: ", sessionId);
            })
            .catch((error) => {
                console.error("Initialization error: ", error);
            });
        fetchRandomText();
        resetTimer();
        props.onStartTimer();
    }

    function userAnalysis() {
        axios
      .get("https://dynotype.onrender.com/userAnalysis", {
        params: {
          sessionId: sessionId, // Pass sessionId
          timeElapsed: Math.floor(timeElapsed / 1000),
          correct: typedWordCount,
          wrong: wrongWordCount,
        },
      })
      .then((response) => {
        setAccuracy(response.data.accuracy);
        setWpm(response.data.wpm);
      })
      .catch((error) => {
        console.error("Update state error: ", error);
      });
    }

    return (
        <>
            <header className="speedDisplay">Typing Speed: {wpm} WPM --- Accuracy: {accuracy}%</header>
            <div>
                <div className="letterTable">
                    {rows.map((row, index) => {
                        return (
                            <div className="letterRow" key={index}>
                                {row}
                            </div>
                        );
                    })}
                </div>
            </div>

            <button className="btn btn-primary" onClick={startTimer}>
                Start
            </button>
            <button className="btn btn-danger" onClick={resetTimer}>
                Reset
            </button>
        </>
    );
}

export default TextContainer;
