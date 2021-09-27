import React from "react";


import { Container, Col, Row } from "react-bootstrap";

import { InfoProps } from "../types/interfaces";


const infoChart = (props: InfoProps) => {
	return (
		<Container>
			<Row>
				<Col>
					<h3>Conducted Tests: {props.info.testCount}</h3>
				</Col>
				<Col>
					<h3>Average Score: {props.info.avgScore}</h3>
				</Col>
				<Col>
					<h3>Standard Deviation: {props.info.sdScore}</h3>
				</Col>
			</Row>
		</Container>
	)
}

export default infoChart;