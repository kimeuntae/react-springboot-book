import React, { useEffect, useState } from 'react';
import BookItem from '../../component/BookItem';
import BookPaging from '../../component/BookPaging';
import { useParams } from 'react-router-dom';
import * as config from '../../common/config';

const Home = () => {
  //const [logState, setLogState] = useState();
  //setLogState(sessionStorage.getItem('email'));
  let user = sessionStorage.getItem('user');
  console.log(99, JSON.parse(user));

  const { page } = useParams();
  const [books, setBooks] = useState([]);

  /*페이징 상태관리 */
  const [paging, setPaging] = useState([]);
  const [pageNum, setPageNum] = useState();

  let val = page === undefined ? 0 : page;

  //페이징 처리
  //함수 실행시 최초 한번 실행되는 것
  useEffect(() => {
    if (sessionStorage.length == 0) {
      alert('세션이 종료되었습니다.');
      return true;
    }
    fetch(config.BACKEND_URL + '/page?page=' + page, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        Authorization: 'Bearer ' + JSON.parse(sessionStorage.user).accessToken,
      },
    })
      .then((res) => res.json())
      .then((res) => {
        console.log(1, res);
        setBooks(res.content);
        setPaging(res);
      }); //비동기 함수
  }, [page]);

  return (
    <div>
      {books.map((book) => (
        <BookItem key={book.id} book={book} pageNum={pageNum} />
      ))}
      <br />
      <BookPaging paging={paging} />
    </div>
  );
};

export default Home;
