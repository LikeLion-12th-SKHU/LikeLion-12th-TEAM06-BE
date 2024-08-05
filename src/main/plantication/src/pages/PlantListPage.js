import React from "react";
import styled from "styled-components";

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 375px;
  height: 100vh;
  box-sizing: border-box;
  margin: auto;
  border: 1px solid;
`;

export const PlantListPage = () => {
  return (
    <MainContainer>
      <h1>PlantListPage</h1>
    </MainContainer>
  );
};
