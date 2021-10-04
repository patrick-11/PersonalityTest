import React from "react";


import { Container, Col, Row } from "react-bootstrap";

import { searchInfoProps } from "../types/interfacesResult";


const searchInfo = (props: searchInfoProps) => {
	return (
		<Container>
			<Row>
				<Col>
					<h3>Gender: {props.gender}</h3>
				</Col>
				<Col className="text-center">
					<h3>Age: {props.age}</h3>
				</Col>
				<Col className="text-end">
					<h3>Average Score: {props.avgScore}</h3>
				</Col>
			</Row>
		</Container>
	)
}

export default searchInfo;