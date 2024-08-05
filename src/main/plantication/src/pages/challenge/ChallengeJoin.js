import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";

function ChallengeJoin() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [selectedImage, setSelectedImage] = useState(null);
  const [url, setUrl] = useState("");
  const [urlError, setUrlError] = useState(false);

  const handleImageUpload = (e) => {
    if (e.target.files && e.target.files[0]) {
      setSelectedImage(URL.createObjectURL(e.target.files[0]));
    }
  };

  const handleJoinClick = () => {
    if (!url.trim()) {
      setUrlError(true);
    } else {
      setUrlError(false);
      navigate(`/challenge/${id}/result`);
    }
  };

  return (
    <>
      <MainContainer>
        <HeaderSt>
          <CancelBtn onClick={() => navigate(`/challenge/${id}`)}>x</CancelBtn>
          <ImgContainer>
            인증사진
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
        <UrlContainer>
          인스타 게시글 주소 🔗{" "}
          <input
            type="text"
            value={url}
            onChange={(e) => setUrl(e.target.value)}
            className={urlError ? "error" : ""}
          />
          {urlError && <ErrorText>주소를 입력해주세요.</ErrorText>}
        </UrlContainer>
        <JoinBtn onClick={handleJoinClick}>신청하기</JoinBtn>
      </MainContainer>
    </>
  );
}

export default ChallengeJoin;

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

const CancelBtn = styled.div`
  position: absolute;
  top: 10px;
  right: 10px;
  cursor: pointer;
  font-size: 25px;
  font-weight: bold;
`;

const ImgContainer = styled.div`
  width: 100%;
  font-weight: bold;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 20px;
  margin-top: 50px;
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

const UrlContainer = styled.div`
  width: 90%;
  margin-bottom: 300px;
  font-weight: bold;

  input {
    margin-top: 10px;
    width: 100%;
    height: 39px;
    border: 1px solid #025d00;
    border-radius: 10px;
  }
`;

const ErrorText = styled.div`
  color: red;
  font-size: 14px;
  margin-top: 5px;
`;

const JoinBtn = styled.button`
  border: none;
  border-radius: 10px;
  background-color: #025d00;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  width: 90%;
  height: 40px;
  cursor: pointer;
`;
