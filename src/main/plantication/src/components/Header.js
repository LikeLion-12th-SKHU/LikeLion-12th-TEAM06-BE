import styled from "styled-components";
import { useNavigate } from "react-router-dom";

function Header() {
  const navigate = useNavigate();

  return (
    <MainContainer>
      <HeaderSt>
        <Logo
          src="/assets/img/로고3.png"
          alt="로고"
          onClick={() => navigate("/")}
        />
        <BtnStyle onClick={() => navigate("/auth")}>로그인</BtnStyle>
      </HeaderSt>
    </MainContainer>
  );
}

export default Header;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 390px;
  box-sizing: border-box;
  background-color: #fdfdfd;
  margin: 0 auto;
  z-index: 1;
`;

const HeaderSt = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 10px;
`;

const BtnStyle = styled.button`
  margin-top: 5px;
  border: none;
  background-color: #025d00;
  border-radius: 5px;
  color: #fff;
  height: 25px;
  width: 60px;
  cursor: pointer;
`;

const Logo = styled.img`
  width: auto;
`;
