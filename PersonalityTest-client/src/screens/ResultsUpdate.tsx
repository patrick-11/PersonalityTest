import React, { useContext, useState, useReducer, useEffect } from "react"

import { Breadcrumb } from "react-bootstrap"
import { useNavigate, useParams } from "react-router-dom"
import { ToastContext } from "../hooks/context/toastContext"
import { resultsReducer } from "../hooks/reducer/resultsReducer"
import { resultsActions } from "../hooks/reducer/actions/resultsActions"
import { resultsAPI } from "../data/results"
import TestQuestions from "../components/TestQuestions"


type ResultUpdateType = {
	id: string,
	questionIndex: number,
	answers: Array<number>
}

const ResultsUpdate = () => {
	let navigate = useNavigate()
	let params = useParams()
	const toastContext  = useContext(ToastContext)
	const dispatch = useReducer(resultsReducer, [])[1]
	const resultUpdateDefault = {id: "", questionIndex: 0, answers: []}
	const [resultUpdate, setResultUpdate] = useState(() => {return JSON.parse(localStorage.getItem("result") || JSON.stringify(resultUpdateDefault))})

	useEffect(() => {
		const getResult = (id: string) => {
			resultsAPI.getResult(id)
				.then(response => {setResultUpdate((prevResultUpdate: ResultUpdateType) => ({...prevResultUpdate, "id": id, "answers": response.answers}))})
				.catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
		}
		if(params.id !== undefined) {getResult(params.id)}
	}, [toastContext, params.id])


	const updateResult = (id: string, answers: Array<number>) => {
		resultsAPI.updateResult(id, answers)
			.then(response => {
				dispatch(resultsActions.updateResult(response))
				toastContext.onToastAdd({show: true, title: "Success", message: "You updated a result successfully!", color: "success"})
			})
			.catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
	}

	const updateResultUpdate = (result: ResultUpdateType) => {
		setResultUpdate(result)
		localStorage.setItem("result", JSON.stringify(result))
	}

	const resetResultUpdate = () => {updateResultUpdate(resultUpdateDefault)}

	const incrementQuestionIndex = () => {
		if(resultUpdate.questionIndex < 10) {updateResultUpdate({...resultUpdate, questionIndex: resultUpdate.questionIndex + 1})}
	}

	const decrementQuestionIndex = () => {
		if(resultUpdate.questionIndex > 0) {updateResultUpdate({...resultUpdate, questionIndex: resultUpdate.questionIndex - 1})}
	}

	const onAnswer = (value: number) => {
		resultUpdate.answers[resultUpdate.questionIndex] = value
		updateResultUpdate({...resultUpdate, "answers": resultUpdate.answers})
	}

	const onCancel = () => {
		resetResultUpdate()
		navigate("/results")
	}

	const onSubmit = () => {
		updateResult(resultUpdate.id, resultUpdate.answers)
		onCancel()
	}

	return (
		<>
			<Breadcrumb className="align-middle">
				<Breadcrumb.Item onClick={() => onCancel()}>Results</Breadcrumb.Item>
				<Breadcrumb.Item active={true}>Result</Breadcrumb.Item>
			</Breadcrumb>
			<hr/>
			<TestQuestions
				user={resultUpdate}
				onAnswer={onAnswer}
				onCancel={onCancel}
				onSubmit={onSubmit}
				onIncrement={incrementQuestionIndex}
				onDecrement={decrementQuestionIndex}
			/>
		</>
	)
}

export default ResultsUpdate