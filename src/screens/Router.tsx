import React from "react";


import { BrowserRouter as Router, Switch, Route, Redirect } from "react-router-dom";

import Header from "../components/header";
import Home from "./Home";
import Test from "./Test";
import Result from "./Result";
import Search from "./Search";

import { AppProps } from "../types/interfaces";


const router = (props: AppProps) => {
	return (
		<Router>
			<Header
				inTest = {props.inTest}
				profile = {props.profile}
				onLogout = {props.onLogout}
				onRegisterShow = {props.onRegisterShow}
			/>

			<Switch>
				<Route path = "/PersonalityTest/Test">
					<Test
						inTest = {props.inTest}
						profile = {props.profile}
						answers = {props.answers}
						results = {props.results}
						resultShow = {props.resultShow}
						onInTestChange = {props.onInTestChange}
						onAnswersChange = {props.onAnswersChange}
						onResultShow = {props.onResultShow}
						onRegisterShow = {props.onRegisterShow}
					/>
				</Route>

				<Route path = "/PersonalityTest/Results">
					<Result
						users = {props.users}
					/>
				</Route>

				<Route path = "/PersonalityTest/Search">
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

export default router;