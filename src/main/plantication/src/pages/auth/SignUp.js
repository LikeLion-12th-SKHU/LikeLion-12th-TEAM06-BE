import { useState, useEffect } from "react";
import styled from "styled-components";
import axios from "axios";

function SignUp() {
  const [password, setPassword] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [email, setEmail] = useState("");
  const [emailError, setEmailError] = useState("");
  const [inputValue, setInputValue] = useState("");
  const [name, setName] = useState("");
  const [username, setUsername] = useState("");
  const handlePasswordChange = (event) => {
    const { value } = event.target;
    setPassword(value);

    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$/;
    if (!passwordRegex.test(value)) {
      setPasswordError(
        "비밀번호는 8자리에서 16자리의 영문 숫자 조합이어야 합니다."
      );
    } else {
      setPasswordError("");
    }
  };

  const handleEmailChange = (event) => {
    const { value } = event.target;
    setEmail(value);

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(value)) {
      setEmailError("유효한 이메일 주소를 입력해주세요.");
    } else {
      setEmailError("");
    }
  };

  const handleChange = (e) => {
    const regex = /^[0-9\b -]{0,13}$/;
    if (regex.test(e.target.value)) {
      setInputValue(e.target.value);
    }
  };

  useEffect(() => {
    if (inputValue.length === 10) {
      setInputValue(inputValue.replace(/(\d{3})(\d{3})(\d{4})/, "$1-$2-$3"));
    }
    if (inputValue.length === 13) {
      setInputValue(
        inputValue
          .replace(/-/g, "")
          .replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3")
      );
    }
  }, [inputValue]);

  const handleSignUp = async (e) => {
    e.preventDefault();
    const userData = {
      name,
      username,
      password,
      email,
      phone: inputValue.replace(/-/g, ""),
    };

    try {
      const response = await axios.post(
        "https://localhost:3000/auth",
        userData
      );
      console.log(response.data);
      // 성공 처리
    } catch (error) {
      console.error(error);
      // 실패 처리
    }
  };

  return (
    <>
      <MainContainer>
        <Header>회원가입하고 다양한 혜택을 누리세요!</Header>
        <Form>
          <Label>
            이름(닉네임) *
            <Input type="text" />
          </Label>
          <Label>
            아이디 *
            <Input type="text" />
          </Label>
          <Label>
            비밀번호(8~16자리 영문, 숫자 조합) *
            <Input
              type="password"
              value={password}
              onChange={handlePasswordChange}
            />
            {passwordError && <Error>{passwordError}</Error>}
          </Label>
          <Label>
            이메일
            <Input
              type="email"
              value={email}
              onChange={handleEmailChange}
              placeholder="email@ddd.com"
            />
            {emailError && <Error>{emailError}</Error>}
          </Label>
          <Label>
            휴대폰 번호 *
            <PhoneContainer>
              <Input
                type="text"
                onChange={handleChange}
                value={inputValue}
                placeholder="010-0000-0000"
              />
              <Button>인증번호</Button>
            </PhoneContainer>
          </Label>
          <Label>
            인증번호 입력 *
            <PhoneContainer>
              <Input type="text" />
              <Button>확인</Button>
              <Button>재전송</Button>
            </PhoneContainer>
          </Label>
          <Agreement>
            필수동의 항목 및 개인정보 수집 및 이용 동의(선택), 광고성 정보
            수신(선택)에 모두 동의합니다.
          </Agreement>
        </Form>
      </MainContainer>
    </>
  );
}

export default SignUp;

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

const PhoneContainer = styled.div`
  width: 95%;
  display: flex;
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
  width: 100px;
`;

const Agreement = styled.div`
  border: 1px solid #bababa;
  padding: 1.3rem;
  margin-top: 30px;
  font-size: 12px;
  color: #555;
`;

const Error = styled.div`
  color: red;
  font-size: 12px;
  margin-top: 5px;
  margin-bottom: 4px;
`;
