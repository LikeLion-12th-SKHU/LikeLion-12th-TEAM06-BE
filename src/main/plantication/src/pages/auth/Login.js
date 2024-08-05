import styled from "styled-components";
import { useNavigate } from "react-router-dom";

function Login() {
  const navigate = useNavigate();

  return (
    <>
      <MainContainer>
        <CoverStyle>
          <BackStyle onClick={() => navigate("/")}>←</BackStyle>
          <LoginStyle>
            <TestSt>
              사람들과 소통하고 힐링하는
              <br />
              식물 플랫폼
              <br />
              <LogoStyle>plantication</LogoStyle>
            </TestSt>
            <ButtonGroup>
              <BtnStyle>
                <img src="./assets/img/google.png" />
                Google로 계속하기
              </BtnStyle>
              <BtnStyle onClick={() => navigate("/auth-signup")}>
                <img src="./assets/img/user.png" />
                회원가입 하기
              </BtnStyle>
            </ButtonGroup>
            <LoginSt>
              이미 계정이 있으신가요?{" "}
              <LoginBtn onClick={() => navigate("/auth/Login")}>
                로그인
              </LoginBtn>
            </LoginSt>
          </LoginStyle>
        </CoverStyle>
      </MainContainer>
    </>
  );
}
export default Login;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 390px;
  height: 100vh;
  background-color: #fdfdfd;
  margin: 0 auto;
`;

const CoverStyle = styled.div`
  margin: 20px;
  padding: 10px;
`;

const LoginStyle = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  margin: 10px;
  padding: 10px;
`;

const TestSt = styled.div`
  font-size: 23px;
  text-align: left;
  margin-top: 50px;
`;

const ButtonGroup = styled.div`
  margin-top: 300px;
  width: 100%;
`;

const BtnStyle = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 18px;
  width: 100%;
  height: 40px;
  margin: 2px;
  margin-top: 5px;
  padding: auto;
  background-color: white;
  border: 1px solid black;
  border-radius: 5px;
  cursor: pointer;

  img {
    width: 24px;
    height: 24px;
    margin-right: 10px;
  }
`;

const BackStyle = styled.div`
  cursor: pointer;
  font-size: 20px;
  font-weight: bold;
`;

const LogoStyle = styled.div`
  background-color: #0a5308;
  width: fit-content;
  font-size: 38px;
  font-style: italic;
  font-weight: bolder;
  color: #fff;
  margin: 0 0 10px;
  margin-top: 5px;
`;

const LoginSt = styled.div`
  margin-top: 10px;
`;

const LoginBtn = styled.span`
  text-decoration: underline;
  cursor: pointer;
`;
