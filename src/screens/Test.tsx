import React from "react"


import { Container } from "react-bootstrap"

import TestStart from "../components/testStart"
import TestQuestions from "../components/testQuestions"
import SearchInfo from "../components/searchInfo"
import RadarChart from "../components/radarChart"
import ButtonCenter from "../components/buttonCenter"

import { TestProps } from "../types/interfacesTest"


const Test = (props: TestProps) => {

	const finishButtonAction = () => {
		props.onInTestChange(false)
		props.onUserChange("finish", null)
	}

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
							<ButtonCenter
								name = {"Finish"}
								size = {"lg"}
								variant = {"primary"}
								action = {finishButtonAction}
							/>
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