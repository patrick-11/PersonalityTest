import React, { useState, useEffect } from "react"


import { Button, Row, Col, ButtonGroup, ToggleButton } from "react-bootstrap"

import { TestQuestionsProps } from "../types/interfacesTest"


const TestQuestions = (props: TestQuestionsProps) => {
	const questions = [
		"Extraverted, enthusiastic.", 
		"Critical, quarrelsome.", 
		"Dependable, self-disciplined.", 
		"Anxious, easily upset.", 
		"Open to new experiences, complex.", 
		"Reserved, quiet.", 
		"Sympathetic, warm.", 
		"Disorganized, careless.", 
		"Calm, emotionally stable.", 
		"Conventional, uncreative."
	]

	const answersOptions = [
		{name: "Disagree strongly", value: 1},
		{name: "Disagree moderately", value: 2},
		{name: "Disagree a little", value: 3},
		{name: "Neither agree nor disagree", value: 4},
		{name: "Agree a little", value: 5},
		{name: "Agree moderately", value: 6},
		{name: "Agree strongly", value: 7},
	]

	const [questionIdx, setQuestionIdx] = useState(() => {return JSON.parse(localStorage.getItem("questionIdx") || "0")})


	useEffect(() => {localStorage.setItem("questionIdx", JSON.stringify(questionIdx))}, [questionIdx])
	

	const onCancel = () => {
		localStorage.setItem("questionIdx", JSON.stringify(0))
		props.onUserChange("finish", null)
		props.onInTestChange(false)
	}

	const onAnswer = (value: number) => {
		let temp = props.user.answers
		temp[questionIdx] = value
		props.onUserChange("answers", temp)
	}

	const onSubmit = () => {
		localStorage.setItem("questionIdx", JSON.stringify(0))
		const tempResults = calculateResults(props.user.answers)
		const tempAvgScore = calculateAvgScore(tempResults)
		props.onUserChange("submit", {results: tempResults, avgScore: tempAvgScore})
	}

	const calculateResults = (answers: Array<number>) => {
		let temp = []
		const reverse = (value: number) => {return (8 - value)}
		temp[0] = (Number(answers[0]) + reverse(answers[5]))/2
		temp[1] = (Number(answers[6]) + reverse(answers[1]))/2
		temp[2] = (Number(answers[2]) + reverse(answers[7]))/2
		temp[3] = (Number(answers[8]) + reverse(answers[3]))/2
		temp[4] = (Number(answers[4]) + reverse(answers[9]))/2
		return temp
	}

	const calculateAvgScore = (results: Array<number>) => {
		let temp = 0
		results.forEach((result) => {temp += result})
		return (temp/results.length)
	}

	return (
		<>
			<Row>
				<Col>
					<div className="text-center">
						<h4>I see myself as: {questions[questionIdx]}</h4>
					</div>
				</Col>
			</Row>
			<br></br>
			<Row>
				<Col className="text-center">
					<ButtonGroup>
						{answersOptions.map((answer, index) => (
							<ToggleButton
								key = {index}
								id={`radio-${index}`}
								type = "radio"
								variant = "outline-primary"
								name = "radio"
								value = {answer.value}
								checked = {props.user.answers[questionIdx] === answer.value}
								onChange = {(event) => onAnswer(parseInt(event.currentTarget.value))}
							>
								{answer.name}
							</ToggleButton>
						))}
					</ButtonGroup>
				</Col>
			</Row>
			<hr/>
			<Row>
				<Col className="text-center">
					<Button
						variant = "secondary"
						onClick = {() => onCancel()}
					>
						Cancel
					</Button>{' '}
					<Button
						variant = "primary"
						disabled = {questionIdx === 0}
						onClick = {() => setQuestionIdx(questionIdx - 1)}
					>
						Back
					</Button>{' '}
					{questionIdx < 9 ?
						<Button
							variant = "primary"
							disabled = {typeof props.user.answers[questionIdx] === "undefined"}
							onClick = {() => setQuestionIdx(questionIdx + 1)}
						>
							Next
						</Button>
						:
						<Button
							variant = "success"
							disabled = {typeof props.user.answers[questionIdx] === "undefined"}
							onClick = {() => {onSubmit()}}
						>
							Submit
						</Button>
					}
				</Col>
			</Row>
		</>
	)
}

export default TestQuestions