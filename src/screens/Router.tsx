import React from "react"


import { BrowserRouter as Router, Switch, Route, Redirect } from "react-router-dom"

import Header from "../components/header"
import Home from "./Home"
import Test from "./Test"
import Result from "./Result"
import Search from "./Search"

import { RouterProps } from "../types/interfacesRouter"


const router = (props: RouterProps) => {
	return (
		<Router>
			<Header
				inTest = {props.inTest}
				user = {props.user}
				onLogout = {props.onLogout}
				onRegisterShow = {props.onRegisterShow}
			/>

			<Switch>
				<Route path = "/Test">
					<Test
						inTest = {props.inTest}
						user = {props.user}
						onUserChange = {props.onUserChange}
						onInTestChange = {props.onInTestChange}
						onRegisterShow = {props.onRegisterShow}
					/>
				</Route>

				<Route path = "/Results">
					<Result
						users = {props.users}
					/>
				</Route>

				<Route path = "/Search">
					<Search
						users = {props.users}
					/>
				</Route>

				<Route path = "/PersonalityTest">
					<Home
						users = {props.users}
					/>
				</Route>

				<Route path = "/">
					<Redirect to = "PersonalityTest"/>
				</Route>
			</Switch>
		</Router>
	)
}

export default router