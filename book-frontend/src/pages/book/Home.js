import React, { useEffect, useState } from 'react';
import BookItem from '../../component/BookItem';
import BookPaging from '../../component/BookPaging';
import { useParams } from 'react-router-dom';

const Home = () => {
  const { page } = useParams();
  const [books, setBooks] = useState([]);

  /*페이징 상태관리 */
  const [paging, setPaging] = useState([]);
  const [pageNum, setPageNum] = useState();

  let val = page === undefined ? 0 : page;

  //페이징 처리
  //함수 실행시 최초 한번 실행되는 것
  useEffect(() => {
    fetch('http://localhost:8080/page?page=' + val)
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
