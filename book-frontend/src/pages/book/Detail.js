import React, { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import { useNavigate, useParams } from 'react-router-dom';

const Detail = () => {
  const navigate = useNavigate();
  const { id } = useParams();

  /* 상태관 리 */
  const [book, setBook] = useState({
    id: '',
    title: '',
    author: '',
  });
  /* 상세조회 */
  useEffect(() => {
    fetch('http://localhost:8080/book/' + id)
      .then((res) => res.json())
      .then((res) => {
        setBook(res);
      });
  }, []);

  /* 삭제 */
  const deleteBook = () => {
    fetch('http://localhost:8080/book/' + id, {
      method: 'DELETE',
    })
      .then((res) => res.text())
      .then((res) => {
        if (res === 'ok') {
          navigate('/');
        } else {
          alert('삭제실패');
        }
      });
  };
  /* 수정 */
  const updateBook = () => {
    navigate('/updateForm/' + id);
  };

  return (
    <div>
      <h1>책 상세보기</h1>
      <Button onClick={() => updateBook(book.id)}>수정</Button>{' '}
      <Button onClick={() => deleteBook(book.id)}>삭제</Button>
      <hr />
      <h3>{book.author}</h3>
      <h1>{book.title}</h1>
    </div>
  );
};

export default Detail;
