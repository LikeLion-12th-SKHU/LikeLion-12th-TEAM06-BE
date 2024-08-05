import React, { useState, useEffect } from "react";
import styled from "styled-components";
import Header from "../components/Header";
import Footer from "../components/Footer";
import postsData from "../common/api/diaryApi.json";
import { useNavigate } from "react-router-dom";

function DiaryPage() {
  const [posts, setPosts] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    // JSON 데이터를 로드합니다.
    setPosts(postsData.posts);
  }, []);

  const handlePostClick = (id) => {
    navigate(`/diary/${id}`);
  };

  return (
    <MainContainer>
      <Header />
      <Title>Diary Page</Title>
      <PostsContainer>
        {posts.map((post) => (
          <PostCard key={post.id} onClick={() => handlePostClick(post.id)}>
            <Image src={post.img} alt={post.user} />
            <Details>
              <User>{post.user}</User>
              <Date>{post.date}</Date>
              <Metrics>
                <Likes>❤️ {post.likes}</Likes>
                <Comments>💬 {post.comments}</Comments>
              </Metrics>
            </Details>
          </PostCard>
        ))}
      </PostsContainer>
      <AddBtn onClick={() => navigate("/diary/add")}> + </AddBtn>
      <Footer />
    </MainContainer>
  );
}

export default DiaryPage;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 390px;
  height: 100vh;
  box-sizing: border-box;
  background-color: #fdfdfd;
  margin: 0 auto;
  align-items: center;
`;

const Title = styled.h1`
  text-align: center;
  margin-bottom: 20px;
  margin-top: 45px;
`;

const PostsContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
`;

const PostCard = styled.div`
  display: flex;
  flex-direction: column;
  border: 1px solid #ccc;
  border-radius: 10px;
  overflow: hidden;
  background-color: #fff;
  cursor: pointer; // 커서 포인터 추가
`;

const Image = styled.img`
  width: 100%;
  height: auto;
  object-fit: cover;
`;

const Details = styled.div`
  padding: 10px;
`;

const User = styled.div`
  font-weight: bold;
`;

const Date = styled.div`
  color: #888;
  margin-bottom: 10px;
`;

const Metrics = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Likes = styled.div`
  color: red;
`;

const Comments = styled.div`
  color: blue;
`;

const AddBtn = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  position: sticky;
  bottom: 100px;
  align-self: flex-end;
  border: none;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  font-size: 30px;
  background-color: rgba(2, 93, 0, 0.6);
  color: white;
  cursor: pointer;
  margin-right: 15px;
`;
