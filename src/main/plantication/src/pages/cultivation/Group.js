import { useEffect, useState } from "react";
import Footer from "../../components/Footer";
import Header from "../../components/Header";
import { useNavigate, useLocation } from "react-router-dom";
import styled from "styled-components";

function Group() {
  const navigate = useNavigate();
  const location = useLocation();
  const [showPopup, setShowPopup] = useState(false);
  const [groups, setGroups] = useState([]);
  const [selectedGroupIndex, setSelectedGroupIndex] = useState(null);
  const [tooltipIndex, setTooltipIndex] = useState(null);

  useEffect(() => {
    const storedGroups = JSON.parse(localStorage.getItem("groups")) || [];
    setGroups(storedGroups);
  }, []);

  const handleJoinClick = (index) => {
    setSelectedGroupIndex(index);
    setShowPopup(true);
  };

  const handlePopup = (isConfirmed) => {
    if (isConfirmed && selectedGroupIndex !== null) {
      const updatedGroups = groups.map((group, i) =>
        i === selectedGroupIndex ? { ...group, isJoined: true } : group
      );
      setGroups(updatedGroups);

      const joinedGroups =
        JSON.parse(localStorage.getItem("joinedGroups")) || [];
      joinedGroups.push(updatedGroups[selectedGroupIndex]);
      localStorage.setItem("joinedGroups", JSON.stringify(joinedGroups));

      alert("가입되었습니다.");
    } else {
      alert("신청 취소하였습니다.");
    }
    setShowPopup(false);
    setSelectedGroupIndex(null);
  };

  return (
    <>
      <MainContainer>
        <Header />
        <Title>공동 재배</Title>
        <ChatIcon
          src="./assets/img/chat.png"
          onClick={() => navigate("/cooperate/my")}
        />

        <Container>
          {groups.length > 0 ? (
            groups.map((group, index) => (
              <GroupDetails key={index}>
                {group.image && <GroupImage src={group.image} alt="Group" />}
                <GroupText>
                  <DetailTitle
                    onMouseEnter={() => setTooltipIndex(index)}
                    onMouseLeave={() => setTooltipIndex(null)}
                  >
                    {group.title}
                  </DetailTitle>
                  <DetailNumber> / {group.number}</DetailNumber>
                  <DetailHashtag>#{group.hashtag}</DetailHashtag>
                  {tooltipIndex === index && <Tooltip>{group.content}</Tooltip>}
                  <JoinBtn
                    onClick={() => handleJoinClick(index)}
                    disabled={group.isJoined}
                  >
                    {group.isJoined ? "가입 완료" : "참가 신청"}
                  </JoinBtn>
                </GroupText>
              </GroupDetails>
            ))
          ) : (
            <NoData>그룹을 추가해주세요.</NoData>
          )}
        </Container>

        <AddBtn
          onClick={() => {
            navigate("/cooperate-create");
          }}
        >
          {" "}
          +
        </AddBtn>

        {showPopup && (
          <Popup>
            <PopupContent>
              <PopupTitle>신청하기</PopupTitle>
              <PopupText>그룹에 참가하시겠습니까?</PopupText>
              <PopupButton onClick={() => handlePopup(true)}>신청</PopupButton>
              <PopupButton onClick={() => handlePopup(false)}>취소</PopupButton>
            </PopupContent>
          </Popup>
        )}
        <Footer />
      </MainContainer>
    </>
  );
}

export default Group;

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
  position: relative;
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
  margin: 1rem 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  position: relative;
`;

const ChatIcon = styled.img`
  position: absolute;
  right: 15px;
  top: 60px;
  width: 25px;
  height: auto;
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

const JoinBtn = styled.button`
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 5px;
  background-color: #0a5308;
  color: white;
  cursor: pointer;
  font-size: 14px;
`;

const NoData = styled.div`
  font-size: 1.2rem;
  color: #ff0000;
`;

const AddBtn = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  z-index: 2;
  bottom: 100px;
  right: 15px;
  position: absolute;
  border: none;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  font-size: 30px;

  background-color: rgba(2, 93, 0, 0.6);
  color: white;
  cursor: pointer;
`;

const Popup = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;

  background-color: rgba(0, 0, 0, 0.5);
  z-index: 4;
`;

const PopupContent = styled.div`
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
  width: 300px;
`;

const PopupTitle = styled.h2`
  font-size: 18px;
  margin-bottom: 10px;
  color: #025d00;
`;

const PopupText = styled.p`
  font-size: 16px;
  margin-bottom: 20px;
`;

const PopupButton = styled.button`
  padding: 10px 20px;
  margin: 0 5px;
  border: none;
  border-radius: 5px;
  background-color: #025d00;
  color: #fff;
  cursor: pointer;
  font-size: 14px;

  &:hover {
    background-color: #45a049;
  }
`;

const Tooltip = styled.div`
  position: absolute;
  background-color: #000;
  color: #fff;
  padding: 0.5rem;
  border-radius: 5px;
  width: 200px;
  font-size: 14px;
  z-index: 10;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
`;
