import styled from "styled-components";

function TestDetail() {
  return (
    <>
      <MainContainer>
        <img src="https://wrtn-image-user-output.s3.ap-northeast-2.amazonaws.com/669b08acedeeaa24165f84ee/dd31c0b7-6de1-44f2-ba4a-7e98c766e425.png"></img>
        <button>스트레스 해소</button>
      </MainContainer>
    </>
  );
}

export default TestDetail;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 390px;
  height: 100vh;
  box-sizing: border-box;
  background-color: #fdfdfd;
  margin: 0 auto;
`;
