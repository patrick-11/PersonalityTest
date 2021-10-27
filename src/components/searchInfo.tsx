import React from "react"


import { Container, Col, Row } from "react-bootstrap"

import { SearchInfoProps } from "../types/interfacesSearch"


const searchInfo = (props: SearchInfoProps) => {
	return (
		<Container>
			<Row>
				<Col>
					<h3>Name: {props.name}</h3>
				</Col>
				<Col className="text-center">
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

export default searchInfo