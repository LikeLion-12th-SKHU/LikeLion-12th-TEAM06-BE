import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import styled from "styled-components";
import { FaArrowLeft } from "react-icons/fa";
import { BsSend } from "react-icons/bs";
import { FaBook } from "react-icons/fa";
import postsData from "../../common/api/diaryApi.json";

function GroupChat() {
  const { groupId } = useParams();
  const navigate = useNavigate();
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const [group, setGroup] = useState("");
  const [showDiaryModal, setShowDiaryModal] = useState(false);
  const [diaries, setDiaries] = useState([]);

  useEffect(() => {
    const storedGroups = JSON.parse(localStorage.getItem("joinedGroups")) || [];
    const currentGroup = storedGroups.find(
      (group) => group.groupId === groupId
    );
    setGroup(currentGroup);
  }, [groupId]);

  useEffect(() => {
    setDiaries(postsData.posts);
  }, []);

  const handleSendMessage = () => {
    if (newMessage.trim() === "") return;
    setMessages([
      ...messages,
      { text: newMessage, sender: "You", type: "text" },
    ]);
    setNewMessage("");
  };

  const handleSendDiary = (diary) => {
    setMessages([
      ...messages,
      { text: diary.content, sender: diary.user, type: "text" },
    ]);
    setShowDiaryModal(false);
  };

  return (
    <MainContainer>
      <ChatHeader>
        <BackButton onClick={() => navigate(-1)}>
          <FaArrowLeft />
        </BackButton>
        {group && (
          <>
            <HeaderText>{group.title}</HeaderText>
            <Participants>{group.people}</Participants>
          </>
        )}
      </ChatHeader>
      <MessagesContainer>
        {messages.map((message, index) => (
          <Message key={index}>
            <UserAvatar
              src="https://via.placeholder.com/40"
              alt="User Avatar"
            />
            <MessageContent>
              <MessageSender>{message.sender}</MessageSender>
              {message.type === "text" ? (
                <MessageText>{message.text}</MessageText>
              ) : (
                <MessageImage src={message.text} alt="Message Image" />
              )}
            </MessageContent>
          </Message>
        ))}
      </MessagesContainer>
      <MessageInputContainer>
        <SendButton onClick={() => setShowDiaryModal(true)}>
          <FaBook />
        </SendButton>
        <MessageInput
          type="text"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          placeholder="메시지를 입력하세요"
        />
        <SendButton onClick={handleSendMessage}>
          <BsSend />
        </SendButton>
      </MessageInputContainer>
      {showDiaryModal && (
        <DiaryModal>
          <DiaryModalContent>
            <ModalHeader>
              <ModalTitle>일기 선택</ModalTitle>
              <CloseButton onClick={() => setShowDiaryModal(false)}>
                X
              </CloseButton>
            </ModalHeader>
            <DiaryList>
              {diaries.map((diary) => (
                <DiaryItem
                  key={diary.id}
                  onClick={() => handleSendDiary(diary)}
                >
                  <DiaryTitle>{diary.user}</DiaryTitle>
                  <DiaryDate>{diary.date}</DiaryDate>
                </DiaryItem>
              ))}
            </DiaryList>
          </DiaryModalContent>
        </DiaryModal>
      )}
    </MainContainer>
  );
}

export default GroupChat;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 390px;
  height: 100vh;
  box-sizing: border-box;
  background-color: #fdfdfd;
  margin: 0 auto;
`;

const ChatHeader = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1rem;
  background-color: #4caf50;
  color: white;
`;

const BackButton = styled.button`
  background: none;
  border: none;
  color: white;
  font-size: 1.5rem;
  cursor: pointer;
`;

const HeaderText = styled.h1`
  font-size: 18px;
  margin: 0;
`;

const Participants = styled.div`
  font-size: 14px;
`;

const MessagesContainer = styled.div`
  flex: 1;
  padding: 1rem;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
`;

const Message = styled.div`
  display: flex;
  margin-bottom: 1rem;
`;

const UserAvatar = styled.img`
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 1rem;
`;

const MessageContent = styled.div`
  display: flex;
  flex-direction: column;
`;

const MessageSender = styled.div`
  font-weight: bold;
  margin-bottom: 0.2rem;
`;

const MessageText = styled.div`
  background-color: #f1f1f1;
  padding: 0.5rem;
  border-radius: 5px;
`;

const MessageImage = styled.img`
  width: 200px;
  border-radius: 10px;
`;

const MessageInputContainer = styled.div`
  display: flex;
  padding: 1rem;
  background-color: #f1f1f1;
`;

const MessageInput = styled.input`
  flex: 1;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-right: 0.5rem;
`;

const SendButton = styled.button`
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 5px;
  background-color: #4caf50;
  color: white;
  cursor: pointer;
  font-size: 1.5rem;

  &:hover {
    background-color: #45a049;
  }
`;

const DiaryModal = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 1000;
`;

const DiaryModalContent = styled.div`
  background-color: white;
  padding: 1rem;
  border-radius: 10px;
  width: 80%;
  max-width: 500px;
`;

const ModalHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const ModalTitle = styled.h2`
  margin: 0;
`;

const CloseButton = styled.button`
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
`;

const DiaryList = styled.div`
  margin-top: 1rem;
`;

const DiaryItem = styled.div`
  padding: 0.5rem;
  border-bottom: 1px solid #ddd;
  cursor: pointer;

  &:hover {
    background-color: #f1f1f1;
  }
`;

const DiaryTitle = styled.div`
  font-weight: bold;
`;

const DiaryDate = styled.div`
  color: #888;
`;
