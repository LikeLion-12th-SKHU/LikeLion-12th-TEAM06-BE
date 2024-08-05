import styled from "styled-components";
import Header from "../../components/Header";
import { Link } from "react-router-dom";
import Footer from "../../components/Footer";
import ChallengeData from "./ChallengeData";

function Challenge() {
  return (
    <MainContainer>
      <Header />
      <ContentSt>
        {ChallengeData.map((Challenge) => (
          <ChallengeSt key={Challenge.id} to={`/challenge/${Challenge.id}`}>
            <ImgSt src={Challenge.imgSrc} />
            <span>{Challenge.title}</span>
          </ChallengeSt>
        ))}
      </ContentSt>
      <Footer />
    </MainContainer>
  );
}

export default Challenge;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100vh;
  max-width: 390px;
  box-sizing: border-box;
  background-color: #fdfdfd;
  margin: 0 auto;
`;

const ContentSt = styled.div`
  padding-bottom: 83px;
`;

const ChallengeSt = styled(Link)`
  margin: 10px;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 95%;
  padding: 10px;
  border-radius: 10px;
  box-shadow: 1px 1px 1px 1px #bababa;
`;

const ImgSt = styled.img`
  width: 80%;
  margin-bottom: 10px;
  border-radius: 10px;
`;
