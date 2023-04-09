import React, { useState } from 'react';
import { Button } from 'react-bootstrap';
import Form from 'react-bootstrap/Form';
import * as config from '../../common/config';
import { useNavigate } from 'react-router-dom';

const LoginForm = () => {
  const navigate = useNavigate();
  /* 상태 관리*/
  const [member, setMember] = useState({
    memberId: '',
    password: '',
  });

  /* 데이터 변경시 객체에 값 채우기 */
  const changeValue = (e) => {
    setMember({ ...member, [e.target.name]: e.target.value });
  };

  const login = () => {
    var data = member;
    console.log(data);
    //return false;
    fetch(config.BACKEND_URL + '/members/login', {
      method: 'POST',
      headers: {
        //데이터 타입 지정
        'Content-Type': 'application/json; charset=utf-8',
      },
      body: JSON.stringify(data),
    })
      .then((res) => res.json())
      .then((res) => {
        console.log(1, res);
        if (res.message == 'error') {
          alert('해당하는 유저를 찾을 수 없습니다.');
        } else {
          sessionStorage.setItem('user', JSON.stringify(res));
          navigate('/');
        }

        //if
      });
  };

  const clearStorage = () => {
    sessionStorage.clear();
    window.location.reload();
  };

  return (
    <div>
      <Form>
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>Email address</Form.Label>
          <Form.Control
            type="email"
            placeholder="Enter email"
            name="memberId"
            onChange={changeValue}
          />
          <Form.Text className="text-muted">
            We'll never share your email with anyone else.
          </Form.Text>
        </Form.Group>
        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            placeholder="Password"
            name="password"
            onChange={changeValue}
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="formBasicCheckbox">
          <Form.Check type="checkbox" label="Check me out" />
        </Form.Group>
        <Button variant="primary" onClick={login}>
          로그인
        </Button>
      </Form>
    </div>
  );
  //<Button onClick={login}>로그인</Button>
  //<Button onClick={clearStorage}>로그아웃</Button>
};

export default LoginForm;
