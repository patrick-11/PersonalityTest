import React, { useContext, useReducer, useCallback, useEffect } from "react"

import { ToastContext } from "../hooks/context/toastContext"
import { resultsReducer } from "../hooks/reducer/resultsReducer"
import { resultsActions } from "../hooks/reducer/actions/resultsActions"
import { resultsAPI } from "../data/results"
import ResultsTable from "../components/ResultsTable"


const Results = () => {
	const toastContext  = useContext(ToastContext)
	const [results, dispatch] = useReducer(resultsReducer, [])


	const getResults = useCallback(() => {
		resultsAPI.getResults()
			.then(response => dispatch(resultsActions.setResults(response)))
			.catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
	}, [toastContext])

	const deleteResult = (id: string) => {
		resultsAPI.deleteResult(id)
			.then(response => {if(response) {
				dispatch(resultsActions.deleteResult(id))
				toastContext.onToastAdd({show: true, title: "Success", message: "You deleted a result successfully!", color: "success"})
			}})
			.catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
	}

	useEffect(() => {getResults()}, [getResults])

	return (<ResultsTable details={"high"} results={results} getResults={getResults} deleteResult={deleteResult}/>)
}

export default Results