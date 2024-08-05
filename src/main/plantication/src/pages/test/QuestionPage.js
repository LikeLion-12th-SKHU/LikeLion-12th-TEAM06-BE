import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import Questions from "../../common/api/questionsApi.json";
import styled from "styled-components";

function QuestionPage() {
  const { questionNumber } = useParams();
  const navigate = useNavigate();
  const questionIndex = parseInt(questionNumber) - 1;
  const [selectedAnswers, setSelectedAnswers] = useState({});
  const [isLastQuestion, setIsLastQuestion] = useState(false);
  const TOTAL_QUESTIONS = Questions.length;

  useEffect(() => {
    if (questionIndex < TOTAL_QUESTIONS && questionIndex >= 0) {
      const question = Questions[questionIndex];
      if (!selectedAnswers[question.option]) {
        setSelectedAnswers((prevAnswers) => ({
          ...prevAnswers,
          [question.option]: [],
        }));
      }
    }
  }, [questionIndex, TOTAL_QUESTIONS]);

  useEffect(() => {
    if (isLastQuestion) {
      handleFinalSubmission();
    }
  }, [selectedAnswers, isLastQuestion]);

  const handleAnswerClick = (answerType) => {
    setSelectedAnswers((prevAnswers) => ({
      ...prevAnswers,
      [Questions[questionIndex].option]: [
        ...prevAnswers[Questions[questionIndex].option],
        answerType,
      ],
    }));

    if (questionIndex < TOTAL_QUESTIONS - 1) {
      navigate(`/recommendation/question/${questionIndex + 2}`);
    } else {
      setIsLastQuestion(true);
    }
  };

  const handleFinalSubmission = () => {
    const countTypes = (types) => {
      const countMap = {};
      types.forEach((type) => {
        countMap[type] = (countMap[type] || 0) + 1;
      });
      return countMap;
    };

    const selectedTypes = {
      C: selectedAnswers["CD"]
        ? countTypes(selectedAnswers["CD"])["C"] >= 2
          ? "C"
          : "D"
        : "D",
      L: selectedAnswers["LG"]?.includes("L") ? "L" : "G",
      S: selectedAnswers["SB"]?.includes("S") ? "S" : "B",
      E: selectedAnswers["EO"]?.includes("E") ? "E" : "O",
    };

    const result = Object.values(selectedTypes).join("");
    navigate(`/recommendation/result/${result}`);
  };

  return (
    <>
      <MainContainer>
        <QuestionContainer>
          <QuestionHeader>
            <QuestionCounter>
              {questionNumber} / {TOTAL_QUESTIONS}
            </QuestionCounter>
          </QuestionHeader>
          <QuestionTitle>{Questions[questionIndex].question}</QuestionTitle>

          <Img src={Questions[questionIndex].img} />

          <AnswerContainer>
            <AnswerButton
              onClick={() =>
                handleAnswerClick(Questions[questionIndex].answers[0].type)
              }
            >
              {Questions[questionIndex].answers[0].content}
            </AnswerButton>
            <AnswerButton
              onClick={() =>
                handleAnswerClick(Questions[questionIndex].answers[1].type)
              }
            >
              {Questions[questionIndex].answers[1].content}
            </AnswerButton>
          </AnswerContainer>
        </QuestionContainer>
      </MainContainer>
    </>
  );
}

export default QuestionPage;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  max-width: 390px;
  height: 100vh;
  box-sizing: border-box;
  background-color: #fdfdfd;
  margin: 0 auto;
`;

const QuestionContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
`;

const QuestionHeader = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  padding: 20px;
`;

const QuestionCounter = styled.div`
  font-size: 17px;
  font-weight: bold;
`;

const QuestionTitle = styled.h1`
  font-weight: 600;
  font-size: 20px;
  padding: 15px;
  text-align: center;
  margin-bottom: 20px;
`;

const Img = styled.img`
  border-radius: 100%;
  width: 220px;
  margin-bottom: 30px;
  box-shadow: -1px -1px 2px 1px #025d00;
`;
const AnswerContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
`;

const AnswerButton = styled.button`
  font-weight: 500;
  height: 55px;
  width: 70%;
  padding: 5px;
  margin: 7px 0;
  font-size: 18px;
  cursor: pointer;
  border: 1px solid #025d00;
  border-radius: 10px;
  background-color: #fff;

  &:hover {
    box-shadow: -2px -2px #bababa;
    color: #fff;
    background-color: #025d00;
  }
`;
