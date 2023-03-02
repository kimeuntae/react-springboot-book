import React from 'react';
import { Card } from 'react-bootstrap';
import { Link, useParams } from 'react-router-dom';

const BookItem = ({ book }) => {
  //const { book, key } = useParams();
  //console.log(key);

  return (
    <Card>
      <Card.Body>
        <Card.Title>{book.title}</Card.Title>
        <Link
          to={'/book/' + book.id}
          className="btn btn-primary"
          varant="primary"
        >
          상세보기
        </Link>
      </Card.Body>
    </Card>
  );
};

export default BookItem;
