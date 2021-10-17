import React from "react"


import { Container, Col, Row } from "react-bootstrap"

import { HomeInfoProps } from "../types/interfacesHome"


const homeInfo = (props: HomeInfoProps) => {
	return (
		<Container>
			<Row>
				<Col>
					<h3>Conducted Tests: {props.info.testCount}</h3>
				</Col>
				<Col className="text-center">
					<h3>Average Score: {props.info.avgScore}</h3>
				</Col>
				<Col className="text-end">
					<h3>Standard Deviation: {props.info.sdScore}</h3>
				</Col>
			</Row>
		</Container>
	)
}

export default homeInfo