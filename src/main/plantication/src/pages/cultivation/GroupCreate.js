import { useNavigate } from "react-router-dom";
import { useState } from "react";
import styled from "styled-components";
import Footer from "../../components/Footer";

function GroupCreate() {
  const navigate = useNavigate();
  const [image, setImage] = useState(null);
  const [title, setTitle] = useState("");
  const [number, setNumber] = useState("");
  const [hashtag, setHashtag] = useState("");
  const [content, setContent] = useState("");
  const [publicStatus, setPublicStatus] = useState("yes");

  const handleSave = (event) => {
    event.preventDefault();
    const newEntry = {
      image,
      title,
      number,
      hashtag,
      content,
      publicStatus,
      isJoined: false,
    };
    const storedGroups = JSON.parse(localStorage.getItem("groups")) || [];
    storedGroups.push(newEntry);
    localStorage.setItem("groups", JSON.stringify(storedGroups));
    navigate("/cooperate");
  };

  const handelImageUpload = (event) => {
    const file = event.target.files[0];
    if (file) {
      const imageUrl = URL.createObjectURL(file);
      setImage(imageUrl);
    }
  };

  return (
    <>
      <MainContainer>
        <BackButton onClick={() => navigate("/cooperate")}>←</BackButton>

        <Title>그룹 생성하기</Title>

        <Form>
          <ImageUpload>
            {image ? (
              <PreviewImage src={image} alt="Uploaded" />
            ) : (
              <>
                <UploadIcon>↑</UploadIcon>
                <UploadText>upload image</UploadText>
              </>
            )}
            <input type="file" accept="image/*" onChange={handelImageUpload} />
          </ImageUpload>
          <Label>
            그룹명:{" "}
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
          </Label>
          <RadioLabel>
            공개여부:
            <RadioGroup>
              예{" "}
              <input
                type="radio"
                name="public"
                value="yes"
                checked={publicStatus === "yes"}
                onChange={() => setPublicStatus("yes")}
              />
              아니오{" "}
              <input
                type="radio"
                name="public"
                value="no"
                checked={publicStatus === "no"}
                onChange={() => setPublicStatus("no")}
              />
            </RadioGroup>
          </RadioLabel>
          <Label>
            인원수 설정:{" "}
            <input
              type="number"
              min={1}
              max={100}
              defaultValue={1}
              value={number}
              onChange={(e) => setNumber(e.target.value)}
            />
          </Label>
          <Label>
            해시태그 추가:{" "}
            <input
              type="text"
              value={hashtag}
              onChange={(e) => setHashtag(e.target.value)}
            />
          </Label>
          <Label>
            메인 소개글:{" "}
            <textarea
              value={content}
              onChange={(e) => setContent(e.target.value)}
            />
          </Label>
          <ButtonGroup>
            <Button type="submit" onClick={handleSave}>
              완료
            </Button>
            <Button type="reset">취소</Button>
          </ButtonGroup>
        </Form>
        <Footer />
      </MainContainer>
    </>
  );
}

export default GroupCreate;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 390px;
  height: 100%;
  box-sizing: border-box;
  background-color: #fdfdfd;
  margin: 0 auto;
  align-items: center;
  padding-bottom: 100px;
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
`;

const Form = styled.div`
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

const RadioLabel = styled.div`
  display: flex;
  flex-direction: row;
  margin-bottom: 12px;
  padding: 7px;

  input {
    margin: 0 5px;
  }
`;

const RadioGroup = styled.div`
  align-items: center;
  margin-left: 10px;

  input {
    margin-left: 5px;
    margin-right: 15px;
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
    height: 100px;
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
`;
