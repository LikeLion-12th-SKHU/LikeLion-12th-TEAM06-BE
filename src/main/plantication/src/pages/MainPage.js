import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import Footer from "../components/Footer";
import Header from "../components/Header";
import styled from "styled-components";
import postsData from "../common/api/diaryApi.json";

function Main() {
  const [posts, setPosts] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    // JSON 데이터를 로드합니다.
    setPosts(postsData.posts.slice(0, 4)); // 처음 4개 항목만 가져옵니다.
  }, []);

  const handlePostClick = (id) => {
    navigate(`/diary/${id}`);
  };
  return (
    <>
      <MainContainer>
        <Header />
        <PromoImage src="/assets/img/main 이미지.png" alt="Promo" />
        <ContentSt>
          <TitleSt>"나만의 식물을 찾는 여정"</TitleSt>
          <BtnSt onClick={() => navigate("/recommendation")}>
            나의 식물 찾기🌱 &#62;
          </BtnSt>
        </ContentSt>
        <PlantDiaryContainer>
          <DiaryText>🌱 금주 Top 식물 일기</DiaryText>
          <PlantDiaryGrid>
            {posts.map((post) => (
              <DiaryImageLink
                key={post.id}
                onClick={() => handlePostClick(post.id)}
              >
                <DiaryImage src={post.img} alt={post.title} />
                <LikesContainer>
                  <LikeIcon>❤️</LikeIcon>
                  <span>{post.likes}</span>
                </LikesContainer>
              </DiaryImageLink>
            ))}
          </PlantDiaryGrid>
        </PlantDiaryContainer>
        <ChallengeSt>
          <ImageWrapper>
            <TitleWrapper>
              <Title>작은 손길을 통해</Title>
            </TitleWrapper>
            <ImgSt
              src="/assets/img/challenge.jpeg"
              alt="Challenge"
              onClick={() => navigate("/challenge")}
            />
            <Participants>
              100+ <br />
              participants
            </Participants>
            <Tag>#브래덴코</Tag>
          </ImageWrapper>
          <TextWrapper>
            <Subtitle>일상에 푸르름을 더하는 시간</Subtitle>
          </TextWrapper>
        </ChallengeSt>
        <FooterContainer>
          <p>Created by Likelion SKHU 12th</p>
          <FooterLink href="https://github.com/likelion-skhu/12th">
            GitHub 주소
          </FooterLink>
          <p>📞 010-1234-1234</p>
        </FooterContainer>
        <Footer />
      </MainContainer>
    </>
  );
}

export default Main;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 390px;
  min-height: 100vh;
  box-sizing: border-box;
  background-color: #fdfdfd;
  margin: 0 auto;
  padding-bottom: 83px;
  overflow: auto;
`;

const ContentSt = styled.div`
  background-color: #00300e;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
`;

const TitleSt = styled.h1`
  color: #fff;
  font-weight: bolder;
  font-size: 22px;
  margin-bottom: 8px;
`;

const BtnSt = styled.button`
  padding: 2px 15px;
  border: 1px solid #4cd964;
  border-radius: 5px;
  color: #fff;
  background-color: #00300e;
  font-weight: bold;
  font-size: 15px;
  cursor: pointer;
`;

const ChallengeSt = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  padding: 20px;
  background-color: #003e11;
`;

const ImageWrapper = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
`;

const ImgSt = styled.img`
  cursor: pointer;
  margin-top: 60px;
  width: 80%;
  border-radius: 10px;
`;

const Participants = styled.div`
  text-align: center;
  position: absolute;
  bottom: 10px;
  left: 10px;
  background-color: white;
  padding: 5px 10px;
  border-radius: 5px;
  font-size: 16px;
  font-weight: bold;
`;

const Tag = styled.div`
  text-align: center;
  position: absolute;
  top: 70px;
  right: 5px;
  background-color: white;
  padding: 5px 10px;
  border-radius: 5px;
  width: 90px;
  font-size: 16px;
  font-weight: bold;
`;

const TextWrapper = styled.div`
  text-align: center;
  color: white;
  margin-top: 10px;
`;

const TitleWrapper = styled.div`
  text-align: left;
`;
const Title = styled.div`
  font-size: 18px;
  font-weight: bold;
`;

const Subtitle = styled.div`
  font-size: 14px;
  margin-top: 5px;
`;

const PromoImage = styled.img`
  width: 100%;
  height: auto;
  aspect-ratio: 390 / 289;
  object-fit: cover;
`;

// Diary text
const DiaryText = styled.h2`
  color: black;
  font-size: 20px;
  font-weight: bold;
  margin: 10px;
`;

// Plant diary container
const PlantDiaryContainer = styled.div`
  width: 100%;
  margin-bottom: 20px;
  margin-top: 20px;
`;

const PlantDiaryGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
  padding: 0 20px;
`;

// Plant diary image container with link
const DiaryImageLink = styled.div`
  position: relative;
  width: 100%;
  cursor: pointer;
`;

// Plant diary image
const DiaryImage = styled.img`
  width: 100%;
  height: 150px;
  object-fit: cover;
  border-radius: 10px;
`;

// Likes container
const LikesContainer = styled.div`
  position: absolute;
  bottom: 10px;
  right: 10px;
  color: white;
  background-color: rgba(0, 0, 0, 0.5);
  padding: 5px 10px;
  border-radius: 10px;
  display: flex;
  align-items: center;
`;

// Likes icon
const LikeIcon = styled.span`
  margin-right: 5px;
`;

const FooterLink = styled.a`
  color: #4caf50;
  text-decoration: none;

  &:hover {
    text-decoration: underline;
  }
`;

const FooterContainer = styled.div`
  display: flex;
  flex-direction: column;
  /* align-items: center; */
  padding: 20px;
  background-color: #e0e0e0;
  /* text-align: center; */
  font-size: 14px;
  color: #555;
  padding-bottom: 60px;
`;
