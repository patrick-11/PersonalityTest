import React from "react";


import { Container, Col, Row } from "react-bootstrap";

import { InfoChartProps } from "../types/interfacesHome";


const infoChart = (props: InfoChartProps) => {
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