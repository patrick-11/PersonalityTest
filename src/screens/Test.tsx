import React from "react";


import { Container, Button } from "react-bootstrap";

import TestStart from "../components/testStart";
import TestQuestions from "../components/testQuestions";
import RadarChart from "../components/radarChart";

import { TestProps } from "../types/interfaces";


const Test = (props: TestProps) => {
	return (
		<Container>
			<hr/>
				<div className="text-center">
					<h2>Ten-Item Personality Inventory - (TIPI)</h2>
				</div>
			<hr/>
				{props.inTest ?
					!props.resultShow ?
						<TestQuestions
							answers = {props.answers}
							onInTestChange = {props.onInTestChange}
							onAnswersChange = {props.onAnswersChange}
							onResultShow = {props.onResultShow}
						/>
						:
						<div className="text-center">
							<RadarChart
								results = {props.results}
								onInTestChange = {props.onInTestChange}
								onAnswersChange = {props.onAnswersChange}
								onResultShow = {props.onResultShow}
							/>
							<hr/>
							<Button
								onClick = {() => {
									props.onResultShow(false)
									props.onInTestChange(false)
									props.onAnswersChange([])
								}}
							>
								Finish
							</Button>
						</div>
					:
					<TestStart
						profile = {props.profile}
						onInTestChange = {props.onInTestChange}
						onRegisterShow = {props.onRegisterShow}
					/>
				}
			<hr/>
		</Container>
	)
}

export default Test;