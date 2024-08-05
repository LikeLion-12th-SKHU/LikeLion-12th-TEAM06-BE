import { useEffect, useState } from "react";
import Footer from "../../components/Footer";
import Header from "../../components/Header";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";

function MyGroup() {
  const navigate = useNavigate();
  const [joinedGroups, setJoinedGroups] = useState([]);

  useEffect(() => {
    const storedGroups = JSON.parse(localStorage.getItem("joinedGroups")) || [];
    setJoinedGroups(storedGroups);
  }, []);

  const handleGroupClick = (index) => {
    navigate(`/cooperate/${index}/chat`);
  };

  return (
    <MainContainer>
      <Header />
      <BackButton onClick={() => navigate("/cooperate")}>←</BackButton>

      <Title>나의 그룹</Title>
      <Container>
        {joinedGroups.length > 0 ? (
          joinedGroups.map((group, index) => (
            <GroupDetails key={index} onClick={() => handleGroupClick(index)}>
              {group.image && <GroupImage src={group.image} alt="Group" />}
              <GroupText>
                <DetailTitle>{group.title}</DetailTitle>
                <DetailNumber> / {group.number}</DetailNumber>
                <DetailHashtag>#{group.hashtag}</DetailHashtag>
                <DetailContent>{group.content}</DetailContent>
              </GroupText>
            </GroupDetails>
          ))
        ) : (
          <NoData>가입한 그룹이 없습니다.</NoData>
        )}
      </Container>
      <Footer />
    </MainContainer>
  );
}

export default MyGroup;

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

const Container = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 1rem;
  padding-bottom: 83px;
`;

const Title = styled.h1`
  font-size: 24px;
  margin: 10px 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  position: relative;
`;

const BackButton = styled.div`
  font-size: 20px;
  font-weight: bold;
  text-align: left;
  width: 100%;
  padding: 1rem;
  cursor: pointer;
`;

const GroupDetails = styled.div`
  display: flex;
  width: 100%;
  border-radius: 10px;
  padding: 1rem;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);
  background-color: white;
  margin-bottom: 1rem;
  height: 140px;
`;

const GroupImage = styled.img`
  width: 40%;
  height: auto;
  border-radius: 10px;
  margin-right: 1rem;
`;

const GroupText = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

const DetailTitle = styled.h2`
  font-size: 18px;
  margin: 0;
`;

const DetailNumber = styled.div`
  font-size: 14px;
  color: #777;
`;

const DetailHashtag = styled.div`
  font-size: 14px;
  color: #777;
`;

const DetailContent = styled.div`
  font-size: 14px;
  color: #777;
`;

const NoData = styled.div`
  font-size: 1.2rem;
  color: #ff0000;
`;
