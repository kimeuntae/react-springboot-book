import React, { useState } from 'react';
import { Button } from 'react-bootstrap';

const LoginForm = () => {
  const login = () => {
    sessionStorage.setItem(
      'user',
      JSON.stringify({
        id: 'test',
        email: 'test1',
        text: 'dfdf',
      }),
    );
  };

  const clearStorage = () => {
    sessionStorage.clear();
    window.location.reload();
  };

  return (
    <div>
      <Button onClick={login}>로그인</Button>
      <Button onClick={clearStorage}>로그아웃</Button>
    </div>
  );
};

export default LoginForm;
