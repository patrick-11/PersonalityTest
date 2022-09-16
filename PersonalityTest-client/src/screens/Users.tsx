import React, { useContext, useReducer, useCallback, useEffect } from "react"

import { ToastContext } from "../hooks/context/ToastContext"
import { UsersReducer } from "../hooks/reducer/UsersReducer"
import { UsersActions } from "../hooks/reducer/actions/UsersActions"
import { UsersAPI } from "../data/UsersAPI"
import { User } from "../hooks/reducer/UsersReducer"
import UsersTable from "../components/UsersTable"


const Users = () => {
  const toastContext  = useContext(ToastContext)
  const [users, dispatch] = useReducer(UsersReducer, [])


  const getUsers = useCallback(() => {
    UsersAPI.getUsers()
      .then(response => dispatch(UsersActions.setUsers(response)))
      .catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
  }, [toastContext])

  const updateUser = (user: User) => {
    UsersAPI.updateUser(user)
      .then(response => {
        dispatch(UsersActions.updateUser(response))
        toastContext.onToastAdd({show: true, title: "Success", message: "You updated a user successfully!", color: "success"})
      })
      .catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
  }

  const deleteUser = (id: string) => {
    UsersAPI.deleteUser(id)
      .then(response => {if(response) {
        dispatch(UsersActions.deleteUser(id))
        toastContext.onToastAdd({show: true, title: "Success", message: "You deleted a user successfully!", color: "success"})
      }})
      .catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
  }

  useEffect(() => {getUsers()}, [getUsers])

  return (<UsersTable users={users} getUsers={getUsers} updateUser={updateUser} deleteUser={deleteUser}/>)
}

export default Users