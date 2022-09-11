import React, { useContext, useReducer, useCallback } from "react"

import { Breadcrumb } from "react-bootstrap"
import { useNavigate, useParams } from "react-router-dom"
import { ToastContext } from "../hooks/context/toastContext"
import { resultsReducer } from "../hooks/reducer/resultsReducer"
import { resultsActions } from "../hooks/reducer/actions/resultsActions"
import { resultsAPI } from "../data/results"
import ResultsTable from "../components/ResultsTable"


const UsersResults = () => {
	let navigate = useNavigate()
	let params = useParams()
	const toastContext  = useContext(ToastContext)
	const [results, dispatch] = useReducer(resultsReducer, [])


	const getResultsByUserId = useCallback((userId?: string) => {
		if(userId !== undefined) {
			resultsAPI.getResultsByUserId(userId)
			.then(response => dispatch(resultsActions.setResults(response)))
			.catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
		} else {
			toastContext.onToastAdd({show: true, title: "Error", message: "User is undefined!", color: "danger"})
		}
	}, [toastContext])

	const deleteResult = (id: string) => {
		resultsAPI.deleteResult(id)
			.then(response => {if(response) {
				dispatch(resultsActions.deleteResult(id))
				toastContext.onToastAdd({show: true, title: "Success", message: "You deleted a result successfully!", color: "danger"})
			}})
			.catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
	}

	return (
		<>
			<Breadcrumb className="align-middle">
				<Breadcrumb.Item onClick={() => navigate("/users")}>Users</Breadcrumb.Item>
				<Breadcrumb.Item active={true}>Results</Breadcrumb.Item>
			</Breadcrumb>
			<hr/>
			<ResultsTable details={"low"} results={results} userId={params.id!} getResults={getResultsByUserId} deleteResult={deleteResult}/>
		</>
	)
}

export default UsersResults