import React, { useContext, useReducer, useCallback, useEffect } from "react"

import { ToastContext } from "../hooks/context/toastContext"
import { resultsReducer } from "../hooks/reducer/resultsReducer"
import { resultsActions } from "../hooks/reducer/actions/resultsActions"
import { resultsAPI } from "../data/results"
import AveragesChart from "../components/AveragesChart"


const Averages = () => {
	const toastContext  = useContext(ToastContext)
	const [results, dispatch] = useReducer(resultsReducer, [])


	const getResults = useCallback(() => {
		resultsAPI.getResults()
			.then(response => dispatch(resultsActions.setResults(response)))
			.catch(error =>  toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
	}, [toastContext])

	useEffect(() => {getResults()}, [getResults])

	return (<AveragesChart results={results} getResults={getResults}/>)
}

export default Averages