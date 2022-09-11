import React, { useContext, useReducer, useCallback, useEffect } from "react"

import { ToastContext } from "../hooks/context/toastContext"
import { usersReducer } from "../hooks/reducer/usersReducer"
import { usersActions } from "../hooks/reducer/actions/usersActions"
import { usersAPI } from "../data/users"
import { User } from "../hooks//reducer/usersReducer"
import UsersTable from "../components/UsersTable"


const Users = () => {
	const toastContext  = useContext(ToastContext)
	const [users, dispatch] = useReducer(usersReducer, [])


	const getUsers = useCallback(() => {
		usersAPI.getUsers()
			.then(response => dispatch(usersActions.setUsers(response)))
			.catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
	}, [toastContext])

	const updateUser = (user: User) => {
		usersAPI.updateUser(user)
			.then(response => {
				dispatch(usersActions.updateUser(response))
				toastContext.onToastAdd({show: true, title: "Success", message: "You updated a user successfully!", color: "success"})
			})
			.catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
	}

	const deleteUser = (id: string) => {
		usersAPI.deleteUser(id)
			.then(response => {if(response) {
				dispatch(usersActions.deleteUser(id))
				toastContext.onToastAdd({show: true, title: "Success", message: "You deleted a user successfully!", color: "success"})
			}})
			.catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
	}

	useEffect(() => {getUsers()}, [getUsers])

	return (<UsersTable users={users} getUsers={getUsers} updateUser={updateUser} deleteUser={deleteUser}/>)
}

export default Users