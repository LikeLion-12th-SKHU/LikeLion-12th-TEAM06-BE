import styled from "styled-components";
import { useNavigate, useParams } from "react-router-dom";
import Plants from "../../common/api/plantsApi.json";

function Result() {
  const { plantsName } = useParams();
  const flower = Plants[plantsName];
  const navigate = useNavigate();

  console.log("URL Parameter:", plantsName);
  console.log("Plant Data:", flower);

  if (!flower) {
    return <div>존재하지 않음.</div>;
  }

  return (
    <>
      <MainContainer>
        <PlantContainer>
          <MyPlant>당신의 식물은 ?</MyPlant>
          <img src={flower.img} alt={flower.nickname} />
          <div>
            <h2>{flower.nickname}</h2>
            <p>{flower.description}</p>
            <h5>{flower.detail.light}</h5>
            <h5>{flower.detail.water}</h5>
            <h5>{flower.detail.temp}</h5>
            <button onClick={() => navigate("/")}>홈으로 돌아가기</button>
          </div>
        </PlantContainer>
      </MainContainer>
    </>
  );
}

export default Result;

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

const MyPlant = styled.div`
  font-size: 20px;
`;
const PlantContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-top: 50px;

  img {
    margin-top: 20px;
    width: 250px;
    height: 250px;
    object-fit: cover;
    border-radius: 10px;
  }

  h2 {
    text-align: center;
    margin-top: 20px;
    font-size: 30px;
  }

  p {
    margin-top: 10px;
    font-size: 20px;
    text-align: center;
    padding: 0 20px;
  }

  button {
    display: flex;
    justify-content: center;
    align-items: center;
    color: #fff;
    background-color: #025d00;
    border: none;
    border-radius: 5px;
    width: 70%;
    height: 50px;
    font-size: 18px;
    font-weight: 600;
    margin: 20px auto 0;
  }

  h5 {
    margin: 40px;
    margin-left: 20px;
    font-size: 18px;
  }
`;
