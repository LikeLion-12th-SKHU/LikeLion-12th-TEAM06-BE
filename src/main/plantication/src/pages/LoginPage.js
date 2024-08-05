import React from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

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

export const LoginPage = () => {
  return (
    <MainContainer>
      <h1>LoginPage</h1>
    </MainContainer>
  );
};
