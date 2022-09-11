import React, { useContext, useReducer, useEffect } from "react"

import { Breadcrumb } from "react-bootstrap"
import { useNavigate, useParams } from "react-router-dom"
import { ToastContext } from "../hooks/context/toastContext"
import { resultsReducer } from "../hooks/reducer/resultsReducer"
import { resultsActions } from "../hooks/reducer/actions/resultsActions"
import { resultsAPI } from "../data/results"
import RadarChart from "../components/RadarChart"


const ResultsShow = () => {
	let navigate = useNavigate()
	let params = useParams()
	const toastContext  = useContext(ToastContext)
	const [results, dispatch] = useReducer(resultsReducer, [])

	useEffect(() => {
		const getResult = (id: string) => {
			resultsAPI.getResult(id)
				.then(response => dispatch(resultsActions.setResult(response)))
				.catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
		}
		if(params.id !== undefined) {getResult(params.id)}
	}, [params.id, toastContext])


	if(results.length > 0) {
		return (
			<>
				<Breadcrumb className="align-middle">
					<Breadcrumb.Item onClick={() => navigate("/results")}>Results</Breadcrumb.Item>
					<Breadcrumb.Item active={true}>Result</Breadcrumb.Item>
				</Breadcrumb>
				<hr/>
				<RadarChart result={results[0].results}/>
			</>
		)
	}
	return null
}

export default ResultsShow