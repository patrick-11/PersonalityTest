import React, { useEffect, useState } from "react"


import Router from "./screens/Router"
import RegisterModal from "./components/registerModal"

import { Profile } from "./types/interfacesRouter"

import data from "./data/users"


const App = () => {
	const users = data
	const [inTest, setInTest] = useState(false)
	const [user, setUser] = useState({name: "", gender: "", age: 50, answers: [], results: [], avgScore: 0})
	const [registerShow, setRegisterShow] = useState(false)
	const [registerFail, setRegisterFail] = useState("")

	
	useEffect(() => {
		const tempInTest = JSON.parse(localStorage.getItem("inTest") || "false")
		const tempUser = JSON.parse(localStorage.getItem("user") || "null")
		if(tempInTest) {onInTestChange(tempInTest)}
		if(tempUser !== null) {onUserChange("user", tempUser)}
	}, [])

	useEffect(() => {localStorage.setItem("inTest", JSON.stringify(inTest))}, [inTest])
	
	useEffect(() => {localStorage.setItem("user", JSON.stringify(user))}, [user])

	
	const onInTestChange = (state: boolean) => {setInTest(state)}

	const onUserChange = (prop: string, value: any) => {
		switch(prop) {

			case "profile": {
				setUser((prevUser) => ({...prevUser, name: value.name, gender: value.gender, age: value.age}))
				break
			}
			case "finish": {
				setUser((prevUser) => ({...prevUser, answers: [], results: [], avgScore: 0}))
				break
			}
			case "reset": {
				setUser({name: "", gender: "", age: 50, answers: [], results: [], avgScore: 0})
				break
			}
			case "submit": {
				setUser((prevUser) => ({...prevUser, results: value.results, avgScore: value.avgScore}))
				break
			}
			case "user": {
				setUser(value)
				break
			}
			default: {
				setUser((prevUser) => ({...prevUser, [prop]: value}))
				break
			}
		}
	}
	
	const onRegisterShow = (state: boolean) => {setRegisterShow(state)}

	const onRegisterFail = () => {setRegisterFail("")}

	const onRegister = (profile: Profile) => {
		onUserChange("profile", profile)
		onRegisterShow(false)
	}

	const onLogout = () => {
		onUserChange("reset", null)
		onInTestChange(false)
		localStorage.clear()
	}
	
	return (
		<>
			<Router
				users = {users}
				inTest = {inTest}
				user = {user}
				onInTestChange = {onInTestChange}
				onUserChange = {onUserChange}
				onRegisterShow = {onRegisterShow}
				onLogout = {onLogout}

			/>

			<RegisterModal
				registerShow = {registerShow}
				registerFail = {registerFail}
				onRegisterShow = {onRegisterShow}
				onRegisterFail = {onRegisterFail}
				onRegister = {onRegister}
			/>
		</>
	)
}

export default App