import React, { useState, useEffect } from "react";


import { Button, Row, Col, ButtonGroup, ToggleButton } from "react-bootstrap";

import { TestQuestionsProps } from "../types/interfacesTest";


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
		{name: "Disagree strongly", value: "1"},
		{name: "Disagree moderately", value: "2"},
		{name: "Disagree a little", value: "3"},
		{name: "Neither agree nor disagree", value: "4"},
		{name: "Agree a little", value: "5"},
		{name: "Agree moderately", value: "6"},
		{name: "Agree strongly", value: "7"},
	]

	const [questionIdx, setQuestionIdx] = useState(() => {
		return JSON.parse(localStorage.getItem("questionIdx") || "0")
	})

	useEffect(() => {
		localStorage.setItem("questionIdx", JSON.stringify(questionIdx))
	}, [questionIdx, setQuestionIdx])

	const onAnswer = (value: string) => {
		let temp = props.answers
		temp[questionIdx] = value
		props.onAnswersChange(temp)
	}

	const onCancel = () => {
		localStorage.setItem("questionIdx", JSON.stringify(0))
		props.onAnswersChange([])
		props.onInTestChange(false)
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
			<hr/>
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
								checked = {props.answers[questionIdx] === answer.value}
								onChange = {(event) => onAnswer(event.currentTarget.value)}
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
						onClick = {() => onCancel()}
					>
						Cancel
					</Button>{' '}
					<Button
						disabled = {questionIdx === 0}
						onClick = {() => setQuestionIdx(questionIdx - 1)}
					>
						Back
					</Button>{' '}
					{questionIdx < 9 ?
						<Button
							disabled = {typeof props.answers[questionIdx] === "undefined"}
							onClick = {() => setQuestionIdx(questionIdx + 1)}
						>
							Next
						</Button>
						:
						<Button
							disabled = {typeof props.answers[questionIdx] === "undefined"}
							onClick = {() => {
								localStorage.setItem("questionIdx", JSON.stringify(0))
								props.onResultShow(true)
							}}
						>
							Submit
						</Button>
					}
				</Col>
			</Row>
		</>
	)
}

export default TestQuestions;