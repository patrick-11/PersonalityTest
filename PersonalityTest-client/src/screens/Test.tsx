import React, { useContext, useReducer } from "react"

import { ToastContext } from "../hooks/context/ToastContext"
import { UserContext } from "../hooks/context/UserContext"
import { useNavigate } from "react-router-dom"
import { ResultsReducer } from "../hooks/reducer/ResultsReducer"
import { ResultsActions } from "../hooks/reducer/actions/ResultsActions"
import { ResultsAPI } from "../data/ResultsAPI"
import TestStart from "../components/TestStart"
import TestQuestions from "../components/TestQuestions"


const Test = () => {
  let navigate = useNavigate()
  const toastContext  = useContext(ToastContext)
  const userContext  = useContext(UserContext)
  const dispatch = useReducer(ResultsReducer, [])[1]


  const createResult = (userId: string, answers: Array<number>) => {
    ResultsAPI.createResult(userId, answers)
      .then(response => {
        dispatch(ResultsActions.setResult(response))
        toastContext.onToastAdd({show: true, title: "Success", message: "You have successfully finshed the test!", color: "success"})
        navigate("/results/" + response.id)
      })
      .catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
  }

  const onAnswer = (value: number) => {
    userContext.user.answers[userContext.user.questionIndex] = value
    userContext.updateUser({...userContext.user, answers: userContext.user.answers})
  }

  const onCancel = () => {userContext.updateUser({...userContext.user, inTest: false, questionIndex: 0, answers: []})}

  const onSubmit = () => {
    createResult(userContext.user.id, userContext.user.answers)
    onCancel()
  }

  if(!userContext.user.inTest) {
    return (<TestStart/>)
  } else {
    return (
      <TestQuestions
        user={userContext.user}
        onAnswer={onAnswer}
        onCancel={onCancel}
        onSubmit={onSubmit}
        onIncrement={userContext.incrementQuestionIndex}
        onDecrement={userContext.decrementQuestionIndex}
      />
    )
  }
}

export default Test