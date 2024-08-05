import { useNavigate } from "react-router-dom";
import { useState } from "react";
import styled from "styled-components";
import Footer from "../components/Footer";

function DiaryAddPage() {
  const navigate = useNavigate();
  const [image, setImage] = useState(null);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  const handleSave = (event) => {
    event.preventDefault();
    const newDiary = {
      image,
      title,
      content,
    };
    // 이 부분에서 데이터 저장 로직을 추가할 수 있습니다.
    console.log("새로운 일기:", newDiary);
    navigate("/diary");
  };

  const handleImageUpload = (event) => {
    const file = event.target.files[0];
    if (file) {
      const imageUrl = URL.createObjectURL(file);
      setImage(imageUrl);
    }
  };

  return (
    <>
      <MainContainer>
        <BackButton onClick={() => navigate("/diary")}>←</BackButton>

        <Title>일기 작성하기</Title>

        <Form>
          <ImageUpload>
            {image ? (
              <PreviewImage src={image} alt="Uploaded Preview" />
            ) : (
              <>
                <UploadIcon>↑</UploadIcon>
                <UploadText>업로드할 이미지 선택</UploadText>
              </>
            )}
            <input type="file" accept="image/*" onChange={handleImageUpload} />
          </ImageUpload>

          <Label>
            제목:
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
          </Label>

          <Label>
            내용:
            <textarea
              value={content}
              onChange={(e) => setContent(e.target.value)}
            />
          </Label>

          <ButtonGroup>
            <Button type="submit" onClick={handleSave}>
              완료
            </Button>
            <Button type="button" onClick={() => navigate("/diary")}>
              취소
            </Button>
          </ButtonGroup>
        </Form>

        <Footer />
      </MainContainer>
    </>
  );
}

export default DiaryAddPage;

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

const BackButton = styled.div`
  font-size: 20px;
  font-weight: bold;
  text-align: left;
  width: 100%;
  padding: 1rem;
  cursor: pointer;
`;

const Title = styled.div`
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 1rem;
`;

const Form = styled.form`
  font-weight: bold;
  display: flex;
  flex-direction: column;
  width: 95%;
  margin-top: 1rem;
  border-radius: 30px;
  box-shadow: 1px 1px #bababa;
`;

const ImageUpload = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #a3c6a2;
  width: 100%;
  height: 300px;
  margin-bottom: 20px;
  position: relative;
  border-top-left-radius: 30px;
  border-top-right-radius: 30px;

  input {
    position: absolute;
    width: 100%;
    height: 100%;
    opacity: 0;
    cursor: pointer;
  }
`;

const PreviewImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-top-right-radius: 30px;
  border-top-left-radius: 30px;
`;

const UploadIcon = styled.div`
  font-size: 50px;
  font-weight: bold;
  color: #fff;
`;

const UploadText = styled.div`
  margin-top: 5px;
  font-style: italic;
  color: #fff;
  font-size: 17px;
`;

const Label = styled.label`
  display: flex;
  flex-direction: column;
  padding: 7px;

  input,
  textarea {
    margin-top: 5px;
    padding: 5px;
    border: 1px solid #bababa;
    border-radius: 5px;
  }
  textarea {
    resize: none;
    height: 150px;
  }
`;

const ButtonGroup = styled.div`
  display: flex;
  justify-content: right;
  margin-top: 1rem;
`;

const Button = styled.button`
  background-color: #a3c6a2;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 10px;
  margin-right: 10px;
  margin-bottom: 15px;
  cursor: pointer;

  &:last-child {
    background-color: #e2e6ea;
  }
`;
