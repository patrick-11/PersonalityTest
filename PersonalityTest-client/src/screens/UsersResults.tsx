import React, { useContext, useReducer, useCallback, useEffect } from "react"

import { Breadcrumb } from "react-bootstrap"
import { useNavigate, useParams } from "react-router-dom"
import { ToastContext } from "../hooks/context/ToastContext"
import { ResultsReducer } from "../hooks/reducer/ResultsReducer"
import { ResultsActions } from "../hooks/reducer/actions/ResultsActions"
import { ResultsAPI } from "../data/ResultsAPI"
import { UsersAPI } from "../data/UsersAPI"
import UsersResultsTable from "../components/UsersResultsTable"


const UsersResults = () => {
  let navigate = useNavigate()
  let params = useParams()
  const toastContext  = useContext(ToastContext)
  const [results, dispatch] = useReducer(ResultsReducer, [])


  const getResultsByUserId = useCallback((userId: string) => {
    UsersAPI.getUserResults(userId)
      .then(response => dispatch(ResultsActions.setResults(response)))
      .catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
  }, [toastContext])

  const deleteResult = (id: string) => {
    ResultsAPI.deleteResult(id)
      .then(response => {if(response) {
        dispatch(ResultsActions.deleteResult(id))
        toastContext.onToastAdd({show: true, title: "Success", message: "You deleted a result successfully!", color: "danger"})
      }})
      .catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
  }

  useEffect(() => {getResultsByUserId(params.id!)}, [getResultsByUserId])

  return (
    <>
      <Breadcrumb className="align-middle">
        <Breadcrumb.Item onClick={() => navigate("/users")}>Users</Breadcrumb.Item>
        <Breadcrumb.Item active={true}>Results</Breadcrumb.Item>
      </Breadcrumb>
      <hr/>
      <UsersResultsTable results={results} userId={params.id!} getResults={getResultsByUserId} deleteResult={deleteResult}/>
    </>
  )
}

export default UsersResults