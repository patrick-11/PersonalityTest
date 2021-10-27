import React from "react"


import { Container, Button } from "react-bootstrap"

import TestStart from "../components/testStart"
import TestQuestions from "../components/testQuestions"
import SearchInfo from "../components/searchInfo"
import RadarChart from "../components/radarChart"

import { TestProps } from "../types/interfacesTest"


const Test = (props: TestProps) => {
	return (
		<Container>
			<hr/>
				<div className="text-center">
					<h2>Ten-Item Personality Inventory - (TIPI)</h2>
				</div>
			<hr/>
				{props.inTest ?
					props.user.results.length === 0 ?
						<TestQuestions
							user = {props.user}
							onInTestChange = {props.onInTestChange}
							onUserChange = {props.onUserChange}
						/>
						:
						<>
							<SearchInfo
									name = {props.user.name}
									gender = {props.user.gender}
									age = {props.user.age}
									avgScore = {props.user.avgScore}
							/>
							<hr/>
							<RadarChart
								results = {props.user.results}
							/>
							<hr/>
							<div className="text-center">
								<Button
									onClick = {() => {
										props.onInTestChange(false)
										props.onUserChange("finish", null)
									}}
								>
									Finish
								</Button>
							</div>
						</>
					:
					<TestStart
						user = {props.user}
						onInTestChange = {props.onInTestChange}
						onRegisterShow = {props.onRegisterShow}
					/>
				}
			<hr/>
		</Container>
	)
}

export default Test