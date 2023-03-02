import React from 'react';
import { Button } from 'react-bootstrap';
import Pagination from 'react-bootstrap/Pagination';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';

const BookPaging = ({ paging }) => {
  const navigate = useNavigate();
  let pageNum = paging.pageable === undefined ? 0 : paging.pageable.pageNumber;

  /*부트스트랩 버튼 클릭시 화면 이동*/
  const first = () => {
    navigate('/page/' + 0);
  };
  const prev = () => {
    navigate('/page/' + (pageNum - 1));
  };
  const next = () => {
    navigate('/page/' + (pageNum + 1));
  };
  const last = () => {
    navigate('/page/' + (paging.totalPages - 1));
  };

  /* 상태관리 안할경우 Link로 던지기 */
  /* 상태관리로 페이징 처리 */
  return (
    <Pagination>
      <Pagination.First onClick={first} />
      <Pagination.Prev onClick={prev} />
      {/* MAP 페이지 번호 */}
      {Array(paging.totalPages)
        .fill()
        .map((_, i) => {
          if (i === pageNum) {
            /*상태관리는 뒤돌경우 안됨*/
            /*return (
              <Pagination.Item active onClick={() => setPageNum(i)}>
                {i + 1}
              </Pagination.Item>
            );*/
            return (
              <li class="page-item active">
                <Link className="page-link" to={'/page/' + i}>
                  {i + 1}
                </Link>
              </li>
            );
          } else {
            /*return (
              <Pagination.Item onClick={() => setPageNum(i)}>
                {i + 1}
              </Pagination.Item>
            );*/
            return (
              <li class="page-item">
                <Link className="page-link" to={'/page/' + i}>
                  {i + 1}
                </Link>
              </li>
            );
          }
        })}
      <Pagination.Next onClick={next} />
      <Pagination.Last onClick={last} />
    </Pagination>
  );
};

export default BookPaging;
