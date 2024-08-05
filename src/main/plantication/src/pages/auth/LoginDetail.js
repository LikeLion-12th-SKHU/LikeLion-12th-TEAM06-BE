import { useState } from "react";
import styled from "styled-components";
import axios from "axios";

function LoginDetail() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [usernameError, setUsernameError] = useState("");
  const [passwordError, setPasswordError] = useState("");

  const handleUsernameChange = (event) => {
    const { value } = event.target;
    setUsername(value);

    if (value.trim() === "") {
      setUsernameError("아이디를 입력해주세요.");
    } else {
      setUsernameError("");
    }
  };

  const handlePasswordChange = (event) => {
    const { value } = event.target;
    setPassword(value);

    if (value.length < 8 || value.length > 16) {
      setPasswordError("비밀번호는 8자리에서 16자리여야 합니다.");
    } else {
      setPasswordError("");
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const userData = {
      username,
      password,
    };

    try {
      const response = await axios.post("http://localhost:3000/auth", userData);
      console.log(response.data); // 성공 시 처리
    } catch (error) {
      console.error(error); // 에러 시 처리
    }
  };

  return (
    <>
      <MainContainer>
        <Header>로그인</Header>
        <Form onSubmit={handleSubmit}>
          <Label>
            아이디
            <Input
              type="text"
              value={username}
              onChange={handleUsernameChange}
            />
            {usernameError && <Error>{usernameError}</Error>}
          </Label>
          <Label>
            비밀번호
            <Input
              type="password"
              value={password}
              onChange={handlePasswordChange}
            />
            {passwordError && <Error>{passwordError}</Error>}
          </Label>
          <Button type="submit">로그인</Button>
        </Form>
      </MainContainer>
    </>
  );
}

export default LoginDetail;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  max-width: 390px;
  height: 100vh;
  box-sizing: border-box;
  background-color: #fdfdfd;
  margin: 0 auto;
`;

const Header = styled.h1`
  font-size: 18px;
  text-align: center;
  margin-bottom: 30px;
`;

const Form = styled.form`
  width: 95%;
`;

const Label = styled.label`
  display: block;
  font-size: 14px;
  margin-bottom: 6px;
`;

const Input = styled.input`
  width: 95%;
  padding: 10px;
  margin-top: 5px;
  border: 1px solid #ccc;
  box-sizing: border-box;
`;

const Button = styled.button`
  border: 1px solid #bababa;
  background-color: #0a5308;
  color: #fff;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  width: 100%;
  padding: 10px;
  margin-top: 20px;
`;

const Error = styled.div`
  color: red;
  font-size: 12px;
  margin-top: 5px;
  margin-bottom: 4px;
`;
