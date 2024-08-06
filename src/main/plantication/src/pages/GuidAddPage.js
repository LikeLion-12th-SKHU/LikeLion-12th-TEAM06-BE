// src/pages/GuidAddPage.js
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Footer from "../components/Footer";
import { createPlant } from "../api"; // createPlant 함수 import

function GuidAddPage() {
  const navigate = useNavigate();
  const [selectedImage, setSelectedImage] = useState(null);
  const [nickname, setNickname] = useState("");
  const [shortDescription, setShortDescription] = useState("");
  const [description, setDescription] = useState("");
  const [urlError, setUrlError] = useState(false);

  const handleImageUpload = (e) => {
    if (e.target.files && e.target.files[0]) {
      setSelectedImage(URL.createObjectURL(e.target.files[0]));
    }
  };

  const handleSaveClick = async () => {
    if (
      !nickname.trim() ||
      !shortDescription.trim() ||
      !description.trim() ||
      !selectedImage
    ) {
      setUrlError(true);
    } else {
      setUrlError(false);
      const newPlant = {
        nickname,
        img: selectedImage,
        shortDescription,
        description,
      };

      try {
        await createPlant(newPlant); // 서버에 새로운 식물 정보 전송
        navigate("/guide"); // Navigate back to the guide page
      } catch (error) {
        console.error("Failed to save new plant data:", error);
      }
    }
  };

  return (
    <MainContainer>
      <HeaderSt>
        <Title>식물 정보</Title>
        <CancelBtn onClick={() => navigate("/guide")}>←</CancelBtn>
        <ImgContainer>
          식물사진:
          <ImageUpload>
            {selectedImage ? (
              <ImagePreview src={selectedImage} alt="Preview" />
            ) : (
              <div>사진첨부</div>
            )}
            <input type="file" onChange={handleImageUpload} />
          </ImageUpload>
        </ImgContainer>
      </HeaderSt>
      <FormContainer>
        <FormGroup>
          <Label htmlFor="nickname">식물명:</Label>
          <Input
            type="text"
            id="nickname"
            value={nickname}
            onChange={(e) => setNickname(e.target.value)}
            className={urlError && !nickname ? "error" : ""}
          />
        </FormGroup>
        <FormGroup>
          <Label htmlFor="shortDescription">한 줄 소개:</Label>
          <Input
            type="text"
            id="shortDescription"
            value={shortDescription}
            onChange={(e) => setShortDescription(e.target.value)}
            className={urlError && !shortDescription ? "error" : ""}
          />
        </FormGroup>
        <FormGroup>
          <Label htmlFor="description">설명:</Label>
          <Textarea
            id="description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            className={urlError && !description ? "error" : ""}
          />
        </FormGroup>
        {urlError && <ErrorText>모든 필드를 입력해주세요.</ErrorText>}
        <SaveBtn onClick={handleSaveClick}>저장</SaveBtn>
      </FormContainer>
      <Footer />
    </MainContainer>
  );
}

export default GuidAddPage;

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

const HeaderSt = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;
  position: relative;
`;

const Title = styled.h1`
  font-size: 24px;
  font-weight: bold;
  margin: 20px 0;
  text-align: center;
  width: 100%;
`;

const CancelBtn = styled.div`
  position: absolute;
  top: 20px;
  left: 10px;
  cursor: pointer;
  font-size: 30px;
  font-weight: bold;
`;

const ImgContainer = styled.div`
  width: 100%;
  font-weight: bold;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 20px;
  margin-top: 30px;
  padding-left: 20px;
`;

const ImageUpload = styled.div`
  margin: 10px 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #025d00;
  width: 80px;
  height: 80px;
  border-radius: 10px;
  color: #fff;
  font-weight: bold;
  position: relative;

  input {
    position: absolute;
    opacity: 0;
    cursor: pointer;
    width: 80px;
    height: 80px;
  }
`;

const ImagePreview = styled.img`
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 5px;
`;

const FormContainer = styled.div`
  width: 90%;
  margin-top: 10px;
  font-weight: bold;
`;

const FormGroup = styled.div`
  margin-bottom: 1rem;
`;

const Label = styled.label`
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
`;

const Input = styled.input`
  width: 100%;
  padding: 0.5rem;
  font-size: 16px;
  border: 1px solid #025d00;
  border-radius: 10px;
`;

const Textarea = styled.textarea`
  width: 100%;
  padding: 0.5rem;
  font-size: 16px;
  border: 1px solid #025d00;
  border-radius: 10px;
  height: 235px;
  resize: none;
`;

const ErrorText = styled.div`
  color: red;
  font-size: 14px;
  margin-top: 5px;
`;

const SaveBtn = styled.button`
  border: none;
  border-radius: 10px;
  background-color: #025d00;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  width: 25%;
  height: 40px;
  cursor: pointer;
  margin-top: 20px;
`;
