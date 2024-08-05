import styled from "styled-components";
import { useNavigate } from "react-router-dom";

function PlantTest() {
  const navigate = useNavigate();

  return (
    <>
      <MainContainer>
        <CoverStyle>
          <BackStyle onClick={() => navigate("/")}>←</BackStyle>

          <TitleSt>
            당신의 현재 감정상태에 <br />
            알맞는 식물을 찾아보세요! <br />
            Plant Test
          </TitleSt>

          <TestBt onClick={() => navigate("/recommendation/question/1")}>
            나의 맞춤 식물 찾기
          </TestBt>
        </CoverStyle>
      </MainContainer>
    </>
  );
}

export default PlantTest;

export const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 390px;
  height: 100vh;
  box-sizing: border-box;
  background-color: #fdfdfd;
  margin: 0 auto;
`;

const CoverStyle = styled.div`
  display: flex;
  flex-direction: column;
  background-image: url("https://wrtn-image-user-output.s3.ap-northeast-2.amazonaws.com/669b08acedeeaa24165f84ee/c5d9a58c-71df-44fa-95a7-7c7d25103439.png");
  height: 100vh;
  background-size: cover;
  background-position: center;
  color: white;
  position: relative;

  font-size: 25px;

  &::before {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
    background-color: rgb(0, 0, 0, 0.6);
    z-index: 1;
  }
`;

const TitleSt = styled.div`
  text-align: left;
  top: 100px;
  left: 20px;
  position: absolute;
  z-index: 2;
  font-size: 27px;
  font-weight: bolder;
`;

const BackStyle = styled.div`
  cursor: pointer;
  color: white;
  position: absolute;
  top: 20px;
  left: 20px;
  z-index: 2;
`;

const TestBt = styled.button`
  position: absolute;
  bottom: 20px;
  right: 20px;
  z-index: 2;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  font-weight: bold;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  background-color: #fff;
`;
