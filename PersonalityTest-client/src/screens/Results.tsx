import React, { useContext, useReducer, useCallback, useEffect } from "react"

import { ToastContext } from "../hooks/context/ToastContext"
import { ResultsReducer } from "../hooks/reducer/ResultsReducer"
import { ResultsActions } from "../hooks/reducer/actions/ResultsActions"
import { ResultsAPI } from "../data/ResultsAPI"
import ResultsTable from "../components/ResultsTable"


const Results = () => {
  const toastContext  = useContext(ToastContext)
  const [results, dispatch] = useReducer(ResultsReducer, [])


  const getResults = useCallback(() => {
    ResultsAPI.getResults()
      .then(response => dispatch(ResultsActions.setResults(response)))
      .catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
  }, [toastContext])

  const deleteResult = (id: string) => {
    ResultsAPI.deleteResult(id)
      .then(response => {if(response) {
        dispatch(ResultsActions.deleteResult(id))
        toastContext.onToastAdd({show: true, title: "Success", message: "You deleted a result successfully!", color: "success"})
      }})
      .catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
  }

  useEffect(() => {getResults()}, [getResults])

  return (<ResultsTable results={results} getResults={getResults} deleteResult={deleteResult}/>)
}

export default Results