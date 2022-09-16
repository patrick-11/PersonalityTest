import React, { useContext, useReducer, useCallback, useEffect } from "react"

import { ToastContext } from "../hooks/context/ToastContext"
import { ResultsReducer } from "../hooks/reducer/ResultsReducer"
import { ResultsActions } from "../hooks/reducer/actions/ResultsActions"
import { ResultsAPI } from "../data/ResultsAPI"
import AveragesChart from "../components/AveragesChart"


const Averages = () => {
  const toastContext  = useContext(ToastContext)
  const [results, dispatch] = useReducer(ResultsReducer, [])


  const getResults = useCallback(() => {
    ResultsAPI.getResults()
      .then(response => dispatch(ResultsActions.setResults(response)))
      .catch(error =>  toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
  }, [toastContext])

  useEffect(() => {getResults()}, [getResults])

  return (<AveragesChart results={results} getResults={getResults}/>)
}

export default Averages