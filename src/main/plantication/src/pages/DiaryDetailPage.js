import React, { useState } from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import Footer from "../components/Footer";
import postsData from "../common/api/diaryApi.json";
import { useNavigate } from "react-router-dom";

function DiaryDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const post = postsData.posts.find((post) => post.id === id);

  const [comments, setComments] = useState(post?.commentsList || []);
  const [newComment, setNewComment] = useState("");

  if (!post) {
    return <div>Post not found</div>;
  }

  const handleCommentChange = (e) => {
    setNewComment(e.target.value);
  };

  const handleCommentSubmit = (e) => {
    e.preventDefault();
    if (newComment.trim()) {
      setComments([...comments, newComment.trim()]);
      setNewComment("");
    }
  };

  return (
    <DetailContainer>
      <CancelBtn onClick={() => navigate("/diary")}>←</CancelBtn>
      <PostCard>
        <Image src={post.img} alt={post.user} />
        <PostContent>
          <Title>{post.title}</Title>
          <Content>{post.content}</Content>
          <Metrics>
            <Likes>❤️ {post.likes}</Likes>
            <Comments>💬 {comments.length}</Comments>
          </Metrics>
          <Date>{post.date}</Date>
        </PostContent>
      </PostCard>
      <CommentSection>
        <CommentList>
          {comments.map((comment, index) => (
            <CommentItem key={index}>{comment}</CommentItem>
          ))}
        </CommentList>
        <CommentForm onSubmit={handleCommentSubmit}>
          <CommentInput
            type="text"
            value={newComment}
            onChange={handleCommentChange}
            placeholder="Write a comment..."
          />
          <SubmitButton type="submit">Submit</SubmitButton>
        </CommentForm>
      </CommentSection>
      <Footer />
    </DetailContainer>
  );
}

export default DiaryDetailPage;

const DetailContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 390px;
  margin: 0 auto;
  padding: 20px;
  box-sizing: border-box;
  background-color: #fdfdfd;
  align-items: center;
  height: 100vh;
  position: relative;
`;

const PostCard = styled.div`
  display: flex;
  flex-direction: column;
  border: 1px solid #ccc;
  border-radius: 10px;
  overflow: hidden;
  background-color: #fff;
  margin-top: 60px;
`;

const Image = styled.img`
  width: 100%;
  height: auto;
  object-fit: cover;
`;

const PostContent = styled.div`
  padding: 10px;
`;

const Title = styled.h1`
  font-size: 24px;
  margin: 0;
  margin-bottom: 10px;
`;

const Content = styled.p`
  font-size: 16px;
  margin: 0;
  margin-bottom: 20px;
`;

const Metrics = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
`;

const Date = styled.p`
  color: #888;
  margin: 0;
`;

const Likes = styled.div`
  color: red;
`;

const Comments = styled.div`
  color: blue;
`;

const CancelBtn = styled.div`
  position: absolute;
  top: 20px;
  left: 20px;
  cursor: pointer;
  font-size: 30px;
  font-weight: bold;
  color: #000;
`;

const CommentSection = styled.div`
  width: 100%;
  max-width: 100%;
  margin-top: 20px;
`;

const CommentList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

const CommentItem = styled.div`
  border-bottom: 1px solid #ddd;
  padding: 10px 0;
`;

const CommentForm = styled.form`
  display: flex;
  flex-direction: column;
  margin-top: 20px;
`;

const CommentInput = styled.input`
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-bottom: 10px;
`;

const SubmitButton = styled.button`
  padding: 10px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  cursor: pointer;

  &:hover {
    background-color: #0056b3;
  }
`;
