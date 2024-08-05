import { useNavigate } from "react-router-dom";
import styled from "styled-components";

function ChallengeResult() {
  const navigate = useNavigate();

  return (
    <>
      <MainContainer>
        <MainWrapper>
          <img src="/assets/img/result.png" />
          <TextSt>신청이 완료되었습니다.</TextSt>
          <Text>감사합니다.</Text>
        </MainWrapper>
        <BackBtn onClick={() => navigate("/challenge")}>돌아가기</BackBtn>
      </MainContainer>
    </>
  );
}

export default ChallengeResult;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 390px;
  height: 100vh;
  box-sizing: border-box;
  background-color: #fdfdfd;
  margin: 0 auto;
  justify-content: center;
  align-items: center;
`;

const MainWrapper = styled.div`
  margin: 50px;
`;

const TextSt = styled.h4`
  margin-top: 20px;
  font-size: 20px;
  font-weight: bold;
  text-align: center;
`;

const Text = styled.div`
  text-align: center;
  margin-top: 10px;
  font-size: 17px;
`;

const BackBtn = styled.button`
  border: none;
  border-radius: 10px;
  background-color: #025d00;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  width: 90%;
  height: 40px;
  letter-spacing: 3px;
  cursor: pointer;
`;
