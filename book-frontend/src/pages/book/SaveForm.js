import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import { useNavigate } from 'react-router-dom';
import * as config from '../../common/config';

const SaveForm = () => {
  let user = sessionStorage.getItem('user');

  //초기설정
  const [book, setBook] = useState({
    title: '',
    author: '',
  });

  //기존값은 그대로 들어감
  const changeValue = (e) => {
    setBook({ ...book, [e.target.name]: e.target.value });
  };

  const navigate = useNavigate();

  const submitBook = (e) => {
    e.preventDefault();
    fetch(config.BACKEND_URL + '/book', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        Authorization: 'Bearer ' + JSON.parse(sessionStorage.user).accessToken,
      },
      body: JSON.stringify(book),
    })
      .then((res) => {
        if (res.status === 201) {
          return res.json();
        } else {
          return null;
        }
      })
      .then((res) => {
        if (res !== null) {
          navigate('/');
        } else {
          alert('책 등록 실패');
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };
  return (
    <Form onSubmit={submitBook}>
      <Form.Group className="mb-3" controlId="title">
        <Form.Label>Title</Form.Label>
        <Form.Control
          type="text"
          placeholder="Enter title"
          onChange={changeValue}
          name="title"
        />
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicPassword">
        <Form.Label>Author</Form.Label>
        <Form.Control
          type="text"
          placeholder="Enter Author"
          onChange={changeValue}
          name="author"
        />
      </Form.Group>
      <Button variant="primary" type="submit">
        Submit
      </Button>
    </Form>
  );
};

export default SaveForm;
